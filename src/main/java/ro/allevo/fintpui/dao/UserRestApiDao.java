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
import ro.allevo.fintpui.model.User;
import ro.allevo.fintpui.model.Users;
import ro.allevo.fintpui.utils.RestClient;
import ro.allevo.fintpui.utils.servlets.ServletsHelper;

public class UserRestApiDao implements UserDao {

	@Autowired
	ServletsHelper servletsHelper;

	@Override
	public User[] getAllUsers() {
		// TODO Auto-generated method stub
		RestTemplate client = new RestClient();
		String url = servletsHelper.getUrl() + "/users";
		Users entity = client.getForObject(url, Users.class);
		return entity.getUsers();
	}

	@Override
	public User getUser(String username) {
		// TODO Auto-generated method stub
		RestTemplate client = new RestClient();
		String url = servletsHelper.getUrl() + "/users/" + username;
		User entity = client.getForObject(url, User.class);
		return entity;
	}

	@Override
	public User getUserById(int userid) {
		// TODO Auto-generated method stub
		User entity = null;
		User[] entities = this.getAllUsers();
		for (User user : entities) {
			if (user.getUserid() == userid)
				entity = user;
		}
		return entity;
	}

	@Override
	public void insertUser(User user) {
		// TODO Auto-generated method stub
		URI uri = UriBuilder.fromUri(servletsHelper.getUrl()).path("users")
				.build();
		servletsHelper.postAPIResource(uri, user);

	}

	@Override
	public void updateUser(String username, User user) {
		// TODO Auto-generated method stub
		URI uri = UriBuilder.fromUri(servletsHelper.getUrl()).path("users")
				.path(username).build();
		servletsHelper.putAPIResource(uri, username, user);

	}

	@Override
	public void deleteUser(String username) {
		// TODO Auto-generated method stub
		URI uri = UriBuilder.fromUri(servletsHelper.getUrl()).path("users")
				.path(username).build();
		servletsHelper.deleteAPIResource(uri);
		System.out.println(uri + "");
	}

}
