package com.wellsoft.pt.repository.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.wellsoft.pt.core.entity.BaseEntity;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

@Entity
@Table(name = "repo_file")
@DynamicUpdate
@DynamicInsert
public class LogicFileInfo extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String _id;
	private String digestValue;
	private String digestAlgorithm;
	private String signatureValue;
	private String certificate;
	private String swfUuid;

	private String uuid;
	private Date createTime;
	private String creator;
	private Date modifyTime;
	private String modifier;
	private Integer recVer;

	private String physicalFileId;//物理文件ID,指向mongodb中保存的文件的ID
	private String fileName;
	private String contentType;
	private Long fileSize;

	/** 该字段不用于持久化，只用于dto使用 */
	private String fileID;
	
	 
	private String purpose;

	/**
	 * 该字段用于对应mongodb中的逻辑文件信息的日志的_id
	 * 
	 * @return
	 */
	@Transient
	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getDigestValue() {
		return digestValue;
	}

	public void setDigestValue(String digestValue) {
		this.digestValue = digestValue;
	}

	public String getDigestAlgorithm() {
		return digestAlgorithm;
	}

	public void setDigestAlgorithm(String digestAlgorithm) {
		this.digestAlgorithm = digestAlgorithm;
	}

	public String getSignatureValue() {
		return signatureValue;
	}

	public void setSignatureValue(String signatureValue) {
		this.signatureValue = signatureValue;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getSwfUuid() {
		return swfUuid;
	}

	public void setSwfUuid(String swfUuid) {
		this.swfUuid = swfUuid;
	}

	public void doBindCreateTimeAsNow() {
		this.setCreateTime(new Date());
	}

	public void doBindModifyTimeAsNow() {
		this.setModifyTime(new Date());
	}

	public void doBindModifierAsCurrentUser() {
		this.setModifier(SpringSecurityUtils.getCurrentUserId());
	}

	public void doBindCreatorAsCurrentUser() {
		this.setCreator(SpringSecurityUtils.getCurrentUserId());
	}

	@Id
	@Override
	public String getUuid() {
		return uuid;
	}

	@Override
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public Date getCreateTime() {
		return createTime;
	}

	@Override
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String getCreator() {
		return creator;
	}

	@Override
	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Override
	public Date getModifyTime() {
		return modifyTime;
	}

	@Override
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Override
	public String getModifier() {
		return modifier;
	}

	@Override
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Override
	@Version
	public Integer getRecVer() {
		return recVer;
	}

	@Override
	public void setRecVer(Integer recVer) {
		this.recVer = recVer;
	}

	public String getPhysicalFileId() {
		return physicalFileId;
	}

	public void setPhysicalFileId(String physicalFileId) {
		this.physicalFileId = physicalFileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	@Transient
	public String getFileID() {
		if (this.uuid == null || this.uuid.trim().length() == 0) {
			return this.fileID;
		} else {
			return getUuid();
		}

	}

	public void setFileID(String fileID) {
		this.fileID = fileID;
	}

	@Transient
	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	

}
