package ro.allevo.fintpui.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse {

	private int code;
	private String message;
	private int uri;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getUri() {
		return uri;
	}
	public void setUri(int uri) {
		this.uri = uri;
	}
	
}
