<%@ tag body-content="empty"  isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

<c:set var="now" value="<%=new java.util.Date()%>" />
<%-- <div class='commontopyellowbg'>Financial Management System</div>
<div class='commontopbluebg'><div class='commontopdate'>
Today is: <span class='bold' style='color:black'><fmt:formatDate value="${now}" pattern="dd/MM/yyyy"/></span>
</div>
Welcome <span class='bold' style='color:#cccccc'>${username}</span></div>
<div class='commontopbreadc' id='breadcrumb'>&nbsp;</div> --%>
<header class="navbar navbar-fixed-top"><!-- set fixed position by adding class "navbar-fixed-top" -->
	<nav class="navbar navbar-default navbar-custom navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header col-md-10 col-xs-10">
				<a class="navbar-brand" href="javascript:void(0);">
					<img src="<cdn:url cdn='${applicationScope.cdn}' value='${sessionScope.citylogo}' context='/egi'/>" height="60">
					<div>
						<span class="title2">
						  Financial Management System
						</span>
						
					</div>
				</a>
			</div>
			
			<div class="nav-right-menu col-md-2 col-xs-2">
				<ul class="hr-menu text-right">
					<li class="ico-menu">
						<a href="http://www.egovernments.org" target="_blank">
							<img src="<cdn:url cdn='${applicationScope.cdn}' value='/resources/global/images/logo@2x.png' context='/egi'/>" title="An eGovZM Solution!" height="20px">
						</a>
					</li>
					
				</ul>
			</div>
			
		</div>
	</nav>
</header>

	
 