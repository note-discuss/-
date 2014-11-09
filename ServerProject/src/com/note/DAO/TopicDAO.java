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
	public ArrayList<Topic> queryTopic(String UserId) {//查询该ID参与的主题
		Connection conn  = dbpool.getConnection();
	    ArrayList<Topic> list= new ArrayList<Topic>();
	    if(conn==null) System.out.println("conn==null!");
		String sql_query="select * from Topic where userid='"+UserId+"'";
		try {
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql_query);
			while(rs.next()){
				Topic topic=new Topic();
				System.out.println("TopicDAO"+rs.getString("title")+rs.getString("note")+rs.getString("userid")
						+rs.getString("date")+rs.getString("conclusion")+rs.getString("site"));
				topic.setTitle(rs.getString("title"));
				topic.setNote(rs.getString("note"));
				topic.setUserid(rs.getString("userid"));
				topic.setDate(rs.getString("date"));
				topic.setConclusion(rs.getString("conclusion"));
				topic.setSite(rs.getString("site"));
				topic.setMember(rs.getString("member"));
				list.add(topic);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<Topic> findTopicList(String id) {
		Connection conn = dbpool.getConnection();
		ArrayList<Topic> list = new ArrayList<Topic>();
		String sql_query="select * from topic where memeber like '%"+id+"%'";
		System.out.println(sql_query);
		try {
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql_query);
			while(rs.next()){
				Topic topic = new Topic(rs.getString("userid"),rs.getString("title"),
				rs.getString("note"),rs.getString("conclusion"),rs.getString("date"),
				rs.getString("site"),rs.getString("member"));
				list.add(topic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
