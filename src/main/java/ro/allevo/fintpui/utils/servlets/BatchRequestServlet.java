package ro.allevo.fintpui.utils.servlets;

import java.io.IOException;
import java.net.URI;
import java.util.Enumeration;
import java.util.Map;

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
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@WebServlet("/batchRequest")
public class BatchRequestServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7109942102128193051L;

	private static Logger logger = LogManager.getLogger(BatchRequestServlet.class
			.getName());
	
	@Autowired
	private ServletsHelper servletsHelper;

	private static String DATA_CHANGED_ERROR_MESSAGE = "The messages on your screen are not up to date." +
			" Please reload page, then perform the desired actions.";
	
	@Override
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, 
				config.getServletContext());
	}
	
	@Override 
	public void doGet (HttpServletRequest request, HttpServletResponse response)
		throws IOException{
		boolean isUserFilterEnabled = Boolean.getBoolean(request
				.getParameter("userFilter"));
		String groupKey = request.getParameter("groupkey");
		
		//build url depending on arguments received
		// if groupkey is specified return details about that batch request
		// else, get all groupkeys (verifying whether the filtering by user is enabled
		URI uri = UriBuilder.fromPath(servletsHelper.getUrl()).path("batchrequests")
				.build();
		
		if(groupKey != null){
			uri = UriBuilder.fromUri(uri).path(groupKey).build();
		}else{
			if(isUserFilterEnabled){
				String username = ((UserDetails)SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal()).getUsername();
				uri = UriBuilder.fromUri(uri).queryParam("user", username).build();
			}
		}
		
		final Client client = servletsHelper.getAPIClient();
		WebResource webResource = client.resource(uri.toString());
		ClientResponse clientResponse = webResource
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON)
				.get(ClientResponse.class);
		JSONObject responseEntity = clientResponse.getEntity(JSONObject.class);
		response.setContentType("application/json");
		response.getWriter().println(responseEntity.toString());
		response.getWriter().flush();
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
	
		logger.info("POST  /batchRequest servlet");
		JSONObject requestEntity = new JSONObject();
		String[] fields = request
				.getParameterValues("fields[]");
		try {
			String username = new String(
					((UserDetails) SecurityContextHolder.getContext()
							.getAuthentication().getPrincipal()).getUsername());
			requestEntity.put("groupkey", request.getParameter("groupkey"));
			requestEntity.put("queuename", request.getParameter("queuename"));
			requestEntity.put("msgtype", request.getParameter("msgtype"));
			requestEntity.put("timekey", request.getParameter("timekey"));
			requestEntity.put("username", username);
			for (int i = 0; i < fields.length; i++) {
				requestEntity.put("field" + (i + 1) + "val", fields[i]);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		final Client client = servletsHelper.getAPIClient();
		URI uri = UriBuilder.fromPath(servletsHelper.getUrl()).path("batchrequests")
				.build();
		WebResource webResource = client.resource(uri.toString());
		ClientResponse clientResponse = webResource
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, requestEntity);
		JSONObject responseEntity = clientResponse.getEntity(JSONObject.class);
		
		response.setContentType("application/json");
		
		switch (clientResponse.getStatus()) {
		case 202:
			response.setStatus(202);
			response.getWriter().println(responseEntity.toString());
			response.getWriter().flush();
			break;
		case 406:
			response.setStatus(406);
			//create custon answer
			responseEntity = new JSONObject();
			try {
				responseEntity.put("message", DATA_CHANGED_ERROR_MESSAGE);
				response.getWriter().println(responseEntity.toString());
				response.getWriter().flush();
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case 403:
			response.setStatus(403);
			response.getWriter().println(responseEntity.toString());
			response.getWriter().flush();
			break;
		default:
			throw new RuntimeException("Failed : HTTP error code : "
					+ clientResponse.getStatus() + " => handle this type of response");
		}
	}
}
