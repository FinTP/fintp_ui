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

package ro.allevo.fintpui.dao;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

import javax.ws.rs.core.UriBuilder;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;


import ro.allevo.fintpui.exception.ConflictException;
import ro.allevo.fintpui.model.RoutingRule;
import ro.allevo.fintpui.model.RoutingRules;
import ro.allevo.fintpui.utils.RestClient;
import ro.allevo.fintpui.utils.servlets.ServletsHelper;

public class RoutingRuleRestApiDao implements RoutingRuleDao {

	@Autowired
	ServletsHelper servletsHelper;

	@Override
	public ArrayList<RoutingRule> getAllRoutingRules() {
		ArrayList<RoutingRule> result = new ArrayList<RoutingRule>();
		RestTemplate client = new RestClient();
		int page = 1;
		String url = servletsHelper.getUrl() + "/routingrules?page=" + page;
		RoutingRules entity = client.getForObject(url, RoutingRules.class);
		result.addAll(Arrays.asList(entity.getRoutingrules()));
		while (entity.hasMore()) {
			page++;
			url = servletsHelper.getUrl() + "/routingrules?page=" + page;
			entity = client.getForObject(url, RoutingRules.class);
			result.addAll(Arrays.asList(entity.getRoutingrules()));

		}
		return result;
	}

	@Override
	public ArrayList<RoutingRule> getRoutingRulesBySchema(String schemaName) {
		ArrayList<RoutingRule> result = new ArrayList<RoutingRule>();
		RestTemplate client = new RestClient();
		int page = 1;
		String url = servletsHelper.getUrl() + "/routingschemas/" + schemaName
				+ "/routingrules?page=" + page;

		RoutingRules entity = client.getForObject(url, RoutingRules.class);
		result.addAll(Arrays.asList(entity.getRoutingrules()));

		while (entity.hasMore()) {
			page++;
			url = servletsHelper.getUrl() + "/routingschemas/" + schemaName
					+ "/routingrules?page=" + page;
			entity = client.getForObject(url, RoutingRules.class);
			result.addAll(Arrays.asList(entity.getRoutingrules()));

		}
		return result;
	}

	@Override
	public RoutingRule getRoutingRule(String name) {

		RestTemplate client = new RestClient();

		String url = servletsHelper.getUrl() + "/routingrules/" + name;
		RoutingRule entity = client.getForObject(url, RoutingRule.class);
		return entity;
	}

	@Override
	public void insertRoutingRule(RoutingRule rule) {
		URI uri = UriBuilder.fromUri(servletsHelper.getUrl())
				.path("routingrules").build();
		JSONObject jsonResponse = servletsHelper.postAPIResource(uri, rule);
		try {
			if (jsonResponse.getInt("code") == 409) {
				throw new ConflictException("Conflict error on rule insertion! Duplicate sequence value detected.");
			}
		} catch (JSONException e) {
			throw new RuntimeException("The api did not return any code");
		}
	}

	@Override
	public void updateRoutingRule(String name, RoutingRule rule) {
		URI uri = UriBuilder.fromUri(servletsHelper.getUrl())
				.path("routingrules").path(name).build();
		servletsHelper.putAPIResource(uri, name, rule);
	}

	@Override
	public void deleteRoutingRule(String name) {
		URI uri = UriBuilder.fromUri(servletsHelper.getUrl())
				.path("routingrules").path(name).build();
		servletsHelper.deleteAPIResource(uri);
	}

}
