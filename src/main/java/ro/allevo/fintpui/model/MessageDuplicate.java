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
