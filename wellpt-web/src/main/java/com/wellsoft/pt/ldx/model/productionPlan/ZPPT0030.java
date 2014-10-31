package com.wellsoft.pt.ldx.model.productionPlan;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "zppt0030")
@DynamicUpdate
@DynamicInsert
public class ZPPT0030 implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 生产订单号
	 */
	@Id
	private String aufnr;
	
	/**
	 * 客户端
	 */
	private String mandt;
	/**
	 * 客户编号
	 */
	private String sortl;
	/**
	 * 销售订单号
	 */
	private String kdauf;
	/**
	 * 销售订单行项
	 */
	private String kdpos;
	/**
	 * SD交期
	 */
	private String edatu;
	/**
	 * 物料ID
	 */
	private String matnr;
	/**
	 * 物料描述
	 */
	private String zgrun;
	/**
	 * 计划量
	 */
	private Double gamng;
	/**
	 * 上线日期
	 */
	private String gstrp;
	/**
	 * 完工日期
	 */
	private String gltrp;
	/**
	 * 生产标准工时之总装
	 */
	private String vgw01;
	/**
	 * 生产标准工时之包装
	 */
	private String vgw02;
	/**
	 * 完成量
	 */
	private Double igmng;
	/**
	 * 未完成量
	 */
	private Double zwwcl;
	/**
	 * 预计船期
	 */
	private String zyjcq;
	/**
	 * 工厂
	 */
	private String dwerk;
	
	public String getAufnr() {
		return aufnr;
	}
	public void setAufnr(String aufnr) {
		this.aufnr = aufnr;
	}
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
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
	public Double getGamng() {
		return gamng;
	}
	public void setGamng(Double gamng) {
		this.gamng = gamng;
	}
	public String getGstrp() {
		return gstrp;
	}
	public void setGstrp(String gstrp) {
		this.gstrp = gstrp;
	}
	public String getGltrp() {
		return gltrp;
	}
	public void setGltrp(String gltrp) {
		this.gltrp = gltrp;
	}
	public String getVgw01() {
		return vgw01;
	}
	public void setVgw01(String vgw01) {
		this.vgw01 = vgw01;
	}
	public String getVgw02() {
		return vgw02;
	}
	public void setVgw02(String vgw02) {
		this.vgw02 = vgw02;
	}
	public Double getIgmng() {
		return igmng;
	}
	public void setIgmng(Double igmng) {
		this.igmng = igmng;
	}
	public Double getZwwcl() {
		return zwwcl;
	}
	public void setZwwcl(Double zwwcl) {
		this.zwwcl = zwwcl;
	}
	public String getEdatu() {
		return edatu;
	}
	public void setEdatu(String edatu) {
		this.edatu = edatu;
	}
	public String getZyjcq() {
		return zyjcq;
	}
	public void setZyjcq(String zyjcq) {
		this.zyjcq = zyjcq;
	}
	public String getDwerk() {
		return dwerk;
	}
	public void setDwerk(String dwerk) {
		this.dwerk = dwerk;
	}
	
}
