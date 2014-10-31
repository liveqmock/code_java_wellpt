package com.wellsoft.pt.ldx.SapListener;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.basicdata.sap.service.SAPRfcService;
import com.wellsoft.pt.basicdata.sap.util.SAPRfcJson;
import com.wellsoft.pt.bpm.engine.context.event.Event;
import com.wellsoft.pt.bpm.engine.context.listener.TaskListener;
import com.wellsoft.pt.bpm.engine.exception.WorkFlowException;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.DyFormData;

@Service
@Transactional
public class SupplierCodeOperationListener implements TaskListener{
	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	@Autowired
	SAPRfcService saprfcservice;
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "供应商code操作申请SAP回写";
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 27;
	}
	public String toStr(String str){
		str = str.replaceAll("\"", "");
		str = str.replace("{", "");
		str = str.replace("}", "");
		String[] retStr = str.split(":");
		return retStr[0];
	}
	@Override
	public void onCompleted(Event event) throws WorkFlowException {
		// 获得流程表单的信息 
		DyFormData dyformdata =dyFormApiFacade.getDyFormData(event.getFormUuid(), event.getDataUuid());
		// 获得主表表单数据
		Map<String,Object> dataMap = dyformdata.getFormDataByFormUuidAndDataUuid(event.getFormUuid(), event.getDataUuid());
		// 通过从表id获得从表数据
		List<Map<String, Object>> childList = dyformdata.getFormDatasById("uf_rsgl_ldhtxqxx");
		
		String sqlx = dataMap.get("sqlx").toString();
		
		SAPRfcJson rfcjson = null;
		//创建
		if(sqlx.indexOf("1")>=0||sqlx.indexOf("5")>=0){
			String params = "{PT_ZMMS0033:{row:[{ZCL:'"+this.toStr(dataMap.get("sqlx").toString())+"',NAME1:'"+dataMap.get("gysqc")+"',SORTL:'"+dataMap.get("gysjc")+"'" +
			",STCD2:'"+dataMap.get("lxryyzz")+"',STRAS:'"+dataMap.get("dz")+"',TELF1:'"+dataMap.get("dh")+"',TELFX:'"+dataMap.get("cz")+"'" +
			",EKORG:'"+this.toStr(dataMap.get("cgzz").toString())+"',KTOKK:'"+this.toStr(dataMap.get("zhz").toString())+"',BUKRS:'"+this.toStr(dataMap.get("gszt").toString())
			+"',ZTERM:'"+this.toStr(dataMap.get("fkfs").toString())+"'" +
			",STCD1:'"+dataMap.get("yyzz")+"',STCD3:'"+dataMap.get("swdjzh")+"',LAND1:'"+this.toStr(dataMap.get("gj").toString())+"',BANKL:'"+this.toStr(dataMap.get("yhdm").toString())+"'," +
			"BANKN:'"+dataMap.get("yhzh")+"',AKONT:'"+this.toStr(dataMap.get("tykm").toString())+"',WAERS:'"+this.toStr(dataMap.get("jybb").toString())+"',ZUAWA:'"+dataMap.get("pxm")+"'" +
			",VERKF:'"+dataMap.get("lxr")+"',TELF2:'"+dataMap.get("lxdh")+"',KOINH:'"+dataMap.get("khh")+"',FDGRV:'"+this.toStr(dataMap.get("xjhbgl").toString())+"'" +
			",LIFNR:'"+dataMap.get("gysbm")+"'}]}}";
			
			//回写接口
			rfcjson = saprfcservice.RFC("sapConnectConfig", "ZMMI0015", params, 1, -1, null);
		}
		//变更
		if(sqlx.indexOf("2")>=0){
			String params = "{PT_ZMMS0033:{row:[{ZCL:'"+this.toStr(dataMap.get("sqlx").toString())+"',NAME1:'"+dataMap.get("gysqc")+"',LIFNR:'"+dataMap.get("gysbm")+"'," +
					"SORTL:'"+dataMap.get("gysjc")+"',STCD2:'"+dataMap.get("lxryyzz")+"',STRAS:'"+dataMap.get("dz")+"',TELF1:'"+dataMap.get("dh")+"'" +
							",TELFX:'"+dataMap.get("cz")+"',EKORG:'"+this.toStr(dataMap.get("cgzz").toString())+"',KTOKK:'"+this.toStr(dataMap.get("zhz").toString())+"',BUKRS:'"+this.toStr(dataMap.get("gszt").toString())+"'" +
							",ZTERM:'"+this.toStr(dataMap.get("fkfs").toString())+"',STCD1:'"+dataMap.get("yyzz")+"',STCD3:'"+dataMap.get("swdjzh")+"',LAND1:'"+this.toStr(dataMap.get("gj").toString())+"'" +
							",BANKL:'"+this.toStr(dataMap.get("yhdm").toString())+"',BANKN:'"+dataMap.get("yhzh")+"',AKONT:'"+this.toStr(dataMap.get("tykm").toString())+"',WAERS:'"+this.toStr(dataMap.get("jybb").toString())+"'" +
							",ZUAWA:'"+dataMap.get("pxm")+"',VERKF:'"+dataMap.get("lxr")+"',TELF2:'"+dataMap.get("lxdh")+"',KOINH:'"+dataMap.get("khh")+"'" +
							",FDGRV:'"+this.toStr(dataMap.get("xjhbgl").toString())+"'}]}}";
			//回写接口
			rfcjson = saprfcservice.RFC("sapConnectConfig", "ZMMI0015", params, 1, -1, null);

		}
		//停用
		 if(sqlx.indexOf("3")>=0){
			 String params = "{PT_ZMMS0033:{row:[{ZCL:'"+this.toStr(dataMap.get("sqlx").toString())+"',LIFNR:'"+dataMap.get("gysbm")+"',EKORG:'"+this.toStr(dataMap.get("cgzz").toString())+"'}]}}";
			//回写接口
			rfcjson = saprfcservice.RFC("sapConnectConfig", "ZMMI0015", params, 1, -1, null);
		 }
		 //启用
		 if(sqlx.indexOf("4")>=0){
			 String params = "{PT_ZMMS0033:{row:[{ZCL:'"+this.toStr(dataMap.get("sqlx").toString())+"',LIFNR:'"+dataMap.get("gysbm")+"',EKORG:'"+this.toStr(dataMap.get("cgzz").toString())+"'}]}}";
				//回写接口
			rfcjson = saprfcservice.RFC("sapConnectConfig", "ZMMI0015", params, 1, -1, null);
		 }
		 if(rfcjson == null){
				throw new WorkFlowException("SAP接口没有返回消息！");
		}
		 JSONArray jObjects = rfcjson.getRecord("PT_RETURN").getJSONArray("row");
		if(jObjects.size()==0){
			throw new WorkFlowException("SAP接口没有返回消息！");
		}else if("E".equals(jObjects.getJSONObject(0).getString("TYPE"))){
			throw new WorkFlowException(jObjects.getJSONObject(0).getString("MESSAGE"));
		}
	}

	@Override
	public void onCreated(Event arg0) throws WorkFlowException {
		// TODO Auto-generated method stub
		
	}

}
