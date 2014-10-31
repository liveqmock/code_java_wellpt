package com.wellsoft.pt.ldx.SapListener;

import java.math.BigDecimal;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
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
public class EmployeeIncentiveListener  implements TaskListener{
	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	@Autowired
	SAPRfcService saprfcservice;
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "LDX_人事管理_员工奖惩表单v1.1";
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 23;
	}

	@Override
	public void onCompleted(Event event) throws WorkFlowException {
		// 获得流程表单的信息 
		DyFormData dyformdata =dyFormApiFacade.getDyFormData(event.getFormUuid(), event.getDataUuid());
		// 获得主表表单数据
		Map<String,Object> dataMap = dyformdata.getFormDataByFormUuidAndDataUuid(event.getFormUuid(), event.getDataUuid());
		
		// 获取人员等级
		String s1 = "";
		if(null!=dataMap.get("SHORT3")){
			dataMap.get("SHORT3").toString();
			System.out.println("人员等级：" + s1);
			s1 = s1.substring(1, 3);
		}
		
		String PI_PERNR = (String) dataMap.get("gh");//工号
		if(StringUtils.isBlank(PI_PERNR)){
			PI_PERNR = "";
		}
		String PI_BEGDA = (String) dataMap.get("nsxrq");//拟生效时间
		if(StringUtils.isNotBlank(PI_BEGDA)){
			PI_BEGDA = PI_BEGDA.replaceAll("[^0-9]", "");
		}else{
			PI_BEGDA = "";
		}
		String PI_ENDDA = PI_BEGDA;
		
		String PI_ZJCZL = (String)(dataMap.get("bmjy"));
		if(StringUtils.isNotBlank(PI_ZJCZL)){
			PI_ZJCZL = DyFormApiFacade.getRealValue(PI_ZJCZL);
		}else{
			PI_ZJCZL = "";
		}
		
		String PI_ZJCYY = (String) dataMap.get("jlhgssy");//奖励或过失事由
		if(StringUtils.isBlank(PI_ZJCYY)){
			PI_ZJCYY = "";
		}
		
		BigDecimal PI_ZJCJE = (BigDecimal)dataMap.get("jcje");//奖（罚）金额
		
		String PI_WAERS = "CNY";
		
		String params1 = "{PI_PERNR:'"+ PI_PERNR +"',PI_BEGDA:'"+ PI_BEGDA +"',PI_ENDDA:'"+ PI_ENDDA
		+"',PI_ZJCZL:'"+ PI_ZJCZL +"',PI_ZJCYY:'"+ PI_ZJCYY +"',PI_ZJCJE:'"+ PI_ZJCJE.toString() +"',PI_WAERS:'"+ PI_WAERS +"'}";

		if(!"".equals(dataMap.get("jcje"))){
			SAPRfcJson rfcjson1 = saprfcservice.RFC("sapConnectConfig", "ZHRI003001", params1, 1, -1, null);
		
			String params2 = "{PI_PERNR:'"+ PI_PERNR +"',PI_ZJCZL:'"+ PI_ZJCZL +"',PI_BEGDA:'"+ PI_BEGDA
					+"',PI_BETRG:'"+ PI_ZJCJE +"',PI_WAERS:'"+ PI_WAERS +"'}";
			SAPRfcJson rfcjson2 = saprfcservice.RFC("sapConnectConfig", "ZHRI003002", params2, 1, -1, null);
			
			if(rfcjson1 == null){
				throw new WorkFlowException("回写SAP失败!");
			}
			net.sf.json.JSONArray jObjects = rfcjson1.getRecord("PT_RETURN").getJSONArray("row");
			if(jObjects.size()==0){
				throw new WorkFlowException("SAP接口没有返回消息！");
			}else if("E".equals(jObjects.getJSONObject(0).getString("TYPE"))){
				throw new WorkFlowException(jObjects.getJSONObject(0).getString("MESSAGE"));
			}
			
			if(rfcjson2 == null){
				throw new WorkFlowException("回写SAP失败!");
			}
			net.sf.json.JSONArray jObjects2 = rfcjson2.getRecord("PT_RETURN").getJSONArray("row");
			if(jObjects2.size()==0){
				throw new WorkFlowException("SAP接口没有返回消息！");
			}else if("E".equals(jObjects2.getJSONObject(0).getString("TYPE"))){
				throw new WorkFlowException(jObjects2.getJSONObject(0).getString("MESSAGE"));
			}
			
		}else{
			SAPRfcJson rfcjson1 = saprfcservice.RFC("sapConnectConfig", "ZHRI003001", params1, 1, -1, null);
			if(rfcjson1 == null){
				throw new WorkFlowException("回写SAP失败!");
			}
			net.sf.json.JSONArray jObjects = rfcjson1.getRecord("PT_RETURN").getJSONArray("row");
			if(jObjects.size()==0){
				throw new WorkFlowException("SAP接口没有返回消息！");
			}else if("E".equals(jObjects.getJSONObject(0).getString("TYPE"))){
				throw new WorkFlowException(jObjects.getJSONObject(0).getString("MESSAGE"));
			}
		}
	}

	@Override
	public void onCreated(Event event) throws WorkFlowException {
		
	}
	
}
