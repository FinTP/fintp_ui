package ro.allevo.fintpui.dao;

import ro.allevo.fintpui.model.User;

public interface UserDao {

	public User[] getAllUsers();
	public User getUser(String username);
	public void insertUser(User user);
	public void updateUser(String username, User user);
	public void deleteUser(String username);
	public User getUserById(int userid);
}
