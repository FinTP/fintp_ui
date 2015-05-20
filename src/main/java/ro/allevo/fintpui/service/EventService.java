package ro.allevo.fintpui.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import ro.allevo.fintpui.dao.EventDao;
import ro.allevo.fintpui.model.Event;

public class EventService {

	@Autowired
	private EventDao eventDao;
	
	public Event[] getAllEvents() {
		return eventDao.getAllEvents();
	}
	
	public Event getEvent(String eventGuid){
		return eventDao.getEvent(eventGuid);
	}
	
	public ArrayList<String> getAllEventsNames(){
		ArrayList<String> result = new ArrayList<>();
		for(Event event : getAllEvents()){
			result.add(event.getGuid());
		}
		return result;
	}
	
}
