package com.wellsoft.pt.ldx.service.ficoManage;

import java.util.List;
import java.util.Map;

import com.wellsoft.pt.core.service.BaseService;

public interface IDefaultPaymentDataService extends BaseService{
	
	/**
	 * 
	 * 删除付款条件
	 * @param params
	 */
	public void deletePayment(String params);
	
	/**
	 * 
	 * 更新付款条件
	 * @param zterm
	 * @return
	 */
	public Map<?,?> updatePayment(String zterm,String bukrs,String kunnr,String zvtext,String zdeadline,String waers,String zearea,String zkdgrp);
	
	/**
	 * 
	 * 保存付款条件
	 * @param zterm
	 * @return
	 */
	public Map<?,?> savePayment(String zterm,String bukrs,String kunnr,String zvtext,String zdeadline,String waers,String zearea,String zkdgrp);
	
	/**
	 * 
	 * sql语句查询
	 * 在实现的方法中，继承的父类存在同名方法
	 * 
	 * @param sql
	 * @return 结果列表
	 */
	public List<Object> findListBySql(String sql);
	
}
