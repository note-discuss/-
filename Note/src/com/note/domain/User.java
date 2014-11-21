package com.note.domain;

import java.io.Serializable;

public class User implements Serializable{
	//实现了Serializable接口，对象可序列化
	private String id;
	private String username;
	private String password;
	private String role;
	private String sex;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String id, String username, String password, String role, String sex) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.sex = sex;
	}
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
	}
	public String getUsername() {
		return this.username;
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
	public String getRole(){
		return role;
	}
	public void setRole(String role){
		this.role = role;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	@Override
	public String toString() {
		return "User [id=" +id + ", username=" + username + ", password=" + password + ", role=" + role + ", sex=" + sex + "]";
	}
	
}

