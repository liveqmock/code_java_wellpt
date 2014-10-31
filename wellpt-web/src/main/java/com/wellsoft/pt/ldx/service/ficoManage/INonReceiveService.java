package com.wellsoft.pt.ldx.service.ficoManage;

import java.util.List;
import java.util.Map;

import com.wellsoft.pt.core.service.BaseService;
import com.wellsoft.pt.ldx.model.ficoManage.Zfmt0017;

public interface INonReceiveService extends BaseService {
	/**
	 * 
	 * 执行sql语句
	 * 
	 * @param sql
	 */
	public void execSql(String sql);

	/**
	 * 
	 * sql语句查询
	 * 
	 * @param sql
	 * @return 结果列表
	 */
	public List<Object> findListBySql(String sql);
	
	/**
	 * 
	 * 删除未收汇手工记录
	 * 
	 * @param params
	 */
	public void deleteDoc(String params);
	
	/**
	 * 
	 * 保存未收汇手工记录
	 * 
	 * @param doc
	 * @return
	 */
	public Map<?, ?> saveDoc(Zfmt0017 doc);
	
	/**
	 * 
	 * 修改未收汇手工记录
	 * 
	 * @param doc
	 * @return
	 */
	public Map<?, ?> updateDoc(Zfmt0017 doc);
	
	/**
	 * 
	 * 检查当前客户是否存在
	 * 
	 * @param bukrs
	 * @param kunnr
	 * @return
	 */
	public String checkVbelnExist(String vbeln);
	
	/**
	 * 
	 * 查找未收汇手工资料
	 * 
	 * @param vbeln
	 * @return
	 */
	public Zfmt0017 findDocByVbeln(String vbeln);

}

