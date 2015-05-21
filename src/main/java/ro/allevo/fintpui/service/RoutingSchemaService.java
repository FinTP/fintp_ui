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

import ro.allevo.fintpui.dao.RoutingSchemaDao;
import ro.allevo.fintpui.model.RoutingSchema;

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
