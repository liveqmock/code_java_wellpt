package com.wellsoft.pt.ldx.model.mainData;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.wellsoft.pt.core.entity.IdEntity;

@Entity
@Table(name = "mmview")
@DynamicUpdate
@DynamicInsert
public class Mmview extends IdEntity {
	private static final long serialVersionUID = 1L;

	private String factory;

	private String mmcgz;

	private String mmddwb;

	private String mmhyqd;

	private String mmpegl;

	private String ppmrp2jhjhsj;

	private String shortdesc;

	private String wl;
	
	private String status = "0";

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Mmview() {
	}

	public String getFactory() {
		return this.factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getMmcgz() {
		return this.mmcgz;
	}

	public void setMmcgz(String mmcgz) {
		this.mmcgz = mmcgz;
	}

	public String getMmddwb() {
		return this.mmddwb;
	}

	public void setMmddwb(String mmddwb) {
		this.mmddwb = mmddwb;
	}

	public String getMmhyqd() {
		return this.mmhyqd;
	}

	public void setMmhyqd(String mmhyqd) {
		this.mmhyqd = mmhyqd;
	}

	public String getMmpegl() {
		return this.mmpegl;
	}

	public void setMmpegl(String mmpegl) {
		this.mmpegl = mmpegl;
	}

	public String getPpmrp2jhjhsj() {
		return this.ppmrp2jhjhsj;
	}

	public void setPpmrp2jhjhsj(String ppmrp2jhjhsj) {
		this.ppmrp2jhjhsj = ppmrp2jhjhsj;
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