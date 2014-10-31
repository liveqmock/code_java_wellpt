package com.wellsoft.pt.dytable.bean;

public class LabelValueBean extends com.wellsoft.pt.common.bean.LabelValueBean {
	private static final long serialVersionUID = 4981625851940847925L;
	private String remark;

	/** 
	 * @param label 
	 * @param value
	 * @param remark
	 */
	public LabelValueBean(String label, String value, String remark) {
		super(value, label);
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
