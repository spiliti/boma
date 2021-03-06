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

package org.egov.stms.service;

import org.egov.stms.entity.SewerageTaxDueDetails;
import org.egov.stms.masters.entity.enums.PropertyType;
import org.egov.stms.transactions.entity.SewerageApplicationDetails;
import org.egov.stms.transactions.entity.SewerageConnectionDetail;
import org.egov.stms.transactions.service.SewerageApplicationDetailsService;
import org.egov.stms.transactions.service.SewerageDemandService;
import org.egov.stms.utils.constants.SewerageTaxConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egov.stms.masters.entity.enums.PropertyType.MIXED;
import static org.egov.stms.masters.entity.enums.PropertyType.RESIDENTIAL;

@Service
@Transactional(readOnly = true)
public class SewerageTaxService {

	@Autowired
	private SewerageApplicationDetailsService sewerageApplicationDetailsService;
	@Autowired
	private SewerageDemandService sewerageDemandService;

	public SewerageTaxDueDetails getSewerageDuesByPropertyIdentifier(final String propertyIdentifier) {
		BigDecimal arrDmd = BigDecimal.ZERO;
		BigDecimal arrColl = BigDecimal.ZERO;
		BigDecimal currDmd = BigDecimal.ZERO;
		BigDecimal currColl = BigDecimal.ZERO;
		BigDecimal totalDue = BigDecimal.ZERO;
		BigDecimal currentInstDue = BigDecimal.ZERO;
		SewerageTaxDueDetails sewerageTaxDue;
		List<SewerageApplicationDetails> sewerageList = sewerageApplicationDetailsService
				.getSewerageConnectionDetailsByPropertyIDentifier(propertyIdentifier);

		if (sewerageList.isEmpty()) {
			sewerageTaxDue = new SewerageTaxDueDetails();
			sewerageTaxDue.setConnectionCount(0);
			sewerageTaxDue.setNoOfClosets(0);
			sewerageTaxDue.setIsSuccess(false);
			sewerageTaxDue.setErrorCode(SewerageTaxConstants.PROPERTYID_NOT_EXIST_ERR_CODE);
			sewerageTaxDue.setErrorMessage(SewerageTaxConstants.STAXDETAILS_PROPERTYID_NOT_EXIST_ERR_MSG_PREFIX
					+ propertyIdentifier + SewerageTaxConstants.STAXDETAILS_NOT_EXIST_ERR_MSG_SUFFIX);
		} else {
			sewerageTaxDue = new SewerageTaxDueDetails();
			final HashMap<String, String> consumerCodes = new HashMap<>();
			Integer noOfClosets = 0;
			for (final SewerageApplicationDetails sewerageApplicationDetails : sewerageList)
				if (sewerageApplicationDetails.getConnection().getShscNumber() != null) {
					SewerageConnectionDetail sewerageConnectionDetail = sewerageApplicationDetails
							.getConnectionDetail();
					PropertyType propertyType = sewerageConnectionDetail.getPropertyType();
					noOfClosets += getNoOfClosets(sewerageConnectionDetail, propertyType);
					sewerageTaxDue = getDueInfo(sewerageApplicationDetails);
					sewerageTaxDue.setPropertyID(propertyIdentifier);
					consumerCodes.put(sewerageApplicationDetails.getApplicationType().getCode(),
							sewerageApplicationDetails.getConnection().getShscNumber());
					arrDmd = arrDmd.add(sewerageTaxDue.getArrearDemand().setScale(2, BigDecimal.ROUND_HALF_UP));
					arrColl = arrColl.add(sewerageTaxDue.getArrearCollection().setScale(2, BigDecimal.ROUND_HALF_UP));
					currDmd = currDmd.add(sewerageTaxDue.getCurrentDemand().setScale(2, BigDecimal.ROUND_HALF_UP));
					currColl = currColl
							.add(sewerageTaxDue.getCurrentCollection().setScale(2, BigDecimal.ROUND_HALF_UP));
					currentInstDue = currentInstDue
							.add(sewerageTaxDue.getCurrentInstDemand().setScale(2, BigDecimal.ROUND_HALF_UP));
					totalDue = totalDue.add(sewerageTaxDue.getTotalTaxDue().setScale(2, BigDecimal.ROUND_HALF_UP));
				}
			sewerageTaxDue.setNoOfClosets(noOfClosets);
			sewerageTaxDue.setArrearDemand(arrDmd);
			sewerageTaxDue.setArrearCollection(arrColl);
			sewerageTaxDue.setCurrentDemand(currDmd);
			sewerageTaxDue.setCurrentCollection(currColl);
			sewerageTaxDue.setTotalTaxDue(totalDue);
			sewerageTaxDue.setCurrentInstDemand(currentInstDue);
			sewerageTaxDue.setConsumerCode(consumerCodes);
			sewerageTaxDue.setConnectionCount(sewerageList.size());
			sewerageTaxDue.setIsSuccess(true);
			sewerageTaxDue.setIsInWorkFlow(false);

		}

		for (final SewerageApplicationDetails sewerageApplicationDetails : sewerageList)
			if ("INPROGRESS".equalsIgnoreCase(sewerageApplicationDetails.getConnection().getStatus().toString()))
				sewerageTaxDue.setIsInWorkFlow(true);

		return sewerageTaxDue;
	}

	private int getNoOfClosets(SewerageConnectionDetail sewerageConnectionDetail, PropertyType propertyType) {
		if (MIXED.equals(propertyType))
			return sewerageConnectionDetail.getNoOfClosetsNonResidential()
					+ sewerageConnectionDetail.getNoOfClosetsResidential();
		return RESIDENTIAL.equals(propertyType) ? sewerageConnectionDetail.getNoOfClosetsResidential()
				: sewerageConnectionDetail.getNoOfClosetsNonResidential();
	}

	private SewerageTaxDueDetails getDueInfo(final SewerageApplicationDetails sewerageApplicationDetails) {
		final Map<String, BigDecimal> resultmap = sewerageDemandService.getDemandDetailsMap(sewerageApplicationDetails);
		final SewerageTaxDueDetails sewerageTaxDueDetails = new SewerageTaxDueDetails();
		if (null != resultmap && !resultmap.isEmpty()) {
			final BigDecimal currDmd = resultmap.get(SewerageTaxConstants.CURR_DMD_STR);
			sewerageTaxDueDetails.setCurrentDemand(currDmd);
			final BigDecimal arrDmd = resultmap.get(SewerageTaxConstants.ARR_DMD_STR);
			sewerageTaxDueDetails.setArrearDemand(arrDmd);
			final BigDecimal currCollection = resultmap.get(SewerageTaxConstants.CURR_COLL_STR);
			sewerageTaxDueDetails.setCurrentCollection(currCollection);
			final BigDecimal arrCollection = resultmap.get(SewerageTaxConstants.ARR_COLL_STR);
			sewerageTaxDueDetails.setArrearCollection(arrCollection);
			final BigDecimal taxDue = currDmd.add(arrDmd).subtract(currCollection).subtract(arrCollection);
			sewerageTaxDueDetails.setTotalTaxDue(taxDue);
			BigDecimal currentInstDemand = sewerageDemandService.getCurrentDue(sewerageApplicationDetails);
			sewerageTaxDueDetails.setCurrentInstDemand(currentInstDemand);
			sewerageTaxDueDetails.setHalfYearlyTax(resultmap.get("halfYearTax"));

		}
		return sewerageTaxDueDetails;
	}

}
