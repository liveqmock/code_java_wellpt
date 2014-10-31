package com.wellsoft.ldx.hr.support;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.axis.utils.StringUtils;
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
 * @author huangwy
 * 描述:ERP_物料管理_采购价格录入申请
 */
@Service
@Transactional
public class PurchasePriceEntryListener implements TaskListener{

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	
	@Autowired 
	SAPRfcService saprfcservice;
	
	@Override
	public String getName() {
		return "ERP_物料管理_采购价格录入申请";
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
	
		List<Map<String, Object>> childList = dyformdata.getFormDatasById("uf_erp_wlgl_cgjglrsq_dtbg");//	uf_scgl_bqxx
		// 循环从表数据
		StringBuffer sb = new StringBuffer();
		sb.append("{row:[");
		for(Map<String,Object> child:childList){
			
			String EKORG = (String)child.get("cgzz");
			if(StringUtils.isEmpty(EKORG)){
				EKORG = "";
			}
			
			String WERKS = (String)child.get("gc");
			if(StringUtils.isEmpty(WERKS)){
				WERKS = "";
			}
			
			String LIFNR = (String)child.get("gysh");
			if(StringUtils.isEmpty(LIFNR)){
				LIFNR = "";
			}
			
			String MATNR = (String)child.get("wlbm");//供应商名称
			if(StringUtils.isEmpty(MATNR)){
				MATNR = "";
			}
			
			String ESOKZ = (String)child.get("xxlb");
			if(StringUtils.isEmpty(ESOKZ)){
				ESOKZ = "";
			}
			BigDecimal APLFZ = (BigDecimal)child.get("jhjhsj");
			
			String WGLIF = (String)child.get("gyscpxh");//计划交货时间
			if(StringUtils.isEmpty(WGLIF)){
				WGLIF = "";
			}
			
			String MINBM = (String)child.get("zxdgl");
			if(StringUtils.isEmpty(MINBM)){
				MINBM = "";
			}
			
			String UEBTO = (String)child.get("gljhxd");
			if(StringUtils.isEmpty(UEBTO)){
				UEBTO = "";
			}
			
			String MWSKZ = (String)child.get("sdm");
			if(StringUtils.isEmpty(MWSKZ)){
				MWSKZ = "";
			}
			
			String KSTBM1 = (String)child.get("qssl1");
			if(StringUtils.isEmpty(KSTBM1)){
				KSTBM1 = "";
			}
			
			String KBETR1 = (String)child.get("jj1");
			if(StringUtils.isEmpty(KBETR1)){
				KBETR1 = "";
			}
			
			String KSTBM2 = (String)child.get("qssl2");
			if(StringUtils.isEmpty(KSTBM2)){
				KSTBM2 = "";
			}
			
			String KBETR2 = (String)child.get("jj2");
			if(StringUtils.isEmpty(KBETR2)){
				KBETR2 = "";
			}
			
			String KSTBM3 = (String)child.get("qssl3");//起始数量2
			if(StringUtils.isEmpty(KSTBM3)){
				KSTBM3 = "";
			}
			
			String KBETR3 = (String)child.get("jj3");
			if(StringUtils.isEmpty(KBETR3)){
				KBETR3 = "";
			}
			
			String KSTBM4 = (String)child.get("qssl4");
			if(StringUtils.isEmpty(KSTBM4)){
				KSTBM4 = "";
			}
			
			String KBETR4 = (String)child.get("jj4");
			if(StringUtils.isEmpty(KBETR4)){
				KBETR4 = "";
			}
			
			
			String KSTBM5 = (String)child.get("qssl5");
			if(StringUtils.isEmpty(KSTBM5)){
				KSTBM5 = "";
			}
			
			String KBETR5 = (String)child.get("jj5");
			if(StringUtils.isEmpty(KBETR5)){
				KBETR5 = "";
			}
			
			String KSTBM6 = (String)child.get("qssl6");
			if(StringUtils.isEmpty(KSTBM6)){
				KSTBM6 = "";
			}
			
			String KBETR6 = (String)child.get("jj6");
			if(StringUtils.isEmpty(KBETR6)){
				KBETR6 = "";
			}
			
			String KSTBM7 = (String)child.get("qssl7");
			if(StringUtils.isEmpty(KSTBM7)){
				KSTBM7 = "";
			}
			
			String KBETR7 = (String)child.get("jj7");
			if(StringUtils.isEmpty(KBETR7)){
				KBETR7 = "";
			}
			
			String KSTBM8 = (String)child.get("qssl8");
			if(StringUtils.isEmpty(KSTBM8)){
				KSTBM8 = "";
			}
			
			String KBETR8 = (String)child.get("jj8");
			if(StringUtils.isEmpty(KBETR8)){
				KBETR8 = "";
			}
			
			String KSTBM9 = (String)child.get("qssl9");
			if(StringUtils.isEmpty(KSTBM9)){
				KSTBM9 = "";
			}
			
			String KBETR9 = (String)child.get("jj9");
			if(StringUtils.isEmpty(KBETR9)){
				KBETR9 = "";
			}
			
			String KSTBM10 = (String)child.get("qssl10");
			if(StringUtils.isEmpty(KSTBM10)){
				KSTBM10 = "";
			}
			
			String KBETR10 = (String)child.get("jj10");
			if(StringUtils.isEmpty(KBETR10)){
				KBETR10 = "";
			}
			
			String KSTBM11 = (String)child.get("qssl11");
			if(StringUtils.isEmpty(KSTBM11)){
				KSTBM11 = "";
			}
			
			String KBETR11 = (String)child.get("jj11");
			if(StringUtils.isEmpty(KBETR11)){
				KBETR11 = "";
			}
			
			String WAERS = (String)child.get("bz");//币种
			if(StringUtils.isEmpty(WAERS)){
				WAERS = "";
			}
			String PEINH = (String)child.get("jgdw");
			String BPRME = (String)child.get("dddw");
			
			String DATAB = (String)child.get("yxrqc");//有效日期从
			DATAB = DATAB.replaceAll("[^0-9]", "");
			
			String DATBI = (String)child.get("yxrqz");//有效日期至
			DATBI = DATBI.replaceAll("[^0-9]", "");
			
			String IDNLF = (String)child.get("bz");//备注
			if(StringUtils.isEmpty(IDNLF)){
				IDNLF = "";
			}
			
			String NORBM = (String)child.get("");
			if(StringUtils.isEmpty(NORBM)){
				NORBM = "";
			}
			String KBETR = (String)child.get("");
			if(StringUtils.isEmpty(KBETR)){//StringUtils.isEmpty(KSTBM)
				KBETR = "";
			}
			String KSTBM = (String)child.get("");
			if(StringUtils.isEmpty(KSTBM)){
				KSTBM = "";
			}
			
			String childParams = "{EKORG:'" + EKORG + "',WERKS:'" + WERKS 
				+ "',LIFNR:'" + LIFNR + "',MATNR:'" + MATNR
				+ "',ESOKZ:'" + ESOKZ + "',APLFZ:'" + APLFZ
				+ "',WGLIF:'" + WGLIF + "',MINBM:'" + MINBM
				+ "',UEBTO:'" + UEBTO + "',MWSKZ:'" + MWSKZ
				
				+ "',KSTBM1:'" + KSTBM1 + "',KBETR1:'" + KBETR1
				+ "',KSTBM2:'" + KSTBM2 + "',KBETR2:'" + KBETR2
				+ "',KSTBM3:'" + KSTBM3 + "',KBETR3:'" + KBETR3
				+ "',KSTBM4:'" + KSTBM4 + "',KBETR4:'" + KBETR4
				+ "',KSTBM5:'" + KSTBM5 + "',KBETR5:'" + KBETR5
				
				+ "',KSTBM6:'" + KSTBM6 + "',KBETR6:'" + KBETR6
				+ "',KSTBM7:'" + KSTBM7 + "',KBETR7:'" + KBETR7
				+ "',KSTBM8:'" + KSTBM8 + "',KBETR8:'" + KBETR8
				+ "',KSTBM9:'" + KSTBM9 + "',KBETR9:'" + KBETR9
				+ "',KSTBM10:'" + KSTBM10 + "',KBETR10:'" + KBETR10
				+ "',KSTBM11:'" + KSTBM11 + "',KBETR11:'" + KBETR11
				
				
				+ "',WAERS:'" + WAERS + "',PEINH:'" + PEINH
				+ "',BPRME:'" + BPRME + "',DATAB:'" + DATAB
				+ "',DATBI:'" + DATBI + "',IDNLF:'" + IDNLF
				+ "',NORBM:'" + NORBM + "',KBETR:'" + KBETR
				+ "',KSTBM:'" + KSTBM 
				+ "'}"; 
			sb.append(childParams);	
			sb.append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]}");
		
		// 请求参数
		String params = "{'PT_ZSDS0028':" + sb.toString() + "}";
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "ZMMI0020", params, 1, -1, null);
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
