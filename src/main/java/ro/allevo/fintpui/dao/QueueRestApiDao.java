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

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import ro.allevo.fintpui.model.Queue;
import ro.allevo.fintpui.model.Queues;
import ro.allevo.fintpui.utils.RestClient;
import ro.allevo.fintpui.utils.servlets.ServletsHelper;



public class QueueRestApiDao implements QueueDao{

	@Autowired
	ServletsHelper servletsHelper;
	
	
	
	@Override
	public Queue getQueue(String queue) {
		RestTemplate client = new RestClient();
		String url = servletsHelper.getUrl() + "/queues/"+queue;
		return client.getForObject(url, Queue.class);
	}

	@Override
	public Queue[] getQueueList() {
		RestTemplate client = new RestClient();
		String url = servletsHelper.getUrl() + "/queues";
		Queues queuesJSON = client.getForObject(url, Queues.class);
		return queuesJSON.getQueues();
	}

	@Override
	public void insertQueue(Queue queue) {
		URI uri = UriBuilder.fromUri(servletsHelper.getUrl()).path("queues").build();
		servletsHelper.postAPIResource(uri, queue);
	}

	
	@Override
	public void updateQueue(String queueName, Queue queue) {
		URI uri = UriBuilder.fromUri(servletsHelper.getUrl()).path("queues").path(queueName).build();
		servletsHelper.putAPIResource(uri, queueName, queue);
	}

	@Override
	public void deleteQueue(String queueName) {
		URI uri = UriBuilder.fromUri(servletsHelper.getUrl()).path("queues").path(queueName).build();
		servletsHelper.deleteAPIResource(uri);
	}

	@Override
	public JSONObject getQueueListAsJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
