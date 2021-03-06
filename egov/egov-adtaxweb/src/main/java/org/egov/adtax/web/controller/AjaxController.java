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

package org.egov.adtax.web.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.egov.adtax.entity.SubCategory;
import org.egov.adtax.service.AdvertisementRateService;
import org.egov.adtax.service.SubCategoryService;
import org.egov.adtax.service.penalty.AdvertisementTaxCalculator;
import org.egov.ptis.domain.model.AssessmentDetails;
import org.egov.ptis.domain.model.enums.BasicPropertyStatus;
import org.egov.ptis.domain.service.property.PropertyExternalService;
import org.egov.ptis.wtms.PropertyIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AjaxController {

    @Autowired
    private SubCategoryService subCategoryService;

    protected @Autowired AdvertisementRateService advertisementRateService;

    private @Autowired AdvertisementTaxCalculator advertisementTaxCalculator;
    @Autowired
    @Qualifier("propertyIntegrationServiceImpl")
    private PropertyIntegrationService propertyIntegrationService;

    @RequestMapping(value = "/ajax-subCategories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<SubCategory> getDesignations(@RequestParam final Long category,
            final HttpServletResponse response) {

        List<SubCategory> subCategoryList = new ArrayList<>();
        if (category != null)
            subCategoryList = subCategoryService.getAllActiveSubCategoryByCategoryId(category);
        return subCategoryList;
    }

    @RequestMapping(value = "/ajax-assessmentDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody AssessmentDetails getAssessmentDetails(@RequestParam String assessmentNoRequest) {

        return propertyIntegrationService.getAssessmentDetailsForFlag(
                assessmentNoRequest, PropertyExternalService.FLAG_FULL_DETAILS, BasicPropertyStatus.ACTIVE);

    }

    @RequestMapping(value = "/hoarding/calculateTaxAmount", method = GET, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody Double getTaxAmount(@RequestParam final Long unitOfMeasureId,
            @RequestParam final Double measurement, @RequestParam final Long subCategoryId,
            @RequestParam final Long rateClassId) {

        return advertisementTaxCalculator.calculateTaxAmount(unitOfMeasureId, measurement, subCategoryId, rateClassId);

    }
}
