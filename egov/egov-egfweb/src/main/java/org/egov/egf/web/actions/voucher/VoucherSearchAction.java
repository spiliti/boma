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
package org.egov.egf.web.actions.voucher;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.egov.commons.CVoucherHeader;
import org.egov.commons.Functionary;
import org.egov.commons.Fund;
import org.egov.commons.Fundsource;
import org.egov.commons.Scheme;
import org.egov.commons.SubScheme;
import org.egov.commons.Vouchermis;
import org.egov.commons.dao.FinancialYearDAO;
import org.egov.egf.commons.VoucherSearchUtil;
import org.egov.infra.admin.master.entity.AppConfigValues;
import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.admin.master.entity.Department;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.egov.infra.exception.ApplicationException;
import org.egov.infra.persistence.utils.Page;
import org.egov.infra.validation.exception.ValidationError;
import org.egov.infra.validation.exception.ValidationException;
import org.egov.infra.web.struts.actions.BaseFormAction;
import org.egov.infra.web.struts.annotation.ValidationErrorPage;
import org.egov.infra.web.utils.EgovPaginatedList;
import org.egov.model.bills.EgBillregistermis;
import org.egov.utils.FinancialConstants;
import org.egov.utils.VoucherHelper;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;

@ParentPackage("egov")
@Results({
        @Result(name = VoucherSearchAction.SEARCH, location = "voucherSearch-search.jsp"),
        @Result(name = com.opensymphony.xwork2.Action.SUCCESS, type = "redirect", location = "voucherSearch.action")
})
public class VoucherSearchAction extends BaseFormAction {
    public static final String SEARCH = "search";
    private static final Logger LOGGER = Logger.getLogger(VoucherSearchAction.class);
    private static final long serialVersionUID = 1L;
    private static final String SHOW_MODE = "showMode";
    private static final String NON_BILL_PAYMENT = "nonBillPayment";
    private static final String INTERNAL = "Internal";
    private static final String TYPE_LIST = "typeList";
    private static final String SOURCE = "source";
    private static final String TYPE = "type";
    private final List<String> headerFields = new ArrayList<>();
    private final List<String> mandatoryFields = new ArrayList<>();
    private final Map<Integer, String> sourceMap = new HashMap<>();
    private transient CVoucherHeader voucherHeader = new CVoucherHeader();
    private transient List<Map<String, Object>> voucherList;
    private transient Map<String, String> nameList = new LinkedHashMap<>();
    private Date fromDate = new Date();
    private Date toDate;

    @Autowired
    private transient AppConfigValueService appConfigValuesService;
    private transient List<String> voucherTypes = VoucherHelper.VOUCHER_TYPES;
    private transient Map<String, List<String>> voucherNames = VoucherHelper.VOUCHER_TYPE_NAMES;
    private transient List<Object> schemeList;
    private String showMode;
    private transient VoucherSearchUtil voucherSearchUtil;
    private Integer page = 1;
    private Integer pageSize = 30;
    private transient EgovPaginatedList pagedResults;
    private transient FinancialYearDAO financialYearDAO;

    public VoucherSearchAction() {
        voucherHeader.setVouchermis(new Vouchermis());
        addRelatedEntity("vouchermis.departmentid", Department.class);
        addRelatedEntity("fundId", Fund.class);
        addRelatedEntity("vouchermis.schemeid", Scheme.class);
        addRelatedEntity("vouchermis.subschemeid", SubScheme.class);
        addRelatedEntity("vouchermis.functionary", Functionary.class);
        addRelatedEntity("fundsourceId", Fundsource.class);
        addRelatedEntity("vouchermis.divisionid", Boundary.class);
    }

    @Override
    public Object getModel() {
        return voucherHeader;
    }

