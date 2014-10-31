/*
 * @(#)2012-11-12 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.workhour.service;

import java.util.Date;
import java.util.List;

import com.wellsoft.pt.basicdata.workhour.entity.WorkHour;
import com.wellsoft.pt.basicdata.workhour.enums.WorkUnit;
import com.wellsoft.pt.basicdata.workhour.support.WorkPeriod;
import com.wellsoft.pt.basicdata.workhour.support.WorkingHour;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryData;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo;

/**
 * Description: 工作时间服务类
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
public interface WorkHourService {

	/**
	 * 获取全部的工作时间配置列表
	 * 
	 * @return
	 */
	List<WorkHour> getAll();

	/**
	 * 保存工作时间配置，若不存在新增，否则更新
	 * 
	 * @param workHour
	 */
	void save(WorkHour workHour);

	/**
	 * 根据主键删除工作时间配置
	 * 
	 * @param uid
	 */
	void removeByPk(String uid);

	/**
	 * 获取指定工作日的信息
	 * 
	 * @param date
	 * @return
	 */
	WorkingHour getWorkingHour(Date date);

	/**
	 * 判断是否为工作时间
	 * 
	 * @param date
	 * @return
	 */
	boolean isWorkHour(Date date);

	/**
	 * 判断是否为工作日
	 * 
	 * @param date
	 * @return
	 */
	boolean isWorkDay(Date date);

	/**
	 * 返回有效的工作时间点
	 * 
	 * @param date	开始时间点
	 * @param amount
	 * @param type	工作日、小时时间单位
	 * @return
	 */
	Date getWorkDate(Date date, Double amount, WorkUnit workingUnit);

	/**
	 * 返回有效工作时间区段(单位秒)以及经历几个工作日
	 * 
	 * @param fromTime
	 * @param toTime
	 * @return
	 */
	WorkPeriod getWorkPeriod(Date fromTime, Date toTime);

	/**
	 * 固定节假日列表查询
	 * 
	 * @param queryInfo
	 * @return
	 */
	JqGridQueryData queryFixedHolidaysList(JqGridQueryInfo queryInfo);

	/**
	 * 特殊节假日列表查询
	 * 
	 * @param queryInfo
	 * @return
	 */
	JqGridQueryData querySpecialHolidaysList(JqGridQueryInfo queryInfo);

	/**
	 * 补班日期列表查询
	 * 
	 * @param queryInfo
	 * @return
	 */
	JqGridQueryData queryMakeUpList(JqGridQueryInfo queryInfo);

	WorkHour findUniqueBy(String string, String chedkedVal);
}
