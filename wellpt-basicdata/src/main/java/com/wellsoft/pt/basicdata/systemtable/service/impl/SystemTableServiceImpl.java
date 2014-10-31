package com.wellsoft.pt.basicdata.systemtable.service.impl;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.basicdata.datadict.entity.DataDictionary;
import com.wellsoft.pt.basicdata.facade.BasicDataApiFacade;
import com.wellsoft.pt.basicdata.systemtable.bean.SystemTableAttributeBean;
import com.wellsoft.pt.basicdata.systemtable.bean.SystemTableBean;
import com.wellsoft.pt.basicdata.systemtable.bean.SystemTableRelationshipBean;
import com.wellsoft.pt.basicdata.systemtable.dao.SystemTableAttributeDao;
import com.wellsoft.pt.basicdata.systemtable.dao.SystemTableDao;
import com.wellsoft.pt.basicdata.systemtable.dao.SystemTableRelationshipDao;
import com.wellsoft.pt.basicdata.systemtable.entity.SystemTable;
import com.wellsoft.pt.basicdata.systemtable.entity.SystemTableAttribute;
import com.wellsoft.pt.basicdata.systemtable.entity.SystemTableRelationship;
import com.wellsoft.pt.basicdata.systemtable.service.SystemTableService;
import com.wellsoft.pt.basicdata.systemtable.support.CollectionUtil;
import com.wellsoft.pt.basicdata.systemtable.support.StringUtil;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryData;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo;
import com.wellsoft.pt.common.enums.Separator;
import com.wellsoft.pt.core.context.ApplicationContextHolder;
import com.wellsoft.pt.core.dao.Page;
import com.wellsoft.pt.core.dao.hibernate.SessionFactoryUtils;
import com.wellsoft.pt.utils.bean.BeanUtils;

/**
 * 
 * Description: 系统表结构服务层实现类
 *  
 * @author zhouyq
 * @date 2013-3-21
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-3-21.1	zhouyq		2013-3-21		Create
 * </pre>
 *
 */
@Service
@Transactional
public class SystemTableServiceImpl implements SystemTableService {

	@Autowired
	private SystemTableDao systemTableDao;

	@Autowired
	private SystemTableAttributeDao systemTableAttributeDao;

	@Autowired
	private SystemTableRelationshipDao systemTableRelationshipDao;

	@Autowired
	BasicDataApiFacade basicDataApiFacade;

	/** 
	 * 通过uuid查找系统表结构
	 * @see com.wellsoft.pt.message.service.SystemTableService#getById(java.lang.String)
	 */
	@Override
	public SystemTable getByUuid(String formUuid) {
		return systemTableDao.get(formUuid);
	}

