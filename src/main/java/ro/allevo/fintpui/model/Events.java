package ro.allevo.fintpui.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Events {
	
	private String href;
	private Event[] events;
	@JsonProperty("has_more")
	private String hasMore;
	
	@JsonProperty("_type")
	private String type;
	
	public String getHref() {
		return href;
	}
	
	public String getType() {
		return type;
	}

	public Event[] getEvents() {
		return events;
	}

	public String getHasMore() {
		return hasMore;
	}

}
