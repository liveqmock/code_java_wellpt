/*
 * @(#)2013-3-22 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.view.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.wellsoft.pt.core.annotation.UnCloneable;
import com.wellsoft.pt.core.entity.IdEntity;

/**
 * Description: 分页定义类
 *  
 * @author wubin
 * @date 2013-3-22
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-3-22.1	wubin		2013-3-22		Create
 * </pre>
 *
 */
@Entity
@Table(name = "view_page_definition")
@DynamicUpdate
@DynamicInsert
public class PageDefinitionNew extends IdEntity {

	/** 如何描述serialVersionUID */
	private static final long serialVersionUID = -5516099203718554480L;

	private Boolean isPaging; //是否分页

	private Integer pageNum; //每页的行数

	@UnCloneable
	private ViewDefinitionNew viewDefinitionNew; // 所属的视图

	/**
	 * @return the isPaging
	 */
	public Boolean getIsPaging() {
		return isPaging;
	}

	/**
	 * @param isPaging 要设置的isPaging
	 */
	public void setIsPaging(Boolean isPaging) {
		this.isPaging = isPaging;
	}

	/**
	 * @return the pageNum
	 */
	public Integer getPageNum() {
		return pageNum;
	}

	/**
	 * @param pageNum 要设置的pageNum
	 */
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * @return the viewDefinitionNew
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "view_def_uuid", nullable = false)
	public ViewDefinitionNew getViewDefinitionNew() {
		return viewDefinitionNew;
	}

	/**
	 * @param viewDefinitionNew 要设置的viewDefinitionNew
	 */
	public void setViewDefinitionNew(ViewDefinitionNew viewDefinitionNew) {
		this.viewDefinitionNew = viewDefinitionNew;
	}

}
