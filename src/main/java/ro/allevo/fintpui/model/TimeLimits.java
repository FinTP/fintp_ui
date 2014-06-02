package ro.allevo.fintpui.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TimeLimits{

	private String href;
	private TimeLimit[] timelimits;
	@JsonProperty("_type")
	private String type;
	
	public String getHref() {
		return href;
	}
	
	public String getType() {
		return type;
	}

	public TimeLimit[] getTimelimits() {
		return timelimits;
	}

}