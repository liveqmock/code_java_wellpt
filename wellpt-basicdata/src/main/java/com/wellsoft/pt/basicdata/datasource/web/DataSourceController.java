/*
 * @(#)2014-7-31 V1.0
 * 
 * Copyright 2014 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.datasource.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wellsoft.pt.basicdata.datasource.bean.DataSourceDefinitionBean;
import com.wellsoft.pt.basicdata.datasource.service.DataSourceDataService;
import com.wellsoft.pt.basicdata.datasource.service.DataSourceDefinitionService;
import com.wellsoft.pt.basicdata.facade.BasicDataApiFacade;
import com.wellsoft.pt.core.web.BaseController;

/**
 * Description: 如何描述该类
 *  
 * @author wubin
 * @date 2014-7-31
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-7-31.1	wubin		2014-7-31		Create
 * </pre>
 *
 */
@Controller
@RequestMapping("/basicdata/datasource")
public class DataSourceController extends BaseController {

	@Autowired
	private DataSourceDefinitionService dataSourceDefinitionService;
	@Autowired
	private DataSourceDataService dataSourceDataService;
	@Autowired
	private BasicDataApiFacade basicDataApiFacade;

	/**
	 * 跳转到数据库定义界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "")
	public String dataSourceDefinition() {
		return forward("/basicdata/datasource/datasource_definition");
	}

	/**
	 * 
	 * 跳转到外部数据配置定义界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/profile")
	public String dataSourceProFile() {
		return forward("/basicdata/datasource/datasource_profile");
	}

	@RequestMapping(value = "/source_show", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getDataSourceData(Model model, @RequestParam(value = "sourceUuid", required = false) String sourceUuid) {
		DataSourceDefinitionBean bean = dataSourceDefinitionService.getBeanByUuid(sourceUuid);
		String Id = bean.getDataSourceId();
		model.addAttribute("queryItems", dataSourceDataService.dataSourceInterpreter(Id));
		return forward("/basicdata/datasource/datasource_explain");
	}
}
