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
	private String schemaCopy;
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
	public String getSchemaCopy() {
		return schemaCopy;
	}
	public void setSchemaCopy(String schemaCopy) {
		this.schemaCopy = schemaCopy;
	}
	
	
}
