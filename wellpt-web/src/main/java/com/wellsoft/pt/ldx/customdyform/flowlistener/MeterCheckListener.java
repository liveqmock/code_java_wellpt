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
import com.wellsoft.pt.ldx.model.lmsModel.LabFile;
import com.wellsoft.pt.ldx.service.lmsService.LMSService;


@Service
@Transactional
public class MeterCheckListener implements TaskListener{

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	@Autowired
	private LMSService lmsService;
	/**
	 * 此流程监听类的名字
	 */
	@Override
	public String getName() {
		return "计量器具送检单回写LMS";
	}

	/**
	 * 此流程监听类的排序
	 */
	@Override
	public int getOrder() {
		return 10;
	}

	/**
	 * 流程节点提交时执行的方法
	 */
	@Override
	public void onCompleted(Event event) throws WorkFlowException {
		// 获得流程表单的数据
		DyFormData dyformdata =dyFormApiFacade.getDyFormData(event.getFormUuid(), event.getDataUuid());
		// 获得主表表单数据
		//Map<String,Object> dataMap = dyformdata.getFormDataByFormUuidAndDataUuid(event.getFormUuid(), event.getDataUuid());
		// 通过从表id获得从表数据
		List<Map<String, Object>> childList = dyformdata.getFormDatasById("uf_leedarson_gdzc_jlqjsdtbg");
		int rowNum = 0;
		// 循环从表数据将结果回写到lms系统
		for(Map<String ,Object> child:childList){
			rowNum++;
			for(String key:child.keySet()){
				if(child.get(key)==null)
					child.put(key, "");
			}
			LabFile labfile = new LabFile();
			// 验收单号(入库登记)
			labfile.setLabfileAcceptno((String) child.get("qgdh"));
			String mainhave = transformMainhave((String) child.get("ztgs"));
			if(StringUtils.isNotBlank(mainhave)){
				// 主体归属
				labfile.setLabfileMainhave(Integer.parseInt(mainhave));
			}
			// 成本中心名称
			labfile.setCostName((String) child.get("cbzxmc"));
			// 成本中心代码
			labfile.setLabfileName((String) child.get("cbzxdm"));
			// 成本中心负责人
			labfile.setLabfileUsr((String) child.get("cbzxfzr"));
			// 接口人员
			labfile.setLabfileInterusr((String) child.get("jkry"));
			// 销售单位
			labfile.setLabfileSaleunit((String) child.get("xsdw"));
			// 销售人员 
			labfile.setLabfileSalenm((String) child.get("xsry"));
			// 销售电话
			labfile.setLabfileSaletel((String) child.get("xsdh"));
			if(StringUtils.isNotBlank(child.get("zcsxz").toString())){
				// 资产属性 
				labfile.setLabfileAssetscls(Integer.parseInt((String) child.get("zcsxz")));
			}
			// 固定资产号 
			labfile.setLabfileAssetsno((String) child.get("gdzch"));
			// 设备名称
			labfile.setLabfileName((String) child.get("mc"));
			// 设备规格
			labfile.setLabfileSpec((String) child.get("xhgg"));
			// 生产厂家
			labfile.setLabfileSup((String) child.get("sccj"));
			// 设备编号
			labfile.setLabfileNo((String) child.get("glbh"));
			// 当前使用部门
			labfile.setLabfileDepno((String) child.get("ccbh"));
			if(child.get("qyrq")!=null){
				// 启用日期
				labfile.setLabfileUsrdate((String) child.get("qyrq"));
			}
			// 管理区域类别 labfile.setLabfile((String) child.get("glqylb"));
			
			String remsg = lmsService.insertMeter(labfile);
			if(StringUtils.isNotBlank(remsg)){
				throw new WorkFlowException("第" + rowNum + "行：" + remsg);
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
