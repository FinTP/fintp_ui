package ro.allevo.fintpui.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RoutingSchemas {
	
	private String href;
	private RoutingSchema[] routingschemas;
							
	@JsonProperty("_type")
	private String type;
	
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	
	public String getType() {
		return type;
	}
	public RoutingSchema[] getRoutingschemas() {
		return routingschemas;
	}
	public void setRoutingschemas(RoutingSchema[] routingschemas) {
		this.routingschemas = routingschemas;
	}
	
}
