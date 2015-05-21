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

package ro.allevo.fintpui.dao;

import java.util.ArrayList;
import java.util.Map;

import ro.allevo.fintpui.model.MessageDD;
import ro.allevo.fintpui.model.MessageDI;
import ro.allevo.fintpui.model.MessageDuplicate;
import ro.allevo.fintpui.model.MessageFT;
import ro.allevo.fintpui.model.MessageInReports;
import ro.allevo.fintpui.model.MessageST;

public interface MessagesDao {

	public MessageFT getFundsTransferMessage(String id);
	public MessageDI getDebitInstrumentsMessage(String id);
	public MessageDD getDirectDebitMessage(String id);
	public MessageST getStatementMessage(String id);
	
	public String getPayload(String correlid);
	
	public String getImage(String correlid);

	public ArrayList<MessageInReports> getMeseagesInReport(Map<String, String> requestParameters,
			StringBuilder total);
	public ArrayList<MessageDuplicate> getDuplicatesMessageDetails(
			Map<String, String> requestParameters);

}
