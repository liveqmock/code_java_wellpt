package com.wellsoft.pt.dyform.entity;

import java.sql.Clob;
import java.text.DecimalFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.json.JSONException;
import org.json.JSONObject;

import com.wellsoft.pt.core.annotation.UnCloneable;
import com.wellsoft.pt.core.entity.IdEntity;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

/**
 * Description: 数据表单定义
 *  
 * @author Administrator
 * @date 2014-5-8
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-5-8.1	Hunt		2014-5-8		Create
 * </pre>
 *
 */
@Entity
@Table(name = "DYFORM_FORM_DEFINITION")
@DynamicUpdate
@DynamicInsert
public class DyFormDefinition extends IdEntity {

	private static final long serialVersionUID = -7964822772029135133L;

	//表名
	private String name;
	//显示名称
	private String displayName;
	//表单属性id
	private String outerId;
	//表单编号
	private String code;
	//表单显示形式 ： 两种 一种是可编辑展示、 一种是直接展示文本
	private String formDisplay;
	//显示单据的名称
	private String displayFormModelName;

	//显示单据对应的表单uuid
	private String displayFormModelId;
	// 应用于
	private String applyTo;
	//打印模板的ID
	private String printTemplateId;
	//打印模板的名称
	private String printTemplateName;
	//描述
	private String remark;

	//html body内容 
	@UnCloneable
	private Clob html;
	//版本 ,形式：1.0
	private String version;
	//模块ID
	private String moduleId;
	//模块名
	private String moduleName;
	//是否启用表单签名
	private String enableSignature;

	//以json的形式保存整个数据表单的定义 
	private String definitionJson;

	/*///////////以下为临时变量/////////////////***/
	private final String minVersion = "1.0";//最低的版本

	//版本格式
	private DecimalFormat versionFormat = new DecimalFormat("0.0");

	//是否升级1.是 0.否
	private String isUp = "0";//非持久化属性

	/** default constructor */
	public DyFormDefinition() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getOuterId() {
		return outerId;
	}

	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFormDisplay() {
		return formDisplay;
	}

	public void setFormDisplay(String formDisplay) {
		this.formDisplay = formDisplay;
	}

	public String getDisplayFormModelName() {
		return displayFormModelName;
	}

	public void setDisplayFormModelName(String displayFormModelName) {
		this.displayFormModelName = displayFormModelName;
	}

	public String getDisplayFormModelId() {
		return displayFormModelId;
	}

	public void setDisplayFormModelId(String displayFormModelId) {
		this.displayFormModelId = displayFormModelId;
	}

	public String getApplyTo() {
		return applyTo;
	}

	public void setApplyTo(String applyTo) {
		this.applyTo = applyTo;
	}

	public String getPrintTemplateId() {
		return printTemplateId;
	}

	public void setPrintTemplateId(String printTemplateId) {
		this.printTemplateId = printTemplateId;
	}

	public String getPrintTemplateName() {
		return printTemplateName;
	}

	public void setPrintTemplateName(String printTemplateName) {
		this.printTemplateName = printTemplateName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;

	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getEnableSignature() {
		return enableSignature;
	}

	public void setEnableSignature(String enableSignature) {
		this.enableSignature = enableSignature;
	}

	public Clob getHtml() {
		return html;
	}

	public void setHtml(Clob html) {
		this.html = html;
	}

	public String getDefinitionJson() {
		return definitionJson;
	}

	public void setDefinitionJson(String definitionJson) {
		this.definitionJson = definitionJson;
	}

	@Transient
	public DecimalFormat getVersionFormat() {
		return versionFormat;
	}

	public void setVersionFormat(DecimalFormat versionFormat) {
		this.versionFormat = versionFormat;
	}

	@Transient
	public String getMinVersion() {
		return minVersion;
	}

	@Transient
	public String getIsUp() {
		return isUp;
	}

	public void setIsUp(String isUp) {
		this.isUp = isUp;
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

	public void doBindVersionAsMinVersion() {
		this.setVersion(minVersion);
		this.doBindVersion2DefinitionJson();
	}

	public void doBindVersion2DefinitionJson() {
		if (this.getDefinitionJson() == null || this.getDefinitionJson().length() == 0)
			return;
		try {
			JSONObject jsonObject = new JSONObject(this.getDefinitionJson());
			jsonObject.put("version", this.getVersion());
			this.setDefinitionJson(jsonObject.toString());
		} catch (JSONException e) {
			e.printStackTrace();
			return;
		}
	}

	public void doBindVersionPlus() {
		this.setVersion(versionFormat.format((Float.parseFloat(this.getVersion()) + 0.1)));
		this.doBindVersion2DefinitionJson();
	}

	@Transient
	public boolean isMinVersion() {
		if (minVersion.equals(this.getVersion())) {
			return true;
		} else {
			return false;
		}
	}

	public void doBindUuid2Json() {
		try {
			JSONObject json = new JSONObject(this.getDefinitionJson());
			String uuid = json.getString("uuid");
			if (uuid == null || uuid.trim().length() == 0 || "undefined".equals(uuid)) {
				String thisUuid = this.getUuid();
				if (thisUuid != null && thisUuid.trim().length() > 0 && !thisUuid.equals("undefined")) {
					json.put("uuid", thisUuid);
					this.setDefinitionJson(json.toString());
				}

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
}