	/** 
	 * 根据uuid查找对应的系统表结构bean
	 * @throws ClassNotFoundException 
	 * @see com.wellsoft.pt.message.service.SystemTableService#getBeanById(java.lang.String)
	 */
	@Override
	public SystemTableBean getBeanById(String uuid) throws ClassNotFoundException {
		SystemTable systemTable = this.systemTableDao.get(uuid);
		SystemTableBean bean = new SystemTableBean();
		BeanUtils.copyProperties(systemTable, bean);

		List<String> entity_propertyName_list = new ArrayList<String>();//实体类中属性
		List<String> attributeTable_propertyName_list = new ArrayList<String>();//属性表中现有属性
		//获取该类首字母小写的类名
		String className = systemTable.getFullEntityName().substring(
				systemTable.getFullEntityName().lastIndexOf(".") + 1);
		String lowerCaseName = className.substring(0, 1).toLowerCase() + className.substring(1);

		//查找属性表的所有属性
		Set<SystemTableAttribute> attributes = systemTable.getAttributes();
		//获得属性表所有属性名
		for (SystemTableAttribute systemTableAttribute : attributes) {
			attributeTable_propertyName_list.add(systemTableAttribute.getAttributeName());
		}
		//查找实体类中所有属性
		BeanWrapperImpl wrapper = new BeanWrapperImpl(Class.forName(systemTable.getFullEntityName()));
		PropertyDescriptor[] descriptors = wrapper.getPropertyDescriptors();
		for (PropertyDescriptor descriptor : descriptors) {
			String propertyName = descriptor.getName();
			if (!("class".equals(propertyName) || "attach".equals(propertyName))) {
				//判断属性表中的列名是否为类名.属性名的形式(原先保存)
				if (attributeTable_propertyName_list.get(0).contains(".")) {
					entity_propertyName_list.add(lowerCaseName + "." + propertyName);
				} else {
					entity_propertyName_list.add(propertyName);
				}
			}
		}

		//查找实体类中是否有增加属性，若有则加入系统属性表中
		String tableName = getTableName(Class.forName(systemTable.getFullEntityName()));//获取表名
		List<String> fieldList = systemTableDao.getFieldByTableName(tableName);//数据库表字段集合
		List<String> different_propertyName_list = CollectionUtil.getDiffentElement(attributeTable_propertyName_list,
				entity_propertyName_list);
		if (different_propertyName_list.size() > 0) {
			for (String different_propertyName : different_propertyName_list) {
				String propertyToField = StringUtil.propertyToField(different_propertyName);//将属性转为字段名
				if (!different_propertyName.equals("attach") && !different_propertyName.equals("class")) {
					Map<String, String> columnMap = getColumnMap(Class.forName(systemTable.getFullEntityName()),
							different_propertyName);
					if (columnMap != null) {
						String fieldName = columnMap.get("name");
						String type = columnMap.get("type");
						if (!type.equals("SET") && !type.equals("LIST")) {//去除关系列
							SystemTableAttribute attr = new SystemTableAttribute();
							attr.setFieldName(fieldName);
							attr.setAttributeName(different_propertyName);
							attr.setColumnAliases(lowerCaseName + "_" + different_propertyName);
							attr.setEntityName(className);
							attr.setColumnType(type);
							attr.setChineseName(fieldName);
							//多方保存时得将实体类也保存而不是bean
							attr.setSystemTable(systemTable);
							systemTableAttributeDao.save(attr);
							System.out.println("新增加的属性名为：" + attr.getAttributeName());
						}
					}
				}
			}
		}

		Set<SystemTableAttribute> attributesBeanChildren = BeanUtils.convertCollection(attributes,
				SystemTableAttribute.class);
		bean.setAttributes(attributesBeanChildren);
		//查找关系表的所有属性
		Set<SystemTableRelationship> relationships = systemTable.getRelationships();
		Set<SystemTableRelationship> relationshipsBeanChildren = BeanUtils.convertCollection(relationships,
				SystemTableRelationship.class);
		bean.setRelationships(relationshipsBeanChildren);
		return bean;
	}

