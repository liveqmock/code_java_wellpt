/*
 * @(#)2012-11-12 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.workhour.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wellsoft.pt.basicdata.workhour.bean.WorkHourBean;
import com.wellsoft.pt.basicdata.workhour.dao.WorkHourDao;
import com.wellsoft.pt.basicdata.workhour.entity.WorkHour;
import com.wellsoft.pt.basicdata.workhour.enums.WorkUnit;
import com.wellsoft.pt.basicdata.workhour.service.WorkHourService;
import com.wellsoft.pt.basicdata.workhour.support.WorkHourUtils;
import com.wellsoft.pt.basicdata.workhour.support.WorkPeriod;
import com.wellsoft.pt.basicdata.workhour.support.WorkingHour;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryData;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo;
import com.wellsoft.pt.core.cache.CacheName;
import com.wellsoft.pt.utils.bean.BeanUtils;

/**
 * Description: 如何描述该类
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
@Service
@Transactional
public class WorkHourServiceImpl implements WorkHourService {

	@Autowired
	private WorkHourDao workHourDao;

	/**
	 * 固定节假日列表查询
	 * 
	 * @param queryInfo
	 * @return
	 */
	@Override
	public JqGridQueryData queryFixedHolidaysList(JqGridQueryInfo queryInfo) {
		List<WorkHour> workHours = workHourDao.findBy("type", "Fixed");
		List<WorkHour> jqUsers = new ArrayList<WorkHour>();
		for (WorkHour workHour : workHours) {
			WorkHour jqWorkHour = new WorkHour();
			BeanUtils.copyProperties(workHour, jqWorkHour);
			jqUsers.add(jqWorkHour);
		}
		JqGridQueryData queryData = new JqGridQueryData();
		queryData.setCurrentPage(queryInfo.getPage());
		queryData.setDataList(jqUsers);
		queryData.setRepeatitems(false);
		return queryData;
	}

	/**
	 * 特殊节假日列表查询
	 * 
	 * @param queryInfo
	 * @return
	 */
	@Override
	public JqGridQueryData querySpecialHolidaysList(JqGridQueryInfo queryInfo) {
		List<WorkHour> workHours = workHourDao.findBy("type", "Special");
		List<WorkHour> jqUsers = new ArrayList<WorkHour>();
		for (WorkHour workHour : workHours) {
			WorkHour jqWorkHour = new WorkHour();
			BeanUtils.copyProperties(workHour, jqWorkHour);
			jqUsers.add(jqWorkHour);
		}
		JqGridQueryData queryData = new JqGridQueryData();
		queryData.setCurrentPage(queryInfo.getPage());
		queryData.setDataList(jqUsers);
		queryData.setRepeatitems(false);
		return queryData;
	}

	/**
	 * 补班日期列表查询
	 * 
	 * @param queryInfo
	 * @return
	 */
	@Override
	public JqGridQueryData queryMakeUpList(JqGridQueryInfo queryInfo) {
		List<WorkHour> workHours = workHourDao.findBy("type", "Makeup");
		List<WorkHour> jqUsers = new ArrayList<WorkHour>();
		for (WorkHour workHour : workHours) {
			WorkHour jqWorkHour = new WorkHour();
			BeanUtils.copyProperties(workHour, jqWorkHour);
			jqUsers.add(jqWorkHour);
		}
		JqGridQueryData queryData = new JqGridQueryData();
		queryData.setCurrentPage(queryInfo.getPage());
		queryData.setDataList(jqUsers);
		queryData.setRepeatitems(false);
		return queryData;
	}

	/**
	 * 保存工作时间
	 * 
	 * @return
	 */
	@RequestMapping(value = "/save")
	@CacheEvict(value = CacheName.DEFAULT, allEntries = true)
	public WorkHour saveBean(WorkHourBean bean) {
		// 删除固定节假日
		Set<WorkHourBean> deletedFixedHolidays = bean.getDeletedFixedHolidays();
		for (WorkHourBean deletedFixedHoliday : deletedFixedHolidays) {
			WorkHour workHour1 = this.workHourDao.get(deletedFixedHoliday.getUuid());
			workHourDao.delete(workHour1);
		}
		// 删除特殊节假日
		Set<WorkHourBean> deletedSpecialHolidays = bean.getDeletedSpecialHolidays();
		for (WorkHourBean deletedSpecialHoliday : deletedSpecialHolidays) {
			WorkHour workHour2 = this.workHourDao.get(deletedSpecialHoliday.getUuid());
			workHourDao.delete(workHour2);
		}
		// 删除补班日期
		Set<WorkHourBean> deletedMakeups = bean.getDeletedMakeups();
		for (WorkHourBean deletedMakeup : deletedMakeups) {
			WorkHour workHour3 = this.workHourDao.get(deletedMakeup.getUuid());
			workHourDao.delete(workHour3);
		}

		// 保存固定节假日
		Set<WorkHourBean> fixedHolidays = bean.getChangedFixedHolidays();
		for (WorkHourBean fixedHoliday : fixedHolidays) {
			WorkHour workHour1 = new WorkHour();
			if (StringUtils.isBlank(fixedHoliday.getUuid())) {
				fixedHoliday.setUuid(null);
			} else {
				workHour1 = this.workHourDao.get(fixedHoliday.getUuid());
			}
			BeanUtils.copyProperties(fixedHoliday, workHour1);
			workHour1.setType(WorkHour.TYPE_FIXED_HOLIDAYS);
			workHour1.setCode(WorkHour.TYPE_FIXED_HOLIDAYS);
			workHour1.setIsWorkday(Boolean.FALSE);

			this.workHourDao.save(workHour1);
		}
		// 保存特殊节假日
		Set<WorkHourBean> specialHolidays = bean.getChangedSpecialHolidays();
		for (WorkHourBean specialHoliday : specialHolidays) {
			WorkHour workHour2 = new WorkHour();
			if (StringUtils.isBlank(specialHoliday.getUuid())) {
				specialHoliday.setUuid(null);
			} else {
				workHour2 = this.workHourDao.get(specialHoliday.getUuid());
			}
			BeanUtils.copyProperties(specialHoliday, workHour2);
			workHour2.setType(WorkHour.TYPE_SPECIAL_HOLIDAYS);
			workHour2.setCode(WorkHour.TYPE_SPECIAL_HOLIDAYS);
			workHour2.setIsWorkday(Boolean.FALSE);
			this.workHourDao.save(workHour2);
		}
		// 保存补班日期
		Set<WorkHourBean> makeups = bean.getChangedMakeups();
		for (WorkHourBean makeup : makeups) {
			WorkHour workHour3 = new WorkHour();
			if (StringUtils.isBlank(makeup.getUuid())) {
				makeup.setUuid(null);
			} else {
				workHour3 = this.workHourDao.get(makeup.getUuid());
			}
			BeanUtils.copyProperties(makeup, workHour3);
			workHour3.setType(WorkHour.TYPE_MAKE_UP);
			workHour3.setCode(WorkHour.TYPE_MAKE_UP);
			workHour3.setIsWorkday(Boolean.FALSE);
			this.workHourDao.save(workHour3);
		}
		return null;
	}

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.workhour.service.WorkHourService#getAll()
	 */
	@Override
	public List<WorkHour> getAll() {
		return workHourDao.getAll();
	}

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.workhour.service.WorkHourService#save(com.wellsoft.pt.basicdata.workhour.entity.WorkHour)
	 */
	@Override
	public void save(WorkHour workHour) {
		workHourDao.save(workHour);
	}

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.workhour.service.WorkHourService#removeByPk(java.lang.String)
	 */
	@Override
	@CacheEvict(value = CacheName.DEFAULT, allEntries = true)
	public void removeByPk(String uid) {
		workHourDao.delete(uid);
	}

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.workhour.service.WorkHourService#getWorkingHour(java.util.Date)
	 */
	@Override
	public WorkingHour getWorkingHour(Date date) {
		return WorkHourUtils.getWorkingHour(date);
	}

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.workhour.service.WorkHourService#isWorkHour(java.util.Date)
	 */
	@Override
	public boolean isWorkHour(Date date) {
		return WorkHourUtils.isWorkHour(date);
	}

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.workhour.service.WorkHourService#isWorkDay(java.util.Date)
	 */
	@Override
	public boolean isWorkDay(Date date) {
		return WorkHourUtils.isWorkDay(date);
	}

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.workhour.service.WorkHourService#getWorkDate(java.util.Date, java.lang.Double, java.lang.Integer)
	 */
	@Override
	public Date getWorkDate(Date date, Double amount, WorkUnit workingUnit) {
		return WorkHourUtils.getWorkDate(date, amount, workingUnit);
	}

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.workhour.service.WorkHourService#getWorkPeriod(java.util.Date, java.util.Date)
	 */
	@Override
	public WorkPeriod getWorkPeriod(Date fromTime, Date toTime) {
		return WorkHourUtils.getWorkPeriod(fromTime, toTime);
	}

	@Override
	public WorkHour findUniqueBy(String string, String chedkedVal) {
		return workHourDao.findUniqueBy("code", chedkedVal);
	}

}
