package com.wellsoft.pt.ldx.service.ssoService;

import java.util.List;

import com.wellsoft.pt.core.service.BaseService;

@SuppressWarnings("rawtypes")
public interface ISsoService extends BaseService{
	public List get(String lcpUserId);
	public int save(String lcpUserId,String userName,String passWord);
	public int update(String username,String oldpassword,String newpassWord);
}
