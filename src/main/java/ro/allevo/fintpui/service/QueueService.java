package ro.allevo.fintpui.service;

import java.util.ArrayList;

import org.codehaus.jettison.json.JSONObject;

import ro.allevo.fintpui.model.Queue;

public interface QueueService {

	/**
	 * Inserts a new queue 
	 * @param Queue queue
	 */
	public void insertQueue(Queue queue);
	/**
	 * Returns a list of all queues
	 * @return Queue[] 
	 */
	public Queue[] getQueueList();
	/**
	 * Return list of names of queues
	 * @return
	 */
	public ArrayList<String> getQueuesNames();
	/**
	 * Returns a JSON containing queues as the FintpApi returns them
	 * @return JSONObject
	 */
	public JSONObject getQueueListAsJSON();
	/**
	 * Deletes the queue with the specified name
	 * @param String queueName
	 */
	public void deleteQueue(String queueName);
	/**
	 * Returns queue with the given name 
	 * @param queueName
	 * @return Queue
	 */
	public Queue getQueue(String queueName);
	/**
	 * Modifies queue with specified name with new details
	 * @param queueName
	 * @param queue
	 */
	public void updateQueue(String queueName, Queue queue);
	/**
	 * Gets the number of messages in queue
	 * Returns -1 if the user performing the request doesn't have enough roles
	 * @param queueName
	 * @return
	 */
	public int getNumberOfMessagesInQueue(String queueName);
	/**
	 * Reutrns the distinct types of queues in the application
	 * @return 
	 */
	public ArrayList<String> getQueueTypes();
	/**
	 * Reutrns the distinct types of messages in the specified queue
	 */
	public ArrayList<String> getMessageTypesInQueue(String queueName);
	public ArrayList<Boolean> getIsParrentMessageInQueue(String queueName);
	ArrayList<String> getChildMessageTypes(String queueName);
	
	
}
