package ro.allevo.fintpui.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageDD extends MessageInReports {

	private final String cdtid;
	private final String valuedate;

	public MessageDD(ResultSet resultSet) throws SQLException {
		super(resultSet);
		cdtid = resultSet.getString("cdtid");
		valuedate = resultSet.getString("valuedate");
	}

	@Override
	public String getTag() {
		return "DD";
	}

	

	public String getCdtid() {
		return cdtid;
	}

	public String getValuedate() {
		return valuedate;
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
