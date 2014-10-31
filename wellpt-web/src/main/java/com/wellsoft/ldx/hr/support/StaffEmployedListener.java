package com.wellsoft.ldx.hr.support;

import java.util.Map;

import javax.transaction.Transactional;

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
public class StaffEmployedListener implements TaskListener{

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	
	@Autowired 
	SAPRfcService saprfcservice;
	
	@Override
	public String getName() {
		return "员工录用审批表(专项工作组)回写SAP";
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
		//ZHRI0018
		
		
		
		StringBuffer sb = new StringBuffer();
		sb.append("{row:[");
		
			
			String ygbh = (String)dataMap.get("yprybm");//应聘人工号
			String yprx = (String)dataMap.get("yprx");//应聘人性
			String yprm = (String)dataMap.get("yprm");//应聘人名
			String ywm = (String)dataMap.get("ywm");//英文名
			String xb = (String)dataMap.get("xb");//英文名
			//性别真实值
			xb = DyFormApiFacade.getRealValue(xb);
			
			String ypzwbm = (String)dataMap.get("ypzwbm");//应聘职位编码
			
			String rsfw = (String)dataMap.get("rsfw");//人事范围
			//人事范围真实值
			rsfw = DyFormApiFacade.getRealValue(rsfw);
			
			String rszfw = (String)dataMap.get("rszfw");//人事子范围
			//人事子范围真实值
			rszfw = DyFormApiFacade.getRealValue(rszfw);
			
			String gj = (String)dataMap.get("gj");//国籍
			String jg = (String)dataMap.get("jg");//籍贯
			String csrq = (String)dataMap.get("csrq");//出生日期
			String hyzk = (String)dataMap.get("hyzk");//婚姻状况
			//婚姻状况真实值
			hyzk = DyFormApiFacade.getRealValue(hyzk);
			
			String bdsj = (String)dataMap.get("bdsj");//报道时间
			String gzfw = (String)dataMap.get("gzfw");//工资范围
			//工资范围真实值
			gzfw = DyFormApiFacade.getRealValue(gzfw);
			
			String PERSG = "1";
			String PERSK = "Z2";
			String zpqd = (String)dataMap.get("zpqd");//招聘渠道
			//招聘渠道真实值
			zpqd = DyFormApiFacade.getRealValue(zpqd);
			
			String APLNO = (String)dataMap.get("yprybm");//应聘人编号
			
//			String yksrq = (String)dataMap.get("yksrq");//原开始日期
//			yksrq = yksrq.replaceAll("[^0-9]", "");
			String SHORT3 = (String)dataMap.get("SHORT3");
			SHORT3 = DyFormApiFacade.getRealValue(SHORT3);
			String childParams = "{PERNR:'" + ygbh + "',NACHN:'" + yprx 
					+ "',VORNA:'" + yprm + "',NAME2:'" + ywm
					+ "',GESCH:'" + xb + "',PLANS:'" + ypzwbm 
					+ "',WERKS:'" + rsfw + "',BTRTL:'" + rszfw 
					+ "',NATIO:'" + gj + "',GBORT:'" + jg 
					+ "',GBDAT:'" + csrq + "',FAMST:'" + hyzk 
					+ "',BEGDA:'" + bdsj + "',ABKRS:'" + gzfw
					+ "',PERSG:'" + PERSG + "',PERSK:'" + PERSK 
					+ "',MASSG:'" + zpqd + "',APLNO:'" + APLNO + 
					"'}"; 
			
			
			sb.append(childParams);	
			sb.append(",");
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]}");
		//PT_ZHRS0034
		String params = "{'PT_ZHRS0034':" + sb.toString() + "}";
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI0018", params, 1, -1, null);
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
