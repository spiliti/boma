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
package org.egov.wtms.autonumber.impl;

import org.apache.commons.lang3.StringUtils;
import org.egov.infra.persistence.utils.GenericSequenceNumberGenerator;
import org.egov.wtms.autonumber.EstimationNumberGenerator;
import org.egov.wtms.utils.WaterTaxUtils;
import org.egov.wtms.utils.constants.WaterTaxConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstimationNumberGeneratorImpl implements EstimationNumberGenerator {
    private static final String ESTIMATION_NUMBER_SEQ_PREFIX = "SEQ_ESTIMATION_NUMBER";
    private static final String ESTIMATION_NUMBER = "EN-";
    private static final String REJECTION_NUMBER = "RN/";
    @Autowired
    private GenericSequenceNumberGenerator genericSequenceNumberGenerator;

    @Autowired
    private WaterTaxUtils waterTaxUtils;

    @Override
    public String generateEstimationNumber(String noticeType) {
    	String noticeNumber = StringUtils.EMPTY;
    	if(StringUtils.isNotBlank(noticeType)) {
    		String numberFormat = StringUtils.EMPTY;
    		if(WaterTaxConstants.NOTICETYPE_ESTIMATION.equalsIgnoreCase(noticeType))
    			numberFormat = ESTIMATION_NUMBER;
    		else if(WaterTaxConstants.NOTICETYPE_REJECTION.equalsIgnoreCase(noticeType))
    			numberFormat = REJECTION_NUMBER;
    		noticeNumber = String.format("%s%s%06d", numberFormat, waterTaxUtils.getCityCode(),
	                genericSequenceNumberGenerator.getNextSequence(ESTIMATION_NUMBER_SEQ_PREFIX));
    	}
        return noticeNumber;
    }

}
