package com.wellsoft.pt.ldx.customdyform.flowlistener;

import java.text.SimpleDateFormat;
import java.util.Date;
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
public class OrderPassWirteSAPListener implements TaskListener{

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	@Autowired 
	private SAPRfcService saprfcservice;
	
	private SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
	private SimpleDateFormat timeformat = new SimpleDateFormat("HHmmss");
	/**
	 * 此流程监听类的名字
	 */
	@Override
	public String getName() {
		return "订单评审与签发申请表[签发时间]回写SAP";
	}

	/**
	 * 此流程监听类的排序
	 */
	@Override
	public int getOrder() {
		return 4;
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
		// 通过从表id获得从表数据
		List<Map<String, Object>> childList = dyformdata.getFormDatasById("uf_erp_ywgl_ddpsyqfsqxx");
		Date now = new Date();
		// 请求参数
		StringBuffer param = new StringBuffer();
		String ddpstjsj = ((String)dataMap.get("ddpstjsj")).replaceAll("[^0-9]", "");
		String ddpswcsj = ((String)dataMap.get("ddpswcsj")).replaceAll("[^0-9]", "");
		param.append("{PT_VBAK2:{row:[");
		for(Map<String,Object> child:childList){
			param.append("{POSNR:'");
			if(child.get("hxmh")!=null)
				param.append(child.get("hxmh"));// 行项目号
			param.append("',ABRVW:'");
			if(dataMap.get("ddlxbzz")!=null)
				param.append(dataMap.get("ddlxbzz"));// 订单类型（包装）
			param.append("',VBELN:'");
			if(dataMap.get("xsddh")!=null)
				param.append(dataMap.get("xsddh"));// 销售订单号
			param.append("',ZPSDAT1:'");param.append(ddpstjsj.substring(0, 8));// 订单评审提交日期
			param.append("',ZPSTIM1:'");param.append(ddpstjsj.substring(8));// 订单评审提交时间
			param.append("',ZPSDAT2:'");param.append(ddpswcsj.substring(0, 8));// 订单评审完成日期
			param.append("',ZPSTIM2:'");param.append(ddpswcsj.substring(8));// 订单评审完成时间
			param.append("',ZPSDAT3:'");param.append(dateformat.format(now));// 订单签发完成日期
			param.append("',ZPSTIM3:'");param.append(timeformat.format(now));// 订单签发完成时间
			param.append("',ETTYP:'"); 
			if(dataMap.get("ETTYP")!=null)
				param.append(dataMap.get("ETTYP"));// 计划行状态
			param.append("',ZPRODTYPE:'");
			if(dataMap.get("cpdlz")!=null)
				param.append(dataMap.get("cpdlz"));// 产品大类
			param.append("',GLTRS:'");
			if(child.get("zgrq") != null)
				param.append(((String)child.get("zgrq")).replaceAll("[^0-9]", ""));// 装柜日期
			param.append("',EDATU:'");
			if(child.get("wgrq") != null)
				param.append(((String)child.get("wgrq")).replaceAll("[^0-9]", ""));// 计划完工日期
			param.append("',ZBPDATE:'");
			if(child.get("cjq") != null)
				param.append(((String)child.get("cjq")).replaceAll("[^0-9]", ""));// 船交期
			param.append("',ZEGDATE:'");
			if(dataMap.get("yhrq") != null)
				param.append(((String)dataMap.get("yhrq")).replaceAll("[^0-9]", ""));// 验货日期 
			param.append("'},") ;
		}
		if(param.toString().endsWith(",")){
			param.deleteCharAt(param.length()-1);
		}
		param.append("]}}");
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "ZOA_SO_EVA_CHANGE", param.toString(), 1, -1, null);
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

	/**
	 * 流程节点创建时执行的方法
	 */
	@Override
	public void onCreated(Event event) throws WorkFlowException {
		
	}

}
