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

package org.egov.infra.workflow.matrix.entity;

import jdk.nashorn.internal.ir.annotations.Immutable;
import org.egov.infra.persistence.entity.AbstractPersistable;
import org.egov.infra.workflow.entity.WorkflowType;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static org.egov.infra.workflow.matrix.entity.WorkFlowAdditionalRule.SEQ_WF_ADDITIONALRULE;

@Entity
@Table(name = "EG_WF_ADDITIONALRULE")
@Immutable
@SequenceGenerator(name = SEQ_WF_ADDITIONALRULE, sequenceName = SEQ_WF_ADDITIONALRULE, allocationSize = 1)
public class WorkFlowAdditionalRule extends AbstractPersistable<Long> {

    public static final String SEQ_WF_ADDITIONALRULE = "SEQ_EG_WF_ADDITIONALRULE";
    private static final long serialVersionUID = 6698631796849930996L;
    @Id
    @GeneratedValue(generator = SEQ_WF_ADDITIONALRULE, strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "objecttypeid")
    private WorkflowType objecttypeid;

    @Length(max = 512)
    private String additionalRule;

    @Length(max = 512)
    private String states;

    @Length(max = 512)
    private String status;

    @Length(max = 512)
    private String buttons;

    @Length(max = 512)
    private String workFlowActions;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public WorkflowType getObjecttypeid() {
        return this.objecttypeid;
    }

    public void setObjecttypeid(WorkflowType objecttypeid) {
        this.objecttypeid = objecttypeid;
    }

    public String getAdditionalRule() {
        return this.additionalRule;
    }

    public void setAdditionalRule(String additionalRule) {
        this.additionalRule = additionalRule;
    }

    public String getStates() {
        return this.states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getButtons() {
        return this.buttons;
    }

    public void setButtons(String buttons) {
        this.buttons = buttons;
    }

    public String getWorkFlowActions() {
        return this.workFlowActions;
    }

    public void setWorkFlowActions(String workFlowActions) {
        this.workFlowActions = workFlowActions;
    }

}
