/*
 * @(#)2012-12-19 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wellsoft.pt.common.bean.LabelValueBean;
import com.wellsoft.pt.core.context.ApplicationContextHolder;
import com.wellsoft.pt.dytable.support.FormDataSignature;
import com.wellsoft.pt.org.entity.Department;
import com.wellsoft.pt.org.facade.OrgApiFacade;
import com.wellsoft.pt.repository.entity.mongo.file.MongoFileEntity;
import com.wellsoft.pt.repository.service.MongoFileService;

/**
 * Description: 如何描述该类
 * 
 * @author jiangmb
 * @date 2012-12-19
 * @version 1.0
 * 
 *          <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-12-19.1	jiangmb		2012-12-19		Create
 * </pre>
 * 
 */
public class FormAndDataBean implements Serializable {
	/** 如何描述serialVersionUID */
	private static final long serialVersionUID = 2806506916014437191L;
	private String formUuid;
	private String dataUuid;
	// 表单定义对象
	private RootTableInfoBean form;
	// 表单数据对象
	private Map<String, Object> data;
	//判断是否默认下载
	private String supportDown;

	// 表单数据签名信息
	private FormDataSignature signature;

	/**
	 * @return the supportDown
	 */
	public String getSupportDown() {
		return supportDown;
	}

	/**
	 * @param supportDown 要设置的supportDown
	 */
	public void setSupportDown(String supportDown) {
		this.supportDown = supportDown;
	}

	/**
	 * @return the formUuid
	 */
	public String getFormUuid() {
		return formUuid;
	}

	/**
	 * @param formUuid
	 *            要设置的formUuid
	 */
	public void setFormUuid(String formUuid) {
		this.formUuid = formUuid;
	}

	/**
	 * @return the dataUuid
	 */
	public String getDataUuid() {
		return dataUuid;
	}

	/**
	 * @param dataUuid
	 *            要设置的dataUuid
	 */
	public void setDataUuid(String dataUuid) {
		this.dataUuid = dataUuid;
	}

	public RootTableInfoBean getForm() {
		return form;
	}

