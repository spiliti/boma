/*
 *    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) 2017  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *            Further, all user interfaces, including but not limited to citizen facing interfaces, 
 *            Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any 
 *            derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *            For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *            For any further queries on attribution, including queries on brand guidelines, 
 *            please contact contact@egovernments.org
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 *
 */

$(document).ready( function () {
	
	
	if ($('#employeeAssgnNotValid').val()) {
		$('#approvalDepartment').val('');
		bootbox.alert($('#employeeAssgnNotValid').val());
	}
	
	//Added to avoid submitting parent form on Preview button click
	//Used to preview certificate in case of Digital signture
	$("#Preview").toggleClass('btn-primary btn-default');
	$("#Preview").prop('type','button');
	
	 if($('#reIssueStatus').val()=='APPROVED' && $("#feeCollected").val()=='false'){
		 $("[id='Print Certificate']").hide();
	 }  
	 
	 if($('#txt-feepaid').val())
	 {
		 $('#txt-feepaid').val(Math.floor($('#txt-feepaid').val()));
	 }
	 
	 if ($("#feeCollected").val() == "false" && (($('#reIssueStatus').val() != '' && $('#reIssueStatus').val() == 'CREATED' && $(
		'#nextActn').val() != 'Junior/Senior Assistance approval pending' && ($(
		'#nextActn').val() != 'Revenue Clerk Approval Pending' && $(
		'#nextActn').val() != 'Clerk Approval Pending') && $(
		'#nextActn').val() != 'Chief Medical Officer of Health Approval Pending' && $(
		'#nextActn').val() != 'Municipal Health Officer Approval Pending')&& ($(
		'#reIssueStatus').val() != 'REJECTED' || ($('#currentState').val() =='Clerk Approved')
))){
		$(".show-row").hide();
		$("#Approve").hide();
		$('#approverDetailHeading').hide();
		$('#approvalDepartment').removeAttr('required');
		$('#approvalDesignation').removeAttr('required');
		$('#approvalPosition').removeAttr('required');
		
		} 
	 else if ($("#feeCollected").val() == 'true' && $(
		'#nextActn').val() != 'Clerk Approval Pending' && 
		($('#nextActn').val() != 'Chief Medical Officer of Health Approval Pending' &&
		$('#nextActn').val() != 'Municipal Health Officer Approval Pending')){
		$(".show-row").hide();
		$("#Approve").show();
		$('#approverDetailHeading').hide();
		$('#approvalDepartment').removeAttr('required');
		$('#approvalDesignation').removeAttr('required');
		$('#approvalPosition').removeAttr('required');
	 }
	 else if($('#nextActn').val() == 'Chief Medical Officer of Health Approval Pending' || $(
		'#nextActn').val() == 'Municipal Health Officer Approval Pending'){
		if ($("#feeCollected").val() == "false"){
		 $(".show-row").show();
			$("#Approve").hide();
			$('#approverDetailHeading').show();
			$('#approvalDepartment').attr('required', 'required');
			$('#approvalDesignation').attr('required', 'required');
			$('#approvalPosition').attr('required', 'required'); 
		}
		else
			{
			$(".show-row").show();
			$("#Approve").show();
			$('#approverDetailHeading').show();
			$('#approvalDepartment').attr('required', 'required');
			$('#approvalDesignation').attr('required', 'required');
			$('#approvalPosition').attr('required', 'required');
			}
	 }
	 else {
		$(".show-row").show();
		$('#approverDetailHeading').show();
		$('#approvalDepartment').attr('required', 'required');
		$('#approvalDesignation').attr('required', 'required');
		$('#approvalPosition').attr('required', 'required');
	}

	$('.slide-history-menu').click(function(){
		$(this).parent().find('.history-slide').slideToggle();
		if($(this).parent().find('#toggle-his-icon').hasClass('fa fa-angle-down'))
		{
			$(this).parent().find('#toggle-his-icon').removeClass('fa fa-angle-down').addClass('fa fa-angle-up');
			//$('#see-more-link').hide();
			}else{
			$(this).parent().find('#toggle-his-icon').removeClass('fa fa-angle-up').addClass('fa fa-angle-down');
			//$('#see-more-link').show();
		}
	});
	
	$('#select-registrationunit').change( function () { 
		var url;
			url='/mrs/registration/getmrregistrationunitzone'
		showRegistrationUnit(url); 
	})
	
	$('#Preview').click(function() {
		var url = '/mrs/reissue/viewCertificate/'+ $('#reIssue').val();
		window.open(url);
	});
	
	function showRegistrationUnit(url)
	{
	 if ($('#select-registrationunit').val() === '') {
	 	$('#txt-zone').val('');
				return;
			} else {
			
				$.ajax({
					type: "GET",
					url: url,
					cache: true,
					dataType: "json",
					data:{
						'registrationUnitId' : $('#select-registrationunit').val()
						}
				}).done(function(value) {
										/*if (value == 0)
											$('#ttxt-zone').val('');
										else*/
					$('#txt-zoneid').val(value.id);
					$('#txt-zone').val(value.name);
				
				});
			}
		
	}
	
	if($('#message').val()){
		bootbox.alert($('#message').val());
		return false; 
	}
	
})

