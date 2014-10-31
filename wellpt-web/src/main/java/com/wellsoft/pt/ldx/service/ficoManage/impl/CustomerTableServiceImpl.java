package com.wellsoft.pt.ldx.service.ficoManage.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.ldx.model.ficoManage.CustomerTable;
import com.wellsoft.pt.ldx.service.ficoManage.ICustomerTableService;
import com.wellsoft.pt.ldx.service.impl.SapServiceImpl;
import com.wellsoft.pt.ldx.util.StringUtils;
import com.wellsoft.pt.org.entity.User;

/**
 * 
 * Description: 客户对应表维护service
 *  
 * @author HeShi
 * @date 2014-8-25
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-8-25 	HeShi		2014-8-25		Create
 * </pre>
 *
 */
@Service
@Transactional
public class CustomerTableServiceImpl extends SapServiceImpl implements ICustomerTableService{
	/**
	 *  删除客户记录
	 */
	@Override
	public void deleteCustomer(String params){
		if(StringUtils.isBlank(params))      //判断获取到的参数值是否为空,为空，直接返回，不做后续处理
			return;
		String[] array = params.split(";");
		if(null==array||array.length==0)
			return;
		for (String map: array) {
			String[] conifg = map.split(",");
			String delete = "delete from zfmt0007 where bukrs='" +conifg[0] + "' and kunnr='"
					+ conifg[1] + "' and mandt=" +getClient() ;
			execSql(delete);
		}
	}

	@Override
	public Map<?, ?> saveCustomer(CustomerTable cust) {
		Map<String,String> map = new HashMap<String,String>();
		if(StringUtils.isBlank(cust.getBukrs())||StringUtils.isBlank(cust.getKunnr())){
			map.put("res","fail");
			map.put("msg","公司代码及客户编号不能为空值!");			
			return map;
		}
		if(StringUtils.isNotBlank(checkCustBukrsExist(cust.getBukrs(),cust.getKunnr()))){
			map.put("res","fail");
			map.put("msg","当前公司代码及客户编号已存在,请勿重复添加!");			
			return map;
		}
		String error = checkCustTableUser(cust);
		if(StringUtils.isNotBlank(error)){
			map.put("res","fail");
			map.put("msg",error);			
			return map;
		}
		StringBuffer insert = new StringBuffer("insert into zfmt0007(mandt,bukrs,kunnr,sortl,zrsm,zom,zaa,zae,zrname,zday) values("+getClient());
		insert.append(",'"+cust.getBukrs())
			.append("','"+cust.getKunnr())
			.append("','").append(StringUtils.isBlank(cust.getSortl())?" ":cust.getSortl())
			.append("','").append(StringUtils.isBlank(cust.getZrsm())?" ":findUserCodeById(cust.getZrsm()))
			.append("','").append(StringUtils.isBlank(cust.getZom())?" ":findUserCodeById(cust.getZom()))
			.append("','").append(StringUtils.isBlank(cust.getZaa())?" ":findUserCodeById(cust.getZaa()))
			.append("','").append(StringUtils.isBlank(cust.getZae())?" ":findUserCodeById(cust.getZae()))
			.append("','").append(StringUtils.isBlank(cust.getZr())?" ":findUserCodeById(cust.getZr()))
			.append("','").append(StringUtils.isBlank(cust.getZday())?"0":cust.getZday())
			.append("')");
		execSql(insert.toString());
		map.put("res","success");
		map.put("msg","保存成功!");			
		return map;
	}

	@Override
	public Map<?, ?> updateCustomer(CustomerTable cust) {
		Map<String,String> map = new HashMap<String,String>();
		String error = checkCustTableUser(cust);
		if(StringUtils.isNotBlank(error)){
			map.put("res","fail");
			map.put("msg",error);			
			return map;
		}
		StringBuffer update = new StringBuffer("update zfmt0007 set mandt=mandt");
		update.append(",sortl='").append(StringUtils.isBlank(cust.getSortl())?" ":cust.getSortl()).append("'")
			.append(",zrsm='").append(StringUtils.isBlank(cust.getZrsm())?" ":findUserCodeById(cust.getZrsm())).append("'")
			.append(",zom='").append(StringUtils.isBlank(cust.getZom())?" ":findUserCodeById(cust.getZom())).append("'")
			.append(",zaa='").append(StringUtils.isBlank(cust.getZaa())?" ":findUserCodeById(cust.getZaa())).append("'")
			.append(",zae='").append(StringUtils.isBlank(cust.getZae())?" ":findUserCodeById(cust.getZae())).append("'")
			.append(",zrname='").append(StringUtils.isBlank(cust.getZr())?" ":findUserCodeById(cust.getZr())).append("'")
			.append(",zday='").append(StringUtils.isBlank(cust.getZday())?"0":cust.getZday()).append("'")
			.append(" where mandt=").append(getClient())
			.append(" and bukrs='").append(cust.getBukrs()).append("'")
			.append(" and kunnr='").append(cust.getKunnr()).append("'");
		execSql(update.toString());
		map.put("res","success");
		map.put("msg","保存成功!");			
		return map;
	}
	
