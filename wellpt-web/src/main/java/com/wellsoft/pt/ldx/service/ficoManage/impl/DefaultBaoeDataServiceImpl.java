package com.wellsoft.pt.ldx.service.ficoManage.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.ldx.service.ficoManage.IDefaultBaoeDataService;
import com.wellsoft.pt.ldx.service.impl.SapServiceImpl;
import com.wellsoft.pt.ldx.util.StringUtils;

/**
 * 
 * Description: 保额维护service
 *  
 * @author qiulb
 * @date 2014-8-7
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-8-7 	qiulb		2014-8-7		Create
 * </pre>
 *
 */
@Service
@Transactional
public class DefaultBaoeDataServiceImpl extends SapServiceImpl implements IDefaultBaoeDataService{
	/**
	 *  删除保额记录
	 */
	@Override
	public void deleteBaoe(String params){
		if(StringUtils.isBlank(params))      //判断获取到的参数值是否为空,为空，直接返回，不做后续处理
			return;
		String [] array = params.split(";"); //定义一个字符串数组，将传入的参数值以‘；’隔开
		if(null==array || array.length ==0)  //判断获取到的数组 array 是否为空,为空，直接返回，不做后续处理
			return;
		//循环删除选中的客户保额信息
		for (String map: array){
			String delete = "delete from zfmt0016_be where kunnr = '" + StringUtils.addLeftZero(map, 10) + "' and mandt = " + getClient() ;
			execSql(delete);
		}
	} 
	
	/**
	 * 更新保额记录
	 */
	@Override
	public Map<?,?> updateBaoe(String kunnr,String zbemt,String waers){
		Map<String,String> map = new HashMap<String,String>();
		String sql = "update ZFMT0016_BE set zbemt = '" + zbemt + "', waers = '" + waers + "' where kunnr = '" 
		+ StringUtils.addLeftZero(kunnr, 10) +"'";
		execSql(sql);
		map.put("res","success");
		map.put("msg","更新成功!");
	
		return map;
	}
	
	/**
	 * 创建保额记录
	 */
	public void createBaoe(String kunnr,String zbemt,String waers){
		
	}
	
	/**
	 * 保存保额记录
	 */
	@Override
	public Map<?,?> saveBaoe(String kunnr,String zbemt,String waers){
		Map<String,String> map = new HashMap<String,String>();
		if(StringUtils.isBlank(kunnr)){
			map.put("res","fail");
			map.put("msg","客户ID不能为空值!");			
			return map;
		}
		
		String sql = "insert into ZFMT0016_BE(MANDT,KUNNR,ZBEMT,WAERS) values('" + getClient() + "','" + StringUtils.addLeftZero(kunnr,10) + "','"
			+ zbemt + "','" + waers + "')";
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
	
	/**
	 *  联动获取客户简称
	 */
	@Override
	public Map<?,?> getSortl(String kunnr){
		Map<String,String> map = new HashMap<String,String>();
		
		if(! StringUtils.isBlank(kunnr)){
			String sql1 = "select kunnr from kna1 where mandt =" + getClient() + " and kunnr = '" +StringUtils.addLeftZero(kunnr, 10) + "'";
			List<Object> lit1 = findListBySql(sql1);
			
			if (lit1 != null && lit1.size() > 0){
				String sql2 = "select sortl from kna1 where mandt = " + getClient() + " and kunnr = '" + StringUtils.addLeftZero(kunnr, 10) + "'";
				List<Object> lit2 = findListBySql(sql2);
				
				if (lit2 != null && lit2.size() > 0){
					map.put("sortl",lit2.get(0).toString());			    
				}
			}else{
				map.put("mess", "该客户在SAP中不存在，请确认输入！");
			}
			
		}else{
			map.put("sortl", "");
		}
		return map;
	}

}
