package com.wellsoft.pt.ldx.model.productionPlan;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "zppt0033")
public class ZPPT0033 implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public ZPPT0033(){
		
	}
	/**
	 * 生产任务单
	 */
	@Id
	@Column(name = "zscrwd")
	private String zscrwd;
	/**
	 * 生产订单号
	 */
	@Column(name = "aufnr")
	private String aufnr;
	/**
	 * 客户端
	 */
	@Column(name = "mandt")
	private String mandt;
	/**
	 * 优先级
	 */
	@Column(name = "zyxj")
	private String zyxj;
	/**
	 * 线号
	 */
	@Column(name = "zxh")
	private String zxh;
	/**
	 * 生产日期
	 */
	@Column(name = "gstrp")
	private String gstrp;
	/**
	 * 产量A
	 */
	@Column(name = "gamng01")
	private Double gamng01;
	/**
	 * 产量B
	 */
	@Column(name = "gamng02")
	private Double gamng02;
	/**
	 * 产量C
	 */
	@Column(name = "gamng03")
	private Double gamng03;
	
	/**
	 * 工序别
	 */
	@Column(name = "ltxa1")
	private String ltxa1;
	
	/**
	 * 负荷
	 */
	@Column(name = "zscfh")
	private String zscfh;
	
	/**
	 * 完工量
	 */
	@Column(name = "zwgl")
	private Double zwgl;

	public String getZscrwd() {
		return zscrwd;
	}
	public void setZscrwd(String zscrwd) {
		this.zscrwd = zscrwd;
	}
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
	public String getZyxj() {
		return zyxj;
	}
	public void setZyxj(String zyxj) {
		this.zyxj = zyxj;
	}
	public String getZxh() {
		return zxh;
	}
	public void setZxh(String zxh) {
		this.zxh = zxh;
	}
	public String getGstrp() {
		return gstrp;
	}
	public void setGstrp(String gstrp) {
		this.gstrp = gstrp;
	}
	public Double getGamng01() {
		return gamng01;
	}
	public void setGamng01(Double gamng01) {
		this.gamng01 = gamng01;
	}
	public Double getGamng02() {
		return gamng02;
	}
	public void setGamng02(Double gamng02) {
		this.gamng02 = gamng02;
	}
	public Double getGamng03() {
		return gamng03;
	}
	public void setGamng03(Double gamng03) {
		this.gamng03 = gamng03;
	}
	public String getZscfh() {
		return zscfh;
	}
	public void setZscfh(String zscfh) {
		this.zscfh = zscfh;
	}
	public String getLtxa1() {
		return ltxa1;
	}
	public void setLtxa1(String ltxa1) {
		this.ltxa1 = ltxa1;
	}
	public Double getZwgl() {
		return zwgl;
	}
	public void setZwgl(Double zwgl) {
		this.zwgl = zwgl;
	}
}
