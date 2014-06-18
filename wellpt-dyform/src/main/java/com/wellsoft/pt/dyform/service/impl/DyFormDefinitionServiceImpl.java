/*
 * @(#)2012-10-30 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dyform.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cache.internal.CacheDataDescriptionImpl;
import org.hibernate.cache.spi.CollectionRegion;
import org.hibernate.cache.spi.EntityRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
import org.hibernate.cache.spi.access.NaturalIdRegionAccessStrategy;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Settings;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.mapping.Collection;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.RootClass;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.persister.internal.PersisterFactoryImpl;
import org.hibernate.persister.spi.PersisterFactory;
import org.hibernate.type.AssociationType;
import org.hibernate.type.Type;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo;
import com.wellsoft.pt.common.component.jqgrid.JqTreeGridNode;
import com.wellsoft.pt.common.component.tree.TreeNode;
import com.wellsoft.pt.core.context.ApplicationContextHolder;
import com.wellsoft.pt.core.dao.PropertyFilter;
import com.wellsoft.pt.core.resource.Config;
import com.wellsoft.pt.core.support.QueryData;
import com.wellsoft.pt.core.support.QueryInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.dyform.dao.DyFormDefinitionDao;
import com.wellsoft.pt.dyform.entity.BeanCopyUtils;
import com.wellsoft.pt.dyform.entity.DyFormDefinition;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.service.DyFormDefinitionService;
import com.wellsoft.pt.dyform.support.DyFormConfig;
import com.wellsoft.pt.dyform.support.DyFormConfig.DbDataType;
import com.wellsoft.pt.dyform.support.DyFormConfig.DyFieldSysType;
import com.wellsoft.pt.dyform.support.DyFormConfig.ValueCreateMethod;
import com.wellsoft.pt.dyform.support.DyFormDefinitionUtils;
import com.wellsoft.pt.dytable.bean.TreeNodeBean;
import com.wellsoft.pt.dytable.support.TableConfig;

/**
 * 
 * @ClassName: FormDefinitionService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author lilin
 */
@Service
@Transactional
public class DyFormDefinitionServiceImpl implements DyFormDefinitionService {
	private Logger logger = LoggerFactory.getLogger(DyFormDefinitionServiceImpl.class);

	@Autowired
	private DyFormDefinitionDao dyFormDefinitionDao;

	@Autowired
	private DyFormApiFacade dyFormApiFacade;

	private void saveFormDefinition(DyFormDefinition formDefinition) {

		//设置版本
		if (StringUtils.isEmpty(formDefinition.getUuid()) || "undefined".equals(formDefinition.getUuid())) {//新增

			formDefinition.doBindVersionAsMinVersion();
			this.dyFormDefinitionDao.save(formDefinition);
			formDefinition.doBindUuid2Json();
			this.dyFormDefinitionDao.save(formDefinition);
		} else {
			if ("1".equals(formDefinition.getIsUp())) {//当调用方要升级版本时,isUp将会设置成1  
				formDefinition.setUuid(null);
				formDefinition.doBindVersionPlus();
				this.dyFormDefinitionDao.save(formDefinition);
				formDefinition.doBindUuid2Json();
				this.dyFormDefinitionDao.save(formDefinition);
			} else {
				DyFormDefinition obj = dyFormDefinitionDao.getEntitybyUuid(formDefinition.getUuid());
				if (obj == null) {
					throw new RuntimeException("tableName[" + formDefinition.getName() + "]对应的表单不存在 ");
				}
				try {
					BeanCopyUtils copyUtils = new BeanCopyUtils();
					copyUtils.copyProperties(obj, formDefinition);
				} catch (Exception e) {
				}
				this.dyFormDefinitionDao.save(obj);
			}

		}

	}

