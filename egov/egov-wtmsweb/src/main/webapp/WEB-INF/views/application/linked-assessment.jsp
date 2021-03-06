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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn" %>
 <form:form method ="post" action="" class="form-horizontal form-groups-bordered"  modelAttribute="linkedAssessment" id="linkedAssessmentform"
			cssClass="form-horizontal form-groups-bordered"
			enctype="multipart/form-data">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<input type="hidden" value="${mode}" id="mode" />	
			<div class="row">
						<div class="col-md-12">

							<div class="panel panel-primary" data-collapsed="0">
								
								<div class="panel-heading">
									<div class="panel-title">
										Applicant Particulars
									</div>
									
								</div>
								<div class="panel-body custom-form">
									<form role="form" class="form-horizontal form-groups-bordered" >
										<div class="form-group">
											<label for="field-1" class="col-sm-4 control-label"><spring:message
			code="lbl.assesmentnumber" />:<span class="mandatory"></span></label>
				
											<div class="col-sm-4 add-margin">
												<form:input class="form-control patternvalidation" data-pattern="number" maxlength="50" id="assessmentNo" path="assessmentNo" />
		<form:errors path="assessmentNo" cssClass="add-margin error-msg" />		
											</div>
				
										</div>
										<div class="form-group">
											<label for="field-1" class="col-sm-4 control-label"><spring:message
			code="lbl1.consumer.number" />:<span class="mandatory"></span></label>
				
											<div class="col-sm-4 add-margin">
												<form:input class="form-control patternvalidation" data-pattern="number" maxlength="50" id="consumerNo" path="consumerNo" />
		<form:errors path="consumerNo" cssClass="add-margin error-msg" />		
								
											</div>
				
										</div>
										<div class="form-group">
											<div class="text-center">
												<button type="button" class="btn btn-primary" id="buttonid"><spring:message code="lbl.search"/></button>
								<a onclick="self.close()" class="btn btn-default" href="javascript:void(0)"><spring:message code="lbl.close"/></a>
											</div>
										</div>
									</form>
								</div>
							</div>
							
		  
							<div class="panel panel-primary hide" data-collapsed="0" id ="assessmentdetailsid">
								
								<div class="panel-heading">
									<div class="panel-title"><spring:message code="lbl.assessmentdetails" /></div>
									
								</div>
								<div class="panel-body">
									<div class="row add-border">
										<div class="col-xs-3 add-margin">
											<spring:message
			code="lbl.assesmentnumber" />
										</div>
										<div class="col-xs-3 add-margin">
										<form:input class="form-control patternvalidation" data-pattern="number" maxlength="50" id="ptassessmentNo" path="propertyAssessmentDetails.assessmentNumber" />
		<form:errors path="propertyAssessmentDetails.assessmentNumber" cssClass="add-margin error-msg" />
										</div>
										<div class="col-xs-3 add-margin">
											<spring:message
			code="lbl.assessmentstatus" />
										</div>
										<div class="col-xs-3 add-margin">
										
										<form:input class="form-control patternvalidation" data-pattern="number" maxlength="50" id="status" path="propertyAssessmentDetails.status" />
		<form:errors path="propertyAssessmentDetails.status" cssClass="add-margin error-msg" />

										</div>
									</div>
									
									<div class="row add-border">
										<div class="col-xs-3 add-margin">
											<spring:message
			code="lbl.digitalSignature.ownerName" />
										</div>
										<div class="col-xs-9 add-margin">
										<form:input class="form-control patternvalidation" data-pattern="number" maxlength="50" id="ownerName" path="propertyAssessmentDetails.ownerName" />
		<form:errors path="propertyAssessmentDetails.ownerName" cssClass="add-margin error-msg" />
										
										</div>
									</div>
