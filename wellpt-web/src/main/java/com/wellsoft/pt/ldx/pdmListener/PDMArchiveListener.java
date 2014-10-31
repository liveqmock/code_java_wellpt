package com.wellsoft.pt.ldx.pdmListener;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.log.SysoCounter;
import com.wellsoft.pt.bpm.engine.context.event.Event;
import com.wellsoft.pt.bpm.engine.context.listener.TaskListener;
import com.wellsoft.pt.bpm.engine.exception.WorkFlowException;
import com.wellsoft.pt.dyform.entity.DyFormDefinition;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.DyFormData;
import com.wellsoft.pt.ldx.service.pdm.DefaultValueService;

@Service
@Transactional
public class PDMArchiveListener implements TaskListener{
	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	@Autowired
	private DefaultValueService dvService;
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "PDM归档";
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void onCompleted(Event event) throws WorkFlowException {
		// 获得流程表单的信息 
		DyFormData dyformdata =dyFormApiFacade.getDyFormData(event.getFormUuid(), event.getDataUuid());
		// 获得主表表单数据
		Map<String,Object> dataMap = dyformdata.getFormDataByFormUuidAndDataUuid(event.getFormUuid(), event.getDataUuid());
		
		DyFormDefinition dyFormDefinition= dyFormApiFacade.getFormDefinition(event.getFormUuid());
		
		System.out.println("表名："+dyFormDefinition.getName());
		
		dvService.update(dyFormDefinition.getName(), "zt='12'", "bh='"+dataMap.get("bh")+"' and zt='11'");
	}

	@Override
	public void onCreated(Event arg0) throws WorkFlowException {
		// TODO Auto-generated method stub
		
	}

}
