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

public class MessageFT extends MessageInReports {


	private final String valuedate;	
	private final String ordbank;	
	private final String benbank;
	private final String service;
	
	public MessageFT(ResultSet resultSet) throws SQLException {
		super(resultSet, 0);
		valuedate = resultSet.getString("valuedate");
		ordbank = resultSet.getString("ordbank");
		benbank = resultSet.getString("benbank");
		service = resultSet.getString("service");
	}

	@Override
	public String getTag() {
		return "FT";
	}

//	@Override
//	public String getPayload() {
//		return super.getMessageService().getPayload(super.getCorrelid());
//	}
//
//	@Override
//	public String getImage() {
//		return null;
//	}

	public String getValuedate() {
		return valuedate;
	}

	public String getOrdbank() {
		return ordbank;
	}

	public String getBenbank() {
		return benbank;
	}

	public String getService() {
		return service;
	}

	@Override
	public boolean hasPayload() {
		return true;
	}

	@Override
	public boolean hasImage() {
		return false;
	}
	
}
