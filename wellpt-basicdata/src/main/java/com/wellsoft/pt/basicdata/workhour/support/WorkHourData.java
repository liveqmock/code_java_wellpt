/*
 * @(#)2014-2-7 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.workhour.support;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.wellsoft.pt.basicdata.workhour.entity.WorkHour;

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
public class WorkHourData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1422087678435722251L;

	private List<WorkHour> workHours;

	// Map<type, list<WorkHour>>
	private Map<String, List<WorkHour>> workHourMap;

	public List<WorkHour> getWorkHours() {
		return workHours;
	}

	public void setWorkHours(List<WorkHour> workHours) {
		this.workHours = workHours;
	}

	public Map<String, List<WorkHour>> getWorkHourMap() {
		return workHourMap;
	}

	public void setWorkHourMap(Map<String, List<WorkHour>> workHourMap) {
		this.workHourMap = workHourMap;
	}

}
