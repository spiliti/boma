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

package org.egov.infra.admin.master.entity;

import org.egov.infra.persistence.entity.AbstractAuditable;
import org.egov.infra.persistence.validator.annotation.Unique;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.egov.infra.admin.master.entity.City.QUERY_CITY_BY_URL;
import static org.egov.infra.admin.master.entity.City.SEQ_CITY;
import static org.egov.infra.security.utils.captcha.CaptchaUtils.CITY_CAPTCHA_PRIV_KEY;
import static org.egov.infra.security.utils.captcha.CaptchaUtils.CITY_CAPTCHA_PUB_KEY;
import static org.egov.infra.utils.ApplicationConstant.*;
import static org.egov.infra.validation.constants.ValidationErrorCode.INVALID_ALPHABETS_WITH_SPACE;
import static org.egov.infra.validation.constants.ValidationErrorCode.INVALID_MASTER_DATA_CODE;
import static org.egov.infra.validation.constants.ValidationRegex.ALPHABETS_WITH_SPACE;
import static org.egov.infra.validation.constants.ValidationRegex.MASTER_DATA_CODE;

@Entity
@Unique(fields = {"code", "domainURL"}, enableDfltMsg = true)
@Table(name = "eg_city")
@NamedQuery(name = QUERY_CITY_BY_URL, query = "Select cw FROM City cw WHERE cw.domainURL=:domainURL")
@SequenceGenerator(name = SEQ_CITY, sequenceName = SEQ_CITY, allocationSize = 1)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class City extends AbstractAuditable {

    public static final String QUERY_CITY_BY_URL = "CITY_BY_URL";
    protected static final String SEQ_CITY = "SEQ_EG_CITY";
    private static final long serialVersionUID = -6267923687226233397L;

    @Id
    @GeneratedValue(generator = SEQ_CITY, strategy = GenerationType.SEQUENCE)
    private Long id;

    @SafeHtml
    @NotBlank
    @Length(max = 256)
    @Pattern(regexp = ALPHABETS_WITH_SPACE, message = INVALID_ALPHABETS_WITH_SPACE)
    private String name;

    @SafeHtml
    @Length(max = 256)
    @Pattern(regexp = ALPHABETS_WITH_SPACE, message = INVALID_ALPHABETS_WITH_SPACE)
    private String localName;

    private boolean active;

    @SafeHtml
    @NotBlank
    @Length(max = 128)
    private String domainURL;

    @SafeHtml
    @NotBlank
    @Length(max = 4)
    @Column(updatable = false)
    @Pattern(regexp = MASTER_DATA_CODE, message = INVALID_MASTER_DATA_CODE)
    private String code;

    @SafeHtml
    @NotBlank
    @Length(max = 10)
    @Pattern(regexp = MASTER_DATA_CODE, message = INVALID_MASTER_DATA_CODE)
    private String lgdCode;

    @SafeHtml
    @NotBlank
    @Length(max = 10)
    @Pattern(regexp = MASTER_DATA_CODE, message = INVALID_MASTER_DATA_CODE)
    private String districtCode;

    @SafeHtml
    @NotBlank
    @Length(max = 10)
    @Pattern(regexp = MASTER_DATA_CODE, message = INVALID_MASTER_DATA_CODE)
    private String districtLGDCode;

    @SafeHtml
    @NotBlank
    @Length(max = 50)
    @Pattern(regexp = ALPHABETS_WITH_SPACE, message = INVALID_ALPHABETS_WITH_SPACE)
    private String districtName;

    @SafeHtml
    @Length(max = 50)
    @Pattern(regexp = ALPHABETS_WITH_SPACE, message = INVALID_ALPHABETS_WITH_SPACE)
    private String regionName;

    @SafeHtml
    @Length(max = 50)
    private String grade;

    @Positive
    private Float longitude;

    @Positive
    private Float latitude;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "preferences")
    @NotAudited
    @Fetch(FetchMode.JOIN)
    @Valid
    private CityPreferences preferences;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(final String localName) {
        this.localName = localName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public String getDomainURL() {
        return domainURL;
    }

    public void setDomainURL(final String domainURL) {
        this.domainURL = domainURL;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getLgdCode() {
        return lgdCode;
    }

    public void setLgdCode(final String lgdCode) {
        this.lgdCode = lgdCode;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(final Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(final Float latitude) {
        this.latitude = latitude;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(final String districtCode) {
        this.districtCode = districtCode;
    }

    public String getDistrictLGDCode() {
        return districtLGDCode;
    }

    public void setDistrictLGDCode(final String districtLGDCode) {
        this.districtLGDCode = districtLGDCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(final String districtName) {
        this.districtName = districtName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(final String regionName) {
        this.regionName = regionName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(final String grade) {
        this.grade = grade;
    }

    public CityPreferences getPreferences() {
        return preferences;
    }

    public void setPreferences(final CityPreferences preferences) {
        this.preferences = preferences;
    }

    public Map<String, Object> toMap() {
        final Map<String, Object> cityPrefs = new HashMap<>();
        cityPrefs.put(CITY_URL_KEY, domainURL);
        cityPrefs.put(CITY_NAME_KEY, name);
        cityPrefs.put(CITY_CORP_NAME_KEY, name);
        cityPrefs.put(CITY_LOCAL_NAME_KEY, localName);
        cityPrefs.put(CITY_CODE_KEY, code);
        cityPrefs.put(CITY_LGD_CODE_KEY, lgdCode);
        cityPrefs.put(CITY_LAT_KEY, latitude);
        cityPrefs.put(CITY_LNG_KEY, longitude);
        cityPrefs.put(CITY_DIST_NAME_KEY, districtName);
        cityPrefs.put(CITY_DIST_CODE_KEY, districtCode);
        cityPrefs.put(CITY_DIST_LGD_CODE_KEY, districtLGDCode);
        cityPrefs.put(CITY_CORP_GRADE_KEY, grade);
        cityPrefs.put(CITY_REGION_NAME_KEY, regionName);
        if (preferences != null) {
            cityPrefs.put(CITY_LOGO_FS_UUID_KEY, preferences.logoExist() ? preferences.getMunicipalityLogo().getFileStoreId() : EMPTY);
            cityPrefs.put(CITY_CORP_NAME_KEY, preferences.getMunicipalityName());
            cityPrefs.put(CITY_CORP_ADDRESS_KEY, preferences.getMunicipalityAddress());
            cityPrefs.put(CITY_CORP_CALLCENTER_NO_KEY, preferences.getMunicipalityCallCenterNo());
            cityPrefs.put(CITY_CORP_CONTACT_NO_KEY, preferences.getMunicipalityContactNo());
            cityPrefs.put(CITY_CORP_EMAIL_KEY, preferences.getMunicipalityContactEmail());
            cityPrefs.put(CITY_CORP_TWITTER_KEY, preferences.getMunicipalityTwitterLink());
            cityPrefs.put(CITY_CORP_FB_KEY, preferences.getMunicipalityFacebookLink());
            cityPrefs.put(CITY_CORP_GOOGLE_MAP_KEY, preferences.getMunicipalityGisLocation());
            cityPrefs.put(CITY_CAPTCHA_PRIV_KEY, preferences.getRecaptchaPK());
            cityPrefs.put(CITY_CAPTCHA_PUB_KEY, preferences.getRecaptchaPub());
            cityPrefs.put(CITY_GOOGLE_API_KEY, preferences.getGoogleApiKey());
        }
        return cityPrefs;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof City))
            return false;
        City city = (City) other;
        return Objects.equals(getDomainURL(), city.getDomainURL()) &&
                Objects.equals(getCode(), city.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDomainURL(), getCode());
    }
}
