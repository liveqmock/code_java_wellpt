package com.wellsoft.pt.basicdata.systemtable.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.basicdata.systemtable.dao.SystemTableAttributeDao;
import com.wellsoft.pt.basicdata.systemtable.dao.SystemTableDao;
import com.wellsoft.pt.basicdata.systemtable.dao.SystemTableRelationshipDao;
import com.wellsoft.pt.basicdata.systemtable.entity.SystemTable;
import com.wellsoft.pt.basicdata.systemtable.entity.SystemTableAttribute;
import com.wellsoft.pt.basicdata.systemtable.entity.SystemTableRelationship;
import com.wellsoft.pt.basicdata.systemtable.service.SystemTableAttributeService;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryData;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo;
import com.wellsoft.pt.common.component.tree.TreeNode;
import com.wellsoft.pt.core.dao.Page;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.utils.bean.BeanUtils;

/**
 * 
 * Description: 系统表结构服务层实现类
 *  
 * @author zhouyq
 * @date 2013-3-21
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-3-21.1	zhouyq		2013-3-21		Create
 * </pre>
 *
 */
@Service
@Transactional
public class SystemTableAttributeServiceImpl implements SystemTableAttributeService {

	@Autowired
	private SystemTableAttributeDao systemTableAttributeDao;

	@Autowired
	private SystemTableRelationshipDao systemTableRelationshipDao;

	@Autowired
	private SystemTableDao systemTableDao;

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.message.service.SystemTableService#query(com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo)
	 */
	@Override
	public JqGridQueryData query(JqGridQueryInfo queryInfo) {
		Page<SystemTableAttribute> pageData = new Page<SystemTableAttribute>();
		pageData.setPageNo(queryInfo.getPage());
		pageData.setPageSize(queryInfo.getRows());
		this.systemTableAttributeDao.findPage(pageData);
		List<SystemTableAttribute> systemTableAttributes = pageData.getResult();
		List<SystemTableAttribute> jqUsers = new ArrayList<SystemTableAttribute>();
		for (SystemTableAttribute systemTableAttribute : systemTableAttributes) {
			SystemTableAttribute jqSystemTableAttribute = new SystemTableAttribute();
			BeanUtils.copyProperties(systemTableAttribute, jqSystemTableAttribute);
			jqUsers.add(jqSystemTableAttribute);
		}
		JqGridQueryData queryData = new JqGridQueryData();
		queryData.setCurrentPage(queryInfo.getPage());
		queryData.setDataList(jqUsers);
		queryData.setRepeatitems(false);
		queryData.setTotalPages(pageData.getTotalPages());
		queryData.setTotalRows(pageData.getTotalCount());
		return queryData;
	}

	/**
	 * 
	 * 根据表的uuid返回表的所有字段的集合
	 * 
	 * @param tableUuid
	 * @return
	 */
	@Override
	public List<SystemTableAttribute> getSystemTableColumns(String tableUuid) {
		SystemTable systemTable = systemTableDao.findUniqueBy("uuid", tableUuid);
		List<SystemTableAttribute> systemTableAttributeList = systemTableAttributeDao
				.findBy("systemTable", systemTable);
		return systemTableAttributeList;
	}

	/**
	 * 
	 * 根据表的uuid返回主表及从表属性的集合
	 * 
	 * @param tableUuid
	 * @return
	 */
	@Override
	public List<SystemTableAttribute> getAttributesByrelationship(String tableUuid) {
		SystemTable systemTable = systemTableDao.get(tableUuid);//获得系统表
		List<SystemTableAttribute> allSystemTableAttributeList = new ArrayList<SystemTableAttribute>();//
		List<SystemTableAttribute> systemTableAttributeList = getSystemTableColumns(tableUuid);//获得本身表所有属性
		for (SystemTableAttribute systemTableAttribute : systemTableAttributeList) {
			allSystemTableAttributeList.add(systemTableAttribute);
		}
		//获得关联表属性
		List<SystemTableRelationship> systemTableRelationshipList = systemTableRelationshipDao.findBy("systemTable",
				systemTable);
		for (SystemTableRelationship systemTableRelationship : systemTableRelationshipList) {
			String secondaryTableName = systemTableRelationship.getSecondaryTableName();//从表名
			SystemTable secondaryTable = systemTableDao.findUniqueBy("fullEntityName", secondaryTableName);
			String secondaryTableUuid = secondaryTable.getUuid();
			List<SystemTableAttribute> secondarySystemTableAttributeList = getSystemTableColumns(secondaryTableUuid);//获得本身表所有属性
			for (SystemTableAttribute systemTableAttribute : secondarySystemTableAttributeList) {
				allSystemTableAttributeList.add(systemTableAttribute);
			}
		}
		return allSystemTableAttributeList;

	}

	/** 
	 * 根据表的uuid返回主表及从表属性的集合(返回TreeNode)
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.systemtable.service.SystemTableAttributeService#getAttributesTreeNodeByrelationship(java.lang.String)
	 */
	@Override
	public List<TreeNode> getAttributesTreeNodeByrelationship(String s, String tableUuid) {
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		List<SystemTableAttribute> allSystemTableAttributeList = getAttributesByrelationship(tableUuid);
		for (Iterator iterator2 = allSystemTableAttributeList.iterator(); iterator2.hasNext();) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId("-1");
			SystemTableAttribute fd = (SystemTableAttribute) iterator2.next();
			SystemTableAttribute fdNew = new SystemTableAttribute();
			BeanUtils.copyProperties(fd, fdNew);
			if (fdNew.getChineseName() == null || fdNew.getChineseName().equals("")) {
				treeNode.setName(fdNew.getAttributeName());
			} else {
				treeNode.setName(fdNew.getChineseName());
			}
			treeNode.setId(fdNew.getAttributeName());
			treeNode.setData(QueryItem.getKey(fdNew.getColumnAliases()));
			treeNodes.add(treeNode);
		}
		return treeNodes;
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.systemtable.service.SystemTableAttributeService#getAttributesByrelationship2(java.lang.String)
	 */
	@Override
	public List<SystemTableRelationship> getAttributesByrelationship2(String tableUuid) {
		SystemTable systemTable = systemTableDao.get(tableUuid);//获得系统表
		List<SystemTableAttribute> allSystemTableAttributeList = new ArrayList<SystemTableAttribute>();//
		List<SystemTableAttribute> systemTableAttributeList = getSystemTableColumns(tableUuid);//获得本身表所有属性
		for (SystemTableAttribute systemTableAttribute : systemTableAttributeList) {
			allSystemTableAttributeList.add(systemTableAttribute);
		}
		//获得关联表属性
		List<SystemTableRelationship> systemTableRelationshipList = systemTableRelationshipDao.findBy("systemTable",
				systemTable);
		return systemTableRelationshipList;
	}

	/**
	 * 
	 * 获得系统表
	 * 
	 * @param tableUuid
	 * @return
	 */
	public SystemTable getTable(String tableUuid) {
		return systemTableDao.get(tableUuid);//获得系统表
	}

}
