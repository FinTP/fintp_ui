/*
* FinTP - Financial Transactions Processing Application
* Copyright (C) 2013 Business Information Systems (Allevo) S.R.L.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>
* or contact Allevo at : 031281 Bucuresti, 23C Calea Vitan, Romania,
* phone +40212554577, office@allevo.ro <mailto:office@allevo.ro>, www.allevo.ro.
*/

package ro.allevo.fintpui.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

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
	Boolean isComposedMsgType;

	/*
	 * DISPLAY
	 */
	@RequestMapping(value = "/queues", method = RequestMethod.GET)
	public ModelAndView printMenu(ModelMap model) {
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
	public String addQueue(ModelMap model, @ModelAttribute Queue queue) {
		logger.info("/addQueue page requested");
		model.addAttribute("types", queueService.getQueueTypes());
		model.addAttribute("connectors",
				serviceMapService.getServiceMapNamesList());
		return "tiles/queues_add";
	}

	@RequestMapping(value = "/queues/insert", method = RequestMethod.POST)
	public String insertQueue(@ModelAttribute("queue") Queue queue) {
		logger.info("/insert queue requested");
		queueService.insertQueue(queue);
		return "redirect:/queues.htm";
	}

	/*
	 * EDIT
	 */
	@RequestMapping(value = "/editQueue", method = RequestMethod.GET)
	public String editQueue(ModelMap model,
			@RequestParam(value = "queue", required = true) String queueName) {
		logger.info("/editQueue requested");
		Queue queue = queueService.getQueue(queueName);
		model.addAttribute("queue", queue);
		model.addAttribute("types", queueService.getQueueTypes());
		model.addAttribute("connectors",
				serviceMapService.getServiceMapNamesList());
		return "tiles/queue_edit";
	}

	@RequestMapping(value = "/queues/update", method = RequestMethod.POST)
	public String updateQueue(@ModelAttribute("queue") Queue queue,
			@RequestParam("init_name") String initialName) {
		logger.info("/update queue requested");
		queueService.updateQueue(initialName, queue);
		return "redirect:/queues.htm";
	}

	public boolean containsOnlyNumbers(String str) {

		// It can't contain only numbers if it's null or empty...
		if (str == null || str.length() == 0)
			return false;

		for (int i = 0; i < str.length(); i++) {

			// If we find a non-digit character we return false.
			if (!Character.isDigit(str.charAt(i)))
				return false;
		}

		return true;
	}

	/*
	 * DELETE
	 */
	@RequestMapping(value = "/queues/deleteQueue")
	public String deleteQueue(@RequestParam("queue") String queueName) {
		logger.info("/delete queue requested");
		queueService.deleteQueue(queueName);
		return "redirect:/queues.htm";
	}

	@RequestMapping(value = "/queues/{queueName}", method = RequestMethod.GET)
	public String viewQueue(
			@PathVariable String queueName,
			@RequestParam(value = "isComposedMsgType", required = false) boolean isComposedMsgType,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "composedMsgId", required = false) String composedMsgId,
			@RequestParam(value = "search", required = false) String searchValue,

			ModelMap model) throws JSONException {
		logger.info("/queues/" + queueName + " requested");
		Integer amountSearchValue;
		try {

			if (isComposedMsgType == true) {
				dbClient.getConnection();
				Queue[] queues = queueService.getQueueList();
				model.addAttribute("queues", queues);

				// add queue name attribute
				model.addAttribute("queueName", queueName);
				ArrayList<String> messageTypes = new ArrayList<>();
				ArrayList<Boolean> isParent = new ArrayList<>();
				// ArrayList<String> ChildMsgType = new ArrayList<>();
				isParent.add(false);

				// add messagetypes array
				messageTypes.add(type);
				// ChildMsgType = queueService.getChildMessageTypes(queueName);

				// System.out.println(messageTypes);
				// build hashmap of headers (message type is the key, array
				// containing table headers is the value)
				// get groups for current message type
				// if there are no groups, then treat as a non-batchalbe page

				/*
				 * headers Map is a hash in which key: messageType (s.a. 103)
				 * value: array of strings which represent the table headers
				 * name (s.a. Sender, Receiver, Reference ...)
				 */
				HashMap<String, ArrayList<String>> headersMap = new HashMap<>();
				/*
				 * columns Map is a hash in which key: messageType (s.a. 103)
				 * value: array of strings which represent the fields contained
				 * by the json object returned by fintp API (s.a. sender,
				 * receiver, trn ...) they correspond in a 1-1 relantionship
				 * with headersMap
				 */
				HashMap<String, ArrayList<String>> columnsMap = new HashMap<>();

				/*
				 * gropsMap is a hash table in which key: message type value :
				 * list of groups belonging to that message type
				 */
				HashMap<String, ArrayList<MessagesGroup>> groupsMap = new HashMap<>();

				/*
				 * gropsFieldsMap is a hash table in which key: message type
				 * (that admits groups/batches) value : "grouped by" fields
				 */
				HashMap<String, ArrayList<String>> groupFieldsMap = new HashMap<>();
				HashMap<String, Boolean> msgType = new HashMap<>();

				// HashMap<String, String> chldmsgtype = new HashMap<>();
				if (containsOnlyNumbers(searchValue)
						&& searchValue.length() < 11) {
					model.addAttribute("amountSearchValue", searchValue);
					amountSearchValue = Integer.parseInt(searchValue);
				} else {
					model.addAttribute("amountSearchValue", -1);
					amountSearchValue = -1;
				}

				for (int i = 0; i < messageTypes.size(); i++) {
					ArrayList<String> columns = new ArrayList<>();
					ArrayList<String> groupFields = new ArrayList<>();
					// System.out.println(messageTypes.get(i));
					headersMap
							.put(messageTypes.get(i),
									dbClient.getTableHeaders(
											messageTypes.get(i), "T", columns));
					columnsMap.put(messageTypes.get(i), columns);
					if (searchValue != null)
					groupsMap.put(messageTypes.get(i), dbClient.getGroups(
							queueName, messageTypes.get(i), amountSearchValue,
							searchValue.toUpperCase()));
					else
						groupsMap.put(
								messageTypes.get(i),
								dbClient.getGroups(queueName,
										messageTypes.get(i), -1, ""));
					dbClient.getTableHeaders(messageTypes.get(i), "G",
							groupFields);
					groupFieldsMap.put(messageTypes.get(i), groupFields);
					if (isParent.get(i) != false) {
						msgType.put(messageTypes.get(i), true);
					} else {
						msgType.put(messageTypes.get(i), false);
					}
					// chldmsgtype.put(messageTypes.get(i),ChildMsgType.get(i));

				}

				model.addAttribute("messageTypes", msgType.keySet());
				model.addAttribute("isParent", msgType);
				model.addAttribute("headers", headersMap);
				model.addAttribute("columns", columnsMap);
				model.addAttribute("groupsMap", groupsMap);
				model.addAttribute("groupFieldNames", groupFieldsMap);
				model.addAttribute("isComposedMsgType", isComposedMsgType);
				model.addAttribute("composedMsgId", composedMsgId);
				if (searchValue != null)
					model.addAttribute("trnSearchValue",
							searchValue.toUpperCase());
				else
					model.addAttribute("trnSearchValue", "");

				/*
				 * System.out.println(headersMap + "headersMap");
				 * System.out.println(columnsMap + "columnsMap");
				 * System.out.println(groupsMap + "groupsMap");
				 * System.out.println(groupFieldsMap + "groupFieldsMap"); //
				 * model.addAttribute("childMsgType", chldmsgtype);
				 */}

			else {
				dbClient.getConnection();
				Queue[] queues = queueService.getQueueList();
				model.addAttribute("queues", queues);

				// add queue name attribute
				model.addAttribute("queueName", queueName);
				ArrayList<String> messageTypes = new ArrayList<>();
				ArrayList<Boolean> isParent = new ArrayList<>();
				ArrayList<String> ChildMsgType = new ArrayList<>();
				isParent = queueService.getIsParrentMessageInQueue(queueName);

				// add messagetypes array
				messageTypes = queueService.getMessageTypesInQueue(queueName);
				ChildMsgType = queueService.getChildMessageTypes(queueName);

				if (messageTypes == null) {
					return "tiles/forbidden";
				}

				// build hashmap of headers (message type is the key, array
				// containing table headers is the value)
				// get groups for current message type
				// if there are no groups, then treat as a non-batchalbe page

				/*
				 * headers Map is a hash in which key: messageType (s.a. 103)
				 * value: array of strings which represent the table headers
				 * name (s.a. Sender, Receiver, Reference ...)
				 */
				HashMap<String, ArrayList<String>> headersMap = new HashMap<>();
				/*
				 * columns Map is a hash in which key: messageType (s.a. 103)
				 * value: array of strings which represent the fields contained
				 * by the json object returned by fintp API (s.a. sender,
				 * receiver, trn ...) they correspond in a 1-1 relantionship
				 * with headersMap
				 */
				HashMap<String, ArrayList<String>> columnsMap = new HashMap<>();

				/*
				 * gropsMap is a hash table in which key: message type value :
				 * list of groups belonging to that message type
				 */
				HashMap<String, ArrayList<MessagesGroup>> groupsMap = new HashMap<>();

				/*
				 * gropsFieldsMap is a hash table in which key: message type
				 * (that admits groups/batches) value : "grouped by" fields
				 */
				HashMap<String, ArrayList<String>> groupFieldsMap = new HashMap<>();
				HashMap<String, Boolean> msgType = new HashMap<>();
				HashMap<String, String> chldmsgtype = new HashMap<>();
				if (containsOnlyNumbers(searchValue)
						&& searchValue.length() < 11) {
					model.addAttribute("amountSearchValue", searchValue);
					amountSearchValue = Integer.parseInt(searchValue);
				} else {
					model.addAttribute("amountSearchValue", -1);
					amountSearchValue = -1;
				}
				for (int i = 0; i < messageTypes.size(); i++) {
					ArrayList<String> columns = new ArrayList<>();
					ArrayList<String> groupFields = new ArrayList<>();
					// System.out.println(messageTypes.get(i));
					headersMap
							.put(messageTypes.get(i),
									dbClient.getTableHeaders(
											messageTypes.get(i), "T", columns));
					columnsMap.put(messageTypes.get(i), columns);
					if (searchValue != null)
						groupsMap.put(messageTypes.get(i), dbClient.getGroups(
								queueName, messageTypes.get(i),
								amountSearchValue, searchValue.toUpperCase()));
					else
						groupsMap.put(
								messageTypes.get(i),
								dbClient.getGroups(queueName,
										messageTypes.get(i), -1, ""));
					dbClient.getTableHeaders(messageTypes.get(i), "G",
							groupFields);
					groupFieldsMap.put(messageTypes.get(i), groupFields);
					if (isParent.get(i) != false) {
						msgType.put(messageTypes.get(i), true);
					} else {
						msgType.put(messageTypes.get(i), false);
					}
					chldmsgtype.put(messageTypes.get(i), ChildMsgType.get(i));
					System.out.println(queueName + " - " + messageTypes.get(i));
					// id= queueService.getMessagesOfGivenType(queueName,
					// messageTypes.get(i));
				}

				model.addAttribute("messageTypes", msgType.keySet());
				model.addAttribute("isParent", msgType);
				model.addAttribute("headers", headersMap);
				model.addAttribute("columns", columnsMap);
				model.addAttribute("groupsMap", groupsMap);
				model.addAttribute("groupFieldNames", groupFieldsMap);
				model.addAttribute("isComposedMsgType", isComposedMsgType);
				model.addAttribute("childMsgType", chldmsgtype);
				model.addAttribute("stmtuid", id);
				if (searchValue != null)
					model.addAttribute("trnSearchValue",
							searchValue.toUpperCase());
				else
					model.addAttribute("trnSearchValue", "");

				if (containsOnlyNumbers(searchValue)
						&& searchValue.length() < 11) {
					model.addAttribute("amountSearchValue", searchValue);
				} else {
					model.addAttribute("amountSearchValue", -1);
					amountSearchValue = -1;
				}

				/*
				 * System.out.println(headersMap + "headersMap");
				 * System.out.println(columnsMap + "columnsMap");
				 * System.out.println(groupsMap + "groupsMap");
				 * System.out.println(groupFieldsMap + "groupFieldsMap");
				 */
			}

		} finally {
			dbClient.closeConnection();
		}
		return "tiles/queue";
	}

}
