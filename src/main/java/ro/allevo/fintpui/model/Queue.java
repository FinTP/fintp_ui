package ro.allevo.fintpui.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Queue {
	private String name;
	private String description;
	private Integer holdstatus;
	private String connector;
	private String type;
	private Integer priority;
	private Integer batchno;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getHoldstatus() {
		return holdstatus;
	}
	public void setHoldstatus(Integer holdstatus) {
		this.holdstatus = holdstatus;
	}
	public String getConnector() {
		return connector;
	}

	public void setConnector(String connector) {
		this.connector = connector;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Integer getBatchno() {
		return batchno;
	}
	public void setBatchno(Integer batchno) {
		this.batchno = batchno;
	}
}
