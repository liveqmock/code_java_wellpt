package com.wellsoft.pt.ldx.service.productionPlan;

import java.util.Map;

import com.wellsoft.pt.core.service.BaseService;

public interface IProductionWorkOrderService extends BaseService{
	/**
	 * 
	 * 删除工单计划量配置
	 * 
	 * @param params
	 */
	public void deleteWoConfigs(String params);
	
	/**
	 * 
	 * 更改工单计划量配置
	 * 
	 * @param kunnr
	 * @param zbemt
	 * @param waers
	 */
	public Map<?,?> updateWoConfig(String type,String key,String zjhl);
	
	/**
	 * 
	 * 保存工单计划量配置
	 * 
	 * @param kunnr
	 * @param zbemt
	 * @param waers
	 */
	public Map<?,?> saveWoConfig(String type,String key,String zjhl);
}
