package com.wellsoft.pt.basicdata.printtemplate.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.wellsoft.pt.core.entity.IdEntity;

/**
 * 
 * Description: 打印模板实体类
 *  
 * @author zhouyq
 * @date 2013-3-21
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-3-21.1	zhouyq		2013-3-21		Create
 * </pre>
 *
 */
@Entity
@Table(name = "cd_print_template")
@DynamicUpdate
@DynamicInsert
public class PrintTemplate extends IdEntity {
	private static final long serialVersionUID = 1L;

	public static final String TEMPLATE_TYPE_WORD = "wordType";
	public static final String TEMPLATE_TYPE_HTML = "htmlType";

	/**分类*/
	private String type;
	/** 名称 */
	private String name;
	/** 模板ID */
	private String id;
	/** 排序编号 */
	private String code;
	/** 模板类型 */
	private String templateType;
	/** 打印间隔 */
	private String printInterval;
	/** 多行行数 */
	private Integer rowNumber;
	/** 是否保留修改痕迹(1：是；0：否) */
	private Boolean isSaveTrace;
	/** 是否只读(1：是；0：否) */
	private Boolean isReadOnly;
	/** 是否保存到源文档(1：是；0：否) */
	private Boolean isSaveSource;
	/** 文件名定义格式 */
	private String fileNameFormat;
	/** 是否保存打印记录(1：是；0：否) */
	private Boolean isSavePrintRecord;
	/** 关键字集合 */
	private String keyWords;
	/**模版文件的uuid**/
	private String fileUuid;

	/** 打印模板使用人，从组织机构中选择直接作为ACL中的SID */
	private List<String> owners = new ArrayList<String>(0);

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type 要设置的type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name 要设置的name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code 要设置的code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the templateType
	 */
	public String getTemplateType() {
		return templateType;
	}

	/**
	 * @param templateType 要设置的templateType
	 */
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	/**
	 * @return the printInterval
	 */
	public String getPrintInterval() {
		return printInterval;
	}

	/**
	 * @param printInterval 要设置的printInterval
	 */
	public void setPrintInterval(String printInterval) {
		this.printInterval = printInterval;
	}

	/**
	 * @return the rowNumber
	 */
	public Integer getRowNumber() {
		return rowNumber;
	}

	/**
	 * @param rowNumber 要设置的rowNumber
	 */
	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

	/**
	 * @return the isSaveTrace
	 */
	public Boolean getIsSaveTrace() {
		return isSaveTrace;
	}

	/**
	 * @param isSaveTrace 要设置的isSaveTrace
	 */
	public void setIsSaveTrace(Boolean isSaveTrace) {
		this.isSaveTrace = isSaveTrace;
	}

	/**
	 * @return the isReadOnly
	 */
	public Boolean getIsReadOnly() {
		return isReadOnly;
	}

	/**
	 * @param isReadOnly 要设置的isReadOnly
	 */
	public void setIsReadOnly(Boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}

	/**
	 * @return the isSaveSource
	 */
	public Boolean getIsSaveSource() {
		return isSaveSource;
	}

	/**
	 * @param isSaveSource 要设置的isSaveSource
	 */
	public void setIsSaveSource(Boolean isSaveSource) {
		this.isSaveSource = isSaveSource;
	}

	/**
	 * @return the fileNameFormat
	 */
	public String getFileNameFormat() {
		return fileNameFormat;
	}

	/**
	 * @param fileNameFormat 要设置的fileNameFormat
	 */
	public void setFileNameFormat(String fileNameFormat) {
		this.fileNameFormat = fileNameFormat;
	}

	/**
	 * @return the isSavePrintRecord
	 */
	public Boolean getIsSavePrintRecord() {
		return isSavePrintRecord;
	}

	/**
	 * @param isSavePrintRecord 要设置的isSavePrintRecord
	 */
	public void setIsSavePrintRecord(Boolean isSavePrintRecord) {
		this.isSavePrintRecord = isSavePrintRecord;
	}

	/**
	 * 
	 * 第三方直接取不存入数据库
	 * 
	 * @return
	 */
	@Transient
	public List<String> getOwners() {
		return owners;
	}

	public void setOwners(List<String> owners) {
		this.owners = owners;
	}

	/**
	 * @return the keyWords
	 */
	public String getKeyWords() {
		return keyWords;
	}

	/**
	 * @param keyWords 要设置的keyWords
	 */
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getFileUuid() {
		return fileUuid;
	}

	public void setFileUuid(String fileUuid) {
		this.fileUuid = fileUuid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
