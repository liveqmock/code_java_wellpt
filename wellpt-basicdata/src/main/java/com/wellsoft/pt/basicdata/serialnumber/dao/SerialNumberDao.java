package com.wellsoft.pt.basicdata.serialnumber.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.wellsoft.pt.basicdata.serialnumber.entity.SerialNumber;
import com.wellsoft.pt.core.dao.hibernate.HibernateDao;

/**
 * 
 * Description: 流水号定义数据层访问类
 *  
 * @author zhouyq
 * @date 2013-3-6
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-3-6.1	zhouyq		2013-3-6		Create
 * </pre>
 *
 */
@Repository
public class SerialNumberDao extends HibernateDao<SerialNumber, String> {

	/**
	 * 
	 * 通过流水号字段名查找所有流水号
	 * 
	 * @param fieldName
	 * @param entity
	 */
	public List<String> findByFieldName(String fieldName, Object entity) {
		String hql = "select " + fieldName + " from " + entity;
		Query query = getSession().createQuery(hql);
		List<String> list = query.list();
		return list;
	}

	public SerialNumber getById(String id) {
		return findUniqueBy("id", id);
	}
}
