package com.wellsoft.pt.ldx.service.productionPlan.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.ldx.service.impl.SapServiceImpl;
import com.wellsoft.pt.ldx.service.productionPlan.IProductionWorkOrderService;
import com.wellsoft.pt.ldx.util.StringUtils;

/**
 * 
 * Description: 保额维护service
 *  
 * @author heshi
 * @date 2014-9-26
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-9-26 	heshi		2014-9-26		Create
 * </pre>
 *
 */
@Service
@Transactional
public class ProductionWorkOrderServiceImpl extends SapServiceImpl implements IProductionWorkOrderService{

	@Override
	public void deleteWoConfigs(String params) {
		if(StringUtils.isBlank(params))      //判断获取到的参数值是否为空,为空，直接返回，不做后续处理
			return;
		String [] array = params.split(";"); //定义一个字符串数组，将传入的参数值以‘；’隔开
		if(null==array || array.length ==0)  //判断获取到的数组 array 是否为空,为空，直接返回，不做后续处理
			return;
		for (String map: array) {
			String[] conifg = map.split(",");
			String key = conifg[0].trim();
			String type = conifg[1].trim();
			String delete;
			if(type.equals("生管")){
				delete = "delete from zppt0069 where aufnr='SG_" + key + "' and mandt=" +getClient() ;
			}else{
				delete = "delete from zppt0069 where aufnr='" + StringUtils.addLeftZero(key,12) + "' and mandt=" +getClient() ;
			}
			execSql(delete);
		}
	}

	@Override
	public Map<?, ?> updateWoConfig(String type, String key, String zjhl) {
		Map<String,String> map = new HashMap<String,String>();
		try {
			Double.parseDouble(zjhl);
		} catch (Exception e) {
			map.put("res","fail");
			map.put("msg","工单计划量必须为数值格式!");
			return map;
		}
		String sql ;
		if(type.equals("生管")){
			sql = "update zppt0069 set zjhl='"+zjhl+"' where aufnr='SG_" + key + "' and mandt=" +getClient() ;
		}else{
			sql = "update zppt0069 set zjhl='"+zjhl+"' where aufnr='" + StringUtils.addLeftZero(key,12) + "' and mandt=" +getClient() ;
		}
		try {
			execSql(sql);
			map.put("res","success");
			map.put("msg","保存成功!");
		} catch (Exception e) {
			map.put("res","fail");
			map.put("msg","保存失败!"+e.getMessage());
		}
		return map;
	}

	@Override
	public Map<?, ?> saveWoConfig(String type, String key, String zjhl) {
		Map<String,String> map = new HashMap<String,String>();
		if(StringUtils.isBlank(key)){
			map.put("res","fail");
			map.put("msg","生管或生产订单号不能为空!");
			return map;
		}
		try {
			Double.parseDouble(zjhl);
		} catch (Exception e) {
			map.put("res","fail");
			map.put("msg","工单计划量必须为数值格式!");
			return map;
		}
		String checksql;
		if(type.equals("生管")){
			checksql = "select 1 from zppt0069 where aufnr='SG_" + key + "' and mandt=" +getClient() ;
		}else{
			checksql = "select 1 from zppt0069 where aufnr='" + StringUtils.addLeftZero(key,12) + "' and mandt=" +getClient() ;
		}
		List<Object> list = findListBySql(checksql);
		if(null!=list&&list.size()>0){
			map.put("res","fail");
			map.put("msg","配置已存在,请勿重复添加!");
			return map;
		}
		
		String sql ;
		if(type.equals("生管")){
			sql = "insert into zppt0069(mandt,aufnr,zjhl) values("+getClient()+",'SG_" + key + "','" +zjhl+"')" ;
		}else{
			sql = "insert into zppt0069(mandt,aufnr,zjhl) values("+getClient()+",'" + StringUtils.addLeftZero(key,12) + "','" +zjhl+"')" ;
		}
		try {
			execSql(sql);
			map.put("res","success");
			map.put("msg","保存成功!");
		} catch (Exception e) {
			map.put("res","fail");
			map.put("msg","保存失败!"+e.getMessage());
		}
		return map;
	}

}
