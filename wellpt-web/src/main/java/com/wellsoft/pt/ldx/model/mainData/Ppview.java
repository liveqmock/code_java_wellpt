package com.wellsoft.pt.ldx.model.mainData;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.wellsoft.pt.core.entity.IdEntity;

@Entity
@Table(name = "ppview")
@DynamicUpdate
@DynamicInsert
public class Ppview extends IdEntity {
	private static final long serialVersionUID = 1L;

	private String factory;

	private String ppbstma;

	private String ppgzjhbzyc;

	private String ppgzjhcswj;

	private String ppgzjhgdyc;

	private String ppgzjhscgly;

	private String ppmrp1control;

	private String ppmrp1group;

	private String ppmrp1pldx;

	private String ppmrp1srz;

	private String ppmrp1type;

	private String ppmrp2aqkc;

	private String ppmrp2bjm;

	private String ppmrp2cglx;

	private String ppmrp2fc;

	private String ppmrp2jhjhsj;

	private String ppmrp2shclsj;

	private String ppmrp2tscgl;

	private String ppmrp2zzscsj;

	private String ppmrp3clz;

	private String ppmrp3kyxjc;

	private String ppmrp3nxxhqj;

	private String ppmrp3xhms;

	private String ppmrp3xqxhqj;

	private String ppmrp4bjfp;

	private String ppmrp4dljz;

	private String scstore;

	private String shortdesc;

	private String wbstore;

	private String wl;
	
	private String status = "0";

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Ppview() {
	}

	public String getFactory() {
		return this.factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getPpbstma() {
		return this.ppbstma;
	}

	public void setPpbstma(String ppbstma) {
		this.ppbstma = ppbstma;
	}

	public String getPpgzjhbzyc() {
		return this.ppgzjhbzyc;
	}

	public void setPpgzjhbzyc(String ppgzjhbzyc) {
		this.ppgzjhbzyc = ppgzjhbzyc;
	}

	public String getPpgzjhcswj() {
		return this.ppgzjhcswj;
	}

	public void setPpgzjhcswj(String ppgzjhcswj) {
		this.ppgzjhcswj = ppgzjhcswj;
	}

	public String getPpgzjhgdyc() {
		return this.ppgzjhgdyc;
	}

	public void setPpgzjhgdyc(String ppgzjhgdyc) {
		this.ppgzjhgdyc = ppgzjhgdyc;
	}

	public String getPpgzjhscgly() {
		return this.ppgzjhscgly;
	}

	public void setPpgzjhscgly(String ppgzjhscgly) {
		this.ppgzjhscgly = ppgzjhscgly;
	}

	public String getPpmrp1control() {
		return this.ppmrp1control;
	}

	public void setPpmrp1control(String ppmrp1control) {
		this.ppmrp1control = ppmrp1control;
	}

	public String getPpmrp1group() {
		return this.ppmrp1group;
	}

	public void setPpmrp1group(String ppmrp1group) {
		this.ppmrp1group = ppmrp1group;
	}

	public String getPpmrp1pldx() {
		return this.ppmrp1pldx;
	}

	public void setPpmrp1pldx(String ppmrp1pldx) {
		this.ppmrp1pldx = ppmrp1pldx;
	}

	public String getPpmrp1srz() {
		return this.ppmrp1srz;
	}

	public void setPpmrp1srz(String ppmrp1srz) {
		this.ppmrp1srz = ppmrp1srz;
	}

	public String getPpmrp1type() {
		return this.ppmrp1type;
	}

	public void setPpmrp1type(String ppmrp1type) {
		this.ppmrp1type = ppmrp1type;
	}

	public String getPpmrp2aqkc() {
		return this.ppmrp2aqkc;
	}

	public void setPpmrp2aqkc(String ppmrp2aqkc) {
		this.ppmrp2aqkc = ppmrp2aqkc;
	}

	public String getPpmrp2bjm() {
		return this.ppmrp2bjm;
	}

	public void setPpmrp2bjm(String ppmrp2bjm) {
		this.ppmrp2bjm = ppmrp2bjm;
	}

	public String getPpmrp2cglx() {
		return this.ppmrp2cglx;
	}

	public void setPpmrp2cglx(String ppmrp2cglx) {
		this.ppmrp2cglx = ppmrp2cglx;
	}

	public String getPpmrp2fc() {
		return this.ppmrp2fc;
	}

	public void setPpmrp2fc(String ppmrp2fc) {
		this.ppmrp2fc = ppmrp2fc;
	}

	public String getPpmrp2jhjhsj() {
		return this.ppmrp2jhjhsj;
	}

	public void setPpmrp2jhjhsj(String ppmrp2jhjhsj) {
		this.ppmrp2jhjhsj = ppmrp2jhjhsj;
	}

	public String getPpmrp2shclsj() {
		return this.ppmrp2shclsj;
	}

	public void setPpmrp2shclsj(String ppmrp2shclsj) {
		this.ppmrp2shclsj = ppmrp2shclsj;
	}

	public String getPpmrp2tscgl() {
		return this.ppmrp2tscgl;
	}

	public void setPpmrp2tscgl(String ppmrp2tscgl) {
		this.ppmrp2tscgl = ppmrp2tscgl;
	}

	public String getPpmrp2zzscsj() {
		return this.ppmrp2zzscsj;
	}

	public void setPpmrp2zzscsj(String ppmrp2zzscsj) {
		this.ppmrp2zzscsj = ppmrp2zzscsj;
	}

	public String getPpmrp3clz() {
		return this.ppmrp3clz;
	}

	public void setPpmrp3clz(String ppmrp3clz) {
		this.ppmrp3clz = ppmrp3clz;
	}

	public String getPpmrp3kyxjc() {
		return this.ppmrp3kyxjc;
	}

	public void setPpmrp3kyxjc(String ppmrp3kyxjc) {
		this.ppmrp3kyxjc = ppmrp3kyxjc;
	}

	public String getPpmrp3nxxhqj() {
		return this.ppmrp3nxxhqj;
	}

	public void setPpmrp3nxxhqj(String ppmrp3nxxhqj) {
		this.ppmrp3nxxhqj = ppmrp3nxxhqj;
	}

	public String getPpmrp3xhms() {
		return this.ppmrp3xhms;
	}

	public void setPpmrp3xhms(String ppmrp3xhms) {
		this.ppmrp3xhms = ppmrp3xhms;
	}

	public String getPpmrp3xqxhqj() {
		return this.ppmrp3xqxhqj;
	}

	public void setPpmrp3xqxhqj(String ppmrp3xqxhqj) {
		this.ppmrp3xqxhqj = ppmrp3xqxhqj;
	}

	public String getPpmrp4bjfp() {
		return this.ppmrp4bjfp;
	}

	public void setPpmrp4bjfp(String ppmrp4bjfp) {
		this.ppmrp4bjfp = ppmrp4bjfp;
	}

	public String getPpmrp4dljz() {
		return this.ppmrp4dljz;
	}

	public void setPpmrp4dljz(String ppmrp4dljz) {
		this.ppmrp4dljz = ppmrp4dljz;
	}

	public String getScstore() {
		return this.scstore;
	}

	public void setScstore(String scstore) {
		this.scstore = scstore;
	}

	public String getShortdesc() {
		return this.shortdesc;
	}

	public void setShortdesc(String shortdesc) {
		this.shortdesc = shortdesc;
	}

	public String getWbstore() {
		return this.wbstore;
	}

	public void setWbstore(String wbstore) {
		this.wbstore = wbstore;
	}

	public String getWl() {
		return this.wl;
	}

	public void setWl(String wl) {
		this.wl = wl;
	}

}