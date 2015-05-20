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

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


@WebServlet("/getPayload")
public class MessageServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private ServletsHelper servletsHelper;
	
	@Override
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				config.getServletContext());
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String messageId = request.getParameter("id");
		String messageType = request.getParameter("type");
		
		final Client client = servletsHelper.getAPIClient();
		URI uri = UriBuilder.fromPath(servletsHelper.getUrl()).path("messages")
				.path(messageId).queryParam("type", messageType).build();
		WebResource webResource = client.resource(uri.toString());
		ClientResponse clientResponse = webResource
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		JSONObject responseEntity = clientResponse.getEntity(JSONObject.class);
		//TODO: get other fields if necessary
		try {
			response.getWriter().println(responseEntity.getString("payload"));
		} catch (JSONException e) {
			throw new RuntimeException("From API : this message doesn't have payload");
		}
	}
	
}
