/*
 * @(#)2014-2-7 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.workhour.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.NamedThreadLocal;

import com.wellsoft.pt.basicdata.workhour.entity.WorkHour;
import com.wellsoft.pt.basicdata.workhour.service.WorkHourService;
import com.wellsoft.pt.core.context.ApplicationContextHolder;
import com.wellsoft.pt.utils.bean.BeanUtils;

/**
 * Description: 如何描述该类
 *  
 * @author zhulh
 * @date 2014-2-7
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-2-7.1	zhulh		2014-2-7		Create
 * </pre>
 *
 */
public class WorkHourContext {
	private static final ThreadLocal<WorkHourData> workHourDatas = new NamedThreadLocal<WorkHourData>(
			"WorkHour context");

	public static List<WorkHour> getAll() {
		List<WorkHour> workDays = getWorkHourData().getWorkHours();
		return workDays == null ? new ArrayList<WorkHour>(0) : workDays;
	}

	public static List<WorkHour> getMakeups() {
		List<WorkHour> makeups = getWorkHourData().getWorkHourMap().get(WorkHour.TYPE_MAKE_UP);
		return makeups == null ? new ArrayList<WorkHour>(0) : makeups;
	}

	public static List<WorkHour> getFixedHolidays() {
		List<WorkHour> fixedHolidays = getWorkHourData().getWorkHourMap().get(WorkHour.TYPE_FIXED_HOLIDAYS);
		return fixedHolidays == null ? new ArrayList<WorkHour>(0) : fixedHolidays;
	}

	public static List<WorkHour> getSpecialHolidays() {
		List<WorkHour> specialHolidays = getWorkHourData().getWorkHourMap().get(WorkHour.TYPE_SPECIAL_HOLIDAYS);
		return specialHolidays == null ? new ArrayList<WorkHour>(0) : specialHolidays;
	}

	public static List<WorkHour> getWorkDays() {
		List<WorkHour> workDays = getWorkHourData().getWorkHourMap().get(WorkHour.TYPE_WORK_DAY);
		return workDays == null ? new ArrayList<WorkHour>(0) : workDays;
	}

	public static WorkHour getWorkDayByCode(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		List<WorkHour> workHours = getWorkHourData().getWorkHourMap().get(WorkHour.TYPE_WORK_DAY);
		for (WorkHour workHour : workHours) {
			if (code.equals(workHour.getCode())) {
				return workHour;
			}
		}
		return null;
	}

	public static boolean isWorkDayByCode(String code) {
		if (StringUtils.isBlank(code)) {
			return false;
		}
		List<WorkHour> workHours = getWorkHourData().getWorkHourMap().get(WorkHour.TYPE_WORK_DAY);
		for (WorkHour workHour : workHours) {
			if (Boolean.TRUE.equals(workHour.getIsWorkday()) && code.equals(workHour.getCode())) {
				return true;
			}
		}
		return false;
	}

	private static WorkHourData getWorkHourData() {
		WorkHourData workHourData = workHourDatas.get();
		if (workHourData == null) {
			WorkHourService workHourService = ApplicationContextHolder.getBean(WorkHourService.class);
			List<WorkHour> workHours = workHourService.getAll();
			WorkHourData value = new WorkHourData();
			value.setWorkHours(BeanUtils.convertCollection(workHours, WorkHour.class));

			Map<String, List<WorkHour>> workHourMap = new ConcurrentHashMap<String, List<WorkHour>>();
			for (WorkHour workHour : value.getWorkHours()) {
				String type = workHour.getType();
				if (!workHourMap.containsKey(type)) {
					workHourMap.put(type, new ArrayList<WorkHour>());
				}
				workHourMap.get(type).add(workHour);
			}
			value.setWorkHourMap(workHourMap);
			workHourDatas.set(value);
			workHourData = workHourDatas.get();
		}

		return workHourData;
	}
}
