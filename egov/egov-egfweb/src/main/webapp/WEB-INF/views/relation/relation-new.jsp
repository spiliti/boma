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
<%@ include file="/includes/taglibs.jsp"%>
<form:form role="form" action="create" modelAttribute="relation"
	id="relationform" cssClass="form-horizontal form-groups-bordered"
	enctype="multipart/form-data">
	<%@ include file="relation-form.jsp"%>
	</div>
	</div>
	</div>
	</div>
	<div class="form-group">
		<div class="text-center">
			<button type='button' class='btn btn-primary' id="buttonSubmit" onclick = "validateFormData();">
				<spring:message code='lbl.create' />
			</button>
			<a href='javascript:void(0)' class='btn btn-default'
				onclick='self.close()'><spring:message code='lbl.close' /></a>
		</div>
	</div>
</form:form>
<script>
	
	var validMobile = true;
	var validEmail = true;
	function submitForm(){
		if ($('form').valid()) {
		} else {
			e.preventDefault();
		}
	}
	function validateFormData(){
		var supplierName = document.getElementById('supplierName').value;
		var supplierCode = document.getElementById('supplierCode').value;
		if(supplierName == "" || supplierCode == ""){
			$('#showErrorMessage').show();
			return false;
		}else {
			$('#showErrorMessage').hide();
		}
		if(validEmail == true && validMobile == true){
			$("#buttonSubmit").attr('type','submit');
			submitForm();
		}else{
			$("#buttonSubmit").attr('type','button');
		}
	}
	function checkLength(){
		var mobileNo = $('#mobile').val();
		if(mobileNo != ""){
			if(mobileNo.length != 10){
				$("#errorForMobileNo").show();
				validMobile = false;
				return false;
			}else{
				validMobile = true;
				$("#errorForMobileNo").hide();
			}
		}else{
			validMobile = true;
			$("#errorForMobileNo").hide();
		}
	}
	function isvalidEmail(){
		var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
		var emailId = $("#emailId").val();
		if(emailId != ""){
			if (filter.test(emailId)) {
				validEmail = true;
				$("#errorForEmailId").hide();
		    }else {
		    	validEmail = false;
		    	$("#errorForEmailId").show();
		    }
		}else{
			validEmail = true;
			$("#errorForEmailId").hide();
		}
			
	}
</script>