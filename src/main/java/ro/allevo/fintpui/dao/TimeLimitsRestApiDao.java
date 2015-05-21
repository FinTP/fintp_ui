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

import javax.ws.rs.core.UriBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import ro.allevo.fintpui.model.TimeLimit;
import ro.allevo.fintpui.model.TimeLimits;
import ro.allevo.fintpui.utils.RestClient;
import ro.allevo.fintpui.utils.servlets.ServletsHelper;

public class TimeLimitsRestApiDao implements TimeLimitsDao {

	@Autowired
	ServletsHelper servletsHelper;
	
	@Override
	public TimeLimit[] getAllTimeLimits() {
		RestTemplate client = new RestClient();
		String url = servletsHelper.getUrl() + "/timelimits";
		TimeLimits timeLimits = client.getForObject(url, TimeLimits.class);
		return timeLimits.getTimelimits();
	}

	@Override
	public void insertTimeLimit(TimeLimit timelimit) {
		URI uri = UriBuilder.fromUri(servletsHelper.getUrl()).path("timelimits").build();
		servletsHelper.postAPIResource(uri, timelimit);
	}

	@Override
	public void updateTimeLimit(String limitName, TimeLimit timelimit) {
		URI uri = UriBuilder.fromUri(servletsHelper.getUrl())
				.path("timelimits").path(limitName).build();
		servletsHelper.putAPIResource(uri, limitName, timelimit);
	}

	@Override
	public void deleteTimeLimit(String limitName) {
		URI uri = UriBuilder.fromUri(servletsHelper.getUrl())
				.path("timelimits").path(limitName).build();
		servletsHelper.deleteAPIResource(uri);
	}

	@Override
	public TimeLimit getTimeLimit(String limitName) {
		RestTemplate client = new RestClient();
		String url = servletsHelper.getUrl() + "/timelimits/"+limitName;
		TimeLimit entity = client.getForObject(url, TimeLimit.class);
		return entity;
	}
}
