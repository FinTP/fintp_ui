package ro.allevo.fintpui.services;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.ws.rs.core.MediaType;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.client.RestTemplate;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import ro.allevo.fintpui.model.Queue;
import ro.allevo.fintpui.model.Queues;
import ro.allevo.fintpui.utils.RestClient;


public class FintpService {

	private String url;
	public static String NESTED_TABLES_XSLT = "nestedTables.xslt";

	public Queue[] getQueues() {
		RestTemplate client = new RestClient();
		String newUrl = url + "/queues";
		Queues queuesJSON = client.getForObject(newUrl, Queues.class);
		return queuesJSON.getQueues();
	}
	
	public ArrayList<String> getMessageTypesInQueue(String queueName) throws JSONException{
		String calledUrl = url + "/queues/"+queueName+"/messagetypes";
		ArrayList<String> messageTypes = new ArrayList<>();
		UserDetails principal = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		Client client = Client.create(new DefaultClientConfig());
		client.addFilter(new HTTPBasicAuthFilter(
				principal.getUsername(),principal.getPassword()));
		WebResource webResource = client.resource(calledUrl);
		JSONObject jsonResponse = webResource.accept(MediaType.APPLICATION_JSON)
				.get(JSONObject.class);
		JSONArray jsonArray = jsonResponse.getJSONArray("messagetypes");
		for(int i = 0; i < jsonArray.length(); i++){
			messageTypes.add(jsonArray.getString(i));
		}
		return messageTypes;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url){
		this.url = url;
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
