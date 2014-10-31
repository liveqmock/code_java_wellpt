package com.wellsoft.pt.ldx.service.pdm.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.core.service.impl.BaseServiceImpl;
import com.wellsoft.pt.ldx.service.pdm.DefaultValueService;
import com.wellsoft.pt.ldx.util.StringUtils;

@Service
@Transactional
@SuppressWarnings("rawtypes")
public class DefaultValueServiceImpl extends BaseServiceImpl implements DefaultValueService{
	@Override
	public List query(String tableName,String whereSql){
		StringBuffer sql = new StringBuffer("select * from ")
		.append(tableName).append(" where ")
		.append(StringUtils.isNotEmpty(whereSql)?whereSql:"1=1");
		
		return this.dao.getSession().createSQLQuery(sql.toString()).list();
	}
	@Override
	public int update(String tableName,String updateRow,String whereSql){
		StringBuffer sql = new StringBuffer("update ")
		.append(tableName)
		.append(" set ").append(updateRow)
		.append(" where ")
		.append(StringUtils.isNotEmpty(whereSql)?whereSql:"1=1");
		return this.dao.getSession().createSQLQuery(sql.toString()).executeUpdate();
	}
	@Override
	public int delete(String tableName,String whereSql){
		StringBuffer sql = new StringBuffer("delete from ")
		.append(tableName)
		.append(" where ")
		.append(StringUtils.isNotEmpty(whereSql)?whereSql:"1=1");
		return this.dao.getSession().createSQLQuery(sql.toString()).executeUpdate();
	}
	@Override
	public List queryBySql(String sql){
		
		return this.dao.getSession().createSQLQuery(sql).list();
	}
}
