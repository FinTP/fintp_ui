package ro.allevo.fintpui.service;

import java.util.ArrayList;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;

import ro.allevo.fintpui.controllers.MessageController;
import ro.allevo.fintpui.dao.MessagesDao;
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
		default:
			throw new RuntimeException("Requested unexpected type of business area");
		}
	}
	
}
