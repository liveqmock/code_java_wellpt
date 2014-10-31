/*
 * @(#)2013-1-25 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.datadict.bean;

import java.util.HashSet;
import java.util.Set;

import com.wellsoft.pt.basicdata.datadict.entity.DataDictionary;

/**
 * Description: 数据字典PO类
 * 
 * @author zhulh
 * @date 2013-1-25
 * @version 1.0
 * 
 *          <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-1-25.1	zhulh		2013-1-25		Create
 * </pre>
 * 
 */
public class DataDictionaryBean extends DataDictionary {

	private static final long serialVersionUID = 4542567638431968255L;

	// jqGrid的行标识
	private String id;

	private String parentName;

	private String parentUuid;

	/** 字典所有者，从组织机构中选择直接作为ACL中的SID */
	private String ownerNames;

	private String ownerIds;

	private Set<DataDictionaryBean> changedChildren = new HashSet<DataDictionaryBean>(0);
	private Set<DataDictionaryBean> deletedChildren = new HashSet<DataDictionaryBean>(0);

	private Set<DataDictionaryAttributeBean> changedAttributes = new HashSet<DataDictionaryAttributeBean>(0);
	private Set<DataDictionaryAttributeBean> deletedAttributes = new HashSet<DataDictionaryAttributeBean>(0);

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getParentUuid() {
		return parentUuid;
	}

	public void setParentUuid(String parentUuid) {
		this.parentUuid = parentUuid;
	}

	public Set<DataDictionaryBean> getChangedChildren() {
		return changedChildren;
	}

	public void setChangedChildren(Set<DataDictionaryBean> changedChildren) {
		this.changedChildren = changedChildren;
	}

	/**
	 * @return the deletedChildren
	 */
	public Set<DataDictionaryBean> getDeletedChildren() {
		return deletedChildren;
	}

	/**
	 * @param deletedChildren 要设置的deletedChildren
	 */
	public void setDeletedChildren(Set<DataDictionaryBean> deletedChildren) {
		this.deletedChildren = deletedChildren;
	}

	/**
	 * @return the ownerNames
	 */
	public String getOwnerNames() {
		return ownerNames;
	}

	/**
	 * @param ownerNames 要设置的ownerNames
	 */
	public void setOwnerNames(String ownerNames) {
		this.ownerNames = ownerNames;
	}

	/**
	 * @return the ownerIds
	 */
	public String getOwnerIds() {
		return ownerIds;
	}

	/**
	 * @param ownerIds 要设置的ownerIds
	 */
	public void setOwnerIds(String ownerIds) {
		this.ownerIds = ownerIds;
	}

	/**
	 * @return the changedAttributes
	 */
	public Set<DataDictionaryAttributeBean> getChangedAttributes() {
		return changedAttributes;
	}

	/**
	 * @param changedAttributes 要设置的changedAttributes
	 */
	public void setChangedAttributes(Set<DataDictionaryAttributeBean> changedAttributes) {
		this.changedAttributes = changedAttributes;
	}

	/**
	 * @return the deletedAttributes
	 */
	public Set<DataDictionaryAttributeBean> getDeletedAttributes() {
		return deletedAttributes;
	}

	/**
	 * @param deletedAttributes 要设置的deletedAttributes
	 */
	public void setDeletedAttributes(Set<DataDictionaryAttributeBean> deletedAttributes) {
		this.deletedAttributes = deletedAttributes;
	}

}
