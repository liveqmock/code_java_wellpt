package com.wellsoft.pt.ldx.model.mainData;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.wellsoft.pt.core.entity.IdEntity;

@Entity
@Table(name = "BAOGUANVIEW")
@DynamicUpdate
@DynamicInsert
public class Baoguanview extends IdEntity {
	private static final long serialVersionUID = 1L;

	private String cnname;

	private String enname;

	private String shortdesc;

	private String status = "0";

	private String wl;

	public Baoguanview() {
	}

	public String getCnname() {
		return this.cnname;
	}

	public void setCnname(String cnname) {
		this.cnname = cnname;
	}

	public String getEnname() {
		return this.enname;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}

	public String getShortdesc() {
		return this.shortdesc;
	}

	public void setShortdesc(String shortdesc) {
		this.shortdesc = shortdesc;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWl() {
		return this.wl;
	}

	public void setWl(String wl) {
		this.wl = wl;
	}

}