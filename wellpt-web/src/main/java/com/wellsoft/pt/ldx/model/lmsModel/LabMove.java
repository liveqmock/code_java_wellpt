package com.wellsoft.pt.ldx.model.lmsModel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
@Entity
@Table(name = "LABMOVE")
@DynamicUpdate
@DynamicInsert
public class LabMove implements Serializable {

	public LabMove(){
		
	}
	/**
	 * id(序号)
	 */
	@Id
	@Column(name="labmove_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	 private Integer labmoveId;
	/**
	 * 设备类型(A=灯架,B=设备)
	 */
	@Column(name="labmove_type")
	 private String labmoveType;
	/**
	 * 设备编号
	 */
	@Column(name="labmove_no")
	 private String labmoveNo;
	/**
	 * 转移类别(A=借出,B=卖出)
	 */
	@Column(name="labmove_cls")
	 private String labmoveCls;
	/**
	 * 转移日期
	 */
	@Column(name="labmove_date")
	 private String labmoveDate;
	/**
	 * 原使用部门(来源hrm01实验室部门资料表)
	 */
	@Column(name="labmove_odepno")
	 private String labmoveOdepno;
	/**
	 * 原使用人员(来源SSMAD员工资料表
	 */
	@Column(name="labmove_oempno")
	 private String labmoveOempno;
	/**
	 * 原安装地点'
	 */
	@Column(name="labmove_oaddress")
	 private String labmoveOaddress;
	/**
	 * 新安装部门(来源hrm01实验室部门资料表
	 */
	@Column(name="labmove_ndepno")
	 private String labmoveNdepno;
	/**
	 * '新使用人员(来源SSMAD员工资料表)
	 */
	@Column(name="labmove_nempno")
	 private String labmoveNempno;
	/**
	 * 新安装地点
	 */
	@Column(name="labmove_naddress")
	 private String labmoveNaddress;
	/**
	 * 预计归还日期(借出时才填)'
	 */
	@Column(name="labmove_setdate")
	 private String labmoveSetdate;
	/**
	 * 实际归还日期(借出时才填)
	 */
	@Column(name="labmove_retdate")
	 private String labmoveRetdate;
	/**
	 * 借出备注
	 */
	@Column(name="labmove_rem")
	 private String labmoveRem;
	/**
	 * 归还备注
	 */
	@Column(name="labmove_retrem")
	 private String labmoveRetrem;
	/**
	 * 原成本中心编码（来源costcenter成本中心表）
	 */
	@Column(name="labmove_ocostno")
	 private String labmoveOcostno;
	/**
	 * 新成本中心编码（来源costcenter成本中心表）
	 */
	@Column(name="labmove_ncostno")
	 private String labmoveNcostno;
	/**
	 * 原设备类别(来源comcls类别资料表)
	 */
	@Column(name="labmove_oname")
	 private Integer labmoveOname;
	/**
	 * 新设备类别(来源comcls类别资料表)
	 */
	@Column(name="labmove_nname")
	 private Integer labmoveNname;
	/**
	 * 原主体归属(1=厦门光电,2=立明光电,3=绿色照明,4=电光源,5=海德信,6=四川鼎吉,7=四川联恺,9=其它)
	 */
	@Column(name="labmove_omainhave")
	 private Integer labmoveOmainhave;
	/**
	 * 新主体归属(1=厦门光电,2=立明光电,3=绿色照明,4=电光源,5=海德信,6=四川鼎吉,7=四川联恺,9=其它)
	 */
	@Column(name="labmove_nmainhave")
	 private Integer labmoveNmainhave;
	/**
	 * 原接口人员(来源SSMAD员工资料表)'
	 */
	@Column(name="labmove_ointerusr")
	 private String labmoveOinterusr;
	/**
	 * 新接口人员(来源SSMAD员工资料表)
	 */
	@Column(name="labmove_ninterusr")
	 private String labmoveNinterusr;
	/**
	 * 修改人员
	 */
	@Column(name="labmove_modeusr")
	 private String labmoveModeusr;
	/**
	 * 修改日期
	 */
	@Column(name="labmove_modedate")
	 private String labmoveModedate;
	/**
	 * 原领用人
	 */
	@Column(name="labmove_oclaim")
	 private String labmoveOclaim;
	/**
	 * 新领用人
	 */
	@Column(name="labmove_nclaim")
	 private String labmoveNclaim;
	/**
	 * 原设备状态（0=完好,1=在修,2=封存,3=闲置,4=报废,5=未验收,6=待报废）
	 */
	@Column(name="labmove_ostate")
	 private Integer labmoveOstate;
	/**
	 * 现设备状态（0=完好,1=在修,2=封存,3=闲置,4=报废,5=未验收,6=待报废）
	 */
	@Column(name="labmove_nstate")
	 private Integer labmoveNstate;
	/**
	 * 原固定资产号
	 */
	@Column(name="labmove_oassetsno")
	 private String labmoveOassetsno;
	/**
	 * 新固定资产号
	 */
	@Column(name="labmove_nassetsno")
	 private String labmoveNassetsno;
	public Integer getLabmoveId() {
		return labmoveId;
	}

	public void setLabmoveId(Integer labmoveId) {
		this.labmoveId = labmoveId;
	}

	public String getLabmoveType() {
		return labmoveType;
	}

	public void setLabmoveType(String labmoveType) {
		this.labmoveType = labmoveType;
	}

	public String getLabmoveNo() {
		return labmoveNo;
	}

	public void setLabmoveNo(String labmoveNo) {
		this.labmoveNo = labmoveNo;
	}

	public String getLabmoveCls() {
		return labmoveCls;
	}

	public void setLabmoveCls(String labmoveCls) {
		this.labmoveCls = labmoveCls;
	}

	public String getLabmoveDate() {
		return labmoveDate;
	}

	public void setLabmoveDate(String labmoveDate) {
		this.labmoveDate = labmoveDate;
	}

	public String getLabmoveOdepno() {
		return labmoveOdepno;
	}

	public void setLabmoveOdepno(String labmoveOdepno) {
		this.labmoveOdepno = labmoveOdepno;
	}

	public String getLabmoveOempno() {
		return labmoveOempno;
	}

	public void setLabmoveOempno(String labmoveOempno) {
		this.labmoveOempno = labmoveOempno;
	}

	public String getLabmoveOaddress() {
		return labmoveOaddress;
	}

	public void setLabmoveOaddress(String labmoveOaddress) {
		this.labmoveOaddress = labmoveOaddress;
	}

	public String getLabmoveNdepno() {
		return labmoveNdepno;
	}

	public void setLabmoveNdepno(String labmoveNdepno) {
		this.labmoveNdepno = labmoveNdepno;
	}

	public String getLabmoveNempno() {
		return labmoveNempno;
	}

	public void setLabmoveNempno(String labmoveNempno) {
		this.labmoveNempno = labmoveNempno;
	}

	public String getLabmoveNaddress() {
		return labmoveNaddress;
	}

	public void setLabmoveNaddress(String labmoveNaddress) {
		this.labmoveNaddress = labmoveNaddress;
	}

	public String getLabmoveSetdate() {
		return labmoveSetdate;
	}

	public void setLabmoveSetdate(String labmoveSetdate) {
		this.labmoveSetdate = labmoveSetdate;
	}

	public String getLabmoveRetdate() {
		return labmoveRetdate;
	}

	public void setLabmoveRetdate(String labmoveRetdate) {
		this.labmoveRetdate = labmoveRetdate;
	}

	public String getLabmoveRem() {
		return labmoveRem;
	}

	public void setLabmoveRem(String labmoveRem) {
		this.labmoveRem = labmoveRem;
	}

	public String getLabmoveRetrem() {
		return labmoveRetrem;
	}

	public void setLabmoveRetrem(String labmoveRetrem) {
		this.labmoveRetrem = labmoveRetrem;
	}

	public String getLabmoveOcostno() {
		return labmoveOcostno;
	}

	public void setLabmoveOcostno(String labmoveOcostno) {
		this.labmoveOcostno = labmoveOcostno;
	}

	public String getLabmoveNcostno() {
		return labmoveNcostno;
	}

	public void setLabmoveNcostno(String labmoveNcostno) {
		this.labmoveNcostno = labmoveNcostno;
	}

	public Integer getLabmoveOname() {
		return labmoveOname;
	}

	public void setLabmoveOname(Integer labmoveOname) {
		this.labmoveOname = labmoveOname;
	}

	public Integer getLabmoveNname() {
		return labmoveNname;
	}

	public void setLabmoveNname(Integer labmoveNname) {
		this.labmoveNname = labmoveNname;
	}

	public Integer getLabmoveOmainhave() {
		return labmoveOmainhave;
	}

	public void setLabmoveOmainhave(Integer labmoveOmainhave) {
		this.labmoveOmainhave = labmoveOmainhave;
	}

	public Integer getLabmoveNmainhave() {
		return labmoveNmainhave;
	}

	public void setLabmoveNmainhave(Integer labmoveNmainhave) {
		this.labmoveNmainhave = labmoveNmainhave;
	}

	public String getLabmoveOinterusr() {
		return labmoveOinterusr;
	}

	public void setLabmoveOinterusr(String labmoveOinterusr) {
		this.labmoveOinterusr = labmoveOinterusr;
	}

	public String getLabmoveNinterusr() {
		return labmoveNinterusr;
	}

	public void setLabmoveNinterusr(String labmoveNinterusr) {
		this.labmoveNinterusr = labmoveNinterusr;
	}

	public String getLabmoveModeusr() {
		return labmoveModeusr;
	}

	public void setLabmoveModeusr(String labmoveModeusr) {
		this.labmoveModeusr = labmoveModeusr;
	}

	public String getLabmoveModedate() {
		return labmoveModedate;
	}

	public void setLabmoveModedate(String labmoveModedate) {
		this.labmoveModedate = labmoveModedate;
	}

	public String getLabmoveOclaim() {
		return labmoveOclaim;
	}

	public void setLabmoveOclaim(String labmoveOclaim) {
		this.labmoveOclaim = labmoveOclaim;
	}

	public String getLabmoveNclaim() {
		return labmoveNclaim;
	}

	public void setLabmoveNclaim(String labmoveNclaim) {
		this.labmoveNclaim = labmoveNclaim;
	}

	public Integer getLabmoveOstate() {
		return labmoveOstate;
	}

	public void setLabmoveOstate(Integer labmoveOstate) {
		this.labmoveOstate = labmoveOstate;
	}

	public Integer getLabmoveNstate() {
		return labmoveNstate;
	}

	public void setLabmoveNstate(Integer labmoveNstate) {
		this.labmoveNstate = labmoveNstate;
	}

	public String getLabmoveOassetsno() {
		return labmoveOassetsno;
	}

	public void setLabmoveOassetsno(String labmoveOassetsno) {
		this.labmoveOassetsno = labmoveOassetsno;
	}

	public String getLabmoveNassetsno() {
		return labmoveNassetsno;
	}

	public void setLabmoveNassetsno(String labmoveNassetsno) {
		this.labmoveNassetsno = labmoveNassetsno;
	}

}
