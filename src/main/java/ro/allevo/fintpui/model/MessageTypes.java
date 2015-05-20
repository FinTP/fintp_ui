package ro.allevo.fintpui.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageTypes {
	private String href;
	private MessageType[] messagetypes;
	@JsonProperty("_type")
	private String type;
	
	public String getHref() {
		return href;
	}

	public MessageType[] getMessagetypes() {
		return messagetypes;
	}

	public void setMessagetypes(MessageType[] messagetypes) {
		this.messagetypes = messagetypes;
	}
}
