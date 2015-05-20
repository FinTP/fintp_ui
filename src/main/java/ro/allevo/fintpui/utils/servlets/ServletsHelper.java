package ro.allevo.fintpui.utils.servlets;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
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

public class ServletsHelper {
	
	private String url;
	
	public Client getAPIClient(){
		final ClientConfig cc = new DefaultClientConfig();
		final Client client = Client.create(cc);
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		client.addFilter(new HTTPBasicAuthFilter(new String(
				((UserDetails) authentication.getPrincipal()).getUsername()),
				new String(((UserDetails) authentication.getPrincipal())
						.getPassword())));
		return client;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public ClientResponse getAPIResource(URI uri) {
		final Client client = getAPIClient();
		WebResource webResource = client.resource(uri);
		ClientResponse clientResponse = webResource
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		return clientResponse;
	}

	public JSONObject postAPIResource(URI uri, Object requestObject){
		try {
			ObjectMapper mapper = new  ObjectMapper();
			mapper.setSerializationInclusion(Inclusion.NON_EMPTY);
			JSONObject requestEntity = new JSONObject(mapper.writeValueAsString(requestObject));
			final Client client = getAPIClient();
			WebResource webResource = client.resource(uri);
			ClientResponse clientResponse = 
					webResource.accept(MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, requestEntity);
			JSONObject responseEntity = clientResponse.getEntity(JSONObject.class);
			return responseEntity;
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JSONObject putAPIResource(URI uri, String initialName, Object requestObject){
		try {
			ObjectMapper mapper = new  ObjectMapper();
			mapper.setSerializationInclusion(Inclusion.NON_EMPTY);
			JSONObject requestEntity = new JSONObject(mapper.writeValueAsString(requestObject));
			final Client client = getAPIClient();
			WebResource webResource = client.resource(uri);
			ClientResponse clientResponse = 
					webResource.accept(MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON)
					.put(ClientResponse.class, requestEntity);
			JSONObject responseEntity = clientResponse.getEntity(JSONObject.class);
			return responseEntity;
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JSONObject deleteAPIResource(URI uri){
		final Client client = getAPIClient();
		WebResource webResource = client.resource(uri);
		ClientResponse clientResponse = 
				webResource.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON)
				.delete(ClientResponse.class);
		JSONObject responseEntity = clientResponse.getEntity(JSONObject.class);
		return responseEntity;
	}
	
	
}