	/** 
	 * 保存系统表结构bean
	 * @throws ClassNotFoundException 
	 * @see com.wellsoft.pt.message.service.SystemTableService#saveBean(com.wellsoft.pt.message.bean.SystemTableBean)
	 */
	@Override
	public boolean saveBean(SystemTableBean bean) throws Exception {

		// 删除系统表关系
		Set<SystemTableRelationshipBean> deletedRelationships = bean.getDeletedRelationships();
		for (SystemTableRelationshipBean deletedRelationship : deletedRelationships) {
			if (deletedRelationship != null) {
				SystemTableRelationship systemTableRelationship = this.systemTableRelationshipDao
						.get(deletedRelationship.getUuid());
				System.out.println("要删除的uuid为：" + deletedRelationship.getUuid());
				systemTableRelationshipDao.delete(systemTableRelationship);
			} else {
				System.out.println("要删除的uuid为null");
			}
		}
		//通过以下方法查找实体类信息是否存在，若返回为null，则不存在实体
		System.out.println("实体类完全限定名：" + bean.getFullEntityName());
		ClassMetadata meta = SessionFactoryUtils.getMultiTenantSessionFactory().getAllClassMetadata()
				.get(bean.getFullEntityName());

		System.out.println("meta的值：" + meta);
		if (meta != null) {
			//1.保存父表的数据
			SystemTable systemTable = new SystemTable();
			// 保存新systemTable 设置id值
			if (StringUtils.isBlank(bean.getUuid())) {
				bean.setUuid(null);

				//先保存父表,然后再保存子表列名
				BeanUtils.copyProperties(bean, systemTable);
				this.systemTableDao.save(systemTable);

				//获取该类首字母小写的类名
				String className = systemTable.getFullEntityName().substring(
						systemTable.getFullEntityName().lastIndexOf(".") + 1);
				String lowerCaseName = className.substring(0, 1).toLowerCase() + className.substring(1);
				System.out.println("首字母小写类名：" + lowerCaseName);

				//2.(1)保存属性表的属性名
				String tableName = getTableName(Class.forName(systemTable.getFullEntityName()));//获取表名
				List<String> fieldList = systemTableDao.getFieldByTableName(tableName);//数据库表字段集合
				BeanWrapperImpl wrapper = new BeanWrapperImpl(Class.forName(bean.getFullEntityName()));
				PropertyDescriptor[] descriptors = wrapper.getPropertyDescriptors();
				for (PropertyDescriptor descriptor : descriptors) {
					SystemTableAttribute attri = new SystemTableAttribute();
					String propertyName = descriptor.getName();
					String propertyToField = StringUtil.propertyToField(propertyName);//将属性转为字段名
					if (!propertyName.equals("attach") && !propertyName.equals("class")) {
						Map<String, String> columnMap = getColumnMap(Class.forName(bean.getFullEntityName()),
								propertyName);
						if (columnMap != null) {
							String fieldName = columnMap.get("name");
							String type = columnMap.get("type");
							if (!type.equals("SET") && !type.equals("LIST")) {//去除关系列
								attri.setFieldName(fieldName);
								attri.setAttributeName(propertyName);
								attri.setColumnAliases(lowerCaseName + "_" + propertyName);
								attri.setEntityName(className);
								attri.setColumnType(type);
								attri.setChineseName(fieldName);
								//多方保存时得将实体类也保存而不是bean
								attri.setSystemTable(systemTable);
								systemTableAttributeDao.save(attri);
								System.out.println("属性为：" + propertyName);
							}
						}
						/*//获得一对多的多值对象
						OneToManyPersister oneToMany = (OneToManyPersister) SessionFactoryUtils
								.getMultiTenantSessionFactory().getAllCollectionMetadata()
								.get(bean.getFullEntityName() + "." + propertyName);
						//若是关联对象属性，则值不为null，自身属性则为null
						if (oneToMany != null) {
							String fullClassName = oneToMany.getElementType().getName();//获取所关联类的完全限定名

							//2.(2)保存关系表
							SystemTableRelationship SystemTableRelationship = new SystemTableRelationship();
							SystemTableRelationship.setMainTableName(bean.getFullEntityName());
							SystemTableRelationship.setSecondaryTableName(fullClassName);
							SystemTableRelationship.setAssociatedAttributes(propertyName);
							SystemTableRelationship.setSystemTable(systemTable);
							systemTableRelationshipDao.save(SystemTableRelationship);

						}*/
					}
				}

				/*//2.保存子表的列名
				List<String> columnsList = getFieldByForm(systemTable);
				for (String column : columnsList) {
					SystemTableAttribute attri = new SystemTableAttribute();
					attri.setAttributeName(column);
					attri.setColumnAliases(lowerCaseName + "_" + column);
					attri.setSystemTable(systemTable);
					systemTableAttributeDao.save(attri);
					System.out.println("列名为：" + column);
				}*/
			} else {
				systemTable = this.systemTableDao.get(bean.getUuid());
				BeanUtils.copyProperties(bean, systemTable);
				this.systemTableDao.save(systemTable);
			}
			// 3.(1)保存属性表修改的数据
			Set<SystemTableAttributeBean> changeAttributes = bean.getChangedAttributes();
			for (SystemTableAttribute changeAttribute : changeAttributes) {
				SystemTableAttribute systemTableAttribute = systemTableAttributeDao.get(changeAttribute.getUuid());
				//将已改变的属性复制给对应的属性
				BeanUtils.copyProperties(changeAttribute, systemTableAttribute);
				this.systemTableAttributeDao.save(systemTableAttribute);
			}

			// 3.(2)保存关系表修改的数据
			Set<SystemTableRelationshipBean> changeRelationships = bean.getChangedRelationships();
			for (SystemTableRelationshipBean changeRelationship : changeRelationships) {

				if (StringUtils.isBlank(changeRelationship.getUuid())) {
					SystemTableRelationship systemTableRelationship = new SystemTableRelationship();
					changeRelationship.setUuid(null);
					SystemTable systemTable1 = systemTableDao.get(bean.getUuid());

					BeanUtils.copyProperties(changeRelationship, systemTableRelationship);
					systemTableRelationship.setSystemTable(systemTable1);
					this.systemTableRelationshipDao.save(systemTableRelationship);
				} else {
					SystemTableRelationship systemTableRelationship = this.systemTableRelationshipDao
							.get(changeRelationship.getUuid());
					//将已改变的属性复制给对应的属性
					BeanUtils.copyProperties(changeRelationship, systemTableRelationship);
					this.systemTableRelationshipDao.save(systemTableRelationship);
				}
			}

		} else {
			System.out.println("不存在该类");
			return false;
		}
		return true;
	}

