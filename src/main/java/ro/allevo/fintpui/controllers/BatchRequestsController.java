package ro.allevo.fintpui.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ro.allevo.fintpui.utils.servlets.ServletsHelper;

@Controller
@RequestMapping("/batchrequests")
public class BatchRequestsController {

	private static Logger logger = LogManager.getLogger(BatchRequestsController.class
			.getName());
	
	@Autowired
	private ServletsHelper servletsHelper;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getBatchRequests(ModelMap model){
		logger.info("batch_requests requested");
		return "tiles/batchRequests";
	}
}
