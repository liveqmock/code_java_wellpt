package com.wellsoft.pt.ldx.model.productionPlan;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "zppt0031")
public class ZPPT0031 implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 生产订单号
	 */
	@Id
	@Column(name = "aufnr")
	private String aufnr;
	
	public ZPPT0031(){
		
	}
	
	/**
	 * 客户端
	 */
	@Column(name = "mandt")
	private String mandt;
	/**
	 * 生管
	 */
	@Column(name = "zsg")
	private String zsg;
	/**
	 * 线别生产日期
	 */
	@Column(name = "zxhscrq")
	private String zxhscrq;
	/**
	 * 已排产量
	 */
	@Column(name = "zypcl")
	private String zypcl;
	/**
	 * 状态
	 */
	@Column(name = "zzt")
	private String zzt;
	/**
	 * 其他
	 */
	@Column(name = "zqt")
	private String zqt;
	/**
	 * 优先级
	 */
	@Column(name = "zyxj")
	private String zyxj;
	/**
	 * 完工判断(1完工 ，0未完工)
	 */
	@Column(name = "zwgpd")
	private String zwgpd;
	
	/**
	 * 缺料状态
	 */
	@Column(name = "zqlzt")
	private String zqlzt;
	/**
	 * 物料状况
	 */
	@Column(name = "zwlzk")
	private String zwlzk;
	
	/**
	 * 工厂
	 */
	@Column(name = "dwerks")
	private String dwerks;
	
	/**
	 * mps上线日期
	 */
	@Column(name = "zsxrq")
	private String zsxrq;
	
	/**
	 * 完成量
	 */
	@Column(name = "zwcl")
	private String zwcl;
	
	/**
	 * 未完成量
	 */
	@Column(name = "zwwcl")
	private String zwwcl;
	
	/**
	 * 当前排产量
	 */
	@Column(name = "zdqpcl")
	private String zdqpcl;
	
	/**
	 * 难易度
	 */
	@Column(name = "zlevel")
	private String zlevel;
	
	/**
	 * 可排产量
	 */
	@Column(name = "zkpcl")
	private String zkpcl;
	
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
	public String getZsg() {
		return zsg;
	}
	public void setZsg(String zsg) {
		this.zsg = zsg;
	}
	public String getZxhscrq() {
		return zxhscrq;
	}
	public void setZxhscrq(String zxhscrq) {
		this.zxhscrq = zxhscrq;
	}
	public String getZypcl() {
		return zypcl;
	}
	public void setZypcl(String zypcl) {
		this.zypcl = zypcl;
	}
	public String getZzt() {
		return zzt;
	}
	public void setZzt(String zzt) {
		this.zzt = zzt;
	}
	public String getZqt() {
		return zqt;
	}
	public void setZqt(String zqt) {
		this.zqt = zqt;
	}
	public String getZyxj() {
		return zyxj;
	}
	public void setZyxj(String zyxj) {
		this.zyxj = zyxj;
	}
	public String getZwgpd() {
		return zwgpd;
	}
	public void setZwgpd(String zwgpd) {
		this.zwgpd = zwgpd;
	}
	public String getZqlzt() {
		return zqlzt;
	}
	public void setZqlzt(String zqlzt) {
		this.zqlzt = zqlzt;
	}
	public String getZwlzk() {
		return zwlzk;
	}
	public void setZwlzk(String zwlzk) {
		this.zwlzk = zwlzk;
	}
	public String getDwerks() {
		return dwerks;
	}
	public void setDwerks(String dwerks) {
		this.dwerks = dwerks;
	}
	public String getZwcl() {
		return zwcl;
	}
	public void setZwcl(String zwcl) {
		this.zwcl = zwcl;
	}
	public String getZwwcl() {
		return zwwcl;
	}
	public void setZwwcl(String zwwcl) {
		this.zwwcl = zwwcl;
	}
	public String getZdqpcl() {
		return zdqpcl;
	}
	public void setZdqpcl(String zdqpcl) {
		this.zdqpcl = zdqpcl;
	}
	public String getZsxrq() {
		return zsxrq;
	}
	public void setZsxrq(String zsxrq) {
		this.zsxrq = zsxrq;
	}
	public String getZlevel() {
		return zlevel;
	}
	public void setZlevel(String zlevel) {
		this.zlevel = zlevel;
	}
	public String getZkpcl() {
		return zkpcl;
	}
	public void setZkpcl(String zkpcl) {
		this.zkpcl = zkpcl;
	}
	
}
