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

package org.egov.pgr.entity;

import org.egov.infra.persistence.entity.AbstractAuditable;
import org.egov.infra.persistence.validator.annotation.Unique;
import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static org.egov.infra.validation.constants.ValidationErrorCode.INVALID_ALPHANUMERIC_UNDERSCORE_HYPHEN_SPACE;
import static org.egov.infra.validation.constants.ValidationRegex.ALPHANUMERIC_UNDERSCORE_HYPHEN_SPACE;
import static org.egov.pgr.entity.Configuration.SEQ_CONFIGURATION;

@Table(name = "egpgr_configuration")
@Entity
@Unique(fields = "key", enableDfltMsg = true)
@SequenceGenerator(name = SEQ_CONFIGURATION, sequenceName = SEQ_CONFIGURATION, allocationSize = 1)
@Immutable
public class Configuration extends AbstractAuditable {

    protected static final String SEQ_CONFIGURATION = "SEQ_EGPGR_CONFIGURATION";
    private static final long serialVersionUID = 8904645810221559541L;

    @Id
    @GeneratedValue(generator = SEQ_CONFIGURATION, strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    @SafeHtml
    @Length(max = 50)
    @Pattern(regexp = ALPHANUMERIC_UNDERSCORE_HYPHEN_SPACE, message = INVALID_ALPHANUMERIC_UNDERSCORE_HYPHEN_SPACE)
    private String key;

    @NotBlank
    @SafeHtml
    @Length(max = 100)
    private String value;

    @SafeHtml
    @Length(max = 200)
    private String description;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    protected void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