	/**
	 * 
	 * 校验不能选择多个人员
	 * 
	 * @param cust
	 * @return
	 */
	private String checkCustTableUser(CustomerTable cust){
		String error = "";
		if(StringUtils.isNotBlank(cust.getZrsm())&&cust.getZrsm().indexOf(";")>-1){
			error = "只能选择一个人作为RSM";
		}else if(StringUtils.isNotBlank(cust.getZom())&&cust.getZom().indexOf(";")>-1){
			error = "只能选择一个人作为OM";
		}else if(StringUtils.isNotBlank(cust.getZaa())&&cust.getZaa().indexOf(";")>-1){
			error = "只能选择一个人作为AA";
		}else if(StringUtils.isNotBlank(cust.getZae())&&cust.getZae().indexOf(";")>-1){
			error = "只能选择一个人作为AE";
		}else if(StringUtils.isNotBlank(cust.getZr())&&cust.getZr().indexOf(";")>-1){
			error = "只能选择一个人作为应收人员";
		}
		return error;
	}

	@Override
	public String checkCustBukrsExist(String bukrs, String kunnr) {
		String sql = "select 1 from zfmt0007 where mandt="+getClient()+" and bukrs='"+StringUtils.nullToString(bukrs)+"' and kunnr='"+StringUtils.nullToString(kunnr)+"'";
		List<Object> list = findListBySql(sql);
		if(null!=list && list.size()>0){
			return "当前客户编号及公司代码已存在!";
		}
		return "";
	}

	@Override
	public CustomerTable findCustomerTable(String bukrs, String kunnr) {
		CustomerTable customerTable = null;
		if(StringUtils.isBlank(bukrs)||StringUtils.isBlank(kunnr))
			return customerTable;
		String sql = "with userinfo as ("
			+ "select distinct mandt,pernr,ename from pa0001)"
			+ " select a.mandt,a.bukrs,a.kunnr,a.sortl,a.zrsm,t1.ename as rsm,a.zom,t2.ename as om,a.zaa,t3.ename as aa,a.zae,t4.ename as ae,a.zrname,t5.ename as rname,a.zday "
			+ " from zfmt0007 a"
			+ " left join userinfo t1 on a.mandt = t1.mandt and a.zrsm = t1.pernr"
			+ " left join userinfo t2 on a.mandt = t2.mandt and a.zom = t2.pernr"
			+ " left join userinfo t3 on a.mandt = t3.mandt and a.zaa = t3.pernr"
			+ " left join userinfo t4 on a.mandt = t4.mandt and a.zae = t4.pernr"
			+ " left join userinfo t5 on a.mandt = t5.mandt and a.zrname = t5.pernr"
			+ " where a.mandt="+getClient()
			+ " and a.bukrs='"+bukrs.trim()+"'"
			+ " and a.kunnr='"+kunnr.trim()+"'";
		List<Object> list = findListBySql(sql);
		if(null!=list&&list.size()>0){
			customerTable = new CustomerTable();
			Object[] cust = (Object[]) list.get(0);
			customerTable.setMandt(StringUtils.nullToString(cust[0]));
			customerTable.setBukrs(StringUtils.nullToString(cust[1]));
			customerTable.setKunnr(StringUtils.nullToString(cust[2]));
			customerTable.setSortl(StringUtils.nullToString(cust[3]));
			customerTable.setZrsm(StringUtils.nullToString(cust[4]));
			customerTable.setZrsmName(StringUtils.nullToString(cust[5]));
			customerTable.setZom(StringUtils.nullToString(cust[6]));
			customerTable.setZomName(StringUtils.nullToString(cust[7]));
			customerTable.setZaa(StringUtils.nullToString(cust[8]));
			customerTable.setZaaName(StringUtils.nullToString(cust[9]));
			customerTable.setZae(StringUtils.nullToString(cust[10]));
			customerTable.setZaeName(StringUtils.nullToString(cust[11]));
			customerTable.setZr(StringUtils.nullToString(cust[12]));
			customerTable.setZrName(StringUtils.nullToString(cust[13]));
			customerTable.setZday(StringUtils.nullToString(cust[14]));
		}
		return customerTable;
	}
	
	/**
	 * 
	 * 根据LCP用户ID查找工号
	 * 
	 * @param id
	 * @return
	 */
	private String findUserCodeById(String id){
		String userCode = id;
		if(StringUtils.isBlank(id))
			return userCode;
		List<User> ul = dao.findBy(User.class,"id",id);
		if(null!=ul&&ul.size()>0){
			userCode = ul.get(0).getCode();
		}
		return userCode;
	}

}
