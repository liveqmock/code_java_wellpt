package com.wellsoft.pt.ldx.model.productionPlan;

import java.io.Serializable;

public class PlanShare implements Serializable {
	private static final long serialVersionUID = 1L;
	//线别
	private String lineNo;
	//客户编号
	private String custNo;
	//生产订单
	private String productOrder;
	//销售订单
	private String saleOrder;
	//订单行号
	private String orderLineNo;
	//物料ID
	private String wlId;
	//物料描述
	private String wlDesc;
	//订单量
	private String orderAmt;
	//SD交期
	private String sdExpDate;
	//MPS上线日期
	private String mpsDate;
	//总装工时
	private String zzHours;
	//包装工时
	private String bzHours;
	//完成量
	private String completeAmt;
	//未完成量
	private String leftAmt;
	//物料状况
	private String wlStatus;
	//排产状态
	private String status;
	//备注-其他
	private String desc;
	//颜色
	private String color;
	//计划量
	private String planAmt;
	//排产计划第一天
	private String day1A;
	private String day1B;
	private String day1C;
	//排产计划第二天
	private String day2A;
	private String day2B;
	private String day2C;
	//排产计划第三天
	private String day3A;
	private String day3B;
	private String day3C;
	//排产计划第四天
	private String day4A;
	private String day4B;
	private String day4C;
	//排产计划第五天
	private String day5A;
	private String day5B;
	private String day5C;
	//排产计划第六天
	private String day6A;
	private String day6B;
	private String day6C;
	//排产计划第七天
	private String day7A;
	private String day7B;
	private String day7C;
	//排产计划第八天
	private String day8A;
	private String day8B;
	private String day8C;
	//排产计划第九天
	private String day9A;
	private String day9B;
	private String day9C;
	//排产计划第十天
	private String day0A;
	private String day0B;
	private String day0C;
	
