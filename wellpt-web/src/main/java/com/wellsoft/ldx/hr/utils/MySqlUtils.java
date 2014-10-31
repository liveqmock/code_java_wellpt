package com.wellsoft.ldx.hr.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySqlUtils {

	public static final String url = "jdbc:mysql://192.168.0.186/crm";  
	public static final String name = "com.mysql.jdbc.Driver";  
	public static final String user = "read";  
	public static final String password = "leedarson@110";  
	
	public static Connection getConnection(){
		
		Statement sql = null;
		ResultSet rs = null;
		Connection dbConn = null;
		
		try {  
	           Class.forName(name);//指定连接类型  
	           dbConn = DriverManager.getConnection(url, user, password);//获取连接  
	    } catch (Exception e) {  
	           e.printStackTrace();  
	    }  
		return dbConn;
	}
}
