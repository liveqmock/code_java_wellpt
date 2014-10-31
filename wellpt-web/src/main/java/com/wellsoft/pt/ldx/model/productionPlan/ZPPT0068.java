package com.wellsoft.pt.ldx.model.productionPlan;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 
 * Description: 工单表
 *  
 * @author HeShi
 * @date 2014-8-31
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-8-31.1	HeShi		2014-8-31		Create
 * </pre>
 *
 */
@Entity
@Table(name = "zppt0068")
@DynamicUpdate
@DynamicInsert
public class ZPPT0068 implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 工单号
	 */
	@Id
	private String zgdh;
	/**
	 * 客户端
	 */
	private String mandt;
	/**
	 * 线号
	 */
	private String zxh;
	/**
	 * 物料ID
	 */
	private String matnr;
	/**
	 * 物料名称
	 */
	private String zgrun;
	/**
	 * 生产订单号
	 */
	private String aufnr;
	/**
	 * 昨日计划量
	 */
	private Double zzrjhl;
	/**
	 * 今日计划量
	 */
	private Double zjrjhl;
	/**
	 * 完成数量
	 */
	private Double zwczt;
	/**
	 * 状态
	 */
	private String zzt;
	/**
	 * 开始日期
	 */
	private String zksdat;
	/**
	 * 顺序号
	 */
	private String zsxh;
	/**
	 * 发布日期
	 */
	private String zfbdat;
	/**
	 * 客户简称
	 */
	private String sortl;
	/**
	 * 销售订单号
	 */
	private String kdauf;
	/**
	 * 销售订单行项目
	 */
	private String kdpos;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 实际完工数量
	 */
	@Transient
	private String sjwgsl;
	public String getZgdh() {
		return zgdh;
	}
	public void setZgdh(String zgdh) {
		this.zgdh = zgdh;
	}
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getZxh() {
		return zxh;
	}
	public void setZxh(String zxh) {
		this.zxh = zxh;
	}
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	public String getZgrun() {
		return zgrun;
	}
	public void setZgrun(String zgrun) {
		this.zgrun = zgrun;
	}
	public String getAufnr() {
		return aufnr;
	}
	public void setAufnr(String aufnr) {
		this.aufnr = aufnr;
	}
	public Double getZzrjhl() {
		return zzrjhl;
	}
	public void setZzrjhl(Double zzrjhl) {
		this.zzrjhl = zzrjhl;
	}
	public Double getZjrjhl() {
		return zjrjhl;
	}
	public void setZjrjhl(Double zjrjhl) {
		this.zjrjhl = zjrjhl;
	}
	public Double getZwczt() {
		return zwczt;
	}
	public void setZwczt(Double zwczt) {
		this.zwczt = zwczt;
	}
	public String getZzt() {
		return zzt;
	}
	public void setZzt(String zzt) {
		this.zzt = zzt;
	}
	public String getZksdat() {
		return zksdat;
	}
	public void setZksdat(String zksdat) {
		this.zksdat = zksdat;
	}
	public String getZsxh() {
		return zsxh;
	}
	public void setZsxh(String zsxh) {
		this.zsxh = zsxh;
	}
	public String getZfbdat() {
		return zfbdat;
	}
	public void setZfbdat(String zfbdat) {
		this.zfbdat = zfbdat;
	}
	public String getSortl() {
		return sortl;
	}
	public void setSortl(String sortl) {
		this.sortl = sortl;
	}
	public String getKdauf() {
		return kdauf;
	}
	public void setKdauf(String kdauf) {
		this.kdauf = kdauf;
	}
	public String getKdpos() {
		return kdpos;
	}
	public void setKdpos(String kdpos) {
		this.kdpos = kdpos;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSjwgsl() {
		return sjwgsl;
	}
	public void setSjwgsl(String sjwgsl) {
		this.sjwgsl = sjwgsl;
	}
}
