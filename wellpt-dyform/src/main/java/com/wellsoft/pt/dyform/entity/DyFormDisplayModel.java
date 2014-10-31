package com.wellsoft.pt.dyform.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.wellsoft.pt.core.entity.IdEntity;
import com.wellsoft.pt.repository.support.enums.EnumYesNo;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

/**
 * Description: 数据表单显示单据
 *  
 * @author Administrator
 * @date 2014-5-8
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-8-4	Hunt		 		Create
 * </pre>
 *
 */
@Entity
@Table(name = "dyform_display_model")
@DynamicUpdate
@DynamicInsert
public class DyFormDisplayModel extends IdEntity {

	private static final long serialVersionUID = -7964822772029135133L;

	/*	private String uuid;
		private Date createTime;
		private String creator;
		private Date modifyTime;
		private String modifier;
		private Integer recVer;*/

	//显示名称
	private String displayName;

	//表单属性id
	private String outerId;

	private String remark;

	//html body内容  
	private String html;

	private String referredFormId;
	private String preview;

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReferredFormId() {
		return referredFormId;
	}

	public void setReferredFormId(String referredFormId) {
		this.referredFormId = referredFormId;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	/*	@Id
		public String getUuid() {
			return uuid;
		}

		public void setUuid(String uuid) {
			this.uuid = uuid;
		}*/

	public String getPreview() {
		if (StringUtils.isBlank(this.preview)) {//默认为NO
			this.doBindPreviewAsNo();
		}
		return preview;
	}

	public void setPreview(String preview) {
		this.preview = preview;
	}

	/*	public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		public String getCreator() {
			return creator;
		}

		public void setCreator(String creator) {
			this.creator = creator;
		}

		public Date getModifyTime() {
			return modifyTime;
		}

		public void setModifyTime(Date modifyTime) {
			this.modifyTime = modifyTime;
		}

		public String getModifier() {
			return modifier;
		}

		public void setModifier(String modifier) {
			this.modifier = modifier;
		}

		@Version
		public Integer getRecVer() {
			return recVer;
		}

		public void setRecVer(Integer recVer) {
			this.recVer = recVer;
		}
	*/
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

	public void doBindPreviewAsNo() {
		this.setPreview(EnumYesNo.NO.getValue());
	}

	public void doBindPreviewAsYes() {
		this.setPreview(EnumYesNo.YES.getValue());
	}

}