	private void createOrUpdateDyForm(String defintionJson) {
		//将定义json转换成hibernate配置文件(也就是hibernate.cfg.xml格式的文件)
		String hbmCfgXml = convertDefinitionJson2HbmCfg(defintionJson);
		this.updateSubformRelation(defintionJson);

		logger.debug("用户配置后生成相应的hbm文件内容(新建):" + hbmCfgXml);

		//根据hibernate配置文件生成或者更新表结构
		Configuration config = new Configuration();
		config.addXML(hbmCfgXml);
		this.addNewConfig(config);
		TableConfig tableconfig = new TableConfig(config);

		if (!this.isFormExistByFormTblName(JSONObject.fromObject(defintionJson).getString("name"))) {//数据表单表不存在
			tableconfig.addTable();
		} else {//数据表单表已存在 
			tableconfig.updateTable();
		}

	}

	@SuppressWarnings("unchecked")
	private void updateSubformRelation(String defintionJson) {
		JSONObject definitionJsonObj = JSONObject.fromObject(defintionJson);//表单定义信息
		JSONObject subformsDefinitionRelation = definitionJsonObj.getJSONObject("subforms");//主表从表关系
		String mainformName = definitionJsonObj.getString("name");//主表表名
		Iterator<String> it = subformsDefinitionRelation.keys();
		while (it.hasNext()) {
			String formUuid = it.next();
			DyFormDefinition subformDefinition = this.dyFormApiFacade.getFormDefinitionByFormUuid(formUuid);
			JSONObject subformJsonDefinition = JSONObject.fromObject(subformDefinition.getDefinitionJson());
			JSONObject subformFieldJsonDefinitons = subformJsonDefinition.getJSONObject("fields");
			JSONObject subformFieldJsonDefinitonOfMainForm = subformFieldJsonDefinitons.getJSONObject(mainformName);
			if (subformFieldJsonDefinitonOfMainForm.isNullObject() || subformFieldJsonDefinitonOfMainForm.isEmpty()) {

				/*JSONObject statusFieldDefinitionJsonDefinition = subformFieldJsonDefinitons.getJSONObject("status");
				JSONObject mainFormFieldDefinitionJsonDefinition = JSONObject
						.fromObject(statusFieldDefinitionJsonDefinition.toString());
				mainFormFieldDefinitionJsonDefinition.put("name", mainformName);
				mainFormFieldDefinitionJsonDefinition.put("sysType", DyFieldSysType.parentForm);
				mainFormFieldDefinitionJsonDefinition.put("length", 50);*/
				//设置在子表设置主表的关联字段,子表的数据通过该字段可以找到其主表对应的数据,该字段的名字为主表的表名
				JSONObject mainFormFieldInSubform = new JSONObject();
				mainFormFieldInSubform.put("name", mainformName);
				mainFormFieldInSubform.put("sysType", DyFieldSysType.parentForm);
				mainFormFieldInSubform.put("length", 50);
				mainFormFieldInSubform.put("dbDataType", DbDataType._string);
				mainFormFieldInSubform.put("valueCreateMethod", ValueCreateMethod.userImport);
				mainFormFieldInSubform.put("inputMode", DyFormConfig.INPUTMODE_Text);
				subformFieldJsonDefinitons.put(mainformName, mainFormFieldInSubform);
				subformJsonDefinition.put("fields", subformFieldJsonDefinitons);

				//保存排序
				JSONObject mainFormFieldOrderInSubform = new JSONObject();
				mainFormFieldOrderInSubform.put("name", DyFormDefinitionUtils.getFieldNameOfOrder(mainformName));
				mainFormFieldOrderInSubform.put("sysType", DyFieldSysType.parentForm);
				mainFormFieldOrderInSubform.put("length", 50);
				mainFormFieldOrderInSubform.put("dbDataType", DbDataType._string);
				mainFormFieldOrderInSubform.put("valueCreateMethod", ValueCreateMethod.userImport);
				mainFormFieldOrderInSubform.put("inputMode", DyFormConfig.INPUTMODE_Text);
				subformFieldJsonDefinitons.put(mainformName, mainFormFieldInSubform);
				subformJsonDefinition.put("fields", subformFieldJsonDefinitons);
				subformDefinition.setDefinitionJson(subformJsonDefinition.toString());
				this.createFormDefinitionAndFormTable(subformDefinition);
			}

		}
	}

