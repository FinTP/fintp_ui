package ro.allevo.fintpui.controllers;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ro.allevo.fintpui.model.MessageInReports;
import ro.allevo.fintpui.model.MessageReportInstance;
import ro.allevo.fintpui.service.MessageService;
import ro.allevo.fintpui.utils.JdbcClient;

@Controller
@RequestMapping("/view")
public class ReportsViewMessageController {
	private static Logger logger = LogManager.getLogger(ReportsFormController.class
			.getName());
	
	@Autowired
	private JdbcClient dbClient;
	
	@Autowired
	private MessageService messageService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getMessageDetails(ModelMap model,
			@RequestParam(value = "id", required = true) String id) {
		logger.info("/view requested");
		try {
			dbClient.getConnection();
			MessageReportInstance message = dbClient.getReport(id);
			model.addAttribute("message", message);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbClient.closeConnection();
		}
		return "tiles/viewMessage";
	}
}
