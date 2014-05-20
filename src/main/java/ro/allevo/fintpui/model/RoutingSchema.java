package ro.allevo.fintpui.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RoutingSchema {

	private String name;
	private int active;
	private String description;
	private String isvisible;
	private String sessioncode;
	private String startlimit;
	private String stoplimit;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public String getIsvisible() {
		return isvisible;
	}
	public void setIsvisible(String isvisible) {
		this.isvisible = isvisible;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSessioncode() {
		return sessioncode;
	}
	public void setSessioncode(String sessioncode) {
		this.sessioncode = sessioncode;
	}
	public String getStartlimit() {
		return startlimit;
	}
	public void setStartlimit(String startlimit) {
		this.startlimit = startlimit;
	}
	public String getStoplimit() {
		return stoplimit;
	}
	public void setStoplimit(String stoplimit) {
		this.stoplimit = stoplimit;
	}
	
	
}
