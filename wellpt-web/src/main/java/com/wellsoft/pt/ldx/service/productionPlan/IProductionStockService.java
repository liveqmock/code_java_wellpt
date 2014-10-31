package com.wellsoft.pt.ldx.service.productionPlan;

import java.util.List;
import java.util.Map;

import com.wellsoft.pt.core.service.BaseService;

public interface IProductionStockService extends BaseService {
	
	/**
	 * 
	 * 查找库存信息
	 * 
	 * @param dwerks
	 * @param lgort
	 * @return
	 */
	public List<String[]> findStockByInfo(String dwerks,String lgort);

	/**
	 * 
	 * 删除可用库存
	 * 
	 * @param params
	 */
	public void deleteStocks(String params);
	
	/**
	 * 
	 * 保存可用库存
	 * 
	 * @param dwerks
	 * @param lgort
	 * @param lvorm
	 */
	public Map<?, ?> saveStocks(String dwerks,String lgort,String lvorm);
	
	/**
	 * 
	 * 更新可用库存
	 * 
	 * @param dwerks
	 * @param lgort
	 * @param lvorm
	 */
	public void updateStocks(String dwerks,String lgort,String lvorm);
}
