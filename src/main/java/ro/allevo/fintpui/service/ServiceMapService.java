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
