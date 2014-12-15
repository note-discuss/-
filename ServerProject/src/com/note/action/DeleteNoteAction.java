package com.note.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.note.DAO.NoteDAO;
import com.note.DAO.TopicDAO;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteNoteAction extends ActionSupport implements ServletRequestAware,
          ServletResponseAware{
	private static final long serialVersionUID=1;
	
	HttpServletRequest request;
	HttpServletResponse response;
	
	private String topicid;
	private String noteid;
	
	public void setTopicid(String topicid) {
		this.topicid = topicid;
	}
	
	public void setNoteid(String noteid) {
		this.noteid = noteid;
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
	
	public void deleteNote(){
		try {
			this.response.setContentType("text/json;charset=utf-8");
			this.response.setCharacterEncoding("UTF-8");
			boolean f1=false;
			boolean f2=false;
			Map<String, String> json = new HashMap<String, String>();
			System.out.println("topicid="+topicid+"noteid="+noteid);
			TopicDAO t_dao=new TopicDAO();
			NoteDAO n_dao=new NoteDAO();
			if(noteid.equals("-1")){
				f1=t_dao.deleteTopic(topicid);
				f2=n_dao.deleteNoteByTopicId(topicid);
			}else{
				f2=n_dao.deleteNoteByNoteId(noteid);
			}
			if (noteid.equals("-1")) {
				if(f1==true&&f2==true){
                    json.put("message", "true");
				}else{
					json.put("message", "false");
				}
			} else {
				if(f2==true){
                    json.put("message", "true");
				}else{
					json.put("message", "false");
				}
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
