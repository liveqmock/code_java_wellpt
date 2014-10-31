package com.wellsoft.pt.ldx.model.ficoManage;

import java.io.Serializable;

public class CustomerTable implements Serializable{
	private static final long serialVersionUID = 1L;
	//客户端
	private String mandt; 
	//公司代码
	private String bukrs; 
	//客户编号
	private String kunnr; 
	//客户简称
	private String sortl; 
	//RSM
	private String zrsm;
	//RSM名称
	private String zrsmName;
	//OM
	private String zom;
	//OM名称
	private String zomName;
	//AA
	private String zaa;
	//AA名称
	private String zaaName;
	//AE
	private String zae;
	//AE名称
	private String zaeName;
	//应收人员
	private String zr;
	//应收人员名称
	private String zrName;
	//放宽期限
	private String zday;
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
	public String getZom() {
		return zom;
	}
	public void setZom(String zom) {
		this.zom = zom;
	}
	public String getZaa() {
		return zaa;
	}
	public void setZaa(String zaa) {
		this.zaa = zaa;
	}
	public String getZae() {
		return zae;
	}
	public void setZae(String zae) {
		this.zae = zae;
	}
	public String getZr() {
		return zr;
	}
	public void setZr(String zr) {
		this.zr = zr;
	}
	public String getZday() {
		return zday;
	}
	public void setZday(String zday) {
		this.zday = zday;
	}
	public String getZomName() {
		return zomName;
	}
	public void setZomName(String zomName) {
		this.zomName = zomName;
	}
	public String getZaaName() {
		return zaaName;
	}
	public void setZaaName(String zaaName) {
		this.zaaName = zaaName;
	}
	public String getZaeName() {
		return zaeName;
	}
	public void setZaeName(String zaeName) {
		this.zaeName = zaeName;
	}
	public String getZrName() {
		return zrName;
	}
	public void setZrName(String zrName) {
		this.zrName = zrName;
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