	public String getLineNo() {
		return lineNo;
	}
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getProductOrder() {
		return productOrder;
	}
	public void setProductOrder(String productOrder) {
		this.productOrder = productOrder;
	}
	public String getSaleOrder() {
		return saleOrder;
	}
	public void setSaleOrder(String saleOrder) {
		this.saleOrder = saleOrder;
	}
	public String getOrderLineNo() {
		return orderLineNo;
	}
	public void setOrderLineNo(String orderLineNo) {
		this.orderLineNo = orderLineNo;
	}
	public String getWlId() {
		return wlId;
	}
	public void setWlId(String wlId) {
		this.wlId = wlId;
	}
	public String getWlDesc() {
		return wlDesc;
	}
	public void setWlDesc(String wlDesc) {
		this.wlDesc = wlDesc;
	}
	public String getOrderAmt() {
		return orderAmt;
	}
	public void setOrderAmt(String orderAmt) {
		this.orderAmt = orderAmt;
	}
	public String getSdExpDate() {
		return sdExpDate;
	}
	public void setSdExpDate(String sdExpDate) {
		this.sdExpDate = sdExpDate;
	}
	public String getZzHours() {
		return zzHours;
	}
	public void setZzHours(String zzHours) {
		this.zzHours = zzHours;
	}
	public String getBzHours() {
		return bzHours;
	}
	public void setBzHours(String bzHours) {
		this.bzHours = bzHours;
	}
	public String getCompleteAmt() {
		return completeAmt;
	}
	public void setCompleteAmt(String completeAmt) {
		this.completeAmt = completeAmt;
	}
	public String getLeftAmt() {
		return leftAmt;
	}
	public void setLeftAmt(String leftAmt) {
		this.leftAmt = leftAmt;
	}
	public String getWlStatus() {
		return wlStatus;
	}
	public void setWlStatus(String wlStatus) {
		this.wlStatus = wlStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getDay1A() {
		return day1A;
	}
	public void setDay1A(String day1a) {
		day1A = day1a;
	}
	public String getDay1B() {
		return day1B;
	}
	public void setDay1B(String day1b) {
		day1B = day1b;
	}
	public String getDay1C() {
		return day1C;
	}
	public void setDay1C(String day1c) {
		day1C = day1c;
	}
	public String getDay2A() {
		return day2A;
	}
	public void setDay2A(String day2a) {
		day2A = day2a;
	}
	public String getDay2B() {
		return day2B;
	}
	public void setDay2B(String day2b) {
		day2B = day2b;
	}
	public String getDay2C() {
		return day2C;
	}
	public void setDay2C(String day2c) {
		day2C = day2c;
	}
	public String getDay3A() {
		return day3A;
	}
	public void setDay3A(String day3a) {
		day3A = day3a;
	}
	public String getDay3B() {
		return day3B;
	}
	public void setDay3B(String day3b) {
		day3B = day3b;
	}
	public String getDay3C() {
		return day3C;
	}
	public void setDay3C(String day3c) {
		day3C = day3c;
	}
	public String getDay4A() {
		return day4A;
	}
	public void setDay4A(String day4a) {
		day4A = day4a;
	}
	public String getDay4B() {
		return day4B;
	}
	public void setDay4B(String day4b) {
		day4B = day4b;
	}
	public String getDay4C() {
		return day4C;
	}
	public void setDay4C(String day4c) {
		day4C = day4c;
	}
	public String getDay5A() {
		return day5A;
	}
	public void setDay5A(String day5a) {
		day5A = day5a;
	}
	public String getDay5B() {
		return day5B;
	}
	public void setDay5B(String day5b) {
		day5B = day5b;
	}
	public String getDay5C() {
		return day5C;
	}
	public void setDay5C(String day5c) {
		day5C = day5c;
	}
	public String getDay6A() {
		return day6A;
	}
	public void setDay6A(String day6a) {
		day6A = day6a;
	}
	public String getDay6B() {
		return day6B;
	}
	public void setDay6B(String day6b) {
		day6B = day6b;
	}
	public String getDay6C() {
		return day6C;
	}
	public void setDay6C(String day6c) {
		day6C = day6c;
	}
	public String getDay7A() {
		return day7A;
	}
	public void setDay7A(String day7a) {
		day7A = day7a;
	}
	public String getDay7B() {
		return day7B;
	}
	public void setDay7B(String day7b) {
		day7B = day7b;
	}
	public String getDay7C() {
		return day7C;
	}
	public void setDay7C(String day7c) {
		day7C = day7c;
	}
	public String getDay8A() {
		return day8A;
	}
	public void setDay8A(String day8a) {
		day8A = day8a;
	}
	public String getDay8B() {
		return day8B;
	}
	public void setDay8B(String day8b) {
		day8B = day8b;
	}
	public String getDay8C() {
		return day8C;
	}
	public void setDay8C(String day8c) {
		day8C = day8c;
	}
	public String getDay9A() {
		return day9A;
	}
	public void setDay9A(String day9a) {
		day9A = day9a;
	}
	public String getDay9B() {
		return day9B;
	}
	public void setDay9B(String day9b) {
		day9B = day9b;
	}
	public String getDay9C() {
		return day9C;
	}
	public void setDay9C(String day9c) {
		day9C = day9c;
	}
	public String getDay0A() {
		return day0A;
	}
	public void setDay0A(String day0a) {
		day0A = day0a;
	}
	public String getDay0B() {
		return day0B;
	}
	public void setDay0B(String day0b) {
		day0B = day0b;
	}
	public String getDay0C() {
		return day0C;
	}
	public void setDay0C(String day0c) {
		day0C = day0c;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getMpsDate() {
		return mpsDate;
	}
	public void setMpsDate(String mpsDate) {
		this.mpsDate = mpsDate;
	}
	public String getPlanAmt() {
		return planAmt;
	}
	public void setPlanAmt(String planAmt) {
		this.planAmt = planAmt;
	}
	
}
