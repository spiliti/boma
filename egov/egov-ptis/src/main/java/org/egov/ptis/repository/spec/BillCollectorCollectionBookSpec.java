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
package org.egov.ptis.repository.spec;

import java.util.Calendar;

import javax.persistence.criteria.Predicate;

import org.egov.ptis.domain.entity.property.view.BillCollectorCollectionRequest;
import org.egov.ptis.domain.entity.property.view.CollectionIndexInfo;
import org.springframework.data.jpa.domain.Specification;

public class BillCollectorCollectionBookSpec {

    public static Specification<CollectionIndexInfo> bcCollectionSpecification(
            final BillCollectorCollectionRequest billCollectorCollectionRequest) {
        return (root, query, builder) -> {
            Calendar cal = Calendar.getInstance();

            final Predicate predicate = builder.conjunction();
            if (billCollectorCollectionRequest.getFromDate() != null)
                predicate.getExpressions()
                        .add(builder.greaterThanOrEqualTo(root.get("receiptDate"), billCollectorCollectionRequest.getFromDate()));
            if (billCollectorCollectionRequest.getToDate() != null) {
                cal.setTime(billCollectorCollectionRequest.getToDate());
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.SECOND, 59);
                cal.set(Calendar.MILLISECOND, 999);
                predicate.getExpressions()
                        .add(builder.lessThanOrEqualTo(root.get("receiptDate"), cal.getTime()));
            }
            if (billCollectorCollectionRequest.getUserId() != null && !billCollectorCollectionRequest.getUserId().equals("-1"))
                predicate.getExpressions()
                        .add(builder.equal(root.get("createdBy"), Integer.valueOf(billCollectorCollectionRequest.getUserId())));
            predicate.getExpressions()
                    .add(builder.equal(root.get("channel"), "SYSTEM"));
            predicate.getExpressions()
                    .add(builder.notEqual(root.get("paymentMode"), "online"));
            predicate.getExpressions()
            .add(builder.notEqual(root.get("billingService"), "PT Mutation Fee"));
            return predicate;
        };
    }

}
