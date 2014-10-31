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

@Service
@Transactional
public class TradeChangeListener implements TaskListener {

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	
	@Autowired 
	SAPRfcService saprfcservice;
	
	@Override
	public String getName() {
		return "ERP_计划管理_交期变更申请表";
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
		String VBELN = (String)dataMap.get("xsdd");//销售订单
		String ZZRBM = (String)dataMap.get("xzzrbm");//责任部门
		if(StringUtils.isNotBlank(ZZRBM)){
			ZZRBM = DyFormApiFacade.getRealValue(ZZRBM);
		}else{
			ZZRBM = "";
		}
		
		String ZZRR = (String)dataMap.get("xzzrr");//责任人
		if(StringUtils.isNotBlank(ZZRR)){
			ZZRR = DyFormApiFacade.getRealValue(ZZRR);
		}else{
			ZZRR = "";
		}
		
		String ZYCDL = (String)dataMap.get("ycdl");//异常大类
		if(StringUtils.isNotBlank(ZYCDL)){
			ZYCDL = DyFormApiFacade.getRealValue(ZYCDL);
		}else{
			ZYCDL = "";
		}
		
		List<Map<String, Object>> childList = dyformdata.getFormDatasById("uf_jhgl_jqbgsqb");//	uf_scgl_bqxx
		// 循环从表数据
		StringBuffer sb = new StringBuffer();
		sb.append("{row:[");
		for(Map<String,Object> child:childList){
			Integer i = 10;
			String xh = Integer.toString(i);
			
			String POSNR = (String)child.get("hxmh");//原开始日期
			
			String ZBPDATE = (String)child.get("gqcjq");//更前船交期
			if(StringUtils.isNotBlank(ZBPDATE)){
				
				ZBPDATE = ZBPDATE.replaceAll("[^0-9]", "");
			}else{
				ZBPDATE = "";
			}
			
			String ZEGDATE = (String)child.get("ghyhrq");//更后验货日期
			if(StringUtils.isNotBlank(ZEGDATE)){
				
				ZEGDATE = ZEGDATE.replaceAll("[^0-9]", "");
			}else{
				ZEGDATE = "";
			}
			
			
			String GLTRS = (String)child.get("gqzgrq");//更前装柜日期
			if(StringUtils.isNotBlank(GLTRS)){
				
				GLTRS = GLTRS.replaceAll("[^0-9]", "");
			}else{
				GLTRS = "";
			}
			
			String ZDATE = (String)child.get("ghwgrq");//更后完工日期
			if(StringUtils.isNotBlank(ZDATE)){
				ZDATE = ZDATE.replaceAll("[^0-9]", "");
			}else{
				ZDATE = "";
			}
			
			
			String ZPORD = (String)child.get("ZPORD");//
			if(StringUtils.isEmpty(ZPORD)){
				ZPORD = "";
			}
			String ZPOOD = (String)child.get("ZPOOD");//
			if(StringUtils.isEmpty(ZPOOD)){
				ZPOOD = "";
			}
			
			String ZBGLX = (String)child.get("ZBGLX");//原开始日期
			if(StringUtils.isEmpty(ZBGLX)){
				ZBGLX = "";
			}else{
				ZBGLX = ZBGLX.replaceAll("[^0-9]", "");
			}
			String childParams = "{VBELN:'" + VBELN + "',POSNR:'" 
				+ POSNR + "',ZBPDATE:'" + ZBPDATE + "',ZEGDATE:'"
				+ ZEGDATE + "',GLTRS:'" + GLTRS + "',ZDATE:'" + ZDATE
				+ "',ZPORD:'" + ZPORD + "',ZPOOD:'" + ZPOOD
				+ "',ZZRBM:'" + ZZRBM + "',ZZRR:'" + ZZRR
				+ "',ZYCDL:'" + ZYCDL + "',ZBGLX:'" + ZBGLX
				+ "'}"; 
			sb.append(childParams);	
			sb.append(",");
			i = i + 10;
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]}");
		
		// 请求参数
		String params = "{'PT_ZSDS0028':" + sb.toString() + "}";
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "ZSDI0007", params, 1, -1, null);
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
