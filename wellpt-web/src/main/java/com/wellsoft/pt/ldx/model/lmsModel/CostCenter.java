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
@Table(name = "COSTCENTER")
@DynamicUpdate
@DynamicInsert
public class CostCenter  implements Serializable{
	public CostCenter(){
		
	}
	/**
	 * ID（编号）
	 */
	@Id
	@Column(name="costcenter_no")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String costcenter_no;
	/**
	 * 成本名称
	 */
	@Column(name="costcenter_name")
	private String costcenter_name;
	/**
	 * 所在父级
	 */
	@Column(name="costcenter_parid")
	private String costcenter_parid;
	/**
	 * 排序
	 */
	@Column(name="costcenter_order")
	private String costcenter_order;
	/**
	 * 录入日期
	 */
	@Column(name="costcenter_adddate")
	private String costcenter_adddate;
	/**
	 * 录入人员
	 */
	@Column(name="costcenter_addusr")
	private String costcenter_addusr;
	/**
	 * 修改日期
	 */
	@Column(name="costcenter_moddate")
	private String costcenter_moddate;
	/**
	 * 修改人员
	 */
	@Column(name="costcenter_modusr")
	private String costcenter_modusr;
	/**
	 * 部门连续名称
	 */
	@Column(name="costcenter_caption")
	private String costcenter_caption;
	/**
	 * 成本代码
	 */
	@Column(name="costcenter_cbno")
	private String costcenter_cbno;
	/**
	 * '用于标识是成本代码还是公司代码
	 */
	@Column(name="costcenter_tag")
	private String costcenter_tag;
	/**
	 * 成本中心负责人
	 */
	@Column(name="costcenter_usr")
	private String costcenter_usr;
	/**
	 * 描述
	 */
	@Column(name="costcenter_rem")
	private String costcenter_rem;
	/**
	 * 生效起始日期
	 */
	@Column(name="costcenter_stadate")
	private String costcenter_stadate;
	/**
	 * 生效截止日期
	 */
	@Column(name="costcenter_enddate")
	private String costcenter_enddate;
	public String getCostcenter_no() {
		return costcenter_no;
	}
	public void setCostcenter_no(String costcenter_no) {
		this.costcenter_no = costcenter_no;
	}
	public String getCostcenter_name() {
		return costcenter_name;
	}
	public void setCostcenter_name(String costcenter_name) {
		this.costcenter_name = costcenter_name;
	}
	public String getCostcenter_parid() {
		return costcenter_parid;
	}
	public void setCostcenter_parid(String costcenter_parid) {
		this.costcenter_parid = costcenter_parid;
	}
	public String getCostcenter_order() {
		return costcenter_order;
	}
	public void setCostcenter_order(String costcenter_order) {
		this.costcenter_order = costcenter_order;
	}
	public String getCostcenter_adddate() {
		return costcenter_adddate;
	}
	public void setCostcenter_adddate(String costcenter_adddate) {
		this.costcenter_adddate = costcenter_adddate;
	}
	public String getCostcenter_addusr() {
		return costcenter_addusr;
	}
	public void setCostcenter_addusr(String costcenter_addusr) {
		this.costcenter_addusr = costcenter_addusr;
	}
	public String getCostcenter_moddate() {
		return costcenter_moddate;
	}
	public void setCostcenter_moddate(String costcenter_moddate) {
		this.costcenter_moddate = costcenter_moddate;
	}
	public String getCostcenter_modusr() {
		return costcenter_modusr;
	}
	public void setCostcenter_modusr(String costcenter_modusr) {
		this.costcenter_modusr = costcenter_modusr;
	}
	public String getCostcenter_caption() {
		return costcenter_caption;
	}
	public void setCostcenter_caption(String costcenter_caption) {
		this.costcenter_caption = costcenter_caption;
	}
	public String getCostcenter_cbno() {
		return costcenter_cbno;
	}
	public void setCostcenter_cbno(String costcenter_cbno) {
		this.costcenter_cbno = costcenter_cbno;
	}
	public String getCostcenter_tag() {
		return costcenter_tag;
	}
	public void setCostcenter_tag(String costcenter_tag) {
		this.costcenter_tag = costcenter_tag;
	}
	public String getCostcenter_usr() {
		return costcenter_usr;
	}
	public void setCostcenter_usr(String costcenter_usr) {
		this.costcenter_usr = costcenter_usr;
	}
	public String getCostcenter_rem() {
		return costcenter_rem;
	}
	public void setCostcenter_rem(String costcenter_rem) {
		this.costcenter_rem = costcenter_rem;
	}
	public String getCostcenter_stadate() {
		return costcenter_stadate;
	}
	public void setCostcenter_stadate(String costcenter_stadate) {
		this.costcenter_stadate = costcenter_stadate;
	}
	public String getCostcenter_enddate() {
		return costcenter_enddate;
	}
	public void setCostcenter_enddate(String costcenter_enddate) {
		this.costcenter_enddate = costcenter_enddate;
	}
}
