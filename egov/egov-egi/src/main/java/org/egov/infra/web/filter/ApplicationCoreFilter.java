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

package org.egov.infra.web.filter;

import org.egov.infra.admin.master.service.CityService;
import org.egov.infra.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.egov.infra.config.core.ApplicationThreadLocals.clearValues;
import static org.egov.infra.config.core.ApplicationThreadLocals.getTenantID;
import static org.egov.infra.config.core.ApplicationThreadLocals.setCityCode;
import static org.egov.infra.config.core.ApplicationThreadLocals.setCityName;
import static org.egov.infra.config.core.ApplicationThreadLocals.setIPAddress;
import static org.egov.infra.config.core.ApplicationThreadLocals.setMunicipalityName;
import static org.egov.infra.config.core.ApplicationThreadLocals.setUserId;
import static org.egov.infra.security.utils.SecurityConstants.LOGIN_IP_ADDRESS;
import static org.egov.infra.utils.ApplicationConstant.CITY_CODE_KEY;
import static org.egov.infra.utils.ApplicationConstant.CITY_CORP_NAME_KEY;
import static org.egov.infra.utils.ApplicationConstant.CITY_NAME_KEY;
import static org.egov.infra.utils.ApplicationConstant.TENANTID_KEY;
import static org.egov.infra.utils.ApplicationConstant.USERID_KEY;
import static org.egov.infra.web.utils.WebUtils.extractOriginIPAddress;

public class ApplicationCoreFilter implements Filter {


    @Autowired
    private CityService cityService;

    @Autowired
    private SecurityUtils securityUtils;

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) req;
            HttpSession session = request.getSession();
            prepareUserSession(session, request);
            prepareApplicationThreadLocal(session);
            chain.doFilter(request, resp);
        } finally {
            clearValues();
        }
    }

    private void prepareUserSession(HttpSession session, HttpServletRequest request) {
        if (session.getAttribute(CITY_CODE_KEY) == null)
            cityService.cityDataAsMap().forEach(session::setAttribute);
        if (session.getAttribute(TENANTID_KEY) == null)
            session.setAttribute(TENANTID_KEY, getTenantID());
        if (session.getAttribute(USERID_KEY) == null)
            session.setAttribute(USERID_KEY, securityUtils.getCurrentUser().getId());
        if (session.getAttribute(LOGIN_IP_ADDRESS) == null)
            session.setAttribute(LOGIN_IP_ADDRESS, extractOriginIPAddress(request));
    }

    private void prepareApplicationThreadLocal(HttpSession session) {
        setCityCode((String) session.getAttribute(CITY_CODE_KEY));
        setCityName((String) session.getAttribute(CITY_NAME_KEY));
        setMunicipalityName((String) session.getAttribute(CITY_CORP_NAME_KEY));
        setUserId((Long) session.getAttribute(USERID_KEY));
        setIPAddress((String) session.getAttribute(LOGIN_IP_ADDRESS));
    }

    @Override
    public void destroy() {
        //Nothing to be destroyed
    }

    @Override
    public void init(FilterConfig filterConfig) {
        //Nothing to be initialized
    }
}
