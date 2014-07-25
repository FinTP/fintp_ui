package ro.allevo.fintpui.controllers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PaymentsController {
	
	private static Logger logger = LogManager.getLogger(UsersController.class
			.getName());

	/**
	 * Plain JSP
	 */
	@RequestMapping(value="/payments",method=RequestMethod.GET)
	public String findUsersPlain(Model model){
		logger.info("/payments requested");
		
		model.addAttribute("title", "Payments");
		return "tiles/payments";
	}
}