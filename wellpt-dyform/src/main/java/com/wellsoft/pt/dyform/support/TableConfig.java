package com.wellsoft.pt.dyform.support;

import java.lang.reflect.Field;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.spi.JdbcServices;
import org.hibernate.engine.jdbc.spi.SqlStatementLogger;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.internal.SessionFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wellsoft.pt.core.context.ApplicationContextHolder;
import com.wellsoft.pt.core.resource.Config;

public class TableConfig {
	private static final Logger log = LoggerFactory.getLogger(TableConfig.class);

	private Configuration config;
	private Dialect dialect;
	private SqlStatementLogger sqlStatementLogger;

	private Mapping mapping;
	//这里利用hbmdll来简化部分处理
	//	private SchemaExport export;
	//	private SchemaUpdate update;
	protected SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			sessionFactory = ApplicationContextHolder.getBean(Config.TENANT_SESSION_FACTORY_BEAN_NAME,
					SessionFactory.class);
		}
		return sessionFactory;
	}

	public TableConfig(Configuration cfg) {
		this.config = cfg;
		dialect = ((SessionFactoryImpl) getSessionFactory()).getDialect();
		sqlStatementLogger = ((SessionFactoryImpl) getSessionFactory()).getServiceRegistry()
				.getService(JdbcServices.class).getSqlStatementLogger();
		initMapping();
	}

	/**
	 * 
	* @Title: initMapping
	* @Description:将mapping初始化
	* @param     设定文件
	* @return void    返回类型
	* @throws
	 */
	private void initMapping() {
		Field mappingField;
		try {
			mappingField = config.getClass().getDeclaredField("mapping");
			mappingField.setAccessible(true);
			mapping = (Mapping) (mappingField.get(config));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addTable() {
		//生成数据库表，这里只创建数据表
		CustomSchemaExport customSchemaExport = new CustomSchemaExport(
				((SessionFactoryImpl) getSessionFactory()).getServiceRegistry(), config);
		customSchemaExport.execute(true, true, false, true);
		//		SchemaExport export = new SchemaExport(((SessionFactoryImpl) getSessionFactory()).getServiceRegistry(), config);
		//		export.execute(true, true, false, true);
	}

	/**
	 * @throws SQLException 
	 * 
	* @Title: updateTable
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param     设定文件
	* @return void    返回类型
	* @throws
	 */
	public void updateTable() {
		CustomSchemaUpdate schemaUpdate = new CustomSchemaUpdate(
				((SessionFactoryImpl) getSessionFactory()).getServiceRegistry(), config);
		schemaUpdate.execute(true, true);

	}

	/**
	 * @throws SQLException 
	 * 
	* @Title: updateTable
	* @Description: 删除表
	* @param     设定文件
	* @return void    返回类型
	* @throws
	 */
	public void dropTable() {
		//删除表
		CustomSchemaExport customSchemaExport = new CustomSchemaExport(
				((SessionFactoryImpl) getSessionFactory()).getServiceRegistry(), config);
		customSchemaExport.execute(true, true, true, false);
		//		SchemaExport export = new SchemaExport(((SessionFactoryImpl) getSessionFactory()).getServiceRegistry(), config);
		//		export.execute(true, true, false, true);

	}

}
