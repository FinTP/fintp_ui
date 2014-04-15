/**
 * Servlet that receives a list with queues names 
 * and responds with the number of messages contained in them.
 */
package ro.allevo.fintpui.utils;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.ws.rs.core.MediaType;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/queuesDynamic")
public class QueuesDynamicData extends HttpServlet {

	private static final long serialVersionUID = 1L;


	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			JSONObject requestData = new JSONObject(
					request.getParameter("data"));
			JSONArray queueNames = requestData.getJSONArray("queues");
			JSONObject responseData = new JSONObject();
			JSONArray numberOfMessagesList = new JSONArray();
			final ClientConfig cc = new DefaultClientConfig();
			final Client client = Client.create(cc);
			Authentication authentication = SecurityContextHolder.getContext()
					.getAuthentication();
			client.addFilter(new HTTPBasicAuthFilter(
					new String(((UserDetails) authentication.getPrincipal())
							.getUsername()), new String(
							((UserDetails) authentication.getPrincipal())
									.getPassword())));
			for (int i = 0; i < queueNames.length(); i++) {
				numberOfMessagesList.put(getNumberOfMessages(queueNames.getString(i), client));
			}
			responseData.put("numbers", numberOfMessagesList);
			response.setContentType("application/json");
			response.getWriter().println(responseData.toString());
			response.getWriter().flush();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private String getNumberOfMessages(String queueName, Client client) throws JSONException{
		Cache cache = CacheManager.getInstance().getCache(
				ApplicationCacheManager.NUMBER_OF_MESSAGES_IN_QUEUES);
		Element element = cache.get(queueName);
		
		//if value in cache, return it
		if (element != null){
			return (String) element.getObjectValue();
		}
		
		Integer nbMessages = getNumberOfMessagesFromAPI(queueName, client);
		//else, if info is useful (known number of messages) update cache
		if(nbMessages != null){
			String value = "" + nbMessages;
			cache.put(new Element(queueName , value));
			return value;
		}
		
		return "-";
	}
	private Integer getNumberOfMessagesFromAPI(String queueName, Client client) throws JSONException {
		
		// TODO: make url available in context!!!
		String urlRoot = "http://localhost:8080/fintpWebServices/api";
		String url = urlRoot + "/queues/" + queueName + "/messages?filter=t";
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		switch (response.getStatus()) {
		case 200:
			JSONObject entity = response.getEntity(JSONObject.class);
			return entity.getInt("total");
		case 403:
			System.out.println("Resource forbidden");
			return null;
		default:
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus() + " => handle this type of response");
		}
	}
}
