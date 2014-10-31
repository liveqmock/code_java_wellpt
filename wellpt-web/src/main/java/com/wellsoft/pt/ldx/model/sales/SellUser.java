package com.wellsoft.pt.ldx.model.sales;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "USERS")
@DynamicUpdate
@DynamicInsert
public class SellUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32, nullable = true)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;

	@Temporal(TemporalType.DATE)
	private Date auditdate;

	private String auditname;

	private String companyaddr;

	private String companyname;

	@Temporal(TemporalType.DATE)
	private Date createdate;

	private String customernum;

	private BigDecimal discountrate;

	private String email;

	private String fax;

	private String mobile;

	private String pwd;

	private String realname;

	private String regname;

	private String remark;

	private Integer status;

	private String tel;

	private String zterm;
	private String kdgrp;
	private String konda;
	private String bzirk;

	@Transient
	private List<String> messages = new ArrayList<String>();

	public SellUser() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getAuditdate() {
		return auditdate;
	}

	public void setAuditdate(Date auditdate) {
		this.auditdate = auditdate;
	}

	public String getAuditname() {
		return auditname;
	}

	public void setAuditname(String auditname) {
		this.auditname = auditname;
	}

	public String getCompanyaddr() {
		return companyaddr;
	}

	public void setCompanyaddr(String companyaddr) {
		this.companyaddr = companyaddr;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public BigDecimal getDiscountrate() {
		return discountrate;
	}

	public void setDiscountrate(BigDecimal discountrate) {
		this.discountrate = discountrate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getRegname() {
		return regname;
	}

	public void setRegname(String regname) {
		this.regname = regname;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCustomernum() {
		return customernum;
	}

	public void setCustomernum(String customernum) {
		this.customernum = customernum;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public String getZterm() {
		return zterm;
	}

	public void setZterm(String zterm) {
		this.zterm = zterm;
	}

	public String getKdgrp() {
		return kdgrp;
	}

	public void setKdgrp(String kdgrp) {
		this.kdgrp = kdgrp;
	}

	public String getKonda() {
		return konda;
	}

	public void setKonda(String konda) {
		this.konda = konda;
	}

	public String getBzirk() {
		return bzirk;
	}

	public void setBzirk(String bzirk) {
		this.bzirk = bzirk;
	}

	// public List<SellRole> getRoles() {
	// return roles;
	// }
	//
	// public void setRoles(List<SellRole> roles) {
	// this.roles = roles;
	// }

}