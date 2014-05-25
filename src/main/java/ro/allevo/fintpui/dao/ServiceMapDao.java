package ro.allevo.fintpui.dao;

import java.util.ArrayList;

import ro.allevo.fintpui.model.ServiceMap;

public interface ServiceMapDao {
	
	public ServiceMap[] getServiceMapList();
	public ArrayList<String > getServiceMapNamesList();
	public void insertServiceMap(ServiceMap serviceMap);
	public void updateServiceMap(String friendlyName, ServiceMap serviceMap);
	public void deleteServiceMap(String friendlyName);

}
