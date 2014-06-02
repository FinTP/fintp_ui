package ro.allevo.fintpui.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageDD extends MessageInReports {

	public MessageDD(ResultSet resultSet) throws SQLException {
		super(resultSet);
	}

	@Override
	public String getTag() {
		return "DD";
	}

	@Override
	public boolean hasPayload() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasImage() {
		// TODO Auto-generated method stub
		return false;
	}

	

	

}
