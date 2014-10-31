/*
 * @(#)2013-3-14 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.view.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.basicdata.datadict.service.DataDictionaryService;
import com.wellsoft.pt.basicdata.excelexporttemplate.entity.ExcelExportDefinition;
import com.wellsoft.pt.basicdata.facade.BasicDataApiFacade;
import com.wellsoft.pt.basicdata.systemtable.entity.SystemTable;
import com.wellsoft.pt.basicdata.systemtable.entity.SystemTableAttribute;
import com.wellsoft.pt.basicdata.systemtable.entity.SystemTableRelationship;
import com.wellsoft.pt.basicdata.view.bean.ViewDefinitionNewBean;
import com.wellsoft.pt.basicdata.view.dao.ViewDefinitionNewDao;
import com.wellsoft.pt.basicdata.view.entity.ColumnDefinitionNew;
import com.wellsoft.pt.basicdata.view.entity.CustomButtonNew;
import com.wellsoft.pt.basicdata.view.entity.ViewDefinitionNew;
import com.wellsoft.pt.basicdata.view.provider.ViewColumnNew;
import com.wellsoft.pt.basicdata.view.provider.ViewDataSourceNew;
import com.wellsoft.pt.basicdata.view.service.GetViewDataNewService;
import com.wellsoft.pt.basicdata.view.service.ViewDefinitionNewService;
import com.wellsoft.pt.bpm.engine.form.CustomDynamicButton;
import com.wellsoft.pt.common.component.tree.TreeNode;
import com.wellsoft.pt.core.context.ApplicationContextHolder;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.dyform.entity.DyFormDefinition;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.FieldDefinition;
import com.wellsoft.pt.security.audit.entity.Resource;
import com.wellsoft.pt.security.facade.SecurityApiFacade;
import com.wellsoft.pt.utils.bean.BeanUtils;

/**
 * Description: 获取视图自定义时候所需要的数据
 *  
 * @author Administrator
 * @date 2013-3-14
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-3-14.1	Administrator		2013-3-14		Create
 * </pre>
 *
 */
@Service
@Transactional
public class GetViewDataNewServiceImpl implements GetViewDataNewService {

	@Autowired
	private DyFormApiFacade dyFormApiFacade;

	@Autowired
	private ViewDefinitionNewDao viewDefinitionNewDao;

	@Autowired
	private ViewDefinitionNewService viewDefinitionNewService;

	@Autowired
	private BasicDataApiFacade basicDataApiFacade;

	@Autowired
	private SecurityApiFacade securityApiFacade;

	@Autowired(required = false)
	private Map<String, ViewDataSourceNew> viewDataSourceMap;

	@Autowired
	private DataDictionaryService dataDictionaryService;

	public GetViewDataNewServiceImpl() {

	}

	public List getFormFields(String treeNodeId, String formUuid) {
		List treeNodes = new ArrayList();
		List fieldDefinitions = dyFormApiFacade.getFormDefinition(formUuid).getFieldDefintions();
		TreeNode treeNode;

		for (Iterator iterator = fieldDefinitions.iterator(); iterator.hasNext(); treeNodes.add(treeNode)) {
			FieldDefinition fieldDefinition = (FieldDefinition) iterator.next();
			treeNode = new TreeNode();
			treeNode.setId(fieldDefinition.getName());
			treeNode.setName(fieldDefinition.getDisplayName());
			treeNode.setData(fieldDefinition.getName());
		}

		return treeNodes;
	}

