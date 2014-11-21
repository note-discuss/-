package com.note.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.note.DAO.UserDAO;
import com.note.domain.User;
import com.opensymphony.xwork2.ActionSupport;

public class GetUserAction extends ActionSupport implements ServletRequestAware,
		ServletResponseAware {
	private static final long serialVersionUID = 1L;

	HttpServletRequest request;

	HttpServletResponse response;

	private String userid;

	public void setUserid(String userid) throws Exception{
		this.userid = new String(userid.getBytes("iso-8859-1"),"utf-8");
	}

	public String getUserid() {
		return this.userid;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void getUser() {
		try {
			this.response.setContentType("text/json;charset=utf-8");
			this.response.setCharacterEncoding("UTF-8");
			// JSONObject json=new JSONObject();
			Map<String, String> json = new HashMap<String, String>();
			UserDAO userdao = new UserDAO();
			User user = userdao.getUser(userid);
			if (user !=null) {
				ArrayList<User> list = new ArrayList<User>();
				list.add(user);
                JSONArray jsonarray = JSONArray.fromObject(list);//³ö´í
                String userlist=jsonarray.toString();
                 System.out.println("userlist="+userlist);
                json.put("UserList", userlist);
			} else {
				json.put("UserList", "null");
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
