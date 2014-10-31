/*
 * @(#)2014-3-5 V1.0
 * 
 * Copyright 2014 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 如何描述该类
 *  
 * @author Administrator
 * @date 2014-3-5
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-3-5.1	Administrator		2014-3-5		Create
 * </pre>
 *
 */
public class PrinttemplateDateUtil {

	public static Map<String, Object> getPrinttemplateMap(String main_form_id,
			Map<String, List<Map<String, Object>>> sourceMap) {
		Map<String, Object> printtemplateMap = new HashMap<String, Object>();//传入打印模板接口map集合
		for (String key : sourceMap.keySet()) {
			//如果为主表formId
			if (key.equals(main_form_id)) {
				Map<String, Map<String, Object>> dataMap1 = new HashMap<String, Map<String, Object>>();
				//获取对应的数据集合
				List<Map<String, Object>> dataMapList = sourceMap.get(key);
				Map<String, Object> dataMap2 = dataMapList.get(0);//主表的话List中只有一条数据
				dataMap1.put(key, dataMap2);
				printtemplateMap.putAll(dataMap1);
			} else {
				Map<String, List<Map<String, Object>>> dataListMap = new HashMap<String, List<Map<String, Object>>>();
				dataListMap.put(key, sourceMap.get(key));
				printtemplateMap.putAll(dataListMap);
			}
		}
		return printtemplateMap;
	}
}
