/*
 * @(#)2013-1-28 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dyform.facade;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wellsoft.pt.core.service.AbstractApiFacade;
import com.wellsoft.pt.dyform.entity.DyFormDefinition;
import com.wellsoft.pt.dyform.service.DyFormDataService;
import com.wellsoft.pt.dyform.service.DyFormDefinitionService;
import com.wellsoft.pt.dyform.support.DyFormDefinitionUtils;
import com.wellsoft.pt.dytable.utils.DynamicUtils;

/**
 * Description: 如何描述该类
 * 
 * @author zhulh
 * @date 2013-1-28
 * @version 1.0
 * 
 *          <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-1-28.1	zhulh		2013-1-28		Create
 * </pre>
 * 
 */
@Component
public class DyFormApiFacade extends AbstractApiFacade {
	private Logger logger = LoggerFactory.getLogger(DyFormApiFacade.class);

	@Autowired
	DyFormDataService dyFormDataService;

	@Autowired
	DyFormDefinitionService dyFormDefinitionService;

	/**
	 * 检查数据是否已存在<br/>
	 
	 * 检查指定的字段的值是否存在于指定的表中
	 * 
	 * @param tblName
	 * @param fieldName
	 * @param fieldValue 
	 * @return
	 * @throws Exception 
	 */
	public boolean queryFormDataExists(String tblName, String fieldName, String fieldValue) throws Exception {
		return dyFormDataService.queryFormDataExists(tblName, fieldName, fieldValue);
	}

	/**
	 * 检查数据是否已存在<br/>
	 * 检查指定的字段的值除了指定的uuid的记录外，还有没有存在于其他记录中<br/>
	 * 
	 * @param tblName
	 * @param fieldName
	 * @param fieldValue
	 * @param uuid
	 * @return
	 * @throws Exception 
	 */
	public boolean queryFormDataExists(String tblName, String fieldName, String fieldValue, String uuid)
			throws Exception {
		return dyFormDataService.queryFormDataExists(uuid, tblName, fieldName, fieldValue);
	}

	/**
	 * @param mainformUuid 主表的表定义uuid
	 * @param formDatas 表单数据列表
	 * @return
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * @throws ParseException 
	 */
	public String saveFormData(String mainformUuid/*主表表单定义uuid*/,
			Map<String/*表单定义uuid*/, List<Map<String /*表单字段值*/, Object/*表单字段值*/>>> formDatas)
			throws JsonParseException, JsonMappingException, JSONException, IOException, ParseException {
		return dyFormDataService.saveFormData(mainformUuid, formDatas);
	}

	/**
	 * 获取主表数据
	 * @param formUuid
	 * @param dataUuid
	 * @return
	 */
	public Map<String /*表单字段值*/, Object/*表单字段值*/> getMainData(String formUuid, String dataUuid) {
		return dyFormDataService.getFormDataOfMainform(formUuid, dataUuid);
	}

	/**
	 * 获取表单数据(包括主表、从表)
	 * @param formUuid
	 * @param dataUuid
	 * @return
	 * @throws JSONException 
	 */
	public Map<String/*表单定义uuid*/, List<Map<String /*表单字段值*/, Object/*表单字段值*/>>> getFormData(String formUuid,
			String dataUuid) throws JSONException {
		return dyFormDataService.getFormData(formUuid, dataUuid);
	}

	/**
	 * 获取表单的某个从表的数据
	 * @param formUuidOfMainform
	 * @param formUuidOfSubform
	 * @param dataUuidOfMainform
	 * @return
	 */
	public List<Map<String /*表单字段值*/, Object/*表单字段值*/>> getSubformData(String formUuidOfMainform,
			String formUuidOfSubform, String dataUuidOfMainform) {
		return dyFormDataService.getFormDataOfSubform(formUuidOfMainform, formUuidOfSubform, dataUuidOfMainform);
	}

	/**
	 * 创建uuid
	 * @return
	 */
	public static String createUuid() {
		return DynamicUtils.getRandomUUID();
	}

	/**
	 * 获取表单定义
	 * @param formUuid 表单定义uuid
	 * @return
	 */
	public DyFormDefinition getFormDefinitionByFormUuid(String formUuid) {
		return this.dyFormDefinitionService.findDyFormDefinitionByFormUuid(formUuid);
	}

	/**
	 * 以json字段串的格式(具体的格式请查看文档)返回formUuid指定的表单的各字段的定义
	 * @param formUuid
	 * @return 找不到formUuid指定的表单时返回null 
	 */
	public String getJsonString4FieldDefinitionByFormUuid(String formUuid) {
		return this.getJsonObject4FieldDefinitionByFormUuid(formUuid).toString();
	}

	/**
	 * 以jsonObject的格式(具体的格式请查看文档)返回formUuid指定的表单的各字段的定义
	 * @param formUuid
	 * @return 找不到formUuid指定的表单时返回null 
	 */
	public JSONObject getJsonObject4FieldDefinitionByFormUuid(String formUuid) {
		DyFormDefinition formDefinition = this.getFormDefinitionByFormUuid(formUuid);
		if (formDefinition == null) {
			return null;
		}

		DyFormDefinitionUtils dUtils = null;
		try {
			dUtils = new DyFormDefinitionUtils(formDefinition);
			return dUtils.getJSONObject4FieldDefinition();
		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public String getJsonString4FormDefinitionByFormUuid(String formUuid) {
		return this.getFormDefinitionByFormUuid(formUuid).getDefinitionJson();
	}

	public JSONObject getJSONObject4FormDefinitionByFormUuid(String formUuid) {
		try {
			DyFormDefinitionUtils dUtils = new DyFormDefinitionUtils(this.getFormDefinitionByFormUuid(formUuid));
			return dUtils.getJSONObject4FormDefinition();
		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public Map<String, Object> getDefaultFormData(String formUuid) throws JSONException {

		return dyFormDataService.getDefaultFormData(formUuid);
	}
	/*
		public List<Map<String, Object>> getFormDataOfParentNode(String formUuid, String dataUuidOfMainform, int pageNO,
				int pageSize) {
			select * from userform_ssxx_xgxk where rowid in(select rid from (select rownum rn,rid from(select rowid rid,uuid from

					userform_ssxx_xgxk  order by uuid desc) where rownum<100) where rn>1) order by uuid desc;
		}*/

}
