package com.note.util;


import java.sql.*;

public class DBPool {
	public DBPool(){}
	public Connection getConnection() {


		String url="jdbc:mysql://localhost:3306/note";
		String username="root";
		String password="zhujiaqi";

		try{
			 Class.forName("com.mysql.jdbc.Driver");
			 //System.out.println("DBPool");
			return DriverManager.getConnection(url,username,password);//建立数据库连接
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	

}
