package com.wellsoft.pt.dyform.support;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.wellsoft.pt.dyform.support.DyFormConfig.EnumSubformPropertyName;

public class SubformDefinition {
	private DyFormDefinitionJSON ddJson;
	private String formUuidOfSubform;
	private final List<SubformFieldDefinition> subformFieldDefinitions;

	public SubformDefinition(String formUuidOfSubform, DyFormDefinitionJSON ddJson) {
		this.formUuidOfSubform = formUuidOfSubform;
		this.ddJson = ddJson;
		subformFieldDefinitions = new ArrayList<SubformFieldDefinition>();
		List<String> fieldNames = this.getFieldNames();
		for (String fieldName : fieldNames) {
			subformFieldDefinitions.add(new SubformFieldDefinition(fieldName, this.ddJson, this.formUuidOfSubform));
		}

	}

	public String getFormUuid() {
		return this.ddJson.getSubformPropertyOfStringType(formUuidOfSubform, EnumSubformPropertyName.formUuid);
	}

	public void setFormUuid(String formUuid) {
		try {
			this.ddJson.addSubformProperty(formUuidOfSubform, EnumSubformPropertyName.formUuid, formUuid);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getOuterId() {
		return this.ddJson.getSubformPropertyOfStringType(formUuidOfSubform, EnumSubformPropertyName.outerId);
	}

	public void setOuterId(String outerId) {
		try {
			this.ddJson.addSubformProperty(formUuidOfSubform, EnumSubformPropertyName.outerId, outerId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return this.ddJson.getSubformPropertyOfStringType(formUuidOfSubform, EnumSubformPropertyName.name);
	}

	public void setName(String name) {
		try {
			this.ddJson.addSubformProperty(formUuidOfSubform, EnumSubformPropertyName.name, name);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getDisplayName() {
		return this.ddJson.getSubformPropertyOfStringType(formUuidOfSubform, EnumSubformPropertyName.displayName);
	}

	public void setDisplayName(String displayName) {
		try {
			this.ddJson.addSubformProperty(formUuidOfSubform, EnumSubformPropertyName.displayName, displayName);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getIsGroupShowTitle() {
		return this.ddJson.getSubformPropertyOfStringType(formUuidOfSubform, EnumSubformPropertyName.isGroupShowTitle);
	}

	public void setIsGroupShowTitle(String isGroupShowTitle) {
		try {
			this.ddJson.addSubformProperty(formUuidOfSubform, EnumSubformPropertyName.isGroupShowTitle,
					isGroupShowTitle);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getGroupShowTitle() {
		return this.ddJson.getSubformPropertyOfStringType(formUuidOfSubform, EnumSubformPropertyName.groupShowTitle);
	}

	public void setGroupShowTitle(String groupShowTitle) {
		try {
			this.ddJson.addSubformProperty(formUuidOfSubform, EnumSubformPropertyName.groupShowTitle, groupShowTitle);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getIsGroupColumnShow() {
		return this.ddJson.getSubformPropertyOfStringType(formUuidOfSubform, EnumSubformPropertyName.isGroupColumnShow);
	}

	public void setIsGroupColumnShow(String isGroupColumnShow) {
		try {
			this.ddJson.addSubformProperty(formUuidOfSubform, EnumSubformPropertyName.isGroupColumnShow,
					isGroupColumnShow);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getSubformApplyTableId() {
		return this.ddJson.getSubformPropertyOfStringType(formUuidOfSubform,
				EnumSubformPropertyName.subformApplyTableId);
	}

	public void setSubformApplyTableId(String subformApplyTableId) {
		try {
			this.ddJson.addSubformProperty(formUuidOfSubform, EnumSubformPropertyName.subformApplyTableId,
					subformApplyTableId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getSubrRelationDataDefiantion() {
		return this.ddJson.getSubformPropertyOfStringType(formUuidOfSubform,
				EnumSubformPropertyName.subrRelationDataDefiantion);
	}

	public void setSubrRelationDataDefiantion(String subrRelationDataDefiantion) {
		try {
			this.ddJson.addSubformProperty(formUuidOfSubform, EnumSubformPropertyName.subrRelationDataDefiantion,
					subrRelationDataDefiantion);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getTableOpen() {
		return this.ddJson.getSubformPropertyOfStringType(formUuidOfSubform, EnumSubformPropertyName.tableOpen);
	}

	public void setTableOpen(String tableOpen) {
		try {
			this.ddJson.addSubformProperty(formUuidOfSubform, EnumSubformPropertyName.tableOpen, tableOpen);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getEditMode() {
		return this.ddJson.getSubformPropertyOfStringType(formUuidOfSubform, EnumSubformPropertyName.editMode);
	}

	public void setEditMode(String editMode) {
		try {
			this.ddJson.addSubformProperty(formUuidOfSubform, EnumSubformPropertyName.editMode, editMode);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getHideButtons() {
		return this.ddJson.getSubformPropertyOfStringType(formUuidOfSubform, EnumSubformPropertyName.hideButtons);
	}

	public void setHideButtons(String hideButtons) {
		try {
			this.ddJson.addSubformProperty(formUuidOfSubform, EnumSubformPropertyName.hideButtons, hideButtons);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public List<String> getFieldNames() {
		try {
			return this.ddJson.getFieldNamesOfSubform(formUuidOfSubform);
		} catch (JSONException e) {

			e.printStackTrace();
			return null;
		}

	}

	public List<SubformFieldDefinition> getSubformFieldDefinitions() {
		return subformFieldDefinitions;
	}

}
