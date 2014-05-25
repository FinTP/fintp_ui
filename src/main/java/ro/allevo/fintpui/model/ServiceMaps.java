package ro.allevo.fintpui.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceMaps {

	private String href;
	private ServiceMap[] servicemaps;
	@JsonProperty("_type")
	private String type;
	
	public String getHref() {
		return href;
	}
	
	public String getType() {
		return type;
	}

	public ServiceMap[] getServicemaps() {
		return servicemaps;
	}

}
