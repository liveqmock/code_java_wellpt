package com.wellsoft.pt.ldx.service.ficoManage;

import com.wellsoft.pt.ldx.service.ISapService;

public interface IFicoReportService extends ISapService {
	/**
	 * 发送客户编号为空的邮件给:"SALES@LEEDARSON.COM"
	 * @throws Exception
	 */
	void sendEmptyKunnrEmail() throws Exception;
	
	/**
	 * 向指定用户发送异常信息邮件
	 * @throws LdxPortalException
	 */
	void sendErrorMessageToAdmin(String to,String cc) throws Exception;
	
	/**
	 * 发送未开票提醒邮件
	 */
	void sendNoInvoiceEmail(String to,String cc);
	
	/**
	 * 发送资金管理报表
	 * @param to
	 * @param cc
	 */
	void sendAccountSummaryEmail(String to,String cc);
	
	/**
	 * 向单个业务员发送日报
	 * @param userId
	 * @param mail
	 * @param type
	 * @return
	 */
	boolean sendReportToSingleAEOrAA(String userId,String mail,String type);
	
	/**
	 * 向所有业务员发送日报
	 */
	void queryAllAeAndAAToSendReoprt();
	
	/**
	 * 向指定销售部门发送日报
	 */
	void sendDailyReoprtToSpecialDep(String dept);
	
	/**
	 * 向所销售部管理人员发送日报
	 */
	void sendReoprtToSellManagers(String to,String cc);
}

