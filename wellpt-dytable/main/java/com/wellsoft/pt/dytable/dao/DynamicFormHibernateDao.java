/*
 * @(#)2012-10-30 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.dao;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cache.internal.CacheDataDescriptionImpl;
import org.hibernate.cache.spi.CollectionRegion;
import org.hibernate.cache.spi.EntityRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
import org.hibernate.cache.spi.access.NaturalIdRegionAccessStrategy;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Settings;
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
import org.hibernate.tuple.entity.EntityMetamodel;
import org.hibernate.type.AssociationType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.wellsoft.pt.core.context.ApplicationContextHolder;
import com.wellsoft.pt.core.resource.Config;
import com.wellsoft.pt.utils.xml.XmlConverUtils;

/**
 * 
* @ClassName: DynamicFormHibernateDao
* @Description: 作为动态表单的基类来处理
* 可在Service层直接使用, 也可以扩展泛型DAO子类使用。
* 处理内容包括动态表单的定义处理和动态表单内容的处理
* @author lilin
 */
@Component
public class DynamicFormHibernateDao {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 通过单例类configuration获取的sessionfactory
	 */
	private SessionFactory sessionFactory;
	/**
	 * 从configinstance获取的单例
	 */
	protected Configuration config;

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
	* @Title: removePersistentClass
	* @Description: 删除
	* @param cfg    configuration
	* @return void    返回类型
	* @throws
	 */
	public void removePersistentClass(Configuration cfg) {
		Field entityPerField;
		Map entityPersisters = new HashMap();

		try {
			entityPerField = ((SessionFactoryImpl) getSessionFactory()).getClass().getDeclaredField("entityPersisters");
			entityPerField.setAccessible(true);
			entityPersisters = (Map) (entityPerField.get(((SessionFactoryImpl) getSessionFactory())));
			Iterator classes = cfg.getClassMappings();
			while (classes.hasNext()) {
				PersistentClass model = (PersistentClass) classes.next();
				entityPersisters.remove(model.getEntityName());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	* @Title: getConfig
	* @Description: 获取config
	* @param @return    设定文件
	* @return Configuration    返回类型
	* @throws
	 */
	public Configuration getConfig() {
		return this.config;
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

	/**
	 * 
	* @Title: setSessionFactory
	* @Description:设置sessionfactory
	* @param @param sessionFactory    设定文件
	* @return void    返回类型
	* @throws
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 
	* @Title: getSession
	* @Description: h
	* @param @return    设定文件
	* @return Session    返回类型
	* @throws
	 */
	public Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	/**
	 * 
	* @Title: flush
	* @Description: Flush当前Session.
	* @param     设定文件
	* @return void    返回类型
	* @throws
	 */
	public void flush() {
		getSession().flush();
	}

	/**
	 * 
	* @Title: save
	* @Description: 这里将一个表单分成了两份，basicinfo为基本表单信息，childform为子表信息
	* @param @param formName 表单名
	* @param @param basicInfo 这里是map<string,string>，要进行类型转换
	* @param @param childForm 这里map<string,string> key为子表名，string为xml格式的字符串
	* @param @return    设定文件
	* @return boolean    返回类型
	* @throws
	 */
	public boolean save(String formName, Map basicMap, Map childMap) {
		Assert.hasText(formName, "表名不能为空");
		Assert.notNull(basicMap, "map值不能为空");
		try {
			Session s = getSession();
			Transaction t = s.beginTransaction();
			//			Session mapSession = s.getSession(EntityMode.MAP);
			//这里需要将valuemap的类型 进行对应处理
			Map typeMap = new HashMap();
			typeMap.putAll(map2TypeMap(formName, basicMap));
			//处理子表的xml字符串，另外要将类型对应进行处理
			parseChildMap(formName, childMap, typeMap);

			s.saveOrUpdate(formName, typeMap);

			t.commit();
			logger.debug("save Map: {}", basicMap);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

	/**
	 * 
	* @Title: parseChildMap
	* @Description: 将xml格式的string转换为map值，另外map的对应值要进行相应转换
	* @param @param name 主表的表名
	* @param @param childMap
	* @param @param formMap    设定文件
	* @return void    返回类型
	* @throws
	 */
	public void parseChildMap(String name, Map childMap, Map formMap) {
		//
		for (Object key : childMap.keySet()) {
			String xmlstring = childMap.get(key).toString();
			//将xmlstring转换为一个list的map
			List<Map> maplist = XmlConverUtils.xmltoList(xmlstring);

			List<Map> typemaplist = new ArrayList();
			for (Map map : maplist) {
				Map typemap = map2TypeMap(key.toString(), map);
				typemap.put(name, formMap);
				typemaplist.add(typemap);
			}
			formMap.put(key, typemaplist);
		}
	}

	/**
	 * 
	* @Title: map2TypeMap
	* @Description: 传入的map是<string,string>这里要将对应的值转换为对应类型
	* @param @param formName  数据库以及hbm配置文件中对应的表名
	* @param @param valueMap  需要转换的map
	* @param @return    设定文件
	* @return Map    返回类型  转换完类型的map
	* @throws
	 */
	private Map map2TypeMap(String formName, Map valueMap) {
		Map sessionMap = new HashMap();
		//这里的valuemap 全部是string对，这里要按照hibernate中对应的类型进行转换
		EntityPersister persister = ((SessionFactoryImpl) sessionFactory).getEntityPersister(formName);
		EntityMetamodel metamodel = persister.getEntityMetamodel();
		Type[] types = metamodel.getPropertyTypes();
		String[] propertyNames = metamodel.getPropertyNames();
		for (int i = 0; i < propertyNames.length; i++) {
			if (valueMap.get(propertyNames[i]) == null) {
				continue;
			}
			String value = valueMap.get(propertyNames[i]).toString();
			if (types[i].getName().equals("string")) {
				sessionMap.put(propertyNames[i], value);
			} else if (types[i].getName().equals("integer")) {
				sessionMap.put(propertyNames[i], Integer.parseInt(value));
			}
		}
		//        for(Object key:valueMap.keySet()){
		//        	value = valueMap.
		//        }

		return sessionMap;
	}

	public boolean save(String formName, File xmlFile) {

		Assert.notNull(xmlFile, "xml文件不能为空");
		try {
			Session s = getSession();

			Transaction t = s.beginTransaction();
			SAXReader reader = new SAXReader();
			Document document = reader.read(xmlFile);
			//			Session dom4jSession = s.getSession(EntityMode.DOM4J);
			Element root = document.getRootElement();
			//			Map map = XmlUtil.parse(new FileInputStream(xmlFile));
			s.saveOrUpdate(getSessionFactoryFormName(formName), root);
			t.commit();
			logger.debug("save xml: {}", xmlFile);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

	/**
	 * 根据Id获取一个动态表单数据
	 * 
	 * @param formName
	 *            表单名，对应hbm.xml映射文件中的表名
	 * @param id
	 *            ID
	 * @return 返回存放数据的Map
	 */
	public Map<String, Object> getById(String formName, String id) {
		Map dynamicForm = new HashMap();
		dynamicForm = (Map) getSession().load(formName, id);
		return dynamicForm;
	}

	/**
	 * 
	* @Title: delete
	* @Description: 删除表单中对应记录
	* @param @param formName 表单名	
	* @param @param id      表单id
	* @param @return    设定文件
	* @return boolean    返回类型
	* @throws
	 */
	public boolean delete(String formName, String id) {

		try {
			Session s = getSession();
			Transaction t = s.beginTransaction();
			s.delete(formName, id);
			t.commit();
			logger.debug("delete: {}", formName);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	* @Title: getAll
	* @Description: 获取表单中所有记录
	* @param @param formName 表单名
	* @param @return    设定文件
	* @return List<Map>    返回类型
	* @throws
	 */

	public List<Map> getAll(String formName) {
		List formList = null;
		try {
			Session s = getSession();
			Transaction t = s.beginTransaction();
			formList = (s.createQuery("from " + getSessionFactoryFormName(formName) + " f")).list();

			t.commit();
			logger.debug("delete: {}", formName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return formList;
	}

	public String getSessionFactoryFormName(String formName) {
		Map classmap = sessionFactory.getAllClassMetadata();
		for (Object element : classmap.keySet()) {
			if (formName.equalsIgnoreCase(String.valueOf(element))) {
				formName = String.valueOf(element);
				break;
			}
		}
		return formName;
	}
}
