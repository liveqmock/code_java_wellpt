package com.wellsoft.pt.basicdata.systemtable.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.wellsoft.pt.core.annotation.UnCloneable;
import com.wellsoft.pt.core.entity.IdEntity;

/**
 * 
 * Description: 系统表结构实体类
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
@Entity
@Table(name = "cd_system_table_entity")
@DynamicUpdate
@DynamicInsert
public class SystemTable extends IdEntity {
	private static final long serialVersionUID = 2943854786118950658L;

	/**表名*/
	private String tableName;
	/** 表完全限定名 */
	private String fullEntityName;
	/** 中文名 */
	private String chineseName;
	/** 排序编号 */
	private String code;
	/** 备注 */
	private String remark;
	/** 模块名 */
	private String moduleName;

	//设置@UnCloneable则在保存时不进行读取
	@UnCloneable
	private Set<SystemTableAttribute> attributes = new HashSet<SystemTableAttribute>();

	@UnCloneable
	private Set<SystemTableRelationship> relationships = new HashSet<SystemTableRelationship>();

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName 要设置的tableName
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the fullEntityName
	 */
	//full_entity_name
	public String getFullEntityName() {
		return fullEntityName;
	}

	/**
	 * @param fullEntityName 要设置的fullEntityName
	 */
	public void setFullEntityName(String fullEntityName) {
		this.fullEntityName = fullEntityName;
	}

	/**
	 * @return the chineseName
	 */
	public String getChineseName() {
		return chineseName;
	}

	/**
	 * @param chineseName 要设置的chineseName
	 */
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark 要设置的remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the attributes
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "systemTable")
	@Cascade(value = { CascadeType.ALL })
	public Set<SystemTableAttribute> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes 要设置的attributes
	 */
	public void setAttributes(Set<SystemTableAttribute> attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * @param moduleName 要设置的moduleName
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * @return the relationships
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "systemTable")
	@Cascade(value = { CascadeType.ALL })
	public Set<SystemTableRelationship> getRelationships() {
		return relationships;
	}

	/**
	 * @param relationships 要设置的relationships
	 */
	public void setRelationships(Set<SystemTableRelationship> relationships) {
		this.relationships = relationships;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
