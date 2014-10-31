package com.wellsoft.pt.ldx.customdyform.flowlistener;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellsoft.pt.bpm.engine.context.event.Event;
import com.wellsoft.pt.bpm.engine.context.listener.TaskListener;
import com.wellsoft.pt.bpm.engine.exception.WorkFlowException;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.DyFormData;
import com.wellsoft.pt.ldx.service.lmsService.impl.LMSServiceImpl;


@Service
@Transactional
public class AssetsScrapListener implements TaskListener{

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	@Autowired
	private LMSServiceImpl lmsService;
	
	/**
	 * 此流程监听类的名称 
	 */
	@Override
	public String getName() {
		return "固资报废申请回写LMS";
	}

	/**
	 * 此流程监听类的排序 
	 */
	@Override
	public int getOrder() {
		return 7;
	}

	/**
	 * 流程节点提交时执行的方法
	 */
	@Override
	public void onCompleted(Event event) throws WorkFlowException {
		// 获得流程表单的数
		DyFormData dyformdata =dyFormApiFacade.getDyFormData(event.getFormUuid(), event.getDataUuid());
		// 获得主表表单数据
		Map<String,Object> dataMap = dyformdata.getFormDataByFormUuidAndDataUuid(event.getFormUuid(), event.getDataUuid());
		if("计量器具".equals(dataMap.get("zclbz"))){
			// 通过从表id获得从表数据
			List<Map<String, Object>> childList = dyformdata.getFormDatasById("uf_leedarson_gdzc_gdzcbfxx");
			// 循环从表数据将结果回写到lms系统
			int rowNum = 0;
			for(Map<String ,Object> child:childList){
				rowNum++;
				String labfile_no = child.get("glbh")==null?"":child.get("glbh").toString();
				// 此管理编号已存在于lms中才回写 
				if(lmsService.findLabFileById(labfile_no).size()>0){
					String remsg = lmsService.updateLabfile(labfile_no , "4", (String)dataMap.get("lsh_gdzcbfsq"));
					if(StringUtils.isNotBlank(remsg)){
						throw new WorkFlowException("第" + rowNum + "行：" + remsg);
					}
				}
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
