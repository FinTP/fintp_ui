package ro.allevo.fintpui.dao;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import ro.allevo.fintpui.model.Queue;
import ro.allevo.fintpui.model.Queues;
import ro.allevo.fintpui.utils.RestClient;
import ro.allevo.fintpui.utils.servlets.ServletsHelper;



public class QueueDaoImplementation implements QueueDao{

	@Autowired
	ServletsHelper servletsHelper;
	
	
	
	@Override
	public Queue getQueue(String queue) {
		RestTemplate client = new RestClient();
		String url = servletsHelper.getUrl() + "/queues/"+queue;
		return client.getForObject(url, Queue.class);
	}

	@Override
	public Queue[] getQueueList() {
		RestTemplate client = new RestClient();
		String url = servletsHelper.getUrl() + "/queues";
		Queues queuesJSON = client.getForObject(url, Queues.class);
		return queuesJSON.getQueues();
	}

	@Override
	public void insertQueue(Queue queue) {
		URI uri = UriBuilder.fromUri(servletsHelper.getUrl()).path("queues").build();
		servletsHelper.postAPIResource(uri, queue);
	}

	
	@Override
	public void updateQueue(String queueName, Queue queue) {
		URI uri = UriBuilder.fromUri(servletsHelper.getUrl()).path("queues").path(queueName).build();
		servletsHelper.putAPIResource(uri, queueName, queue);
	}

	@Override
	public void deleteQueue(String queueName) {
		URI uri = UriBuilder.fromUri(servletsHelper.getUrl()).path("queues").path(queueName).build();
		servletsHelper.deleteAPIResource(uri);
	}

	@Override
	public JSONObject getQueueListAsJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
