package com.note.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.note.domain.Note;
import com.note.domain.Topic;
import com.note.domain.User;
import com.opensymphony.xwork2.ActionSupport;
import com.note.DAO.*;
import com.note.util.*;

public class AddNoteAction extends ActionSupport implements
		ServletRequestAware, ServletResponseAware {
	private static final long serialVersionUID = 1L;

	HttpServletRequest request;

	HttpServletResponse response;

	private String title;
	private String note;
	private String date;
	private String userid;
	private String site;
	private String conclusion;
	private String member;
	private String topicid;

	public String getMember() {
		return this.member;
	}

	public String getUserid() {
		return this.userid;
	}

	public String getTopicid(){
		return this.topicid;
	}
	
	public String getSite() {
		return this.site;
	}

	public String getTitle() {
		return this.title;
	}

	public String getNote() {
		return this.note;
	}

	public String getConclusion() {
		return this.conclusion;
	}

	public String getDate() {
		return this.date;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTopicid(String topicid){
		this.topicid=topicid;
	}
	
	public void setMember(String member) {
		this.member = member;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void addNote() {
		try {
			this.response.setContentType("text/json;charset=utf-8");
			this.response.setCharacterEncoding("UTF-8");
			// JSONObject json=new JSONObject();
			Map<String, String> json = new HashMap<String, String>();
			Note note1 = new Note(userid, title, note, conclusion, date,
					site, member,topicid);
			NoteDAO notedao = new NoteDAO();
			boolean f = notedao.addNote(note1);
			System.out
					.println(title + note + userid + conclusion + site + date);
			if (f == true) {
				json.put("message", "添加成功！");
			} else {
				json.put("message", "系统出错！");
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
