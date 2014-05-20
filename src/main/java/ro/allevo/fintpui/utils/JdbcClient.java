package ro.allevo.fintpui.utils;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;
import ro.allevo.fintpui.controllers.MessageController;
import ro.allevo.fintpui.model.MessageReportInstance;
import ro.allevo.fintpui.model.MessagesGroup;

public class JdbcClient {

	private static final String FT_MESSAGES_QUERY = "select distinct friendlyname from fincfg.msgtypes where businessarea = 'Funds Transfer'";
	private static final String BIC_CODES_QUERY = "select bic from fincfg.biccodes";
	private static final String CURRENCIES_QUERY = "select currency from fincfg.currencies";
	private static final String STATES_QUERY = "select status from fincfg.reportingtxstates";
	private static final String USERS_NAMES_QUERY = "select username from fincfg.users";
	private static final String USERS_IDS_QUERY = "select userid from fincfg.users";
	
	private final ArrayList<String> services = new ArrayList<>(Arrays.asList("ROL", "FinCopy", "TGT"));  
	private final ArrayList<String> directions = new ArrayList<>(Arrays.asList("Incoming", "Outgoing"));
 	
	private String user;
	private String password;
	private String driver;
	private String url;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void establishConnection(){
		try {
			Class.forName(driver);
			if(connection == null || connection.isClosed()){
				Context context = (Context) new InitialContext().lookup("java:comp/env");
				DataSource dataSource = (DataSource) context.lookup("jdbc/toFintpConfig");
				connection = dataSource.getConnection();
				}			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (NamingException e) {
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
		return connection.createStatement().executeQuery(query);
	}
	
	public ArrayList<MessageReportInstance> getReportsByStroedProcedure(Map<String,String> requestParameters, StringBuilder total) throws ParseException{
		SimpleDateFormat html5Format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
		SimpleDateFormat dbFormat = new SimpleDateFormat("dd MM yyyy hh:mm:ss");
		SimpleDateFormat dbFormatDateOnly = new SimpleDateFormat("dd MM yyyy");
		SimpleDateFormat DateFormat = new SimpleDateFormat("MM/dd/yyyy");
		//TODO: change valueDateFormat or html5Format to pe the same
		SimpleDateFormat valueDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		
		SimpleDateFormat valueDateDBFormat = new SimpleDateFormat("yyMMdd");
		String minDate = null, maxDate = null;
	
		if (requestParameters.get("interval").equals("current")) {
			//if current date is selected, pass as arguments the beggining and ending of current day
			minDate = dbFormatDateOnly.format(new Date()) + " 00:00:00";
			maxDate = dbFormatDateOnly.format(new Date()) + " 23:59:59";
		}else{
			minDate = dbFormatDateOnly.format(DateFormat.parse(requestParameters.get("startDate")))+" "+requestParameters.get("startTime");
			maxDate = dbFormatDateOnly.format(DateFormat.parse(requestParameters.get("endDate")))+" "+requestParameters.get("endTime");
		}

		ArrayList<MessageReportInstance> reportInstances = new ArrayList<>();
		String procedure = getProcedureCallString("findata.getftpayments", 26);
		
		try {
			connection.setAutoCommit(false);
			CallableStatement statement = connection.prepareCall(procedure);
			statement.setString(1, minDate);
			statement.setString(2, maxDate);
			if(!requestParameters.get("messageTypes").equals("")){
				statement.setString(3, requestParameters.get("messageTypes"));
			}else{
				statement.setNull(3, Types.VARCHAR);
			}
			if(!requestParameters.get("sender").equals("")){
				statement.setString(4, requestParameters.get("sender"));
			}else{
				statement.setNull(4, Types.VARCHAR);
			}
			if(!requestParameters.get("receiver").equals("")){
				statement.setString(5, requestParameters.get("receiver"));
			}else{
				statement.setNull(5, Types.VARCHAR);
			}
			if(!requestParameters.get("trn").equals("")){
				statement.setString(6, requestParameters.get("trn"));
			}else{
				statement.setNull(6, Types.VARCHAR);
			}
			if (!requestParameters.get("valueDate").equals("")) {
				String valueDate= valueDateDBFormat.format(valueDateFormat.parse(requestParameters.get("valueDate")));
				statement.setString(7, valueDate);
			}else{
				statement.setNull(7, Types.VARCHAR);
			}
			if (!requestParameters.get("minAmount").equals("")) {
				statement.setBigDecimal(8,
						new BigDecimal(requestParameters.get("minAmount")));
			}else{
				statement.setNull(8, Types.NUMERIC);
			}
			if (!requestParameters.get("maxAmount").equals("")) {
				statement.setBigDecimal(9,
						new BigDecimal(requestParameters.get("maxAmount")));
			}else{
				statement.setNull(9, Types.NUMERIC);
			}
			if(!requestParameters.get("currency").equals("")){
				statement.setString(10, requestParameters.get("currency"));
			}else{
				statement.setNull(10, Types.VARCHAR);
			}
			if(!requestParameters.get("dbtaccount").equals("")){
				statement.setString(11, requestParameters.get("dbtaccount"));
			}else{
				statement.setNull(11, Types.VARCHAR);
			}
			if(!requestParameters.get("dbtcustname").equals("")){
				statement.setString(12, requestParameters.get("dbtcustname"));
			}else{
				statement.setNull(12, Types.VARCHAR);
			}
			if(!requestParameters.get("ordbank").equals("")){
				statement.setString(13, requestParameters.get("ordbank"));
			}else{
				statement.setNull(13, Types.VARCHAR);
			}
			if(!requestParameters.get("benbank").equals("")){
				statement.setString(14, requestParameters.get("benbank"));
			}else{
				statement.setNull(14, Types.VARCHAR);
			}
			if(!requestParameters.get("cdtaccount").equals("")){
				statement.setString(15, requestParameters.get("cdtaccount"));
			}else{
				statement.setNull(15, Types.VARCHAR);
			}
			if(!requestParameters.get("cdtcustname").equals("")){
				statement.setString(16, requestParameters.get("cdtcustname"));
			}else{
				statement.setNull(16, Types.VARCHAR);
			}
			if(!requestParameters.get("service").equals("")){
				statement.setString(17, requestParameters.get("service"));
			}else{
				statement.setNull(17, Types.VARCHAR);
			}
			if(!requestParameters.get("direction").equals("")){
				statement.setString(18, requestParameters.get("direction"));
			}else{
				statement.setNull(18, Types.VARCHAR);
			}
			if(!requestParameters.get("state").equals("")){
				statement.setString(19, requestParameters.get("state"));
			}else{
				statement.setNull(19, Types.VARCHAR);
			}
			if(!requestParameters.get("batchID").equals("")){
				statement.setString(20, requestParameters.get("batchID"));
			}else{
				statement.setNull(20, Types.VARCHAR);
			}

			if(!requestParameters.get("userid").equals("")){
				statement.setString(21, requestParameters.get("userid"));
			}else{
				statement.setNull(21, Types.VARCHAR);
			}
		
			if(requestParameters.containsKey("orderField")
					&& requestParameters.get("orderField")!=null){
				statement.setString(22, requestParameters.get("orderField"));
			}else{
				statement.setNull(22, Types.VARCHAR);
			}
			if(requestParameters.containsKey("order")
					&& requestParameters.get("order")!=null){
				statement.setString(23, requestParameters.get("order"));
			}else{
				statement.setNull(23, Types.VARCHAR);
			}
			if (requestParameters.get("offset")!=null) {
				statement.setInt(24,
						Integer.parseInt(requestParameters.get("offset")));
			} else {
				statement.setNull(24, Types.INTEGER);
			}
			if (requestParameters.get("limit")!=null) {
				statement.setInt(25,
						Integer.parseInt(requestParameters.get("limit")));
			} else {
				statement.setNull(25, Types.INTEGER);
			}
			if (driver.contains("oracle")){
				statement.registerOutParameter(26, OracleTypes.CURSOR);
			}else{
				statement.registerOutParameter(26, Types.OTHER);
			}
			statement.execute();
			ResultSet resultSet = (ResultSet) statement.getObject(26);
			boolean gotTotal = false;
			while(resultSet.next()){
				reportInstances.add(new MessageReportInstance(resultSet));
				if(!gotTotal){
					total.append(resultSet.getInt("rnummax"));
					gotTotal=true;
				}
			}
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
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
					.getResource(MessageController.NESTED_TABLES_XSLT).getPath();
			String friendlyPayload = MessageController.applyXSLT(payload, path);
			MessageReportInstance result = new MessageReportInstance(resultSet);
			result.setPayload(friendlyPayload);
			return result;
		}
	}
	
	
	public ArrayList<String> getFTMessageTypes() throws SQLException{
		return getDistinctTypes(FT_MESSAGES_QUERY, "friendlyname");
	}
	
	public ArrayList<String> getBicCodes() throws SQLException{
		return getDistinctTypes(BIC_CODES_QUERY, "bic");
	}
	
	public ArrayList<String> getCurrencies() throws SQLException{
		return getDistinctTypes(CURRENCIES_QUERY, "currency");
	}
	
	public ArrayList<String> getStates() throws SQLException{
		return getDistinctTypes(STATES_QUERY, "status");
	}
	
	public ArrayList<String> getUserNames() throws SQLException{
		return getDistinctTypes(USERS_NAMES_QUERY, "username");
	}
	
	public ArrayList<String> getUserIds() throws SQLException{
		return getDistinctTypes(USERS_IDS_QUERY, "userid");
	}
	
	public ArrayList<String> getTableHeaders(String messageType, String reason,
			ArrayList<String> headerFieldNames){
		ArrayList<String> headers = new ArrayList<>();
		String procedure = getProcedureCallString("findata.getgroupsheaderformtqueue", 3);
		try {
			connection.setAutoCommit(false);
			CallableStatement statement = connection.prepareCall(procedure);
			statement.setString(1, messageType);
			statement.setString(2, reason);
			if (driver.contains("oracle")){
				statement.registerOutParameter(3, OracleTypes.CURSOR);
			}else{
				statement.registerOutParameter(3, Types.OTHER);
			}
			statement.execute();
			ResultSet resultSet = (ResultSet) statement.getObject(3);
			while(resultSet.next()){
				headers.add(resultSet.getString(1));
				if(headerFieldNames != null)
					headerFieldNames.add(resultSet.getString(2));
				
			}
			connection.setAutoCommit(true);
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return headers;
	}
	
	public ArrayList<MessagesGroup> getGroups(String queue, String messageType){
		ArrayList<MessagesGroup> groups = new ArrayList<>();
		String procedure = getProcedureCallString("findata.getgroupsformtqueue", 3);
		try {
			connection.setAutoCommit(false);
			CallableStatement statement = connection.prepareCall(procedure);
			statement.setString(1, queue);
			statement.setString(2, messageType);
			if (driver.contains("oracle")){
				statement.registerOutParameter(3, OracleTypes.CURSOR);
			}else{
				statement.registerOutParameter(3, Types.OTHER);
			}
			statement.execute();
			ResultSet resultSet = (ResultSet) statement.getObject(3);
		
			while(resultSet.next()){
				groups.add( new MessagesGroup(resultSet));
			}
			connection.setAutoCommit(true);
			
		}catch (Exception e){
			e.printStackTrace();
		}
		return groups;
	}
	
	private ArrayList<String> getDistinctTypes(String query, String columnName) throws SQLException {
		ArrayList<String> result = new ArrayList<>();
		ResultSet resultSet = connection.createStatement().executeQuery(query);
		while (resultSet.next()) {
			result.add(resultSet.getString(columnName));
		}
		return result;
	}
	
	private String getProcedureCallString(String procedureName, int nbArguments){
		String questionMarks="";
		for(int i = 0;i < nbArguments-1; i++){
			questionMarks+="?,";
		}
		questionMarks+="?";
		return "{call " + procedureName + "("+ questionMarks + ")}";
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
	public ArrayList<String> getServices() {
		return services;
	}
	public ArrayList<String> getDirections() {
		return directions;
	}

	
}
