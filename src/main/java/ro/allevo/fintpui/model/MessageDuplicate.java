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

public class MessageDuplicate {

	private final String guid;
	private final String correlationid;
	private final String trn;
	private final String livearch;
	private final String feedback;
	private final String queuename;
	
	
	
	public MessageDuplicate(ResultSet resultSet) throws SQLException {
		
		trn = resultSet.getString("trn");
		guid = resultSet.getString("guid");
		correlationid = resultSet.getString("correlationid");
		livearch = resultSet.getString("livearch");
		feedback = resultSet.getString("feedback");
		queuename = resultSet.getString("queuename");
		
	}
	

	

	public String getTrn() {
		return trn;
	}

	public String getLivearch() {
		return livearch;
	}

	public String getQueuename() {
		return queuename;
	}

	public String getFeedback() {
		return feedback;
	}

	public String getGuid() {
		return guid;
	}




	public String getCorrelationid() {
		return correlationid;
	}

}
