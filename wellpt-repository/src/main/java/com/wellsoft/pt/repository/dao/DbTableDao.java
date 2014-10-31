package com.wellsoft.pt.repository.dao;

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
import org.hibernate.transform.AliasedTupleSubsetResultTransformer;
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
public class DbTableDao extends HibernateDao<SystemTable, String> {
 

	/**
	 * 
	 * 系统表数据查询
	 * (non-Javadoc)
	 * @throws ClassNotFoundException 
	 * @see com.wellsoft.pt.basicdata.systemtable.service.SystemTableService#query(java.lang.String, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	public List<Map<String, Object>> query(String sql ) throws Exception {
		SQLQuery query = getSession().createSQLQuery(sql);
		// query.setResultSetMapping(arg0)
		 
		AliasedTupleSubsetResultTransformer f = new AliasedTupleSubsetResultTransformer() {
			
			@Override
			public boolean isTransformedValueATupleElement(String[] arg0, int arg1) {
			 
				return false;
			}
			
			
			@Override//重写这个方法是关键
			public Object transformTuple(Object[] tuple/*值数组*/, String[] aliases/*字段数组*/) { 
				Map<String, Object> map = new HashMap<String, Object>();
				for(int i = 0; i < aliases.length; i ++){
					
					map.put(aliases[i].toLowerCase(), tuple[i]);
					 
				}
				return map;
			}
		};
		 List<Map<String, Object>> resultList = query.setResultTransformer(f).list();
		return resultList;
	}

	 
}
