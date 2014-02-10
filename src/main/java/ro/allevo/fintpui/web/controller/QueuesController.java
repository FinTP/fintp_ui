package ro.allevo.fintpui.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import ro.allevo.fintpui.core.domain.Queue;
import ro.allevo.fintpui.core.services.FintpService;

@Controller
@RequestMapping("/queues")
public class QueuesController  {

	private FintpService fintpService;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String printMenu(ModelMap model){
		List<Queue> queues = fintpService.getQueues();
		model.addAttribute("queues", queues);
		return "queues";
	}
	
	public void setFintpService(FintpService fintpService) {
		this.fintpService = fintpService;
	}

}
