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
	
	var updateurl;
	var wsSource = $('#wsSource').val();
	var wsTransactionId = $('#wsTransactionId').val();
	var wsPortalRequest = $('#wsPortalRequest').val();
	 updateurl='/mrs/reissue/create/';
	

	$('#btnregistrationsearch').click( function () {
		var url;
				url = '/mrs/registration/searchregisteredrecord'
		callAjaxSearch(url);
	});
	
	function callAjaxSearch(url) {
		$('.report-section').removeClass('display-hide');
		$.ajax({
			url : url,
			type: "POST",
			dataType: "json",
			beforeSend : function() {
				$('.loader-class').modal('show', {
					backdrop : 'static'
				});
			},
			data : getFormData(jQuery('form')),
			complete : function() {
				$('.loader-class').modal('hide');
			},
			success : function (response) {
				if(response.data){
					 $('#searchResultsDiv').show();
					 $('#searchResultsLabelDiv').show();
					 reportdatatable =	$("#registration_table").dataTable({
						"aaData":response.data,
						"bDestroy" : true,
						"sDom" : "<'row'<'col-xs-12 hidden col-right'f>r>t<'row'<'col-xs-3'i><'col-xs-3 col-right'l><'col-xs-3 col-right'<'export-data'T>><'col-xs-3 text-right'p>>",
						"aLengthMenu" : [
								[ 10, 25, 50, -1 ],
								[ 10, 25, 50, "All" ] ],
						"oTableTools" : {
							"sSwfPath" : "../../../../../../egi/resources/global/swf/copy_csv_xls_pdf.swf",
							"aButtons" : [ "xls", "pdf",
									"print" ]
						},
						aaSorting : [],
						columns : [{
							"data" : "registrationNo",
							render: function(data, type, row, meta){
								 if(row.registrationNo == 'undefined' || row.registrationNo == '')
								 {
								 return "N/A";
								 } else{
									 return row.registrationNo;
								 }
						},
							"sClass" : "text-left"
						}, {
							"data" : "applicationNo",
							render : function(data,
									type, row, meta) {
								if (row.applicationNo == 'undefined'
										|| row.applicationNo == '') {
									return "N/A";
								} else {
									return row.applicationNo;
								}
							},
							"sClass" : "text-left"
						}, {
							"data" : "registrationDate",
							"sClass" : "text-left"
						}, {
							"data" : "dateOfMarriage",
							"sClass" : "text-left"
						}, {
							"data" : "husbandName",
							"sClass" : "text-left"
						}, {
							"data" : "wifeName",
							"sClass" : "text-left"
						}, {
							"data" : "feePaid",
							"sClass" : "text-left"
						},{
							"data" : "status",
							"sClass" : "text-left"
						},{
							"data" : "marriageRegistrationUnit",
							"sClass" : "text-left"
						},
						{
							"data" : null,
						    sortable: false,
						    "render": function ( data, type, row, meta ) {
						    	var reissueUrl;
						    	if(wsSource == 'WARDSECRETARY')
						    		reissueUrl = updateurl+'form/'+row.id;
						    	else
						    		reissueUrl = updateurl+row.id;
						    		
							    return '<button type="button" class="btn btn-xs btn-secondary reissue" value='+reissueUrl +'><span class="glyphicon glyphicon-edit"></span>&nbsp;Re Issue Certificate</button>';
							        
						    }
						
						}]
					 });
					 $('#errorsDiv').hide();
				} else if (response.error){
					$('#errorsDiv').show();
					$("#errorTable").dataTable({
		    			"aaData":response.error,
		    			"bDestroy": true,
		    			"autoWidth": true, searching: false, paging: false, info: false,
		    			"columns" : [
		    		      { "data" : "errorMessage", "title":"Errors"},
		    			  ],
		                  "columnDefs": [
		                                 {"className": "dt-center", "targets": "_all"}
		                               ],
		                               "createdRow": function( row, data, dataIndex){
		                                    $(row).css('color', '#FF0000');
		                                 }
		    			});
		            $('#searchResultsDiv').hide();
		            $('#searchResultsLabelDiv').hide();
				}
			},
			error : function (response) {
				console.log(" ------------ failed ------------ ");
			}
		});
	}

	$(document).on('click','.reissue',function(){
	    var url = $(this).val();
	    if(wsSource == 'WARDSECRETARY')
	    	reissueForm(url);
	    else if(url){
	    	openPopup(url);
	    }
	});

	function openPopup(url)
	{
		window.open(url,'window','scrollbars=yes,resizable=yes,height=700,width=800,status=yes');
	}

	function reissueForm(reissueUrl) {
		jQuery('<form>.').attr({
			method : 'POST',
			action : reissueUrl,
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
	
});

function getFormData($form){
	var unindexed_array = $form.serializeArray();
	var indexed_array = {};

	$.map(unindexed_array, function(n, i){
	indexed_array[n['name']] = n['value'];
	});

	return indexed_array;
	}



