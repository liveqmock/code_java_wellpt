package com.wellsoft.pt.ldx.service.productionPlan;

import java.util.List;
import java.util.Map;

import com.wellsoft.pt.core.service.BaseService;

public interface IProductionLineCapacityService extends BaseService {
	
	/**
	 * 
	 * 查找标准产能
	 * 
	 * @param zxh 线号
	 * @param zrq 日期
	 * @return
	 */
	public List<String[]> findLineCapacityByInfo(String zxh,String zrq);

	/**
	 * 
	 * 删除标准产能
	 * 
	 * @param params json格式参数
	 */
	public void deleteLineCapacity(String params);
	
	/**
	 * 
	 * 复制标准产能
	 * 
	 * @param zsg 生管
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return
	 */
	public Map<?,?> copyLineCapacity(String zsg,String startDate,String endDate);
	
	/**
	 * 
	 * 批量删除标准产能
	 * 
	 * @param zsg 生管
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return
	 */
	public Map<?,?> batchDeleteLineCapacity(String zsg,String startDate,String endDate);
	
	/**
	 * 
	 * 调整标准产能
	 * 
	 * @param zsg 生管
	 * @param date 日期
	 * @param zxs 系数
	 * @return
	 */
	public Map<?,?> adjustLineCapacity(String zsg,String date,String zxs);
	
	/**
	 * 
	 * 保存标准产能
	 * 
	 * @param dwerks 工厂
	 * @param department 部门
	 * @param clazz 课别
	 * @param zxh 线号
	 * @param zsg 生管
	 * @param zrq 日期
	 * @param ltxa1 工序别
	 * @param gamng1 标准产能A
	 * @param gamng2 标准产能B 
	 * @param gamng3 标准产能C
	 * @param zxbbbxs 线别报表显示
	 * @param zcxdm 产线ID
	 * @return
	 */
	 
	public Map<?, ?> saveLineCapacity(String dwerks,String department,String clazz,String zxh,String zsg,String zrq,String ltxa1,String gamng1,String gamng2,String gamng3,String zxbbbxs,String zcxdm);
	
	/**
	 * 
	 * 更新标准产能
	 * 
	 * @param dwerks 工厂
	 * @param department 部门
	 * @param clazz 课别
	 * @param zxh 线号
	 * @param zsg 生管
	 * @param zrq 日期
	 * @param ltxa1 工序别
	 * @param gamng1 标准产能A
	 * @param gamng2 标准产能B 
	 * @param gamng3 标准产能C
	 * @param zxbbbxs 线别报表显示
	 * @param zcxdm 产线ID
	 * @return
	 */
	public Map<?, ?> updateLineCapacity(String dwerks,String department,String clazz,String zxh,String zsg,String zrq,String ltxa1,String gamng1,String gamng2,String gamng3,String zxbbbxs,String zcxdm);
}
