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

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ro.allevo.fintpui.model.MessageInReports;
import ro.allevo.fintpui.service.MessageService;
import ro.allevo.fintpui.utils.JdbcClient;

@Controller
@RequestMapping
public class ReportsController {

	private static Logger logger = LogManager.getLogger(ReportsController.class
			.getName());
	@Autowired
	private JdbcClient client;
	
	@Autowired 
	private MessageService messageService;
	
	@RequestMapping(value = "/reports", method=RequestMethod.GET)
	public String printResults(
			ModelMap map,
			@RequestParam(value = "businessArea", required = true) String businessArea,
			@RequestParam(value = "interval", required = true) String intervalType,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "issdate", required = false) String issdate,
			@RequestParam(value = "matdate", required = false) String matdate,
			@RequestParam(value = "dbtid", required = false) String dbtid,
			@RequestParam(value = "cdtid", required = false) String cdtid,
		    @RequestParam(value = "messageTypesFT", required = false) String messageTypeFT,
		    @RequestParam(value = "messageTypesDD", required = false) String messageTypeDD,
		    @RequestParam(value = "messageTypesDI", required = false) String messageTypeDI,
		    @RequestParam(value = "messageTypesST", required = false) String messageTypesST,
			@RequestParam(value = "sender", required = false) String sender,
			@RequestParam(value = "receiver", required = false) String receiver,
			@RequestParam(value = "trn", required = false) String reference,
			@RequestParam(value = "valueDate", required = false) String valueDate,
			@RequestParam(value = "batchID", required = false) String batchId,
			@RequestParam(value = "orderField", required = true, defaultValue = "insertdate") String orderField,
			@RequestParam(value = "order", required = true, defaultValue = "desc") String order,
			@RequestParam(value = "limit", required = true, defaultValue = "100") String limit,
			@RequestParam(value = "offset", required = true, defaultValue = "0") String offset,
			@RequestParam Map<String,String> allRequestParams) throws ParseException, SQLException {
		logger.info("/reports requested");

		client.getConnection();
		StringBuilder total = new StringBuilder();
		ArrayList<MessageInReports> reportInstances = messageService.getMessagesInReport(allRequestParams, total);
		client.closeConnection();
		
		map.addAttribute("messages", reportInstances);
		map.addAttribute("total", total);
		map.addAttribute("offset", offset);
		map.addAttribute("orderField", orderField);
		map.addAttribute("order", order);
		map.addAttribute("limit", limit);
		
		return "tiles/reports";
	}
	
	@RequestMapping(value = "/viewMessage", method = RequestMethod.GET)
	public String printMessage(ModelMap model,  
			@RequestParam(value="id", required = true) String id,
			@RequestParam(value="businessArea", required = true) String businessArea){
		
		MessageInReports message = messageService.getMessageInReports(id, businessArea);
		model.addAttribute("message", message);
		model.addAttribute("businessArea",businessArea);
		if(message.hasPayload()){
			model.addAttribute("payload", messageService.getPayload(id));
		}
		if(message.hasImage()){
			model.addAttribute("image", messageService.getImage(id));
		}
		
		return "tiles/viewMessage";
	}
}
