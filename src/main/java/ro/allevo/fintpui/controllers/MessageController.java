package ro.allevo.fintpui.controllers;


import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import ro.allevo.fintpui.services.FintpService;
import ro.allevo.fintpui.utils.servlets.ServletsHelper;


@Controller
@RequestMapping("/viewPayload")
public class MessageController {

	private static Logger logger = LogManager.getLogger(ReportsFormController.class
			.getName());
	
	@Autowired
	private ServletsHelper servletsHelper;
	
	@RequestMapping(method=RequestMethod.GET)
	public String getMessagePayload(ModelMap model, 
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "type", required = true) String type){
		logger.info("/payload view requested");
		
		try {

			final Client client = servletsHelper.getAPIClient();
			URI uri = UriBuilder.fromPath(servletsHelper.getUrl())
					.path("messages").path(id).queryParam("type", type).build();
			WebResource webResource = client.resource(uri.toString());
			ClientResponse clientResponse = webResource
					.accept(MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
			JSONObject responseEntity = clientResponse
					.getEntity(JSONObject.class);
			String payload = responseEntity.getString("payload");
			//now, get friendly payload
			String path = getClass().getClassLoader()
					.getResource(FintpService.NESTED_TABLES_XSLT).getPath();
			String friendlyPayload = FintpService.applyXSLT(payload, path);
			
			model.addAttribute("payload", friendlyPayload);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "tiles/viewPayload";
	}
}
