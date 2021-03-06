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

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn" %>
<div class="row">
    <div class="col-md-12">
        <form:form id="boundaryTypeSuccess" method="post"
                   class="form-horizontal form-groups-bordered" modelAttribute="boundaryType">
            <c:if test="${not empty message}">
                <div class="alert alert-success" role="alert"><spring:message code="${message}"/></div>
            </c:if>
            <div class="panel panel-primary" data-collapsed="0">
                <div class="panel-heading">
                    <div class="panel-title">
                        <strong><spring:message code="title.viewBoundaryType"/></strong>
                    </div>
                </div>
                <div class="panel-body">
                    <div class="row add-border">
                        <div class="col-md-3 col-xs-6 add-margin"><spring:message code="lbl.boundaryType.heirarchyType"/></div>
                        <div class="col-md-9 col-xs-6 add-margin">
                            <strong><c:out value="${boundaryType.hierarchyType.name}"></c:out></strong>
                            <input id="boundaryTypeId" type="hidden" value="<c:out value="${boundaryType.id}" />"/>
                        </div>
                    </div>
                    <div class="row add-border">
                        <div class="col-md-3 col-xs-6 add-margin"><spring:message code="lbl.name"/></div>
                        <div class="col-md-9 col-xs-6 add-margin">
                            <strong><c:out value="${boundaryType.name}"></c:out></strong>
                        </div>
                    </div>
                    <div class="row add-border">
                        <div class="col-md-3 col-xs-6 add-margin"><spring:message code="lbl.code"/></div>
                        <div class="col-md-9 col-xs-6 add-margin">
                            <strong><c:out value="${boundaryType.code}"></c:out></strong>
                        </div>
                    </div>
                    <div class="row add-border">
                        <div class="col-md-3 col-xs-6 add-margin"><spring:message code="lbl.local.name"/></div>
                        <div class="col-md-9 col-xs-6 add-margin">
                            <strong><c:out value="${boundaryType.localName}"></c:out></strong>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="text-center">
                    <button type="submit" id="boundaryTypeCreateBtn" class="btn btn-primary">
                        <spring:message code="lbl.create"/>
                    </button>
                    <button type="submit" id="boundaryTypeUpdateBtn" class="btn btn-primary">
                        <spring:message code="lbl.update"/>
                    </button>
                    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="self.close()"><spring:message code="lbl.close"/></button>
                </div>
            </div>
        </form:form>
    </div>
</div>
<script src="<cdn:url  value='/resources/js/app/boundarytype.js?rnd=${app_release_no}'/>"></script>
