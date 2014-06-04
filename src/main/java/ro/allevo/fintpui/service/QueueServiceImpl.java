package ro.allevo.fintpui.service;

import java.net.URI;
import java.util.ArrayList;

import javax.ws.rs.core.UriBuilder;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.sun.jersey.api.client.ClientResponse;

import ro.allevo.fintpui.dao.QueueDao;
import ro.allevo.fintpui.model.Queue;
import ro.allevo.fintpui.model.QueueType;
import ro.allevo.fintpui.model.QueueTypes;
import ro.allevo.fintpui.utils.RestClient;
import ro.allevo.fintpui.utils.servlets.ServletsHelper;

public class QueueServiceImpl implements QueueService{

	@Autowired
	QueueDao queueDao;
	
	@Autowired
	ServletsHelper servletsHelper;
	
	
	@Override
	public Queue getQueue(String queueName) {
		return queueDao.getQueue(queueName);
	}
	
	@Override
	public Queue[] getQueueList() {
		return queueDao.getQueueList();
	}
	
	@Override
	public JSONObject getQueueListAsJSON() {
		// TODO Auto-generated method stub
		// TODO : enable pagination (only if needed) 
		return null;
	}

	@Override
	public void insertQueue(Queue queue) {
		queueDao.insertQueue(queue);
	}
	
	
	@Override
	public void updateQueue(String queueName, Queue queue) {
		queueDao.updateQueue(queueName, queue);
	}
	
	@Override
	public void deleteQueue(String queueName) {
		queueDao.deleteQueue(queueName);
	}

	@Override
	public int getNumberOfMessagesInQueue(String queueName) {
		URI uri = UriBuilder.fromPath(servletsHelper.getUrl()).path("queues")
				.path(queueName).path("messages").queryParam("filter", "t")
				.build();
		ClientResponse response = servletsHelper.getAPIResource(uri);
		switch (response.getStatus()) {
		case 200:
			JSONObject entity = response.getEntity(JSONObject.class);
			try {
				return entity.getInt("total");
			} catch (JSONException e) {
				throw new RuntimeException("Failed : Requested total field but not provided by API");
			}
		case 403:
			return -1;
		case 404:
			return -1;
		default:
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus() + " => handle this type of response: "
					+ "at GET " + uri);
		}
		
		
	}

	@Override
	public ArrayList<String> getQueueTypes() {
		ArrayList<String> queueTypes = new ArrayList<>();
		RestTemplate client = new RestClient();
		String url = servletsHelper.getUrl() + "/queuetypes";
		QueueTypes  response = client.getForObject(url, QueueTypes.class);
		for(QueueType queueType : response.getQueuetypes()){
			queueTypes.add(queueType.getTypename());
		}
		return queueTypes;
	}

	@Override
	public ArrayList<String> getMessageTypesInQueue(String queueName) {
		ArrayList<String> messageTypes = new ArrayList<>();
		URI uri = UriBuilder.fromPath(servletsHelper.getUrl()).path("queues")
				.path(queueName).path("messagetypes").build();
		ClientResponse response = servletsHelper.getAPIResource(uri);
		JSONObject jsonResponse = response.getEntity(JSONObject.class);
		if(response.getStatus() == 403){
			return null;
		}
		JSONArray jsonArray;
		try {
			jsonArray = jsonResponse.getJSONArray("messagetypes");
			for(int i = 0; i < jsonArray.length(); i++){
				messageTypes.add(jsonArray.getString(i));
			}
			
		} catch (JSONException e) {
			throw new RuntimeException("Failed : MessageTypes resource doesn't provide messagetypes Array");
		}
		return messageTypes;
		
	}

	@Override
	public ArrayList<String> getQueuesNames() {
		ArrayList<String> names = new ArrayList<>();
		Queue[] queues = getQueueList();
		for(Queue queue : queues){
			names.add(queue.getName());
		}
		return names;
	}
}
