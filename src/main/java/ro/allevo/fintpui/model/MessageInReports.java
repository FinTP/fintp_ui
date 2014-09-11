package ro.allevo.fintpui.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class MessageInReports {

	private final String insertdate;
	private final String msgtype;
	private final String sender;
	private final String receiver;
	private final String trn;
	private final String amount;
	private final String currency;
	private final String dbtaccount;
	private final String dbtcustname;
	private final String cdtaccount;
	private final String cdtcustname;
	private final String direction;
	private final String state;
	private final String batchid;
	private final String userid;
	private String correlid;
	
	
	
	
	public MessageInReports(ResultSet resultSet, int exclude) throws SQLException {
		
		if (exclude!=1){
		insertdate = resultSet.getString("insertdate");
		
		msgtype = resultSet.getString("msgtype");
		sender = resultSet.getString("sender");
		receiver = resultSet.getString("receiver");
		trn = resultSet.getString("trn");
		amount = resultSet.getString("amount");
		currency = resultSet.getString("currency");
		dbtaccount = resultSet.getString("dbtaccount");
		dbtcustname = resultSet.getString("dbtcustname");
		cdtaccount = resultSet.getString("cdtaccount");
		cdtcustname = resultSet.getString("cdtcustname");
		direction = resultSet.getString("direction");
		state = resultSet.getString("state");
		batchid = resultSet.getString("batchid");
		correlid = resultSet.getString("correlid");
		userid = resultSet.getString("userid");}
		
		else
		{
			insertdate="";
			msgtype="";
			sender="";
			receiver ="";
			trn ="";
			amount="";
			currency ="";
			dbtaccount ="";
			dbtcustname="";
			cdtaccount="";
			cdtcustname="";
			direction="";
			state ="";
			batchid ="";
			correlid ="";
			userid="";
		}
		
	}
	
	public abstract String getTag();
	
	public abstract boolean hasPayload();

	public abstract boolean hasImage();
	
	public String getCorrelid() {
		return correlid;
	}

	public String getInsertdate() {
		return insertdate;
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
		return trn;
	}

	public String getAmount() {
		return amount;
	}

	public String getCurrency() {
		return currency;
	}

	public String getDbtaccount() {
		return dbtaccount;
	}

	public String getDbtcustname() {
		return dbtcustname;
	}

	public String getCdtaccount() {
		return cdtaccount;
	}

	public String getCdtcustname() {
		return cdtcustname;
	}

	public String getDirection() {
		return direction;
	}

	public String getState() {
		return state;
	}

	public String getBatchid() {
		return batchid;
	}

	public String getUserid() {
		return userid;
	}

}
