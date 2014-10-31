/*
 * @(#)2013-3-15 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.view.bean;

import com.wellsoft.pt.basicdata.view.entity.ColumnDefinitionNew;

/**
 * Description: 视图列属性的BEAN
 *  
 * @author Administrator
 * @date 2013-3-15
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-3-15.1	Administrator		2013-3-15		Create
 * </pre>
 *
 */
public class ColumnDefinitionNewBean extends ColumnDefinitionNew {

	/** 如何描述serialVersionUID */
	private static final long serialVersionUID = -131094026905717515L;

	// jqGrid的行标识
	private String id;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id 要设置的id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColumnDefinitionNewBean other = (ColumnDefinitionNewBean) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
