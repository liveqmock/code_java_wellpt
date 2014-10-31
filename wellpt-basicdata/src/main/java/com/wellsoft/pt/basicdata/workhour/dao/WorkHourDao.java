/*
 * @(#)2012-11-12 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.workhour.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.wellsoft.pt.basicdata.workhour.entity.WorkHour;
import com.wellsoft.pt.core.cache.CacheName;
import com.wellsoft.pt.core.dao.hibernate.HibernateDao;

/**
 * Description: 工作时间持久层访问类
 *  
 * @author zhulh
 * @date 2012-11-12
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-11-12.1	zhulh		2012-11-12		Create
 * </pre>
 *
 */
@Repository
public class WorkHourDao extends HibernateDao<WorkHour, String> {

	/** 判断是否为工作日的HQL */
	private static final String IS_WORK_DAY_BY_CODE = "select count(work_hour.uuid) from WorkHour work_hour where work_hour.code = :code and work_hour.isWorkday = :isWorkday";

	/** 根据类型查询的HQL */
	private static final String GET_BY_TYPE = "from WorkHour work_hour where work_hour.type = :type";

	/** 根据类型和代码查询工作日的HQL */
	private static final String GET_BY_TYPE_AND_CODE = "from WorkHour work_hour where work_hour.type = :type and work_hour.code = :code";

	/**
	 * 根据指定编码，判断是否为工作日
	 * 
	 * @param code
	 * @return
	 */
	@Cacheable(value = CacheName.DEFAULT)
	public Boolean isWorkDayByCode(String code) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("code", code);
		values.put("isWorkday", true);
		return (Long) this.findUnique(IS_WORK_DAY_BY_CODE, values) > 0;
	}

	/**
	 * 根据指定类型，获取工作日列表
	 * 
	 * @param type
	 * @return
	 */
	@Cacheable(value = CacheName.DEFAULT)
	public List<WorkHour> getWorkHourByType(String type) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("type", type);
		return this.find(GET_BY_TYPE, values);
	}

	/**
	 * 根据指定类型和代码，获取工作日
	 * 
	 * @param type
	 * @return
	 */
	@Cacheable(value = CacheName.DEFAULT)
	public WorkHour getWorkHourByTypeAndCode(String type, String code) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("type", type);
		values.put("code", code);
		return this.findUnique(GET_BY_TYPE_AND_CODE, values);
	}

}
