package ro.allevo.fintpui.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.springframework.beans.factory.annotation.Autowired;

import ro.allevo.fintpui.model.MessageDD;
import ro.allevo.fintpui.model.MessageDI;
import ro.allevo.fintpui.model.MessageFT;
import ro.allevo.fintpui.model.MessageInReports;

import ro.allevo.fintpui.utils.JdbcClient;

public class MessagesJdbcDao implements MessagesDao {

	@Autowired JdbcClient jdbcClient;
	
	private static final String fundsTransferQuery = "select  insertdate,msgtype,sender,receiver,trn,valuedate,amount,currency,"
			+ "	dbtaccount,dbtcustname,ordbank,benbank,cdtaccount,cdtcustname,service,"
			+ " direction, correlid, case when errcode is null then state"
			+ " else state||' ['||errcode||']'"
			+ " end state,batchid,userid "
			+ " from findata.repstatft ";
	private static final String debitInstrumentsQuery = "select  insertdate,msgtype,sender,receiver,trn,issdate,matdate,amount,currency,"
			+ "	dbtaccount,dbtcustname,dbtid,cdtaccount,cdtcustname,"
			+ " direction, correlid, case when errcode is null then state"
			+ " else state||' ['||errcode||']'"
			+ " end state,batchid,userid "
			+ " from findata.repstatdi ";
	private static final String debitDirectQuery = "select insertdate,msgtype,sender,receiver,trn,valuedate,amount,currency,"
			+ " dbtaccount,dbtcustname,cdtaccount,cdtcustname,cdtid,"
			+ " direction, correlid, case when errcode is null then state"
			+ " else state||' ['||errcode||']'"
			+ " end state,batchid,userid "
			+ " from findata.repstatdd ";
	