    @Override
    public void prepare() {
        super.prepare();
        getHeaderFields();
        populateSourceMap();
        if (headerFields.contains("department"))
            addDropdownData("departmentList", persistenceService.findAllBy("from Department order by name"));
        if (headerFields.contains("functionary"))
            addDropdownData("functionaryList", persistenceService.findAllBy(" from Functionary where isactive=true order by name"));
        if (headerFields.contains("fund"))
            addDropdownData("fundList", persistenceService.findAllBy(" from Fund where isactive=true and isnotleaf=false order by name"));
        if (headerFields.contains("fundsource"))
            addDropdownData("fundsourceList",
                    persistenceService.findAllBy(" from Fundsource where isactive=true order by name"));
        if (headerFields.contains("field"))
            addDropdownData("fieldList",
                    persistenceService.findAllBy(" from Boundary b where lower(b.boundaryType.name)='ward' "));
        if (headerFields.contains("scheme"))
            addDropdownData("schemeList", Collections.emptyList());
        if (headerFields.contains("subscheme"))
            addDropdownData("subschemeList", Collections.emptyList());

        if (parameters.get(SHOW_MODE) == null) {
            addDropdownData(TYPE_LIST, VoucherHelper.VOUCHER_TYPES);
        } else {
            showMode = parameters.get(SHOW_MODE)[0];
            if (NON_BILL_PAYMENT.equalsIgnoreCase(showMode)) {
                final List<String> typeList = new ArrayList<>();
                typeList.add(FinancialConstants.STANDARD_VOUCHER_TYPE_JOURNAL);
                addDropdownData(TYPE_LIST, typeList);
                voucherHeader.setType(FinancialConstants.STANDARD_VOUCHER_TYPE_JOURNAL);
                nameList = new LinkedHashMap<>();
                nameList.put(FinancialConstants.JOURNALVOUCHER_NAME_CONTRACTORJOURNAL,
                        FinancialConstants.JOURNALVOUCHER_NAME_CONTRACTORJOURNAL);
                nameList.put(FinancialConstants.JOURNALVOUCHER_NAME_SUPPLIERJOURNAL,
                        FinancialConstants.JOURNALVOUCHER_NAME_SUPPLIERJOURNAL);
                nameList.put(FinancialConstants.JOURNALVOUCHER_NAME_SALARYJOURNAL,
                        FinancialConstants.JOURNALVOUCHER_NAME_SALARYJOURNAL);
            } else {
                addDropdownData(TYPE_LIST, VoucherHelper.VOUCHER_TYPES);
            }
        }
    }

    private void populateSourceMap() {
        List<Object[]> sourceList = persistenceService
                .findAllBy(" select distinct m.id,m.name from CVoucherHeader  vh, EgModules m where m.id=vh.moduleId and vh.status!=4 order by m.name");

        for (final Object[] obj : sourceList)
            sourceMap.put((Integer) obj[0], (String) obj[1]);
        // For vouchers created from the financial module
        sourceMap.put(-2, INTERNAL);
    }

    public Map<String, String> getVoucherNameMap(final String type) {
        final List<Object> voucherNameList = getPersistenceService().findAllBy(
                "select  distinct name from  CVoucherHeader where type=?1", type);
        nameList = new LinkedHashMap<>();

        for (final Object voucherName : voucherNameList)
            nameList.put((String) voucherName, (String) voucherName);
        return nameList;
    }

    @SkipValidation
    @Action(value = "/voucher/voucherSearch-beforesearch")
    public String beforesearch() {
        finYearDate();
        if (showMode != null && showMode.equalsIgnoreCase(NON_BILL_PAYMENT)) {
            if (LOGGER.isDebugEnabled())
                LOGGER.debug(NON_BILL_PAYMENT);
        } else if (voucherHeader.getType() != null && !voucherHeader.getType().equals("-1"))
            getVoucherNameMap(voucherHeader.getType());

        return SEARCH;

    }

    public void prepareSearch() {

        if (showMode != null && !showMode.equalsIgnoreCase("nonbillPayment") &&
                parameters.get(TYPE) != null && !parameters.get(TYPE)[0].equalsIgnoreCase("-1"))
            nameList = getVoucherNameMap(parameters.get(TYPE)[0]);

    }

