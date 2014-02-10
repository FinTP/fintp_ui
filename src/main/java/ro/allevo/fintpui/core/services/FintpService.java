package ro.allevo.fintpui.core.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import ro.allevo.fintpui.core.domain.Queue;
import ro.allevo.fintpui.core.domain.Queues;
import ro.allevo.fintpui.utils.RestClient;

public class FintpService {

	private String url;

	public List<Queue> getQueues() {
		RestTemplate client = new RestClient("admin","admin");
		List<Queue> result = new ArrayList<>();
		String newUrl = url+"/queues";
		Queues queuesJSON = client.getForObject(newUrl, Queues.class);
		for (int i = 0; i < queuesJSON.getQueues().length; i++) {
			result.add(queuesJSON.getQueues()[i]);
		}
		// client.
		return result;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	

}
