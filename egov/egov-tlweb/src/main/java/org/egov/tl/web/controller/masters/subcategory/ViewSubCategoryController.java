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

package org.egov.tl.web.controller.masters.subcategory;

import org.egov.tl.entity.LicenseSubCategory;
import org.egov.tl.entity.LicenseSubCategoryDetails;
import org.egov.tl.service.LicenseSubCategoryService;
import org.egov.tl.service.SubCategoryDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
public class ViewSubCategoryController {

    @Autowired
    private LicenseSubCategoryService licenseSubCategoryService;

    @Autowired
    private SubCategoryDetailsService subCategoryDetailsService;

    @GetMapping(value = "/licensesubcategory/by-category", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<LicenseSubCategory> subcategories(@RequestParam Long categoryId) {
        return licenseSubCategoryService.getSubCategoriesByCategory(categoryId);
    }

    @GetMapping(value = "/licensesubcategory/detail", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<LicenseSubCategoryDetails> subcategoryDetailsBySubcategoryId(@RequestParam Long subCategoryId) {
        return subCategoryDetailsService.getSubcategoryDetailsBySubcategoryId(subCategoryId);
    }

    @GetMapping(value = "/licensesubcategory/detail-by-feetype", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public LicenseSubCategoryDetails subcategoryDetailBySubcategoryAndFeeType(@RequestParam Long subCategoryId, @RequestParam Long feeTypeId) {
        return subCategoryDetailsService.getSubcategoryDetailBySubcategoryAndFeeType(subCategoryId, feeTypeId);
    }

    @GetMapping(value = "/licensesubcategory/view/{code}")
    public String viewSubCategory(@PathVariable String code, RedirectAttributes attribs, Model model) {
        LicenseSubCategory licenseSubCategory = licenseSubCategoryService.getSubCategoryByCode(code);
        if (licenseSubCategory == null) {
            attribs.addAttribute("error", "error.subcategory.not.found");
            return "redirect:/licensesubcategory/view";
        }
        model.addAttribute("licenseSubCategory", licenseSubCategory);
        return "subcategory-view";
    }
}
