/*
 * @(#)2014-8-20 V1.0
 * 
 * Copyright 2014 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.datasource.provider;

import java.util.Map;
import java.util.Set;

import com.wellsoft.pt.basicdata.datasource.entity.DataSourceColumn;
import com.wellsoft.pt.basicdata.view.support.DyViewQueryInfoNew;

/**
 * Description: 数据源数据接口的实现类
 *  
 * @author wubin
 * @date 2014-8-20
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-8-20.1	wubin		2014-8-20		Create
 * </pre>
 *
 */
public abstract class AbstractDataSourceProvider implements DataSourceProvider {

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.datasource.provider.DataSourceProvider#count(java.util.Set, java.lang.String, java.util.Map)
	 */
	@Override
	public Long count(Set<DataSourceColumn> dataSourceColumns, String whereHql, Map<String, Object> queryParams) {
		return -1L;
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.datasource.provider.DataSourceProvider#custom(java.lang.Object[])
	 */
	@Override
	public Object[] custom(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.datasource.provider.DataSourceProvider#getWhereSql(com.wellsoft.pt.basicdata.view.support.DyViewQueryInfoNew)
	 */
	@Override
	public String getWhereSql(DyViewQueryInfoNew dyViewQueryInfoNew) {
		return null;
	}

}
