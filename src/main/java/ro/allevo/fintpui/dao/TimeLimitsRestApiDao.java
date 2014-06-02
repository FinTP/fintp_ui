package ro.allevo.fintpui.dao;

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTimeLimit(String limitName, TimeLimit timelimit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteTimeLimit(String limitName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TimeLimit getTimeLimit(String limitName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
