package com.wellsoft.pt.ldx.model.orderManage;

import java.io.Serializable;

/**
 * 订单明细自建表
 * 
 * @author HeShi
 */
public class Zsdt0050 implements Serializable {
	private static final long serialVersionUID = 1L;
	//客户端(定时同步字段)
	private String mandt;
	//订单编号(定时同步字段)
	private String vbeln;
	//新单/翻单(定时同步字段)
	private String abrvw;
	//客户简称(定时同步字段)
	private String sortl;
	//产品大类(定时同步字段)
	private String zprodtype;
	//PONO(手工维护)
	private String zpono;
	//合同编号(定时同步字段)
	private String bstkd;
	//合同数量(定时同步字段)
	private String kwmeng;
	//订单签发日期(定时同步字段)
	private String zpsdat3;
	//订单交期(定时同步字段)
	private String edatu;
	//回复客人交期(手工维护)
	private String zhfkr;
	//客人批次号(手工维护)
	private String krpch;
	//各项目对应表提供日期(定时同步字段)
	private String xmdyb;
	//塑件提供日期(手工维护)
	private String zsjtg;
	//包装指导提供日期(手工维护)
	private String zbzzd;
	//预计入库时间(定时同步字段)
	private String zyjrk;
	//验货日期(手工维护)
	private String zyhrq;
	//验货结果(手工维护)
	private String zyhjg;
	//验货备注(手工维护)
	private String zyhbz;
	//最后一次出货日期(定时同步字段)
	private String zhychh;
	//出货尾数(定时同步字段)
	private String zchws;
	//出货尾数备注(手工维护)
	private String chwsbz;
	//尾数预计出货数量(手工维护)
	private String zchsl;
	//尾数预计出货日期(手工维护)
	private String zchrq;
	//报关单(手工维护)
	private String zzbgd;
	//备注(手工维护)
	private String zbz;
	//OM出货计划提供日期(手工维护)
	private String omchjh;
	//折算后合同数量--逻辑尚未确定
	private String zshtsl;
	//折算后出货尾数--逻辑尚未确定
	private String zschws;
	//是否逾期(SAP定时任务更新)
	private String zsfyq;
	//逾期责任部门(手工维护)
	private String zyqbm;
	//逾期异常大类(手工维护)
	private String zycdl;
	//逾期备注(手工维护)
	private String zyqbz;
	//订单状态(SAP定时任务更新)
	private String zddzt;
	//评审状态(SAP定时任务更新)
	private String zddps;
	//加工厂(定时同步字段)
	private String werks;
	//add by HeShi 20130224
	private String zzjq;
	//冲销标识
	private String zcxbs;
	//装柜日期(查询用)
	private String zgrq;
	//船交期(查询用)
	private String cjq;
	/**
	 * 权限控制
	 * @return
	 */
	private boolean admin;
	/**
	 * 用户名
	 */
	private String userName;
	
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
	public String getAbrvw() {
		return abrvw;
	}
	public void setAbrvw(String abrvw) {
		this.abrvw = abrvw;
	}
	public String getSortl() {
		return sortl;
	}
	public void setSortl(String sortl) {
		this.sortl = sortl;
	}
	public String getZprodtype() {
		return zprodtype;
	}
	public void setZprodtype(String zprodtype) {
		this.zprodtype = zprodtype;
	}
	public String getZpono() {
		return zpono;
	}
	public void setZpono(String zpono) {
		this.zpono = zpono;
	}
	public String getBstkd() {
		return bstkd;
	}
	public void setBstkd(String bstkd) {
		this.bstkd = bstkd;
	}
	public String getKwmeng() {
		return kwmeng;
	}
	public void setKwmeng(String kwmeng) {
		this.kwmeng = kwmeng;
	}
	public String getZpsdat3() {
		return zpsdat3;
	}
	public void setZpsdat3(String zpsdat3) {
		this.zpsdat3 = zpsdat3;
	}
	public String getEdatu() {
		return edatu;
	}
	public void setEdatu(String edatu) {
		this.edatu = edatu;
	}
	public String getZhfkr() {
		return zhfkr;
	}
	public void setZhfkr(String zhfkr) {
		this.zhfkr = zhfkr;
	}
	public String getKrpch() {
		return krpch;
	}
	public void setKrpch(String krpch) {
		this.krpch = krpch;
	}
	public String getXmdyb() {
		return xmdyb;
	}
	public void setXmdyb(String xmdyb) {
		this.xmdyb = xmdyb;
	}
	public String getZsjtg() {
		return zsjtg;
	}
	public void setZsjtg(String zsjtg) {
		this.zsjtg = zsjtg;
	}
	public String getZbzzd() {
		return zbzzd;
	}
	public void setZbzzd(String zbzzd) {
		this.zbzzd = zbzzd;
	}
	public String getZyjrk() {
		return zyjrk;
	}
	public void setZyjrk(String zyjrk) {
		this.zyjrk = zyjrk;
	}
	public String getZyhrq() {
		return zyhrq;
	}
	public void setZyhrq(String zyhrq) {
		this.zyhrq = zyhrq;
	}
	public String getZyhjg() {
		return zyhjg;
	}
	public void setZyhjg(String zyhjg) {
		this.zyhjg = zyhjg;
	}
	public String getZyhbz() {
		return zyhbz;
	}
	public void setZyhbz(String zyhbz) {
		this.zyhbz = zyhbz;
	}
	public String getZhychh() {
		return zhychh;
	}
	public void setZhychh(String zhychh) {
		this.zhychh = zhychh;
	}
	public String getZchws() {
		return zchws;
	}
	public void setZchws(String zchws) {
		this.zchws = zchws;
	}
	public String getChwsbz() {
		return chwsbz;
	}
	public void setChwsbz(String chwsbz) {
		this.chwsbz = chwsbz;
	}
	public String getZchsl() {
		return zchsl;
	}
	public void setZchsl(String zchsl) {
		this.zchsl = zchsl;
	}
	public String getZchrq() {
		return zchrq;
	}
	public void setZchrq(String zchrq) {
		this.zchrq = zchrq;
	}
	public String getZzbgd() {
		return zzbgd;
	}
	public void setZzbgd(String zzbgd) {
		this.zzbgd = zzbgd;
	}
	public String getZbz() {
		return zbz;
	}
	public void setZbz(String zbz) {
		this.zbz = zbz;
	}
	public String getOmchjh() {
		return omchjh;
	}
	public void setOmchjh(String omchjh) {
		this.omchjh = omchjh;
	}
	public String getZshtsl() {
		return zshtsl;
	}
	public void setZshtsl(String zshtsl) {
		this.zshtsl = zshtsl;
	}
	public String getZschws() {
		return zschws;
	}
	public void setZschws(String zschws) {
		this.zschws = zschws;
	}
	public String getZsfyq() {
		return zsfyq;
	}
	public void setZsfyq(String zsfyq) {
		this.zsfyq = zsfyq;
	}
	public String getZyqbm() {
		return zyqbm;
	}
	public void setZyqbm(String zyqbm) {
		this.zyqbm = zyqbm;
	}
	public String getZycdl() {
		return zycdl;
	}
	public void setZycdl(String zycdl) {
		this.zycdl = zycdl;
	}
	public String getZyqbz() {
		return zyqbz;
	}
	public void setZyqbz(String zyqbz) {
		this.zyqbz = zyqbz;
	}
	public String getZddzt() {
		return zddzt;
	}
	public void setZddzt(String zddzt) {
		this.zddzt = zddzt;
	}
	public String getWerks() {
		return werks;
	}
	public void setWerks(String werks) {
		this.werks = werks;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getZzjq() {
		return zzjq;
	}
	public void setZzjq(String zzjq) {
		this.zzjq = zzjq;
	}
	public String getZddps() {
		return zddps;
	}
	public void setZddps(String zddps) {
		this.zddps = zddps;
	}
	public String getZcxbs() {
		return zcxbs;
	}
	public void setZcxbs(String zcxbs) {
		this.zcxbs = zcxbs;
	}
	public String getZgrq() {
		return zgrq;
	}
	public void setZgrq(String zgrq) {
		this.zgrq = zgrq;
	}
	public String getCjq() {
		return cjq;
	}
	public void setCjq(String cjq) {
		this.cjq = cjq;
	}
}
