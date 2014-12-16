package com.note.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.note.domain.Topic;
import com.note.util.DBPool;


public class TopicDAO {
    Statement stmt=null;
    ResultSet rs=null;
    DBPool dbpool = new DBPool();
    
    public boolean deleteTopic(String topicid) {
    	boolean flag=false;
    	String sql_delete="delete from topic where id='"+topicid+"'";
		Connection conn=dbpool.getConnection();
		try {
			Statement stmt=conn.createStatement();
			int value=stmt.executeUpdate(sql_delete);
			if(value>0){
				flag=true;	
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	public boolean addTopic(Topic topic) {//添加一个主题
		Connection conn  = dbpool.getConnection();
		boolean flag=false;
		String sql_insert="insert into topic (title,note,conclusion,userid,site,date,member) values('"+
		topic.getTitle()+"','"+topic.getNote()+"','"+topic.getConclusion()+"','"
		+topic.getUserid()+"','"+topic.getSite()+"','"+topic.getDate()+"','"+
		topic.getMember()+"')";
		System.out.println(sql_insert);
		try {
			stmt=conn.createStatement();
			int count=stmt.executeUpdate(sql_insert);
			if(count!=0){
				flag=true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return flag;
	}
	public boolean updateMember(String member,String topicid){
		Connection conn  = dbpool.getConnection();
		boolean flag=false;
		try{
			String update_str="update topic set member='"+
					member+"' where id='"+topicid+"';";
			stmt=conn.createStatement();
			int return_count=stmt.executeUpdate(update_str);
			if(return_count==1){
				flag=true;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	public boolean updateConclusion(String conclusion,String topicid){
		Connection conn  = dbpool.getConnection();
		boolean flag=false;
		try{
			String update_str="update topic set conclusion='"+
			conclusion+"' where id='"+topicid+"';";
			stmt=conn.createStatement();
			int return_count=stmt.executeUpdate(update_str);
			if(return_count==1){
				flag=true;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	public Topic queryTopic(String topicid) {
		Connection conn = dbpool.getConnection();
		String sql_query="select * from topic where id = '"+topicid+"'";
		System.out.println(sql_query);
		Topic topic = null;
		try {
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql_query);
			ArrayList<Topic> list1 = new ArrayList<Topic>();
			while(rs.next()){
				topic= new Topic(rs.getString("userid"),rs.getString("title"),
				rs.getString("note"),rs.getString("conclusion"),rs.getString("date"),
				rs.getString("site"),rs.getString("member"),rs.getString("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return topic;
	}
	public ArrayList<Topic> findTopicList(String memberid) {
		Connection conn = dbpool.getConnection();
		ArrayList<Topic> list = null;
		boolean f=false;
		String sql_query="select * from topic where member like '%("+memberid+")%'";
		System.out.println(sql_query);
		try {
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql_query);
			ArrayList<Topic> list1 = new ArrayList<Topic>();
			while(rs.next()){
				//System.out.println("1");
				f=true;
				Topic topic = new Topic(rs.getString("userid"),rs.getString("title"),
				rs.getString("note"),rs.getString("conclusion"),rs.getString("date"),
				rs.getString("site"),rs.getString("member"),rs.getString("id"));
				list1.add(topic);
			}
			if(f==true) list=list1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<Topic> findMyTopicList(String title,String userid) {
		Connection conn = dbpool.getConnection();
		ArrayList<Topic> list = null;
		boolean f=false;
		String sql_query="select * from topic where title like '%"+title+"%' and member like '%"+userid + "%'";
		System.out.println(sql_query);
		try {
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql_query);
			ArrayList<Topic> list1 = new ArrayList<Topic>();
			while(rs.next()){
				f=true;
				Topic topic = new Topic(rs.getString("userid"),rs.getString("title"),
				rs.getString("note"),rs.getString("conclusion"),rs.getString("date"),
				rs.getString("site"),rs.getString("member"),rs.getString("id"));
				list1.add(topic);
			}
			if(f==true) list=list1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
