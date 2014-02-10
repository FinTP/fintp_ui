package ro.allevo.fintpui.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
@RequestMapping("/")
public class SiteController {

	@RequestMapping(method = RequestMethod.GET)
	public String printMenu(ModelMap model){
		model.addAttribute("message", "Welcome user to Fintp");
		System.out.println("REDIRECTING to home...");
		return "home";
	}
	
}
