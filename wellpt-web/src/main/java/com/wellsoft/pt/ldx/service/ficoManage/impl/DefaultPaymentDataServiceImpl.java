package com.wellsoft.pt.ldx.service.ficoManage.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.ldx.service.ficoManage.IDefaultPaymentDataService;
import com.wellsoft.pt.ldx.service.impl.SapServiceImpl;
/**
 * 
 * Description: 付款条件维护service
 *  
 * @author qiulb
 * @date 2014-8-12
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-8-12 	qiulb		2014-8-12		Create
 * </pre>
 *
 */
@Service
@Transactional
public class DefaultPaymentDataServiceImpl extends SapServiceImpl implements IDefaultPaymentDataService{
	
	/**
	 *  删除付款条件
	 */
	public void deletePayment(String params){
		if(StringUtils.isBlank(params))      //判断获取到的参数值是否为空,为空，直接返回，不做后续处理
			return;
		String [] array = params.split(";"); //定义一个字符串数组，将传入的参数值以‘；’隔开
		if(null==array || array.length == 0) //判断获取到的数组 array 是否为空,为空，直接返回，不做后续处理
			return;
		
		//循环删除选中的付款条件
		for(String map : array){
			String delete = " delete from zfmt0008 where zterm = '" + map + "' and mandt = " + getClient();
			execSql(delete);
		}
	}
	
	/**
	 *  更新付款条件
	 */
	public Map<?,?> updatePayment(String zterm,String bukrs,String kunnr,String zvtext,String zdeadline,String waers,String zearea,String zkdgrp){
		Map<String,String> map = new HashMap<String,String>();
		//设置默认值
		bukrs = StringUtils.isBlank(bukrs)?" ":bukrs;
		kunnr = StringUtils.isBlank(kunnr)?" ":kunnr;
		zvtext = StringUtils.isBlank(zvtext)?" ":zvtext;
		zdeadline = StringUtils.isBlank(zdeadline)?"0":zdeadline;
		waers = StringUtils.isBlank(waers)?" ":waers;
		zearea = StringUtils.isBlank(zearea)?" ":zearea;		
		zkdgrp = StringUtils.isBlank(zkdgrp)?" ":zkdgrp;
		
		String update = " update zfmt0008 set bukrs = '" + bukrs + "', kunnr = '" + kunnr + "', zvtext = '" + zvtext +
		                "', zdeadline = '" + zdeadline + "', waers = '" + waers + "', zearea = '" + zearea + "', zkdgrp = '" + 
		                zkdgrp + "' where zterm = '" + zterm + "'";
		execSql(update);
		map.put("res","success");
		map.put("msg","更新成功!");
		
		return map;
	}
	
	/**
	 *  保存付款条件
	 */
	public Map<?,?> savePayment(String zterm,String bukrs,String kunnr,String zvtext,String zdeadline,String waers,String zearea,String zkdgrp){
		Map<String,String> map = new HashMap<String,String>();
		if(StringUtils.isBlank(zterm)){
			map.put("res","fail");
			map.put("msg","付款条件码不能为空值!");			
			return map;
		}
		//设置默认值
		bukrs = StringUtils.isBlank(bukrs)?" ":bukrs;
		kunnr = StringUtils.isBlank(kunnr)?" ":kunnr;
		zvtext = StringUtils.isBlank(zvtext)?" ":zvtext;
		zdeadline = StringUtils.isBlank(zdeadline)?"0":zdeadline;
		waers = StringUtils.isBlank(waers)?" ":waers;
		zearea = StringUtils.isBlank(zearea)?" ":zearea;
		zkdgrp = StringUtils.isBlank(zkdgrp)?" ":zkdgrp;
		
		//执行 insert 语句，向数据库表 zfmt0008 中插入一条新增的记录
		String insert = " insert into zfmt0008(MANDT,ZTERM,BUKRS,KUNNR,ZVTEXT,ZDEADLINE,WAERS,ZEAREA,ZKDGRP) values ( '" +
						getClient() + "','" + zterm + "','" + bukrs + "','" + kunnr + "','" + zvtext + "','" + zdeadline +
						"','" + waers + "','" + zearea + "','" + zkdgrp + "' )" ;
		execSql(insert);
		map.put("res","success");
		map.put("msg","创建成功!");
		
		return map;
	}
}
