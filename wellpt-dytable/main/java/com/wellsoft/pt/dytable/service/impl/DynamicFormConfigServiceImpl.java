package com.wellsoft.pt.dytable.service.impl;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.core.context.ApplicationContextHolder;
import com.wellsoft.pt.core.resource.Config;
import com.wellsoft.pt.dytable.dao.DynamicFormHibernateDao;
import com.wellsoft.pt.dytable.dao.FieldDefinitionDao;
import com.wellsoft.pt.dytable.dao.FormDefinitionDao;
import com.wellsoft.pt.dytable.entity.FieldDefinition;
import com.wellsoft.pt.dytable.entity.FormDefinition;
import com.wellsoft.pt.dytable.service.DynamicFormConfigService;
import com.wellsoft.pt.dytable.support.TableConfig;

/**
 * Description: 处理根据配置文件添加 修改 删除动态表单定义的操作和处理service接口实现类
 *  
 * @author jiangmb
 * @date 2012-12-21
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-12-21.1	jiangmb		2012-12-21		Create
 * </pre>
 *
 */
@Service
@Transactional
public class DynamicFormConfigServiceImpl implements DynamicFormConfigService {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	//表单定义Dao
	private FormDefinitionDao formDefinitionDao;
	//表单字段定义Dao
	private FieldDefinitionDao fieldDefinitionDao;
	//动态表单Dao
	private DynamicFormHibernateDao dynamicFormDao;
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			sessionFactory = ApplicationContextHolder.getBean(Config.TENANT_SESSION_FACTORY_BEAN_NAME,
					SessionFactory.class);
		}
		return sessionFactory;
	}

	/**
	 * 
	* @Title: createDynamicForm
	* @Description: 根据hbm文件创建自定义表单
	* 
	* @param @param hbmxmlfile    设定文件
	* @return void    返回类型
	* @throws
	 */

	public void createDynamicForm(File hbmxmlfile) {
		//加载配置
		Configuration config = new Configuration();
		config.addFile(hbmxmlfile);

		// sessionfactoy加载配置文件
		dynamicFormDao.addNewConfig(config);
		//生成数据库表，这里只创建数据表		
		TableConfig tableconfig = new TableConfig(config);
		tableconfig.addTable();

		//cfg生成相应的formdefinition和fieldefinition并插入数据库
		addDBTable(config);
	}

	/**
	 * 
	* @Title: updateDynamicForm
	* @Description: 更新数据库，包括更新sessionfactory中相应的映射
	* @param     设定文件
	* @return void    返回类型
	* @throws
	 */
	public void updateDynamicForm(File hbmxmlfile) {
		//新建一个config
		Configuration config = new Configuration();
		config.addFile(hbmxmlfile);
		//sessionfactory重新加载配置
		dynamicFormDao.addNewConfig(config);
		//数据库更新相关表
		TableConfig tableconfig = new TableConfig(config);
		tableconfig.updateTable();
		//根据hbm配置文件或者数据库中的schema生成相应的formdefinition和fieldefinition并插入数据库,这里简化处理先删除，再添加
		deleteDBTable(config);
		addDBTable(config);
	}

	public void updateDynamicForm(File hbmxmlfile, FormDefinition entity) {
		//新建一个config
		Configuration config = new Configuration();
		config.addFile(hbmxmlfile);
		//sessionfactory重新加载配置 先删除老的配置，然后添加新的配置
		dynamicFormDao.removePersistentClass(config);
		dynamicFormDao.addNewConfig(config);
		//数据库更新相关表
		TableConfig tableconfig = new TableConfig(config);
		tableconfig.updateTable();
		//根据hbm配置文件或者数据库中的schema生成相应的formdefinition和fieldefinition并插入数据库,这里简化处理先删除，再添加
		//		deleteDBTable(config);
		addDBTable(config, entity);
	}

	/**
	 * 
	* @Title: deleteDynamicForm
	* @Description: 删除相应的配置，这里数据库中如果有数据要如何处理呢？
	* @param     设定文件
	* @return void    返回类型
	* @throws
	 */
	public void deleteDynamicForm(File hbmxmlfile) {
		//新建一个config
		Configuration config = new Configuration();
		config.addFile(hbmxmlfile);
		//删除sessionfactory中的处理
		dynamicFormDao.removePersistentClass(config);

		//将涉及到数据表进行删除
		TableConfig tableconfig = new TableConfig(config);
		tableconfig.deleteTable();

		//删除form_definition中表单的定义
		//		deleteDBTable(config);
	}

	/**
	 * 
	* @Title: addDBTable
	* @Description: 根据config来获取表的基本配置，然后封装为form和filed 插入数据库
	* @param @param config    设定文件
	* @return void    返回类型
	* @throws
	 */
	private void addDBTable(Configuration config) {
		FormDefinition form;
		FieldDefinition fielddefinition;

		Iterator itertable = config.getTableMappings();
		Dialect dialect = ((SessionFactoryImpl) getSessionFactory()).getDialect();
		Mapping mapping;
		Field mappingField;
		try {
			mappingField = config.getClass().getDeclaredField("mapping");
			mappingField.setAccessible(true);
			mapping = (Mapping) (mappingField.get(config));

			while (itertable.hasNext()) {
				Table table = (Table) itertable.next();
				form = new FormDefinition();
				form.setName(table.getName());

				List<FieldDefinition> fieldlist = new ArrayList();
				Iterator iterfield = table.getColumnIterator();
				while (iterfield.hasNext()) {
					Column column = (Column) iterfield.next();
					fielddefinition = new FieldDefinition();
					fielddefinition.setEntityName(form.getName());
					fielddefinition.setFieldName(column.getQuotedName(dialect));
					fielddefinition.setType(column.getSqlType(dialect, mapping));
					fieldlist.add(fielddefinition);
					fieldDefinitionDao.save(fielddefinition);
				}
				//				form.setFiledDefinitions(fieldlist);
				formDefinitionDao.save(form);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addDBTable(Configuration config, FormDefinition entity) {
		FormDefinition form;
		FieldDefinition fielddefinition;
		Iterator itertable = config.getTableMappings();
		Dialect dialect = ((SessionFactoryImpl) getSessionFactory()).getDialect();
		Mapping mapping;
		Field mappingField;
		try {
			mappingField = config.getClass().getDeclaredField("mapping");
			mappingField.setAccessible(true);
			mapping = (Mapping) (mappingField.get(config));

			while (itertable.hasNext()) {
				Table table = (Table) itertable.next();
				form = new FormDefinition();
				form.setName(table.getName());
				//form.setParentForm(entity);
				List<FieldDefinition> fieldlist = new ArrayList();
				Iterator iterfield = table.getColumnIterator();
				while (iterfield.hasNext()) {
					Column column = (Column) iterfield.next();
					fielddefinition = new FieldDefinition();
					fielddefinition.setEntityName(form.getName());
					fielddefinition.setFieldName(column.getQuotedName(dialect));
					fielddefinition.setType(column.getSqlType(dialect, mapping));
					fieldlist.add(fielddefinition);
					fieldDefinitionDao.save(fielddefinition);
				}

				formDefinitionDao.save(form);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	* @Title: deleteDBTable
	* @Description: 根据config来获取表的基本配置，然后删除form
	* @param @param config    设定文件
	* @return void    返回类型
	* @throws
	 */
	private void deleteDBTable(Configuration config) {
		//		Iterator itertable = config.getTableMappings();
		//		while (itertable.hasNext()) {
		//			Table table = (Table) itertable.next();
		//			FormDefinition form = this.formDefinitionDao.getByName(table.getName());
		//			formDefinitionDao.delete(form);
		//		}

	}

	/**
	 * 
	* @Title: setFormDefinitionDao
	* @Description: 设置FormDefinitionDao
	* @param @param formDefinitionDao    设定文件
	* @return void    返回类型
	* @throws
	 */
	@Autowired
	public void setFormDefinitionDao(FormDefinitionDao formDefinitionDao) {
		this.formDefinitionDao = formDefinitionDao;
	}

	/**
	 * 
	* @Title: getFormDefinitionDao
	* @Description: 获取FormDefinitionDao
	* @param @return    设定文件
	* @return FormDefinitionDao    返回类型
	* @throws
	 */
	public FormDefinitionDao getFormDefinitionDao() {
		return formDefinitionDao;
	}

	/**
	 * 
	* @Title: setFiledDefinitionDao
	* @Description: 设置fielddefinitiondao
	* @param @param filedDefinitionDao    设定文件
	* @return void    返回类型
	* @throws
	 */
	@Autowired
	public void setFieldDefinitionDao(FieldDefinitionDao filedDefinitionDao) {
		this.fieldDefinitionDao = filedDefinitionDao;
	}

	/**
	 * 
	* @Title: getFiledDefinitionDao
	* @Description: 获取fieldDefinitiondao
	* @param @return    设定文件
	* @return FieldDefinitionDao    返回类型
	* @throws
	 */
	public FieldDefinitionDao getFieldDefinitionDao() {
		return fieldDefinitionDao;
	}

	/**
	 * 
	* @Title: setDynamicForDao
	* @Description: 获取dynamicfromdao
	* @param @param dynamicForDao    设定文件
	* @return void    返回类型
	* @throws
	 */
	@Autowired
	public void setDynamicFormDao(DynamicFormHibernateDao dynamicFormDao) {
		this.dynamicFormDao = dynamicFormDao;
	}

	/**
	 * 
	* @Title: getDynamicFormDao
	* @Description: 设置dynamicfromdao
	* @param @return    设定文件
	* @return DynamicFormDao    返回类型
	* @throws
	 */
	public DynamicFormHibernateDao getDynamicFormDao() {
		return dynamicFormDao;
	}

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.dytable.service.DynamicFormConfigService#addNewConfig(org.hibernate.cfg.Configuration)
	 */
	@Override
	public void addNewConfig(Configuration cfg) {
		dynamicFormDao.addNewConfig(cfg);
	}
}
