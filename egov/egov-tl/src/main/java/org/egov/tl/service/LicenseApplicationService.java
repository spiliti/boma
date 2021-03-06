/*
 *    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) 2018  eGovernments Foundation
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

package org.egov.tl.service;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.egov.tl.utils.Constants.BUTTONAPPROVE;
import static org.egov.tl.utils.Constants.BUTTONREJECT;
import static org.egov.tl.utils.Constants.LICENSE_STATUS_ACKNOWLEDGED;
import static org.egov.tl.utils.Constants.NEWLICENSE;
import static org.egov.tl.utils.Constants.NEWLICENSEREJECT;
import static org.egov.tl.utils.Constants.RENEWLICENSE;
import static org.egov.tl.utils.Constants.RENEWLICENSEREJECT;
import static org.egov.tl.utils.Constants.RENEW_WITHOUT_FEE;
import static org.egov.tl.utils.Constants.SIGNWORKFLOWACTION;
import static org.egov.tl.utils.Constants.STATUS_ACTIVE;
import static org.egov.tl.utils.Constants.VIEW_LINK;
import static org.egov.tl.utils.Constants.NEW_APPTYPE_CODE;
import static org.egov.tl.utils.Constants.RENEW_APPTYPE_CODE;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.egov.demand.model.EgDemand;
import org.egov.infra.admin.master.entity.AppConfigValues;
import org.egov.infra.integration.event.model.ApplicationDetails;
import org.egov.infra.integration.event.model.enums.ApplicationStatus;
import org.egov.infra.integration.event.model.enums.TransactionStatus;
import org.egov.infra.integration.event.publisher.ThirdPartyApplicationEventPublisher;
import org.egov.infra.validation.exception.ValidationException;
import org.egov.infra.web.utils.WebUtils;
import org.egov.infra.workflow.entity.OwnerGroup;
import org.egov.infra.workflow.matrix.entity.WorkFlowMatrix;
import org.egov.tl.entity.LicenseNotice;
import org.egov.tl.entity.TradeLicense;
import org.egov.tl.entity.Validity;
import org.egov.tl.entity.contracts.WorkflowBean;
import org.egov.tl.utils.Constants;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.egov.pims.commons.Position;

@Service
public class LicenseApplicationService extends TradeLicenseService {

    private static final String LICENSE_CLOSURE_FAILED = "License closure failed";

	private static final String LICENSE_RENEWAL_FAILED = "License renewal failed";

	private static final String LICENSE_CREATION_FAILED = "License creation failed";

	private static final String LICENSE_CLOSED = "License closed";

	private static final String LICENSE_RENEWED = "License renewed";

	private static final String LICENSE_CREATED = "License created";

	private static final Logger LOGGER = Logger.getLogger(LicenseApplicationService.class);

    @Autowired
    private LicenseProcessWorkflowService licenseProcessWorkflowService;

    @Autowired
    private LicenseCitizenPortalService licenseCitizenPortalService;

    @Autowired
    private ThirdPartyApplicationEventPublisher thirdPartyApplicationEventPublisher;
    
    @Autowired
    private LicenseDemandVoucherService licenseDemandVoucherService;

    @Transactional
    public TradeLicense createWithMeseva(TradeLicense license, WorkflowBean wfBean) {
        return create(license, wfBean);
    }

	@Transactional
	public TradeLicense processWithWardSecretary(TradeLicense license, WorkflowBean wfBean, String tpTransactionId) {
		try {
			String remarks = null;
			if (NEW_APPTYPE_CODE.equals(wfBean.getActionName())) {
				license = create(license, wfBean);
				remarks = LICENSE_CREATED;
			} else if (RENEW_APPTYPE_CODE.equals(wfBean.getActionName())) {
				license = renew(license, wfBean);
				remarks = LICENSE_RENEWED;
			} else if (Constants.CLOSURE_APPTYPE_CODE.equals(wfBean.getActionName())) {
				remarks = LICENSE_CLOSED;
			}
			thirdPartyApplicationEventPublisher
					.publishEvent(ApplicationDetails.builder().withApplicationNumber(license.getApplicationNumber())
							.withViewLink(format(VIEW_LINK,
									WebUtils.extractRequestDomainURL(ServletActionContext.getRequest(), false),
									license.getUid()))
							.withTransactionStatus(TransactionStatus.SUCCESS)
							.withApplicationStatus(ApplicationStatus.INPROGRESS).withRemark(remarks)
							.withTransactionId(tpTransactionId).build());

		} catch (ValidationException validationException) {
			throw validationException;
		} catch (Exception e) {
			LOGGER.error("Ward Secretary : License process failed");
			String message = NEW_APPTYPE_CODE.equals(wfBean.getActionName()) ? LICENSE_CREATION_FAILED
					: RENEW_APPTYPE_CODE.equals(wfBean.getActionName()) ? LICENSE_RENEWAL_FAILED
							: LICENSE_CLOSURE_FAILED;
			thirdPartyApplicationEventPublisher
					.publishEvent(ApplicationDetails.builder().withTransactionStatus(TransactionStatus.FAILED)
							.withRemark(message).withTransactionId(tpTransactionId).withRemark(e.toString()).build());
		}
		return license;
	}

    @Transactional
    public TradeLicense renewWithMeeseva(TradeLicense license, WorkflowBean wfBean) {
        return renew(license, wfBean);
    }

    @Transactional
    public TradeLicense create(TradeLicense license, WorkflowBean workflowBean) {
        Date fromRange = installmentDao.getInsatllmentByModuleForGivenDate(licenseUtils.getModule(), new DateTime().toDate())
                .getFromDate();
        Date toRange = installmentDao
                .getInsatllmentByModuleForGivenDate(licenseUtils.getModule(), new DateTime().plusYears(1).toDate()).getToDate();
        if (license.getCommencementDate() == null || license.getCommencementDate().before(fromRange)
                || license.getCommencementDate().after(toRange))
            throw new ValidationException("TL-009", "TL-009");
        Validity validity = validityService.getApplicableLicenseValidity(license);
    	if (validity == null) {
            throw new ValidationException("TL-010", "License validity not defined.");
    	}
        license.setLicenseAppType(licenseAppTypeService.getNewLicenseApplicationType());
        
        license.getLicensee().setLicense(license);
        license.setStatus(licenseStatusService.getLicenseStatusByName(LICENSE_STATUS_ACKNOWLEDGED));
        license.setUid(UUID.randomUUID().toString());
        if (isBlank(license.getApplicationNumber()))
            license.setApplicationNumber(licenseNumberUtils.generateApplicationNumber());
        processAndStoreDocument(license);
        if (securityUtils.currentUserIsEmployee())
            licenseProcessWorkflowService.createNewLicenseWorkflowTransition(license, workflowBean);
        else
            licenseProcessWorkflowService.getWfWithThirdPartyOp(license, workflowBean);
        EgDemand demand = raiseNewDemand(license);
        licenseRepository.save(license);
        //Demand Voucher Posting
        List<AppConfigValues> appConfigValuesList = licenseDemandVoucherService.getAppConfigValueByModuleNameAndKeyName
        		(Constants.MODULE_NAME, Constants.DEMAND_VOUCHER_POSTING);
        String demandVhPosting = "NO";
        for (final AppConfigValues appConfigVal : appConfigValuesList) {
        	demandVhPosting = appConfigVal.getValue();
        }
        if(demandVhPosting.equalsIgnoreCase("YES")) {
        	licenseDemandVoucherService.createDemandVoucher(license, demand, null, demandVhPosting);
        }
        if (securityUtils.currentUserIsCitizen())
            licenseCitizenPortalService.onCreate(license);
        licenseApplicationIndexService.createOrUpdateLicenseApplicationIndex(license);
        tradeLicenseSmsAndEmailService.sendSmsAndEmail(license, workflowBean.getWorkFlowAction());
        return license;
    }

    @Transactional
    public TradeLicense renew(TradeLicense license, WorkflowBean workflowBean) {
    	Validity validity = validityService.getApplicableLicenseValidity(license);
    	if (validity == null) {
            throw new ValidationException("TL-010", "License validity not defined.");
    	}
        license.setApplicationDate(new Date());
        license.setLicenseAppType(licenseAppTypeService.getRenewLicenseApplicationType());
        if (!currentUserIsMeeseva())
            license.setApplicationNumber(licenseNumberUtils.generateApplicationNumber());
        updateDemandForTradeAreaChange(license);
        license.setStatus(licenseStatusService.getLicenseStatusByName(LICENSE_STATUS_ACKNOWLEDGED));
        processAndStoreDocument(license);
        if (license.isPaid())
            workflowBean.setAdditionaRule(RENEW_WITHOUT_FEE);

        if (securityUtils.currentUserIsEmployee())
            licenseProcessWorkflowService.createNewLicenseWorkflowTransition(license, workflowBean);
        else
            licenseProcessWorkflowService.getWfWithThirdPartyOp(license, workflowBean);
        this.licenseRepository.save(license);
        //Demand Voucher Posting
        List<AppConfigValues> appConfigValuesList = licenseDemandVoucherService.getAppConfigValueByModuleNameAndKeyName
        		(Constants.MODULE_NAME, Constants.DEMAND_VOUCHER_POSTING);
        String demandVhPosting = "NO";
        for (final AppConfigValues appConfigVal : appConfigValuesList) {
        	demandVhPosting = appConfigVal.getValue();
        }
        if(demandVhPosting.equalsIgnoreCase("YES")) {
        	licenseDemandVoucherService.createDemandVoucher(license, license.getDemand(),null, demandVhPosting);
        }
        if (securityUtils.currentUserIsCitizen())
            licenseCitizenPortalService.onCreate(license);
        tradeLicenseSmsAndEmailService.sendSmsAndEmail(license, workflowBean.getWorkFlowAction());
        licenseApplicationIndexService.createOrUpdateLicenseApplicationIndex(license);
        return license;
    }

    @Transactional
    public void updateLicense(TradeLicense license, WorkflowBean workflowBean) {
        processAndStoreDocument(license);
        updateDemandForTradeAreaChange(license);
        license.setCollectionPending(!license.isPaid());
        if (BUTTONREJECT.equalsIgnoreCase(workflowBean.getWorkFlowAction()))
            licenseProcessWorkflowService.getRejectTransition(license, workflowBean);
        else
            licenseProcessWorkflowService.createNewLicenseWorkflowTransition(license, workflowBean);

        if (BUTTONAPPROVE.equals(workflowBean.getWorkFlowAction())) {
            if (isEmpty(license.getLicenseNumber()) && license.isNewApplication()) {
                license.setLicenseNumber(licenseNumberUtils.generateLicenseNumber());
            }
            if (!license.isCollectionPending()) {
                generateAndStoreCertificate(license);
            }
        }

        licenseRepository.save(license);
        licenseCitizenPortalService.onUpdate(license);
        tradeLicenseSmsAndEmailService.sendSmsAndEmail(license, workflowBean.getWorkFlowAction());
        licenseApplicationIndexService.createOrUpdateLicenseApplicationIndex(license);
    }
    
    @Transactional
    public void updateLicenseToInitialState(TradeLicense license, WorkflowBean workflowBean, OwnerGroup owner, String extraInfo) {
    	
        license.setCollectionPending(true);
        license.setStatus(licenseStatusService.getLicenseStatusByName(LICENSE_STATUS_ACKNOWLEDGED));
        licenseProcessWorkflowService.rollbackStateInfo(license, workflowBean, (Position) owner, extraInfo);

        licenseRepository.save(license);
        licenseCitizenPortalService.onUpdate(license);
        licenseApplicationIndexService.createOrUpdateLicenseApplicationIndex(license);
	}
   
    @Transactional
    public void saveRejectionNotice(LicenseNotice licenseNotice) {
    	licenseNoticeRepository.save(licenseNotice);
    }

    public void processDigitalSignature(String applicationNumber) {
        if (isNotBlank(applicationNumber)) {
            TradeLicense license = licenseRepository.findByApplicationNumber(applicationNumber);
            WorkflowBean workflowBean = new WorkflowBean();
            workflowBean.setWorkFlowAction(SIGNWORKFLOWACTION);
            workflowBean.setAdditionaRule(license.isNewApplication() ? NEWLICENSE : RENEWLICENSE);
            license.setCertificateFileId(license.getDigiSignedCertFileStoreId());
            licenseProcessWorkflowService.createNewLicenseWorkflowTransition(license, workflowBean);
            licenseRepository.save(license);
            licenseCitizenPortalService.onUpdate(license);
            tradeLicenseSmsAndEmailService.sendSMsAndEmailOnDigitalSign(license);
            licenseApplicationIndexService.createOrUpdateLicenseApplicationIndex(license);
        }

    }

    public void collectionTransition(TradeLicense tradeLicense) {
        licenseProcessWorkflowService.collectionWorkflowTransition(tradeLicense);
        if (tradeLicense.getStatus().getStatusCode().equals(STATUS_ACTIVE)) {
            generateAndStoreCertificate(tradeLicense);
        }
    }

    public WorkFlowMatrix getWorkflowAPI(TradeLicense tradeLicense, WorkflowBean workflowBean) {
        if (BUTTONREJECT.equalsIgnoreCase(workflowBean.getWorkFlowAction()))
            workflowBean.setAdditionaRule(tradeLicense.isNewApplication() ? NEWLICENSEREJECT : RENEWLICENSEREJECT);
        return licenseProcessWorkflowService.getWorkFlowMatrix(tradeLicense, workflowBean);
    }
}
