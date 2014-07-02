package ro.allevo.fintpui.dao;

import java.util.ArrayList;

import ro.allevo.fintpui.model.RoutingRule;

public interface RoutingRuleDao {
	
	public ArrayList<RoutingRule> getAllRoutingRules();
	public ArrayList<RoutingRule> getRoutingRulesBySchema(String schemaName);
	public RoutingRule getRoutingRule(String name);
	public void insertRoutingRule(RoutingRule rule);
	public void updateRoutingRule(String name, RoutingRule rule);
	public void deleteRoutingRule(String name);
	

}
