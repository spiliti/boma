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

package org.egov.mrs.domain.entity;

import org.egov.infra.validation.constants.ValidationRegex;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;

import static org.egov.infra.validation.constants.ValidationErrorCode.INVALID_ALPHANUMERIC_WITH_SPECIAL_CHARS;
import static org.egov.infra.validation.constants.ValidationRegex.ALPHANUMERIC_WITH_SPECIAL_CHARS;

import javax.persistence.Embeddable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Embeddable
public class Contact {

    @Pattern(regexp = ValidationRegex.MOBILE_NUMBER)
    @SafeHtml
    @Length(max = 15)
    @Audited
    private String mobileNo;

    @SafeHtml
    @Audited
    @Length(max = 128)
    @Email(regexp = ValidationRegex.EMAIL)
    private String email;

    @NotNull
    @SafeHtml
    @Length(max = 256)
    @Pattern(regexp = ALPHANUMERIC_WITH_SPECIAL_CHARS, message = INVALID_ALPHANUMERIC_WITH_SPECIAL_CHARS)
    private String residenceAddress;

    /*  @NotNull*/
    @SafeHtml
    @Length(max = 256)
    @Pattern(regexp = ALPHANUMERIC_WITH_SPECIAL_CHARS, message = INVALID_ALPHANUMERIC_WITH_SPECIAL_CHARS)
    private String officeAddress;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(final String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getResidenceAddress() {
        return residenceAddress;
    }

    public void setResidenceAddress(final String residenceAddress) {
        this.residenceAddress = residenceAddress;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(final String officeAddress) {
        this.officeAddress = officeAddress;
    }
}
