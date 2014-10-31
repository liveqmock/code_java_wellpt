/*
 * @(#)2013-3-19 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.view.bean;

import java.io.*;
import java.util.*;

import com.wellsoft.pt.core.support.*;

/**
 * Description: 如何描述该类
 *  
 * @author wubin
 * @date 2013-3-19
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-3-19.1	wubin		2013-3-19		Create
 * </pre>
 *
 */
public class ViewAndDataNewBean implements Serializable {
	//视图的uuid
	private String viewUuid;
	//数据uuid
	private String dataUuid;
	//视图的定义对象
	private ViewDefinitionNewBean viewDefinitionNewBean;
	//视图的数据对象
	private List<QueryItem> data;

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
	 * @return the dataUuid
	 */
	public String getDataUuid() {
		return dataUuid;
	}

	/**
	 * @param dataUuid 要设置的dataUuid
	 */
	public void setDataUuid(String dataUuid) {
		this.dataUuid = dataUuid;
	}

	/**
	 * @return the viewDefinitionNewBean
	 */
	public ViewDefinitionNewBean getViewDefinitionBean() {
		return viewDefinitionNewBean;
	}

	/**
	 * @param viewDefinitionNewBean 要设置的viewDefinitionBean
	 */
	public void setViewDefinitionBean(ViewDefinitionNewBean viewDefinitionNewBean) {
		this.viewDefinitionNewBean = viewDefinitionNewBean;
	}

	/**
	 * @return the data
	 */
	public List<QueryItem> getData() {
		return data;
	}

	/**
	 * @param data 要设置的data
	 */
	public void setData(List<QueryItem> data) {
		this.data = data;
	}

}
