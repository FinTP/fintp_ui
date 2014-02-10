package ro.allevo.fintpui.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ro.allevo.fintpui.model.Queue;
import ro.allevo.fintpui.services.FintpService;

@Controller
@RequestMapping("/queues")
public class QueuesController {

	private static Logger logger = LogManager.getLogger(QueuesController.class
			.getName());
	
	private FintpService fintpService;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String printMenu(ModelMap model){
		logger.info("/queues requested");
		Queue[] queues = fintpService.getQueues();
		model.addAttribute("queues", queues);
		return "tiles/queues";
	}
	
	public void setFintpService(FintpService fintpService) {
		this.fintpService = fintpService;
	}
}
