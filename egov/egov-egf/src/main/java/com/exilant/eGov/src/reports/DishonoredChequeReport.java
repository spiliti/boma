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
/*
 * Created on June 2,2008
 * @author Iliyaraja S
 */
package com.exilant.eGov.src.reports;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.egov.utils.FinancialConstants;
import org.springframework.beans.factory.annotation.Autowired;

import com.exilant.exility.common.TaskFailedException;

public class DishonoredChequeReport {
    private static final Logger LOGGER = Logger.getLogger(DishonoredChequeReport.class);
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS", Locale.getDefault());
    public String originalVcId[];
    public String bankRefNo[];
    public String bankCharge[];
    List<Object[]> rs;
    TaskFailedException taskExc;
    String chqFromDate = "", chqToDate = "";
    String chequeNo = "";
    String instrumentMode = "";
    Long fundId;
    List<Map<String, String>> arList = new ArrayList<>();
    java.util.Date dt = new java.util.Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
    @Autowired
    CommnFunctions commnFunctions;

    @PersistenceContext
    private EntityManager entityManager;

    public DishonoredChequeReport() {
    }

    // get all Dishonored Cheque details -- chqstatus=2,isReconciled=1,isDishonored=1

    @SuppressWarnings("unchecked")
    public List<Map<String, String>> getDishonoredChequeDetails(final DishonoredChequeBean disChqBean)
            throws TaskFailedException, Exception {
        if (LOGGER.isInfoEnabled())
            LOGGER.info(" INSIDE getDishonoredChequeDetails()>>>>>>>> ");
        try {
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("Chq From date---->" + disChqBean.getStartDate());
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("Chq To date------>" + disChqBean.getEndDate());
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("Fund id--->" + disChqBean.getFundLst());
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("Mode --->" + disChqBean.getMode());
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("Cheque Number --->" + disChqBean.getChequeNo());
            if (!"".equals(disChqBean.getStartDate())) {
                dt = sdf.parse(disChqBean.getStartDate());
                chqFromDate = formatter.format(dt);
                if (LOGGER.isInfoEnabled())
                    LOGGER.info("After convert Chq From date is--->" + chqFromDate);
            }
            if (!"".equals(disChqBean.getEndDate())) {
                dt = sdf.parse(disChqBean.getEndDate());
                chqToDate = formatter.format(dt);
                if (LOGGER.isInfoEnabled())
                    LOGGER.info("After convert Chq To date is--->" + chqToDate);
            }
            if (!"".equals(disChqBean.getFundLst()))
                fundId = Long.parseLong(disChqBean.getFundLst());

            if (!"".equals(disChqBean.getChequeNo()))
                chequeNo = disChqBean.getChequeNo();

            if (!"".equals(disChqBean.getMode()))
                // intrumentMode=disChqBean.getMode();
                if (disChqBean.getMode().equals("1"))
                    instrumentMode = FinancialConstants.INSTRUMENT_TYPE_DD;
                else
                    instrumentMode = FinancialConstants.INSTRUMENT_TYPE_CHEQUE;

            // This method for getting bank ref no,bank charge amount and old voucher header id for the Dishonored cheques
            getBankEntryDetails();

            final StringBuilder basicquery1 = new StringBuilder(
                    "SELECT distinct vh.id as \"voucherHeaderId\", vh.id as \"payinVHeaderId\",")
                            .append(" vh.cgn as \"cgnumber\", vh.VOUCHERNUMBER as \"voucherNumber\", vh.TYPE as \"type\",")
                            .append(" vh.FUNDID as \"fundId\", vh.FUNDSOURCEID as \"fundSourceId\", ih.INSTRUMENTNUMBER as \"chequeNumber\",")
                            .append(" ih.INSTRUMENTDATE as \"chequeDate\", ih.INSTRUMENTAMOUNT as \"amount\", bank.NAME as \"bank\",")
                            .append(" bacc.ACCOUNTNUMBER as \"accNumber\", bacc.ID as \"accIdParam\",")
                            .append(" ih.PAYTO as \"payTo\", ih.ISPAYCHEQUE AS \"payCheque\", vmis.DEPARTMENTID AS \"departmentId\",")
                            .append(" vmis.FUNCTIONARYID AS \"functionaryId\", iod.INSTRUMENTSTATUSDATE as \"recChequeDate\",")
                            .append(" iod.dishonorbankrefno as \"dishonorBankRefNo\", status.description as \"status\"")
                            .append(" FROM VOUCHERHEADER vh, egf_instrumentheader ih, BANK bank, BANKACCOUNT bacc, VOUCHERMIS vmis,")
                            .append(" bankbranch branch, egf_instrumenttype it, EGF_INSTRUMENTVOUCHER iv, egf_instrumentotherdetails iod,")
                            .append(" egw_status status ");

            StringBuilder wherequery1 = new StringBuilder(
                    " WHERE vh.status = 0 AND vh.id = vmis.voucherheaderid and ih.INSTRUMENTTYPE = it.id")
                            .append(" and it.TYPE = :instrumentMode and iv.VOUCHERHEADERID = vh.ID and iv.INSTRUMENTHEADERID = ih.id")
                            .append(" and iod.instrumentheaderid = ih.id and ih.id_status = status.id and status.moduletype = 'Instrument'")
                            .append(" and status.description in ('Dishonored', 'dishonour cheque in workflow')")
                            .append(" and ih.BANKACCOUNTID = bacc.id and bacc.BRANCHID = branch.id")
                            .append(" and branch.BANKID = bank.id");

            final StringBuffer orderbyquery = new StringBuffer(" ORDER BY \"voucherNumber\", \"type\", \"chequeDate\" ");

            if (fundId != null && fundId != 0)
                wherequery1 = wherequery1.append(" AND vh.FUNDID = :fundId");

            if (!"".equals(chqFromDate))
                wherequery1 = wherequery1.append(" AND ih.INSTRUMENTDATE  >= :chqFromDate");
            if (!"".equals(chqToDate))
                wherequery1 = wherequery1.append(" AND ih.INSTRUMENTDATE  <= :chqToDate");
            if (!"".equals(chequeNo))
                wherequery1 = wherequery1.append(" AND ih.INSTRUMENTNUMBER = trim(:chequeNo)");

            final StringBuilder query = new StringBuilder()
                    .append(basicquery1)
                    .append(wherequery1)
                    .append(orderbyquery);
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("  getDishonoredChequeDetails Query is  " + query);
            rs = entityManager.createNativeQuery(query.toString())
                    .setParameter("instrumentMode", instrumentMode)
                    .setParameter("fundId", fundId)
                    .setParameter("chqFromDate", chqFromDate)
                    .setParameter("chqToDate", chqToDate)
                    .setParameter("chequeNo", chequeNo)
                    .getResultList();
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("After Execute Query----getDishonoredChequeDetails");
            int i = 1;
            for (final Object[] element : rs) {
                boolean bkChgAvailable = false;
                String voucherHeaderId = "", payinSlipVHeaderId = "", cgnum = "", voucherNumber = "", voucherType = "";
                String fundId = "", chequeNumber = "", chequeDate = "", amount = "", bankName = "", accIdParam = "";
                String recChequeDate = "", payeeName = "";
                String bankReferenceNo = "", status = "";
                final Map<String, String> data = new HashMap<>();

                if (element[0].toString() != null)
                    voucherHeaderId = element[0].toString();
                else
                    voucherHeaderId = "&nbsp;";

                // for bank charges
                for (int k = 0; k < originalVcId.length; k++)
                    if (originalVcId[k].equals(voucherHeaderId)) {
                        bkChgAvailable = true;
                        // data.put("bankRefNumber",bankRefNo[k] );
                        data.put("bankChargeAmt", bankCharge[k]);
                    }
                if (!bkChgAvailable)
                    // data.put("bankRefNumber","&nbsp;" );
                    data.put("bankChargeAmt", "&nbsp;");
                if (element[1].toString() != null)
                    payinSlipVHeaderId = element[1].toString();
                else
                    payinSlipVHeaderId = "&nbsp;";

                if (element[18].toString() != null)
                    bankReferenceNo = element[18].toString();
                else
                    bankReferenceNo = "&nbsp;";

                if (element[2].toString() != null)
                    cgnum = element[2].toString();
                else
                    cgnum = "&nbsp;";

                if (element[3].toString() != null)
                    voucherNumber = element[3].toString();
                else
                    voucherNumber = "&nbsp;";

                if (element[4].toString() != null)
                    voucherType = element[4].toString();
                else
                    voucherType = "&nbsp;";

                if (element[5].toString() != null)
                    fundId = element[5].toString();
                else
                    fundId = "&nbsp;";
                if (element[7].toString() != null)
                    chequeNumber = element[7].toString();
                else
                    chequeNumber = "&nbsp;";
                if (element[8].toString() != null) {
                    dt = format.parse(element[8].toString());
                    chequeDate = formatter.format(dt);
                } else
                    chequeDate = "&nbsp;";
                if (element[9].toString() != null)
                    amount = element[9].toString();
                else
                    amount = "&nbsp;";
                if (element[10].toString() != null)
                    bankName = element[10].toString();
                else
                    bankName = "&nbsp;";
                if (element[12].toString() != null)
                    accIdParam = element[12].toString();
                else
                    accIdParam = "&nbsp;";
                if (element[17].toString() != null) {
                    dt = format.parse(element[17].toString());
                    recChequeDate = formatter.format(dt);
                } else
                    recChequeDate = "&nbsp;";
                if (element[13].toString() != null)
                    payeeName = element[13].toString();
                else
                    payeeName = "&nbsp;";
                if (element[19].toString() != null)
                    status = element[19].toString();
                else
                    status = "&nbsp;";
                // insMode
                data.put("voucherHeaderId", voucherHeaderId);
                data.put("payinSlipVHeaderId", payinSlipVHeaderId);
                data.put("cgnum", cgnum);
                data.put("insMode", instrumentMode);
                data.put("voucherNumber", voucherNumber);
                data.put("bankRefNumber", bankReferenceNo);
                data.put("voucherType", voucherType);
                data.put("fundId", fundId);
                data.put("chequeNumber", chequeNumber);
                data.put("chequeDate", chequeDate);
                data.put("amount", amount);
                data.put("bankName", bankName);
                data.put("accIdParam", accIdParam);
                data.put("recChequeDate", recChequeDate);
                data.put("payeeName", payeeName);
                data.put("status", status);
                data.put("serialNo", i + "");
                i++;

                arList.add(data);

            }// while
        } catch (final SQLException sqlE) {
            LOGGER.error("Exception in main ", sqlE);
            throw taskExc;
        }
        return arList;
    }// main method for getting Dishonored cheque details

