package com.wellsoft.pt.ldx.model.ficoManage;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
/**
 * 未收汇手工资料表
 * @author HeShi
 */
@Entity
@Table(name = "zfmt0017")
@DynamicUpdate
@DynamicInsert
public class Zfmt0017 implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 客户端
	 */
	private String mandt;
	/**
	 * 外向交货单
	 */
	@Id
	private String vbeln;
	/**
	 * 报关单号
	 */
	private String vbelv;
	/**
	 * 报关数量
	 */
	private String lfimg;
	/**
	 * 报关金额
	 */
	private String zeamt;
	/**
	 * 申报日期
	 */
	private String zddate;
	/**
	 * 折算数量
	 */
	private String zclfimg;
	/**
	 * 报关海运费
	 */
	private String zcost;
	/**
	 * 柜型
	 */
	private String zmodel;
	/**
	 * 付款条件代码
	 */
	private String zterm;
	/**
	 * 扣款类型
	 */
	private String zvtext;
	/**
	 * 扣款金额
	 */
	private String zcamt;
	/**
	 * 备注
	 */
	private String znote;
	/**
	 * 客户编号
	 */
	private String kunnr;
	
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getVbeln() {
		return vbeln;
	}
	public void setVbeln(String vbeln) {
		this.vbeln = vbeln;
	}
	public String getVbelv() {
		return vbelv;
	}
	public void setVbelv(String vbelv) {
		this.vbelv = vbelv;
	}
	public String getLfimg() {
		return lfimg;
	}
	public void setLfimg(String lfimg) {
		this.lfimg = lfimg;
	}
	public String getZddate() {
		return zddate;
	}
	public void setZddate(String zddate) {
		this.zddate = zddate;
	}
	public String getZclfimg() {
		return zclfimg;
	}
	public void setZclfimg(String zclfimg) {
		this.zclfimg = zclfimg;
	}
	public String getZcost() {
		return zcost;
	}
	public void setZcost(String zcost) {
		this.zcost = zcost;
	}
	public String getZmodel() {
		return zmodel;
	}
	public void setZmodel(String zmodel) {
		this.zmodel = zmodel;
	}
	public String getZterm() {
		return zterm;
	}
	public void setZterm(String zterm) {
		this.zterm = zterm;
	}
	public String getZvtext() {
		return zvtext;
	}
	public void setZvtext(String zvtext) {
		this.zvtext = zvtext;
	}
	public String getZcamt() {
		return zcamt;
	}
	public void setZcamt(String zcamt) {
		this.zcamt = zcamt;
	}
	public String getZnote() {
		return znote;
	}
	public void setZnote(String znote) {
		this.znote = znote;
	}
	public String getKunnr() {
		return kunnr;
	}
	public void setKunnr(String kunnr) {
		this.kunnr = kunnr;
	}
	public String getZeamt() {
		return zeamt;
	}
	public void setZeamt(String zeamt) {
		this.zeamt = zeamt;
	}
}
