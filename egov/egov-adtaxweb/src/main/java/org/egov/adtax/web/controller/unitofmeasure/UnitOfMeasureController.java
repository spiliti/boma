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
package org.egov.adtax.web.controller.unitofmeasure;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.validation.Valid;

import org.egov.adtax.entity.UnitOfMeasure;
import org.egov.adtax.service.UnitOfMeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/unitOfMeasure")
public class UnitOfMeasureController {

    private final UnitOfMeasureService unitOfMeasureService;

    @Autowired
    public UnitOfMeasureController(final UnitOfMeasureService unitOfMeasureService) {
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @ModelAttribute
    public UnitOfMeasure unitOfMeasure() {
        return new UnitOfMeasure();
    }

    @ModelAttribute(value = "unitOfMeasures")
    public List<UnitOfMeasure> getAllUnitOfMeasures() {
        return unitOfMeasureService.getAllUnitOfMeasure();
    }

    @RequestMapping(value = "create", method = GET)
    public String create() {
        return "unitOfMeasure-form";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search() {
        return "unitOfMeasure-search";
    }

    @RequestMapping(value = "create", method = POST)
    public String create(@Valid @ModelAttribute final UnitOfMeasure unitOfMeasure,
            final BindingResult errors, final RedirectAttributes redirectAttrs) {
        if (errors.hasErrors())
            return "unitOfMeasure-form";
        unitOfMeasureService.createUnitOfMeasure(unitOfMeasure);
        redirectAttrs.addFlashAttribute("unitOfMeasure", unitOfMeasure);
        redirectAttrs.addFlashAttribute("message", "message.uom.create");
        return new StringBuilder("redirect:/unitOfMeasure/success/").append(unitOfMeasure.getId()).toString();
    }

    @RequestMapping(value = "/success/{description}", method = GET)
    public ModelAndView successView(@PathVariable("description") final Long description,
            @ModelAttribute final UnitOfMeasure unitOfMeasure) {
        return new ModelAndView("unitOfMeasure/unitOfMeasure-success", "unitOfMeasure",
                unitOfMeasureService.getUnitOfMeasureById(description));

    }

}