	@Override
	public String getPayload(String correlId){
		String query = "select payload " + "from feedbackagg "
				+ "where correlid = '" + correlId + "'";
		Statement statement;
		try {
			statement = jdbcClient.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			if (!resultSet.next()) {
				return null;
			} else {
				return resultSet.getString("payload");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	
	}

	@Override
	public String getImage(String correlid) {

		String query = "select encode(payload,'escape') " + "from blobsqueue "
				+ "where correlationid = '" + correlid + "'";
		// Statement statement;
		try {
			PreparedStatement pstmt = jdbcClient.getConnection().prepareStatement(query);
			jdbcClient.getConnection().setAutoCommit(true);
			ResultSet resultSet = pstmt.executeQuery();
			
			if (!resultSet.next()) {
				return null;
			} else {
				return resultSet.getString("encode");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ArrayList<MessageInReports> getMeseagesInReport(Map<String, String> requestParameters,
			StringBuilder total) {
		
			SimpleDateFormat html5Format = new SimpleDateFormat(
					"yyyy-MM-dd'T'hh:mm");
			SimpleDateFormat dbFormat = new SimpleDateFormat("dd MM yyyy hh:mm:ss");
			SimpleDateFormat dbFormatDateOnly = new SimpleDateFormat("dd MM yyyy");
			SimpleDateFormat DateFormat = new SimpleDateFormat("MM/dd/yyyy");
			// TODO: change valueDateFormat or html5Format to pe the same
			SimpleDateFormat valueDateFormat = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat valueDateDBFormat = new SimpleDateFormat("yyMMdd");
			String minDate = null, maxDate = null;
			Connection connection = jdbcClient.getConnection();


			ArrayList<MessageInReports> reportInstances = new ArrayList<>();

			try {

				if (requestParameters.get("interval").equals("current")) {
					// if current date is selected, pass as arguments the beggining and
					// ending of current day
					minDate = dbFormatDateOnly.format(new Date()) + " "+ requestParameters.get("startTime");
					maxDate = dbFormatDateOnly.format(new Date()) + " "+ requestParameters.get("endTime");
				} else {
					minDate = dbFormatDateOnly.format(DateFormat
							.parse(requestParameters.get("startDate")))
							+ " "
							+ requestParameters.get("startTime");
					maxDate = dbFormatDateOnly.format(DateFormat
							.parse(requestParameters.get("endDate")))
							+ " "
							+ requestParameters.get("endTime");
				}
				
				if (requestParameters.get("businessArea").equals("Funds Transfer")) {

					String procedure = getProcedureCallString(
							"findata.getftpayments", 26);
					connection.setAutoCommit(false);
					CallableStatement statement = connection.prepareCall(procedure);
					statement.setString(1, minDate);
					statement.setString(2, maxDate);
					if (!requestParameters.get("messageTypesFT").equals("")) {
						statement.setString(3,
								requestParameters.get("messageTypesFT"));
					} else {
						statement.setNull(3, Types.VARCHAR);
					}
					if (!requestParameters.get("sender").equals("")) {
						statement.setString(4, requestParameters.get("sender"));
					} else {
						statement.setNull(4, Types.VARCHAR);
					}
					if (!requestParameters.get("receiver").equals("")) {
						statement.setString(5, requestParameters.get("receiver"));
					} else {
						statement.setNull(5, Types.VARCHAR);
					}
					if (!requestParameters.get("trn").equals("")) {
						statement.setString(6, requestParameters.get("trn"));
					} else {
						statement.setNull(6, Types.VARCHAR);
					}
					if (!requestParameters.get("valueDate").equals("")) {
						String valueDate = valueDateDBFormat.format(valueDateFormat
								.parse(requestParameters.get("valueDate")));
						statement.setString(7, valueDate);
					} else {
						statement.setNull(7, Types.VARCHAR);
					}
					if (!requestParameters.get("minAmount").equals("")) {
						statement.setBigDecimal(8,
								new BigDecimal(requestParameters.get("minAmount")));
					} else {
						statement.setNull(8, Types.NUMERIC);
					}
					if (!requestParameters.get("maxAmount").equals("")) {
						statement.setBigDecimal(9,
								new BigDecimal(requestParameters.get("maxAmount")));
					} else {
						statement.setNull(9, Types.NUMERIC);
					}
					if (!requestParameters.get("currency").equals("")) {
						statement.setString(10, requestParameters.get("currency"));
					} else {
						statement.setNull(10, Types.VARCHAR);
					}
					if (!requestParameters.get("dbtaccount").equals("")) {
						statement
								.setString(11, requestParameters.get("dbtaccount"));
					} else {
						statement.setNull(11, Types.VARCHAR);
					}
					if (!requestParameters.get("dbtcustname").equals("")) {
						statement.setString(12,
								requestParameters.get("dbtcustname"));
					} else {
						statement.setNull(12, Types.VARCHAR);
					}
					if (!requestParameters.get("ordbank").equals("")) {
						statement.setString(13, requestParameters.get("ordbank"));
					} else {
						statement.setNull(13, Types.VARCHAR);
					}
					if (!requestParameters.get("benbank").equals("")) {
						statement.setString(14, requestParameters.get("benbank"));
					} else {
						statement.setNull(14, Types.VARCHAR);
					}
					if (!requestParameters.get("cdtaccount").equals("")) {
						statement
								.setString(15, requestParameters.get("cdtaccount"));
					} else {
						statement.setNull(15, Types.VARCHAR);
					}
					if (!requestParameters.get("cdtcustname").equals("")) {
						statement.setString(16,
								requestParameters.get("cdtcustname"));
					} else {
						statement.setNull(16, Types.VARCHAR);
					}
					if (!requestParameters.get("service").equals("")) {
						statement.setString(17, requestParameters.get("service"));
					} else {
						statement.setNull(17, Types.VARCHAR);
					}
					if (!requestParameters.get("direction").equals("")) {
						statement.setString(18, requestParameters.get("direction"));
					} else {
						statement.setNull(18, Types.VARCHAR);
					}
					if (!requestParameters.get("state").equals("")) {
						statement.setString(19, requestParameters.get("state"));
					} else {
						statement.setNull(19, Types.VARCHAR);
					}
					if (!requestParameters.get("batchID").equals("")) {
						statement.setString(20, requestParameters.get("batchID"));
					} else {
						statement.setNull(20, Types.VARCHAR);
					}

					if (!requestParameters.get("userid").equals("")) {
						statement.setInt(21, Integer.parseInt(requestParameters.get("userid")));
					} else {
						statement.setNull(21, Types.INTEGER);
					}

					if (requestParameters.containsKey("orderField")
							&& requestParameters.get("orderField") != null) {
						statement
								.setString(22, requestParameters.get("orderField"));
					} else {
						statement.setNull(22, Types.VARCHAR);
					}
					if (requestParameters.containsKey("order")
							&& requestParameters.get("order") != null) {
						statement.setString(23, requestParameters.get("order"));
					} else {
						statement.setNull(23, Types.VARCHAR);
					}
					if (requestParameters.get("offset") != null) {
						statement.setInt(24,
								Integer.parseInt(requestParameters.get("offset")));
					} else {
						statement.setNull(24, Types.INTEGER);
					}
					if (requestParameters.get("limit") != null) {
						statement.setInt(25,
								Integer.parseInt(requestParameters.get("limit")));
					} else {
						statement.setNull(25, Types.INTEGER);
					}
					if (jdbcClient.getDriver().contains("oracle")) {
						statement.registerOutParameter(26, OracleTypes.CURSOR);
					} else {
						statement.registerOutParameter(26, Types.OTHER);
					}
					statement.execute();
					ResultSet resultSet = (ResultSet) statement.getObject(26);
					boolean gotTotal = false;
					while (resultSet.next()) {
						reportInstances.add(new MessageFT(resultSet));
						if (!gotTotal) {
							total.append(resultSet.getInt("rnummax"));
							gotTotal = true;
						}
					}
					connection.setAutoCommit(true);

				}

				if (requestParameters.get("businessArea").equals(
						"Debit Instruments")) {

					String procedure = getProcedureCallString(
							"findata.getdipayments", 25);
					connection.setAutoCommit(false);
					CallableStatement statement = connection.prepareCall(procedure);
					statement.setString(1, minDate);
					statement.setString(2, maxDate);
					if (!requestParameters.get("messageTypesDI").equals("")) {
						statement.setString(3,
								requestParameters.get("messageTypesDI"));
					} else {
						statement.setNull(3, Types.VARCHAR);
					}
					if (!requestParameters.get("sender").equals("")) {
						statement.setString(4, requestParameters.get("sender"));
					} else {
						statement.setNull(4, Types.VARCHAR);
					}
					if (!requestParameters.get("receiver").equals("")) {
						statement.setString(5, requestParameters.get("receiver"));
					} else {
						statement.setNull(5, Types.VARCHAR);
					}
					if (!requestParameters.get("trn").equals("")) {
						statement.setString(6, requestParameters.get("trn"));
					} else {
						statement.setNull(6, Types.VARCHAR);
					}
					if (!requestParameters.get("issdate").equals("")) {
						String issdate = valueDateDBFormat
								.format(valueDateFormat.parse(requestParameters
										.get("issdate")));
						statement.setString(7, issdate);
					} else {
						statement.setNull(7, Types.VARCHAR);
					}

					if (!requestParameters.get("matdate").equals("")) {
						String maturityDate = valueDateDBFormat
								.format(valueDateFormat.parse(requestParameters
										.get("matdate")));
						statement.setString(8, maturityDate);
					} else {
						statement.setNull(8, Types.VARCHAR);
					}

					if (!requestParameters.get("minAmount").equals("")) {
						statement.setBigDecimal(9,
								new BigDecimal(requestParameters.get("minAmount")));
					} else {
						statement.setNull(9, Types.NUMERIC);
					}
					if (!requestParameters.get("maxAmount").equals("")) {
						statement.setBigDecimal(10, new BigDecimal(
								requestParameters.get("maxAmount")));
					} else {
						statement.setNull(10, Types.NUMERIC);
					}
					if (!requestParameters.get("currency").equals("")) {
						statement.setString(11, requestParameters.get("currency"));
					} else {
						statement.setNull(11, Types.VARCHAR);
					}
					if (!requestParameters.get("dbtaccount").equals("")) {
						statement
								.setString(12, requestParameters.get("dbtaccount"));
					} else {
						statement.setNull(12, Types.VARCHAR);
					}
					if (!requestParameters.get("dbtcustname").equals("")) {
						statement.setString(13,
								requestParameters.get("dbtcustname"));
					} else {
						statement.setNull(13, Types.VARCHAR);
					}

					if (!requestParameters.get("dbtid").equals("")) {
						statement.setString(14, requestParameters.get("dbtid"));
					} else {
						statement.setNull(14, Types.VARCHAR);
					}

					if (!requestParameters.get("cdtaccount").equals("")) {
						statement
								.setString(15, requestParameters.get("cdtaccount"));
					} else {
						statement.setNull(15, Types.VARCHAR);
					}
					if (!requestParameters.get("cdtcustname").equals("")) {
						statement.setString(16,
								requestParameters.get("cdtcustname"));
					} else {
						statement.setNull(16, Types.VARCHAR);
					}
					if (!requestParameters.get("direction").equals("")) {
						statement.setString(17, requestParameters.get("direction"));
					} else {
						statement.setNull(17, Types.VARCHAR);
					}
					if (!requestParameters.get("state").equals("")) {
						statement.setString(18, requestParameters.get("state"));
					} else {
						statement.setNull(18, Types.VARCHAR);
					}
					if (!requestParameters.get("batchID").equals("")) {
						statement.setString(19, requestParameters.get("batchID"));
					} else {
						statement.setNull(19, Types.VARCHAR);
					}

					if (!requestParameters.get("userid").equals("")) {
						statement.setInt(20, Integer.parseInt(requestParameters.get("userid")));
					} else {
						statement.setNull(20, Types.INTEGER);
					}

					if (requestParameters.containsKey("orderField")
							&& requestParameters.get("orderField") != null) {
						statement
								.setString(21, requestParameters.get("orderField"));
					} else {
						statement.setNull(21, Types.VARCHAR);
					}
					if (requestParameters.containsKey("order")
							&& requestParameters.get("order") != null) {
						statement.setString(22, requestParameters.get("order"));
					} else {
						statement.setNull(22, Types.VARCHAR);
					}
					if (requestParameters.get("offset") != null) {
						statement.setInt(23,
								Integer.parseInt(requestParameters.get("offset")));
					} else {
						statement.setNull(23, Types.INTEGER);
					}
					if (requestParameters.get("limit") != null) {
						statement.setInt(24,
								Integer.parseInt(requestParameters.get("limit")));
					} else {
						statement.setNull(24, Types.INTEGER);
					}
					if (jdbcClient.getDriver().contains("oracle")) {
						statement.registerOutParameter(25, OracleTypes.CURSOR);
					} else {
						statement.registerOutParameter(25, Types.OTHER);
					}
					statement.execute();
					ResultSet resultSet = (ResultSet) statement.getObject(25);
					boolean gotTotal = false;
					while (resultSet.next()) {
						reportInstances.add(new MessageDI(resultSet));
						if (!gotTotal) {
							total.append(resultSet.getInt("rnummax"));
							gotTotal = true;
						}

					}
					connection.setAutoCommit(true);
				}
				if (requestParameters.get("businessArea").equals("Direct Debit")) {

					String procedure = getProcedureCallString(
							"findata.getddpayments", 24);
					connection.setAutoCommit(false);
					CallableStatement statement = connection.prepareCall(procedure);
					statement.setString(1, minDate);
					statement.setString(2, maxDate);
					if (!requestParameters.get("messageTypesDD").equals("")) {
						statement.setString(3,
								requestParameters.get("messageTypesDD"));
					} else {
						statement.setNull(3, Types.VARCHAR);
					}
					if (!requestParameters.get("sender").equals("")) {
						statement.setString(4, requestParameters.get("sender"));
					} else {
						statement.setNull(4, Types.VARCHAR);
					}
					if (!requestParameters.get("receiver").equals("")) {
						statement.setString(5, requestParameters.get("receiver"));
					} else {
						statement.setNull(5, Types.VARCHAR);
					}
					if (!requestParameters.get("trn").equals("")) {
						statement.setString(6, requestParameters.get("trn"));
					} else {
						statement.setNull(6, Types.VARCHAR);
					}
					if (!requestParameters.get("valueDate").equals("")) {
						String valueDate = valueDateDBFormat.format(valueDateFormat
								.parse(requestParameters.get("valueDate")));
						statement.setString(7, valueDate);
					} else {
						statement.setNull(7, Types.VARCHAR);
					}
					if (!requestParameters.get("minAmount").equals("")) {
						statement.setBigDecimal(8,
								new BigDecimal(requestParameters.get("minAmount")));
					} else {
						statement.setNull(8, Types.NUMERIC);
					}
					if (!requestParameters.get("maxAmount").equals("")) {
						statement.setBigDecimal(9,
								new BigDecimal(requestParameters.get("maxAmount")));
					} else {
						statement.setNull(9, Types.NUMERIC);
					}
					if (!requestParameters.get("currency").equals("")) {
						statement.setString(10, requestParameters.get("currency"));
					} else {
						statement.setNull(10, Types.VARCHAR);
					}
					if (!requestParameters.get("dbtaccount").equals("")) {
						statement
								.setString(11, requestParameters.get("dbtaccount"));
					} else {
						statement.setNull(11, Types.VARCHAR);
					}
					if (!requestParameters.get("dbtcustname").equals("")) {
						statement.setString(12,
								requestParameters.get("dbtcustname"));
					} else {
						statement.setNull(12, Types.VARCHAR);
					}
					if (!requestParameters.get("cdtid").equals("")) {
						statement.setString(13, requestParameters.get("cdtid"));
					} else {
						statement.setNull(13, Types.VARCHAR);
					}
					
					if (!requestParameters.get("cdtaccount").equals("")) {
						statement
								.setString(14, requestParameters.get("cdtaccount"));
					} else {
						statement.setNull(14, Types.VARCHAR);
					}
					if (!requestParameters.get("cdtcustname").equals("")) {
						statement.setString(15,
								requestParameters.get("cdtcustname"));
					} else {
						statement.setNull(15, Types.VARCHAR);
					}
					
					if (!requestParameters.get("direction").equals("")) {
						statement.setString(16, requestParameters.get("direction"));
					} else {
						statement.setNull(16, Types.VARCHAR);
					}
					if (!requestParameters.get("state").equals("")) {
						statement.setString(17, requestParameters.get("state"));
					} else {
						statement.setNull(17, Types.VARCHAR);
					}
					if (!requestParameters.get("batchID").equals("")) {
						statement.setString(18, requestParameters.get("batchID"));
					} else {
						statement.setNull(18, Types.VARCHAR);
					}

					if (!requestParameters.get("userid").equals("")) {
						statement.setInt(19, Integer.parseInt(requestParameters.get("userid")));
					} else {
						statement.setNull(19, Types.INTEGER);
					}

					if (requestParameters.containsKey("orderField")
							&& requestParameters.get("orderField") != null) {
						statement
								.setString(20, requestParameters.get("orderField"));
					} else {
						statement.setNull(20, Types.VARCHAR);
					}
					if (requestParameters.containsKey("order")
							&& requestParameters.get("order") != null) {
						statement.setString(21, requestParameters.get("order"));
					} else {
						statement.setNull(21, Types.VARCHAR);
					}
					if (requestParameters.get("offset") != null) {
						statement.setInt(22,
								Integer.parseInt(requestParameters.get("offset")));
					} else {
						statement.setNull(22, Types.INTEGER);
					}
					if (requestParameters.get("limit") != null) {
						statement.setInt(23,
								Integer.parseInt(requestParameters.get("limit")));
					} else {
						statement.setNull(23, Types.INTEGER);
					}
					if (jdbcClient.getDriver().contains("oracle")) {
						statement.registerOutParameter(24, OracleTypes.CURSOR);
					} else {
						statement.registerOutParameter(24, Types.OTHER);
					}
					//System.out.println(statement);
					statement.execute();
					
					ResultSet resultSet = (ResultSet) statement.getObject(24);
					boolean gotTotal = false;
					while (resultSet.next()) {
						reportInstances.add(new MessageDD(resultSet));
						if (!gotTotal) {
							total.append(resultSet.getInt("rnummax"));
							gotTotal = true;
						}
					}
					connection.setAutoCommit(true);

				}
			} catch (SQLException e) {
				e.printStackTrace();

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return reportInstances;
		
	}
	
	private String getProcedureCallString(String procedureName, int nbArguments) {
		String questionMarks = "";
		for (int i = 0; i < nbArguments - 1; i++) {
			questionMarks += "?,";
		}
		questionMarks += "?";
		return "{call " + procedureName + "(" + questionMarks + ")}";
	}

	@Override
	public MessageFT getFundsTransferMessage(String id) {
		String whereClause = " where correlid = '" + id +"'";
		try {
			
			PreparedStatement pstmt = jdbcClient.getConnection().prepareStatement(fundsTransferQuery + whereClause);
			ResultSet resultSet = pstmt.executeQuery();
			if(resultSet.next()){
				MessageFT message = new MessageFT(resultSet);
				return message;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public MessageDI getDebitInstrumentsMessage(String id) {
		String whereClause = " where correlid = '" + id +"'";
		try {
			PreparedStatement pstmt = jdbcClient.getConnection().prepareStatement(debitInstrumentsQuery + whereClause);
			ResultSet resultSet = pstmt.executeQuery();
			if(resultSet.next()){
				MessageDI message = new MessageDI(resultSet);
				return message;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public MessageDD getDirectDebitMessage(String id) {
		String whereClause = " where correlid = '" + id +"'";
		try {
			
			PreparedStatement pstmt = jdbcClient.getConnection().prepareStatement(debitDirectQuery + whereClause);
			ResultSet resultSet = pstmt.executeQuery();
			//System.out.println(debitDirectQuery + whereClause);
			if(resultSet.next()){
				MessageDD message = new MessageDD(resultSet);
				return message;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
