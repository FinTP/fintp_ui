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
