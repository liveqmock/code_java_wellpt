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
import com.wellsoft.pt.bpm.engine.context.listener.DirectionListener;
import com.wellsoft.pt.bpm.engine.context.listener.TaskListener;
import com.wellsoft.pt.bpm.engine.exception.WorkFlowException;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.DyFormData;

@Service
@Transactional
public class LaborContractListener implements DirectionListener{


	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	
	@Autowired 
	SAPRfcService saprfcservice;
	
	@Override
	public String getName() {
		return "劳动合同续签审批表(专项工作组)";
	}

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public void transition(Event event) throws WorkFlowException {
		
		// 获得流程表单的信息 
		DyFormData dyformdata =dyFormApiFacade.getDyFormData(event.getFormUuid(), event.getDataUuid());
		// 获得主表表单数据
		Map<String,Object> dataMap = dyformdata.getFormDataByFormUuidAndDataUuid(event.getFormUuid(), event.getDataUuid());
		// 通过从表id获得从表数据
		List<Map<String, Object>> childList = dyformdata.getFormDatasById("uf_rsgl_ldhtxqzxxx");
		//dataMap.get(key)
		// 循环从表数据
		StringBuffer sb = new StringBuffer();
		sb.append("{row:[");
		for(Map<String,Object> child:childList){
			Integer i = 0;
			String xh = Integer.toString(i);
			String ygbh = (String)child.get("ygbh");//员工编号
			if(StringUtils.isBlank(ygbh)){
				ygbh = "";
			}
			
//			String CTEDT = (String)child.get("yjzrq");//原截止日期
//			if(StringUtils.isNotBlank(CTEDT)){
//				CTEDT = CTEDT.replaceAll("[^0-9]", "");
//			}else{
//				CTEDT = "";
//			}
			
			String BEGDA = (String)child.get("xksrq");//新开始日期
			if(StringUtils.isNotBlank(BEGDA)){
				BEGDA = BEGDA.replaceAll("[^0-9]", "");
			}else{
				BEGDA = "";
				throw new WorkFlowException("请输入新开始日期！");
			}
			
			String CTEDT = (String)child.get("xjzrq");//新截止日期
			if(StringUtils.isNotBlank(CTEDT)){
				CTEDT = CTEDT.replaceAll("[^0-9]", "");
			}else{
				CTEDT = "";
				throw new WorkFlowException("请输入新截止日期！");
			}
			
			String CTTYP = "01";
			
			String childParams = "{ZSERIAL:'" + xh + "',PERNR:'" 
				+ ygbh + "',BEGDA:'" + BEGDA + "',ENDDA:'" + "99991231" + "',CTEDT:'" + CTEDT + "',CTTYP:'" + CTTYP + "'}"; 
			sb.append(childParams);	
			sb.append(",");
			i = i + 1;
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]}");
		
		// 请求参数
		String params = "{'PT_ZHRS0010':" + sb.toString() + "}";
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI000601", params, 1, -1, null);
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


}
