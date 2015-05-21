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
			ArrayList<String> messageTypesDI = dbClient.getDIMessageTypes();
			model.addAttribute("messageTypesDI", messageTypesDI);
			ArrayList<String> messageTypesDD = dbClient.getDDMessageTypes();
			model.addAttribute("messageTypesDD", messageTypesDD);
			ArrayList<String> messageTypesFT = dbClient.getFTMessageTypes();
			model.addAttribute("messageTypesFT", messageTypesFT);
			ArrayList<String> messageTypesST = dbClient.getSTMessageTypes();
			model.addAttribute("messageTypesST", messageTypesST);
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
