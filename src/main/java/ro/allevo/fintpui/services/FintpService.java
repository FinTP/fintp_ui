package ro.allevo.fintpui.services;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import org.springframework.web.client.RestTemplate;
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
