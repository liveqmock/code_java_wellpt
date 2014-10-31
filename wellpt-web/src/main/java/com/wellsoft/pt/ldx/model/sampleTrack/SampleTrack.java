package com.wellsoft.pt.ldx.model.sampleTrack;

import java.io.Serializable;
import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.wellsoft.pt.core.entity.IdEntity;

@Entity
@Table(name = "USERFORM_SAMPLE_TRACK")
@DynamicUpdate
@DynamicInsert
public class SampleTrack  implements Serializable{
	private static final long serialVersionUID = 1L;
	public SampleTrack() {
	}
	/**
	 * 样品跟踪Id
	 */
	@Id
	@Column(name="uuid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	/**
	 * 单支样品序号
	 */
	@Column(name="sampleid")
	private String sampleid;
	/**
	 * 行项目ID(非数据库字段，在客户反馈结果、品保检验结果中进行分组时用到，作为分组后的主键)
	 */
	@Column(name="lineitemid")
	private String lineitemid;
	/**
	 * 样品单状态(1待制作2待邮寄3已完成4已取消)
	 */
	@Column(name="sampleorderstatus")
	private Integer sampleorderstatus;
	/**
	 * 项目状态(0:待EP定型、1:已EP定型)
	 */
	@Column(name="projectstatus")
	private Integer projectstatus;
	/**
	 * 样品单号
	 */
	@Column(name="sampleorderid")
	private String sampleorderid;
	/**
	 * 产品ID
	 */
	@Column(name="productid")
	private String productid;
	/**
	 * 产品名称
	 */
	@Column(name="productname")
	private String productname;
	/**
	 * 样品数量
	 */
	@Column(name="samplenum")
	private String samplenum;
	
	/**
	 * 是否报关(0否,1是)
	 */
	@Column(name="iscustoms")
	private Integer iscustoms;
	/**
	 * 是否开票(0否,1是)
	 */
	@Column(name="isinvoice")
	private Integer isinvoice;
	/**
	 * 样品费承担方
	 */
	@Column(name="samplecharge")
	private String samplecharge;
	/**
	 * 运费承担方
	 */
	@Column(name="freight")
	private String freight;
	/**
	 * 模具费承担方
	 */
	@Column(name="mouldcharge")
	private String mouldcharge;
	/**
	 * 测试类型
	 */
	@Column(name="testmode")
	private String testmode;
	/**
	 * 申请部门
	 */
	@Column(name="applydept")
	private String applydept;
	/**
	 * 付款方式
	 */
	@Column(name="paymode")
	private String paymode;
	/**
	 * 申请人员
	 */
	@Column(name="applyuser")
	private String applyuser;
	/**
	 * 跟单人员
	 */
	@Column(name="documentaryuser")
	private String documentaryuser;
	/**
	 * 送样目的
	 */
	@Column(name="sampleaim")
	private String sampleaim;
	/**
	 * 申请日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="applydate")
	private Date applydate;
	/**
	 * RSM审批日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="rsmauditingdate")
	private Date rsmauditingdate;
	/**
	 * PM审批日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="pmauditingdate")
	private Date pmauditingdate;
	/**
	 * 客户服务部样品管理组审批日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="customergroupauditingdate")
	private Date customergroupauditingdate;
	/**
	 * 技术中心/外购组回复日期
	 */
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name="techoutgroupreplydate")
	private String techoutgroupreplydate;
	/**
	 * AE确认日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="aesuredate")
	private Date aesuredate;
	/**
	 * 样品预计交期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="samplepredictddate")
	private Date samplepredictddate;
	/**
	 * 样品交货绝期
	 */
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sampledeliveryperiod")
	private String sampledeliveryperiod;
	/**
	 * 生产方式（1自制、2外购、3外购&自制）
	 */
	@Column(name="productmode")
	private Integer productmode;
	/**
	 * 完工日期
	 */
	@Column(name="completedate")
	private String completedate;
	/**
	 * 领样Week
	 */
	@Column(name="week")
	private String week;
	/**
	 * 预计送样目的地
	 */
	@Column(name="presampledestination")
	private String presampledestination;
	/**
	 * 客户代码
	 */
	@Column(name="customerid")
	private String customerid;
	/**
	 * 库存数量(单行显示0或1,计算逻辑：完成日期-送样目的地；)
	 */
	@Column(name="stocknubmer")
	private Integer stocknubmer;
	/**
	 * 行项目备注说明
	 */
	@Column(name="memo")
	private String memo;
	/**
	 * 材料成本(RMB)数据来源：CRM
	 */
	@Column(name="bomcost")
	private  String bomcost;
	/**
	 * 人工成本(RMB)数据来源：CRM
	 */
	@Column(name="labourcost")
	private String labourcost;
	/**
	 * 包装成本(RMB)
	 */
	@Column(name="packcost")
	private String packcost;
	/**
	 * 样品成本(RMB)
	 */
	@Column(name="samplecost")
	private String samplecost;
	/**
	 * PI金额
	 */
	@Column(name="pimoney")
	private String pimoney;
	/**
	 * PI金额单位
	 */
	@Column(name="pimoneyunit")
	private String pimoneyunit;
	/**
	 * 距离交期天数(计算逻辑： 样品预计交期-当前查询日期（也可能是负数）)
	 * 不参与数据库的操作
	 */
	@Transient
	private Integer day;//不参与数据库的操作
	/**
	 * 抬头备注信息
	 */
	@Column(name="topmemo")
	private String topmemo;
	/**
	 * 包装方式
	 */
	@Column(name="packmode")
	private String packmode;
	/**
	 * 单价(RMB)
	 */
	@Column(name="unitprice")
	private String unitprice;
	/**
	 * 技术要求
	 */
	@Column(name="techrequire")
	private String techrequire;
	/**
	 * 测试标准
	 */
	@Column(name="teststandard")
	private String teststandard;
	/**
	 * 标签要求
	 */
	@Column(name="labelrequire")
	private String labelrequire;
	/**
	 * 样品预计交期Week
	 */
	@Column(name="samplepredictddateweek")
	private String samplepredictddateweek;
	/**
	 * 目标交期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="aimdate")
	private Date aimdate;
	/**
	 * 签发Week（相关人员确认日期来计算）
	 */
	@Column(name="signweek")
	private String signweek;
	
