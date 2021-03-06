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
package org.egov.works.services.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;
import org.egov.commons.Accountdetailtype;
import org.egov.commons.CChartOfAccounts;
import org.egov.commons.Fund;
import org.egov.egf.commons.EgovCommon;
import org.egov.infra.exception.ApplicationRuntimeException;
import org.egov.infstr.services.PersistenceService;
import org.egov.works.abstractestimate.entity.AbstractEstimate;
import org.egov.works.abstractestimate.entity.FinancialDetail;
import org.egov.works.models.estimate.BudgetFolioDetail;
import org.egov.works.models.estimate.DepositWorksUsage;
import org.egov.works.services.ContractorBillService;
import org.egov.works.services.DepositWorksUsageService;

@SuppressWarnings("deprecation")
public class DepositWorksUsageServiceImpl extends BaseServiceImpl<DepositWorksUsage, Long> implements
        DepositWorksUsageService {
    private static final Logger LOGGER = Logger.getLogger(DepositWorksUsageServiceImpl.class);
    private EgovCommon egovCommon;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    public static final String dateFormat = "dd-MMM-yyyy";
    private ContractorBillService contractorBillService;

    @PersistenceContext
    private EntityManager entityManager;

    public DepositWorksUsageServiceImpl(final PersistenceService<DepositWorksUsage, Long> persistenceService) {
        super(persistenceService);

    }

    @Override
    public BigDecimal getTotalDepositWorksAmount(final Fund fund, final CChartOfAccounts coa,
            final Accountdetailtype accountdetailtype, final Long depositCode, final Date appropriationDate) {

        return egovCommon.getDepositAmountForDepositCode(appropriationDate, coa.getGlcode(), fund.getCode(),
                accountdetailtype.getId(), depositCode.intValue());
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Map<String, List> getDepositFolioDetails(final AbstractEstimate abstractEstimate, final Fund fund,
            final CChartOfAccounts coa, final Accountdetailtype accountdetailtype, final Long depositCode,
            final Date appropriationDate) {
        final List<BudgetFolioDetail> approvedBudgetFolioResultList = new ArrayList<>();

        // Getting deposit works usage list across the financial year
        final List<DepositWorksUsage> depositWorksUsageList = entityManager
                .createQuery(new StringBuffer("from DepositWorksUsage dwu")
                        .append(" where trunc(dwu.appropriationDate) <= trunc(:appropriationDate) and dwu.depositCode.fund.id = :fundId")
                        .append(" and dwu.depositCode.id = :depositCode and dwu.coa.id = coaId order by dwu.id asc").toString(),
                        DepositWorksUsage.class)
                .setParameter("appropriationDate", appropriationDate, TemporalType.DATE)
                .setParameter("fundId", fund.getId())
                .setParameter("depositCode", depositCode)
                .getResultList();

        if (depositWorksUsageList != null && !depositWorksUsageList.isEmpty())
            return addApprovedEstimateResultList(approvedBudgetFolioResultList, depositWorksUsageList,
                    appropriationDate);
        return new HashMap<>();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map<String, List> addApprovedEstimateResultList(final List<BudgetFolioDetail> depositFolioResultList,
            final List<DepositWorksUsage> depositWorksUsageList, final Date appropriationDate) {
        int srlNo = 1;
        Double cumulativeTotal = 0.00D;
        BigDecimal totalDeposit = BigDecimal.ZERO;
        double cumulativeExpensesIncurred = 0.0;
        final Map<String, List> budgetFolioMap = new HashMap<>();
        for (final DepositWorksUsage depositWorksUsage : depositWorksUsageList) {
            final BudgetFolioDetail budgetFolioDetail = new BudgetFolioDetail();
            budgetFolioDetail.setSrlNo(srlNo++);

            if (depositWorksUsage.getAbstractEstimate() != null) {
                budgetFolioDetail.setEstimateNo(depositWorksUsage.getAbstractEstimate().getEstimateNumber());
                budgetFolioDetail.setNameOfWork(depositWorksUsage.getAbstractEstimate().getName());
                budgetFolioDetail
                        .setEstimateDate(sdf.format(depositWorksUsage.getAbstractEstimate().getEstimateDate()));
                if (!isAppropriationRejected(depositWorksUsage.getAppropriationNumber())) {
                    budgetFolioDetail.setExpensesIncurred(contractorBillService.getTotalActualExpenseForProject(
                            depositWorksUsage.getAbstractEstimate(), appropriationDate));
                    budgetFolioDetail.setCumulativeExpensesIncurred(cumulativeExpensesIncurred);
                    budgetFolioDetail.setActualBalanceAvailable(depositWorksUsage.getTotalDepositAmount().doubleValue()
                            - cumulativeExpensesIncurred);
                    cumulativeExpensesIncurred = cumulativeExpensesIncurred
                            + budgetFolioDetail.getExpensesIncurred().doubleValue();
                } else {
                    budgetFolioDetail.setExpensesIncurred(0.0);
                    budgetFolioDetail.setCumulativeExpensesIncurred(cumulativeExpensesIncurred);
                    budgetFolioDetail.setActualBalanceAvailable(depositWorksUsage.getTotalDepositAmount().doubleValue()
                            - cumulativeExpensesIncurred);
                    cumulativeExpensesIncurred = cumulativeExpensesIncurred
                            + budgetFolioDetail.getExpensesIncurred().doubleValue();
                }
            }

            budgetFolioDetail.setBudgetApprNo(depositWorksUsage.getAppropriationNumber());
            budgetFolioDetail.setCumulativeTotal(cumulativeTotal);
            budgetFolioDetail.setAppDate(sdf.format(depositWorksUsage.getAppropriationDate()));
            if (depositWorksUsage.getReleasedAmount().compareTo(BigDecimal.ZERO) > 0) {
                cumulativeTotal = cumulativeTotal - depositWorksUsage.getReleasedAmount().doubleValue();
                budgetFolioDetail
                        .setWorkValue(depositWorksUsage.getAbstractEstimate().getTotalAmount().getValue() * -1);// to
                // display
                // released
                // amount
                // as
                // negative
                budgetFolioDetail.setAppropriatedValue(BigDecimal.ZERO.subtract(depositWorksUsage.getReleasedAmount())
                        .doubleValue());
            } else {
                cumulativeTotal = cumulativeTotal + depositWorksUsage.getConsumedAmount().doubleValue();
                budgetFolioDetail.setWorkValue(depositWorksUsage.getAbstractEstimate().getTotalAmount().getValue());
                budgetFolioDetail.setAppropriatedValue(depositWorksUsage.getConsumedAmount().doubleValue());
            }
            totalDeposit = depositWorksUsage.getTotalDepositAmount();

            budgetFolioDetail.setBalanceAvailable(totalDeposit);

            depositFolioResultList.add(budgetFolioDetail);
        }
        final List calculatedValuesList = new ArrayList();
        calculatedValuesList.add(cumulativeTotal);
        budgetFolioMap.put("depositFolioList", depositFolioResultList);
        budgetFolioMap.put("calculatedValues", calculatedValuesList);
        budgetFolioMap.put("totalCumulativeExpensesIncurred", Arrays.asList(cumulativeExpensesIncurred));
        return budgetFolioMap;
    }

    @Override
    public BigDecimal getTotalUtilizedAmountForDepositWorks(final FinancialDetail financialDetail) {
        return (BigDecimal) genericService.findByNamedQuery("getDepositWorksUsageAmount", financialDetail
                .getAbstractEstimate().getDepositCode().getId(), financialDetail.getFund().getId(),
                financialDetail
                        .getCoa().getId());
    }

    @Override
    public BigDecimal getTotalUtilizedAmountForDepositWorks(final FinancialDetail financialDetail, final Date appDate) {
        BigDecimal totalUtilizedAmount = BigDecimal.ZERO;
        // NOTE: utilizedAmountForRunningProject holds sum of all appropriation
        // amount for estimates which are not closed
        BigDecimal utilizedAmountForRunningProject = entityManager
                .createQuery(new StringBuffer("select sum(dwu.consumedAmount-dwu.releasedAmount)")
                        .append(" from DepositWorksUsage dwu")
                        .append(" where dwu.createdDate <= :createdDate and EXISTS (select 'true' from FinancialDetail fd")
                        .append(" where fd.abstractEstimate.id=dwu.abstractEstimate.id and fd.fund.id = :fundId")
                        .append(" and fd.abstractEstimate.depositCode.id = :depositCode and fd.coa.id = :coaId) ")
                        .append(" and (dwu.abstractEstimate.projectCode.id is null or dwu.abstractEstimate.projectCode.id not in")
                        .append(" (select proj.id from ProjectCode proj where proj.egwStatus.code='CLOSED'))").toString(),
                        BigDecimal.class)
                .setParameter("createdDate", appDate, TemporalType.DATE)
                .setParameter("fundId", financialDetail.getFund().getId())
                .setParameter("depositCode", financialDetail.getAbstractEstimate().getDepositCode().getId())
                .setParameter("coaId", financialDetail.getCoa().getId())
                .getSingleResult();
        if (utilizedAmountForRunningProject == null)
            utilizedAmountForRunningProject = BigDecimal.ZERO;
        LOGGER.debug("Total Utilized amount for deposit works (Running projects) >>>>Depositcodeid="
                + financialDetail.getAbstractEstimate().getDepositCode().getId() + "|| till date=" + appDate
                + "||utilizedAmount=" + utilizedAmountForRunningProject);
        // NOTE: utilizedAmountForClosedProject holds sum of all appropriation
        // amount for estimates which are closed
        Double utilizedAmountForClosedProject = entityManager.createQuery(
                new StringBuffer("select sum(fd.abstractEstimate.projectCode.projectValue)")
                        .append(" from FinancialDetail fd")
                        .append(" where trunc(fd.abstractEstimate.projectCode.completionDate) <= trunc(:completionDate)")
                        .append(" and fd.fund.id =:fundId and fd.abstractEstimate.depositCode.id = :depositCode and fd.coa.id = :coaId")
                        .append(" and fd.abstractEstimate.projectCode.egwStatus.code = 'CLOSED'").toString(),
                Double.class)
                .setParameter("completionDate", appDate, TemporalType.DATE)
                .setParameter("fundId", financialDetail.getFund().getId())
                .setParameter("depositCode", financialDetail.getAbstractEstimate().getDepositCode().getId())
                .setParameter("coaId", financialDetail.getCoa().getId())
                .getSingleResult();

        if (utilizedAmountForClosedProject == null)
            utilizedAmountForClosedProject = 0.0;
        totalUtilizedAmount = utilizedAmountForRunningProject.add(new BigDecimal(utilizedAmountForClosedProject
                .doubleValue()));
        LOGGER.debug("Total Utilized amount for deposit works (Closed projects) >>>>Depositcodeid="
                + financialDetail.getAbstractEstimate().getDepositCode().getId() + "|| till date=" + appDate
                + "||utilizedAmount=" + utilizedAmountForClosedProject);
        LOGGER.debug("Total Utilized amount for deposit works (including Closed and running projects) >>>>Depositcodeid="
                + financialDetail.getAbstractEstimate().getDepositCode().getId()
                + "|| till date="
                + appDate
                + "||totalutilizedAmount=" + totalUtilizedAmount);
        return totalUtilizedAmount;
    }

    public void setEgovCommon(final EgovCommon egovCommon) {
        this.egovCommon = egovCommon;
    }

    @Override
    public DepositWorksUsage getDepositWorksUsage(final AbstractEstimate estimate, final String appropriationNumber) {
        final List<DepositWorksUsage> results = entityManager.createQuery(
                "from DepositWorksUsage dwu where dwu.abstractEstimate = :estimate and dwu.appropriationNumber = :appropriationNumber",
                DepositWorksUsage.class)
                .setParameter("estimate", estimate)
                .setParameter("appropriationNumber", appropriationNumber)
                .getResultList();

        return results.isEmpty() ? null : results.get(0);
    }

    public void setContractorBillService(final ContractorBillService contractorBillService) {
        this.contractorBillService = contractorBillService;
    }

    private boolean isAppropriationRejected(final String apprNumber) {

        if (apprNumber == null)
            throw new ApplicationRuntimeException("Invalid parameter passed to isAppropriationRejected() ||apprNumber="
                    + apprNumber);
        final String[] str = apprNumber.split("/");
        if (str.length > 0 && "BC".equalsIgnoreCase(str[0]))
            return true;

        final String rejectedApprNumber = "BC/" + apprNumber;

        final List<DepositWorksUsage> results = entityManager.createQuery(
                "from DepositWorksUsage dwu where dwu.appropriationNumber = :appropriationNumber", DepositWorksUsage.class)
                .setParameter("appropriationNumber", rejectedApprNumber)
                .getResultList();

        final DepositWorksUsage depositWorksUsage = results.isEmpty() ? null : results.get(0);

        if (depositWorksUsage != null)
            return true;

        return false;
    }
}
