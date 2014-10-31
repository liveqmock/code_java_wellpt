package com.wellsoft.pt.ldx.service.productionPlan.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.basicdata.sap.service.SAPRfcService;
import com.wellsoft.pt.basicdata.sap.util.SAPRfcJson;
import com.wellsoft.pt.ldx.service.impl.SapServiceImpl;
import com.wellsoft.pt.ldx.service.productionPlan.IProductionLineCapacityService;
import com.wellsoft.pt.ldx.util.DateUtils;
import com.wellsoft.pt.ldx.util.StringUtils;


/**
 * 
 * Description: 标准产能service
 *  
 * @author heshi
 * @date 2014-8-4
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-8-4.1	heshi		2014-8-4		Create
 * </pre>
 *
 */
@Service
@Transactional
public class ProductionLineCapacityServiceImpl extends SapServiceImpl implements IProductionLineCapacityService {
	
	@Autowired
	private SAPRfcService saprfcservice;

	@Override
	public List<String[]> findLineCapacityByInfo(String zxh,String zrq) {
		StringBuffer sql = new StringBuffer("select dwerks,department,class,zxh,zsg,zrq,ltxa1,gamng1,gamng2,gamng3,zxbbbxs,zcxdm from zppt0034 where mandt='" + getClient() + "'");
		if(StringUtils.isNotBlank(zxh)){
			sql.append(" and zxh='" + zxh + "'");
		}
		if(StringUtils.isNotBlank(zrq)){
			sql.append(" and zrq='" + zrq + "'");
		}
		List<Object> list = findListBySql(sql.toString());
		if(null==list||list.size()==0)
			return null;
		List<String[]> resultList = new ArrayList<String[]>(); 
		for(Object obj:list){
			Object[] objects = (Object[]) obj;
			String[] line = new String[objects.length];
			for(int i=0;i<objects.length;i++){
				line[i]=StringUtils.nullToString(objects[i]);
			}
			resultList.add(line);
		}
		return resultList;
	}

	@Override
	public void deleteLineCapacity(String params) {
		if(StringUtils.isBlank(params))
			return;
		String[] array = params.split(";");
		if(null==array||array.length==0)
			return;
		for (String map: array) {
			String[] conifg = map.split(",");
			String delete = "delete from zppt0034 where zxh='" +conifg[0] + "' and zrq='"
					+ conifg[1] + "' and mandt=" +getClient() ;
			execSql(delete);
		}
	}

	@Override
	public Map<?, ?> saveLineCapacity(String dwerks,String department,String clazz,String zxh,String zsg,String zrq,String ltxa1,String gamng1,String gamng2,String gamng3,String zxbbbxs,String zcxdm) {
		Map<String,String> map = new HashMap<String,String>();
		if(StringUtils.isBlank(dwerks)){
			map.put("res","fail");
			map.put("msg","工厂不能为空值!");
			return map;
		}
		if(StringUtils.isBlank(department)){
			map.put("res","fail");
			map.put("msg","部门不能为空值!");
			return map;
		}
		if(StringUtils.isBlank(clazz)){
			map.put("res","fail");
			map.put("msg","课别不能为空值!");
			return map;
		}
		if(StringUtils.isBlank(zxh)){
			map.put("res","fail");
			map.put("msg","线号不能为空值!");
			return map;
		}
		if(StringUtils.isBlank(zsg)){
			map.put("res","fail");
			map.put("msg","生管不能为空值!");
			return map;
		}
		if(StringUtils.isBlank(zrq)){
			map.put("res","fail");
			map.put("msg","日期不能为空值!");
			return map;
		}
		if(StringUtils.isBlank(ltxa1)){
			map.put("res","fail");
			map.put("msg","工序别不能为空值!");
			return map;
		}
		if(StringUtils.isBlank(zxbbbxs)){
			map.put("res","fail");
			map.put("msg","线别报表显示不能为空值!");
			return map;
		}
		if(StringUtils.isBlank(gamng1)){
			gamng1="0";
		}
		if(StringUtils.isBlank(gamng2)){
			gamng2="0";
		}
		if(StringUtils.isBlank(gamng3)){
			gamng3="0";
		}
		if(StringUtils.isBlank(zcxdm)){
			zcxdm=" ";
		}
		List<String[]> list = findLineCapacityByInfo(zxh,zrq);
		if(list!=null && list.size()>0){
			map.put("res","fail");
			map.put("msg","当前标准产能已配置,请勿重复添加!");
			return map;
		}
		String sql = "insert into zppt0034(mandt,dwerks,department,class,zxh,zsg,zrq,ltxa1,gamng1,gamng2,gamng3,zxbbbxs,zcxdm) values('"
				+ getClient()
				+ "','"
				+ dwerks
				+ "','"
				+ department
				+ "','"
				+ clazz
				+ "','"
				+ zxh
				+ "','"
				+ zsg
				+ "','"
				+ zrq + "','" + ltxa1 + "','" + gamng1 + "','" + gamng2 + "','" + gamng3 + "','" + zxbbbxs + "','"+zcxdm+"')";
		execSql(sql);
		map.put("res","success");
		map.put("msg","保存成功!");
		return map;
	}

