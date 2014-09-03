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
