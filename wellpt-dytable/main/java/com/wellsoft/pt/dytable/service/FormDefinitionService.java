/*
 * @(#)2012-10-30 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.service;

import java.util.List;
import java.util.Map;

import com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo;
import com.wellsoft.pt.common.component.tree.TreeNode;
import com.wellsoft.pt.core.dao.Page;
import com.wellsoft.pt.core.dao.PropertyFilter;
import com.wellsoft.pt.core.support.QueryData;
import com.wellsoft.pt.core.support.QueryInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.dytable.bean.FormDefinitionBean;
import com.wellsoft.pt.dytable.bean.RootTableInfoBean;
import com.wellsoft.pt.dytable.bean.TreeNodeBean;
import com.wellsoft.pt.dytable.entity.FieldDefinition;
import com.wellsoft.pt.dytable.entity.FormDefinition;

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
public interface FormDefinitionService {

	QueryData getForPageAsTree(JqGridQueryInfo jqGridQueryInfo, QueryInfo queryInfo, String flag);

	/**
	 * 获取所有自定义表单列表
	 * 
	 * @return
	 */
	List<FormDefinition> getAllDynamicFormList();

	/**
	 * 获取所有的打印模板
	 */
	public List getPrintTemplates(String treeNodeId);

	/**
	 * 
	 * 获取所有的显示单据表单
	 * 
	 * @param s
	 * @return
	 */
	public List<TreeNode> getShowTableModels(String s, String flag);

	/**
	 * 
	 * 根据formUuid获取字段的树形
	 * 
	 * @param s
	 * @param formUuid
	 * @return
	 */
	public List<TreeNode> getFieldByFormUuid(String s, String formUuid);

	/**
	 * 获取顶层表单列表
	 * 
	 * @return
	 */
	List<FormDefinition> getAllTopDynamicFormList();

	/**
	 * 保存自定义表单基本信息
	 * @param entity
	 * @return
	 */
	void saveFormDefinition(FormDefinition entity);

	/**
	 * 用户上传html模板，对模板进行相关设置，保存用户设置信息包括：主表信息，主表字段信息，主表与从表对应关系信息，从表表头与子表字段对应关系
	 * 
	 * @param rootTableInfo
	 * @return
	 */
	RootTableInfoBean saveFormDefinition(RootTableInfoBean rootTableInfo);

	/**
	 * 通过uuid查找对应的表单配置信息
	 * 
	 * @param uuid
	 * @param isHtmlBodyContent
	 * @param b 
	 * @return
	 */
	public RootTableInfoBean getRootTableInfoByUuid(String uuid, boolean isHtmlBodyContent, boolean getDataDict);

	/**
	 * 用户修改表单配置信息(包括上传html模板)，更新用户设置信息包括：主表信息，主表字段信息，主表与从表对应关系信息，从表表头与子表字段对应关系
	 * 
	 * @param rootTableInfo
	 * @return
	 */
	RootTableInfoBean updateupdFormDefinition(RootTableInfoBean rootTableInfo);

	/**
	 * 获取表单完整树数据
	 * 
	 * @param parentId
	 * @param selectedId
	 * @return
	 */
	List<TreeNodeBean> getFullTree(String parentId, String selectedId);

	/**
	 * 删除表单定义通过实现对象
	 * 
	 * @param entity
	 */
	boolean deleteFormDefinition(String uuid);

	/**
	 * 删除子表数据
	 * 
	 * @param entity
	 */
	public void deleteFormDefinitionChild(FormDefinition entity);

	/**
	 * 获取动态表单定义页面数据列表
	 * 
	 * @param page
	 * @param filters
	 * @return
	 */
	Page<FormDefinition> searchForm(final Page<FormDefinition> page, final List<PropertyFilter> filters);

	/**
	 * 获取动态表单定义页面数据列表
	 * 
	 * @param page
	 * @return
	 */
	Page<FormDefinition> searchFormDefinition(Page<FormDefinition> page);

	/**
	 * 获取所有表单定义列表
	 * 
	 * @return
	 */
	List<FormDefinition> searchFormDefinition();

	/**
	 * 获取表单对应的字段信息列表
	 * 
	 * @param uuid
	 * @return
	 */
	List<FieldDefinition> getFieldByForm(String uuid);

	/**
	 * 根据表单UUID查找对应的表单定义信息对象
	 * 
	 * @param uid
	 * @return
	 */
	FormDefinition getFormByUUID(String uid);

	FormDefinitionBean getFormDefinitionBeanByUUID(String uid);

	/**
	 * 获取最新版本或所有版本的表单定义信息列表
	 * 
	 * @param isAll
	 * @return
	 */
	List<FormDefinition> getForms(boolean isAll);

	/**
	 * 按实体名获取最新版本的表单定义信息对象
	 * 
	 * @param entityName
	 * @return
	 */
	FormDefinition getForm(String entityName);

	/**
	 * 按实体名获取所有版本的表单定义信息列表
	 * 
	 * @param entityName
	 * @return
	 */
	List<FormDefinition> getForms(String entityName);

	/**
	 * 根据实体和版本号获取对应版本的表单定义信息对象
	 * 
	 * @param entityName
	 * @param version
	 * @return
	 */
	FormDefinition getForm(String entityName, String version);

	/**
	 * 
	 * 获取动态表单下拉框的初始值
	 * 
	 * @param s
	 * @param s1
	 * @return
	 */
	public QueryItem getFormKeyValuePair(String s, String s1);

	/**
	 * 
	 * 验证表单数据是否满足配置
	 * 
	 * @param formId
	 * @param formDataUuid
	 * @return
	 */
	public String verificationFormData(String formId, String formDataUuid);

	public Map verificationDytableData(String formId, List<Map> mainTableDatas);
}
