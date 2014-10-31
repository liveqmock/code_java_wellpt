package com.wellsoft.pt.ldx.service.ficoManage;

import java.util.List;
import java.util.Map;

import com.wellsoft.pt.core.service.BaseService;
import com.wellsoft.pt.ldx.model.ficoManage.CustomerTable;

public interface ICustomerTableService extends BaseService {
	
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
	 * 删除客户对应表记录
	 * 
	 * @param params
	 */
	public void deleteCustomer(String params);
	
	/**
	 * 保存客户对应表
	 * 
	 * @param cust
	 * @return
	 */
	public Map<?, ?> saveCustomer(CustomerTable cust);
	
	/**
	 * 修改客户对应表
	 * 
	 * @param cust
	 * @return
	 */
	public Map<?, ?> updateCustomer(CustomerTable cust);
	
	/**
	 * 
	 * 检查当前客户是否存在
	 * 
	 * @param bukrs
	 * @param kunnr
	 * @return
	 */
	public String checkCustBukrsExist(String bukrs,String kunnr);

	/**
	 * 
	 * 查找客户对应表
	 * 
	 * @param bukrs
	 * @param kunnr
	 * @return
	 */
	public CustomerTable findCustomerTable(String bukrs, String kunnr);
	
}

