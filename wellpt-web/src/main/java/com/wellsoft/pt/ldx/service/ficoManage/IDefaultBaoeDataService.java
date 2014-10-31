package com.wellsoft.pt.ldx.service.ficoManage;

import java.util.List;
import java.util.Map;

import com.wellsoft.pt.core.service.BaseService;

public interface IDefaultBaoeDataService extends BaseService{
	/**
	 * 
	 * 删除客户保额数据
	 * 
	 * @param params
	 */
	public void deleteBaoe(String params);
	
	/**
	 * 
	 * 更改客户保额数据
	 * 
	 * @param kunnr
	 * @param zbemt
	 * @param waers
	 */
	public Map<?,?> updateBaoe(String kunnr,String zbemt,String waers);
	
	/**
	 * 
	 * 创建客户保额数据
	 * 
	 * @param kunnr
	 * @param zbemt
	 * @param waers
	 */
	public void createBaoe(String kunnr,String zbemt,String waers);
	
	/**
	 * 
	 * 保存客户保额数据
	 * 
	 * @param kunnr
	 * @param zbemt
	 * @param waers
	 */
	public Map<?,?> saveBaoe(String kunnr,String zbemt,String waers);
	
	/**
	 * 
	 * 联动获取客户简称
	 * 
	 * @param kunnr
	 */
	public Map<?,?> getSortl(String kunnr);
	
	/**
	 * 
	 * sql语句查询
	 * 
	 * @param sql
	 * @return 结果列表
	 */
	public List<Object> findListBySql(String sql);

}
