package ro.allevo.fintpui.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
	
		private String guid;
		private String additionalinfo;
		private String correlationid;
		private String innerexception;
		private String machine;
		private String message;
		private String type;
		private Timestamp eventdate;
		private Timestamp insertdate;
		private BigDecimal service;
		
		public String getAdditionalinfo() {
			return additionalinfo;
		}
		public void setAdditionalinfo(String additionalinfo) {
			this.additionalinfo = additionalinfo;
		}
		public String getCorrelationid() {
			return correlationid;
		}
		public void setCorrelationid(String correlationid) {
			this.correlationid = correlationid;
		}
		public String getGuid() {
			return guid;
		}
		public void setGuid(String guid) {
			this.guid = guid;
		}
		public String getInnerexception() {
			return innerexception;
		}
		public void setInnerexception(String innerexception) {
			this.innerexception = innerexception;
		}
		public String getMachine() {
			return machine;
		}
		public void setMachine(String machine) {
			this.machine = machine;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public Timestamp getEventdate() {
			return eventdate;
		}
		public void setEventdate(Timestamp eventdate) {
			this.eventdate = eventdate;
		}
		public Timestamp getInsertdate() {
			return insertdate;
		}
		public void setInsertdate(Timestamp insertdate) {
			this.insertdate = insertdate;
		}
		public BigDecimal getService() {
			return service;
		}
		public void setService(BigDecimal service) {
			this.service = service;
		}
		
}
