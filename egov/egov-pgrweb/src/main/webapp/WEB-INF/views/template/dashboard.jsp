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
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6 lt8"> <![endif]-->
<!--[if IE 7 ]> <html lang="en" class="no-js ie7 lt8"> <![endif]-->
<!--[if IE 8 ]> <html lang="en" class="no-js ie8 lt8"> <![endif]-->
<!--[if IE 9 ]> <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html lang="en" class="no-js" id="page-top">
<!--<![endif]-->
<head>
    <c:if test="${empty analyticsEnabled}">
        <spring:eval expression="@environment.getProperty('analytics.enabled')" scope="application" var="analyticsEnabled"/>
        <spring:eval expression="@environment.getProperty('analytics.config')" scope="application" var="analyticsConfig"/>
    </c:if>
    <c:if test="${analyticsEnabled}">
        <c:out value="${analyticsConfig}" escapeXml="false"/>
    </c:if>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><tiles:insertAttribute name="title"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="<cdn:url value='/resources/global/images/favicon.png' context='/egi'/>" sizes="32x32">
    <link rel="stylesheet" href="<cdn:url value='/resources/global/css/bootstrap/bootstrap.css' context='/egi'/>">
    <link rel="stylesheet" href="<cdn:url value='/resources/css/global.css?rnd=${app_release_no}' context='/dashboard'/>">
    <link rel="stylesheet" href="<cdn:url value='/resources/css/jquery.loadmask.css' context='/dashboard'/>">
    <link rel="stylesheet" href="<cdn:url value='/resources/global/css/font-icons/font-awesome/css/font-awesome.min.css' context='/egi'/>">
    <link rel="stylesheet" href="<cdn:url value='/resources/js/jquery/plugins/jquery-ui/jquery-ui.min.css' context='/dashboard'/>"/>
    <link rel="stylesheet" href="<cdn:url value='/resources/global/js/jquery/plugins/datatables/responsive/css/dataTables.bootstrap.css' context='/egi'/>">
    <link rel="stylesheet" href="<cdn:url value='/resources/js/jquery/plugins/datatable/extensions/responsive/css/dataTables.responsive.css' context='/dashboard'/>">
    <link rel="stylesheet" href="<cdn:url value='/resources/js/jquery/plugins/bootstrap-switch/bootstrap-switch.min.css' context='/dashboard'/>" con>

    <script src="<cdn:url value='/resources/global/js/jquery/jquery.js' context='/egi'/>"></script>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="<cdn:url  value='/resources/global/js/ie8/html5shiv.min.js' context='/egi'/>"></script>
    <script src="<cdn:url  value='/resources/global/js/ie8/respond.min.js' context='/egi'/>"></script>
    <![endif]-->
    <script>
        $(window).load(function () {
            $("#cover").delay(1000).slideUp(300);
            $("#preloader-container").delay(1000).slideUp(300);
        });
        var citylat = parseFloat("${sessionScope.citylat}");
        var citylng = parseFloat("${sessionScope.citylng}");
        console.log(citylat, citylng);
    </script>
</head>
<body data-spy="scroll">
<div class="cover" id="cover"></div>
<div class="preloader-container" id="preloader-container">
    <div class="preloader-logo">eGov<img src="<cdn:url value='/resources/images/mask.gif' context='/dashboard'/>">Dashboard</div>