	/**
	 * AE确认交期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="aesuredelivery")
	private Date aesuredelivery;
	/**
	 * 创建日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date create_date;
	/**
	 * 更新日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_date")
	private Date update_date;
	/**
	 * 更新者
	 */
	@Column(name="update_user")
	private String update_user;
	/**
	 * 批次号
	 */
	@Column(name="batchnum")
	private String batchnum;
	
	//客户反馈结果
	/**
	 * 实际送样目的地
	 */
	@Column(name="sampledestination")
	private String sampledestination;
	/**
	 * 送样时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sampledate")
	private Date sampledate;
	/**
	 * 快递单号
	 */
	@Column(name="expressagenum")
	private String expressagenum;
	/**
	 * 客户反馈结果
	 */
	@Column(name="customerresult")
	private String customerresult;
	//品保检验结果
	/**
	 * 品保检验描述
	 */
	@Column(name="qccheckmemo")
	private String qccheckmemo;
	/**
	 * 测试委托单
	 */
	@Column(name="testappnum")
	private String testappnum;
	/**
	 * 品保检验开始时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="qccheckdate")
	private Date qccheckdate;
	/**
	 * 品保检验完成时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="qcfinishdate")
	private Date qcfinishdate;
	/**
	 * 品保判定结果（1OK;2NG;3放行;4特采出货）
	 */
	@Column(name="qccheckresult")
	private Integer qccheckresult;
	/**
	 * 品保二次判定结果（1OK;2NG;3放行;4特采出货）
	 */
	@Column(name="qcsecondresult")
	private Integer qcsecondresult;
	/**
	 * 检验人员
	 */
	@Column(name="qcname")
	private String qcname;
	/**
	 * 品保检验项目
	 */
	@Column(name="qccheckitem")
	private String qccheckitem;
	/**
	 * 品质异常描述
	 */
	@Column(name="qcexceptcause")
	private String qcexceptcause;
	/**
	 * 品质检验时间
	 */
	@Column(name="qccosttime")
	private String qccosttime;
	//样品课反馈结果
	/**
	 * 排程-开始
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="planstart")
	private Date planstart;
	/**
	 * 排程-结束
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="planend")
	private Date planend;
	/**
	 * 生产时间
	 */
	@Column(name="prodcosttime")
	private String prodcosttime;
	/**
	 * 生产状态
	 */
	@Column(name="prodstatus")
	private Integer prodstatus;
	/**
	 * 生产状态备注
	 */
	@Column(name="prodstatusmemo")
	private String prodstatusmemo;
	/**
	 * 领样说明
	 */
	@Column(name="ledsamplememo")
	private String ledsamplememo;
	
