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

import org.egov.ptis.domain.entity.property.PropertyMutationMaster;
import org.egov.ptis.domain.entity.property.PropertyTypeMaster;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Repository(value = "propertyMutationMasterDAO")
@Transactional(readOnly = true)
public class PropertyMutationMasterHibDAO implements PropertyMutationMasterDAO {

    @PersistenceContext
    private EntityManager entityManager;

    private Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }

    // this method return list of ProperyMutationMaster objects based on type
    // which is passed as parameter type
    @Override
    public List<PropertyMutationMaster> getAllPropertyMutationMastersByType(String type) {
        Query qry = getCurrentSession().createQuery(
                "from PropertyMutationMaster PM where upper(PM.type) = :type order by PM.orderId");
        qry.setParameter("type", type.toUpperCase());
        return qry.list();
    }

    public List<PropertyMutationMaster> getAllActiveReasonsByType(String type) {
        Query qry = getCurrentSession().createQuery(
                "from PropertyMutationMaster PM where upper(PM.type) = :type and active = true order by PM.orderId");
        qry.setParameter("type", type.toUpperCase());
        return qry.list();
    }

    // this method returns ProperyMutationMaster object based on code which is
    // passed as a parameter
    @Override
    public PropertyMutationMaster getPropertyMutationMasterByCode(String code) {
        Query qry = getCurrentSession().createQuery(
                "from PropertyMutationMaster PM where upper(PM.code) = :code");
        qry.setParameter("code", code.toUpperCase());
        return (PropertyMutationMaster) qry.uniqueResult();
    }

    @Override
    public PropertyMutationMaster getPropertyMutationMasterByCodeAndType(String code, String type) {
        Query qry = getCurrentSession().createQuery(
                "from PropertyMutationMaster PM where upper(PM.code) = :code and upper(PM.type) = :type");
        qry.setParameter("code", code.toUpperCase());
        qry.setParameter("type", type.toUpperCase());
        return (PropertyMutationMaster) qry.uniqueResult();
    }

    public Object findById(Serializable id, boolean lock) {

        return null;
    }

    @Override
    public List findAll() {

        return null;
    }

    public List findByExample(Object exampleT) {

        return null;
    }

    public Object create(Object entity) {

        return null;
    }

    public void delete(Object entity) {

    }

    public Object update(Object entity) {

        return null;
    }

    @Override
    public PropertyMutationMaster findById(Long id, boolean lock) {
        return (PropertyMutationMaster) getCurrentSession()
                .createQuery("from PropertyMutationMaster P where P.id =:id ").setParameter("id", id).getSingleResult();
    }

    @Override
    public PropertyMutationMaster create(PropertyMutationMaster propertyMutationMaster) {

        return null;
    }

    @Override
    public void delete(PropertyMutationMaster propertyMutationMaster) {

    }

    @Override
    public PropertyMutationMaster update(PropertyMutationMaster propertyMutationMaster) {

        return null;
    }

    @Override
    public PropertyMutationMaster getPropertyMutationMasterByIdAndType(Long id, String type) {
        Query qry = getCurrentSession()
                .createQuery("from PropertyMutationMaster PM where id = :id and upper(PM.type) = :type");
        qry.setParameter("id", id);
        qry.setParameter("type", type.toUpperCase());
        return (PropertyMutationMaster) qry.uniqueResult();
    }

    @Override
    public List<PropertyMutationMaster> getPropertyMutationMasterByType(String type) {
        Query qry = getCurrentSession()
                .createQuery("from PropertyMutationMaster PM where upper(PM.type) = :type");
        qry.setParameter("type", type.toUpperCase());
        return qry.list();
    }
    
    @Override
    public List<PropertyMutationMaster> getAllPropertyMutationMasterByCodeAndType(String code, String type) {
        Query qry = getCurrentSession().createQuery(
                "from PropertyMutationMaster PM where upper(PM.code) = :code and upper(PM.type) = :type");
        qry.setParameter("code", code.toUpperCase());
        qry.setParameter("type", type.toUpperCase());
        return (List<PropertyMutationMaster>) qry.getResultList();
    }

}
