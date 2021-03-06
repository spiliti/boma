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

package org.egov.adtax.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.egov.adtax.entity.Advertisement;
import org.egov.adtax.entity.AdvertisementAdditionalTaxRate;
import org.egov.adtax.repository.AdvertisementRepository;
import org.egov.adtax.search.contract.HoardingDcbReport;
import org.egov.adtax.service.penalty.AdvertisementAdditionalTaxCalculator;
import org.egov.adtax.service.penalty.AdvertisementPenaltyCalculator;
import org.egov.adtax.utils.constants.AdvertisementTaxConstants;
import org.egov.collection.integration.services.CollectionIntegrationService;
import org.egov.commons.Installment;
import org.egov.dcb.bean.Receipt;
import org.egov.demand.model.EgDemand;
import org.egov.demand.model.EgDemandDetails;
import org.egov.demand.model.EgdmCollectedReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AdvertisementReportService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private AdvertisementPenaltyCalculator advtPenaltyCalculator;

    @Autowired
    protected CollectionIntegrationService collectionIntegrationService;
    @Autowired
    private AdvertisementAdditionalTaxCalculator advertisementAdditionalTaxCalculator;
    @Autowired
    private AdvertisementAdditinalTaxRateService advertisementAdditinalTaxRateService;

    public List<HoardingDcbReport> getHoardingWiseDCBResult(final Advertisement hoarding) {
        final List<HoardingDcbReport> hoardingDcbReportResults = new ArrayList<>();
        Map<Installment, BigDecimal> penaltyAmountMap;
        Map<Installment, BigDecimal> additionalTaxAmountMap;
        final Map<String, String> additionalTaxes = new HashMap<>();

        final List<AdvertisementAdditionalTaxRate> additionalTaxRates = advertisementAdditinalTaxRateService
                .getAllActiveAdditinalTaxRates();

        for (final AdvertisementAdditionalTaxRate taxRates : additionalTaxRates)
            additionalTaxes.put(taxRates.getTaxType(), taxRates.getReasonCode());

        if (hoarding != null && hoarding.getDemandId() != null) {
            penaltyAmountMap = advtPenaltyCalculator.getPenaltyByInstallment(hoarding.getActiveAdvertisementPermit());
            additionalTaxAmountMap = advertisementAdditionalTaxCalculator
                    .getAdditionalTaxesByInstallment(hoarding.getActiveAdvertisementPermit());
            final HashMap<String, HoardingDcbReport> hoardingwiseMap = new HashMap<>();
            HoardingDcbReport hoardingReport = new HoardingDcbReport();

            for (final EgDemandDetails demandDtl : hoarding.getDemandId().getEgDemandDetails())
                hoardingReport = prepareHoardingWiseMap(additionalTaxes, hoardingwiseMap, demandDtl);

            populateReceipts(hoarding, hoardingReport);

            populateHoardingWiseMap(penaltyAmountMap, hoardingwiseMap);

            populateAdditionalTaxMap(additionalTaxAmountMap, hoardingwiseMap);

            if (hoardingwiseMap.size() > 0)
                hoardingwiseMap.forEach((key, value) -> hoardingDcbReportResults.add(value));
        }
        return hoardingDcbReportResults;
    }

    private void populateReceipts(final Advertisement hoarding, HoardingDcbReport hoardingReport) {
        for (final EgDemandDetails demandDtl : hoarding.getDemandId().getEgDemandDetails())
            for (final EgdmCollectedReceipt collRecpt : demandDtl.getEgdmCollectedReceipts())
                if (!collRecpt.isCancelled()) {
                    Receipt receipt = new Receipt();
                    receipt.setReceiptNumber(collRecpt.getReceiptNumber());
                    receipt.setReceiptDate(collRecpt.getReceiptDate());
                    receipt.setReceiptAmt(collRecpt.getAmount());
                    hoardingReport.addReceipts(receipt);
                }
    }

    private void populateHoardingWiseMap(Map<Installment, BigDecimal> penaltyAmountMap,
            final HashMap<String, HoardingDcbReport> hoardingwiseMap) {
        for (final Map.Entry<Installment, BigDecimal> penaltyMap : penaltyAmountMap.entrySet())
            if (hoardingwiseMap.containsKey(penaltyMap.getKey().getDescription()))
                hoardingwiseMap.get(penaltyMap.getKey().getDescription()).setPenaltyAmount(hoardingwiseMap
                        .get(penaltyMap.getKey().getDescription()).getPenaltyAmount().add(penaltyMap.getValue()));
    }

    private void populateAdditionalTaxMap(Map<Installment, BigDecimal> additionalTaxAmountMap,
            final HashMap<String, HoardingDcbReport> hoardingwiseMap) {
        for (final Map.Entry<Installment, BigDecimal> additionataxmap : additionalTaxAmountMap.entrySet())
            if (hoardingwiseMap.containsKey(additionataxmap.getKey().getDescription()))
                hoardingwiseMap.get(additionataxmap.getKey().getDescription()).setAdditionalTaxAmount(hoardingwiseMap
                        .get(additionataxmap.getKey().getDescription()).getAdditionalTaxAmount()
                        .add(additionataxmap.getValue()));
    }

    private HoardingDcbReport prepareHoardingWiseMap(final Map<String, String> additionalTaxes,
            final HashMap<String, HoardingDcbReport> hoardingwiseMap, final EgDemandDetails demandDtl) {
        HoardingDcbReport hoardingReport;
        final HoardingDcbReport hoardingDcbReportObj = hoardingwiseMap
                .get(demandDtl.getEgDemandReason().getEgInstallmentMaster().getDescription());
        if (hoardingDcbReportObj == null) {
            hoardingReport = new HoardingDcbReport();
            hoardingReport.setInstallmentYearDescription(
                    demandDtl.getEgDemandReason().getEgInstallmentMaster().getDescription());
            setArrearAmount(demandDtl, hoardingReport);
            setDemandAmount(demandDtl, hoardingReport);
            setPenaltyAmount(demandDtl, hoardingReport);
            setAdditionalTaxAmount(additionalTaxes, demandDtl, hoardingReport);
            hoardingwiseMap.put(demandDtl.getEgDemandReason().getEgInstallmentMaster().getDescription(), hoardingReport);
        } else {
            hoardingReport = hoardingwiseMap.get(demandDtl.getEgDemandReason().getEgInstallmentMaster().getDescription());
            updateArrearAmount(demandDtl, hoardingReport);
            updateDemandAmount(demandDtl, hoardingReport);
            updatePenaltyAmount(demandDtl, hoardingReport);
            updateAdditionalTaxAmount(additionalTaxes, demandDtl, hoardingReport);
            hoardingwiseMap.put(demandDtl.getEgDemandReason().getEgInstallmentMaster().getDescription(), hoardingReport);
        }
        return hoardingReport;
    }

    private void updateAdditionalTaxAmount(final Map<String, String> additionalTaxes, final EgDemandDetails demandDtl,
            HoardingDcbReport hoardingReport) {
        if (!additionalTaxes.isEmpty() &&
                additionalTaxes.containsValue(demandDtl.getEgDemandReason().getEgDemandReasonMaster().getCode())) {
            hoardingReport.setAdditionalTaxAmount(getAdditionalTaxAmount(demandDtl, hoardingReport));
            hoardingReport.setCollectedAdditionalTaxAmount(getCollectedAdditionalTaxAmount(demandDtl, hoardingReport));
        }
    }

    private void updatePenaltyAmount(final EgDemandDetails demandDtl, HoardingDcbReport hoardingReport) {
        if (demandDtl.getEgDemandReason().getEgDemandReasonMaster().getCode()
                .equalsIgnoreCase(AdvertisementTaxConstants.DEMANDREASON_PENALTY)) {
            hoardingReport.setPenaltyAmount(getPenaltyAmount(demandDtl, hoardingReport));
            hoardingReport.setCollectedPenaltyAmount(getCollectedPenaltyAmount(demandDtl, hoardingReport));
        }
    }

    private void updateDemandAmount(final EgDemandDetails demandDtl, HoardingDcbReport hoardingReport) {
        if (demandDtl.getEgDemandReason().getEgDemandReasonMaster().getCode()
                .equalsIgnoreCase(AdvertisementTaxConstants.DEMANDREASON_ADVERTISEMENTTAX) ||
                demandDtl.getEgDemandReason().getEgDemandReasonMaster().getCode()
                        .equalsIgnoreCase(AdvertisementTaxConstants.DEMANDREASON_ENCROCHMENTFEE)) {
            hoardingReport.setDemandAmount(hoardingReport.getDemandAmount()
                    .add(demandDtl.getAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN)));
            hoardingReport.setCollectedDemandAmount(
                    hoardingReport.getCollectedDemandAmount()
                            .add(demandDtl.getAmtCollected().setScale(2, BigDecimal.ROUND_HALF_EVEN)));
        }
    }

    private void updateArrearAmount(final EgDemandDetails demandDtl, HoardingDcbReport hoardingReport) {
        if (demandDtl.getEgDemandReason().getEgDemandReasonMaster().getCode()
                .equalsIgnoreCase(AdvertisementTaxConstants.DEMANDREASON_ARREAR_ADVERTISEMENTTAX)) {
            hoardingReport.setArrearAmount(hoardingReport.getArrearAmount()
                    .add(demandDtl.getAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN)));
            hoardingReport.setCollectedArrearAmount(
                    hoardingReport.getCollectedArrearAmount()
                            .add(demandDtl.getAmtCollected().setScale(2, BigDecimal.ROUND_HALF_EVEN)));
        }
    }

    private void setAdditionalTaxAmount(final Map<String, String> additionalTaxes, final EgDemandDetails demandDtl,
            HoardingDcbReport hoardingReport) {
        if (!additionalTaxes.isEmpty() &&
                additionalTaxes.containsValue(demandDtl.getEgDemandReason().getEgDemandReasonMaster().getCode())) {
            hoardingReport.setAdditionalTaxAmount(demandDtl.getAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN));
            hoardingReport.setCollectedAdditionalTaxAmount(
                    demandDtl.getAmtCollected().setScale(2, BigDecimal.ROUND_HALF_EVEN));
        }
    }

    private void setPenaltyAmount(final EgDemandDetails demandDtl, HoardingDcbReport hoardingReport) {
        if (demandDtl.getEgDemandReason().getEgDemandReasonMaster().getCode()
                .equalsIgnoreCase(AdvertisementTaxConstants.DEMANDREASON_PENALTY)) {
            hoardingReport.setPenaltyAmount(demandDtl.getAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN));
            hoardingReport
                    .setCollectedPenaltyAmount(demandDtl.getAmtCollected().setScale(2, BigDecimal.ROUND_HALF_EVEN));
        }
    }

    private void setDemandAmount(final EgDemandDetails demandDtl, HoardingDcbReport hoardingReport) {
        if (demandDtl.getEgDemandReason().getEgDemandReasonMaster().getCode()
                .equalsIgnoreCase(AdvertisementTaxConstants.DEMANDREASON_ADVERTISEMENTTAX) ||
                demandDtl.getEgDemandReason().getEgDemandReasonMaster().getCode()
                        .equalsIgnoreCase(AdvertisementTaxConstants.DEMANDREASON_ENCROCHMENTFEE)) {
            hoardingReport.setDemandAmount(demandDtl.getAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN));
            hoardingReport
                    .setCollectedDemandAmount(demandDtl.getAmtCollected().setScale(2, BigDecimal.ROUND_HALF_EVEN));
        }
    }

    private void setArrearAmount(final EgDemandDetails demandDtl, HoardingDcbReport hoardingReport) {
        if (demandDtl.getEgDemandReason().getEgDemandReasonMaster().getCode()
                .equalsIgnoreCase(AdvertisementTaxConstants.DEMANDREASON_ARREAR_ADVERTISEMENTTAX)) {
            hoardingReport.setArrearAmount(demandDtl.getAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN));
            hoardingReport
                    .setCollectedArrearAmount(demandDtl.getAmtCollected().setScale(2, BigDecimal.ROUND_HALF_EVEN));
        }
    }

    private BigDecimal getPenaltyAmount(final EgDemandDetails demandDtl, HoardingDcbReport hoardingReport) {
        return hoardingReport.getPenaltyAmount() != null
                ? hoardingReport.getPenaltyAmount()
                        .add(demandDtl.getAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN))
                : demandDtl.getAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    private BigDecimal getCollectedPenaltyAmount(final EgDemandDetails demandDtl, HoardingDcbReport hoardingReport) {
        return hoardingReport.getCollectedPenaltyAmount() != null
                ? hoardingReport.getCollectedPenaltyAmount()
                        .add(demandDtl.getAmtCollected().setScale(2, BigDecimal.ROUND_HALF_EVEN))
                : demandDtl.getAmtCollected().setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    private BigDecimal getCollectedAdditionalTaxAmount(final EgDemandDetails demandDtl, HoardingDcbReport hoardingReport) {
        return hoardingReport.getCollectedAdditionalTaxAmount() != null
                ? hoardingReport.getCollectedAdditionalTaxAmount()
                        .add(demandDtl.getAmtCollected().setScale(2, BigDecimal.ROUND_HALF_EVEN))
                : demandDtl.getAmtCollected().setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    private BigDecimal getAdditionalTaxAmount(final EgDemandDetails demandDtl, HoardingDcbReport hoardingReport) {
        return hoardingReport.getAdditionalTaxAmount() != null
                ? hoardingReport.getAdditionalTaxAmount()
                        .add(demandDtl.getAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN))
                : demandDtl.getAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public Advertisement findByAdvertisementNumber(final String hoardingNumber) {
        return advertisementRepository.findByAdvertisementNumber(hoardingNumber);
    }

    public Advertisement findBy(final Long hoardingId) {
        return advertisementRepository.findOne(hoardingId);
    }

    public Advertisement getAdvertisementByDemand(final EgDemand demand) {
        return advertisementRepository.findByDemandId(demand);
    }

}