	/**
	 * 客户潜力级别
	 */
	@Column(name="customerpotentialrank")
	private String customerpotentialrank;
	
	/**
	 * 品质异常责任部门反馈
	 */
	@Column(name="qcexceptreply")
	private String qcexceptreply;
	
	/**
	 * 交期异常责任部门反馈
	 */
	@Column(name="prodexceptreply")
	private String prodexceptreply;
	
	/**
	 * 品质异常状态(0正常1异常)
	 */
	@Column(name="qcexceptstatus")
	private Integer qcexceptstatus;
	
	/**
	 * 交期异常状态(0正常1异常)
	 */
	@Column(name="prodexceptstatus")
	private Integer prodexceptstatus;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSampleid() {
		return sampleid;
	}

	public void setSampleid(String sampleid) {
		this.sampleid = sampleid;
	}

	public String getLineitemid() {
		return lineitemid;
	}

	public void setLineitemid(String lineitemid) {
		this.lineitemid = lineitemid;
	}

	public Integer getSampleorderstatus() {
		return sampleorderstatus;
	}

	public void setSampleorderstatus(Integer sampleorderstatus) {
		this.sampleorderstatus = sampleorderstatus;
	}

	public Integer getProjectstatus() {
		return projectstatus;
	}

	public void setProjectstatus(Integer projectstatus) {
		this.projectstatus = projectstatus;
	}

	public String getSampleorderid() {
		return sampleorderid;
	}

