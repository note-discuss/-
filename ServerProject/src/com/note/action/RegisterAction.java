package com.note.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.note.domain.User;
import com.opensymphony.xwork2.ActionSupport;
import com.note.DAO.*;
import com.note.util.*;

public class RegisterAction extends ActionSupport implements ServletRequestAware,
ServletResponseAware {
	private static final long serialVersionUID = 1L;

	HttpServletRequest request;

	HttpServletResponse response;
	
	private String username;
	private String password;
	private String id;
	private String sex;
	private String role;
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password)throws Exception {
		this.password = new String(password.getBytes("iso-8859-1"),"utf-8");
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) throws Exception{
		this.username = new String(username.getBytes("iso-8859-1"),"utf-8");
	}
	
	public void setId(String id)throws Exception {
		this.id = new String(id.getBytes("iso-8859-1"),"utf-8");
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setSex(String sex) throws Exception{
		this.sex = new String(sex.getBytes("iso-8859-1"),"utf-8");
	}
	
	public String getSex() {
		return this.sex;
	}
	
	public void setRole(String role) throws Exception{
		this.role = new String(role.getBytes("iso-8859-1"),"utf-8");
	}
	
	public String getRole() {
		return this.role;
	}
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public void register(){
		try {
            //如果不采用接口注入的方式的获取HttpServletRequest，HttpServletResponse的方式
			  // HttpServletRequest request =ServletActionContext.getRequest();
			  // HttpServletResponse response=ServletActionContext.getResponse();
			  // this.request.setCharacterEncoding("gbk");
			   String url=request.getRequestURI();
			   String url1=new String(url.getBytes("iso-8859-1"),"gb2312");
			   System.out.println("url1="+url1);
			   this.response.setContentType("text/json;charset=utf-8");
			   this.response.setCharacterEncoding("UTF-8");
			   //JSONObject json=new JSONObject(); 
			    Map<String,String> json=new HashMap<String,String>();
			    System.out.println(username+password+id+sex+role);
			    User user = new User(id,username,password,role,sex);
			    UserDAO userdao = new UserDAO();
			   boolean f=userdao.addUser(user);
				if (f==true) {
					 json.put("message", "注册成功！点击确定登录！");
				} else {
					json.put("message", "通信出错！");
				}
			  byte[] jsonBytes = json.toString().getBytes("utf-8");
			  response.setContentLength(jsonBytes.length);
			  response.getOutputStream().write(jsonBytes);
			  response.getOutputStream().flush();
			  response.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
