package ro.allevo.fintpui.dao;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import ro.allevo.fintpui.model.MessageType;
import ro.allevo.fintpui.model.MessageTypes;
import ro.allevo.fintpui.model.Queue;
import ro.allevo.fintpui.utils.RestClient;
import ro.allevo.fintpui.utils.servlets.ServletsHelper;


public class MessageTypesRestApiDao implements MessageTypesDao {
	@Autowired
	ServletsHelper servletsHelper;
	
	@Override
	public ArrayList<MessageType> getMessageTypesInQueue(String queue) {
		RestTemplate client = new RestClient();
		String url = servletsHelper.getUrl() + "/queues/"+queue +"/messagetypes";
		MessageType messageTypes[] = client.getForObject(url, MessageTypes.class).getMessagetypes();
		return new ArrayList<MessageType>(Arrays.asList(messageTypes));
	}
}
