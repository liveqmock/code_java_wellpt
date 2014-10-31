/*
 * @(#)2012-10-30 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.service.impl;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.wellsoft.pt.basicdata.datadict.entity.DataDictionary;
import com.wellsoft.pt.basicdata.facade.BasicDataApiFacade;
import com.wellsoft.pt.basicdata.printtemplate.entity.PrintTemplate;
import com.wellsoft.pt.common.bean.LabelValueBean;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo;
import com.wellsoft.pt.common.component.jqgrid.JqTreeGridNode;
import com.wellsoft.pt.common.component.tree.TreeNode;
import com.wellsoft.pt.common.enums.Separator;
import com.wellsoft.pt.core.dao.Page;
import com.wellsoft.pt.core.dao.PropertyFilter;
import com.wellsoft.pt.core.support.QueryData;
import com.wellsoft.pt.core.support.QueryInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.dytable.bean.CheckRuleBean;
import com.wellsoft.pt.dytable.bean.ColInfoBean;
import com.wellsoft.pt.dytable.bean.FormDefinitionBean;
import com.wellsoft.pt.dytable.bean.RootTableInfoBean;
import com.wellsoft.pt.dytable.bean.SubTableInfoBean;
import com.wellsoft.pt.dytable.bean.TableInfoBean;
import com.wellsoft.pt.dytable.bean.TreeNodeBean;
import com.wellsoft.pt.dytable.dao.DynamicFormHibernateDao;
import com.wellsoft.pt.dytable.dao.FieldCheckRuleDao;
import com.wellsoft.pt.dytable.dao.FieldDefinitionDao;
import com.wellsoft.pt.dytable.dao.FieldOptionDao;
import com.wellsoft.pt.dytable.dao.FieldRelationDao;
import com.wellsoft.pt.dytable.dao.FormDataDao;
import com.wellsoft.pt.dytable.dao.FormDefinitionDao;
import com.wellsoft.pt.dytable.dao.FormRelationDao;
import com.wellsoft.pt.dytable.entity.FieldCheckRule;
import com.wellsoft.pt.dytable.entity.FieldDefinition;
import com.wellsoft.pt.dytable.entity.FieldOption;
import com.wellsoft.pt.dytable.entity.FieldRelation;
import com.wellsoft.pt.dytable.entity.FormDefinition;
import com.wellsoft.pt.dytable.entity.FormRelation;
import com.wellsoft.pt.dytable.service.FormDefinitionService;
import com.wellsoft.pt.dytable.support.DytableConfig;
import com.wellsoft.pt.dytable.support.FormUtils;
import com.wellsoft.pt.utils.bean.BeanUtils;

/**
 * 
 * @ClassName: FormDefinitionService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author lilin
 */
@Service
@Transactional
public class FormDefinitionServiceImpl implements FormDefinitionService {
	private Logger logger = LoggerFactory.getLogger(FormDefinitionServiceImpl.class);

	private DecimalFormat versionFormat = new DecimalFormat("0.0");

	private static final String QUERY_TOPLEVEL_FORM = "select f from FormDefinition f where f.parentForm.uuid is null or f.parentForm.uuid =''";
	@Autowired
	private FormDefinitionDao formDefinitionDao;
	@Autowired
	private FieldDefinitionDao fieldDefinitionDao;
	@Autowired
	private DynamicFormHibernateDao dynamicFormDao;
	@Autowired
	private FieldOptionDao fieldOptionDao;
	@Autowired
	private FieldCheckRuleDao fieldCheckRuleDao;
	@Autowired
	private BasicDataApiFacade basicDataApiFacade;

	@Autowired
	private FormRelationDao formRelationDao;

	@Autowired
	private FieldRelationDao fieldRelationDao;
	@Autowired
	private FormDataDao formDataDao;

	/**
	 * 获取所有自定义表单列表
	 */
	public List<FormDefinition> getAllDynamicFormList() {
		return formDefinitionDao.getAll();
	}

	public List<FormDefinition> getAllTopDynamicFormList() {
		return formDefinitionDao.getTopLevel();
	}

	/**
	 * 保存自定义表单基本信息
	 * 
	 * @param entity
	 * @return
	 */
	public void saveFormDefinition(FormDefinition entity) {
		try {
			formDefinitionDao.save(entity);

		} catch (Exception ex) {
			ex.printStackTrace();

		}
	}

	/**
	 * 插入字段对应的选择项
	 * 
	 * @param fieldDefinition
	 * @param list
	 */
	public void saveFieldOption(FieldDefinition fieldDefinition, List<LabelValueBean> list) {
		if (list != null && list.size() > 0) {
			for (LabelValueBean lvb : list) {
				FieldOption option = new FieldOption();
				option.setFieldDefinition(fieldDefinition);
				option.setValue(lvb.getValue());
				option.setText(lvb.getLabel());
				fieldOptionDao.save(option);
			}
		}
	}

	/**
	 * 插入字段对应的校验项
	 * 
	 * @param fieldDefinition
	 * @param list
	 */
	public void saveFieldCheckRule(FieldDefinition fieldDefinition, List<CheckRuleBean> list) {
		if (list != null && list.size() > 0) {
			for (CheckRuleBean obj : list) {
				FieldCheckRule rule = new FieldCheckRule();
				rule.setFieldDefinition(fieldDefinition);
				rule.setValue(obj.getValue());
				rule.setLabel(obj.getLabel());
				rule.setCheckRule(obj.getRule());
				fieldCheckRuleDao.save(rule);
			}
		}
	}

	/**
	 * 如何描述该方法
	 * 
	 * @param fcList
	 * @return
	 */
	private List<CheckRuleBean> getFieldCheckRuleBeanList(List<FieldCheckRule> fcList) {
		List<CheckRuleBean> list = new ArrayList<CheckRuleBean>();
		for (FieldCheckRule obj : fcList) {
			CheckRuleBean crb = new CheckRuleBean();
			crb.setValue(obj.getValue());
			crb.setLabel(obj.getLabel());
			crb.setRule(obj.getCheckRule());
			list.add(crb);
		}
		return list;
	}

	/**
	 * 如何描述该方法
	 * 
	 * @param foList
	 * @return
	 */
	private List<LabelValueBean> getFieldOptioinBeanList(FieldDefinition fieldDefinition, boolean getDataDict) {
		List<LabelValueBean> list = new ArrayList<LabelValueBean>();
		fieldDefinition.getFieldOptions();
		if ("17".equals(fieldDefinition.getInputType()) || "18".equals(fieldDefinition.getInputType())
				|| "19".equals(fieldDefinition.getInputType())) {// 下拉,单选,复选
			List<FieldOption> foList = fieldOptionDao.getListByFieldUid(fieldDefinition.getUuid());
			if (fieldDefinition.getOptionDataSource() != null && 2 == (fieldDefinition.getOptionDataSource())
					&& !foList.isEmpty()) {
				if (getDataDict) {
					// 数据字典
					List<DataDictionary> dicList = basicDataApiFacade.getDataDictionariesByType(foList.get(0)
							.getValue());
					// List<DataDictionary> dicList =
					// dataDic.getChildrenById("0101");
					for (DataDictionary obj : dicList) {
						LabelValueBean lvb = new LabelValueBean(obj.getCode(), obj.getName());
						list.add(lvb);
					}
					//					for (FieldOption fo : foList) {
					//						LabelValueBean lvb = new LabelValueBean(fo.getValue(), fo.getText());
					//						list.add(lvb);
					//					}
				} else {
					// 常量
					for (FieldOption fo : foList) {
						LabelValueBean lvb = new LabelValueBean(fo.getValue(), fo.getText());
						list.add(lvb);
					}
				}
			} else {// 常量
				for (FieldOption fo : foList) {
					LabelValueBean lvb = new LabelValueBean(fo.getValue(), fo.getText());
					list.add(lvb);
				}
			}
		}
		return list;
	}

