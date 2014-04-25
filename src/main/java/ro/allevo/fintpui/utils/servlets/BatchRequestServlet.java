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
	
	@Override
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, 
				config.getServletContext());
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
	
		logger.info("POST  /batchRequest servlet");
		JSONObject requestEntity = new JSONObject();
		Map<String, String> parametersMap = request.getParameterMap();
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
		
		System.out.println(requestEntity);
		final Client client = servletsHelper.getAPIClient();
		URI uri = UriBuilder.fromPath(servletsHelper.getUrl()).path("batchrequests")
				.build();
		WebResource webResource = client.resource(uri.toString());
		ClientResponse clientResponse = webResource
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, requestEntity);
		JSONObject responseEntity = clientResponse.getEntity(JSONObject.class);
		
		
		switch (clientResponse.getStatus()) {
		case 202:
			response.setStatus(200);
			response.setContentType("application/json");
			response.getWriter().println(responseEntity.toString());
			response.getWriter().flush();
		case 403:
			response.setStatus(403);
			response.setContentType("application/json");
			response.getWriter().println(responseEntity.toString());
			response.getWriter().flush();
		default:
			throw new RuntimeException("Failed : HTTP error code : "
					+ clientResponse.getStatus() + " => handle this type of response");
		}
	}
}
