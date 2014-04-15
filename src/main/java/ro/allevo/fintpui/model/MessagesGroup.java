package ro.allevo.fintpui.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class MessagesGroup {

	private BigDecimal totalAmount;
	private int count;
	private String timestamp;
	private String groupKey;
	private ArrayList<String> fields = new ArrayList<>();
	
	private static final String TOTAL_AMOUNT_COLUMN_NAME = "totamt";
	private static final String COUNT_COLUMN_NAME = "cnt";
	private static final String TIMESTAMP_COLUMN_NAME = "timekey";
	private static final String GROUPKEY_COLUMN_NAME = "groupkey";
	
	public MessagesGroup(ResultSet resultSet) {
		try {
			ResultSetMetaData metaData = resultSet.getMetaData();
			int numberOfFileds = metaData.getColumnCount() - 4;
			totalAmount = resultSet.getBigDecimal(TOTAL_AMOUNT_COLUMN_NAME);
			count = resultSet.getInt(COUNT_COLUMN_NAME);
			timestamp = resultSet.getString(TIMESTAMP_COLUMN_NAME);
			groupKey = resultSet.getString(GROUPKEY_COLUMN_NAME);
			for(int i = 1; i <= numberOfFileds ; i++){
				fields.add(resultSet.getString(i));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getGroupKey() {
		return groupKey;
	}
	public void setGroupKey(String groupKey) {
		this.groupKey = groupKey;
	}
	public ArrayList<String> getFields() {
		return fields;
	}
	public void setFields(ArrayList<String> fields) {
		this.fields = fields;
	}
	
	@Override
	public String toString(){
		String result = "Group Key " + groupKey;
		result+="\nCount : " + count;
		result+="\nTotalAmount : " + totalAmount;
		result+="\nTime : " + timestamp;
		result+="\nFields " + fields;
		return result;
	}
	
}
