package com.wellsoft.pt.dyform.support;

import org.json.JSONException;

import com.wellsoft.pt.core.context.ApplicationContextHolder;
import com.wellsoft.pt.dyform.entity.DyFormDefinition;
import com.wellsoft.pt.dyform.service.DyFormDefinitionService;
import com.wellsoft.pt.dyform.support.DyFormConfig.EnumFieldPropertyName;
import com.wellsoft.pt.dyform.support.DyFormConfig.EnumSubformFieldPropertyName;

public class SubformFieldDefinition {
	private String name;
	private DyFormDefinitionJSON ddJson;
	private String formUuidOfSubform;
	private DyFormDefinitionJSON subFormJson;

	public SubformFieldDefinition(String fieldName, DyFormDefinitionJSON ddJson, String formUuidOfSubform) {
		this.name = fieldName;
		this.ddJson = ddJson;
		this.formUuidOfSubform = formUuidOfSubform;
	}

	public String getName() {
		return this.name;
	}

	public String getDisplayName() {
		return this.ddJson.getSubformFieldPropertyOfStringType(this.formUuidOfSubform, this.name,
				EnumSubformFieldPropertyName.displayName);
	}

	public String getInputMode() {
		return this.loadSubFormJson().getFieldPropertyOfStringType(this.name, EnumFieldPropertyName.inputMode);
	}

	public DyFormDefinitionJSON loadSubFormJson() {
		if (subFormJson == null) {
			DyFormDefinition df = ((DyFormDefinitionService) ApplicationContextHolder
					.getBean("dyFormDefinitionService")).findDyFormDefinitionByFormUuid(formUuidOfSubform);
			df.setJsonHandler(null);
			if (df != null) {
				try {
					this.subFormJson = new DyFormDefinitionJSON(df.getDefinitionJson());
				} catch (JSONException e) {
				}
			}
		}

		return subFormJson;
	}

}
