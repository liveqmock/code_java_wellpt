package com.wellsoft.pt.ldx.SapListener;

import java.math.BigDecimal;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fr.stable.StringUtils;
import com.wellsoft.pt.basicdata.sap.service.SAPRfcService;
import com.wellsoft.pt.basicdata.sap.util.SAPRfcJson;
import com.wellsoft.pt.bpm.engine.context.event.Event;
import com.wellsoft.pt.bpm.engine.context.listener.TaskListener;
import com.wellsoft.pt.bpm.engine.exception.WorkFlowException;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.DyFormData;

@Service
@Transactional
public class ApplyForBenefitsListener implements TaskListener{
	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	@Autowired
	SAPRfcService saprfcservice;
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "LDX_薪资管理_福利金申请单v1.1";
	}

	@Override
	public int getOrder() {
		return 26;
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
		
		String PI_PERNR =  (String) dataMap.get("sqrgh");//申请人工号
		
		if(StringUtils.isBlank(PI_PERNR)){
			PI_PERNR = "";
		}
		String PI_LGART =  this.toStr(dataMap.get("sqxm").toString());
		BigDecimal PI_BETRG = (BigDecimal) dataMap.get("sqje");//申请金额
		if(PI_BETRG == null){
			PI_BETRG = new BigDecimal(0);
			throw new WorkFlowException("请填写申请金额！");
		}
		
		String PI_BEGDA =  (String) dataMap.get("sxrq");
		if(StringUtils.isNotBlank(PI_BEGDA)){
			PI_BEGDA = (String)PI_BEGDA.replaceAll("[^0-9]", "");//PI_MASSG
		}
		
		String params = "{PI_PERNR:'"+PI_PERNR+"',PI_LGART:'"+PI_LGART+"',PI_BETRG:'"+PI_BETRG.toString()+"'" +
				",PI_WAERS:'CNY',PI_BEGDA:'"+PI_BEGDA+"'}";
		
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI003113", params, 1, -1, null);
		// 解析返回结果
		// 解析返回结果
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
	public String toStr(String str){
		str = str.replaceAll("\"", "");
		str = str.replace("{", "");
		str = str.replace("}", "");
		String[] retStr = str.split(":");
		return retStr[0];
	}
}
