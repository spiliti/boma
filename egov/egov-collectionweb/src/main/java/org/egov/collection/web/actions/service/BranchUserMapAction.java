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

package org.egov.collection.web.actions.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.egov.collection.constants.CollectionConstants;
import org.egov.collection.entity.BranchUserMap;
import org.egov.commons.Bankbranch;
import org.egov.commons.dao.BankBranchHibernateDAO;
import org.egov.commons.dao.BankHibernateDAO;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.UserService;
import org.egov.infra.web.struts.actions.BaseFormAction;
import org.egov.infstr.services.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;

@ParentPackage("egov")
@Results({
        @Result(name = BranchUserMapAction.BANKBRANCHLIST, location = "branchUserMap-bankBranchList.jsp"),
        @Result(name = BranchUserMapAction.SUCCESS, location = "branchUserMap-success.jsp"),
        @Result(name = BranchUserMapAction.BEFORESEARCH, location = "branchUserMap-index.jsp"),
        @Result(name = BranchUserMapAction.INDEX, location = "branchUserMap-index.jsp"),
        @Result(name = BranchUserMapAction.NEW, location = "branchUserMap-new.jsp"),
        @Result(name = BranchUserMapAction.MODIFY, location = "branchUserMap-modify.jsp") })

public class BranchUserMapAction extends BaseFormAction {
    private static final Logger LOGGER = Logger.getLogger(BranchUserMapAction.class);
    private static final long serialVersionUID = 1L;
    protected static final String BEFORECREATE = "beforeCreate";
    protected static final String BEFORESEARCH = "beforesearch";
    protected static final String MODIFY = "modify";
    private BranchUserMap branchUserMap = new BranchUserMap();
    private static final String BANK_BRANCH_LIST = "bankBranchList";
    private static final String BANK_NAME_LIST = "bankNameList";
    private static final String BANK_COLLECTION_OPERATOR_USER_LIST = "bankCollectionOperatorUserList";
    protected static final String BANKBRANCHLIST = "bankBranchList";
    private static final String MAPPED_BANK_COLLECTION_OPERATOR_USER_LIST = "mappedBankCollectionOperatorUserList";
    private List<Bankbranch> bankBranchArrayList = new ArrayList<Bankbranch>(0);
    private PersistenceService<BranchUserMap, Long> branchUserMapService;
    private List<BranchUserMap> branchUserList = null;
    private Integer bankId;
    private Integer branchId;
    private Long userId;
    private Long branchUserMapId;
    private Boolean isActive = Boolean.FALSE;
    private String mode;
    @Autowired
    private BankHibernateDAO bankHibernateDAO;
    @Autowired
    private UserService userService;
    @Autowired
    private BankBranchHibernateDAO bankBranchHibernateDAO;
    @PersistenceContext
    private EntityManager entityManager;

    public BranchUserMapAction() {
        addRelatedEntity("bankbranch", Bankbranch.class);
        addRelatedEntity("bankuser", User.class);
    }

    @Override
    public BranchUserMap getModel() {
        return branchUserMap;
    }

    @Override
    public void prepare() {
        addDropdownData(BANK_NAME_LIST, bankHibernateDAO.getAllBankHavingBranchAndAccounts());
        addDropdownData(BANK_BRANCH_LIST, (validateIntegerField(bankId))
                ? bankBranchHibernateDAO.getAllBankBranchsByBankForReceiptPayments(bankId) : Collections.emptyList());
        addDropdownData(BANK_COLLECTION_OPERATOR_USER_LIST, getBankCollectionOperator());
    }

    @Action(value = "/service/branchUserMap-newform")
    public String newform() {
        return NEW;
    }

    private List<User> getBankCollectionOperator() {
        List<User> bankCollOpUserList = new ArrayList<User>(
                userService.getUsersByRoleName(CollectionConstants.BANK_COLLECTION_OPERATOR));
        List<User> userMapToBranchList = getMappedBankCollectionOperator();
        if (!userMapToBranchList.isEmpty())
            bankCollOpUserList.removeAll(userMapToBranchList);
        if (bankCollOpUserList.isEmpty())
            return Collections.emptyList();
        else
            return bankCollOpUserList;
    }

    private List<User> getMappedBankCollectionOperator() {
        Query query = entityManager.createNamedQuery(CollectionConstants.QUERY_BRANCHUSER, User.class);
        List<User> userList = query.getResultList();
        return userList.isEmpty() ? Collections.emptyList() : userList;
    }

    @Action(value = "/service/branchUserMap-bankBranchsByBankForReceiptPayments")
    public String bankBranchsByBankForReceiptPayments() {
        bankBranchArrayList = bankBranchHibernateDAO.getAllBankBranchsByBankForReceiptPayments(bankId);
        return BANKBRANCHLIST;
    }

