package com.wellsoft.pt.ldx.customdyform.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wellsoft.pt.core.dao.hibernate.HibernateDao;
import com.wellsoft.pt.repository.dao.DbTableDao;

@Repository
public class SpecialDyformDao extends HibernateDao{
	
	@Autowired
	DbTableDao dbTableDao;
	
	public List<Map<String,Object>> sqlQuery(String sql) throws Exception{
		return dbTableDao.query(sql);
	}
	
}
