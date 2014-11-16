package com.note.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.note.DAO.NoteDAO;
import com.note.DAO.UserDAO;
import com.note.DAO.TopicDAO;
import com.note.domain.Note;
import com.note.domain.Topic;
import com.opensymphony.xwork2.ActionSupport;

public class GetNoteListAction extends ActionSupport implements ServletRequestAware,
ServletResponseAware {
	private static final long serialVersionUID = 1L;

	HttpServletRequest request;

	HttpServletResponse response;
	
	private String topicid;
	
	public void setTopicid(String topicid) {
		this.topicid = topicid;
	}
	
	public String getTopicid() {
		return this.topicid;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public void getNoteList(){
		try {
			   this.response.setContentType("text/json;charset=utf-8");
			   this.response.setCharacterEncoding("UTF-8");
			  Map<String,String> json=new HashMap<String,String>();
			  NoteDAO notedao= new NoteDAO();
              ArrayList<Note> list = notedao.queryNoteByTopicId(topicid);
              if(list!=null){
                 JSONArray jsonarray = JSONArray.fromObject(list);//³ö´í
                 String notelist=jsonarray.toString();
                  System.out.println("notelist="+notelist);
                 json.put("NoteList", notelist);
              }else{
            	  System.out.println("list==null!");
            	  json.put("NoteList", "list==null!");
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
