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


package com.exilant.eGov.src.transactions;

/**
 * This class defines common methods used for integration
 *
 * @author Tilak
 */


import com.exilant.exility.common.TaskFailedException;
import org.apache.log4j.Logger;
import org.egov.commons.CFiscalPeriod;
import org.egov.infra.persistence.utils.GenericSequenceNumberGenerator;
import org.egov.infstr.services.PersistenceService;
import org.egov.utils.VoucherHelper;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.Serializable;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.List;

public class CommonMethodsImpl implements CommonMethodsI {
    private static final Logger LOGGER = Logger.getLogger(CommonMethodsImpl.class);

    @Autowired
    @Qualifier("persistenceService")
    private PersistenceService persistenceService;

    @Autowired
    private GenericSequenceNumberGenerator genericSequenceNumberGenerator;

    /**
     * Get the cash in hand code account for the boundary
     */
    @Override
    public String getCashInHand(final int BoundaryId, final Connection connection) throws Exception {
        String cashinHandCode = "";
        try {
            final StringBuilder query = new StringBuilder("SELECT a.glcode ")
                    .append("FROM CHARTOFACCOUNTS a, EG_BOUNDARY b, eg_boundary_type c")
                    .append(" WHERE id = (SELECT cashinhand FROM CODEMAPPING WHERE EG_BOUNDARYID = :boundaryId )")
                    .append(" and b.ID_BNDRY_TYPE = c.ID_BNDRY_TYPE and b.ID_BNDRY= :boundaryId");
            List<Object[]> rset = persistenceService.getSession().createNativeQuery(query.toString())
                    .setParameter("boundaryId", BoundaryId, IntegerType.INSTANCE)
                    .list();
            for (final Object[] element : rset)
                cashinHandCode = element[0].toString();
            if (rset == null || rset.size() == 0)
                throw new Exception();
            if (LOGGER.isInfoEnabled())
                LOGGER.info(">>>cashinHandCode " + cashinHandCode);
        } catch (final Exception e) {
            LOGGER.error(" Glcode for cashinhand not found ", e);
            throw new Exception(e.toString());
        }
        return cashinHandCode;
    }

    /**
     * Get the Cheque in hand code account for the boundary
     */
    @Override
    public String getChequeInHand(final int BoundaryId, final Connection connection) throws Exception {
        String chequeinHandCode = "";
        try {
            final StringBuilder query = new StringBuilder("SELECT a.glcode")
                    .append("FROM CHARTOFACCOUNTS a, EG_BOUNDARY b, eg_boundary_type c")
                    .append(" WHERE id = (SELECT chequeinhand FROM CODEMAPPING WHERE EG_BOUNDARYID = :boundaryId)")
                    .append(" and b.ID_BNDRY_TYPE = c.ID_BNDRY_TYPE and b.ID_BNDRY = :boundaryId");
            List<Object[]> rset = persistenceService.getSession().createNativeQuery(query.toString())
                    .setParameter("boundaryId", BoundaryId, IntegerType.INSTANCE)
                    .list();
            for (final Object[] element : rset)
                chequeinHandCode = element[0].toString();
            if (rset == null || rset.size() == 0)
                throw new Exception("Chequeinhand Code not Found");
            if (LOGGER.isInfoEnabled())
                LOGGER.info(">>>chequeinHandCode " + chequeinHandCode);
        } catch (final Exception e) {
            LOGGER.error(" Glcode for chequeinHandCode not found ", e);
            throw new Exception(e.toString());
        }

        return chequeinHandCode;
    }

