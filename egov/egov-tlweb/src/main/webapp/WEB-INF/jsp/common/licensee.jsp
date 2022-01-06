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

<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<div class="panel-heading custom_form_panel_heading">
    <div class="panel-title"><s:text name='license.title.applicantdetails'/></div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label text-right"><s:text name='licensee.applicantname'/><span class="mandatory"></span></label>
    <div class="col-sm-3 add-margin">
        <s:textfield name="licensee.applicantName" id="applicantName" maxlength="250" cssClass="form-control"/>
        <div id="applicantname_error" class="error-msg" style="display:none;" align="left"></div>
        <s:fielderror fieldName="model.licensee.applicantName" cssClass="error-msg"/>
    </div>
    <label class="col-sm-2 control-label text-right"><s:text name='licensee.fatherorspousename'/><span class="mandatory"></span></label>
    <div class="col-sm-3 add-margin">
        <s:textfield name="licensee.fatherOrSpouseName" maxlength="250" id="fatherOrSpouseName" cssClass="form-control"/>
        <div id="fatherorSpouse_error" class="error-msg" style="display:none;" align="left"></div>
        <s:fielderror fieldName="model.licensee.fatherOrSpouseName" cssClass="error-msg"/>
    </div>
</div>
<div class="row">
    <label class="col-sm-3 control-label text-right"><s:text name='licensee.mobileNo'/><span class="mandatory"></span></label>
    <div class="col-sm-3 add-margin" style="margin-bottom:15px;">
        <div class="input-group">
            <span class="input-group-addon" id="basic-addon1">+260</span>
            <s:textfield name="licensee.mobilePhoneNumber" id="mobilePhoneNumber" maxlength="10" required="true"
                         cssClass="form-control patternvalidation" data-pattern="number"/>
        </div>
        <div class="error-msg hide" id="mobileError">Should be 10 digits</div>
        <s:fielderror fieldName="model.licensee.mobilePhoneNumber" cssClass="error-msg"/>
    </div>
    <label class="col-sm-2 control-label text-right"><s:text name='licensee.emailId'/><span class="mandatory"></span></label>
    <div class="col-sm-3 add-margin" style="margin-bottom:15px;">
        <s:textfield name="licensee.emailId" id="emailId" maxlength="64" type="email" required="true" cssClass="form-control"/>
        <div id="email_error" class="error-msg" style="display:none;" align="left"></div>
    </div>

</div>
<div class="form-group">
    <label class="col-sm-3 control-label text-right"><s:text name='licensee.address'/><span class="mandatory"></span></label>
    <div class="col-sm-5 add-margin">
        <s:textarea name="licensee.address" id="licenseeAddress" required="true" maxlength="250" cssClass="form-control" rows="3"/>
        <div id="licenseeAddress_error" class="error-msg" style="display:none;" align="left"></div>
        <s:fielderror fieldName="model.licensee.address" cssClass="error-msg"/>
    </div>
</div>

<script>
    try {
        $(":input").inputmask();
    } catch (e) {
    }
</script>