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

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class MessageInReports {

	private final String insertdate;
	private final String msgtype;
	private final String sender;
	private final String receiver;
	private final String trn;
	private final String amount;
	private final String currency;
	private final String dbtaccount;
	private final String dbtcustname;
	private final String cdtaccount;
	private final String cdtcustname;
	private final String direction;
	private final String state;
	private final String batchid;
	private final String userid;
	private String correlid;
	
	
	
	
	public MessageInReports(ResultSet resultSet, int exclude) throws SQLException {
		
		if (exclude!=1){
		insertdate = resultSet.getString("insertdate");
		
		msgtype = resultSet.getString("msgtype");
		sender = resultSet.getString("sender");
		receiver = resultSet.getString("receiver");
		trn = resultSet.getString("trn");
		amount = resultSet.getString("amount");
		currency = resultSet.getString("currency");
		dbtaccount = resultSet.getString("dbtaccount");
		dbtcustname = resultSet.getString("dbtcustname");
		cdtaccount = resultSet.getString("cdtaccount");
		cdtcustname = resultSet.getString("cdtcustname");
		direction = resultSet.getString("direction");
		state = resultSet.getString("state");
		batchid = resultSet.getString("batchid");
		correlid = resultSet.getString("correlid");
		userid = resultSet.getString("userid");}
		
		else
		{
			insertdate="";
			msgtype="";
			sender="";
			receiver ="";
			trn ="";
			amount="";
			currency ="";
			dbtaccount ="";
			dbtcustname="";
			cdtaccount="";
			cdtcustname="";
			direction="";
			state ="";
			batchid ="";
			correlid ="";
			userid="";
		}
		
	}
	
	public abstract String getTag();
	
	public abstract boolean hasPayload();

	public abstract boolean hasImage();
	
	public String getCorrelid() {
		return correlid;
	}

	public String getInsertdate() {
		return insertdate;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public String getSender() {
		return sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public String getTrn() {
		return trn;
	}

	public String getAmount() {
		return amount;
	}

	public String getCurrency() {
		return currency;
	}

	public String getDbtaccount() {
		return dbtaccount;
	}

	public String getDbtcustname() {
		return dbtcustname;
	}

	public String getCdtaccount() {
		return cdtaccount;
	}

	public String getCdtcustname() {
		return cdtcustname;
	}

	public String getDirection() {
		return direction;
	}

	public String getState() {
		return state;
	}

	public String getBatchid() {
		return batchid;
	}

	public String getUserid() {
		return userid;
	}

}