    /**
     * Get the property tax code for the year
     */
    @Override
    public String getPTCode(final String forYear, final Connection connection) throws Exception {
        String ptCodeAndName = "";
        try {
            if (!forYear.equalsIgnoreCase("")) {
                if (LOGGER.isInfoEnabled())
                    LOGGER.info("  forYear  " + forYear);
                final String query1 = "select financialyear from financialyear  where :forYear between startingdate and endingdate";
                List<Object[]> rset = persistenceService.getSession().createNativeQuery(query1)
                        .setParameter("forYear", forYear, StringType.INSTANCE)
                        .list();
                String fId = "", isOld = "";
                for (final Object[] element : rset)
                    fId = element[0].toString();
                if (rset == null || rset.size() == 0) {
                    final StringBuilder query2 = new StringBuilder("select a.glcode, a.name")
                            .append(" from chartofaccounts a, egf_tax_account_mapping b")
                            .append(" where b.glcodeid = a.id and upper(b.financialyear) = upper('old')");
                    rset = persistenceService.getSession().createNativeQuery(query2.toString())
                            .list();
                    for (final Object[] element : rset) {
                        ptCodeAndName = element[0].toString();
                        ptCodeAndName = ptCodeAndName + "#" + element[1].toString();
                        if (LOGGER.isInfoEnabled())
                            LOGGER.info(">>>ptCodeAndName " + ptCodeAndName);
                    }
                }
                if (!fId.equalsIgnoreCase("")) {
                    final StringBuilder query3 = new StringBuilder("select a.isold")
                            .append(" from egf_tax_account_mapping a, egf_tax_code b, financialyear c")
                            .append(" where a.taxcodeid = b.id and b.code = 'PT' and a.financialyear = c.financialyear and c.financialyear = :fyId");
                    rset = persistenceService.getSession().createNativeQuery(query3.toString())
                            .setParameter("fyId", fId, StringType.INSTANCE)
                            .list();
                    for (final Object[] element : rset) {
                        if (LOGGER.isInfoEnabled())
                            LOGGER.info("   inside 2    ");
                        isOld = element[0].toString();
                    }
                    if (isOld != null && isOld.equals("1")) {
                        if (LOGGER.isInfoEnabled())
                            LOGGER.info("   inside 4    ");
                        final StringBuilder query4 = new StringBuilder("select a.glcode, a.name")
                                .append(" from chartofaccounts a, egf_tax_account_mapping b")
                                .append(" where b.glcodeid = a.id and upper(b.financialyear) = upper('old')");
                        rset = persistenceService.getSession().createNativeQuery(query4.toString())
                                .list();
                        for (final Object[] element : rset) {
                            ptCodeAndName = element[0].toString();
                            ptCodeAndName = ptCodeAndName.concat("#").concat(element[1].toString());
                            if (LOGGER.isInfoEnabled())
                                LOGGER.info(">>>ptCodeAndName** " + ptCodeAndName);
                        }
                    } else {
                        if (LOGGER.isInfoEnabled())
                            LOGGER.info("   inside 5   ");
                        final StringBuilder query5 = new StringBuilder("select a.glcode, a.name")
                                .append(" from chartofaccounts a, egf_tax_account_mapping b, egf_tax_code c, financialyear d")
                                .append(" where b.taxcodeid = c.id and c.code = 'PT' and b.glcodeid = a.id and b.financialyear = d.financialyear")
                                .append(" and d.financialyear = :fyId");
                        rset = persistenceService.getSession().createNativeQuery(query5.toString())
                                .setParameter("fyId", fId, StringType.INSTANCE)
                                .list();
                        for (final Object[] element : rset) {
                            ptCodeAndName = element[0].toString();
                            ptCodeAndName = ptCodeAndName.concat("#").concat(element[1].toString());
                            if (LOGGER.isInfoEnabled())
                                LOGGER.info(">>>ptCodeAndName " + ptCodeAndName);
                        }
                        if (rset == null || rset.size() == 0)
                            throw new Exception("Property Tax code not Found for " + forYear);

                    }
                }
            } else {
                // if foryear is not given, then use Sespense code
                final StringBuilder query = new StringBuilder("select a.glcode, a.name")
                        .append(" from chartofaccounts a, egf_accountcode_purpose b")
                        .append(" where a.purposeid = b.id and upper(b.name) = upper('SuspenseCode')");
                List<Object[]> rset = persistenceService.getSession().createNativeQuery(query.toString())
                        .list();
                for (final Object[] element : rset) {
                    ptCodeAndName = element[0].toString();
                    ptCodeAndName = ptCodeAndName.concat("#").concat(element[1].toString());
                    if (LOGGER.isInfoEnabled())
                        LOGGER.info(">>>ptCodeAndName1 " + ptCodeAndName);
                }
                if (rset == null || rset.size() == 0)
                    throw new Exception("Property Tax code not Found for " + forYear);
            }
        } catch (final Exception e) {
            LOGGER.error(" PT code not found ", e);
            throw new Exception(e.toString());
        }
        return ptCodeAndName;
    }

