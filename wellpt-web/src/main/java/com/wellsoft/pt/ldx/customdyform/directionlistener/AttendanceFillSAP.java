package com.wellsoft.pt.ldx.customdyform.directionlistener;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellsoft.pt.basicdata.sap.service.SAPRfcService;
import com.wellsoft.pt.basicdata.sap.util.SAPRfcJson;
import com.wellsoft.pt.bpm.engine.context.event.Event;
import com.wellsoft.pt.bpm.engine.context.listener.DirectionListener;
import com.wellsoft.pt.bpm.engine.exception.WorkFlowException;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.DyFormData;

@Service("attendanceFillSAP")
@Transactional
public class AttendanceFillSAP implements DirectionListener{
	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	@Autowired 
	private SAPRfcService saprfcservice;
	
	
	@Override
	public String getName() {
		return "考勤补卡单（总部）回写SAP";
	}
	@Override
	public int getOrder() {
		return 11;
	}
	@Override
	public void transition(Event event) throws WorkFlowException {
		// 获得流程表单的信息 
		DyFormData dyformdata =dyFormApiFacade.getDyFormData(event.getFormUuid(), event.getDataUuid());
		// 获得主表表单数据
		Map<String,Object> dataMap = dyformdata.getFormDataByFormUuidAndDataUuid(event.getFormUuid(), event.getDataUuid());

		String pernr = (String) (dataMap.get("yggh")==null?"":dataMap.get("yggh"));
		// 通过从表id获得从表数据
		List<Map<String, Object>> childList = dyformdata.getFormDatasById("uf_rsgl_bqxx");
		String ldate = "";
		String ltime = "";
		int rowNum = 0;
		// 循环从表数据
		for(Map<String,Object> child:childList){
			rowNum++;
			ldate = child.get("bksj")==null?"":((String) child.get("bksj")).replaceAll("[^0-9]", "");
			ltime = child.get("ldksj")==null?"":((String) child.get("ldksj") + ":00").replaceAll("[^0-9]", "");
			// 请求参数
			StringBuffer param = new StringBuffer();
			param.append("{PI_PERNR:'");
			param.append(pernr);
			param.append("',PI_LDATE:'");
			param.append(ldate);
			param.append("',PI_LTIME:'");
			param.append(ltime);
			param.append("',PI_ABWGR:'");
			if(child.get("syz")!=null){
				param.append(child.get("syz"));
			}
			param.append("',PI_SATZA:'");
			if(child.get("xsbkz")!=null){
				param.append(child.get("xsbkz"));
			}
			param.append("'}");
			
			SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "ZHRI001401", param.toString(), 1, -1, null);
			
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
}
