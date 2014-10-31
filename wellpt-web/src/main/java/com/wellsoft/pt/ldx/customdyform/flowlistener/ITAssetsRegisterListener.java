package com.wellsoft.pt.ldx.customdyform.flowlistener;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellsoft.pt.bpm.engine.context.event.Event;
import com.wellsoft.pt.bpm.engine.context.listener.TaskListener;
import com.wellsoft.pt.bpm.engine.exception.WorkFlowException;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.DyFormData;
import com.wellsoft.pt.file.facade.FileManagerApiFacade;


@Service("iTAssetsRegisterListener")
@Transactional
public class ITAssetsRegisterListener implements TaskListener{

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	@Autowired
	private FileManagerApiFacade fileManagerApiFacade;
	
	/**
	 * 此流程监听类的名字
	 */
	@Override
	public String getName() {
		return "IT固定资产验收报告[送固资文件库]";
	}

	/**
	 * 此流程监听类的排序
	 */
	@Override
	public int getOrder() {
		return 9;
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
		// 固定资产信息表单
		DyFormData assetsData = dyFormApiFacade.createDyformData(dyFormApiFacade.getFormUuidById("uf_assets"));
		assetsData.setFieldValue("centralNo",dataMap.get("gkglbm"));// 归档管理编号
		assetsData.setFieldValue("deviceName",dataMap.get("sbmc"));// 设备名称
		assetsData.setFieldValue("sapNo",dataMap.get("sapgzbm"));// SAP编号
		assetsData.setFieldValue("serialNum",dataMap.get("sbccxlh"));// 设备出厂序列号
		assetsData.setFieldValue("applicant",dataMap.get("sbsqrxm"));// 申请人
		assetsData.setFieldValue("deviceType",dataMap.get("sblx"));// 设备类别
		assetsData.setFieldValue("warrantyPeriod",dataMap.get("bxnx"));// 保修年限
		assetsData.setFieldValue("purchaseAmount",dataMap.get("cgje1"));// 采购金额
		assetsData.setFieldValue("supplier",dataMap.get("gys"));// 供应商
		assetsData.setFieldValue("startDate",dataMap.get("qyrq"));// 启用日期
		//assetsData.setFieldValue("retirementDate",dataMap.get("sbmc"));// 报废日期
		assetsData.setFieldValue("cpu",dataMap.get("cpu"));// CPU
		assetsData.setFieldValue("memory",dataMap.get("nc"));// 内存
		assetsData.setFieldValue("hardDisk",dataMap.get("yp"));// 硬盘
		assetsData.setFieldValue("graphics",dataMap.get("xk"));// 显卡
		assetsData.setFieldValue("specification",dataMap.get("ggxh"));// 规格型号
		//assetsData.setFieldValue("monitorSerialNum",dataMap.get("xk"));// 管理条形码编号
		assetsData.setFieldValue("wiredAddress",dataMap.get("yxmacdz"));// 有线MAC地址
		assetsData.setFieldValue("wirelessAddress",dataMap.get("wxmacdz"));// 无线MAC地址
		assetsData.setFieldValue("iniBody",dataMap.get("cssszt"));// 初始所属主体
		assetsData.setFieldValue("iniDept",dataMap.get("cssybmmc"));// 初始使用部门
		assetsData.setFieldValue("iniUser",dataMap.get("cssyrxm"));// 初始使用人
		assetsData.setFieldValue("iniStatus",dataMap.get("syztbq"));// 初始使用状态
		assetsData.setFieldValue("initAddr",dataMap.get("cssydqmc"));// 初始使用区域
		//assetsData.setFieldValue("changeDate",dataMap.get("xk"));// 调整日期
		assetsData.setFieldValue("curBody",dataMap.get("xsszt"));// 现所属主体
		assetsData.setFieldValue("curDept",dataMap.get("xsybm"));// 现使用部门
		assetsData.setFieldValue("curDeptName",dataMap.get("xsybmmc"));// 现使用部门名称
		assetsData.setFieldValue("curDeptNo",dataMap.get("xsybmid"));// 现使用部门ID
		assetsData.setFieldValue("curUser",dataMap.get("xsyr"));// 现使用人
		assetsData.setFieldValue("curUserName",dataMap.get("xsyrxm"));// 现使用人姓名
		assetsData.setFieldValue("curUserNo",dataMap.get("xsyrid"));// 现使用人ID
		assetsData.setFieldValue("curAddr",dataMap.get("xsyqymc"));// 现使用区域
		assetsData.setFieldValue("lastStatus",dataMap.get("zzsyztbq"));// 最终使用状态
		dyFormApiFacade.saveFormData(assetsData);
		// 归入固定资产信息的文件库
		if(!fileManagerApiFacade.createNewFile("d5654e74-2371-4108-b2ca-8ce30bf5cc99", assetsData)){
			throw new WorkFlowException("归入固定资产信息失败！");
		}
	}

	/**
	 * 流程节点创建时执行的方法
	 */
	@Override
	public void onCreated(Event event) throws WorkFlowException {
		
	}

}