	@Override
	public Map<?, ?> updateLineCapacity(String dwerks,String department,String clazz,String zxh,String zsg,String zrq,String ltxa1,String gamng1,String gamng2,String gamng3,String zxbbbxs,String zcxdm) {
		Map<String,String> map = new HashMap<String,String>();
		if(StringUtils.isBlank(dwerks)){
			map.put("res","fail");
			map.put("msg","工厂不能为空值!");
			return map;
		}
		if(StringUtils.isBlank(department)){
			map.put("res","fail");
			map.put("msg","部门不能为空值!");
			return map;
		}
		if(StringUtils.isBlank(clazz)){
			map.put("res","fail");
			map.put("msg","课别不能为空值!");
			return map;
		}
		if(StringUtils.isBlank(zxh)){
			map.put("res","fail");
			map.put("msg","线号不能为空值!");
			return map;
		}
		if(StringUtils.isBlank(zsg)){
			map.put("res","fail");
			map.put("msg","生管不能为空值!");
			return map;
		}
		if(StringUtils.isBlank(zrq)){
			map.put("res","fail");
			map.put("msg","日期不能为空值!");
			return map;
		}
		if(StringUtils.isBlank(ltxa1)){
			map.put("res","fail");
			map.put("msg","工序别不能为空值!");
			return map;
		}
		if(StringUtils.isBlank(zxbbbxs)){
			map.put("res","fail");
			map.put("msg","线别报表显示不能为空值!");
			return map;
		}
		if(StringUtils.isBlank(gamng1)){
			gamng1="0";
		}
		if(StringUtils.isBlank(gamng2)){
			gamng2="0";
		}
		if(StringUtils.isBlank(gamng3)){
			gamng3="0";
		}
		if(StringUtils.isBlank(zcxdm)){
			zcxdm=" ";
		}
		String sql = "update zppt0034 set dwerks='" + dwerks + "',department='" + department + "',class='" + clazz
				+ "',zsg='" + zsg + "',ltxa1='" + ltxa1 + "',gamng1='" + gamng1 + "',gamng2='" + gamng2 + "',gamng3='"
				+ gamng3 + "',zxbbbxs='" + zxbbbxs + "',zcxdm='"+zcxdm+"' where zxh='" + zxh + "' and zrq='" + zrq + "' and mandt="
				+ getClient();
		execSql(sql);
		map.put("res","success");
		map.put("msg","保存成功!");
		return map;
	}

	@Override
	public Map<?,?> copyLineCapacity(String zsg,String startDate,String endDate) {
		HashMap<String,String> hashMap = new HashMap<String,String>();
		JSONObject jsonObject = new JSONObject();
		if(StringUtils.isBlank(startDate)||StringUtils.isBlank(endDate)){
			hashMap.put("res","fail");
			hashMap.put("err","开始日期及结束日期不能为空");
			return hashMap;
		}
		if(StringUtils.isNotBlank(zsg)){
			jsonObject.element("PI_ZSG",zsg);
		}
		jsonObject.element("PI_DATE_START",startDate);
		jsonObject.element("PI_DATE_END",endDate);
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig",
				"ZPPI0013", jsonObject.toString(), 1, -1, null);
		JSONObject jObject = rfcjson.getRecord("PT_RETURN");
		JSONArray jObs = jObject.getJSONArray("row");
		for (int i = 0; i < jObs.size(); i++) {
			if ("E".equals(((JSONObject) jObs.get(i)).get("TYPE").toString())) {
				hashMap.put("res","fail");
				hashMap.put("err", ((JSONObject)jObs.get(i)).get("MESSAGE").toString());
				return hashMap;
			}
		}
		hashMap.put("res","success");
		return hashMap;
	}

	@Override
	public Map<?, ?> adjustLineCapacity(String zsg, String date, String zxs) {
		Map<String,String> map = new HashMap<String,String>();
		if(StringUtils.isBlank(date)){
			map.put("res","fail");
			map.put("msg","日期不能为空值!");
			return map;
		}
		if(StringUtils.isBlank(zxs)){
			map.put("res","fail");
			map.put("msg","调整系数不能为空值!");
			return map;
		}
		date = date.replaceAll("-","");
		try {
			DateUtils.parseDate(date,"yyyyMMdd");
		} catch (Exception e) {
			map.put("res","fail");
			map.put("msg","日期格式不正确!");
			return map;
		}
		try {
			Double.parseDouble(zxs.trim());
		} catch (Exception e) {
			map.put("res","fail");
			map.put("msg","系数格式不正确!");
			return map;
		}
		String update = "update zppt0034 set gamng1=TO_CHAR(gamng1/" + zxs
				+ "/100,'999999999999')*100,gamng2=TO_CHAR(gamng2/" + zxs
				+ "/100,'999999999999')*100,gamng3=TO_CHAR(gamng3/" + zxs + "/100,'999999999999')*100 where ZSG='"
				+ zsg + "' and ZRQ='" + date + "' and mandt='" + getClient() + "'";
		execSql(update);
		map.put("res","success");
		map.put("msg","调整成功!");
		return map;
	}

	@Override
	public Map<?, ?> batchDeleteLineCapacity(String zsg, String startDate, String endDate) {
		HashMap<String,String> hashMap = new HashMap<String,String>();
		if(StringUtils.isBlank(zsg)){
			hashMap.put("res","fail");
			hashMap.put("err","生管不能为空!");
			return hashMap;
		}
		if(StringUtils.isBlank(startDate)||StringUtils.isBlank(endDate)){
			hashMap.put("res","fail");
			hashMap.put("err","开始日期及结束日期不能为空!");
			return hashMap;
		}
		String delete = "delete from zppt0034 where zsg='"
				+ zsg + "' and zrq>='" + startDate + "' and zrq<='"+endDate+"' and mandt='" + getClient() + "'";
		execSql(delete);
		hashMap.put("res","success");
		hashMap.put("msg","操作成功!");
		return hashMap;
	}
	
}