    /**
     * Get the account code for the bank account
     */
    @Override
    public String getBankCode(final int bankAccountId, final Connection connection) throws Exception {
        String bankCodeAndName = "";
        try {
            final String query = "select glcode, name from chartofaccounts where id = (select glcodeid from bankaccount where id = :id)";
            List<Object[]> rset = persistenceService.getSession().createNativeQuery(query)
                    .setParameter("id", bankAccountId, IntegerType.INSTANCE)
                    .list();
            for (final Object[] element : rset) {
                bankCodeAndName = element[0].toString();
                bankCodeAndName = bankCodeAndName.concat("#").concat(element[1].toString());
                if (LOGGER.isInfoEnabled())
                    LOGGER.info(">>>bankCodeAndName " + bankCodeAndName);
            }
            if (rset == null || rset.size() == 0)
                throw new Exception("BAnk Code Not Found");
        } catch (final Exception e) {
            LOGGER.error(" Bank code not found ", e);
            throw new Exception(e.toString());
        }
        return bankCodeAndName;
    }

    /**
     * Get the fiscal period id for the date passed
     */
    @Override
    public String getFiscalPeriod(final String vDate, final Connection connection) throws TaskFailedException, Exception {
        String fiscalPeriodID = "null";
        try {
            final String query = "select id from fiscalperiod  where :vDate between startingdate and endingdate";
            List<Object[]> rset = persistenceService.getSession().createNativeQuery(query)
                    .setParameter("vDate", vDate, StringType.INSTANCE)
                    .list();
            for (final Object[] element : rset) {
                fiscalPeriodID = element[0].toString();
                if (LOGGER.isInfoEnabled())
                    LOGGER.info(">>>fiscalPeriodID " + fiscalPeriodID);
            }
            if (rset == null || rset.size() == 0)
                throw new TaskFailedException("fiscal Period Not Found");
        } catch (final TaskFailedException e) {
            LOGGER.error("fiscal Period Not Found = ", e);
            throw new TaskFailedException("fiscal Period Not Found");
        } catch (final Exception e) {
            LOGGER.error("failed to get fiscalperiodId ", e);
            throw new Exception(e.toString());
        }
        return fiscalPeriodID;
    }

    /**
     * Get the bank and bank branch of the bank acocunt
     */
    @Override
    public String getBankId(final int bankAccountId, final Connection connection) throws Exception {
        String bankAndBranchId = "null";
        try {
            final String sql = "select b.id, c.id from bankaccount a, bankbranch b, bank c where a.branchid = b.id and b.bankid = c.id and a.id = :id";
            List<Object[]> rset = persistenceService.getSession().createNativeQuery(sql)
                    .setParameter("id", bankAccountId, IntegerType.INSTANCE)
                    .list();
            for (final Object[] element : rset) {
                bankAndBranchId = element[0].toString();
                bankAndBranchId = bankAndBranchId.concat("#").concat(element[1].toString());
                if (LOGGER.isInfoEnabled())
                    LOGGER.info(">>>bankAndBranchId " + bankAndBranchId);
            }
            if (rset == null || rset.size() == 0)
                throw new Exception("Bank Code Not Found");
        } catch (final Exception e) {
            LOGGER.error(" Bank Id not found ", e);
            throw new Exception(e.toString());
        }
        return bankAndBranchId;
    }

