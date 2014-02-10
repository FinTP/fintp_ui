package ro.allevo.fintpui.controllers;
import java.util.ArrayList;
import java.util.List;

import ro.allevo.fintpui.model.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
	
	private static Logger logger = LogManager.getLogger(UserController.class
			.getName());

	/**
	 * JSP with Tiles
	 */
	@RequestMapping(value="/users",method=RequestMethod.GET)
	public String findUsersTiles(Model model){
		logger.info("/users requested");
		
		buildUserList(model);
		model.addAttribute("title", "Users List - Tiles");
		return "tiles/users";
	}

	private void buildUserList(Model model) {
		List<User> users = new ArrayList<User>();
		users.add(new User("Paul", "Chapman"));
		users.add(new User("Mike", "Wiesner"));
		users.add(new User("Mark", "Secrist"));
		users.add(new User("Ken", "Krueger"));
		users.add(new User("Wes", "Gruver"));
		users.add(new User("Kevin", "Crocker"));
		model.addAttribute("users", users);
	}
}