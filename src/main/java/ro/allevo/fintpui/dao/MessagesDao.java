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
