package ro.allevo.fintpui.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
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
			@RequestParam(value = "issdate", required = false) String issdate,
			@RequestParam(value = "matdate", required = false) String matdate,
			@RequestParam(value = "dbtid", required = false) String dbtid,
			@RequestParam(value = "issuanceDate", required = false) String issuanceDate,
			@RequestParam(value = "maturityDate", required = false) String maturityDate,
			@RequestParam(value = "dbtID", required = false) String dbtID,
			@RequestParam(value = "cdtid", required = false) String cdtid,
		    @RequestParam(value = "messageTypes", required = false) String messageType,
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
