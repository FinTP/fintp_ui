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
		super(resultSet);
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
