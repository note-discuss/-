package com.note.util;

import java.sql.*;

public class DBPool {
	public DBPool() {
	}

	public Connection getConnection() {

		String url = "jdbc:mysql://localhost:3306/note";
		String username = "root";
		String password = "zhujiaqi";

		/*String url = "w.rdc.sae.sina.com.cn:3307/app_classnote";
		String username = "jyxl24x1n0";
		String password = "43z4l33yhymz22z0jy120ljx2l2jj524wj2mz053";*/
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// System.out.println("DBPool");
			return DriverManager.getConnection(url, username, password);// 建立数据库连接

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
