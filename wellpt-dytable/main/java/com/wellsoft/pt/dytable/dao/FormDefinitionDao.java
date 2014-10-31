/*
 * @(#)2012-10-30 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.wellsoft.pt.core.dao.Page;
import com.wellsoft.pt.core.dao.PropertyFilter;
import com.wellsoft.pt.core.dao.hibernate.HibernateDao;
import com.wellsoft.pt.dytable.entity.FormDefinition;

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
@Component
public class FormDefinitionDao extends HibernateDao<FormDefinition, String> {
	private static final String QUERY_CHILDREN = "select f from FormDefinition f where f.parentForm.uuid = ?";
	private static final String QUERY_TOPLEVEL_FORM = "select f from FormDefinition f where f.parentForm.uuid is null or f.parentForm.uuid =''";
	private static final String QUERY_MAX_VERSION = "select f from FormDefinition f where f.name = ? order by version desc";
	private static final String QUERY_MAX_VERSION_LIST = "select * from dytable_form_definition a,"
			+ "(select name, max(version) version from dytable_form_definition group by name) b"
			+ " where a.name = b.name and a.version = b.version";

	public FormDefinition getEntitybyUuid(String uuid) {
		List<FormDefinition> list = findBy("uuid", uuid);
		return (list.size() > 0 ? list.get(0) : null);
	}

	public List<FormDefinition> getShowTable() {
		return this.find("select f from FormDefinition f where f.formDisplay = '1'");
	}

	public List<FormDefinition> getShowTable2() {
		return this.find("select f from FormDefinition f where f.formDisplay = '2'");
	}

	public Long countById(String s) {
		HashMap hashmap = new HashMap();
		hashmap.put("id", s);
		return (Long) findUnique("select count(*) from FormDefinition form_def where form_def.id = :id", hashmap);
	}

	public List<FormDefinition> getChildren(String uuid) {
		return this.find(QUERY_CHILDREN, uuid);
	}

	public List<FormDefinition> getTopLevel() {
		return this.find(QUERY_TOPLEVEL_FORM);
	}

	/**
	 * 表单实体对应的最大版本对象
	 * 
	 * @param tableName
	 * @return
	 */
	public FormDefinition getMaxVersion(String tableName) {
		List<FormDefinition> list = this.find(QUERY_MAX_VERSION, tableName);
		return list.get(0);
	}

	/**
	 * 获取表单定义最大版本列表
	 * 
	 * @return
	 */
	public List<FormDefinition> getMaxVersionList() {
		return getSession().createSQLQuery(QUERY_MAX_VERSION_LIST).addEntity(FormDefinition.class).list();
	}

	public Page<FormDefinition> searchForm(final Page<FormDefinition> page, final List<PropertyFilter> filters) {
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
	public void save(FormDefinition entity, String bodyContent) {
		if (StringUtils.isNotEmpty(bodyContent)) {
			entity.setHtmlBodyContent(Hibernate.getLobCreator(getSession()).createClob(bodyContent));
		}
		this.save(entity);
	}
}