	/** 
	 * 删除系统表结构
	 * @see com.wellsoft.pt.message.service.SystemTableService#remove(java.lang.String)
	 */
	@Override
	public void remove(SystemTableBean bean) {

		this.systemTableDao.delete(bean.getUuid());
	}

	/**
	 * 
	 * 批量删除
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.systemtable.service.SystemTableService#deleteAllById(java.lang.String[])
	 */
	@Override
	public void deleteAllById(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			SystemTable systemTable = systemTableDao.getById(ids[i]);
			systemTableDao.delete(systemTable);
		}

	}

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.message.service.SystemTableService#query(com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo)
	 */
	@Override
	public JqGridQueryData query(JqGridQueryInfo queryInfo) {
		Page<SystemTable> pageData = new Page<SystemTable>();
		pageData.setPageNo(queryInfo.getPage());
		pageData.setPageSize(queryInfo.getRows());
		this.systemTableDao.findPage(pageData);
		List<SystemTable> systemTables = pageData.getResult();
		List<SystemTable> jqUsers = new ArrayList<SystemTable>();
		for (SystemTable systemTable : systemTables) {
			SystemTable jqSystemTable = new SystemTable();
			BeanUtils.copyProperties(systemTable, jqSystemTable);
			jqUsers.add(jqSystemTable);
		}
		JqGridQueryData queryData = new JqGridQueryData();
		queryData.setCurrentPage(queryInfo.getPage());
		queryData.setDataList(jqUsers);
		queryData.setRepeatitems(false);
		queryData.setTotalPages(pageData.getTotalPages());
		queryData.setTotalRows(pageData.getTotalCount());
		return queryData;
	}

	/**
	 * 
	 *获得表所有字段的集合
	 * (non-Javadoc)
	 * @throws ClassNotFoundException 
	 * @see com.wellsoft.pt.basicdata.systemtable.service.SystemTableService#getFieldByForm(com.wellsoft.pt.basicdata.systemtable.entity.SystemTable)
	 */
	@Override
	public List<String> getFieldByForm(SystemTable systemTable) throws ClassNotFoundException {
		List<String> fields = null;//表字段集合
		if (systemTable != null) {
			Set<SystemTableAttribute> attributes = systemTable.getAttributes();
			String fullEntityName = systemTable.getFullEntityName();
			System.out.println("完全限定名：" + fullEntityName);
			String tableName = getTableName(Class.forName(fullEntityName));
			fields = systemTableDao.getFieldByTableName(tableName);
		} else {
			System.out.println("该类不存在！");
		}
		return fields;
	}

	/**
	 * 
	 * 根据表的uuid得到表的所有字段的类型
	 * 
	 * @param uuid
	 * @return
	 * @throws Exception 
	 * @throws Exception 
	 */
	@Override
	public Map<String, String> getFieldsTypeByForm(SystemTable systemTable) throws Exception {
		Map<String, String> fieldsTypeMap = new HashMap<String, String>();
		String fullEntityName = systemTable.getFullEntityName();
		Class clazz = Class.forName(fullEntityName);
		String tableName = getTableName(clazz);
		fieldsTypeMap = systemTableDao.getFieldsTypeByTable(tableName);
		return fieldsTypeMap;
	}

	/**
	 * 
	 * 系统表数据查询
	 * (non-Javadoc)
	 * @throws ClassNotFoundException 
	 * @see com.wellsoft.pt.basicdata.systemtable.service.SystemTableService#query(java.lang.String, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public List<Map<String, Object>> query(SystemTable systemTable, Boolean distinct, String[] projection,
			String selection, Map<String, Object> selectionArgs, String groupBy, String having, String orderBy,
			int firstResult, int maxResults) throws Exception {
		String fullEntityName = systemTable.getFullEntityName();//获得完全限定名
		String tableName = getTableName(Class.forName(fullEntityName));//获得表名

		List<Map<String, Object>> fieldsResultList = new ArrayList<Map<String, Object>>();//表数据查询之后的集合（存放所有的Map<String, Object>）
		Set<SystemTableAttribute> attributes = systemTable.getAttributes();//获得该表对应的类的属性集合
		String sql = null;
		if (distinct) {
			sql = "SELECT " + " DISTINCT ";
		} else {
			//所要执行的sql语句
			sql = "SELECT ";
		}
		//查询列名不为空
		if (projection != null && !("".equals(projection))) {
			//将数组转为字符串
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < projection.length; i++) {
				buffer.append(projection[i]).append(",");
			}
			String pro = buffer.toString();
			String projection1 = pro.substring(0, pro.lastIndexOf(","));
			if (selection != null && !("".equals(selection))) {
				if (groupBy != null && !("".equals(groupBy))) {
					if (having != null && !("".equals(having))) {
						if (orderBy != null && !("".equals(orderBy))) {
							sql = sql + projection1 + " FROM " + tableName + " WHERE " + selection + " GROUP BY "
									+ groupBy + " HAVING " + having + " ORDER BY " + orderBy;
						} else {
							sql = sql + projection1 + " FROM " + tableName + " WHERE " + selection + " GROUP BY "
									+ groupBy + " HAVING " + having;
						}
					} else {
						if (orderBy != null && !("".equals(orderBy))) {
							sql = sql + projection1 + " FROM " + tableName + " WHERE " + selection + " GROUP BY "
									+ groupBy + " ORDER BY " + orderBy;
						} else {
							sql = sql + projection1 + " FROM " + tableName + " WHERE " + selection + " GROUP BY "
									+ groupBy;
						}
					}
				} else {
					//groupBy如果为null的话，则不能使用HAVING
					if (orderBy != null && !("".equals(orderBy))) {
						sql = sql + projection1 + " FROM " + tableName + " WHERE " + selection + " ORDER BY " + orderBy;
					} else {
						sql = sql + projection1 + " FROM " + tableName + " WHERE " + selection;
					}
				}
			} else {//WHERE条件为空，无条件查询指定列
				if (groupBy != null && !("".equals(groupBy))) {
					if (having != null && !("".equals(having))) {
						if (orderBy != null && !("".equals(orderBy))) {
							sql = sql + projection1 + " FROM " + tableName + " GROUP BY " + groupBy + " HAVING "
									+ having + " ORDER BY " + orderBy;
						} else {
							sql = sql + projection1 + " FROM " + tableName + " GROUP BY " + groupBy + " HAVING "
									+ having;
						}
					} else {
						if (orderBy != null && !("".equals(orderBy))) {
							sql = sql + projection1 + " FROM " + tableName + " GROUP BY " + groupBy + " ORDER BY "
									+ orderBy;
						} else {
							sql = sql + projection1 + " FROM " + tableName + " GROUP BY " + groupBy;
						}
					}
				} else {
					//groupBy如果为null的话，则不能使用HAVING
					if (orderBy != null && !("".equals(orderBy))) {
						sql = sql + projection1 + " FROM " + tableName + " ORDER BY " + orderBy;
					} else {
						sql = sql + projection1 + " FROM " + tableName;
					}
				}
			}
			fieldsResultList = systemTableDao.query(sql, selectionArgs, firstResult, maxResults);
		} else {//查询列名为空,查询所有的列，遍历属性并得到对应的列名
			StringBuffer buffer = new StringBuffer();
			for (SystemTableAttribute systemTableAttribute : attributes) {
				String fieldName = systemTableAttribute.getAttributeName();//获得当前属性所对应的表列名
				buffer.append(fieldName).append(",");
			}
			String columnNames = buffer.substring(0, buffer.lastIndexOf(","));
			if (selection != null && !("".equals(selection))) {
				if (groupBy != null && !("".equals(groupBy))) {
					if (having != null && !("".equals(having))) {
						if (orderBy != null && !("".equals(orderBy))) {
							sql = sql + columnNames + " FROM " + tableName + " WHERE " + selection + " GROUP BY "
									+ groupBy + " HAVING " + having + " ORDER BY " + orderBy;
						} else {
							sql = sql + columnNames + " FROM " + tableName + " WHERE " + selection + " GROUP BY "
									+ groupBy + " HAVING " + having;
						}
					} else {
						if (orderBy != null && !("".equals(orderBy))) {
							sql = sql + columnNames + " FROM " + tableName + " WHERE " + selection + " GROUP BY "
									+ groupBy + " ORDER BY " + orderBy;
						} else {
							sql = sql + columnNames + " FROM " + tableName + " WHERE " + selection + " GROUP BY "
									+ groupBy;
						}
					}
				} else {
					//groupBy如果为null的话，则不能使用HAVING
					if (orderBy != null && !("".equals(orderBy))) {
						sql = sql + columnNames + " FROM " + tableName + " WHERE " + selection + " ORDER BY " + orderBy;
					} else {
						sql = sql + columnNames + " FROM " + tableName + " WHERE " + selection;
					}
				}
			} else {//WHERE条件为空，无条件查询指定列
				if (groupBy != null && !("".equals(groupBy))) {
					if (having != null && !("".equals(having))) {
						if (orderBy != null && !("".equals(orderBy))) {
							sql = sql + columnNames + " FROM " + tableName + " GROUP BY " + groupBy + " HAVING "
									+ having + " ORDER BY " + orderBy;
						} else {
							sql = sql + columnNames + " FROM " + tableName + " GROUP BY " + groupBy + " HAVING "
									+ having;
						}
					} else {
						if (orderBy != null && !("".equals(orderBy))) {
							sql = sql + columnNames + " FROM " + tableName + " GROUP BY " + groupBy + " ORDER BY "
									+ orderBy;
						} else {
							sql = sql + columnNames + " FROM " + tableName + " GROUP BY " + groupBy;
						}
					}
				} else {
					//groupBy如果为null的话，则不能使用HAVING
					if (orderBy != null && !("".equals(orderBy))) {
						sql = sql + columnNames + " FROM " + tableName + " ORDER BY " + orderBy;
					} else {
						sql = sql + columnNames + " FROM " + tableName;
					}
				}
			}
			fieldsResultList = systemTableDao.query(sql, selectionArgs, firstResult, maxResults);
		}

		return fieldsResultList;

	}

	/**
	* 根据类的完全限定名获取表名
	* 
	* @param classtype
	* @return
	*/

	public String getTableName(Class classtype) {
		Annotation[] anno = classtype.getAnnotations();
		String tableName = "";
		for (int i = 0; i < anno.length; i++) {
			if (anno[i] instanceof Table) {
				Table table = (Table) anno[i];
				System.out.println(table.name());
				tableName = table.name();
			}
		}
		return tableName;
	}

	/** 
	*通过实体类和属性，获取实体类属性对应的表字段名称 
	*  
	* @param clazz 
	*            实体类 
	* @param propertyName 
	*            属性名称 
	* @return 字段名称 
	*/

	@SuppressWarnings("unchecked")
	public Map<String, String> getColumnMap(Class clazz, String propertyName) {
		return systemTableDao.getColumnMap(clazz, propertyName);
	}

	/**
	 * 
	 * 获取PersistentClass对象
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static PersistentClass getPersistentClass(Class clazz) {
		PersistentClass pc = getConfiguration().getClassMapping(clazz.getName());
		if (pc == null) {
			Configuration configuration = getConfiguration();
			configuration = getConfiguration().addClass(clazz);
			pc = getConfiguration().getClassMapping(clazz.getName());
		}
		return pc;
	}

	/**
	 * 
	 * 获得Configuration对象
	 * 
	 * @return
	 */
	public static Configuration getConfiguration() {
		//获取Configuration对象
		LocalSessionFactoryBean localSessionFactoryBean = (LocalSessionFactoryBean) ApplicationContextHolder
				.getBean(Separator.AMPERSAND.getValue() + SessionFactoryUtils.getTenantSessionFactoryBeanName());
		Configuration configuration = localSessionFactoryBean.getConfiguration();
		return configuration;
	}

	/**
	 * 
	 * 获得所有系统表集合
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.systemtable.service.SystemTableService#getTableImformation()
	 */
	@Override
	public List<SystemTable> getAllSystemTables() {
		//获得所有的系统表
		List<SystemTable> systemTableList = systemTableDao.getAll();
		return systemTableList;
	}

	/**
	 * 
	 * 返回关系表的关联属性选项，用于select选项
	 * 
	 * @return
	 * @throws ClassNotFoundException 
	 */
	@Override
	public List<String> getAssociatedAttributes(SystemTableBean bean) throws ClassNotFoundException {
		//		SystemTable systemTable = systemTableDao.get(uuid);
		BeanWrapperImpl wrapper = new BeanWrapperImpl(Class.forName(bean.getFullEntityName()));
		PropertyDescriptor[] descriptors = wrapper.getPropertyDescriptors();
		List<String> associatedAttributesList = new ArrayList<String>();
		for (PropertyDescriptor descriptor : descriptors) {
			String propertyName = descriptor.getName();
			if (!("class".equals(propertyName) || "attach".equals(propertyName))) {
				String propertyType = descriptor.getPropertyType().getSimpleName();
				System.out.println("属性名：" + propertyName + "\t\t类型：" + propertyType);
				if (!("Integer".equals(propertyType) || "Long".equals(propertyType) || "Double".equals(propertyType)
						|| "Date".equals(propertyType) || "String".equals(propertyType) || "Boolean"
							.equals(propertyType))) {
					associatedAttributesList.add(propertyName);
					System.out.println("所需属性名为：" + propertyName);
				}
			}
		}
		return associatedAttributesList;
	}

	/**
	 * 
	 * 返回指定模块ID下的所有系统表属性集合
	 * 
	 * @param ModuleId
	 * @return
	 */
	@Override
	public List<SystemTableAttribute> getSystemTableAttributesByModuleId(String moduleId) {
		List<SystemTable> systemTableList = systemTableDao.findBy("moduleName", moduleId);
		//存放该模块下的所有系统表属性集合
		List<SystemTableAttribute> allSystemTableAttributeList = new ArrayList<SystemTableAttribute>();
		for (SystemTable systemTable : systemTableList) {
			List<SystemTableAttribute> systemTableAttributeList = systemTableAttributeDao.findBy("systemTable",
					systemTable);
			for (SystemTableAttribute systemTableAttribute : systemTableAttributeList) {
				allSystemTableAttributeList.add(systemTableAttribute);
			}
		}
		return allSystemTableAttributeList;
	}

	/**
	 * 返回指定模块ID下的所有系统表关系集合
	 */
	@Override
	public List<SystemTableRelationship> getSystemTableRelationshipsByModuleId(String moduleId) {
		List<SystemTable> systemTableList = systemTableDao.findBy("moduleName", moduleId);
		//存放该模块下的所有系统表关系集合
		List<SystemTableRelationship> allSystemTableRelationshipList = new ArrayList<SystemTableRelationship>();
		for (SystemTable systemTable : systemTableList) {
			List<SystemTableRelationship> systemTableRelationshipList = systemTableRelationshipDao.findBy(
					"systemTable", systemTable);
			for (SystemTableRelationship systemTableRelationship : systemTableRelationshipList) {
				allSystemTableRelationshipList.add(systemTableRelationship);
			}
		}
		return allSystemTableRelationshipList;
	}

	/**
	 * 
	 * 获取所有的模块名
	 * 
	 * @return
	 */
	@Override
	public List<DataDictionary> getAllModuleName() {
		List<DataDictionary> dataDictionaryList = basicDataApiFacade.getDataDictionariesByType("MODULE_ID");
		return dataDictionaryList;
	}
}