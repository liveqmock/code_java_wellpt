package com.wellsoft.pt.ldx.model.sales;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class Sellorderline implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;
	@Temporal(TemporalType.DATE)
	private Date createdate;

	private BigDecimal dj;

	private String hblx;

	private Integer jgdw;

	private String jhgc;

	@Temporal(TemporalType.DATE)
	private Date jhrq;
	
	@Transient
	private String jhrq1;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "product")
	private DmsProduct product;

	private Integer sl;

	private Integer status;

	private String tjlx;

	private String xmlx;

	public Sellorderline() {
	}

	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public BigDecimal getDj() {
		return this.dj;
	}

	public void setDj(BigDecimal dj) {
		this.dj = dj;
	}

	public String getHblx() {
		return this.hblx;
	}

	public void setHblx(String hblx) {
		this.hblx = hblx;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getJgdw() {
		return this.jgdw;
	}

	public void setJgdw(Integer jgdw) {
		this.jgdw = jgdw;
	}

	public String getJhgc() {
		return this.jhgc;
	}

	public void setJhgc(String jhgc) {
		this.jhgc = jhgc;
	}

	public Date getJhrq() {
		return this.jhrq;
	}

	public void setJhrq(Date jhrq) {
		this.jhrq = jhrq;
	}

	public String getJhrq1() {
		return jhrq1;
	}

	public void setJhrq1(String jhrq1) {
		this.jhrq1 = jhrq1;
	}

	public DmsProduct getProduct() {
		return this.product;
	}

	public void setProduct(DmsProduct product) {
		this.product = product;
	}

	public Integer getSl() {
		return this.sl;
	}

	public void setSl(Integer sl) {
		this.sl = sl;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTjlx() {
		return this.tjlx;
	}

	public void setTjlx(String tjlx) {
		this.tjlx = tjlx;
	}

	public String getXmlx() {
		return this.xmlx;
	}

	public void setXmlx(String xmlx) {
		this.xmlx = xmlx;
	}

}