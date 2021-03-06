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

import java.util.Date;

import org.hibernate.validator.constraints.SafeHtml;

public class MarriageRegistrationSearchFilter {

	@SafeHtml
    private String applicationNo;
	@SafeHtml
    private String registrationNo;
	@SafeHtml
    private String husbandName;
	@SafeHtml
    private String wifeName;
    private Date dateOfMarriage;
    private Date applicationDate;
    @SafeHtml
    private String marriageRegistrationType;
    private Date fromDate;
    private Date toDate;
    private Long marriageRegistrationUnit;
    @SafeHtml
    private String registrationUnitName;
    private Long registrationUnitZone;
    private boolean isRegistrationUnitActive;
    @SafeHtml
    private String certificateNo;
    @SafeHtml
    private String certificateType;
    @SafeHtml
    private String frequency;
    
    private String errorMessage;

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getHusbandName() {
        return husbandName;
    }

    public void setHusbandName(String husbandName) {
        this.husbandName = husbandName;
    }

    public String getWifeName() {
        return wifeName;
    }

    public void setWifeName(String wifeName) {
        this.wifeName = wifeName;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getMarriageRegistrationType() {
        return marriageRegistrationType;
    }

    public void setMarriageRegistrationType(String marriageRegistrationType) {
        this.marriageRegistrationType = marriageRegistrationType;
    }

    public Date getDateOfMarriage() {
        return dateOfMarriage;
    }

    public void setDateOfMarriage(Date dateOfMarriage) {
        this.dateOfMarriage = dateOfMarriage;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Long getMarriageRegistrationUnit() {
        return marriageRegistrationUnit;
    }

    public void setMarriageRegistrationUnit(Long marriageRegistrationUnit) {
        this.marriageRegistrationUnit = marriageRegistrationUnit;
    }

	public String getRegistrationUnitName() {
		return registrationUnitName;
	}

	public void setRegistrationUnitName(String registrationUnitName) {
		this.registrationUnitName = registrationUnitName;
	}

	public Long getRegistrationUnitZone() {
		return registrationUnitZone;
	}

	public void setRegistrationUnitZone(Long registrationUnitZone) {
		this.registrationUnitZone = registrationUnitZone;
	}

	public boolean getIsRegistrationUnitActive() {
		return isRegistrationUnitActive;
	}

	public void setIsRegistrationUnitActive(boolean isRegistrationUnitActive) {
		this.isRegistrationUnitActive = isRegistrationUnitActive;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	
	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}
	
	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}