    /**
     * This function executes the Query-get the Bank charges and bank ref no for the dishonored cheques.
     */

    @SuppressWarnings("unchecked")
    private void getBankEntryDetails() throws Exception {
        try {
            final StringBuilder basicquery1 = new StringBuilder("SELECT rvh.id AS \"voucherHeaderId\", rvh.cgn AS \"cgnumber\",")
                    .append(" rvh.VOUCHERNUMBER AS \"voucherNumber\", rvh.TYPE AS \"vouType\", rvh.FUNDID AS \"fundId\",")
                    .append(" rvh.ORIGINALVCID AS \"oldVhId\",")
                    .append(" be.REFNO AS \"bankRefNumber\", be.TXNDATE AS \"bankRefDate\", be.TXNAMOUNT AS \"bankChargeAmt\"")
                    .append(" FROM VOUCHERHEADER rvh, bankentries be");
            StringBuilder wherequery1 = new StringBuilder(" WHERE rvh.NAME = 'Bank Entry' AND rvh.ID = be.VOUCHERHEADERID ")
                    .append(" AND rvh.ORIGINALVCID != 0 AND rvh.TYPE = 'Payment'");
            final StringBuilder orderbyquery = new StringBuilder(" ORDER BY \"oldVhId\" ");

            if (fundId != null && fundId != 0)
                wherequery1.append(" AND rvh.FUNDID = :fundId");

            if (!chqFromDate.equals("") && !chqToDate.equals("")) {
                if (LOGGER.isInfoEnabled())
                    LOGGER.info(" INSIDE FROM AND TO DATE ");
                wherequery1.append(" AND be.txndate BETWEEN to_date(:chqFromDate) and to_date(:chqToDate)");
            } else {
                if (LOGGER.isInfoEnabled())
                    LOGGER.info(" INSIDE FROM OR TO DATE ");
                if (!chqFromDate.equals(""))
                    wherequery1.append(" AND be.txndate >= :chqFromDate");
                if (!chqToDate.equals(""))
                    wherequery1 = wherequery1.append(" AND be.txndate <= :chqToDate");
            }

            final StringBuilder query = new StringBuilder()
                    .append(basicquery1)
                    .append(wherequery1)
                    .append(orderbyquery);

            if (LOGGER.isInfoEnabled())
                LOGGER.info("  getBankEntryDetails Query is  " + query);

            rs = entityManager.createNativeQuery(query.toString())
                    .setParameter("fundId", fundId)
                    .setParameter("chqFromDate", chqFromDate)
                    .setParameter("chqToDate", chqToDate)
                    .getResultList();

            if (LOGGER.isInfoEnabled())
                LOGGER.info("After Execute Query----getBankEntryDetails");

            int resSize = 0, i = 0;

            resSize = rs.size();

            originalVcId = new String[resSize];
            bankRefNo = new String[resSize];
            bankCharge = new String[resSize];

            for (final Object[] element : rs) {
                originalVcId[i] = element[5].toString();
                bankRefNo[i] = element[6].toString();
                bankCharge[i] = commnFunctions.numberToString(element[8].toString()).toString();
                i += 1;
            }
        } catch (final Exception sqlE) {
            LOGGER.error("Exception in main ", sqlE);
            throw taskExc;
        }
    }
}