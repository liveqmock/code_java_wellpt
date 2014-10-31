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

/**
 * 
 * @StaffLeaveListener.java 
 * 描述：离职申请表(专项工作组)
 * VERSION       DATE            BY       CHANGE/COMMENT
 * 1.0          2014-10-05       WenYi       create
 */
@Service
@Transactional
public class StaffLeaveZXGZZListener implements TaskListener{

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	
	@Autowired 
	SAPRfcService saprfcservice;
	
	@Override
	public String getName() {
		return "离职申请表(专项工作组)";
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
	
		String PI_PERNR = (String)dataMap.get("ygbh");//员工编号 PI_PERNR
		String PI_BEGDA = (String)dataMap.get("lzrq");//离职日期 PI_BEGDA lzrq
		if(StringUtils.isNotBlank(PI_BEGDA)){
			PI_BEGDA = (String)PI_BEGDA.replaceAll("[^0-9]", "");//PI_MASSG
		}else{
			PI_BEGDA = "";
		}
		String PI_MASSG = (String)dataMap.get("lzyy");//离职原因
		if(StringUtils.isBlank(PI_MASSG)){
			PI_MASSG = "";
		}else{
			PI_MASSG = DyFormApiFacade.getRealValue(PI_MASSG);
		}
		String params = "{'PI_PERNR':'"+ PI_PERNR + "','PI_BEGDA':'"  + PI_BEGDA + "','PI_MASSG':'" + PI_MASSG + "'}";
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI0004", params, 1, -1, null);
		if(rfcjson == null){
			throw new WorkFlowException("回写SAP失败!");
		}
		String MESSAGE =  rfcjson.getRecord("PO_RETURN").getString("MESSAGE");
		String TYPE =  rfcjson.getRecord("PO_RETURN").getString("TYPE");
		if(StringUtils.isEmpty(TYPE)){
			throw new WorkFlowException("sap接口没有返回消息！");
		}
		if("E".equals(TYPE)){
			throw new WorkFlowException("回写SAP失败!");
		}
	}

	@Override
	public void onCreated(Event event) throws WorkFlowException {
		
	}

}
