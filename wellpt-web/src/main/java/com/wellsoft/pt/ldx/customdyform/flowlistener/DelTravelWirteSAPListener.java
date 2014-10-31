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
public class DelTravelWirteSAPListener implements TaskListener{

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	@Autowired 
	private SAPRfcService saprfcservice;
	
	/**
	 * 此流程监听类的名字
	 */
	@Override
	public String getName() {
		return "出差申请单（总部）[销差]回写SAP";
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
		DyFormData dyformdata = dyFormApiFacade.getDyFormData(event.getFormUuid(), event.getDataUuid());
		// 获得主表表单数据
		//Map<String,Object> dataMap = dyformdata.getFormDataByFormUuidAndDataUuid(event.getFormUuid(), event.getDataUuid());
		// 通过从表id获得从表数据
		List<Map<String, Object>> childList = dyformdata.getFormDatasById("uf_rsgl_ccxx");
		int rowNum = 0;
		for(Map<String,Object> child:childList){
			rowNum++;
			StringBuffer params = new StringBuffer();
			
			params.append("{PI_PERNR:'");
			if(child.get("ygbh") != null)
				params.append(child.get("ygbh"));// 员工编号
			params.append("',PI_BEGDAO:'");
			params.append(((String)child.get("nkssj")).replaceAll("[^0-9]", ""));// 拟开始日期
			params.append("',PI_ENDDAO:'");
			params.append(((String)child.get("njssj")).replaceAll("[^0-9]", ""));// 拟结束日期
			params.append("',PI_BEGDA:'");
			params.append(((String)child.get("sjkssj")).replaceAll("[^0-9]", ""));// 实际开始日期
			params.append("',PI_ENDDA:'");
			params.append(((String)child.get("sjjssj")).replaceAll("[^0-9]", ""));// 实际结束日期
			params.append("',PI_AWART:'0003'}");
			SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI001502", params.toString(), 1, -1, null);
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
