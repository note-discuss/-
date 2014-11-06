package com.note.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.note.domain.Topic;
import com.note.domain.User;
import com.note.util.DBPool;


public class UserDAO {
    Statement stmt=null;
    ResultSet rs=null;
    DBPool dbpool = new DBPool();
    
	public boolean addUser(User user) {
		Connection conn  = dbpool.getConnection();
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
}
