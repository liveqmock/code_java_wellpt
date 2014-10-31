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
@Table(name = "TSTTYPE")
@DynamicUpdate
@DynamicInsert
public class TstType implements Serializable{
	public TstType(){
		
	}
	/**
	 *ID 
	 */
	@Id
	@Column(name="tsttype_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer tsttype_id;
	/**
	 * 类别名称
	 */
	@Column(name="tsttype_name")
	private String tsttype_name;
	/**
	 * 父级ID
	 */
	@Column(name="tsttype_parid")
	private Integer tsttype_parid;
	/**
	 * 排序
	 */
	@Column(name="tsttype_seq")
	private Integer tsttype_seq;
	/**
	 * 当前状态(0=启用,1=停用)
	 */
	@Column(name="tsttype_state")
	private Integer tsttype_state;
	/**
	 * 是否点灯(0=灯架灯点,1=不点灯,2=其它设备点灯
	 */
	@Column(name="tsttype_light")
	private Integer tsttype_light;
	/**
	 * 所属实验室
	 */
	@Column(name="tsttype_laboratoryid")
	private String tsttype_laboratoryid;
	/**
	 * 所属实验室名称
	 */
	@Column(name="tsttype_laboratorynm")
	private String tsttype_laboratorynm;
	/**
	 * 测试类别模式
	 */
	@Column(name="tsttype_cls")
	private Integer tsttype_cls;
	/**
	 * 英文名称
	 */
	@Column(name="tsttype_enname")
	private String tsttype_enname;
	/**
	 * 计算单位
	 */
	@Column(name="tsttype_computerunitid")
	private Integer tsttype_computerunitid;
	/**
	 * 折算标准
	 */
	@Column(name="tsttype_discount")
	private Integer tsttype_discount;
	/**
	 * 所属大类(E=EMC,P=性能,R=RoHS,S=安规)
	 */
	@Column(name="tsttype_bigcls")
	private String tsttype_bigcls;
	/**
	 * 单价
	 */
	@Column(name="tsttype_price")
	private Integer tsttype_price;
	/**
	 * 所属小类(目前就安规大类中有小类，G=固定式灯具)
	 */
	@Column(name="tsttype_smallcls")
	private String tsttype_smallcls;
	public Integer getTsttype_id() {
		return tsttype_id;
	}
	public void setTsttype_id(Integer tsttype_id) {
		this.tsttype_id = tsttype_id;
	}
	public String getTsttype_name() {
		return tsttype_name;
	}
	public void setTsttype_name(String tsttype_name) {
		this.tsttype_name = tsttype_name;
	}
	public Integer getTsttype_parid() {
		return tsttype_parid;
	}
	public void setTsttype_parid(Integer tsttype_parid) {
		this.tsttype_parid = tsttype_parid;
	}
	public Integer getTsttype_seq() {
		return tsttype_seq;
	}
	public void setTsttype_seq(Integer tsttype_seq) {
		this.tsttype_seq = tsttype_seq;
	}
	public Integer getTsttype_state() {
		return tsttype_state;
	}
	public void setTsttype_state(Integer tsttype_state) {
		this.tsttype_state = tsttype_state;
	}
	public Integer getTsttype_light() {
		return tsttype_light;
	}
	public void setTsttype_light(Integer tsttype_light) {
		this.tsttype_light = tsttype_light;
	}
	public String getTsttype_laboratoryid() {
		return tsttype_laboratoryid;
	}
	public void setTsttype_laboratoryid(String tsttype_laboratoryid) {
		this.tsttype_laboratoryid = tsttype_laboratoryid;
	}
	public String getTsttype_laboratorynm() {
		return tsttype_laboratorynm;
	}
	public void setTsttype_laboratorynm(String tsttype_laboratorynm) {
		this.tsttype_laboratorynm = tsttype_laboratorynm;
	}
	public Integer getTsttype_cls() {
		return tsttype_cls;
	}
	public void setTsttype_cls(Integer tsttype_cls) {
		this.tsttype_cls = tsttype_cls;
	}
	public String getTsttype_enname() {
		return tsttype_enname;
	}
	public void setTsttype_enname(String tsttype_enname) {
		this.tsttype_enname = tsttype_enname;
	}
	public Integer getTsttype_computerunitid() {
		return tsttype_computerunitid;
	}
	public void setTsttype_computerunitid(Integer tsttype_computerunitid) {
		this.tsttype_computerunitid = tsttype_computerunitid;
	}
	public Integer getTsttype_discount() {
		return tsttype_discount;
	}
	public void setTsttype_discount(Integer tsttype_discount) {
		this.tsttype_discount = tsttype_discount;
	}
	public String getTsttype_bigcls() {
		return tsttype_bigcls;
	}
	public void setTsttype_bigcls(String tsttype_bigcls) {
		this.tsttype_bigcls = tsttype_bigcls;
	}
	public Integer getTsttype_price() {
		return tsttype_price;
	}
	public void setTsttype_price(Integer tsttype_price) {
		this.tsttype_price = tsttype_price;
	}
	public String getTsttype_smallcls() {
		return tsttype_smallcls;
	}
	public void setTsttype_smallcls(String tsttype_smallcls) {
		this.tsttype_smallcls = tsttype_smallcls;
	}
}
