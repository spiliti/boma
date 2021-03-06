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

var map, geocoder, marker;
var lat, lng, address;
var fileinputid = ['file1', 'file2', 'file3'];
var filefilled = {};
var removedarray = [];
var fileid;

function extractCoordinate(latorlong) {
    var loc_arry = latorlong.split(",");
    var degree = parseFloat(loc_arry[0]);
    var minutes = parseFloat(loc_arry[1]);
    var seconds = parseFloat(loc_arry[2]);
    var formatted = degree + ((minutes * 60) + seconds) / 3600;
    return formatted;
}


$(document).ready(function () {

    $('#triggerFile').click(function () {
        if (removedarray.length == 0 || removedarray.length == 3) {
            var keys = Object.keys(filefilled);
            fileid = fileinputid[keys.length];
        } else {
            fileid = removedarray[0];
        }
        $('#' + fileid).trigger("click");
    });

    $('.remove-img').click(function () {
        delete filefilled[$(this).attr('data-file-id')];
        if ($.inArray($(this).attr('data-file-id'), removedarray) !== -1) {

        } else {
            removedarray.push($(this).attr('data-file-id'));
            removedarray.sort();
        }

        $('#' + $(this).attr('data-file-id')).val('');
        $('#triggerFile').removeAttr('disabled');
        if ($(this).attr('data-file-id') == 'file1') {
            $('#file1block, .preview-cross1, #preview1').hide();
            $('#preview1').removeAttr("src");
            $('#filename1').html('');
        } else if ($(this).attr('data-file-id') == 'file2') {
            $('#file2block, .preview-cross2, #preview2').hide();
            $('#preview2').removeAttr("src");
            $('#filename2').html('');
        } else if ($(this).attr('data-file-id') == 'file3') {
            $('#file3block, .preview-cross3, #preview3').hide();
            $('#preview3').removeAttr("src");
            $('#filename3').html('');
        }

    });

    $('#file1, #file2, #file3').on('change.bs.fileinput', function (e) {
        if (e.target.files.length > 0) {
            filefilled[$(this).attr('id')] = this.files[0].name;
            previewImage(this, this.files[0].name);
            var index = removedarray.indexOf(fileid);
            if (index > -1) {
                removedarray.splice(index, 1);
            }
            if (fileid == 'file1') {
                EXIF.getData(e.target.files[0], function () {
                    var rawImgLat = EXIF.getTag(this, "GPSLatitude");
                    var rawImgLng = EXIF.getTag(this, "GPSLongitude");
                    if (rawImgLat && rawImgLng) {
                        var imgLat = extractCoordinate(rawImgLat.toString());
                        var imgLng = extractCoordinate(rawImgLng.toString());
                        var geocoder = new google.maps.Geocoder;
                        geocoder.geocode({
                            'location': {
                                lat: imgLat,
                                lng: imgLng
                            }
                        }, function (results, status) {
                            if (status === 'OK') {
                                if (results[0]) {
                                    $('#location').typeahead('val', results[0].formatted_address);
                                    var location = new google.maps.LatLng(imgLat, imgLng);
                                    $('#lat').val(imgLat);
                                    $('#lng').val(imgLng);
                                    lat = imgLat;
                                    lng = imgLng;
                                    map.setCenter(location);
                                }
                            }
                        });
                    }
                });
            }
            if (Object.keys(filefilled).length == 3) {
                $('#triggerFile').attr('disabled', 'disabled');
            }
        }
    });

    function previewImage(input, filename) {
        filename = ((filename.length > 15) ? filename.substring(0, 13) + ".." : filename);
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                if (fileid == 'file1') {
                    $('#file1block, .preview-cross1, #preview1').show();
                    $('.preview-cross1').attr('data-file-id', fileid);
                    $('#preview1').attr('src', e.target.result).width(100);
                    $('#filename1').html(filename);
                } else if (fileid == 'file2') {
                    $('#file2block, .preview-cross2, #preview2').show();
                    $('.preview-cross2').attr('data-file-id', fileid);
                    $('#preview2').attr('src', e.target.result).width(100);
                    $('#filename2').html(filename);
                } else if (fileid == 'file3') {
                    $('#file3block, .preview-cross3, #preview3').show();
                    $('.preview-cross3').attr('data-file-id', fileid);
                    $('#preview3').attr('src', e.target.result).width(100);
                    $('#filename3').html(filename);
                }
            }

            reader.readAsDataURL(input.files[0]);
        }
    }

    function initialize() {
        var latLng, mapOptions = {
            zoom: 10,
            timeout: 500,
            mapTypeControl: false,
            navigationControl: false,
        };

        var userLocationFound = function (position) {
            latLng = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };
            var userLatLng = new google.maps.LatLng(latLng.lat, latLng.lng);
            lat = latLng.lat;
            lng = latLng.lng;

            getAddress(lat, lng);
            map.setCenter(userLatLng);
            mapcenterchangeevent();
        }

        var userLocationNotFound = function () {

            if (!citylat || !citylng) {
                citylat = 20.5937;
                citylng = 78.9629;
            }

            latLng = {
                lat: citylat,
                lng: citylng
            };
            setlatlong(citylat, citylng);
            mapcenterchangeevent();
        }


        geocoder = new google.maps.Geocoder();
        map = new google.maps.Map(document.getElementById("normal"), mapOptions);

        var GeoMarker = new GeolocationMarker(map);
        $('<div/>').addClass('centerMarker').appendTo(map.getDiv());

        navigator.geolocation.getCurrentPosition(userLocationFound, userLocationNotFound, mapOptions);

        setTimeout(function () {
            if (!latLng) {
                userLocationNotFound();
            }
        }, mapOptions.timeout + 500);

        searchBar(map);

    };

    function searchBar(map) {
        var input = (
            document.getElementById('pac-input'));

        var autocomplete = new google.maps.places.Autocomplete(input);
        autocomplete.bindTo('bounds', map);

        autocomplete.addListener('place_changed', function () {
            var place = autocomplete.getPlace();
            if (!place.geometry) {
                window.alert("Autocomplete's returned place contains no geometry");
                return;
            }

            // If the place has a geometry, then present it on a map.
            if (place.geometry.viewport) {
                map.fitBounds(place.geometry.viewport);
            } else {
                map.setCenter(place.geometry.location);
                map.setZoom(17);  // Why 17? Because it looks good.
            }

            var address = '';
            if (place.address_components) {
                address = [
                    (place.address_components[0] && place.address_components[0].short_name || ''),
                    (place.address_components[1] && place.address_components[1].short_name || ''),
                    (place.address_components[2] && place.address_components[2].short_name || '')
                ].join(' ');
            }

        });

    };

    function mapcenterchangeevent() {
        google.maps.event.addListener(map, 'center_changed', function () {
            var location = map.getCenter();
            getAddress(location.lat(), location.lng());
        });
    }

    function setlatlong(citylat, citylng) {
        var userLatLng = new google.maps.LatLng(citylat, citylng);
        lat = citylat;
        lng = citylng;

        getAddress(lat, lng);

        map.setCenter(userLatLng);
    }

    function getAddress(lat, lng) {
        var geocoder = new google.maps.Geocoder;
        geocoder.geocode({'location': {lat: lat, lng: lng}}, function (results, status) {
            if (status === 'OK') {
                if (results[0]) {
                    address = results[0].formatted_address;
                }
            }
        });
    }

    google.maps.event.addDomListener(window, 'load', initialize);

    google.maps.event.addDomListener(window, "resize", resizingMap());

    $('#modal-6').on('show.bs.modal', function () {
        resizeMap();
    });

    function resizeMap() {
        if (typeof map == "undefined") return;
        setTimeout(function () {
            resizingMap();
        }, 400);
    }

    function resizingMap() {
        if (typeof map == "undefined") return;
        var center = map.getCenter();
        google.maps.event.trigger(map, "resize");
        map.setCenter(center);
    }

    $('.btn-save-location').click(function () {
        var location = map.getCenter();
        lat = location.lat();
        lng = location.lng();
        var geocoder = new google.maps.Geocoder;
        geocoder.geocode({'location': {lat: lat, lng: lng}}, function (results, status) {
            if (status === 'OK') {
                if (results[0]) {
                    address = results[0].formatted_address;
                    $('#location').typeahead('val', address);
                    $('#latlngaddress').val(address);
                }
            }
        });
        $('#lat').val(lat);
        $('#lng').val(lng);
        $('#crosshierarchyId, #locationid').val('');
    });

    $('#modal-6').on('hidden.bs.modal', function () {
        $('#pac-input').val('');
        var userLatLng = new google.maps.LatLng(lat, lng);
        map.setCenter(userLatLng);
    });

});