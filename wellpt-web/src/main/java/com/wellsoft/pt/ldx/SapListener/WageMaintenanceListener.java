package com.wellsoft.pt.ldx.SapListener;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.basicdata.sap.service.SAPRfcService;
import com.wellsoft.pt.bpm.engine.context.event.Event;
import com.wellsoft.pt.bpm.engine.context.listener.TaskListener;
import com.wellsoft.pt.bpm.engine.exception.WorkFlowException;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.DyFormData;

@Service
@Transactional
public class WageMaintenanceListener implements TaskListener{
	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	@Autowired
	SAPRfcService saprfcservice;
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 25;
	}
	@Override
	public void onCreated(Event event) throws WorkFlowException {
		
	}
	@Override
	public void onCompleted(Event event) throws WorkFlowException {
		// TODO Auto-generated method stub
		
	}
}
