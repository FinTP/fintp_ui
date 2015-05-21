/*
* FinTP - Financial Transactions Processing Application
* Copyright (C) 2013 Business Information Systems (Allevo) S.R.L.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>
* or contact Allevo at : 031281 Bucuresti, 23C Calea Vitan, Romania,
* phone +40212554577, office@allevo.ro <mailto:office@allevo.ro>, www.allevo.ro.
*/

package ro.allevo.fintpui.controllers;

import ro.allevo.fintpui.model.User;
import ro.allevo.fintpui.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsersController {
	
	@Autowired
	private UserService userService;
	
	
	private static Logger logger = LogManager.getLogger(UsersController.class.getName());

	/*
	 * DISPLAY
	 */
	@RequestMapping(value="/users", method = RequestMethod.GET)
	public String printUsers(ModelMap model){
		logger.info("/users requested");
		User[] users = userService.getAllUsers();
		model.addAttribute("users", users);
		return "tiles/users";
	}
	/*
	 * INSERT
	 */
	@RequestMapping(value = "/addUser", method=RequestMethod.GET)
	public String addUser(ModelMap model, @ModelAttribute("user") User user){
		logger.info("/addUser page requested");
		return "tiles/users_add";
	}

	@RequestMapping(value = "/users/insert", method = RequestMethod.POST)
	public String insertUser(@ModelAttribute("user") User user){
		logger.info("/insert user requested");
		userService.insertUser(user);
		//if the user chose to initialize schema with copies from other schema, do so
		return "redirect:/users.htm";
	}
	
	/*
	 * EDIT
	 */
	@RequestMapping(value = "/editUser")
	public String editUser(ModelMap model, @RequestParam(value="user", required = true) String username){
		logger.info("/editUser requested");
		User user = userService.getUser(username);
		model.addAttribute("user",user);
		return "tiles/users_edit";
	}
	
	@RequestMapping(value = "/users/update", method = RequestMethod.POST)
	public String updateUser(@ModelAttribute("user") User user, @RequestParam("init_name") String userName){
		logger.info("/update user requested");
		userService.updateUser(userName, user);
		return "redirect:/users.htm";
	}
	
	/*
	 * DELETE
	 */
	@RequestMapping(value = "/users/deleteUser")
	public String deleteUser(@RequestParam("user") String username){
		logger.info("/delete user requested");
		userService.deleteUser(username);
		return "redirect:/users.htm";
	}

}
