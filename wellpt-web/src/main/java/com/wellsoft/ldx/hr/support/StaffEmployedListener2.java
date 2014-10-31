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
public class StaffEmployedListener2 implements TaskListener{

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	
	@Autowired 
	SAPRfcService saprfcservice;
	
	@Override
	public String getName() {
		return "员工录用审批表(专项工作组)基本工资回写";
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
		
		String PI_PERNR = (String)dataMap.get("yprybm");//应聘人编号
		String PI_BEGDA = (String)dataMap.get("ksrq");//开始日期
		PI_BEGDA = PI_BEGDA.replaceAll("[^0-9]", "");
		String PI_TRFGB = "01";//
		String PI_TRFGR = "01";//
		
		String PI_DIVGV = (String)dataMap.get("mggzhsqdsgs");//每个工资核算期的工时数
		String PI_TRFST = (String)dataMap.get("gzdjsp");//工资等级水平
		
		PI_TRFST = DyFormApiFacade.getRealValue(PI_TRFST);
		
		String PI_BETRG1 = (String)dataMap.get("gzze");//工资总额
		String PI_BETRG3 = (String)dataMap.get("gwgz");//岗位工资
		String PI_BETRG4 = (String)dataMap.get("jxgz");//绩效工资
		
		
		String params = "{PI_PERNR:'" + PI_PERNR +  "',PI_BEGDA:'" + PI_BEGDA + "',PI_TRFGB:'" + PI_TRFGB 
				+ "',PI_TRFGR:'" + PI_TRFGR + "',PI_DIVGV:'" + PI_DIVGV
				+ "',PI_TRFST:'" + PI_TRFST + "',PI_BETRG1:'" + PI_BETRG1 
				+ "',PI_BETRG3:'" + PI_BETRG3 + "',PI_BETRG4:'" + PI_BETRG4 + 
				"'}"; 
		
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI002901", params, 1, -1, null);
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
	public void onCreated(Event event) throws WorkFlowException {
		
	}

	
}
