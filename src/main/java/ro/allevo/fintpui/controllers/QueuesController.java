package ro.allevo.fintpui.controllers;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Multiset.Entry;

import ro.allevo.fintpui.model.MessagesGroup;
import ro.allevo.fintpui.model.Queue;
import ro.allevo.fintpui.services.FintpService;
import ro.allevo.fintpui.utils.JdbcClient;
import ro.allevo.fintpui.utils.servlets.ServletsHelper;


@Controller
@RequestMapping("/queues")
public class QueuesController {

	@Autowired
	private JdbcClient dbClient;
	
	@Autowired
	private ServletsHelper servletsHelper;

	private static Logger logger = LogManager.getLogger(QueuesController.class
			.getName());
	
	private FintpService fintpService;
	
	public void setFintpService(FintpService fintpService) {
		this.fintpService = fintpService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String printMenu(ModelMap model){
		logger.info("/queues requested");
		logger.info("api url: " + servletsHelper.getUrl());
		Queue[] queues = fintpService.getQueues();
		model.addAttribute("queues", queues);
		model.addAttribute("apiUri", servletsHelper.getUrl());
		
		return "tiles/queues";
	}
	
	@RequestMapping(value = "/{queueName}", method = RequestMethod.GET) 
	public String webletIconData(@PathVariable String queueName, ModelMap model){
		logger.info("/queues/"+queueName + " requested");
		
		try {
			dbClient.establishConnection();
			Queue[] queues = fintpService.getQueues();
			model.addAttribute("queues", queues);
			
			//add queue name attribute
			model.addAttribute("queueName", queueName);
			
			//add messagetypes array 
			ArrayList<String> messageTypes = fintpService
					.getMessageTypesInQueue(queueName);
			model.addAttribute("messageTypes", messageTypes);
			
			//build hashmap of headers (message type is the key, array containing table headers is the value) 
			//get groups for current message type
			//if there are no groups, then treat as a non-batchalbe page
			
			/*
			 * headers Map is a hash in which
			 *    key: messageType (s.a. 103)
			 *    value: array of strings which represent the table headers name (s.a. Sender, Receiver, Reference ...)
			 */
			HashMap<String, ArrayList<String>> headersMap = new HashMap<>();
			/*
			 * columns Map is a hash in which
			 *    key: messageType (s.a. 103)
			 *    value: array of strings which represent the fields contained by the json object 
			 *          returned by fintp API  (s.a. sender, receiver, trn ...)
			 *          they correspond in a 1-1 relantionship with headersMap
			 */
			HashMap<String, ArrayList<String>> columnsMap = new HashMap<>();
			
			/*
			 * gropsMap is a hash table in which
			 *    key: message type
			 *    value : list of groups belonging to that message type
			 *    
			 */
			HashMap<String, ArrayList<MessagesGroup>> groupsMap = new HashMap<>();
			
			/*
			 * gropsFieldsMap is a hash table in which
			 *    key: message type (that admits groups/batches)
			 *    value : "grouped by" fields
			 *    
			 */
			HashMap<String, ArrayList<String>> groupFieldsMap = new HashMap<>();
			
			for (int i = 0; i < messageTypes.size(); i++) {
				ArrayList<String> columns = new ArrayList<>();
				ArrayList<String> groupFields = new ArrayList<>();
				headersMap.put(messageTypes.get(i),
						dbClient.getTableHeaders(messageTypes.get(i), "T", columns));
				columnsMap.put(messageTypes.get(i), columns);
				groupsMap.put(messageTypes.get(i),
						dbClient.getGroups(queueName, messageTypes.get(i)));
				dbClient.getTableHeaders(messageTypes.get(i),"G", groupFields);
				groupFieldsMap.put(messageTypes.get(i), groupFields);
			}
			
			model.addAttribute("headers" , headersMap);
			model.addAttribute("columns", columnsMap);
			model.addAttribute("groupsMap", groupsMap);
			model.addAttribute("groupFieldNames", groupFieldsMap);
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}finally{
			dbClient.closeConnection();
		}
		return "tiles/queue";
	}
	
}
