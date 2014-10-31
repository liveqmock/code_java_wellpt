package com.wellsoft.pt.basicdata.workhour.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wellsoft.pt.basicdata.workhour.entity.WorkHour;
import com.wellsoft.pt.basicdata.workhour.service.WorkHourService;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryData;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo;
import com.wellsoft.pt.core.web.BaseController;

/**
 * 
 * Description: 工作时间控制层
 *  
 * @author zhouyq
 * @date 2013-4-17
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-4-17.1	zhouyq		2013-4-17		Create
 * </pre>
 *
 */
@Controller
@RequestMapping("/basicdata/workhour")
public class WorkHourController extends BaseController {

	@Autowired
	private WorkHourService workHourService;

	/**
	 * 跳转到 工作时间界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "")
	public String workHour(Model model) {
		List<WorkHour> workHourList = workHourService.getAll();
		List<WorkHour> workDayList = new ArrayList<WorkHour>();
		List<WorkHour> fixedHolidaysList = new ArrayList<WorkHour>();
		List<WorkHour> specialHolidaysList = new ArrayList<WorkHour>();
		List<WorkHour> makeUpList = new ArrayList<WorkHour>();
		for (WorkHour workHour : workHourList) {
			if ("Special".equals(workHour.getType())) {
				System.out.println("特殊节假日：" + workHour.getName());
				specialHolidaysList.add(workHour);
			} else if ("Workday".equals(workHour.getType())) {
				workDayList.add(workHour);
			} else if ("Fixed".equals(workHour.getType())) {
				System.out.println("固定节假日：" + workHour.getName());
				fixedHolidaysList.add(workHour);
			} else if ("Makeup".equals(workHour.getType())) {
				System.out.println("补班日期：" + workHour.getName());
				makeUpList.add(workHour);
			}
		}
		Collections.sort(workDayList, comparator);
		model.addAttribute("workDayList", workDayList);
		model.addAttribute("fixedHolidaysList", fixedHolidaysList);
		model.addAttribute("specialHolidaysList", specialHolidaysList);
		model.addAttribute("makeUpList", makeUpList);
		return forward("/basicdata/workhour/workhour");
	}

	//对星期进行排序
	Comparator<WorkHour> comparator = new Comparator<WorkHour>() {
		@Override
		public int compare(WorkHour workhour1, WorkHour workhour2) {
			return workhour1.getSortOrder().compareTo(workhour2.getSortOrder());
		}
	};

	/**
	 * 
	 * 固定节假日列表
	 * 
	 * @param queryInfo
	 * @return
	 */
	@RequestMapping(value = "/fixedHolidaysList")
	public @ResponseBody
	JqGridQueryData fixedHolidaysListAsJson(JqGridQueryInfo queryInfo) {
		JqGridQueryData queryData = workHourService.queryFixedHolidaysList(queryInfo);
		return queryData;
	}

	/**
	 * 
	 * 特殊节假日列表
	 * 
	 * @param queryInfo
	 * @return
	 */
	@RequestMapping(value = "/specialHolidaysList")
	public @ResponseBody
	JqGridQueryData specialHolidaysList(JqGridQueryInfo queryInfo) {
		JqGridQueryData queryData = workHourService.querySpecialHolidaysList(queryInfo);
		return queryData;
	}

	/**
	 * 
	 * 补班日期列表
	 * 
	 * @param queryInfo
	 * @return
	 */
	@RequestMapping(value = "/makeUpList")
	public @ResponseBody
	JqGridQueryData makeUpList(JqGridQueryInfo queryInfo) {
		JqGridQueryData queryData = workHourService.queryMakeUpList(queryInfo);
		return queryData;
	}

	/**
	 * 
	 * 工作日
	 * 
	 * @param fromTime1
	 * @param toTime1
	 * @param fromTime2
	 * @param toTime2
	 * @param notCheckedValArray
	 * @param checkedValArray
	 * @return
	 */
	@RequestMapping(value = "/save")
	public String save(@RequestParam("fromTime1") String fromTime1, @RequestParam("toTime1") String toTime1,
			@RequestParam("fromTime2") String fromTime2, @RequestParam("toTime2") String toTime2,
			@RequestParam("notCheckedValArray") String[] notCheckedValArray,
			@RequestParam("checkedValArray") String[] checkedValArray) {
		for (String chedkedVal : checkedValArray) {
			//过滤从jqgrid穿过来的checkbox的value值
			if (!("on".equals(chedkedVal))) {
				WorkHour checkedWorkHour = workHourService.findUniqueBy("code", chedkedVal);
				checkedWorkHour.setFromTime1(fromTime1);
				checkedWorkHour.setToTime1(toTime1);
				checkedWorkHour.setFromTime2(fromTime2);
				checkedWorkHour.setToTime2(toTime2);
				checkedWorkHour.setIsWorkday(true);
				workHourService.save(checkedWorkHour);
			}

		}
		for (String notChedkedVal : notCheckedValArray) {
			if (!("on".equals(notChedkedVal))) {
				WorkHour notCheckedWorkHour = workHourService.findUniqueBy("code", notChedkedVal);
				notCheckedWorkHour.setFromTime1(fromTime1);
				notCheckedWorkHour.setToTime1(toTime1);
				notCheckedWorkHour.setFromTime2(fromTime2);
				notCheckedWorkHour.setToTime2(toTime2);
				notCheckedWorkHour.setIsWorkday(false);
				workHourService.save(notCheckedWorkHour);
			}

		}
		return forward("/basicdata/workhour/workhour");
	}
}
