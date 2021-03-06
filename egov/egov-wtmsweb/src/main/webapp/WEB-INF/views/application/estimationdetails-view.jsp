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

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="row">
	<div class="col-md-12">
	<form:hidden path="fieldInspectionDetails.waterConnectionDetails" id="fieldInspectionDetails" value="${waterConnectionDetails.id}"/> 
		<div class="panel panel-primary" data-collapsed="0">				
			<div class="panel-heading">
				<div class="panel-title">
					<spring:message code="lbl.fieldinspection.details" />
				</div>					
			</div>
			<div class="panel-body">
				<table class="table table-striped table-bordered" id="estimateDetails">
					<thead>
					      <tr>
							<th class="text-center"><spring:message code="lbl.slno" /></th>
							<th class="text-center"><spring:message code="lbl.material" /><span class="mandatory"></span></th>
							<th class="text-center"><spring:message code="lbl.quantity" /></th>
							<th class="text-center"><spring:message code="lbl.uom" /></th>
							<th class="text-center"><spring:message code="lbl.rate" /></th>
							<th class="text-center"><spring:message code="lbl.amount" /></th>
					      </tr>
				         </thead>
					<tbody>
						<c:forEach var="estimationDetails" items="${waterConnectionDetails.estimationDetails}" varStatus="status">
					      <tr>
							<td class="text-center"><span id="slNo1">${status.index+1}</span></td>
							<td class="text-center"><textarea class="form-control table-input" name="estimationDetails[${status.index}].itemDescription" id="estimationDetails${status.index}itemDescription" maxlength="256" readOnly="readonly">${estimationDetails.itemDescription}</textarea></td>
							<td class="text-right"><input type="text" class="form-control table-input text-right patternvalidation quantity" data-pattern="decimalvalue" name="estimationDetails[${status.index}].quantity" id="estimationDetails${status.index}quantity" maxlength="8" onblur="calculateTotalAmount();" value="${estimationDetails.quantity}" readOnly="readonly"></td>
							<td class="text-right"><input type="text" class="form-control table-input patternvalidation" data-pattern="alphanumerichyphenbackslash" name="estimationDetails[${status.index}].unitOfMeasurement" id="estimationDetails${status.index}unitOfMeasurement" maxlength="50" value="${estimationDetails.unitOfMeasurement}" readOnly="readonly"></td>
							<td class="text-right"><input type="text" class="form-control table-input text-right patternvalidation unitrate" data-pattern="decimalvalue" name="estimationDetails[${status.index}].unitRate" id="estimationDetails${status.index}unitRate" maxlength="8" onblur="calculateTotalAmount();" value="${estimationDetails.unitRate}" readOnly="readonly"></td>
							<td class="text-right"><input type="text" class="form-control table-input text-right" id="estimationDetails${status.index}amount" readOnly="readonly" "></td>
					      </tr>
					    </c:forEach>
					      <tr>
							<td class="text-center"></td>
							<td class="text-center"></td>
							<td class="text-right"></td>
							<td class="text-right"></td>
							<td class="text-right"><spring:message code="lbl.grandtotal" /></td>
							<td class="text-right"><input type="text" class="form-control text-right" id="grandTotal" readOnly="readonly"></td>
					      </tr>
					</tbody>
				</table>
				<div class="form-group">
					<div class="row">
						<label class="col-sm-3 control-label text-right"><spring:message code="lbl.categoryofroad" /></label>
						<div class="col-sm-3 add-margin">
							<select name="fieldInspectionDetails.roadCategory" id="roadCategory"
								class="form-control action-dropdown" disabled="true">
								<c:forEach items="${roadCategoryList}" var="roadCategory">
									<option></option>
									<option value="${roadCategory.id}" title="${roadCategory.name}" <c:if test="${fn:contains(waterConnectionDetails.fieldInspectionDetails.roadCategory, roadCategory)}"> Selected </c:if> >${roadCategory.name}</option>
								</c:forEach>
							</select>
						</div>
						<label class="col-sm-2 control-label text-right"><spring:message code="lbl.existingpipeline" /><span class="mandatory"></span></label>
						<div class="col-sm-3 add-margin">
							<form:input class="form-control patternvalidation" data-pattern="numericslashhyphen" maxlength="4" id="existingPipeline" path="fieldInspectionDetails.existingPipeline" value="${fieldInspectionDetails.existingPipeline}" readonly="true" />
							<form:errors path="fieldInspectionDetails.existingPipeline" cssClass="add-margin error-msg" />		
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="row">
						<label class="col-sm-3 control-label text-right"><spring:message code="lbl.pipelinedistance" /><span class="mandatory"></span></label>
						<div class="col-sm-3 add-margin">
							<form:input class="form-control patternvalidation text-right" data-pattern="number" maxlength="4" id="pipelineDistance" path="fieldInspectionDetails.pipelineDistance" value="${fieldInspectionDetails.pipelineDistance}" readonly="true" />
							<form:errors path="fieldInspectionDetails.pipelineDistance" cssClass="add-margin error-msg" />		
						</div>
						<label class="col-sm-2 control-label text-right"><spring:message code="lbl.supervisioncharges" /></label>
						<div class="col-sm-3 add-margin">
							<form:input class="form-control patternvalidation text-right" data-pattern="number" maxlength="8" id="supervisionCharges" path="fieldInspectionDetails.supervisionCharges" value="${fieldInspectionDetails.supervisionCharges}" readonly="true"/>
							<form:errors path="fieldInspectionDetails.supervisionCharges" cssClass="add-margin error-msg" />		
						</div>

					</div>
				</div>
				
				<div class="form-group">
					<div class="row">
						<label class="col-sm-3 control-label text-right "><spring:message code="lbl.securitydeposit" /></label>
						<div class="col-sm-3 add-margin "> 
							<form:input class="form-control text-right patternvalidation" data-pattern="number" maxlength="8" id="securityDeposit" path="fieldInspectionDetails.securityDeposit" value="${fieldInspectionDetails.securityDeposit}" readOnly="readonly"/>
							<form:errors path="fieldInspectionDetails.securityDeposit" cssClass="add-margin error-msg" />
						</div>
						<label class="col-sm-2 control-label text-right"><spring:message code="lbl.roadcuttingcharges" /></label>
						<div class="col-sm-3 add-margin">
							<form:input class="form-control patternvalidation text-right" data-pattern="number" maxlength="8" id="roadCuttingCharges" path="fieldInspectionDetails.roadCuttingCharges" value="${fieldInspectionDetails.roadCuttingCharges}" readOnly="readonly"/>
							<form:errors path="fieldInspectionDetails.roadCuttingCharges" cssClass="add-margin error-msg" />		
						</div>
						
					</div>
				</div>
				
				
				<div class="form-group">
					<div class="row">
						<label class="col-sm-3 control-label text-right"><spring:message code="lbl.applicationfee" /></label>
						<div class="col-sm-3 add-margin"> 
							<form:input class="form-control text-right patternvalidation" data-pattern="number" maxlength="8" id="applicationFee" path="fieldInspectionDetails.applicationFee" value="${fieldInspectionDetails.applicationFee}"  readOnly="readonly"/>
							<form:errors path="fieldInspectionDetails.applicationFee" cssClass="add-margin error-msg" />
						</div>
						<label class="col-sm-2 control-label text-right"><spring:message code="lbl.attached.document"/></label>
						<div class="col-sm-3 add-margin view-content">
							<c:choose>
								<c:when test="${waterConnectionDetails.fieldInspectionDetails.fileStore != null}">
									<a href="/egi/downloadfile?fileStoreId=${waterConnectionDetails.fieldInspectionDetails.fileStore.fileStoreId}&moduleName=WTMS" target="_blank">
									<c:out value="${waterConnectionDetails.fieldInspectionDetails.fileStore.fileName}"/></a>
								</c:when>
								<c:otherwise>
									<c:out value="N/A"/>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
			</div>				
		</div>
	</div>
</div>
<script src="<cdn:url value='/resources/js/app/estimatedetails.js?rnd=${app_release_no}'/>"></script>
<script src="<cdn:url value='/resources/js/app/documentsupload.js?rnd=${app_release_no}'/>"></script>
