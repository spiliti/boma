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
package org.egov.commons.dao;

import org.egov.commons.ObjectType;
import org.egov.infra.exception.ApplicationRuntimeException;
import org.hibernate.query.Query;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ObjectTypeDAO {

	@PersistenceContext
	private EntityManager entityManager;

	private Session getSession() {
		return this.entityManager.unwrap(Session.class);
	}

	public void createObjectType(final ObjectType objectType) {
		try {
			getSession().save(objectType);
		} catch (final Exception e) {
			throw new ApplicationRuntimeException("Error occurred while creating Object Type",e);
		}
	}

	public void updateObjectType(final ObjectType objectType) {
		try {
			getSession().saveOrUpdate(objectType);
		} catch (final Exception e) {
			throw new ApplicationRuntimeException("Error occurred while updating Object Type",e);
		}
	}

	public void removeObjectType(final ObjectType objectType) {
		try {
			getSession().delete(objectType);
		} catch (final Exception e) {
			throw new ApplicationRuntimeException("Error occurred while deleting Object Type",e);
		}
	}

	public ObjectType getObjectType(final int objType) {
		try {
			return (ObjectType) getSession().get(ObjectType.class, new Integer(objType));
		} catch (final Exception e) {
			throw new ApplicationRuntimeException("Error occurred while getting Object Type",e);
		}
	}

	public ObjectType getObjectType(final String objTypeName) {
		final Query qry = getSession().createQuery("from ObjectType D where D.type =:objTypeName ");
		qry.setString("objTypeName", objTypeName);
		return (ObjectType) qry.uniqueResult();
	}

}
