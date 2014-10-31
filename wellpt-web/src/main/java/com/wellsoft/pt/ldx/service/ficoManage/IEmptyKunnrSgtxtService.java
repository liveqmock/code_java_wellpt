package com.wellsoft.pt.ldx.service.ficoManage;

import java.util.List;
import java.util.Map;

import com.wellsoft.pt.core.service.BaseService;

public interface IEmptyKunnrSgtxtService extends BaseService{
	
	/*
	 * 删除客户摘要信息
	 * 
	 * @params
	 */
	public void deleteSgtxt(String params);
	
	/*
	 * 更新未知客户表：ZFMT0019
	 * 
	 * @param sgtxt
	 * @param kunnr
	 * @param zcrem
	 */
	public Map<?,?> updateSgtxt(String sgtxt,String kunnr,String zcrem);
	
	/*
	 * 保存记录到未知客户表：ZFMT0019
	 * 
	 * @param sgtxt
	 * @param kunnr
	 * @param zcrem
	 */
	public Map<?,?> saveSgtxt(String sgtxt,String kunnr,String zcrem);
	
	/*
	 * 根据输入的客户代码获取客户编号
	 * 
	 * @sortl
	 */
	public Map<?,?> getKunnr(String sortl);
	
	/**
	 * 
	 * sql语句查询
	 * 
	 * @param sql
	 * @return 结果列表
	 */
	public List<Object> findListBySql(String sql);
	
}
