package com.wellsoft.pt.ldx.service.ficoManage;

import java.util.List;

import com.wellsoft.pt.ldx.model.ficoManage.Zfmt0003;
import com.wellsoft.pt.ldx.model.ficoManage.Zfmt0004;
import com.wellsoft.pt.ldx.service.ISapService;

public interface IFicoManageService extends ISapService {

	/**
	 * 
	 * 根据流水号查找到款信息
	 * 
	 * @param flowNum
	 * @return
	 */
	public Zfmt0003 findAcctByFlowNum(String flowNum);

	/**
	 * 
	 * 根据工号查找用户名
	 * 
	 * @param zsname
	 * @return
	 */
	public String findUserNameByCode(String zsname);

	/**
	 * 
	 * 根据流水号查询到账分解列表
	 * 
	 * @param flowNum
	 * @return
	 */
	public List<Zfmt0004> findSepAcctsByFlowNum(String flowNum);

	/**
	 * 
	 * 保存操作日志信息
	 * 
	 * @param user 用户
	 * @param type 操作类型
	 * @param string 日志信息
	 */
	public void saveLog(String user, String type, String record);
	
	/**
	 * 业务员提交前校验到账信息分解表收款金额汇总值等于到账总表中的收款金额
	 * @param flowNum
	 * @throws Exception
	 */
	public void checkAcctBalance(String flowNum) throws Exception;

	/**
	 * 业务员提交到账信息
	 * @param flowNum
	 * @return
	 * @throws Exception
	 * 
	 * 应收款、其他应收款提交顺序为:PP(应收确认)->FF(业务提交)->CC(生成凭证)
	 * 预收、样品提交顺序为:PP(信息登记)->PF(业务提交)->PC(财务确认)->FC(预收分解)->CC(生成凭证)
	 */
	public void submitAcctYw(String flowNum) throws Exception;
	
	/**
	 * 根据凭证编号（流水号）更新到账信息状态
	 * @param flowNum
	 * @return
	 */
	public void updateAcctStatusByFlowNum(String flowNum) throws Exception;
	
	/**
	 * 编辑预收分解信息时,校验当前内容是否是可维护状态
	 * @param zbelnr
	 * @param zposnr
	 * @throws Exception
	 */
	public void checkStatusBeforEdit(String zbelnr,String zposnr) throws Exception;
	
	/**
	 * 删除预收分解信息
	 * @param zfmt0004
	 * @return
	 */
	public void deleteZfmt0004(Zfmt0004 zfmt0004) throws Exception;
	
	/**
	 * 新增预收分解信息
	 * @param zfmt0004
	 * @return
	 */
	public String saveZfmt0004(Zfmt0004 zfmt0004) throws Exception;
	
	/**
	 * 更新当前行项父信息
	 * @param zbelnr
	 * @param zpsonr
	 * @throws Exception
	 */
	public void updateParentAcct(String zbelnr,String zpsonr) throws Exception;
	
	/**
	 * 修改预收分解信息
	 * @param zfmt0004
	 * @return
	 */
	public void updateZfmt0004(Zfmt0004 zfmt0004) throws Exception;
	
	/**
	 * 新增未开票通知记录
	 * @param zbelnr 流水号
	 * @param vbeln 外向交货单
	 * @throws Exception
	 */
	public void saveZounrNotExistInfo(String zbelnr,String vbeln,boolean deleteZetime) throws Exception;
	
	/**
	 * 校验当前流水号对应的外向交货单是否已经提醒财务开票
	 * @param zbelnr
	 * @param vbeln
	 * @throws LdxPortalException
	 */
	public boolean checkZounrInfoExist(String zbelnr,String vbeln) throws Exception;
	
	/**
	 * 上传单条外向交货单数据
	 * @param sortl 客户简称 
	 * @param zbelnr 流水号
	 * @param ecis ECIS号
	 * @param vbeln 外向交货单
	 * @param zhc 手续费
	 * @param zcamount 本次收款金额
	 * @param kunnr 客户编号
	 * @param bukrs 公司代码
	 */
	public String uploadSingleReceive(String sortl,String zbelnr,String ecis,String vbeln,Double zhc,Double zcamount,String kunnr,String bukrs);
	
	/**
	 * 根据凭证编号（流水号）及行项，查询预收分解信息
	 * @param zbelnr
	 * @param zposnr
	 * @return
	 */
	public Zfmt0004 findSepAcctByZbelnrAndZposnr(String zbelnr,String zposnr) ;
	
	/**
	 * 校验当前预收分解信息冲销金额不能大于本次收款金额
	 * @param zbelnr
	 * @param zpsonr
	 * @throws LdxPortalException
	 */
	public void checkParentAcctBalance(String zbelnr,String zpsonr) throws Exception;
	
	/**
	 * 业务员分解后提交到账信息
	 * @param flowNum
	 * @return
	 * @throws LdxPortalException
	 * 
	 * 应收款、其他应收款提交顺序为:PP(应收确认)->FF(业务提交)->CC(生成凭证)
	 * 预收、样品提交顺序为:PP(信息登记)->PF(业务提交)->PC(财务确认)->FC(预收分解)->CC(生成凭证)
	 */
	public void submitAcctSep(String flowNum) throws Exception;
	
	/**
	 * 查询会计凭证明细
	 * @param belnr
	 * @param bukrs
	 * @param gjahr
	 * @return
	 * @throws LdxPortalException
	 */
	public Object[] getBelnrDetail(String belnr,String bukrs,String gjahr);
	
	/**
	 * 校验当前账户是否为手工导入数据
	 * @param zbelnr
	 * @throws Exception
	 */
	public void checkManualAcct(String zbelnr) throws Exception;

