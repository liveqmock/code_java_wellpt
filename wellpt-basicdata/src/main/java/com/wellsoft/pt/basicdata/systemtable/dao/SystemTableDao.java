package com.wellsoft.pt.basicdata.systemtable.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.Table;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import com.wellsoft.pt.basicdata.systemtable.entity.SystemTable;
import com.wellsoft.pt.common.enums.Separator;
import com.wellsoft.pt.core.context.ApplicationContextHolder;
import com.wellsoft.pt.core.dao.hibernate.HibernateDao;
import com.wellsoft.pt.core.dao.hibernate.SessionFactoryUtils;
import com.wellsoft.pt.core.support.QueryItemResultTransformer;

/**
 * 
 * Description: 系统表结构数据层访问类
 *  
 * @author zhouyq
 * @date 2013-3-21
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-3-21.1	zhouyq		2013-3-21		Create
 * </pre>
 *
 */
@Repository
public class SystemTableDao extends HibernateDao<SystemTable, String> {

	/**
	 * 
	 * 通过表名获取表所有字段集合
	 * 
	 * @param tableName
	 * @return
	 */
	public List<String> getFieldByTableName(String tableName) {
		List<String> fields = new ArrayList<String>();
		Configuration configuration = getConfiguration();
		//通过Configuration对象得到所有表的集合
		Iterator<Table> tables = configuration.getTableMappings();
		while (tables.hasNext()) {
			Table table = tables.next();
			if (tableName.equals(table.getName())) {
				//迭代表的所有属性
				Iterator<Column> columns = table.getColumnIterator();
				while (columns.hasNext()) {
					Column column = columns.next();
					String fieldName = column.getName();
					fields.add(fieldName);
				}
			}
		}
		return fields;
	}

	public Map<String, String> getFieldsTypeByTable(String tableName) {
		Map<String, String> fieldsTypeMap = new HashMap<String, String>();
		//JDBC连接数据库
		ConnectionProvider connectionProvider = ((SessionFactoryImpl) SessionFactoryUtils
				.getMultiTenantSessionFactory()).getServiceRegistry().getService(ConnectionProvider.class);
		try {
			System.out.println(connectionProvider.getConnection());
			Connection connection = connectionProvider.getConnection();
			PreparedStatement statement = connection.prepareStatement("select * from " + tableName);
			statement.executeQuery();
			ResultSet resultSet = statement.getResultSet();
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			while (resultSet.next()) {
				for (int i = 1; i < resultSetMetaData.getColumnCount(); i++) {
					String columnName = resultSetMetaData.getColumnName(i);//获得列名
					String columnTypeName = resultSetMetaData.getColumnTypeName(i);//获得列的数据类型
					/*int coulumnType = resultSetMetaData.getColumnType(i);
					String columnTypeName = null;
					if (coulumnType == -7) {
						columnTypeName = "BIT";
					} else if (coulumnType == 4) {
						columnTypeName = "INTEGER";
					} else if (coulumnType == 8) {
						columnTypeName = "DOUBLE";
					} else if (coulumnType == 12) {
						columnTypeName = "VARCHAR";
					} else if (coulumnType == 91) {
						columnTypeName = "DATE";
					} else if (coulumnType == -5) {
						columnTypeName = "BIGINT";
					}*/
					//如果已经存在该数据类型就无需再存储
					if (!fieldsTypeMap.containsKey(columnName)) {
						fieldsTypeMap.put(columnName, columnTypeName);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fieldsTypeMap;
	}

	/**
	 * 
	 * 系统表数据查询
	 * (non-Javadoc)
	 * @throws ClassNotFoundException 
	 * @see com.wellsoft.pt.basicdata.systemtable.service.SystemTableService#query(java.lang.String, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	public List<Map<String, Object>> query(String sql, Map<String, Object> selectionArgs, int firstResult,
			int maxResults) throws Exception {
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setProperties(selectionArgs);
		//对查询出来的结果进行分页，设置每页起始行数，最大行数，将结果设为map集合
		List<Map<String, Object>> fieldsResultList = query.setFirstResult(firstResult).setMaxResults(maxResults)
				.setResultTransformer(QueryItemResultTransformer.INSTANCE).list();
		return fieldsResultList;
	}

	/** 
		*通过实体类和属性，获取实体类属性对应的表字段名称 
		*  
		* @param clazz 
		*            实体类 
		* @param propertyName 
		*            属性名称 
		* @return 字段名称 
		*/

	@SuppressWarnings("unchecked")
	public static Map<String, String> getColumnMap(Class clazz, String propertyName) {
		Map<String, String> map = new HashMap<String, String>();
		String fieldName = null;
		String type = "";
		if (!StringUtils.isEmpty(propertyName)) {
			try {
				PersistentClass persistentClass = getPersistentClass(clazz);
				Property property = persistentClass.getProperty(propertyName);
				Iterator it = property.getColumnIterator();
				type = property.getValue().getType().getName();
				if (it.hasNext()) {
					Column column = (Column) it.next();
					fieldName = column.getName();
				}
			} catch (Exception e) {
				return null;
			}
		}
		map.put("name", fieldName);
		if (type.equals("integer")) {
			type = "INTEGER";
		} else if (type.equals("timestamp")) {
			type = "DATE";
		} else if (type.equals("double")) {
			type = "DOUBLE";
		} else if (type.equals("boolean")) {
			type = "BOOLEAN";
		} else if (type.equals("long")) {
			type = "LONG";
		} else if (type.equals("clob")) {
			type = "CLOB";
		} else if (type.equals("string")) {
			type = "STRING";
		} else if (type.indexOf("java.util.Set") > -1) {
			type = "SET";
		} else if (type.indexOf("java.util.Collection") > -1) {
			type = "LIST";
		} else {
			type = "";
		}
		map.put("type", type);
		return map;
	}

	@SuppressWarnings("unchecked")
	private static PersistentClass getPersistentClass(Class clazz) {
		PersistentClass pc = getConfiguration().getClassMapping(clazz.getName());
		if (pc == null) {
			Configuration configuration = getConfiguration();
			configuration = getConfiguration().addClass(clazz);
			pc = getConfiguration().getClassMapping(clazz.getName());
		}
		return pc;
	}

	/**
	 * 
	 * 获得Configuration对象
	 * 
	 * @return
	 */
	public static Configuration getConfiguration() {
		//获取Configuration对象
		LocalSessionFactoryBean localSessionFactoryBean = (LocalSessionFactoryBean) ApplicationContextHolder
				.getBean(Separator.AMPERSAND.getValue() + SessionFactoryUtils.getTenantSessionFactoryBeanName());
		Configuration configuration = localSessionFactoryBean.getConfiguration();
		return configuration;
	}

	public SystemTable getById(String id) {

		return findUniqueBy("id", id);
	}
}
