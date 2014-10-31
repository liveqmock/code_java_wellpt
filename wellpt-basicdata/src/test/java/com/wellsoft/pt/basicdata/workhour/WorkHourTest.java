/*
 * @(#)2012-11-12 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.workhour;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import com.wellsoft.pt.basicdata.workhour.dao.WorkHourDao;
import com.wellsoft.pt.basicdata.workhour.entity.WorkHour;
import com.wellsoft.pt.basicdata.workhour.enums.WorkUnit;
import com.wellsoft.pt.basicdata.workhour.service.WorkHourService;
import com.wellsoft.pt.basicdata.workhour.support.WorkPeriod;
import com.wellsoft.pt.basicdata.workhour.support.WorkingHour;
import com.wellsoft.pt.core.test.spring.SpringTxTestCase;

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
@ContextConfiguration(locations = { "/applicationContext-cache.xml", "/applicationContext-core.xml" })
public class WorkHourTest extends SpringTxTestCase {

	@Autowired
	private WorkHourDao workHourDao;

	@Autowired
	private WorkHourService workHourService;

	@Test
	@Rollback(false)
	public void testSaveWorkHour() {
		//清空数据
		List<WorkHour> workHours = workHourDao.getAll();
		for (WorkHour workHour : workHours) {
			workHourDao.delete(workHour);
		}

		workHours.clear();
		//工作日
		String[] names = new String[] { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" };
		String[] codes = new String[] { "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN" };
		for (int i = 0; i < 7; i++) {
			WorkHour workHour = new WorkHour();
			workHour.setType(WorkHour.TYPE_WORK_DAY);
			workHour.setName(names[i]);
			workHour.setCode(codes[i]);
			if (i != 5 && i != 6) {
				workHour.setIsWorkday(Boolean.TRUE);
			} else {
				workHour.setIsWorkday(Boolean.FALSE);
			}
			workHour.setFromTime1("08:30");
			workHour.setToTime1("12:00");
			workHour.setFromTime2("13:30");
			workHour.setToTime2("18:00");
			workHours.add(workHour);
		}
		//固定节假日
		WorkHour workHour = new WorkHour();
		workHour.setType(WorkHour.TYPE_FIXED_HOLIDAYS);
		workHour.setName("元旦");
		workHour.setCode(WorkHour.TYPE_FIXED_HOLIDAYS);
		workHour.setIsWorkday(Boolean.FALSE);
		workHour.setFromDate("01-01");
		workHour.setToDate("01-03");
		workHours.add(workHour);

		workHour = new WorkHour();
		workHour.setType(WorkHour.TYPE_FIXED_HOLIDAYS);
		workHour.setName("五一");
		workHour.setCode(WorkHour.TYPE_FIXED_HOLIDAYS);
		workHour.setIsWorkday(Boolean.FALSE);
		workHour.setFromDate("05-01");
		workHour.setToDate("05-03");
		workHours.add(workHour);

		workHour = new WorkHour();
		workHour.setType(WorkHour.TYPE_FIXED_HOLIDAYS);
		workHour.setName("国庆");
		workHour.setCode(WorkHour.TYPE_FIXED_HOLIDAYS);
		workHour.setIsWorkday(Boolean.FALSE);
		workHour.setFromDate("10-01");
		workHour.setToDate("10-07");
		workHours.add(workHour);
		//特殊节假日
		workHour = new WorkHour();
		workHour.setType(WorkHour.TYPE_SPECIAL_HOLIDAYS);
		workHour.setName("中秋");
		workHour.setCode(WorkHour.TYPE_SPECIAL_HOLIDAYS);
		workHour.setIsWorkday(Boolean.FALSE);
		workHour.setFromDate("2012-09-19");
		workHour.setToDate("2012-09-19");
		workHours.add(workHour);

		//补班日期
		workHour = new WorkHour();
		workHour.setType(WorkHour.TYPE_MAKE_UP);
		workHour.setName("补班");
		workHour.setCode(WorkHour.TYPE_MAKE_UP);
		workHour.setIsWorkday(Boolean.FALSE);
		workHour.setFromDate("2012-11-17");
		workHour.setToDate("2012-11-18");
		//workHours.add(workHour);

		//保存
		for (WorkHour entity : workHours) {
			workHourDao.save(entity);
		}
	}

	@Test
	@Rollback(false)
	public void testIsWorkDay() throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dateFormat.parse("2012-11-18");
		Boolean result = workHourService.isWorkDay(date);
		System.out.println(result);
	}

	@Test
	@Rollback(false)
	public void testIsWorkHour() throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dateFormat.parse("2012-11-18");
		Boolean result = null;
		for (int i = 0; i < 100; i++) {
			result = workHourService.isWorkHour(new Date());
			result = workHourService.isWorkHour(new Date());
		}
		System.out.println(result);
	}

	@Test
	@Rollback(false)
	public void testGetWorkingHour() throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dateFormat.parse("2012-11-18");
		WorkingHour result = workHourService.getWorkingHour(new Date());
		System.out.println(result);
	}

	@Test
	@Rollback(false)
	public void testGetWorkDateByDay() throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = new Date();
		Date date2 = dateFormat.parse("2012-11-14 18:00:00");
		Date result = workHourService.getWorkDate(date1, 0.000001d, WorkUnit.WorkingDay);
		System.out.println(dateFormat.format(result));
		System.out.println(result);
	}

	@Test
	@Rollback(false)
	public void testGetWorkDateByHour() throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = new Date();
		Date date2 = dateFormat.parse("2013-04-27 21:36:00");
		Date result = workHourService.getWorkDate(date2, 0.000001d, WorkUnit.WorkingHour);
		System.out.println(dateFormat.format(result));
		System.out.println(result);
	}

	@Test
	@Rollback(false)
	public void testGetWorkPeriod() throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = dateFormat.parse("2012-11-13 18:30:00");
		Date date2 = dateFormat.parse("2012-11-13 19:00:00");
		WorkPeriod result = workHourService.getWorkPeriod(date1, date2);
		System.out.println(result);
	}
}