	/**
	 * 财务退回
	 * @param flowNum
	 * @return
	 * @throws LdxPortalException
	 */
	public void rejectAcct(String flowNum) throws Exception;

	/**
	 * 
	 * 根据流水号更新当前到账信息应收会计字段(zpname)
	 * 
	 * @param flowNum
	 */
	public void updateZPnameByZbelnr(String flowNum) throws Exception;
	
	/**
	 * 修改到账信息
	 * @param zbelnr
	 * @param type
	 * @param value
	 * @throws Exception
	 */
	public void updateZfmt0003(String zbelnr,String type,String value) throws Exception;
	
	/**
	 * 根据凭证编号(流水号)生成凭证：针对整单
	 * @param zbelnr
	 * @throws Exception
	 */
	public void certOne(String zbelnr) throws Exception;
	
	/**
	 * 
	 * 生成凭证1前校验币种
	 * 
	 * @param zbelnr
	 * @throws Exception
	 */
	public void checkWaersBeforeCert1(String zbelnr) throws Exception;
	
	/**
	 * 生成凭证1接口调用前,公司代码
	 * @param zbelnr
	 * @throws Exception
	 */
	public void checkBukrsBeforeCert1(String zbelnr) throws Exception;
	
	/**
	 * 调用SAP生成凭证接口:ZFMI0001
	 * @param zbelnr
	 * @param zpsonr
	 * @return
	 * @throws Exception
	 */
	public List<String> generateCert(String zbelnr,String zpsonr) throws Exception;
	
	/**
	 * 根据凭证编号(流水号)手工生成凭证
	 * @param zbelnr
	 * @param belnr
	 * @throws Exception
	 */
	public void certManual(String zbelnr,List<String> belnr) throws Exception;
	
	/**
	 * 生成冲销凭证接口调用前,校验当前预收款币种与到账信息是否一致
	 * @param zbelnr
	 * @param zposnr
	 * @throws LdxPortalException
	 */
	public void checkWaersBeforeCert2(String zbelnr,String zposnr) throws Exception;
	
	/**
	 * 根据凭证编号(流水号)、及行项生成凭证：针对单笔预收分解
	 * @param zbelnr
	 * @param zpsonr
	 * @throws Exception
	 */
	public String certTwo(String zbelnr,String zposnr) throws Exception;
	
	/**
	 * 根据凭证编号(流水号),行项 手工冲销预收
	 * @param zbelnr
	 * @param belnr
	 * @param zposnr
	 * @throws Exception
	 */
	public void saveReceiveFlushManual(String zbelnr,String belnr,String zposnr) throws Exception;
	
	/**
	 * 财务分解退回
	 * @param zbelnr
	 * @throws Exception
	 */
	public void sepratePushBack(String zbelnr) throws Exception;

	/**
	 * 
	 * 为财务凭证界面查找到款分解信息
	 * 
	 * @param flowNum
	 * @return
	 */
	public List<Zfmt0004> findReceivedForCert(String flowNum);
	
	/**
	 * 校验当前预收分解信息的凭证是否已经冲销
	 * @param zbelnr 流水号
	 * @param zposnr 行项
	 * @throws Exception
	 */
	public void checkReceiveBelnrFlush(String zbelnr,String zposnr) throws Exception;
	
	/**
	 * 回冲预收
	 * @param zbelnr
	 * @param zpsonr
	 * @throws Exception
	 */
	public void updateBackFlushReceive(String zbelnr,String zposnr) throws Exception;
	
	/**
	 * 校验当前到账信息的会计凭证是否已经冲销
	 * @param zbelnr 流水号
	 * @throws Exception
	 */
	public void checkFicoBelnrFlush(String zbelnr) throws Exception;
	
	/**
	 * 回冲凭证
	 * @param zbelnr
	 * @throws Exception
	 */
	public void updateBackFlushBelnr(String zbelnr) throws Exception;
	
	/**
	 * 回冲凭证并删除到账信息
	 * @param zbelnr
	 * @throws Exception
	 */
	public void updateBackFlushAndDoDelete(String zbelnr) throws Exception;
	
	/**
	 * 财务界面提醒功能
	 * @throws Exception
	 */
	public void saveRemindInfo() throws Exception;
	
	/**
	 * 发送邮件给业务员
	 * @param zbelnrs
	 * @param cc
	 * @param type 发送类型
	 * @throws Exception
	 */
	public void sendNoticeEmailToAe(String[] zbelnrs,String cc) throws Exception;
	
	/**
	 * 向用户发送邮件提醒
	 * @param userId
	 * @param zbelnrs
	 * @param cc
	 * @param type 发送类型
	 * @param from 访问者
	 * @param update 是否更新提醒状态
	 * @throws Exception
	 */
	public void sendNoticeToSingleAe(String userId,String[] zbelnrs,String cc,String type,String from,boolean update) throws Exception;
	
	/**
	 * 同步未收汇相关信息至zfmt0016表
	 */
	public String saveZfmt0016FromSapSys();
	
	/**
	 * 
	 * 每周保存未收汇报表历史记录
	 * 
	 * @throws Exception
	 */
	public void saveZfmt0016LogWeekly() throws Exception;
	
	/**
	 * 
	 * 每月保存未收汇报表历史记录
	 * 
	 * @throws Exception
	 */
	public void saveZfmt0016LogMonthly() throws Exception;
	
	/**
	 * 
	 * 定期发送未收汇报表
	 * 
	 * @throws Exception
	 */
	public void sendNoneReceivedReport() throws Exception;
	
	/**
	 * 同步客户对应表，数据来自sap最新维护的
	 * 
	 * @throws Exception
	 */
	public void updateCustomerTableFromSap() throws Exception;
}

