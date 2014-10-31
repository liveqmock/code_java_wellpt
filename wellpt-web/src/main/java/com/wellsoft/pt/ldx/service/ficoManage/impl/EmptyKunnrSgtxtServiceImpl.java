package com.wellsoft.pt.ldx.service.ficoManage.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.ldx.service.ficoManage.IEmptyKunnrSgtxtService;
import com.wellsoft.pt.ldx.service.impl.SapServiceImpl;
import com.wellsoft.pt.ldx.util.StringUtils;

/**
 * 
 * Description: 未知客户摘要信息维护service
 *  
 * @author qiulb
 * @date 2014-9-18
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-9-18 	qiulb		2014-9-18		Create
 * </pre>
 *
 */
@Service
@Transactional
public class EmptyKunnrSgtxtServiceImpl extends SapServiceImpl implements IEmptyKunnrSgtxtService{
	
	/*
	 * 删除未知客户摘要信息
	 */
	@Override
	public void deleteSgtxt(String params){
		if(StringUtils.isBlank(params))      //判断获取到的参数值是否为空,为空，直接返回，不做后续处理
			return;
		String [] array = params.split(";"); //定义一个字符串数组，将传入的参数值以‘；’隔开
		
		if(null==array || array.length == 0) //判断获取到的数组 array 是否为空,为空，直接返回，不做后续处理 
			return;
		
		for(String map:array){
			String delete = "delete from zfmt0019 where sgtxt = '" + map + "' and mandt = " + getClient();
			execSql(delete);
		}
	}
	
	/*
	 * 更新未知客户摘要信息
	 */
	@Override
	public Map<?,?> updateSgtxt(String sgtxt,String kunnr,String zcrem){
		Map<String,String> map = new HashMap<String,String>();
		if(StringUtils.isBlank(sgtxt)){
			map.put("res", "fail");
			map.put("msg", "摘要不能为空！");
			return map;
		}
		String update = "update zfmt0019 set sgtxt = '" + sgtxt.toUpperCase() + "',zcrem = '" + zcrem + "' where sgtxt = '"
		+ sgtxt + "'";
		execSql(update);
		map.put("res", "success");
		map.put("msg", "更新成功！");
		
		return map;
	}
	
	/*
	 * 保存位置客户摘要信息
	 */
	@Override
	public Map<?,?> saveSgtxt(String sgtxt,String kunnr,String zcrem){
		Map<String,String> map = new HashMap<String,String>();
		if(StringUtils.isBlank(sgtxt)){
			map.put("res", "fail");
			map.put("msg", "摘要不能为空！");
			return map;
		}
		String save = "insert into zfmt0019 (MANDT,SGTXT,KUNNR,ZCREM) values ('" + getClient() + "','" +
		sgtxt.toUpperCase() + "','" + StringUtils.addLeftZero(kunnr, 10) + "','" + zcrem + "')";
		execSql(save);
		map.put("res", "success");
		map.put("msg", "保存成功！");
		
		return map;			
	}
	
	/*
	 * 根据客户简称获取客户编号
	 */
	@Override
	public Map<?,?> getKunnr(String sortl){
		Map<String,String> map = new HashMap<String,String>();
		
		if (! StringUtils.isBlank(sortl)){
			String sql = "select kunnr from kna1 where mandt = " + getClient() + " and sortl = '" + sortl + ",";
			List<Object> list1 = findListBySql(sql);
			
			if (list1 != null && list1.size() > 0){
				map.put("kunnr", StringUtils.removeLeftZero(list1.get(0).toString()));
			}else{
				map.put("mess", "没有在SAP中找到对应的客户编号，请确认输入！");
			}
			
		}else{
			map.put("kunnr", "");
		}
		
		return map;
	}
	

}
