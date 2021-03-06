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

$(document).ready(function(){
	$('#typeofwork').blur(function(){
		 if ($('#typeofwork').val() === '') {
			   $('#subTypeOfWork').empty();
			   $('#subTypeOfWork').append($('<option>').text('Select from below').attr('value', ''));
				return;
				} else {
				$.ajax({
					type: "GET",
					url: "/egworks/lineestimate/getsubtypeofwork",
					cache: true,
					dataType: "json",
					data:{'id' : $('#typeofwork').val()}
				}).done(function(value) {
					console.log(value);
					$('#subTypeOfWork').empty();
					$('#subTypeOfWork').append($("<option value=''>Select from below</option>"));
					$.each(value, function(index, val) {
					     $('#subTypeOfWork').append($('<option>').text(val.description).attr('value', val.id));
					});
				});
			}
		});
	
	var workIdNumber = new Bloodhound({
	    datumTokenizer: function (datum) {
	        return Bloodhound.tokenizers.whitespace(datum.value);
	    },
	    queryTokenizer: Bloodhound.tokenizers.whitespace,
	    remote: {
	        url: '/egworks/letterofacceptance/ajaxworkidentificationnumbers-loatocancel?code=%QUERY',
	        filter: function (data) {
	            return $.map(data, function (ct) {
	                return {
	                    name: ct
	                };
	            });
	        }
	    }
	});

	workIdNumber.initialize();
	var workIdNumber_typeahead = $('#workIdentificationNumber').typeahead({
		hint : true,
		highlight : true,
		minLength : 3
	}, {
		displayKey : 'name',
		source : workIdNumber.ttAdapter()
	});
});

jQuery('#btnsearch').click(function(e) {
	var fromDate = '';
	var toDate = '';
	if($('#milestoneFromDate').val() != "") {
		fromDate = $('#milestoneFromDate').data('datepicker').date;
	}
	if($('#milestoneToDate').val() != "") {
		toDate = $('#milestoneToDate').data('datepicker').date;
	}
	var flag = true; 
	if(toDate != '' && fromDate != '') {
		if(fromDate > toDate) {
			flag = false;
			bootbox.alert('Milestone To Date should be greater than Milestone From Date');
		}
	}
	if(flag)
		callAjaxSearch();
});

function getFormData($form) {
	var unindexed_array = $form.serializeArray();
	var indexed_array = {};

	$.map(unindexed_array, function(n, i) {
		indexed_array[n['name']] = n['value'];
	});

	return indexed_array;
}

function callAjaxSearch() {
	drillDowntableContainer = jQuery("#resultTable");
	jQuery('.report-section').removeClass('display-hide');
	reportdatatable = drillDowntableContainer
			.dataTable({
				ajax : {
					url : "/egworks/milestone/ajax-searchmilestoneforview",
					type : "POST",
					"data" : getFormData(jQuery('form')),
					"error" : function() {
						jQuery("#resultTable tbody tr td").html(
								"No data available.");
					}
				},
				"fnRowCallback" : function(row, data, index) {
					$('td:eq(0)',row).html(index+1);
					$('td:eq(1)', row).html(
							'<a href="javascript:void(0);" onclick="openLineEstimate(\''
									+ data.lineEstimateId + '\')">'
									+ data.workIdentificationNumber + '</a>');
					$('td:eq(4)', row).html(
							'<a href="javascript:void(0);" onclick="openLOA(\''
									+ data.workOrderId + '\')">'
									+ data.workOrderNumber + '</a>');
					$('td:eq(5)',row).html(parseFloat(Math.round(data.agreementAmount * 100) / 100).toFixed(2));
					$('td:eq(9)', row).html(
							'<a href="javascript:void(0);" onclick="openMilestone(\''
									+ data.id + '\')">View Milestone</a>');
				},
				"bPaginate": false,
				"bDestroy" : true,
				'bAutoWidth': false,
				"sDom" : "<'row'<'col-xs-12 hidden col-right'f>r>t<'row'<'col-xs-3'i><'col-xs-3 col-right'l><'col-xs-3 col-right'<'export-data'T>><'col-xs-3 text-right'p>>",
				"oTableTools" : {
					"sSwfPath" : "../../../../../../egi/resources/global/swf/copy_csv_xls_pdf.swf",
					"aButtons" : [ ]
				},
				aaSorting : [],
				columns : [ {
					"data" : "",
					"sClass" : "text-center",
					"sWidth" : "3%"
				}, {
					"data" : "",
					"sClass" : "text-center",
					"sWidth" : "15%"
				}, {
					"data" : "nameOfWork",
					"sClass" : "text-center",
					"sWidth" : "25%"
				}, {
					"data" : "department",
					"sClass" : "text-center",
					"sWidth" : "10%"
				}, {
					"data" : "",
					"sClass" : "text-center",
					"sWidth" : "10%"
				}, {
					"data" : "",
					"sClass" : "text-right",
					"sWidth" : "10%"
				}, {
					"data" : "typeOfWork",
					"sClass" : "text-center",
					"sWidth" : "15%"
				}, {
					"data" : "subTypeOfWork",
					"sClass" : "text-center",
					"sWidth" : "5%"
				}, {
					"data" : "status",
					"sClass" : "text-center",
					"sWidth" : "10%"
				}, {
					"data" : "",
					"sClass" : "text-center",
					"sWidth" : "10%"
				} ]
			});
}

function openLineEstimate(lineEstimateId) {
	window.open("/egworks/lineestimate/view/" + lineEstimateId, '', 'height=650,width=980,scrollbars=yes,left=0,top=0,status=yes');
}

function openLOA(workOrderId) {
	window.open("/egworks/letterofacceptance/view/" + workOrderId, '', 'height=650,width=980,scrollbars=yes,left=0,top=0,status=yes');
}

function openMilestone(milestoneId) {
	window.open("/egworks/milestone/viewmilestone/" + milestoneId, '', 'height=650,width=980,scrollbars=yes,left=0,top=0,status=yes');
}