	private String convertClobToString(Clob clob) {
		StringBuffer sb = new StringBuffer();
		Reader clobStream = null;
		try {
			clobStream = clob.getCharacterStream();
			char[] b = new char[60000];
			int i = 0;
			while ((i = clobStream.read(b)) != -1) {
				sb.append(b, 0, i);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("content:" + sb.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (clobStream != null) {
				try {
					clobStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 用户上传html模板，对模板进行相关设置，保存用户设置信息包括：主表信息，主表字段信息，主表与从表对应关系信息，从表表头与子表字段对应关系
	 * 
	 * @param rootTableInfo
	 * @return
	 */
	public RootTableInfoBean saveFormDefinition(RootTableInfoBean rootTableInfo) {
		// 保存主表信息
		FormDefinition formDefinition = new FormDefinition();
		TableInfoBean tableInfo = rootTableInfo.getTableInfo();
		formDefinition.setName(tableInfo.getTableName());
		formDefinition.setDescname(tableInfo.getTableDesc());
		formDefinition.setModuleId(tableInfo.getModuleId());
		formDefinition.setModuleName(tableInfo.getModuleName());
		formDefinition.setHtmlPath(tableInfo.getHtmlPath());
		formDefinition.setApplyTo(tableInfo.getApplyTo());
		formDefinition.setPrintTemplate(tableInfo.getPrintTemplate());
		formDefinition.setPrintTemplateName(tableInfo.getPrintTemplateName());
		formDefinition.setShowTableModel(tableInfo.getShowTableModel());
		formDefinition.setShowTableModelId(tableInfo.getShowTableModelId());
		formDefinition.setId(tableInfo.getId());
		formDefinition.setTableNum(tableInfo.getTableNum());
		formDefinition.setFormDisplay(tableInfo.getFormDisplay());
		formDefinition.setFormSign(tableInfo.getFormSign());
		//		formDefinition.setApplyTo(tableInfo)
		if (StringUtils.isEmpty(tableInfo.getUuid()) || "undefined".equals(tableInfo.getUuid())) {
			formDefinition.setVersion("1.0");
		} else {
			String version = tableInfo.getVersion();
			if ("1".equals(tableInfo.getIsUp())) {
				FormDefinition obj = formDefinitionDao.getMaxVersion(formDefinition.getName());
				DecimalFormat formate = new DecimalFormat("0.0");
				version = formate.format((Float.parseFloat(obj.getVersion()) + 0.1));
				formDefinition.setVersion(version);
			}
		}

		formDefinitionDao.save(formDefinition, tableInfo.getHtmlBodyContent());
		if (logger.isDebugEnabled()) {
			logger.debug("table's uuid is " + formDefinition.getUuid());
		}
		tableInfo.setUuid(formDefinition.getUuid());

		// 保存主表字段信息
		List<ColInfoBean> colInfoList = tableInfo.getFields();
		for (ColInfoBean colInfo : colInfoList) {
			FieldDefinition fieldDefinition = new FieldDefinition();
			fieldDefinition.setFormDefinition(formDefinition);
			fieldDefinition.setEntityName(tableInfo.getTableName());
			fieldDefinition.setFieldName(colInfo.getColEnName());
			fieldDefinition.setDescname(colInfo.getColCnName());
			fieldDefinition.setType(colInfo.getDataType());
			if (colInfo.getDataLength() == null || "".equals(colInfo.getDataLength())) {
				fieldDefinition.setLength(255);
			} else {
				fieldDefinition.setLength(colInfo.getDataLength());
			}
			fieldDefinition.setServiceName(colInfo.getServiceName());
			fieldDefinition.setMethodName(colInfo.getMethodName());
			fieldDefinition.setData(colInfo.getData());
			fieldDefinition.setDesignatedId(colInfo.getDesignatedId());
			fieldDefinition.setDesignatedType(colInfo.getDesignatedType());
			fieldDefinition.setIsOverride(colInfo.getIsOverride());
			fieldDefinition.setIsSaveDb(colInfo.getIsSaveDb());
			fieldDefinition.setUnEditDesignatedId(colInfo.getUnEditDesignatedId());
			fieldDefinition.setUnEditIsSaveDb(colInfo.getUnEditIsSaveDb());
			fieldDefinition.setUploadFileType(colInfo.getUploadFileType());
			fieldDefinition.setIsGetZhengWen(colInfo.getIsGetZhengWen());
			fieldDefinition.setDataShow(colInfo.getDataShow());
			fieldDefinition.setApplyTo(colInfo.getApplyTo());
			fieldDefinition.setDefaultValue(colInfo.getDefaultValue());
			fieldDefinition.setFontSize(colInfo.getFontSize());
			fieldDefinition.setFontColor(colInfo.getFontColor());
			fieldDefinition.setSetUrlOnlyRead(colInfo.getSetUrlOnlyRead());
			fieldDefinition.setJsFunction(colInfo.getJsFunction());
			fieldDefinition.setInputType(colInfo.getInputMode());
			fieldDefinition.setOptionDataSource(colInfo.getOptionDataSource());
			fieldDefinition.setTextareaRow(colInfo.getRows());
			fieldDefinition.setTextareaCol(colInfo.getCols());
			fieldDefinition.setFontWidth(colInfo.getFontWidth());
			fieldDefinition.setFontHight(colInfo.getFontHight());
			fieldDefinition.setTextAlign(colInfo.getTextAlign());
			fieldDefinition.setIsHide(colInfo.getIsHide());
			fieldDefinition.setDefaultValueWay(colInfo.getDefaultValueWay());
			fieldDefinition.setInputDataType(colInfo.getInputDataType());
			fieldDefinition.setRelationDataTwoSql(colInfo.getRelationDataTwoSql());
			fieldDefinition.setRelationDataText(colInfo.getRelationDataText());
			fieldDefinition.setRelationDataValue(colInfo.getRelationDataValue());
			fieldDefinition.setRelationDataSQL(colInfo.getRelationDataSql());
			fieldDefinition.setRelationDataShowType(colInfo.getRelationDataShowType());
			fieldDefinition.setRelationDataTextTwo(colInfo.getRelationDataTextTwo());
			fieldDefinition.setRelationDataValueTwo(colInfo.getRelationDataValueTwo());
			fieldDefinition.setRelationDataValueTwo(colInfo.getRelationDataValueTwo());
			fieldDefinition.setCtrlField(colInfo.getCtrlField());
			if (colInfo.getRelationDataDefiantion() != null && !colInfo.getRelationDataDefiantion().equals("")) {
				fieldDefinition.setRelationDataDefiantion(Hibernate.getLobCreator(fieldDefinitionDao.getSession())
						.createClob(colInfo.getRelationDataDefiantion()));
			}
			fieldDefinitionDao.save(fieldDefinition);
			// 保存字段选择项
			List<LabelValueBean> lvbList = colInfo.getOptions();
			saveFieldOption(fieldDefinition, lvbList);
			// 保存字段校验规则
			List<CheckRuleBean> crList = colInfo.getCheckRules();
			saveFieldCheckRule(fieldDefinition, crList);
		}

		// 保存主从表关系信息,字段映射关系
		List<SubTableInfoBean> subTableList1 = rootTableInfo.getSubTables();
		for (SubTableInfoBean subTableInfo : subTableList1) {
			FormRelation formRelation = new FormRelation();
			formRelation.setId(subTableInfo.getId());
			formRelation.setMainFormUuid(formDefinition.getUuid());
			formRelation.setSubFormUuid(subTableInfo.getTableId());
			formRelation.setEditMode(subTableInfo.getEditType());
			formRelation.setHideButtons(subTableInfo.getHideButtons());
			this.formRelationDao.save(formRelation);

			// 字段映射关系
			List<ColInfoBean> colMappingList = subTableInfo.getFields();
			for (ColInfoBean colMappingInfo : colMappingList) {
				FieldRelation fieldRelation = new FieldRelation();
				fieldRelation.setHeaderName(colMappingInfo.getThCnName());
				fieldRelation.setFieldUuid(colMappingInfo.getUuid());
				fieldRelation.setFieldOrder(colMappingInfo.getFieldOrder());
				fieldRelation.setUuid2(colMappingInfo.getUuid2());
				fieldRelation.setSubColHidden(colMappingInfo.getSubColHidden());
				fieldRelation.setSubColEdit(colMappingInfo.getSubColEdit());
				fieldRelation.setFieldWidth(colMappingInfo.getFieldWidth());
				fieldRelation.setFormRelation(formRelation);
				this.fieldRelationDao.save(fieldRelation);
			}
		}

		// 保存主从表关系信息,字段映射关系
		// List<SubTableInfoBean> subTableList = rootTableInfo.getSubTables();
		// if (subTableList != null && subTableList.size() > 0) {
		// for (SubTableInfoBean subTableInfo : subTableList) {
		//
		// MainSubTableRelation relation = new MainSubTableRelation();
		// relation.setId(subTableInfo.getId());
		// relation.setMainTableId(formDefinition.getUuid());
		// relation.setSubTableId(subTableInfo.getTableId());
		// relation.setEditType(subTableInfo.getEditType());
		// mainSubTableRelationDao.save(relation);
		//
		// //字段映射关系
		// List<ColInfoBean> colMappingList = subTableInfo.getFields();
		// for (ColInfoBean colMappingInfo : colMappingList) {
		// ThColMapping thColMapping = new ThColMapping();
		// thColMapping.setThCnName(colMappingInfo.getThCnName());
		// thColMapping.setFieldUuid(colMappingInfo.getUuid());
		// thColMapping.setMainSubTableRelation(relation);
		// thColMappingDao.save(thColMapping);
		// }
		// }
		// }

		// 根据配置信息创建表
		//		String hbmCfgXml = HibernateUtil.getHbmCfgXml(rootTableInfo);
		//		if (logger.isDebugEnabled()) {
		//			logger.debug("用户配置后生成相应的hbm文件内容(新建):" + hbmCfgXml);
		//		}
		//		Configuration config = new Configuration();
		//		config.addXML(hbmCfgXml);
		//		dynamicFormDao.addNewConfig(config);
		//		TableConfig tableconfig = new TableConfig(config);
		//		tableconfig.addTable();

		return rootTableInfo;
	}

	/**
	 * 用户修改表单配置信息(包括上传html模板)，更新用户设置信息包括：主表信息，主表字段信息，主表与从表对应关系信息，从表表头与子表字段对应关系
	 * 
	 * @param rootTableInfo
	 * @return
	 */
	public RootTableInfoBean updateupdFormDefinition(RootTableInfoBean rootTableInfo) {
		// 更新主表信息
		TableInfoBean tableInfo = rootTableInfo.getTableInfo();
		boolean isUp = false;
		if ("1".equals(tableInfo.getIsUp())) {
			isUp = true;
		}
		FormDefinition formDefinition = formDefinitionDao.get(tableInfo.getUuid());
		formDefinition.setName(tableInfo.getTableName());
		formDefinition.setDescname(tableInfo.getTableDesc());
		formDefinition.setModuleId(tableInfo.getModuleId());
		formDefinition.setModuleName(tableInfo.getModuleName());
		formDefinition.setHtmlPath(tableInfo.getHtmlPath());
		formDefinition.setApplyTo(tableInfo.getApplyTo());
		formDefinition.setPrintTemplate(tableInfo.getPrintTemplate());
		formDefinition.setPrintTemplateName(tableInfo.getPrintTemplateName());
		formDefinition.setShowTableModel(tableInfo.getShowTableModel());
		formDefinition.setShowTableModelId(tableInfo.getShowTableModelId());
		formDefinition.setId(tableInfo.getId());
		formDefinition.setTableNum(tableInfo.getTableNum());
		formDefinition.setFormDisplay(tableInfo.getFormDisplay());
		formDefinition.setFormSign(tableInfo.getFormSign());
		if (isUp) {
			FormDefinition temp = new FormDefinition();
			BeanUtils.copyProperties(formDefinition, temp);
			temp.setUuid(null);
			FormDefinition obj = formDefinitionDao.getMaxVersion(formDefinition.getName());
			DecimalFormat formate = new DecimalFormat("0.0");
			String version = formate.format((Float.parseFloat(obj.getVersion()) + 0.1));
			temp.setVersion(version);
			temp.setHtmlBodyContent(formDefinition.getHtmlBodyContent());
			formDefinition = temp;
		}
		if (StringUtils.isNotEmpty(tableInfo.getHtmlBodyContent())) {
			formDefinitionDao.save(formDefinition, tableInfo.getHtmlBodyContent());
		} else {
			formDefinitionDao.save(formDefinition);
		}
		tableInfo.setUuid(formDefinition.getUuid());

		if (logger.isDebugEnabled()) {
			logger.debug("更新后主表uuid:" + formDefinition.getUuid());
		}

		if (isUp) {
			// 保存主表字段信息
			List<ColInfoBean> colInfoList = tableInfo.getFields();
			for (ColInfoBean colInfo : colInfoList) {
				FieldDefinition fieldDefinition = new FieldDefinition();
				fieldDefinition.setFormDefinition(formDefinition);
				fieldDefinition.setEntityName(tableInfo.getTableName());
				fieldDefinition.setFieldName(colInfo.getColEnName());
				fieldDefinition.setDescname(colInfo.getColCnName());
				fieldDefinition.setType(colInfo.getDataType());
				if (colInfo.getDataLength() == null || "".equals(colInfo.getDataLength())) {
					fieldDefinition.setLength(255);
				} else {
					fieldDefinition.setLength(colInfo.getDataLength());
				}
				fieldDefinition.setFloatSet(colInfo.getFloatSet());
				fieldDefinition.setServiceName(colInfo.getServiceName());
				fieldDefinition.setMethodName(colInfo.getMethodName());
				fieldDefinition.setData(colInfo.getData());
				fieldDefinition.setDesignatedId(colInfo.getDesignatedId());
				fieldDefinition.setDesignatedType(colInfo.getDesignatedType());
				fieldDefinition.setIsOverride(colInfo.getIsOverride());
				fieldDefinition.setIsSaveDb(colInfo.getIsSaveDb());
				fieldDefinition.setUnEditDesignatedId(colInfo.getUnEditDesignatedId());
				fieldDefinition.setUnEditIsSaveDb(colInfo.getUnEditIsSaveDb());
				fieldDefinition.setUploadFileType(colInfo.getUploadFileType());
				fieldDefinition.setIsGetZhengWen(colInfo.getIsGetZhengWen());
				fieldDefinition.setDataShow(colInfo.getDataShow());
				fieldDefinition.setApplyTo(colInfo.getApplyTo());
				fieldDefinition.setDefaultValue(colInfo.getDefaultValue());
				fieldDefinition.setFontSize(colInfo.getFontSize());
				fieldDefinition.setFontColor(colInfo.getFontColor());
				fieldDefinition.setSetUrlOnlyRead(colInfo.getSetUrlOnlyRead());
				fieldDefinition.setJsFunction(colInfo.getJsFunction());
				fieldDefinition.setInputType(colInfo.getInputMode());
				fieldDefinition.setOptionDataSource(colInfo.getOptionDataSource());
				fieldDefinition.setTextareaRow(colInfo.getRows());
				fieldDefinition.setTextareaCol(colInfo.getCols());
				fieldDefinition.setFontWidth(colInfo.getFontWidth());
				fieldDefinition.setFontHight(colInfo.getFontHight());
				fieldDefinition.setTextAlign(colInfo.getTextAlign());
				fieldDefinition.setIsHide(colInfo.getIsHide());
				fieldDefinition.setDefaultValueWay(colInfo.getDefaultValueWay());
				fieldDefinition.setInputDataType(colInfo.getInputDataType());
				fieldDefinition.setRelationDataTwoSql(colInfo.getRelationDataTwoSql());
				fieldDefinition.setRelationDataText(colInfo.getRelationDataText());
				fieldDefinition.setRelationDataValue(colInfo.getRelationDataValue());
				fieldDefinition.setRelationDataSQL(colInfo.getRelationDataSql());
				fieldDefinition.setRelationDataShowType(colInfo.getRelationDataShowType());
				fieldDefinition.setRelationDataShowMethod(colInfo.getRelationDataShowMethod());
				fieldDefinition.setRelationDataTextTwo(colInfo.getRelationDataTextTwo());
				fieldDefinition.setRelationDataValueTwo(colInfo.getRelationDataValueTwo());
				fieldDefinition.setCtrlField(colInfo.getCtrlField());
				if (colInfo.getRelationDataDefiantion() != null && !colInfo.getRelationDataDefiantion().equals("")) {
					fieldDefinition.setRelationDataDefiantion(Hibernate.getLobCreator(fieldDefinitionDao.getSession())
							.createClob(colInfo.getRelationDataDefiantion()));
				}
				//				fieldDefinition.setRelationDataDefiantion(colInfo.getRelationDataDefiantion());
				fieldDefinitionDao.save(fieldDefinition);
				// 保存字段选择项
				List<LabelValueBean> lvbList = colInfo.getOptions();
				saveFieldOption(fieldDefinition, lvbList);
				// 保存字段校验规则
				List<CheckRuleBean> crList = colInfo.getCheckRules();
				saveFieldCheckRule(fieldDefinition, crList);
			}
		} else {
			// 保存主表字段信息
			List<ColInfoBean> colInfoList = tableInfo.getFields();
			//			Set<FieldDefinition> definition = formDefinition.getFieldDefinitions();
			//			for (FieldDefinition field : definition) {
			//				// 删除字段选择项
			//				fieldOptionDao.deleteByFieldUid(field.getUuid());
			//				// 删除字段校验规则
			//				fieldCheckRuleDao.deleteByFieldUid(field.getUuid());
			//				definition.remove(field);
			//				fieldDefinitionDao.delete(field);
			//			}
			for (ColInfoBean colInfo : colInfoList) {
				FieldDefinition fieldDefinition = new FieldDefinition();
				if (StringUtils.isNotBlank(colInfo.getUuid())) {
					fieldDefinition = this.fieldDefinitionDao.get(colInfo.getUuid());
				}
				fieldDefinition.setFormDefinition(formDefinition);
				fieldDefinition.setEntityName(tableInfo.getTableName());
				fieldDefinition.setFieldName(colInfo.getColEnName());
				fieldDefinition.setDescname(colInfo.getColCnName());
				fieldDefinition.setType(colInfo.getDataType());
				if (colInfo.getDataLength() == null || "".equals(colInfo.getDataLength())) {
					fieldDefinition.setLength(255);
				} else {
					fieldDefinition.setLength(colInfo.getDataLength());
				}
				fieldDefinition.setFloatSet(colInfo.getFloatSet());
				fieldDefinition.setServiceName(colInfo.getServiceName());
				fieldDefinition.setMethodName(colInfo.getMethodName());
				fieldDefinition.setData(colInfo.getData());
				fieldDefinition.setDesignatedId(colInfo.getDesignatedId());
				fieldDefinition.setDesignatedType(colInfo.getDesignatedType());
				fieldDefinition.setIsOverride(colInfo.getIsOverride());
				fieldDefinition.setIsSaveDb(colInfo.getIsSaveDb());
				fieldDefinition.setUnEditDesignatedId(colInfo.getUnEditDesignatedId());
				fieldDefinition.setUnEditIsSaveDb(colInfo.getUnEditIsSaveDb());
				fieldDefinition.setUploadFileType(colInfo.getUploadFileType());
				fieldDefinition.setIsGetZhengWen(colInfo.getIsGetZhengWen());
				fieldDefinition.setDataShow(colInfo.getDataShow());
				fieldDefinition.setApplyTo(colInfo.getApplyTo());
				fieldDefinition.setDefaultValue(colInfo.getDefaultValue());
				fieldDefinition.setFontSize(colInfo.getFontSize());
				fieldDefinition.setFontColor(colInfo.getFontColor());
				fieldDefinition.setSetUrlOnlyRead(colInfo.getSetUrlOnlyRead());
				fieldDefinition.setJsFunction(colInfo.getJsFunction());
				fieldDefinition.setInputType(colInfo.getInputMode());
				fieldDefinition.setOptionDataSource(colInfo.getOptionDataSource());
				fieldDefinition.setTextareaRow(colInfo.getRows());
				fieldDefinition.setTextareaCol(colInfo.getCols());
				fieldDefinition.setFontWidth(colInfo.getFontWidth());
				fieldDefinition.setFontHight(colInfo.getFontHight());
				fieldDefinition.setTextAlign(colInfo.getTextAlign());
				fieldDefinition.setIsHide(colInfo.getIsHide());
				fieldDefinition.setDefaultValueWay(colInfo.getDefaultValueWay());
				fieldDefinition.setInputDataType(colInfo.getInputDataType());
				fieldDefinition.setRelationDataTwoSql(colInfo.getRelationDataTwoSql());
				fieldDefinition.setRelationDataText(colInfo.getRelationDataText());
				fieldDefinition.setRelationDataValue(colInfo.getRelationDataValue());
				fieldDefinition.setRelationDataSQL(colInfo.getRelationDataSql());
				fieldDefinition.setRelationDataShowType(colInfo.getRelationDataShowType());
				fieldDefinition.setRelationDataShowMethod(colInfo.getRelationDataShowMethod());
				fieldDefinition.setRelationDataTextTwo(colInfo.getRelationDataTextTwo());
				fieldDefinition.setRelationDataValueTwo(colInfo.getRelationDataValueTwo());
				fieldDefinition.setCtrlField(colInfo.getCtrlField());
				if (colInfo.getRelationDataDefiantion() != null && !colInfo.getRelationDataDefiantion().equals("")) {
					fieldDefinition.setRelationDataDefiantion(Hibernate.getLobCreator(fieldDefinitionDao.getSession())
							.createClob(colInfo.getRelationDataDefiantion()));
				}
				//				fieldDefinition.setRelationDataDefiantion(colInfo.getRelationDataDefiantion());
				fieldDefinitionDao.save(fieldDefinition);
				// 删除字段选择项
				fieldOptionDao.deleteByFieldUid(fieldDefinition.getUuid());
				// 保存字段选择项
				List<LabelValueBean> lvbList = colInfo.getOptions();
				saveFieldOption(fieldDefinition, lvbList);
				// 删除字段校验规则
				fieldCheckRuleDao.deleteByFieldUid(fieldDefinition.getUuid());
				// 保存字段校验规则
				List<CheckRuleBean> crList = colInfo.getCheckRules();
				saveFieldCheckRule(fieldDefinition, crList);
			}
		}

		// 删除主从表关系信息
		FormRelation example = new FormRelation();
		example.setMainFormUuid(formDefinition.getUuid());
		List<FormRelation> formRelations = this.formRelationDao.findByExample(example);
		for (FormRelation formRelation : formRelations) {
			this.formRelationDao.delete(formRelation);
		}
		// 保存主从表关系信息,字段映射关系
		List<SubTableInfoBean> subTableList1 = rootTableInfo.getSubTables();
		for (SubTableInfoBean subTableInfo : subTableList1) {
			FormRelation formRelation = new FormRelation();
			formRelation.setId(subTableInfo.getId());
			formRelation.setMainFormUuid(formDefinition.getUuid());
			formRelation.setSubFormUuid(subTableInfo.getTableId());
			formRelation.setEditMode(subTableInfo.getEditType());
			formRelation.setHideButtons(subTableInfo.getHideButtons());
			formRelation.setTableTitle(subTableInfo.getTableTitle());
			formRelation.setTableOpen(subTableInfo.getTableOpen());
			formRelation.setIsGroupShowTitle(subTableInfo.getIsGroupShowTitle());
			formRelation.setGroupShowTitle(subTableInfo.getGroupShowTitle());
			formRelation.setSubformApplyTableId(subTableInfo.getSubformApplyTableId());
			formRelation.setSubrRelationDataDefiantion(subTableInfo.getSubrRelationDataDefiantion());
			this.formRelationDao.save(formRelation);

			// 字段映射关系
			List<ColInfoBean> colMappingList = subTableInfo.getFields();
			for (ColInfoBean colMappingInfo : colMappingList) {
				FieldRelation fieldRelation = new FieldRelation();
				fieldRelation.setHeaderName(colMappingInfo.getThCnName());
				fieldRelation.setUuid2(colMappingInfo.getUuid2());
				fieldRelation.setFieldUuid(colMappingInfo.getUuid());
				fieldRelation.setFieldOrder(colMappingInfo.getFieldOrder());
				fieldRelation.setSubColHidden(colMappingInfo.getSubColHidden());
				fieldRelation.setSubColEdit(colMappingInfo.getSubColEdit());
				fieldRelation.setFieldWidth(colMappingInfo.getFieldWidth());
				fieldRelation.setFormRelation(formRelation);
				this.fieldRelationDao.save(fieldRelation);
			}
		}

		// 更新主从表关系信息,字段映射关系(删除原有关系，再重新插入)
		// 删除
		// List<MainSubTableRelation> relationList =
		// mainSubTableRelationDao.getListByMainTableId(tableInfo.getUuid());
		// for (MainSubTableRelation relation : relationList) {
		// List<ThColMapping> thColMappingList =
		// thColMappingDao.getMappingListByRelationId(relation.getUuid());
		// for (ThColMapping mapping : thColMappingList) {
		// thColMappingDao.delete(mapping);
		// }
		// mainSubTableRelationDao.delete(relation);
		// thColMappingDao.flush();
		// mainSubTableRelationDao.flush();
		// }
		// //插入
		// List<SubTableInfoBean> subTableList = rootTableInfo.getSubTables();
		// if (subTableList != null && subTableList.size() > 0) {
		// for (SubTableInfoBean subTableInfo : subTableList) {
		//
		// MainSubTableRelation relation = new MainSubTableRelation();
		// relation.setId(subTableInfo.getId());
		// relation.setMainTableId(formDefinition.getUuid());
		// relation.setSubTableId(subTableInfo.getTableId());
		// relation.setEditType(subTableInfo.getEditType());
		// mainSubTableRelationDao.save(relation);
		//
		// //字段映射关系
		// List<ColInfoBean> colMappingList = subTableInfo.getFields();
		// for (ColInfoBean colMappingInfo : colMappingList) {
		// ThColMapping thColMapping = new ThColMapping();
		// thColMapping.setThCnName(colMappingInfo.getThCnName());
		// // FieldDefinition fieldDefinition = new FieldDefinition();
		// // fieldDefinition.setUuid(colMappingInfo.getUuid());
		// // fieldDefinition =
		// fieldDefinitionDao.get(colMappingInfo.getUuid());
		//
		// thColMapping.setFieldUuid(colMappingInfo.getUuid());
		// thColMapping.setMainSubTableRelation(relation);
		// thColMappingDao.save(thColMapping);
		// }
		// }
		// }

		// 根据配置信息修改表结构
		//		String hbmCfgXml = HibernateUtil.getHbmCfgXml(rootTableInfo);
		//		if (logger.isDebugEnabled()) {
		//			logger.debug("用户配置后生成相应的hbm文件内容(修改):" + hbmCfgXml);
		//		}
		//		Configuration config = new Configuration();
		//		config.addXML(hbmCfgXml);
		//		dynamicFormDao.addNewConfig(config);
		//		TableConfig tableconfig = new TableConfig(config);
		//		tableconfig.updateTable();

		return rootTableInfo;
	}

	/**
	 * 通过uuid查找对应的表单配置信息
	 * 
	 * @param uuid
	 * @param isHtmlBodyContent
	 * @return
	 */
	public RootTableInfoBean getRootTableInfoByUuid(String uuid, boolean isHtmlBodyContent, boolean getDataDict) {
		RootTableInfoBean rootTableInfo = new RootTableInfoBean();

		// 读取主表信息
		if (!uuid.equals("undefined") && StringUtils.isNotBlank(uuid)) {
			FormDefinition formDefinition = formDefinitionDao.get(uuid);
			Set<FieldDefinition> fieldList = formDefinition.getFieldDefinitions();// fieldDefinitionDao.getFieldListByTableId(uuid);

			if (logger.isDebugEnabled()) {
				logger.debug("主表uuid:" + uuid + ";字段记录数:" + fieldList.size());
			}

			List<ColInfoBean> colInfoList = new ArrayList<ColInfoBean>();
			for (FieldDefinition fieldDefinition : fieldList) {
				ColInfoBean colInfo = new ColInfoBean();
				colInfo.setUuid(fieldDefinition.getUuid());
				colInfo.setColCnName(fieldDefinition.getDescname());
				colInfo.setColEnName(fieldDefinition.getFieldName());
				colInfo.setDataLength(fieldDefinition.getLength());
				colInfo.setFloatSet(fieldDefinition.getFloatSet());
				colInfo.setServiceName(fieldDefinition.getServiceName());
				colInfo.setMethodName(fieldDefinition.getMethodName());
				colInfo.setData(fieldDefinition.getData());
				colInfo.setDesignatedId(fieldDefinition.getDesignatedId());
				colInfo.setDesignatedType(fieldDefinition.getDesignatedType());
				colInfo.setIsOverride(fieldDefinition.getIsOverride());
				colInfo.setIsSaveDb(fieldDefinition.getIsSaveDb());
				colInfo.setUnEditDesignatedId(fieldDefinition.getUnEditDesignatedId());
				colInfo.setUnEditIsSaveDb(fieldDefinition.getUnEditIsSaveDb());
				colInfo.setUploadFileType(fieldDefinition.getUploadFileType());
				colInfo.setIsGetZhengWen(fieldDefinition.getIsGetZhengWen());
				colInfo.setDataShow(fieldDefinition.getDataShow());
				colInfo.setApplyTo(fieldDefinition.getApplyTo());
				colInfo.setDefaultValue(fieldDefinition.getDefaultValue());
				colInfo.setFontColor(fieldDefinition.getFontColor());
				colInfo.setFontSize(fieldDefinition.getFontSize());
				colInfo.setJsFunction(fieldDefinition.getJsFunction());
				colInfo.setDataType(fieldDefinition.getType());
				colInfo.setInputMode(fieldDefinition.getInputType());
				colInfo.setOptionDataSource(fieldDefinition.getOptionDataSource());
				colInfo.setRows(fieldDefinition.getTextareaRow());
				colInfo.setCols(fieldDefinition.getTextareaCol());
				colInfo.setFontWidth(fieldDefinition.getFontWidth());
				colInfo.setFontHight(fieldDefinition.getFontHight());
				colInfo.setSetUrlOnlyRead(fieldDefinition.getSetUrlOnlyRead());
				colInfo.setTextAlign(fieldDefinition.getTextAlign());
				colInfo.setIsHide(fieldDefinition.getIsHide());
				colInfo.setDefaultValueWay(fieldDefinition.getDefaultValueWay());
				colInfo.setInputDataType(fieldDefinition.getInputDataType());
				colInfo.setRelationDataTwoSql(fieldDefinition.getRelationDataTwoSql());
				colInfo.setRelationDataText(fieldDefinition.getRelationDataText());
				colInfo.setRelationDataValue(fieldDefinition.getRelationDataValue());
				colInfo.setRelationDataSql(fieldDefinition.getRelationDataSql());
				colInfo.setRelationDataShowType(fieldDefinition.getRelationDataShowType());
				colInfo.setRelationDataShowMethod(fieldDefinition.getRelationDataShowMethod());
				colInfo.setRelationDataTextTwo(fieldDefinition.getRelationDataTextTwo());
				colInfo.setRelationDataValueTwo(fieldDefinition.getRelationDataValueTwo());
				colInfo.setCtrlField(fieldDefinition.getCtrlField());
				if (fieldDefinition.getRelationDataDefiantion() == null) {
					colInfo.setRelationDataDefiantion("");
				} else {
					try {
						colInfo.setRelationDataDefiantion(IOUtils.toString(
								fieldDefinition.getRelationDataDefiantion().getCharacterStream()).toString());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// List<FieldOption> foList =
				// fieldOptionDao.getListByFieldUid(fieldDefinition.getUuid());
				List<LabelValueBean> selectList = getFieldOptioinBeanList(fieldDefinition, getDataDict);
				colInfo.setOptions(selectList);
				List<FieldCheckRule> crList = fieldCheckRuleDao.getListByFieldUid(fieldDefinition.getUuid());
				colInfo.setCheckRules(getFieldCheckRuleBeanList(crList));
				colInfoList.add(colInfo);
			}

			TableInfoBean tableInfo = new TableInfoBean();
			tableInfo.setFields(colInfoList);
			tableInfo.setHtmlPath(formDefinition.getHtmlPath());
			tableInfo.setIsUp("0");
			tableInfo.setApplyTo(formDefinition.getApplyTo());
			tableInfo.setPrintTemplate(formDefinition.getPrintTemplate());
			tableInfo.setPrintTemplateName(formDefinition.getPrintTemplateName());
			tableInfo.setShowTableModel(formDefinition.getShowTableModel());
			tableInfo.setShowTableModelId(formDefinition.getShowTableModelId());
			tableInfo.setId(formDefinition.getId());
			tableInfo.setModuleId(formDefinition.getModuleId());
			tableInfo.setTableDesc(formDefinition.getDescname());
			tableInfo.setTableName(formDefinition.getName());
			tableInfo.setUuid(formDefinition.getUuid());
			tableInfo.setVersion(formDefinition.getVersion());
			tableInfo.setTableNum(formDefinition.getTableNum());
			tableInfo.setFormDisplay(formDefinition.getFormDisplay());
			tableInfo.setFormSign(formDefinition.getFormSign());
			if (isHtmlBodyContent && formDefinition.getHtmlBodyContent() != null) {
				tableInfo.setHtmlBodyContent(convertClobToString(formDefinition.getHtmlBodyContent()));
			}

			// 读取从表信息
			// List<MainSubTableRelation> relationList =
			// mainSubTableRelationDao.getListByMainTableId(uuid);
			FormRelation example = new FormRelation();
			example.setMainFormUuid(uuid);
			List<FormRelation> relationList = formRelationDao.findByExample(example);
			if (logger.isDebugEnabled()) {
				logger.debug("主表uuid:" + uuid + ";从表记录数:" + relationList.size());
			}

			List<SubTableInfoBean> subTables = new ArrayList<SubTableInfoBean>();
			for (FormRelation relation : relationList) {
				if (StringUtils.isBlank(relation.getSubFormUuid())) {
					continue;
				}
				SubTableInfoBean subTableInfo = new SubTableInfoBean();
				FormDefinition subFormDefinition = formDefinitionDao.get(relation.getSubFormUuid());
				subTableInfo.setId(relation.getId());
				subTableInfo.setApplyTo(subFormDefinition.getApplyTo());
				subTableInfo.setTableId(subFormDefinition.getUuid());
				subTableInfo.setTableName(subFormDefinition.getName());
				subTableInfo.setDescTableName(subFormDefinition.getDescname());
				subTableInfo.setEditType(relation.getEditMode());
				subTableInfo.setHideButtons(relation.getHideButtons());
				subTableInfo.setTableTitle(relation.getTableTitle());
				subTableInfo.setTableOpen(relation.getTableOpen());
				subTableInfo.setIsGroupShowTitle(relation.getIsGroupShowTitle());
				subTableInfo.setGroupShowTitle(relation.getGroupShowTitle());
				subTableInfo.setSubformApplyTableId(relation.getSubformApplyTableId());
				subTableInfo.setSubrRelationDataDefiantion(relation.getSubrRelationDataDefiantion());
				if (isHtmlBodyContent && "2".equals(subTableInfo.getEditType())) {
					subTableInfo.setHtmlBodyContent(convertClobToString(subFormDefinition.getHtmlBodyContent()));
				}
				// 通过关系表uuid查找对应的表头与字段的映射列表
				Set<FieldRelation> thColMappingList = relation.getFieldRelations();

				if (logger.isDebugEnabled()) {
					logger.debug("主从表映射uuid:" + relation.getUuid() + ";从表uuid:" + subFormDefinition.getUuid()
							+ ";html表头与字段映射记录数:" + thColMappingList.size());
				}

				List<ColInfoBean> colMappingList = new ArrayList<ColInfoBean>();
				// 获取从表全部字段列表
				List<FieldDefinition> fields = fieldDefinitionDao.getFieldListByTableId(subFormDefinition.getUuid());
				for (FieldRelation thColMapping : thColMappingList) {
					for (FieldDefinition fieldDefinition : fields) {
						if (!StringUtils.equals(fieldDefinition.getUuid(), thColMapping.getFieldUuid())) {
							continue;
						}
						ColInfoBean colInfo = new ColInfoBean();
						colInfo.setThCnName(thColMapping.getHeaderName());
						colInfo.setSubColHidden(thColMapping.getSubColHidden());
						colInfo.setSubColEdit(thColMapping.getSubColEdit());
						colInfo.setFieldWidth(thColMapping.getFieldWidth());
						colInfo.setUuid2(thColMapping.getUuid2());
						colInfo.setUuid(fieldDefinition.getUuid());
						colInfo.setColEnName(fieldDefinition.getFieldName());
						colInfo.setColCnName(fieldDefinition.getDescname());
						colInfo.setDataShow(fieldDefinition.getDataShow());
						colInfo.setDataType(fieldDefinition.getType());
						colInfo.setDataLength(fieldDefinition.getLength());
						colInfo.setFloatSet(fieldDefinition.getFloatSet());
						colInfo.setServiceName(fieldDefinition.getServiceName());
						colInfo.setMethodName(fieldDefinition.getMethodName());
						colInfo.setData(fieldDefinition.getData());
						colInfo.setDesignatedId(fieldDefinition.getDesignatedId());
						colInfo.setDesignatedType(fieldDefinition.getDesignatedType());
						colInfo.setIsOverride(fieldDefinition.getIsOverride());
						colInfo.setIsSaveDb(fieldDefinition.getIsSaveDb());
						colInfo.setUnEditDesignatedId(fieldDefinition.getUnEditDesignatedId());
						colInfo.setUnEditIsSaveDb(fieldDefinition.getUnEditIsSaveDb());
						colInfo.setUploadFileType(fieldDefinition.getUploadFileType());
						colInfo.setIsGetZhengWen(fieldDefinition.getIsGetZhengWen());
						colInfo.setApplyTo(fieldDefinition.getApplyTo());
						colInfo.setDefaultValue(fieldDefinition.getDefaultValue());
						colInfo.setFontColor(fieldDefinition.getFontColor());
						colInfo.setFontSize(fieldDefinition.getFontSize());
						colInfo.setSetUrlOnlyRead(fieldDefinition.getSetUrlOnlyRead());
						colInfo.setJsFunction(fieldDefinition.getJsFunction());
						colInfo.setInputMode(fieldDefinition.getInputType());
						colInfo.setOptionDataSource(fieldDefinition.getOptionDataSource());
						List<LabelValueBean> selectList = getFieldOptioinBeanList(fieldDefinition, getDataDict);
						colInfo.setOptions(selectList);
						List<FieldCheckRule> crList = fieldCheckRuleDao.getListByFieldUid(fieldDefinition.getUuid());
						colInfo.setCheckRules(getFieldCheckRuleBeanList(crList));
						colInfo.setRows(fieldDefinition.getTextareaRow());
						colInfo.setCols(fieldDefinition.getTextareaCol());
						colInfo.setFontWidth(fieldDefinition.getFontWidth());
						colInfo.setFontHight(fieldDefinition.getFontHight());
						colInfo.setTextAlign(fieldDefinition.getTextAlign());
						colInfo.setIsHide(fieldDefinition.getIsHide());
						colInfo.setDefaultValueWay(fieldDefinition.getDefaultValueWay());
						colInfo.setInputDataType(fieldDefinition.getInputDataType());
						colInfo.setRelationDataTwoSql(fieldDefinition.getRelationDataTwoSql());
						colInfo.setRelationDataText(fieldDefinition.getRelationDataText());
						colInfo.setRelationDataValue(fieldDefinition.getRelationDataValue());
						colInfo.setRelationDataSql(fieldDefinition.getRelationDataSql());
						colInfo.setRelationDataShowMethod(fieldDefinition.getRelationDataShowMethod());
						colInfo.setRelationDataShowType(fieldDefinition.getRelationDataShowType());
						colInfo.setRelationDataTextTwo(fieldDefinition.getRelationDataTextTwo());
						colInfo.setRelationDataValueTwo(fieldDefinition.getRelationDataValueTwo());
						colInfo.setCtrlField(fieldDefinition.getCtrlField());
						//					colInfo.setRelationDataDefiantion(fieldDefinition.getRelationDataDefiantion());
						if (fieldDefinition.getRelationDataDefiantion() == null) {
							colInfo.setRelationDataDefiantion("");
						} else {
							try {
								colInfo.setRelationDataDefiantion(IOUtils.toString(
										fieldDefinition.getRelationDataDefiantion().getCharacterStream()).toString());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						colMappingList.add(colInfo);
					}
					subTableInfo.setFields(colMappingList);
				}
				subTables.add(subTableInfo);
			}

			rootTableInfo.setTableInfo(tableInfo);
			rootTableInfo.setSubTables(subTables);
		}
		return rootTableInfo;
	}

	/*
	 * public void deleteFormDefinition(FormDefinition entity) { try {
	 * //先删除实际的表单定义 List<FormDefinition> forms =
	 * formDefinitionDao.getChildren(entity.getUuid()); //删除对应的field配置 for
	 * (FormDefinition form : forms) { List<FieldDefinition> fields =
	 * fieldDefinitionDao.getFiledByEntityName(form.getName()); for
	 * (FieldDefinition field : fields) { fieldDefinitionDao.delete(field); }
	 * formDefinitionDao.delete(form); } //删除表单
	 * formDefinitionDao.delete(entity);
	 * 
	 * } catch (Exception ex) { ex.printStackTrace();
	 * 
	 * } }
	 */

	public boolean deleteFormDefinition(String uuid) {
		FormDefinition entity = formDefinitionDao.get(uuid);
		RootTableInfoBean rootTableInfo = getRootTableInfoByUuid(entity.getUuid(), false, false);

		// 查找主表对应的从表对应关系列表
		// List<MainSubTableRelation> relationList =
		// mainSubTableRelationDao.getListByMainTableId(entity.getUuid());
		FormRelation example = new FormRelation();
		example.setMainFormUuid(entity.getUuid());
		List<FormRelation> formRelations = this.formRelationDao.findByExample(example);

		if (logger.isDebugEnabled()) {
			logger.debug("主表uuid:" + entity.getUuid() + ";从表个数:" + formRelations.size());
		}

		// 删除表映射,html表头与字段的映射
		for (FormRelation formRelation : formRelations) {
			this.formRelationDao.delete(formRelation);
		}

		// 删除字段
		// List<FieldDefinition> fieldList =
		// fieldDefinitionDao.findBy("tableId", entity.getUuid());
		// if (logger.isDebugEnabled()) {
		// logger.debug("主表uuid:" + entity.getUuid() + ";字段记录数:" +
		// fieldList.size());
		// }
		// for (FieldDefinition field : fieldList) {
		// //删除字段选择
		// fieldOptionDao.deleteByFieldUid(field.getUuid());
		// //删除字段校验规则
		// fieldCheckRuleDao.deleteByFieldUid(field.getUuid());
		// //删除字段
		// fieldDefinitionDao.delete(field);
		// }

		formDefinitionDao.delete(entity);
		formDefinitionDao.flush();
		List<FormDefinition> forms = formDefinitionDao.findBy("name", entity.getName());
		// 删除表结构(没有其它版本在使用此表)
		if (forms.size() == 0 || (forms.size() == 0 && forms.get(0).getUuid().equals(uuid))) {
			//			String hbmCfgXml = HibernateUtil.getHbmCfgXml(rootTableInfo);
			//			if (logger.isDebugEnabled()) {
			//				logger.debug("用户配置后生成相应的hbm文件内容(删除):" + hbmCfgXml);
			//			}
			//			Configuration config = new Configuration();
			//			config.addXML(hbmCfgXml);
			//			dynamicFormDao.addNewConfig(config);
			//			TableConfig tableconfig = new TableConfig(config);
			//			tableconfig.deleteTable();
			return true;
		}

		return false;
	}

	// 只删除子表数据
	public void deleteFormDefinitionChild(FormDefinition entity) {
		try {
			// 先删除实际的表单定义
			List<FormDefinition> forms = formDefinitionDao.getChildren(entity.getUuid());
			// 删除对应的field配置
			for (FormDefinition form : forms) {
				List<FieldDefinition> fields = fieldDefinitionDao.getFiledByEntityName(form.getName());
				for (FieldDefinition field : fields) {
					fieldDefinitionDao.delete(field);
				}
				formDefinitionDao.delete(form);
			}

		} catch (Exception ex) {
			ex.printStackTrace();

		}
	}

	/**
	 * 获取表单完整树数据
	 * 
	 * @return
	 */
	public List<TreeNodeBean> getFullTree(String parentId, String selectedId) {
		List<TreeNodeBean> list = Lists.newArrayList();
		if (StringUtils.isEmpty(parentId) || parentId.equals("-1")) {
			FormDefinition selectedForm = new FormDefinition();
			if (StringUtils.isNotEmpty(selectedId)) {
				selectedForm = formDefinitionDao.get(selectedId);
			}
			List<FormDefinition> forms = formDefinitionDao.getMaxVersionList();
			TreeNodeBean n1 = new TreeNodeBean("", "--请选择--");
			list.add(n1);
			for (FormDefinition obj : forms) {
				TreeNodeBean node = new TreeNodeBean(obj.getUuid(), obj.getDescname() + "(" + obj.getVersion() + ")");
				if (StringUtils.equals(obj.getName(), selectedForm.getName())
						&& !StringUtils.equals(obj.getVersion(), selectedForm.getVersion())) {
					//表名和被选中表的表名一样,但版本不一样，那么说明被选中表不是最高版本。
					//将被选中表的低版本做为最高版本的子节点，并设置被选中的版本的表为选中状态
					node.setIsParent("true");
					node.setOpen(true);
					List<TreeNodeBean> children = new ArrayList<TreeNodeBean>();

					List<FormDefinition> lists = formDefinitionDao.findBy("name", selectedForm.getName());//获取被选中表的所有版本
					for (int i = lists.size() - 1; i >= 0; i--) {
						FormDefinition form = lists.get(i);
						if (!StringUtils.equals(obj.getUuid(), form.getUuid())) {//设置被选中表的低版本为最高版本的子节点
							TreeNodeBean n = new TreeNodeBean(form.getUuid(), form.getDescname() + "("
									+ form.getVersion() + ")");
							if (StringUtils.equals(selectedId, form.getUuid())) {
								n.setChecked(true);//设置被选中的版本的表为选中状态
							}
							children.add(n);
						}
					}
					node.setChildren(children);
				} else {
					node.setIsParent("true");
					node.setOpen(false);
					if (StringUtils.equals(selectedId, obj.getUuid())) {
						node.setChecked(true);
					}
				}
				if (StringUtils.equals(obj.getVersion(), "1.0")) {// 初使版本
					node.setIsParent("false");
				}
				list.add(node);
			}
		} else {
			FormDefinition pForm = formDefinitionDao.get(parentId);
			List<FormDefinition> forms = formDefinitionDao.findBy("name", pForm.getName());
			/*TreeNodeBean n1 = new TreeNodeBean("", "--请选择--");
			list.add(n1);*/
			for (int i = forms.size() - 1; i >= 0; i--) {
				FormDefinition obj = forms.get(i);

				if (!StringUtils.equals(parentId, obj.getUuid())) {
					TreeNodeBean node = new TreeNodeBean(obj.getUuid(), obj.getDescname() + "(" + obj.getVersion()
							+ ")");
					list.add(node);
				}
			}
		}
		return list;
	}

	/**
	 * 搜索用户
	 */
	@Transactional
	public Page<FormDefinition> searchForm(final Page<FormDefinition> page, final List<PropertyFilter> filters) {
		return formDefinitionDao.searchForm(page, filters);
	}

	public Page<FormDefinition> searchFormDefinition(Page<FormDefinition> page) {

		Page<FormDefinition> formdefinitions = formDefinitionDao.findPage(page, this.QUERY_TOPLEVEL_FORM);
		for (FormDefinition form : formdefinitions.getResult()) {
			// form.setChildren(formDefinitionDao.getChildren(form.getUuid()));
		}
		return formdefinitions;
	}

	public List<FormDefinition> searchFormDefinition() {

		List<FormDefinition> formdefinitions = formDefinitionDao.find(this.QUERY_TOPLEVEL_FORM);
		for (FormDefinition form : formdefinitions) {
			// form.setChildren(formDefinitionDao.getChildren(form.getUuid()));
		}
		return formdefinitions;
	}

	public List<FieldDefinition> getFieldByForm(String uuid) {
		FormDefinition form = formDefinitionDao.get(uuid);
		if (form == null) {
			return new ArrayList<FieldDefinition>(0);
		}
		Set<FieldDefinition> fieldDefinitions = BeanUtils.convertCollection(form.getFieldDefinitions(),
				FieldDefinition.class);
		List<FieldDefinition> list = Arrays.asList(fieldDefinitions.toArray(new FieldDefinition[0]));
		List<FieldDefinition> list2 = new ArrayList<FieldDefinition>();
		for (FieldDefinition fieldDefinition : list) {
			fieldDefinition.setRelationDataDefiantion(null);
			list2.add(fieldDefinition);
		}
		return list2;
	}

	/**
	 * 根据表单UUID查找对应的表单定义信息对象
	 * 
	 * @param uid
	 * @return
	 */
	public FormDefinition getFormByUUID(String uid) {
		return formDefinitionDao.get(uid);
	}

	/**
	 * 根据表单UUID查找对应的表单定义信息对象
	 * 
	 * @param uid
	 * @return
	 */
	public FormDefinitionBean getFormDefinitionBeanByUUID(String uid) {
		FormDefinitionBean bean = new FormDefinitionBean();
		formDefinitionDao.get(uid).getHtmlBodyContent();
		String htmlBody = "";
		try {
			htmlBody = IOUtils.toString(formDefinitionDao.get(uid).getHtmlBodyContent().getCharacterStream());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		BeanUtils.copyProperties(formDefinitionDao.get(uid), bean);
		bean.setHtmlBody(htmlBody);
		return bean;
	}

	/**
	 * 获取最新版本或所有版本的表单定义信息列表
	 * 
	 * @param isAll
	 * @return
	 */
	public List<FormDefinition> getForms(boolean isAll) {
		if (isAll) {
			return formDefinitionDao.getAll();
		} else {
			return formDefinitionDao.getMaxVersionList();
		}
	}

	/**
	 * 按实体名获取最新版本的表单定义信息对象
	 * 
	 * @param entityName
	 * @return
	 */
	public FormDefinition getForm(String entityName) {
		return formDefinitionDao.getMaxVersion(entityName);
	}

	/**
	 * 按实体名获取所有版本的表单定义信息列表
	 * 
	 * @param entityName
	 * @return
	 */
	public List<FormDefinition> getForms(String entityName) {
		return formDefinitionDao.findBy("name", entityName);
	}

	/**
	 * 根据实体和版本号获取对应版本的表单定义信息对象
	 * 
	 * @param entityName
	 * @param version
	 * @return
	 */
	public FormDefinition getForm(String entityName, String version) {
		return (FormDefinition) formDefinitionDao.find("from FormDefinition where name = ? and version = ? ",
				new String[] { entityName, version }).get(0);
	}

	/** 
	 * 获取动态表单所有的打印模板
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.dytable.service.FormDefinitionService#getPrintTemplates(java.lang.String, java.lang.String)
	 */
	@Override
	public List getPrintTemplates(String treeNodeId) {
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		List templates = basicDataApiFacade.getAllPrintTemplates();

		for (Iterator iterator2 = templates.iterator(); iterator2.hasNext();) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId("-1");
			PrintTemplate template = (PrintTemplate) iterator2.next();
			PrintTemplate templateNew = new PrintTemplate();
			BeanUtils.copyProperties(template, templateNew);
			TreeNode child = new TreeNode();
			treeNode.setName(templateNew.getName());
			treeNode.setId(templateNew.getId());
			treeNode.setData(templateNew);
			treeNodes.add(treeNode);
		}
		return treeNodes;
	}

	/**
	 * 
	 * 获得显示单据的表单
	 * 
	 * @param s
	 * @return
	 */
	public List<TreeNode> getShowTableModels(String s, String flag) {
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		List<FormDefinition> fds = new ArrayList<FormDefinition>();
		if ("1".equals(flag)) {
			fds = formDefinitionDao.getShowTable();
		} else {
			fds = formDefinitionDao.getShowTable2();
		}
		for (Iterator iterator2 = fds.iterator(); iterator2.hasNext();) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId("-1");
			FormDefinition fd = (FormDefinition) iterator2.next();
			FormDefinition fdNew = new FormDefinition();
			BeanUtils.copyProperties(fd, fdNew);
			treeNode.setName(fdNew.getDescname());
			treeNode.setId(fdNew.getUuid());
			treeNode.setData(fdNew);
			treeNodes.add(treeNode);
		}
		return treeNodes;
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.dytable.service.FormDefinitionService#getForPageAsTree(com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo, com.wellsoft.pt.core.support.QueryInfo)
	 */
	@Override
	public QueryData getForPageAsTree(JqGridQueryInfo jqGridQueryInfo, QueryInfo queryInfo, String flag) {
		// 设置查询字段条件
		Map<String, Object> values = PropertyFilter.convertToMap(queryInfo.getPropertyFilters());
		// 查询父节点为null的部门
		List<QueryItem> results = null;
		SessionFactory sessionFactory = formDefinitionDao.getSessionFactory();
		Session session = formDefinitionDao.getSession();
		System.out.println(sessionFactory + "" + session);
		if (StringUtils.isBlank(jqGridQueryInfo.getNodeid())) {
			if ("1".equals(flag)) {
				results = this.formDefinitionDao.namedQuery("topFormDefinitionTreeQuery", values, QueryItem.class,
						queryInfo.getPagingInfo());
			} else {
				results = this.formDefinitionDao.namedQuery("topFormDefinitionTreeQuery2", values, QueryItem.class,
						queryInfo.getPagingInfo());
			}

		} else {
			String uuid = jqGridQueryInfo.getNodeid();
			FormDefinition parent = this.formDefinitionDao.get(uuid);
			values.put("parentUuid", uuid);
			values.put("id", parent.getId());
			results = this.formDefinitionDao.namedQuery("formDefinitionTreeQuery", values, QueryItem.class,
					queryInfo.getPagingInfo());
		}
		// results = pageData.getResult();
		List<JqTreeGridNode> retResults = new ArrayList<JqTreeGridNode>();

		int level = jqGridQueryInfo.getN_level() == null ? 0 : jqGridQueryInfo.getN_level() + 1;
		String parentId = jqGridQueryInfo.getNodeid() == null ? "null" : jqGridQueryInfo.getNodeid();
		for (int index = 0; index < results.size(); index++) {
			QueryItem item = results.get(index);
			JqTreeGridNode node = new JqTreeGridNode();
			node.setId(item.get("id").toString());// id
			List<Object> cell = node.getCell();
			cell.add(item.get("uuid"));// UUID
			cell.add(item.get("descname") + " (" + item.get("version") + ")");// name
			cell.add(item.get("name"));// name
			cell.add(item.get("version"));// version
			cell.add(item.get("moduleName"));// enabled
			cell.add(item.get("moduleId"));// formName
			cell.add(item.get("id"));// version
			cell.add(item.get("tableNum"));// version
			// level field
			cell.add(level);
			// parent id field
			cell.add(parentId);
			// leaf field
			if (StringUtils.isBlank(jqGridQueryInfo.getNodeid())) {
				if (Double.valueOf(1).toString().equals(item.get("version"))) {
					cell.add(true);
				} else {
					cell.add(this.formDefinitionDao.countById(item.get("id").toString()) <= 1);
				}
			} else {
				cell.add(true);
			}
			// expanded field	第一个节点展开
			if ("null".equals(parentId)) {
				cell.add(true);
			} else {
				cell.add(false);
			}

			retResults.add(node);
		}
		QueryData queryData = new QueryData();
		queryData.setDataList(retResults);
		queryData.setPagingInfo(queryInfo.getPagingInfo());
		return queryData;
	}

	/** 
	 * 获取动态表单下拉框的初始值
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.dytable.service.FormDefinitionService#getFormKeyValuePair(java.lang.String, java.lang.String)
	 */
	@Override
	public QueryItem getFormKeyValuePair(String s, String s1) {
		QueryItem queryitem = new QueryItem();
		if (StringUtils.isNotBlank(s1)) {
			String as[] = s1.split(Separator.SEMICOLON.getValue());
			String as1[];
			int j = (as1 = as).length;
			for (int i = 0; i < j; i++) {
				String s2 = as1[i];
				FormDefinition formDefinition = new FormDefinition();
				formDefinition.setUuid(s2);
				List<FormDefinition> list = formDefinitionDao.findByExample(formDefinition);
				if (list.size() > 0) {
					for (int k = 0; k < list.size(); k++) {
						FormDefinition formDefinition1 = (FormDefinition) list.get(k);
						String s3 = formDefinition1.getDescname();
						if (s2.equals(formDefinition1.getUuid())) {
							if (queryitem.get("value") == null) {
								queryitem.put("label", formDefinition1.getModuleName() + "/" + s3);
								queryitem.put("value", s2);
							} else {
								queryitem.put(
										"label",

										(new StringBuilder()).append(queryitem.get("label"))
												.append(Separator.SEMICOLON.getValue())
												.append(formDefinition1.getModuleName() + "/" + s3).toString());
								queryitem.put(
										"value",
										(new StringBuilder()).append(queryitem.get("value"))
												.append(Separator.SEMICOLON.getValue()).append(s2).toString());
							}
						}
					}
				} else {
					queryitem.put("label", s2);
					queryitem.put("value",
							(new StringBuilder()).append(queryitem.get("value")).append(Separator.SEMICOLON.getValue())
									.append(s2).toString());
				}
			}

		}
		return queryitem;

	}

	/** 
	 * 根据formUuid获取字段的树形
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.dytable.service.FormDefinitionService#getFieldByFormUuid(java.lang.String, java.lang.String)
	 */
	@Override
	public List<TreeNode> getFieldByFormUuid(String s, String formUuid) {
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		List<FieldDefinition> fieldDefinitions = getFieldByForm(formUuid);
		for (Iterator iterator2 = fieldDefinitions.iterator(); iterator2.hasNext();) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId("-1");
			FieldDefinition fd = (FieldDefinition) iterator2.next();
			FieldDefinition fdNew = new FieldDefinition();
			BeanUtils.copyProperties(fd, fdNew);
			if (fdNew.getDescname() == null || fdNew.getDescname().equals("")) {
				treeNode.setName(fdNew.getFieldName());
			} else {
				treeNode.setName(fdNew.getDescname());
			}
			treeNode.setId(fdNew.getFieldName());
			treeNode.setData(fdNew);
			treeNodes.add(treeNode);
		}
		return treeNodes;
	}

	/**
	 * 
	 * 验证满足验证条件里设置的数据是否为空
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.dytable.service.FormDefinitionService#verificationFormData(java.lang.String, java.lang.String)
	 */
	@Override
	public String verificationFormData(String formId, String formDataUuid) {
		// TODO Auto-generated method stub
		String result = "";
		Map mainTableData = FormUtils.createColumnMap();
		RootTableInfoBean rootTableInfo = this.getRootTableInfoByUuid(formId, true, true);
		String tableName = rootTableInfo.getTableInfo().getTableName();
		List<Map> list = formDataDao.getFormDataBySql(tableName, "UUID='" + formDataUuid + "'");
		if (list.size() > 0) {
			mainTableData = list.get(0);//获取的表单数据
		}
		List<ColInfoBean> colInfoBeans = rootTableInfo.getTableInfo().getFields();
		//验证表单信息
		for (ColInfoBean colInfoBean : colInfoBeans) {
			List<CheckRuleBean> crs = colInfoBean.getCheckRules();
			for (CheckRuleBean cr : crs) {
				if (cr.getValue() != null
						&& Integer.parseInt(cr.getValue()) == DytableConfig.DYTABLE_VERIFICATION_NOTNULL) {
					String colValue = mainTableData.get(colInfoBean.getColEnName().toLowerCase()) == null ? ""
							: mainTableData.get(colInfoBean.getColEnName().toLowerCase()).toString();
					if (colValue.equals("")) {
						result += colInfoBean.getColEnName() + "不能为空;";
					}
				}
			}
		}
		if (result.equals("")) {
			return "success";
		} else {
			return result;
		}
	}

	/**
	 * 
	 * 验证动态表单数据是否满足验证
	 * 返回不满足的字段
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.dytable.service.FormDefinitionService#verificationFormData(java.lang.String, java.lang.String)
	 */
	@Override
	public Map<Integer, List<Map<String, String>>> verificationDytableData(String formId, List<Map> mainTableDatas) {
		// TODO Auto-generated method stub
		Map<Integer, List<Map<String, String>>> resultMap = new HashMap<Integer, List<Map<String, String>>>();
		int i = 1;
		for (Map mainTableData : mainTableDatas) {
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			RootTableInfoBean rootTableInfo = this.getRootTableInfoByUuid(formId, true, true);
			String tableName = rootTableInfo.getTableInfo().getTableName();
			List<ColInfoBean> colInfoBeans = rootTableInfo.getTableInfo().getFields();
			//验证表单信息
			for (ColInfoBean colInfoBean : colInfoBeans) {
				//验证最大长度
				if (colInfoBean.getDataType() != null
						&& ("1".equals(colInfoBean.getDataType()) || "13".equals(colInfoBean.getDataType()) || "14"
								.equals(colInfoBean.getDataType()))) {//类型为字符串，整形时
					String colValue = mainTableData.get(colInfoBean.getColEnName().toLowerCase()) == null ? ""
							: mainTableData.get(colInfoBean.getColEnName().toLowerCase()).toString();
					if (colValue.length() > colInfoBean.getDataLength()) {
						Map<String, String> map = new HashMap<String, String>();
						map.put(colInfoBean.getColEnName(), "最大长度为" + colInfoBean.getDataLength() + "位");
						list.add(map);
					}
				}
				//验证是否满足验证条件
				List<CheckRuleBean> crs = colInfoBean.getCheckRules();
				for (CheckRuleBean cr : crs) {
					if (cr.getValue() != null
							&& Integer.parseInt(cr.getValue()) == DytableConfig.DYTABLE_VERIFICATION_NOTNULL) {//不为空
						String colValue = mainTableData.get(colInfoBean.getColEnName().toLowerCase()) == null ? ""
								: mainTableData.get(colInfoBean.getColEnName().toLowerCase()).toString();
						if (colValue.equals("")) {
							Map<String, String> map = new HashMap<String, String>();
							map.put(colInfoBean.getColEnName(), "不能为空");
							list.add(map);
						}
					} else if (cr.getValue() != null
							&& Integer.parseInt(cr.getValue()) == DytableConfig.DYTABLE_VERIFICATION_UNIQUE) {//唯一
						String colValue = mainTableData.get(colInfoBean.getColEnName().toLowerCase()) == null ? ""
								: mainTableData.get(colInfoBean.getColEnName().toLowerCase()).toString();
						if (!colValue.equals("")) {
							List<Map> list1 = formDataDao.getFormDataBySql(tableName, colInfoBean.getColEnName() + "='"
									+ colValue + "'");
							String uuid_ = mainTableData.get("uuid") == null ? "" : mainTableData.get("uuid")
									.toString();
							if (StringUtils.isBlank(uuid_)) {//添加
								if (list1.size() > 0) {
									Map<String, String> map = new HashMap<String, String>();
									map.put(colInfoBean.getColEnName(), "不能插入相同的数据");
									list.add(map);
								}
							} else {
								if (list1.size() > 0) {//修改
									String uuidTemp = list1.get(0).get("uuid").toString();
									if (!uuidTemp.equals(uuid_)) {
										Map<String, String> map = new HashMap<String, String>();
										map.put(colInfoBean.getColEnName(), "不能插入相同的数据");
										list.add(map);
									}
								}
							}
						}
					} else if (cr.getValue() != null
							&& Integer.parseInt(cr.getValue()) == DytableConfig.DYTABLE_VERIFICATION_EMAIL) {//email
						String colValue = mainTableData.get(colInfoBean.getColEnName().toLowerCase()) == null ? ""
								: mainTableData.get(colInfoBean.getColEnName().toLowerCase()).toString();
						if (!colValue.equals("")) {
							Pattern pattern = Pattern
									.compile("^/w+([-.]/w+)*@/w+([-]/w+)*/.(/w+([-]/w+)*/.)*[a-z]{2,3}$");
							Matcher matcher = pattern.matcher(colValue);
							if (!matcher.matches()) {
								Map<String, String> map = new HashMap<String, String>();
								map.put(colInfoBean.getColEnName(), "该值必需为email");
								list.add(map);
							}
						}
					} else if (cr.getValue() != null
							&& Integer.parseInt(cr.getValue()) == DytableConfig.DYTABLE_VERIFICATION_URL) {//url校验
						//					String colValue = mainTableData.get(colInfoBean.getColEnName().toLowerCase()) == null ? "" : mainTableData.get(
						//							colInfoBean.getColEnName().toLowerCase()).toString();
						//					if (!colValue.equals("")) {
						//						Pattern pattern = Pattern.compile("^/w+([-.]/w+)*@/w+([-]/w+)*/.(/w+([-]/w+)*/.)*[a-z]{2,3}$");
						//						Matcher matcher = pattern.matcher(colValue);
						//						if (!matcher.matches()) {
						//							Map map = new HashMap();
						//							map.put(colInfoBean.getColEnName(), "该值必需为url");
						//							list.add(map);
						//						}
						//					}
					} else if (cr.getValue() != null
							&& Integer.parseInt(cr.getValue()) == DytableConfig.DYTABLE_VERIFICATION_ISCARD) {//身份证校验
						String colValue = mainTableData.get(colInfoBean.getColEnName().toLowerCase()) == null ? ""
								: mainTableData.get(colInfoBean.getColEnName().toLowerCase()).toString();
						if (!colValue.equals("")) {
							if (!IDCardValidate(colValue)) {
								Map<String, String> map = new HashMap<String, String>();
								map.put(colInfoBean.getColEnName(), "该值必需为身份证");
								list.add(map);
							}
						}
					}
				}

			}
			if (list.size() > 0) {
				resultMap.put(i, list);
			}
			i++;
		}
		return resultMap;
	}

	/***********************验证身份证开始*****************************/
	public boolean IDCardValidate(String IDStr) {
		String errorInfo = "";//记录错误信息
		String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" };
		//String[] Checker = {"1","9","8","7","6","5","4","3","2","1","1"};
		String Ai = "";

		//================ 号码的长度 15位或18位 ================
		if (IDStr.length() != 15 && IDStr.length() != 18) {
			errorInfo = "号码长度应该为15位或18位。";
			System.out.println(errorInfo);
			return false;
		}
		//=======================(end)======================== 

		//================ 数字 除最后以为都为数字 ================
		if (IDStr.length() == 18) {
			Ai = IDStr.substring(0, 17);
		} else if (IDStr.length() == 15) {
			Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
		}
		if (isNumeric(Ai) == false) {
			errorInfo = "15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
			System.out.println(errorInfo);
			return false;
		}
		//=======================(end)========================

		//================ 出生年月是否有效 ================
		String strYear = Ai.substring(6, 10);//年份
		String strMonth = Ai.substring(10, 12);//月份
		String strDay = Ai.substring(12, 14);//月份

		if (this.isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
			errorInfo = "生日无效。";
			System.out.println(errorInfo);
			return false;
		}

		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
					|| (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
				errorInfo = "生日不在有效范围。";
				System.out.println(errorInfo);
				return false;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			errorInfo = "月份无效";
			System.out.println(errorInfo);
			return false;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			errorInfo = "日期无效";
			System.out.println(errorInfo);
			return false;
		}
		//=====================(end)=====================

		//================ 地区码时候有效 ================
		Hashtable h = GetAreaCode();
		if (h.get(Ai.substring(0, 2)) == null) {
			errorInfo = "地区编码错误。";
			System.out.println(errorInfo);
			return false;
		}
		//==============================================

		//================ 判断最后一位的值 ================
		int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi + Integer.parseInt(String.valueOf(Ai.charAt(i))) * Integer.parseInt(Wi[i]);
		}
		int modValue = TotalmulAiWi % 11;
		String strVerifyCode = ValCodeArr[modValue];
		Ai = Ai + strVerifyCode;

		if (IDStr.length() == 18) {
			if (Ai.equals(IDStr) == false) {
				errorInfo = "身份证无效，最后一位字母错误";
				System.out.println(errorInfo);
				return false;
			}
		} else {
			System.out.println("所在地区:" + h.get(Ai.substring(0, 2).toString()));
			System.out.println("新身份证号:" + Ai);
			return true;
		}
		//=====================(end)=====================
		System.out.println("所在地区:" + h.get(Ai.substring(0, 2).toString()));
		return true;
	}

	/**======================================================================
	 * 功能：判断字符串是否为数字
	 * @param str
	 * @return
	 */
	private boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
		/*判断一个字符时候为数字
		if(Character.isDigit(str.charAt(0)))
		{
		 return true;
		}
		else
		{
		 return false;
		}*/
	}

	/**======================================================================
	 * 功能：判断字符串是否为日期格式
	 * @param str
	 * @return
	 */
	public boolean isDate(String strDate) {
		Pattern pattern = Pattern
				.compile("^((/d{2}(([02468][048])|([13579][26]))[/-///s]?((((0?[13578])|(1[02]))[/-///s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[/-///s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[/-///s]?((0?[1-9])|([1-2][0-9])))))|(/d{2}(([02468][1235679])|([13579][01345789]))[/-///s]?((((0?[13578])|(1[02]))[/-///s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[/-///s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[/-///s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(/s(((0?[0-9])|([1-2][0-3]))/:([0-5]?[0-9])((/s)|(/:([0-5]?[0-9])))))?$");
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	private Hashtable GetAreaCode() {
		Hashtable hashtable = new Hashtable();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}
	/***********************验证身份证结束*****************************/
}
