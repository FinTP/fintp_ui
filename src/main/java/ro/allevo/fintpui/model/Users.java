package ro.allevo.fintpui.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Users {

	private String href;
	private User[] users;
	
	@JsonProperty("_type")
	private String type;
	
	public String getHref() {
		return href;
	}
	
	public String getType() {
		return type;
	}

	public User[] getUsers() {
		return users;
	}
}
