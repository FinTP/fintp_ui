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
	 * Plain JSP
	 */
	@RequestMapping(value="/users/jsp",method=RequestMethod.GET)
	public String findUsersPlain(Model model){
		logger.info("/users/jsp requested");
		
		buildUserList(model);
		model.addAttribute("title", "Users List - Plain JSP");
		return "users";
	}
	
	/**
	 * JSP with Tiles
	 */
	@RequestMapping(value="/users/tiles",method=RequestMethod.GET)
	public String findUsersTiles(Model model){
		logger.info("/users/tiles requested");
		
		buildUserList(model);
		model.addAttribute("title", "Users List - Tiles");
		return "tiles/users";
	}

	/**
	 * HTML with ThymeLeaf
	 */
	@RequestMapping(value="/users/thymeleaf",method=RequestMethod.GET)
	public String findUsersThymeLeaf(Model model){
		logger.info("/users/thymeleaf requested");
		
		buildUserList(model);
		model.addAttribute("title", "Users List - Thymeleaf");
		return "thymeleaf/users";
	}
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String root()
	{
		logger.info("/ requested");
		return "index";
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