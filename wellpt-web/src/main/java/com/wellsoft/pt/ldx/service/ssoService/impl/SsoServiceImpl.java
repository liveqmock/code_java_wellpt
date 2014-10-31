package com.wellsoft.pt.ldx.service.ssoService.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.core.service.impl.BaseServiceImpl;
import com.wellsoft.pt.ldx.service.ssoService.ISsoService;

@Service
@Transactional
@SuppressWarnings("rawtypes")
public class SsoServiceImpl extends BaseServiceImpl implements ISsoService{
	@Override
	public List get(String lcpUserId){
		StringBuffer sql = new StringBuffer("select lcpuserid,username,password from USERFORM_LDX_SINGLE_LOGIN where 1=1 and ")
		.append(" lcpuserid='"+lcpUserId+"'");
		
		return this.dao.getSession().createSQLQuery(sql.toString()).list();
	}
	@Override
	public int save(String lcpUserId,String userName,String passWord){
		StringBuffer sql = new StringBuffer("insert into USERFORM_LDX_SINGLE_LOGIN(lcpuserid,username,password) ")
		.append(" values(")
		.append("'"+lcpUserId+"',")
		.append("'"+userName+"',")
		.append("'"+passWord+"')");
		return this.dao.getSession().createSQLQuery(sql.toString()).executeUpdate();
	}
	@Override
	public int update(String username,String oldpassword,String newpassWord){
		StringBuffer sql = new StringBuffer("update USERFORM_LDX_SINGLE_LOGIN set ")
		.append("password='"+newpassWord+"' ")
		.append(" where username='"+username+"' and password='")
		.append(oldpassword+"'");
		return this.dao.getSession().createSQLQuery(sql.toString()).executeUpdate();
	}

}
