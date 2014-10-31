package com.wellsoft.pt.ldx.model.sales;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class Sellorder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;
	private String cpz;

	@Temporal(TemporalType.DATE)
	private Date createdate;

	private String auditname;
	@Temporal(TemporalType.DATE)
	private Date auditdate;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "customer")
	private SellUser customer;

	private String ddbh;

	private String ddlx;

	private String bstkd;

	private String fkf;

	private String fktj;

	private String fxqd;

	private String hblx;

	@Temporal(TemporalType.DATE)
	private Date jhrq;
	
	@Transient
	private String jhrq1;

	private String khz;

	private String songdf;

	private String soudf;

	private String sszz;

	private Integer status;

	private String remark;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "orderid", referencedColumnName = "id")
	@OrderBy("id")
	private List<Sellorderline> orderlines = new ArrayList<Sellorderline>();

	@Transient
	private String jg;

	@Transient
	private List<String> messages = new ArrayList<String>();

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public Sellorder() {
	}

	public String getCpz() {
		return this.cpz;
	}

	public void setCpz(String cpz) {
		this.cpz = cpz;
	}

	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public SellUser getCustomer() {
		return this.customer;
	}

	public void setCustomer(SellUser customer) {
		this.customer = customer;
	}

	public String getDdbh() {
		return this.ddbh;
	}

	public void setDdbh(String ddbh) {
		this.ddbh = ddbh;
	}

	public String getDdlx() {
		return this.ddlx;
	}

	public void setDdlx(String ddlx) {
		this.ddlx = ddlx;
	}

	public String getFkf() {
		return this.fkf;
	}

	public void setFkf(String fkf) {
		this.fkf = fkf;
	}

	public String getFktj() {
		return this.fktj;
	}

	public void setFktj(String fktj) {
		this.fktj = fktj;
	}

	public String getFxqd() {
		return this.fxqd;
	}

	public void setFxqd(String fxqd) {
		this.fxqd = fxqd;
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

	public String getKhz() {
		return this.khz;
	}

	public void setKhz(String khz) {
		this.khz = khz;
	}

	public String getSongdf() {
		return this.songdf;
	}

	public void setSongdf(String songdf) {
		this.songdf = songdf;
	}

	public String getSoudf() {
		return this.soudf;
	}

	public void setSoudf(String soudf) {
		this.soudf = soudf;
	}

	public String getSszz() {
		return this.sszz;
	}

	public void setSszz(String sszz) {
		this.sszz = sszz;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<Sellorderline> getOrderlines() {
		return orderlines;
	}

	public void setOrderlines(List<Sellorderline> orderlines) {
		this.orderlines = orderlines;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAuditname() {
		return auditname;
	}

	public void setAuditname(String auditname) {
		this.auditname = auditname;
	}

	public Date getAuditdate() {
		return auditdate;
	}

	public void setAuditdate(Date auditdate) {
		this.auditdate = auditdate;
	}

	public String getBstkd() {
		return bstkd;
	}

	public void setBstkd(String bstkd) {
		this.bstkd = bstkd;
	}

	public String getJg() {
		return jg;
	}

	public void setJg(String jg) {
		this.jg = jg;
	}

}