package com.wellsoft.pt.ldx.SapListener;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
public class StaffAdditionsListener implements TaskListener{
	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	@Autowired
	SAPRfcService saprfcservice;
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "LDX_人事管理_人员增补申请表v1.1";
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 25;
	}

	@Override
	public void onCreated(Event event) throws WorkFlowException {
		
	}

	@Override
	public void onCompleted(Event event) throws WorkFlowException {
		// 获得流程表单的信息 
		DyFormData dyformdata =dyFormApiFacade.getDyFormData(event.getFormUuid(), event.getDataUuid());
		// 获得主表表单数据
		Map<String,Object> dataMap = dyformdata.getFormDataByFormUuidAndDataUuid(event.getFormUuid(), event.getDataUuid());
		// 通过从表id获得从表数据
		List<Map<String, Object>> childList = dyformdata.getFormDatasById("uf_ldx_rsgl_ryzbsqxx");
		
		String zbrylb = null!=dataMap.get("zbrylb")?dataMap.get("zbrylb").toString():"";
		
		//if("2".equals(zbrylb)){
		if(zbrylb.indexOf("2")>=0){
			String params = "{PT_HRP9100:{row:[";
			params +="{ZSERAL:'1',BEGDA:'"+dataMap.get("sxsj")+"',ENDDA:'"+dataMap.get("sxsj")+"'"+
				",OBJID:'"+dataMap.get("zwbm")+"',ZZXQRS:'"+dataMap.get("xqrs")+"',ZZLZYGXM:'"+dataMap.get("lzygxm")+"'" +
				",ZZXWDGSJ:'"+dataMap.get("xwdgsj")+"',ZZGZDD:'"+this.toStr(dataMap.get("gzdd").toString())+"',ZZZYGZNR:'"+dataMap.get("zygznr")+"'" +
				",ZZXBYQ:'"+this.toStr(dataMap.get("xbyq").toString())+"',ZZNLYQ:'"+dataMap.get("nlyq")+"',ZZHYYQ:'"+this.toStr(dataMap.get("hyyq").toString())+"',ZZXLYQ:'"+dataMap.get("xlyq")+"'" +
				",ZZYYYQ:'"+dataMap.get("yyyq")+"',ZZZCYQ:'"+dataMap.get("zcyq")+"',ZZGZJYYQ:'"+dataMap.get("gzjyyq")+"',ZZJNYQ:'"+dataMap.get("jnyq")+"'" +
				",ZZXGYQ:'"+dataMap.get("xgyq")+"',ZZQTYQ:'"+dataMap.get("qtyq")+"',ZZRYZBYY:'"+this.toStr(dataMap.get("ryzbyy").toString())+"'}";
			params += "]}}";

			SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI0018", params, 1, -1, null);
			// 解析返回结果
			this.workFlow(rfcjson);
		}else{
			for(int i=0;i<childList.size();i++){
				Map<String,Object> child = childList.get(i);
				String params1 = "{PT_HRP9100:{row:[";
				params1 +="{BEGDA:'"+dataMap.get("BEGDA")+"',ENDDA:'"+dataMap.get("BEGDA")+"',BEGDA:'"+dataMap.get("BEGDA")+"'"+
					",ZZRYZBYY:'"+this.toStr(dataMap.get("ryzbyy").toString())+"',OBJID:'"+child.get("zwbm")+"',ZZXQRS:'"+child.get("xqrs")+"',ZZXWDGSJ:'"+child.get("xwdgsj")+"'" +
					",ZZXBYQ:'"+this.toStr(child.get("xbyq").toString())+"',ZZNLYQ:'"+child.get("nlyq")+"',ZZJNYQ:'"+child.get("jnyq")+"',ZZXGYQ:'"+child.get("xgyq")+"'" +
					",ZZQTYQ:'"+child.get("qtyq")+"'}";
				//if(i<childList.size()-1)params += ",";
				params1 += "]}}";

				SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI0018", params1, 1, -1, null);
				this.workFlow(rfcjson);
			}
		}
		
	}
	public void workFlow(SAPRfcJson rfcjson){
	// 解析返回结果
		if(null == rfcjson){
			throw new WorkFlowException("SAP接口没有返回消息！");
		}
		JSONArray jObjects = rfcjson.getRecord("PT_RETURN").getJSONArray("row");
		if(jObjects.size()==0){
			throw new WorkFlowException("SAP接口没有返回消息！");
		}else if("E".equals(jObjects.getJSONObject(0).getString("TYPE"))){
			throw new WorkFlowException(jObjects.getJSONObject(0).getString("MESSAGE"));
		}
	}
	public String toStr(String str){
		str = str.replaceAll("\"", "");
		str = str.replace("{", "");
		str = str.replace("}", "");
		String[] retStr = str.split(":");
		return retStr[0];
	}
}
