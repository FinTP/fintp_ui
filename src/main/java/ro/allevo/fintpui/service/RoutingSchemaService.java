package ro.allevo.fintpui.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import ro.allevo.fintpui.dao.RoutingSchemaDao;
import ro.allevo.fintpui.model.RoutingSchema;
import ro.allevo.fintpui.utils.RestClient;

public class RoutingSchemaService {

	@Autowired
	private RoutingSchemaDao routingSchemaDao;
	
	public RoutingSchema[] getAllRoutingSchemas() {
		return routingSchemaDao.getAllRoutingSchemas();
	}
	
	public ArrayList<String> getAllRoutingSchemaNames(){
		ArrayList<String> result = new ArrayList<>();
		for(RoutingSchema routingSchema : getAllRoutingSchemas()){
			result.add(routingSchema.getName());
		}
		return result;
	}
	
	public void insertRoutingSchema(RoutingSchema routingSchema) {
		routingSchemaDao.insertRoutingSchema(routingSchema);
	}

	public void updateRoutingSchema(String schemaName, RoutingSchema routingSchema){
		routingSchemaDao.updateRoutingSchema(schemaName, routingSchema);
	}
	
	public void deleteRoutingSchema(String schemaName){
		routingSchemaDao.deleteRoutingSchema(schemaName);
	}
	
	public RoutingSchema getRoutingSchema(String schemaName){
		return routingSchemaDao.getRoutingSchema(schemaName);
	}
	
}
