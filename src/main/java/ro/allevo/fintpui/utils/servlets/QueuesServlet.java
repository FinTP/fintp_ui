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
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

@WebServlet("/queuesServlet")
public class QueuesServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6327513475061655728L;
	private static Logger logger = LogManager.getLogger(QueuesServlet.class
			.getName());
	
	@Autowired
	private ServletsHelper servletsHelper;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				config.getServletContext());
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response){
		logger.info("/queuesServlet POST reuested");
		
		try {
			URI uri = UriBuilder.fromUri(servletsHelper.getUrl()).path("queues").build();
			final Client client = servletsHelper.getAPIClient();
			WebResource webResource = client.resource(uri);
			ClientResponse clientResponse = webResource.accept(MediaType.APPLICATION_JSON)
					.get(ClientResponse.class);
			response.setContentType("application/json");
			response.getWriter().println(clientResponse.getEntity(JSONObject.class));
			response.getWriter().flush();
		} catch (ClientHandlerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UniformInterfaceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
