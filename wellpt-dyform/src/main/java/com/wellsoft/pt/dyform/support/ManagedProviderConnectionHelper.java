/*
 * @(#)2013-2-8 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dyform.support;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.internal.StandardServiceRegistryImpl;
import org.hibernate.cfg.Environment;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.internal.util.config.ConfigurationHelper;
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
class ManagedProviderConnectionHelper implements ConnectionHelper {
	private Properties cfgProperties;
	private StandardServiceRegistryImpl serviceRegistry;
	private Connection connection;

	public ManagedProviderConnectionHelper(Properties cfgProperties) {
		this.cfgProperties = cfgProperties;
	}

	public void prepare(boolean needsAutoCommit) throws SQLException {
		serviceRegistry = createServiceRegistry(cfgProperties);
		connection = serviceRegistry.getService(ConnectionProvider.class).getConnection();
		if (needsAutoCommit && !connection.getAutoCommit()) {
			connection.commit();
			connection.setAutoCommit(true);
		}
	}

	private static StandardServiceRegistryImpl createServiceRegistry(Properties properties) {
		Environment.verifyProperties(properties);
		ConfigurationHelper.resolvePlaceHolders(properties);
		return (StandardServiceRegistryImpl) new StandardServiceRegistryBuilder().applySettings(properties).build();
	}

	public Connection getConnection() throws SQLException {
		return connection;
	}

	public void release() throws SQLException {
		try {
			releaseConnection();
		} finally {
			releaseServiceRegistry();
		}
	}

	private void releaseConnection() throws SQLException {
		if (connection != null) {
			try {
				new SqlExceptionHelper().logAndClearWarnings(connection);
			} finally {
				try {
					serviceRegistry.getService(ConnectionProvider.class).closeConnection(connection);
				} finally {
					connection = null;
				}
			}
		}
	}

	private void releaseServiceRegistry() {
		if (serviceRegistry != null) {
			try {
				serviceRegistry.destroy();
			} finally {
				serviceRegistry = null;
			}
		}
	}
}
