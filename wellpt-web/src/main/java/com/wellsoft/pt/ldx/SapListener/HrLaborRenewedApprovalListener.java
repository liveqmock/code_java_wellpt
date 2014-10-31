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
public class HrLaborRenewedApprovalListener implements TaskListener{
	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	@Autowired
	SAPRfcService saprfcservice;
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "LDX_人事管理_劳动合同续签审批";
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 22;
	}

	@Override
	public void onCompleted(Event event) throws WorkFlowException {
		// 获得流程表单的信息 
		DyFormData dyformdata =dyFormApiFacade.getDyFormData(event.getFormUuid(), event.getDataUuid());
		// 获得主表表单数据
		Map<String,Object> dataMap = dyformdata.getFormDataByFormUuidAndDataUuid(event.getFormUuid(), event.getDataUuid());
		// 通过从表id获得从表数据
		List<Map<String, Object>> childList = dyformdata.getFormDatasById("uf_rsgl_ldhtxqxx");
		// 获取人员等级
		String s1 = "";
		if(null!=dataMap.get("SHORT3")){
			dataMap.get("SHORT3").toString();
			System.out.println("人员等级：" + s1);
			s1 = s1.substring(1, 3);
		}
		
		boolean flag = true;
		String callback = "";
//		String params = "{PT_ZHRS0010:{row:[" ;
		for(int i=0;i<childList.size();i++){
			Map<String,Object> child = childList.get(i);
			// 请求参数
			String params = "{PT_ZHRS0010:{row:[" ;
			params += "{'ZSERIAL':'"+i+"','PERNR':'"+child.get("ygbh")+"','BEGDA':'"+ child.get("yksrq")+"','ENDDA':'99991231'" +
					",'CTEDT':'"+child.get("yjzrq")+"','CTTYP':'01'}";
			//if(i<childList.size()-1)params += ",";
			params += "]}}";
			SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI000601", params, 1, -1, null);
			JSONArray jObjects = rfcjson.getRecord("PT_RETURN").getJSONArray("row");
			if(rfcjson == null||jObjects.size()==0){
				flag = false;
			}else if("E".equals(jObjects.getJSONObject(0).getString("TYPE"))){
				callback = jObjects.getJSONObject(0).getString("MESSAGE");
			}
		}
		if(flag){
			throw new WorkFlowException(callback);
		}else{
			throw new WorkFlowException("SAP接口没有返回消息！");
		}
		//params += "]}}";
		//回写接口
		/*SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI000601", params, 1, -1, null);
		System.out.println("回写接口返回值："+rfcjson);
		// 解析返回结果
		if(rfcjson == null){
			throw new WorkFlowException("SAP接口没有返回消息！");
		}
		JSONArray jObjects = rfcjson.getRecord("PT_RETURN").getJSONArray("row");
		if(jObjects.size()==0){
			throw new WorkFlowException("SAP接口没有返回消息！");
		}else if("E".equals(jObjects.getJSONObject(0).getString("TYPE"))){
			throw new WorkFlowException(jObjects.getJSONObject(0).getString("MESSAGE"));
		}*/
	}

	@Override
	public void onCreated(Event arg0) throws WorkFlowException {
		// TODO Auto-generated method stub
		
	}

}
