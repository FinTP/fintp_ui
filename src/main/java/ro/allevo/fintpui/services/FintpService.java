package ro.allevo.fintpui.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import ro.allevo.fintpui.model.Queue;
import ro.allevo.fintpui.model.Queues;
import ro.allevo.fintpui.utils.RestClient;


public class FintpService {

	private String url;

	public Queue[] getQueues() {
		RestTemplate client = new RestClient("admin", "admin");
		List<Queue> result = new ArrayList<>();
		String newUrl = url + "/queues";
		Queues queuesJSON = client.getForObject(newUrl, Queues.class);
		return queuesJSON.getQueues();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
