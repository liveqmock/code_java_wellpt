package com.wellsoft.pt.ldx.service.ficoManage;

import java.util.List;
import java.util.Map;

import com.wellsoft.pt.core.service.BaseService;

public interface IBillTrackService extends BaseService {
	
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
	 * 批量开票确认
	 * 
	 * @param params
	 * @param type 开票类型 Y已开票 N未开票
	 * @return
	 */
	public Map<?,?> billConfirm(String params,String type);
	
	/**
	 * 
	 * 单条开票确认
	 * 
	 * @param zbelnr 流水号
	 * @param vbeln 外向交货单
	 * @param type 开票类型: Y已开票 N未开票
	 * @return
	 */
	public void confirmSingleBill(String zbelnr,String vbeln,String type);
	
	/**
	 * 
	 * 开票后发送提醒邮件
	 * 
	 * @param zbelnr 流水号
	 * @param vbeln 外向交货单
	 * @param noticeType 通知类型: Y已开票 N未开票
	 */
	public void sendEmailAfterBill(String zbelnr,String vbeln,String noticeType);
}