	/** 
	 * 返回数据导出模版的树
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.dyview.service.GetViewDataService#getAllExcelExportRules(java.lang.String)
	 */
	@Override
	public List getAllExcelExportRules(String s) {
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		List<ExcelExportDefinition> excelExportDefinitions = basicDataApiFacade.getAllExcelExportRules();
		TreeNode treeNode = new TreeNode();
		for (Iterator iterator = excelExportDefinitions.iterator(); iterator.hasNext();) {
			ExcelExportDefinition ed = (ExcelExportDefinition) iterator.next();
			ExcelExportDefinition edNew = new ExcelExportDefinition();
			BeanUtils.copyProperties(ed, edNew);
			TreeNode child = new TreeNode();
			child.setId(edNew.getUuid());
			child.setName(edNew.getName());
			treeNodes.add(child);
		}
		return treeNodes;
	}

	/** 
	 * 获取视图所有的数据来源
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.dyview.service.GetViewDataService#getForms(java.lang.String)
	 */
	@Override
	public List getForms(String treeNodeId, String id) {
		String moduleId = null;
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		if ("1".equals(id)) {//获取动态表单下面的所有表
			List forms = dyFormApiFacade.getAllFormDefinitions(true);
			TreeNode treeNode = new TreeNode();
			treeNode.setId("-1");
			List<TreeNode> modules = new ArrayList<TreeNode>();
			modules = dataDictionaryService.getFromTypeAsTreeAsync("-1", "MODULE_ID");

			for (Iterator iterator = modules.iterator(); iterator.hasNext();) {
				TreeNode module = (TreeNode) iterator.next();
				if (module.getData().equals(moduleId)) {
					List children = new ArrayList();
					for (Iterator iterator2 = forms.iterator(); iterator2.hasNext();) {
						DyFormDefinition form = (DyFormDefinition) iterator2.next();
						if (form.getModuleId().equals(moduleId)) {
							DyFormDefinition formNew = new DyFormDefinition();
							BeanUtils.copyProperties(form, formNew);
							TreeNode child = new TreeNode();
							child.setId(formNew.getName());
							child.setName(formNew.getDisplayName());
							child.setData(formNew.getDefinitionJson());
							children.add(child);
						}

					}
					module.setChildren(children);
					treeNodes.add(module);
				} else if (moduleId == null) {
					List children = new ArrayList();
					for (Iterator iterator2 = forms.iterator(); iterator2.hasNext();) {
						DyFormDefinition form = (DyFormDefinition) iterator2.next();
						DyFormDefinition formNew = new DyFormDefinition();
						BeanUtils.copyProperties(form, formNew);

						if (module.getData().equals(formNew.getModuleId())) {
							TreeNode child = new TreeNode();
							child.setId(formNew.getName());
							child.setName(formNew.getDisplayName());
							child.setData(formNew.getDefinitionJson());
							children.add(child);
						}
					}
					module.setChildren(children);
					treeNodes = modules;
				}
			}

		} else if ("2".equals(id) || "7".equals(id)) {//获取系统表的表定义
			List forms = basicDataApiFacade.getAllSystemTables();
			TreeNode treeNode;
			for (Iterator iterator = forms.iterator(); iterator.hasNext();) {
				SystemTable form = (SystemTable) iterator.next();
				SystemTable formNew = new SystemTable();
				BeanUtils.copyProperties(form, formNew);
				treeNode = new TreeNode();
				treeNode.setId(formNew.getUuid());
				treeNode.setName(formNew.getChineseName());
				treeNode.setData(formNew);
				treeNodes.add(treeNode);
			}
		} else if ("3".equals(id)) {
			System.out.println(viewDataSourceMap);
			TreeNode treeNode;
			for (String key : viewDataSourceMap.keySet()) {
				ViewDataSourceNew source = viewDataSourceMap.get(key);
				treeNode = new TreeNode();
				treeNode.setName(source.getModuleName());
				treeNode.setId(key);
				treeNodes.add(treeNode);
			}
		} else if ("4".equals(id)) {//获取动态表单下面的所有表
			List<DyFormDefinition> dyforms = dyFormApiFacade.getAllFormDefinitions(true);
			for (DyFormDefinition dyformDefinition : dyforms) {
				DyFormDefinition dyformDefinitionBean = new DyFormDefinition();
				BeanUtils.copyProperties(dyformDefinition, dyformDefinitionBean);
				TreeNode treeNode = new TreeNode();
				treeNode.setId(dyformDefinitionBean.getUuid());
				treeNode.setName(dyformDefinitionBean.getDisplayName());
				treeNode.setData(dyformDefinitionBean.getUuid());
				treeNodes.add(treeNode);
			}
		}
		return treeNodes;
	}