    /**
     * Get the bank balance for the bank account
     */
    @Override
    public double getAccountBalance(final int bankAccountId, final String vcDate, final Connection connection) throws Exception {
        double opeAvailable = 0, totalAvailable = 0;
        try {
            final StringBuilder str = new StringBuilder("SELECT case when sum(openingDebitBalance) = null then 0 ELSE sum(openingDebitBalance) end")
                    .append(" - case when sum(openingCreditBalance) = null then 0 else sum(openingCreditBalance) end AS \"openingBalance\" ")
                    .append(" FROM transactionSummary")
                    .append(" WHERE financialYearId = (SELECT id FROM financialYear WHERE startingDate <= :vcDate")
                    .append(" AND endingDate >= :vcDate) AND glCodeId = (select glcodeid from bankaccount where id = :bankAccountId)");
            if (LOGGER.isInfoEnabled())
                LOGGER.info(str);
            List<Object[]> rset = persistenceService.getSession().createNativeQuery(str.toString())
                    .setParameter("vcDate", vcDate, StringType.INSTANCE)
                    .setParameter("bankAccountId", bankAccountId, IntegerType.INSTANCE)
                    .list();
            for (final Object[] element : rset)
                opeAvailable = Double.parseDouble(element[0].toString());
            if (LOGGER.isInfoEnabled())
                LOGGER.info("opening balance  " + opeAvailable);

            final StringBuilder str1 = new StringBuilder("SELECT (case when sum(gl.debitAmount) = null then 0 else sum(gl.debitAmount) end")
                    .append(" - case when sum(gl.creditAmount) = null then 0 else sum(gl.creditAmount) end) + ")
                    .append(opeAvailable)
                    .append(" as \"totalAmount\"")
                    .append(" FROM generalLedger gl, voucherHeader vh")
                    .append(" WHERE vh.id = gl.voucherHeaderId AND gl.glCodeid = (select glcodeid from bankaccount where id = :bankAccountId) AND")
                    .append(" vh.voucherDate >= (SELECT TO_CHAR(startingDate, 'dd-Mon-yyyy') FROM financialYear WHERE startingDate <= :vcDate")
                    .append(" AND endingDate >= :vcDate) AND vh.voucherDate <= :vcDate");
            if (LOGGER.isInfoEnabled())
                LOGGER.info(str1);
            rset = persistenceService.getSession().createNativeQuery(str1.toString())
                    .setParameter("bankAccouuntId", bankAccountId, IntegerType.INSTANCE)
                    .setParameter("vcDate", vcDate, StringType.INSTANCE)
                    .list();
            for (final Object[] element : rset) {
                totalAvailable = Double.parseDouble(element[0].toString());
                if (LOGGER.isInfoEnabled())
                    LOGGER.info("total balance  " + totalAvailable);
            }

        } catch (final Exception e) {
            LOGGER.error(" could not get Bankbalance  ", e);
            throw new Exception(e.toString());
        }
        return totalAvailable;
    }

    /**
     * Get the acccount code and name for the account with the purposeid
     */
    @Override
    public String getCodeName(final String purposeId) throws Exception {
        String codeAndName = "null";
        try {
            final StringBuilder query = new StringBuilder("select a.glcode, a.name from chartofaccounts a, egf_accountcode_purpose b where a.purposeid = b.id and b.id = :purposeId");
            List<Object[]> rset = persistenceService.getSession().createNativeQuery(query.toString())
                    .setParameter("purposeId", purposeId, StringType.INSTANCE)
                    .list();
            // for(int i=0;rset.next();i++){
            for (final Object[] element : rset) {
                codeAndName = element[0].toString();
                codeAndName = codeAndName.concat("#").concat(element[1].toString());
            }

        } catch (final Exception e) {
            LOGGER.error(" code not found for purpose id ", e);
            throw new Exception(e.toString());
        }
        return codeAndName;
    }

    /**
     * Get the name of the accountcode passed
     */
    @Override
    public String getNameFromCode(final String glcode, final Connection connection) throws Exception {

        String codeName = "null";
        try {
            final StringBuilder query = new StringBuilder("select name from chartofaccounts where glcode = :glcode");
            if (LOGGER.isInfoEnabled())
                LOGGER.info("  query   " + query);
            List<Object[]> rset = persistenceService.getSession().createNativeQuery(query.toString())
                    .setParameter("glcode", glcode, StringType.INSTANCE)
                    .list();
            for (final Object[] element : rset) {
                codeName = element[0].toString();
                if (LOGGER.isInfoEnabled())
                    LOGGER.info("  codeName   " + codeName);
            }
            if (rset == null || rset.size() == 0)
                throw new Exception("code not found");
        } catch (final Exception e) {
            LOGGER.error(" code not found ", e);
            throw new Exception(e.toString());
        }
        return codeName;
    }

