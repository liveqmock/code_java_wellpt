package com.wellsoft.pt.ldx.service.mps;

import java.util.List;
import java.util.Map;

import com.wellsoft.pt.core.service.BaseService;
import com.wellsoft.pt.ldx.model.mainData.WlGc;
import com.wellsoft.pt.ldx.model.mps.Dqoview;



@SuppressWarnings("rawtypes")
public interface DqoService  extends BaseService{
	
	@SuppressWarnings("rawtypes")
	public Map deleteObjects(String s);//删除
	@SuppressWarnings("rawtypes")
	public Dqoview getObject(String qid);// 查找
	@SuppressWarnings("rawtypes")
	public List<Dqoview> getObjects(String sql);//根据查询语句在查找数据
	@SuppressWarnings("rawtypes")
	public String addobjects(List<Dqoview>dqoview);//修改或则添加
	@SuppressWarnings("rawtypes")
	public String addobject();//修改或则添加
	@SuppressWarnings("rawtypes")
	public String addorupdateobject(String sql);//修改或则添加
	
}
