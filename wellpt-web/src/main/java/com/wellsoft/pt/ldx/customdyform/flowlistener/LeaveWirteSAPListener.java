package com.wellsoft.pt.ldx.customdyform.flowlistener;

import java.util.List;
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


@Service
@Transactional
public class LeaveWirteSAPListener implements TaskListener{

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	@Autowired 
	private SAPRfcService saprfcservice;
	
	/**
	 * 此流程监听类的名字
	 */
	@Override
	public String getName() {
		return "请销假申请单（总部）[请假]回写SAP";
	}

	/**
	 * 此流程监听类的排序
	 */
	@Override
	public int getOrder() {
		return 16;
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
		String pernr = (String) dataMap.get("gh");
		// 通过从表id获得从表数据
		List<Map<String, Object>> childList = dyformdata.getFormDatasById("userform_rsgl_qjxx");
		String begda = "";
		String endda = "";
		String beguz = "";
		String enduz = "";
		// 循环从表数据
		int rowNum = 0;
		for(Map<String,Object> child:childList){
			rowNum ++;
			begda = ((String) child.get("StartDate")).replaceAll("[^0-9]", "");
			endda = ((String) child.get("EndDate")).replaceAll("[^0-9]", "");
			beguz = ((String) child.get("StartTime") + ":00").replaceAll("[^0-9]", "");
			enduz = ((String) child.get("EndTime") + ":00").replaceAll("[^0-9]", "");
			// 请求参数
			String params = "{PI_PERNR:'" + pernr + "',PI_BEGDA:'" 
					+ begda + "',PI_ENDDA:'" + endda + "',PI_BEGUZ:'" 
					+ beguz + "',PI_ENDUZ:'" + enduz + "',PI_AWART:'" + child.get("Type_QJZ") + "'}";
			SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "zhri000801", params, 1, -1, null);
			// 解析返回结果，拼接页面的提示信息
			if(rfcjson == null){
				throw new WorkFlowException("第" + rowNum + "行：SAP接口没有返回消息！");
			}
			JSONArray jArray = rfcjson.getRecord("PT_RETURN").getJSONArray("row");
			if(jArray.size()==0){
				throw new WorkFlowException("第" + rowNum + "行：SAP接口没有返回消息！");
			}else if(!"S".equals(jArray.getJSONObject(0).getString("TYPE"))){
				throw new WorkFlowException("第" + rowNum + "行：" + jArray.getJSONObject(0).getString("MESSAGE"));
			}
		}
	}

	/**
	 * 流程节点创建时执行的方法
	 */
	@Override
	public void onCreated(Event event) throws WorkFlowException {
		
	}

}
