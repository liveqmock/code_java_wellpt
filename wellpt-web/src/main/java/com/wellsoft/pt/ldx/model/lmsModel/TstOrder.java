package com.wellsoft.pt.ldx.model.lmsModel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "TSTORDER")
@DynamicUpdate
@DynamicInsert
public class TstOrder implements Serializable{
	public TstOrder(){
		
	}
	/**
	 * 
	 */
	@Id
	@Column(name="tstorder_no")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String tstorder_no;
	/**
	 * 
	 */
	@Column(name="tstorder_date")
	private String tstorder_date;
	/**
	 * 
	 */
	@Column(name="tstorder_depno")
	private String tstorder_depno;
	/**
	 * 
	 */
	@Column(name="tstorder_wgno")
	private String tstorder_wgno;
	/**
	 * 
	 */
	@Column(name="tstorder_wgnm")
	private String tstorder_wgnm;
	/**
	 * 
	 */
	@Column(name="tstorder_orderno")
	private String tstorder_orderno;
	/**
	 * 
	 */
	@Column(name="tstorder_planno")
	private String tstorder_planno;
	/**
	 * 
	 */
	@Column(name="tstorder_cstno")
	private String tstorder_cstno;
	/**
	 * 
	 */
	@Column(name="tstorder_pur")
	private Integer tstorder_pur;
	/**
	 * 
	 */
	@Column(name="tstorder_fittypeid")
	private Integer tstorder_fittypeid;
	/**
	 * 
	 */
	@Column(name="tstorder_sdepno")
	private String tstorder_sdepno;
	/**
	 * 
	 */
	@Column(name="tstorder_cipusrno")
	private String tstorder_cipusrno;
	/**
	 * 
	 */
	@Column(name="tstorder_endusrno")
	private String tstorder_endusrno;
	/**
	 * 
	 */
	@Column(name="tstorder_stausrno")
	private String tstorder_stausrno;
	/**
	 * 
	 */
	@Column(name="tstorder_extcstno")
	private String tstorder_extcstno;
	/**
	 * 
	 */
	@Column(name="tstorder_out")
	private Integer tstorder_out=0;
	/**
	 * 
	 */
	@Column(name="tstorder_state")
	private Integer tstorder_state;
	/**
	 * 
	 */
	@Column(name="tstorder_usr")
	private String tstorder_usr;
	/**
	 * 
	 */
	@Column(name="tstorder_recdate")
	private String tstorder_recdate;
	/**
	 * 
	 */
	@Column(name="tstorder_sam")
	private Integer tstorder_sam;
	/**
	 * 
	 */
	@Column(name="tstorder_clstype")
	private Integer tstorder_clstype;
	/**
	 * 
	 */
	@Column(name="tstorder_revno")
	private String tstorder_revno;
	/**
	 * 
	 */
	@Column(name="tstorder_rem")
	private String tstorder_rem;
	/**
	 * 
	 */
	@Column(name="tstorder_addusr")
	private String tstorder_addusr;
	/**
	 * 
	 */
	@Column(name="tstorder_adddate")
	private String tstorder_adddate;
	/**
	 * 
	 */
	@Column(name="tstorder_gdusr")
	private String tstorder_gdusr;
	/**
	 * 
	 */
	@Column(name="tstorder_gddate")
	private String tstorder_gddate;
	/**
	 * 
	 */
	@Column(name="tstorder_stopid")
	private Integer tstorder_stopid;
	/**
	 * 
	 */
	@Column(name="tstorder_standardno")
	private String tstorder_standardno;
	/**
	 * 
	 */
	@Column(name="tstorder_outto")
	private Integer tstorder_outto;
	/**
	 * 
	 */
	@Column(name="tstorder_complete")
	private Integer tstorder_complete;
	/**
	 * 
	 */
	@Column(name="tstorder_ng")
	private Integer tstorder_ng;
	/**
	 * 
	 */
	@Column(name="tstorder_amt")
	private Integer tstorder_amt;
	/**
	 * 
	 */
	@Column(name="tstorder_changed")
	private Integer tstorder_changed;
	/**
	 * 
	 */
	@Column(name="tstorder_costno")
	private String tstorder_costno;
	/**
	 * 
	 */
	@Column(name="tstorder_rptcls")
	private Integer tstorder_rptcls;
	/**
	 * 
	 */
	@Column(name="tstorder_rptlgn")
	private Integer tstorder_rptlgn;
	/**
	 * 
	 */
	@Column(name="tstorder_rptsend")
	private Integer tstorder_rptsend;
	/**
	 * 
	 */
	@Column(name="tstorder_productcls")
	private Integer tstorder_productcls;
	/**
	 * 
	 */
	@Column(name="tstorder_factory")
	private String tstorder_factory;
	/**
	 * 
	 */
	@Column(name="tstorder_mdate")
	private String tstorder_mdate;
	public String getTstorder_no() {
		return tstorder_no;
	}
	public void setTstorder_no(String tstorder_no) {
		this.tstorder_no = tstorder_no;
	}
	public String getTstorder_date() {
		return tstorder_date;
	}
	public void setTstorder_date(String tstorder_date) {
		this.tstorder_date = tstorder_date;
	}
	public String getTstorder_depno() {
		return tstorder_depno;
	}
	public void setTstorder_depno(String tstorder_depno) {
		this.tstorder_depno = tstorder_depno;
	}
	public String getTstorder_wgno() {
		return tstorder_wgno;
	}
	public void setTstorder_wgno(String tstorder_wgno) {
		this.tstorder_wgno = tstorder_wgno;
	}
	public String getTstorder_wgnm() {
		return tstorder_wgnm;
	}
	public void setTstorder_wgnm(String tstorder_wgnm) {
		this.tstorder_wgnm = tstorder_wgnm;
	}
	public String getTstorder_orderno() {
		return tstorder_orderno;
	}
	public void setTstorder_orderno(String tstorder_orderno) {
		this.tstorder_orderno = tstorder_orderno;
	}
	public String getTstorder_planno() {
		return tstorder_planno;
	}
	public void setTstorder_planno(String tstorder_planno) {
		this.tstorder_planno = tstorder_planno;
	}
	public String getTstorder_cstno() {
		return tstorder_cstno;
	}
	public void setTstorder_cstno(String tstorder_cstno) {
		this.tstorder_cstno = tstorder_cstno;
	}
	public Integer getTstorder_pur() {
		return tstorder_pur;
	}
	public void setTstorder_pur(Integer tstorder_pur) {
		this.tstorder_pur = tstorder_pur;
	}
	public Integer getTstorder_fittypeid() {
		return tstorder_fittypeid;
	}
	public void setTstorder_fittypeid(Integer tstorder_fittypeid) {
		this.tstorder_fittypeid = tstorder_fittypeid;
	}
	public String getTstorder_sdepno() {
		return tstorder_sdepno;
	}
	public void setTstorder_sdepno(String tstorder_sdepno) {
		this.tstorder_sdepno = tstorder_sdepno;
	}
	public String getTstorder_cipusrno() {
		return tstorder_cipusrno;
	}
	public void setTstorder_cipusrno(String tstorder_cipusrno) {
		this.tstorder_cipusrno = tstorder_cipusrno;
	}
	public String getTstorder_endusrno() {
		return tstorder_endusrno;
	}
	public void setTstorder_endusrno(String tstorder_endusrno) {
		this.tstorder_endusrno = tstorder_endusrno;
	}
	public String getTstorder_stausrno() {
		return tstorder_stausrno;
	}
	public void setTstorder_stausrno(String tstorder_stausrno) {
		this.tstorder_stausrno = tstorder_stausrno;
	}
	public String getTstorder_extcstno() {
		return tstorder_extcstno;
	}
	public void setTstorder_extcstno(String tstorder_extcstno) {
		this.tstorder_extcstno = tstorder_extcstno;
	}
	public Integer getTstorder_out() {
		return tstorder_out;
	}
	public void setTstorder_out(Integer tstorder_out) {
		this.tstorder_out = tstorder_out;
	}
	public Integer getTstorder_state() {
		return tstorder_state;
	}
	public void setTstorder_state(Integer tstorder_state) {
		this.tstorder_state = tstorder_state;
	}
	public String getTstorder_usr() {
		return tstorder_usr;
	}
	public void setTstorder_usr(String tstorder_usr) {
		this.tstorder_usr = tstorder_usr;
	}
	public String getTstorder_recdate() {
		return tstorder_recdate;
	}
	public void setTstorder_recdate(String tstorder_recdate) {
		this.tstorder_recdate = tstorder_recdate;
	}
	public Integer getTstorder_sam() {
		return tstorder_sam;
	}
	public void setTstorder_sam(Integer tstorder_sam) {
		this.tstorder_sam = tstorder_sam;
	}
	public Integer getTstorder_clstype() {
		return tstorder_clstype;
	}
	public void setTstorder_clstype(Integer tstorder_clstype) {
		this.tstorder_clstype = tstorder_clstype;
	}
	public String getTstorder_revno() {
		return tstorder_revno;
	}
	public void setTstorder_revno(String tstorder_revno) {
		this.tstorder_revno = tstorder_revno;
	}
	public String getTstorder_rem() {
		return tstorder_rem;
	}
	public void setTstorder_rem(String tstorder_rem) {
		this.tstorder_rem = tstorder_rem;
	}
	public String getTstorder_addusr() {
		return tstorder_addusr;
	}
	public void setTstorder_addusr(String tstorder_addusr) {
		this.tstorder_addusr = tstorder_addusr;
	}
	public String getTstorder_adddate() {
		return tstorder_adddate;
	}
	public void setTstorder_adddate(String tstorder_adddate) {
		this.tstorder_adddate = tstorder_adddate;
	}
	public String getTstorder_gdusr() {
		return tstorder_gdusr;
	}
	public void setTstorder_gdusr(String tstorder_gdusr) {
		this.tstorder_gdusr = tstorder_gdusr;
	}
	public String getTstorder_gddate() {
		return tstorder_gddate;
	}
	public void setTstorder_gddate(String tstorder_gddate) {
		this.tstorder_gddate = tstorder_gddate;
	}
	public Integer getTstorder_stopid() {
		return tstorder_stopid;
	}
	public void setTstorder_stopid(Integer tstorder_stopid) {
		this.tstorder_stopid = tstorder_stopid;
	}
	public String getTstorder_standardno() {
		return tstorder_standardno;
	}
	public void setTstorder_standardno(String tstorder_standardno) {
		this.tstorder_standardno = tstorder_standardno;
	}
	public Integer getTstorder_outto() {
		return tstorder_outto;
	}
	public void setTstorder_outto(Integer tstorder_outto) {
		this.tstorder_outto = tstorder_outto;
	}
	public Integer getTstorder_complete() {
		return tstorder_complete;
	}
	public void setTstorder_complete(Integer tstorder_complete) {
		this.tstorder_complete = tstorder_complete;
	}
	public Integer getTstorder_ng() {
		return tstorder_ng;
	}
	public void setTstorder_ng(Integer tstorder_ng) {
		this.tstorder_ng = tstorder_ng;
	}
	public Integer getTstorder_amt() {
		return tstorder_amt;
	}
	public void setTstorder_amt(Integer tstorder_amt) {
		this.tstorder_amt = tstorder_amt;
	}
	public Integer getTstorder_changed() {
		return tstorder_changed;
	}
	public void setTstorder_changed(Integer tstorder_changed) {
		this.tstorder_changed = tstorder_changed;
	}
	public String getTstorder_costno() {
		return tstorder_costno;
	}
	public void setTstorder_costno(String tstorder_costno) {
		this.tstorder_costno = tstorder_costno;
	}
	public Integer getTstorder_rptcls() {
		return tstorder_rptcls;
	}
	public void setTstorder_rptcls(Integer tstorder_rptcls) {
		this.tstorder_rptcls = tstorder_rptcls;
	}
	public Integer getTstorder_rptlgn() {
		return tstorder_rptlgn;
	}
	public void setTstorder_rptlgn(Integer tstorder_rptlgn) {
		this.tstorder_rptlgn = tstorder_rptlgn;
	}
	public Integer getTstorder_rptsend() {
		return tstorder_rptsend;
	}
	public void setTstorder_rptsend(Integer tstorder_rptsend) {
		this.tstorder_rptsend = tstorder_rptsend;
	}
	public Integer getTstorder_productcls() {
		return tstorder_productcls;
	}
	public void setTstorder_productcls(Integer tstorder_productcls) {
		this.tstorder_productcls = tstorder_productcls;
	}
	public String getTstorder_factory() {
		return tstorder_factory;
	}
	public void setTstorder_factory(String tstorder_factory) {
		this.tstorder_factory = tstorder_factory;
	}
	public String getTstorder_mdate() {
		return tstorder_mdate;
	}
	public void setTstorder_mdate(String tstorder_mdate) {
		this.tstorder_mdate = tstorder_mdate;
	}
}
