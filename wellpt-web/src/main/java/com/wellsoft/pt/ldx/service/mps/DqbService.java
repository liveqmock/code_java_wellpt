package com.wellsoft.pt.ldx.service.mps;

import java.util.List;
import java.util.Map;

import com.wellsoft.pt.core.service.BaseService;
import com.wellsoft.pt.ldx.model.mainData.WlGc;
import com.wellsoft.pt.ldx.model.mps.Dqbview;



@SuppressWarnings("rawtypes")
public interface DqbService  extends BaseService{
	
	@SuppressWarnings("rawtypes")
	public Map deleteObjects(String qids);//删除
	@SuppressWarnings("rawtypes")
	public Dqbview getObject(String qid);// 查找
	@SuppressWarnings("rawtypes")
	public List<Dqbview> getObjects(String sql);//根据查询语句在查找数据
	@SuppressWarnings("rawtypes")
	public String addobjects(List<Dqbview>dqbview);//修改或则添加 
	@SuppressWarnings("rawtypes")
	public String addorupdateobject(String sql);//修改或则添加
	public String getQuestion_id(Dqbview e);//传递一个Dqbview 对象 获取刚刚插入的对象的数据的question_id
	public String gettype(String s);
	public Map savedqb(Dqbview dqb);
	public String getmeaning(String s);

}
