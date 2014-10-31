package com.wellsoft.pt.ldx.SapListener;

import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
import com.wellsoft.pt.ldx.util.StringUtils;

@Service
@Transactional
public class MobilizeApplicationsListenter implements TaskListener {
	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	@Autowired
	SAPRfcService saprfcservice;

	@Override
	public String getName() {
		return "LDX_人事管理_调动申请表_回写";
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 21;
	}

	@Override
	public void onCompleted(Event event) throws WorkFlowException {
		// 获得流程表单的信息
		DyFormData dyformdata = dyFormApiFacade.getDyFormData(
				event.getFormUuid(), event.getDataUuid());
		// 获得主表表单数据
		Map<String, Object> dataMap = dyformdata
				.getFormDataByFormUuidAndDataUuid(event.getFormUuid(),
						event.getDataUuid());

		String x = dataMap.get("ddlx").toString();
		String y = dataMap.get("kbmddlx").toString();

		if (!"02".equals(x)) {
			if (!"".equals(y)) {
				String params = "{PI_PERNR:'" + dataMap.get("gh")
						+ "',PI_BEGDA:'" + dataMap.get("sxrq") + "',PI_MASSG:'"
						+ this.toStr(dataMap.get("ddlx").toString()) + "',PI_PLANS:'"
						+ dataMap.get("xzw") + "',PI_BTRTL:'"
						+ dataMap.get("xrszfw") + "',PI_PERSK:'"
						+ this.toStr(dataMap.get("xygzz").toString()) + "',PI_WERKS:'"
						+ this.toStr(dataMap.get("xgs").toString()) + "',PI_PERSG:'1'}";
				// 回写接口
				SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig",
						"ZHRI0002", params, 1, -1, null);
				System.out.println("回写接口返回值：" + rfcjson);

				// 解析返回结果
				if(rfcjson == null){
					throw new WorkFlowException("SAP接口没有返回消息！");
				}
				JSONObject jObjects = rfcjson.getRecord("PO_RETURN");
				if(null==jObjects){
					throw new WorkFlowException("SAP接口没有返回消息！");
				}else if("E".equals(jObjects.getString("TYPE"))){
					throw new WorkFlowException(jObjects.getString("MESSAGE"));
				}
			} else {
				throw new WorkFlowException("请选择跨部门调动类型！");
			}
		}
	}

	@Override
	public void onCreated(Event arg0) throws WorkFlowException {
		// TODO Auto-generated method stub

	}
	public String toStr(String str){
		str = str.replaceAll("\"", "");
		str = str.replace("{", "");
		str = str.replace("}", "");
		String[] retStr = str.split(":");
		return retStr[0];
	}
}
