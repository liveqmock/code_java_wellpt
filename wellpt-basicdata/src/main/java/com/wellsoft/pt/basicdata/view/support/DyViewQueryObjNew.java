/*
 * @(#)2013-3-22 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.view.support;

import java.io.Serializable;
import java.util.List;

import com.wellsoft.pt.core.support.PagingInfo;

/**
 * Description: 视图解析时候请求参数类
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
public class DyViewQueryObjNew implements Serializable {

	private PagingInfo pagingInfo; //分页信息

	private List keywords; //关键字

	private String orderbyArr; //点击查询的默认排序方式

}
