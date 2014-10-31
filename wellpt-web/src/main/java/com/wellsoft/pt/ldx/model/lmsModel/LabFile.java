package com.wellsoft.pt.ldx.model.lmsModel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "LABFILE")
@DynamicUpdate
@DynamicInsert
public class LabFile  implements Serializable{
	public LabFile() {
	}
	/**
	 * 设备编号
	 */
	@Id
	@Column(name="labfile_no")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String labfileNo;
	/**
	 * 生产厂家
	 */
	@Column(name="labfile_sup")
	private String labfileSup;
	/**
	 * 设备名称
	 */
	@Column(name="labfile_name")
	private String labfileName;
	/**
	 * 出厂编号
	 */
	@Column(name="labfile_defno")
	private String labfileDefno;
	/**
	 * 设备规格
	 */
	@Column(name="labfile_spec")
	private String labfileSpec;
	/**
	 * 设备类型
	 */
	@Column(name="labfile_cls")
	private String labfileCls;
	/**
	 * 当前使用部门
	 */
	@Column(name="labfile_depno")
	private String labfileDepno;
	/**
	 * 成本中心负责人
	 */
	@Column(name="labfile_usr")
	private String labfileUsr;
	/**
	 * 库存数量
	 */
	@Column(name="labfile_num")
	private Integer labfileNum;
	/**
	 * 安装地点
	 */
	@Column(name="labfile_address")
	private String labfileAddress;
	/**
	 * 校准方式
	 */
	@Column(name="labfile_check")
	private Integer labfileCheck;
	/**
	 * 定检周期(0=,1=一年,2=二年,3=三年,4=50h,5=五年)
	 */
	@Column(name="labfile_cycle")
	private Integer labfileCycle;
	/**
	 * 设备状态(0=完好,1=在修,2=封存,3=闲置,4=报废,5=未验收,6=待报废)
	 */
	@Column(name="labfile_state")
	private Integer labfileState;
	/**
	 * 备注
	 */
	@Column(name="labfile_rem")
	private String labfileRem;
	/**
	 * 验收单号
	 */
	@Column(name="labfile_acceptno")
	private String labfileAcceptno;
	/**
	 * 验收情况
	 */
	@Column(name="labfile_acceptrem")
	private String labfileAcceptrem;
	/**
	 * 校准日期
	 */
	@Column(name="labfile_checkdate")
	private String labfileCheckdate;
	/**
	 * 有效期至
	 */
	@Column(name="labfile_havedate")
	private String labfileHavedate;
	/**
	 * 精度要求
	 */
	@Column(name="labfile_accuracy")
	private String labfileAccuracy;
	/**
	 * 固定资产号
	 */
	@Column(name="labfile_assetsno")
	private String labfileAssetsno;
	/**
	 * 主体归属(1=厦门光电,2=立明光电,3=绿色照明,4=电光源,5=海德信,6=四川鼎吉,7=四川联恺,8=绿天光电,9=其它)
	 */
	@Column(name="labfile_mainhave")
	private Integer labfileMainhave;
	/**
	 * 资产属性(1=固定资产,2=低值易耗品,3=其它)
	 */
	@Column(name="labfile_assetscls")
	private Integer labfileAssetscls;
	/**
	 * 启用日期
	 */
	@Column(name="labfile_usrdate")
	private String labfileUsrdate;
	/**
	 * 报废单号
	 */
	@Column(name="labfile_scrappedno")
	private String labfileScrappedno;
	/**
	 * 购置原值
	 */
	@Column(name="labfile_purchase")
	private String labfilePurchase;
	/**
	 * 销售单位
	 */
	@Column(name="labfile_saleunit")
	private String labfileSaleunit;
	/**
	 * 销售人员
	 */
	@Column(name="labfile_salenm")
	private String labfileSalenm;
	/**
	 * 销售电话
	 */
	@Column(name="labfile_saletel")
	private String labfileSaletel;
	/**
	 * 使用年限
	 */
	@Column(name="labfile_usryear")
	private Integer labfileUsryear;
	/**
	 * 证书编号
	 */
	@Column(name="labfile_proveno")
	private String labfileProveno;
	/**
	 * 测试项目
	 */
	@Column(name="labfile_wgno")
	private String labfileWgno;
	/**
	 * 使用工厂
	 */
	@Column(name="labfile_factory")
	private String labfileFactory;
	/**
	 * 资产状态(10=完好,11=在修,12=封存闲置,13=报废,14=未验收,15=待报废)
	 */
	@Column(name="labfile_zcstate")
	private Integer labfileZcstate;
	/**
	 * 序列号
	 */
	@Column(name="labfile_serialno")
	private String labfileSerialno;
	/**
	 * 成本中心代码
	 */
	@Column(name="labfile_costno")
	private String labfileCostno;
	/**
	 * 采购人员
	 */
	@Column(name="labfile_procure")
	private String labfileProcure;
	/**
	 * 接口人员
	 */
	@Column(name="labfile_interusr")
	private String labfileInterusr;
	/**
	 * 领用人
	 */
	@Column(name="labfile_claim")
	private String labfileClaim;
	/**
	 * 成本中心名称
	 */
	@Transient
	private String costName;
	/**
	 * 主体归属名称
	 */
	@Transient
	private String mainHaveName;
	/**
	 * 生产厂家名称
	 */
	@Transient
	private String supName;
	
