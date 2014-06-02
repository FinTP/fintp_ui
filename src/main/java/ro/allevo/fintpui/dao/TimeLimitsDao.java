package ro.allevo.fintpui.dao;

import ro.allevo.fintpui.model.TimeLimit;

public interface TimeLimitsDao {
	
	
	public TimeLimit[] getAllTimeLimits();
	public TimeLimit getTimeLimit(String limitName);
	public void insertTimeLimit(TimeLimit timelimit);
	public void updateTimeLimit(String limitName, TimeLimit timelimit);
	public void deleteTimeLimit(String limitName);
}
