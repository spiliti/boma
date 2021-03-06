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
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.egov.collection.service.CategoryService;
import org.egov.collection.service.ServiceDetailsService;
import org.egov.collection.service.ServiceTypeBankAccountService;
import org.egov.commons.Bankaccount;
import org.egov.commons.dao.BankBranchHibernateDAO;
import org.egov.commons.dao.BankHibernateDAO;
import org.egov.commons.dao.BankaccountHibernateDAO;
import org.egov.commons.entity.BankAccountServiceMap;
import org.egov.infra.web.struts.actions.BaseFormAction;
import org.egov.infra.web.struts.annotation.ValidationErrorPageExt;
import org.egov.infstr.models.ServiceDetails;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

@ParentPackage("egov")
@Results({
		@Result(name = ServiceTypeToBankAccountMappingAction.NEW, location = "serviceTypeToBankAccountMapping-new.jsp"),
		@Result(name = ServiceTypeToBankAccountMappingAction.SUCCESS, location = "serviceTypeToBankAccountMapping-success.jsp"),
		@Result(name = ServiceTypeToBankAccountMappingAction.INDEX, location = "serviceTypeToBankAccountMapping-index.jsp") })
public class ServiceTypeToBankAccountMappingAction extends BaseFormAction {
	private static final long serialVersionUID = 1L;
	private static final String BANK_BRANCH_LIST = "bankBranchList";
	private static final String BANK_NAME_LIST = "bankNameList";
	private static final String BANK_ACCOUNT_LIST = "bankAccountIdList";
	private static final String SERVICECATERGORY_LIST = "serviceCategoryList";
	private static final String SERVICEDETAILS_LIST = "serviceDetailsList";
	private static final String MODIFY = "modify";
	private BankAccountServiceMap bankAccountServiceMap = new BankAccountServiceMap();
	private List<BankAccountServiceMap> bankAccountServices = new ArrayList<>();
	private Integer bankId;
	private Integer branchId;
	private Long serviceCategory;
	private Long serviceAccountId;
	private String sourcePage;
	private String target;
	private Integer fundId;

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private BankHibernateDAO bankHibernateDAO;
	@Autowired
	private BankBranchHibernateDAO bankBrankHibernateDAO;
	@Autowired
	private BankaccountHibernateDAO bankAccountHibernateDAO;
	@Autowired
	private ServiceTypeBankAccountService bankAccountMappingService;
	@Autowired
	private CategoryService serviceCategoryService;
	@Autowired
	private ServiceDetailsService serviceDetailsService;

	public ServiceTypeToBankAccountMappingAction() {
		addRelatedEntity("serviceDetails", ServiceDetails.class);
		addRelatedEntity("bankAccountId", Bankaccount.class);
	}

	@Action(value = "/service/serviceTypeToBankAccountMapping-newform")
	public String newform() {
		if (getServiceAccountId() != null) {
			populateListsForView();
			setupDropdownDataExcluding();
			bankAccountServiceMap = bankAccountMappingService.findById(getServiceAccountId());
			addDropdownData(BANK_BRANCH_LIST, bankBrankHibernateDAO.getAllBankBranchsByBank(
					bankAccountServiceMap.getBankAccountId().getBankbranch().getBank().getId()));
			addDropdownData(BANK_ACCOUNT_LIST,
					bankAccountHibernateDAO.getBankAccountByBankBranchForReceiptsPayments(
							bankAccountServiceMap.getBankAccountId().getBankbranch().getId(),
							bankAccountServiceMap.getServiceDetails().getFund().getId()));
			setServiceCategory(bankAccountServiceMap.getServiceDetails().getServiceCategory().getId());
			setBankId(bankAccountServiceMap.getBankAccountId().getBankbranch().getBank().getId());
			setBranchId(bankAccountServiceMap.getBankAccountId().getBankbranch().getId());
		} else {
			populateLists();
		}
		return NEW;
	}

	private void populateLists() {
		addDropdownData(SERVICECATERGORY_LIST, serviceCategoryService.getActiveServiceCategoryList());
		addDropdownData(SERVICEDETAILS_LIST, Collections.emptyList());
		addDropdownData(BANK_NAME_LIST, bankHibernateDAO.getAllBankHavingBranchAndAccounts());
		addDropdownData(BANK_BRANCH_LIST, Collections.emptyList());
		addDropdownData(BANK_ACCOUNT_LIST, Collections.emptyList());
	}

