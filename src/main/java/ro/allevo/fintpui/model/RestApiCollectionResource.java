package ro.allevo.fintpui.model;

import com.fasterxml.jackson.annotation.JsonProperty;


public class RestApiCollectionResource<ResourceType> {

	private String href;
	@JsonProperty("_type")
	private String type;
	@JsonProperty("timelimits")
	private ResourceType[] collection;
	
	public String getHref() {
		return href;
	}
	
	public ResourceType[] getCollection(){
		return collection;
	}
	
	public String getType() {
		return type;
	}
	
}
