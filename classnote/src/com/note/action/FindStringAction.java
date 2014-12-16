package com.note.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.note.DAO.TopicDAO;
import com.note.DAO.UserDAO;
import com.note.domain.Topic;
import com.opensymphony.xwork2.ActionSupport;

public class FindStringAction extends ActionSupport implements
		ServletRequestAware, ServletResponseAware {
	private static final long serialVersionUID = 1L;

	HttpServletRequest request;

	HttpServletResponse response;

	private String prefix;

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix= prefix;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void findString() {
		try {
			this.response.setContentType("text/json;charset=utf-8");
			this.response.setCharacterEncoding("UTF-8");
			// JSONObject json=new JSONObject();
			System.out.println(prefix);
			Map<String, String> json = new HashMap<String, String>();
			UserDAO userdao = new UserDAO();
			ArrayList<String> list = userdao.findName(prefix);
			if (list != null) {
				String message = "";
				int len = list.size();
				for (int i = 0; i < len; ++i) {
					message = message + list.get(i);
				}
				System.out.println(message);
				json.put("message", message);
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
