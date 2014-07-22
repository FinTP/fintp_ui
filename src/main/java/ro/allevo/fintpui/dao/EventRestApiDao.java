package ro.allevo.fintpui.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import ro.allevo.fintpui.model.Event;
import ro.allevo.fintpui.model.Events;
import ro.allevo.fintpui.utils.RestClient;
import ro.allevo.fintpui.utils.servlets.ServletsHelper;

public class EventRestApiDao implements EventDao {

	@Autowired
	ServletsHelper servletsHelper;
	
	@Override
	public Event getEvent(String eventGuid) {
		// TODO Auto-generated method stub
		RestTemplate client = new RestClient();
		String url = servletsHelper.getUrl() + "/events/"+eventGuid;
		Event entity = client.getForObject(url, Event.class);
		return entity;
	}

	@Override
	public Event[] getAllEvents() {
		// TODO Auto-generated method stub
		RestTemplate client = new RestClient();
		String url = servletsHelper.getUrl() + "/events";
		Events entity = client.getForObject(url, Events.class);
		return entity.getEvents();
	}

}
