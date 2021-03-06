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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ro.allevo.fintpui.model.RoutingSchema;
import ro.allevo.fintpui.service.RoutingRulesService;
import ro.allevo.fintpui.service.RoutingSchemaService;
import ro.allevo.fintpui.service.TimeLimitsService;
import ro.allevo.fintpui.utils.servlets.ServletsHelper;

@Controller
@RequestMapping()
public class RoutingSchemasController {

		@Autowired
		private ServletsHelper servletsHelper;
		
		@Autowired
		private RoutingSchemaService routingSchemaService;
		
		@Autowired
		private RoutingRulesService routingRulesService;
		
		@Autowired
		private TimeLimitsService timeLimitsService;
		
		private static Logger logger = LogManager.getLogger(RoutingSchemasController.class
				.getName());
		
		/*
		 * DISPLAY
		 */
		@RequestMapping(value="/schemas", method = RequestMethod.GET)
		public String printSchemas(ModelMap model){
			logger.info("/schemas requested");
			RoutingSchema[] routingSchemas = routingSchemaService.getAllRoutingSchemas();
			model.addAttribute("schemas", routingSchemas);
			return "tiles/schemas";
		}
		
	
		/*
		 * INSERT
		 */
		@RequestMapping(value = "/addSchema", method=RequestMethod.GET)
		public String addSchema(ModelMap model, @ModelAttribute("schema") RoutingSchema schema){
			logger.info("/addSchema page requested");
			model.addAttribute("schemas", routingSchemaService.getAllRoutingSchemaNames());
			model.addAttribute("timelimits", timeLimitsService.getAllTimeLimitNames());
			return "tiles/schemas_add";
		}

		@RequestMapping(value = "/schemas/insert", method = RequestMethod.POST)
		public String insertSchema(@ModelAttribute("schema") RoutingSchema routingSchema){
			logger.info("/insert routing schema requested");
			routingSchemaService.insertRoutingSchema(routingSchema);
			//if the user chose to initialize schema with copies from other schema, do so
			if(!routingSchema.getSchemaCopy().equals("")){
				routingRulesService.copyRules(routingSchema.getSchemaCopy(), routingSchema.getName());
			}
			return "redirect:/schemas.htm";
		}
		
		/*
		 * EDIT
		 */
		@RequestMapping(value = "/editSchema")
		public String editSchema(ModelMap model, @RequestParam(value="schema", required = true) String schemaName){
			logger.info("/editSchema requested");
			RoutingSchema schema = routingSchemaService.getRoutingSchema(schemaName);
			model.addAttribute("schema",schema);
			model.addAttribute("timelimits", timeLimitsService.getAllTimeLimitNames());
			return "tiles/schemas_edit";
		}
		
		@RequestMapping(value = "/schemas/update", method = RequestMethod.POST)
		public String updateSchema(@ModelAttribute("schema") RoutingSchema schema, @RequestParam("init_name") String initialName){
			logger.info("/update routing schema requested");
			routingSchemaService.updateRoutingSchema(initialName, schema);
			return "redirect:/schemas.htm";
		}
		
		/*
		 * DELETE
		 */
		@RequestMapping(value = "/schemas/deleteSchema")
		public String deleteQueue(@RequestParam("schema") String schemaName){
			logger.info("/delete schema requested");
			routingRulesService.deleteRulesFromSchema(schemaName);
			routingSchemaService.deleteRoutingSchema(schemaName);
			return "redirect:/schemas.htm";
		}
}
