/*
 *    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) 2018  eGovernments Foundation
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

package org.egov.infra.security.auth;

import org.egov.infra.admin.master.entity.User;
import org.egov.infra.notification.service.NotificationService;
import org.egov.infra.security.token.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

import static java.util.Locale.getDefault;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.egov.infra.notification.entity.NotificationPriority.HIGH;

@Service
@Transactional(readOnly = true)
public class PreAuthService {

    private static final String PRE_AUTH_SERVICE = "Pre Auth Service";
    private static final String OTP_MSG_KEY = "msg.preauth.otp";
    private static final String OTP_MSG_SUBJECT = "Portal Login OTP";

    @Autowired
    private NotificationService notificationService;

    @Autowired
    @Qualifier("parentMessageSource")
    private MessageSource messageSource;

    @Autowired
    private TokenService tokenService;

    @Transactional
    public boolean sendOtpIfRequired(User user) {
        boolean useMultiFA = user != null && user.isUseMultiFA();
        if (useMultiFA) {
            String otp = randomNumeric(6);
            tokenService.createToken(TimeUnit.MINUTES.toSeconds(1), otp, user.getMobileNumber(), PRE_AUTH_SERVICE);
            String message = messageSource.getMessage(OTP_MSG_KEY, new String[]{otp}, getDefault());
            notificationService.sendSMS(user.getMobileNumber(), message, HIGH);
            notificationService.sendEmail(user.getEmailId(), OTP_MSG_SUBJECT, message);
        }
        return useMultiFA;
    }

    public boolean validOtpAuth(User user, String otp) {
        return isNotBlank(otp) && tokenService.redeemToken(otp, user.getMobileNumber(), PRE_AUTH_SERVICE);
    }
}
