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

import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

import ro.allevo.fintpui.model.MessageDuplicate;
import ro.allevo.fintpui.service.MessageService;
import ro.allevo.fintpui.utils.JdbcClient;
import ro.allevo.fintpui.utils.servlets.ServletsHelper;

@Controller
@RequestMapping("/viewDuplicates")
public class MessageDuplicatesController {
	@Autowired
	private JdbcClient client;

	@Autowired
	private MessageService messageService;

	private static Logger logger = LogManager
			.getLogger(ReportsFormController.class.getName());

	@Autowired
	private ServletsHelper servletsHelper;

	public static String NESTED_TABLES_XSLT = "nestedTables.xslt";

	@RequestMapping(method = RequestMethod.GET)
	public String getMessagePayload(
			ModelMap model,
			@RequestParam(value = "inMsgID", required = true) String id,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "inQueueName", required = true) String queue,
			@RequestParam(value = "inLiveArch", required = true) String livearch,
			@RequestParam(value = "dupID", required = false) String dupid,
			@RequestParam(value = "dupQueue", required = false) String dupqueue) {
		logger.info("/duplicates view requested");

		Map<String, String> allRequestParams = new HashMap<String, String>();
		allRequestParams.put("inMsgID", id);
		allRequestParams.put("inQueueName", queue);
		allRequestParams.put("inLiveArch", livearch);
		/*
		 * allRequestParams.put("dupID", dupid);
		 * allRequestParams.put("dupQueue", dupqueue);
		 */

		client.getConnection();
		ArrayList<MessageDuplicate> msgdupl = messageService
				.getDuplicateMessageDetails(allRequestParams);
		client.closeConnection();
		String payload = "";
		String friendlyPayload = "";
		String friendlyPayloaddupl = "";
		try {

			final Client client = servletsHelper.getAPIClient();
			URI uri = UriBuilder.fromPath(servletsHelper.getUrl())
					.path("queues").path(queue).path("messages").path(id)
					.queryParam("type", type).build();
			WebResource webResource = client.resource(uri.toString());
			ClientResponse clientResponse = webResource
					.accept(MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
			JSONObject responseEntity = clientResponse
					.getEntity(JSONObject.class);
			if (dupqueue.equalsIgnoreCase("n/a")) {
				friendlyPayload = messageService.getPayload(dupid);
			} else {
				payload = responseEntity.getString("payload");
				// String payload = responseEntity.getString("payload");
				// now, get friendly payload
				String path = getClass().getClassLoader()
						.getResource(NESTED_TABLES_XSLT).getPath();
				friendlyPayload = applyXSLT(payload, path);
			}

			if (dupid.equals("") == false) {
				final Client clientd = servletsHelper.getAPIClient();

				URI urid = UriBuilder.fromPath(servletsHelper.getUrl())
						.path("queues").path(dupqueue).path("messages")
						.path(dupid).queryParam("type", type).build();
				WebResource webResourced = clientd.resource(urid.toString());
				ClientResponse clientResponsed = webResourced
						.accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON)
						.get(ClientResponse.class);
				JSONObject responseEntityd = clientResponsed
						.getEntity(JSONObject.class);

				if (dupqueue.equalsIgnoreCase("n/a")) {
					friendlyPayloaddupl = messageService.getPayload(dupid);
				} else {
				String payloadd = responseEntityd.getString("payload");
				// now, get friendly payload
				String pathd = getClass().getClassLoader()
						.getResource(NESTED_TABLES_XSLT).getPath();
				friendlyPayloaddupl = applyXSLT(payloadd, pathd);}
				model.addAttribute("duplpayload", friendlyPayloaddupl);
			}
			model.addAttribute("origpayload", friendlyPayload);

			model.addAttribute("duplicate", msgdupl);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "tiles/viewDuplicates";
	}

	public static String applyXSLT(String input, String xsltPath) {

		try {
			StringReader reader = new StringReader(input);
			StringWriter writer = new StringWriter();
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory
					.newTransformer(new javax.xml.transform.stream.StreamSource(
							xsltPath));

			transformer.transform(new javax.xml.transform.stream.StreamSource(
					reader),
					new javax.xml.transform.stream.StreamResult(writer));

			return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
