/*
* FinTP - Financial Transactions Processing Application
* Copyright (C) 2013 Business Information Systems (Allevo) S.R.L.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>
* or contact Allevo at : 031281 Bucuresti, 23C Calea Vitan, Romania,
* phone +40212554577, office@allevo.ro <mailto:office@allevo.ro>, www.allevo.ro.
*/

package ro.allevo.fintpui.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceMap {

	private BigDecimal serviceid;
	private String delayednotifq;
	private BigDecimal duplicatecheck;
	private String duplicatemap;
	private String duplicatenotifq;
	private String duplicateq;
	private String exitpoint;
	private String friendlyname;
	private BigDecimal heartbeatinterval;
	private BigDecimal ioidentifier;
	private Timestamp lastheartbeat;
	private BigDecimal lastsessionid;
	private String partner;
	private BigDecimal servicetype;
	private String sessionid;
	private BigDecimal status;
	private String version;
	public BigDecimal getServiceid() {
		return serviceid;
	}
	public void setServiceid(BigDecimal serviceid) {
		this.serviceid = serviceid;
	}
	public String getDelayednotifq() {
		return delayednotifq;
	}
	public void setDelayednotifq(String delayednotifq) {
		this.delayednotifq = delayednotifq;
	}
	public BigDecimal getDuplicatecheck() {
		return duplicatecheck;
	}
	public void setDuplicatecheck(BigDecimal duplicatecheck) {
		this.duplicatecheck = duplicatecheck;
	}
	public String getDuplicatemap() {
		return duplicatemap;
	}
	public void setDuplicatemap(String duplicatemap) {
		this.duplicatemap = duplicatemap;
	}
	public String getDuplicatenotifq() {
		return duplicatenotifq;
	}
	public void setDuplicatenotifq(String duplicatenotifq) {
		this.duplicatenotifq = duplicatenotifq;
	}
	public String getDuplicateq() {
		return duplicateq;
	}
	public void setDuplicateq(String duplicateq) {
		this.duplicateq = duplicateq;
	}
	public String getExitpoint() {
		return exitpoint;
	}
	public void setExitpoint(String exitpoint) {
		this.exitpoint = exitpoint;
	}
	public String getFriendlyname() {
		return friendlyname;
	}
	public void setFriendlyname(String friendlyname) {
		this.friendlyname = friendlyname;
	}
	public BigDecimal getHeartbeatinterval() {
		return heartbeatinterval;
	}
	public void setHeartbeatinterval(BigDecimal heartbeatinterval) {
		this.heartbeatinterval = heartbeatinterval;
	}
	public BigDecimal getIoidentifier() {
		return ioidentifier;
	}
	public void setIoidentifier(BigDecimal ioidentifier) {
		this.ioidentifier = ioidentifier;
	}
	public Timestamp getLastheartbeat() {
		return lastheartbeat;
	}
	public void setLastheartbeat(Timestamp lastheartbeat) {
		this.lastheartbeat = lastheartbeat;
	}
	public BigDecimal getLastsessionid() {
		return lastsessionid;
	}
	public void setLastsessionid(BigDecimal lastsessionid) {
		this.lastsessionid = lastsessionid;
	}
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public BigDecimal getServicetype() {
		return servicetype;
	}
	public void setServicetype(BigDecimal servicetype) {
		this.servicetype = servicetype;
	}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	public BigDecimal getStatus() {
		return status;
	}
	public void setStatus(BigDecimal status) {
		this.status = status;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	
}
