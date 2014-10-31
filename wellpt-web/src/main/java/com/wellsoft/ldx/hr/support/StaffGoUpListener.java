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
/**
 * 
 * @StaffGoUpListener.java 
 * 描述：LDX_人事管理_员工晋升表单v1.4
 * VERSION       DATE            BY       CHANGE/COMMENT
 * 1.0          2014-9-30          WenYi       create
 */
@Service
@Transactional
public class StaffGoUpListener implements TaskListener{

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	
	@Autowired 
	SAPRfcService saprfcservice;

	@Override
	public String getName() {
		return "LDX_人事管理_员工晋升表单v1.4";
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
		String jssxrq = (String)dataMap.get("jssxrq");//晋升生效日期
		
		String PI_ENDDA="99991231";
		String PI_MASSG="03";
		String jshygbm = (String)dataMap.get("jshygbm");//晋升后职位编码
		
		String jshrszfw = (String)dataMap.get("jshrszfw");//晋升后人事子范围
		String jshygzz = (String)dataMap.get("jshygzz");//晋升后员工子组
		String jshrsfw = (String)dataMap.get("jshrsfw");//晋升后人事范围
		String PI_PERSG="1";
		
		String params2 = "{PI_PERNR:'" + PI_PERNR + "',PI_BEGDA:'" 
				+ jssxrq + "',PI_ENDDA:'" + PI_ENDDA + "',PI_MASSG:'" 
				+ PI_MASSG + "',PI_PLANS:'" + jshygbm + "',PI_BTRTL:'" 
				+ jshrszfw + "',PI_PERSK:'" + jshygzz + "',PI_WERKS:'" 
				+ jshrsfw + "',PI_PERSG:'" + PI_PERSG + "'}";
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI0002", params2, 1, -1, null);
		if(rfcjson == null){
			throw new WorkFlowException("回写SAP失败!");
		}
		
		
	}

	@Override
	public void onCreated(Event event) throws WorkFlowException {
		
	}
	
	
}
