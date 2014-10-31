package com.wellsoft.pt.ldx.service.productionPlan;

import java.util.List;
import java.util.Map;

import com.wellsoft.pt.core.service.BaseService;

public interface IProductionLineManagerService extends BaseService {
	
	/**
	 * 
	 * 查找生管信息
	 * 
	 * @param zsg 生管
	 * @param zoauser OA用户
	 * @return
	 */
	public List<String[]> findLineManagerByInfo(String zsg,String zoauser);

	/**
	 * 
	 * 删除生管信息
	 * 
	 * @param params 
	 */
	public void deleteLineManager(String params);
	
	/**
	 * 
	 * 保存生管信息
	 * 
	 * @param zsg 生管
	 * @param zoauser OA用户
	 * @param zkz 课长
	 * @param verid 生产版本
	 * @param matkl 物料组
	 * @param atwrt 特性值
	 * @param dwerks 工厂
	 * @param zzz 组长
	 * @return
	 */
	public Map<?, ?> saveLineManager(String zsg,String zoauser,String zkz,String verid,String matkl,String atwrt,String dwerks,String zzz);
	
	/**
	 * 
	 * 更新生管信息
	 * 
	 * @param zsg 生管
	 * @param zoauser OA用户
	 * @param zkz 课长
	 * @param verid 生产版本
	 * @param matkl 物料组
	 * @param atwrt 特性值
	 * @param dwerks 工厂
	 * @param zzz 组长
	 */
	public Map<?, ?> updateLineManager(String zsg,String zoauser,String zkz,String verid,String matkl,String atwrt,String dwerks,String zzz);
}