	/**
	 *判断是已存在表名为参数formTblName的数据表单表
	 * 
	 * @param formTblName
	 * @return
	 */
	public boolean isFormExistByFormTblName(String formTblName) {
		//查看表是否已存在,若已存在 
		List<DyFormDefinition> dfds = this.findDyFormDefinitionByTblName(formTblName);
		if (dfds == null || dfds.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public DyFormDefinition findDyFormDefinitionByFormUuid(String formUuid) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("uuid", formUuid);

		DyFormDefinition dydf = this.dyFormDefinitionDao.findUnique(
				"select o from DyFormDefinition o where o.uuid = :uuid", params);

		//getSessionFactory().getCurrentSession().close();//session由自己管理
		return dydf;
	}

	@Override
	public DyFormDefinition findDyFormDefinitionByOuterId(String outerId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", outerId);

		return this.dyFormDefinitionDao.findUnique("select o from DyFormDefinition o where o.outerId = :id", params);
	}

	@Override
	public List<DyFormDefinition> findDyFormDefinitionByTblName(String tblName) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", tblName);
		List<DyFormDefinition> list = this.dyFormDefinitionDao.find(
				"select o from DyFormDefinition o where o.name = :name", params);
		return list;

	}

	/**
	 *判断是已存在表名为参数formTblID的数据表单表
	 * 
	 * @param formTblName
	 * @return
	 */
	public boolean isFormExistByFormOuterId(String formTblId) {
		//查看表是否已存在,若已存在
		List<DyFormDefinition> forms = this.dyFormDefinitionDao.find(Restrictions.eq("id", formTblId));
		if (forms == null || forms.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	private String convertDefinitionJson2HbmCfg(String defintionJson) {
		JSONObject definitionJsonObj = JSONObject.fromObject(defintionJson);//表单定义信息
		Document doc = DocumentHelper.createDocument();

		doc.addDocType("hibernate-mapping", "-//Hibernate/Hibernate Mapping DTD 3.0//EN",
				"http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd");

		Element root = doc.addElement("hibernate-mapping");

		Element classEl = root.addElement("class").addAttribute("entity-name", definitionJsonObj.getString("name"));

		// uuid字段
		Element uuidEl = classEl.addElement("id");
		uuidEl.addAttribute("name", "uuid");
		uuidEl.addAttribute("column", "uuid");
		uuidEl.addAttribute("type", "string");
		uuidEl.addElement("generator").addAttribute("class", "org.hibernate.id.UUIDGenerator");

		// creator字段
		Element creatorEl = classEl.addElement("property");
		creatorEl.addAttribute("name", "creator");
		creatorEl.addAttribute("column", "creator");
		creatorEl.addAttribute("type", "string");

		// createTime字段
		Element createTimeEl = classEl.addElement("property");
		createTimeEl.addAttribute("name", "createTime");
		createTimeEl.addAttribute("column", "create_time");
		createTimeEl.addAttribute("type", "java.sql.Timestamp");

		// modifier字段
		Element modifierEl = classEl.addElement("property");
		modifierEl.addAttribute("name", "modifier");
		modifierEl.addAttribute("column", "modifier");
		modifierEl.addAttribute("type", "string");

		// modifyTime字段
		Element modifyTimeEl = classEl.addElement("property");
		modifyTimeEl.addAttribute("name", "modifyTime");
		modifyTimeEl.addAttribute("column", "modify_time");
		modifyTimeEl.addAttribute("type", "java.sql.Timestamp");

		// sortOrder字段
		Element sortOrderEl = classEl.addElement("property");
		sortOrderEl.addAttribute("name", "sortOrder");
		sortOrderEl.addAttribute("column", "sort_order");
		sortOrderEl.addAttribute("type", "int");

		// 表dytable_form_definition的外键
		Element formUuidEl = classEl.addElement("property");
		formUuidEl.addAttribute("name", "formUuid");
		formUuidEl.addAttribute("column", "form_uuid");
		formUuidEl.addAttribute("type", "string");

		//在表单数据中也存在父节点和子节点的关系,父节点和子节点的关系即通过该字段关联起来
		Element subTableEl = classEl.addElement("property");
		subTableEl.addAttribute("name", "parentUuid");
		subTableEl.addAttribute("column", "parent_uuid");
		subTableEl.addAttribute("type", "string");

		// 从表指向主表的外键
		Element statusEl = classEl.addElement("property");
		statusEl.addAttribute("name", "status");
		statusEl.addAttribute("column", "status");
		statusEl.addAttribute("type", "string");

		//设置用户自定义字段

		Iterator it = definitionJsonObj.getJSONObject("fields").entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, JSONObject> jsonObjEntry = (Entry<String, JSONObject>) it.next();
			String fieldName = jsonObjEntry.getKey();
			JSONObject fieldDefinitionJsonObj = jsonObjEntry.getValue();
			Element proEl = classEl.addElement("property");

			proEl.addAttribute("name", fieldName);//设置字段名
			proEl.addAttribute("column", fieldName);

			//设置字段的类型
			String dataType = fieldDefinitionJsonObj.getString("dbDataType");
			if (DbDataType._date.equals(dataType)) {//日期类型的字段
				proEl.addAttribute("type", "java.sql.Timestamp");
			} else if (DbDataType._int.equals(dataType)) {
				proEl.addAttribute("type", "int");
			} else if (DbDataType._long.equals(dataType)) {
				proEl.addAttribute("type", "long");
			} else if (DbDataType._float.equals(dataType)) {
				proEl.addAttribute("type", "float");
			} else if (DbDataType._clob.equals(dataType)) {
				proEl.addAttribute("type", "clob");
			} else {
				proEl.addAttribute("type", "string");

				//设置字段的长度，默认长度为255
				String dataLength = fieldDefinitionJsonObj.getString("length");
				if (null != dataLength && dataLength.trim().length() > 0) {
					proEl.addAttribute("length", dataLength);
				} else {
					proEl.addAttribute("length", "255");
				}
			}
		}

		return doc.asXML();

	}

	/**
	 * 通过单例类configuration获取的sessionfactory
	 */
	private SessionFactory sessionFactory;

	/**
	 * 从configinstance获取的单例
	 */

	public void addNewConfig(Configuration cfg) {
		cfg.buildMappings();
		logger.info("add NewConfig.....");
		Settings settings = ((SessionFactoryImpl) getSessionFactory()).getSettings();

		Field mappingField;
		Mapping mapping;

		Field filterField;
		Map filters;

		Field idenField;
		Map identifierGenerators;

		/*Field allCacheField;
		Map allCacheRegions;*/

		Field entityPerField;
		Map entityPersisters;

		Field namedQueriesField;
		Map namedQueries;

		Field namedSqlQueriesField;
		Map namedSqlQueries;

		Field sqlResultSetMappingsField;
		Map sqlResultSetMappings;

		Field importsField;
		Map imports;

		Field classMetadataField;
		Map classMetadata;

		Field collectionPersistersField;
		Map collectionPersisters;
		try {
			mappingField = cfg.getClass().getDeclaredField("mapping");
			mappingField.setAccessible(true);
			mapping = (Mapping) (mappingField.get(cfg));

			filterField = ((SessionFactoryImpl) getSessionFactory()).getClass().getDeclaredField("filters");
			filterField.setAccessible(true);
			filters = (Map) (filterField.get(((SessionFactoryImpl) getSessionFactory())));

			idenField = ((SessionFactoryImpl) getSessionFactory()).getClass().getDeclaredField("identifierGenerators");
			idenField.setAccessible(true);
			identifierGenerators = (Map) (idenField.get(((SessionFactoryImpl) getSessionFactory())));

			/*allCacheField = ((SessionFactoryImpl) getSessionFactory()).getClass().getDeclaredField("allCacheRegions");
			allCacheField.setAccessible(true);
			allCacheRegions = (Map) (allCacheField.get(((SessionFactoryImpl) getSessionFactory())));*/

			entityPerField = ((SessionFactoryImpl) getSessionFactory()).getClass().getDeclaredField("entityPersisters");
			entityPerField.setAccessible(true);
			entityPersisters = (Map) (entityPerField.get(((SessionFactoryImpl) getSessionFactory())));

			namedQueriesField = ((SessionFactoryImpl) getSessionFactory()).getClass().getDeclaredField("namedQueries");
			namedQueriesField.setAccessible(true);
			namedQueries = (Map) (namedQueriesField.get(((SessionFactoryImpl) getSessionFactory())));

			namedSqlQueriesField = ((SessionFactoryImpl) getSessionFactory()).getClass().getDeclaredField(
					"namedSqlQueries");
			namedSqlQueriesField.setAccessible(true);
			namedSqlQueries = (Map) (namedSqlQueriesField.get(((SessionFactoryImpl) getSessionFactory())));

			sqlResultSetMappingsField = ((SessionFactoryImpl) getSessionFactory()).getClass().getDeclaredField(
					"sqlResultSetMappings");
			sqlResultSetMappingsField.setAccessible(true);
			sqlResultSetMappings = (Map) (sqlResultSetMappingsField.get(((SessionFactoryImpl) getSessionFactory())));

			importsField = ((SessionFactoryImpl) getSessionFactory()).getClass().getDeclaredField("imports");
			importsField.setAccessible(true);
			imports = (Map) (importsField.get(((SessionFactoryImpl) getSessionFactory())));

			collectionPersistersField = ((SessionFactoryImpl) getSessionFactory()).getClass().getDeclaredField(
					"collectionPersisters");
			collectionPersistersField.setAccessible(true);
			collectionPersisters = (Map) (collectionPersistersField.get(((SessionFactoryImpl) getSessionFactory())));

			classMetadataField = ((SessionFactoryImpl) getSessionFactory()).getClass()
					.getDeclaredField("classMetadata");
			classMetadataField.setAccessible(true);
			classMetadata = (Map) (collectionPersistersField.get(((SessionFactoryImpl) getSessionFactory())));

			filters.putAll(cfg.getFilterDefinitions());
			//Generators:  
			Iterator classes = cfg.getClassMappings();
			while (classes.hasNext()) {
				PersistentClass model = (PersistentClass) classes.next();

				if (!model.isInherited()) {
					//				IdentifierGenerator generator = model.getIdentifier().createIdentifierGenerator(settings.getDialect(),
					//						settings.getDefaultCatalogName(), settings.getDefaultSchemaName(), (RootClass) model);

					IdentifierGenerator generator = model.getIdentifier().createIdentifierGenerator(
							cfg.getIdentifierGeneratorFactory(),
							((SessionFactoryImpl) getSessionFactory()).getDialect(), settings.getDefaultCatalogName(),
							settings.getDefaultSchemaName(), (RootClass) model);

					identifierGenerators.put(model.getEntityName(), generator);
				}
			}

			///////////////////////////////////////////////////////////////////////  
			// Prepare persisters and link them up with their cache  
			// region/access-strategy  

			String cacheRegionPrefix = settings.getCacheRegionPrefix() == null ? "" : settings.getCacheRegionPrefix()
					+ ".";

			Map entityAccessStrategies = new HashMap();
			Map tmpEntityPersisters = new HashMap();
			Map tmpClassMetadata = new HashMap();

			//		this.configuration.getClassMap().putAll(cfg.getClassMap());
			classes = cfg.getClassMappings();

			while (classes.hasNext()) {

				PersistentClass model = (PersistentClass) classes.next();

				model.prepareTemporaryTables(mapping, ((SessionFactoryImpl) getSessionFactory()).getDialect());
				String cacheRegionName = cacheRegionPrefix + model.getRootClass().getCacheRegionName();
				model.prepareTemporaryTables(mapping, ((SessionFactoryImpl) getSessionFactory()).getDialect());
				// cache region is defined by the root-class in the hierarchy...  
				EntityRegionAccessStrategy accessStrategy = (EntityRegionAccessStrategy) entityAccessStrategies
						.get(cacheRegionName);
				if (accessStrategy == null && settings.isSecondLevelCacheEnabled()) {
					AccessType accessType = AccessType.fromExternalName(model.getCacheConcurrencyStrategy());

					if (accessType != null) {

						logger.trace("Building cache for entity data [" + model.getEntityName() + "]");
						EntityRegion entityRegion = settings.getRegionFactory().buildEntityRegion(cacheRegionName,
								((SessionFactoryImpl) getSessionFactory()).getProperties(),
								CacheDataDescriptionImpl.decode(model));
						accessStrategy = entityRegion.buildAccessStrategy(accessType);
						entityAccessStrategies.put(cacheRegionName, accessStrategy);
						/*allCacheRegions.put(cacheRegionName, entityRegion);*/
					}
				}
				final String naturalIdCacheRegionName = cacheRegionPrefix + model.getNaturalIdCacheRegionName();
				NaturalIdRegionAccessStrategy naturalIdAccessStrategy = (NaturalIdRegionAccessStrategy) entityAccessStrategies
						.get(naturalIdCacheRegionName);

				EntityPersister cp = ((SessionFactoryImpl) getSessionFactory())
						.getServiceRegistry()
						.getService(PersisterFactory.class)
						.createEntityPersister(model, accessStrategy, naturalIdAccessStrategy,
								(SessionFactoryImplementor) getSessionFactory(), mapping);
				tmpEntityPersisters.put(model.getEntityName(), cp);
				tmpClassMetadata.put(model.getEntityName(), cp.getClassMetadata());

			}

			//Named Queries:  
			namedQueries.putAll(cfg.getNamedQueries());
			namedSqlQueries.putAll(cfg.getNamedSQLQueries());
			sqlResultSetMappings.putAll(cfg.getSqlResultSetMappings());
			imports.putAll(cfg.getImports());

			entityPersisters.putAll(tmpEntityPersisters);
			//这个不能写入，有错误，回头还要再调试看原因
			classMetadata.putAll(Collections.unmodifiableMap(tmpClassMetadata));

			Map tmpEntityToCollectionRoleMap = new HashMap();
			Map tempCollectionPersisters = new HashMap();
			//这个不能写入，有错误，回头还要再调试看原因
			//			cfg.getCollectionMap().putAll(cfg.getCollectionMap());
			Iterator collections = cfg.getCollectionMappings();

			while (collections.hasNext()) {
				Collection model = (Collection) collections.next();
				final String cacheRegionName = cacheRegionPrefix + model.getCacheRegionName();
				final AccessType accessType = AccessType.fromExternalName(model.getCacheConcurrencyStrategy());
				CollectionRegionAccessStrategy accessStrategy = null;
				if (accessType != null && settings.isSecondLevelCacheEnabled()) {
					logger.trace("Building cache for collection data [" + model.getRole() + "]");
					CollectionRegion collectionRegion = settings.getRegionFactory().buildCollectionRegion(
							cacheRegionName, ((SessionFactoryImpl) getSessionFactory()).getProperties(),
							CacheDataDescriptionImpl.decode(model));
					accessStrategy = collectionRegion.buildAccessStrategy(accessType);
					entityAccessStrategies.put(cacheRegionName, accessStrategy);
					/*allCacheRegions.put(cacheRegionName, collectionRegion);*/
				}
				CollectionPersister persister = new PersisterFactoryImpl().createCollectionPersister(cfg, model,
						accessStrategy, (SessionFactoryImpl) getSessionFactory());
				tempCollectionPersisters.put(model.getRole(), persister.getCollectionMetadata());
				Type indexType = persister.getIndexType();
				if (indexType != null && indexType.isAssociationType() && !indexType.isAnyType()) {
					String entityName = ((AssociationType) indexType)
							.getAssociatedEntityName((SessionFactoryImpl) getSessionFactory());
					Set roles = (Set) tmpEntityToCollectionRoleMap.get(entityName);
					if (roles == null) {
						roles = new HashSet();
						tmpEntityToCollectionRoleMap.put(entityName, roles);
					}
					roles.add(persister.getRole());
				}
				Type elementType = persister.getElementType();
				if (elementType.isAssociationType() && !elementType.isAnyType()) {
					String entityName = ((AssociationType) elementType)
							.getAssociatedEntityName((SessionFactoryImpl) getSessionFactory());
					Set roles = (Set) tmpEntityToCollectionRoleMap.get(entityName);
					if (roles == null) {
						roles = new HashSet();
						tmpEntityToCollectionRoleMap.put(entityName, roles);
					}
					roles.add(persister.getRole());
				}

			}
			//加入新的  
			collectionPersisters.putAll(tempCollectionPersisters);

			Iterator itr = tmpEntityToCollectionRoleMap.entrySet().iterator();
			while (itr.hasNext()) {
				final Map.Entry entry = (Map.Entry) itr.next();
				entry.setValue(Collections.unmodifiableSet((Set) entry.getValue()));
			}
			//这个不能写入，有错误，回头还要再调试看原因
			//		collectionRolesByEntityParticipant.putAll(tmpEntityToCollectionRoleMap);

			// after *all* persisters and named queries are registered  
			Iterator iter = tmpEntityPersisters.values().iterator();
			while (iter.hasNext()) {
				((EntityPersister) iter.next()).postInstantiate();
			}
			iter = tempCollectionPersisters.values().iterator();
			while (iter.hasNext()) {
				((CollectionPersister) iter.next()).postInstantiate();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("add NewConfig error.....");
		}
	}

	/**
	 * 
	* @Title: getSessionFactory
	* @Description: 获取sessionfactory
	* @param @return    设定文件
	* @return SessionFactory    返回类型
	* @throws
	 */
	public SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			sessionFactory = ApplicationContextHolder.getBean(Config.TENANT_SESSION_FACTORY_BEAN_NAME,
					SessionFactory.class);
		}
		return sessionFactory;
	}

