/*
 * @(#)2012-11-5 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.service;

import java.util.List;
import java.util.Map;

import com.wellsoft.pt.common.component.tree.TreeNode;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.dytable.bean.FormAndDataBean;
import com.wellsoft.pt.dytable.bean.RootFormDataBean;
import com.wellsoft.pt.dytable.support.FormDataInfo;
import com.wellsoft.pt.dytable.support.FormDataSignature;

/**
 * Description: 动态表单表单数据service接口
 * 
 * @author jiangmb
 * @date 2012-11-5
 * @version 1.0
 * 
 *          <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-11-5.1	jiangmb		2012-11-5		Create
 * </pre>
 * 
 */
public interface FormDataService {

	/**
	 * 
	 * 解析表单定义配置为xml的方法
	 * 
	 * @param xmlStr 请求的数据
	 * @param xslStr 解析的方法
	 * @return
	 */
	String getXml(String xmlStr, String xslStr);

	/**
	 * 
	 * 根据表单的Id获取表单的Uuid
	 * 
	 * @param Id
	 * @return
	 */
	String getFormUuidById(String Id);

	/**
	 * 保存表单数字签名
	 * 
	 * @param formData
	 * @return	返回签名在JCR中的结点名
	 * @throws Exception 
	 */
	String saveSignature(RootFormDataBean formData);

	/**
	 * 保存动态表单数据，返回表单数据uuid(包括新建与修改)
	 * 
	 * @param formData
	 * @return
	 */
	Map<String, Object> save(RootFormDataBean formData);

	/**
	 * 获取数据MD5算法的消息摘要
	 * 
	 * @param formData
	 * @return
	 */
	FormDataSignature getDigestValue(String formData);

	/**
	 * 删除表单数据记录(单条)
	 * 
	 * @param formUid
	 * @param dataUid
	 */
	void delete(String formUid, String dataUid);

	/**
	 * 删除表单数据记录(批量)
	 * 
	 * @param formUid
	 * @param dataUid
	 */
	void delete(String formUid, String[] dataUids);

	/**
	 * 根据主表uid获取对应的数据列表
	 * 
	 * @param tableType
	 *            1主表 2从表
	 * @param tableName
	 * @param dataUid
	 * @return
	 */
	List<Map> getFormDataList(String tableType, String tableName, String dataUid);

	/**
	 * 获取表单信息对象,此对象未添加字段控制信息
	 * 
	 * @param formUid
	 *            表单定义uuid
	 * @param dataUid
	 *            表单数据uuid
	 * @return
	 */
	FormAndDataBean getFormData(String formUid, String dataUid, String temp);

	/**
	 * 获取表单信息对象,此对象未添加字段控制信息（获取显示数据）
	 * 
	 * @param formUid
	 *            表单定义uuid
	 * @param dataUid
	 *            表单数据uuid
	 * @return
	 */
	FormAndDataBean getFormShowData(String formUid, String dataUid, String temp);

	/**
	 * 根据字段名，获取表单字段的值
	 * 
	 * @param formUid
	 * @param dataUid
	 * @param fieldName
	 * @return
	 */
	@Deprecated
	Object getFormFieldValue(String formUid, String dataUid, String fieldName);

	@Deprecated
	String getSubFormFieldValue(String formUid, String dataUid, String fieldName);

	/**
	 * 根据映射名，获取表单字段的值
	 * 
	 * @param formUuid
	 * @param dataUuid
	 * @param mappingName
	 * @return
	 */
	Object getFieldValueByMappingName(String formUuid, String dataUuid, String mappingName);

	/**
	 * 根据映射名，获取表单名、表单字段名、表单字段值,并放入一个map中
	 * 
	 * @param formUuid
	 * @param dataUuid
	 * @param mappingName
	 * @return
	 */
	Object getFieldInfosByMappingName(String formUuid, String dataUuid, String mappingName);

	/**
	 * 根据主表的formUuid和dataUuid取到从表的dataUuid和formUuid列表的方法
	 * 
	 * @param formUuid
	 * @param dataUuid
	 * @return
	 */
	List<FormDataInfo> getSubFormDataInfo(String formUuid, String dataUuid);

	/**
	 * @param formUuid
	 * @param projection
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<QueryItem> query(String formUuid, String[] projection, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy, int firstResult, int maxResults);

	List<QueryItem> query2(String formUuid, String[] projection, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy, int firstResult, int maxResults);

	List<QueryItem> query(String tableName, boolean distinct, String[] projection, String selection,
			Map<String, Object> selectionArgs, String groupBy, String having, String orderBy, int firstResult,
			int maxResults);

	/**
	 * 
	 * 根据表名查询表的总记录数
	 * 
	 * @param tableName
	 * @return
	 */
	Integer queryTotal(String tableName);

	List<String> copySubFormData(String sourceFormUuid, String sourceDataUuid, String targetFormUuid,
			String targetDataUuid, String subFormUuid, String whereSql, Map<String, Object> values);

	/**
	 * 动态表单数据复制
	 * 
	 * @param sourceFormUuid
	 *            源表单定义UUID
	 * @param sourceDataUuid
	 *            源表单数据UUID
	 * @param targetFormUuid
	 *            目标表单定义UUID
	 * @return 返回复制后的表单数据
	 */
	String copyFormData(String sourceFormUuid, String sourceDataUuid, String targetFormUuid);

	/**
	 * 动态表单数据复制
	 * 
	 * @param formAndDataBean
	 *            源表单数据
	 * @param targetFormUuid
	 *            目标表单定义UUID
	 * @return 返回复制后的表单数据
	 */
	String copyFormData(RootFormDataBean formAndDataBean, String targetFormUuid);

	/**
	 * 
	 * 保存或者更新表单数据
	 * 
	 * @param formUuid
	 * @param data
	 * @return
	 */
	String saveFormData(String formUuid, Map<String, Object> data);

	/**
	 * 
	 * 获取承办单位的字段
	 * 
	 * @param s
	 * @param viewUuid
	 * @return
	 */
	List<TreeNode> getShiliZiduan(String s, String s1);

	/**
	 * 
	 * 获得时间占用资源的树形(动态表单数据)
	 * 
	 * @param s
	 * @param s1
	 * @return
	 */
	List<TreeNode> getFormDataAsTree(String s, String s1);

	/**
	 * 
	 * 获得时间占用资源的树形（视图数据）
	 * 
	 * @param s
	 * @param s1
	 * @return
	 */
	List<TreeNode> getViewDataAsTree(String s, String s1);

	/**
	 * 
	 * 占用时间模块用来打开具体的会议安排
	 * 
	 * @param s
	 * @param s1
	 * @return
	 */
	List<TreeNode> getViewDataAsTree2(String s, String s1);

	/**
	 * 
	 * 获得承办单位的显示值
	 * 
	 * @param s
	 * @param s1
	 * @return
	 */
	Map<String, String> getShiliZiduanLabel(String s, String s1);

	List<Map> getSubFormData(String formUuid, String temp);

}
