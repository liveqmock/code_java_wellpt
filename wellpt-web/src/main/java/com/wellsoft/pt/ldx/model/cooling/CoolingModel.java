package com.wellsoft.pt.ldx.model.cooling;

import java.math.BigDecimal;



/**
 * 冷却模型
 * @author HeShi
 *
 */
public class CoolingModel {
	private static final long serialVersionUID = 1L;
	//表面发射率0.9
	private static Double EI = 0.9D;
	//环境温度
	private static Double TA = 25.0D;
	//LED热生成率
	private static Double FP = 0.7D;
	//波尔斯曼常数
	private static Double BC = 5.67E-8;
	
	//======输入参数==========
    //散热件类型:1光面 2叶面
	private Integer coolType;
	//散热件高度
	private Double hi;
	//基本温度
	private Double tc;
	//输入功率
	private Double wi;
	//驱动效率
	private Double yi;
	//最小外径
	private Double diMin;
	//最大外径
	private Double diMax;
	//======计算参数==========
	//散热件高度=hi/1000
	private Double h2;
	public Double getH2() {
		//System.out.println("散热件高度=="+(getHi()/1000));
		return getHi()/1000;
	}
	//Ta开氏温度=TA+273
	private Double tak;
	public Double getTak() {
		//System.out.println("Ta开氏温度=="+(TA+273));
		return TA+273;
	}
	//散热件温度T1=基本温度-8
	private Double t1;
	public Double getT1() {
		//System.out.println("散热件温度T1=="+(getTc()-8));
		return getTc()-8;
	}
	//温差=T1-TA
	private Double tm;
	public Double getTm() {
		//System.out.println("温差=="+(getT1()-TA));
		return getT1()-TA;
	}
	//T1开氏温度=T1+273
	private Double t1k;
	public Double getT1k() {
		//System.out.println("T1开氏温度=="+(getT1()+273));
		return getT1()+273;
	}
	//散热件辐射面积估算值=(最小外径+最大外径)*3.14*散热件高度/2
	private Double s1;
	public Double getS1() {
		//System.out.println("散热件辐射面积估算值=="+((getDiMax()+getDiMin())*3.14D*getHi()/2));
		return (getDiMax()+getDiMin())*3.14D*getHi()/2;
	}
	//======计算结果==========
	//热功率=输入功率w1*驱动效率η1*LED热生成率η2
	private Double pf;
	public Double getPf() {
		//System.out.println("热功率=="+(getWi()*getYi()*FP));
		return getWi()*getYi()*FP;
	}
	/**
	 * 表面换热系数
	 * f1:光面=1.49*{温差/散热件高度H2}E0.25
	 * f2:页面=f1*0.8
	 */
	private Double fc;
	public Double getFc() {
		return 1.49*Math.pow((getTm()/getH2()),0.25D)*(coolType.intValue()==2?0.8D:1);
	}
	/**
	 * 表面换热系数:精确后
	 */
	private Double fcFinal;
	public Double getFcFinal() {
		BigDecimal b = new BigDecimal(getFc());
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	/**
	 * 散热面积
	 * 1光面:热功率w2*(10^6)/{表面换热系数1*(温差+波尔斯曼常数*外表面发射率*(T1开氏温度4-Ta开氏温度4)}
	 * 2叶面:{热功率w2-波尔斯曼常数*散热件辐射面积估算S1*(10^-6)*外表面发射率*(T1开氏温度4-Ta开氏温度4)}/表面换热系数2*温差*10^-6
	 */
	private Double fa;
	public Double getFa() {
		switch (coolType) {
		case 1:
			//光面
			return getPf()*(1E6)/(getFc()*getTm()+BC*EI*(Math.pow(getT1k(),4)-Math.pow(getTak(),4)));
		case 2:
			//叶面
			return (getPf()-BC*getS1()*(1E-6)*EI*(Math.pow(getT1k(),4)-Math.pow(getTak(),4)))/(getFc()*getTm()*(1E-6));

		default:
			return 0D;
		}
	}
	/**
	 * 散热面积:精确后
	 */
	private Double faFinal;
	public Double getFaFinal() {
		BigDecimal b = new BigDecimal(getFa());
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	public Integer getCoolType() {
		return coolType;
	}
	public void setCoolType(Integer coolType) {
		this.coolType = coolType;
	}
	public Double getHi() {
		return hi;
	}
	public void setHi(Double hi) {
		this.hi = hi;
	}
	public Double getTc() {
		return tc;
	}
	public void setTc(Double tc) {
		this.tc = tc;
	}
	public Double getWi() {
		return wi;
	}
	public void setWi(Double wi) {
		this.wi = wi;
	}
	public Double getDiMin() {
		return diMin;
	}
	public void setDiMin(Double diMin) {
		this.diMin = diMin;
	}
	public Double getDiMax() {
		return diMax;
	}
	public void setDiMax(Double diMax) {
		this.diMax = diMax;
	}
	public Double getYi() {
		return yi;
	}
	public void setYi(Double yi) {
		this.yi = yi;
	}
	
	
}
