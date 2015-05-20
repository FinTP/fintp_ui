package ro.allevo.fintpui.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

	private int userid;
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String skincolor;
	private int islocked;
	private int noretry;
	private String email;
	

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSkincolor() {
		return skincolor;
	}

	public void setSkincolor(String skincolor) {
		this.skincolor = skincolor;
	}

	public int getIslocked() {
		return islocked;
	}

	public void setIslocked(int islocked) {
		this.islocked = islocked;
	}

	public int getNoretry() {
		return noretry;
	}

	public void setNoretry(int noretry) {
		this.noretry = noretry;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}