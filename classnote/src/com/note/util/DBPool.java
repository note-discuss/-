package com.note.util;

import java.sql.*;

public class DBPool {
	public DBPool() {
	}

	public Connection getConnection() {

	    /*String url = "jdbc:mysql://localhost:3306/note";
		String username = "root";
		String password = "zhujiaqi";*/

		String url = "jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_zjqclassnote";
		String username = "10xo40lkky";
		String password = "2izxi3z3k3wjl110m1ljl0mkiwz4h5iy013l3m03";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// System.out.println("DBPool");
			return DriverManager.getConnection(url, username, password);// 建立数据库连接

		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		}
		return null;
	}

}
