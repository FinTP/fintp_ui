package ro.allevo.fintpui.utils.servlets;

import java.io.IOException;
import java.net.URI;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@WebServlet("/routeRequest")
public class RouteRequestServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4780995753062269904L;

	private static Logger logger = LogManager
			.getLogger(BatchRequestServlet.class.getName());

	@Autowired
	private ServletsHelper servletsHelper;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				config.getServletContext());
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		logger.info("POST /routeRequest");
		JSONObject requestEntity = new JSONObject();
		String username = new String(((UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal()).getUsername());
		try {
			requestEntity.put("user", username);
			JSONArray messagesAsJSON = new JSONArray();
			String[] messages = request.getParameterValues("messages[]");
			for (int i = 0; i < messages.length; i++) {
				messagesAsJSON.put(messages[i]);
			}
			requestEntity.put("action", request.getParameter("action"));
			requestEntity.put("messages", messagesAsJSON);
			requestEntity.put("user", username);
			requestEntity.put("source", request.getParameter("source"));
			String destination = request.getParameter("destination");
			if (destination != null) {
				requestEntity.put("destination", destination);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		final Client client = servletsHelper.getAPIClient();
		URI uri = UriBuilder.fromPath(servletsHelper.getUrl())
				.path("routerequests").build();

		WebResource webResource = client.resource(uri.toString());
		ClientResponse clientResponse = webResource
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, requestEntity);
		JSONObject responseEntity = clientResponse.getEntity(JSONObject.class);
		response.setContentType("application/json");
		response.getWriter().println(responseEntity.toString());
		response.getWriter().flush();
	}
}
