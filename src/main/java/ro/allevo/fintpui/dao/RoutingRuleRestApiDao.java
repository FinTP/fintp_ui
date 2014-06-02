package ro.allevo.fintpui.dao;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import ro.allevo.fintpui.model.RoutingRule;
import ro.allevo.fintpui.model.RoutingRules;
import ro.allevo.fintpui.utils.RestClient;
import ro.allevo.fintpui.utils.servlets.ServletsHelper;

public class RoutingRuleRestApiDao implements RoutingRuleDao{

	@Autowired
	ServletsHelper servletsHelper;
	
	@Override
	public RoutingRule[] getAllRoutingRules() {
		RestTemplate client = new RestClient();
		String url = servletsHelper.getUrl() + "/routingrules";
		RoutingRules entity = client.getForObject(url, RoutingRules.class);
		return entity.getRoutingrules();
	}
	
	@Override
	public RoutingRule[] getRoutingRulesBySchema(String schemaName) {
		RestTemplate client = new RestClient();
		String url = servletsHelper.getUrl() + "/routingschemas/"+ schemaName + "/routingrules";
		RoutingRules entity = client.getForObject(url, RoutingRules.class);
		return entity.getRoutingrules();
	}


	@Override
	public RoutingRule getRoutingRule(String name) {
		RestTemplate client = new RestClient();
		String url = servletsHelper.getUrl() + "/routingrules/"+name;
		RoutingRule entity = client.getForObject(url, RoutingRule.class);
		return entity;
	}

	@Override
	public void insertRoutingRule(RoutingRule rule) {
		URI uri = UriBuilder.fromUri(servletsHelper.getUrl()).path("routingrules").build();
		servletsHelper.postAPIResource(uri, rule);
	}

	@Override
	public void updateRoutingRule(String name, RoutingRule rule) {
		URI uri = UriBuilder.fromUri(servletsHelper.getUrl())
				.path("routingrules").path(name).build();
		servletsHelper.putAPIResource(uri, name, rule);
	}

	@Override
	public void deleteRoutingRule(String name) {
		URI uri = UriBuilder.fromUri(servletsHelper.getUrl())
				.path("routingrules").path(name).build();
		servletsHelper.deleteAPIResource(uri);
	}


}
