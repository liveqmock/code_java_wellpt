package com.wellsoft.pt.ldx.model.sampleTrack;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author shangguanzc 样品跟踪表查询参数对应的模型
 * @version 1.0
 */
@SuppressWarnings("serial")
public class SampleTrackSearckParam implements Serializable {
	/**
	 * 样品单号，从
	 */
	private String formSampleOrderId;
	/**
	 * 样品单号，到
	 */
	private String toSampleOrderId;	
	/**
	 * 行项目ID(非数据库字段，在客户反馈结果、品保检验结果中进行分组时用到，作为分组后的主键)
	 */
	private String lineItemId;
	/**
	 * 样品单状态
	 */
	private String sampleOrderStatus;
	/**
	 * 项目状态
	 */
	private String projectStatus;
	/**
	 * 单只样品序号，从
	 */
	private String formSampleId;
	/**
	 * 单只样品序号，到
	 */
	private String toSampleId;
	/**
	 * 申请人员
	 */
	private String applyUser;
	/**
	 * 申请部门
	 */
	private String applyDept;
	/**
	 * 申请日期，从
	 */
	private String fromApplyDate;
	/**
	 * 申请日期，到
	 */
	private String toApplyDate;
	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 领样Week
	 */
	private String week;
	/**
	 * 预计交期Week
	 */
	private String samplePredictDDateWeek;
	/**
	 * 完工日期，从
	 */
	private String formCompleteDate;
	/**
	 * 完工日期，到
	 */
	private String toCompleteDate;
	/**
	 * 生产方式
	 */
	private String productMode;
	/**
	 * 客户代码
	 */
	private String customerId;
	/**
	 * 距离交期天数，从
	 */
	private String formDay;

	/**
	 * 距离交期天数，到
	 */
	private String toDay;
	/**
	 * 客户反馈结果
	 */
	private String customerResult;
	/**
	 * 产品ID
	 */
	private String productId;
	/**
	 * 材料成本
	 */
	private String bomCost;
	/**
	 * 样品型号（单支样品序号前8位）
	 */
	private String sampleTypeNo;
	/**
	 * 生产方式列表
	 */
	private List productModeList;
	/**
	 * 生产方式列表
	 */
	private List sampleOrderStatusList;	
	/**
	 * 快递单号
	 */
	private String expressageNum;

	// 样品成本结算中的参数 start
	/**
	 * 分组条件，默认为部门名称
	 */
	private String groupCondition = "部门名称";
	/**
	 * 年份，默认为2014
	 */
	private String year = "2014";
	/**
	 * 费用承担方,默认我司
	 */
	private String sampleCharge1 = "我司";
	/**
	 * 生产方式,默认1自制
	 */
	private int productMode1 = 1;
	// 样品成本结算中的参数 end
	
	/**
	 * 品质异常状态(0正常1异常)
	 */
	private String qcExceptStatus;
	
	/**
	 * 交期异常状态(0正常1异常)
	 */
	private String prodExceptStatus;
	
	public String getFormSampleOrderId() {
		return formSampleOrderId;
	}

	public void setFormSampleOrderId(String formSampleOrderId) {
		this.formSampleOrderId = formSampleOrderId;
	}

	public String getToSampleOrderId() {
		return toSampleOrderId;
	}

	public void setToSampleOrderId(String toSampleOrderId) {
		this.toSampleOrderId = toSampleOrderId;
	}

	public String getLineItemId() {
		return lineItemId;
	}

	public void setLineItemId(String lineItemId) {
		this.lineItemId = lineItemId;
	}

	public String getSampleOrderStatus() {
		return sampleOrderStatus;
	}

	public void setSampleOrderStatus(String sampleOrderStatus) {
		this.sampleOrderStatus = sampleOrderStatus;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getFormSampleId() {
		return formSampleId;
	}

	public void setFormSampleId(String formSampleId) {
		this.formSampleId = formSampleId;
	}

	public String getToSampleId() {
		return toSampleId;
	}

	public void setToSampleId(String toSampleId) {
		this.toSampleId = toSampleId;
	}

	public String getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public String getApplyDept() {
		return applyDept;
	}

	public void setApplyDept(String applyDept) {
		this.applyDept = applyDept;
	}

	public String getFromApplyDate() {
		return fromApplyDate;
	}

	public void setFromApplyDate(String fromApplyDate) {
		this.fromApplyDate = fromApplyDate;
	}

	public String getToApplyDate() {
		return toApplyDate;
	}

	public void setToApplyDate(String toApplyDate) {
		this.toApplyDate = toApplyDate;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getSamplePredictDDateWeek() {
		return samplePredictDDateWeek;
	}

	public void setSamplePredictDDateWeek(String samplePredictDDateWeek) {
		this.samplePredictDDateWeek = samplePredictDDateWeek;
	}

	public String getFormCompleteDate() {
		return formCompleteDate;
	}

	public void setFormCompleteDate(String formCompleteDate) {
		this.formCompleteDate = formCompleteDate;
	}

	public String getToCompleteDate() {
		return toCompleteDate;
	}

	public void setToCompleteDate(String toCompleteDate) {
		this.toCompleteDate = toCompleteDate;
	}

	public String getProductMode() {
		return productMode;
	}

	public void setProductMode(String productMode) {
		this.productMode = productMode;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getFormDay() {
		return formDay;
	}

	public void setFormDay(String formDay) {
		this.formDay = formDay;
	}

	public String getToDay() {
		return toDay;
	}

	public void setToDay(String toDay) {
		this.toDay = toDay;
	}

	public String getCustomerResult() {
		return customerResult;
	}

	public void setCustomerResult(String customerResult) {
		this.customerResult = customerResult;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getBomCost() {
		return bomCost;
	}

	public void setBomCost(String bomCost) {
		this.bomCost = bomCost;
	}

	public String getSampleTypeNo() {
		return sampleTypeNo;
	}

	public void setSampleTypeNo(String sampleTypeNo) {
		this.sampleTypeNo = sampleTypeNo;
	}

	public List getProductModeList() {
		return productModeList;
	}

	public void setProductModeList(List productModeList) {
		this.productModeList = productModeList;
	}

	public List getSampleOrderStatusList() {
		return sampleOrderStatusList;
	}

	public void setSampleOrderStatusList(List sampleOrderStatusList) {
		this.sampleOrderStatusList = sampleOrderStatusList;
	}

	public String getExpressageNum() {
		return expressageNum;
	}

	public void setExpressageNum(String expressageNum) {
		this.expressageNum = expressageNum;
	}

	public String getGroupCondition() {
		return groupCondition;
	}

	public void setGroupCondition(String groupCondition) {
		this.groupCondition = groupCondition;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSampleCharge1() {
		return sampleCharge1;
	}

	public void setSampleCharge1(String sampleCharge1) {
		this.sampleCharge1 = sampleCharge1;
	}

	public int getProductMode1() {
		return productMode1;
	}

	public void setProductMode1(int productMode1) {
		this.productMode1 = productMode1;
	}

	public String getQcExceptStatus() {
		return qcExceptStatus;
	}

	public void setQcExceptStatus(String qcExceptStatus) {
		this.qcExceptStatus = qcExceptStatus;
	}

	public String getProdExceptStatus() {
		return prodExceptStatus;
	}

	public void setProdExceptStatus(String prodExceptStatus) {
		this.prodExceptStatus = prodExceptStatus;
	}

}