$('#txt-phoneNo').blur( function () {
	 var mobileno = jQuery("#txt-phoneNo").val();
		if (mobileno.length < 10) {
			bootbox.alert("Please enter 9 digit mobile number");
			$(this).val('');
		}
	})

jQuery('form').validate({
	    ignore: ".ignore",
	    invalidHandler: function(e, validator){
	        if(validator.errorList.length)
	        	$('#settingstab a[href="#' + jQuery(validator.errorList[0].element).closest(".tab-pane").attr('id') + '"]').tab('show');
	    }
	});
	

$('#Forward').click(function() {
	if ($('form').valid()) {
		return true;
	} else {
		return false;
	}
});

$(".btn-primary").click(function(e) {
	var action = $('#workFlowAction').val();
	if (action == 'Reject') {
		$('#Reject').attr('formnovalidate', 'true');
		bootbox.confirm({
			message : "Do You Really Want to Reject The Registration!",
			buttons : {
				confirm : {
					label : 'Yes',
					className : 'btn-primary'
				},
				cancel : {
					label : 'No',
					className : 'btn-danger'
				}
			},
			callback : function(result) {
				if (result) {
					var approvalComent = $('#approvalComent').val();
					if (approvalComent == "") {
						bootbox.alert("Please enter rejection comments!");
						$('#approvalComent').focus();
						return true;
					} else {
						validateWorkFlowApprover(action);
						$('.loader-class').modal('show', {
							backdrop : 'static'
						});
						$(document.forms[0]).append( $('<input>', {
                            type: 'hidden',
                            name: tokenName,
                            value: tokenVal
                        }))
						document.forms[0].submit();
					}
				} else {
					e.stopPropagation();
					e.preventDefault();
				}
			}
		});
		return false;
	} else if (action == 'Cancel ReIssue') {
		$('#Cancel Registration').attr('formnovalidate', 'true');
		bootbox.confirm({
			message : "Do You Really Want to Cancel The ReIssue!",
			buttons : {
				confirm : {
					label : 'Yes',
					className : 'btn-primary'
				},
				cancel : {
					label : 'No',
					className : 'btn-danger'
				}
			},
			callback : function(result) {
				if (result) {
					var approvalComent = $('#approvalComent').val();
					if (approvalComent == "") {
						bootbox.alert("Please enter cancellation comments!");
						$('#approvalComent').focus();
						return true;
					} else {
						validateWorkFlowApprover(action);
						$('.loader-class').modal('show', {
							backdrop : 'static'
						});
						$(document.forms[0]).append( $('<input>', {
                            type: 'hidden',
                            name: tokenName,
                            value: tokenVal
                        }))
						document.forms[0].submit();
					}
				} else {
					e.stopPropagation();
					e.preventDefault();
				}
			}
		});
		return false;
	} else {
		validateWorkFlowApprover(action);
	}
});