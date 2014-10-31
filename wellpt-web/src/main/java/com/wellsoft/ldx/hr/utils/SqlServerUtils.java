package com.wellsoft.ldx.hr.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SqlServerUtils {

	
	public static Connection getConnection(){
		Statement sql = null;
		ResultSet rs = null;
		Connection dbConn = null;  
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";   //加载JDBC驱动  
		String dbURL = "jdbc:sqlserver://192.168.0.56:1433; DatabaseName=ldx_20140905";   //连接服务器和数据库sample  
		String userName = "sa";   //默认用户名  
		String userPwd = "localpsd";   //密码  
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