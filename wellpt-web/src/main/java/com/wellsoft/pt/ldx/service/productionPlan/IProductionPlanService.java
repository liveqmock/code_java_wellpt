package com.wellsoft.pt.ldx.service.productionPlan;

import java.util.List;
import java.util.Map;

import com.wellsoft.pt.core.service.BaseService;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.ldx.model.productionPlan.PlanShare;
import com.wellsoft.pt.ldx.model.productionPlan.ZPPT0031;
import com.wellsoft.pt.ldx.model.productionPlan.ZPPT0033;
import com.wellsoft.pt.org.entity.User;

public interface IProductionPlanService extends BaseService {
	
	public void saveTest(String aufnr,String value);
	
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
	 * 分页查询生产订单列表
	 * 
	 * @param pageInfo
	 * @param map
	 */
	
	public List<Object[]> findProductionPlanByPage(PagingInfo pageInfo, Map<String, String> map,String order);
	/**
	 * 
	 * 根据生产订单号查询排产记录明细
	 * 
	 * @param aufnr
	 * @return
	 */
	public Map<?,?> findPlanDetail(String aufnr);
	
	/**
	 * 
	 * 分页查询主计划列表
	 * 
	 * @param type1 查询类型1
	 * @param value1 查询条件1
	 * @param type2 查询类型2
	 * @param value2 查询条件2
	 * @param type3 查询类型3
	 * @param value3From 查询条件3开始
	 * @param value3To 查询条件3结束
	 * @param type4 查询类型4
	 * @param value4 查询条件4
	 * @param sgdd 手工订单
	 * @param sgNum 生管编号
	 * @param currPage 当前页
	 * @param pageSize 每页大小
	 * @param order 排序
	 * @param planType 排产类型:0整灯计划,1部件计划
	 * @return
	 */
	public Map<?,?> searchPlanPage(String type1,String value1,String type2,String value2,String type3,String value3From,String value3To,String type4,String value4,String sgdd,String sgNum,String currPage,String pageSize,String order,String planType);
	
	/**
	 * 
	 * 生成下拉框选项
	 * 
	 * @param parentType 父选项类型
	 * @param parentValue 父选项值
	 * @param childType 子选项类型
	 * @return
	 */
	public Map<?,?> getSelectItemByParent(String parentType,String parentValue,String childType);
	
	/**
	 * 
	 * 查询负荷列表
	 * 
	 * @param startDay 开始时间
	 * @param endDay 结束时间
	 * @param gxb 工序别
	 * @param loadShare 线别共享
	 * @param zxh 线号
	 * @return
	 */
	public Map<?,?> searchLoads(String startDay,String endDay,String gxb,String loadShare,String zxh,String userType,String sgNum);
	
	/**
	 * 
	 * 计算剩余排产量
	 * 
	 * @param aufnr 生产订单号
	 * @return
	 */
	public Map<?,?> getRemainAmt(String aufnr);
	
	/**
	 * 
	 * 生成排产建议
	 * 
	 * @param aufnr 生产订单号
	 * @param startDay 开始日期
	 * @param endDay 结束日期 
	 * @param gxb 工序
	 * @param zxh 线号
	 * @param leftAmt 剩余排产量
	 * @param loadShare 线别共享
	 * @param gsType 工时类型
	 * @param zz 总装工时
	 * @param bz 包装工时
	 * @return
	 */
	public Map<?,?> generateAdvice(String aufnr,String startDay,String endDay,String gxb,String zxh,String leftAmt,String loadShare,String gsType,String zz,String bz);
	
	/**
	 * 
	 * 如何描述该方法
	 * 
	 * @param zscrwd 生产任务单号
	 * @param aufnr 生产订单
	 * @param zxh 线号
	 * @param ltxa1 工序别
	 * @param gstrp 生产日期
	 * @param gamng01 A时段计划产量
	 * @param gamng02 B时段计划产量
	 * @param gamng03 C时段计划产量
	 * @param gsType 工时类型
	 * @param zzDefalt 总装默认工时
	 * @param bzDefalt 包装默认工时
	 * @return
	 */
	public Map<?,?> saveSubPlan(String zscrwd,String aufnr,String zxh,String ltxa1,String gstrp,String gamng01,String gamng02,String gamng03,String gsType,String zzDefalt,String bzDefalt);
	
	/**
	 * 
	 * 修改生产计划动态表数据
	 * 
	 * @param planDetail
	 */
	public void updatePlanDetail(ZPPT0031 planDetail);
	
	/**
	 * 
	 * 添加生产计划
	 * 
	 * @param subPlan
	 * @return 生产任务单
	 */
	public String addSubPlan(ZPPT0033 subPlan);
	
