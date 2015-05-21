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

package ro.allevo.fintpui.service;

import java.util.ArrayList;
import java.util.Map;




import org.springframework.beans.factory.annotation.Autowired;

import ro.allevo.fintpui.controllers.MessageController;
import ro.allevo.fintpui.dao.MessagesDao;
import ro.allevo.fintpui.model.MessageDuplicate;
import ro.allevo.fintpui.model.MessageInReports;



public class MessageService {

	@Autowired
	MessagesDao messagesDao;
	
	public String getPayload(String correlid){
		String payload = messagesDao.getPayload(correlid);
		String path = getClass().getClassLoader()
				.getResource(MessageController.NESTED_TABLES_XSLT).getPath();
		String friendlyPayload = MessageController.applyXSLT(payload, path);
		return friendlyPayload;
	}

	public String getImage(String correlid) {
		String base64TiffImage = messagesDao.getImage(correlid);
		
		return base64TiffImage;
	}
	
	public ArrayList<MessageInReports> getMessagesInReport(Map<String,String> requestParameters, StringBuilder total){
		return messagesDao.getMeseagesInReport(requestParameters, total);
	}
	
	/**
	 * Factory method that returns a report message depending on its businessArea
	 * @param id : the correlation id of the message
	 * @param businessArea
	 * @return
	 */
	public MessageInReports getMessageInReports(String id, String businessArea){
		switch (businessArea) {
		case "Funds Transfer":
			return messagesDao.getFundsTransferMessage(id);
		case "Debit Instruments":
			return messagesDao.getDebitInstrumentsMessage(id);
		case "Direct Debit":
			return messagesDao.getDirectDebitMessage(id);
		case "Statements":
			return messagesDao.getStatementMessage(id);
		default:
			throw new RuntimeException("Requested unexpected type of business area");
		}
	}

	public ArrayList<MessageDuplicate> getDuplicateMessageDetails(
			Map<String, String> allRequestParams) {
		return messagesDao.getDuplicatesMessageDetails(allRequestParams);
	}
	
}
