package ro.allevo.fintpui.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RoutingRule {
	
	private int guid;
	private String queue;
	private String description;
	private String messagecondition;
	private String functioncondition;
	private String metadatacondition;
	private String action;
	private String schema;
	private int sequence;
	private int ruletype;
	public int getGuid() {
		return guid;
	}
	public void setGuid(int guid) {
		this.guid = guid;
	}
	public String getQueue() {
		return queue;
	}
	public void setQueue(String queue) {
		this.queue = queue;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMessagecondition() {
		return messagecondition;
	}
	public void setMessagecondition(String messagecondition) {
		this.messagecondition = messagecondition;
	}
	public String getFunctioncondition() {
		return functioncondition;
	}
	public void setFunctioncondition(String functioncondition) {
		this.functioncondition = functioncondition;
	}
	public String getMetadatacondition() {
		return metadatacondition;
	}
	public void setMetadatacondition(String metadatacondition) {
		this.metadatacondition = metadatacondition;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public int getRuletype() {
		return ruletype;
	}
	public void setRuletype(int ruletype) {
		this.ruletype = ruletype;
	}
	
	@Override
	public String toString(){
		String result = "[guid = " + guid 
				+ "; description = " +  description
				+ "; schema = " + schema+ "]";
		return result;
	}
	
}
