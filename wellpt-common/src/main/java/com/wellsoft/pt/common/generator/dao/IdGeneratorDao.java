/*
 * @(#)2013-6-23 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.common.generator.dao;

import org.springframework.stereotype.Repository;

import com.wellsoft.pt.common.generator.entity.IdGenerator;
import com.wellsoft.pt.core.dao.hibernate.HibernateDao;

/**
 * Description: 如何描述该类
 *  
 * @author rzhu
 * @date 2013-6-23
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-6-23.1	rzhu		2013-6-23		Create
 * </pre>
 *
 */
@Repository
public class IdGeneratorDao extends HibernateDao<IdGenerator, String> {

}
