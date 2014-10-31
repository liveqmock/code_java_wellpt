package com.wellsoft.ldx.hr.support;

import java.util.List;
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
 * 描述：员工离职申请表
 * VERSION       DATE            BY       CHANGE/COMMENT
 * 1.0          2014-9-30          WenYi       create
 */
@Service
@Transactional
public class StaffLeaveListener implements TaskListener{

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	
	@Autowired 
	SAPRfcService saprfcservice;
	
	@Override
	public String getName() {
		return "员工离职申请表";
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void onCompleted(Event event) throws WorkFlowException {
		
		// 获得流程表单的信息 
		DyFormData dyformdata =dyFormApiFacade.getDyFormData(event.getFormUuid(), event.getDataUuid());
		// 获得主表表单数据
		Map<String,Object> dataMap = dyformdata.getFormDataByFormUuidAndDataUuid(event.getFormUuid(), event.getDataUuid());
		// 请求参数
		String ygbh = (String)dataMap.get("ygbh");//员工编号
		if(StringUtils.isBlank(ygbh)){
			ygbh = "";
		}
		
		//String lzrq = (String)dataMap.get("lzrq");//离职日期
		String lzrq = ((String) dataMap.get("lzrq"));
		if(StringUtils.isNotBlank(lzrq)){
			lzrq.replaceAll("[^0-9]", "");
		}else{
			lzrq = "";
		}
		
		String lzyy = (String)dataMap.get("lzyy");//离职原因
		String lzyyRel = "";
		if(StringUtils.isNotBlank(lzyy)){
			lzyyRel =DyFormApiFacade.getDisplayValue(lzyy);
		}else{
			lzyyRel = "";
		}
		
		String params = "{'PI_PERNR':'"+ ygbh + "','PI_BEGDA':'"  + lzrq + "','PI_MASSG':'" + lzyyRel + "'}";
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
	public void onCreated(Event arg0) throws WorkFlowException {
		
	}

}
