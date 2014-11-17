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

	public void setTopicid(String topicid)throws Exception {
		this.topicid= new String(topicid.getBytes("iso-8859-1"),"utf-8");
	}
	
	public void setUserid(String userid)throws Exception {
		this.userid= new String(userid.getBytes("iso-8859-1"),"utf-8");
	}

	public void setTitle(String title) throws Exception{
		this.title= new String(title.getBytes("iso-8859-1"),"utf-8");
	}

	public void setMember(String member) throws Exception{
		this.member= new String(member.getBytes("iso-8859-1"),"utf-8");
	}

	public void setNote(String note) throws Exception{
		this.note= new String(note.getBytes("iso-8859-1"),"utf-8");
	}

	public void setConclusion(String conclusion) throws Exception{
		this.conclusion=new String(conclusion.getBytes("iso-8859-1"),"utf-8");
	}

	public void setSite(String site) throws Exception{
		this.site=new String(site.getBytes("iso-8859-1"),"utf-8");
	}

	public void setDate(String date) throws Exception{
		this.date=new String(date.getBytes("iso-8859-1"),"utf-8");
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
			System.out.println(title + note + userid + conclusion + site + date);
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
