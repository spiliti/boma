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
package org.egov.eis.service;

import org.egov.eis.entity.Employee;
import org.egov.eis.entity.Jurisdiction;
import org.egov.eis.repository.JurisdictionRepository;
import org.egov.infra.admin.master.entity.Boundary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.egov.eis.utils.constants.EisConstants.BOUNDARY_TYPE_CITY;
import static org.egov.eis.utils.constants.EisConstants.WARD;
import static org.egov.infra.utils.ApplicationConstant.ADMIN_HIERARCHY_TYPE;

@Service
@Transactional(readOnly = true)
public class JurisdictionService {

    private final JurisdictionRepository jurisdictionRepository;

    @Autowired
    public JurisdictionService(final JurisdictionRepository jurisdictionRepository) {
        this.jurisdictionRepository = jurisdictionRepository;
    }

    @Transactional
    public void save(final Jurisdiction jurisdiction) {
        jurisdictionRepository.save(jurisdiction);
    }

    @Transactional
    public void update(final Jurisdiction jurisdiction) {
        jurisdictionRepository.saveAndFlush(jurisdiction);
    }

    @Transactional
    public void delete(final Jurisdiction jurisdiction) {
        jurisdictionRepository.delete(jurisdiction);
    }

    public Jurisdiction getById(final Long id) {
        return jurisdictionRepository.findOne(id);
    }

    @Transactional
    public Employee removeDeletedJurisdictions(Employee employee, String removedJurisdictionIds) {
        if (null != removedJurisdictionIds)
            for (String id : removedJurisdictionIds.split(",")) {
                employee.getJurisdictions().remove(jurisdictionRepository.findOne(Long.valueOf(id)));
            }
        return employee;
    }

    public List<Boundary> getEmployeeJuridictions(Long employeeId) {
        List<Boundary> wardBoundaries = jurisdictionRepository.getEmployeeBoundaries(employeeId, WARD, ADMIN_HIERARCHY_TYPE);
        if (wardBoundaries.isEmpty())
            wardBoundaries = jurisdictionRepository.getEmployeeBoundaries(employeeId, BOUNDARY_TYPE_CITY, ADMIN_HIERARCHY_TYPE);
        return wardBoundaries;

    }
}
