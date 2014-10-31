package com.wellsoft.pt.ldx.model.mainData;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.wellsoft.pt.core.entity.IdEntity;

@Entity
@Table(name = "planview")
@DynamicUpdate
@DynamicInsert
public class PlanView extends IdEntity {
	private static final long serialVersionUID = 1L;

	private String factory;

	private String ficokj1jgdw;

	private String ppmrp1srz;

	private String pricejhjg;

	private String pricejhjgrq;

	private String scstore;

	private String shortdesc;

	private String wbstore;

	private String ppmrp2cglx;

	private String wl;
	
	private String status = "0";

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public PlanView() {
	}

	public String getFactory() {
		return this.factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getFicokj1jgdw() {
		return this.ficokj1jgdw;
	}

	public void setFicokj1jgdw(String ficokj1jgdw) {
		this.ficokj1jgdw = ficokj1jgdw;
	}

	public String getPpmrp1srz() {
		return this.ppmrp1srz;
	}

	public void setPpmrp1srz(String ppmrp1srz) {
		this.ppmrp1srz = ppmrp1srz;
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

	public String getPpmrp2cglx() {
		return ppmrp2cglx;
	}

	public void setPpmrp2cglx(String ppmrp2cglx) {
		this.ppmrp2cglx = ppmrp2cglx;
	}

}