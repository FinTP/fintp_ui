package ro.allevo.fintpui.dao;

import ro.allevo.fintpui.model.RoutingRule;

public interface RoutingRuleDao {
	
	public RoutingRule[] getAllRoutingRules();
	public RoutingRule[] getRoutingRulesBySchema(String schemaName);
	public RoutingRule getRoutingRule(String name);
	public void insertRoutingRule(RoutingRule rule);
	public void updateRoutingRule(String name, RoutingRule rule);
	public void deleteRoutingRule(String name);
	

}
