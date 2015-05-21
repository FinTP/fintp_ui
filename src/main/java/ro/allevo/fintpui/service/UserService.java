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

import org.springframework.beans.factory.annotation.Autowired;
import ro.allevo.fintpui.dao.UserDao;
import ro.allevo.fintpui.model.User;

public class UserService {

	@Autowired
	private UserDao userDao;
	
	public User[] getAllUsers() {
		return userDao.getAllUsers();
	}
	
	public void insertUser(User user) {
		userDao.insertUser(user);
	}

	public void updateUser(String username, User user){
		userDao.updateUser(username, user);
	}
	
	public void deleteUser(String username){
		userDao.deleteUser(username);
	}
	
	public User getUser(String username){
		return userDao.getUser(username);
	}
	public User getUserById(int userid){
		return userDao.getUserById(userid);
	}
}
