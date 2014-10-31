package com.wellsoft.pt.ldx.customdyform.flowlistener;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import net.sf.json.JSONArray;

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


@Service("bOrderChangeWirteSAPListener")
@Transactional
public class BOrderChangeWirteSAPListener implements TaskListener{

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	@Autowired 
	private SAPRfcService saprfcservice;
	
	/**
	 * 此流程监听类的名字
	 */
	@Override
	public String getName() {
		return "B类订单变更申请回写SAP";
	}

	/**
	 * 此流程监听类的排序
	 */
	@Override
	public int getOrder() {
		return 2;
	}

	/**
	 * 流程节点提交时执行的方法
	 */
	@Override
	public void onCompleted(Event event) throws WorkFlowException {
		// 获得流程表单的信息 
		DyFormData dyformdata = dyFormApiFacade.getDyFormData(event.getFormUuid(), event.getDataUuid());
		// 获得主表表单数据
		Map<String,Object> dataMap = dyformdata.getFormDataByFormUuidAndDataUuid(event.getFormUuid(), event.getDataUuid());
		for(String key:dataMap.keySet()){
			if(dataMap.get(key)==null){
				dataMap.put(key, "");
			}
		}
		// 通过从表id获得从表数据
		List<Map<String, Object>> childList = dyformdata.getFormDatasById("uf_ywgl_blddbgnr");
		if(StringUtils.isNotBlank((String)dataMap.get("sxbg1z"))){
			StringBuffer params = new StringBuffer();
			params.append("{PI_VBELN:'");
			params.append(dataMap.get("xsddh"));
			params.append("',PT_VBAK4:{row:[");
			for(Map<String,Object> child:childList){
				for(String key:child.keySet()){
					if(child.get(key)==null){
						child.put(key, "");
					}
				}
				params.append("{VBELN:'");
				params.append(dataMap.get("xsddh"));// 销售订单号
				params.append("',POSNR:'");
				params.append(child.get("hxmh"));// 行项目号
				params.append("',KWMENG:'");
				params.append(child.get("gqsl"));// 更前数量
				params.append("',ABGRU:'");
				params.append(child.get("sfqxz"));// 是否取消
				params.append("',ZBPDATE:'");
				params.append(child.get("gqcjq"));// 更前船交期
				params.append("',ZEGDATE:'");
				params.append(child.get("gqyhrq"));// 更前验货日期
				params.append("',GLTRS:'");
				params.append(child.get("gqzgrq"));// 更前装柜日期
				params.append("',EDATU:'");
				params.append(child.get("gqwgrq"));// 更前完工日期
				params.append("',ZPORD:'");
				params.append(dataMap.get("ZPORD"));// 订单变更提交日期
				params.append("',ZPOOD:'");
				params.append(dataMap.get("ZPOOD"));// 订单变更完成日期
				params.append("',ZZRBM:'");
				params.append(dataMap.get("xzzrbmmc"));// 选择责任部门名称
				params.append("',ZZRR:'");
				params.append(dataMap.get("xzzrrxm"));// 选择责任人姓名
				params.append("',ZYCDL:'");
				params.append(dataMap.get("ycdlz"));// 异常大类值
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
		
		// 
		if(StringUtils.isNotBlank((String)dataMap.get("sxbg2z"))){
			StringBuffer params = new StringBuffer();
			params.append("{PI_VBELN:'");
			params.append(dataMap.get("xsddh"));
			params.append("',PT_VBAK4:{row:[");
			for(Map<String,Object> child:childList){
				for(String key:child.keySet()){
					if(child.get(key) == null)
						child.put(key, "");
				}
				params.append("{VBELN:'");
				params.append(dataMap.get("xsddh"));// 销售订单号
				params.append("',POSNR:'");
				params.append(child.get("hxmh"));// 行项目号
				params.append("',KWMENG:'");
				params.append(child.get("ghsl"));// 更后数量
				params.append("',ABGRU:'");
				params.append(child.get("sfqxz"));// 是否取消
				params.append("',ZBPDATE:'");
				params.append(child.get("gqcjq"));// 更前船交期
				params.append("',ZEGDATE:'");
				params.append(child.get("gqyhrq"));// 更前验货日期
				params.append("',GLTRS:'");
				params.append(child.get("gqzgrq"));// 更前装柜日期
				params.append("',EDATU:'");
				params.append(child.get("gqwgrq"));// 更前完工日期
				params.append("',ZPORD:'");
				params.append(dataMap.get("ZPORD"));// 订单变更提交日期
				params.append("',ZPOOD:'");
				params.append(dataMap.get("ZPOOD"));// 订单变更完成日期
				params.append("',ZZRBM:'");
				params.append(dataMap.get("xzzrbmmc"));// 选择责任部门名称
				params.append("',ZZRR:'");
				params.append(dataMap.get("xzzrrxm"));// 选择责任人姓名
				params.append("',ZYCDL:'");
				params.append(dataMap.get("ycdlz"));// 异常大类值
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
	}

	/**
	 * 流程节点创建时执行的方法
	 */
	@Override
	public void onCreated(Event event) throws WorkFlowException {
		
	}

}