	private void populateListsForView() {
		addDropdownData(SERVICECATERGORY_LIST, serviceCategoryService.getActiveServiceCategoryList());
		addDropdownData(SERVICEDETAILS_LIST, (serviceCategory != null && serviceCategory != -1)?
					serviceDetailsService.findActiveServiceDetailsByCategoryId(serviceCategory):Collections.emptyList());
		if (StringUtils.isNotBlank(sourcePage) && MODIFY.equals(sourcePage))
			addDropdownData(BANK_NAME_LIST, bankHibernateDAO.getAllBankHavingBranchAndAccounts());
		else
			addDropdownData(BANK_NAME_LIST, bankAccountMappingService.getBankMappedToService());
		addDropdownData(BANK_BRANCH_LIST, bankId != null && bankId != -1
				? bankBrankHibernateDAO.getAllBankBranchsByBankForReceiptPayments(bankId) : Collections.emptyList());
		addDropdownData(BANK_ACCOUNT_LIST,
				branchId != null && branchId != -1
						? bankAccountHibernateDAO.getBankAccountByBankBranchForReceiptsPayments(branchId, fundId)
						: Collections.emptyList());
	}

	@Action(value = "/service/serviceTypeToBankAccountMapping-list")
	public String list() {
		populateListsForView();
		return INDEX;
	}

	@SuppressWarnings("unchecked")
	@Action(value = "/service/serviceTypeToBankAccountMapping-search")
	public String search() {
		populateListsForView();
		final StringBuilder searchkQueryString = new StringBuilder();
		searchkQueryString.append("select distinct asm from BankAccountServiceMap asm where 1=1");
		if (bankId != null && bankId != -1)
			searchkQueryString.append(" and asm.bankAccountId.bankbranch.bank.id=:bankId");
		if (branchId != null && branchId != -1)
			searchkQueryString.append(" and asm.bankAccountId.bankbranch.id=:branchId");
		if (bankAccountServiceMap.getBankAccountId() != null && bankAccountServiceMap.getBankAccountId().getId() != -1)
			searchkQueryString.append(" and asm.bankAccountId.id=:accountId");
		if (serviceCategory != null && serviceCategory != -1)
			searchkQueryString.append(" and asm.serviceDetails.serviceCategory.id=:serviceCatId");
		if (bankAccountServiceMap.getServiceDetails() != null
				&& bankAccountServiceMap.getServiceDetails().getId() != -1)
			searchkQueryString.append(" and asm.serviceDetails.id=:serviceId");
		Query searchBankAccountListQuery = entityManager.unwrap(Session.class)
				.createQuery(searchkQueryString.toString());
		if (bankId != null && bankId != -1)
			searchBankAccountListQuery.setParameter("bankId", bankId);
		if (branchId != null && branchId != -1)
			searchBankAccountListQuery.setParameter("branchId", branchId);
		if (bankAccountServiceMap.getBankAccountId() != null && bankAccountServiceMap.getBankAccountId().getId() != -1)
			searchBankAccountListQuery.setParameter("accountId", bankAccountServiceMap.getBankAccountId().getId());
		if (serviceCategory != null && serviceCategory != -1)
			searchBankAccountListQuery.setParameter("serviceCatId", serviceCategory);
		if (bankAccountServiceMap.getServiceDetails() != null
				&& bankAccountServiceMap.getServiceDetails().getId() != -1)
			searchBankAccountListQuery.setParameter("serviceId", bankAccountServiceMap.getServiceDetails().getId());
		bankAccountServices = searchBankAccountListQuery.list();
		target = "searchresult";
		return INDEX;
	}

