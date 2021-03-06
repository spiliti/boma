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
package org.egov.works.abstractestimate.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.egov.infra.persistence.entity.AbstractAuditable;
import org.egov.infra.persistence.entity.component.Period;
import org.egov.infra.validation.exception.ValidationError;
import org.egov.works.models.masters.Overhead;
import org.egov.works.models.masters.OverheadRate;
import org.joda.time.LocalDate;

@Entity
@Table(name = "EGW_ESTIMATE_OVERHEADS")
@SequenceGenerator(name = OverheadValue.SEQ_EGW_ESTIMATEOVERHEADS, sequenceName = OverheadValue.SEQ_EGW_ESTIMATEOVERHEADS, allocationSize = 1)
public class OverheadValue extends AbstractAuditable {

    private static final long serialVersionUID = 5585187999492385271L;

    public static final String SEQ_EGW_ESTIMATEOVERHEADS = "SEQ_EGW_ESTIMATE_OVERHEADS";

    @Id
    @GeneratedValue(generator = SEQ_EGW_ESTIMATEOVERHEADS, strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "overhead")
    private Overhead overhead;

    @NotNull
    private double amount;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "abstractEstimate")
    private AbstractEstimate abstractEstimate;

    public OverheadValue() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public AbstractEstimate getAbstractEstimate() {
        return abstractEstimate;
    }

    public void setAbstractEstimate(final AbstractEstimate abstractEstimate) {
        this.abstractEstimate = abstractEstimate;
    }

    public Overhead getOverhead() {
        return overhead;
    }

    public void setOverhead(final Overhead overhead) {
        this.overhead = overhead;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(final double amount) {
        this.amount = amount;
    }

    public OverheadRate getOverheadRateOn(final Date estimateDate) {

        for (final OverheadRate overheadRate : overhead.getOverheadRates())
            if (overheadRate != null && isWithin(overheadRate.getValidity(), estimateDate))
                return overheadRate;

        return null;
    }

    public boolean isWithin(final Period period, final Date dateTime) {
        final LocalDate start = new LocalDate(period.getStartDate());
        final LocalDate end = new LocalDate(period.getEndDate());
        final LocalDate date = new LocalDate(dateTime);
        if (period.getEndDate() == null)
            return start.compareTo(date) <= 0;
        else
            return start.compareTo(date) <= 0 && end.compareTo(date) >= 0;
    }

    public List<ValidationError> validate() {
        return new ArrayList<>();
    }

}
