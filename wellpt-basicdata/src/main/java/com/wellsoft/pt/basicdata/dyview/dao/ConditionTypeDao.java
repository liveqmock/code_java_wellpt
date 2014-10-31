/*
 * @(#)2013-3-30 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.dyview.dao;

import org.springframework.stereotype.*;

import com.wellsoft.pt.basicdata.dyview.entity.*;
import com.wellsoft.pt.core.dao.hibernate.*;

/**
 * Description: 如何描述该类
 *  
 * @author wubin
 * @date 2013-3-30
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-3-30.1	wubin		2013-3-30		Create
 * </pre>
 *
 */
@Repository
public class ConditionTypeDao extends HibernateDao<ConditionType, String> {
	public ConditionTypeDao() {

	}
}
