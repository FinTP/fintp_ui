package ro.allevo.fintpui;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	String message = "Welcome to your 1st Maven Spring project !";

	@RequestMapping("/hi")
	public ModelAndView showMessage() {
		System.out.println("from controller");
		return new ModelAndView("hello", "message", message);
	}
	
	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("time", new Date());
		model.put("message", message);
		return "welcome";
	}
}

