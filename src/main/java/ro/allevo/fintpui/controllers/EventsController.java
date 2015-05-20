package ro.allevo.fintpui.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ro.allevo.fintpui.model.Event;
import ro.allevo.fintpui.service.EventService;

@Controller
public class EventsController {
	
	@Autowired
	private EventService eventService;
	
	private static Logger logger = LogManager.getLogger(EventsController.class
			.getName());
	
	@RequestMapping(value="/events", method = RequestMethod.GET)
	public String printEvent(ModelMap model){
		logger.info("/event requested");
		Event[] events = eventService.getAllEvents();
		model.addAttribute("events", events);
		return "tiles/events";
	}

}
