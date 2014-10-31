/*
 * @(#)2013-1-25 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.datadict.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wellsoft.pt.core.web.BaseController;

/**
 * Description: 如何描述该类
 *  
 * @author zhulh
 * @date 2013-1-25
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-1-25.1	zhulh		2013-1-25		Create
 * </pre>
 *
 */
@Controller
@RequestMapping("/basicdata/datadict")
public class DataDictionaryController extends BaseController {

	/**
	 * 跳转到数据字典维护界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "")
	public String datadict() {
		return forward("/basicdata/datadict/datadict");
	}

}
