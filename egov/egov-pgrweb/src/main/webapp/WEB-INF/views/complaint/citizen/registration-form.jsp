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
<link rel="stylesheet" href="<cdn:url  value='/resources/global/css/egov/map-autocomplete.css?rnd=${app_release_no}' context='/egi'/>">
<link rel="stylesheet" href="<cdn:url  value='/resources/css/or-divider.css?rnd=${app_release_no}'/>">
<div class="row">
    <div class="col-md-12" style="position:relative;">
        <div class="tour-section" data-toggle="tooltip" title="Click for demo!"><i class="fa fa-question"></i></div>
        <form:form role="form" modelAttribute="complaint" id="complaintform" cssClass="form-horizontal form-groups-bordered" enctype="multipart/form-data">
            <div class="panel panel-primary" data-collapsed="0">
                <div class="panel-heading">
                    <div class="panel-title">
                        <strong><spring:message code="lbl.hdr.complaintInfo"/></strong>
                    </div>
                </div>
                <div class="panel-body custom-form">
                    <c:if test="${not empty complaintTypes}">
                        <div class="form-group">
                            <label class="col-sm-3 control-label"><spring:message code="lbl.top.grievance.types"/></label>
                            <div class="col-sm-6 add-margin" id="topcomplaint">
                                <c:forEach items="${complaintTypes}" var="complaintType">
                                    <a data-id="${complaintType.id}" data-name="${complaintType.name}"
                                       onclick="setComplaintTypeId('${complaintType.id}','${complaintType.category.id}')"
                                       href="javascript:void(0)" class="btn btn-secondary btn-xs tag-element freq-ct"
                                       data-toggle="popover" title='<spring:message code="lbl.top.grievance.toggle"/>'>
                                        <c:out value="${complaintType.name }"/> </a>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="or-spacer">
                            <div class="mask"></div>
                            <span><i>OR</i></span>
                        </div>
                    </c:if>
                    <c:choose>
                        <c:when test="${useAutoCompleteComplaintType}">
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><spring:message code="lbl.complaintType"/><span class="mandatory"></span></label>
                                <div class="col-sm-6">
                                    <input id="complaintTypeName" typeahead-editable="false" class="form-control typeahead is_valid_alphabet" placeholder='<spring:message code="lbl.placholder.grievance"/>'
                                           autocomplete="off" required="required" value="${complaint.complaintType.name}"/>
                                    <form:hidden path="complaintType" id="complaintType" value=""/>
                                    <form:errors path="complaintType" cssClass="add-margin error-msg"/>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">
                                    <spring:message code="lbl.category"/><span class="mandatory"></span>
                                </label>
                                <div class="col-sm-6 add-margin">
                                    <form:select path="complaintType.category" id="complaintTypeCategory"
                                                 cssClass="form-control" cssErrorClass="form-control error" required="required">
                                        <form:option value="">
                                            <spring:message code="lbl.select"/>
                                        </form:option>
                                        <form:options items="${categories}" itemValue="id" itemLabel="name"/>
                                    </form:select>
                                    <form:errors path="complaintType.category" cssClass="error-msg"/>
                                </div>
                            </div>
                            <br>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">
                                    <spring:message code="lbl.complaintType"/><span class="mandatory"></span>
                                </label>
                                <div class="col-sm-6">
                                    <form:select path="complaintType" id="complaintType"
                                                 cssClass="form-control" cssErrorClass="form-control error" required="required">
                                        <form:option value="">
                                            <spring:message code="lbl.select"/>
                                        </form:option>
                                        <form:options items="${complaint.complaintType.category.complaintTypes}" itemLabel="name" itemValue="id"/>
                                    </form:select>
                                    <form:errors path="complaintType" cssClass="add-margin error-msg"/>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="panel panel-primary" data-collapsed="0">
                <div class="panel-heading">
                    <div class="panel-title">
                        More Details
                    </div>
                </div>
                <div class="panel-body custom-form ">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            <spring:message code="lbl.compDetails"/> <span class="mandatory"></span>
                        </label>
                        <div class="col-sm-6">
                            <form:textarea path="details" id="doc" placeholder="Give more details about the complaint to help us solve your issue" minlength="10" maxlength="500" cssClass="form-control autogrow" required="required"/>
                            <div class="text-left">
                                <small><spring:message code="lbl.comp.details"/></small>
                            </div>
                            <form:errors path="details" cssClass="add-margin error-msg"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-3 col-xs-12 text-center" id="upload-section">
                            <a href="#" id="triggerFile" class="btn btn-secondary"><spring:message code="lbl.uploadPhotoVid"/></a>
                            <input type="file" id="file1" name="files" data-id="1" class="filechange inline btn" style="display:none;"
                                   accept="audio/*,video/*,image/*" data-accepts="${allowedFileExt}" data-size="${maxFileSize}"/>
                            <input type="file" id="file2" name="files" data-id="2" class="filechange inline btn" style="display:none;"
                                   accept="audio/*,video/*,image/*" data-accepts="${allowedFileExt}" data-size="${maxFileSize}"/>
                            <input type="file" id="file3" name="files" data-id="3" class="filechange inline btn" style="display:none;"
                                   accept="audio/*,video/*,image/*" data-accepts="${allowedFileExt}" data-size="${maxFileSize}"/>
                        </div>
                        <div class="col-sm-6 col-xs-12">
                            <div id="file1block" class="add-margin col-sm-4 col-xs-4">
                                <img id="preview1" src="#" alt="" class="display-hide "/>
                                <div class="remove-img preview-cross1 display-hide" data-file-id><i class="fa fa-times-circle"></i></div>
                                <div class="add-padding" id="filename1"></div>
                            </div>
                            <div id="file2block" class="add-margin display-hide col-sm-4 col-xs-4">
                                <img id="preview2" src="#" alt="" class="display-hide"/>
                                <div class="remove-img preview-cross2 display-hide" data-file-id><i class="fa fa-times-circle"></i></div>
                                <div class="add-padding" id="filename2"></div>
                            </div>
                            <div id="file3block" class="add-margin display-hide col-sm-4 col-xs-4">
                                <img id="preview3" src="#" alt="" class="display-hide"/>
                                <div class="remove-img preview-cross3 display-hide" data-file-id><i class="fa fa-times-circle"></i></div>
                                <div class="add-padding" id="filename3"></div>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label"><spring:message code="lbl.complaintLoc"/><span class="mandatory"></span></label>
                        <div class="col-sm-6">
                            <div class="input-group" id="location-tour">
                                <input id="location" type="text" value="${crossHierarchyLocation}" class="form-control low-width" placeholder='<spring:message code="lbl.placeholder.grievance.loc"/>' autocomplete="off" required="required"/>
                                <span class="input-group-addon map-class btn-secondary" data-toggle="tooltip" title="Locate on map" onclick="jQuery('#modal-6').modal('show', {backdrop: 'static'});"><i class="fa fa-map-marker specific"></i></span>
                                <form:hidden path="location" id="locationid"/>
                                <form:hidden path="crossHierarchyId" id="crosshierarchyId"/>
                                <form:hidden path="receivingMode" id="receivingMode"/>
                                <form:hidden path="lat" id="lat"/>
                                <form:hidden path="lng" id="lng"/>
                            </div>
                            <form:errors path="location" cssClass="add-margin error-msg"/>
                            <form:hidden path="latlngAddress" id="latlngaddress"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label"><spring:message code="lbl.landmark"/></label>
                        <div class="col-sm-6">
                            <form:textarea path="landmarkDetails" class="form-control" id="landmarkDetails" placeholder="" maxlength="200"/>
                            <form:errors path="landmarkDetails" cssClass="add-margin error-msg"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="text-center">
                    <button type="submit" class="btn btn-primary" id="create-griev"><spring:message code="title.complaint.reg"/></button>
                    <a href="javascript:void(0)" class="btn btn-default" onclick="self.close()"><spring:message code="lbl.close"/></a>
                </div>
            </div>
        </form:form>
    </div>
