package com.wellsoft.pt.ldx.model.mainData;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.wellsoft.pt.core.entity.IdEntity;

@Entity
@Table(name = "ficoview")
@DynamicUpdate
@DynamicInsert
public class Ficoview extends IdEntity {
	private static final long serialVersionUID = 1L;

	private String factory;

	private String ficocb1cbhspl;

	private String ficocb1lrzx;

	private String ficocb1qs;

	private String ficocb1wcbgs;

	private String ficocb1wlly;

	private String ficocb1ysz;

	private String ficokj1bzjg;

	private String ficokj1jgdw;

	private String ficokj1jgkz;

	private String ficokj1jgqd;

	private String ficokj1pgl;

	private String ficokj1zqdwjg;

	private String ppmrp2cglx;

	private String pricejhjg;

	private String pricejhjgrq;

	private String sdkmszz;

	private String shortdesc;

	private String wl;

	private String status = "0";

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Ficoview() {
	}

	public String getFactory() {
		return this.factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getFicocb1cbhspl() {
		return this.ficocb1cbhspl;
	}

	public void setFicocb1cbhspl(String ficocb1cbhspl) {
		this.ficocb1cbhspl = ficocb1cbhspl;
	}

	public String getFicocb1lrzx() {
		return this.ficocb1lrzx;
	}

	public void setFicocb1lrzx(String ficocb1lrzx) {
		this.ficocb1lrzx = ficocb1lrzx;
	}

	public String getFicocb1qs() {
		return this.ficocb1qs;
	}

	public void setFicocb1qs(String ficocb1qs) {
		this.ficocb1qs = ficocb1qs;
	}

	public String getFicocb1wcbgs() {
		return this.ficocb1wcbgs;
	}

	public void setFicocb1wcbgs(String ficocb1wcbgs) {
		this.ficocb1wcbgs = ficocb1wcbgs;
	}

	public String getFicocb1wlly() {
		return this.ficocb1wlly;
	}

	public void setFicocb1wlly(String ficocb1wlly) {
		this.ficocb1wlly = ficocb1wlly;
	}

	public String getFicocb1ysz() {
		return this.ficocb1ysz;
	}

	public void setFicocb1ysz(String ficocb1ysz) {
		this.ficocb1ysz = ficocb1ysz;
	}

	public String getFicokj1bzjg() {
		return this.ficokj1bzjg;
	}

	public void setFicokj1bzjg(String ficokj1bzjg) {
		this.ficokj1bzjg = ficokj1bzjg;
	}

	public String getFicokj1jgdw() {
		return this.ficokj1jgdw;
	}

	public void setFicokj1jgdw(String ficokj1jgdw) {
		this.ficokj1jgdw = ficokj1jgdw;
	}

	public String getFicokj1jgkz() {
		return this.ficokj1jgkz;
	}

	public void setFicokj1jgkz(String ficokj1jgkz) {
		this.ficokj1jgkz = ficokj1jgkz;
	}

	public String getFicokj1jgqd() {
		return this.ficokj1jgqd;
	}

	public void setFicokj1jgqd(String ficokj1jgqd) {
		this.ficokj1jgqd = ficokj1jgqd;
	}

	public String getFicokj1pgl() {
		return this.ficokj1pgl;
	}

	public void setFicokj1pgl(String ficokj1pgl) {
		this.ficokj1pgl = ficokj1pgl;
	}

	public String getFicokj1zqdwjg() {
		return this.ficokj1zqdwjg;
	}

	public void setFicokj1zqdwjg(String ficokj1zqdwjg) {
		this.ficokj1zqdwjg = ficokj1zqdwjg;
	}

	public String getPpmrp2cglx() {
		return this.ppmrp2cglx;
	}

	public void setPpmrp2cglx(String ppmrp2cglx) {
		this.ppmrp2cglx = ppmrp2cglx;
	}

	public String getPricejhjg() {
		return this.pricejhjg;
	}

	public void setPricejhjg(String pricejhjg) {
		this.pricejhjg = pricejhjg;
	}

	public String getPricejhjgrq() {
		return this.pricejhjgrq;
	}

	public void setPricejhjgrq(String pricejhjgrq) {
		this.pricejhjgrq = pricejhjgrq;
	}

	public String getSdkmszz() {
		return this.sdkmszz;
	}

	public void setSdkmszz(String sdkmszz) {
		this.sdkmszz = sdkmszz;
	}

	public String getShortdesc() {
		return this.shortdesc;
	}

	public void setShortdesc(String shortdesc) {
		this.shortdesc = shortdesc;
	}

	public String getWl() {
		return this.wl;
	}

	public void setWl(String wl) {
		this.wl = wl;
	}

}