	@ValidationErrorPageExt(action = NEW, makeCall = true, toMethod = "newform")
	@Action(value = "/service/serviceTypeToBankAccountMapping-create")
	public String create() {
		if (bankAccountServiceMap.getId() != null) {
			final BankAccountServiceMap bankAccountServiceMapObj = bankAccountMappingService
					.findById(bankAccountServiceMap.getId());
			bankAccountServiceMapObj.setBankAccountId(bankAccountServiceMap.getBankAccountId());
			bankAccountServiceMapObj.setServiceDetails(bankAccountServiceMap.getServiceDetails());
			if (isMapExists()) {
				addActionError(getText("service.master.bankmap.exist.error",
						new String[] { bankAccountServiceMap.getServiceDetails().getName(),
								bankAccountServiceMap.getBankAccountId().getBankbranch().getBank().getName(),
								bankAccountServiceMap.getBankAccountId().getAccountnumber() }));
				return newform();
			} else
				bankAccountMappingService.persist(bankAccountServiceMapObj);

		} else { // On create calling this
			validateCreate();
			if (hasErrors())
				return newform();
			else
				bankAccountMappingService.persist(bankAccountServiceMap);

		}
		if (!hasErrors()) {
			if (StringUtils.isNotBlank(sourcePage) && MODIFY.equals(sourcePage))
				addActionMessage(getText("service.master.successmessage.modify",
						new String[] { bankAccountServiceMap.getServiceDetails().getName() }));
			else
				addActionMessage(getText("service.master.successmessage.create",
						new String[] { bankAccountServiceMap.getServiceDetails().getName(),
								bankAccountServiceMap.getBankAccountId().getBankbranch().getBank().getName(),
								bankAccountServiceMap.getBankAccountId().getAccountnumber() }));
		}
		return SUCCESS;
	}

	public Boolean isMapExists() {
		if (bankAccountServiceMap.getServiceDetails().getId() != null
				&& bankAccountServiceMap.getBankAccountId().getId() != null) {
			Optional<BankAccountServiceMap> bankAccountServiceOptional = bankAccountMappingService
					.findByServiceDetailsAndBankAccount(bankAccountServiceMap.getServiceDetails().getId(),
							bankAccountServiceMap.getBankAccountId().getId());
			if (bankAccountServiceOptional.isPresent()) {
				final BankAccountServiceMap bankAccountServiceMapObj = bankAccountServiceOptional.get();
				if ((bankAccountServiceMap.getId() == null && bankAccountServiceMapObj.getId() != null)
						|| (bankAccountServiceMap.getId() != null
								&& bankAccountServiceMapObj.getId() != bankAccountServiceMap.getId())) {
					return true;
				}
			}
		}
		return false;
	}

	private void validateCreate() {
		if (serviceCategory == null || serviceCategory == -1)
			addActionError(getText("error.select.service.category"));
		if (bankId == null && bankId == -1)
			addActionError(getText("error.select.bank"));
		if (branchId == null && branchId == -1)
			addActionError(getText("error.select.bankbranch"));
		if (bankAccountServiceMap.getServiceDetails() == null
				|| bankAccountServiceMap.getServiceDetails().getId() == null)
			addActionError(getText("error.select.service.type"));
		if (bankAccountServiceMap.getBankAccountId() == null
				&& bankAccountServiceMap.getBankAccountId().getId() == null)
			addActionError(getText("error.select.bankaccount"));
		if (!hasErrors() && isMapExists())
			addActionError(getText("service.master.bankmap.exist.error",
					new String[] { bankAccountServiceMap.getServiceDetails().getName(),
							bankAccountServiceMap.getBankAccountId().getBankbranch().getBank().getName(),
							bankAccountServiceMap.getBankAccountId().getAccountnumber() }));
	}

	@Override
	public Object getModel() {
		return bankAccountServiceMap;
	}

	public List<BankAccountServiceMap> getBankAccountServices() {
		return bankAccountServices;
	}

	public void setBankAccountServices(final List<BankAccountServiceMap> bankAccountServices) {
		this.bankAccountServices = bankAccountServices;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(final Integer bankId) {
		this.bankId = bankId;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(final Integer branchId) {
		this.branchId = branchId;
	}

	public Long getServiceCategory() {
		return serviceCategory;
	}

	public void setServiceCategory(final Long serviceCategory) {
		this.serviceCategory = serviceCategory;
	}

	public Long getServiceAccountId() {
		return serviceAccountId;
	}

	public void setServiceAccountId(final Long serviceAccountId) {
		this.serviceAccountId = serviceAccountId;
	}

	public String getSourcePage() {
		return sourcePage;
	}

	public void setSourcePage(final String sourcePage) {
		this.sourcePage = sourcePage;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(final String target) {
		this.target = target;
	}

}
