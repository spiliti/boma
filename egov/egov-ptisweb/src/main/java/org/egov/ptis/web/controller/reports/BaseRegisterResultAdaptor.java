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
package org.egov.ptis.web.controller.reports;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.egov.commons.Installment;
import org.egov.infra.web.support.json.adapter.DataTableJsonAdapter;
import org.egov.infra.web.support.ui.DataTable;
import org.egov.ptis.client.util.PropertyTaxUtil;
import org.egov.ptis.domain.entity.property.view.FloorDetailsInfo;
import org.egov.ptis.domain.entity.property.view.InstDmdCollInfo;
import org.egov.ptis.domain.entity.property.view.PropertyMVInfo;
import org.egov.ptis.service.utils.PropertyTaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

@Component
public class BaseRegisterResultAdaptor implements DataTableJsonAdapter<PropertyMVInfo> {

    private static final String CLASSIFICATION = "classification";
    private static final String PROPERTY_USAGE = "propertyUsage";
    private static final String AREA = "area";
    private static final String TOTAL_ARREAR_EDU_CESS = "totalArrearEduCess";
    private static final String TOTAL_ARREA_LIB_CESS = "totalArreaLibCess";
    private static final String TOTAL_ARREAR_PROPERTY_TAX = "totalArrearPropertyTax";
    private static final String ARREAR_UAC_PENALTY = "arrearUACPenalty";
    private static final String CURR_PENALTY_FINE = "currPenaltyFine";
    private static final String CURRENT_UAC_PENALTY = "currentUACPenalty";
    private static final String TOTAL_CURR_LIB_CESS = "totalCurrLibCess";
    private static final String TOTAL_CURR_EDU_CESS = "totalCurrEduCess";
    private static final String TOTAL_CURR_PROPERTY_TAX = "totalCurrPropertyTax";
    private static final String CURRENTYEAR_FIRST_HALF = "Current 1st Half";
    private static final String CURRENTYEAR_SECOND_HALF = "Current 2nd Half";

    private static PropertyTaxUtil propertyTaxUtil;
    private static PropertyTaxService propertyTaxService;

    public BaseRegisterResultAdaptor() {
    }

    @Autowired
    public BaseRegisterResultAdaptor(final PropertyTaxUtil propertyTaxUtil, final PropertyTaxService propertyTaxService) {
        BaseRegisterResultAdaptor.propertyTaxUtil = propertyTaxUtil;
        BaseRegisterResultAdaptor.propertyTaxService = propertyTaxService;
    }

    @Override
    public JsonElement serialize(final DataTable<PropertyMVInfo> baseRegisterResponse, final Type type,
            final JsonSerializationContext jsc) {
        final List<PropertyMVInfo> baseRegisterResult = baseRegisterResponse.getData();
        final JsonArray baseRegisterResultData = new JsonArray();
        baseRegisterResult.forEach(baseRegisterResultObj -> {
            final JsonObject jsonObject = new JsonObject();
            final Map<String, BigDecimal> valuesMap = getTaxDetails(baseRegisterResultObj);
            final Set<FloorDetailsInfo> floorDetails = new LinkedHashSet<>(baseRegisterResultObj.getFloorDetails());
            if (floorDetails.size() > 1) {
                addMultipleFloorProperty(baseRegisterResultData, baseRegisterResultObj, valuesMap, floorDetails);
            } else {
                addSinglePropertyDetails(baseRegisterResultObj, jsonObject, valuesMap);
                for (FloorDetailsInfo floor : floorDetails)
                    addFloorDetails(jsonObject, floor);
                baseRegisterResultData.add(jsonObject);
            }
        });
        return enhance(baseRegisterResultData, baseRegisterResponse);
    }

