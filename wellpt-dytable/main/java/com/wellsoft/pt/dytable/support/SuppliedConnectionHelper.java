/*
 * @(#)2013-2-8 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.support;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.tool.hbm2ddl.ConnectionHelper;

/**
 * Description: 如何描述该类
 *  
 * @author zhulh
 * @date 2013-2-8
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-2-8.1	zhulh		2013-2-8		Create
 * </pre>
 *
 */
class SuppliedConnectionHelper implements ConnectionHelper {
	private Connection connection;
	private boolean toggleAutoCommit;

	public SuppliedConnectionHelper(Connection connection) {
		this.connection = connection;
	}

	public void prepare(boolean needsAutoCommit) throws SQLException {
		toggleAutoCommit = needsAutoCommit && !connection.getAutoCommit();
		if (toggleAutoCommit) {
			try {
				connection.commit();
			} catch (Throwable ignore) {
				// might happen with a managed connection
			}
			connection.setAutoCommit(true);
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void release() throws SQLException {
		new SqlExceptionHelper().logAndClearWarnings(connection);
		if (toggleAutoCommit) {
			connection.setAutoCommit(false);
		}
		connection = null;
	}
}
