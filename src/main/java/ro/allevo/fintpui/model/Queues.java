package ro.allevo.fintpui.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Queues {

	private String href;
	private Queue[] queues;
	@JsonProperty("_type")
	private String type;
	
	public String getHref() {
		return href;
	}
	
	public Queue[] getQueues() {
		return queues;
	}
	
	public String getType() {
		return type;
	}
	
}