	/** 
	 * 获取所有的动态表单下拉框信息
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.dyview.service.GetViewDataService#getDyForms(java.lang.String, java.lang.String)
	 */
	@Override
	public List getDyForms(String s, String moduleId) {
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		List forms = dyFormApiFacade.getAllFormDefinitions(true);
		TreeNode treeNode = new TreeNode();
		treeNode.setId("-1");
		List<TreeNode> modules = new ArrayList<TreeNode>();
		modules = dataDictionaryService.getFromTypeAsTreeAsync("-1", "MODULE_ID");

		for (Iterator iterator = modules.iterator(); iterator.hasNext();) {
			TreeNode module = (TreeNode) iterator.next();
			if (module.getData().equals(moduleId)) {
				List children = new ArrayList();
				for (Iterator iterator2 = forms.iterator(); iterator2.hasNext();) {
					DyFormDefinition form = (DyFormDefinition) iterator2.next();
					if (!(StringUtils.isBlank(form.getModuleId()))) {
						if (form.getModuleId().equals(moduleId)) {
							DyFormDefinition formNew = new DyFormDefinition();
							BeanUtils.copyProperties(form, formNew);
							TreeNode child = new TreeNode();
							child.setId(formNew.getName());
							child.setName(formNew.getDisplayName());
							child.setData(formNew.getUuid());
							children.add(child);
						}
					}
				}
				module.setIsParent(true);
				module.setNocheck(true);
				module.setChildren(children);
				treeNodes.add(module);
			} else if (StringUtils.isBlank(moduleId)) {
				List children = new ArrayList();
				for (Iterator iterator2 = forms.iterator(); iterator2.hasNext();) {
					DyFormDefinition form = (DyFormDefinition) iterator2.next();
					DyFormDefinition formNew = new DyFormDefinition();
					BeanUtils.copyProperties(form, formNew);

					if (module.getData().equals(formNew.getModuleId())) {
						TreeNode child = new TreeNode();
						child.setId(formNew.getName());
						child.setName(formNew.getDisplayName());
						child.setData(formNew.getUuid());
						children.add(child);
					}
				}
				module.setChildren(children);
				treeNodes = modules;
			}
		}
		return treeNodes;
	}

	/**
	 * 
	 * 根据真实值获取对面表单的显示值(树形下拉框调用)
	 * 
	 * @param moduleId
	 * @param value
	 * @return
	 */
	public Map<String, Object> getDyFormsLabel(String moduleId, String value) {
		Map<String, Object> map = new HashMap<String, Object>();
		List forms = dyFormApiFacade.getAllFormDefinitions(true);
		TreeNode treeNode = new TreeNode();
		treeNode.setId("-1");
		List<TreeNode> modules = new ArrayList<TreeNode>();
		modules = dataDictionaryService.getFromTypeAsTreeAsync("-1", "MODULE_ID");
		for (Iterator iterator = modules.iterator(); iterator.hasNext();) {
			TreeNode module = (TreeNode) iterator.next();
			if (module.getData().equals(moduleId)) {
				for (Iterator iterator2 = forms.iterator(); iterator2.hasNext();) {
					DyFormDefinition form = (DyFormDefinition) iterator2.next();
					if (!(StringUtils.isBlank(form.getModuleId()))) {
						if (form.getModuleId().equals(moduleId)) {
							DyFormDefinition formNew = new DyFormDefinition();
							BeanUtils.copyProperties(form, formNew);
							if (formNew.getUuid().equals(value)) {
								map.put("label", formNew.getDisplayName());
							}
						}
					}
				}
			}
		}
		return map;
	}

