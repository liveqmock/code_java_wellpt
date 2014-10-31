package com.wellsoft.pt.repository.service;

import org.hibernate.dialect.*;
import org.hibernate.engine.spi.*;
import org.hibernate.mapping.*;

/**
 * 
* @ClassName: FieldManager
* @Description: 定义fieldmanager用于处理所有字段的定义
* @author lilin
 */
public interface FieldService {

	public void deleteField(String id);

	public boolean isIndex(String entityName, String fileldname);

	public boolean isShow(String entityName, String fileldname);

	public boolean isSort(String entityName, String fileldname);

	public void saveEntityField(Class clazz);

	public void saveTableField(Table table, Dialect dialect, Mapping mapping);

}
