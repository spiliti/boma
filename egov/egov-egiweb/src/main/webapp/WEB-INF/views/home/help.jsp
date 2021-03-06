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

<style>
html,body{
	background:#fff;
}
.page-container .main-content{
    position: absolute;
    height: calc(100%);
}
.padding-left-right{
	padding-left: 15px;
	padding-right: 15px;
}
</style>
<script>
$(document).ready(function(){

	$('.source').click(function(){
		$('li').removeClass('tab-current-newreq');
		$(this).addClass('tab-current-newreq');
		var src = $(this).data('src');
		var fn = window[src];
		if (typeof fn === "function") fn(src);
	});
	
});

function property(src){
	//bootbox.alert(src);
	$('#getiframe').attr('src', 'https://www.egovernments.org/docs/usermanuals/ap/ptis.pdf');
}
function water(src){
	//bootbox.alert(src);
	$('#getiframe').attr('src', 'https://www.egovernments.org/docs/usermanuals/ap/wcms.pdf');
}
function grievance(src){
	//bootbox.alert(src);
	$('#getiframe').attr('src', 'https://www.egovernments.org/docs/usermanuals/ap/pgrs.pdf');
}
function council(src){
	//bootbox.alert(src);
	$('#getiframe').attr('src', 'https://www.egovernments.org/docs/usermanuals/ap/council.pdf');
}
function lcms(src){
	//bootbox.alert(src);
	$('#getiframe').attr('src', 'https://www.egovernments.org/docs/usermanuals/ap/lcms.pdf');
}
function ntr(src){
	//bootbox.alert(src);
	$('#getiframe').attr('src', 'https://www.egovernments.org/docs/usermanuals/ap/ntr.pdf');
}
function eis(src){
	//bootbox.alert(src);
	$('#getiframe').attr('src', 'https://www.egovernments.org/docs/usermanuals/ap/eis.pdf');
}
function fin(src){
	//bootbox.alert(src);
	$('#getiframe').attr('src', 'https://www.egovernments.org/docs/usermanuals/ap/fin.pdf');
}
function tl(src){
	//bootbox.alert(src);
	$('#getiframe').attr('src', 'https://www.egovernments.org/docs/usermanuals/ap/tl.pdf');
}
function asset(src){
	//bootbox.alert(src);
	$('#getiframe').attr('src', 'https://www.egovernments.org/docs/usermanuals/ap/asset.pdf');
}
function lest(src){
	//bootbox.alert(src);
	$('#getiframe').attr('src', 'https://www.egovernments.org/docs/usermanuals/ap/lest.pdf');
}
function advtx(src){
	//bootbox.alert(src);
	$('#getiframe').attr('src', 'https://www.egovernments.org/docs/usermanuals/ap/advtx.pdf');
}
function mrgreg(src){
	//bootbox.alert(src);
	$('#getiframe').attr('src', 'https://www.egovernments.org/docs/usermanuals/ap/mrgreg.pdf');
}
function swtx(src){
	//bootbox.alert(src);
	$('#getiframe').attr('src', 'https://www.egovernments.org/docs/usermanuals/ap/swtx.pdf');
}

</script>
<div class="row text-center" style="height:calc(100%);">

	<div class="citizen-screens tabs tabs-style-topline">
		<nav>
			<ul style="overflow:auto;">
				<li class="tab-current-newreq source" data-src="property" tooltip="Property and Vacant Land Tax">
					<a href="javascript:void(0);">
						<div class="text-center"><i class="fa fa-building-o"></i></div>
						<span class="hidden-xs padding-left-right">Property and Vacant Land Tax</span>
					</a>
				</li>
				<li class="source" data-src="water">
					<a href="javascript:void(0);">
						<div class="text-center"><i class="fa fa-tint"></i></div>
						<span class="hidden-xs padding-left-right">Water Charges</span>
					</a>
				</li>
				<li class="source" data-src="grievance">
					<a href="javascript:void(0);">
						<div class="text-center"><i class="fa fa-pencil"></i></div>
						<span class="hidden-xs padding-left-right">Public Grievance and Redressal System</span>
					</a>
				</li>
				<li class="source" data-src="lcms"><a
					href="javascript:void(0);">
						<div class="text-center">
							<i class="fa fa-gavel"></i>
						</div> <span class="hidden-xs padding-left-right">Legal Case Management</span>
				</a></li>
				<li class="source" data-src="ntr"><a
					href="javascript:void(0);">
						<div class="text-center">
							<i class="fa fa-university"></i>
						</div> <span class="hidden-xs padding-left-right">Non Tax Revenue</span>
				</a></li>
				<li class="source" data-src="fin"><a
					href="javascript:void(0);">
						<div class="text-center">
							<i class="fa fa-money"></i>
						</div> <span class="hidden-xs padding-left-right">Finance Management</span>
				</a></li>
				<li class="source" data-src="eis"><a
					href="javascript:void(0);">
						<div class="text-center">
							<i class="fa fa-user"></i>
						</div> <span class="hidden-xs padding-left-right">Employee Management</span>
				</a></li>
				<li class="source" data-src="tl"><a
					href="javascript:void(0);">
						<div class="text-center">
							<i class="fa fa-outdent"></i>
						</div> <span class="hidden-xs padding-left-right">Trade License</span>
				</a></li>
				<li class="source" data-src="asset"><a
					href="javascript:void(0);">
						<div class="text-center">
							<i class="fa fa-shopping-bag"></i>
						</div> <span class="hidden-xs padding-left-right">Asset</span>
				</a></li>
				<li class="source" data-src="lest"><a
					href="javascript:void(0);">
						<div class="text-center">
							<i class="fa fa-building-o"></i>
						</div> <span class="hidden-xs padding-left-right">Leases & Agreements</span>
				</a></li>
				<li class="source" data-src="advtx"><a
					href="javascript:void(0);">
						<div class="text-center">
							<i class="fa fa-adn"></i>
						</div> <span class="hidden-xs padding-left-right">Advertisement Tax</span>
				</a></li>
				<li class="source" data-src="council"><a
					href="javascript:void(0);">
						<div class="text-center">
							<i class="fa fa-connectdevelop"></i>
						</div> <span class="hidden-xs padding-left-right">Council Management</span>
				</a></li>
				<li class="source" data-src="mrgreg"><a
					href="javascript:void(0);">
						<div class="text-center">
							<i class="fa fa-registered"></i>
						</div> <span class="hidden-xs padding-left-right">Marriage Registration</span>
				</a></li>
				<li class="source" data-src="swtx"><a
					href="javascript:void(0);">
						<div class="text-center">
							<i class="fa fa-ticket"></i>
						</div> <span class="hidden-xs padding-left-right">Sewerage Tax</span>
				</a></li>
			</ul>
		</nav>
	</div>
	
	<div class="col-md-12" style="height:calc(100%);margin-top:10px;margin-bottom:20px;">
		<iframe id="getiframe" src="http://www.egovernments.org/docs/usermanuals/ap/ptis.pdf" width="100%" height="100%">
	</div>
	
</div>