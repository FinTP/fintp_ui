package ro.allevo.fintpui.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import ro.allevo.fintpui.model.RoutingSchema;
import ro.allevo.fintpui.model.RoutingSchemas;
import ro.allevo.fintpui.utils.RestClient;
import ro.allevo.fintpui.utils.servlets.ServletsHelper;

@Controller
@RequestMapping("/schemas")
public class RoutingSchemasController {

		@Autowired
		ServletsHelper servletsHelper;
		
		private static Logger logger = LogManager.getLogger(RoutingSchemasController.class
				.getName());
		
		@RequestMapping(method = RequestMethod.GET)
		public String printSchemas(ModelMap model){
			logger.info("/schemas requested");
			RoutingSchema[] routingSchemas = getRoutingSchemas();
			model.addAttribute("schemas", routingSchemas);
			return "tiles/schemas";
		}
		
		private RoutingSchema[] getRoutingSchemas() {
			RestTemplate client = new RestClient();
			String url = servletsHelper.getUrl() + "/routingschemas";
			RoutingSchemas schemasJSON = client.getForObject(url, RoutingSchemas.class);
			return schemasJSON.getRoutingschemas();
		}
		
}