	public void setForm(RootTableInfoBean form) {
		this.form = form;
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

	/**
	 * 判断是否有指定的字段映射名
	 * 
	 * @param fieldMappingName
	 * @return
	 */
	public boolean hasFieldMappingName(String fieldMappingName) {
		return StringUtils.isNotBlank(form.getFieldName(fieldMappingName));
	}

	/**
	 * @param tableName
	 * @return
	 */
	public Object getFormData(String tableName) {
		return this.data.get(tableName);
	}

	/**
	 * @param fieldName
	 * @return
	 * @see getFieldValueByMappingName
	 */
	public Object getFieldValue(String fieldName) {
		Object obj = data.get(form.getTableInfo().getTableName());
		if (obj instanceof Map) {
			Map map = (Map) obj;
			String fieldValue = new String();
			String value = new String();
			if ("20".equals(form.getFieldType(fieldName))) {
				if (map.get(fieldName) != null) {
					value = (String) map.get(fieldName);
					String[] values = value.split(",");
					for (int i = 0; i < values.length; i++) {
						fieldValue = values[1] + ";";
					}
				}
				return fieldValue;
			} else {
				return map.get(fieldName);
			}
		}
		return null;
	}

	/**
	 * 通过传入的映射名来获取字段值
	 * 
	 * @param string
	 * @return
	 */
	public Object getFieldValueByMappingName(String fieldMappingName) {
		Object obj = data.get(form.getTableInfo().getTableName());
		if (obj instanceof Map) {
			Map map = (Map) obj;
			String fieldValue = new String();
			String value = new String();
			if ("20".equals(form.getFieldType(form.getFieldName(fieldMappingName)))) {
				if (map.get(form.getFieldName(fieldMappingName)) != null) {
					value = (String) map.get(form.getFieldName(fieldMappingName));
					String[] values = value.split(",");
					for (int i = 0; i < values.length; i++) {
						fieldValue = values[1] + ";";
					}
				}
				return fieldValue;
			} else {
				return map.get(form.getFieldName(fieldMappingName));
			}
		}
		return null;
	}

	/**
	 * 根据映射名(包含字段名)获取动态表单的某字段的显示值
	 * @param fieldMappingName
	 * @return
	 */
	public String getFieldDisplayValueByMappingName(String fieldMappingName) {
		String fieldValues = "";
		Object obj = data.get(form.getTableInfo().getTableName());
		if (obj instanceof Map) {
			Map map = (Map) obj;
			String value = new String();
			String inputDataType = "";
			String fieldName = "";
			if (form.getFieldName(fieldMappingName) != null) {
				//说明传入的是映射名
				inputDataType = form.getFieldType(form.getFieldName(fieldMappingName));
				fieldName = form.getFieldName(fieldMappingName);
			} else {
				//说明传入的是字段名
				inputDataType = form.getFieldType(fieldMappingName);
				fieldName = fieldMappingName;
			}

			//如果输入样式是4，5，6即附件
			if ("4".equals(inputDataType) || "5".equals(inputDataType) || "6".equals(inputDataType)) {

				//				if (map.get(fieldName) != null) {
				//获得附件字段的值即nodename
				//				value = (String) map.get(fieldName);
				List<MongoFileEntity> fileEntities = new ArrayList<MongoFileEntity>();
				MongoFileService mongoFileService = ApplicationContextHolder.getBean(MongoFileService.class);
				fileEntities = mongoFileService.getFilesFromFolder((String) map.get("id"), fieldName);
				//					if (fieldName.equals("FILEUPLOAD")) {
				//						//根据附件字段的值去调用fileservice得到文件名
				//						fileEntities = (List<FileEntity>) map.get("fileupload");
				//					} else {
				//						//根据附件字段的值去调用fileservice得到文件名
				//						fileEntities = (List<FileEntity>) map.get("filelist" + fieldName.toLowerCase());
				//					}
				//					String fileNames = "";
				if (fileEntities != null) {
					for (MongoFileEntity fileEntity : fileEntities) {
						fieldValues += ";" + fileEntity.getFileName();
					}
					fieldValues = fieldValues.replaceFirst(";", "");
				}
				//				}
			} else if ("8".equals(inputDataType) || "9".equals(inputDataType) || "10".equals(inputDataType)
					|| "11".equals(inputDataType)) {
				OrgApiFacade orgApiFacade = ApplicationContextHolder.getBean(OrgApiFacade.class);
				if (map.get(fieldName) != null) {
					value = (String) map.get(fieldName);
					String[] values = value.split(";");
					for (int i = 0; i < values.length; i++) {
						String userName = orgApiFacade.getUserNameById(values[i]);
						if (userName != "" && userName != null) {
							fieldValues += userName;
						}
						Department department = orgApiFacade.getDepartmentById(values[i]);
						if (department.getName() != null && department.getName() != "") {
							fieldValues += department.getName();
						}
					}
				}
			} else if ("18".equals(inputDataType) || "17".equals(inputDataType)) {
				//复选框的解析或单选框的解析
				if (map.get(fieldName) != null) {
					value = (String) map.get(fieldName);
					String[] values = value.split(";");
					List<ColInfoBean> colInfoBeans = form.getTableInfo().getFields();
					for (int i = 0; i < values.length; i++) {
						for (ColInfoBean colInfoBean : colInfoBeans) {
							List<LabelValueBean> labelValueBeans = colInfoBean.getOptions();
							for (LabelValueBean labelValueBean : labelValueBeans) {
								if (labelValueBean.getValue().equals(values[i])) {
									fieldValues += labelValueBean.getLabel();
								}
							}
						}
					}
				}
			} else {
				Object fieldValue = getFieldValueByMappingName(fieldMappingName);
				return fieldValue == null ? "" : fieldValue.toString();
			}
		}
		return fieldValues;
	}

	/**
	 * 获取字段类型定义为附件的字段名列表
	 * @return
	 */
	/*
	public List<String> getAttachmentFields() {
		List<String> list = new ArrayList<String>();
		Object obj = data.get(form.getTableInfo().getTableName());
		if (obj instanceof Map) {
			Map map = (Map) obj;
			String fieldValue = new String();
			String value = new String();
			List<ColInfoBean> lists = form.getTableInfo().getFields();
			for (ColInfoBean colInfoBean : lists) {
				//获取各个字段的输入样式
				String inputDataType = colInfoBean.getInputDataType();
				//如果输入样式是4，5，6即附件
				if ("4".equals(inputDataType) || "5".equals(inputDataType) || "6".equals(inputDataType)) {
					String fieldName = colInfoBean.getColEnName();
					if (map.get(fieldName) != null) {
						//获得附件字段的值即nodename
						value = (String) map.get(fieldName);
						List<FileEntity> fileEntities = new ArrayList<FileEntity>();
						if (fieldName.equals("FILEUPLOAD") || fieldName.equals("fileupload")) {
							//根据附件字段的值去调用fileservice得到文件名
							fileEntities = (List<FileEntity>) map.get("filelist");
						} else {
							//根据附件字段的值去调用fileservice得到文件名
							fileEntities = (List<FileEntity>) map.get("filelist" + fieldName.toLowerCase());
						}
						String fileNames = "";
						for (FileEntity fileEntity : fileEntities) {
							fileNames += ";" + fileEntity.getFilename();
						}
						fileNames = fileNames.replaceFirst(";", "");
						list.add(fileNames);
					}
				}
			}
		}
		return list;
	}
	*/

	/**
	 * 
	 * 根据传入的标示符判断是否防止下载
	 *
	 */
	public void setIsDownFile(Boolean down) {
		if (down == true) {
			supportDown = "1";
		} else if (down == false) {
			supportDown = "2";
		}
	}

	/**
	 * 
	 * 根据映射名设置对应的字段值
	 * 
	 * @param mappingName
	 * @param value
	 */
	public void setFieldValueByMappingName(String mappingName, Object value) {
		Object obj = data.get(form.getTableInfo().getTableName());
		if (obj == null) {
			obj = new HashMap();
			data.put(form.getTableInfo().getTableName(), obj);
		}
		Map map = new HashMap();
		if (obj instanceof Map) {
			map = (Map) obj;
			if (form.getFieldName(mappingName) != null) {
				map.put(form.getFieldName(mappingName), value);
			}
		}
	}

	/**
	 * 通过传入的映射名来获取字段名以及字段值以及表名
	 * 
	 * @param fieldMappingName
	 * @return
	 */
	public Object getFieldNameandValueByMappingName(String fieldMappingName) {
		Object obj = data.get(form.getTableInfo().getTableName());
		//		Map<String, Object> map = new HashMap<String, Object>();
		Map map = (Map) obj;
		if (obj instanceof Map) {
			map.put("tableName", form.getTableInfo().getTableName());
			map.put("fieldName", form.getFieldName(fieldMappingName));
			map.put("fieldValue", map.get(form.getFieldName(fieldMappingName)));
		}
		return map;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public Map getMainFormData() {
		Object obj = data.get(form.getTableInfo().getTableName());
		if (obj instanceof Map) {
			return (Map) obj;
		}
		return new HashMap();
	}

	/**
	 * 设置表字段在表单中是否隐藏
	 * 
	 * @param tableName
	 *            表名
	 * @param list
	 *            需要隐藏字段
	 */
	public void setFieldHiddens(String tableName, List<String> list) {
		List<ColInfoBean> fields = getColList(tableName);
		for (String colName : list) {
			for (ColInfoBean col : fields) {
				if (StringUtils.equals(colName, col.getColEnName())) {
					col.setHidden(true);
					break;
				}
			}
		}
	}

	/**
	 * 设置表单字段是否是只读
	 * 
	 * @param tableName
	 *            表名
	 * @param list
	 *            只读字段列表
	 */
	public void setFieldOnlyReads(String tableName, List<String> list) {
		List<ColInfoBean> fields = getColList(tableName);
		for (String colName : list) {
			for (ColInfoBean col : fields) {
				if (StringUtils.equals(colName, col.getColEnName())) {
					col.setEdit(false);
					break;
				}
			}
		}
	}

	/**
	 * 
	 * 设置所有的字段为只读状态
	 * 
	 * @param tableName
	 */
	public void setAllFieldReads(String tableName) {
		List<ColInfoBean> fields = getColList(tableName);
		for (ColInfoBean col : fields) {
			col.setEdit(false);
		}
	}

	/**
	 * 设置表单字段展示形式为隐藏文本域
	 * 
	 * @param tableName
	 *            表名
	 * @param list
	 *            只读字段列表
	 */
	public void setFieldDataShow(String tableName, List<String> list) {
		List<ColInfoBean> fields = getColList(tableName);
		for (String colName : list) {
			for (ColInfoBean col : fields) {
				if (StringUtils.equals(colName, col.getColEnName())) {
					col.setDataShow("2");
					break;
				}
			}
		}
	}

	/**
	 * 设置表单字段展示形式为隐藏文本域
	 * 
	 * @param tableName
	 *            表名
	 * @param list
	 *            只读字段列表
	 */
	public void setAllFieldDataShow() {
		String tableName = form.getTableInfo().getTableName();
		List<ColInfoBean> fields = getColList(tableName);
		for (ColInfoBean col : fields) {
			col.setDataShow("2");
		}
	}

	/**
	 * 设置表单字段是否是必填项
	 * 
	 * @param tableName
	 *            表名
	 * @param list
	 *            必填项列表
	 */
	public void setFieldRequireds(String tableName, List<String> list) {
		List<ColInfoBean> fields = getColList(tableName);
		for (String colName : list) {
			for (ColInfoBean col : fields) {
				if (StringUtils.equals(colName, col.getColEnName())) {
					List<CheckRuleBean> chkList = col.getCheckRules();
					for (CheckRuleBean rule : chkList) {
						if (StringUtils.equals(rule.getValue(), "1")) {
							chkList.remove(rule);
							break;
						}
					}
					chkList.add(new CheckRuleBean("1", "必填"));
					break;
				}
			}
		}
	}

	private List<ColInfoBean> getColList(String tableName) {
		List<ColInfoBean> list = new ArrayList<ColInfoBean>();

		if (StringUtils.equals(tableName, form.getTableInfo().getTableName())) {
			list = form.getTableInfo().getFields();
		} else {
			for (SubTableInfoBean tab : form.getSubTables()) {
				if (StringUtils.equals(tableName, tab.getTableName())) {
					list = tab.getFields();
					break;
				}
			}
		}
		return list;
	}

	public String getFormName(String uuid) {
		//主表获取
		if (StringUtils.equals(form.getTableInfo().getUuid(), uuid)) {
			return form.getTableInfo().getTableName();
		} else {//从表获取
			List<SubTableInfoBean> subTables = form.getSubTables();
			for (SubTableInfoBean subTableInfoBean : subTables) {
			}
		}
		return null;
	}
}
