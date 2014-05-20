package ro.allevo.fintpui.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.core.UriBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import ro.allevo.fintpui.model.RoutingRule;
import ro.allevo.fintpui.model.RoutingRules;
import ro.allevo.fintpui.model.RoutingSchema;
import ro.allevo.fintpui.utils.RestClient;
import ro.allevo.fintpui.utils.servlets.ServletsHelper;


@Controller
@RequestMapping("routingrules")
public class RoutingRulesController {
	@Autowired 
	private ServletsHelper servletsHelper;
	
	private static Logger logger = LogManager.getLogger(RoutingRulesController.class
			.getName());
	
	@RequestMapping(method = RequestMethod.GET)
	public String printRules(ModelMap model, @RequestParam("schema") String schema){
		logger.info("/routingrules requested");
		model.addAttribute("rules", getRoutingRules(schema));
		model.addAttribute("schema", getRoutingSchemaByName(schema));
		HashMap<String, ArrayList<RoutingRule>> mappedRules = getRulesGroupedByQueues(getRoutingRules(schema));
		model.addAttribute("mappedRules", mappedRules);
		return "tiles/routingrules";
	}
	
	private RoutingRule[] getRoutingRules(String schema) {
		URI uri = UriBuilder.fromPath(servletsHelper.getUrl())
				.path("routingschemas").path(schema).path("routingrules")
				.build();
		RestTemplate client = new RestClient();
		RoutingRules rules = client.getForObject(uri.toString(), RoutingRules.class);
		return rules.getRoutingrules();
	}
	
	private RoutingSchema getRoutingSchemaByName(String name) {
		RestTemplate client = new RestClient();
		String url = servletsHelper.getUrl() + "/routingschemas/"+name;
		RoutingSchema schema = client.getForObject(url, RoutingSchema.class);
		return schema;
	}
	
	private HashMap<String, ArrayList<RoutingRule>> getRulesGroupedByQueues(RoutingRule[] rules){
		HashMap<String, ArrayList<RoutingRule>> map = new HashMap<>();
		for(RoutingRule rule : rules){
			String queueName = rule.getQueue();
			if(!map.containsKey(queueName)){
				ArrayList<RoutingRule> routingRules = new ArrayList<>();
				routingRules.add(rule);
				map.put(queueName, routingRules);
			}else{
				map.get(queueName).add(rule);
			}
		}
		return map;
	}
	
	
	
	
}
