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

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<div class="row">
	<div class="col-md-12">
		<form:form id="agencyform" method="post" class="form-horizontal form-groups-bordered" modelAttribute="agency" commandName="agency">
			<div class="panel panel-primary" data-collapsed="0">
				<div class="panel-heading ">
					<div class="panel-title">
						<strong><spring:message code="title.agency.details"/></strong>
					</div>
				</div>
				<div class="panel-body custom-form"> 										
					<div class="form-group">
                    	<label class="col-sm-3 control-label text-right"><spring:message code="lbl.agency.code"/><span class="mandatory"></span></label>
                           <div class="col-sm-3 add-margin">
                           		<c:choose>
									<c:when test="${empty agency.id}">
                               			<form:input type="text" cssClass="form-control patternvalidation" 
                        	  data-pattern="alphanumerichyphenbackslash"  maxlength="15" path="code" id="agencycode" required="required"/>
                               			<form:errors path="code" cssClass="error-msg" />
                               		</c:when>
                               		<c:otherwise>
                               			<form:input type="text" cssClass="form-control" path="code" id="agencycode" required="required" disabled="true"/>
                               			<form:errors path="code" cssClass="error-msg" />
                               		</c:otherwise>
                               	</c:choose>
                           </div>
                        <label class="col-sm-2 control-label text-right"><spring:message code="lbl.agency.name"/><span class="mandatory"></span></label>
                           <div class="col-sm-3 add-margin">
                               <form:input type="text" cssClass="form-control patternvalidation" 
                        	  data-pattern="alphanumericwithspace" path="name"  maxlength="32" id='agencyname' required="required"/>
                               <form:errors path="name" cssClass="error-msg"/>
                           </div>
                    </div>
	                <div class="form-group">
	                	<label class="col-sm-3 control-label text-right"><spring:message code="lbl.agency.ssid"/></label>
                       <div class="col-sm-3 add-margin">
                           <form:input type="text" cssClass="form-control patternvalidation" 
                        	  data-pattern="alphanumerichyphenbackslash" path="ssId" id="ssid" maxlength="15"/>
                           <form:errors path="ssId" cssClass="error-msg"/>
                       </div>
	                   <label class="col-sm-2 control-label text-right"><spring:message code="lbl.agency.deposit"/><span class="mandatory"></span></label>
                       <div class="col-sm-3 add-margin">
                           <form:input class="form-control patternvalidation" data-pattern="decimalvalue" 
                           		maxlength="10" id="depositAmount" path="depositAmount" required="required"/>
                           <form:errors path="depositAmount" cssClass="error-msg"/>
                       </div>
	                </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label text-right"><spring:message code="lbl.mobileNo"/><span class="mandatory"></span></label>
                        <div class="col-sm-3 add-margin">
                            <form:input type="text" cssClass="form-control patternvalidation" data-pattern="number" path="mobileNumber" maxlength="10" id="mobilenumber" required="required"/>
                            <form:errors path="mobileNumber" cssClass="error-msg"/>
                        </div>
                        <label class="col-sm-2 control-label text-right"><spring:message code="lbl.emailid"/></label>
                        <div class="col-sm-3 add-margin">
                            <form:input type="text" cssClass="form-control patternvalidation"   data-pattern="regexp_alphabetspecialcharacters"  path="emailId" id="emailid"  maxlength="32"/>
                            <form:errors path="emailId" cssClass="error-msg"/>
                        </div>
                    </div>
                   	<div class="form-group">
                    	<label class="col-sm-3 control-label text-right"><spring:message code="lbl.address"/></label>
                        <div class="col-sm-3 add-margin">
                        	<form:textarea path="address" id="agencyaddress" cssClass="form-control patternvalidation" 
                        	  data-pattern="alphanumericwithspacehyphenunderscore" cssErrorClass="form-control error" maxlength="250"/>
							<form:errors path="address" cssClass="error-msg"/>
                        </div>
                        <label class="col-sm-2 control-label text-right"><spring:message code="lbl.status"/><span class="mandatory"></span></label>
                        <div class="col-sm-3 add-margin">
                        	<form:select path="status" id="status_dropdown" cssClass="form-control" 
							cssErrorClass="form-control error" required="required">
								<form:option value=""><spring:message code="lbl.select" /></form:option>
								<form:options items="${status}" />
							</form:select>
							<form:errors path="status" cssClass="error-msg" />
                    	</div>
                    </div>
           		</div>
        	</div>
        	<div class="row">
				<div class="text-center">
					<button type="submit" class="btn btn-primary"><spring:message code="lbl.submit"/></button>
					<c:if test="${empty agency.id}">
						<button type="reset" class="btn btn-default"><spring:message code="lbl.reset"/></button>
					</c:if>
			        <a href="javascript:void(0)" class="btn btn-default" onclick="self.close()"><spring:message code="lbl.close"/></a>
				</div>
			</div>
		</form:form>
	</div>	
</div>
				
	


