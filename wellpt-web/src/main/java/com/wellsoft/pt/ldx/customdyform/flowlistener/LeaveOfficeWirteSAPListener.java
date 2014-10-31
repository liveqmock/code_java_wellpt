package com.wellsoft.pt.ldx.customdyform.flowlistener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.transaction.Transactional;

import net.sf.json.JSONObject;

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
public class LeaveOfficeWirteSAPListener implements TaskListener{

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	@Autowired 
	private SAPRfcService saprfcservice;
	
	private SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	
	/**
	 * 此流程监听类的名字
	 */
	@Override
	public String getName() {
		return "离职申请表（国际营销事业部）[离职]回写SAP";
	}

	/**
	 * 此流程监听类的排序
	 */
	@Override
	public int getOrder() {
		return 12;
	}

	/**
	 * 流程节点提交时执行的方法
	 */
	@Override
	public void onCompleted(Event event) throws WorkFlowException {
		// 获得流程表单的信息 
		DyFormData dyformdata =dyFormApiFacade.getDyFormData(event.getFormUuid(), event.getDataUuid());
		// 获得主表表单数据
		Map<String,Object> dataMap = dyformdata.getFormDataByFormUuidAndDataUuid(event.getFormUuid(), event.getDataUuid());
		
		// 请求参数
		StringBuffer params = new StringBuffer();
		params.append("{PI_PERNR:'");
		params.append(dataMap.get("ygbh"));// 员工编号
		params.append("',PI_BEGDA:'");
		// 入职日期
		if(dataMap.get("rzrq")==null){
			params.append(yyyyMMdd.format(new Date()));
		}else{
			params.append(dataMap.get("rzrq").toString().replaceAll("[^0-9]", ""));
		}
		params.append("',PI_MASSG:'");
		params.append(dataMap.get("lzyyz"));// 离职原因
		params.append("'}");
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI0004", params.toString(), 1, -1, null);
		// 解析返回结果
		if(rfcjson == null){
			throw new WorkFlowException("SAP接口没有返回消息！");
		}
		JSONObject jObjects = rfcjson.getRecord("PO_RETURN");
		if("".equals(jObjects.getString("TYPE"))){
			throw new WorkFlowException("SAP接口没有返回消息！");
		}else if(!"S".equals(jObjects.getString("TYPE"))){
			throw new WorkFlowException(jObjects.getString("MESSAGE"));
		}
	}

	/**
	 * 流程节点创建时执行的方法
	 */
	@Override
	public void onCreated(Event event) throws WorkFlowException {
		
	}

}
