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

// JavaScript Document

function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}

function MM_goToURL() { //v3.0
  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;
  for (i=0; i<(args.length-1); i+=2) eval(args[i]+".location='"+args[i+1]+"'");
}
function MM_showHideLayers() { //v9.0
  var i,p,v,obj,args=MM_showHideLayers.arguments;
  for (i=0; i<(args.length-2); i+=3) 
  with (document) if (getElementById && ((obj=getElementById(args[i]))!=null)) { v=args[i+2];
    if (obj.style) { obj=obj.style; v=(v=='show')?'visible':(v=='hide')?'hidden':v; }
    obj.visibility=v; }
}
function MM_callJS(jsStr) { //v2.0
  return eval(jsStr)
}
function show(x)
{
if(document.getElementById(x).style.display=='none')
document.getElementById(x).style.display='';
else
document.getElementById(x).style.display='none';
}


/* This javascript is used for auto tab feature. Automatically sets focus to the next form element when the current form element's 
maxlength has been reached. The user, then, does not have to manually click in or tab to the next field. Very easy to change for different
 size fields.  */ 
function autoTab(input,len, e) {
	var isNN = (navigator.appName.indexOf("Netscape")!=-1);
  var keyCode = (isNN) ? e.which : e.keyCode; 
  var filter = (isNN) ? [0,8,9] : [0,8,9,16,17,18,37,38,39,40,46];
  if(input.value.length >= len && !containsElement(filter,keyCode)) {
    input.value = input.value.slice(0, len);
    input.form[(getIndex(input)+1) % input.form.length].focus();
  }

  function containsElement(arr, ele) {
    var found = false, index = 0;
    while(!found && index < arr.length)
    if(arr[index] == ele)
    found = true;
    else
    index++;
    return found;
  }

  function getIndex(input) {
    var index = -1, i = 0, found = false;
    while (i < input.form.length && index == -1)
    if (input.form[i] == input)index = i;
    else i++;
    return index;
  }
  return true;
}

function populatePartNumbers(wardDropdownId) {
	populatepartNumbers({
		wardId : document.getElementById(wardDropdownId).value
	});			
}

function paintAlternateColorForRows() {
   jQuery("document").ready(function() {
   		jQuery("tr:even > td").addClass("greybox");
   		jQuery("tr:odd > td").addClass("bluebox");
   	});
}

function checkHouseNoStartsWithNo(field) {
	var pattern = /^0+$/; 
	if (pattern.test(field.value)) {
		bootbox.alert('House number should not contain only zeros');
		field.value="";
		field.focus();
		return false;
	}
	
	return true;
}

function clearSelectedFile(obj) {
	jQuery(obj).parent().find('input:file').val('');
	jQuery(obj).remove();
}

$(document).ready(function () {

//check file name and format is valid
var fileNamePattern = "^[\\w\\[\\]\\(\\)\\-\\s]{1,245}\\.(0){1,9}";
$('input:file').change(function () {
    var acceptableExtensions = $(this).data("accepts");
    if (acceptableExtensions) {
        var file = $(this);
        var fileName = file.val().split(/[\\/]/g).pop();
        var acceptableFileNamePattern = new RegExp(fileNamePattern.replace("0", acceptableExtensions), "ig");
        if (!fileName.match(acceptableFileNamePattern)) {
            file.replaceWith(file.val('').clone(true))
            bootbox.alert({
                title: "Invalid file, ensure the file name and format is valid.",
                message: "File name must be less than <i style='font-weight: bold'>256</i> character long.<br/>" +
                    "Should not contain any special characters except <i style='font-weight: bold'>- _ ] [ ) ( and space</i>.<br/>" +
                    "Accepts only <i style='font-weight: bold'>" + acceptableExtensions + "</i> formats."
            });
            return false;
        }
    }
});

//check file size is valid
$('input:file').change(function () {
    if ($(this).data("size")) {
        var file = $(this);
        var maxFileSize = parseInt($(this).data("size"));
        if (file.get(0).files.length) {
            var fileSize = (this.files[0].size / 1024 / 1024).toFixed(0);
            if (fileSize > maxFileSize) {
                bootbox.alert({
                    title: "Invalid file, size exceeded.",
                    message: "File could not be uploaded, it is larger than the maximum allowed file size.<br/>" +
                        "(<i style=\'font-weight: bold\'>Uploaded : " + fileSize + " MB, Allowed : " + maxFileSize + " MB</i>)"
                });
                file.replaceWith(file.val('').clone(true));
                return false;
            }
        }
    }
    var file = $(this);
    var fileName = file.val().split(/[\\/]/g).pop();
    if (fileName) {
		$(this)
				.after(
						"<a href='javascript:void(0);' onclick='clearSelectedFile(this);' class='fileclear'><span class='tblactionicon delete'><i class='fa fa-times-circle'></i></span></a>");
	} else {
		if ($(this).next().is("span")) {
			$(this).next().remove();
		}
	}
});

//Add tooltip about the file size and the file extension allowed
$('input:file').hover(function () {
    if ($(this).data("accepts") && $(this).data("size")) {
        $(this).attr('title', 'Accepts only file with extension [' + $(this).data("accepts")
            + '], file size upto [' + $(this).data("size") + 'MB] and file name length upto [255]');
        $(this).tooltip();
    }
});

});