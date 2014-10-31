package com.wellsoft.ldx.hr.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SqlServerUtils2 {

	public static Connection getConnection(){
		Statement sql = null;
		ResultSet rs = null;
		Connection dbConn = null;  
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";   //加载JDBC驱动  
		String dbURL = "jdbc:sqlserver://192.168.0.133:1433;databasename=dataportal_prod";   //连接服务器和数据库sample  
		String userName = "sa";   //默认用户名  
		String userPwd = "123456";   //密码  
		try {
			Class.forName(driverName);
			dbConn = DriverManager.getConnection(dbURL, userName, userPwd); 
			sql=dbConn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbConn;
	}
}
