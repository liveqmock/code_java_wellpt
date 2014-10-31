/*
 * @(#)2012-11-26 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.bean;

import java.io.Serializable;

/**
 * Description: 如何描述该类
 *  
 * @author jiangmb
 * @date 2012-11-26
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-11-26.1	jiangmb		2012-11-26		Create
 * </pre>
 *
 */
public class CheckRuleBean implements Serializable {
	//规则ID值
	private String value;
	//规则说明
	private String label;
	//规则
	private String rule;
	public CheckRuleBean(){}
	public CheckRuleBean(String value,String label){
		this.value = value;
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
}
