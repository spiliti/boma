/*
 *    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) 2017  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *            Further, all user interfaces, including but not limited to citizen facing interfaces,
 *            Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *            derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *            For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *            For any further queries on attribution, including queries on brand guidelines,
 *            please contact contact@egovernments.org
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 *
 */
package org.egov.restapi.web.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.egov.commons.entity.Source;
import org.egov.dcb.bean.ChequePayment;
import org.egov.egf.expensebill.service.ExpenseBillService;
import org.egov.egf.model.BillPayment.BillPaymentDetails;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.validation.exception.ValidationError;
import org.egov.infra.validation.exception.ValidationException;
import org.egov.model.bills.EgBillregister;
import org.egov.model.bills.BillIntegration;
import org.egov.model.service.BillIntegrationService;
import org.egov.restapi.constants.RestApiConstants;
import org.egov.restapi.model.BillRegister;
import org.egov.restapi.model.RestErrors;
import org.egov.restapi.service.BillService;
import org.egov.restapi.util.JsonConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@RestController
public class CreateBillController {
    private static final Logger LOG = Logger.getLogger(CreateBillController.class);

    @Autowired
    private BillService billService;

    @Autowired
    private ExpenseBillService expenseBillService;

    @Autowired
    private BillIntegrationService tpbiService;

    private static final String SOURCE = "source";

    /**
     * API to create works contractor bill.
     *
     * @param egBillregister
     * @param request
     * @return successMessage and billnumber - server response in JSON format
     */

