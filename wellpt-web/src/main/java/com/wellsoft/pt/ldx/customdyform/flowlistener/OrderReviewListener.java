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
public class OrderReviewListener implements TaskListener{

	@Autowired
	private DyFormApiFacade dyformApiFacade;
	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	/**
	 * 此流程监听类的名字
	 */
	@Override
	public String getName() {
		return "订单评审与签发申请表[流程状态监听]";
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
		// 获得流程表单的数据
		DyFormData dyformdata =dyFormApiFacade.getDyFormData(event.getFormUuid(), event.getDataUuid());
		// 获得流程主表表单数据
		Map<String,Object> dataMap = dyformdata.getFormDataByFormUuidAndDataUuid(event.getFormUuid(), event.getDataUuid());
		// 通过从表id获得从表数据
		List<Map<String, Object>> childList = dyformdata.getFormDatasById("uf_erp_ywgl_ddpsyqfsqxx");
		// 循环从表数据，向成品例行试验&RoHS测试&寿命跟踪计划单新增数据
		for(Map<String,Object> child:childList){
			// 目标表单（成品例行试验&RoHS测试&寿命跟踪计划单）的uuid
			String targetFromUuid = dyformApiFacade.getFormUuidById("uf_pbgl_cplxsyyrohscsysmgz");
			// 目标表单的数据
			DyFormData targetDataForm =dyformApiFacade.getDyFormData(
					targetFromUuid, (String)child.get("uuid"));
			Map<String,Object> targetData = targetDataForm.getFormDataByFormUuidAndDataUuid(
					targetFromUuid,  (String)child.get("uuid"));
			// 若目标表单尚无相应数据，则新增一条
			if(targetData==null){
				targetData = new HashMap<String,Object>();
				targetData.put("uuid",child.get("uuid"));
				targetData.put("xsddh",dataMap.get("xsddh"));
				targetData.put("hxm",child.get("hxmh"));
				
				targetData.put("kh",dataMap.get("khjc"));
				targetData.put("cpid",child.get("cpid"));
				targetData.put("ggms",dataMap.get("bz"));
				
				targetData.put("wgrq", child.get("wgrq"));
				
				targetData.put("scgc",child.get("gc"));

				targetData.put("lysllxsy",child.get("lysllxsy"));
				targetData.put("lyslrohs",child.get("lyslrohs"));
				targetData.put("lyslsmgz",child.get("lyslsmgz"));
				
				Map<String,List<Map<String,Object>>> map = targetDataForm.getFormDatas();
				List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
				list.add(targetData);
				map.put(targetFromUuid, list);
				dyformApiFacade.saveFormData(targetDataForm);
			}
			
			child.put("flow_status", "1");
		}
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