	@Override
	public void createFormDefinitionAndFormTable(DyFormDefinition formDefinition) {
		//保存表单数据表定义信息
		this.saveFormDefinition(formDefinition);

		//生成表单数据表
		this.createOrUpdateDyForm(formDefinition.getDefinitionJson());

	}

	@Override
	public List<TreeNodeBean> getFormOutlineOfAllVersionTreeByFormUuid(String formUuid) {
		List<TreeNodeBean> list = Lists.newArrayList();
		DyFormDefinition pForm = dyFormDefinitionDao.get(formUuid);
		List<DyFormDefinition> forms = dyFormDefinitionDao.findBy("name", pForm.getName());
		for (int i = forms.size() - 1; i >= 0; i--) {
			DyFormDefinition obj = forms.get(i);
			if (!StringUtils.equals(formUuid, obj.getUuid())) {
				TreeNodeBean node = new TreeNodeBean(obj.getUuid(), obj.getDisplayName() + "(" + obj.getVersion() + ")");
				list.add(node);
			}
		}
		return list;
	}

	@Override
	public List<TreeNodeBean> getFormOutlineOfAllVersionTreeByFormName(String formName) {
		List<TreeNodeBean> list = Lists.newArrayList();
		List<DyFormDefinition> forms = dyFormDefinitionDao.findBy("name", formName);
		for (int i = forms.size() - 1; i >= 0; i--) {
			DyFormDefinition obj = forms.get(i);
			TreeNodeBean node = new TreeNodeBean(obj.getUuid(), obj.getDisplayName() + "(" + obj.getVersion() + ")");
			list.add(node);
		}
		return list;
	}

