package com.note.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.note.domain.Topic;
import com.note.domain.User;
import com.note.util.DBPool;


public class UserDAO {
    Statement stmt=null;
    ResultSet rs=null;
    DBPool dbpool = new DBPool();
    
	public boolean addUser(User user) {
		Connection conn  = dbpool.getConnection();
		if(conn==null) System.out.println("in addUser conn == null!");
		boolean flag=false;
		String sql_insert="insert into user (id,username,password,sex,role) values('"+
		user.getId()+"','"+user.getUsername()+"','"+user.getPassword()+"','"
		+user.getSex()+"','"+user.getRole()+"')";
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
	public boolean queryUser(String id) {
		Connection conn = dbpool.getConnection();
		String sql_query="select * from user where id='"+id+"'";
		try {
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql_query);
			while(rs.next()){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public User getUser(String id) {
		Connection conn = dbpool.getConnection();
		String sql_query="select * from user where id='"+id+"'";
		User user = null;
		try {
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql_query);
			while(rs.next()){
				user = new User(rs.getString("id"), rs.getString("username"), rs.getString("password"),
						rs.getString("role"), rs.getString("sex"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	public boolean login(String id,String password) {
		Connection conn = dbpool.getConnection();
		String sql_query="select * from user where id='"+id+"' and password='"+password+"'";
		try {
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql_query);
			while(rs.next()){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public ArrayList<String> findName(String s) {
		Connection conn = dbpool.getConnection();
		ArrayList<String> list = new ArrayList<String>();
		String sql_query="select * from user where username like '"+s+"%'";
		System.out.println(sql_query);
		try {
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql_query);
			while(rs.next()){
				String tmp=rs.getString("username")+"("+rs.getString("id")+")";
				list.add(tmp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
