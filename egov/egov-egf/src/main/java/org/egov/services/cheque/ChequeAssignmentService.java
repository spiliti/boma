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
package org.egov.services.cheque;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.egov.commons.*;
import org.egov.commons.dao.ChartOfAccountsDAO;
import org.egov.commons.dao.EgwStatusHibernateDAO;
import org.egov.commons.utils.EntityType;
import org.egov.infra.admin.master.entity.AppConfigValues;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.egov.infra.exception.ApplicationException;
import org.egov.infra.exception.ApplicationRuntimeException;
import org.egov.infstr.services.PersistenceService;
import org.egov.model.payment.ChequeAssignment;
import org.egov.model.payment.Paymentheader;
import org.egov.utils.Constants;
import org.egov.utils.FinancialConstants;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.*;
import org.hibernate.type.ObjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ChequeAssignmentService extends PersistenceService<Paymentheader, Long> {

    private static final Logger LOGGER = Logger.getLogger(ChequeAssignmentService.class);
    private static final String DELIMETER = "~";
    public final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Constants.LOCALE);
    public SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Constants.LOCALE);
    public List<CChartOfAccounts> purchaseBillGlcodeList = new ArrayList<CChartOfAccounts>();
    public List<CChartOfAccounts> worksBillGlcodeList = new ArrayList<CChartOfAccounts>();
    public List<CChartOfAccounts> salaryBillGlcodeList = new ArrayList<CChartOfAccounts>();
    public List<CChartOfAccounts> contingentBillGlcodeList = new ArrayList<CChartOfAccounts>();
    @Autowired
    private AppConfigValueService appConfigValuesService;
    @Autowired
    @Qualifier("persistenceService")
    private PersistenceService persistenceService;
    private Query query;
    private List<ChequeAssignment> finalCBillChequeAssignmentList;
    private List<ChequeAssignment> tempExpenseChequeAssignmentList;
    private List<ChequeAssignment> finalChequeAssignmentList;
    private String approvedstatus = "";
    private List<Integer> statusId = new ArrayList<Integer>();
    private List<BigDecimal> cBillGlcodeIdList = null;
    private String instrumentReconciledStatus = "";
    private String instrumentNewStatus = "";
    private Map<String, Map<String, Object>> filterConditions;
    @Autowired
    private ChartOfAccountsDAO coaDAO;

    @Autowired
    private EgwStatusHibernateDAO egwStatusDAO;

    public ChequeAssignmentService() {
        super(Paymentheader.class);
    }

    public ChequeAssignmentService(final Class<Paymentheader> type) {
        super(type);
    }

    @SuppressWarnings("unchecked")
    public void setPersistenceService(final PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    // *************IMPORTANT - CALL THIS METHOD BEFORE CALLING ANYTHING ELSE**********************************************
    public void setStatusAndFilterValues(final Map<String, String[]> parameters, final CVoucherHeader voucherHeader)
            throws ParseException {
        filterConditions = getFilterParamaters(parameters, voucherHeader);
        setStatusValues();
    }

    // This method returns the Direct Bank Payments and Bill Payments for Expense, Contractor and Supplier bills for mode Cheque
    public List<ChequeAssignment> getPaymentVoucherNotInInstrument(final Map<String, String[]> parameters)
            throws ApplicationException,
            ParseException {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Starting getPaymentVoucherNotInInstrument...");
        finalChequeAssignmentList.addAll(getExpenseBillPayments());
        finalChequeAssignmentList.addAll(getDirectBankPaymentsForChequeAssignment());
        finalChequeAssignmentList.addAll(getContractorSupplierPaymentsForChequeAssignment(parameters));
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Completed getPaymentVoucherNotInInstrument.");
        return finalChequeAssignmentList;
    }

    // This method returns the Bill Payments for Expense for mode Cheque
    public List<ChequeAssignment> getExpenseBillPayments() throws ParseException, NumberFormatException, ApplicationException {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Starting getExpenseBillPayments...");
        getExpenseBillPaymentsHavingNoCheques();
        getExpenseBillPaymentsWithNoSurrenderedCheque();
        getExpenseBillPaymentsWithSurrenderedCheques();
        if (tempExpenseChequeAssignmentList != null && tempExpenseChequeAssignmentList.size() != 0)
            prepareChequeList();
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Completed getExpenseBillPayments.");
        return finalCBillChequeAssignmentList;
    }

    // This method returns the consolidated mode payments that are not for salary or remittance
    @SuppressWarnings("unchecked")
    public List<ChequeAssignment> getPaymentVouchersConsolidatedMode(final Map<String, String[]> parameters,
                                                                     final CVoucherHeader voucherHeader) throws ParseException {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Starting getPaymentVouchersConsolidatedMode...");
        filterConditions = getFilterParamaters(parameters, voucherHeader);
        final Map.Entry<String, Map<String, Object>> filterEntry = filterConditions.entrySet().iterator().next();
        final String filterQuery = filterEntry.getKey();
        final Map<String, Object> filterParams = filterEntry.getValue();
        setStatusValues();

        query = getSession()
                .createNativeQuery(new StringBuilder("select vh.id as voucherid, vh.voucherNumber as voucherNumber, vh.voucherDate as voucherDate,sum(misbill.paidamount) as paidAmount,")
                        .append(" current_date as chequeDate")
                        .append(" from Paymentheader ph,voucherheader vh,vouchermis vmis, Miscbilldetail misbill ")
                        .append(" where ph.voucherheaderid=misbill.payvhid and ph.voucherheaderid=vh.id and vmis.voucherheaderid= vh.id and vh.status = :voucherStatus")
                        .append(" ").append(filterQuery).append(" ")
                        .append(" and vh.id not in (select voucherHeaderId from egf_InstrumentVoucher iv, EGF_INSTRUMENTHEADER ih where iv.INSTRUMENTHEADERID = ih.id")
                        .append(" and ih.ID_STATUS in (:ihStatus)) ) and vh.type = :vhType and vh.name NOT IN (:vhNames) ")
                        .append(" group by vh.id,vh.voucherNumber,vh.voucherDate order by vh.voucherNumber ").toString())
                .addScalar("voucherid")
                .addScalar("voucherNumber")
                .addScalar("voucherDate")
                .addScalar("paidAmount")
                .addScalar("chequeDate")
                .setResultTransformer(Transformers.aliasToBean(ChequeAssignment.class));
        query.setParameter("voucherStatus", Long.valueOf(approvedstatus), LongType.INSTANCE)
                .setParameterList("ihStatus",statusId, IntegerType.INSTANCE)
                .setParameter("vhType", FinancialConstants.STANDARD_VOUCHER_TYPE_PAYMENT, StringType.INSTANCE)
                .setParameterList("vhNames", Arrays.asList(FinancialConstants.PAYMENTVOUCHER_NAME_REMITTANCE, FinancialConstants.PAYMENTVOUCHER_NAME_SALARY));
        filterParams.entrySet().forEach(entry -> query.setParameter(entry.getKey(), entry.getValue()));
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Completed getPaymentVouchersConsolidatedMode.");
        return query.list();

    }

    @SuppressWarnings("unchecked")
    public List<ChequeAssignment> getContractorSupplierPaymentsForChequeAssignment(final Map<String, String[]> parameters)
            throws ParseException {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Starting getContractorSupplierPaymentsForChequeAssignment...");

        final Bankaccount ba = (Bankaccount) persistenceService.find(" from Bankaccount where id = ?1",
                Long.valueOf(parameters.get("bankaccount")[0]));

        final Map.Entry<String, Map<String, Object>> filterEntry = filterConditions.entrySet().iterator().next();
        final String filterQuery = filterEntry.getKey();
        final Map<String, Object> filterParams = filterEntry.getValue();

        String billCondition = "";
        if (null != parameters.get("voucherName") && null != parameters.get("voucherName")[0] &&
                FinancialConstants.PAYMENTVOUCHER_NAME_PENSION.equalsIgnoreCase(parameters.get("voucherName")[0]))
            billCondition = " in (:expenditureType) ";
        else
            billCondition = " not in (:expenditureType) ";
        final StringBuilder supplierBillPaymentQuery = new StringBuilder("select vh.id as voucherid ,vh.voucherNumber as voucherNumber ,vh.voucherDate as voucherDate ,0 as detailtypeid,")
                .append(" 0 as detailkeyid ,misbill.paidto as paidTo,sum(misbill.paidamount) as paidAmount,current_date as chequeDate")
                .append(" from Paymentheader ph,voucherheader vh,vouchermis vmis, Miscbilldetail misbill ,voucherheader billvh, eg_billregister br, eg_billregistermis billmis,")
                .append(" generalledger gl, egf_instrumentvoucher iv right outer join voucherheader pvh on (pvh.id=iv.VOUCHERHEADERID) where ph.voucherheaderid=misbill.payvhid")
                .append(" and ph.voucherheaderid=vh.id and vh.name='Bill Payment' and  vmis.voucherheaderid= vh.id and vh.status = :vhStatus")
                .append(" ").append(filterQuery).append(" ")
                .append(" and gl.voucherheaderid =vh.id  and gl.creditamount>0 and gl.glcodeid in (:glcodeId) and br.id=billmis.billid and billmis.voucherheaderid=billvh.id")
                .append(" and br.expendituretype ").append(billCondition).append(" and misbill.billvhid=billvh.id and pvh.id=vh.id and iv.id IS NULL")
                .append(" group by vh.id,vh.voucherNumber,vh.voucherDate,misbill.paidto ")
                .append(" union select vh.id as voucherid ,vh.voucherNumber as voucherNumber ,vh.voucherDate as voucherDate ,0 as detailtypeid ,0 as detailkeyid,")
                .append(" misbill.paidto as paidTo,sum(misbill.paidamount) as paidAmount,current_date as chequeDate ")
                .append(" from Paymentheader ph,voucherheader vh,vouchermis vmis, Miscbilldetail misbill ,voucherheader billvh, eg_billregister br, eg_billregistermis billmis,")
                .append(" generalledger gl, egf_instrumentvoucher iv right outer join voucherheader pvh on (pvh.id=iv.VOUCHERHEADERID)")
                .append(" left outer join egf_instrumentheader ih on (ih.ID=iv.INSTRUMENTHEADERID) where ph.voucherheaderid=misbill.payvhid and ph.voucherheaderid=vh.id")
                .append(" and vh.name='Bill Payment' and  vmis.voucherheaderid= vh.id and vh.status = :vhStatus").append(" ").append(filterQuery).append(" ")
                .append(" and gl.voucherheaderid =vh.id  and gl.creditamount>0 and gl.glcodeid in (:glcodeId) and br.id=billmis.billid and billmis.voucherheaderid=billvh.id")
                .append(" and br.expendituretype  ").append(billCondition).append(" and misbill.billvhid=billvh.id and pvh.id=vh.id and ih.id IN (SELECT MAX(ih.id)")
                .append(" FROM egf_instrumentvoucher iv RIGHT OUTER JOIN voucherheader pvh ON (pvh.id=iv.VOUCHERHEADERID) LEFT OUTER JOIN egf_instrumentheader ih")
                .append(" ON (ih.ID =iv.INSTRUMENTHEADERID) WHERE pvh.id =vh.id AND ih.payto =misbill.paidto) AND ih.ID_STATUS NOT IN (:statusId)")
                .append(" group by vh.id,vh.voucherNumber,vh.voucherDate,misbill.paidto  ")
                .append(" union select vh.id as voucherid ,vh.voucherNumber as voucherNumber ,vh.voucherDate as voucherDate ,0 as detailtypeid ,0 as detailkeyid,")
                .append(" misbill.paidto as paidTo,sum(misbill.paidamount) as paidAmount,current_date as chequeDate ")
                .append(" from Paymentheader ph,voucherheader vh,vouchermis vmis, Miscbilldetail misbill ,voucherheader billvh, eg_billregister br,")
                .append(" eg_billregistermis billmis, generalledger gl ")
                .append(" where ph.voucherheaderid=misbill.payvhid and ph.voucherheaderid=vh.id and vh.name='Bill Payment' and  vmis.voucherheaderid= vh.id and vh.status = :vhStatus")
                .append(" ").append(filterQuery).append(" ").append(" and gl.voucherheaderid =vh.id  and gl.creditamount>0 and gl.glcodeid in (:glcodeId) and br.id=billmis.billid")
                .append(" and billmis.voucherheaderid=billvh.id and br.expendituretype  ").append(billCondition).append(" and misbill.billvhid=billvh.id")
                .append(" and misbill.paidto NOT IN (SELECT DISTINCT(ih.payto) FROM egf_instrumentvoucher iv  RIGHT OUTER JOIN voucherheader pvh")
                .append(" ON (pvh.id=iv.VOUCHERHEADERID)  LEFT OUTER JOIN egf_instrumentheader ih  ON (ih.ID=iv.INSTRUMENTHEADERID)  WHERE pvh.id=vh.id AND ih.ID_STATUS IN (:statusId)) ")
                .append(" group by vh.id,vh.voucherNumber,vh.voucherDate,misbill.paidto order by paidto,voucherNumber ");
        query = getSession().createNativeQuery(supplierBillPaymentQuery.toString())
                .addScalar("voucherid", LongType.INSTANCE).addScalar("voucherNumber").addScalar("voucherDate")
                .addScalar("detailtypeid", LongType.INSTANCE)
                .addScalar("detailkeyid", LongType.INSTANCE).addScalar("paidTo")
                .addScalar("paidAmount", BigDecimalType.INSTANCE).addScalar("chequeDate")
                .setResultTransformer(Transformers.aliasToBean(ChequeAssignment.class));
        query.setParameter("vhStatus", Long.valueOf(approvedstatus), LongType.INSTANCE)
                .setParameter("glcodeId", ba.getChartofaccounts().getId(), LongType.INSTANCE)
                .setParameterList("statusId", statusId, IntegerType.INSTANCE);
        if (null != parameters.get("voucherName") && null != parameters.get("voucherName")[0] &&
                FinancialConstants.PAYMENTVOUCHER_NAME_PENSION.equalsIgnoreCase(parameters.get("voucherName")[0]))
            query.setParameter("expenditureType", FinancialConstants.STANDARD_EXPENDITURETYPE_PENSION);
        else
            query.setParameterList("expenditureType", Arrays.asList(FinancialConstants.STANDARD_EXPENDITURETYPE_CONTINGENT, FinancialConstants.STANDARD_EXPENDITURETYPE_PENSION));
        filterParams.entrySet().forEach(entry -> query.setParameter(entry.getKey(), entry.getValue()));
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("CONTRACTOR/SUPLLIER BILL PAYMENT QUERY - " + supplierBillPaymentQuery);
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Completed getContractorSupplierPaymentsForChequeAssignment.");
        return query.list();
    }

    @SuppressWarnings("unchecked")
    public List<ChequeAssignment> getDirectBankPaymentsForChequeAssignment() throws ParseException {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Starting getDirectBankPaymentsForChequeAssignment...");
        final Map.Entry<String, Map<String, Object>> filterEntry = filterConditions.entrySet().iterator().next();
        final String filterQuery = filterEntry.getKey();
        final Map<String, Object> filterParams = filterEntry.getValue();

        final StringBuilder bankPaymentQuery = new StringBuilder("select vh.id as voucherid ,vh.voucherNumber as voucherNumber ,0 as detailtypeid ,0 as detailkeyid,")
                .append(" vh.voucherDate as voucherDate  ,misbill.paidto as paidTo,sum(misbill.paidamount) as paidAmount,current_date as chequeDate")
                .append(" From Paymentheader ph,voucherheader vh,vouchermis vmis, Miscbilldetail misbill, egf_instrumentvoucher iv right outer join voucherheader pvh")
                .append(" on (pvh.id=iv.VOUCHERHEADERID) Where ph.voucherheaderid=misbill.payvhid and ph.voucherheaderid=vh.id and vh.name in ('Direct Bank Payment','Advance Payment')")
                .append(" and vmis.voucherheaderid= vh.id and vh.status = :vhStatus").append(" ").append(filterQuery).append(" ")
                .append(" and pvh.id=vh.id and  iv.id IS NULL  group by vh.id,vh.voucherNumber,vh.voucherDate,misbill.paidto ")
                .append(" union select vh.id as voucherid ,vh.voucherNumber as voucherNumber ,0 as detailtypeid ,0 as detailkeyid,vh.voucherDate as voucherDate,")
                .append(" misbill.paidto as paidTo,sum(misbill.paidamount) as paidAmount,current_date as chequeDate")
                .append(" From Paymentheader ph,voucherheader vh,vouchermis vmis, Miscbilldetail misbill, egf_instrumentvoucher iv right outer join voucherheader pvh")
                .append(" on (pvh.id=iv.VOUCHERHEADERID) left outer join egf_instrumentheader ih on (ih.ID=iv.INSTRUMENTHEADERID)")
                .append(" Where ph.voucherheaderid=misbill.payvhid and ph.voucherheaderid=vh.id and vh.name in ('Direct Bank Payment','Advance Payment') and vmis.voucherheaderid= vh.id")
                .append(" and vh.status = :vhStatus").append(" ").append(filterQuery).append(" ")
                .append(" and pvh.id=vh.id and ih.id IN (SELECT MAX(ih.id) FROM egf_instrumentvoucher iv RIGHT OUTER JOIN voucherheader pvh ON (pvh.id=iv.VOUCHERHEADERID)")
                .append(" LEFT OUTER JOIN egf_instrumentheader ih ON (ih.ID =iv.INSTRUMENTHEADERID) WHERE pvh.id =vh.id AND ih.payto =misbill.paidto) AND ih.ID_STATUS NOT IN (:statusId)")
                .append(" group by vh.id,vh.voucherNumber,vh.voucherDate,misbill.paidto  ")
                .append(" union select vh.id as voucherid ,vh.voucherNumber as voucherNumber ,0 as detailtypeid ,0 as detailkeyid,vh.voucherDate as voucherDate  ,misbill.paidto as paidTo,")
                .append(" sum(misbill.paidamount) as paidAmount,current_date as chequeDate ")
                .append(" From Paymentheader ph,voucherheader vh,vouchermis vmis, Miscbilldetail misbill ")
                .append(" Where ph.voucherheaderid=misbill.payvhid and ph.voucherheaderid=vh.id and vh.name in ('Direct Bank Payment','Advance Payment') and vmis.voucherheaderid= vh.id")
                .append(" and vh.status = :vhStatus").append(" ").append(filterQuery).append(" ")
                .append(" and misbill.paidto NOT IN (SELECT DISTINCT(ih.payto) FROM egf_instrumentvoucher iv  RIGHT OUTER JOIN voucherheader pvh  ")
                .append(" ON (pvh.id=iv.VOUCHERHEADERID)  LEFT OUTER JOIN egf_instrumentheader ih  ON (ih.ID=iv.INSTRUMENTHEADERID)  WHERE pvh.id=vh.id AND ih.ID_STATUS IN (:statusId))")
                .append("  group by vh.id,vh.voucherNumber,vh.voucherDate,misbill.paidto order by paidto,voucherNumber ");
        query = getSession().createNativeQuery(bankPaymentQuery.toString())
                .addScalar("voucherid", LongType.INSTANCE).addScalar("voucherNumber")
                .addScalar("detailtypeid", LongType.INSTANCE).addScalar("detailkeyid", LongType.INSTANCE)
                .addScalar("voucherDate").addScalar("paidTo").addScalar("paidAmount", BigDecimalType.INSTANCE)
                .addScalar("chequeDate")
                .setResultTransformer(Transformers.aliasToBean(ChequeAssignment.class));
        query.setParameter("vhStatus", Long.valueOf(approvedstatus), LongType.INSTANCE)
                .setParameterList("statusId", statusId, IntegerType.INSTANCE);
        filterParams.entrySet().forEach(entry -> query.setParameter(entry.getKey(), entry.getValue()));
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("DIRECT BANK PAYMENT QUERY - " + bankPaymentQuery);
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Completed getDirectBankPaymentsForChequeAssignment.");
        return query.list();
    }

    // Getting only those payments for which cheques have not been assigned.
    @SuppressWarnings("unchecked")
    private void getExpenseBillPaymentsHavingNoCheques() throws NumberFormatException, ApplicationException {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Starting getExpenseBillPaymentsHavingNoCheques... NOT YET ASSIGNED");
        List<ChequeAssignment> billChequeAssignmentList = null;
        final List<Long> billVHIds = new ArrayList<Long>();
        final List<Long> billVHIdsForDebtitSideCC = new ArrayList<Long>();
        List<Object[]> generalLedgerDetailList = new ArrayList<Object[]>();
        List<Object[]> generalLedgerDetailListForDebtitSideCC = new ArrayList<Object[]>();
        final Map<Long, List<Object[]>> billVHIdAndgeneralLedgerDetailListMap = new HashMap<Long, List<Object[]>>();
        final Map<Long, List<Object[]>> billVHIdAndGLDListForDebtitSideCCMap = new HashMap<Long, List<Object[]>>();
        final Map.Entry<String, Map<String, Object>> filterEntry = filterConditions.entrySet().iterator().next();
        final String filterQuery = filterEntry.getKey();
        final Map<String, Object> filterParams = filterEntry.getValue();
        final StringBuilder strQuery = new StringBuilder("select vh.id as voucherid ,vh.voucherNumber as voucherNumber ,vh.voucherDate as voucherDate ,0 as detailtypeid,")
                .append(" 0 as detailkeyid , misbill.paidto as paidTo,case when sum(misbill.paidamount) is null then 0 else sum(misbill.paidamount) end   as paidAmount,")
                .append(" current_date as chequeDate, misbill.billvhid as billVHId ")
                .append(" from Paymentheader ph,egf_instrumentvoucher iv right outer join voucherheader vh on (vh.id=iv.VOUCHERHEADERID) ,vouchermis vmis, Miscbilldetail misbill,")
                .append(" generalledger gl, voucherheader billvh, eg_billregister br,eg_billregistermis billmis ")
                .append(" where ph.voucherheaderid=misbill.payvhid and ph.voucherheaderid=vh.id and vmis.voucherheaderid= vh.id and vh.status = :vhStatus")
                .append(" ").append(filterQuery).append("  ")
                .append(" and gl.voucherheaderid =vh.id and gl.creditamount>0 and misbill.billvhid=billvh.id  and br.id=billmis.billid and billmis.voucherheaderid=billvh.id")
                .append(" and br.expendituretype = :expendituretype and iv.id is null  ")
                .append(" group by  misbill.billvhid,vh.id,vh.voucherNumber,vh.voucherDate,misbill.paidto ");
        query = getSession().createNativeQuery(strQuery.toString())
                .addScalar("voucherid",LongType.INSTANCE)
                .addScalar("voucherNumber")
                .addScalar("voucherDate")
                .addScalar("paidAmount")
                .addScalar("chequeDate")
                .addScalar("paidTo")
                .addScalar("billVHId",LongType.INSTANCE)
                .addScalar("detailtypeid",LongType.INSTANCE)
                .addScalar("detailkeyid",LongType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(ChequeAssignment.class));
        query.setParameter("vhStatus", Long.valueOf(approvedstatus), LongType.INSTANCE)
                .setParameter("expendituretype", FinancialConstants.STANDARD_EXPENDITURETYPE_CONTINGENT, StringType.INSTANCE);
        filterParams.entrySet().forEach(entry -> query.setParameter(entry.getKey(), entry.getValue()));
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("NOT YET ASSIGNED No cheques - " + strQuery);
        billChequeAssignmentList = query.list();

        for (final ChequeAssignment ca : billChequeAssignmentList)
            billVHIds.add(ca.getBillVHId().longValue());
        if (billVHIds != null && billVHIds.size() > 0)
            generalLedgerDetailList = getDetailTypeKeyAmtForBillVHId(billVHIds);
        for (final Object[] gld : generalLedgerDetailList)
            if (billVHIdAndgeneralLedgerDetailListMap.containsKey(getLongValue(gld[3])))
                billVHIdAndgeneralLedgerDetailListMap.get(getLongValue(gld[3])).add(gld);
            else {
                final List<Object[]> generalLedgerDetails = new ArrayList<Object[]>();
                generalLedgerDetails.add(gld);
                billVHIdAndgeneralLedgerDetailListMap.put(getLongValue(gld[3]), generalLedgerDetails);
            }
        for (final ChequeAssignment ca : billChequeAssignmentList) {
            final List<Object[]> detailTypeKeyAmtList = billVHIdAndgeneralLedgerDetailListMap.get(ca.getBillVHId().longValue());

            if (detailTypeKeyAmtList == null || detailTypeKeyAmtList.size() == 0)
                billVHIdsForDebtitSideCC.add(ca.getBillVHId().longValue());
        }
        if (billVHIdsForDebtitSideCC != null && billVHIdsForDebtitSideCC.size() > 0)
            generalLedgerDetailListForDebtitSideCC = getDetailTypeKeyAmtForDebtitSideCC(billVHIdsForDebtitSideCC);
        for (final Object[] gld : generalLedgerDetailListForDebtitSideCC)
            if (billVHIdAndGLDListForDebtitSideCCMap.containsKey(getLongValue(gld[3])))
                billVHIdAndGLDListForDebtitSideCCMap.get(getLongValue(gld[3])).add(gld);
            else {
                final List<Object[]> generalLedgerDetails = new ArrayList<Object[]>();
                generalLedgerDetails.add(gld);
                billVHIdAndGLDListForDebtitSideCCMap.put(getLongValue(gld[3]), generalLedgerDetails);
            }
        for (final ChequeAssignment chqAssgn : billChequeAssignmentList) {
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("NOT YET ASSIGNED: Start Checking for Billvhid " + chqAssgn.getBillVHId());
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("NOT YET ASSIGNED: checking getDetailTypeKeyAmtForBillVHId for Net payable codes");

            List<Object[]> detailTypeKeyAmtList = billVHIdAndgeneralLedgerDetailListMap.get(chqAssgn.getBillVHId()) != null ? billVHIdAndgeneralLedgerDetailListMap
                    .get(chqAssgn.getBillVHId())
                    : new ArrayList<Object[]>();

            if (detailTypeKeyAmtList != null && detailTypeKeyAmtList.size() != 0) {
                if (LOGGER.isDebugEnabled())
                    LOGGER.debug("NOT YET ASSIGNED: detailTypeKeyAmtList for Billvhid " + chqAssgn.getBillVHId() + " size :"
                            + detailTypeKeyAmtList.size());
                if (detailTypeKeyAmtList.size() < 2) {
                    tempExpenseChequeAssignmentList.add(chqAssgn);
                    if (LOGGER.isDebugEnabled())
                        LOGGER.debug("NOT YET ASSIGNED: adding inside detailTypeKeyAmtList.size()<2 block to Assignment List\n"
                                + chqAssgn);
                } else
                    for (final Object[] detailTypeKeyAmtObj : detailTypeKeyAmtList) {
                        final ChequeAssignment ca = new ChequeAssignment();
                        ca.setVoucherid(new BigDecimal(chqAssgn.getVoucherid()));
                        ca.setVoucherNumber(chqAssgn.getVoucherNumber());
                        if (LOGGER.isDebugEnabled())
                            LOGGER.debug("NOT YET ASSIGNED: Voucher Number" + chqAssgn.getVoucherNumber());
                        ca.setVoucherDate(chqAssgn.getVoucherDate());
                        ca.setPaidAmount(BigDecimal.valueOf(Double.valueOf(detailTypeKeyAmtObj[2].toString())));
                        ca.setChequeDate(chqAssgn.getChequeDate());
                        ca.setPaidTo(getEntity(Integer.parseInt(detailTypeKeyAmtObj[0].toString()),
                                (Serializable) detailTypeKeyAmtObj[1]).getName());
                        ca.setDetailtypeid(Long.valueOf(detailTypeKeyAmtObj[0].toString()));
                        ca.setDetailkeyid(Long.valueOf(detailTypeKeyAmtObj[1].toString()));
                        if (LOGGER.isDebugEnabled())
                            LOGGER.debug("NOT YET ASSIGNED: detailTypeKeyAmtList.size()>=2 block to Assignment List\n" + ca);
                        tempExpenseChequeAssignmentList.add(ca);
                    }
            } else {
                if (LOGGER.isDebugEnabled())
                    LOGGER.debug("NOT YET ASSIGNED:  checking getDetailTypeKeyAmtForDebtitSideCC for " + chqAssgn.getBillVHId());
                detailTypeKeyAmtList = billVHIdAndGLDListForDebtitSideCCMap.get(chqAssgn.getBillVHId()) != null ? billVHIdAndGLDListForDebtitSideCCMap
                        .get(chqAssgn.getBillVHId())
                        : new ArrayList<Object[]>();
                if (detailTypeKeyAmtList == null || detailTypeKeyAmtList.size() == 0) {
                    if (LOGGER.isDebugEnabled())
                        LOGGER.debug("NOT YET ASSIGNED: adding to checkassignlist as detailTypeKeyAmtList is null or zero"
                                + chqAssgn);
                    tempExpenseChequeAssignmentList.add(chqAssgn);
                } else if (detailTypeKeyAmtList != null && detailTypeKeyAmtList.size() == 1) {
                    if (LOGGER.isDebugEnabled())
                        LOGGER.debug("NOT YET ASSIGNED: adding to checkassignlist as detailTypeKeyAmtList is 1" + chqAssgn);
                    tempExpenseChequeAssignmentList.add(chqAssgn);
                } else {
                    BigDecimal deduction = BigDecimal.valueOf(0);
                    // THIS dedcution will work for only one subledger .If more than one you cannot have non subledger dedcution
                    // as
                    // you can not distribute that among multiple people
                    // Also this needs same subledger entity used on debit and credit side
                    if (detailTypeKeyAmtList.size() == 1)
                        deduction = getNonSubledgerDeductions(chqAssgn.getBillVHId());
                    Map<String, BigDecimal> dedMap = new HashMap<String, BigDecimal>();
                    dedMap = getSubledgerAmtForDeduction(chqAssgn.getBillVHId());
                    String key = "";
                    for (final Object[] obj : detailTypeKeyAmtList) {
                        final ChequeAssignment c = new ChequeAssignment();
                        c.setChequeDate(chqAssgn.getChequeDate());
                        c.setVoucherHeaderId(chqAssgn.getVoucherid());
                        c.setVoucherNumber(chqAssgn.getVoucherNumber());
                        if (LOGGER.isDebugEnabled())
                            LOGGER.debug("NOT YET ASSIGNED: Voucher Number  :" + chqAssgn.getVoucherNumber());
                        c.setVoucherDate(chqAssgn.getVoucherDate());
                        c.setDetailtypeid(Long.valueOf(obj[0].toString()));
                        c.setDetailkeyid(Long.valueOf(obj[1].toString()));
                        key = obj[0].toString() + DELIMETER + obj[1].toString();
                        // deduct only if deduction is available
                        if (deduction != null)
                            obj[2] = (BigDecimal.valueOf(Double.valueOf(obj[2].toString()))).subtract(deduction);
                        c.setPaidAmount(dedMap.get(key) == null ? BigDecimal.valueOf(Double.valueOf(obj[2].toString())) : (BigDecimal.valueOf(Double.valueOf(obj[2].toString()))).subtract(dedMap
                                .get(key)));
                        c.setPaidTo(getEntity(Integer.valueOf(obj[0].toString()), (Serializable) obj[1]).getName());
                        if (LOGGER.isDebugEnabled())
                            LOGGER.debug("NOT YET ASSIGNED:  detailTypeKeyAmtList.size()>=2 block to Assignment List\n" + c);
                        tempExpenseChequeAssignmentList.add(c);
                    }
                }
            }
        }
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Completed getExpenseBillPaymentsHavingNoCheques.");
    }

    private BigDecimal getNonSubledgerDeductions(final Long billVHId) {
        final Query query = getSession().createNativeQuery(new StringBuilder("SELECT SUM(gl.creditamount) ")
                .append(" FROM generalledger gl ")
                .append(" WHERE gl.creditamount > 0 AND gl.glcodeid NOT IN (:glcodeIdList) AND voucherheaderid = :vhId AND gl.glcodeid NOT IN ")
                .append(" (SELECT glcodeid FROM chartofaccountdetail) order by gl.glcode").toString());
        query.setParameterList("glcodeIdList", cBillGlcodeIdList, BigDecimalType.INSTANCE)
                .setParameter("vhId", billVHId, LongType.INSTANCE);
        final List<BigDecimal> results = query.list();
        if (!results.isEmpty())
            return BigDecimal.valueOf(Double.valueOf(results.get(0).toString()));
        else
            return BigDecimal.valueOf(0);
    }

    // Getting only those payments for which cheques have been assigned but no cheque is surrendered.
    @SuppressWarnings("unchecked")
    private void getExpenseBillPaymentsWithNoSurrenderedCheque() throws NumberFormatException, ApplicationException {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Starting getExpenseBillPaymentsWithNoSurrenderedCheque...ALREADY ASSIGNED: ");
        final List<Long> billVHIds = new ArrayList<Long>();
        final List<Long> billVHIdsForDebtitSideCC = new ArrayList<Long>();
        final Map<Long, List<Object[]>> billVHIdAndgeneralLedgerDetailListMap = new HashMap<Long, List<Object[]>>();
        final Map<Long, List<Object[]>> billVHIdAndGLDListForDebtitSideCCMap = new HashMap<Long, List<Object[]>>();
        List<Object[]> generalLedgerDetailList = new ArrayList<Object[]>();
        List<Object[]> generalLedgerDetailListForDebtitSideCC = new ArrayList<Object[]>();
        List<ChequeAssignment> billChequeAssignmentList = null;
        final Map.Entry<String, Map<String, Object>> filterEntry = filterConditions.entrySet().iterator().next();
        final String filterQuery = filterEntry.getKey();
        final Map<String, Object> filterParams = filterEntry.getValue();
        final StringBuilder strQuery = new StringBuilder(" select vh.id as voucherid ,vh.voucherNumber as voucherNumber ,vh.voucherDate as voucherDate ,0 as detailtypeid,")
                .append(" 0 as detailkeyid, misbill.paidto as paidTo,case when sum(misbill.paidamount) is null then 0 else sum(misbill.paidamount) end as paidAmount,")
                .append(" current_date as chequeDate,misbill.billvhid as billVHId ")
                .append(" from Paymentheader ph, voucherheader vh ,vouchermis vmis, Miscbilldetail misbill , generalledger gl,voucherheader billvh, eg_billregister br,")
                .append(" eg_billregistermis billmis  ")
                .append(" where ph.voucherheaderid=misbill.payvhid and ph.voucherheaderid=vh.id and vmis.voucherheaderid= vh.id and vh.status = :vhStatus")
                .append(filterQuery).append(" ")
                .append(" and gl.voucherheaderid =vh.id and gl.creditamount>0 and misbill.billvhid=billvh.id  and br.id=billmis.billid and billmis.voucherheaderid=billvh.id")
                .append(" and br.expendituretype = :expendituretype")
                .append(" and not exists(select 1 from egf_instrumentvoucher iv, egf_instrumentheader ih where ih.id= iv.instrumentheaderid and iv.voucherheaderid=vh.id")
                .append(" and ih.id_status not in (:statusId))   ")
                .append(" and exists (select 1 from egf_instrumentvoucher iv where  iv.voucherheaderid=vh.id) group by misbill.billvhid,vh.id,vh.voucherNumber,vh.voucherDate,")
                .append(" misbill.paidto ");
        final Query query = getSession().createNativeQuery(strQuery.toString())
                .addScalar("voucherid", LongType.INSTANCE).addScalar("voucherNumber").addScalar("voucherDate")
                .addScalar("paidAmount", BigDecimalType.INSTANCE)
                .addScalar("chequeDate").addScalar("paidTo").addScalar("billVHId", LongType.INSTANCE)
                .addScalar("detailtypeid", LongType.INSTANCE)
                .addScalar("detailkeyid", LongType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(ChequeAssignment.class));
        query.setParameter("vhStatus", Long.valueOf(approvedstatus), LongType.INSTANCE)
                .setParameter("expendituretype", FinancialConstants.STANDARD_EXPENDITURETYPE_CONTINGENT)
                .setParameterList("statusId", statusId, IntegerType.INSTANCE);
        filterParams.entrySet().forEach(entry -> query.setParameter(entry.getKey(), entry.getValue()));
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("ALREADY ASSIGNED: No surrendered cheques - " + strQuery);
        billChequeAssignmentList = query.list();
        for (final ChequeAssignment ca : billChequeAssignmentList)
            billVHIds.add(ca.getBillVHId().longValue());
        if (billVHIds != null && billVHIds.size() > 0)
            generalLedgerDetailList = getDetailTypeKeyAmtForBillVHId(billVHIds);
        for (final Object[] gld : generalLedgerDetailList)
            if (billVHIdAndgeneralLedgerDetailListMap.containsKey(getLongValue(gld[3])))
                billVHIdAndgeneralLedgerDetailListMap.get(getLongValue(gld[3])).add(gld);
            else {
                final List<Object[]> generalLedgerDetails = new ArrayList<Object[]>();
                generalLedgerDetails.add(gld);
                billVHIdAndgeneralLedgerDetailListMap.put(getLongValue(gld[3]), generalLedgerDetails);
            }
        for (final ChequeAssignment ca : billChequeAssignmentList) {
            final List<Object[]> detailTypeKeyAmtList = billVHIdAndgeneralLedgerDetailListMap.get(ca.getBillVHId().longValue());

            if (detailTypeKeyAmtList == null || detailTypeKeyAmtList.size() == 0)
                billVHIdsForDebtitSideCC.add(ca.getBillVHId().longValue());
        }
        if (billVHIdsForDebtitSideCC != null && billVHIdsForDebtitSideCC.size() > 0)
            generalLedgerDetailListForDebtitSideCC = getDetailTypeKeyAmtForDebtitSideCC(billVHIdsForDebtitSideCC);
        for (final Object[] gld : generalLedgerDetailListForDebtitSideCC)
            if (billVHIdAndGLDListForDebtitSideCCMap.containsKey(getLongValue(gld[3])))
                billVHIdAndGLDListForDebtitSideCCMap.get(getLongValue(gld[3])).add(gld);
            else {
                final List<Object[]> generalLedgerDetails = new ArrayList<Object[]>();
                generalLedgerDetails.add(gld);
                billVHIdAndGLDListForDebtitSideCCMap.put(getLongValue(gld[3]), generalLedgerDetails);
            }
        for (final ChequeAssignment chqAssgn : billChequeAssignmentList) {

            if (LOGGER.isDebugEnabled())
                LOGGER.debug("ALREADY ASSIGNED: Start Checking for Billvhid " + chqAssgn.getBillVHId());
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("ALREADY ASSIGNED: checking getDetailTypeKeyAmtForBillVHId for Net payable codes");
            List<Object[]> detailTypeKeyAmtList = billVHIdAndgeneralLedgerDetailListMap.get(chqAssgn.getBillVHId().longValue()) != null ? billVHIdAndgeneralLedgerDetailListMap
                    .get(chqAssgn.getBillVHId().longValue())
                    : new ArrayList<Object[]>();
            if (detailTypeKeyAmtList != null && detailTypeKeyAmtList.size() != 0) {
                if (LOGGER.isDebugEnabled())
                    LOGGER.debug("ALREADY ASSIGNED: detailTypeKeyAmtList for Billvhid " + chqAssgn.getBillVHId() + " size :"
                            + detailTypeKeyAmtList.size());
                if (detailTypeKeyAmtList.size() < 2)// single subledger
                {

                    final StringBuilder queryString = new StringBuilder("select distinct(ih.payTo) from egf_InstrumentHeader ih, egf_InstrumentVoucher iv")
                            .append(" where iv.instrumentHeaderId=ih.id and iv.voucherHeaderId = :vhId and ih.payTo = :payTo and ih.id_status in (:statusId)");
                    if (LOGGER.isDebugEnabled())
                        LOGGER.debug("ALREADY ASSIGNED: queryString" + queryString);

                    final List<Object> payTo = getSession().createNativeQuery(queryString.toString())
                            .setParameter("vhId", chqAssgn.getVoucherid(), LongType.INSTANCE)
                            .setParameterList("statusId", statusId, IntegerType.INSTANCE)
                            .setParameter("payTo", chqAssgn.getPaidTo(), StringType.INSTANCE).list();

                    if (payTo == null || payTo.size() == 0) {
                        if (LOGGER.isDebugEnabled())
                            LOGGER.debug(" ALREADY ASSIGNED: adding to chequeAssignlist as payTo s null or size 0" + chqAssgn);
                        tempExpenseChequeAssignmentList.add(chqAssgn);
                    } else {
                        if (LOGGER.isDebugEnabled())
                            LOGGER.debug("ALREADY ASSIGNED: Not adding continuing");
                        continue;
                    }
                } else {
                    if (LOGGER.isDebugEnabled())
                        LOGGER.debug("ALREADY ASSIGNED:  Entering detailTypeKeyAmtList.size()>2 code");
                    for (final Object[] detailTypeKeyAmtObj : detailTypeKeyAmtList) {
                        StringBuilder queryString = new StringBuilder("select distinct(ih.payTo) from egf_InstrumentHeader ih, egf_InstrumentVoucher iv)")
                                .append(" where iv.instrumentHeaderId=ih.id and iv.voucherHeaderId = :vhId and ih.detailTypeId = :detailTypeId")
                                .append(" and ih.detailKeyId = :detailKeyId and ih.id_status in (:statusId)");
                        if (LOGGER.isDebugEnabled())
                            LOGGER.debug("queryString" + queryString);
                        List<Object> payTo = getSession().createNativeQuery(queryString.toString())
                                .setParameter("vhId", chqAssgn.getVoucherid(), LongType.INSTANCE)
                                .setParameter("detailTypeId", detailTypeKeyAmtObj[0])
                                .setParameter("detailKeyId", detailTypeKeyAmtObj[1])
                                .setParameterList("statusId", statusId, IntegerType.INSTANCE)
                                .list();
                        if (payTo == null || payTo.size() == 0) {
                            // this check will avoid already assigned by single subledger take subleger logic as it should be
                            // single subledger take payto
                            queryString = new StringBuilder("select distinct(ih.payTo) from egf_InstrumentHeader ih, egf_InstrumentVoucher iv")
                                    .append(" where iv.instrumentHeaderId=ih.id and iv.voucherHeaderId = :vhId and ih.payTo = :payTo and ih.id_status in (:statusId)");
                            if (LOGGER.isDebugEnabled())
                                LOGGER.debug("ALREADY ASSIGNED: queryString" + queryString);
                            payTo = getSession().createNativeQuery(queryString.toString())
                                    .setParameter("vhId", chqAssgn.getVoucherid(), LongType.INSTANCE)
                                    .setParameterList("statusId", statusId, IntegerType.INSTANCE)
                                    .setParameter("payTo", chqAssgn.getPaidTo(), StringType.INSTANCE)
                                    .list();
                            if (payTo != null)
                                continue;
                            final ChequeAssignment ca = new ChequeAssignment();
                            ca.setVoucherid(new BigDecimal(chqAssgn.getVoucherid()));
                            ca.setVoucherNumber(chqAssgn.getVoucherNumber());
                            ca.setVoucherDate(chqAssgn.getVoucherDate());
                            ca.setPaidAmount(BigDecimal.valueOf(Double.valueOf(detailTypeKeyAmtObj[2].toString())));
                            ca.setChequeDate(chqAssgn.getChequeDate());
                            ca.setPaidTo(getEntity(Integer.parseInt(detailTypeKeyAmtObj[0].toString()),
                                    (Serializable) detailTypeKeyAmtObj[1]).getName());
                            ca.setDetailtypeid(Long.valueOf(detailTypeKeyAmtObj[0].toString()));
                            ca.setDetailkeyid(Long.valueOf(detailTypeKeyAmtObj[1].toString()));
                            if (LOGGER.isDebugEnabled())
                                LOGGER.debug(" ALREADY ASSIGNED: adding to chequeAssignlist" + ca);
                            tempExpenseChequeAssignmentList.add(ca);
                        } else {
                            if (LOGGER.isDebugEnabled())
                                LOGGER.debug("ALREADY ASSIGNED: Not adding continuing");
                            continue;

                        }
                    }
                }
            } else {
                if (LOGGER.isDebugEnabled())
                    LOGGER.debug("ALREADY ASSIGNED:  entering getDetailTypeKeyAmtForDebtitSideCC  ");
                detailTypeKeyAmtList = billVHIdAndGLDListForDebtitSideCCMap.get(chqAssgn.getBillVHId().longValue()) != null ? billVHIdAndGLDListForDebtitSideCCMap
                        .get(chqAssgn.getBillVHId().longValue())
                        : new ArrayList<Object[]>();
                if (detailTypeKeyAmtList == null || detailTypeKeyAmtList.size() == 0) {
                    final StringBuilder queryString = new StringBuilder("select distinct(ih.payTo) from egf_InstrumentHeader ih, egf_InstrumentVoucher iv")
                            .append(" where iv.instrumentHeaderId=ih.id and iv.voucherHeaderId = :vhId and ih.payTo = :payTo and ih.id_status in (:statusId)");
                    if (LOGGER.isDebugEnabled())
                        LOGGER.debug("ALREADY ASSIGNED: queryString" + queryString);
                    final List<Object> payTo = getSession().createNativeQuery(queryString.toString())
                            .setParameter("vhId", chqAssgn.getVoucherid(), LongType.INSTANCE)
                            .setParameterList("statusId", statusId, IntegerType.INSTANCE)
                            .setParameter("payTo", chqAssgn.getPaidTo(), StringType.INSTANCE)
                            .list();
                    if (payTo == null || payTo.size() == 0) {
                        if (LOGGER.isDebugEnabled())
                            LOGGER.debug("ALREADY ASSIGNED: adding to chequeAssignlist as payto is null or 0" + chqAssgn);
                        tempExpenseChequeAssignmentList.add(chqAssgn);
                    }
                } else {
                    Map<String, BigDecimal> dedMap = new HashMap<String, BigDecimal>();
                    dedMap = getSubledgerAmtForDeduction(chqAssgn.getBillVHId());
                    String key = "";
                    for (final Object[] obj : detailTypeKeyAmtList) {
                        StringBuilder queryString = new StringBuilder("select distinct(ih.payTo) from egf_InstrumentHeader ih, egf_InstrumentVoucher iv")
                                .append(" where iv.instrumentHeaderId=ih.id and iv.voucherHeaderId = :vhId and ih.detailTypeId = :detailTypeId and ih.detailKeyId = :detailKeyId")
                                .append(" and ih.id_status in (:statusId)");
                        if (LOGGER.isDebugEnabled())
                            LOGGER.debug("ALREADY ASSIGNED: Querying for " + queryString);
                        List<Object> payTo = getSession().createNativeQuery(queryString.toString())
                                .setParameter("vhId", chqAssgn.getVoucherid())
                                .setParameter("detailTypeId", obj[0])
                                .setParameter("detailKeyId", obj[1])
                                .setParameterList("statusId",statusId, IntegerType.INSTANCE)
                                .list();
                        if (payTo == null || payTo.size() == 0) {

                            // this check will avoid already assigned by single subledger take subleger logic as it should be
                            // single subledger take payto
                            queryString = new StringBuilder(" select distinct(ih.payTo) from egf_InstrumentHeader ih, egf_InstrumentVoucher iv")
                                    .append(" where iv.instrumentHeaderId=ih.id and iv.voucherHeaderId = :vhId and ih.payTo = :payTo and ih.id_status in (:statusId)");
                            if (LOGGER.isDebugEnabled())
                                LOGGER.debug("ALREADY ASSIGNED: queryString" + queryString);
                            payTo = getSession().createNativeQuery(queryString.toString())
                                    .setParameter("vhId", chqAssgn.getVoucherid(), LongType.INSTANCE)
                                    .setParameterList("statusId", statusId, IntegerType.INSTANCE)
                                    .setParameter("payTo", chqAssgn.getPaidTo(), StringType.INSTANCE)
                                    .list();
                            if (payTo != null)
                                continue;

                            final ChequeAssignment c = new ChequeAssignment();
                            c.setChequeDate(chqAssgn.getChequeDate());
                            c.setVoucherHeaderId(chqAssgn.getVoucherid());
                            c.setVoucherNumber(chqAssgn.getVoucherNumber());
                            c.setVoucherDate(chqAssgn.getVoucherDate());
                            c.setDetailtypeid(Long.valueOf(obj[0].toString()));
                            c.setDetailkeyid(Long.valueOf(obj[1].toString()));
                            key = obj[0].toString() + DELIMETER + obj[1].toString();
                            c.setPaidAmount(dedMap.get(key) == null ? BigDecimal.valueOf(Double.valueOf(obj[2].toString())) : (BigDecimal.valueOf(Double.valueOf(obj[2].toString())))
                                    .subtract(dedMap.get(key)));
                            c.setPaidTo(getEntity(Integer.valueOf(obj[0].toString()), (Serializable) obj[1]).getName());
                            if (LOGGER.isDebugEnabled())
                                LOGGER.debug("ALREADY ASSIGNED: adding to chequeAssignlist as from payTo==null || payTo.size()==0 \n"
                                        + c);
                            tempExpenseChequeAssignmentList.add(c);
                        } else
                            continue;
                    }
                }
            }
        }
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Completed getExpenseBillPaymentsWithNoSurrenderedCheque.");
    }

    // Getting only those payments associated with surrendered cheques
    @SuppressWarnings("unchecked")
    private void getExpenseBillPaymentsWithSurrenderedCheques() throws NumberFormatException, ApplicationException {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Starting getExpenseBillPaymentsWithSurrenderedCheques...ASSIGNED BUT SURRENDARD: ");
        List<ChequeAssignment> billChequeAssignmentList = null;
        final List<Long> billVHIds = new ArrayList<Long>();
        final List<Long> billVHIdsForDebtitSideCC = new ArrayList<Long>();
        final Map<Long, List<Object[]>> billVHIdAndgeneralLedgerDetailListMap = new HashMap<Long, List<Object[]>>();
        final Map<Long, List<Object[]>> billVHIdAndGLDListForDebtitSideCCMap = new HashMap<Long, List<Object[]>>();
        List<Object[]> generalLedgerDetailList = new ArrayList<Object[]>();
        List<Object[]> generalLedgerDetailListForDebtitSideCC = new ArrayList<Object[]>();
        final Map.Entry<String, Map<String, Object>> filterEntry = filterConditions.entrySet().iterator().next();
        final String filterQuery = filterEntry.getKey();
        final Map<String, Object> filterParams = filterEntry.getValue();
        final StringBuilder strQuery = new StringBuilder(" select vh.id as voucherid ,vh.voucherNumber as voucherNumber ,vh.voucherDate as voucherDate ,0 as detailtypeid,")
                .append(" 0 as detailkeyid, misbill.paidto as paidTo,case when sum(misbill.paidamount)=null then 0 else sum(misbill.paidamount) end as paidAmount,")
                .append(" current_date as chequeDate,misbill.billvhid as billVHId  ")
                .append(" from Paymentheader ph, voucherheader vh ,vouchermis vmis, Miscbilldetail misbill , generalledger gl,voucherheader billvh, eg_billregister br,")
                .append(" eg_billregistermis billmis   ")
                .append(" where ph.voucherheaderid=misbill.payvhid and ph.voucherheaderid=vh.id and vmis.voucherheaderid= vh.id and vh.status = :status")
                .append(filterQuery).append(" ")
                .append(" and gl.voucherheaderid =vh.id and gl.creditamount>0 and misbill.billvhid=billvh.id  and br.id=billmis.billid and billmis.voucherheaderid=billvh.id")
                .append(" and br.expendituretype = :expendituretype and exists(select 1 from egf_instrumentvoucher iv, egf_instrumentheader ih where ih.id= iv.instrumentheaderid")
                .append(" and iv.voucherheaderid=vh.id and ih.id_status not in (:statusId)) ")
                .append(" group by misbill.billvhid,vh.id,vh.voucherNumber,vh.voucherDate,misbill.paidto ");
        final Query query = getSession().createNativeQuery(strQuery.toString())
                .addScalar("voucherid", LongType.INSTANCE)
                .addScalar("voucherNumber")
                .addScalar("voucherDate")
                .addScalar("paidAmount")
                .addScalar("chequeDate")
                .addScalar("paidTo")
                .addScalar("billVHId", LongType.INSTANCE)
                .addScalar("detailtypeid", LongType.INSTANCE)
                .addScalar("detailkeyid", LongType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(ChequeAssignment.class));
        query.setParameter("status", Long.valueOf(approvedstatus), LongType.INSTANCE)
                .setParameter("expendituretype", FinancialConstants.STANDARD_EXPENDITURETYPE_CONTINGENT, StringType.INSTANCE)
                .setParameterList("statusId", statusId, IntegerType.INSTANCE);
        filterParams.entrySet().forEach(entry -> query.setParameter(entry.getKey(), entry.getValue()));
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("ASSIGNED BUT SURRENDARD: With surrendered cheques - " + strQuery);
        billChequeAssignmentList = query.list();
        for (final ChequeAssignment ca : billChequeAssignmentList)
            billVHIds.add(ca.getBillVHId().longValue());
        if (billVHIds != null && billVHIds.size() > 0)
            generalLedgerDetailList = getDetailTypeKeyAmtForBillVHId(billVHIds);
        for (final Object[] gld : generalLedgerDetailList)
            if (billVHIdAndgeneralLedgerDetailListMap.containsKey(getLongValue(gld[3])))
                billVHIdAndgeneralLedgerDetailListMap.get(getLongValue(gld[3])).add(gld);
            else {
                final List<Object[]> generalLedgerDetails = new ArrayList<Object[]>();
                generalLedgerDetails.add(gld);
                billVHIdAndgeneralLedgerDetailListMap.put(getLongValue(gld[3]), generalLedgerDetails);
            }
        for (final ChequeAssignment ca : billChequeAssignmentList) {
            final List<Object[]> detailTypeKeyAmtList = billVHIdAndgeneralLedgerDetailListMap.get(ca.getBillVHId().longValue());

            if (detailTypeKeyAmtList == null || detailTypeKeyAmtList.size() == 0)
                billVHIdsForDebtitSideCC.add(ca.getBillVHId().longValue());
        }
        if (billVHIdsForDebtitSideCC != null && billVHIdsForDebtitSideCC.size() > 0)
            generalLedgerDetailListForDebtitSideCC = getDetailTypeKeyAmtForDebtitSideCC(billVHIdsForDebtitSideCC);
        for (final Object[] gld : generalLedgerDetailListForDebtitSideCC)
            if (billVHIdAndGLDListForDebtitSideCCMap.containsKey(getLongValue(gld[3])))
                billVHIdAndGLDListForDebtitSideCCMap.get(getLongValue(gld[3])).add(gld);
            else {
                final List<Object[]> generalLedgerDetails = new ArrayList<Object[]>();
                generalLedgerDetails.add(gld);
                billVHIdAndGLDListForDebtitSideCCMap.put(getLongValue(gld[3]), generalLedgerDetails);
            }
        for (final ChequeAssignment chqAssgn : billChequeAssignmentList) {
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("ASSIGNED BUT SURRENDARD: With surrendered cheques -  for Billvhid" + chqAssgn.getBillVHId());
            List<Object[]> detailTypeKeyAmtList = billVHIdAndgeneralLedgerDetailListMap.get(chqAssgn.getBillVHId().longValue()) != null ? billVHIdAndgeneralLedgerDetailListMap
                    .get(chqAssgn.getBillVHId().longValue())
                    : new ArrayList<Object[]>();

            if (detailTypeKeyAmtList != null && detailTypeKeyAmtList.size() != 0) {
                if (LOGGER.isDebugEnabled())
                    LOGGER.debug("ASSIGNED BUT SURRENDARD: With surrendered cheques -  for Billvhid " + chqAssgn.getBillVHId()
                            + " and size " + detailTypeKeyAmtList);
                if (detailTypeKeyAmtList.size() < 2) {
                    final StringBuilder queryString = new StringBuilder("select iv.id,ih.id_status from egf_instrumentheader ih, egf_instrumentvoucher iv")
                            .append(" where iv.instrumentheaderid=ih.id and iv.voucherheaderid = :vhId and ih.payTo = :payTo order by id desc");
                    if (LOGGER.isDebugEnabled())
                        LOGGER.debug("instrumentStatus- " + queryString);
                    final List<Object[]> instrumentStatus = getSession().createNativeQuery(queryString.toString())
                            .setParameter("vhId", chqAssgn.getVoucherid(), LongType.INSTANCE)
                            .setParameter("payTo", chqAssgn.getPaidTo(), StringType.INSTANCE)
                            .list();
                    if (instrumentStatus == null
                            || instrumentStatus.size() == 0
                            || !instrumentStatus.get(0)[1].toString().equalsIgnoreCase(instrumentNewStatus) && !instrumentStatus
                            .get(0)[1].toString().equalsIgnoreCase(instrumentReconciledStatus)) {
                        if (LOGGER.isDebugEnabled())
                            LOGGER.debug("ASSIGNED BUT SURRENDARD: Adding to chequeAssignmentlist as istrumentStatus " + chqAssgn);
                        tempExpenseChequeAssignmentList.add(chqAssgn);
                    } else {
                        if (LOGGER.isDebugEnabled())
                            LOGGER.debug("ASSIGNED BUT SURRENDARD: Continuing not adding");
                        continue;
                    }
                } else
                    for (final Object[] detailTypeKeyAmtObj : detailTypeKeyAmtList) {
                        final StringBuilder queryString = new StringBuilder("select iv.id,ih.id_status from egf_instrumentheader ih, egf_instrumentvoucher iv")
                                .append(" where iv.instrumentheaderid=ih.id and iv.voucherheaderid = :vhId and ih.detailtypeid = :detailTypeId")
                                .append(" and ih.detailkeyid = :detailKeyId order by id desc ");
                        if (LOGGER.isDebugEnabled())
                            LOGGER.debug("ASSIGNED BUT SURRENDARD: Inside detailTypeKeyAmtList loop- " + queryString);
                        final List<Object[]> instrumentStatus = getSession().createNativeQuery(queryString.toString())
                                .setParameter("vhId", chqAssgn.getVoucherid(), LongType.INSTANCE)
                                .setParameter("detailTypeId", detailTypeKeyAmtObj[0])
                                .setParameter("detailKeyId", detailTypeKeyAmtObj[1])
                                .list();
                        if (instrumentStatus == null
                                || instrumentStatus.size() == 0
                                || !instrumentStatus.get(0)[1].toString().equalsIgnoreCase(instrumentNewStatus)
                                && !instrumentStatus
                                .get(0)[1].toString().equalsIgnoreCase(instrumentReconciledStatus)) {
                            final ChequeAssignment ca = new ChequeAssignment();
                            ca.setVoucherid(new BigDecimal(chqAssgn.getVoucherid()));
                            ca.setVoucherNumber(chqAssgn.getVoucherNumber());
                            ca.setVoucherDate(chqAssgn.getVoucherDate());
                            ca.setPaidAmount(BigDecimal.valueOf(Double.valueOf(detailTypeKeyAmtObj[2].toString())));
                            ca.setChequeDate(chqAssgn.getChequeDate());
                            ca.setPaidTo(getEntity(Integer.parseInt(detailTypeKeyAmtObj[0].toString()),
                                    (Serializable) detailTypeKeyAmtObj[1]).getName());
                            ca.setDetailtypeid(Long.valueOf(detailTypeKeyAmtObj[0].toString()));
                            ca.setDetailkeyid(Long.valueOf(detailTypeKeyAmtObj[1].toString()));
                            if (LOGGER.isDebugEnabled())
                                LOGGER.debug("ASSIGNED BUT SURRENDARD: inside loop adding  " + ca);
                            tempExpenseChequeAssignmentList.add(ca);
                        } else
                            continue;
                    }
            }// End of checking bills with SL where credit amount>0
            else {
                if (LOGGER.isDebugEnabled())
                    LOGGER.debug("ASSIGNED BUT SURRENDARD:  checking getDetailTypeKeyAmtForDebtitSideCC for "
                            + chqAssgn.getBillVHId());
                detailTypeKeyAmtList = billVHIdAndGLDListForDebtitSideCCMap.get(chqAssgn.getBillVHId().longValue()) != null ? billVHIdAndGLDListForDebtitSideCCMap
                        .get(chqAssgn.getBillVHId().longValue())
                        : new ArrayList<Object[]>();
                if (detailTypeKeyAmtList == null || detailTypeKeyAmtList.size() == 0) {
                    final StringBuilder queryString = new StringBuilder("select iv.id,ih.id_status from egf_instrumentheader ih, egf_instrumentvoucher iv")
                            .append(" where iv.instrumentheaderid=ih.id and iv.voucherheaderid = :vhId")
                            .append(" and ih.payTo = :payTo order by id desc");
                    if (LOGGER.isDebugEnabled())
                        LOGGER.debug("ASSIGNED BUT SURRENDARD: getDetailTypeKeyAmtForDebtitSideCC " + queryString);
                    final List<Object[]> instrumentStatus = getSession().createNativeQuery(queryString.toString())
                            .setParameter("vhId", chqAssgn.getVoucherid(), LongType.INSTANCE)
                            .setParameter("payTo", chqAssgn.getPaidTo(), StringType.INSTANCE)
                            .list();

                    if (instrumentStatus == null
                            || instrumentStatus.size() == 0
                            || !instrumentStatus.get(0)[1].toString().equalsIgnoreCase(instrumentNewStatus) && !instrumentStatus
                            .get(0)[1].toString().equalsIgnoreCase(instrumentReconciledStatus)) {
                        if (LOGGER.isDebugEnabled())
                            LOGGER.debug("ASSIGNED BUT SURRENDARD: Adding to chequeAssignmentlist in getDetailTypeKeyAmtForDebtitSideCC "
                                    + chqAssgn);
                        tempExpenseChequeAssignmentList.add(chqAssgn);
                    }
                } else if (detailTypeKeyAmtList != null && detailTypeKeyAmtList.size() == 1) {
                    final StringBuilder queryString = new StringBuilder("select iv.id,ih.id_status from egf_instrumentheader ih, egf_instrumentvoucher iv")
                            .append(" where iv.instrumentheaderid=ih.id and iv.voucherheaderid = :vhId and ih.payTo = :payTo order by id desc");
                    if (LOGGER.isDebugEnabled())
                        LOGGER.debug("ASSIGNED BUT SURRENDARD: detailTypeKeyAmtList size=1" + queryString);
                    final List<Object[]> instrumentStatus = getSession().createNativeQuery(queryString.toString())
                            .setParameter("vhId", chqAssgn.getVoucherid(), LongType.INSTANCE)
                            .setParameter("payTo", chqAssgn.getPaidTo(), StringType.INSTANCE)
                            .list();
                    if (instrumentStatus == null
                            || instrumentStatus.size() == 0
                            || !instrumentStatus.get(0)[1].toString().equalsIgnoreCase(instrumentNewStatus) && !instrumentStatus
                            .get(0)[1].toString().equalsIgnoreCase(instrumentReconciledStatus)) {
                        final StringBuilder queryString2 = new StringBuilder("select iv.id,ih.id_status from egf_instrumentheader ih, egf_instrumentvoucher iv")
                                .append(" where iv.instrumentheaderid=ih.id and iv.voucherheaderid = :vhId and ih.payTo = :payTo order by id desc");
                        if (LOGGER.isDebugEnabled())
                            LOGGER.debug("ASSIGNED BUT SURRENDARD: detailTypeKeyAmtList  again checking " + queryString2);
                        final List<Object[]> instrumentStatusWithsubledgerPaidto = getSession().createNativeQuery(queryString2.toString())
                                .setParameter("vhId", chqAssgn.getVoucherid(), LongType.INSTANCE)
                                .setParameter("payTo",
                                        getEntity(Integer.parseInt(detailTypeKeyAmtList.get(0)[0].toString()),
                                                (Serializable) detailTypeKeyAmtList.get(0)[1]).getName(), StringType.INSTANCE)
                                .list();
                        if (instrumentStatusWithsubledgerPaidto == null
                                || instrumentStatusWithsubledgerPaidto.size() == 0
                                || !instrumentStatusWithsubledgerPaidto.get(0)[1].toString().equalsIgnoreCase(
                                instrumentNewStatus) && !instrumentStatusWithsubledgerPaidto.get(0)[1].toString()
                                .equalsIgnoreCase(instrumentReconciledStatus)) {
                            if (LOGGER.isDebugEnabled())
                                LOGGER.debug("ASSIGNED BUT SURRENDARD: adding inside  again checking" + chqAssgn);
                            tempExpenseChequeAssignmentList.add(chqAssgn);
                        }
                    }
                } else// if more than 1 SL entries with debit side CC
                {
                    Map<String, BigDecimal> dedMap = new HashMap<String, BigDecimal>();
                    dedMap = getSubledgerAmtForDeduction(chqAssgn.getBillVHId());
                    String key = "";
                    for (final Object[] obj : detailTypeKeyAmtList) {
                        final StringBuilder queryString = new StringBuilder("select iv.id,ih.id_status from egf_instrumentheader ih, egf_instrumentvoucher iv")
                                .append(" where iv.instrumentheaderid=ih.id and iv.voucherheaderid = :vhId and ih.detailtypeid = :detailtypeid")
                                .append(" and ih.detailkeyid = :detailkeyid order by id desc ");
                        if (LOGGER.isDebugEnabled())
                            LOGGER.debug("ASSIGNED BUT SURRENDARD: detailTypeKeyAmtList  checking " + queryString);
                        final List<Object[]> instrumentStatus = getSession().createNativeQuery(queryString.toString())
                                .setParameter("vhId", chqAssgn.getVoucherid(), LongType.INSTANCE)
                                .setParameter("detailtypeid", obj[0])
                                .setParameter("detailkeyid", obj[1])
                                .list();
                        if (instrumentStatus == null
                                || instrumentStatus.size() == 0
                                || !instrumentStatus.get(0)[1].toString().equalsIgnoreCase(instrumentNewStatus)
                                && !instrumentStatus
                                .get(0)[1].toString().equalsIgnoreCase(instrumentReconciledStatus)) {
                            final ChequeAssignment c = new ChequeAssignment();
                            c.setChequeDate(chqAssgn.getChequeDate());
                            c.setVoucherHeaderId(chqAssgn.getVoucherid());
                            c.setVoucherNumber(chqAssgn.getVoucherNumber());
                            c.setVoucherDate(chqAssgn.getVoucherDate());
                            c.setDetailtypeid(Long.valueOf(obj[0].toString()));
                            c.setDetailkeyid(Long.valueOf(obj[1].toString()));
                            key = obj[0].toString() + DELIMETER + obj[1].toString();
                            c.setPaidAmount(dedMap.get(key) == null ? BigDecimal.valueOf(Double.valueOf(obj[2].toString())) : (BigDecimal.valueOf(Double.valueOf(obj[2].toString())))
                                    .subtract(dedMap.get(key)));
                            c.setPaidTo(getEntity(Integer.valueOf(obj[0].toString()), (Serializable) obj[1]).getName());
                            if (LOGGER.isDebugEnabled())
                                LOGGER.debug("ASSIGNED BUT SURRENDARD: adding inside  detailTypeKeyAmtList loop" + c);
                            tempExpenseChequeAssignmentList.add(c);
                        } else
                            continue;
                    }
                }
            }// End of main Else
        }
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Completed getExpenseBillPaymentsWithSurrenderedCheques.");
    }

    private Map<String, Map<String, Object>> getFilterParamaters(final Map<String, String[]> parameters, final CVoucherHeader voucherHeader)
            throws ParseException {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Starting getFilterParamaters...");
        final StringBuffer sql = new StringBuffer();
        Map<String, Map<String, Object>> map = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        if (!"".equals(parameters.get("fromDate")[0])) {
            sql.append(" and vh.voucherDate >= :fromDate");
            params.put("fromDate", formatter.parse(parameters.get("fromDate")[0]));
        }
        if (!"".equals(parameters.get("toDate")[0])) {
            sql.append(" and vh.voucherDate <= :toDate");
            params.put("toDate", formatter.parse(parameters.get("toDate")[0]));
        }
        if (!StringUtils.isEmpty(voucherHeader.getVoucherNumber())) {
            sql.append(" and vh.voucherNumber like :voucherNumber");
            params.put("voucherNumber", "%".concat(voucherHeader.getVoucherNumber()).concat("%"));
        }
        if (voucherHeader.getFundId() != null) {
            sql.append(" and vh.fundId = :fundId");
            params.put("fundId", voucherHeader.getFundId().getId());
        }
        if (voucherHeader.getVouchermis().getFundsource() != null) {
            sql.append(" and vmis.fundsourceId = :fundSourceId");
            params.put("fundSourceId", voucherHeader.getVouchermis().getFundsource().getId());
        }
        if (voucherHeader.getVouchermis().getDepartmentid() != null) {
            sql.append(" and vmis.departmentid = :deptId");
            params.put("deptId", voucherHeader.getVouchermis().getDepartmentid().getId());
        }
        if (voucherHeader.getVouchermis().getSchemeid() != null) {
            sql.append(" and vmis.schemeid = :schemeId");
            params.put("schemeId", voucherHeader.getVouchermis().getSchemeid().getId());
        }
        if (voucherHeader.getVouchermis().getSubschemeid() != null) {
            sql.append(" and vmis.subschemeid = :subSchemeId");
            params.put("subSchemeId", voucherHeader.getVouchermis().getSubschemeid().getId());
        }
        if (voucherHeader.getVouchermis().getFunctionary() != null) {
            sql.append(" and vmis.functionaryid = :functionaryid");
            params.put("functionaryid", voucherHeader.getVouchermis().getFunctionary().getId());
        }
        if (voucherHeader.getVouchermis().getDivisionid() != null) {
            sql.append(" and vmis.divisionid = :divisionId");
            params.put("divisionId", voucherHeader.getVouchermis().getDivisionid().getId());
        }
        sql.append(" and ph.bankaccountnumberid = :accountNumberId");
        params.put("accountNumberId", Long.valueOf(parameters.get("bankaccount")[0]));
        sql.append(" and lower(ph.type) = lower(:paymentMode)");
        params.put("paymentMode", parameters.get("paymentMode")[0]);
        map.put(sql.toString(), params);
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Completed getFilterParamaters.");
        return map;
    }

    @SuppressWarnings("unchecked")
    private List<Object[]> getDetailTypeKeyAmtForBillVHId(final List<Long> billVHIds) {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Starting getDetailTypeKeyAmtForBillVHId...");
        final List<Long> cBillGlcodeIdsList = new ArrayList<Long>();
        for (final BigDecimal glCodeId : cBillGlcodeIdList)
            cBillGlcodeIdsList.add(glCodeId.longValue());
        List<Object[]> generalLedgerDetailList = new ArrayList<Object[]>();
        int size = billVHIds.size();
        if (size > 999) {
            int fromIndex = 0;
            int toIndex = 0;
            final int step = 1000;
            List<Object[]> newGLDList;
            while (size - step >= 0) {
                newGLDList = new ArrayList<Object[]>();
                toIndex += step;
                final Query generalLedgerDetailsQuery = getSession().createQuery(new StringBuilder("select gld.detailTypeId.id,gld.detailKeyId.id,gld.amount,gl.voucherHeaderId.id")
                        .append(" from CGeneralLedger gl, CGeneralLedgerDetail gld")
                        .append(" where gl.voucherHeaderId.id in (:ids) and gl.id = gld.generalLedgerId.id and gl.creditAmount>0 and gl.glcodeId.id in (:glcodeIdList)").toString())
                        .setParameterList("ids", billVHIds.subList(fromIndex, toIndex), LongType.INSTANCE)
                        .setParameterList("glcodeIdList", cBillGlcodeIdsList, LongType.INSTANCE);
                newGLDList = generalLedgerDetailsQuery.list();
                fromIndex = toIndex;
                size -= step;
                if (newGLDList != null)
                    generalLedgerDetailList.addAll(newGLDList);
            }

            if (size > 0) {
                newGLDList = new ArrayList<Object[]>();
                fromIndex = toIndex;
                toIndex = fromIndex + size;
                final Query generalLedgerDetailsQuery = getSession().createQuery(new StringBuilder("select gld.detailTypeId.id,gld.detailKeyId,gld.amount,gl.voucherHeaderId.id")
                        .append(" from CGeneralLedger gl, CGeneralLedgerDetail gld")
                        .append(" where gl.voucherHeaderId.id in (:ids) and gl.id = gld.generalLedgerId.id and gl.creditAmount>0 and gl.glcodeId.id in (:glcodeIdList)").toString())
                        .setParameterList("ids", billVHIds.subList(fromIndex, toIndex), LongType.INSTANCE)
                        .setParameterList("glcodeIdList", cBillGlcodeIdsList, LongType.INSTANCE);
                newGLDList = generalLedgerDetailsQuery.list();
                if (newGLDList != null)
                    generalLedgerDetailList.addAll(newGLDList);
            }
        } else {
            final Query generalLedgerDetailsQuery = getSession().createQuery(new StringBuilder("select gld.detailTypeId.id,gld.detailKeyId,gld.amount,gl.voucherHeaderId.id")
                    .append(" from CGeneralLedger gl, CGeneralLedgerDetail gld")
                    .append(" where gl.voucherHeaderId.id in (:ids) and gl.id = gld.generalLedgerId.id and gl.creditAmount>0 and gl.glcodeId.id in (:glcodeIdList)").toString())
                    .setParameterList("ids", billVHIds, LongType.INSTANCE)
                    .setParameterList("glcodeIdList", cBillGlcodeIdsList, LongType.INSTANCE);
            generalLedgerDetailList = generalLedgerDetailsQuery.list();
        }
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Completed getDetailTypeKeyAmtForBillVHId.");
        return generalLedgerDetailList;
    }

    @SuppressWarnings("unchecked")
    private List<Object[]> getDetailTypeKeyAmtForDebtitSideCC(final List<Long> billVHIds) {
        List<Object[]> generalLedgerDetailList = new ArrayList<Object[]>();
        int size = billVHIds.size();
        if (size > 999) {
            int fromIndex = 0;
            int toIndex = 0;
            final int step = 1000;
            List<Object[]> newGLDList;
            while (size - step >= 0) {
                newGLDList = new ArrayList<Object[]>();
                toIndex += step;
                final Query generalLedgerDetailsQuery = getSession().createQuery(new StringBuilder("select gld.detailTypeId.id,gld.detailKeyId,gld.amount,gl.voucherHeaderId.id")
                        .append(" from CGeneralLedger gl, CGeneralLedgerDetail gld")
                        .append(" where gl.voucherHeaderId.id in (:ids) and gl.id = gld.generalLedgerId.id and  gl.debitAmount>0").toString())
                        .setParameterList("ids", billVHIds.subList(fromIndex, toIndex), LongType.INSTANCE);
                newGLDList = generalLedgerDetailsQuery.list();
                fromIndex = toIndex;
                size -= step;
                if (newGLDList != null)
                    generalLedgerDetailList.addAll(newGLDList);

            }

            if (size > 0) {
                newGLDList = new ArrayList<Object[]>();
                fromIndex = toIndex;
                toIndex = fromIndex + size;
                final Query generalLedgerDetailsQuery = getSession().createQuery(new StringBuilder("select gld.detailTypeId.id,gld.detailKeyId,gld.amount,gl.voucherHeaderId.id")
                        .append(" from CGeneralLedger gl, CGeneralLedgerDetail gld")
                        .append(" where gl.voucherHeaderId.id in (:ids) and gl.id = gld.generalLedgerId.id and  gl.debitAmount>0").toString())
                        .setParameterList("ids", billVHIds.subList(fromIndex, toIndex), LongType.INSTANCE);
                newGLDList = generalLedgerDetailsQuery.list();
                if (newGLDList != null)
                    generalLedgerDetailList.addAll(newGLDList);
            }

        } else {
            final Query generalLedgerDetailsQuery = getSession().createQuery(new StringBuilder("select gld.detailTypeId.id,gld.detailKeyId,gld.amount,gl.voucherHeaderId.id")
                    .append(" from CGeneralLedger gl, CGeneralLedgerDetail gld")
                    .append(" where gl.voucherHeaderId.id in (:ids) and gl.id = gld.generalLedgerId.id and  gl.debitAmount>0").toString())
                    .setParameterList("ids", billVHIds, LongType.INSTANCE);
            generalLedgerDetailList = generalLedgerDetailsQuery.list();
        }
        return generalLedgerDetailList;
    }

    private void setStatusValues() {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Starting setStatusValues...");
        finalChequeAssignmentList = new ArrayList<ChequeAssignment>();
        finalCBillChequeAssignmentList = new ArrayList<ChequeAssignment>();
        tempExpenseChequeAssignmentList = new ArrayList<ChequeAssignment>();
        final List<AppConfigValues> appList = appConfigValuesService
                .getConfigValuesByModuleAndKey("EGF", "APPROVEDVOUCHERSTATUS");
        approvedstatus = appList.get(0).getValue();
        final List<String> descriptionList = new ArrayList<String>();
        descriptionList.add("New");
        descriptionList.add("Reconciled");
        final List<EgwStatus> egwStatusList = egwStatusDAO.getStatusListByModuleAndCodeList("Instrument", descriptionList);

        for (final EgwStatus egwStatus : egwStatusList)
            statusId.add(egwStatus.getId());
        if (egwStatusList.size() == 2) {
            instrumentNewStatus = egwStatusList.get(0).getId().toString();
            instrumentReconciledStatus = egwStatusList.get(1).getId().toString();
        }

        getGlcodeIds();
        // cBillGlcodeIdList=cBillGlcodeIdList;
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Completed setStatusValues.");
    }

    private void prepareChequeList() {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Starting prepareChequeList...");
        ChequeAssignment outerChqAssgn;
        ChequeAssignment innerChqAssgn;
        final List<Integer> alreadyProcessedIndices = new ArrayList<Integer>();
        for (int i = 0; i < tempExpenseChequeAssignmentList.size(); i++) {
            if (alreadyProcessedIndices.contains(i))
                continue;
            outerChqAssgn = tempExpenseChequeAssignmentList.get(i);
            for (int j = i + 1; j < tempExpenseChequeAssignmentList.size(); j++) {
                innerChqAssgn = tempExpenseChequeAssignmentList.get(j);
                if (outerChqAssgn.getVoucherid().equals(innerChqAssgn.getVoucherid())
                        && outerChqAssgn.getVoucherDate().equals(innerChqAssgn.getVoucherDate())
                        && outerChqAssgn.getVoucherNumber().equals(innerChqAssgn.getVoucherNumber())
                        && outerChqAssgn.getPaidTo().equals(innerChqAssgn.getPaidTo())
                        && outerChqAssgn.getDetailtypeid().equals(innerChqAssgn.getDetailtypeid())
                        && outerChqAssgn.getDetailkeyid().equals(innerChqAssgn.getDetailkeyid())) {
                    outerChqAssgn.setPaidAmount(outerChqAssgn.getPaidAmount().add(innerChqAssgn.getPaidAmount()));
                    alreadyProcessedIndices.add(j);
                }
            }
            finalCBillChequeAssignmentList.add(outerChqAssgn);
        }
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Completed prepareChequeList.");
    }

    @SuppressWarnings("unchecked")
    private Map<String, BigDecimal> getSubledgerAmtForDeduction(final Long billVHId) {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Starting getSubledgerAmtForDeduction...");
        final Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
        final Query query = getSession().createNativeQuery(new StringBuilder("SELECT gld.detailtypeid, gld.detailkeyid, SUM(gld.amount)")
                .append(" FROM generalledgerdetail gld, generalledger gl")
                .append(" WHERE gl.voucherheaderid = :vhId AND gl.id = gld.generalledgerid AND gl.creditamount > 0 AND gl.glcodeid NOT IN (:glcodeIdList)")
                .append(" GROUP BY gld.detailtypeid, gld.detailkeyid").toString())
                .setParameterList("glcodeIdList", cBillGlcodeIdList, BigDecimalType.INSTANCE)
                .setParameter("vhId", billVHId, LongType.INSTANCE);
        final List<Object[]> list = query.list();
        if (list != null && !list.isEmpty())
            for (final Object[] ob : list)
                map.put(ob[0].toString() + DELIMETER + ob[1].toString(), BigDecimal.valueOf(Double.valueOf(ob[2].toString())));
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Completed getSubledgerAmtForDeduction.");
        return map;
    }

    public void getGlcodeIds() throws ApplicationRuntimeException {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Starting getGlcodeIds...");
        try {
            List<AppConfigValues> appList;

            worksBillGlcodeList = populateGlCodeIds(Constants.WORKS_BILL_PURPOSE_IDS);
            purchaseBillGlcodeList = populateGlCodeIds(Constants.PURCHASE_BILL_PURPOSE_IDS);
            salaryBillGlcodeList = populateGlCodeIds("salaryBillPurposeIds");

            // Contingent Bill
            appList = appConfigValuesService.getConfigValuesByModuleAndKey(Constants.EGF, Constants.CONTINGENCY_BILL_PURPOSE_IDS);
            cBillGlcodeIdList = new ArrayList<BigDecimal>();
            if (appList != null && appList.size() > 0) {
                final Integer iPurposeIds[] = new Integer[appList.size()];
                int z = 0;
                for (final AppConfigValues appConfigValues : appList) {
                    iPurposeIds[z] = Integer.parseInt(appConfigValues.getValue());
                    z++;
                }
                final List<CChartOfAccounts> coaList = coaDAO.getAccountCodeByListOfPurposeId(iPurposeIds);
                if (LOGGER.isDebugEnabled())
                    LOGGER.debug("Size contingentBillGlcodeList" + coaList.size());
                contingentBillGlcodeList = coaList;
                for (final CChartOfAccounts coa1 : coaList)
                    // if(LOGGER.isDebugEnabled())
                    // LOGGER.debug("Adding to contingentBillGlcodeList"+coa1.getGlcode()+":::"+coa1.getPurposeId());
                    cBillGlcodeIdList.add(BigDecimal.valueOf(coa1.getId()));
            }
        } catch (final Exception e) {
            LOGGER.error(e.getMessage());
            throw new ApplicationRuntimeException(e.getMessage());
        }
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Completed getGlcodeIds.");
    }

    private List<CChartOfAccounts> populateGlCodeIds(final String appConfigKey) throws ApplicationException {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Starting populateGlCodeIds...");
        final List<CChartOfAccounts> glCodeList = new ArrayList<CChartOfAccounts>();

        final List<AppConfigValues> appList = appConfigValuesService.getConfigValuesByModuleAndKey(Constants.EGF, appConfigKey);
        final String purposeids = appList.get(0).getValue();
        if (purposeids != null && !purposeids.equals("")) {
            final String purposeIds[] = purposeids.split(",");
            for (final String purposeId : purposeIds) {
                final List<CChartOfAccounts> coaList = coaDAO.getAccountCodeByPurpose(Integer.parseInt(purposeId));
                for (final CChartOfAccounts coa1 : coaList)
                    glCodeList.add(coa1);
            }
        }
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Completed populateGlCodeIds.");
        return glCodeList;
    }

    public EntityType getEntity(final Integer detailTypeId, final Serializable detailKeyId) throws ApplicationException {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Starting getEntity...");
        EntityType entity;
        try {
            final Accountdetailtype accountdetailtype = (Accountdetailtype) persistenceService.find(
                    " from Accountdetailtype where id=?1", detailTypeId);
            final Class<?> service = Class.forName(accountdetailtype.getFullQualifiedName());
            // getting the entity type service.
            final String detailTypeName = service.getSimpleName();
            String dataType = "";
            final java.lang.reflect.Method method = service.getMethod("getId");
            dataType = method.getReturnType().getSimpleName();
            if (dataType.equals("Long"))
                entity = (EntityType) persistenceService.find(
                        String.format("from %s where id = ?1 order by name", detailTypeName), Long.valueOf(detailKeyId.toString()));
            else
                entity = (EntityType) persistenceService.find(
                        String.format("from %s where id = ?1 order by name", detailTypeName), Integer.valueOf(detailKeyId.toString()));
        } catch (final Exception e) {
            LOGGER.error("Exception to get EntityType=" + e.getMessage() + "for detailTypeId=" + detailTypeId
                    + "  for Detail key " + detailKeyId);
            throw new ApplicationException("Exception to get EntityType=" + e.getMessage());
        }
        if (entity == null) {
            LOGGER.error("Exception to get EntityType  for detailTypeId=" + detailTypeId + "  for Detail key " + detailKeyId);
            throw new ApplicationException("Exception to get EntityType");
        }
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Completed getEntity.");
        return entity;
    }

    private Long getLongValue(final Object object) {
        return object != null ? new Long(object.toString()) : 0;
    }
}