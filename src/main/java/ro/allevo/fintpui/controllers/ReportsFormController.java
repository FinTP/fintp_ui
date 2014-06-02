package ro.allevo.fintpui.controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ro.allevo.fintpui.utils.JdbcClient;

@Controller
@RequestMapping("/reportsForm")
public class ReportsFormController {
	private static Logger logger = LogManager.getLogger(ReportsFormController.class
			.getName());
	
	@Autowired
	private JdbcClient dbClient;
	
	@RequestMapping(method=RequestMethod.GET)
	public String printForm(ModelMap model){
		logger.info("/reportsForm requested");
		try{
			dbClient.getConnection();
			ArrayList<String> messageTypes = dbClient.getFTMessageTypes();
			model.addAttribute("messageTypes", messageTypes);
			model.addAttribute("states", dbClient.getStates());
			model.addAttribute("bicCodes", dbClient.getBicCodes());
			model.addAttribute("currencies", dbClient.getCurrencies());
			model.addAttribute("directions",dbClient.getDirections());
			model.addAttribute("services", dbClient.getServices());
			model.addAttribute("userIds", dbClient.getUserIds());
			model.addAttribute("userNames", dbClient.getUserNames());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			dbClient.closeConnection();
		}
		return "tiles/reportsForm";
	}
}
