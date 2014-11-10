package com.note.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.note.DAO.UserDAO;
import com.note.domain.User;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport implements ServletRequestAware,
		ServletResponseAware {
	private static final long serialVersionUID = 1L;

	HttpServletRequest request;

	HttpServletResponse response;

	private String password;
	private String id;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void login() {
		try {
			this.response.setContentType("text/json;charset=utf-8");
			this.response.setCharacterEncoding("UTF-8");
			// JSONObject json=new JSONObject();
			Map<String, String> json = new HashMap<String, String>();
			System.out.println(password + id);
			UserDAO userdao = new UserDAO();
			boolean f = userdao.queryUser(id);
			if (f == true) {
				boolean ff = userdao.login(id, password);
				if (ff == true)
					json.put("message", "登录成功！");
				else
					json.put("message", "用户名或密码错误！");
			} else {
				json.put("message", "ID不存在！");
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
