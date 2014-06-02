package ro.allevo.fintpui.dao;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import ro.allevo.fintpui.model.RoutingSchema;
import ro.allevo.fintpui.model.RoutingSchemas;
import ro.allevo.fintpui.utils.RestClient;
import ro.allevo.fintpui.utils.servlets.ServletsHelper;

public class RoutingSchemaRestApiDao implements RoutingSchemaDao{

	@Autowired
	ServletsHelper servletsHelper;
	
	@Override
	public RoutingSchema[] getAllRoutingSchemas() {
		RestTemplate client = new RestClient();
		String url = servletsHelper.getUrl() + "/routingschemas";
		RoutingSchemas entity = client.getForObject(url, RoutingSchemas.class);
		return entity.getRoutingschemas();
	}

	@Override
	public void insertRoutingSchema(RoutingSchema routingSchema) {
		URI uri = UriBuilder.fromUri(servletsHelper.getUrl()).path("routingschemas").build();
		servletsHelper.postAPIResource(uri, routingSchema);
	}

	@Override
	public void updateRoutingSchema(String schemaName,
			RoutingSchema routingSchema) {
		URI uri = UriBuilder.fromUri(servletsHelper.getUrl())
				.path("routingschemas").path(schemaName).build();
		servletsHelper.putAPIResource(uri, schemaName, routingSchema);
	}

	@Override
	public void deleteRoutingSchema(String schemaName) {
		URI uri = UriBuilder.fromUri(servletsHelper.getUrl())
				.path("routingschemas").path(schemaName).build();
		servletsHelper.deleteAPIResource(uri);
	}

	@Override
	public RoutingSchema getRoutingSchema(String schemaName) {
		RestTemplate client = new RestClient();
		String url = servletsHelper.getUrl() + "/routingschemas/"+schemaName;
		RoutingSchema entity = client.getForObject(url, RoutingSchema.class);
		return entity;
	}

}
