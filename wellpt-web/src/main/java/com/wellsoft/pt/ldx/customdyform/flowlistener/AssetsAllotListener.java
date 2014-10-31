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
import com.wellsoft.pt.ldx.service.lmsService.LMSService;



@Service
@Transactional
public class AssetsAllotListener implements TaskListener{

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	@Autowired
	private LMSService lmsService;
	
	/**
	 * 此流程监听类的名称 
	 */
	@Override
	public String getName() {
		return "资产调拨单回写LMS";
	}

	/**
	 * 此流程监听类的排序 
	 */
	@Override
	public int getOrder() {
		return 26;
	}

	/**
	 * 流程节点提交时执行的方法
	 */
	@Override
	public void onCompleted(Event event) throws WorkFlowException {
		// 获得流程表单的数据
		DyFormData dyformdata =dyFormApiFacade.getDyFormData(event.getFormUuid(), event.getDataUuid());
		// 获得主表表单数据
		Map<String,Object> dataMap = dyformdata.getFormDataByFormUuidAndDataUuid(event.getFormUuid(), event.getDataUuid());
		if("计量器具".equals(dataMap.get("gdzclbz"))){
			for(String key:dataMap.keySet()){
				if(dataMap.get(key)==null)
					dataMap.put(key,"");
			}
			// 通过从表id获得从表数据
			List<Map<String, Object>> childList = dyformdata.getFormDatasById("uf_leedarson_gdzc_gdzczmxx");
			
			String labfile_no = "";
			String labfile_assetsno = "";
			String labfile_cls = "";
			String labfile_address = "";
			String labfile_claim = "";
			String labfile_mainhave = "";
			String labfile_costno = "";
			String labfile_usr = "";
			String labfile_interusr = "";
			int rowNum = 0;
			// 循环从表数据将结果回写到lms系统
			for(Map<String ,Object> child:childList){
				rowNum++;
				// 管理编号 
				labfile_no = child.get("glbh")==null?"":child.get("glbh").toString();
				// 此管理编号已存在于lms中才回写 
				if(lmsService.findLabFileById(labfile_no).size()>0){
					// SAP资产编号 
					labfile_assetsno = child.get("drfsapzcbm")==null?"":child.get("drfsapzcbm").toString();
					// 管理区域类别 
					labfile_cls = child.get("drfglqylbzsz")==null?"":child.get("drfglqylbzsz").toString();
					// 安装地点 
					labfile_address = child.get("drfazdd")==null?"":child.get("drfazdd").toString();
					// 使用人姓名 
					labfile_claim = dataMap.get("drfsyrxm").toString();
					// 主体归属 
					labfile_mainhave = transformMainhave(dataMap.get("drfztgsdm").toString());
					// 成本中心代码 
					labfile_costno = dataMap.get("drfcbzxdm").toString();
					// 成本中心负责人 
					labfile_usr = dataMap.get("drfcbzxfzr").toString();
					// 接口人员 
					labfile_interusr = dataMap.get("drfjkryjll").toString();
					String remsg = lmsService.updateLabfileBTZT(labfile_no, labfile_assetsno, labfile_cls, labfile_address,
							labfile_claim, labfile_mainhave, labfile_costno, labfile_usr, labfile_interusr);
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

	private String transformMainhave(String mainhave){
		if("1000".equals(mainhave)){
			return "3";
		}else if("2000".equals(mainhave)){
			return "1";
		}else if("4000".equals(mainhave)){
			return "4";
		}else if("4600".equals(mainhave)){
			return "8";
		}else if("5000".equals(mainhave)){
			return "5";
		}else if("6000".equals(mainhave)){
			return "6";
		}else if("6500".equals(mainhave)){
			return "7";
		}else{
			return "";
		}
	}
	
}
