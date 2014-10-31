package com.wellsoft.pt.ldx.model.ficoManage;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 到帐信息表
 * @author HeShi
 */
public class Zfmt0003 implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 客户端
	 */
	private String mandt;
	/**
	 * 公司代码
	 */
	private String bukrs;
	/**
	 * 凭证编号（流水号）
	 */
	private String zbelnr;
	/**
	 * 业务员
	 */
	private String zsname;
	/**
	 * 客户编号
	 */
	private String kunnr;
	/**
	 * 客户简称
	 */
	private String sortl;
	/**
	 * 收款币别
	 */
	private String waers;
	/**
	 * 国际收支申报单号
	 */
	private String zdoip;
	/**
	 * 收款金额
	 */
	private String zcamount;
	/**
	 * 业务对象（摘要）
	 */
	private String sgtxt;
	/**
	 * 预收状态
	 */
	private String zdrs;
	/**
	 * 流转状态
	 */
	private String zcirs;
	/**
	 * 银行类科目
	 */
	private String hkont;
	/**
	 * Email
	 */
	private String mail;
	/**
	 * 提醒
	 */
	private String zremind;
	/**
	 * 最早通知时间
	 */
	private String zetime;
	/**
	 * 是否登记(1是0否)
	 */
	private String zcheck;
	/**
	 * 数据来源(1自动0手动)
	 */
	private String zds;
	/**
	 * 凭证中的凭证日期
	 */
	private String bldat;
	/**
	 * 到款日期（起始值）
	 */
	private String fromBldat = "2014-01-01";
	/**
	 * 到款日期（终止值）
	 */
	private String toBldat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	
	/**
	 * 凭证中的过账日期
	 */
	private String budat;
	/**
	 * 国家汇款人
	 */
	private String zcrem;
	/**
	 * 是否入账(1是0否)
	 */
	private String zpost;
	/**
	 * 回复日期
	 */
	private String zrdate;
	/**
	 * 其他(备注)
	 */
	private String ztext;
	/**
	 * 合同号(非本表字段,查询用)
	 */
	private String bstkd;
	/**
	 * 查询权限标识(非本表字段,查询用)
	 */
	private String auth;
	/**
	 * 到账分解状态(非本表字段,查询用)
	 */
	private String sepstatus;
	/**
	 * zae(非本表字段，查询用)
	 */
	private String zae;
	/**
	 * zaeName(非本表字段，查询用)
	 */
	private String zaeName;
	/**
	 * zaa(非本表字段，查询用)
	 */
	private String zaa;
	/**
	 * zaaName(非本表字段，查询用)
	 */
	private String zaaName;
	/**
	 * zrsm(非本表字段，查询用)
	 */
	private String zrsm;
	/**
	 * zrsmName(非本表字段，查询用)
	 */
	private String zrsmName;
	/**
	 * zrname(非本表字段，查询用)
	 */
	private String zrname;
	/**
	 * zrnameName(非本表字段，查询用)
	 */
	private String zrnameName;
	/**
	 * zrbl(非本表字段，zfmt0004表的字段，仅供查询用)业务类型：'A':'预收','B':'应收','C':'其他应收款','D':'样品款'
	 */
	private String zrbl;
	/**
	 * vbeln(非本表字段，zfmt0004表的字段，仅供查询用)外向交货单号
	 */
	private String vbeln;
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getBukrs() {
		return bukrs;
	}
	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}
	public String getZbelnr() {
		return zbelnr;
	}
	public void setZbelnr(String zbelnr) {
		this.zbelnr = zbelnr;
	}
	public String getZsname() {
		return zsname;
	}
	public void setZsname(String zsname) {
		this.zsname = zsname;
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
	public String getWaers() {
		return waers;
	}
	public void setWaers(String waers) {
		this.waers = waers;
	}
	public String getZcamount() {
		return zcamount;
	}
	public void setZcamount(String zcamount) {
		this.zcamount = zcamount;
	}
	public String getSgtxt() {
		return sgtxt;
	}
	public void setSgtxt(String sgtxt) {
		this.sgtxt = sgtxt;
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
	public String getHkont() {
		return hkont;
	}
	public void setHkont(String hkont) {
		this.hkont = hkont;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getZdoip() {
		return zdoip;
	}
	public void setZdoip(String zdoip) {
		this.zdoip = zdoip;
	}
	public String getZremind() {
		return zremind;
	}
	public void setZremind(String zremind) {
		this.zremind = zremind;
	}
	public String getZetime() {
		return zetime;
	}
	public void setZetime(String zetime) {
		this.zetime = zetime;
	}
	public String getZcheck() {
		return zcheck;
	}
	public void setZcheck(String zcheck) {
		this.zcheck = zcheck;
	}
	public String getZds() {
		return zds;
	}
	public void setZds(String zds) {
		this.zds = zds;
	}
	public String getBldat() {
		return bldat;
	}
	public void setBldat(String bldat) {
		this.bldat = bldat;
	}
	public String getFromBldat() {
		return fromBldat;
	}
	public void setFromBldat(String fromBldat) {
		this.fromBldat = fromBldat;
	}
	public String getToBldat() {
		return toBldat;
	}
	public void setToBldat(String toBldat) {
		this.toBldat = toBldat;
	}
	public String getBudat() {
		return budat;
	}
	public void setBudat(String budat) {
		this.budat = budat;
	}
	public String getZcrem() {
		return zcrem;
	}
	public void setZcrem(String zcrem) {
		this.zcrem = zcrem;
	}
	public String getZpost() {
		return zpost;
	}
	public void setZpost(String zpost) {
		this.zpost = zpost;
	}
	public String getZrdate() {
		return zrdate;
	}
	public void setZrdate(String zrdate) {
		this.zrdate = zrdate;
	}
	public String getZtext() {
		return ztext;
	}
	public void setZtext(String ztext) {
		this.ztext = ztext;
	}
	public String getBstkd() {
		return bstkd;
	}
	public void setBstkd(String bstkd) {
		this.bstkd = bstkd;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public String getSepstatus() {
		return sepstatus;
	}
	public void setSepstatus(String sepstatus) {
		this.sepstatus = sepstatus;
	}
	public String getZae() {
		return zae;
	}
	public void setZae(String zae) {
		this.zae = zae;
	}
	public String getZaeName() {
		return zaeName;
	}
	public void setZaeName(String zaeName) {
		this.zaeName = zaeName;
	}
	public String getZrname() {
		return zrname;
	}
	public void setZrname(String zrname) {
		this.zrname = zrname;
	}
	public String getZrnameName() {
		return zrnameName;
	}
	public void setZrnameName(String zrnameName) {
		this.zrnameName = zrnameName;
	}
	public String getZrbl() {
		return zrbl;
	}
	public void setZrbl(String zrbl) {
		this.zrbl = zrbl;
	}
	public String getVbeln() {
		return vbeln;
	}
	public void setVbeln(String vbeln) {
		this.vbeln = vbeln;
	}
	public String getZaa() {
		return zaa;
	}
	public void setZaa(String zaa) {
		this.zaa = zaa;
	}
	public String getZaaName() {
		return zaaName;
	}
	public void setZaaName(String zaaName) {
		this.zaaName = zaaName;
	}
	public String getZrsm() {
		return zrsm;
	}
	public void setZrsm(String zrsm) {
		this.zrsm = zrsm;
	}
	public String getZrsmName() {
		return zrsmName;
	}
	public void setZrsmName(String zrsmName) {
		this.zrsmName = zrsmName;
	}
	
}