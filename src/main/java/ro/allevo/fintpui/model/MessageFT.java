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
