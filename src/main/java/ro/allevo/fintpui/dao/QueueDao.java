package ro.allevo.fintpui.dao;


import org.codehaus.jettison.json.JSONObject;

import ro.allevo.fintpui.model.Queue;


public interface QueueDao {

	public Queue getQueue(String queue);
	public Queue[] getQueueList();
	public JSONObject getQueueListAsJSON();
	public void insertQueue(Queue queue);
	public void updateQueue(String queueName, Queue queue);
	public void deleteQueue(String queueName);
	
}