	public List<TreeNodeBean> getFormOutlineOfAllVersionTree(String parentId, String selectedFormUuid) {
		List<TreeNodeBean> tree = Lists.newArrayList();

		List<DyFormDefinition> forms = dyFormDefinitionDao.getMaxVersionList();
		for (DyFormDefinition fDefinition : forms) {
			TreeNodeBean node = new TreeNodeBean(fDefinition.getUuid(), fDefinition.getDisplayName() + "("
					+ fDefinition.getVersion() + ")");
			tree.add(node);

			if (fDefinition.isMinVersion()) {//该表单已是最低版本
				node.setOpen(false);
				node.setIsParent("false");
				setChecked(node, selectedFormUuid);//设置选中节点
			} else {//该表单还有其他低版本
				List<TreeNodeBean> chirldrenTree = this.getFormOutlineOfAllVersionTreeByFormUuid(fDefinition.getUuid());
				setChecked(node, selectedFormUuid);//设置选中节点

				if (chirldrenTree == null || chirldrenTree.size() == 0) {
					node.setOpen(false);
					node.setIsParent("false");
				} else {
					node.setIsParent("true");
					if (setChecked(chirldrenTree, selectedFormUuid)) {//设置选中节点
						node.setOpen(true);
					} else {
						node.setOpen(false);
					}
					node.setChildren(chirldrenTree);

				}
			}
		}
		return tree;
	}

