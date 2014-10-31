package com.wellsoft.ldx.hr.support;

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
public class SQLPersonArriveNoticeListener implements TaskListener{

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	
	@Override
	public String getName() {
		return "Leedarson_人事管理_人员到岗通知单v1.2 SQL回写";
	}

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public void onCompleted(Event event) throws WorkFlowException {
		// 获得流程表单的信息 
		DyFormData dyformdata =dyFormApiFacade.getDyFormData(event.getFormUuid(), event.getDataUuid());
		// 获得主表表单数据
		Map<String,Object> dataMap = dyformdata.getFormDataByFormUuidAndDataUuid(event.getFormUuid(), event.getDataUuid());
		
		String userid = (String)dataMap.get("");//userid  如何取值
		String rygh = (String)dataMap.get("rygh");//人员工号
		String systype = (String)dataMap.get("");//systype
		String account = (String)dataMap.get("");//account
		
		String formUuid = dyFormApiFacade.getFormUuidById("uf_doc_account");
		DyFormData dyformData = dyFormApiFacade.createDyformData(formUuid);
//		userid： LCP用户ID，如：U0010000001
//		code：工号
//		systype ：系统类型
//		account： 帐号
//		stats：状态
		dyformData.setFieldMapValue("userid", "U0010000001");
		dyformData.setFieldMapValue("code", rygh);
		dyformData.setFieldMapValue("systype", systype);
		dyformData.setFieldMapValue("account", account);
		dyformData.setFieldMapValue("stats", "1");
		String rtnValue = dyFormApiFacade.saveFormData(dyformData);
		System.out.println(rtnValue);
	}

	@Override
	public void onCreated(Event event) throws WorkFlowException {
		
	}

}
