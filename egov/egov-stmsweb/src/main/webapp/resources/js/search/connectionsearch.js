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



var tableContainer;
var isFlag=false;
var applicationType;
var isAnonymousOrWardSecretaryUser;
var wsSource;
var wsTransactionId;
var wsPortalRequest;
jQuery(document).ready(function($) {
	isAnonymousOrWardSecretaryUser = $('#isAnonymousOrWardSecretaryUser').val();
	wsSource = $("#wsSource").val();
	wsTransactionId = $("#wsTransactionId").val();
	wsPortalRequest = $("#wsPortalRequest").val();
	if (isAnonymousOrWardSecretaryUser == "yes") {
		$('#consumerNumber').hide();
		$('#applicationNoLabel').hide();
		$('#nameAndMobileNumberDiv').hide();
		$('#boundaryAndDoorNumberDiv').hide();
		$('#dateRangesDiv').hide();
	}
	tableContainer = $("#aplicationSearchResults");
	 document.onkeydown=function(evt){
		 var keyCode = evt ? (evt.which ? evt.which : evt.keyCode) : event.keyCode;
	if(keyCode == 13){
		submitButton();	
	}
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
});
	

$(".btnSearch").click(function(event){
	$('#searchSewerageapplication').show();
	var consumerNumber=$('#consumerNumber').val();
	var shscNumber=$('#shscNumber').val();
	var applicantName=$('#applicantName').val();
	var mobileNumber=$('#mobileNumber').val();
	var wardName=$('#app-mobno').val();
	var doorNo=$('#app-appcodo').val();
	var fromDate=$('#fromDate').val();
	var toDate=$('#toDate').val();
	
	if((isAnonymousOrWardSecretaryUser == 'yes' && shscNumber == '') 
		|| (consumerNumber == '' && shscNumber == '' && applicantName == '' && mobileNumber == '' && wardName == '' && doorNo == '' && fromDate == '' && toDate == '')) {
				bootbox.alert("Please Enter Atleast One Input Value For Searching");
				return false;
			} else {
				submitButton();
				return true;
			}
	});

$(document).on('change', 'select.actiondropdown', function() {
	var ptassessmentno= prevdatatable.fnGetData($(this).parent().parent(), 2);
	var shscnumber= prevdatatable.fnGetData($(this).parent().parent(), 3); 
	
	if($(this).find(":selected").text()=="Change number of seats"){
		var changeincloseturl=$(this).val();
		var consumerno= $(this).data('consumer-no');
		applicationType = "changenumberofseats";
		jQuery.ajax({
			url: "/stms/ajaxconnection/check-application-inworkflow/"+shscnumber,     
			type: "GET",
			data: {
				shscnumber : shscnumber,
			},
			datatype: "text",
			success: function (response) {
				if(response!="") {
				bootbox.alert(response);
				return false;
				} else {
					loadPropertyDetails(changeincloseturl,consumerno,shscnumber,ptassessmentno);
				}
			}, 
			error: function (response) {
				console.log("failed");
			}
		});
		}  
	else if($(this).find(":selected").text()=="Close Sewerage Connection"){
		var closeconnectionurl=$(this).val();
		var consumerno = $(this).data('consumer-no');
		jQuery.ajax({
			url: "/stms/ajaxconnection/check-application-inworkflow/"+shscnumber,
			type: "GET",
			data: {
				shscnumber : shscnumber,
			},
			datatype: "text",
			success: function (response){
				if(response!=""){
				bootbox.alert(response);
				return false;
				}
				else{
					loadPropertyDetails(closeconnectionurl, consumerno, shscnumber,ptassessmentno);
				}
			},
			error: function (response) {
				console.log("failed");
			}
		});
	}
	else{  
		if($(this).find(":selected").index()>0){
			callurl($(this).val(), $(this).data('consumer-no'),ptassessmentno,shscnumber);
			
		}
	}
});

function callurl(url, consumernumber, ptassessmentno, shscnumber){
	
	url=url.replace('{consumerno}', consumernumber);
	url=url.replace('{assessmentno}', ptassessmentno);
	url=url.replace('{shscNumber}', shscnumber);
    openPopup(url)
}

function openPopup(url){
	window.open(url,'window','scrollbars=1,resizable=yes,height=600,width=800,status=yes');
}


var prevdatatable;
var oTable;
function submitButton() {
	$('.report-section').removeClass('display-hide');
	oTable = $('#aplicationSearchResults');
	if(prevdatatable)
	{
		prevdatatable.fnClearTable();
		$('#aplicationSearchResults thead tr').remove();
	}
	prevdatatable = oTable.dataTable({
		dom: "<'row'<'col-xs-4 pull-right'f>r>t<'row add-margin'<'col-md-3 col-xs-6'i><'col-md-2 col-xs-6'l><'col-md-2 col-xs-6 text-right'B><'col-md-5 col-xs-6 text-right'p>>",
		processing: true,
        serverSide: true,
        sort: true,
        filter: true,
        "searching": false,
        "order": [[0, 'asc']],
        "autoWidth": false,
        "bDestroy": true,
        ajax : {
		url : "/stms/existing/sewerage/",
		type : 'POST',
		beforeSend : function() {
			$('.loader-class').modal('show', {
				backdrop : 'static'
			});
		},
		data : function(args) {
			var formData = getFormData(jQuery('form'));
			formData['args']=JSON.stringify(args);
			return formData;
		},
		complete : function() {
			$('.loader-class').modal('hide');
		}
	},	
		columns : [
					  { title : "Application Number", data:"applicationNumber"},
					  { title : "Application Date", data:"applicationDate"},
					  { title : "pt assesmentno", targets: [2], data: "assessmentNumber", visible: false},
					  {title : 'S.H.S.C Number',class : 'row-detail', data : 'shscNumber',
			        	   "render": function ( data, type, row, meta ) {
					            return '<a onclick="openPopup(\'/stms/application/view/'+row.applicationNumber+'/'+row.assessmentNumber+'\')" href="javascript:void(0);">'+data+'</a>';} },
					  { title : "Applicant Name", data: "applicantName"},
					  { title : "Application Type", data: "applicationType"},
					  { title : "Property type", data: "propertyType"},
					  { title : "Revenue ward", data: "revenueWard"},
					  { title : "Address", data: "address"},
					  { title : "Application Status", data: "applicationStatus"},
					  { title : "Actions","sortable":false,
						  render: function(data, type, row){
							  var option ="<option>Select from below</option>";
							  if(row.actions!=null){
								  $.each(row.actions, function(key,value){
									  option+= "<option value="+key+">"+value+"</option>";
								  });
							  }
							  return ('<select class="actiondropdown" data-consumer-no="'+row.applicationNumber+'" >'+option+'</select>');
						  }
					  }
					  ],
					  "aaSorting": [[5, 'asc']] 
				});
	
}

$("#viewDCB").click(function(){
	var appNumber = document.getElementById("applNumber").value;
	var assessmentNum=document.getElementById("assessmentNo").value;
	window.open("/stms/reports/sewerageRateReportView/"+appNumber+"/"+assessmentNum, '_blank', "width=800, height=600, scrollbars=yes");
});
  

function loadPropertyDetails(url,consumerno,shscnumber, propertyID) {
	var errorMessage=""; 
	var subErrorMessage="";
	if(propertyID != '') {
		$.ajax({
			url: "/ptis/rest/property/"+propertyID,      
			type: "GET",
			dataType: "json",
			success: function (response) { 
				var waterTaxDue = getWaterTaxDue(propertyID);
					if(applicationType==="changenumberofseats"){
						subErrorMessage = " change number of seats";
					} else {
						subErrorMessage = " close sewerage connection";
					}
					if(response.propertyDetails.taxDue > 0) {
						errorMessage = "For entered Property tax Assessment number "+propertyID+" demand is due Rs."+ response.propertyDetails.taxDue+"/-. Please clear demand and apply for"+subErrorMessage;
					} else if(waterTaxDue['WATERTAXDUE'] > 0) {
						errorMessage = "For entered Property tax Assessment number "+propertyID+" linked water tap connection demand with Consumer code:"+waterTaxDue['CONSUMERCODE'][0]+" is due Rs."+ waterTaxDue['WATERTAXDUE']+"/- . Please clear demand and apply for"+subErrorMessage;
					}
					if(response.propertyDetails.taxDue > 0 || waterTaxDue['WATERTAXDUE'] > 0) {
						bootbox.alert(errorMessage);
					} if (wsSource == 'WARDSECRETARY') {
						if (applicationType === "changenumberofseats") {
							url = "/stms/transactions/modifyConnection/form/"+shscnumber;
						} else {
							url = "/stms/transactions/closeConnection/form/"+shscnumber;
						}
						submitFormForWardSecretary(url);
					} else {
						callurl(url, consumerno, propertyID, shscnumber);
					}
			}, 
			error: function (response) {
				console.log("failed");
			}
		});
	}		
}

function getWaterTaxDue(propertyID) {
	var result;
	if(propertyID != "") {
		$.ajax({
			url: "/stms/ajaxconnection/check-watertax-due",
				type: "GET",
				'async':false,
				cache: true,
				data: {
					assessmentNo : propertyID
				},
				dataType: "json",
		}).done(function(value) {
				result = value; 
		});
		return result;
	}
}

function getFormData($form) {
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};
    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });
    return indexed_array;
}

function submitFormForWardSecretary(url) {
	jQuery('<form>.').attr({
		method : 'POST',
		action : url,
		target : '_self'
	}).append(jQuery('<input>').attr({
		type : 'hidden',
		id : 'wsTransactionId',
		name : 'wsTransactionId',
		value : wsTransactionId
	})).append(jQuery('<input>').attr({
		type : 'hidden',
		id : 'wsSource',
		name : 'wsSource',
		value : wsSource
	})).append(jQuery('<input>').attr({
		type : 'hidden',
		id : 'wsPortalRequest',
		name : 'wsPortalRequest',
		value : wsPortalRequest
	})).append(jQuery('<input>').attr({
        type: 'hidden',
        name: tokenName,
        value: tokenVal
    })).appendTo(document.body).submit();
}