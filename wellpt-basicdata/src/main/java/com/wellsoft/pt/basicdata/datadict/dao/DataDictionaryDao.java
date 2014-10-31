/*
 * @(#)2012-11-15 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.datadict.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wellsoft.pt.basicdata.datadict.entity.DataDictionary;
import com.wellsoft.pt.core.dao.hibernate.HibernateDao;

/**
 * Description: 数据字典数据层访问类
 *  
 * @author zhulh
 * @date 2012-11-15
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-11-15.1	zhulh		2012-11-15		Create
 * </pre>
 *
 */
@Repository
public class DataDictionaryDao extends HibernateDao<DataDictionary, String> {
	/** 根据父节点查询所有子结点 */
	private final static String QUERY_BY_PARENT = "from DataDictionary t where t.parent.uuid = :parentUuid";
	/** 查询所有根结点 */
	private final static String QUERY_BY_TOP_LEVEL = "from DataDictionary t where t.parent.uuid = null or t.parent.uuid = '' order by code asc";

	// 查询字典类型下子结点的指定字典编码的数据字典列表
	private final static String QUERY_BY_TYPE_AND_CODE = "from DataDictionary t where t.parent.type = :type and t.code = :code";

	/**
	 * @param dataDictionary
	 * @return
	 */
	public List<DataDictionary> getByParent(DataDictionary dataDictionary) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("parentUuid", dataDictionary.getUuid());
		return this.find(QUERY_BY_PARENT, values);
	}

	/**
	 * @param dataDictionary
	 * @return
	 */
	public DataDictionary getByCategory(String category) {
		return this.findUnique("category", category);
	}

	/**
	 * 如何描述该方法
	 * 
	 * @param id
	 * @return
	 */
	public DataDictionary getById(String id) {
		return this.findUniqueBy("id", id);
	}

	/**
	 * 查询所有数据字典的根结点
	 * 
	 * @return
	 */
	public List<DataDictionary> getTopLevel() {
		return this.find(QUERY_BY_TOP_LEVEL, new HashMap<String, Object>(0));
	}

	/**
	 * 
	 * @param type
	 * @return
	 */
	public DataDictionary getByType(String type) {
		return this.findUniqueBy("type", type);
	}

	/**
	 * @param type
	 * @param code
	 * @return
	 */
	public List<DataDictionary> getDataDictionaries(String type, String code) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("type", type);
		values.put("code", code);
		return this.find(QUERY_BY_TYPE_AND_CODE, values);
	}

}
