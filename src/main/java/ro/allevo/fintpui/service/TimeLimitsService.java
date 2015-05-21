/*
* FinTP - Financial Transactions Processing Application
* Copyright (C) 2013 Business Information Systems (Allevo) S.R.L.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>
* or contact Allevo at : 031281 Bucuresti, 23C Calea Vitan, Romania,
* phone +40212554577, office@allevo.ro <mailto:office@allevo.ro>, www.allevo.ro.
*/

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
