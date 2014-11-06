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
		String sql_insert="insert into Topic (title,,note,conclusion,userid,date) values('"+
		topic.getTitle()+"','"+topic.getNote()+"','"+topic.getConclusion()+"','"
		+topic.getUserId()+"','"+topic.getDate()+"')";
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
						+rs.getString("date")+rs.getString("conclusion"));
				topic.setTitle(rs.getString("title"));
				topic.setNote(rs.getString("note"));
				topic.setUserId(rs.getString("userid"));
				topic.setDate(rs.getString("date"));
				topic.setConclusion(rs.getString("conclusion"));
				list.add(topic);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
		
	}
}
