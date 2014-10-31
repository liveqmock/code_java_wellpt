package com.wellsoft.pt.ldx.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.basicdata.dyview.provider.ViewColumn;
import com.wellsoft.pt.core.dao.UniversalDao;
import com.wellsoft.pt.core.service.impl.BaseServiceImpl;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.core.support.QueryItemResultTransformer;
import com.wellsoft.pt.ldx.service.ISrmService;

@Service
@Transactional
public class LmsManageImpl  extends BaseServiceImpl implements ISrmService {
	protected UniversalDao lmsDao;
	
	public LmsManageImpl(){
		lmsDao = this.getDao("lmsSessionFactory");
	}
	
	@Override
	public void execSql(String sql) {
		lmsDao.getSession().createSQLQuery(sql).executeUpdate();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findListBySql(String sql) {
		return lmsDao.getSession().createSQLQuery(sql).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QueryItem> queryForItemBySql(
			Collection<ViewColumn> viewColumns, String querySql,
			Map<String, Object> queryParams, String orderby,
			PagingInfo pagingInfo, Boolean isDistinct) {
		StringBuffer sql = new StringBuffer("select ");
		if (isDistinct) {
			sql.append("distinct ");
		}
		boolean start = true;
		for (ViewColumn col : viewColumns) {
			if (!start) {
				sql.append(",");
			} else {
				start = false;
			}
			sql.append(col.getAttributeName()).append(" as ")
					.append(col.getColumnAlias());
		}
		sql.append(" from ").append(querySql);
		for (String key : queryParams.keySet()) {
			sql.append(" and " + key + "=:" + key);
		}
		if (StringUtils.isNotBlank(orderby)) {
			sql.append(" order by ").append(orderby);
		}
		SQLQuery localQuery = this.lmsDao.getSession().createSQLQuery(
				sql.toString());
		localQuery.setProperties(queryParams);
		if (pagingInfo.getCurrentPage() != -1) {
			localQuery.setFirstResult(pagingInfo.getFirst());
			localQuery.setMaxResults(pagingInfo.getPageSize());
		}
		localQuery.setResultTransformer(QueryItemResultTransformer.INSTANCE);
		List<QueryItem> localList = localQuery.list();
		if (pagingInfo.isAutoCount()) {
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

	@SuppressWarnings("rawtypes")
	public SQLQuery createNativeQuery(String s, Map map) {
		SQLQuery query1 = this.lmsDao.getSession().createSQLQuery(s);
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

	@Override
	public List<QueryItem> queryQueryItemData(String hql,
			Map<String, Object> values, PagingInfo pagingInfo) {
		return this.lmsDao.query(hql, values, QueryItem.class, pagingInfo);
	}
}
