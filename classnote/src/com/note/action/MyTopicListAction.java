package com.note.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.note.DAO.TopicDAO;
import com.note.domain.Topic;
import com.opensymphony.xwork2.ActionSupport;

public class MyTopicListAction extends ActionSupport implements ServletRequestAware,
ServletResponseAware {
	private static final long serialVersionUID = 1L;

	HttpServletRequest request;

	HttpServletResponse response;
	
	private String searchstr;
	private String userid;

	public String getSearchstr(){
		return this.searchstr;
	}
	
	public String getUserid(){
		return this.searchstr;
	}
	
	public void setSearchstr(String searchstr) {
		this.searchstr= searchstr;
	}
	
	public void setUserid(String userid){
		this.userid= userid;
	}
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public void myTopicList() throws InvocationTargetException{
		try {
			   this.response.setContentType("text/json;charset=utf-8");
			   this.response.setCharacterEncoding("UTF-8");
			   //JSONObject json=new JSONObject(); 
			  Map<String,String> json=new HashMap<String,String>();
			  TopicDAO topicdao= new TopicDAO();
           ArrayList<Topic> list = topicdao.findMyTopicList(searchstr,userid);
           if(list!=null){
         	  System.out.println(list.get(0).getDate());
         	  JSONArray jsonarray = JSONArray.fromObject(list);//³ö´í
             String topiclist=jsonarray.toString();
              System.out.println("topiclist="+topiclist);
               json.put("TopicList", topiclist);
           }else{
         	  System.out.println("list==null!");
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
