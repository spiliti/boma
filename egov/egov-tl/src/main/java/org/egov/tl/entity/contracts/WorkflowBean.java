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

package org.egov.tl.entity.contracts;

import org.egov.infra.admin.master.entity.Department;
import org.egov.infra.admin.master.entity.User;
import org.egov.pims.commons.Designation;
import org.hibernate.validator.constraints.SafeHtml;

import java.util.List;

public class WorkflowBean {

    @SafeHtml
    private String actionName;

    @SafeHtml
    private String additionaRule;

    @SafeHtml
    private String actionState;

    @SafeHtml
    private String currentState;

    @SafeHtml
    private String currentDesignation;

    @SafeHtml
    private String disabledFields;

    @SafeHtml
    private String approverComments;

    @SafeHtml
    private String workFlowAction;

    private Long approverUserId;
    private Integer departmentId;
    private Integer designationId;

    private List<User> appoverUserList;
    private List<Department> departmentList;
    private List<Designation> designationList;


    private Long approverPositionId;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(final String actionName) {
        this.actionName = actionName;
    }

    public String getActionState() {
        return actionState;
    }

    public void setActionState(final String actionState) {
        this.actionState = actionState;
    }

    public Long getApproverUserId() {
        return approverUserId;
    }

    public void setApproverUserId(final Long approverUserId) {
        this.approverUserId = approverUserId;
    }

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(final List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    public List<Designation> getDesignationList() {
        return designationList;
    }

    public void setDesignationList(final List<Designation> designationList) {
        this.designationList = designationList;
    }

    public List<User> getAppoverUserList() {
        return appoverUserList;
    }

    public void setAppoverUserList(final List<User> appoverUserList) {
        this.appoverUserList = appoverUserList;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(final Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getDesignationId() {
        return designationId;
    }

    public void setDesignationId(final Integer designationId) {
        this.designationId = designationId;
    }

    public String getApproverComments() {
        return approverComments;
    }

    public void setApproverComments(String approverComments) {
        this.approverComments = approverComments;
    }

    public String getWorkFlowAction() {
        return workFlowAction;
    }

    public void setWorkFlowAction(String workFlowAction) {
        this.workFlowAction = workFlowAction;
    }

    public Long getApproverPositionId() {
        return approverPositionId;
    }

    public void setApproverPositionId(Long approverPositionId) {
        this.approverPositionId = approverPositionId;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getAdditionaRule() {
        return additionaRule;
    }

    public void setAdditionaRule(String additionaRule) {
        this.additionaRule = additionaRule;
    }

    public String getCurrentDesignation() {
        return currentDesignation;
    }

    public void setCurrentDesignation(String currentDesignation) {
        this.currentDesignation = currentDesignation;
    }

    public String getDisabledFields() {
        return disabledFields;
    }

    public void setDisabledFields(String disabledFields) {
        this.disabledFields = disabledFields;
    }
}