    @RequestMapping(value = "/egf/bill", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public String createContractorBill(@RequestBody final String requestJson, final HttpServletRequest request,
            final HttpServletResponse response) {
        return contractorBill(requestJson, request, response);
    }

    @RequestMapping(value = "/v1.0/egf/bill", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public String createSecuredContractorBill(@RequestBody final String requestJson, final HttpServletRequest request,
            final HttpServletResponse response, final OAuth2Authentication authentication) {
        return contractorBill(requestJson, request, response);
    }

    /**
     * @param requestJson
     * @param response
     * @return
     */
    public String contractorBill(final String requestJson, final HttpServletRequest request, final HttpServletResponse response) {
        if (LOG.isDebugEnabled())
            LOG.debug("Rest API creating bill with the data: " + requestJson);
        String billNumber;
        EgBillregister egBillregister;
        EgBillregister savedBillregister;
        ApplicationThreadLocals.setUserId(2L);
        BillRegister billRegister = null;
        final JsonObject jsonObject = new JsonObject();
        try {
            billRegister = (BillRegister) getObjectFromJSONRequest(requestJson, BillRegister.class);
        } catch (final IOException e) {
            LOG.error(e.getStackTrace());
            final List<RestErrors> errorList = new ArrayList<>(0);
            final RestErrors re = new RestErrors();
            re.setErrorCode(e.getMessage());
            re.setErrorMessage(e.getMessage());
            errorList.add(re);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return JsonConvertor.convert(errorList);
        }
        try {
            final List<RestErrors> errors = billService.validateBillRegister(billRegister);
            if (!errors.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return JsonConvertor.convert(errors);
            } else {
                egBillregister = new EgBillregister();
                billService.createProjectCode(billRegister);
                billService.populateBillRegister(egBillregister, billRegister);
                savedBillregister = billService.createBill(egBillregister);
                billNumber = savedBillregister.getBillnumber();

                // Save in bill integration table

                HttpSession session = request.getSession();
                Object source = session != null ? session.getAttribute(SOURCE) : null;
                String strSource;
                if (source == null) {
                	strSource = Source.SOFTTECHWMS.toString();
                }else {
                	strSource = source.toString();
                }
                BillIntegration tpbi = new BillIntegration();
                tpbi.setEgBill(savedBillregister);
                tpbi.setSource(strSource);
                tpbi.setTpbillno(billRegister.getTpBillNo());

                tpbiService.createBillIntegration(tpbi);
            }
        } catch (final ValidationException e) {
            LOG.error(e.getStackTrace());
            final List<RestErrors> errorList = new ArrayList<>(0);

            final List<ValidationError> errors = e.getErrors();
            for (final ValidationError ve : errors) {
                final RestErrors re = new RestErrors();
                re.setErrorCode(ve.getKey());
                re.setErrorMessage(ve.getMessage());
                errorList.add(re);
            }
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return JsonConvertor.convert(errorList);
        } catch (final Exception e) {
            LOG.error(e.getMessage());
            final List<RestErrors> errorList = new ArrayList<>(0);
            final RestErrors re = new RestErrors();
            re.setErrorCode(e.getMessage());
            re.setErrorMessage(e.getMessage());
            errorList.add(re);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return JsonConvertor.convert(errorList);
        }
        jsonObject.addProperty("successMessage", "Works Bill created Successfully");
        jsonObject.addProperty("billNumber", billNumber);
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(jsonObject); 
        response.setStatus(HttpServletResponse.SC_CREATED);
        return jsonArray.toString();
    }

    /**
     * This method is used to get POJO object from JSON request.
     *
     * @param jsonString - request JSON string
     * @return
     * @throws IOException
     */
    private Object getObjectFromJSONRequest(final String jsonString, final Class cls) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
        mapper.configure(SerializationConfig.Feature.AUTO_DETECT_FIELDS, true);
        mapper.setDateFormat(ChequePayment.CHEQUE_DATE_FORMAT);
        return mapper.readValue(jsonString, cls);
    }

    @RequestMapping(value = "/egf/billpaymentdetails", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getBillAndPaymentDetails(final HttpServletResponse response, String billNo, String cityCode) {
        return billAndPaymentDetails(response, billNo);
    }
    
    @RequestMapping(value = "/v1.0/egf/billpaymentdetails", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getSecuredBillAndPaymentDetails(final HttpServletResponse response, String billNo, String cityCode) {
        return billAndPaymentDetails(response, billNo);
    }

    /**
     * @param response
     * @param billNo
     * @return
     */
    public String billAndPaymentDetails(final HttpServletResponse response, String billNo) {
        RestErrors restErrors;
        try {
            if (StringUtils.isEmpty(billNo)) {
                restErrors = new RestErrors();
                restErrors.setErrorCode(RestApiConstants.THIRD_PARTY_ERR_CODE_NO_BILLNO);
                restErrors.setErrorMessage(RestApiConstants.THIRD_PARTY_ERR_MSG_NO_BILLNO);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return JsonConvertor.convert(restErrors);
            }

            EgBillregister egBillregister = expenseBillService.getByBillnumber(billNo);
            if (egBillregister == null) {
                restErrors = new RestErrors();
                restErrors.setErrorCode(RestApiConstants.THIRD_PARTY_ERR_CODE_NOT_VALID_BILLNUMBER);
                restErrors.setErrorMessage(RestApiConstants.THIRD_PARTY_ERR_MSG_NOT_VALID_BILLNUMBER);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return JsonConvertor.convert(restErrors);
            }

            if (!egBillregister.getStatus().getCode().contentEquals("APPROVED")) {
                restErrors = new RestErrors();
                restErrors.setErrorCode(RestApiConstants.THIRD_PARTY_ERR_CODE_NO_BILL_STATUS);
                restErrors.setErrorMessage(RestApiConstants.THIRD_PARTY_ERR_MSG_NO_BILL_STATUS);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return JsonConvertor.convert(restErrors);
            }
            response.setStatus(HttpServletResponse.SC_CREATED);
            BillPaymentDetails billPaymetDetails = billService.getBillAndPaymentDetails(billNo);
            if (billPaymetDetails == null) {
                restErrors = new RestErrors();
                restErrors.setErrorCode(RestApiConstants.THIRD_PARTY_ERR_CODE_NO_DATA_FOUND);
                restErrors.setErrorMessage(RestApiConstants.THIRD_PARTY_ERR_MESSAGE_NO_DATA_FOUND);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return JsonConvertor.convert(restErrors);
            } else {
                LOG.debug(billPaymetDetails.toString());
                return JsonConvertor.convert(billPaymetDetails);
            }

        } catch (final Exception e) {
            e.printStackTrace();

            restErrors = new RestErrors();
            restErrors.setErrorCode(RestApiConstants.THIRD_PARTY_ERR_CODE_GENERIC_ERROR);
            restErrors.setErrorMessage(e.getMessage());

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            return JsonConvertor.convert(restErrors);
        }
    }

}
