package com.wellsoft.pt.ldx.web;

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
import com.wellsoft.pt.ldx.service.lmsService.LMSService;

@Service
@Transactional
public class AppliancesRecipientsListener implements TaskListener{
	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	@Autowired
	private LMSService lmsService;

	@Override
	public String getName() {
		return "计量器具领用流程监听";
	}

	@Override
	public int getOrder() {
		return 5;
	}

	@Override
	public void onCompleted(Event event) throws WorkFlowException {
		// 获得流程表单的数据
		DyFormData dyformdata =dyFormApiFacade.getDyFormData(event.getFormUuid(), event.getDataUuid());
		if("T511".equals(event.getTaskId())){
			// 通过从表id获得从表数据
			List<Map<String, Object>> childList = dyformdata.getFormDatasById("uf_leedarson_gdzc_jlqjlyxx");
			// 循环从表数据将结果回写到lms系统
			for(Map<String ,Object> child:childList){
				String labfileNo = (String) child.get("glbh");
				String labfileClaim = (String)child.get("syr");
				String labfileAddress = (String)child.get("azdd");
				lmsService.updateMeterAccess(labfileNo, labfileAddress, labfileClaim);
			}
		}
	}

	@Override
	public void onCreated(Event arg0) throws WorkFlowException {
	}

}
