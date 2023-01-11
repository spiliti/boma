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

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/includes/taglibs.jsp" %>
<html>
	<head>
	<script type="text/javascript">
	jQuery(document).ready(function() { 
		jQuery("#updatePaytax").click(function(e) {
        	 if(jQuery("#mobileNumber").val() == '') {
            	 bootbox.alert("Please enter mobile number");
            	 return false;
            	 }
        	 var mobileNo = jQuery("#mobileNumber").val();
        	 if(mobileNo.length < 10) {
				  bootbox.alert("Please enter 9 digit mobile number");
	              return false;
				  }
             }); 
		jQuery("#mobileNumber").blur(function(e){
			  var mobileNo = jQuery("#mobileNumber").val();
			  if(mobileNo.length < 10) {
				  bootbox.alert("Please enter 9 digit mobile number");
	              return false;
				  }
			});
	  });
			 function onSubmit(obj,formId) {
				var formObj = document.getElementById(formId);
				formObj.action = obj;
				formObj.submit;
			    return true;
			} 

				
	</script>
	<title><s:text name="update.mobileNo.title"></s:text></title>
	</head>
	<body>
		<div class="formmainbox">

	<table border="0" cellspacing="0" cellpadding="0" width="100%"> 
						<s:form action="collection" name="userDetailsForm" theme="simple" id="userDetailsForm">
						<s:hidden id="assessmentNumber" name="assessmentNumber" value="%{assessmentNumber}"></s:hidden>
							<tr>
								<td width="100%" colspan="4" class="headingbg">												
									<div class="headingbg">					
										<s:text name="update.mobileNo.title" />									
									</div>									
								</td>
							</tr>
							<tr>
								<td class="bluebox">&nbsp;</td>
								<td class="bluebox"> 
									<s:text name="OwnerName" />:
								</td>
								
								<td class="bluebox">
								<span style="font-weight:bold"><s:property value="%{propertyOwner.name}"/></span>
								</td>
								<td class="bluebox">&nbsp;</td>
							</tr>	
							<tr>
								<td class="bluebox">&nbsp;</td>
								<td class="bluebox">
									<s:text name="doorNo" />:
								</td>
								
								<td class="bluebox">
								<span style="font-weight:bold"><s:property value="%{doorNo}"/></span>
								</td>
								<td class="bluebox">&nbsp;</td>
							</tr>	
							<tr>
								<td class="bluebox">&nbsp;</td>
								<td class="bluebox">
									<s:text name="existing.mobileno" />:
								</td>
								
								<td class="bluebox">
								<span style="font-weight:bold"><s:property value="%{propertyOwner.mobileNumber}"/></span>
								</td>
								<td class="bluebox">&nbsp;</td>
							</tr>	
							<tr>
								<td class="bluebox">&nbsp;</td>
								<td class="bluebox">
									<s:text name="new.mobileno" /><span class="mandatory1">*</span>:
								</td>
								
								<td class="bluebox">
									<s:textfield name="mobileNumber" id="mobileNumber" value="%{mobileNumber}" maxlength="10" onblur="validNumber(this);validMobileNumber(this);checkZero(this,'Mobile Number');"/>
								</td>
								<td class="bluebox">&nbsp;</td>
							</tr>		
							<tr>
						<td class="bluebox" colspan="4">
							&nbsp; &nbsp; &nbsp;
						</td>
					</tr>				
					<tr>
						<td class="bluebox">&nbsp;</td>
						<td class="bluebox" colspan="2">
							<div class="bluebox" style="text-align:center">
								<s:submit name="updatePaytax" value="Update & Pay tax" id="updatePaytax" cssClass="buttonsubmit" onclick="return onSubmit('collection-updateMobileNo.action', 'userDetailsForm');"></s:submit>
								<input type="button" value="Close" class="button" onclick="window.close();" />
							</div>
						</td>
						<td class="bluebox">&nbsp;</td>
					</tr>	
					
	</s:form></table>
	</div>	
	</body>
	</html>