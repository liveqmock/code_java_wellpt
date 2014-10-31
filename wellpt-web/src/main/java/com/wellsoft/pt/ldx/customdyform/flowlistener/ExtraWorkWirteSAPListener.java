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
public class ExtraWorkWirteSAPListener implements TaskListener{

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	@Autowired 
	private SAPRfcService saprfcservice;
	
	/**
	 * 此流程监听类的名字
	 */
	@Override
	public String getName() {
		return "加班申请单（总部）回写SAP";
	}

	/**
	 * 此流程监听类的排序
	 */
	@Override
	public int getOrder() {
		return 10;
	}

	/**
	 * 流程节点提交时执行的方法
	 */
	@Override
	public void onCompleted(Event event) throws WorkFlowException {
		// 获得流程表单的信息 
		DyFormData dyformdata = dyFormApiFacade.getDyFormData(event.getFormUuid(), event.getDataUuid());
		// 获得主表表单数据
		//Map<String,Object> dataMap = dyformdata.getFormDataByFormUuidAndDataUuid(event.getFormUuid(), event.getDataUuid());
		// 通过从表id获得从表数据
		String jhjbkssj = "";
		String jhjbjssj = "";
		List<Map<String, Object>> childList = dyformdata.getFormDatasById("uf_rsgl_jbxx");
		int rowNum = 0;
		for(Map<String,Object> child:childList){
			rowNum ++;
			StringBuffer params = new StringBuffer();
			
			jhjbkssj = ((String)child.get("jhjbkssj")).replaceAll("[^0-9]", "");
			jhjbjssj = ((String)child.get("jhjbjssj")).replaceAll("[^0-9]", "");
			params.append("{PI_PERNR:'");
			if(child.get("ygbh")!=null)
				params.append(child.get("ygbh"));// 员工编号
			params.append("',PI_BEGDA:'");
			params.append(jhjbkssj.substring(0, 8));// 计划加班开始日期
			params.append("',PI_ENDDA:'");
			params.append(jhjbjssj.substring(0, 8));// 计划加班结束日期
			params.append("',PI_BEGUZ:'");
			params.append(jhjbkssj.substring(8) + "00");// 计划加班开始时间
			params.append("',PI_ENDUZ:'");
			params.append(jhjbjssj.substring(8) + "00");// 计划加班结束时间
			params.append("',PI_KTART:'");
			if(child.get("txffz")!=null)
				params.append(child.get("txffz"));// 调休/付费
			params.append("'}");
			SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI0063", params.toString(), 1, -1, null);
			// 解析返回结果，拼接页面的提示信息
			if(rfcjson == null){
				throw new WorkFlowException("第" + rowNum + "行：SAP接口没有返回消息！");
			}
			JSONArray jArray = rfcjson.getRecord("PT_RETURN").getJSONArray("row");
			if(jArray.size()==0){
				throw new WorkFlowException("第" + rowNum + "行：SAP接口没有返回消息！");
			}else if(!"S".equals(jArray.getJSONObject(0).getString("TYPE"))){
				throw new WorkFlowException("第" + rowNum + "行：" + jArray.getJSONObject(0).getString("MESSAGE"));
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
