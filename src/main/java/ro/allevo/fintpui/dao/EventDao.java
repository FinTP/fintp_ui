package ro.allevo.fintpui.dao;

import ro.allevo.fintpui.model.Event;

public interface EventDao {

	public Event getEvent(String eventGuid);
	public Event[] getAllEvents();

}
