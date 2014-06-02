package ro.allevo.fintpui.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeLimit {

	private String limitname;
	private String limittime;
	public String getLimitname() {
		return limitname;
	}
	public void setLimitname(String limitname) {
		this.limitname = limitname;
	}
	public String getLimittime() {
		return limittime;
	}
	public void setLimittime(String limittime) {
		this.limittime = limittime;
	}
}
