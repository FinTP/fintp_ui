package ro.allevo.fintpui.dao;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import ro.allevo.fintpui.model.TimeLimit;
import ro.allevo.fintpui.model.TimeLimits;
import ro.allevo.fintpui.utils.RestClient;
import ro.allevo.fintpui.utils.servlets.ServletsHelper;

public class TimeLimitsRestApiDao implements TimeLimitsDao {

	@Autowired
	ServletsHelper servletsHelper;
	
	@Override
	public TimeLimit[] getAllTimeLimits() {
		RestTemplate client = new RestClient();
		String url = servletsHelper.getUrl() + "/timelimits";
		TimeLimits timeLimits = client.getForObject(url, TimeLimits.class);
		return timeLimits.getTimelimits();
	}

	@Override
	public void insertTimeLimit(TimeLimit timelimit) {
		URI uri = UriBuilder.fromUri(servletsHelper.getUrl()).path("timelimits").build();
		servletsHelper.postAPIResource(uri, timelimit);
	}

	@Override
	public void updateTimeLimit(String limitName, TimeLimit timelimit) {
		URI uri = UriBuilder.fromUri(servletsHelper.getUrl())
				.path("timelimits").path(limitName).build();
		servletsHelper.putAPIResource(uri, limitName, timelimit);
	}

	@Override
	public void deleteTimeLimit(String limitName) {
		URI uri = UriBuilder.fromUri(servletsHelper.getUrl())
				.path("timelimits").path(limitName).build();
		servletsHelper.deleteAPIResource(uri);
	}

	@Override
	public TimeLimit getTimeLimit(String limitName) {
		RestTemplate client = new RestClient();
		String url = servletsHelper.getUrl() + "/timelimits/"+limitName;
		TimeLimit entity = client.getForObject(url, TimeLimit.class);
		return entity;
	}
}
