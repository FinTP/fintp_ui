package ro.allevo.fintpui.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QueueTypes {

	private String href;
	private QueueType[] queuetypes;
	@JsonProperty("_type")
	private String type;
	
	public String getHref() {
		return href;
	}
	
	public String getType() {
		return type;
	}

	public QueueType[] getQueuetypes() {
		return queuetypes;
	}
}
