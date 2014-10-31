package com.wellsoft.pt.ldx.model.sales;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "PRODUCT")
@DynamicUpdate
@DynamicInsert
public class DmsProduct implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private String matnr;
	private String stext;
	private String maktx;
	private String name;
	private String remark;
	private String unit;
	private Integer unitcount;
	private Integer status;

	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

	public String getStext() {
		return stext;
	}

	public void setStext(String stext) {
		this.stext = stext;
	}

	public String getMaktx() {
		return maktx;
	}

	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getUnitcount() {
		return unitcount;
	}

	public void setUnitcount(Integer unitcount) {
		this.unitcount = unitcount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
