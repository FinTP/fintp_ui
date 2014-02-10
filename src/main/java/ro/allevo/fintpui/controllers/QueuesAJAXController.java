package ro.allevo.fintpui.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/queuesAJAX")
public class QueuesAJAXController {

	@RequestMapping(method = RequestMethod.GET)
	public String printMenu(ModelMap model) {
		return "queuesAJAX";
	}
}
