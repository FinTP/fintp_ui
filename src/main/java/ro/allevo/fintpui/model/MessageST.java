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

public class MessageST extends MessageInReports {


	private final String msgtype;
	private final String sender;
	private final String receiver;
	private final String stmtref;
	private final String currency;
	private final String direction;
	private final String state;
	private final String userid;

	private final String stmtnumber;
	private final String accnumber;
	private final String obdate;
	private final String cbdate;
	private final String ibalance;
	private final String fbalance;
	
	public MessageST(ResultSet resultSet) throws SQLException {
		super(resultSet, 1);
		
		msgtype = resultSet.getString("msgtype");
		sender = resultSet.getString("sender");
		receiver = resultSet.getString("receiver");
		stmtref = resultSet.getString("stmtref");
		accnumber = resultSet.getString("accnumber");
		stmtnumber = resultSet.getString("stmtnumber");
		obdate = resultSet.getString("obdate");
		cbdate = resultSet.getString("cbdate");
		ibalance = resultSet.getString("ibalance");
		fbalance = resultSet.getString("fbalance");
		currency = resultSet.getString("currency");
		direction = resultSet.getString("direction");
		state = resultSet.getString("state");
		userid = resultSet.getString("userid");
		
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
		return stmtref;
	}

	public String getCurrency() {
		return currency;
	}

	public String getDirection() {
		return direction;
	}

	public String getState() {
		return state;
	}

	public String getUserid() {
		return userid;
	}
	public String getStmtnumber() {
		return stmtnumber;
	}
	public String getStmtref() {
		return stmtref;
	}

	public String getAccnumber() {
		return accnumber;
	}

	public String getObdate() {
		return obdate;
	}

	public String getCbdate() {
		return cbdate;
	}

	public String getIbalance() {
		return ibalance;
	}

	public String getFbalance() {
		return fbalance;
	}


	@Override
	public String getTag() {
		// TODO Auto-generated method stub
		return "ST";
	}


	@Override
	public boolean hasPayload() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean hasImage() {
		// TODO Auto-generated method stub
		return false;
	}
}
