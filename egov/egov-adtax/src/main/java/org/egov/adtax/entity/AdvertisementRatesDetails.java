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

package org.egov.adtax.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.egov.infra.persistence.entity.AbstractPersistable;

@Entity
@Table(name = "EGADTAX_RATES_DETAILS")
@SequenceGenerator(name = AdvertisementRatesDetails.SEQ_RATESDETAILS, sequenceName = AdvertisementRatesDetails.SEQ_RATESDETAILS, allocationSize = 1)
public class AdvertisementRatesDetails extends AbstractPersistable<Long> {

    private static final long serialVersionUID = 8947498263482063238L;

    public static final String SEQ_RATESDETAILS = "SEQ_EGADTAX_RATESDETAILS";

    @Id
    @GeneratedValue(generator = SEQ_RATESDETAILS, strategy = GenerationType.SEQUENCE)
    private Long id;

    private Double unitFrom;

    private Double unitTo;

    @NotNull
    @ManyToOne
    // (fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "rate")
    private AdvertisementRate advertisementRate;

    private Double amount;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public Double getUnitFrom() {
        return unitFrom;
    }

    public void setUnitFrom(final Double unitFrom) {
        this.unitFrom = unitFrom;
    }

    public Double getUnitTo() {
        return unitTo;
    }

    public void setUnitTo(final Double unitTo) {
        this.unitTo = unitTo;
    }

    public AdvertisementRate getAdvertisementRate() {
        return advertisementRate;
    }

    public void setAdvertisementRate(final AdvertisementRate advertisementRate) {
        this.advertisementRate = advertisementRate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(final Double amount) {
        this.amount = amount;
    }

}