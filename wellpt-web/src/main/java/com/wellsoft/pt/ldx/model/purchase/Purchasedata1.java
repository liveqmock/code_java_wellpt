package com.wellsoft.pt.ldx.model.purchase;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@Entity
public class Purchasedata1 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private BigDecimal p1 = new BigDecimal("0");

	private BigDecimal p10 = new BigDecimal("0");

	private BigDecimal p11 = new BigDecimal("0");

	private BigDecimal p12 = new BigDecimal("0");

	private BigDecimal p2 = new BigDecimal("0");

	private BigDecimal p3 = new BigDecimal("0");

	private BigDecimal p4 = new BigDecimal("0");

	private BigDecimal p5 = new BigDecimal("0");

	private BigDecimal p6 = new BigDecimal("0");

	private BigDecimal p7 = new BigDecimal("0");

	private BigDecimal p8 = new BigDecimal("0");

	private BigDecimal p9 = new BigDecimal("0");

	private BigDecimal q1 = new BigDecimal("0.00");

	private BigDecimal q10 = new BigDecimal("0.00");

	private BigDecimal q11 = new BigDecimal("0.00");

	private BigDecimal q12 = new BigDecimal("0.00");

	private BigDecimal q2 = new BigDecimal("0.00");

	private BigDecimal q3 = new BigDecimal("0.00");

	private BigDecimal q4 = new BigDecimal("0.00");

	private BigDecimal q5 = new BigDecimal("0.00");

	private BigDecimal q6 = new BigDecimal("0.00");

	private BigDecimal q7 = new BigDecimal("0.00");

	private BigDecimal q8 = new BigDecimal("0.00");

	private BigDecimal q9 = new BigDecimal("0.00");

	private BigDecimal tp = new BigDecimal("0");

	private BigDecimal tq = new BigDecimal("0.00");

	private String zjfl2;

	private String zjfl3;

	public Purchasedata1() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getP1() {
		return this.p1;
	}

	public void setP1(BigDecimal p1) {
		this.p1 = p1;
	}

	public BigDecimal getP10() {
		return this.p10;
	}

	public void setP10(BigDecimal p10) {
		this.p10 = p10;
	}

	public BigDecimal getP11() {
		return this.p11;
	}

	public void setP11(BigDecimal p11) {
		this.p11 = p11;
	}

	public BigDecimal getP12() {
		return this.p12;
	}

	public void setP12(BigDecimal p12) {
		this.p12 = p12;
	}

	public BigDecimal getP2() {
		return this.p2;
	}

	public void setP2(BigDecimal p2) {
		this.p2 = p2;
	}

	public BigDecimal getP3() {
		return this.p3;
	}

	public void setP3(BigDecimal p3) {
		this.p3 = p3;
	}

	public BigDecimal getP4() {
		return this.p4;
	}

	public void setP4(BigDecimal p4) {
		this.p4 = p4;
	}

	public BigDecimal getP5() {
		return this.p5;
	}

	public void setP5(BigDecimal p5) {
		this.p5 = p5;
	}

	public BigDecimal getP6() {
		return this.p6;
	}

	public void setP6(BigDecimal p6) {
		this.p6 = p6;
	}

	public BigDecimal getP7() {
		return this.p7;
	}

	public void setP7(BigDecimal p7) {
		this.p7 = p7;
	}

	public BigDecimal getP8() {
		return this.p8;
	}

	public void setP8(BigDecimal p8) {
		this.p8 = p8;
	}

	public BigDecimal getP9() {
		return this.p9;
	}

	public void setP9(BigDecimal p9) {
		this.p9 = p9;
	}

	public BigDecimal getQ1() {
		return this.q1;
	}

	public void setQ1(BigDecimal q1) {
		this.q1 = q1;
	}

	public BigDecimal getQ10() {
		return this.q10;
	}

	public void setQ10(BigDecimal q10) {
		this.q10 = q10;
	}

	public BigDecimal getQ11() {
		return this.q11;
	}

	public void setQ11(BigDecimal q11) {
		this.q11 = q11;
	}

	public BigDecimal getQ12() {
		return this.q12;
	}

	public void setQ12(BigDecimal q12) {
		this.q12 = q12;
	}

	public BigDecimal getQ2() {
		return this.q2;
	}

	public void setQ2(BigDecimal q2) {
		this.q2 = q2;
	}

	public BigDecimal getQ3() {
		return this.q3;
	}

	public void setQ3(BigDecimal q3) {
		this.q3 = q3;
	}

	public BigDecimal getQ4() {
		return this.q4;
	}

	public void setQ4(BigDecimal q4) {
		this.q4 = q4;
	}

	public BigDecimal getQ5() {
		return this.q5;
	}

	public void setQ5(BigDecimal q5) {
		this.q5 = q5;
	}

	public BigDecimal getQ6() {
		return this.q6;
	}

	public void setQ6(BigDecimal q6) {
		this.q6 = q6;
	}

	public BigDecimal getQ7() {
		return this.q7;
	}

	public void setQ7(BigDecimal q7) {
		this.q7 = q7;
	}

	public BigDecimal getQ8() {
		return this.q8;
	}

	public void setQ8(BigDecimal q8) {
		this.q8 = q8;
	}

	public BigDecimal getQ9() {
		return this.q9;
	}

	public void setQ9(BigDecimal q9) {
		this.q9 = q9;
	}

	public BigDecimal getTp() {
		return this.tp;
	}

	public void setTp(BigDecimal tp) {
		this.tp = tp;
	}

	public BigDecimal getTq() {
		return this.tq;
	}

	public void setTq(BigDecimal tq) {
		this.tq = tq;
	}

	public String getZjfl2() {
		return this.zjfl2;
	}

	public void setZjfl2(String zjfl2) {
		this.zjfl2 = zjfl2;
	}

	public String getZjfl3() {
		return this.zjfl3;
	}

	public void setZjfl3(String zjfl3) {
		this.zjfl3 = zjfl3;
	}

	public void count() {
		DecimalFormat df1 = new DecimalFormat("#");
		DecimalFormat df2 = new DecimalFormat("#.00");
		tq = new BigDecimal(df1.format(Double.valueOf(q1.toString())
				+ Double.valueOf(q2.toString()) + Double.valueOf(q3.toString())
				+ Double.valueOf(q4.toString()) + Double.valueOf(q5.toString())
				+ Double.valueOf(q6.toString()) + Double.valueOf(q7.toString())
				+ Double.valueOf(q8.toString()) + Double.valueOf(q9.toString())
				+ Double.valueOf(q10.toString())
				+ Double.valueOf(q11.toString())
				+ Double.valueOf(q12.toString())));
		tp = new BigDecimal(df2.format(Double.valueOf(p1.toString())
				+ Double.valueOf(p2.toString()) + Double.valueOf(p3.toString())
				+ Double.valueOf(p4.toString()) + Double.valueOf(p5.toString())
				+ Double.valueOf(p6.toString()) + Double.valueOf(p7.toString())
				+ Double.valueOf(p8.toString()) + Double.valueOf(p9.toString())
				+ Double.valueOf(p10.toString())
				+ Double.valueOf(p11.toString())
				+ Double.valueOf(p12.toString())));
	}

}