	public String getSupName() {
		return supName;
	}
	public void setSupName(String supName) {
		this.supName = supName;
	}
	public String getMainHaveName() {
		if("1".equals(this.labfileMainhave))mainHaveName="厦门光电";
		if("2".equals(this.labfileMainhave))mainHaveName="立明光电";
		if("3".equals(this.labfileMainhave))mainHaveName="绿色照明";
		if("4".equals(this.labfileMainhave))mainHaveName="电光源";
		if("5".equals(this.labfileMainhave))mainHaveName="海德信";
		if("6".equals(this.labfileMainhave))mainHaveName="四川鼎吉";
		if("7".equals(this.labfileMainhave))mainHaveName="四川联恺";
		if("8".equals(this.labfileMainhave))mainHaveName="绿天光电";
		if("9".equals(this.labfileMainhave))mainHaveName="其它";
		return mainHaveName;
	}
	public void setMainHaveName(String mainHaveName) {
	}
	public String getCostName() {
		return costName;
	}
	public void setCostName(String costName) {
		this.costName = costName;
	}
	public String getLabfileNo() {
		return labfileNo;
	}
	public void setLabfileNo(String labfileNo) {
		this.labfileNo = labfileNo;
	}
	public String getLabfileSup() {
		return labfileSup;
	}
	public void setLabfileSup(String labfileSup) {
		this.labfileSup = labfileSup;
	}
	public String getLabfileName() {
		return labfileName;
	}
	public void setLabfileName(String labfileName) {
		this.labfileName = labfileName;
	}
	public String getLabfileDefno() {
		return labfileDefno;
	}
	public void setLabfileDefno(String labfileDefno) {
		this.labfileDefno = labfileDefno;
	}
	public String getLabfileSpec() {
		return labfileSpec;
	}
	public void setLabfileSpec(String labfileSpec) {
		this.labfileSpec = labfileSpec;
	}
	public String getLabfileCls() {
		return labfileCls;
	}
	public void setLabfileCls(String labfileCls) {
		this.labfileCls = labfileCls;
	}
	public String getLabfileDepno() {
		return labfileDepno;
	}
	public void setLabfileDepno(String labfileDepno) {
		this.labfileDepno = labfileDepno;
	}
	public String getLabfileUsr() {
		return labfileUsr;
	}
	public void setLabfileUsr(String labfileUsr) {
		this.labfileUsr = labfileUsr;
	}
	public Integer getLabfileNum() {
		return labfileNum;
	}
	public void setLabfileNum(Integer labfileNum) {
		this.labfileNum = labfileNum;
	}
	public String getLabfileAddress() {
		return labfileAddress;
	}
	public void setLabfileAddress(String labfileAddress) {
		this.labfileAddress = labfileAddress;
	}
	public Integer getLabfileCheck() {
		return labfileCheck;
	}
	public void setLabfileCheck(Integer labfileCheck) {
		this.labfileCheck = labfileCheck;
	}
	public Integer getLabfileCycle() {
		return labfileCycle;
	}
	public void setLabfileCycle(Integer labfileCycle) {
		this.labfileCycle = labfileCycle;
	}
	public Integer getLabfileState() {
		return labfileState;
	}
	public void setLabfileState(Integer labfileState) {
		this.labfileState = labfileState;
	}
	public String getLabfileRem() {
		return labfileRem;
	}
	public void setLabfileRem(String labfileRem) {
		this.labfileRem = labfileRem;
	}
	public String getLabfileAcceptno() {
		return labfileAcceptno;
	}
	public void setLabfileAcceptno(String labfileAcceptno) {
		this.labfileAcceptno = labfileAcceptno;
	}
	public String getLabfileAcceptrem() {
		return labfileAcceptrem;
	}
	public void setLabfileAcceptrem(String labfileAcceptrem) {
		this.labfileAcceptrem = labfileAcceptrem;
	}
	public String getLabfileCheckdate() {
		return labfileCheckdate;
	}
	public void setLabfileCheckdate(String labfileCheckdate) {
		this.labfileCheckdate = labfileCheckdate;
	}
	public String getLabfileHavedate() {
		return labfileHavedate;
	}
	public void setLabfileHavedate(String labfileHavedate) {
		this.labfileHavedate = labfileHavedate;
	}
	public String getLabfileAccuracy() {
		return labfileAccuracy;
	}
	public void setLabfileAccuracy(String labfileAccuracy) {
		this.labfileAccuracy = labfileAccuracy;
	}
	public String getLabfileAssetsno() {
		return labfileAssetsno;
	}
	public void setLabfileAssetsno(String labfileAssetsno) {
		this.labfileAssetsno = labfileAssetsno;
	}
	public Integer getLabfileMainhave() {
		return labfileMainhave;
	}
	public void setLabfileMainhave(Integer labfileMainhave) {
		this.labfileMainhave = labfileMainhave;
	}
	public Integer getLabfileAssetscls() {
		return labfileAssetscls;
	}
	public void setLabfileAssetscls(Integer labfileAssetscls) {
		this.labfileAssetscls = labfileAssetscls;
	}
	public String getLabfileUsrdate() {
		return labfileUsrdate;
	}
	public void setLabfileUsrdate(String labfileUsrdate) {
		this.labfileUsrdate = labfileUsrdate;
	}
	public String getLabfileScrappedno() {
		return labfileScrappedno;
	}
	public void setLabfileScrappedno(String labfileScrappedno) {
		this.labfileScrappedno = labfileScrappedno;
	}
	public String getLabfilePurchase() {
		return labfilePurchase;
	}
	public void setLabfilePurchase(String labfilePurchase) {
		this.labfilePurchase = labfilePurchase;
	}
	public String getLabfileSaleunit() {
		return labfileSaleunit;
	}
	public void setLabfileSaleunit(String labfileSaleunit) {
		this.labfileSaleunit = labfileSaleunit;
	}
	public String getLabfileSalenm() {
		return labfileSalenm;
	}
	public void setLabfileSalenm(String labfileSalenm) {
		this.labfileSalenm = labfileSalenm;
	}
	public String getLabfileSaletel() {
		return labfileSaletel;
	}
	public void setLabfileSaletel(String labfileSaletel) {
		this.labfileSaletel = labfileSaletel;
	}
	public Integer getLabfileUsryear() {
		return labfileUsryear;
	}
	public void setLabfileUsryear(Integer labfileUsryear) {
		this.labfileUsryear = labfileUsryear;
	}
	public String getLabfileProveno() {
		return labfileProveno;
	}
	public void setLabfileProveno(String labfileProveno) {
		this.labfileProveno = labfileProveno;
	}
	public String getLabfileWgno() {
		return labfileWgno;
	}
	public void setLabfileWgno(String labfileWgno) {
		this.labfileWgno = labfileWgno;
	}
	public String getLabfileFactory() {
		return labfileFactory;
	}
	public void setLabfileFactory(String labfileFactory) {
		this.labfileFactory = labfileFactory;
	}
	public Integer getLabfileZcstate() {
		return labfileZcstate;
	}
	public void setLabfileZcstate(Integer labfileZcstate) {
		this.labfileZcstate = labfileZcstate;
	}
	public String getLabfileSerialno() {
		return labfileSerialno;
	}
	public void setLabfileSerialno(String labfileSerialno) {
		this.labfileSerialno = labfileSerialno;
	}
	public String getLabfileCostno() {
		return labfileCostno;
	}
	public void setLabfileCostno(String labfileCostno) {
		this.labfileCostno = labfileCostno;
	}
	public String getLabfileProcure() {
		return labfileProcure;
	}
	public void setLabfileProcure(String labfileProcure) {
		this.labfileProcure = labfileProcure;
	}
	public String getLabfileInterusr() {
		return labfileInterusr;
	}
	public void setLabfileInterusr(String labfileInterusr) {
		this.labfileInterusr = labfileInterusr;
	}
	public String getLabfileClaim() {
		return labfileClaim;
	}
	public void setLabfileClaim(String labfileClaim) {
		this.labfileClaim = labfileClaim;
	}
}