    /**
     * Get the accountcode of the is paased.
     */
    @Override
    public String getGlCode(final String glCodeId, final Connection connection) throws Exception {
        String glCode = "null";
        try {
            final StringBuffer query = new StringBuffer("select glcode from chartofaccounts where id = :glcode");
            if (LOGGER.isInfoEnabled())
                LOGGER.info("  query   " + query);
            List<Object[]> rset = persistenceService.getSession().createNativeQuery(query.toString())
                    .setParameter("glcode", glCodeId)
                    .list();
            for (final Object[] element : rset) {
                glCode = element[0].toString();
                if (LOGGER.isInfoEnabled())
                    LOGGER.info("  glCode   " + glCode);
            }
            if (rset == null || rset.size() == 0)
                throw new Exception("id not found");
        } catch (final Exception e) {
            LOGGER.error(" id not found ", e);
            throw new Exception(e.toString());
        }
        return glCode;
    }

    /**
     * This is to check if a record already exist in integrationlog
     */
    @Override
    public String checkRecordIdInLog(final String recordId, final int userId, final Connection connection) throws Exception {
        String cgn = null;
        try {
            final StringBuilder query = new StringBuilder("SELECT VOUCHERNUMBER FROM integrationlog WHERE RECORDID = :recordId and USERID = :userId order by id desc");
            if (LOGGER.isInfoEnabled())
                LOGGER.info("  query   " + query);
            List<Object[]> rset = persistenceService.getSession().createNativeQuery(query.toString())
                    .setParameter("recordId", recordId, StringType.INSTANCE)
                    .setParameter("userId", userId, IntegerType.INSTANCE)
                    .list();
            for (final Object[] element : rset) {
                cgn = element[0].toString();
                if (LOGGER.isInfoEnabled())
                    LOGGER.info("  cgn in log  " + cgn);
            }
        } catch (final Exception e) {
            LOGGER.error("Exp=", e);
            throw new Exception(e.toString());
        }
        return cgn;
    }

    /**
     * Get the division code of the id passed
     */
    @Override
    public String getDivisionCode(final Integer divid, final Connection connection) throws Exception {
        String divCode = null;
        final String sql = "SELECT distinct BNDRY_NUM FROM EG_BOUNDARY where ID_BNDRY = :divId";
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Divisio code query-->>>>>>>> " + sql);
        try {
            List<Object[]> rset = persistenceService.getSession().createNativeQuery(sql)
                    .setParameter("divId", divid, IntegerType.INSTANCE)
                    .list();
            for (final Object[] element : rset) {
                divCode = element[0].toString();
                if (LOGGER.isDebugEnabled())
                    LOGGER.debug("divCode >>>>>>>" + divCode);
            }

        } catch (final Exception e) {
            LOGGER.error("Exp=", e);
            throw new Exception(e.toString());
        }
        return divCode;
    }

    /**
     * Get the division id for the sub field passed
     */
    @Override
    public Integer getDivisionId(final Integer fieldId, final Connection connection) throws Exception {
        Integer divId = null;
        final String sql = "Select PARENT from EG_BOUNDARY where ID_BNDRY = :id";
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Division id query-->>>>>>>> " + sql);
        try {

            List<Object[]> rset = persistenceService.getSession().createNativeQuery(sql)
                    .setParameter("id", fieldId, IntegerType.INSTANCE)
                    .list();
            for (final Object[] element : rset)
                divId = Integer.parseInt(element[0].toString());
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("Division id is >>>>>>>" + divId);
        } catch (final Exception e) {
            LOGGER.error("Exp=", e);
            throw new Exception(e.toString());
        }
        return divId;
    }

