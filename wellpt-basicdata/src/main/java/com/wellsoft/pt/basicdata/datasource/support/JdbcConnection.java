/*
 * @(#)2014-8-14 V1.0
 * 
 * Copyright 2014 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.datasource.support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Description: 如何描述该类
 *  
 * @author wubin
 * @date 2014-8-14
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-8-14.1	wubin		2014-8-14		Create
 * </pre>
 *
 */
public class JdbcConnection {

	private Connection connection = null;

	/**
	  * 3
	  *通过properties配置文件的方式灵活配置连接参数，properties中的属性名固化
	  * @return  数据库连接
	  */
	public Connection openConnection() {

		String url = "";
		String user = "";
		String password = "";
		Properties props = new Properties();
		try {
			props.load(this.getClass().getClassLoader().getResourceAsStream("system.properties"));
			url = props.getProperty("multi.tenancy.tenant.url");
			user = props.getProperty("multi.tenancy.tenant.username");
			password = props.getProperty("multi.tenancy.tenant.password");

			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(url, user, password);
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 
	 * 创建连接
	 * 
	 * @return
	 */
	public Connection createConnection(String databaseType, String userName, String passWord, String url) {
		try {
			if (databaseType.equals("1")) {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} else if (databaseType.equals("2")) {

			} else if (databaseType.equals("3")) {
				Class.forName("com.mysql.jdbc.Driver");
			}
			connection = DriverManager.getConnection(url, userName, passWord);
			return connection;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	  * 释放连接
	  */
	public void releaseConnection() {
		if (connection != null)
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
