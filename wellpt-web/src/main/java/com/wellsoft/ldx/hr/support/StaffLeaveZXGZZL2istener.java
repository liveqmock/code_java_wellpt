package com.wellsoft.ldx.hr.support;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.transaction.Transactional;

import net.sf.json.JSONArray;

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

/**
 * 
 * @StaffLeaveListener.java 
 * 描述：离职申请表(专项工作组)空缺开发
 * VERSION       DATE            BY       CHANGE/COMMENT
 * 1.0          2014-10-05       WenYi       create
 */
@Service
@Transactional
public class StaffLeaveZXGZZL2istener implements TaskListener{

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	
	@Autowired 
	private SAPRfcService saprfcservice;

	private SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	
	@Override
	public String getName() {
		return "离职申请表(专项工作组)[职位空缺开放]回写SAP";
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
		
		String PI_PLVAR="01";
		
		String PI_SEARK = (String) dataMap.get("PLANS");
		if(StringUtils.isBlank(PI_SEARK)){
			PI_SEARK = "";
		}
		
		String PI_BEGDA = yyyyMMdd.format(new Date());
		String PI_ENDDA="99991231";
		String PI_OPEN="0";
		
		String params = "{'PI_PLVAR':'"+ PI_PLVAR + "','PI_SEARK':'"  + PI_SEARK + "','PI_BEGDA':'" + PI_BEGDA  + "','PI_ENDDA':'"  + PI_ENDDA + "','PI_OPEN':'" + PI_OPEN + "'}";
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI0081", params, 1, -1, null);
		if(rfcjson == null){
			throw new WorkFlowException("回写SAP失败!");
		}
		JSONArray jObjects = rfcjson.getRecord("PT_RETURN").getJSONArray("row");
		if(jObjects.size()==0){
			throw new WorkFlowException("SAP接口没有返回消息！");
		}else if(!"S".equals(jObjects.getJSONObject(0).getString("TYPE"))){
			throw new WorkFlowException(jObjects.getJSONObject(0).getString("MESSAGE"));
		}
	}

	@Override
	public void onCreated(Event event) throws WorkFlowException {
		
	}

}