    /**
     * Get the financial year for the data passed
     */
    @Override
    public String getFinacialYear(final String vDate, final Connection connection) throws Exception {
        String finYear = "null";
        final String sql = "select FINANCIALYEAR from FINANCIALYEAR  where :vDate between startingdate and endingdate";
        try {
            List<Object[]> rset = persistenceService.getSession().createNativeQuery(sql)
                    .setParameter("vDate", vDate, StringType.INSTANCE)
                    .list();
            for (final Object[] element : rset) {
                finYear = element[0].toString();
                if (LOGGER.isDebugEnabled())
                    LOGGER.debug("finYear id>>>>>>>" + finYear);
            }

        } catch (final Exception e) {
            LOGGER.error("Exp=", e);
            throw new Exception(e.toString());
        }
        return finYear;
    }

    /**
     * This method gets the GlCodeId by passing GLCODE as parameter added by Sapna
     */
    @Override
    public String getGlCodeId(final String glCode, final Connection connection) throws Exception {
        String glCodeId = "null";
        try {
            final String query = "select id from chartofaccounts where glCode like :glcode";
            if (LOGGER.isInfoEnabled())
                LOGGER.info("  query   " + query);
            List<Object[]> rset = persistenceService.getSession().createNativeQuery(query)
                    .setParameter("glcode", glCode, StringType.INSTANCE)
                    .list();
            for (final Object[] element : rset) {
                glCodeId = element[0].toString();
                if (LOGGER.isDebugEnabled())
                    LOGGER.debug("  glCodeId   " + glCodeId);
            }
            if (rset == null || rset.size() == 0)
                throw new Exception("id not found");
        } catch (final Exception e) {
            LOGGER.error(" id not found ", e);
            throw new Exception(e.toString());
        }
        return glCodeId;
    }

    /**
     * This API will return the transaction no for any type of txn. Input :Type,transaction date and connection Output
     * :Transaction number in the format txnType+number+/+month+/+year
     */
    @Override
    public String getTxnNumber(final String txnType, final String vDate) throws Exception {
        String finYear = "";
        String fiscalPeriod = "";
        String retVal = "";

        final String month[] = vDate.split("/");
        if (LOGGER.isInfoEnabled())
            LOGGER.info(" The month :" + month[1]);
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        final SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        final String txndate = formatter.format(sdf.parse(vDate));

        final StringBuilder sql = new StringBuilder("select a.FINANCIALYEAR, b.id")
                .append(" from FINANCIALYEAR a, fiscalperiod b")
                .append(" where a.id = b.financialyearid AND :txnDate between b.startingdate and b.endingdate");
        if (LOGGER.isInfoEnabled())
            LOGGER.info(sql);
        try {
            List<Object[]> rset = persistenceService.getSession().createNativeQuery(sql.toString())
                    .setParameter("txnDate", txndate)
                    .list();
            for (final Object[] element : rset) {
                finYear = element[0].toString();
                fiscalPeriod = element[1].toString();
                if (LOGGER.isInfoEnabled())
                    LOGGER.info("finYear id>>>>>>>" + finYear + " fiscalPeriod :" + fiscalPeriod);
            }
            if (rset == null || rset.size() == 0)
                throw new Exception("Year is not defined in the system");

            final String year = finYear.substring(2, 4).concat(finYear.substring(finYear.length() - 2, finYear.length()));
            if (LOGGER.isInfoEnabled())
                LOGGER.info(" The year String :" + year);
            // ---
            if (LOGGER.isDebugEnabled())
                LOGGER.debug(" In CommonMethodsImpl :getTxnNumber method ");
            //persistenceService.setType(CFiscalPeriod.class);
            final CFiscalPeriod fiscalPeriodObj = (CFiscalPeriod) persistenceService.find("from CFiscalPeriod where id = ?1",
                    Long.parseLong(fiscalPeriod));
            // Sequence name will be SQ_U_DBP_CGVN_FP7 for txnType U/DBP/CGVN and fiscalPeriodIdStr 7
            final String sequenceName = VoucherHelper.sequenceNameFor(txnType, fiscalPeriodObj.getName());
            Serializable runningNumber = genericSequenceNumberGenerator.getNextSequence(sequenceName);
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("----- Txn Number : " + runningNumber);
            // ---

            retVal = new StringBuilder(txnType).append(runningNumber).append("/").append(month[1]).append("/").append(year).toString();
            if (LOGGER.isInfoEnabled())
                LOGGER.info("Return value is :" + retVal);

        } catch (final Exception e) {
            LOGGER.error("Exp = ", e);
            throw new Exception(e.toString());
        }
        return retVal;
    }

