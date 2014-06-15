/*
 * @(#)2013-6-23 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.common.generator.service.impl;

import java.lang.annotation.Annotation;
import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.common.generator.dao.IdGeneratorDao;
import com.wellsoft.pt.common.generator.entity.IdGenerator;
import com.wellsoft.pt.common.generator.service.IdGeneratorService;
import com.wellsoft.pt.core.annotation.CommonEntity;
import com.wellsoft.pt.core.entity.IdEntity;
import com.wellsoft.pt.core.service.impl.BaseServiceImpl;

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
@Service
@Transactional
public class IdGeneratorServiceImpl extends BaseServiceImpl implements IdGeneratorService {

	@Autowired
	private IdGeneratorDao idGeneratorDao;

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.common.generator.service.IdGeneratorService#generate(java.lang.Class, java.lang.String)
	 */
	@Override
	public <ENTITY extends IdEntity> String generate(Class<ENTITY> entityClass, String pattern) {
		Annotation annotation = entityClass.getAnnotation(CommonEntity.class);
		if (annotation != null) {
			throw new RuntimeException("实体类[" + entityClass.getCanonicalName() + "]不是租户库中的类");
		}

		return create(entityClass.getCanonicalName(), pattern);
	}

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.common.generator.service.IdGeneratorService#generate(java.lang.String, java.lang.String)
	 */
	@Override
	public String generate(String tableName, String pattern) {
		return create(tableName, pattern);
	}

	/**
	 * @param entityClass
	 * @param pattern
	 * @return
	 */
	private <ENTITY> String create(String entityClassName, String pattern) {
		IdGenerator idGenerator = idGeneratorDao.get(entityClassName);
		if (idGenerator == null) {
			idGenerator = new IdGenerator();
			idGenerator.setEntityClassName(entityClassName);
			idGenerator.setPointer(1l);
		} else {
			idGenerator.setPointer(idGenerator.getPointer() + 1);
		}
		idGeneratorDao.save(idGenerator);

		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		return decimalFormat.format(idGenerator.getPointer());
	}

}
