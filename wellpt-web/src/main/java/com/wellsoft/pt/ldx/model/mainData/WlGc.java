package com.wellsoft.pt.ldx.model.mainData;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.wellsoft.pt.core.entity.IdEntity;

@Entity
@Table(name = "wlgc")
@DynamicUpdate
@DynamicInsert
public class WlGc extends IdEntity {

	private static final long serialVersionUID = 1L;

	private String wl;

	private String factory;

	private String scstore;

	private String wbstore;
	
	private String shortdesc;
	

	public WlGc() {
	}

	@Column(name = "wl")
	public String getWl() {
		return wl;
	}

	public void setWl(String wl) {
		this.wl = wl;
	}

	@Column(name = "factory")
	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	@Column(name = "scstore")
	public String getScstore() {
		return scstore;
	}

	public void setScstore(String scstore) {
		this.scstore = scstore;
	}

	@Column(name = "wbstore")
	public String getWbstore() {
		return wbstore;
	}

	public void setWbstore(String wbstore) {
		this.wbstore = wbstore;
	}

	@Column(name = "shortdesc")
	public String getShortdesc() {
		return shortdesc;
	}

	public void setShortdesc(String shortdesc) {
		this.shortdesc = shortdesc;
	}

}
