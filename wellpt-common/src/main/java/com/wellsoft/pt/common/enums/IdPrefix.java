/*
 * @(#)2013-1-18 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.common.enums;

/**
 * Description: 标识实体ID的前缀
 *  
 * @author zhulh
 * @date 2013-1-18
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-1-18.1	zhulh		2013-1-18		Create
 * </pre>
 *
 */
public enum IdPrefix {
	USER("User", "U"), DEPARTMENT("Department", "D"), GROUP("Group", "G"), UNIT("Unit", "O"), ROLE("Role", "R"), PRIVILEGE(
			"Privilege", "P"), TENANT("Multi Tenant", "mt_tenant");

	private String name;
	private String value;

	/**
	 * 如何描述该构造方法
	 *
	 * @param name
	 * @param value
	 */
	private IdPrefix(String name, String value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name 要设置的name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value 要设置的value
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
