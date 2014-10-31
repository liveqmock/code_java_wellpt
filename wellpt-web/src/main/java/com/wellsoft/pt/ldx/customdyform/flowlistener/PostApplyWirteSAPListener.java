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


@Service
@Transactional
public class PostApplyWirteSAPListener implements TaskListener{

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	@Autowired 
	private SAPRfcService saprfcservice;
	
	/**
	 * 此流程监听类的名字
	 */
	@Override
	public String getName() {
		return "职位申请书回写SAP";
	}

	/**
	 * 此流程监听类的排序
	 */
	@Override
	public int getOrder() {
		return 26;
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
		String begda = ((String) dataMap.get("sqsj"));
		String gbdat = ((String) dataMap.get("csrq"));
		String endda = "";
		///////////////////////应聘人员活动，空缺分配，组织分配，个人数据///////////////////////////////
		// 请求参数
		String params = "{PI_BEGDA:'" + begda + "',PI_ENDDA:'99991231',PI_WERKS:'" + dataMap.get("rsfwz") 
				+ "',PI_APTYP:'" + dataMap.get("sqrfwz") + "',PI_APGRP:'" + dataMap.get("sqrzz") 
				+ "',PI_BTRTL:'" + dataMap.get("rszfwz") + "',PI_OBJID:'" + dataMap.get("sqzwbh") 
				+ "',PI_NACHN:'" + dataMap.get("x") + "',PI_VORNA:'" + dataMap.get("m") 
				+ "',PI_NAME2:'" + dataMap.get("ywm") + "',PI_GESC2:'" + dataMap.get("xbz") 
				+ "',PI_GBDAT:'" + gbdat + "',PI_GBORT:'" + dataMap.get("jg") + "',PI_NATIO:'" + dataMap.get("gjz") 
				+ "',PI_FATXT:'" + dataMap.get("hyzkz") + "'}";
		// 应聘人员活动，空缺分配，组织分配，个人数据
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI002702", params, 1, -1, null);
		// 解析返回结果
		if(rfcjson == null){
			throw new WorkFlowException("SAP接口没有返回消息！");
		}
		JSONArray jArray = rfcjson.getRecord("PT_RETURN").getJSONArray("row");
		if(jArray.size()==0){
			throw new WorkFlowException("SAP接口没有返回消息！");
		}else if(!"S".equals(jArray.getJSONObject(0).getString("TYPE"))){
			throw new WorkFlowException(jArray.getJSONObject(0).getString("MESSAGE"));
		}
		
		///////////////////////附加个人数据修改///////////////////////////////
		// 请求参数 
		params = "{PI_APLNO:'" + dataMap.get("ypbm") + "',PI_BEGDA:'" + begda 
				+ "',PI_ENDDA:'99991231',PI_RACKY:'',PI_PCODE:'" + dataMap.get("zzmmz") 
				+ "',PI_ZDACFD:'" + dataMap.get("rsdacfwz") + "'}";
		// 附加个人数据修改
		rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI002704", params, 1, -1, null);
		// 解析返回结果
		if(rfcjson == null){
			throw new WorkFlowException("SAP接口没有返回消息！");
		}
		jArray = rfcjson.getRecord("PT_RETURN").getJSONArray("row");
		if(jArray.size()==0){
			throw new WorkFlowException("SAP接口没有返回消息！");
		}else if(!"S".equals(jArray.getJSONObject(0).getString("TYPE"))){
			throw new WorkFlowException(jArray.getJSONObject(0).getString("MESSAGE"));
		}
		
		///////////////////////PB30申请人身份证（0185）写入///////////////////////////////
		// 请求参数 
		params = "{PI_APLNO:'" + dataMap.get("ypbm") + "',PI_BEGDA:'" + gbdat 
				+ "',PI_ENDDA:'99991231',PI_ICNUM:'" + dataMap.get("zjhm") + "'}";
		// PB30申请人身份证（0185）写入
		rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI002714", params, 1, -1, null);
		// 解析返回结果
		if(rfcjson == null){
			throw new WorkFlowException("SAP接口没有返回消息！");
		}
		jArray = rfcjson.getRecord("PT_RETURN").getJSONArray("row");
		if(jArray.size()==0){
			throw new WorkFlowException("SAP接口没有返回消息！");
		}else if(!"S".equals(jArray.getJSONObject(0).getString("TYPE"))){
			throw new WorkFlowException(jArray.getJSONObject(0).getString("MESSAGE"));
		}
		
		///////////////////////通讯数据修改///////////////////////////////
		// 通过从表id获得从表数据
		List<Map<String, Object>> childList = dyformdata.getFormDatasById("uf_ldx_rsgl_zwsqstxxx");
		int rowNum = 0;
		for(Map<String,Object> child:childList){
			rowNum++;
			// 请求参数 
			params = "{PI_APLNO:'" + dataMap.get("ypbm") + "',PI_BEGDA:'" + begda 
					+ "',PI_ENDDA:'99991231',PI_USRID_LONG:'" + child.get("txhm") 
					+ "',PI_SUBTY:'" + child.get("txlxz") + "'}";
			// 通讯数据修改 
			rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI002705", params, 1, -1, null);
			// 解析返回结果，拼接页面的提示信息
			if(rfcjson == null){
				throw new WorkFlowException("第" + rowNum + "行：SAP接口没有返回消息！");
			}
			jArray = rfcjson.getRecord("PT_RETURN").getJSONArray("row");
			if(jArray.size()==0){
				throw new WorkFlowException("第" + rowNum + "行：SAP接口没有返回消息！");
			}else if(!"S".equals(jArray.getJSONObject(0).getString("TYPE"))){
				throw new WorkFlowException("第" + rowNum + "行：" + jArray.getJSONObject(0).getString("MESSAGE"));
			}
		}
			
		///////////////////////地址数据修改 ///////////////////////////////
		// 通过从表id获得从表数据
		childList = dyformdata.getFormDatasById("uf_ldx_rsgl_zwsqsdzxx");
		rowNum = 0;
		for(Map<String,Object> child:childList){
			rowNum++;
			// 请求参数 
			params = "{PI_APLNO:'" + dataMap.get("ypbm") + "',PI_BEGDA:'" + begda 
					+ "',PI_SUBTY:'"+ child.get("dzlxz") +"',PI_ENDDA:'99991231',PI_LAND1:'" + child.get("gjz") 
					+ "',PI_STATE:'" + child.get("sfz") + "',PI_LOCAT:'" + child.get("xxdz") 
					+ "',PI_PSTLZ:'" + child.get("yzbm") + "'}";
			// 地址数据修改 
			rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI002706", params, 1, -1, null);
			// 解析返回结果，拼接页面的提示信息
			if(rfcjson == null){
				throw new WorkFlowException("第" + rowNum + "行：SAP接口没有返回消息！");
			}
			jArray = rfcjson.getRecord("PT_RETURN").getJSONArray("row");
			if(jArray.size()==0){
				throw new WorkFlowException("第" + rowNum + "行：SAP接口没有返回消息！");
			}else if(!"S".equals(jArray.getJSONObject(0).getString("TYPE"))){
				throw new WorkFlowException("第" + rowNum + "行：" + jArray.getJSONObject(0).getString("MESSAGE"));
			}
		}
		
		///////////////////////应聘者家庭成员/相关人员数据修改 ///////////////////////////////
		// 通过从表id获得从表数据
		childList = dyformdata.getFormDatasById("uf_ldx_rsgl_zwsqsjtcyxx");
		rowNum = 0;
		for(Map<String,Object> child:childList){
			rowNum ++;
			// 请求参数 
			params = "{PI_APLNO:'" + dataMap.get("ypbm") + "',PI_BEGDA:'" + begda 
					+ "',PI_SUBTY:'"+ child.get("cylxz") +"',PI_ENDDA:'99991231',PI_FANAM:'" + child.get("x") 
					+ "',PI_FAVOR:'" + child.get("m") + "',PI_GESC2:'" + child.get("xbz") 
					+ "',PI_TELNR:'" + child.get("tx") + "',PI_ZZFJSJ:'" + child.get("fjsj") + "',PI_LAND1:'CN'";
			if(child.get("csrq") != null){
				String fgbat = ((String) child.get("csrq")).replaceAll("[^0-9]", "");
				params += ",PI_FGBDT:'" + fgbat + "'}";
				// 应聘者家庭成员/相关人员数据修改 (需维护出生日期) 
				rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI002707", params, 1, -1, null);
			}else{
				params += "}";
				// 应聘者家庭成员/相关人员数据修改  (无需维护出生日期) 
				rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI002717", params, 1, -1, null);
			}
			
			// 解析返回结果，拼接页面的提示信息
			if(rfcjson == null){
				throw new WorkFlowException("第" + rowNum + "行：SAP接口没有返回消息！");
			}
			jArray = rfcjson.getRecord("PT_RETURN").getJSONArray("row");
			if(jArray.size()==0){
				throw new WorkFlowException("第" + rowNum + "行：SAP接口没有返回消息！");
			}else if(!"S".equals(jArray.getJSONObject(0).getString("TYPE"))){
				throw new WorkFlowException("第" + rowNum + "行：" + jArray.getJSONObject(0).getString("MESSAGE"));
			}
		}
		
		///////////////////////教育数据修改 ///////////////////////////////
		// 通过从表id获得从表数据
		childList = dyformdata.getFormDatasById("uf_ldx_rsgl_zwsqsjyxx");
		rowNum = 0;
		for(Map<String,Object> child:childList){
			rowNum ++;
			begda = ((String) child.get("rxsj")).replaceAll("[^0-9]", "");
			endda = ((String) child.get("bysj")).replaceAll("[^0-9]", "");
			// 请求参数 
			params = "{PI_APLNO:'" + dataMap.get("ypbm") + "',PI_SUBTY:'" + child.get("jydjz") 
					+ "',PI_SLART:'"+ child.get("jydjz") +"',PI_BEGDA:'" + begda + "',PI_ENDDA:'" + endda
					+ "',PI_AUSBI:'" + child.get("jxxsz") + "',PI_INSTI:'" + child.get("xxmc") 
					+ "',PI_SLAND:'CN',PI_ZZYMC:'" + child.get("zy") + "',PI_ZXL:'" + child.get("xlz") 
					+ "',PI_ZXW:'" + child.get("xwz") + "',PI_ZJXXS:'" + child.get("zsbh") + "',PI_SLABS:'01'}";
			// 教育数据修改
			rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI002708", params, 1, -1, null);
			
			// 解析返回结果，拼接页面的提示信息
			if(rfcjson == null){
				throw new WorkFlowException("第" + rowNum + "行：SAP接口没有返回消息！");
			}
			jArray = rfcjson.getRecord("PT_RETURN").getJSONArray("row");
			if(jArray.size()==0){
				throw new WorkFlowException("第" + rowNum + "行：SAP接口没有返回消息！");
			}else if(!"S".equals(jArray.getJSONObject(0).getString("TYPE"))){
				throw new WorkFlowException("第" + rowNum + "行：" + jArray.getJSONObject(0).getString("MESSAGE"));
			}
		}
		
		///////////////////////PB30资格证书信息(9010)信息修改 ///////////////////////////////
		// 通过从表id获得从表数据
		childList = dyformdata.getFormDatasById("uf_ldx_rsgl_zwsqszgzsxx");
		rowNum = 0;
		for(Map<String,Object> child:childList){
			rowNum ++;
			begda = ((String) child.get("yxqxks")).replaceAll("[^0-9]", "");
			endda = ((String) child.get("yxqxjs")).replaceAll("[^0-9]", "");
			// 请求参数 
			params = "{PI_APLNO:'" + dataMap.get("ypbm") + "',PI_BEGDA:'" + begda 
					+ "',PI_ENDDA:'"+ endda +"',PI_ZZZGZS:'" + child.get("zgzsmc") + "'}";
			// PB30资格证书信息(9010)信息修改
			rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI002715", params, 1, -1, null);
			
			// 解析返回结果，拼接页面的提示信息
			if(rfcjson == null){
				throw new WorkFlowException("第" + rowNum + "行：SAP接口没有返回消息！");
			}
			jArray = rfcjson.getRecord("PT_RETURN").getJSONArray("row");
			if(jArray.size()==0){
				throw new WorkFlowException("第" + rowNum + "行：SAP接口没有返回消息！");
			}else if(!"S".equals(jArray.getJSONObject(0).getString("TYPE"))){
				throw new WorkFlowException("第" + rowNum + "行：" + jArray.getJSONObject(0).getString("MESSAGE"));
			}
		}

		///////////////////////工作履历数据修改 ///////////////////////////////
		// 通过从表id获得从表数据
		childList = dyformdata.getFormDatasById("uf_ldx_rsgl_zwsqsgzjl");
		rowNum = 0;
		for(Map<String,Object> child:childList){
			rowNum ++;
			begda = ((String) child.get("rzsj")).replaceAll("[^0-9]", "");
			endda = ((String) child.get("lzsj")).replaceAll("[^0-9]", "");
			// 请求参数 
			params = "{PI_APLNO:'" + dataMap.get("ypbm") + "',PI_ZQYMC:'" + child.get("qymc") 
					+ "',PI_ZRZBMGW:'"+ child.get("szbm") +"',PI_BEGDA:'" + begda + "',PI_ENDDA:'" + endda
					+ "',PI_ZZHY:'" + child.get("szhy") + "',PI_ZQYXZ:'" + child.get("qyxzz") 
					+ "',PI_ZZJZGXM:'" + child.get("zjzgxm") + "',PI_ZZJZGLXFS:'" + child.get("zjzglxfs") + "',PI_ZLZYY:'" + child.get("lzyy") + "'}";
			// 工作履历数据修改
			rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI002710", params, 1, -1, null);
			
			// 解析返回结果，拼接页面的提示信息
			if(rfcjson == null){
				throw new WorkFlowException("第" + rowNum + "行：SAP接口没有返回消息！");
			}
			jArray = rfcjson.getRecord("PT_RETURN").getJSONArray("row");
			if(jArray.size()==0){
				throw new WorkFlowException("第" + rowNum + "行：SAP接口没有返回消息！");
			}else if(!"S".equals(jArray.getJSONObject(0).getString("TYPE"))){
				throw new WorkFlowException("第" + rowNum + "行：" + jArray.getJSONObject(0).getString("MESSAGE"));
			}
		}
				
		///////////////////////培训信息数据修改 ///////////////////////////////
		// 通过从表id获得从表数据
		childList = dyformdata.getFormDatasById("uf_ldx_rsgl_zwsqspxjl");
		rowNum = 0;
		for(Map<String,Object> child:childList){
			rowNum ++;
			begda = ((String) child.get("cjsj")).replaceAll("[^0-9]", "");
			// 请求参数 
			params = "{PI_APLNO:'" + dataMap.get("ypbm") + "',PI_BEGDA:'" + begda 
					+ "',PI_ENDDA:'99991231',PI_ZNAME:'" + child.get("pxkc") + "',PI_ZPOSITION:'" + child.get("pxdd") 
					+ "',PI_ZGRADE:'" + child.get("khcj")  + "'}";
			// 培训信息数据修改
			rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI002711", params, 1, -1, null);
			
			// 解析返回结果，拼接页面的提示信息
			if(rfcjson == null){
				throw new WorkFlowException("第" + rowNum + "行：SAP接口没有返回消息！");
			}
			jArray = rfcjson.getRecord("PT_RETURN").getJSONArray("row");
			if(jArray.size()==0){
				throw new WorkFlowException("第" + rowNum + "行：SAP接口没有返回消息！");
			}else if(!"S".equals(jArray.getJSONObject(0).getString("TYPE"))){
				throw new WorkFlowException("第" + rowNum + "行：" + jArray.getJSONObject(0).getString("MESSAGE"));
			}
		}
				
		///////////////////////先前月薪信息数据修改 ///////////////////////////////
		
		begda = ((String) dataMap.get("sqsj")).replaceAll("[^0-9]", "");
		// 请求参数 
		params = "{PI_APLNO:'" + dataMap.get("ypbm") + "',PI_BEGDA:'" + begda 
				+ "',PI_ENDDA:'99991231',PI_ZZFXRQ:'" + dataMap.get("fxrq") + "',PI_ZZJBGZ:'" + dataMap.get("jbgz") 
				+ "',PI_ZZJJ:'" + dataMap.get("jj")  + "',PI_ZZSYJT:'" + dataMap.get("syjt") 
				+ "',PI_ZZJTZF:'" + dataMap.get("jtzf") + "',PI_ZZQT:'" + dataMap.get("qt") + "',PI_ZZHJ:'" + dataMap.get("hj") + "'}";
		// 先前月薪信息数据修改 
		rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI002712", params, 1, -1, null);
		
		// 解析返回结果 
		if(rfcjson == null){
			throw new WorkFlowException("SAP接口没有返回消息！");
		}
		jArray = rfcjson.getRecord("PT_RETURN").getJSONArray("row");
		if(jArray.size()==0){
			throw new WorkFlowException("SAP接口没有返回消息！");
		}else if(!"S".equals(jArray.getJSONObject(0).getString("TYPE"))){
			throw new WorkFlowException(jArray.getJSONObject(0).getString("MESSAGE"));
		}
		
	}

	/**
	 * 流程节点创建时执行的方法
	 */
	@Override
	public void onCreated(Event event) throws WorkFlowException {
		
	}

}
