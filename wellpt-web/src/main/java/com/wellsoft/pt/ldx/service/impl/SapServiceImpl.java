package com.wellsoft.pt.ldx.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.basicdata.datasource.entity.DataSourceColumn;
import com.wellsoft.pt.basicdata.sap.config.SAPDbConfig;
import com.wellsoft.pt.core.dao.UniversalDao;
import com.wellsoft.pt.core.service.impl.BaseServiceImpl;
import com.wellsoft.pt.core.support.NativeQueryItemResultTransformer;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.core.support.QueryItemResultTransformer;
import com.wellsoft.pt.ldx.service.ISapService;
import com.wellsoft.pt.org.entity.User;

@Service
@Transactional
public class SapServiceImpl extends BaseServiceImpl implements ISapService {
	protected UniversalDao sapDao;
	@Resource(name="sapConnectConfig")
	private SAPDbConfig sapConfig;
	@Autowired
	private UniversalDao universalDao;

	public SapServiceImpl() {
		sapDao = this.getDao("sapSessionFactory");
	}
	
	@Override
	public User getCurrentUser(String userId) {
		return universalDao.findUniqueBy(User.class,"id",userId);
	}

	@Override
	public void execSql(String sql) {
		sapDao.getSession().createSQLQuery(sql).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findListBySql(String sql) {
		return sapDao.getSession().createSQLQuery(sql).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QueryItem> queryForItemBySql(
			Set<DataSourceColumn> viewColumns, String querySql,
			Map<String, Object> queryParams, String orderby,
			PagingInfo pagingInfo, String clientString, Boolean isDistinct) {
		StringBuffer sql = new StringBuffer("select ");
		if (isDistinct) {
			sql.append("distinct ");
		}
		boolean start = true;
		for (DataSourceColumn col : viewColumns) {
			if (!start) {
				sql.append(",");
			} else {
				start = false;
			}
			sql.append(col.getFieldName()).append(" as ")
					.append(col.getColumnAliase());
		}
		sql.append(" from ").append(querySql);
//		for (String key : queryParams.keySet()) {
//			sql.append(" and " + key + "=:" + key);
//		}
		if (StringUtils.isNotBlank(clientString)) {
			sql.append(" and ").append(clientString).append("='")
					.append(getClient()).append("' ");
		}
		if (StringUtils.isNotBlank(orderby)) {
			sql.append(" order by ").append(orderby);
		}
		SQLQuery localQuery = this.sapDao.getSession().createSQLQuery(
				sql.toString());
		localQuery.setProperties(queryParams);
		if (pagingInfo.getCurrentPage() != -1) {
			localQuery.setFirstResult(pagingInfo.getFirst());
			localQuery.setMaxResults(pagingInfo.getPageSize());
		}
		localQuery.setResultTransformer(NativeQueryItemResultTransformer.INSTANCE);
		List<QueryItem> localList = localQuery.list();
		if (pagingInfo.isAutoCount()) {
			// localQuery = this.sapDao.getSession().createSQLQuery(
			// new StringBuilder().append("select count(1) from (")
			// .append(sql).append(")").toString());

			long l = countSqlResult(sql, queryParams, isDistinct);
			pagingInfo.setTotalCount(l);
		}
		List<QueryItem> items = new ArrayList<QueryItem>();
		if (null != localList && localList.size() > 0) {
			for (QueryItem item : localList) {
				QueryItem itemres = new QueryItem();
				for (String key : item.keySet()) {
					itemres.put(key.toLowerCase(), item.get(key));
				}
				items.add(itemres);
			}
		}
		return items;
	}

	private long countSqlResult(StringBuffer s,
			Map<String, Object> queryParams, Boolean isDistinct) {
		String s1 = prepareCountSql(s.toString(), isDistinct);
		try {
			Long long1 = ((BigDecimal) createNativeQuery(s1, queryParams)
					.uniqueResult()).longValue();
			return long1.longValue();
		} catch (Exception exception) {
			throw new RuntimeException((new StringBuilder())
					.append("hql can't be auto count, hql is:").append(s1)
					.toString(), exception);
		}
	}
	
	private long countSqlResult(StringBuffer s,
			Map<String, Object> queryParams) {
		String s1 = prepareCountSql(s.toString());
		try {
			Long long1 = ((BigDecimal) createNativeQuery(s1, queryParams)
					.uniqueResult()).longValue();
			return long1.longValue();
		} catch (Exception exception) {
			throw new RuntimeException((new StringBuilder())
					.append("hql can't be auto count, hql is:").append(s1)
					.toString(), exception);
		}
	}

	@SuppressWarnings("rawtypes")
	public SQLQuery createNativeQuery(String s, Map map) {
		SQLQuery query1 = this.sapDao.getSession().createSQLQuery(s);
		if (map != null)
			query1.setProperties(map);
		return query1;
	}

	private String prepareCountSql(String s, Boolean isDistinct) {
		String s1 = s, s2 = "";
		s1 = (new StringBuilder()).append("from ")
				.append(StringUtils.substringAfter(s1, "from")).toString();
		s1 = StringUtils.substringBefore(s1, "order by");
		if (isDistinct) {
			String s3 = StringUtils.substringAfter(s, "distinct");
			String s4 = StringUtils.substringBefore(s3, "from");
			String s5 = s4.split(",")[0].trim();
			String s6 = s5.split("as")[0].trim();
			s2 = (new StringBuilder())
					.append("select count(distinct " + s6 + ") ").append(s1)
					.toString();
		} else {
			s2 = (new StringBuilder()).append("select count(*) ").append(s1)
					.toString();
		}
		return s2;
	}
	
	private String prepareCountSql(String s) {
		String count="select count(1) from ("+s+")";
		return count;
	}

	@Override
	public List<QueryItem> queryQueryItemData(String hql,
			Map<String, Object> values, PagingInfo pagingInfo) {
		return this.sapDao.query(hql, values, QueryItem.class, pagingInfo);
	}

	public String getClient() {
		return sapConfig.getClient();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List findByHql(String hql) {
		return sapDao.getSession().createQuery(hql).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QueryItem> queryItemBySql(Set<DataSourceColumn> dataSourceColumn, String querySql,
			Map<String, Object> queryParams, String orderby, PagingInfo pagingInfo, String clientString, boolean isDistinct) {

		StringBuffer sql = new StringBuffer("select ");
		if (isDistinct) {
			sql.append("distinct ");
		}
		boolean start = true;
		for (DataSourceColumn col : dataSourceColumn) {
			if (!start) {
				sql.append(",");
			} else {
				start = false;
			}
			sql.append(col.getFieldName()).append(" as ")
					.append(col.getColumnAliase());
		}
		sql.append(" from ").append(querySql);
//		for (String key : queryParams.keySet()) {
//			sql.append(" and " + key + "=:" + key);
//		}
		if (StringUtils.isNotBlank(clientString)) {
			sql.append(" and ").append(clientString).append("='")
					.append(getClient()).append("' ");
		}
		if (StringUtils.isNotBlank(orderby)) {
			sql.append(" order by ").append(orderby);
		}
		SQLQuery localQuery = this.sapDao.getSession().createSQLQuery(
				sql.toString());
		localQuery.setProperties(queryParams);
		if (pagingInfo.getCurrentPage() != -1) {
			localQuery.setFirstResult(pagingInfo.getFirst());
			localQuery.setMaxResults(pagingInfo.getPageSize());
		}
		localQuery.setResultTransformer(QueryItemResultTransformer.INSTANCE);
		List<QueryItem> localList = localQuery.list();
		if (pagingInfo.isAutoCount()) {
			// localQuery = this.sapDao.getSession().createSQLQuery(
			// new StringBuilder().append("select count(1) from (")
			// .append(sql).append(")").toString());

			long l = countSqlResult(sql, queryParams, isDistinct);
			pagingInfo.setTotalCount(l);
		}
		List<QueryItem> items = new ArrayList<QueryItem>();
		if (null != localList && localList.size() > 0) {
			for (QueryItem item : localList) {
				QueryItem itemres = new QueryItem();
				for (String key : item.keySet()) {
					itemres.put(key.toLowerCase(), item.get(key));
				}
				items.add(itemres);
			}
		}
		return items;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findPageListBySql(String sql, PagingInfo pagingInfo) {
		SQLQuery localQuery = this.sapDao.getSession().createSQLQuery(sql.toString());
		if (pagingInfo.getCurrentPage() != -1) {
			localQuery.setFirstResult(pagingInfo.getFirst());
			localQuery.setMaxResults(pagingInfo.getPageSize());
		}
		if (pagingInfo.isAutoCount()) {
			long l = countSqlResult(new StringBuffer(sql), null);
			pagingInfo.setTotalCount(l);
		}
		return localQuery.list();
	}
}
