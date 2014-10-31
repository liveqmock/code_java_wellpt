/*
 * @(#)2012-11-15 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.datadict.dao;

import org.springframework.stereotype.Repository;

import com.wellsoft.pt.basicdata.datadict.entity.DataDictionaryAttribute;
import com.wellsoft.pt.core.dao.hibernate.HibernateDao;

/**
 * Description: 数据字典属性值数据层访问类
 *  
 * @author zhulh
 * @date 2012-11-15
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-11-15.1	zhulh		2012-11-15		Create
 * </pre>
 *
 */
@Repository
public class DataDictionaryAttributeDao extends HibernateDao<DataDictionaryAttribute, String> {

}
