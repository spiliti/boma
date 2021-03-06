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

package org.egov.wtms.application.service;

import static org.egov.wtms.utils.constants.WaterTaxConstants.PROPERTY_MODULE_NAME;
import static org.egov.wtms.utils.constants.WaterTaxConstants.WATERTAXREASONCODE;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.ValidationException;

import org.egov.commons.CFinancialYear;
import org.egov.commons.Installment;
import org.egov.commons.dao.FinancialYearDAO;
import org.egov.commons.dao.InstallmentDao;
import org.egov.demand.model.EgDemand;
import org.egov.demand.model.EgDemandDetails;
import org.egov.infra.admin.master.service.ModuleService;
import org.egov.wtms.application.entity.WaterConnectionDetails;
import org.egov.wtms.masters.entity.WaterRatesDetails;
import org.egov.wtms.utils.WaterTaxUtils;
import org.egov.wtms.application.service.WaterDemandConnectionService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("regulariseConnectionDemandService")
public class RegulariseDemandGenerationImpl implements RegulariseDemandGeneration {

    @Autowired
    private InstallmentDao installmentDao;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private FinancialYearDAO financialYearDAO;

    @Autowired
    private ConnectionDemandService connectionDemandService;

    @Autowired
    private WaterTaxUtils waterTaxUtils;
    
    @Autowired
    private WaterDemandConnectionService waterDemandConnectionService;

    @Override
    public WaterConnectionDetails generateDemandForRegulariseConnection(WaterConnectionDetails waterConnectionDetails) {
        CFinancialYear financialYear = financialYearDAO.getFinancialYearByDate(new Date());
        List<Installment> installmentList = installmentDao.getInstallmentsByModuleBetweenFromDateAndToDate(
                moduleService.getModuleByName(PROPERTY_MODULE_NAME), new DateTime().minusYears(1).toDate(),
                financialYear.getEndingDate());
        EgDemandDetails demandDetails;
        WaterRatesDetails waterRates = connectionDemandService.getWaterRatesDetailsForDemandUpdate(waterConnectionDetails);
        if (waterRates == null)
            throw new ValidationException("err.water.rate.not.found");
        EgDemand currentDemand = waterDemandConnectionService.getCurrentDemand(waterConnectionDetails).getDemand();
        for (Installment installment : installmentList) {
            demandDetails = connectionDemandService.createDemandDetails(waterRates.getMonthlyRate() * 6, WATERTAXREASONCODE,
                    installment);
            currentDemand.addBaseDemand(currentDemand.getBaseDemand().add(BigDecimal.valueOf(waterRates.getMonthlyRate() * 6)));
            currentDemand.setEgInstallmentMaster(installment);
            currentDemand.getEgDemandDetails().add(demandDetails);
        }
        waterConnectionDetails.getWaterDemandConnection().get(0).setDemand(currentDemand);
        return waterConnectionDetails;
    }
}