	/**
	 * 
	 * 删除生产任务单
	 * 
	 * @param zscrwd 生产任务单号
	 * @param aufnr 生产订单
	 * @param gsType 工时类型
	 * @param zzDefalt 总装默认工时
	 * @param bzDefalt 包装默认工时
	 * @return
	 */
	public Map<?,?> deleteSubPlan(String zscrwd,String aufnr,String gsType,String zzDefalt,String bzDefalt);
	
	/**
	 * 
	 * 查找生产任务单
	 * 
	 * @param zscrwd
	 * @return
	 */
	public ZPPT0033 findSubPlanById(String zscrwd);
	
	/**
	 * 
	 * 对当前线别进行汇总操作
	 * 
	 * @param zxh 线号
	 * @param start 开始日期
	 * @return
	 */
	public Map<?,?> planGather(String zxh,String start);
	
	/**
	 * 
	 * 对当前线别数据进行汇总后自动重排操作
	 * 
	 * @param zxh 线号
	 * @param start 开始日期
	 * @param gsType 工时类型
 	 * @param zzDefalt 总装默认工时
	 * @param bzDefalt 包装默认工时
	 * @return
	 */
	public Map<?,?> planSeprate(String zxh,String start,String gsType,String zzDefalt,String bzDefalt);
	
	/**
	 * 
	 * 对当前线别及日期范围内排产记录进行整理操作
	 * 
	 * @param zxh
	 * @param start
	 * @param end
	 * @param gsType
	 * @param zzDefalt
	 * @param bzDefalt
	 * @return
	 */
	public Map<?,?> autoArrangeXbPlan(String zxh,String start,String end,String gsType,String zzDefalt,String bzDefalt);
	
	/**
	 * 
	 * 计算到期日
	 * 
	 * @param start
	 * @param add
	 * @return
	 */
	public Map<?,?> getEndDateInit(String start,String add);

	/**
	 * 
	 * 根据上传信息更新完工记录
	 * 
	 * @param wg
	 * @param gsType
	 * @param zzDefalt
	 * @param bzDefalt
	 */
	public void updateCompletePlans(String[] wg, String gsType, String zzDefalt, String bzDefalt) throws Exception ;

	/**
	 * 
	 * 删除零排产量记录
	 * 
	 * @param string
	 */
	public void deleteZeroPlan(String aufnr);
	
	/**
	 * 
	 * 手动刷新功能
	 * 
	 * @param type 1欠料 2MPS 3工单追加
	 * @return
	 */
	public Map<?,?> manualRefresh(String type);
	
	
	/**
	 * 
	 * 发布最新版本排产计划
	 * 
	 * @param zsg 生管
	 * @return
	 */
	public Map<?,?> publishAllPlan(String zsg);
	
	/**
	 * 
	 * 查询当前排产计划
	 * 
	 * @param map
	 * @return
	 */
	public List<PlanShare> getCurrentPlan(Map<String, Object> map);

	/**
	 *
	 * 获取主计划
	 * 
	 * @param aufnr
	 * @return
	 */
	public ZPPT0031 get(String aufnr);

	/**
	 * 
	 * 更新MPS
	 * 
	 * @param client
	 * @param vbeln
	 * @param posnr
	 * @param date
	 * @param desc
	 * @param zsg
	 * @param aufnr
	 * @return
	 */
	public String updateMpsDate(String client, String vbeln, String posnr, String date, String desc, String zsg,
			String aufnr);
	
	/**
	 * 
	 * 根据生产订单号及线号更新工单完工数量
	 * 
	 * @param aufnr 订单号
	 * @param zxh 线号
	 * @param wgAmt 完工数量
	 */
	public void updateCompleteCountForWorkOrder(String aufnr,String zxh,double wgAmt);
	
	/**
	 * 
	 * 创建新工单
	 * 
	 * @param aufnr 生产订单号
	 * @param zxh 线号
	 * @param gdAmt 工单数量
	 * @param publishDate 发布日期
	 * @param startDay 开始日期
	 */
	void createWorkOrder(String aufnr, String zxh, double gdAmt, String publishDate,String startDay,Integer index);

	/**
	 * 
	 * 根据HQL查询实体列表
	 * 
	 * @param string
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findByHql(String string);

	/**
	 * 
	 * 根据用户ID获取用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public User getCurrentUser(String userId);

	/**
	 * 
	 * 查询发布后的排产计划
	 * 
	 * @param map
	 * @return
	 */
	public List<PlanShare> findSharingPlanByPage(Map<String, Object> map);
	
	/**
	 * 
	 * 根据生管和日期查询MES报工数据
	 * 
	 * @param zsg
	 * @param zrq
	 * @return
	 */
	public List<Object[]> findMesFinishData(String zsg,String zrq);

	/**
	 * 
	 * 发布后调用MES存储过程
	 *
	 */
	public void callMesProcedureAfterPublish();
	
}