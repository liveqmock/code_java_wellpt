package com.wellsoft.ldx.hr.support;

import java.util.Map;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wellsoft.pt.basicdata.sap.service.SAPRfcService;
import com.wellsoft.pt.basicdata.sap.util.SAPRfcJson;
import com.wellsoft.pt.bpm.engine.context.event.Event;
import com.wellsoft.pt.bpm.engine.context.listener.TaskListener;
import com.wellsoft.pt.bpm.engine.exception.WorkFlowException;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.DyFormData;

@Service
@Transactional
public class HireInterviewArrangeSapListener implements TaskListener{

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	
	@Autowired 
	SAPRfcService saprfcservice;
	
	@Override
	public String getName() {
		return "LDX_人事管理_招聘面试安排表";
	}

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public void onCompleted(Event event) throws WorkFlowException {
		// 获得流程表单的信息 
		DyFormData dyformdata =dyFormApiFacade.getDyFormData(event.getFormUuid(), event.getDataUuid());
		// 获得主表表单数据
		Map<String,Object> dataMap = dyformdata.getFormDataByFormUuidAndDataUuid(event.getFormUuid(), event.getDataUuid());
		
		String PI_APLNO= (String)dyformdata.getFieldValue("sqrbh");//  APLNO
		String PI_APSTV="1";
		
		String params = "{'PI_APLNO':" + PI_APLNO
						+ ",'PI_APSTV':" + PI_APSTV
						+ "}";
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI0023", params, 1, -1, null);
		if(rfcjson == null){
			throw new WorkFlowException("回写SAP失败!");
		}
		net.sf.json.JSONArray jObjects = rfcjson.getRecord("PT_RETURN").getJSONArray("row");
		if(jObjects.size()==0){
			throw new WorkFlowException("SAP接口没有返回消息！");
		}else if("E".equals(jObjects.getJSONObject(0).getString("TYPE"))){
			throw new WorkFlowException(jObjects.getJSONObject(0).getString("MESSAGE"));
		}
		
	}

	@Override
	public void onCreated(Event evnet) throws WorkFlowException {
		
	}
	
	
}
