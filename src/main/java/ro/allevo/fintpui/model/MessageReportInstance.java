package ro.allevo.fintpui.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sun.xml.bind.v2.runtime.RuntimeUtil.ToStringAdapter;

public class MessageReportInstance {

	private String insertdate;
	private String msgtype;
	private String sender;
	private String receiver;
	private String trn;
	private String valuedate;
	private String amount;
	private String currency;
	private String dbtaccount;
	private String dbtcustname;
	private String ordbank;
	private String benbank;
	private String cdtaccount;
	private String cdtcustname;
	private String service;
	private String direction;
	private String state;
	private String batchid;
	private String correlid;
	private String payload;
	private String issuanceDate;
	private String maturityDate;
	private String dbtID;
	private String userid;
	
	
	//select clause
	public static final String reportsProjection = "select  insertdate,msgtype,sender,receiver,trn,valuedate,amount,currency,"
			+ "	dbtaccount,dbtcustname,ordbank,benbank,cdtaccount,cdtcustname,service,"
			+ " direction, correlid, case when errcode is null then state"
			+ " else state||' ['||errcode||']'"
			+ " end state,batchid,userid "
			+ " from findata.repstatft ";
	
	private MessageReportInstance(){
		
	}
	
	public static MessageReportInstance getFundsTransferMessage(ResultSet resultSet){
		try {
			MessageReportInstance messageReportInstance = new MessageReportInstance();
			messageReportInstance.setInsertdate(resultSet.getString("insertdate"));
			messageReportInstance.setMsgtype(resultSet.getString("msgtype"));
			messageReportInstance.setSender(resultSet.getString("sender"));
			messageReportInstance.setReceiver(resultSet.getString("receiver"));
			messageReportInstance.setTrn(resultSet.getString("trn"));
			//TODO: format here value date
			messageReportInstance.setValuedate(resultSet.getString("valuedate"));
			messageReportInstance.setAmount(resultSet.getString("amount"));
			messageReportInstance.setCurrency(resultSet.getString("currency"));
			messageReportInstance.setDbtaccount(resultSet.getString("dbtaccount"));
			messageReportInstance.setDbtcustname(resultSet.getString("dbtcustname"));
			messageReportInstance.setOrdbank(resultSet.getString("ordbank"));
			messageReportInstance.setBenbank(resultSet.getString("benbank"));
			messageReportInstance.setCdtaccount(resultSet.getString("cdtaccount"));
			messageReportInstance.setCdtcustname(resultSet.getString("cdtcustname"));
			messageReportInstance.setService(resultSet.getString("service"));
			messageReportInstance.setDirection(resultSet.getString("direction"));
			messageReportInstance.setState(resultSet.getString("state"));
			messageReportInstance.setBatchid(resultSet.getString("batchid"));
			messageReportInstance.setCorrelid(resultSet.getString("correlid"));
			return messageReportInstance;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static MessageReportInstance getDebitInstrumentsMessage(ResultSet resultSet){
		try {
			MessageReportInstance messageReportInstance = new MessageReportInstance();
			messageReportInstance.setInsertdate(resultSet.getString("insertdate"));
			messageReportInstance.setMsgtype(resultSet.getString("msgtype"));
			messageReportInstance.setSender(resultSet.getString("sender"));
			messageReportInstance.setReceiver(resultSet.getString("receiver"));
			messageReportInstance.setTrn(resultSet.getString("trn"));
			messageReportInstance.setInsertdate(resultSet.getString("issdate"));
			messageReportInstance.setMaturityDate(resultSet.getString("matdate"));
			messageReportInstance.setAmount(resultSet.getString("amount"));
			messageReportInstance.setCurrency(resultSet.getString("currency"));
			messageReportInstance.setDbtaccount(resultSet.getString("dbtaccount"));
			messageReportInstance.setDbtcustname(resultSet.getString("dbtcustname"));
			messageReportInstance.setDbtID(resultSet.getString("dbtid"));
			messageReportInstance.setCdtaccount(resultSet.getString("cdtaccount"));
			messageReportInstance.setCdtcustname(resultSet.getString("cdtcustname"));
			messageReportInstance.setDirection(resultSet.getString("direction"));
			messageReportInstance.setBatchid(resultSet.getString("batchid"));
			messageReportInstance.setUserid(resultSet.getString("userid"));
			messageReportInstance.setCorrelid(resultSet.getString("correlid"));
			messageReportInstance.setState(resultSet.getString("state"));
			
			return messageReportInstance;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

	public String getInsertdate() {
		return insertdate;
	}

	public void setInsertdate(String insertdate) {
		this.insertdate = insertdate;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getTrn() {
		return trn;
	}

	public void setTrn(String trn) {
		this.trn = trn;
	}

	public String getValuedate() {
		return valuedate;
	}

	public void setValuedate(String valuedate) {
		this.valuedate = valuedate;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDbtaccount() {
		return dbtaccount;
	}

	public void setDbtaccount(String dbtaccount) {
		this.dbtaccount = dbtaccount;
	}

	public String getDbtcustname() {
		return dbtcustname;
	}

	public void setDbtcustname(String dbtcustname) {
		this.dbtcustname = dbtcustname;
	}

	public String getOrdbank() {
		return ordbank;
	}

	public void setOrdbank(String ordbank) {
		this.ordbank = ordbank;
	}

	public String getBenbank() {
		return benbank;
	}

	public void setBenbank(String benbank) {
		this.benbank = benbank;
	}

	public String getCdtaccount() {
		return cdtaccount;
	}

	public void setCdtaccount(String cdtaccount) {
		this.cdtaccount = cdtaccount;
	}

	public String getCdtcustname() {
		return cdtcustname;
	}

	public void setCdtcustname(String cdtcustname) {
		this.cdtcustname = cdtcustname;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBatchid() {
		return batchid;
	}

	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}

	public String getCorrelid() {
		return correlid;
	}

	public void setCorrelid(String correlid) {
		this.correlid = correlid;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String toString(){
		return "correlid " + correlid;
	}

	public String getIssuanceDate() {
		return issuanceDate;
	}

	public void setIssuanceDate(String issuanceDate) {
		this.issuanceDate = issuanceDate;
	}

	public String getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}

	public String getDbtID() {
		return dbtID;
	}

	public void setDbtID(String dbtID) {
		this.dbtID = dbtID;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
}
