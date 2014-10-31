package com.wellsoft.pt.ldx.customdyform.datainterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wellsoft.pt.basicdata.datasource.entity.DataSourceColumn;
import com.wellsoft.pt.basicdata.datasource.provider.AbstractDataSourceProvider;
import com.wellsoft.pt.basicdata.dyview.provider.ViewColumnType;
import com.wellsoft.pt.basicdata.sap.service.SAPRfcService;
import com.wellsoft.pt.basicdata.sap.util.SAPRfcJson;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.ldx.customdyform.sap.CostCenter;

@Component
public class RecrScheduleRoleId extends AbstractDataSourceProvider {

	@Override
	public Collection<DataSourceColumn> getAllDataSourceColumns() {
		Set<DataSourceColumn> DataSourceColumns = new HashSet<DataSourceColumn>();
		// 列定义

		DataSourceColumn WERKS = new DataSourceColumn();
		WERKS.setFieldName("WERKS");
		WERKS.setColumnAliase("WERKS");
		WERKS.setColumnName("公司编码");
		WERKS.setColumnDataType(ViewColumnType.STRING.name());
		DataSourceColumns.add(WERKS);

		DataSourceColumn BTRTL = new DataSourceColumn();
		BTRTL.setFieldName("BTRTL");
		BTRTL.setColumnAliase("BTRTL");
		BTRTL.setColumnName("人事子范围");
		BTRTL.setColumnDataType(ViewColumnType.STRING.name());
		DataSourceColumns.add(BTRTL);

		DataSourceColumn BTEXT = new DataSourceColumn();
		BTEXT.setFieldName("BTEXT");
		BTEXT.setColumnAliase("BTEXT");
		BTEXT.setColumnName("人事子范围文本");
		BTEXT.setColumnDataType(ViewColumnType.STRING.name());
		DataSourceColumns.add(BTEXT);

		return DataSourceColumns;
	}

	@Autowired
	SAPRfcService saprfcservice;

	@Override
	public String getModuleId() {
		return "Ddsq_xgs";
	}

	@Override
	public String getModuleName() {
		return "调动申请_新公司";
	}

	@Override
	public List<QueryItem> query(Set<DataSourceColumn> arg0, String arg1, Map<String, Object> arg2, String arg3,
			PagingInfo arg4) {
		
		Map<String, Object> mustField = new HashMap<String, Object>();// 必选项
		if (arg1 != null && arg1.trim().length() > 0) {
			int bIndex = arg1.indexOf("{");
			int eIndex = arg1.lastIndexOf("}");
			if (bIndex != -1 && eIndex != -1) {
				String cJson = arg1.substring(bIndex, eIndex + 1);
				try {
					org.json.JSONObject cJsonObj = new org.json.JSONObject(cJson);
					Iterator<String> keysIt = cJsonObj.keys();
					while (keysIt.hasNext()) {
						String key = keysIt.next();						
						mustField.put(key, cJsonObj.getString(key));
					}
				} catch (JSONException e) {

					e.printStackTrace();

					return new ArrayList<QueryItem>();
				}

			}

		}

		// String params =
		// "{'PI_MATNR':'param','PI_WERKS':'efg','PI_TAB':{'MATNR':'1'},'PT_TAB':{'row':[{'MATNR':'1','WERKS':'2'},{'MATNR':'3','WERKS':'4'}]}}";
		List<QueryItem> list = new ArrayList<QueryItem>();

		String funName = "ZHRI0047";
		String params = "{'PI_WERKS':'" + mustField.get("PI_WERKS") + "'}";		
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", funName, params, 1, -1, null);
		mustField.remove("PI_WERKS");
		JSONObject jo = rfcjson.getRfcjsonobject();
		JSONArray ja = jo.getJSONObject(jo.getJSONArray("_recordName_").getString(0)).getJSONArray("row"); 
		
		Map<String, Object> optionalField = new HashMap<String, Object>();//可选项
		//Iterator<String> itx = arg2.keySet().iterator();
		 
		/*while(itx.hasNext()){
			String key = itx.next();
			String value = (String) arg2.get(key); 
			
			if(value == null || value.trim().length() == 0){
				continue;
			}
			if(key.indexOf("___MUST") != -1){
				mustField.put(key.substring(0, key.indexOf("___MUST")), value);
			}else{
				optionalField.put(key, value);
			} 
		}*/
		
		
		
		Iterator<JSONObject> it = ja.iterator();
		while(it.hasNext()){
			JSONObject jotmp = it.next(); 
			Map<String, Object> map = CostCenter.parserToMap(jotmp.toString());  
			Iterator<String> cdit = mustField.keySet().iterator();
			while(cdit.hasNext()){ 
				String key = cdit.next();
				String value = (String) mustField.get(key);
				Object valObj =   map.get(key.toUpperCase());
				String val = null;
				 if(valObj != null){
					 val = valObj.toString();
				 }else{ 
					 it.remove();
					 continue;
				 }
				if(val.toUpperCase().indexOf(value.toUpperCase()) == -1){
					 it.remove();
					continue;
				}
			}
		}
		
		
		 it = ja.iterator();
		while(it.hasNext()){
			boolean match = false;
			JSONObject jotmp = it.next(); 
			Map<String, Object> map = CostCenter.parserToMap(jotmp.toString());  
			Iterator<String> cdit = optionalField.keySet().iterator();
			
			while(cdit.hasNext()){
				String key = cdit.next();
				String value = (String) optionalField.get(key);
				Object valObj =   map.get(key.toUpperCase());
				String val = null;
				 if(valObj != null){
					 val = valObj.toString();
				 }else{
					 //it.remove();
					 //continue;
				 }
				if(val.toUpperCase().indexOf(value.toUpperCase()) != -1){
					match = true;
					break;
				}else{
					
				}
			}
			if(optionalField.size() == 0){
				match = true;
			}
			if(match){
				QueryItem item = new QueryItem();
				item.putAll(map);
				list.add(item);
			}
			
		}
		return list;
	}

	@Override
	public Object[] custom(Object[] arg0) {
		return new String[] { "sap" };
	}
}
