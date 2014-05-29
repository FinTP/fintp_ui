package ro.allevo.fintpui.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import ro.allevo.fintpui.model.Queues;
import ro.allevo.fintpui.model.ServiceMap;
import ro.allevo.fintpui.model.ServiceMaps;
import ro.allevo.fintpui.utils.RestClient;
import ro.allevo.fintpui.utils.servlets.ServletsHelper;

public class ServiceMapRestApiDao implements ServiceMapDao{

	@Autowired
	ServletsHelper servletsHelper;
	
	@Override
	public ServiceMap[] getServiceMapList() {
		RestTemplate client = new RestClient();
		String url = servletsHelper.getUrl() + "/servicemaps";
		ServiceMaps entity = client.getForObject(url, ServiceMaps.class);
		return entity.getServicemaps();
	}

	@Override
	public ArrayList<String> getServiceMapNamesList() {
		ArrayList<String> friendlyNames = new ArrayList<>();
		for(ServiceMap map : getServiceMapList()){
			friendlyNames.add(map.getFriendlyname());
		}
		return friendlyNames;
	}
	
	@Override
	public void insertServiceMap(ServiceMap serviceMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateServiceMap(String friendlyName, ServiceMap serviceMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteServiceMap(String friendlyName) {
		// TODO Auto-generated method stub
		
	}

	

}
