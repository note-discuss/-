package com.note.util;

import java.sql.*;

public class DBPool {
	public DBPool() {
	}

	public Connection getConnection() {

	    String url = "jdbc:mysql://localhost:3306/note";
		String username = "root";
		String password = "zhujiaqi";

		/*String url = "jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_serverproject";
		String username = "2lj1o3mz5y";
		String password = "kikjjy040j2x2zxzl315jml5wjwmxx1m42j3ik24";*/
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
