package com.note.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.note.domain.Note;
import com.note.domain.Topic;
import com.note.util.DBPool;

public class NoteDAO {
    Statement stmt=null;
    ResultSet rs=null;
    DBPool dbpool = new DBPool();
    
    public boolean deleteNoteByTopicId(String topicid) {
    	boolean flag=false;
    	String sql_delete="delete from note where topicid='"+topicid+"'";
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
    public boolean deleteNoteByNoteId(String noteid) {
    	boolean flag=false;
    	String sql_delete="delete from note where id='"+noteid+"'";
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
	public boolean addNote(Note note) {
		Connection conn  = dbpool.getConnection();
		boolean flag=false;
		String sql_insert="insert into note (title,note,conclusion,userid,site,date,member,topicid) values('"+
		note.getTitle()+"','"+note.getNote()+"','"+note.getConclusion()+"','"
		+note.getUserid()+"','"+note.getSite()+"','"+note.getDate()+"','"+
		note.getMember()+"','"+note.getTopicid()+"')";
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
	public boolean updateMember(String member,String noteid){
		Connection conn  = dbpool.getConnection();
		boolean flag=false;
		try{
			String update_str="update note set member='"+
					member+"' where id='"+noteid+"';";
			System.out.println(update_str);
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
	public boolean updateConclusion(String conclusion,String noteid){
		Connection conn  = dbpool.getConnection();
		boolean flag=false;
		try{
			String update_str="update note set conclusion='"+
			conclusion+"' where id='"+noteid+"';";
			System.out.println(update_str);
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
	public ArrayList<Note> queryNoteByNoteId(String noteid) {
		Connection conn = dbpool.getConnection();
		ArrayList<Note> list = null;
		boolean f=false;
		String sql_query="select * from note where id='"+noteid+"'";
		System.out.println(sql_query);
		try {
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql_query);
			ArrayList<Note> list1 = new ArrayList<Note>();
			while(rs.next()){
				f=true;
				Note note = new Note(rs.getString("userid"),rs.getString("title"),
				rs.getString("note"),rs.getString("conclusion"),rs.getString("date"),
				rs.getString("site"),rs.getString("member"),rs.getString("id"),rs.getString("topicid"));
				list1.add(note);
			}
			if(f==true) list=list1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<Note> queryNoteByTopicId(String topicid) {
		Connection conn = dbpool.getConnection();
		ArrayList<Note> list = null;
		boolean f=false;
		String sql_query="select * from note where topicid='"+topicid+"'";
		System.out.println(sql_query);
		try {
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql_query);
			ArrayList<Note> list1 = new ArrayList<Note>();
			while(rs.next()){
				f=true;
				Note note = new Note(rs.getString("userid"),rs.getString("title"),
				rs.getString("note"),rs.getString("conclusion"),rs.getString("date"),
				rs.getString("site"),rs.getString("member"),rs.getString("id"),rs.getString("topicid"));
				list1.add(note);
			}
			if(f==true) list=list1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
