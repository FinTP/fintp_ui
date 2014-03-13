package ro.allevo.fintpui.utils;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import ro.allevo.fintpui.model.MessageReportInstance;
import ro.allevo.fintpui.services.FintpService;

public class JdbcClient {

	private String user;
	private String password;
	private String driver;
	private Connection connection;
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	
	public void establishConnection(){
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(driver, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void closeConnection(){
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private ResultSet performGenericQuery(String selectClause,
			String whereClause, String orderClause, String limitClause)
			throws SQLException {
		String query = selectClause + " " + whereClause + " " + orderClause
				+ " " + limitClause;
		System.out.println(query);
		return connection.createStatement().executeQuery(query);

	}
	
	public String count(String tableName, String whereClause) throws SQLException{
		String query = "select count(*) from " + tableName + " " + whereClause;
		ResultSet resultSet = connection.createStatement().executeQuery(query);
		if(resultSet.next()){
			return resultSet.getString(1);
		}else{
			return "0";
		}
	}
	
	public ArrayList<MessageReportInstance> getReports(Map<String,String> requestParameters, StringBuilder total) throws ParseException, SQLException{
		SimpleDateFormat databaseFormat = new SimpleDateFormat("yyMMdd");
		SimpleDateFormat pageInputFormat = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat insertDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		//building where clause
		String whereClause = "";
		if (requestParameters.get("interval").equals("current")) {
			
			whereClause+= "where date_trunc( 'day', insertdate ) = '"
					+new SimpleDateFormat("yyMMdd").format(new Date()) +"'";
		}else{
			whereClause+= "where date_trunc( 'day', insertdate ) >= '"
					+insertDateFormat.format(pageInputFormat.parse(requestParameters.get("startDate")))
					+"' and date_trunc( 'day', insertdate ) <= '" 
					+insertDateFormat.format(pageInputFormat.parse(requestParameters.get("endDate"))) 
					+"'";
		}
		if(!requestParameters.get("messageTypes").equals("")){
			whereClause+= " and msgtype = '" + requestParameters.get("messageTypes")+ "' ";
		}
		if(!requestParameters.get("sender").equals("")){
			whereClause+= " and lower(sender) like lower('%" + requestParameters.get("sender")+ "%') ";
		}
		if(!requestParameters.get("receiver").equals("")){
			whereClause+= " and lower(receiver) like lower('%" + requestParameters.get("receiver").equals("") + "%') ";
		}
		if(!requestParameters.get("trn").equals("")){
			whereClause+= " and lower(trn) like lower('%" +requestParameters.get("trn")+ "%') ";
		}
		if (!requestParameters.get("valueDate").equals("")) {
			String valueDateFormat= databaseFormat.format(pageInputFormat.parse(requestParameters.get("valueDate")));
			whereClause+= " and valuedate = '" + valueDateFormat+ "' ";
		}
		if(!requestParameters.get("batchID").equals("")){
			whereClause+= " and lower(batchid) like lower('%" +requestParameters.get("batchID")+ "%') ";
		}
		
		String orderField, order, limit, offset;
		if(requestParameters.containsKey("orderField"))
			orderField = requestParameters.get("orderField");
		else
			orderField = "insertdate";
		
		if(requestParameters.containsKey("order"))
			order = requestParameters.get("order");
		else
			order = "desc";
		
		if(requestParameters.containsKey("limit"))
			limit = requestParameters.get("limit");
		else
			limit = "100";
		
		if(requestParameters.containsKey("offset"))
			offset = requestParameters.get("offset");
		else
			offset = "0";
		
		//build order by clause
		String orderByClause = "order by " + orderField + " " + order;
		
		//TODO: different limit clause for Oracle
		//build limit clause
		String limitClause = "limit " + limit + " offset " + offset;
		
		ResultSet resultSet = performGenericQuery(
				MessageReportInstance.reportsProjection, whereClause, orderByClause, limitClause);
		ArrayList<MessageReportInstance> reportInstances = new ArrayList<>();
		while(resultSet.next()){
			reportInstances.add(new MessageReportInstance(resultSet));
		}
		
		total.append((count("findata.repstatft", whereClause)));
		
		return reportInstances;
	}
	
	public MessageReportInstance getReport(String correlId) throws SQLException{
		String whereClause = " where correlid = '" + correlId + "'";
		ResultSet resultSet = performGenericQuery(
				MessageReportInstance.reportsProjection, whereClause, "", "");
		if(!resultSet.next()){
			return null;
		}else{
			String payload = getPayload(correlId);
			String path = getClass().getClassLoader()
					.getResource(FintpService.NESTED_TABLES_XSLT).getPath();
			String friendlyPayload = FintpService.applyXSLT(payload, path);
			MessageReportInstance result = new MessageReportInstance(resultSet);
			result.setPayload(friendlyPayload);
			return result;
		}
	}
	
	
	public ArrayList<String> getFTMessageTypes() throws SQLException{
		ArrayList<String> result = new ArrayList<>();
		String query = "select distinct friendlyname from fincfg.msgtypes where businessarea = 'Funds Transfer'";
		ResultSet resultSet = connection.createStatement().executeQuery(query);
		while (resultSet.next()) {
			result.add(resultSet.getString("friendlyname"));
		}
		return result;
	}
	
	private String getPayload(String correlId){
		
		String query = "select payload "
				+ "from feedbackagg "
				+ "where correlid = '"+correlId+"'";
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			if(!resultSet.next()){
				 return null;
			}else{
				return resultSet.getString("payload");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
