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
import org.egov.commons.CFunction;
import org.egov.commons.service.FunctionService;
import org.egov.egf.web.adaptor.FunctionJsonAdaptor;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/function")
public class FunctionController {
	private final static String FUNCTION_NEW = "function-new";
	private final static String FUNCTION_RESULT = "function-result";
	private final static String FUNCTION_EDIT = "function-edit";
	private final static String FUNCTION_VIEW = "function-view";
	private final static String FUNCTION_SEARCH = "function-search";
	@Autowired
	private FunctionService functionService;
	@Autowired
	private MessageSource messageSource;
	
	

	private void prepareNewForm(Model model) {
		model.addAttribute("functions", functionService.findAllIsNotLeafTrue());
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newForm(final Model model) {
		prepareNewForm(model);
		model.addAttribute("CFunction", new CFunction());
		return FUNCTION_NEW;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute final CFunction function,
			final BindingResult errors, final Model model,
			final RedirectAttributes redirectAttrs) {
		if (errors.hasErrors()) {
			prepareNewForm(model);
			return FUNCTION_NEW;
		}
		if(function.getParentId()!=null && function.getParentId().getId()!=null )
			function.setParentId(functionService.findOne(function.getParentId().getId()));
		else
			function.setParentId(null);

		functionService.create(function);
		redirectAttrs.addFlashAttribute("message",
				messageSource.getMessage("msg.function.success", null, null));
		return "redirect:/function/result/" + function.getId();
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") final Long id, Model model) {
		CFunction function = functionService.findOne(id);
		prepareNewForm(model);
		model.addAttribute("CFunction", function);
		return FUNCTION_EDIT;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute final CFunction function,
			final BindingResult errors, final Model model,
			final RedirectAttributes redirectAttrs) {
		List<String> errorMsgs = validateFunctionDetails(function);
    	for(String msgs : errorMsgs) {
    		errors.addError(new ObjectError("message : ",msgs));
    	}
		if (errors.hasErrors()) {
			prepareNewForm(model);
			return FUNCTION_EDIT;
		}
		functionService.update(function);
		redirectAttrs.addFlashAttribute("message",
				messageSource.getMessage("msg.function.success", null, null));
		return "redirect:/function/result/" + function.getId();
	}

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable("id") final Long id, Model model) {
		CFunction function = functionService.findOne(id);
		prepareNewForm(model);
		model.addAttribute("function", function);
		return FUNCTION_VIEW;
	}

	@RequestMapping(value = "/result/{id}", method = RequestMethod.GET)
	public String result(@PathVariable("id") final Long id, Model model) {
		CFunction function = functionService.findOne(id);
		model.addAttribute("function", function);
		return FUNCTION_RESULT;
	}

	@RequestMapping(value = "/search/{mode}", method = RequestMethod.GET)
	public String search(@PathVariable("mode") final String mode, Model model) {
		CFunction function = new CFunction();
		prepareNewForm(model);
		model.addAttribute("function", function);
		return FUNCTION_SEARCH;

	}

	@RequestMapping(value = "/ajaxsearch/{mode}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	public @ResponseBody String ajaxsearch(
			@PathVariable("mode") final String mode, Model model,
			@ModelAttribute final CFunction function) {
		List<CFunction> searchResultList = functionService.search(function);
		String result = new StringBuilder("{ \"data\":")
				.append(toSearchResultJson(searchResultList)).append("}")
				.toString();
		return result;
	}

	public Object toSearchResultJson(final Object object) {
		final GsonBuilder gsonBuilder = new GsonBuilder();
		final Gson gson = gsonBuilder.registerTypeAdapter(CFunction.class,
				new FunctionJsonAdaptor()).create();
		final String json = gson.toJson(object);
		return json;
	}
	private List<String> validateFunctionDetails(final CFunction function){
    	List<String> msgs = new ArrayList<String>();
    	
        String functionName = function.getName(); 
        functionName = functionName.replaceAll("[^a-zA-Z0-9 ]", "");
        functionName = functionName.trim();
        if(!functionName.isEmpty()) {
        	function.setName(functionName);
        }else {
        	msgs.add(messageSource.getMessage("msg.function.name.check", null, functionName, null));
        }
                
        String functionCode = function.getCode();
        functionCode = functionCode.replaceAll("[^a-zA-Z0-9 ]", "");
        functionCode = functionCode.trim();
        if(!functionCode.isEmpty()) {
        	function.setCode(functionCode);
        }else {
        	msgs.add(messageSource.getMessage("msg.function,code.check", null, functionCode, null));
        }
		return msgs;
        
    }
}