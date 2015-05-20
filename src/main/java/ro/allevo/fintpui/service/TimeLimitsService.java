package ro.allevo.fintpui.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import ro.allevo.fintpui.dao.TimeLimitsDao;
import ro.allevo.fintpui.model.TimeLimit;

public class TimeLimitsService {

	@Autowired
	TimeLimitsDao timeLimitsDao;
	
	public TimeLimit[] getAllTimeLimits(){
		return timeLimitsDao.getAllTimeLimits();
	}
	public TimeLimit getTimeLimit(String limitName){
		return timeLimitsDao.getTimeLimit(limitName);
	}
	public void insertTimeLimit(TimeLimit timelimit){
		timeLimitsDao.insertTimeLimit(timelimit);
	}
	
	public void updateTimeLimit(String limitName, TimeLimit timelimit){
		timeLimitsDao.updateTimeLimit(limitName, timelimit);
	}
	public void deleteTimeLimit(String limitName){
		timeLimitsDao.deleteTimeLimit(limitName);
	}
	
	public ArrayList<String> getAllTimeLimitNames() {
		TimeLimit[] timeLimits = getAllTimeLimits();
		ArrayList<String> result = new ArrayList<>();
 		for(TimeLimit timeLimit : timeLimits){
			result.add(timeLimit.getLimitname());
		}
		return result;
	}
}