    /**
     * added by Iliyaraja This API will return the generated Voucher number. Input :Fund Id,txnType,transaction date and
     * connection Output :Transaction number in the format fundType+txnType+/+number+/+month+/+year
     */
    @Override
    public String getTxnNumber(final String fundId, String txnType, final String vDate, final Connection con) throws Exception {
        if (txnType == null || txnType.equals(""))
            throw new Exception("Configuration setting for voucher numbering is not done");

        String fType = "";
        String finYear = "";
        String fiscalPeriod = "";
        String retVal = "";

        final String month[] = vDate.split("/");
        if (LOGGER.isInfoEnabled())
            LOGGER.info(" The month :" + month[1]);
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        final SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        final String txndate = formatter.format(sdf.parse(vDate));

        final StringBuilder sql = new StringBuilder("select a.FINANCIALYEAR, b.id")
                .append(" from FINANCIALYEAR a, fiscalperiod b")
                .append(" where a.id = b.financialyearid AND :txndate between b.startingdate and b.endingdate");
        if (LOGGER.isInfoEnabled())
            LOGGER.info(sql);
        try {
            // This is for getting fund type based on the fund id.
            final String query = "SELECT identifier as \"fund_identi\" from fund where id = :fundId";
            List<Object[]> rset = persistenceService.getSession().createNativeQuery(query)
                    .setParameter("fundId", fundId)
                    .list();
            for (final Object[] element : rset) {
                fType = element[0].toString();
                if (LOGGER.isInfoEnabled())
                    LOGGER.info("Fund Id--->" + fundId + " Fund Type---->" + fType);
            }
            if (rset == null || rset.size() == 0)
                throw new Exception("Fund is not defined in the system");

            rset = persistenceService.getSession().createNativeQuery(sql.toString())
                    .setParameter("txndate", txndate, StringType.INSTANCE)
                    .list();
            for (final Object[] element : rset) {
                finYear = element[0].toString();
                fiscalPeriod = element[1].toString();
                if (LOGGER.isInfoEnabled())
                    LOGGER.info("finYear id>>>>>>>" + finYear + " fiscalPeriod :" + fiscalPeriod);
            }
            if (rset == null || rset.size() == 0)
                throw new Exception("Year is not defined in the system");

            final String year = finYear.substring(2, 4).concat(finYear.substring(finYear.length() - 2, finYear.length()));
            if (LOGGER.isInfoEnabled())
                LOGGER.info(" The year String :" + year);
            txnType = fType.concat(txnType);

            // ---
            if (LOGGER.isDebugEnabled())
                LOGGER.debug(" In CommonMethodsImpl :getTxnNumber method ");
            final CFiscalPeriod fiscalPeriodObj = (CFiscalPeriod) persistenceService.find("from CFiscalPeriod where id = ?1",
                    Long.parseLong(fiscalPeriod));
            // Sequence name will be SQ_U_DBP_CGVN_FP7 for txnType U/DBP/CGVN and fiscalPeriodIdStr 7
            final String sequenceName = VoucherHelper.sequenceNameFor(txnType, fiscalPeriodObj.getName());
            Serializable runningNumber = genericSequenceNumberGenerator.getNextSequence(sequenceName);
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("----- Txn Number : " + runningNumber);
            // ---

            retVal = new StringBuilder(txnType).append("/").append(runningNumber.toString()).append("/").append(month[1])
                    .append("/").append(year).toString();
            if (LOGGER.isInfoEnabled())
                LOGGER.info("Return value is :" + retVal);

        } catch (final Exception e) {
            LOGGER.error("Exp = ", e);
            throw new Exception(e.toString());
        }
        return retVal;
    }

