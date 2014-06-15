/*
 * @(#)2013-6-23 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.common.generator.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Description: 如何描述该类
 *  
 * @author rzhu
 * @date 2013-6-23
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-6-23.1	rzhu		2013-6-23		Create
 * </pre>
 *
 */
@Entity
@Table(name = "cd_id_generator")
public class IdGenerator implements Serializable {
	private static final long serialVersionUID = 3723895529914963601L;

	private String entityClassName;

	private Long pointer;

	/**
	 * @return the entityClassName
	 */
	@Id
	public String getEntityClassName() {
		return entityClassName;
	}

	/**
	 * @param entityClassName 要设置的entityClassName
	 */
	public void setEntityClassName(String entityClassName) {
		this.entityClassName = entityClassName;
	}

	/**
	 * @return the pointer
	 */
	public Long getPointer() {
		return pointer;
	}

	/**
	 * @param pointer 要设置的pointer
	 */
	public void setPointer(Long pointer) {
		this.pointer = pointer;
	}

}
