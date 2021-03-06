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

$(document).ready(function() {
	$('#btngenerate').on('click', function(event) {
		$("#recoveryNoticesForm").submit();
	});

	jQuery('#ward').change(
		function() {
			var wardId = jQuery('#ward').val();
			if (wardId == "-1") {
				jQuery('#block').val("-1");
				jQuery('#block').html("");
				jQuery('#block').append(
						"<option value='-1'>Select</option>");
			} else {
				jQuery.ajax({
					url : "/egi/boundary/block/by-ward",
					type : "GET",
					data : {
						wardId : wardId
					},
					cache : false,
					dataType : "json",
					success : function(response) {
						jQuery('#block').prop("disabled", false);
						jQuery('#block').html("");
						jQuery('#block').append(
								"<option value='-1'>Select</option>");
						jQuery.each(JSON.parse(response), function(j, block) {
							jQuery('#block').append(
									"<option value='" + block.id + "'>"
											+ block.name + "</option>");
						});
					},
					error : function(response) {
						jQuery('#block').prop("disabled", false);
						jQuery('#block').html("");
						jQuery('#block').append(
								"<option value='-1'>Select</option>");
						bootbox.alert("No block details mapped for ward");
					}
				});
			}
		});

	jQuery('#propertyType').change(
		function() {
			var propertyTypeId = jQuery('#propertyType').val();
			if (propertyTypeId == "-1") {
				jQuery('#categoryType').val("-1");
				jQuery('#categoryType').html("");
				jQuery('#categoryType').append(
						"<option value='-1'>Select</option>");
			} else {
				jQuery.ajax({
					url : "/ptis/public/common/ajaxCommon-propTypeCategoryByPropType.action",
					type : "GET",
					data : {
						propTypeId : propertyTypeId
					},
					cache : false,
					dataType : "json",
					success : function(response) {
						jQuery('#categoryType').prop("disabled", false);
						jQuery('#categoryType').html("");
						jQuery('#categoryType').append(
								"<option value='-1'>Select</option>");
						jQuery.each(response, function(j, res) {
							jQuery.each(res, function(j, list) {
								jQuery.each(list, function(j, rec) {
									jQuery('#categoryType').append(
											"<option value='" + rec.Value + "'>"
													+ rec.Text + "</option>");
								});
							});
						});
					},
					error : function(response) {
						jQuery('#categoryType').prop("disabled", false);
						jQuery('#categoryType').html("");
						jQuery('#categoryType').append(
								"<option value='-1'>Select</option>");
					}
				});
			}
		});
});