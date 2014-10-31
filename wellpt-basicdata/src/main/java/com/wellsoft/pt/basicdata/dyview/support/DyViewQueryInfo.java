/*
 * @(#)2013-3-22 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.dyview.support;

import java.io.Serializable;
import java.util.Map;

import com.wellsoft.pt.core.support.PagingInfo;

/**
 * Description: 如何描述该类
 *  
 * @author wubin
 * @date 2013-3-22
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-3-22.1	Upon		2013-3-22		Create
 * </pre>
 *
 */
public class DyViewQueryInfo implements Serializable {

	/** 如何描述serialVersionUID */
	private static final long serialVersionUID = 8564707148749059106L;

	private String viewUuid;//视图的uuid

	private String viewName;//视图的名字

	private CondSelectAskInfo condSelect;

	private PagingInfo pageInfo;//分页信息

	private Map<String, String> expandParams;//扩展参数

	private String openBy;//视图的名字

	/**
	 * @return the expandParams
	 */
	public Map<String, String> getExpandParams() {
		return expandParams;
	}

	/**
	 * @param expandParams 要设置的expandParams
	 */
	public void setExpandParams(Map<String, String> expandParams) {
		this.expandParams = expandParams;
	}

	/**
	 * @return the condSelect
	 */
	public CondSelectAskInfo getCondSelect() {
		return condSelect;
	}

	/**
	 * @param condSelect 要设置的condSelect
	 */
	public void setCondSelect(CondSelectAskInfo condSelect) {
		this.condSelect = condSelect;
	}

	/**
	 * @return the viewName
	 */
	public String getViewName() {
		return viewName;
	}

	/**
	 * @param viewName 要设置的viewName
	 */
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	/**
	 * @return the pageInfo
	 */
	public PagingInfo getPageInfo() {
		return pageInfo;
	}

	/**
	 * @param pageInfo 要设置的pageInfo
	 */
	public void setPageInfo(PagingInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	/**
	 * @return the viewUuid
	 */
	public String getViewUuid() {
		return viewUuid;
	}

	/**
	 * @param viewUuid 要设置的viewUuid
	 */
	public void setViewUuid(String viewUuid) {
		this.viewUuid = viewUuid;
	}

	/**
	 * @return the openBy
	 */
	public String getOpenBy() {
		return openBy;
	}

	/**
	 * @param openBy 要设置的openBy
	 */
	public void setOpenBy(String openBy) {
		this.openBy = openBy;
	}

}
