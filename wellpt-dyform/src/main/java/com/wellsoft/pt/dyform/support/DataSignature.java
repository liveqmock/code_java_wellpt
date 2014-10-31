/*
 * @(#)2014-1-10 V1.0
 * 
 * Copyright 2014 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dyform.support;

import java.io.Serializable;
import java.util.List;

import com.wellsoft.pt.repository.entity.FileUpload;

/**
 * Description: 表单数据签名
 *  
 * @author zhulh
 * @date 2014-1-10
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-1-10.1	zhulh		2014-1-10		Create
 * 
 * 
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-4-3.10	 hunt		2014-4-3		添加了fileID字段
 * </pre>
 *
 */
public class DataSignature implements Serializable {

	/** 如何描述serialVersionUID */
	private static final long serialVersionUID = 978277335246260603L;

	public static final int STATUS_SUCCESS = 1;
	public static final int STATUS_FAILURE = -1;

	private String fileID;

	// 被签名的内容 
	private String signedData;
	// 消息摘要
	private String digestValue;
	// 消息摘要算法
	private String digestAlgorithm;
	// 证书
	private String certificate;
	// 签名值
	private String signatureValue;
	// 状态(0签名失败, 1签名成功)
	private int status;
	// 备注
	private String remark;

	// 附件签名信息
	private List<FileUpload> files;

	public String getSignedData() {
		return signedData;
	}

	public void setSignedData(String signedData) {
		this.signedData = signedData;
	}

	/**
	 * @return the digestValue
	 */
	public String getDigestValue() {
		return digestValue;
	}

	/**
	 * @param digestValue 要设置的digestValue
	 */
	public void setDigestValue(String digestValue) {
		this.digestValue = digestValue;
	}

	/**
	 * @return the digestAlgorithm
	 */
	public String getDigestAlgorithm() {
		return digestAlgorithm;
	}

	/**
	 * @param digestAlgorithm 要设置的digestAlgorithm
	 */
	public void setDigestAlgorithm(String digestAlgorithm) {
		this.digestAlgorithm = digestAlgorithm;
	}

	/**
	 * @return the certificate
	 */
	public String getCertificate() {
		return certificate;
	}

	/**
	 * @param certificate 要设置的certificate
	 */
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	/**
	 * @return the signatureValue
	 */
	public String getSignatureValue() {
		return signatureValue;
	}

	/**
	 * @param signatureValue 要设置的signatureValue
	 */
	public void setSignatureValue(String signatureValue) {
		this.signatureValue = signatureValue;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status 要设置的status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark 要设置的remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the files
	 */
	public List<FileUpload> getFiles() {
		return files;
	}

	/**
	 * @param files 要设置的files
	 */
	public void setFiles(List<FileUpload> files) {
		this.files = files;
	}

	public String getFileID() {
		return fileID;
	}

	public void setFileID(String fileID) {
		this.fileID = fileID;
	}

}