    @ValidationErrorPage(value = SEARCH)
    @Action(value = "/voucher/voucherSearch-search")
    public String search() throws ApplicationException, ParseException {
        if (parameters.get(SHOW_MODE) != null)
            showMode = parameters.get(SHOW_MODE)[0];
        if (voucherHeader.getModuleId() != null && voucherHeader.getModuleId() == -1)
            voucherHeader.setModuleId(null);
        // validate if mode is edit and financial year is not active
        if (showMode != null && showMode.equalsIgnoreCase("edit")) {
            final boolean validateFinancialYearForPosting = voucherSearchUtil.validateFinancialYearForPosting(fromDate, toDate);
            if (!validateFinancialYearForPosting)
                throw new ValidationException(Arrays.asList(new ValidationError(
                        "Financial Year  Not active for Posting(either year or date within selected date range)",
                        "Financial Year  Not active for Posting(either year or date within selected date range)")));
        }

        List<CVoucherHeader> list;
        List<Query> qryObj;
        boolean ismodifyJv = false;
        voucherList = new ArrayList<>();
        // for view voucher implementing paginated result
        if (isBlank(showMode)) {
            qryObj = voucherSearchUtil.voucherSearchQuery(voucherHeader, fromDate, toDate, showMode);
            final Query qry = qryObj.get(0);
            final Long count = (Long) qryObj.get(1).uniqueResult();
            final Page resPage = new Page(qry, page, pageSize);
            pagedResults = new EgovPaginatedList(resPage, count.intValue());
            list = pagedResults.getList();
        } else if (showMode.equalsIgnoreCase("nonbillPayment"))
            list = voucherSearchUtil.searchNonBillVouchers(voucherHeader, fromDate, toDate, showMode);
        else
            list = voucherSearchUtil.search(voucherHeader, fromDate, toDate, showMode);
        Map<String, Object> voucherMap;
        if (isBlank(showMode)) {
            for (final CVoucherHeader voucherheader : list) {
                voucherMap = new HashMap<>();
                final BigDecimal amt = voucherheader.getTotalAmount();
                voucherMap.put("id", voucherheader.getId());
                voucherMap.put("vouchernumber", voucherheader.getVoucherNumber());
                voucherMap.put(TYPE, voucherheader.getType());
                voucherMap.put("name", voucherheader.getName());
                voucherMap.put("deptName", voucherheader.getVouchermis().getDepartmentid().getName());
                voucherMap.put("voucherdate", voucherheader.getVoucherDate());
                voucherMap.put("fundname", voucherheader.getFundId().getName());
                if (voucherheader.getModuleId() == null)
                    voucherMap.put(SOURCE, INTERNAL);
                else
                    voucherMap.put(SOURCE, sourceMap.get(voucherheader.getModuleId()));

                voucherMap.put("amount", amt.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
                voucherMap.put("status", getVoucherStatus(voucherheader.getStatus()));
                voucherList.add(voucherMap);
            }
            pagedResults.setList(voucherList);
        } else
            for (final CVoucherHeader voucherheader : list) {
                if (voucherheader.getState() != null) {
                    final EgBillregistermis billMis = (EgBillregistermis) persistenceService.find(
                            "from EgBillregistermis where voucherHeader.id=?1", voucherheader.getId());
                    if (billMis != null) {
                        /*
                         * bill state will be null if created from create JV screen and voucher is in end state
                         */
                        if (billMis.getEgBillregister().getState() == null && voucherheader.getState().getValue().contains("END"))
                            ismodifyJv = true;
                        else
                            ismodifyJv = false;
                    } else if (voucherheader.getName().equalsIgnoreCase(FinancialConstants.JOURNALVOUCHER_NAME_GENERAL) &&
                            voucherheader.getState().getValue().contains("END"))
                        ismodifyJv = true;
                } else
                    ismodifyJv = true;
                if (ismodifyJv) {
                    voucherMap = new HashMap<>();
                    voucherMap.put("id", voucherheader.getId());
                    voucherMap.put("vouchernumber", voucherheader.getVoucherNumber());
                    voucherMap.put(TYPE, voucherheader.getType());
                    voucherMap.put("name", voucherheader.getName());
                    voucherMap.put("deptName", voucherheader.getVouchermis().getDepartmentid().getName());
                    voucherMap.put("voucherdate", voucherheader.getVoucherDate());
                    voucherMap.put("fundname", voucherheader.getFundId().getName());
                    if (voucherheader.getModuleId() == null)
                        voucherMap.put(SOURCE, INTERNAL);
                    else
                        voucherMap.put(SOURCE, sourceMap.get(voucherheader.getModuleId()));
                    voucherMap.put("amount", voucherheader.getTotalAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
                    voucherMap.put("status", getVoucherStatus(voucherheader.getStatus()));
                    voucherList.add(voucherMap);
                }
            }
        return SEARCH;
    }

    private String getVoucherStatus(final int status) {
        if (FinancialConstants.CREATEDVOUCHERSTATUS.equals(status))
            return "Approved";
        if (FinancialConstants.REVERSEDVOUCHERSTATUS.equals(status))
            return "Reversed";
        if (FinancialConstants.REVERSALVOUCHERSTATUS.equals(status))
            return "Reversal";
        if (FinancialConstants.CANCELLEDVOUCHERSTATUS.equals(status))
            return "Cancelled";
        if (FinancialConstants.PREAPPROVEDVOUCHERSTATUS.equals(status))
            return "Preapproved";
        return "";
    }

    public void finYearDate() {
        final String financialYearId = financialYearDAO.getCurrYearFiscalId();
        if (isBlank(financialYearId))
            fromDate = new Date();
        else
            fromDate = (Date) persistenceService.find("select startingDate  from CFinancialYear where id=?1",
                    Long.parseLong(financialYearId));
    }

    public void setFinancialYearDAO(final FinancialYearDAO financialYearDAO) {
        this.financialYearDAO = financialYearDAO;
    }

    protected void getHeaderFields() {
        final List<AppConfigValues> appConfigList = appConfigValuesService.getConfigValuesByModuleAndKey(FinancialConstants.MODULE_NAME_APPCONFIG, "DEFAULT_SEARCH_MISATTRRIBUTES");

        for (final AppConfigValues appConfigVal : appConfigList) {
            final String value = appConfigVal.getValue();
            final String header = value.substring(0, value.indexOf('|'));
            headerFields.add(header);
            final String mandate = value.substring(value.indexOf('|') + 1);
            if (mandate.equalsIgnoreCase("M"))
                mandatoryFields.add(header);
        }

    }

    @Override
    public void validate() {
        if (voucherHeader.getVoucherNumber() == null || "".equals(voucherHeader.getVoucherNumber())) {
            if (fromDate == null)
                addFieldError("From Date", getText("Please Enter From Date"));
            if (toDate == null)
                addFieldError("To Date", getText("Please Enter To Date"));
            checkMandatoryField("fundId", "fund", voucherHeader.getFundId(), "voucher.fund.mandatory");
            checkMandatoryField("vouchermis.departmentid", "department", voucherHeader.getVouchermis().getDepartmentid(),
                    "voucher.department.mandatory");
            checkMandatoryField("vouchermis.schemeid", "scheme", voucherHeader.getVouchermis().getSchemeid(),
                    "voucher.scheme.mandatory");
            checkMandatoryField("vouchermis.subschemeid", "subscheme", voucherHeader.getVouchermis().getSubschemeid(),
                    "voucher.subscheme.mandatory");
            checkMandatoryField("vouchermis.functionary", "functionary", voucherHeader.getVouchermis().getFunctionary(),
                    "voucher.functionary.mandatory");
            checkMandatoryField("fundsourceId", "fundsource", voucherHeader.getVouchermis().getFundsource(),
                    "voucher.fundsource.mandatory");
            checkMandatoryField("vouchermis.divisionId", "field", voucherHeader.getVouchermis().getDivisionid(),
                    "voucher.field.mandatory");
        }
    }

    protected void checkMandatoryField(final String objectName, final String fieldName, final Object value, final String errorKey) {
        if (mandatoryFields.contains(fieldName) && value == null)
            addFieldError(objectName, getText(errorKey));
    }

    public boolean isFieldMandatory(final String field) {
        return mandatoryFields.contains(field);
    }

    public boolean shouldShowHeaderField(final String field) {
        return headerFields.contains(field);
    }

    public List<Map<String, Object>> getVoucherList() {
        return voucherList;
    }

    public String ajaxLoadSchemes() {
        schemeList = persistenceService.findAllBy(" from Scheme where fund=?1", voucherHeader.getFundId());
        return "schemes";
    }

    public String ajaxLoadSubSchemes() {
        schemeList = persistenceService.findAllBy(" from SubScheme where scheme=?1", voucherHeader.getVouchermis()
                .getSchemeid());
        return "schemes";
    }

    public List<Object> getSchemeList() {
        return schemeList;
    }

    public void setSchemeId(final Integer schemeId) {
        voucherHeader.getVouchermis().setSchemeid((Scheme) persistenceService.find(" from Scheme where id=?1", schemeId));
    }

    public void setVoucherHeader(final CVoucherHeader voucherHeader) {
        this.voucherHeader = voucherHeader;
    }

    public void setVoucherSearchUtil(final VoucherSearchUtil voucherSearchUtil) {
        this.voucherSearchUtil = voucherSearchUtil;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(final Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(final Date toDate) {
        this.toDate = toDate;
    }

    public Map<String, String> getNameList() {
        return nameList;
    }

    public void setNameList(final Map<String, String> nameList) {
        this.nameList = nameList;
    }

    public Map<Integer, String> getSourceMap() {
        return sourceMap;
    }

    public String getShowMode() {
        return showMode;
    }

    public void setShowMode(final String showMode) {
        this.showMode = showMode;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(final Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(final Integer pageSize) {
        this.pageSize = pageSize;
    }

    public EgovPaginatedList getPagedResults() {
        return pagedResults;
    }

    public void setPagedResults(final EgovPaginatedList pagedResults) {
        this.pagedResults = pagedResults;
    }

    public List<String> getVoucherTypes() {
        return voucherTypes;
    }

    public void setVoucherTypes(final List<String> voucherTypes) {
        this.voucherTypes = voucherTypes;
    }

    public Map<String, List<String>> getVoucherNames() {
        return voucherNames;
    }

    public void setVoucherNames(final Map<String, List<String>> voucherNames) {
        this.voucherNames = voucherNames;
    }

}
