package com.wellsoft.pt.ldx.service.productionPlan.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.ldx.service.impl.SapServiceImpl;
import com.wellsoft.pt.ldx.service.productionPlan.IProductionStockService;
import com.wellsoft.pt.ldx.util.StringUtils;

/**
 * 
 * Description: 可用库存service
 *  
 * @author heshi
 * @date 2014-7-31
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-7-31.1	heshi		2014-7-31		Create
 * </pre>
 *
 */
@Service
@Transactional
public class ProductionStockServiceImpl extends SapServiceImpl implements IProductionStockService {

	@Override
	public void deleteStocks(String params) {
		if(StringUtils.isBlank(params))
			return;
		String[] array = params.split(";");
		if(null==array||array.length==0)
			return;
		for (String map: array) {
			String[] conifg = map.split(",");
			String delete = "delete from zppt0036 where dwerks='" +conifg[0] + "' and lgort='"
					+ conifg[1] + "' and mandt=" +getClient() ;
			execSql(delete);
		}
	}

	@Override
	public Map<?, ?> saveStocks(String dwerks, String lgort, String lvorm) {
		Map<String,String> map = new HashMap<String,String>();
		if(StringUtils.isBlank(dwerks)||StringUtils.isBlank(lgort)){
			map.put("res","fail");
			map.put("msg","工厂及库存不能为空值!");
			return map;
		}
		List<String[]> list = findStockByInfo(dwerks,lgort);
		if(list!=null && list.size()>0){
			map.put("res","fail");
			map.put("msg","当前库存已配置,请勿重复添加!");
			return map;
		}
		if(StringUtils.isBlank(lvorm)){
			lvorm = " ";
		}
		String sql = "insert into ZPPT0036(MANDT,DWERKS,LGORT,LVORM) values('" + getClient() + "','" + dwerks + "','"
			+ lgort + "','" + lvorm + "')";
		execSql(sql);
		map.put("res","success");
		map.put("msg","保存成功!");
		return map;
	}
	
	@Override
	public void updateStocks(String dwerks, String lgort, String lvorm) {
		if(StringUtils.isBlank(lvorm)){
			lvorm = " ";
		}
		String sql = "update zppt0036 set lvorm='" + lvorm + "' where dwerks='" + dwerks
				+ "' and lgort='" + lgort + "' and mandt=" +getClient() ;
		execSql(sql);
	}

	@Override
	public List<String[]> findStockByInfo(String dwerks, String lgort) {
		StringBuffer sql = new StringBuffer("select dwerks,lgort,lvorm from zppt0036 where mandt='" + getClient() + "'");
		if(StringUtils.isNotBlank(dwerks)){
			sql.append(" and dwerks='" + dwerks + "'");
		}
		if(StringUtils.isNotBlank(lgort)){
			sql.append(" and lgort='" + lgort + "'");
		}
		List<Object> list = findListBySql(sql.toString());
		if(null==list||list.size()==0)
			return null;
		List<String[]> resultList = new ArrayList<String[]>(); 
		for(Object obj:list){
			Object[] objects = (Object[]) obj;
			String[] lineRes = {StringUtils.nullToString(objects[0]),StringUtils.nullToString(objects[1]),StringUtils.nullToString(objects[2])};
			resultList.add(lineRes);
		}
		return resultList;
	}
	
}
