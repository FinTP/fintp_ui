package ro.allevo.fintpui.controllers;


import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

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

import ro.allevo.fintpui.utils.servlets.ServletsHelper;


@Controller
@RequestMapping("/viewPayload")
public class MessageController {

	private static Logger logger = LogManager.getLogger(ReportsFormController.class
			.getName());
	
	@Autowired
	private ServletsHelper servletsHelper;
	
	public static String NESTED_TABLES_XSLT = "nestedTables.xslt";
	
	@RequestMapping(method=RequestMethod.GET)
	public String getMessagePayload(ModelMap model, 
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "queue", required = true) String queue){
		logger.info("/payload view requested");
		
		try {

			final Client client = servletsHelper.getAPIClient();
			URI uri = UriBuilder.fromPath(servletsHelper.getUrl())
					.path("queues").path(queue)
					.path("messages").path(id).queryParam("type", type).build();
			System.out.println(uri);
			WebResource webResource = client.resource(uri.toString());
			ClientResponse clientResponse = webResource
					.accept(MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
			JSONObject responseEntity = clientResponse
					.getEntity(JSONObject.class);
			String payload = responseEntity.getString("payload");
			//now, get friendly payload
			String path = getClass().getClassLoader()
					.getResource(NESTED_TABLES_XSLT).getPath();
			String friendlyPayload = applyXSLT(payload, path);
			
			model.addAttribute("payload", friendlyPayload);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "tiles/viewPayload";
	}
	
	public static String applyXSLT(String input, String xsltPath){
		
		try {
		    StringReader reader = new StringReader(input);
		    StringWriter writer = new StringWriter();
		    TransformerFactory tFactory = TransformerFactory.newInstance();
		    Transformer transformer = tFactory.newTransformer(
		            new javax.xml.transform.stream.StreamSource(xsltPath));

		    transformer.transform(
		            new javax.xml.transform.stream.StreamSource(reader), 
		            new javax.xml.transform.stream.StreamResult(writer));

		    return writer.toString();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return null;
	}
}
