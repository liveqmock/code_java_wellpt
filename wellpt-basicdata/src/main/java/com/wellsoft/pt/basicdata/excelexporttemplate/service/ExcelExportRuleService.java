/*
 * @(#)2014-6-17 V1.0
 * 
 * Copyright 2014 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.excelexporttemplate.service;

import java.io.File;
import java.util.List;

import com.wellsoft.pt.basicdata.excelexporttemplate.bean.ExcelExportDefinitionBean;
import com.wellsoft.pt.basicdata.excelexporttemplate.entity.ExcelExportDefinition;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryData;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo;

/**
 * Description: 如何描述该类
 *  
 * @author wubin
 * @date 2014-6-17
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-6-17.1	wubin		2014-6-17		Create
 * </pre>
 *
 */
public interface ExcelExportRuleService {

	/**
	 * 
	 * 通过uuid获取excel导出规则的bean
	 * 
	 * @param uuid
	 * @return
	 */
	public ExcelExportDefinitionBean getBeanByUuid(String uuid);

	public void saveBean(ExcelExportDefinitionBean bean);

	/**
	 * 
	 * 删除一条导出规则
	 * 
	 * @param uuid
	 */
	public void remove(String uuid);

	/**
	 * 
	 * 删除多条的导出规则
	 * 
	 * @param uuids
	 */
	public void removeAll(String[] uuids);

	/**
	 * 
	 * JQgridExcel导出规则列表查询
	 * 
	 * @param queryInfo
	 * @return
	 */
	public JqGridQueryData query(JqGridQueryInfo queryInfo);

	/**
	 * 
	 * 根据excel导出模版获得导出文件
	 * 
	 * @param id
	 * @return
	 */
	public File generateExcelFile(String id, String[] dataArray);

	/**
	 * 
	 * 获取所有的导出模版规则
	 * 
	 * @return
	 */
	public List<ExcelExportDefinition> getExcelExportRule();

	public ExcelExportDefinition getExcelExportDefinition(String uuid);

}
