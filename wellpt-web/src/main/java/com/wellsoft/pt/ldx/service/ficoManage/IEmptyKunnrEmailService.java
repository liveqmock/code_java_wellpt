package com.wellsoft.pt.ldx.service.ficoManage;

import java.util.List;
import java.util.Map;

import com.wellsoft.pt.core.service.BaseService;

public interface IEmptyKunnrEmailService extends BaseService {
	
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
	 * 修改备注
	 * 
	 * @param zbelnr
	 * @param ztext
	 * @return
	 */
	public Map<?, ?> saveText(String zbelnr,String ztext);
	
}

