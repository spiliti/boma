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
package org.egov.ptis.domain.dao.property;

import org.egov.ptis.domain.entity.property.PropertyTypeMaster;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository(value = "propertyTypeMasterDAO")
@Transactional(readOnly = true)
public class PropertyTypeMasterHibernateDAO implements PropertyTypeMasterDAO {

    @PersistenceContext
    private EntityManager entityManager;

    private Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }

    @Override
    public PropertyTypeMaster getPropertyTypeMasterByName(final String type) {
        final Query qry = getCurrentSession().createQuery(
                "from PropertyTypeMaster P where P.type =:type ");
        qry.setString("type", type);
        return (PropertyTypeMaster) qry.uniqueResult();
    }

    @Override
    public PropertyTypeMaster getPropertyTypeMasterByCode(final String code) {
        final Query qry = getCurrentSession().createQuery(
                "from PropertyTypeMaster P where P.code =:Code ");
        qry.setString("Code", code);
        return (PropertyTypeMaster) qry.uniqueResult();
    }

    @Override
    public PropertyTypeMaster findById(final Long id, final boolean lock) {
		return (PropertyTypeMaster) getCurrentSession().createQuery("from PropertyTypeMaster P where P.id =:id ")
				.setParameter("id", id).uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PropertyTypeMaster> findAll() {
        return getCurrentSession().createQuery("from PropertyTypeMaster").list();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<PropertyTypeMaster> findAllExcludeEWSHS() {
        return getCurrentSession().createQuery("from PropertyTypeMaster where type != 'EWSHS' ").list();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<PropertyTypeMaster> findBuiltUpOwnerShipTypes() {
        return getCurrentSession().createQuery("from PropertyTypeMaster where code not in ('EWSHS','VAC_LAND') ").list();
    }
    

    @Override
    public PropertyTypeMaster create(final PropertyTypeMaster propertyTypeMaster) {

        return null;
    }

    @Override
    public void delete(final PropertyTypeMaster propertyTypeMaster) {


    }

    @Override
    public PropertyTypeMaster update(final PropertyTypeMaster propertyTypeMaster) {

        return null;
    }
}
