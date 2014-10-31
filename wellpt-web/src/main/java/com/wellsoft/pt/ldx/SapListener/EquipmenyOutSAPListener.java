package com.wellsoft.pt.ldx.SapListener;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.basicdata.sap.service.SAPRfcService;
import com.wellsoft.pt.basicdata.sap.util.SAPRfcJson;
import com.wellsoft.pt.bpm.engine.context.event.Event;
import com.wellsoft.pt.bpm.engine.context.listener.TaskListener;
import com.wellsoft.pt.bpm.engine.exception.WorkFlowException;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.DyFormData;

@Service
@Transactional
public class EquipmenyOutSAPListener implements TaskListener {
	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	@Autowired
	SAPRfcService saprfcservice;

	// private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
	// private SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm:ss");

	/**
	 * 此流程监听类的名字
	 */
	@Override
	public String getName() {
		return "设备委外维修SAP回写";
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
	@SuppressWarnings("unused")
	@Override
	public void onCompleted(Event event) throws WorkFlowException {
		// 获得流程表单的信息
		DyFormData dyformdata = dyFormApiFacade.getDyFormData(event.getFormUuid(), event.getDataUuid());
		dyformdata.getFormDataByFormUuidAndDataUuid(event.getFormUuid(), event.getDataUuid());
		// 通过从表id获得从表数据
		List<Map<String, Object>> childList = dyformdata.getFormDatasById("uf_cggl_sbwwwxsq_dtbd");
		for (Map<String, Object> child : childList) {
			// 请求参数
			// String params = "{row:[{'AUFNR':'" + child.get("wxddh") + "'}]}";
			String params = "{'PT_ZPMT0002':{'row':[{'AUFNR':'" + child.get("wxddh") + "'}]}}";
			// PT_ZPMT0002 {"PT_ZPMT0002":{"row":[{"AUFNR":"1"}]}}
			// new Date();
			SAPRfcJson sap = saprfcservice.RFC("sapConnectConfig", "ZPMI0002", params, 1, -1, null);
			
			// 解析返回结果
			if(sap == null){
				throw new WorkFlowException("SAP接口没有返回消息！");
			}
			JSONArray jObjects = sap.getRecord("PT_RETURN").getJSONArray("row");
			if(jObjects.size()==0){
				throw new WorkFlowException("SAP接口没有返回消息！");
			}else if("E".equals(jObjects.getJSONObject(0).getString("TYPE"))){
				throw new WorkFlowException(jObjects.getJSONObject(0).getString("MESSAGE"));
			}
		}
	}

	/**
	 * 流程节点创建时执行的方法
	 */
	@Override
	public void onCreated(Event event) throws WorkFlowException {

	}
	public String toStr(String str){
		str = str.replaceAll("\"", "");
		str = str.replace("{", "");
		str = str.replace("}", "");
		String[] retStr = str.split(":");
		return retStr[0];
	}
}
