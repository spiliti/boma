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
package org.egov.works.web.controller.letterofacceptance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.egov.infra.admin.master.entity.Department;
import org.egov.infra.admin.master.service.DepartmentService;
import org.egov.infra.exception.ApplicationException;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.works.letterofacceptance.entity.SearchRequestLetterOfAcceptance;
import org.egov.works.letterofacceptance.service.LetterOfAcceptanceService;
import org.egov.works.lineestimate.service.LineEstimateService;
import org.egov.works.models.workorder.WorkOrder;
import org.egov.works.utils.WorksUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/letterofacceptance")
public class CancelLetterOfAcceptanceController {
    @Autowired
    private LineEstimateService lineEstimateService;

    @Autowired
    private LetterOfAcceptanceService letterOfAcceptanceService;

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    @Qualifier("messageSource")
    private MessageSource messageSource;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private WorksUtils worksUtils;

    @RequestMapping(value = "/cancel/search", method = RequestMethod.GET)
    public String showSearchLetterOfAcceptanceForm(
            @ModelAttribute final SearchRequestLetterOfAcceptance searchRequestLetterOfAcceptance,
            final Model model) throws ApplicationException {
        model.addAttribute("departments", departmentService.getAllDepartments());
        final List<Department> departments = lineEstimateService.getUserDepartments(securityUtils.getCurrentUser());
        if (departments != null && !departments.isEmpty())
            searchRequestLetterOfAcceptance.setDepartmentName(departments.get(0).getId());
        model.addAttribute("searchRequestContractorBill", searchRequestLetterOfAcceptance);
        searchRequestLetterOfAcceptance.setDepartmentName(worksUtils.getDefaultDepartmentId());

        return "searchloa-cancel";
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public String cancelLetterOfAcceptance(final HttpServletRequest request,
            final Model model) throws ApplicationException {
        final Long letterOfAcceptanceId = Long.parseLong(request.getParameter("id"));
        final String cancellationReason = request.getParameter("cancellationReason");
        final String cancellationRemarks = request.getParameter("cancellationRemarks");
        WorkOrder workOrder = letterOfAcceptanceService.getWorkOrderById(letterOfAcceptanceId);
        final String billNumbers = letterOfAcceptanceService.checkIfBillsCreated(workOrder.getId());
        if (!billNumbers.equals("")) {
            final String message = messageSource.getMessage("error.loa.bills.created", new String[] { billNumbers }, null);
            model.addAttribute("errorMessage", message);
            return "letterofacceptance-success";
        }

        if (letterOfAcceptanceService.checkIfMileStonesCreated(workOrder)) {
            model.addAttribute("errorMessage", messageSource.getMessage("error.loa.milestone.created",
                    new String[] {}, null));
            return "letterofacceptance-success";
        }
        workOrder.setCancellationReason(cancellationReason);
        workOrder.setCancellationRemarks(cancellationRemarks);
        workOrder = letterOfAcceptanceService.cancel(workOrder);
        model.addAttribute("workOrder", workOrder);
        model.addAttribute("mode", "cancel");
        return "letterofacceptance-success";
    }
}
