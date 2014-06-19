package ro.allevo.fintpui.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.ws.rs.core.UriBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import ro.allevo.fintpui.dao.RoutingRuleDao;
import ro.allevo.fintpui.model.RoutingRule;
import ro.allevo.fintpui.model.RoutingRules;
import ro.allevo.fintpui.utils.RestClient;

public class RoutingRulesService {
	
	@Autowired
	private RoutingRuleDao routingRuleDao;
	
	public RoutingRule[] getAllRoutingRules(){
		return routingRuleDao.getAllRoutingRules();
	}
	
	public RoutingRule[] getRoutingRulesBySchema(String schemaName) {
		return routingRuleDao.getRoutingRulesBySchema(schemaName);
	}
	
	public ArrayList<String> getAllRoutingRuleIds(){
		ArrayList<String> result = new ArrayList<>();
		for(RoutingRule routingRule : getAllRoutingRules()){
			result.add(""+routingRule.getGuid());
		}
		return result;
	}
	
	public ArrayList<String> getRoutingRulesIdsBySchema(String schemaName) {
		ArrayList<String> ruleIds = new ArrayList<>();
		for(RoutingRule routingRule : routingRuleDao.getRoutingRulesBySchema(schemaName)){
			ruleIds.add(""+routingRule.getGuid());
		}
		return ruleIds;
	}

	public void insertRoutingRule(RoutingRule routingRule){
		routingRuleDao.insertRoutingRule(routingRule);
	}
	
	public void updateRoutingRUle(String ruleId, RoutingRule routingRule){
		routingRuleDao.updateRoutingRule(ruleId, routingRule);
	}
	
	public void deleteRoutingRule(String ruleId){
		routingRuleDao.deleteRoutingRule(ruleId);
	}
	
	public RoutingRule getRoutingRule(String ruleId){
		return routingRuleDao.getRoutingRule(ruleId);
	}
	
	public void copyRules(String sourceSchema, String destSchema){
		//get rules from source
		RoutingRule[] rulesToBeCopied = routingRuleDao.getRoutingRulesBySchema(sourceSchema);
		
		//set them the new schema, then post
		for(RoutingRule rule : rulesToBeCopied){
			rule.setSchema(destSchema);
			routingRuleDao.insertRoutingRule(rule);
		}
	}
	
	public void deleteRulesFromSchema(String sourceSchema){
		
		RoutingRule[] rulesToBeCopied = routingRuleDao.getRoutingRulesBySchema(sourceSchema);
		
		//set them the new schema, then post
		for(RoutingRule rule : rulesToBeCopied){
			routingRuleDao.deleteRoutingRule(""+rule.getGuid());
		}
	}
	
	
	public Map<String, ArrayList<RoutingRule>> getRulesGroupedByQueues(RoutingRule[] rules){
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
		Map<String, ArrayList<RoutingRule>> sortedMap = new TreeMap<String, ArrayList<RoutingRule>>(map);
		return sortedMap;
	}
}
