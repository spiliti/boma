<%--
  ~    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
  ~    accountability and the service delivery of the government  organizations.
  ~
  ~     Copyright (C) 2017  eGovernments Foundation
  ~
  ~     The updated version of eGov suite of products as by eGovernments Foundation
  ~     is available at http://www.egovernments.org
  ~
  ~     This program is free software: you can redistribute it and/or modify
  ~     it under the terms of the GNU General Public License as published by
  ~     the Free Software Foundation, either version 3 of the License, or
  ~     any later version.
  ~
  ~     This program is distributed in the hope that it will be useful,
  ~     but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~     GNU General Public License for more details.
  ~
  ~     You should have received a copy of the GNU General Public License
  ~     along with this program. If not, see http://www.gnu.org/licenses/ or
  ~     http://www.gnu.org/licenses/gpl.html .
  ~
  ~     In addition to the terms of the GPL license to be adhered to in using this
  ~     program, the following additional terms are to be complied with:
  ~
  ~         1) All versions of this program, verbatim or modified must carry this
  ~            Legal Notice.
  ~            Further, all user interfaces, including but not limited to citizen facing interfaces,
  ~            Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
  ~            derived works should carry eGovernments Foundation logo on the top right corner.
  ~
  ~            For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
  ~            For any further queries on attribution, including queries on brand guidelines,
  ~            please contact contact@egovernments.org
  ~
  ~         2) Any misrepresentation of the origin of the material is prohibited. It
  ~            is required that all modified versions of this material be marked in
  ~            reasonable ways as different from the original version.
  ~
  ~         3) This license does not grant any rights to any user of the program
  ~            with regards to rights under trademark law for use of the trade names
  ~            or trademarks of eGovernments Foundation.
  ~
  ~   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
  ~
  --%>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>
<form:form role="form" method="post"
	cssClass="form-horizontal form-groups-bordered" id="usageform"
	modelAttribute="searchFilter">	
	<input type="hidden" id="wsTransactionId" name="wsTransactionId"
				value="${wsTransactionId}" />
	<input type="hidden" id="wsSource" name="wsSource"
				value="${wsSource}" />
	<input type="hidden" id="wsPortalRequest" name="wsPortalRequest"
				value="${wsPortalRequest}" />
	<jsp:include page="registration-searchform.jsp"></jsp:include>
</form:form>
<div class="row display-hide report-section" id="table_container">
	<div class="col-md-12 table-header text-left" id="searchResultsLabelDiv">
		<spring:message code="lbl.search.result" />
	</div>
	<div class="col-md-12 form-group report-table-container" id="searchResultsDiv">
		<table class="table table-bordered table-hover multiheadertbl"
			id="registration_table">
			<thead>
				<tr>
					<th><spring:message code="lbl.registration.no"/></th>
					<th><spring:message code="lbl.application.no"/></th>
					<th><spring:message code="lbl.application.date"/></th>
					<th><spring:message code="lbl.date.of.marriage"/></th>
					<th><spring:message code="lbl.husband.name"/></th>
					<th><spring:message code="lbl.wife.name"/></th>
					<th><spring:message code="lbl.marriage.fee"/></th>
					<th><spring:message code="lbl.status"/></th>
					<th><spring:message code="lbl.registrationunit"/></th>
					<th><spring:message code="lbl.action"/></th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="col-md-12 form-group" id="errorsDiv">
		<table class="table table-bordered datatable dt-responsive"
			id="errorTable" ></table>
	</div>
</div>


<script
	src="<cdn:url value='/resources/global/js/bootstrap/bootstrap.js' context='/egi'/>"></script>
<link rel="stylesheet"
	href="<cdn:url value='/resources/global/css/bootstrap/bootstrap-datepicker.css' context='/egi'/>" />
<script
	src="<cdn:url value='/resources/global/js/bootstrap/bootstrap-datepicker.js' context='/egi'/>"></script>
<link rel="stylesheet"
	href="<cdn:url value='/resources/global/css/jquery/plugins/datatables/jquery.dataTables.min.css' context='/egi'/>" />
<link rel="stylesheet"
	href="<cdn:url value='/resources/global/css/jquery/plugins/datatables/dataTables.bootstrap.min.css' context='/egi'/>">
<script type="text/javascript"
	src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/jquery.dataTables.min.js' context='/egi'/>"></script>
<script type="text/javascript"
	src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/dataTables.bootstrap.js' context='/egi'/>"></script>
<script type="text/javascript"
	src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/dataTables.tableTools.js' context='/egi'/>"></script>
<script type="text/javascript"
	src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/TableTools.min.js' context='/egi'/>"></script>
<script type="text/javascript"
	src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/responsive/js/datatables.responsive.js' context='/egi'/>"></script>

<script
	src="<cdn:url value='/resources/js/app/registrationsearch-issuecertificate.js?rnd=${app_release_no}'/> "></script>
