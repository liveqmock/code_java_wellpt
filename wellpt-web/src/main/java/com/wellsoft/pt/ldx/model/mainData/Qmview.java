package com.wellsoft.pt.ldx.model.mainData;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.wellsoft.pt.core.entity.IdEntity;

@Entity
@Table(name = "qmview")
@DynamicUpdate
@DynamicInsert
public class Qmview extends IdEntity {
	private static final long serialVersionUID = 1L;

	private String factory;

	private String qmjylx01;

	private String qmjylx03;

	private String qmjylx10;

	private String qmjylx89;

	private String qmjylxldx01;

	private String qmjylxldx02;

	private String qmjylxldx03;

	private String qmjylxldx04;

	private String qmjylxldx05;

	private String qmjylxldx06;

	private String qmpcgl;

	private String qmpjjyq;

	private String shortdesc;

	private String wl;
	
	private String status = "0";

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Qmview() {
	}

	public String getFactory() {
		return this.factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getQmjylx01() {
		return this.qmjylx01;
	}

	public void setQmjylx01(String qmjylx01) {
		this.qmjylx01 = qmjylx01;
	}

	public String getQmjylx03() {
		return this.qmjylx03;
	}

	public void setQmjylx03(String qmjylx03) {
		this.qmjylx03 = qmjylx03;
	}

	public String getQmjylx10() {
		return this.qmjylx10;
	}

	public void setQmjylx10(String qmjylx10) {
		this.qmjylx10 = qmjylx10;
	}

	public String getQmjylx89() {
		return this.qmjylx89;
	}

	public void setQmjylx89(String qmjylx89) {
		this.qmjylx89 = qmjylx89;
	}

	public String getQmjylxldx01() {
		return this.qmjylxldx01;
	}

	public void setQmjylxldx01(String qmjylxldx01) {
		this.qmjylxldx01 = qmjylxldx01;
	}

	public String getQmjylxldx02() {
		return this.qmjylxldx02;
	}

	public void setQmjylxldx02(String qmjylxldx02) {
		this.qmjylxldx02 = qmjylxldx02;
	}

	public String getQmjylxldx03() {
		return this.qmjylxldx03;
	}

	public void setQmjylxldx03(String qmjylxldx03) {
		this.qmjylxldx03 = qmjylxldx03;
	}

	public String getQmjylxldx04() {
		return this.qmjylxldx04;
	}

	public void setQmjylxldx04(String qmjylxldx04) {
		this.qmjylxldx04 = qmjylxldx04;
	}

	public String getQmjylxldx05() {
		return this.qmjylxldx05;
	}

	public void setQmjylxldx05(String qmjylxldx05) {
		this.qmjylxldx05 = qmjylxldx05;
	}

	public String getQmjylxldx06() {
		return this.qmjylxldx06;
	}

	public void setQmjylxldx06(String qmjylxldx06) {
		this.qmjylxldx06 = qmjylxldx06;
	}

	public String getQmpcgl() {
		return this.qmpcgl;
	}

	public void setQmpcgl(String qmpcgl) {
		this.qmpcgl = qmpcgl;
	}

	public String getQmpjjyq() {
		return this.qmpjjyq;
	}

	public void setQmpjjyq(String qmpjjyq) {
		this.qmpjjyq = qmpjjyq;
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