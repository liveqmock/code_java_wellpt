package com.wellsoft.pt.ldx.customdyform.flowlistener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellsoft.pt.bpm.engine.context.event.Event;
import com.wellsoft.pt.bpm.engine.context.listener.TaskListener;
import com.wellsoft.pt.bpm.engine.exception.WorkFlowException;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.DyFormData;


@Service
@Transactional
public class WirteCheckListener implements TaskListener{

	@Autowired
	private DyFormApiFacade dyformApiFacade;
	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	/**
	 * 此流程监听类的名字
	 */
	@Override
	public String getName() {
		return "请购验收单（生产零星请购）[送计量器具送检单]";
	}

	/**
	 * 此流程监听类的排序
	 */
	@Override
	public int getOrder() {
		return 17;
	}

	/**
	 * 流程节点提交时执行的方法
	 */
	@Override
	public void onCompleted(Event event) throws WorkFlowException {
		// 获得流程表单的数据
		DyFormData dyformdata =dyFormApiFacade.getDyFormData(event.getFormUuid(), event.getDataUuid());
		// 获得流程主表表单数据
		Map<String,Object> dataMap = dyformdata.getFormDataByFormUuidAndDataUuid(event.getFormUuid(), event.getDataUuid());
		// 通过从表id获得从表数据
		List<Map<String, Object>> childList = dyformdata.getFormDatasById("uf_leedarson_ledqglxcgxx");
		// 循环从表数据，向计量器具送检单新增数据
		for(Map<String,Object> child:childList){
			// 目标表单（计量器具送检单）的uuid
			String targetFromUuid = dyformApiFacade.getFormUuidById("uf_leedarson_gdzc_jlqjsdtbg");
			// 目标表单的数据
			DyFormData targetDataForm =dyformApiFacade.getDyFormData(
					targetFromUuid, (String)child.get("uuid"));
			Map<String,Object> targetData = targetDataForm.getFormDataByFormUuidAndDataUuid(
					targetFromUuid,  (String)child.get("uuid"));
			// 若目标表单尚无相应数据，则新增一条
			if(targetData==null){
				targetData = new HashMap<String,Object>();
				targetData.put("uuid",child.get("uuid"));
				targetData.put("sqr",dataMap.get("sqr"));
				targetData.put("ztgs",dataMap.get("sszt"));
				targetData.put("cbzxmc",dataMap.get("cbzxmc"));
				targetData.put("cbzxdm",dataMap.get("cbzxdm"));
				targetData.put("cbzxfzr",dataMap.get("cbzxfzr"));
				targetData.put("jkry",dataMap.get("lxr"));
				
				targetData.put("mc",child.get("pm"));
				targetData.put("xhgg",child.get("ggpp"));
				
				Map<String,List<Map<String,Object>>> map = targetDataForm.getFormDatas();
				List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
				list.add(targetData);
				map.put(targetFromUuid, list);
				dyformApiFacade.saveFormData(targetDataForm);
			}
			// 更新从表 流程状态
			dataMap.put("flow_status", "1");
		}
		// 更新主表单 流程状态
		dataMap.put("flow_status", "1");
		
		dyFormApiFacade.saveFormData(dyformdata);

	}

	/**
	 * 流程节点创建时执行的方法
	 */
	@Override
	public void onCreated(Event event) throws WorkFlowException {
		
	}

}
