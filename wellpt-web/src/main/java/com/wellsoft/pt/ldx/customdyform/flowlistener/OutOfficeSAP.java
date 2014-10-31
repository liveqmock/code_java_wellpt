package com.wellsoft.pt.ldx.customdyform.flowlistener;

import java.util.Map;

import javax.transaction.Transactional;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellsoft.pt.basicdata.sap.service.SAPRfcService;
import com.wellsoft.pt.basicdata.sap.util.SAPRfcJson;
import com.wellsoft.pt.bpm.engine.context.event.Event;
import com.wellsoft.pt.bpm.engine.context.listener.TaskListener;
import com.wellsoft.pt.bpm.engine.exception.WorkFlowException;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.DyFormData;


@Service("outOfficeSAP")
@Transactional
public class OutOfficeSAP implements TaskListener{

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	@Autowired 
	private SAPRfcService saprfcservice;
	
	/**
	 * 此流程监听类的名字
	 */
	@Override
	public String getName() {
		return "公出申请单（总部）回写SAP";
	}

	/**
	 * 此流程监听类的排序
	 */
	@Override
	public int getOrder() {
		return 7;
	}

	/**
	 * 流程节点提交时执行的方法
	 */
	@Override
	public void onCompleted(Event event) throws WorkFlowException {
		// 获得流程表单的信息 
		DyFormData dyformdata = dyFormApiFacade.getDyFormData(event.getFormUuid(), event.getDataUuid());
		// 获得主表表单数据
		Map<String,Object> dataMap = dyformdata.getFormDataByFormUuidAndDataUuid(event.getFormUuid(), event.getDataUuid());
		StringBuffer params = new StringBuffer();
		String nkssj = ((String)dataMap.get("nkssj") + "00").replaceAll("[^0-9]", "");
		String njssj = ((String)dataMap.get("njssj") + "00").replaceAll("[^0-9]", "");
		params.append("{PI_PERNR:'");
		params.append(dataMap.get("ygbh"));// 员工编号
		params.append("',PI_BEGDA:'");
		if(nkssj.length()==14)
			params.append(nkssj.substring(0, 8));// 拟开始日期
		params.append("',PI_ENDDA:'");
		if(njssj.length()==14)
			params.append(njssj.substring(0, 8));// 拟结束日期
		params.append("',PI_AWART:'");
		if(dataMap.get("cqqlxz")!=null)
			params.append(dataMap.get("cqqlxz"));// 类型（0001：外出办事，0002：外出培训，0003：出差）
		params.append("',PI_BEGUZ:'");
		if(nkssj.length()==14)
			params.append(nkssj.substring(8));// 拟开始时间
		params.append("',PI_ENDUZ:'");
		if(njssj.length()==14)
			params.append(njssj.substring(8));// 拟结束时间
		params.append("'}");
		
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI001501", params.toString(), 1, -1, null);
		
		// 解析返回结果
		if(rfcjson == null){
			throw new WorkFlowException("SAP接口没有返回消息！");
		}
		JSONArray jArray = rfcjson.getRecord("PT_RETURN").getJSONArray("row");
		if(jArray.size()==0){
			throw new WorkFlowException("SAP接口没有返回消息！");
		}else if(!"S".equals(jArray.getJSONObject(0).getString("TYPE"))){
			throw new WorkFlowException(jArray.getJSONObject(0).getString("MESSAGE"));
		}
			
	}

	/**
	 * 流程节点创建时执行的方法
	 */
	@Override
	public void onCreated(Event event) throws WorkFlowException {
		
	}

}