</div>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>

            <a class="navbar-brand customer-logo" data-toggle="tooltip" data-placement="bottom" title="Dashboard Home" href="/dashboard/home">
                <img src="<cdn:url  value='/resources/global/images/municipality_logo.png'  context='/egi'/>" height="55">
            </a>
            <a class="navbar-brand visible-lg-block visible-md-block" data-toggle="tooltip" data-placement="bottom" title="Complaint Redressal Home" href="/pgr/dashboard/home?isdefault=true">Complaints Redressal</a>
            <span class="navbar-brand visible-sm-block visible-xs-block" style="color:#FFF"><span class="title"></span></span>
        </div>
        <span class="navbar-brand pull-right egov-logo-right" style="color:#FFF">
		    <!-- span class="title"></span-->
		    <a href="http://www.zm4a.org" target="_blank">
			   <img src="<cdn:url value='/resources/global/images/logo@2x.png' context='/egi'/>" title="An eGovZM Solution!" height="20px">
			</a>
		    </span>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
                <li class="visible-sm-block visible-xs-block">
                    <a data-toggle="tooltip" data-placement="bottom" title="Home" href="/dashboard/home"><i class="fa fa-home fa-lg fa-fw"></i> Smart City Home</a>
                </li>
                <li id='deflt'>
                    <a href="javascript:void(0)" class="menu-item" data-fn="redressalEfficiency" data-win="performanceWin" id="pgrPerf"><i class="fa fa-bar-chart fa-lg fa-fw"></i> Efficiency</a>
                </li>
                <li>
                    <a href="javascript:void(0)" class="menu-item" data-fn="openCompPendency" data-win="slaWin" id="pgrSla"><i class="fa fa-pie-chart fa-lg fa-fw"></i> Pendency</a>
                </li>
                <li>
                    <a href="javascript:void(0)" class="menu-item" data-fn="compTypeDistribution" data-win="overviewWin" id="pgrOverview"><i class="fa fa-flash fa-lg fa-fw"></i> Type Distribution</a>
                </li>
                <li id='top5'>
                    <a href="javascript:void(0)" class="menu-item" data-fn="topFiveCompType" data-win="topFiveCompTypeWin" id="topFiveCompTypeview"><i class="fa fa-area-chart fa-lg fa-fw"></i> Top Five Complaint Types</a>
                </li>
                <!-- <li>
                    <a href="javascript:void(0)"  class="menu-item" data-fn="wardwiseAnalysis" data-win="wardwiseAnalysisWin" id="wardwiseAnalysis"><i class="fa fa-map-marker fa-lg fa-fw"></i> GIS Analysis</a>
                </li> -->
            </ul>
        </div>
    </div>
</nav>
<tiles:insertAttribute name="body"/>
<footer class="footer">
    <div style="margin-top: 20px;padding: 5px 19px;font-size: 12px;background: #F1F2F3;">Pamodzi Govtech ZM</a></div>
</footer>
</body>
<script src="<cdn:url value='/resources/global/js/bootstrap/bootstrap.js' context='/egi'/>"></script>
<script src="<cdn:url value='/resources/js/highchart/highstock.js' context='/dashboard'/>"></script>
<script src="<cdn:url value='/resources/js/highchart/modules/exporting.js' context='/dashboard'/>"></script>
<script src="<cdn:url value='/resources/js/highchart/highcharts-more.js' context='/dashboard'/>"></script>
<script src="<cdn:url value='/resources/js/highchart/modules/data.js' context='/dashboard'/>"></script>
<script src="<cdn:url value='/resources/js/highchart/modules/drilldown.js' context='/dashboard'/>"></script>
<script src="<cdn:url value='/resources/js/geoxml3.js' context='/dashboard'/>"></script>
<script src="https://maps.googleapis.com/maps/api/js?key=${sessionScope.googleApiKey}&v=3.exp&libraries=places"></script>
<script src="<cdn:url value='/resources/js/date.js' context='/dashboard'/>"></script>
<script src="<cdn:url value='/resources/js/jquery/plugins/jquery.loadmask.min.js' context='/dashboard'/>"></script>
<script src="<cdn:url value='/resources/js/jquery/plugins/jquery-ui/jquery-ui.min.js' context='/dashboard'/>"></script>
<link rel="stylesheet" href="<cdn:url value='/resources/global/css/jquery/plugins/datatables/jquery.dataTables.min.css' context='/egi'/>"/>
<link rel="stylesheet" href="<cdn:url value='/resources/global/css/jquery/plugins/datatables/dataTables.bootstrap.min.css' context='/egi'/>">
<script src="<cdn:url value='/resources/js/jquery/plugins/datatable/js/jquery.dataTables.min.js' context='/dashboard'/>"></script>
<script src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/dataTables.bootstrap.js' context='/egi'/>"></script>
<script src="<cdn:url value='/resources/js/jquery/plugins/datatable/extensions/responsive/js/dataTables.responsive.min.js' context='/dashboard'/>"></script>
<script src="<cdn:url value='/resources/js/jquery/plugins/bootstrap-switch/bootstrap-switch.min.js' context='/dashboard'/>"></script>
<script src="<cdn:url value='/resources/js/global.js?rnd=${app_release_no}' context='/dashboard'/>"></script>
<script src="<cdn:url value='/resources/js/app/dashboard.js?rnd=${app_release_no}'/>"></script>
</html>
