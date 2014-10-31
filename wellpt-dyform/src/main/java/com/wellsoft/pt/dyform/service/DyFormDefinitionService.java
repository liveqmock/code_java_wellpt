/*
 * @(#)2012-10-30 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dyform.service;

import java.sql.SQLException;
import java.util.List;

import com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo;
import com.wellsoft.pt.common.component.tree.TreeNode;
import com.wellsoft.pt.core.support.QueryData;
import com.wellsoft.pt.core.support.QueryInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.dyform.entity.DyFormDefinition;
import com.wellsoft.pt.dytable.bean.TreeNodeBean;

/**
 * Description: 动态表单定义service类
 *  
 * @author jiangmb
 * @date 2012-12-20
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-12-20.1	jiangmb		2012-12-20		Create
 * </pre>
 *
 */
public interface DyFormDefinitionService {

	/**
	 *通过form_uuid获取数据表单表的定义
	 * @param formUuid
	 * @return
	 */

	DyFormDefinition findDyFormDefinitionByFormUuid(String formUuid);

	/**
	 * 
	 * 
	 * @param s
	 * @param s1
	 * @return
	 */
	public QueryItem getFormKeyValuePair(String moduleId, String s1);

	/**
	 *通过tblId获取数据表单表的定义
	 * @param formUuid
	 * @return
	 */
	DyFormDefinition findDyFormDefinitionByOuterId(String tblId);

	/**
	 *通过tblName获取数据表单表的定义
	 * @param formUuid
	 * @return
	 */
	List<DyFormDefinition> findDyFormDefinitionByTblName(String tblName);

	public List<DyFormDefinition> findDyFormDefinitionsByOuterId(String outerId);
	/**
	 * 保存表单数据表定义信息并生成表单数据表
	 * 
	 * @param formDefinition 定义信息
	 * @param deletedFieldsJSONObj 
	 * @throws SQLException 
	 */
	void createFormDefinitionAndFormTable(DyFormDefinition formDefinition);

	/**
	 * 判断数据表单表的表是否已存在 
	 * @param formTblId
	 * @return
	 */
	public boolean isFormExistByFormOuterId(String formTblId);

	/**
	 * 判断数据表单表的表名是否已存在 
	 * @param formTblName
	 * @return
	 */
	public boolean isFormExistByFormTblName(String formTblName);

	/**
	 * 根据formUuid获取该表单<font color="red"><b> <i>其他</i> </b>版本</font>的定义概要信息树
	 * @param formUuid
	 * @return
	 */
	public List<TreeNodeBean> getFormOutlineOfAllVersionTreeByFormUuid(String formUuid);

	/**
	 * 根据formName获取各版本的定义概要信息树
	 * @param formName
	 * @return
	 */
	public List<TreeNodeBean> getFormOutlineOfAllVersionTreeByFormName(String formName);

	/**
	 * 获取所有的表单概要信息树,并且将参数selectedFormUuid指定的表单设置为选中状态,若selectedFormUuid为null则不设置选中节点
	 * @param parentId 该参数无其他用途,只是按照控件协议
	 * @param selectedFormUuid
	 * @return
	 */
	public List<TreeNodeBean> getFormOutlineOfAllVersionTree(String parentId, String selectedFormUuid);

	public QueryData getForPageAsTree(JqGridQueryInfo jqGridQueryInfo, QueryInfo queryInfo, String flag);

	/**
	 * 根据formUuid获取字段的树形(控制显示\隐藏字段使用)
	 * 
	 * @param s
	 * @param formUuid
	 * @return
	 */
	public List<TreeNode> getFieldByFormUuid(String s, String formUuid);

	/**
	 * 根据表名，获取对应的所有版本的定义
	 * @param tblName 
	 * 
	 * @return
	 */
	List<DyFormDefinition> getFormDefinitionsByTblName(String tblName);

	void updateFormDefinitionAndFormTable(DyFormDefinition formDefinition, List<String> deletedFieldNames);

	/**
	 * 获取所有表单最高版本的定义列表
	 * 
	 * @return
	 */
	List<DyFormDefinition> getMaxVersionList();

	/**
	 * 获取所有的表单定义
	 * 
	 * @return
	 */
	public List<DyFormDefinition> getAllFormDefintions();

	/**
	 * 根据指定的表名，获取其对应的最高版本的定义
	 * 
	 * @param tableName
	 */
	DyFormDefinition getFormDefinitionOfMaxVersionByTblName(String tableName);

	DyFormDefinition getFormDefinition(String tblName, String version);

	/**
	 * 删除表单定义及表单表结构
	 * 
	 * @param formUuid
	 * @return
	 */
	void dropForm(String formUuid);

	List<DyFormDefinition> getFormDefinitionByModelId(String modelId);
}
