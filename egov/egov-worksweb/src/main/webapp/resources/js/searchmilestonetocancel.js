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
jQuery('#btnsearch').click(function(e) {
	callAjaxSearch();
});

function callAjaxSearch() {
	drillDowntableContainer = jQuery("#resultTable");
	jQuery('.report-section').removeClass('display-hide');
	reportdatatable = drillDowntableContainer
			.dataTable({
				ajax : {
					url : "/egworks/milestone/cancel/ajax-search",
					type : "POST",
					"data" : getFormData(jQuery('form')),
					"error" : function() {
						jQuery("#resultTable tbody tr td").html(
								"No data available.");
					}
				},
				"bDestroy" : true,
				'bAutoWidth': false,
				"sDom" : "<'row'<'col-xs-12 hidden col-right'f>r>t<'row'<'col-xs-3'i><'col-xs-3 col-right'l><'col-xs-3 col-right'<'export-data'T>><'col-xs-3 text-right'p>>",
				"aLengthMenu" : [ [ 10, 25, 50, -1 ], [ 10, 25, 50, "All" ] ],
				"oTableTools" : {
					"sSwfPath" : "../../../../../../egi/resources/global/swf/copy_csv_xls_pdf.swf",
					"aButtons" : []
				},
				"fnRowCallback" : function(row, data, index) {
					$('td:eq(0)',row).html('<input type="radio" name="selectCheckbox" value="'+ data.id +'"/>');
					if (data.workOrderNumber != null)
						$('td:eq(1)', row).html(
								'<a href="javascript:void(0);" onclick="viewLetterOfAcceptance(\''
										+ data.workOrderId + '\')">'
										+ data.workOrderNumber + '</a>');
					$('td:eq(3)',row).html(parseFloat(Math.round(data.agreementAmount * 100) / 100).toFixed(2));
					if (data.id != null)
						$('td:eq(6)', row).html(
								'<a href="javascript:void(0);" onclick="viewMilestone(\''
										+ data.id + '\', \'' + data.trackMilestone + '\')">View Milestone</a>');
					return row;
				},
				aaSorting : [],
				columns : [ {
					"data" : "",
					"sClass" : "text-center","width": "2%"
				}, {
					"data" : "",
					"sClass" : "text-center","autoWidth": "false"
				}, {
					"data" : "workOrderDate",
					"sClass" : "text-center" ,"width": "6%"
				}, {
					"data" : "",
					"sClass" : "text-right","autoWidth": "false"
				}, {
					"data" : "contractor",
					"sClass" : "text-center","autoWidth": "false"
				}, {
					"data" : "workIdentificationNumber",
					"sClass" : "text-center","autoWidth": "false"
				}, {
					"data" : "",
					"sClass" : "text-center","autoWidth": "false"
				} ]
			});
}

function viewLetterOfAcceptance(id) {
	window.open("/egworks/letterofacceptance/view/" + id, '', 'height=650,width=980,scrollbars=yes,left=0,top=0,status=yes');
}

function viewMilestone(id, trackMilestone) {
	if(trackMilestone == 'true')
		window.open("/egworks/milestone/viewtrackmilestone/" + id, '', 'height=650,width=980,scrollbars=yes,left=0,top=0,status=yes');
	else
		window.open("/egworks/milestone/viewmilestone/" + id, '', 'height=650,width=980,scrollbars=yes,left=0,top=0,status=yes');
}

function getFormData($form) {
	var unindexed_array = $form.serializeArray();
	var indexed_array = {};

	$.map(unindexed_array, function(n, i) {
		indexed_array[n['name']] = n['value'];
	});

	return indexed_array;
}

$(document).ready(function() {
	var loaNumber = new Bloodhound({
	    datumTokenizer: function (datum) {
	        return Bloodhound.tokenizers.whitespace(datum.value);
	    },
	    queryTokenizer: Bloodhound.tokenizers.whitespace,
	    remote: {
	        url: '/egworks/milestone/ajaxloanumbers-milestonetocancel?code=%QUERY',
	        filter: function (data) {
	            return $.map(data, function (ct) {
	                return {
	                    name: ct
	                };
	            });
	        }
	    }
	});

	loaNumber.initialize();
	var loaNumber_typeahead = $('#workOrderNumber').typeahead({
		hint : true,
		highlight : true,
		minLength : 3
	}, {
		displayKey : 'name',
		source : loaNumber.ttAdapter()
	});
	
	var contractor = new Bloodhound({
	    datumTokenizer: function (datum) {
	        return Bloodhound.tokenizers.whitespace(datum.value);
	    },
	    queryTokenizer: Bloodhound.tokenizers.whitespace,
	    remote: {
	        url: '/egworks/milestone/ajaxcontractors-milestonetocancel?code=%QUERY',
	        filter: function (data) {
	            return $.map(data, function (ct) {
	                return {
	                    name: ct
	                };
	            });
	        }
	    }
	});

	contractor.initialize();
	var contractor_typeahead = $('#contractor').typeahead({
		hint : true,
		highlight : true,
		minLength : 3
	}, {
		displayKey : 'name',
		source : contractor.ttAdapter()
	});
	
	$('#btncancel').click(function() {
		var milestoneId = $('input[name=selectCheckbox]:checked').val();
		if(milestoneId == null) {
			bootbox.alert("Please select a Milestone to Cancel");
		} else {
			if($('#cancellationReason').val() == 'Others')
				$('#cancellationRemarks').attr('required', 'required');
			else
				$('#cancellationRemarks').removeAttr('required');
			
			if($("#cancelForm").valid()) {
				$('#cancelForm #id').val(milestoneId);
				bootbox.confirm($('#confirm').val(), function(result) {
					if(!result) {
						bootbox.hideAll();
						return false;
					} else {
						$("#cancelForm").attr('action','/egworks/milestone/cancel');
						$("#cancelForm").submit();
					}
				});
			}
		}
		return false;
	});
});