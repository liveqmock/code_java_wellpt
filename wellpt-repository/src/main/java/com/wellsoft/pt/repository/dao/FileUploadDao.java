/*
 * @(#)2014-1-5 V1.0
 * 
 * Copyright 2014 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.repository.dao;

import org.springframework.stereotype.Repository;

import com.wellsoft.pt.core.dao.hibernate.HibernateDao;
import com.wellsoft.pt.repository.entity.FileUpload;

/**
 * Description: 如何描述该类
 *  
 * @author zhulh
 * @date 2014-1-5
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-1-5.1	zhulh		2014-1-5		Create
 * </pre>
 *
 */
@Repository
public class FileUploadDao extends HibernateDao<FileUpload, String> {

}
