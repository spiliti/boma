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

package org.egov.egf.web.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.egov.commons.CChartOfAccounts;
import org.egov.commons.dao.ChartOfAccountsDAO;
import org.egov.commons.service.AccountdetailtypeService;
import org.egov.commons.service.ChartOfAccountsService;
import org.egov.egf.web.adaptor.RecoveryJsonAdaptor;
import org.egov.model.recoveries.Recovery;
import org.egov.model.service.RecoveryService;
import org.egov.services.masters.BankService;
import org.egov.services.masters.EgPartyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/recovery")
public class RecoveryController {
    private final static String RECOVERY_NEW = "recovery-new";
    private final static String RECOVERY_RESULT = "recovery-result";
    private final static String RECOVERY_EDIT = "recovery-edit";
    private final static String RECOVERY_VIEW = "recovery-view";
    private final static String RECOVERY_SEARCH = "recovery-search";
    @Autowired
    @Qualifier("remittanceRecoveryService")
    private RecoveryService recoveryService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    @Qualifier("chartOfAccountsService")
    private ChartOfAccountsService chartOfAccountsService;
    @Autowired
    @Qualifier("bankService")
    private BankService bankService;
    @Autowired
    @Qualifier("egPartyTypeService")
    private EgPartyTypeService egPartyTypeService;
    @Autowired
    private ChartOfAccountsDAO chartOfAccountsDAO;
    @Autowired
    private AccountdetailtypeService accountdetailtypeService;

