/*
* FinTP - Financial Transactions Processing Application
* Copyright (C) 2013 Business Information Systems (Allevo) S.R.L.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>
* or contact Allevo at : 031281 Bucuresti, 23C Calea Vitan, Romania,
* phone +40212554577, office@allevo.ro <mailto:office@allevo.ro>, www.allevo.ro.
*/

package ro.allevo.fintpui.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;
import ro.allevo.fintpui.model.MessagesGroup;

public class JdbcClient {

	private static final String FT_MESSAGES_QUERY = "select distinct friendlyname from fincfg.msgtypes where businessarea = 'Funds Transfer'";
	private static final String DI_MESSAGES_QUERY = "select distinct friendlyname from fincfg.msgtypes where businessarea = 'Debit Instruments'";
	private static final String DD_MESSAGES_QUERY = "select distinct friendlyname from fincfg.msgtypes where businessarea = 'Direct Debit'";
	private static final String ST_MESSAGES_QUERY = "select distinct friendlyname from fincfg.msgtypes where businessarea = 'Statements'";

	private static final String BIC_CODES_QUERY = "select bic from fincfg.biccodes";
	private static final String CURRENCIES_QUERY = "select currency from fincfg.currencies";
	private static final String STATES_QUERY = "select status from fincfg.reportingtxstates";
	private static final String USERS_NAMES_QUERY = "select username from fincfg.users";
	private static final String USERS_IDS_QUERY = "select userid from fincfg.users";

	private final ArrayList<String> services = new ArrayList<>(Arrays.asList(
			"ROL", "TGT", "N/A"));
	private final ArrayList<String> directions = new ArrayList<>(Arrays.asList(
			"Incoming", "Outgoing"));

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

	public Connection getConnection() {
		try {
			Class.forName(driver);
			if (connection == null || connection.isClosed()) {
				Context context = (Context) new InitialContext()
						.lookup("java:comp/env");
				DataSource dataSource = (DataSource) context
						.lookup("jdbc/toFintpConfig");
				connection = dataSource.getConnection();
			}
			return connection;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public ArrayList<String> getFTMessageTypes() throws SQLException {
		return getDistinctTypes(FT_MESSAGES_QUERY, "friendlyname");
	}

	public ArrayList<String> getDIMessageTypes() throws SQLException {
		return getDistinctTypes(DI_MESSAGES_QUERY, "friendlyname");
	}
	
	public ArrayList<String> getDDMessageTypes() throws SQLException {
		return getDistinctTypes(DD_MESSAGES_QUERY, "friendlyname");
	}
	
	public ArrayList<String> getSTMessageTypes() throws SQLException {
		return getDistinctTypes(ST_MESSAGES_QUERY, "friendlyname");
	}


	public ArrayList<String> getBicCodes() throws SQLException {
		return getDistinctTypes(BIC_CODES_QUERY, "bic");
	}

	public ArrayList<String> getCurrencies() throws SQLException {
		return getDistinctTypes(CURRENCIES_QUERY, "currency");
	}

	public ArrayList<String> getStates() throws SQLException {
		return getDistinctTypes(STATES_QUERY, "status");
	}

	public ArrayList<String> getUserNames() throws SQLException {
		return getDistinctTypes(USERS_NAMES_QUERY, "username");
	}

	public ArrayList<String> getUserIds() throws SQLException {
		return getDistinctTypes(USERS_IDS_QUERY, "userid");
	}

	public ArrayList<String> getTableHeaders(String messageType, String reason,
			ArrayList<String> headerFieldNames) {
		ArrayList<String> headers = new ArrayList<>();
		String procedure = getProcedureCallString(
				"findata.getgroupsheaderformtqueue", 3);
		try {
			connection.setAutoCommit(false);
			CallableStatement statement = connection.prepareCall(procedure);
			statement.setString(1, messageType);
			statement.setString(2, reason);
			if (driver.contains("oracle")) {
				statement.registerOutParameter(3, OracleTypes.CURSOR);
			} else {
				statement.registerOutParameter(3, Types.OTHER);
			}
			statement.execute();
			ResultSet resultSet = (ResultSet) statement.getObject(3);
			while (resultSet.next()) {
				headers.add(resultSet.getString(1));
				if (headerFieldNames != null)
					headerFieldNames.add(resultSet.getString(2));

			}
			connection.setAutoCommit(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return headers;
	}

	public ArrayList<MessagesGroup> getGroups(String queue, String messageType, int amountSearchValue, String searchValue) {
		ArrayList<MessagesGroup> groups = new ArrayList<>();
		String procedure = getProcedureCallString(
				"findata.getgroupsformtqueue", 5);
		try {
			connection.setAutoCommit(false);
			CallableStatement statement = connection.prepareCall(procedure);
			statement.setString(1, queue);
			statement.setString(2, messageType);
			statement.setInt(3, amountSearchValue);
			statement.setString(4, searchValue);
			if (driver.contains("oracle")) {
				statement.registerOutParameter(5, OracleTypes.CURSOR);
			} else {
				statement.registerOutParameter(5, Types.OTHER);
			}
			statement.execute();
			System.out.println(statement);
			ResultSet resultSet = (ResultSet) statement.getObject(5);

			while (resultSet.next()) {
				groups.add(new MessagesGroup(resultSet));
			}
			connection.setAutoCommit(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return groups;
	}

	private ArrayList<String> getDistinctTypes(String query, String columnName)
			throws SQLException {
		ArrayList<String> result = new ArrayList<>();
		ResultSet resultSet = connection.createStatement().executeQuery(query);
		while (resultSet.next()) {
			result.add(resultSet.getString(columnName));
		}
		return result;
	}

	private String getProcedureCallString(String procedureName, int nbArguments) {
		String questionMarks = "";
		for (int i = 0; i < nbArguments - 1; i++) {
			questionMarks += "?,";
		}
		questionMarks += "?";
		return "{call " + procedureName + "(" + questionMarks + ")}";
	}

	public ArrayList<String> getServices() {
		return services;
	}

	public ArrayList<String> getDirections() {
		return directions;
	}

}
