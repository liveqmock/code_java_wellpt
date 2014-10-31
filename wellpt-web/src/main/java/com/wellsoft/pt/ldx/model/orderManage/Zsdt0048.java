package com.wellsoft.pt.ldx.model.orderManage;

import java.io.Serializable;

/**
 * 出货信息自建表
 * 
 * @author HeShi
 */
public class Zsdt0048 implements Serializable {
	private static final long serialVersionUID = 1L;
	//客户端
	private String mandt;
	//客户编码
	private String sortl;
	//合同号PI
	private String pi;
	//外向交货单
	private String vbeln;
	//销售订单号
	private String vgbel;
	//装柜编号
	private String zzjbh;
	//预计出货数量
	private String lfimg;
	//OM出货计划提供日期
	private String omchjh;
	//客人确认出货
	private String zdata;
	//信用证船期
	private String zxyzcq;
	//信用证到期日
	private String zdqdata;
	//抵运港口
	private String zdygk;
	//柜型
	private String zgx;
	//运输方式
	private String zysfs;
	//装柜日期
	private String zzgrq;
	//船期
	private String zchq;
	//订单交期
	private String edatu;
	//责任部门
	private String zzrbm;
	//出货异常大类
	private String zchyc;
	//出货异常备注
	private String zycbz;
	//折算后实际出货数量
	private String zchsl;
	//出货状态
	private String zchzt;
	//实际出货数量
	private String sjchsl;
	//验货状态
	private String yhzt;
	//验货结果
	private String zyhjg;
	
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
	public String getPi() {
		return pi;
	}
	public void setPi(String pi) {
		this.pi = pi;
	}
	public String getVbeln() {
		return vbeln;
	}
	public void setVbeln(String vbeln) {
		this.vbeln = vbeln;
	}
	public String getVgbel() {
		return vgbel;
	}
	public void setVgbel(String vgbel) {
		this.vgbel = vgbel;
	}
	public String getZzjbh() {
		return zzjbh;
	}
	public void setZzjbh(String zzjbh) {
		this.zzjbh = zzjbh;
	}
	public String getLfimg() {
		return lfimg;
	}
	public void setLfimg(String lfimg) {
		this.lfimg = lfimg;
	}
	public String getOmchjh() {
		return omchjh;
	}
	public void setOmchjh(String omchjh) {
		this.omchjh = omchjh;
	}
	public String getZdata() {
		return zdata;
	}
	public void setZdata(String zdata) {
		this.zdata = zdata;
	}
	public String getZxyzcq() {
		return zxyzcq;
	}
	public void setZxyzcq(String zxyzcq) {
		this.zxyzcq = zxyzcq;
	}
	public String getZdqdata() {
		return zdqdata;
	}
	public void setZdqdata(String zdqdata) {
		this.zdqdata = zdqdata;
	}
	public String getZdygk() {
		return zdygk;
	}
	public void setZdygk(String zdygk) {
		this.zdygk = zdygk;
	}
	public String getZgx() {
		return zgx;
	}
	public void setZgx(String zgx) {
		this.zgx = zgx;
	}
	public String getZysfs() {
		return zysfs;
	}
	public void setZysfs(String zysfs) {
		this.zysfs = zysfs;
	}
	public String getZzgrq() {
		return zzgrq;
	}
	public void setZzgrq(String zzgrq) {
		this.zzgrq = zzgrq;
	}
	public String getZchq() {
		return zchq;
	}
	public void setZchq(String zchq) {
		this.zchq = zchq;
	}
	public String getEdatu() {
		return edatu;
	}
	public void setEdatu(String edatu) {
		this.edatu = edatu;
	}
	public String getZzrbm() {
		return zzrbm;
	}
	public void setZzrbm(String zzrbm) {
		this.zzrbm = zzrbm;
	}
	public String getZchyc() {
		return zchyc;
	}
	public void setZchyc(String zchyc) {
		this.zchyc = zchyc;
	}
	public String getZycbz() {
		return zycbz;
	}
	public void setZycbz(String zycbz) {
		this.zycbz = zycbz;
	}
	public String getZchsl() {
		return zchsl;
	}
	public void setZchsl(String zchsl) {
		this.zchsl = zchsl;
	}
	public String getZchzt() {
		return zchzt;
	}
	public void setZchzt(String zchzt) {
		this.zchzt = zchzt;
	}
	public String getSjchsl() {
		return sjchsl;
	}
	public void setSjchsl(String sjchsl) {
		this.sjchsl = sjchsl;
	}
	public String getYhzt() {
		return yhzt;
	}
	public void setYhzt(String yhzt) {
		this.yhzt = yhzt;
	}
	public String getZyhjg() {
		return zyhjg;
	}
	public void setZyhjg(String zyhjg) {
		this.zyhjg = zyhjg;
	}
}
