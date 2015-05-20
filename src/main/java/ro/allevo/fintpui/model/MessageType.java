package ro.allevo.fintpui.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageType {
	private String friendlyname;
	private String businessarea;
	private String messagetype;
	private String parentmsgtype;
	private String reportingstorage;
	private String storage;
	
	public String getFriendlyname() {
		return friendlyname;
	}

	public void setFriendlyname(String friendlyname) {
		this.friendlyname = friendlyname;
	}

	public String getBusinessarea() {
		return businessarea;
	}

	public void setBusinessarea(String businessarea) {
		this.businessarea = businessarea;
	}

	public String getMessagetype() {
		return messagetype;
	}

	public void setMessagetype(String messagetype) {
		this.messagetype = messagetype;
	}

	public String getParentmsgtype() {
		return parentmsgtype;
	}

	public void setParentmsgtype(String parentmsgtype) {
		this.parentmsgtype = parentmsgtype;
	}

	public String getReportingstorage() {
		return reportingstorage;
	}

	public void setReportingstorage(String reportingstorage) {
		this.reportingstorage = reportingstorage;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}
}
