package ro.allevo.fintpui.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoutingRules {

	private String href;
	private RoutingRule[] routingrules;
	@JsonProperty("_type")
	private String type;
	@JsonProperty("has_more")
	private boolean hasMore;
	
	public String getHref() {
		return href;
	}
	
	public String getType() {
		return type;
	}

	public RoutingRule[] getRoutingrules() {
		return routingrules;
	}

}