    private void prepareNewForm(Model model) {
        model.addAttribute("chartOfAccountss", Collections.EMPTY_LIST);
        model.addAttribute("chartOfAccounts", chartOfAccountsDAO.getForRecovery());
        model.addAttribute("egPartytypes", egPartyTypeService.findAll());
        model.addAttribute("banks", bankService.findAll());
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newForm(final Model model) {
        prepareNewForm(model);
        model.addAttribute("recovery", new Recovery());
        return RECOVERY_NEW;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute final Recovery recovery, final BindingResult errors, final Model model,
            final RedirectAttributes redirectAttrs) {
    	List<String> errorMsgs = validateRecoveryDetails(recovery);
    	for(String msgs : errorMsgs) {
    		errors.addError(new ObjectError("message : ",msgs));
    	}
        if (errors.hasErrors()) {
            prepareNewForm(model);
            return RECOVERY_NEW;
        }
        if (recovery.getBank() != null && recovery.getBank().getId() != null)
            recovery.setBank(bankService.findById(recovery.getBank().getId(), false));
        else
            recovery.setBank(null);
       
        recovery.setChartofaccounts(chartOfAccountsService.findById(recovery.getChartofaccounts().getId(), false));
        recovery.setEgPartytype(egPartyTypeService.findById(recovery.getEgPartytype().getId(), false));
        recoveryService.create(recovery);
        redirectAttrs.addFlashAttribute("message", messageSource.getMessage("msg.recovery.success", null, null));
        return "redirect:/recovery/result/" + recovery.getId();
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") final Long id, Model model) {
        Recovery recovery = recoveryService.findOne(id);
        if (recovery.getBank() != null && recovery.getBank().getId() != null)
            recovery.setBankLoan(true);
        List<CChartOfAccounts> coas = new ArrayList<CChartOfAccounts>();
        CChartOfAccounts coa = chartOfAccountsService.findById(recovery.getChartofaccounts().getId(), false);
        coas.add(coa);
        prepareNewForm(model);
        recovery.setChartofaccounts(coa);
        model.addAttribute("chartOfAccountss",coas);
        model.addAttribute("recovery", recovery);
        return RECOVERY_EDIT;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute final Recovery recovery, final BindingResult errors, final Model model,
            final RedirectAttributes redirectAttrs) {
    	List<String> errorMsgs = validateRecoveryDetails(recovery);
    	for(String msgs : errorMsgs) {
    		errors.addError(new ObjectError("message : ",msgs));
    	}
        if (errors.hasErrors()) {
            prepareNewForm(model);
            return RECOVERY_EDIT;
        }
        if (recovery.getBank() != null && recovery.getBank().getId() != null)
            recovery.setBank(bankService.findById(recovery.getBank().getId(), false));
        else
            recovery.setBank(null);
        recovery.setChartofaccounts(chartOfAccountsService.findById(recovery.getChartofaccounts().getId(), false));
        recovery.setEgPartytype(egPartyTypeService.findById(recovery.getEgPartytype().getId(), false));
        recoveryService.update(recovery);
        redirectAttrs.addFlashAttribute("message", messageSource.getMessage("msg.recovery.success", null, null));
        return "redirect:/recovery/result/" + recovery.getId();
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") final Long id, Model model) {
        Recovery recovery = recoveryService.findOne(id);
        if (recovery.getBank() != null && recovery.getBank().getId() != null)
            recovery.setBankLoan(true);
        prepareNewForm(model);
        model.addAttribute("recovery", recovery);
        return RECOVERY_VIEW;
    }

    @RequestMapping(value = "/result/{id}", method = RequestMethod.GET)
    public String result(@PathVariable("id") final Long id, Model model) {
        Recovery recovery = recoveryService.findOne(id);
        model.addAttribute("recovery", recovery);
        return RECOVERY_RESULT;
    }

    @RequestMapping(value = "/search/{mode}", method = RequestMethod.GET)
    public String search(@PathVariable("mode") final String mode, Model model)
    {
        Recovery recovery = new Recovery();
        prepareNewForm(model);
        model.addAttribute("recovery", recovery);
        return RECOVERY_SEARCH;

    }

    @RequestMapping(value = "/ajaxsearch/{mode}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    public @ResponseBody String ajaxsearch(@PathVariable("mode") final String mode, Model model,
            @ModelAttribute final Recovery recovery)
    {
        if (recovery != null && recovery.getChartofaccounts().getId() != null)
            recovery.setChartofaccounts(chartOfAccountsService.findById(recovery.getChartofaccounts().getId(), false));
        List<Recovery> searchResultList = recoveryService.search(recovery);
        String result = new StringBuilder("{ \"data\":").append(toSearchResultJson(searchResultList)).append("}").toString();
        return result;
    }

    @RequestMapping(value = "/ajax/getAccountCodes", method = RequestMethod.GET)
    public @ResponseBody List<CChartOfAccounts> getAccountCodes(@RequestParam("subLedgerCode") String subLedgerCode) {
        List<CChartOfAccounts> accounts = chartOfAccountsDAO.getBySubLedgerCode(subLedgerCode);
        return accounts;
    }

    public Object toSearchResultJson(final Object object)
    {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.registerTypeAdapter(Recovery.class, new RecoveryJsonAdaptor()).create();
        final String json = gson.toJson(object);
        return json;
    }
    
    private List<String> validateRecoveryDetails(final Recovery recovery){
    	List<String> msgs = new ArrayList<String>();
    	
        String recoveryName = recovery.getRecoveryName();
        recoveryName = recoveryName.replaceAll("[^a-zA-Z0-9 ]", "");
        recoveryName = recoveryName.trim();
        if(!recoveryName.isEmpty()) {
        	recovery.setRecoveryName(recoveryName);
        }else {
        	msgs.add(messageSource.getMessage("msg.relation.name", null, recoveryName, null));
        }
          
        String recoveryType = recovery.getType();
        recoveryType = recoveryType.replaceAll("[^a-zA-Z0-9]", "");
        recoveryType = recoveryType.trim();
        if(!recoveryType.isEmpty()) {
        	recovery.setType(recoveryType);
        }else {
        	msgs.add(messageSource.getMessage("msg.relation.name", null, recoveryType, null));
        }
        
        String remittedTo = recovery.getRemitted();
        remittedTo = remittedTo.replaceAll("[^a-zA-Z0-9 ]", "");
        remittedTo = remittedTo.trim();
        if(!remittedTo.isEmpty()) {
        	recovery.setRemitted(remittedTo);
        }else {
        	msgs.add(messageSource.getMessage("msg.relation.name", null, remittedTo, null));
        }
        
        String accountNumber = recovery.getAccountNumber();
        if(accountNumber!= null) {
        	accountNumber = accountNumber.replaceAll("[^a-zA-Z0-9]", "");
            accountNumber = accountNumber.trim();
            if(!accountNumber.isEmpty()) {
            	recovery.setAccountNumber(accountNumber);
            }else {
            	msgs.add(messageSource.getMessage("msg.relation.name", null, accountNumber, null));
            }
        }
        
        String ifscCode = recovery.getIfscCode();
        if(ifscCode!= null) {
        	ifscCode = ifscCode.replaceAll("[^a-zA-Z0-9]", "");
            ifscCode = ifscCode.trim();
            if(!ifscCode.isEmpty()) {
            	recovery.setIfscCode(ifscCode);
            }else {
            	msgs.add(messageSource.getMessage("msg.relation.name", null, ifscCode, null));
            }
        }
        
		return msgs;
        
    }
}