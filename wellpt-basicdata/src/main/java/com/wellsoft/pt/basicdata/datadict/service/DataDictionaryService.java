/*
 * @(#)2012-11-15 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.datadict.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.wellsoft.pt.basicdata.datadict.bean.DataDictionaryBean;
import com.wellsoft.pt.basicdata.datadict.entity.DataDictionary;
import com.wellsoft.pt.common.component.tree.TreeNode;
import com.wellsoft.pt.core.support.QueryItem;

/**
 * Description: 数据字典服务层接口
 * 
 * @author zhulh
 * @date 2012-11-15
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-11-15.1	zhulh		2012-11-15		Create
 * </pre>
 * 
 */
public interface DataDictionaryService {
	public static final String ACL_SID = "ROLE_DATA_DICT";

	/**
	 * 通过UUID获取数据字典
	 * 
	 * @param string
	 * @return
	 */
	DataDictionary get(String uid);

	/**
	 * 保存编码，若uuid不存在则创建，若存在则新增，返回保存/更新后的实体。使用acl接口存放字典的所有者
	 * 
	 * @param dataDictionary
	 * @return
	 */
	DataDictionary saveAcl(DataDictionary dataDictionary);

	/**
	 * 根据主键uuid删除指定数据字典，若字典存在子结点则删除
	 * 
	 * @param uuid
	 */
	void removeByPk(String uuid);

	/**
	 * 根据所有主键uuids删除指定数据字典，若字典存在子结点则删除
	 * 
	 * @param uuids
	 */
	void removeAllByPk(Collection<String> uuids);

	/**
	 * 异步加载树形结点，维护时不需要考虑ACL权限
	 * 
	 * @param id
	 * @return
	 */
	List<TreeNode> getAsTreeAsync(String id);

	/**
	 * 异步加载树形结点，维护时不需要考虑ACL权限(视图专用)
	 * 
	 * @param id
	 * @return
	 */
	List<TreeNode> getAsTreeAsyncForView(String id);

	/**
	 * 从指定类型开始异步加载树形结点，维护时不需要考虑ACL权限
	 * 
	 * @param id
	 * @return
	 */
	List<TreeNode> getFromTypeAsTreeAsync(String uuid, String type);

	List<TreeNode> getViewTypeAsTreeAsync(String uuid, String type);

	/**
	 * 根据UUID获取数据字典
	 * 
	 * @param uuid
	 * @return
	 */
	DataDictionaryBean getBean(String uuid);

	/**
	 * 保存数据字典
	 * 
	 * @param bean
	 *            数据字典VO类
	 */
	void saveBean(DataDictionaryBean bean);

	/**
	 * 删除数据字典
	 * 
	 * @param uuid
	 *            数据字典UUID
	 */
	void remove(String uuid);

	/**
	 * 返回指定ID的字典编码
	 * 
	 * @param id
	 * @return
	 */
	DataDictionary getByType(String type);

	/**
	 * 如何描述该方法
	 * 
	 * @param type
	 * @return
	 */
	List<DataDictionary> getDataDictionariesByType(String type);

	/**
	 * 
	 * @param type
	 * @return
	 */
	QueryItem getKeyValuePair(String sourceCode, String targetCode);

	/**
	 * 根据字典类型返回指定下子结点的指定字典编码的数据字典列表
	 * 
	 * @param type
	 * @param code
	 * @return
	 */
	List<DataDictionary> getDataDictionaries(String type, String code);

	/**
	 * 根据字典类型返回指定下子结点的指定字典编码的节点名称（树形下拉框配置为取自字典隐藏值配置方法）
	 * 
	 * @param type
	 * @param code
	 * @return
	 */
	Map<String, Object> getDataDictionaryName(String type, String code);

	/**
	 * radio,checkbox,select控件使用的字典树，不同点是code返回的是type
	 * 
	 * @param id
	 * @return
	 */
	List<TreeNode> getAsTreeAsyncForControl(String id);

	/**
	 * 
	 * 职能字典使用，不同点是data返回的是字典UUID
	 * 
	 * @param type
	 * @return
	 */
	List<TreeNode> getAsTreeAsyncForUuid(String uuid, String type);
}
