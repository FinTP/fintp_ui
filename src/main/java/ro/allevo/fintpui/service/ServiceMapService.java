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

import ro.allevo.fintpui.dao.ServiceMapDao;
import ro.allevo.fintpui.model.ServiceMap;

public class ServiceMapService {

	@Autowired
	ServiceMapDao serviceMapDao;
	
	public ServiceMap[] getServiceMapsList(){
		return serviceMapDao.getServiceMapList();
	}
	
	public ArrayList<String> getServiceMapNamesList(){
		return serviceMapDao.getServiceMapNamesList();
	}
	
	public ServiceMap getServiceMap(String friendlyName){
		return serviceMapDao.getServiceMap(friendlyName);
	}

	public void insertServiceMap(ServiceMap serviceMap) {
		serviceMapDao.insertServiceMap(serviceMap);
	}

	public void deleteServiceMap(String friendlyName) {
		serviceMapDao.deleteServiceMap(friendlyName);
	}

	public void updateServiceMap(String friendlyName, ServiceMap serviceMap) {
		serviceMapDao.updateServiceMap(friendlyName, serviceMap);
	}
}
