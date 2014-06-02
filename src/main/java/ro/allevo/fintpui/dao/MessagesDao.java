package ro.allevo.fintpui.dao;

import java.util.ArrayList;
import java.util.Map;

import ro.allevo.fintpui.model.MessageDD;
import ro.allevo.fintpui.model.MessageDI;
import ro.allevo.fintpui.model.MessageFT;
import ro.allevo.fintpui.model.MessageInReports;

public interface MessagesDao {

	public MessageFT getFundsTransferMessage(String id);
	public MessageDI getDebitInstrumentsMessage(String id);
	public MessageDD getDirectDebitMessage(String id);
	
	
	public String getPayload(String correlid);
	
	public String getImage(String correlid);

	public ArrayList<MessageInReports> getMeseagesInReport(Map<String, String> requestParameters,
			StringBuilder total);
}
