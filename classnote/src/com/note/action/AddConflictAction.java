package com.note.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.note.DAO.NoteDAO;
import com.note.DAO.TopicDAO;
import com.note.DAO.UserDAO;
import com.note.domain.User;
import com.opensymphony.xwork2.ActionSupport;

public class AddConflictAction extends ActionSupport implements ServletRequestAware,
		ServletResponseAware {
	private static final long serialVersionUID = 1L;

	HttpServletRequest request;

	HttpServletResponse response;

	private String conclusion;
	private String topicid;
	private String noteid;

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}
	
	public void setTopicid(String topicid) {
		this.topicid =topicid;
	}
	
	public void setNoteid(String noteid){
		this.noteid = noteid;
	}
	
	public String getConclusion() {
		return this.conclusion;
	}
	
	public String getTopicid() {
		return this.topicid;
	}
	
	public String getNoteid() {
		return this.noteid;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void addConflict() {
		try {
			this.response.setContentType("text/json;charset=utf-8");
			this.response.setCharacterEncoding("UTF-8");
			boolean f=false;
			Map<String, String> json = new HashMap<String, String>();
			System.out.println(conclusion+topicid+" "+noteid);
			if(noteid.equals("-1")){
				TopicDAO t_dao=new TopicDAO();
				f=t_dao.updateConclusion(conclusion,topicid);
			}else{
				NoteDAO n_dao=new NoteDAO();
				f=n_dao.updateConclusion(conclusion,noteid);
			}
			if (f) {
                json.put("message", "true");
			} else {
				json.put("message", "false");
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
