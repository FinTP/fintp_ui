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
@RequestMapping("/batchesForm")
public class BatchesFormController {

	private static Logger logger = LogManager.getLogger(ReportsFormController.class
			.getName());
	
	@Autowired
	private JdbcClient dbClient;
	
	@RequestMapping(method = RequestMethod.GET)
	public String printForm(ModelMap model){
		logger.info("/batchesForm requested");
		try{
			dbClient.establishConnection();
			ArrayList<String> messageTypes = dbClient.getFTMessageTypes();
			model.addAttribute("messageTypes", messageTypes);
			model.addAttribute("bicCodes", dbClient.getBicCodes());
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			dbClient.closeConnection();
		}
		return "tiles/batchesForm";
	}
}