	/** 
	 * 根据传入的formUuid来获取表单的字段信息
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.dyview.service.GetViewDataService#getFieldByForm(java.lang.String)
	 */
	@Override
	public List<FieldDefinition> getFieldByForm(String formUuid) {
		List<FieldDefinition> fieldDefinitions = dyFormApiFacade.getFormDefinition(formUuid).getFieldDefintions();
		return fieldDefinitions;
	}

	/** 
	 * 根据传入的viewUuid来获取视图的列属性信息
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.dyview.service.GetViewDataService#getFieldByView(java.lang.String)
	 */
	@Override
	public List<ColumnDefinitionNew> getFieldByView(String viewUuid) {
		ViewDefinitionNew view = viewDefinitionNewDao.get(viewUuid);
		Set<ColumnDefinitionNew> columnDefinitionNews = BeanUtils.convertCollection(view.getColumnDefinitionNews(),
				ColumnDefinitionNew.class);
		return Arrays.asList(columnDefinitionNews.toArray(new ColumnDefinitionNew[0]));
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.dyview.service.GetViewDataService#getSystemTableColumns(java.lang.String)
	 */
	@Override
	public List<SystemTableAttribute> getSystemTableColumns(String tableUuid) {
		List<SystemTableAttribute> systemTableAttributes = basicDataApiFacade.getAttributesByrelationship(tableUuid);
		return systemTableAttributes;
	}

	/** 
	 * 根据传入的模块id获取视图的列字段信息
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.dyview.service.GetViewDataService#getViewColumns(java.lang.String)
	 */
	@Override
	public List<ViewColumnNew> getViewColumns(String id) {
		ViewDataSourceNew source = viewDataSourceMap.get(id);
		List<ViewColumnNew> viewColumnNews = (List<ViewColumnNew>) source.getAllViewColumns();
		return viewColumnNews;
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.dyview.service.GetViewDataService#getViewColumnsTree(java.lang.String, java.lang.String)
	 */
	@Override
	public List<TreeNode> getViewColumnsTree(String s, String id) {
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		List<ViewColumnNew> viewColumnNews = getViewColumns(id);
		for (Iterator iterator2 = viewColumnNews.iterator(); iterator2.hasNext();) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId("-1");
			ViewColumnNew vc = (ViewColumnNew) iterator2.next();
			ViewColumnNew vcNew = new ViewColumnNew();
			BeanUtils.copyProperties(vc, vcNew);
			if (StringUtils.isNotBlank(vcNew.getColumnName())) {
				treeNode.setName(vcNew.getColumnName());
			}
			treeNode.setId(vcNew.getColumnAlias());
			treeNode.setData(vcNew.getColumnAlias());
			treeNodes.add(treeNode);
		}
		return treeNodes;
	}

	/** 
	 * 获取自定义按钮
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.dyview.service.GetViewDataService#getCustomButtonByCode(java.lang.String)
	 */
	@Override
	public List getCustomButtonByCode(String code) {
		List<Resource> resources = securityApiFacade.getDynamicButtonResourcesByCode(code);
		return resources;
	}

	/** 
	 * 获取有权限的自定义按钮
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.dyview.service.GetViewDataService#setCustomButtonRights(com.wellsoft.pt.basicdata.dyview.bean.ViewDefinitionBean, java.util.Set)
	 */
	@Override
	public void setCustomButtonRights(ViewDefinitionNewBean bean, Set<CustomButtonNew> customButtonNews) {
		List rights = new ArrayList();
		for (CustomButtonNew customButtonNew : customButtonNews) {
			String code = customButtonNew.getCode();
			if (StringUtils.isNotBlank(code)) {
				if (SecurityApiFacade.isGranted(code)) {
					rights.add(code);
				}
			}
		}

		List buttons = new ArrayList();
		for (Iterator iterator = rights.iterator(); iterator.hasNext();) {
			String right = (String) iterator.next();
			Resource resource = securityApiFacade.getButtonByCode(right);
			CustomDynamicButton button = new CustomDynamicButton();
			button.setId(resource.getCode());
			button.setName(resource.getName());
			button.setCode(resource.getCode());
			button.setScript(resource.getTarget());
			buttons.add(button);
			bean.setButtons(buttons);
		}
	}

	/** 
	 * 根据传入的tableUuid来获取主表以及从表属性的集合
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.dyview.service.GetViewDataService#getAttributesByrelationship(java.lang.String)
	 */
	@Override
	public List<SystemTableRelationship> getAttributesByrelationship(String tableUuid) {
		List<SystemTableRelationship> SystemTableRelationship = basicDataApiFacade
				.getAttributesByrelationship2(tableUuid);
		return SystemTableRelationship;
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.dyview.service.GetViewDataService#getSystemTable(java.lang.String)
	 */
	@Override
	public SystemTable getSystemTable(String tableUuid) {
		SystemTable table = basicDataApiFacade.getTable(tableUuid);
		return table;
	}

	/** 
	 * 获取下拉框动态表单的初始值
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.dyview.service.GetViewDataService#getKeyValuePair(java.lang.String, java.lang.String)
	 */
	@Override
	public QueryItem getKeyValuePair(String s, String s1) {
		QueryItem queryItem = new QueryItem();
		queryItem = dyFormApiFacade.getFormKeyValuePair(s, s1);
		return queryItem;
	}

	/** 
	 * 获取特殊列值的数据集合
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.dyview.service.GetViewDataService#getSpecialFieldValues(java.lang.String, java.util.List)
	 */
	@Override
	public List<Map<String, Object>> getSpecialFieldValues(String viewUuid, List<String> viewDataUuid,
			List<Map<String, Object>> viewDataArray) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		ViewDefinitionNewBean viewDefinitionNewBean = viewDefinitionNewService.getBeanByUuid(viewUuid);
		String specialFieldMethod = viewDefinitionNewBean.getSpecialFieldMethod();
		String requestParamName = viewDefinitionNewBean.getRequestParamName();
		String requestParamId = viewDefinitionNewBean.getRequestParamId();
		String[] requestParamIds = requestParamId.split(";");
		String responseParamName = viewDefinitionNewBean.getResponseParamName();
		String responseParamId = viewDefinitionNewBean.getResponseParamId();
		String[] responseParamIds = responseParamId.split(";");
		String[] serviceNameAndMethod = specialFieldMethod.split("\\.");
		String servicename = serviceNameAndMethod[0];
		String method = serviceNameAndMethod[1];
		Class<? extends Object> serviceName = ApplicationContextHolder.getBean(servicename).getClass();
		try {
			Class[] args = new Class[2];
			Method serviceMethod = serviceName.getDeclaredMethod(method, Map.class, String[].class);
			for (int i = 0; i < viewDataArray.size(); i++) {
				Map<String, Object> viewDataMap = viewDataArray.get(i);
				Map<String, Object> requestParams = new HashMap<String, Object>();
				for (String key : viewDataMap.keySet()) {
					for (String rpId : requestParamIds) {
						if (rpId.equals(key)) {
							requestParams.put(key, viewDataMap.get(key));
						}
					}
				}
				Map<String, Object> map = (Map<String, Object>) serviceMethod.invoke(
						ApplicationContextHolder.getBean(servicename), requestParams, responseParamIds);
				list.add(map);
			}

		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.dyview.service.GetViewDataService#getSpecialFieldValue(java.util.Map, java.util.Map)
	 */
	@Override
	public Map<String, Object> getSpecialFieldValue(Map<String, Object> requestParams, String[] responseParams) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mailboxFrom", "小强");
		return map;
	}

}
