package com.wellsoft.pt.basicdata.serialnumber.entity;

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
 * Description: 流水号定义实体类
 *  
 * @author zhouyq
 * @date 2013-3-5
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-3-5.1	zhouyq		2013-3-5		Create
 * </pre>
 *
 */
@Entity
@Table(name = "cd_serial_number")
@DynamicUpdate
@DynamicInsert
public class SerialNumber extends IdEntity {
	private static final long serialVersionUID = 2943854786118950658L;

	/**分类*/
	private String type;
	/** 名称 */
	private String name;
	/** ID */
	private String id;
	/** 排序编号 */
	private String code;
	/** 关键部分 */
	private String keyPart;
	/** 头部 */
	private String headPart;
	/** 初始值 */
	private String initialValue;
	/** 是否补位*/
	private Boolean isFillPosition;
	/** 增量 */
	private Integer incremental;
	/** 尾部 */
	private String lastPart;
	/** 是否补号*/
	private Boolean isFillNumber;
	/** 新年度开始日期*/
	private String startDate;
	/** 可编辑 */
	private Boolean isEditor;
	/** 备注 */
	private String remark;
	/** 指针 */
	private String pointer;
	/**生成的流水号*/
	private String serialNum;

	/** 流水号定义使用人，从组织机构中选择直接作为ACL中的SID */
	private List<String> owners = new ArrayList<String>(0);

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

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

	public String getKeyPart() {
		return keyPart;
	}

	public void setKeyPart(String keyPart) {
		this.keyPart = keyPart;
	}

	public String getHeadPart() {
		return headPart;
	}

	public void setHeadPart(String headPart) {
		this.headPart = headPart;
	}

	public String getInitialValue() {
		return initialValue;
	}

	public void setInitialValue(String initialValue) {
		this.initialValue = initialValue;
	}

	public Boolean getIsFillPosition() {
		return isFillPosition;
	}

	public void setIsFillPosition(Boolean isFillPosition) {
		this.isFillPosition = isFillPosition;
	}

	public Integer getIncremental() {
		return incremental;
	}

	public void setIncremental(Integer incremental) {
		this.incremental = incremental;
	}

	public String getLastPart() {
		return lastPart;
	}

	public void setLastPart(String lastPart) {
		this.lastPart = lastPart;
	}

	public Boolean getIsFillNumber() {
		return isFillNumber;
	}

	public void setIsFillNumber(Boolean isFillNumber) {
		this.isFillNumber = isFillNumber;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public Boolean getIsEditor() {
		return isEditor;
	}

	public void setIsEditor(Boolean isEditor) {
		this.isEditor = isEditor;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getPointer() {
		return pointer;
	}

	public void setPointer(String pointer) {
		this.pointer = pointer;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
}
