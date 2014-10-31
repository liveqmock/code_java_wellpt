package com.wellsoft.pt.ldx.service.pdm;

import java.util.List;

import com.wellsoft.pt.core.service.BaseService;
@SuppressWarnings("rawtypes")
public interface DefaultValueService extends BaseService{
	public List query(String tableName,String whereSql);
	public List queryBySql(String sql);
	public int update(String tableName,String updateRow,String whereSql);
	public int delete(String tableName,String whereSql);
}
