/*
 * @(#)2013-3-13 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.dyview.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.wellsoft.pt.core.annotation.UnCloneable;
import com.wellsoft.pt.core.entity.IdEntity;

/**
 * Description: 视图自定义的样式属性
 * 
 * @author wubin
 * @date 2013-3-13
 * @version 1.0
 * 
 *          <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2013-3-13.1	Administrator		2013-3-13		Create
 * </pre>
 * 
 */
@Entity
@Table(name = "dyview_columncss_definition")
@DynamicUpdate
@DynamicInsert
public class ColumnCssDefinition extends IdEntity {

	/** 如何描述serialVersionUID */
	private static final long serialVersionUID = 3401432395180118817L;

	private String cssId;//行样式唯一标识Id

	private String viewColumn;// 视图列

	private String columnCondition; // 列条件(大于、小于、等于。。)

	private String columnConditionValue;// 列条件值

	private String fontColor; // 字体颜色

	private String fontWide; // 是否加粗

	@UnCloneable
	private ViewDefinition viewDefinition; // 所属的视图

	/**
	 * @return the cssId
	 */
	public String getCssId() {
		return cssId;
	}

	/**
	 * @param cssId 要设置的cssId
	 */
	public void setCssId(String cssId) {
		this.cssId = cssId;
	}

	/**
	 * @return the viewColumn
	 */
	public String getViewColumn() {
		return viewColumn;
	}

	/**
	 * @param viewColumn 要设置的viewColumn
	 */
	public void setViewColumn(String viewColumn) {
		this.viewColumn = viewColumn;
	}

	/**
	 * @return the columnCondition
	 */
	public String getColumnCondition() {
		return columnCondition;
	}

	/**
	 * @param columnCondition 要设置的columnCondition
	 */
	public void setColumnCondition(String columnCondition) {
		this.columnCondition = columnCondition;
	}

	/**
	 * @return the columnConditionValue
	 */
	public String getColumnConditionValue() {
		return columnConditionValue;
	}

	/**
	 * @param columnConditionValue 要设置的columnConditionValue
	 */
	public void setColumnConditionValue(String columnConditionValue) {
		this.columnConditionValue = columnConditionValue;
	}

	/**
	 * @return the fontColor
	 */
	public String getFontColor() {
		return fontColor;
	}

	/**
	 * @param fontColor 要设置的fontColor
	 */
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	/**
	 * @return the fontWide
	 */
	public String getFontWide() {
		return fontWide;
	}

	/**
	 * @param fontWide 要设置的fontWide
	 */
	public void setFontWide(String fontWide) {
		this.fontWide = fontWide;
	}

	/**
	 * @return the viewDefinition
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "view_def_uuid", nullable = false)
	public ViewDefinition getViewDefinition() {
		return viewDefinition;
	}

	/**
	 * @param viewDefinition 要设置的viewDefinition
	 */
	public void setViewDefinition(ViewDefinition viewDefinition) {
		this.viewDefinition = viewDefinition;
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
		result = prime * result + ((cssId == null) ? 0 : cssId.hashCode());
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
		ColumnCssDefinition other = (ColumnCssDefinition) obj;
		if (cssId == null) {
			if (other.cssId != null)
				return false;
		} else if (!cssId.equals(other.cssId))
			return false;
		return true;
	}

}