	public void setSampleorderid(String sampleorderid) {
		this.sampleorderid = sampleorderid;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getSamplenum() {
		return samplenum;
	}

	public void setSamplenum(String samplenum) {
		this.samplenum = samplenum;
	}

	public Integer getIscustoms() {
		return iscustoms;
	}

	public void setIscustoms(Integer iscustoms) {
		this.iscustoms = iscustoms;
	}

	public Integer getIsinvoice() {
		return isinvoice;
	}

	public void setIsinvoice(Integer isinvoice) {
		this.isinvoice = isinvoice;
	}

	public String getSamplecharge() {
		return samplecharge;
	}

	public void setSamplecharge(String samplecharge) {
		this.samplecharge = samplecharge;
	}

	public String getFreight() {
		return freight;
	}

	public void setFreight(String freight) {
		this.freight = freight;
	}

	public String getMouldcharge() {
		return mouldcharge;
	}

	public void setMouldcharge(String mouldcharge) {
		this.mouldcharge = mouldcharge;
	}

	public String getTestmode() {
		return testmode;
	}

	public void setTestmode(String testmode) {
		this.testmode = testmode;
	}

	public String getApplydept() {
		return applydept;
	}

	public void setApplydept(String applydept) {
		this.applydept = applydept;
	}

	public String getPaymode() {
		return paymode;
	}

	public void setPaymode(String paymode) {
		this.paymode = paymode;
	}

	public String getApplyuser() {
		return applyuser;
	}

	public void setApplyuser(String applyuser) {
		this.applyuser = applyuser;
	}

	public String getDocumentaryuser() {
		return documentaryuser;
	}

	public void setDocumentaryuser(String documentaryuser) {
		this.documentaryuser = documentaryuser;
	}

	public String getSampleaim() {
		return sampleaim;
	}

	public void setSampleaim(String sampleaim) {
		this.sampleaim = sampleaim;
	}

	public Date getApplydate() {
		return applydate;
	}

	public void setApplydate(Date applydate) {
		this.applydate = applydate;
	}

	public Date getRsmauditingdate() {
		return rsmauditingdate;
	}

	public void setRsmauditingdate(Date rsmauditingdate) {
		this.rsmauditingdate = rsmauditingdate;
	}

	public Date getPmauditingdate() {
		return pmauditingdate;
	}

	public void setPmauditingdate(Date pmauditingdate) {
		this.pmauditingdate = pmauditingdate;
	}

	public Date getCustomergroupauditingdate() {
		return customergroupauditingdate;
	}

	public void setCustomergroupauditingdate(Date customergroupauditingdate) {
		this.customergroupauditingdate = customergroupauditingdate;
	}

	public String getTechoutgroupreplydate() {
		return techoutgroupreplydate;
	}

	public void setTechoutgroupreplydate(String techoutgroupreplydate) {
		this.techoutgroupreplydate = techoutgroupreplydate;
	}

	public Date getAesuredate() {
		return aesuredate;
	}

	public void setAesuredate(Date aesuredate) {
		this.aesuredate = aesuredate;
	}

	public Date getSamplepredictddate() {
		return samplepredictddate;
	}

	public void setSamplepredictddate(Date samplepredictddate) {
		this.samplepredictddate = samplepredictddate;
	}

	public String getSampledeliveryperiod() {
		return sampledeliveryperiod;
	}

	public void setSampledeliveryperiod(String sampledeliveryperiod) {
		this.sampledeliveryperiod = sampledeliveryperiod;
	}

	public Integer getProductmode() {
		return productmode;
	}

	public void setProductmode(Integer productmode) {
		this.productmode = productmode;
	}

	public String getCompletedate() {
		return completedate;
	}

	public void setCompletedate(String completedate) {
		this.completedate = completedate;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getPresampledestination() {
		return presampledestination;
	}

	public void setPresampledestination(String presampledestination) {
		this.presampledestination = presampledestination;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public Integer getStocknubmer() {
		return stocknubmer;
	}

	public void setStocknubmer(Integer stocknubmer) {
		this.stocknubmer = stocknubmer;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getBomcost() {
		return bomcost;
	}

	public void setBomcost(String bomcost) {
		this.bomcost = bomcost;
	}

	public String getLabourcost() {
		return labourcost;
	}

	public void setLabourcost(String labourcost) {
		this.labourcost = labourcost;
	}

	public String getPackcost() {
		return packcost;
	}

	public void setPackcost(String packcost) {
		this.packcost = packcost;
	}

	public String getSamplecost() {
		return samplecost;
	}

	public void setSamplecost(String samplecost) {
		this.samplecost = samplecost;
	}

	public String getPimoney() {
		return pimoney;
	}

	public void setPimoney(String pimoney) {
		this.pimoney = pimoney;
	}

	public String getPimoneyunit() {
		return pimoneyunit;
	}

	public void setPimoneyunit(String pimoneyunit) {
		this.pimoneyunit = pimoneyunit;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public String getTopmemo() {
		return topmemo;
	}

	public void setTopmemo(String topmemo) {
		this.topmemo = topmemo;
	}

	public String getPackmode() {
		return packmode;
	}

	public void setPackmode(String packmode) {
		this.packmode = packmode;
	}

	public String getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(String unitprice) {
		this.unitprice = unitprice;
	}

	public String getTechrequire() {
		return techrequire;
	}

	public void setTechrequire(String techrequire) {
		this.techrequire = techrequire;
	}

	public String getTeststandard() {
		return teststandard;
	}

	public void setTeststandard(String teststandard) {
		this.teststandard = teststandard;
	}

	public String getLabelrequire() {
		return labelrequire;
	}

	public void setLabelrequire(String labelrequire) {
		this.labelrequire = labelrequire;
	}

	public String getSamplepredictddateweek() {
		return samplepredictddateweek;
	}

	public void setSamplepredictddateweek(String samplepredictddateweek) {
		this.samplepredictddateweek = samplepredictddateweek;
	}

	public Date getAimdate() {
		return aimdate;
	}

	public void setAimdate(Date aimdate) {
		this.aimdate = aimdate;
	}

	public String getSignweek() {
		return signweek;
	}

	public void setSignweek(String signweek) {
		this.signweek = signweek;
	}

	public Date getAesuredelivery() {
		return aesuredelivery;
	}

	public void setAesuredelivery(Date aesuredelivery) {
		this.aesuredelivery = aesuredelivery;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

	public String getBatchnum() {
		return batchnum;
	}

	public void setBatchnum(String batchnum) {
		this.batchnum = batchnum;
	}

	public String getSampledestination() {
		return sampledestination;
	}

	public void setSampledestination(String sampledestination) {
		this.sampledestination = sampledestination;
	}

	public Date getSampledate() {
		return sampledate;
	}

	public void setSampledate(Date sampledate) {
		this.sampledate = sampledate;
	}

	public String getExpressagenum() {
		return expressagenum;
	}

	public void setExpressagenum(String expressagenum) {
		this.expressagenum = expressagenum;
	}

	public String getCustomerresult() {
		return customerresult;
	}

	public void setCustomerresult(String customerresult) {
		this.customerresult = customerresult;
	}

	public String getQccheckmemo() {
		return qccheckmemo;
	}

	public void setQccheckmemo(String qccheckmemo) {
		this.qccheckmemo = qccheckmemo;
	}

	public String getTestappnum() {
		return testappnum;
	}

	public void setTestappnum(String testappnum) {
		this.testappnum = testappnum;
	}

	public Date getQccheckdate() {
		return qccheckdate;
	}

	public void setQccheckdate(Date qccheckdate) {
		this.qccheckdate = qccheckdate;
	}

	public Date getQcfinishdate() {
		return qcfinishdate;
	}

	public void setQcfinishdate(Date qcfinishdate) {
		this.qcfinishdate = qcfinishdate;
	}

	public Integer getQccheckresult() {
		return qccheckresult;
	}

	public void setQccheckresult(Integer qccheckresult) {
		this.qccheckresult = qccheckresult;
	}

	public Integer getQcsecondresult() {
		return qcsecondresult;
	}

	public void setQcsecondresult(Integer qcsecondresult) {
		this.qcsecondresult = qcsecondresult;
	}

	public String getQcname() {
		return qcname;
	}

	public void setQcname(String qcname) {
		this.qcname = qcname;
	}

	public String getQccheckitem() {
		return qccheckitem;
	}

	public void setQccheckitem(String qccheckitem) {
		this.qccheckitem = qccheckitem;
	}

	public String getQcexceptcause() {
		return qcexceptcause;
	}

	public void setQcexceptcause(String qcexceptcause) {
		this.qcexceptcause = qcexceptcause;
	}

	public String getQccosttime() {
		return qccosttime;
	}

	public void setQccosttime(String qccosttime) {
		this.qccosttime = qccosttime;
	}

	public Date getPlanstart() {
		return planstart;
	}

	public void setPlanstart(Date planstart) {
		this.planstart = planstart;
	}

	public Date getPlanend() {
		return planend;
	}

	public void setPlanend(Date planend) {
		this.planend = planend;
	}

	public String getProdcosttime() {
		return prodcosttime;
	}

	public void setProdcosttime(String prodcosttime) {
		this.prodcosttime = prodcosttime;
	}

	public Integer getProdstatus() {
		return prodstatus;
	}

	public void setProdstatus(Integer prodstatus) {
		this.prodstatus = prodstatus;
	}

	public String getProdstatusmemo() {
		return prodstatusmemo;
	}

	public void setProdstatusmemo(String prodstatusmemo) {
		this.prodstatusmemo = prodstatusmemo;
	}

	public String getLedsamplememo() {
		return ledsamplememo;
	}

	public void setLedsamplememo(String ledsamplememo) {
		this.ledsamplememo = ledsamplememo;
	}

	public String getCustomerpotentialrank() {
		return customerpotentialrank;
	}

	public void setCustomerpotentialrank(String customerpotentialrank) {
		this.customerpotentialrank = customerpotentialrank;
	}

	public String getQcexceptreply() {
		return qcexceptreply;
	}

	public void setQcexceptreply(String qcexceptreply) {
		this.qcexceptreply = qcexceptreply;
	}

	public String getProdexceptreply() {
		return prodexceptreply;
	}

	public void setProdexceptreply(String prodexceptreply) {
		this.prodexceptreply = prodexceptreply;
	}

	public Integer getQcexceptstatus() {
		return qcexceptstatus;
	}

	public void setQcexceptstatus(Integer qcexceptstatus) {
		this.qcexceptstatus = qcexceptstatus;
	}

	public Integer getProdexceptstatus() {
		return prodexceptstatus;
	}

	public void setProdexceptstatus(Integer prodexceptstatus) {
		this.prodexceptstatus = prodexceptstatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
