package com.wellsoft.pt.ldx.SapListener;

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
public class EmployeesHiredApprovalListener implements TaskListener{
	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	@Autowired
	SAPRfcService saprfcservice;
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "员工录用审批表_员工入职信息维护";
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 24;
	}

	@Override
	public void onCompleted(Event event) throws WorkFlowException {
		// 获得流程表单的信息 
		DyFormData dyformdata =dyFormApiFacade.getDyFormData(event.getFormUuid(), event.getDataUuid());
		// 获得主表表单数据
		Map<String,Object> dataMap = dyformdata.getFormDataByFormUuidAndDataUuid(event.getFormUuid(), event.getDataUuid());
		
		String params = "{PT_ZHRS0034:{row:[{PERNR:'"+dataMap.get("yprybm")+"',NACHN:'"+dataMap.get("x")+"',VORNA:'"+dataMap.get("m")+"'" +
				",NAME2:'"+dataMap.get("ywm")+"',GESCH:'"+this.toStr(dataMap.get("xb").toString())+"',PLANS:'"+dataMap.get("ypzwbm")+"',WERKS:'"+this.toStr(dataMap.get("rsfw").toString())+"'" +
				",BTRTL:'"+this.toStr(dataMap.get("rszfw").toString())+"',NATIO:'"+dataMap.get("gj")+"',GBORT:'"+dataMap.get("jg")+"',GBDAT:'"+dataMap.get("csrq")+"'" +
				",FAMST:'"+this.toStr(dataMap.get("hyzk").toString())+"',BEGDA:'"+dataMap.get("bdjsr")+"',ABKRS:'"+this.toStr(dataMap.get("gzfw").toString())+"',PERSG:'1',PERSK:'Z2',MASSG:'"+this.toStr(dataMap.get("zpqd").toString())+"'" +
				",APLNO:'"+dataMap.get("yprybm")+"'}]}}";
		
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI0018", params, 1, -1, null);
		// 解析返回结果
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
	public void onCreated(Event event) throws WorkFlowException {
		// TODO Auto-generated method stub
		
	}
	public String toStr(String str){
		str = str.replaceAll("\"", "");
		str = str.replace("{", "");
		str = str.replace("}", "");
		String[] retStr = str.split(":");
		return retStr[0];
	}
}
