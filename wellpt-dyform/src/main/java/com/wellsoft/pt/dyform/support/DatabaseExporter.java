/*
 * @(#)2013-2-8 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dyform.support;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.internal.CoreMessageLogger;
import org.hibernate.tool.hbm2ddl.ConnectionHelper;
import org.jboss.logging.Logger;

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
class DatabaseExporter implements Exporter {
	private static final CoreMessageLogger LOG = Logger.getMessageLogger(CoreMessageLogger.class,
			DatabaseExporter.class.getName());

	private final ConnectionHelper connectionHelper;
	private final SqlExceptionHelper sqlExceptionHelper;

	private final Connection connection;
	private final Statement statement;

	public DatabaseExporter(ConnectionHelper connectionHelper, SqlExceptionHelper sqlExceptionHelper)
			throws SQLException {
		this.connectionHelper = connectionHelper;
		this.sqlExceptionHelper = sqlExceptionHelper;

		connectionHelper.prepare(true);
		connection = connectionHelper.getConnection();
		statement = connection.createStatement();
	}

	@Override
	public boolean acceptsImportScripts() {
		return true;
	}

	@Override
	public void export(String string) throws Exception {
		statement.executeUpdate(string);
		try {
			SQLWarning warnings = statement.getWarnings();
			if (warnings != null) {
				sqlExceptionHelper.logAndClearWarnings(connection);
			}
		} catch (SQLException e) {
			LOG.unableToLogSqlWarnings(e);
		}
	}

	@Override
	public void release() throws Exception {
		try {
			statement.close();
		} finally {
			connectionHelper.release();
		}
	}
}
