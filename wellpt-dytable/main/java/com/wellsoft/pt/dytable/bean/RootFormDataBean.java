/*
 * @(#)2012-11-13 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wellsoft.pt.dytable.support.DytableConfig;
import com.wellsoft.pt.dytable.support.FormDataSignature;

/**
 * Description: 主从表数据集合对象
 *  
 * @author jiangmb
 * @date 2012-11-13
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-11-13.1	jiangmb		2012-11-13		Create
 * </pre>
 *
 */
public class RootFormDataBean implements Serializable {
	/** 如何描述serialVersionUID */
	private static final long serialVersionUID = 3924076705721901319L;

	private boolean file2swf;//是否转为swf文件

	private boolean setReadOnly;//是否设为全部只读

	//private List<String> deleteFile;//删除的文件名

	private List<FormDataBean> formDatas = new ArrayList<FormDataBean>();

	// 是否启用签名
	private boolean enableSignature;
	// 表单数字签名
	private FormDataSignature signature = new FormDataSignature();

	 
	/**
	 * 返回表单数字签名在JCR中的模块名
	 * 
	 * @return
	 */
	public String getSignatureModuleName() {
		return DytableConfig.DYTABLE_JCR_MODLE_NAME;
	}

	/**
	 * 返回表单数字签名在JCR中的结点名
	 * 
	 * @return
	 */
	public String getSignatureNodeName() {
		// TODO
		String nodeName = "";
		FormDataBean formDataBean = this.getFormDatas().get(0);
		List<FormDataColValBean> formDataColValBeans = formDataBean.getRecordList().get(0).getColValList();
		for (FormDataColValBean formDataColValBean : formDataColValBeans) {
			if (formDataColValBean.getColEnName().toUpperCase().equals("signature_".toUpperCase())) {
				nodeName = formDataColValBean.getValue();
			}
		}
		//		signature_
		return nodeName;
	}

	 

	/**
	 * @return the setReadOnly
	 */
	public boolean isSetReadOnly() {
		return setReadOnly;
	}

	/**
	 * @param setReadOnly 要设置的setReadOnly
	 */
	public void setSetReadOnly(boolean setReadOnly) {
		this.setReadOnly = setReadOnly;
	}

	/**
	 * @return the file2swf
	 */
	public boolean isFile2swf() {
		return file2swf;
	}

	/**
	 * @param file2swf 要设置的file2swf
	 */
	public void setFile2swf(boolean file2swf) {
		this.file2swf = file2swf;
	}

	public List<FormDataBean> getFormDatas() {
		return formDatas;
	}

	public void setFormDatas(List<FormDataBean> formDatas) {
		this.formDatas = formDatas;
	}

	/**
	 * @return the enableSignature
	 */
	public boolean isEnableSignature() {
		return enableSignature;
	}

	/**
	 * @param enableSignature 要设置的enableSignature
	 */
	public void setEnableSignature(boolean enableSignature) {
		this.enableSignature = enableSignature;
	}

	/**
	 * @return the signature
	 */
	public FormDataSignature getSignature() {
		return signature;
	}

	/**
	 * @param signature 要设置的signature
	 */
	public void setSignature(FormDataSignature signature) {
		this.signature = signature;
	}

	public void addFileValue(String fieldName, String value) {
		for (int i = 0; i < formDatas.size(); i++) {
			List<FormDataRecordBean> recordList = formDatas.get(i).getRecordList();
			for (int j = 0; j < recordList.size(); j++) {
				List<FormDataColValBean> colValList = recordList.get(j).getColValList();
				for (int k = 0; k < colValList.size(); k++) {
					if (fieldName.equals(colValList.get(k).getColEnName())) {
						if (value != null) {
							String valueNew = colValList.get(k).getValue() + value;
							colValList.get(k).setValue(valueNew);
						}
					}
				}
			}
		}
	}
}
