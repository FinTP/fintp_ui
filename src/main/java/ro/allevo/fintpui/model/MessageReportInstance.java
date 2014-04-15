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
	
	//select clause
	public static final String reportsProjection = "select  insertdate,msgtype,sender,receiver,trn,valuedate,amount,currency,"
			+ "	dbtaccount,dbtcustname,ordbank,benbank,cdtaccount,cdtcustname,service,"
			+ " direction, correlid, case when errcode is null then state"
			+ " else state||' ['||errcode||']'"
			+ " end state,batchid,userid "
			+ " from findata.repstatft ";
	
	public MessageReportInstance(ResultSet resultSet){
		try {
			setInsertdate(resultSet.getString("insertdate"));
			setMsgtype(resultSet.getString("msgtype"));
			setSender(resultSet.getString("sender"));
			setReceiver(resultSet.getString("receiver"));
			setTrn(resultSet.getString("trn"));
			//TODO: format here value date
			setValuedate(resultSet.getString("valuedate"));
			setAmount(resultSet.getString("amount"));
			setCurrency(resultSet.getString("currency"));
			setDbtaccount(resultSet.getString("dbtaccount"));
			setDbtcustname(resultSet.getString("dbtcustname"));
			setOrdbank(resultSet.getString("ordbank"));
			setBenbank(resultSet.getString("benbank"));
			setCdtaccount(resultSet.getString("cdtaccount"));
			setCdtcustname(resultSet.getString("cdtcustname"));
			setService(resultSet.getString("service"));
			setDirection(resultSet.getString("direction"));
			setState(resultSet.getString("state"));
			setBatchid(resultSet.getString("batchid"));
			setCorrelid(resultSet.getString("correlid"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
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
	
}