	private boolean setChecked(Object obj, String selectedFormUuid) {
		if (obj == null) {
			return false;
		}

		if (obj instanceof TreeNodeBean) {
			TreeNodeBean node = (TreeNodeBean) obj;
			if (node.getId().equals(selectedFormUuid)) {
				node.setChecked(true);
				return true;
			}
		} else if (obj instanceof List) {
			List<TreeNodeBean> nodes = (List<TreeNodeBean>) obj;
			for (TreeNodeBean node : nodes) {
				return setChecked(node, selectedFormUuid);
			}
		}

		return false;
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

		Session session = dyFormDefinitionDao.getSession();
		System.out.println(sessionFactory + "" + session);
		if (StringUtils.isBlank(jqGridQueryInfo.getNodeid())) {
			if ("1".equals(flag)) {
				results = this.dyFormDefinitionDao.namedQuery("topDyFormDefinitionTreeQuery", values, QueryItem.class,
						queryInfo.getPagingInfo());
			} else {
				results = this.dyFormDefinitionDao.namedQuery("topDyFormDefinitionTreeQuery2", values, QueryItem.class,
						queryInfo.getPagingInfo());
			}

		} else {
			String uuid = jqGridQueryInfo.getNodeid();
			DyFormDefinition parent = this.dyFormDefinitionDao.get(uuid);
			values.put("parentUuid", uuid);
			values.put("outerId", parent.getOuterId());
			results = this.dyFormDefinitionDao.namedQuery("dyFormDefinitionTreeQuery", values, QueryItem.class,
					queryInfo.getPagingInfo());
		}
		// results = pageData.getResult();
		List<JqTreeGridNode> retResults = new ArrayList<JqTreeGridNode>();

		int level = jqGridQueryInfo.getN_level() == null ? 0 : jqGridQueryInfo.getN_level() + 1;
		String parentId = jqGridQueryInfo.getNodeid() == null ? "null" : jqGridQueryInfo.getNodeid();
		for (int index = 0; index < results.size(); index++) {
			QueryItem item = results.get(index);
			JqTreeGridNode node = new JqTreeGridNode();
			node.setId(item.get("outerId").toString());// id
			List<Object> cell = node.getCell();
			cell.add(item.get("uuid"));// UUID
			cell.add(item.get("displayName") + " (" + item.get("version") + ")");// name
			cell.add(item.get("name"));// name
			cell.add(item.get("version"));// version
			cell.add(item.get("moduleName"));// enabled
			cell.add(item.get("moduleId"));// formName
			cell.add(item.get("outerId"));// version
			cell.add(item.get("code"));// version
			// level field
			cell.add(level);
			// parent id field
			cell.add(parentId);
			// leaf field
			if (StringUtils.isBlank(jqGridQueryInfo.getNodeid())) {
				if (Double.valueOf(1).toString().equals(item.get("version"))) {
					cell.add(true);
				} else {
					long count = this.dyFormDefinitionDao.countById(item.get("outerId").toString());
					cell.add(count <= 1);
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
	 * 根据formUuid获取字段的树形(控制显示\隐藏字段使用)
	 * 
	 * (non-Javadoc)
	 */
	@Override
	public List<TreeNode> getFieldByFormUuid(String s, String formUuid) {
		org.json.JSONObject fieldjson = dyFormApiFacade.getJsonObject4FieldDefinitionByFormUuid(formUuid);
		Iterator<String> it = fieldjson.keys();
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		while (it.hasNext()) {
			String fieldName = it.next();
			org.json.JSONObject fieldinfo;
			try {
				fieldinfo = fieldjson.getJSONObject(fieldName);
				TreeNode treeNode = new TreeNode();
				String displayname = fieldinfo.getString("displayName");
				String fieldname = fieldinfo.getString("name");
				if (displayname == null || displayname.equals("")) {
					treeNode.setName(fieldname);
				} else {
					treeNode.setName(displayname);
				}
				treeNode.setId(fieldname);
				//treeNode.setData(fieldinfo);
				treeNodes.add(treeNode);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return treeNodes;
	}
}
