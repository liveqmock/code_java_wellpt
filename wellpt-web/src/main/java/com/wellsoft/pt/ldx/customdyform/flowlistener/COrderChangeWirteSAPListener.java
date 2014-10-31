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


@Service("cOrderChangeWirteSAPListener")
@Transactional
public class COrderChangeWirteSAPListener implements TaskListener{

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	@Autowired 
	private SAPRfcService saprfcservice;
	
	/**
	 * 此流程监听类的名字
	 */
	@Override
	public String getName() {
		return "C类订单变更回写SAP";
	}

	/**
	 * 此流程监听类的排序
	 */
	@Override
	public int getOrder() {
		return 3;
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
		for(String key:dataMap.keySet()){
			if(dataMap.get(key)==null)
				dataMap.put(key, "");
		}
		// 通过从表id获得从表数据
		List<Map<String, Object>> childList = dyformdata.getFormDatasById("uf_ywgl_clddbgnr");
		StringBuffer params = new StringBuffer();
		params.append("{PI_VBELN:'");
		params.append(dataMap.get("xsddh"));
		params.append("',PT_VBAK4:{row:[");
		for(Map<String,Object> child:childList){
			params.append("{VBELN:'");
			params.append(dataMap.get("xsddh"));// 销售订单号
			params.append("',POSNR:'");
			if(child.get("hxmh") != null)
				params.append(child.get("hxmh"));// 行项目号
			params.append("',KWMENG:'");
			if(child.get("ddsl") != null)
				params.append(child.get("ddsl"));// 订单数量
			params.append("',ZBPDATE:'");
			if(child.get("ghcjq") != null)
				params.append(child.get("ghcjq"));// 更后船交期
			params.append("',ZEGDATE:'");
			if(child.get("ghyhrq") != null)
				params.append(child.get("ghyhrq"));// 更后验货日期
			params.append("',GLTRS:'");
			if(child.get("ghzgrq") != null)
				params.append(child.get("ghzgrq"));// 更后装柜日期
			params.append("',EDATU:'");
			if(child.get("ghwgrq") != null)
				params.append(child.get("ghwgrq"));// 更后完工日期
			params.append("',ZPORD:'");
			params.append(dataMap.get("ZPORD"));
			params.append("',ZPOOD:'");
			params.append(dataMap.get("ZPOOD"));
			params.append("',ZZRBM:'");
			params.append(dataMap.get("xzzrbm"));
			params.append("',ZZRR:'");
			params.append(dataMap.get("xzzrr"));
			params.append("',ZYCDL:'");
			params.append(dataMap.get("ycdl"));
			params.append("',ZBGLX:'");
			params.append(dataMap.get("ZBGLX"));
			params.append("'},");
		}
		if(params.toString().endsWith(",")){
			params.deleteCharAt(params.length()-1);
		}
		params.append("]}}");
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "ZOA_SO_CHANGE", params.toString(), 1, -1, null);
		// 解析返回结果
		if(rfcjson == null){
			throw new WorkFlowException("SAP接口没有返回消息！");
		}
		JSONArray jArray = rfcjson.getRecord("PT_RETURN").getJSONArray("row");
		if(jArray.size()==0){
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
