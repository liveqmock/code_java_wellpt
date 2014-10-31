/*
 * @(#)2014-7-31 V1.0
 * 
 * Copyright 2014 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.datasource.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.basicdata.datasource.bean.DataSourceColumnBean;
import com.wellsoft.pt.basicdata.datasource.bean.DataSourceDefinitionBean;
import com.wellsoft.pt.basicdata.datasource.dao.DataSourceColumnDao;
import com.wellsoft.pt.basicdata.datasource.dao.DataSourceDefinitionDao;
import com.wellsoft.pt.basicdata.datasource.dao.DataSourceProfileDao;
import com.wellsoft.pt.basicdata.datasource.entity.DataSourceColumn;
import com.wellsoft.pt.basicdata.datasource.entity.DataSourceDefinition;
import com.wellsoft.pt.basicdata.datasource.service.DataSourceDefinitionService;
import com.wellsoft.pt.common.component.tree.TreeNode;
import com.wellsoft.pt.core.service.impl.BaseServiceImpl;
import com.wellsoft.pt.utils.bean.BeanUtils;

/**
 * Description: 数据源的服务类的实现
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
@Service
@Transactional
public class DataSourceDefinitionServiceImpl extends BaseServiceImpl implements DataSourceDefinitionService {

	@Autowired
	private DataSourceDefinitionDao dataSourceDefinitionDao;
	@Autowired
	private DataSourceColumnDao dataSourceColumnDao;
	@Autowired
	private DataSourceProfileDao dataSourceProfileDao;

	/** 
	 * 保存数据源定义
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.datasource.service.DataSourceDefinitionService#saveBean(com.wellsoft.pt.basicdata.datasource.bean.DataSourceDefinitionBean)
	 */
	@Override
	public String saveBean(DataSourceDefinitionBean bean) {
		DataSourceDefinition dataSourceDefinition = new DataSourceDefinition();
		if (StringUtils.isNotBlank(bean.getUuid())) {
			dataSourceDefinition = this.dataSourceDefinitionDao.get(bean.getUuid());
		} else {
			bean.setUuid(null);
			bean.setId((new StringBuilder("DS")).append(UUID.randomUUID()).toString());
		}
		BeanUtils.copyProperties(bean, dataSourceDefinition);
		this.dataSourceDefinitionDao.save(dataSourceDefinition);
		//保存列定义的属性
		Set<DataSourceColumnBean> beans = new LinkedHashSet<DataSourceColumnBean>();
		beans = bean.getDataSourceColumnBean();
		if (StringUtils.isNotBlank(bean.getUuid())) {
			//删除已存在的列
			for (DataSourceColumn dsc : bean.getDataSourceColumns()) {
				DataSourceColumn dataSourceColumn = this.dataSourceColumnDao.get(dsc.getUuid());
				this.dataSourceColumnDao.delete(dataSourceColumn);
			}
		}
		for (DataSourceColumn dsc : beans) {
			if (StringUtils.isNotBlank(dsc.getUuid())) {
				DataSourceColumn dataSourceColumn = this.dataSourceColumnDao.get(dsc.getUuid());
				BeanUtils.copyProperties(dsc, dataSourceColumn);
				dataSourceColumn.setDataSourceDefinition(dataSourceDefinition);
				this.dataSourceColumnDao.save(dataSourceColumn);
			} else {
				DataSourceColumn dataSourceColumn = new DataSourceColumn();
				BeanUtils.copyProperties(dsc, dataSourceColumn);
				dataSourceColumn.setDataSourceDefinition(dataSourceDefinition);
				this.dataSourceColumnDao.save(dataSourceColumn);
			}
		}
		return dataSourceDefinition.getUuid();
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.datasource.service.DataSourceDefinitionService#getBeanById(java.lang.String)
	 */
	@Override
	public DataSourceDefinitionBean getBeanByJqGridId(String jqGridId) {
		DataSourceDefinition dataSourceDefinition = dataSourceDefinitionDao.getByJqGridId(jqGridId);
		DataSourceDefinitionBean bean = new DataSourceDefinitionBean();
		BeanUtils.copyProperties(dataSourceDefinition, bean);
		//设置列定义
		Set<DataSourceColumn> dataSourceColumns = dataSourceDefinition.getDataSourceColumns();
		bean.setDataSourceColumns(BeanUtils.convertCollection(dataSourceColumns, DataSourceColumn.class));
		return bean;
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.datasource.service.DataSourceDefinitionService#getBeanById(java.lang.String)
	 */
	@Override
	public DataSourceDefinitionBean getBeanById(String id) {
		DataSourceDefinition dataSourceDefinition = dataSourceDefinitionDao.getById(id);
		DataSourceDefinitionBean bean = new DataSourceDefinitionBean();
		BeanUtils.copyProperties(dataSourceDefinition, bean);
		//设置列定义
		Set<DataSourceColumn> dataSourceColumns = dataSourceDefinition.getDataSourceColumns();
		bean.setDataSourceColumns(BeanUtils.convertCollection(dataSourceColumns, DataSourceColumn.class));
		return bean;
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.datasource.service.DataSourceDefinitionService#deleteById(java.lang.String)
	 */
	@Override
	public void deleteById(String id) {
		DataSourceDefinition dataSourceDefinition = dataSourceDefinitionDao.getByJqGridId(id);
		dataSourceDefinitionDao.delete(dataSourceDefinition);
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.datasource.service.DataSourceDefinitionService#deleteAllById(java.lang.String[])
	 */
	@Override
	public void deleteAllById(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			DataSourceDefinition dataSourceDefinition = dataSourceDefinitionDao.getByJqGridId(ids[i]);
			dataSourceDefinitionDao.delete(dataSourceDefinition);
		}
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.datasource.service.DataSourceDefinitionService#getBeanByUuid(java.lang.String)
	 */
	@Override
	public DataSourceDefinitionBean getBeanByUuid(String uuid) {
		DataSourceDefinition dataSourceDefinition = dataSourceDefinitionDao.getByUuid(uuid);
		DataSourceDefinitionBean bean = new DataSourceDefinitionBean();
		BeanUtils.copyProperties(dataSourceDefinition, bean);
		//设置列定义
		Set<DataSourceColumn> dataSourceColumns = dataSourceDefinition.getDataSourceColumns();
		bean.setDataSourceColumns(BeanUtils.convertCollection(dataSourceColumns, DataSourceColumn.class));
		return bean;
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.datasource.service.DataSourceDefinitionService#getAll()
	 */
	@Override
	public List<DataSourceDefinition> getAll() {
		List<DataSourceDefinition> list = dataSourceDefinitionDao.getAll();
		return list;
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.datasource.service.DataSourceDefinitionService#getDataSourceFieldsById(java.lang.String)
	 */
	@Override
	public Set<DataSourceColumn> getDataSourceFieldsById(String dataSourceDefId) {
		DataSourceDefinitionBean bean = new DataSourceDefinitionBean();
		bean = this.getBeanById(dataSourceDefId);
		Set<DataSourceColumn> dataSourceColumns = bean.getDataSourceColumns();
		return dataSourceColumns;
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.datasource.service.DataSourceDefinitionService#getAllByTreeNode()
	 */
	@Override
	public List<TreeNode> getAllByTreeNode(String s) {
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		List<DataSourceDefinition> list = this.getAll();
		for (java.util.Iterator<DataSourceDefinition> iterator = list.iterator(); iterator.hasNext();) {
			DataSourceDefinition dataSourceDefinition = iterator.next();
			DataSourceDefinition dataSourceDefinitionNew = new DataSourceDefinition();
			BeanUtils.copyProperties(dataSourceDefinition, dataSourceDefinitionNew);
			TreeNode treeNode = new TreeNode();
			treeNode.setId(dataSourceDefinition.getDataSourceId());
			treeNode.setName(dataSourceDefinition.getDataSourceName());
			//			treeNode.setData(dataSourceDefinition);
			treeNodes.add(treeNode);
		}
		return treeNodes;
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.datasource.service.DataSourceDefinitionService#getDataSourceFieldsByTree(java.lang.String, java.lang.String)
	 */
	@Override
	public List<TreeNode> getDataSourceFieldsByTree(String s, String dataSourceDefId) {
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		Set<DataSourceColumn> dataSourceColumns = this.getDataSourceFieldsById(dataSourceDefId);
		for (DataSourceColumn dataSourceColumn : dataSourceColumns) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(dataSourceColumn.getFieldName());
			treeNode.setName(dataSourceColumn.getTitleName());
			treeNode.setData(dataSourceColumn);
			treeNodes.add(treeNode);
		}
		return treeNodes;
	}

}