    private void addFloorDetails(final JsonObject jsonObject, FloorDetailsInfo floor) {
        jsonObject.addProperty(PROPERTY_USAGE,
                floor.getPropertyUsage());
        jsonObject.addProperty(CLASSIFICATION,
                floor.getClassification());
        jsonObject.addProperty(AREA, floor.getBuiltUpArea().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    }

    private void addMultipleFloorProperty(JsonArray baseRegisterResultData, PropertyMVInfo baseRegisterResultObj,
            final Map<String, BigDecimal> valuesMap, Set<FloorDetailsInfo> floorDetails) {
        JsonObject jsonObject;
        int count = 0;
        for (FloorDetailsInfo floor : floorDetails) {
            if (count == 0) {
                jsonObject = new JsonObject();
                addSinglePropertyDetails(baseRegisterResultObj, jsonObject, valuesMap);
                addFloorDetails(jsonObject, floor);
                count++;
            } else {
                jsonObject = new JsonObject();
                addFloorDetails(jsonObject, floor);
            }
            baseRegisterResultData.add(jsonObject);
        }
    }

    private void addSinglePropertyDetails(PropertyMVInfo baseRegisterResultObj, final JsonObject jsonObject,
            final Map<String, BigDecimal> valuesMap) {
        BigDecimal currTotal = BigDecimal.ZERO;

        final BigDecimal currColl = baseRegisterResultObj.getAggrCurrFirstHalfColl() == null
                ? BigDecimal.ZERO.add(((baseRegisterResultObj.getAggrCurrSecondHalfColl() == null) ? BigDecimal.ZERO
                        : baseRegisterResultObj.getAggrCurrSecondHalfColl()))
                : baseRegisterResultObj.getAggrCurrFirstHalfColl()
                        .add(baseRegisterResultObj.getAggrCurrSecondHalfColl());

        final BigDecimal totalColl = currColl.add(baseRegisterResultObj.getAggrArrColl() != null
                ? baseRegisterResultObj.getAggrArrColl() : BigDecimal.ZERO);
        if (!valuesMap.isEmpty())
            currTotal = (valuesMap.get(TOTAL_CURR_PROPERTY_TAX) != null ? valuesMap.get(TOTAL_CURR_PROPERTY_TAX)
                    : BigDecimal.ZERO)
                            .add((valuesMap.get(TOTAL_CURR_EDU_CESS) != null
                                    ? valuesMap.get(TOTAL_CURR_EDU_CESS)
                                    : BigDecimal.ZERO)
                                            .add((valuesMap.get(TOTAL_CURR_LIB_CESS) != null
                                                    ? valuesMap.get(TOTAL_CURR_LIB_CESS) : BigDecimal.ZERO)
                                                            .add(valuesMap.get(CURRENT_UAC_PENALTY) != null
                                                                    ? valuesMap.get(CURRENT_UAC_PENALTY)
                                                                    : BigDecimal.ZERO)));

        jsonObject.addProperty("assessmentNo", baseRegisterResultObj.getPropertyId());
        jsonObject.addProperty("ownerName", baseRegisterResultObj.getOwnerName());
        jsonObject.addProperty("doorNo",
                baseRegisterResultObj.getHouseNo() != null ? baseRegisterResultObj.getHouseNo() : "NA");
        jsonObject.addProperty("natureOfUsage",
                baseRegisterResultObj.getUsage() != null ? baseRegisterResultObj.getUsage() : "NA");
        jsonObject.addProperty("exemption", baseRegisterResultObj.getIsExempted() ? "Yes" : "No");
        jsonObject.addProperty("courtCase", baseRegisterResultObj.getIsUnderCourtCase() ? "Yes" : "No");
        jsonObject.addProperty("arrearPeriod", propertyTaxService.getArrearsPeriod(baseRegisterResultObj));
        jsonObject.addProperty("currentColl", currColl);
        jsonObject.addProperty("arrearTotal", baseRegisterResultObj.getAggrArrDmd() != null
                ? baseRegisterResultObj.getAggrArrDmd() : BigDecimal.ZERO);
        jsonObject.addProperty("arrearPenaltyFines", baseRegisterResultObj.getAggrArrearPenaly() != null
                ? baseRegisterResultObj.getAggrArrearPenaly() : BigDecimal.ZERO);
        jsonObject.addProperty("arrearColl", baseRegisterResultObj.getAggrArrColl() != null
                ? baseRegisterResultObj.getAggrArrColl() : BigDecimal.ZERO);
        jsonObject.addProperty("totalColl", totalColl);
        jsonObject.addProperty("waivedOffPT", baseRegisterResultObj.getWaivedoffAmount() != null
                ? baseRegisterResultObj.getWaivedoffAmount() : BigDecimal.ZERO);
        jsonObject.addProperty("courtVerdictAndWOAmount",
                propertyTaxService.getCourtVerdictAndWriteOffAmount(baseRegisterResultObj));

        if (!valuesMap.isEmpty()) {
            jsonObject.addProperty("generalTax", valuesMap.get(TOTAL_CURR_PROPERTY_TAX) != null
                    ? valuesMap.get(TOTAL_CURR_PROPERTY_TAX) : BigDecimal.ZERO);
            jsonObject.addProperty("libraryCessTax", valuesMap.get(TOTAL_CURR_LIB_CESS) != null
                    ? valuesMap.get(TOTAL_CURR_LIB_CESS) : BigDecimal.ZERO);
            jsonObject.addProperty("eduCessTax", valuesMap.get(TOTAL_CURR_EDU_CESS) != null
                    ? valuesMap.get(TOTAL_CURR_EDU_CESS) : BigDecimal.ZERO);
            jsonObject.addProperty("penaltyFinesTax",
                    valuesMap.get(CURR_PENALTY_FINE) != null ? valuesMap.get(CURR_PENALTY_FINE) : BigDecimal.ZERO);
            jsonObject.addProperty("UACpenalty", valuesMap.get(CURRENT_UAC_PENALTY) != null
                    ? valuesMap.get(CURRENT_UAC_PENALTY) : BigDecimal.ZERO);
            jsonObject.addProperty(ARREAR_UAC_PENALTY, valuesMap.get(ARREAR_UAC_PENALTY) != null
                    ? valuesMap.get(ARREAR_UAC_PENALTY) : BigDecimal.ZERO);
            jsonObject.addProperty("arrearPropertyTax", valuesMap.get(TOTAL_ARREAR_PROPERTY_TAX) != null
                    ? valuesMap.get(TOTAL_ARREAR_PROPERTY_TAX) : BigDecimal.ZERO);
            jsonObject.addProperty("arrearlibCess", valuesMap.get(TOTAL_ARREA_LIB_CESS) != null
                    ? valuesMap.get(TOTAL_ARREA_LIB_CESS) : BigDecimal.ZERO);
            jsonObject.addProperty("arrearEduCess", valuesMap.get(TOTAL_ARREAR_EDU_CESS) != null
                    ? valuesMap.get(TOTAL_ARREAR_EDU_CESS) : BigDecimal.ZERO);
            jsonObject.addProperty("currTotal", currTotal);
        }
    }

    private Map<String, BigDecimal> getTaxDetails(final PropertyMVInfo propMatView) {

        BigDecimal totalArrearPropertyTax = BigDecimal.ZERO;
        BigDecimal totalArrearEduCess = BigDecimal.ZERO;
        BigDecimal totalArreaLibCess = BigDecimal.ZERO;
        BigDecimal arrearPenaltyFine = BigDecimal.ZERO;
        BigDecimal totalCurrPropertyTax = BigDecimal.ZERO;
        BigDecimal totalCurrEduCess = BigDecimal.ZERO;
        BigDecimal totalCurrLibCess = BigDecimal.ZERO;
        BigDecimal currPenaltyFine = BigDecimal.ZERO;
        BigDecimal currentUACPenalty = BigDecimal.ZERO;
        BigDecimal arrearUACPenalty = BigDecimal.ZERO;

        final List<InstDmdCollInfo> instDemandCollList = new LinkedList<>(propMatView.getInstDmdColl());
        final Map<String, Installment> currYearInstMap = propertyTaxUtil.getInstallmentsForCurrYear(new Date());
        final Map<String, BigDecimal> values = new LinkedHashMap<>();
        for (final InstDmdCollInfo instDmdCollObj : instDemandCollList)
            if (instDmdCollObj.getInstallment().equals(currYearInstMap.get(CURRENTYEAR_FIRST_HALF).getId())) {
                totalCurrPropertyTax = totalCurrPropertyTax
                        .add(instDmdCollObj.getGeneralTax() != null ? instDmdCollObj.getGeneralTax() : BigDecimal.ZERO);
                values.put(TOTAL_CURR_PROPERTY_TAX, totalCurrPropertyTax);
                totalCurrEduCess = totalCurrEduCess
                        .add(instDmdCollObj.getEduCessTax() != null ? instDmdCollObj.getEduCessTax() : BigDecimal.ZERO);
                values.put(TOTAL_CURR_EDU_CESS, totalCurrEduCess);
                totalCurrLibCess = totalCurrLibCess
                        .add(instDmdCollObj.getLibCessTax() != null ? instDmdCollObj.getLibCessTax() : BigDecimal.ZERO);
                values.put(TOTAL_CURR_LIB_CESS, totalCurrLibCess);
                currPenaltyFine = currPenaltyFine.add(instDmdCollObj.getPenaltyFinesTax() != null
                        ? instDmdCollObj.getPenaltyFinesTax() : BigDecimal.ZERO);
                values.put(CURR_PENALTY_FINE, currPenaltyFine);
                currentUACPenalty = currentUACPenalty.add(instDmdCollObj.getUnauthPenaltyTax() != null
                        ? instDmdCollObj.getUnauthPenaltyTax() : BigDecimal.ZERO);
                values.put(CURRENT_UAC_PENALTY, currentUACPenalty);

            } else if (instDmdCollObj.getInstallment().equals(currYearInstMap.get(CURRENTYEAR_SECOND_HALF).getId())) {
                totalCurrPropertyTax = totalCurrPropertyTax
                        .add(instDmdCollObj.getGeneralTax() != null ? instDmdCollObj.getGeneralTax() : BigDecimal.ZERO);
                values.put(TOTAL_CURR_PROPERTY_TAX, totalCurrPropertyTax);
                totalCurrEduCess = totalCurrEduCess
                        .add(instDmdCollObj.getEduCessTax() != null ? instDmdCollObj.getEduCessTax() : BigDecimal.ZERO);
                values.put(TOTAL_CURR_EDU_CESS, totalCurrEduCess);
                totalCurrLibCess = totalCurrLibCess
                        .add(instDmdCollObj.getLibCessTax() != null ? instDmdCollObj.getLibCessTax() : BigDecimal.ZERO);
                values.put(TOTAL_CURR_LIB_CESS, totalCurrLibCess);
                currPenaltyFine = currPenaltyFine.add(instDmdCollObj.getPenaltyFinesTax() != null
                        ? instDmdCollObj.getPenaltyFinesTax() : BigDecimal.ZERO);
                values.put(CURR_PENALTY_FINE, currPenaltyFine);
                currentUACPenalty = currentUACPenalty.add(instDmdCollObj.getUnauthPenaltyTax() != null
                        ? instDmdCollObj.getUnauthPenaltyTax() : BigDecimal.ZERO);
                values.put(CURRENT_UAC_PENALTY, currentUACPenalty);

            } else {
                totalArrearPropertyTax = totalArrearPropertyTax
                        .add(instDmdCollObj.getGeneralTax() != null ? instDmdCollObj.getGeneralTax() : BigDecimal.ZERO);
                values.put(TOTAL_ARREAR_PROPERTY_TAX, totalArrearPropertyTax);
                totalArrearEduCess = totalArrearEduCess
                        .add(instDmdCollObj.getEduCessTax() != null ? instDmdCollObj.getEduCessTax() : BigDecimal.ZERO);
                values.put(TOTAL_ARREAR_EDU_CESS, totalArrearEduCess);
                totalArreaLibCess = totalArreaLibCess
                        .add(instDmdCollObj.getLibCessTax() != null ? instDmdCollObj.getLibCessTax() : BigDecimal.ZERO);
                values.put(TOTAL_ARREA_LIB_CESS, totalArreaLibCess);
                arrearPenaltyFine = arrearPenaltyFine.add(instDmdCollObj.getPenaltyFinesTax() != null
                        ? instDmdCollObj.getPenaltyFinesTax() : BigDecimal.ZERO);
                values.put("arrearPenaltyFine", arrearPenaltyFine);
                arrearUACPenalty = arrearUACPenalty.add(instDmdCollObj.getUnauthPenaltyTax() != null
                        ? instDmdCollObj.getUnauthPenaltyTax() : BigDecimal.ZERO);
                values.put(ARREAR_UAC_PENALTY, arrearUACPenalty);
            }
        return values;
    }
}
