<%--
  ~    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
  ~    accountability and the service delivery of the government  organizations.
  ~
  ~     Copyright (C) 2018  eGovernments Foundation
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

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn" %>

<div class="row">
    <div class="col-md-12">
        <c:choose>
            <c:when test="${not empty error}">
                <div class="alert alert-danger" role="alert">
                    <spring:message code="${error}"/>
                </div>
                <div class="form-group">
                    <div class="text-center">
                        <a href='javascript:void(0)' class='btn btn-default' onclick='self.close()'><spring:message code='lbl.close'/></a>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <form:form role="form" modelAttribute="licenseDocumentType" cssClass="form-horizontal form-groups-bordered">
                    <div class="panel panel-primary" data-collapsed="0">
                        <div class="panel-heading">
                            <div class="panel-title"><spring:message code="title.document.edit"/></div>
                        </div>
                        <div class="panel-body custom-form">
                            <div class="form-group">
                                <label class="col-sm-2 control-label text-right">
                                    <spring:message code="lbl.name"/><span id="mandatory" class="mandatory"></span>
                                </label>
                                <div class="col-sm-3 add-margin">
                                    <form:input path="name" id="name" class="form-control text-left patternvalidation" maxLength="50"/>
                                    <form:errors path="name" cssClass="error-msg"/>
                                </div>
                                <label class="col-sm-3 control-label text-right">
                                    <spring:message code="lbl.licenseAppType"/> <span class="mandatory"></span>
                                </label>
                                <div class="col-sm-3 add-margin">
                                    <form:select path="applicationType" id="applicationType_dropdown" cssClass="form-control"
                                                 disabled="true" cssErrorClass="form-control error">
                                        <form:option value=""><spring:message code="lbl.select"/></form:option>
                                        <form:options items="${applicationTypes}" itemLabel="name"/>
                                    </form:select>
                                    <form:errors path="applicationType" cssClass="error-msg"/>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label text-right"><spring:message code="lbl.enabled"/></label>
                                    <div class="col-sm-3 add-margin">
                                        <form:checkbox path="enabled" id="enabled"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label text-right"><spring:message code="lbl.mandatory"/></label>
                                    <div class="col-sm-3 add-margin">
                                        <form:checkbox path="mandatory" id="mandatory"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="text-center">
                            <button type='submit' class='btn btn-primary' id="buttonSubmit">
                                <spring:message code='lbl.save'/>
                            </button>
                            <button type="reset" class="btn btn-default"><spring:message code="lbl.reset"/></button>
                            <a href='javascript:void(0)' class='btn btn-default' onclick='self.close()'><spring:message code='lbl.close'/></a>
                        </div>
                    </div>
                </form:form>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<script type="text/javascript" src="<cdn:url value='/resources/js/app/license-document-type.js?rnd=${app_release_no}'/>"></script>
