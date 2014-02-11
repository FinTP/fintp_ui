package ro.allevo.fintpui.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/queuesAJAX")
public class QueuesAJAXController {
	
	private static Logger logger = LogManager.getLogger(QueuesAJAXController.class
			.getName());
	@RequestMapping(method = RequestMethod.GET)
	public String printMenu(ModelMap model) {
		logger.info("queuesAJAX requested");
		return "tiles/queuesAJAX";
	}
}
