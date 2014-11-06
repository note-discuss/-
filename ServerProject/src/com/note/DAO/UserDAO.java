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
    
	public boolean addUser(User user) {//添加一个用户
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
	//public User queryUser(String name,String id){
		
//	}
}
