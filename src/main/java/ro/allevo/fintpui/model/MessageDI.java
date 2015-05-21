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


import org.springframework.beans.factory.annotation.Autowired;

import ro.allevo.fintpui.service.MessageService;

public class MessageDI extends MessageInReports{

	@Autowired
	MessageService messageService;
	
	private final String matdate;
	private final String issdate;
	private final String dbtid;
	
	public MessageDI(ResultSet resultSet) throws SQLException {
		super(resultSet, 0);
		matdate = resultSet.getString("matdate");
		issdate = resultSet.getString("issdate");
		dbtid = resultSet.getString("dbtid");
	}

	@Override
	public String getTag() {
		return "DI";
	}



//	@Override
//	public String getPayload() {
//		return messageService.getPayload(super.getCorrelid());
//	}
//
//	@Override
//	public String getImage() {
//		return messageService.getImage(super.getCorrelid());
//	}
	
	public String getMatdate() {
		return matdate;
	}

	public String getIssdate() {
		return issdate;
	}

	public String getDbtid() {
		return dbtid;
	}

	@Override
	public boolean hasPayload() {
		return true;
	}

	@Override
	public boolean hasImage() {
		return true;
	}

}
