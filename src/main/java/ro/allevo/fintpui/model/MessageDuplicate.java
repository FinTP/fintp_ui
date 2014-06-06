package ro.allevo.fintpui.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import ro.allevo.fintpui.service.MessageService;

public class MessageDuplicate {

	private final String guid;
	private final String correlid;
	private final String trn;
	private final String livearch;
	private final String feedback;
	private final String dupqueue;
	
	
	
	public MessageDuplicate(ResultSet resultSet) throws SQLException {
		
		trn = resultSet.getString("trn");
		guid = resultSet.getString("guid");
		correlid = resultSet.getString("correlid");
		livearch = resultSet.getString("livearch");
		feedback = resultSet.getString("feedback");
		dupqueue = resultSet.getString("dupqueue");
		
	}
	

	public String getCorrelid() {
		return correlid;
	}

	public String getTrn() {
		return trn;
	}

	public String getLivearch() {
		return livearch;
	}

	public String getDupqueue() {
		return dupqueue;
	}

	public String getFeedback() {
		return feedback;
	}

	public String getGuid() {
		return guid;
	}

}
