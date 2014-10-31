/*
 * @(#)2012-10-30 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dyform.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;

import com.wellsoft.pt.core.dao.Page;
import com.wellsoft.pt.core.dao.PropertyFilter;
import com.wellsoft.pt.core.dao.hibernate.HibernateDao;
import com.wellsoft.pt.dyform.entity.DyFormDefinition;

/**
 * Description: 对应FormDefinition的Dao操作
 *  
 * @author jiangmb
 * @date 2012-12-21
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-12-21.1	jiangmb		2012-12-21		Create
 * </pre>
 *
 */
@Repository
public class DyFormDefinitionDao extends HibernateDao<DyFormDefinition, String> {

	private static final String QUERY_MAX_VERSION = "select f from DyFormDefinition f where f.name = :name order by version desc";
	private static final String QUERY_MAX_VERSION_LIST = "select * from dyform_form_definition a,"
			+ "(select name, max(version) version from dyform_form_definition group by name) b"
			+ " where a.name = b.name and a.version = b.version";

	public DyFormDefinition getEntitybyUuid(String uuid) {
		List<DyFormDefinition> list = findBy("uuid", uuid);
		return (list.size() > 0 ? list.get(0) : null);
	}

	public List<DyFormDefinition> getShowTable() {
		return this.find("select f from DyFormDefinition f where f.formDisplay = '1'");
	}

	public List<DyFormDefinition> getShowTable2() {
		return this.find("select f from DyFormDefinition f where f.formDisplay = '2'");
	}

	public Long countById(String s) {
		HashMap hashmap = new HashMap();
		hashmap.put("id", s);
		return (Long) findUnique("select count(*) from DyFormDefinition form_def where form_def.outerId = :id", hashmap);
	}

	/**
	 * 表单实体对应的最大版本对象
	 * 
	 * @param tableName
	 * @return
	 */
	public DyFormDefinition getMaxVersion(String tableName) {
		//List<DyFormDefinition> list = this.find(QUERY_MAX_VERSION, tableName);
		HashMap hashmap = new HashMap();
		hashmap.put("name", tableName);
		List<DyFormDefinition> list = this.find(QUERY_MAX_VERSION, hashmap);
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 获取表单定义最大版本列表
	 * 
	 * @return
	 */
	public List<DyFormDefinition> getMaxVersionList() {
		return getSession().createSQLQuery(QUERY_MAX_VERSION_LIST).addEntity(DyFormDefinition.class).list();
	}

	public Page<DyFormDefinition> searchForm(final Page<DyFormDefinition> page, final List<PropertyFilter> filters) {
		Criterion[] srccriterions = buildCriterionByPropertyFilter(filters);
		Criterion[] desccriterion = new Criterion[srccriterions.length + 1];
		desccriterion[0] = Restrictions.isNull("parentForm");
		System.arraycopy(srccriterions, 0, desccriterion, 1, srccriterions.length);
		return findPage(page, desccriterion);
	}

	/**
	 * 保存表单定义信息
	 * 
	 * @param entity
	 * @param bodyContent 用户html模板部分
	 */
	@Override
	public void save(DyFormDefinition entity) {
		this.getSession().save(entity);
		System.out.println("--->");
	}

	public List<DyFormDefinition> getFormDefinitionsByTblName(String tblName) {
		return this.find(Restrictions.eq("name", tblName));
	}

	public void dropFormTbl(final String tblName) {
		Session session = this.getSessionFactory().getCurrentSession();
		session.doWork(new Work() {
			@Override
			public void execute(Connection con) throws SQLException {
				con.createStatement().execute("drop table " + tblName);
			}
		});

	}
}
