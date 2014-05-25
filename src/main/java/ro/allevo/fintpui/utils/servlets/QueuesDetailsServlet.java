/**
 * Servlet that receives a list with queues names 
 * and responds with the number of messages contained in them.
 */
package ro.allevo.fintpui.utils.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.ws.rs.core.MediaType;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import ro.allevo.fintpui.service.QueueService;
import ro.allevo.fintpui.utils.ApplicationCacheManager;

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
public class QueuesDetailsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ServletsHelper servletsHelper;
	
	@Autowired
	private QueueService queueService;
	
	@Override
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				config.getServletContext());
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			JSONObject requestData = new JSONObject(
					request.getParameter("data"));
			JSONArray queueNames = requestData.getJSONArray("queues");
			JSONObject responseData = new JSONObject();
			JSONArray numberOfMessagesList = new JSONArray();
			final Client client = servletsHelper.getAPIClient();
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
		int nbMessages =queueService.getNumberOfMessagesInQueue(queueName);
		//else, if info is useful (known number of messages) update cache
		if(nbMessages != -1){
			String value = "" + nbMessages;
			cache.put(new Element(queueName , value));
			return value;
		}
		
		return "-";
	}
}
