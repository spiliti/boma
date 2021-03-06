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

package org.egov.infra.web.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.net.URLDecoder.decode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.egov.infra.security.utils.SecurityConstants.USER_AGENT_HEADER;
import static org.egov.infra.security.utils.SecurityConstants.X_FORWARDED_FOR_HEADER;
import static org.egov.infra.utils.ApplicationConstant.COLON;
import static org.egov.infra.utils.ApplicationConstant.COMMA;
import static org.egov.infra.utils.ApplicationConstant.SLASH;
import static org.egov.infra.utils.ApplicationConstant.UNKNOWN;

public final class WebUtils {

    private static final char QUESTION_MARK = '?';
    private static final char FORWARD_SLASH = '/';
    private static final String SCHEME_DOMAIN_SEPARATOR = "://";

    private WebUtils() {
        //Since utils are with static methods
    }

    /**
     * This will return only domain name from http request <br/>
     * eg: http://www.domain.com/cxt/xyz will return www.domain.com http://somehost:8090/cxt/xyz will return somehost
     **/
    public static String extractRequestedDomainName(HttpServletRequest httpRequest) {
        String requestURL = httpRequest.getRequestURL().toString();
        return extractRequestedDomainName(requestURL);
    }

    /**
     * This will return only domain name from given requestUrl <br/>
     * eg: http://www.domain.com/cxt/xyz will return www.domain.com http://somehost:8090/cxt/xyz will return somehost
     **/
    public static String extractRequestedDomainName(String requestURL) {
        int domainNameStartIndex = requestURL.indexOf(SCHEME_DOMAIN_SEPARATOR) + 3;
        int domainNameEndIndex = requestURL.indexOf(FORWARD_SLASH, domainNameStartIndex);
        String domainName = requestURL.substring(domainNameStartIndex,
                domainNameEndIndex > 0 ? domainNameEndIndex : requestURL.length());
        if (domainName.contains(COLON))
            domainName = domainName.split(COLON)[0];
        return domainName;
    }

    /**
     * This will return full domain name including http/s scheme and optionally with contextroot depends on 'withContext' value eg:
     * http://www.domain.com/cxt/xyz withContext value as true will return http://www.domain.com/cxt/ <br/>
     * http://www.domain.com/cxt/xyz withContext value as false will return http://www.domain.com
     **/
    public static String extractRequestDomainURL(HttpServletRequest httpRequest, boolean withContext) {
        StringBuilder url = new StringBuilder(httpRequest.getRequestURL());
        String uri = httpRequest.getRequestURI();
        return withContext ? url.substring(0, url.length() - uri.length() + httpRequest.getContextPath().length()) + FORWARD_SLASH
                : url.substring(0, url.length() - uri.length());
    }

    public static String extractQueryParamsFromUrl(String url) {
        return url.substring(url.indexOf(QUESTION_MARK) + 1);
    }

    public static String extractURLWithoutQueryParams(String url) {
        return url.substring(0, url.indexOf(QUESTION_MARK));
    }

    public static String currentContextPath(ServletRequest request) {
        return request.getServletContext().getContextPath().replace(SLASH, EMPTY);
    }

    public static String extractOriginIPAddress(HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        String proxiedIPAddress = request.getHeader(X_FORWARDED_FOR_HEADER);
        if (isNotBlank(proxiedIPAddress)) {
            String[] ipAddresses = proxiedIPAddress.split(COMMA);
            ipAddress = ipAddresses[ipAddresses.length - 1].trim();
        }
        return ipAddress;
    }

    public static String extractUserAgent(HttpServletRequest request) {
        return defaultIfBlank(request.getHeader(USER_AGENT_HEADER), UNKNOWN);
    }

    public static Map<String, String> bindErrorToMap(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField,
                        FieldError::getDefaultMessage, (a, b) -> b, HashMap::new));
    }

    public static String decodeQueryString(String queryString) {
        try {
            return decode(queryString, UTF_8.name());
        } catch (UnsupportedEncodingException usee) {
            return queryString;
        }
    }
}