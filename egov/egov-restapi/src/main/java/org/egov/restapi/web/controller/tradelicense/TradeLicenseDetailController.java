/*
 *    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) 2018  eGovernments Foundation
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

package org.egov.restapi.web.controller.tradelicense;

import org.egov.restapi.model.RestErrors;
import org.egov.restapi.web.contracts.tradelicense.LicenseSimpleDeskRequest;
import org.egov.restapi.web.contracts.tradelicense.TradeLicenseDetailRequest;
import org.egov.restapi.web.contracts.tradelicense.TradeLicenseDetailResponse;
import org.egov.restapi.web.contracts.tradelicense.TradeLicenseSimpleDeskResponse;
import org.egov.tl.service.TradeLicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/tradelicense")
public class TradeLicenseDetailController {

    @Autowired
    @Qualifier("tradeLicenseService")
    private TradeLicenseService tradeLicenseService;
    
    @Value("${filestore.base.dir}")
    private String fileStoreBaseDir;

    @GetMapping(value = "/details", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public List<TradeLicenseDetailResponse> tradeLicenseDetails(TradeLicenseDetailRequest request) {
        return licenseDetails(request);
    }

    @GetMapping(value = "/v1.0/license/details", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public List<TradeLicenseDetailResponse> securedTradeLicenseDetails(TradeLicenseDetailRequest request) {
        return licenseDetails(request);
    }

    /**
     * @param request
     * @return
     */
    public List<TradeLicenseDetailResponse> licenseDetails(TradeLicenseDetailRequest request) {
        return tradeLicenseService.getLicenses(request.tradeLicenseLike())
                .parallelStream()
                .map(TradeLicenseDetailResponse::new)
                .collect(Collectors.toList());
    }
    
    @PostMapping(value = "/licenseappicationdetails", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public List<TradeLicenseSimpleDeskResponse> licenseAppicationDetails(@RequestBody LicenseSimpleDeskRequest request) {
    	return tradeLicenseService.getLicenses(request.tradeLicenseLikeSimpledesk())
        		.parallelStream()
        		.map(arg0 -> {
					try {
						return new TradeLicenseSimpleDeskResponse(arg0,fileStoreBaseDir);
					} catch (IOException e) {
						e.printStackTrace();
					}
					return null;
				})
        		.collect(Collectors.toList());
    }

    @ExceptionHandler(RuntimeException.class)
    public RestErrors restErrors(RuntimeException runtimeException) {
        return new RestErrors("LICENSE NOT EXIST", runtimeException.getMessage());
    }
}
