/*
 * @(#)2013-3-13 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.dyview.service;

import java.util.*;

import com.wellsoft.pt.basicdata.dyview.entity.*;

/**
 * Description: 如何描述该类
 *  
 * @author Administrator
 * @date 2013-3-13
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-3-13.1	Administrator		2013-3-13		Create
 * </pre>
 *
 */
public interface ColumnDefinitionService {

	/**
	 * 
	 * 通过viewUuid查找列定义对象
	 * 
	 * @return
	 */
	public ColumnDefinition getFieldById(String viewUuid);

	/**
	 * 获取所有的列定义信息
	 * 
	 * @return
	 */
	public List<ColumnDefinition> getAllField();

	/**
	 * 保存单个实体对象
	 * 
	 * @param entity
	 */
	public void saveField(ColumnDefinition entity);

	/**
	 * 
	 *根据提供的viewUuid删除列定义信息 
	 * @param viewUuid
	 */
	public void deleteField(String viewUuid);
}
