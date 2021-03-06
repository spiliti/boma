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
 */
 
package org.egov.mrs.web.controller.application.registration;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.egov.infra.reporting.engine.ReportOutput;
import org.egov.mrs.application.MarriageConstants;
import org.egov.mrs.application.service.MarriageCertificateService;
import org.egov.mrs.domain.entity.MarriageRegistration;
import org.egov.mrs.domain.entity.ReIssue;
import org.egov.mrs.domain.service.MarriageRegistrationService;
import org.egov.mrs.domain.service.ReIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/registration")
public class MarriageRejectionNoticeController {

	
	@Autowired
    protected MarriageRegistrationService marriageRegistrationService;
	
	@Autowired
    protected ReIssueService reIssueService;
	
	@Autowired
	private MarriageCertificateService marriageCertificateService;

	@RequestMapping(value = "/rejectionnotice", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<byte[]> generateRejectionNotice(final HttpServletRequest request,
			final HttpSession session) throws IOException {
		MarriageRegistration marriageRegistration = null;
		ReIssue reIssue = null;
		if (MarriageConstants.STATETYPE_REGISTRATION
				.equalsIgnoreCase(request.getParameter("applicationType").toString()))
			marriageRegistration = marriageRegistrationService.findByApplicationNo(request.getParameter("applicationNo"));
		else if (MarriageConstants.STATETYPE_REISSUE
				.equalsIgnoreCase(request.getParameter("applicationType").toString()))
			reIssue = reIssueService.findByApplicationNo(request.getParameter("applicationNo"));
		return generateReport(marriageRegistration, reIssue, session, request);
	}

	private ResponseEntity<byte[]> generateReport(MarriageRegistration marriageRegistration, ReIssue reIssue,
			final HttpSession session, final HttpServletRequest request) throws IOException {
		ReportOutput reportOutput = null;
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		headers.add("content-disposition", "inline;filename=RejectionNotice.pdf");
		reportOutput = marriageCertificateService.prepareRejectionCertificate(marriageRegistration, reIssue, request);
		return new ResponseEntity<byte[]>(reportOutput.getReportOutputData(), headers, HttpStatus.CREATED);
	}

}