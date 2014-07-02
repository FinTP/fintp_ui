package ro.allevo.fintpui.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;




import javax.ws.rs.core.UriBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import org.springframework.web.servlet.ModelAndView;

import com.sun.jersey.api.client.ClientResponse;

import ro.allevo.fintpui.exception.NotAuthorizedException;
import ro.allevo.fintpui.model.MessagesGroup;
import ro.allevo.fintpui.model.Queue;
import ro.allevo.fintpui.service.QueueService;
import ro.allevo.fintpui.service.ServiceMapService;
import ro.allevo.fintpui.utils.JdbcClient;
import ro.allevo.fintpui.utils.servlets.ServletsHelper;


@Controller
public class QueuesController {

	@Autowired
	private JdbcClient dbClient;
	
	@Autowired
	private ServletsHelper servletsHelper;
	
	@Autowired
	private QueueService queueService;
	
	@Autowired
	private ServiceMapService serviceMapService;

	private static Logger logger = LogManager.getLogger(QueuesController.class
			.getName());
	
	/*
	 * DISPLAY
	 */
	@RequestMapping(value = "/queues",method = RequestMethod.GET)
	public ModelAndView printMenu(ModelMap model){
		logger.info("/queues requested");
		Queue[] queues = queueService.getQueueList();
		model.addAttribute("queues", queues);
		model.addAttribute("apiUri", servletsHelper.getUrl());
		return new ModelAndView("tiles/queues", model);
	}
	
	/*
	 * INSERT
	 */
	@RequestMapping(value = "/addQueue", method = RequestMethod.GET) 
	public String addQueue(ModelMap model, @ModelAttribute Queue queue){
		logger.info("/addQueue page requested");
		model.addAttribute("types", queueService.getQueueTypes());
		model.addAttribute("connectors",
				serviceMapService.getServiceMapNamesList());
		return "tiles/queues_add";
	}
	
	@RequestMapping(value = "/queues/insert", method = RequestMethod.POST) 
	public String insertQueue(@ModelAttribute("queue") Queue queue){
		logger.info("/insert queue requested");
		queueService.insertQueue(queue);
		return "redirect:/queues.htm";
	}
	
	/*
	 * EDIT
	 */
	@RequestMapping(value = "/editQueue", method = RequestMethod.GET)
	public String editQueue(ModelMap model, @RequestParam(value="queue", required=true) String queueName){
		logger.info("/editQueue requested");
		Queue queue = queueService.getQueue(queueName);
		model.addAttribute("queue", queue);
		model.addAttribute("types", queueService.getQueueTypes());
		model.addAttribute("connectors",
				serviceMapService.getServiceMapNamesList());
		return "tiles/queue_edit";
	}
	
	@RequestMapping(value = "/queues/update", method = RequestMethod.POST)
	public String updateQueue(@ModelAttribute("queue") Queue queue, @RequestParam("init_name") String initialName){
		logger.info("/update queue requested");
		queueService.updateQueue(initialName, queue);
		return "redirect:/queues.htm";
	}
	
	/*
	 * DELETE
	 */
	@RequestMapping(value = "/queues/deleteQueue")
	public String deleteQueue(@RequestParam("queue") String queueName){
		logger.info("/delete queue requested");
		queueService.deleteQueue(queueName);
		return "redirect:/queues.htm";
	}
	
	
	
	@RequestMapping(value = "/queues/{queueName}", method = RequestMethod.GET) 
	public String viewQueue(@PathVariable String queueName, ModelMap model){
		logger.info("/queues/"+queueName + " requested");
		
		try {
			dbClient.getConnection();
			Queue[] queues = queueService.getQueueList();
			model.addAttribute("queues", queues);
			
			//add queue name attribute
			model.addAttribute("queueName", queueName);
			
			//add messagetypes array 
			ArrayList<String> messageTypes = queueService.getMessageTypesInQueue(queueName);
			if(messageTypes == null){
				return "tiles/forbidden";
			}
			
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
			
			
		} finally{
			dbClient.closeConnection();
		}
		return "tiles/queue";
	}

}
