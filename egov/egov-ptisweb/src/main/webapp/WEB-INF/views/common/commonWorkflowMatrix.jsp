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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>
<c:if test="${endorsementNotices!=null && !endorsementNotices.isEmpty()}"> 
 			<jsp:include page="/WEB-INF/views/common/endorsement_history.jsp"/>
</c:if>
<c:if test="${nextAction !='END'}" > 
		<div class="panel panel-primary" data-collapsed="0" >				
			<div class="panel-body">
				</br> <input type="checkbox" id="applicationCheck" cssClass="applicationcheckbox" onchange="EnableForwardButton()"/>
				<spring:message code="lbl.checkbox" />
			</div>
		<div class="panel-heading">
			<div class="panel-title">
				<spring:message code="lbl.forwardto" />
			</div>
		</div> 
		<div class="panel-body">
	
 <c:if test="${currentState!= 'null' && !'Closed'.equalsIgnoreCase(currentState)}">
	<form:hidden path="" id="currentState" name="currentState" value="${currentState}"/>
</c:if> 
 <c:if test="${currentState!= 'null' && 'Closed'.equalsIgnoreCase(currentState)}">
	<form:hidden path="" id="currentState" name="currentState" value=""/>
</c:if> 
																																																									

<form:hidden path="" id="currentDesignation" name="currentDesignation" value="${currentDesignation}"/>
<form:hidden path="" id="additionalRule" name="additionalRule" value="${additionalRule}"/>
<form:hidden  path="" id="amountRule" name="amountRule" value="${amountRule}"/>
<form:hidden path="" id="workFlowDepartment" name="workFlowDepartment" value="${workFlowDepartment}"/>
<form:hidden path="" id="pendingActions" name="pendingActions" value="${pendingActions}"/>
<%-- <form:hidden path="" id="approverName" name="approverName" /> --%>
<form:hidden path="" name="stateType" id="stateType" value="${stateType}"/>	
				<div class="row show-row"  id="approverDetailHeading">
				<div class="show-row form-group" >
					<label class="col-sm-3 control-label text-right"><spring:message code="lbl.department"/><span class="mandatory"></span></label>
					<div class="col-sm-3 add-margin">
						<form:select path="" data-first-option="false"
							id="approvalDepartment" cssClass="form-control"
							cssErrorClass="form-control error" required="required">
							<form:option value="">
								<spring:message code="lbl.select" />
							</form:option>
							<form:options items="${approvalDepartmentList}" itemValue="id"
								itemLabel="name" />     
						</form:select>
					</div>
					<label class="col-sm-2 control-label text-right"><spring:message code="lbl.designation"/><span class="mandatory"></span></label>
					<div class="col-sm-3 add-margin">
						<form:select path="" data-first-option="false" 
							id="approvalDesignation" cssClass="form-control" onfocus="callAlertForDepartment();"
							cssErrorClass="form-control error" required="required">  
							<form:option value="">
								<spring:message code="lbl.select" />
							</form:option>
						</form:select>					
					</div>
				</div>
				<div class="show-row form-group">
					<label class="col-sm-3 control-label text-right"><spring:message code="lbl.employee"/><span class="mandatory"></span></label>
					<div class="col-sm-3 add-margin">
					<form:select path="" data-first-option="false" 
						id="approvalPosition" name="approvalPosition" cssClass="form-control" onfocus="callAlertForDesignation();" 
						cssErrorClass="form-control error" required="required">  
						<form:option value="">
							<spring:message code="lbl.select" />
						</form:option>
					</form:select>		
					</div> 
				</div>
				</div>
			
				<div class="row">
					<label class="col-sm-3 control-label text-right"><spring:message code="lbl.comments"/></label>
					<div class="col-sm-6 add-margin">
					<form:textarea class="form-control" path="" id="approvalComent"
						name="approvalComent" maxlength="1024"/>
					<small class="error-msg" style="float:right;"> <spring:message code="lbl.comments.maxlength" /> </small>	
				     </div>
				</div>
				
			</div>				
		</div>
</c:if>
<script src="<cdn:url value='/resources/js/app/commonworkflow.js'/>"></script>