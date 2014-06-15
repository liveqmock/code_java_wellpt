/** 
* @Project:wellpt 
* @Description: 用于表示键值对的Bean
* @author: jiangmb
* @version: V1.0   
* @date: 2012-10-24 
* @Copyright (c) 威尔公司-版权所有
*/

package com.wellsoft.pt.common.bean;

import java.io.Serializable;

/**
 * @ClassName: LabelValueBean
 * @Description: 用于表示键值对的Bean
 * @author jiangmb
 * @date 2012-10-24 
 * @version : 1.0
 */

@SuppressWarnings("serial")
public class LabelValueBean implements Serializable {
	private String value;
	private String label;
	public LabelValueBean(){
		
	}
	public LabelValueBean(String value,String label){
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
	public String toString(){
        StringBuffer sb = new StringBuffer("LabelValueBean[");
        sb.append(label);
        sb.append(", ");
        sb.append(value);
        sb.append("]");
        return sb.toString();
	}
}