    @Action(value = "/service/branchUserMap-create")
    public String create() {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Inside create");
        if (branchId != null && userId != null) {
            branchUserMap.setBankbranch(bankBranchHibernateDAO.findById(branchId, Boolean.FALSE));
            branchUserMap.setIsActive(getIsActive());
            branchUserMap.setBankuser(userService.getUserById(userId));
            branchUserMapService.persist(branchUserMap);
            addActionMessage(getText("branchuser.master.success", new String[] {
                    branchUserMap.getBankuser().getUsername(),
                    branchUserMap.getBankbranch().getBranchname() }));
        }
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("end create");
        return SUCCESS;
    }

    @Action(value = "/service/branchUserMap-beforeSearch")
    public String beforeSearch() {
        mode = VIEW;
        addDropdownData(MAPPED_BANK_COLLECTION_OPERATOR_USER_LIST, getMappedBankCollectionOperator());
        return INDEX;
    }

    @SuppressWarnings("unchecked")
    @Action(value = "/service/branchUserMap-search")
    public String search() {
        final StringBuilder searchQueryString = new StringBuilder(
                "select bu from  org.egov.collection.entity.BranchUserMap bu where 1=1  ");
        if (userId != null && userId != -1)
            searchQueryString.append(" and bu.bankuser.id =:bankUserId");
        if (validateIntegerField(bankId))
            searchQueryString.append(" and  bu.bankbranch.bank.id =:bankId ");
        if (validateIntegerField(branchId))
            searchQueryString.append(" and bu.bankbranch.id =:branchId ");
        final Query searchQuery = entityManager.createQuery(
                searchQueryString.toString());

        if (userId != null && userId != -1)
            searchQuery.setParameter("bankUserId", userId);
        if (validateIntegerField(bankId))
            searchQuery.setParameter("bankId", bankId);
        if (validateIntegerField(branchId))
            searchQuery.setParameter("branchId", branchId);
        branchUserList = searchQuery.getResultList();
        addDropdownData(MAPPED_BANK_COLLECTION_OPERATOR_USER_LIST, getMappedBankCollectionOperator());
        return INDEX;
    }

    @Action(value = "/service/branchUserMap-searchModify")
    public String searchModify() {
        mode = MODIFY;
        addDropdownData(MAPPED_BANK_COLLECTION_OPERATOR_USER_LIST, getMappedBankCollectionOperator());
        return INDEX;
    }

    @Action(value = "/service/branchUserMap-beforeModify")
    public String beforeModify() {
        if (branchUserMapId != null) {
            branchUserMap = entityManager.find(BranchUserMap.class, branchUserMapId);
            addDropdownData(MAPPED_BANK_COLLECTION_OPERATOR_USER_LIST, getMappedBankCollectionOperator());
            if (branchUserMap != null && branchUserMap.getBankbranch() != null && branchUserMap.getBankbranch().getBank() != null
                    && branchUserMap.getBankbranch().getBank().getId() != null)
                addDropdownData(BANK_BRANCH_LIST, bankBranchHibernateDAO
                        .getAllBankBranchsByBankForReceiptPayments(branchUserMap.getBankbranch().getBank().getId()));

        } else {
            addActionError(getText("branchuser.master.modify.validate.selectrecord"));
            return search();
        }

        return MODIFY;
    }

    @Action(value = "/service/branchUserMap-modify")
    public String modify() {
        BranchUserMap branchUser = null;
        if (branchUserMap != null && branchUserMap.getId() != null) {
            branchUser = entityManager.find(BranchUserMap.class, branchUserMap.getId());
            if (branchUserMap.getBankbranch() != null &&
                    validateIntegerField(branchUserMap.getBankbranch().getId()))
                branchUser.setBankbranch(bankBranchHibernateDAO.findById(branchUserMap.getBankbranch().getId(), Boolean.FALSE));
            else {
                addActionError(getText("branchuser.master.branch.error"));
                branchUserMapId = branchUserMap.getId();
                return beforeModify();
            }
            branchUser.setIsActive(isActive);
            branchUserMapService.persist(branchUser);
            addActionMessage(getText("branchuser.master.success", new String[] { branchUser.getBankuser().getUsername(),
                    bankBranchHibernateDAO.findById(branchUser.getBankbranch().getId(), Boolean.FALSE).getBranchname() }));

        }
        return SUCCESS;
    }

    private boolean validateIntegerField(Integer field) {
        return (field != null && field != -1) ? Boolean.TRUE : Boolean.FALSE;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(final Integer bankId) {
        this.bankId = bankId;
    }

    public List getBankBranchArrayList() {
        return bankBranchArrayList;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public PersistenceService<BranchUserMap, Long> getBranchUserMapService() {
        return branchUserMapService;
    }

    public void setBranchUserMapService(PersistenceService<BranchUserMap, Long> branchUserMapService) {
        this.branchUserMapService = branchUserMapService;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public List<BranchUserMap> getBranchUserList() {
        return branchUserList;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Long getBranchUserMapId() {
        return branchUserMapId;
    }

    public void setBranchUserMapId(Long branchUserMapId) {
        this.branchUserMapId = branchUserMapId;
    }

    public BranchUserMap getBranchUserMap() {
        return branchUserMap;
    }

    public void setBranchUserMap(BranchUserMap branchUserMap) {
        this.branchUserMap = branchUserMap;
    }

}
