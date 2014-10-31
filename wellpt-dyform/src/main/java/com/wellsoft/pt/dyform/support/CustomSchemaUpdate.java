/*
 * @(#)2013-2-8 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dyform.support;

import java.io.FileInputStream;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.internal.StandardServiceRegistryImpl;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.NamingStrategy;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.hibernate.engine.jdbc.internal.Formatter;
import org.hibernate.engine.jdbc.spi.JdbcServices;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.engine.jdbc.spi.SqlStatementLogger;
import org.hibernate.internal.CoreMessageLogger;
import org.hibernate.internal.util.ReflectHelper;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.ConnectionHelper;
import org.hibernate.tool.hbm2ddl.DatabaseMetadata;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.hibernate.tool.hbm2ddl.SchemaUpdateScript;
import org.hibernate.tool.hbm2ddl.Target;
import org.jboss.logging.Logger;

import com.wellsoft.pt.core.resource.Config;
import com.wellsoft.pt.dyform.support.exception.hibernate.HibernateDdlException;

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
public class CustomSchemaUpdate {
	private static final CoreMessageLogger LOG = Logger.getMessageLogger(CoreMessageLogger.class,
			SchemaUpdate.class.getName());

	private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(CustomSchemaUpdate.class);

	private final Configuration configuration;
	private final ConnectionHelper connectionHelper;
	private final SqlStatementLogger sqlStatementLogger;
	private final SqlExceptionHelper sqlExceptionHelper;
	private final Dialect dialect;

	private final List<Exception> exceptions = new ArrayList<Exception>();

	private Formatter formatter;

	private boolean haltOnError;
	private boolean format = true;
	private String outputFile;
	private String delimiter;

	public CustomSchemaUpdate(Configuration cfg) throws HibernateException {
		this(cfg, cfg.getProperties());
	}

	public CustomSchemaUpdate(Configuration configuration, Properties properties) throws HibernateException {
		this.configuration = configuration;
		this.dialect = Dialect.getDialect(properties);

		Properties props = new Properties();
		props.putAll(dialect.getDefaultProperties());
		props.putAll(properties);
		this.connectionHelper = new ManagedProviderConnectionHelper(props);

		this.sqlExceptionHelper = new SqlExceptionHelper();
		this.sqlStatementLogger = new SqlStatementLogger(false, true);
		this.formatter = FormatStyle.DDL.getFormatter();
	}

	public CustomSchemaUpdate(ServiceRegistry serviceRegistry, Configuration cfg) throws HibernateException {
		this.configuration = cfg;

		final JdbcServices jdbcServices = serviceRegistry.getService(JdbcServices.class);
		this.dialect = jdbcServices.getDialect();
		if (Config.MULTI_TENANCY) {
			this.connectionHelper = new MultiTenantConnectionProviderConnectionHelper();
		} else {
			this.connectionHelper = new SuppliedConnectionProviderConnectionHelper(
					serviceRegistry.getService(ConnectionProvider.class));
		}

		this.sqlExceptionHelper = new SqlExceptionHelper();
		this.sqlStatementLogger = jdbcServices.getSqlStatementLogger();
		this.formatter = (sqlStatementLogger.isFormat() ? FormatStyle.DDL : FormatStyle.NONE).getFormatter();
	}

	private static StandardServiceRegistryImpl createServiceRegistry(Properties properties) {
		Environment.verifyProperties(properties);
		ConfigurationHelper.resolvePlaceHolders(properties);
		return (StandardServiceRegistryImpl) new StandardServiceRegistryBuilder().applySettings(properties).build();
	}

	public static void main(String[] args) {
		try {
			Configuration cfg = new Configuration();

			boolean script = true;
			// If true then execute db updates, otherwise just generate and display updates
			boolean doUpdate = true;
			String propFile = null;

			for (int i = 0; i < args.length; i++) {
				if (args[i].startsWith("--")) {
					if (args[i].equals("--quiet")) {
						script = false;
					} else if (args[i].startsWith("--properties=")) {
						propFile = args[i].substring(13);
					} else if (args[i].startsWith("--config=")) {
						cfg.configure(args[i].substring(9));
					} else if (args[i].startsWith("--text")) {
						doUpdate = false;
					} else if (args[i].startsWith("--naming=")) {
						cfg.setNamingStrategy((NamingStrategy) ReflectHelper.classForName(args[i].substring(9))
								.newInstance());
					}
				} else {
					cfg.addFile(args[i]);
				}

			}

			if (propFile != null) {
				Properties props = new Properties();
				props.putAll(cfg.getProperties());
				props.load(new FileInputStream(propFile));
				cfg.setProperties(props);
			}

			StandardServiceRegistryImpl serviceRegistry = createServiceRegistry(cfg.getProperties());
			try {
				new SchemaUpdate(serviceRegistry, cfg).execute(script, doUpdate);
			} finally {
				serviceRegistry.destroy();
			}
		} catch (Exception e) {
			LOG.unableToRunSchemaUpdate(e);
			e.printStackTrace();
		}
	}

	/**
	 * Execute the schema updates
	 *
	 * @param script print all DDL to the console
	 */
	public void execute(boolean script, boolean doUpdate) {
		execute(Target.interpret(script, doUpdate));
	}

	public void execute(Target target) {
		LOG.runningHbm2ddlSchemaUpdate();

		Connection connection = null;
		Statement stmt = null;
		Writer outputFileWriter = null;

		exceptions.clear();

		try {
			DatabaseMetadata meta;
			try {
				LOG.fetchingDatabaseMetadata();
				connectionHelper.prepare(true);
				connection = connectionHelper.getConnection();
				meta = new DatabaseMetadata(connection, dialect, configuration);
				stmt = connection.createStatement();
			} catch (SQLException sqle) {
				exceptions.add(sqle);
				LOG.unableToGetDatabaseMetadata(sqle);
				throw new HibernateException(sqle.getMessage());
			}

			LOG.updatingSchema();

			List<SchemaUpdateScript> scripts = configuration.generateSchemaUpdateScriptList(dialect, meta);
			logger.info("======================>begin processing ddl ");
			//ddl无法回滚，所以这里的日志显得非常重要，如果出现ddl失败,只能通过日志把ddl执行过的信息还原
			for (SchemaUpdateScript script : scripts) {
				String formatted = formatter.format(script.getScript());
				try {
					if (delimiter != null) {
						formatted += delimiter;
					}

					if (target.doScript()) {
						System.out.println(formatted);
					}

					if (target.doExport()) {
						LOG.debug(script.getScript());
						logger.info("====>" + formatted);
						stmt.executeUpdate(formatted);
					}

				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
					throw new HibernateDdlException("Error during DDL export,╯□╰我们也不想这样!!请联系技术人员进行还原处理!!!!");
					/*if (!script.isQuiet()) {
						if (haltOnError) {
							throw new JDBCException("Error during DDL export", e);
						}
						exceptions.add(e);
						LOG.unsuccessful(script.getScript());
						LOG.error(e.getMessage());
					}*/
				}
			}
			logger.info("======================>complete processing ddl ");
			LOG.schemaUpdateComplete();

		} catch (HibernateException e) {
			//exceptions.add(e);
			//LOG.unableToCompleteSchemaUpdate(e);
			throw e;
		} finally {

			try {
				if (stmt != null) {
					stmt.close();
				}
				connectionHelper.release();
			} catch (Exception e) {
				exceptions.add(e);
				LOG.unableToCloseConnection(e);
			}
			try {

			} catch (Exception e) {
				exceptions.add(e);
				LOG.unableToCloseConnection(e);
			}
		}
	}

	/**
	 * Returns a List of all Exceptions which occured during the export.
	 *
	 * @return A List containig the Exceptions occured during the export
	 */
	public List getExceptions() {
		return exceptions;
	}

	public void setHaltOnError(boolean haltOnError) {
		this.haltOnError = haltOnError;
	}

	public void setFormat(boolean format) {
		this.formatter = (format ? FormatStyle.DDL : FormatStyle.NONE).getFormatter();
	}

	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
}
