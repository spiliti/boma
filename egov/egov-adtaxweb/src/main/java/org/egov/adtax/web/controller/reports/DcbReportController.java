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
package org.egov.adtax.web.controller.reports;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.egov.adtax.entity.Advertisement;
import org.egov.adtax.entity.AdvertisementPermitDetail;
import org.egov.adtax.search.contract.HoardingSearch;
import org.egov.adtax.service.AdvertisementPermitDetailService;
import org.egov.adtax.service.AdvertisementReportService;
import org.egov.adtax.web.controller.GenericController;
import org.egov.infra.config.core.LocalizationSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.GsonBuilder;

@Controller
@RequestMapping(value = "/reports")
public class DcbReportController extends GenericController {

    @Autowired
    private AdvertisementReportService hoardingReportService;

    @Autowired
    private AdvertisementPermitDetailService advertisementPermitDetailService;

    @RequestMapping(value = "search-for-dcbreport", method = GET)
    public String searchHoardingForm(@ModelAttribute final HoardingSearch hoardingSearch, Model model) {
    	model.addAttribute("mode", "AdvertisementwiseDCBReport");
        return "report-dcb";
    }

    @RequestMapping(value = "search-for-dcbreport", method = POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
	public String searchHoarding(@Valid @ModelAttribute("hoardingSearch") final HoardingSearch hoardingSearch,
			final BindingResult resultBinder) {
		if (resultBinder.hasErrors()) {
			List<HoardingSearch> errors = new ArrayList<>();
			HoardingSearch search;
			String criteriaName;
			for (ObjectError error : resultBinder.getAllErrors()) {
				search = new HoardingSearch();
				criteriaName = error.getCodes()[0].split("\\.")[2];
				search.setErrorMessage(new StringBuilder().append("Invalid input for ").append("adminBoundryParent".equalsIgnoreCase(criteriaName)
						? "Locality" : "adminBoundry".equalsIgnoreCase(criteriaName) ? "Ward" : criteriaName).toString());
				errors.add(search);
			}
			return new StringBuilder("{ \"error\":").append(new GsonBuilder().create().toJson(errors)).append("}")
					.toString();
		} else {
			return new StringBuilder("{ \"data\":")
					.append(new GsonBuilder().setDateFormat(LocalizationSettings.datePattern()).create().toJson(
							advertisementPermitDetailService.getAdvertisementSearchResult(hoardingSearch, null)))
					.append("}").toString();
		}
	}

    @RequestMapping(value = "getHoardingDcb/{id}")
    public String viewHoarding(@PathVariable final Long id, final Model model) {
        AdvertisementPermitDetail advertisementPermitDetail = advertisementPermitDetailService.findBy(id);
        if (advertisementPermitDetail != null) {
            final Advertisement hoarding = advertisementPermitDetail.getAdvertisement();
            model.addAttribute("hoarding", hoarding);
            model.addAttribute("dcbResult", hoardingReportService.getHoardingWiseDCBResult(hoarding));
        }
        return "report-dcbview";
    }
}
