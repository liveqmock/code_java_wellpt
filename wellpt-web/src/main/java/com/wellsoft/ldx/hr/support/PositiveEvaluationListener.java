package com.wellsoft.ldx.hr.support;

import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
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
public class PositiveEvaluationListener implements TaskListener {

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	
	@Autowired 
	SAPRfcService saprfcservice;
	
	@Override
	public String getName() {
		return "LDX_人事管理_转正评估申请表v1.1";
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
		
		String PI_PERNR = (String)dataMap.get("gh");//工号
		if(StringUtils.isBlank(PI_PERNR)){
			PI_PERNR = "";
		}
		
		String PI_BEGDA = ((String) dataMap.get("sjzzrq"));//时间转正日期
		if(StringUtils.isBlank(PI_BEGDA)){
			PI_BEGDA = "";
		}else{
			PI_BEGDA = PI_BEGDA.replaceAll("[^0-9]", "");
		}
		
		String TRFGB = (String)dataMap.get("TRFGB");//区域
		if(StringUtils.isBlank(TRFGB)){
			TRFGB = "";
		}
		String TRFGR = (String)dataMap.get("TRFGR");//组
		if(StringUtils.isBlank(TRFGR)){
			TRFGR = "";
		}
		String TRFST = (String)dataMap.get("TRFST");//等级
		if(StringUtils.isBlank(TRFST)){
			TRFST = "";
		}
		
		String PI_BETRG1 = (String)dataMap.get("zzhgzze");//转正后工资总额
		if(StringUtils.isBlank(PI_BETRG1)){
			PI_BETRG1 = "";
		}
		
		String PI_BETRG2 = (String)dataMap.get("zzhjbgz");//转正后基本工资
		if(StringUtils.isBlank(PI_BETRG2)){
			PI_BETRG2 = "";
		}
		String PI_BETRG3 = (String)dataMap.get("zzhgwgz");//转正后岗位工资
		if(StringUtils.isBlank(PI_BETRG3)){
			PI_BETRG3 = "";
		}
		
		String PI_BETRG4 = (String)dataMap.get("zzhjxgz");//转正后绩效工资
		if(StringUtils.isBlank(PI_BETRG4)){
			PI_BETRG4 = "";
		}
		String PI_BETRG5 = (String)dataMap.get("zzhjbjt");//转正后加班津贴
		if(StringUtils.isBlank(PI_BETRG5)){
			PI_BETRG5 = "";
		}
		
		String PI_LGART5 = (String)dataMap.get("PI_LGART5");//1219加班津贴
		if(StringUtils.isBlank(PI_LGART5)){
			PI_LGART5 = "";
		}
		
		String PI_PREAS = "01";
		// 请求参数
		
		String params2 = "{PI_PERNR:'" + PI_PERNR + "',PI_BEGDA:'" 
				+ PI_BEGDA + "',PI_TRFGB:'" + TRFGB + "',PI_TRFGR:'" 
				+ TRFGR + "',PI_TRFST:'" + TRFST + "',PI_BETRG1:'" 
				+ PI_BETRG1 + "',PI_BETRG2:'" + PI_BETRG2 + "',PI_BETRG3:'" 
				+ PI_BETRG3 + "',PI_BETRG4:'" + PI_BETRG4 + "',PI_BETRG5:'" 
				+ PI_BETRG5 + "',PI_LGART5:'" + PI_LGART5 + "',PI_PREAS:'" 
				+ PI_PREAS +  "'}";
		
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI002901", params2, 1, -1, null);
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
