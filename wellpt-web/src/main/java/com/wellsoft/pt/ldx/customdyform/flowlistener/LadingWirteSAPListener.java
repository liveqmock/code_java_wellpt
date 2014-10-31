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
public class LadingWirteSAPListener implements TaskListener{

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	@Autowired 
	private SAPRfcService saprfcservice;
	
	
	/**
	 * 此流程监听类的名字
	 */
	@Override
	public String getName() {
		return "提单放单申请单回写SAP";
	}

	/**
	 * 此流程监听类的排序
	 */
	@Override
	public int getOrder() {
		return 20;
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
		// 通过从表id获得从表数据
		List<Map<String, Object>> childList = dyformdata.getFormDatasById("uf_erp_ywgl_tdfdsqddtbg");
		// 请求参数
		StringBuffer params = new StringBuffer();
		params.append("{PT_LIKP:{row:[");
		for(Map<String,Object> child:childList){
			params.append("{VBELN:'");
			if(child.get("wxjhdh")!=null)
				params.append(child.get("wxjhdh"));
			params.append("',BOLNR:'");
			if(child.get("tdh")!=null)
				params.append(child.get("tdh"));
			params.append("',ZBOFD:'");
			params.append(((String)dataMap.get("tdfdrq")).replaceAll("[^0-9]", ""));
			
			params.append("'},");
		}
		if(params.toString().endsWith(",")){
			params.deleteCharAt(params.length()-1);
		}
		params.append("]}}");
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "ZOA_DELIVERY_CHANGE", params.toString(), 1, -1, null);
		// 解析返回结果
		if(rfcjson == null){
			throw new WorkFlowException("SAP接口没有返回消息！");
		}
		JSONArray jArray = rfcjson.getRecord("PT_RETURN").getJSONArray("row");
		if(jArray.size()<=0){
			throw new WorkFlowException("SAP接口没有返回消息！");
		}else{
			StringBuffer remsg = new StringBuffer();
			boolean isSuccess = true;
			for(int i = 0 ;i < jArray.size();i++){
				remsg.append(jArray.getJSONObject(i).getString("MESSAGE") + "<br/>");
				if(!"S".equals(jArray.getJSONObject(i).getString("TYPE"))){
					isSuccess = false;
				}
			}
			if(!isSuccess){
				throw new WorkFlowException(remsg.toString());
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
