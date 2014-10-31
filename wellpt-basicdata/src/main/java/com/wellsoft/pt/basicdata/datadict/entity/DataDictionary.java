/*
 * @(#)2012-11-14 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.datadict.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.wellsoft.pt.core.annotation.UnCloneable;
import com.wellsoft.pt.core.entity.IdEntity;

/**
 * Description: 数据字典实体类
 * 
 * @author zhulh
 * @date 2012-11-14
 * @version 1.0
 * 
 *          <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-11-14.1	zhulh		2012-11-14		Create
 * </pre>
 * 
 */
@Entity
@Table(name = "cd_data_dict")
@DynamicUpdate
@DynamicInsert
public class DataDictionary extends IdEntity {
	private static final long serialVersionUID = 2943854786118950658L;

	/** 名称 */
	private String name;
	/** 代码 */
	private String code;
	/** 字典类型 */
	private String type;

	/** 父结点 */
	@UnCloneable
	private DataDictionary parent;
	/** 自关联 */
	@UnCloneable
	private Set<DataDictionary> children = new HashSet<DataDictionary>(0);
	/** 字典属性 */
	@UnCloneable
	private Set<DataDictionaryAttribute> attributes = new HashSet<DataDictionaryAttribute>(0);

	/** 字典所有者，从组织机构中选择直接作为ACL中的SID */
	private List<String> owners = new ArrayList<String>(0);

	//来源uuid 
	private String sourceUuid;

	//来源类型
	private String sourceType;

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getSourceUuid() {
		return sourceUuid;
	}

	public void setSourceUuid(String sourceUuid) {
		this.sourceUuid = sourceUuid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            要设置的name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            要设置的code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the parent
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_uuid")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	public DataDictionary getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *            要设置的parent
	 */
	public void setParent(DataDictionary parent) {
		this.parent = parent;
	}

	/**
	 * @return the children
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
	@Cascade(value = { CascadeType.ALL })
	@LazyCollection(LazyCollectionOption.TRUE)
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("code asc")
	public Set<DataDictionary> getChildren() {
		return children;
	}

	/**
	 * @param childrens
	 *            要设置的childrens
	 */
	public void setChildren(Set<DataDictionary> childrens) {
		this.children = childrens;
	}

	/**
	 * @return the attributes
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dataDictionary")
	@Cascade(value = { CascadeType.ALL })
	@LazyCollection(LazyCollectionOption.TRUE)
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("attrOrder")
	public Set<DataDictionaryAttribute> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes
	 *            要设置的attributes
	 */
	public void setAttributes(Set<DataDictionaryAttribute> attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return the owners
	 */
	@Transient
	public List<String> getOwners() {
		return owners;
	}

	/**
	 * @param owners 要设置的owners
	 */
	public void setOwners(List<String> owners) {
		this.owners = owners;
	}

}
