package com.wellsoft.ldx.hr.support;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.json.JSONArray;
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
 * @DeviceDamageApplication.java 
 * 描述：Leedarson_采购管理_设备易损件采购申请
 * VERSION       DATE            BY       CHANGE/COMMENT
 * 1.0          2014-9-29          WenYi       create
 */
@Service
@Transactional
public class DeviceDamageApplication implements TaskListener{

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	
	@Autowired 
	SAPRfcService saprfcservice;
	
	@Override
	public String getName() {
		return "Leedarson_采购管理_设备易损件采购申请";
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
		// 通过从表id获得从表数据
		List<Map<String, Object>> childList = dyformdata.getFormDatasById("uf_cggl_sbysjcgsq_dtbd");
		// 循环从表数据
		StringBuffer sb = new StringBuffer();
		sb.append("{row:[");
		for(Map<String,Object> child:childList){
			
			String gc = (String)child.get("gc");//工厂
			String jhddh = (String)child.get("jhddh");//计划订单号
			String cgz = (String)child.get("cgz");//采购组
			String cgsl = (String)child.get("cgsl");//采购数量
			String childParams = "{PLWRK:'" + gc + "',PLNUM:'" 
				+ jhddh + "',EKGRP:'" + cgz + "',MENGE:'" + cgsl + "'}"; 
			sb.append(childParams);	
			sb.append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]}");
		
		// 请求参数  PT_ZPPS0022<-XML=FUNCTION(getItemXML())
		String params = "{'PT_ZPPS0022': "+ sb.toString() + "}";
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "ZPPI0011", params, 1, -1, null);
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
