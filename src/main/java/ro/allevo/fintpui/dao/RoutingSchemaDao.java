package ro.allevo.fintpui.dao;

import ro.allevo.fintpui.model.RoutingSchema;

public interface RoutingSchemaDao {

	public RoutingSchema[] getAllRoutingSchemas();

	public void insertRoutingSchema(RoutingSchema routingSchema);
	
	public void updateRoutingSchema(String schemaName, RoutingSchema routingSchema);
	
	public void deleteRoutingSchema(String schemaName);
	
	public RoutingSchema getRoutingSchema(String schemaName);
}
