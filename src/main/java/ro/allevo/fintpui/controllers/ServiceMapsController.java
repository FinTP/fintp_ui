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

import ro.allevo.fintpui.model.ServiceMap;
import ro.allevo.fintpui.model.TimeLimit;
import ro.allevo.fintpui.service.QueueService;
import ro.allevo.fintpui.service.ServiceMapService;
import ro.allevo.fintpui.utils.JdbcClient;
import ro.allevo.fintpui.utils.servlets.ServletsHelper;

@Controller
public class ServiceMapsController {
	@Autowired
	private JdbcClient dbClient;
	
	@Autowired
	private ServletsHelper servletsHelper;
	
	@Autowired
	private ServiceMapService serviceMapService;

	@Autowired 
	private QueueService queueService;
	
	private static Logger logger = LogManager.getLogger(ServiceMapsController.class
			.getName());
	
	
	/*
	 * DISPLAY
	 */
	@RequestMapping(value = "/connectors",method = RequestMethod.GET)
	public String printTimelimits(ModelMap model){
		logger.info("/connectors requested");
		ServiceMap[] servicemaps = serviceMapService.getServiceMapsList();
		model.addAttribute("servicemaps", servicemaps);
		
		model.addAttribute("apiUri", servletsHelper.getUrl());
		return "tiles/servicemaps";
	}
	
	/*
	 * EDIT
	 */
	@RequestMapping(value="/editConnector", method = RequestMethod.GET)
	public String editConnector(ModelMap model, @RequestParam(value="connector") String servicemapname){
		logger.info("/editConnector requested");
		ServiceMap servicemap = serviceMapService.getServiceMap(servicemapname);
		model.addAttribute("servicemap", servicemap);
		model.addAttribute("queues", queueService.getQueuesNames());
		model.addAttribute("apiUri", servletsHelper.getUrl());
		return "tiles/servicemap_edit";
	}
	
	@RequestMapping(value = "/servicemaps/update", method = RequestMethod.POST)
	public String updateConnectors(@ModelAttribute("servicemap") ServiceMap serviceMap, @RequestParam("friendlyname") String friendlyName){
		logger.info("/update conectors requested");
		serviceMapService.updateServiceMap(friendlyName, serviceMap);
		return "redirect:/connectors.htm";
	}
}
