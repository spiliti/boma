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
package org.egov.ptis.master.service;

import org.egov.commons.Installment;
import org.egov.commons.dao.InstallmentDao;
import org.egov.infra.admin.master.entity.Module;
import org.egov.infra.admin.master.service.ModuleService;
import org.egov.ptis.constants.PropertyTaxConstants;
import org.egov.ptis.domain.entity.property.StructureClassification;
import org.egov.ptis.domain.repository.master.structureclassification.StructureClassificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class StructureClassificationService {

	private final StructureClassificationRepository structureClassificationRepository;

	@Autowired
	public StructureClassificationService(final StructureClassificationRepository structureClassificationRepository) {
		this.structureClassificationRepository = structureClassificationRepository;
	}

	@Autowired
	private InstallmentDao installmentDao;

	@Autowired
	private ModuleService moduleService;

	@Transactional
	public StructureClassification create(final StructureClassification structureClassification) {
		structureClassificationRepository.save(structureClassification);
		return structureClassification;
	}

	public Installment getInstallment() {
		final Module module = moduleService.getModuleByName(PropertyTaxConstants.PTMODULENAME);
		return installmentDao.getInsatllmentByModuleForGivenDate(module, new Date());
	}

	public List<StructureClassification> getAllActiveStructureTypes() {
		return structureClassificationRepository.findByIsActiveTrueOrderByTypeName();
	}

	public StructureClassification findOne(final Long id) {
		return structureClassificationRepository.findOne(id);
	}

	@Transactional
	public void update(final StructureClassification structureClassification) {
		structureClassificationRepository.save(structureClassification);
	}

	public List<String> validateUpdateClassification(final StructureClassification structureClassification) {
		final List<String> errors = new ArrayList<>();
		if (!structureClassificationRepository
				.findByCodeAndNotInId(structureClassification.getConstrTypeCode(), structureClassification.getId())
				.isEmpty())
			errors.add("error.duplicate.struc.code");
		else if (!structureClassificationRepository
                        .findByNameAndNotInId(structureClassification.getTypeName(), structureClassification.getId())
                        .isEmpty())
                        errors.add("error.duplicate.structure");
		else if (!structureClassificationRepository.findByUsageUnitRateActive(structureClassification.getId()).isEmpty()
				&& !structureClassification.getIsActive())
			errors.add("error.active.unitrates.exist.forstructure");
		return errors;
	}
	
	public List<StructureClassification> getAllStructureTypes() {
            return structureClassificationRepository.findAll();
    }
	
	 public Boolean isActiveClassification(String code){
	    	return structureClassificationRepository.findIsActiveByCode(code);
	    }
	 
	 public StructureClassification getClassificationByCode(String code){
	    	return structureClassificationRepository.findClassificationByCode(code);
	    }
}