</div>


<div class="modal fade" id="modal-6">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-primary" data-collapsed="0"><!-- to apply shadow add class "panel-shadow" -->
                            <!-- panel head -->
                            <div class="panel-heading">
                                <div class="panel-title"><spring:message code="lbl.complaintLoc"/></div>
                            </div>

                            <!-- panel body -->
                            <div class="panel-body no-padding">
                                <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=${sessionScope.googleApiKey}&libraries=places"></script>
                                <script type="text/javascript" src="<cdn:url  value='/resources/global/js/geolocation/geolocationmarker-compiled.js' context='/egi'/>"></script>
                                <div id="normal" class="img-prop"></div>
                                <input id="pac-input" class="controls" type="text" placeholder="Enter a location">
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-info btn-save-location" data-dismiss="modal"><spring:message code="lbl.ok"/></button>
                <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.close"/></button>
            </div>
        </div>
    </div>
</div>
<script src="<cdn:url  value='/resources/global/js/jquery/plugins/exif.js' context='/egi'/>"></script>
<script src="<cdn:url  value='/resources/js/app/fileuploadndmaps.js?rnd=${app_release_no}'/>"></script>
<script src="<cdn:url  value='/resources/js/app/complaint.js?rnd=${app_release_no}'/>"></script>
<script src="<cdn:url  value='/resources/global/js/egov/inbox.js?rnd=${app_release_no}' context='/egi'/>"></script>
