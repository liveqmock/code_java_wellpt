package com.wellsoft.pt.ldx.model.ficoManage;

import java.io.Serializable;

/**
 * 到帐信息分解表
 * @author HeShi
 */
public class Zfmt0004 implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 客户端
	 */
	private String mandt;
	/**
	 * 凭证编号（流水号）
	 */
	private String zbelnr;
	/**
	 * 行项目
	 */
	private String zposnr;
	/**
	 * 合同号
	 */
	private String bstkd;
	/**
	 * 外向交货单号
	 */
	private String vbeln;
	/**
	 * 收款金额
	 */
	private String zcamount;
	/**
	 * 冲销金额
	 */
	private String zwoamt;
	/**
	 * 业务类型
	 */
	private String zrbl;
	/**
	 * 预计出口日期
	 */
	private String zpodat;
	/**
	 * 合同金额
	 */
	private String zbamt;
	/**
	 * 合同币种
	 */
	private String waers;
	/**
	 * 记账汇率
	 */
	private String kursf;
	/**
	 * 备注
	 */
	private String zanote;
	/**
	 * 样品款类型
	 */
	private String zsmc;
	/**
	 * 手续费
	 */
	private String zhc;
	/**
	 * 凭证号
	 */
	private String belnr;
	/**
	 * 特殊总帐
	 */
	private String umskz;
	/**
	 * 原因代码
	 */
	private String rstgr;
	/**
	 * 快递单号
	 */
	private String ztnum;
	/**
	 * 预收状态
	 */
	private String zdrs;
	/**
	 * 流转状态
	 */
	private String zcirs;
	/**
	 * 发票号
	 */
	private String zuonr;
	/**
	 * 预收冲销凭证号
	 */
	private String stblg;
	/**
	 * AE预收金额
	 */
	private String aeamt;
	
	/**
	 * 销售税代码
	 */
	private String mwskz;
	
	/**
	 * 源信息行项
	 */
	private String zposnrSup;
	
	/**
	 * 未清金额
	 */
	private String wqamt;
	
	/**
	 * 产品组
	 */
	private String spart;
	
	/**
	 * 红冲凭证号
	 */
	private String zstblg;
	
	/**
	 * 红冲预收凭证
	 */
	private String zpStblg;
	
	/**
	 * 冲销预收过账日期
	 */
	private String zwodat;
	/**
	 * 客户编号
	 */
	private String kunnr;
	
	/**
	 * 客户简称
	 */
	private String sortl;
	
	/**
	 * 公司代码
	 */
	private String bukrs;
	
	/**
	 * 折算系数
	 */
	private String zpeinh;
	
	/**
	 * 会计凭证2,3
	 */
	private String belnr2;
	private String belnr3;
	
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getZbelnr() {
		return zbelnr;
	}
	public void setZbelnr(String zbelnr) {
		this.zbelnr = zbelnr;
	}
	public String getZposnr() {
		return zposnr;
	}
	public void setZposnr(String zposnr) {
		this.zposnr = zposnr;
	}
	public String getBstkd() {
		return bstkd;
	}
	public void setBstkd(String bstkd) {
		this.bstkd = bstkd;
	}
	public String getVbeln() {
		return vbeln;
	}
	public void setVbeln(String vbeln) {
		this.vbeln = vbeln;
	}
	public String getZcamount() {
		return zcamount;
	}
	public void setZcamount(String zcamount) {
		this.zcamount = zcamount;
	}
	public String getZwoamt() {
		return zwoamt;
	}
	public void setZwoamt(String zwoamt) {
		this.zwoamt = zwoamt;
	}
	public String getZrbl() {
		return zrbl;
	}
	public void setZrbl(String zrbl) {
		this.zrbl = zrbl;
	}
	public String getZpodat() {
		return zpodat;
	}
	public void setZpodat(String zpodat) {
		this.zpodat = zpodat;
	}
	public String getZbamt() {
		return zbamt;
	}
	public void setZbamt(String zbamt) {
		this.zbamt = zbamt;
	}
	public String getWaers() {
		return waers;
	}
	public void setWaers(String waers) {
		this.waers = waers;
	}
	public String getKursf() {
		return kursf;
	}
	public void setKursf(String kursf) {
		this.kursf = kursf;
	}
	public String getZanote() {
		return zanote;
	}
	public void setZanote(String zanote) {
		this.zanote = zanote;
	}
	public String getZsmc() {
		return zsmc;
	}
	public void setZsmc(String zsmc) {
		this.zsmc = zsmc;
	}
	public String getZhc() {
		return zhc;
	}
	public void setZhc(String zhc) {
		this.zhc = zhc;
	}
	public String getBelnr() {
		return belnr;
	}
	public void setBelnr(String belnr) {
		this.belnr = belnr;
	}
	public String getUmskz() {
		return umskz;
	}
	public void setUmskz(String umskz) {
		this.umskz = umskz;
	}
	public String getRstgr() {
		return rstgr;
	}
	public void setRstgr(String rstgr) {
		this.rstgr = rstgr;
	}
	public String getZtnum() {
		return ztnum;
	}
	public void setZtnum(String ztnum) {
		this.ztnum = ztnum;
	}
	public String getZdrs() {
		return zdrs;
	}
	public void setZdrs(String zdrs) {
		this.zdrs = zdrs;
	}
	public String getZcirs() {
		return zcirs;
	}
	public void setZcirs(String zcirs) {
		this.zcirs = zcirs;
	}
	public String getZuonr() {
		return zuonr;
	}
	public void setZuonr(String zuonr) {
		this.zuonr = zuonr;
	}
	public String getStblg() {
		return stblg;
	}
	public void setStblg(String stblg) {
		this.stblg = stblg;
	}
	public String getAeamt() {
		return aeamt;
	}
	public void setAeamt(String aeamt) {
		this.aeamt = aeamt;
	}
	public String getMwskz() {
		return mwskz;
	}
	public void setMwskz(String mwskz) {
		this.mwskz = mwskz;
	}
	public String getZposnrSup() {
		return zposnrSup;
	}
	public void setZposnrSup(String zposnrSup) {
		this.zposnrSup = zposnrSup;
	}
	public String getWqamt() {
		return wqamt;
	}
	public void setWqamt(String wqamt) {
		this.wqamt = wqamt;
	}
	public String getSpart() {
		return spart;
	}
	public void setSpart(String spart) {
		this.spart = spart;
	}
	public String getZstblg() {
		return zstblg;
	}
	public void setZstblg(String zstblg) {
		this.zstblg = zstblg;
	}
	public String getZpStblg() {
		return zpStblg;
	}
	public void setZpStblg(String zpStblg) {
		this.zpStblg = zpStblg;
	}
	public String getZwodat() {
		return zwodat;
	}
	public void setZwodat(String zwodat) {
		this.zwodat = zwodat;
	}
	public String getKunnr() {
		return kunnr;
	}
	public void setKunnr(String kunnr) {
		this.kunnr = kunnr;
	}
	public String getSortl() {
		return sortl;
	}
	public void setSortl(String sortl) {
		this.sortl = sortl;
	}
	public String getBukrs() {
		return bukrs;
	}
	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}
	public String getZpeinh() {
		return zpeinh;
	}
	public void setZpeinh(String zpeinh) {
		this.zpeinh = zpeinh;
	}
	public String getBelnr2() {
		return belnr2;
	}
	public void setBelnr2(String belnr2) {
		this.belnr2 = belnr2;
	}
	public String getBelnr3() {
		return belnr3;
	}
	public void setBelnr3(String belnr3) {
		this.belnr3 = belnr3;
	}
}