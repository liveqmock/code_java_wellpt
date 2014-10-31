package com.wellsoft.pt.repository.entity.mongo;

import com.wellsoft.pt.security.core.context.TenantContextHolder;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

public  class SessionInfo {
	public static String getCurrentUserId(){
		String userId = "";
		try{
			userId = SpringSecurityUtils.getCurrentUserId();;
		}catch (Exception e) {
			userId = "hunt";
		}
	 
		return userId;
	}
	
	
	public static String getTenantId(){
		String tenantId = "";
		try{
			tenantId = TenantContextHolder.getTenantId();
		}catch (Exception e) {
			tenantId = "tenant";
		}
		 
		return tenantId ;
	}
	
	
	 
	
}