    /*
     * This API will return the running number. Parameter : Fund Id,txnType,transaction date and connection return : String
     * transaction number.
     */
    @Override
    public String getTransRunningNumber(final String fundId, String txnType, final String vDate, final Connection con)
            throws Exception {

        if (txnType == null || txnType.equals(""))
            throw new Exception("Configuration setting for voucher numbering is not done");

        String fType = "";
        String finYear = "";
        String fiscalPeriod = "";
        String retVal = "";

        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        final SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        final String txndate = formatter.format(sdf.parse(vDate));

        final StringBuilder sql = new StringBuilder("select a.FINANCIALYEAR, b.id")
                .append(" from FINANCIALYEAR a, fiscalperiod b")
                .append(" where a.id = b.financialyearid AND :txndate between b.startingdate and b.endingdate");
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("sql in getTransRunningNumber() :" + sql);
        try {
            // This is for getting fund type based on the fund id.
            final String query = "SELECT identifier as \"fund_identi\" from fund where id = :fundId";
            List<Object[]> rset = persistenceService.getSession().createNativeQuery(query)
                    .setParameter("fundId", fundId, StringType.INSTANCE)
                    .list();
            for (final Object[] element : rset) {
                fType = element[0].toString();
                if (LOGGER.isInfoEnabled())
                    LOGGER.info("Fund Id  :--->" + fundId + " Fund Type  :---->" + fType);
            }
            if (rset == null || rset.size() == 0)
                throw new Exception("Fund is not defined in the system");
            rset = persistenceService.getSession().createNativeQuery(sql.toString())
                    .setParameter("txndate", txndate, StringType.INSTANCE)
                    .list();
            for (final Object[] element : rset) {
                finYear = element[0].toString();
                fiscalPeriod = element[1].toString();
                if (LOGGER.isDebugEnabled())
                    LOGGER.debug("finYear id>>>>>>>" + finYear + " fiscalPeriod :" + fiscalPeriod);
            }
            if (rset == null || rset.size() == 0)
                throw new Exception("Year is not defined in the system");

            txnType = fType.concat(txnType);
            // ---
            if (LOGGER.isDebugEnabled())
                LOGGER.debug(" In CommonMethodsImpl :getTxnNumber method ");
            //persistenceService.setType(CFiscalPeriod.class);
            final CFiscalPeriod fiscalPeriodObj = (CFiscalPeriod) persistenceService.find("from CFiscalPeriod where id = ?1",
                    Long.parseLong(fiscalPeriod));
            // Sequence name will be SQ_U_DBP_CGVN_FP7 for txnType U/DBP/CGVN and fiscalPeriodIdStr 7
            final String sequenceName = VoucherHelper.sequenceNameFor(txnType, fiscalPeriodObj.getName());
            Serializable runningNumber = genericSequenceNumberGenerator.getNextSequence(sequenceName);
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("----- Running Number : " + runningNumber);
            // ---
            retVal = runningNumber.toString();
            if (LOGGER.isInfoEnabled())
                LOGGER.info("Return value is in getTransRunningNumber() :" + retVal);

        } catch (final Exception e) {
            LOGGER.error("Exp occured in getTransRunningNumber() :", e);
            throw new Exception(e.toString());
        }
        return retVal;
    }

    /**
     * This API will return the sub feild id when the boundary num is passed. We are ordering by id_bndry_type desc so that sub
     * field value will come first.
     */
    @Override
    public Integer getDivisionIdFromCode(final String divisionCode, final Connection connection) throws Exception {
        Integer divId = null;
        final StringBuilder sql = new StringBuilder("Select id_bndry from EG_BOUNDARY where BNDRY_NUM = :divCode and is_bndry_active = 1 order by id_bndry_type desc");
        if (LOGGER.isInfoEnabled())
            LOGGER.info("Sub Field id query-->>>>>>>> " + sql);
        List<Object[]> rset = persistenceService.getSession().createNativeQuery(sql.toString())
                .setParameter("divCode", divisionCode, StringType.INSTANCE)
                .list();
        for (final Object[] element : rset)
            divId = Integer.parseInt(element[0].toString());
        if (LOGGER.isInfoEnabled())
            LOGGER.info("Sub Feild id is >>>>>>>" + divId);
        return divId;
    }

}