package ro.allevo.fintpui.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import ro.allevo.fintpui.model.RoutingRule;
import ro.allevo.fintpui.model.RoutingSchema;
import ro.allevo.fintpui.service.QueueService;
import ro.allevo.fintpui.service.RoutingRulesService;
import ro.allevo.fintpui.service.RoutingSchemaService;
import ro.allevo.fintpui.utils.Invariants;
import ro.allevo.fintpui.utils.servlets.ServletsHelper;


@Controller
@RequestMapping()
public class RoutingRulesController {
	@Autowired 
	private ServletsHelper servletsHelper;
	
	@Autowired 
	private RoutingRulesService routingRulesService;
	
	@Autowired
	private RoutingSchemaService routingSchemaService;
	
	@Autowired 
	private QueueService queueService;
	
	private static Logger logger = LogManager.getLogger(RoutingRulesController.class
			.getName());
	
	/*
	 * DISPLAY
	 */
	@RequestMapping(value = "/routingrules", method = RequestMethod.GET)
	public String printRules(ModelMap model, @RequestParam(value = "schema", required = false) String schemaName){
		logger.info("/routingrules requested with arguments [schema:" + schemaName+ "]");
		final RoutingRule[] rules;
		
		
		if(schemaName == null){
			rules = routingRulesService.getAllRoutingRules();
		}else{
			rules = routingRulesService.getRoutingRulesBySchema(schemaName);
			model.addAttribute("schema", routingSchemaService.getRoutingSchema(schemaName));
		}
		model.addAttribute("rules", rules);
		Map<String, ArrayList<RoutingRule>> mappedRules = routingRulesService
				.getRulesGroupedByQueues(rules);
		model.addAttribute("mappedRules", mappedRules);
		return "tiles/routingrules";
	}
	
	/*
	 * INSERT
	 */
	@RequestMapping(value="/addRule", method = RequestMethod.GET)
	public String printRules(ModelMap model, @ModelAttribute("rule") RoutingRule rule){
		logger.info("/addRoutingrules requested");
		model.addAttribute("queues", queueService.getQueuesNames());
		model.addAttribute("schemas", routingSchemaService.getAllRoutingSchemaNames());
		model.addAttribute("types", Invariants.RULES_TYPES);
		model.addAttribute("actions", Invariants.RULES_ACTIONS);
		model.addAttribute("actionsNoParam", Invariants.RULES_ACTIONS_NO_PARAM);
		model.addAttribute("actionsParam", Invariants.RULES_ACTIONS_WITH_PARAM);
		model.addAttribute("actionsDropDown", Invariants.RULES_ACTIONS_WITH_DROP_DOWN);
		return "tiles/routingrules_add";
	}
	
	@RequestMapping(value="/routingrules/insert", method = RequestMethod.POST)
	public String insertRule(@ModelAttribute("rule") RoutingRule routingRule){
		logger.info("/insert routing rule requested");
		routingRulesService.insertRoutingRule(routingRule);
		
		return "redirect:/routingrules.htm?schema="+routingRule.getSchema();
	}
	
	/*
	 * EDIT
	 */
	@RequestMapping(value="/editRule", method = RequestMethod.GET)
	public String editRule(ModelMap model, @RequestParam(value="rule") String ruleName){
		logger.info("/editRule requested");
		RoutingRule rule = routingRulesService.getRoutingRule(ruleName);
		model.addAttribute("rule", rule);
		model.addAttribute("queues", queueService.getQueuesNames());
		model.addAttribute("schemas", routingSchemaService.getAllRoutingSchemaNames());
		model.addAttribute("types", Invariants.RULES_TYPES);
		model.addAttribute("actions", Invariants.RULES_ACTIONS);
		model.addAttribute("actionsNoParam", Invariants.RULES_ACTIONS_NO_PARAM);
		model.addAttribute("actionsParam", Invariants.RULES_ACTIONS_WITH_PARAM);
		model.addAttribute("actionsDropDown", Invariants.RULES_ACTIONS_WITH_DROP_DOWN);
		
		
		//if action contains argument, extract the action name
		String action;
		if(rule.getAction().contains("(")){
			action = rule.getAction().substring(0,rule.getAction().indexOf("("));
		}else{
			action = rule.getAction();
		}
		logger.info(action);
		logger.info(Invariants.RULES_ACTIONS_WITH_DROP_DOWN.contains(action));
		if(Invariants.RULES_ACTIONS_NO_PARAM.contains(action)){
			logger.info("NO ARGUMENT");
			model.addAttribute("actionType", "NO_ARGUMENT");
		}
		if(Invariants.RULES_ACTIONS_WITH_PARAM.contains(action)){
			logger.info("TEXT ARGUMENT");
			String argument = rule.getAction().substring(rule.getAction().indexOf("(") + 1, rule.getAction().length() - 1);
			model.addAttribute("actionType", "TEXT_ARGUMENT");
			model.addAttribute("argument", argument);
		}
		if(Invariants.RULES_ACTIONS_WITH_DROP_DOWN.contains(action)){
			String argument = rule.getAction().substring(rule.getAction().indexOf("(") + 1, rule.getAction().length() - 1);
			model.addAttribute("actionType", "LIST_ARGUMENT");
			model.addAttribute("argument", argument);
			logger.info("DROP DOWN ARGUMENT");
			logger.info("dest queue " + argument);
		}
		
		return "tiles/routingrules_edit";
	}
	
	@RequestMapping(value = "/routingrules/update", method = RequestMethod.POST)
	public String updateRule(@ModelAttribute("rule") RoutingRule rule, @RequestParam("init_name") String initialName){
		logger.info("/update routing rule requested");
		routingRulesService.updateRoutingRUle(initialName, rule);
		return "redirect:/routingrules.htm?schema="+rule.getSchema();
	}
	
	/*
	 * DELETE
	 */
	@RequestMapping(value = "routingrules/deleteRule")
	public String deleteRule(@RequestParam("rule") String ruleId){
		logger.info("/delete rule requested");
		 RoutingRule rule = routingRulesService.getRoutingRule(ruleId);
		routingRulesService.deleteRoutingRule(ruleId);
		return "redirect:/routingrules.htm?schema="+rule.getSchema();
	}
}
