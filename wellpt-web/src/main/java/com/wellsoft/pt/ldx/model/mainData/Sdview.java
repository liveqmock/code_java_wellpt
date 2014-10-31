package com.wellsoft.pt.ldx.model.mainData;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.wellsoft.pt.core.entity.IdEntity;

@Entity
@Table(name = "sdview")
@DynamicUpdate
@DynamicInsert
public class Sdview extends IdEntity {
	private static final long serialVersionUID = 1L;

	private String factory;

	private String sdfxqd;

	private String sdkmszz;

	private String sdkyxjc;

	private String sdsfl;

	private String sdxmlbz;

	private String sdxszz;

	private String sdysz;

	private String sdzzz;

	private String shortdesc;

	private String wl;
	
	private String status = "0";

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Sdview() {
	}

	public String getFactory() {
		return this.factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getSdfxqd() {
		return this.sdfxqd;
	}

	public void setSdfxqd(String sdfxqd) {
		this.sdfxqd = sdfxqd;
	}

	public String getSdkmszz() {
		return this.sdkmszz;
	}

	public void setSdkmszz(String sdkmszz) {
		this.sdkmszz = sdkmszz;
	}

	public String getSdkyxjc() {
		return this.sdkyxjc;
	}

	public void setSdkyxjc(String sdkyxjc) {
		this.sdkyxjc = sdkyxjc;
	}

	public String getSdsfl() {
		return this.sdsfl;
	}

	public void setSdsfl(String sdsfl) {
		this.sdsfl = sdsfl;
	}

	public String getSdxmlbz() {
		return this.sdxmlbz;
	}

	public void setSdxmlbz(String sdxmlbz) {
		this.sdxmlbz = sdxmlbz;
	}

	public String getSdxszz() {
		return this.sdxszz;
	}

	public void setSdxszz(String sdxszz) {
		this.sdxszz = sdxszz;
	}

	public String getSdysz() {
		return this.sdysz;
	}

	public void setSdysz(String sdysz) {
		this.sdysz = sdysz;
	}

	public String getSdzzz() {
		return this.sdzzz;
	}

	public void setSdzzz(String sdzzz) {
		this.sdzzz = sdzzz;
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