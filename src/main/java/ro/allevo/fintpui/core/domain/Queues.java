package ro.allevo.fintpui.core.domain;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Queues {
	private String href;
	private Queue[] queues;
	@JsonProperty("_type")
	private String type;
	
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public Queue[] getQueues() {
		return queues;
	}
	public void setQueues(Queue[] queues) {
		this.queues = queues;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