<div class="row add-border">
										<div class="col-xs-3 add-margin"><spring:message
			code="lbl.propertyaddress" /></div>
										<div class="col-xs-9 add-margin view-content">
										<form:input class="form-control patternvalidation" data-pattern="number" maxlength="150" id="address" path="propertyAssessmentDetails.address" />
		<form:errors path="propertyAssessmentDetails.address" cssClass="add-margin error-msg" />
											<c:out value="${linkedAssessment.propertyAssessmentDetails.address}" />	
										</div>
									</div>
								</div>
							</div>
						

							<div class="panel panel-primary hide" data-collapsed="0" id="connectiondetailsid">
								
								<div class="panel-heading">
									<div class="panel-title">Connection Details</div>
									
								</div>
								<div class="panel-body no-margin-bottom">
									<div class="form-group no-margin-bottom">
									
		
				<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0" class="table table-bordered datatable text-center" id="waterSourceTbl">
					<thead>
						<tr>
							<th class="text-center">
								<spring:message code="lbl1.consumer.number" />
							</th>
							<th class="text-center">
								<spring:message code="lbl.isprimary" />
							</th>
							<th class="text-center">
								<spring:message code="lbl.demanddue" />
							</th>
							<th class="text-center">
								<spring:message code="lbl.status"/>
							</th>


						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
									</div>
								</div>
							</div>
							
														<div class="panel panel-primary hide" data-collapsed="0" id ="activeassessmentdetailsid">
								
								<div class="panel-heading">
									<div class="panel-title"><spring:message code="lbl.assessmentdetails" /></div>
									
								</div>
								<div class="panel-body">
									<div class="row add-border">
										<div class="col-xs-3 add-margin">
										<label><spring:message
			code="lbl.ptassesmentnumber" /><span class="mandatory"></span></label>
			</div>
			<div class="col-xs-3">
				<div class="input-group">
			<form:input id="activePropertyIdentifier" path="activeAssessmentDetails.assessmentNumber" class="form-control patternvalidation" data-pattern="number" maxlength="15" min="3" required="required" /> 
			<span class="input-group-addon" id="search-span"> <i class="fa fa-search specific"></i></span>
		</div>
		<form:errors path="activeAssessmentDetails.assessmentNumber" id="assessmentNumberError" cssClass="add-margin error-msg" />
										</div>

										<div class="col-xs-3 add-margin text-right">
											<spring:message
			code="lbl.assessmentstatus" />
										</div>
										<div class="col-xs-3 add-margin ">
										
											<form:input class="form-control patternvalidation" data-pattern="number" maxlength="50" id="actstatus" path="activeAssessmentDetails.status" />
		<form:errors path="activeAssessmentDetails.status" cssClass="add-margin error-msg" />
										</div>
									</div>
									
									<div class="row add-border">
										<div class="col-xs-3 add-margin">
											<spring:message
			code="lbl.digitalSignature.ownerName" />
										</div>
										<div class="col-xs-9 add-margin ">
										<form:input class="form-control patternvalidation" data-pattern="number" maxlength="50" id="actOwnerName" path="activeAssessmentDetails.ownerName" />
		<form:errors path="activeAssessmentDetails.ownerName" cssClass="add-margin error-msg" />	
										</div>
									</div>
<div class="row add-border">
										<div class="col-xs-3 add-margin"><spring:message
			code="lbl.propertyaddress" /></div>
										<div class="col-xs-9 add-margin ">
										<form:input class="form-control patternvalidation" data-pattern="number" maxlength="150" id="actAddress" path="activeAssessmentDetails.address" />
		<form:errors path="activeAssessmentDetails.address" cssClass="add-margin error-msg" />
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-primary hide" data-collapsed="0" id="activeconnectiondetailsid">
								
								<div class="panel-heading">
									<div class="panel-title">Connection Details</div>
									
								</div>
								<div class="panel-body no-margin-bottom">
									<div class="form-group no-margin-bottom">
										
				<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0" class="table table-bordered datatable text-center" id="activeConnDetailsTbl">
					<thead>
						<tr>
							<th colspan="1" class="text-center">
								<spring:message code="lbl1.consumer.number" />
							</th>
							<th colspan="1" class="text-center">
								<spring:message code="lbl.isprimary" />
							</th>
							<th colspan="1" class="text-center">
								<spring:message code="lbl.demanddue" />
							</th>
							<th colspan="1" class="text-center">
								<spring:message code="lbl.status"/>
							</th>
						</tr>
					</thead>
										<tbody>
					</tbody>
					
				</table>
									</div>
								</div>
							</div>
							
																<div class="form-group" id ="save-button">
											<div class="text-center">
												<button type="button" class="btn btn-primary" id="buttonsave"><spring:message code="lbl.save"/></button>
								<a onclick="self.close()" class="btn btn-default" href="javascript:void(0)"><spring:message code="lbl.close"/></a>
											</div>
										</div>
						</div>
				</div>
		</form:form>			
				<link rel="stylesheet" href="<cdn:url value='/resources/global/js/jquery/plugins/datatables/responsive/css/datatables.responsive.css' context='/egi'/>">
				<link rel="stylesheet" href="<cdn:url value='/resources/global/css/jquery/plugins/datatables/jquery.dataTables.min.css' context='/egi'/>"/>
				<link rel="stylesheet" href="<cdn:url value='/resources/global/css/jquery/plugins/datatables/dataTables.bootstrap.min.css' context='/egi'/>">
                <script src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/jquery.dataTables.min.js' context='/egi'/>"
	            type="text/javascript"></script>
                <script src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/dataTables.bootstrap.js' context='/egi'/>"
	            type="text/javascript"></script>
                <script src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/responsive/js/datatables.responsive.js' context='/egi'/>"
	            type="text/javascript"></script>
	            <script src="<cdn:url value='/resources/js/app/linked-assessment.js?rnd=${app_release_no}'/>"></script>