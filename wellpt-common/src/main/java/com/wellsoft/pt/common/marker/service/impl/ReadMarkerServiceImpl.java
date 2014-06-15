/*
 * @(#)2013-3-1 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.common.marker.service.impl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.common.marker.dao.ReadMarkerDao;
import com.wellsoft.pt.common.marker.entity.ReadMarker;
import com.wellsoft.pt.common.marker.entity.ReadMarkerId;
import com.wellsoft.pt.common.marker.service.ReadMarkerService;
import com.wellsoft.pt.core.entity.IdEntity;
import com.wellsoft.pt.core.service.impl.BaseServiceImpl;
import com.wellsoft.pt.core.support.QueryItem;

/**
 * Description: 如何描述该类
 * 
 * @author zhulh
 * @date 2013-3-1
 * @version 1.0
 * 
 *          <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-3-1.1	zhulh		2013-3-1		Create
 * </pre>
 * 
 */
@Service
@Transactional
public class ReadMarkerServiceImpl extends BaseServiceImpl implements ReadMarkerService {

	private static final Map<Class<?>, Map<String, PropertyDescriptor>> classPropertyDescriptorMap = new HashMap<Class<?>, Map<String, PropertyDescriptor>>();

	@Autowired
	private ReadMarkerDao readMarkerDao;

	/**
	 * 根据对象UUID，将对象设置为对所有人未读，删除UUID相关的记录即可 (non-Javadoc)
	 * 
	 * @see com.wellsoft.pt.common.marker.service.ReadMarkerService#markNew(java.lang.String)
	 */
	@Override
	public void markNew(String uuid) {
		this.readMarkerDao.deleteByEntityUuid(uuid);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.pt.common.marker.service.ReadMarkerService#markNew(com.wellsoft.pt.core.entity.IdEntity)
	 */
	@Override
	public <ENTITY extends IdEntity> void markNew(ENTITY entity) {
		this.markNew(entity.getUuid());
	}

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.common.marker.service.ReadMarkerService#markNew(java.lang.String, java.lang.String)
	 */
	@Override
	public void markNew(String uuid, String userId) {
		this.readMarkerDao.delete(uuid, userId);
	}

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.common.marker.service.ReadMarkerService#markNew(com.wellsoft.pt.core.entity.IdEntity, java.lang.String)
	 */
	@Override
	public <ENTITY extends IdEntity> void markNew(ENTITY entity, String userId) {
		this.markNew(entity.getUuid(), userId);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.pt.common.marker.service.ReadMarkerService#markRead(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void markRead(String uuid, String userId) {
		ReadMarker readMarker = new ReadMarker(uuid, userId);
		if (!isExist(readMarker)) {
			this.readMarkerDao.save(readMarker);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.pt.common.marker.service.ReadMarkerService#markRead(com.wellsoft.pt.core.entity.IdEntity,
	 *      java.lang.String)
	 */
	@Override
	public <ENTITY extends IdEntity> void markRead(ENTITY entity, String userId) {
		this.markRead(entity.getUuid(), userId);
	}

	private boolean isExist(ReadMarker readMarker) {
		return this.readMarkerDao.getSession().get(ReadMarker.class, readMarker.getId()) != null;
	}

	private boolean isExist(String uuid, String userId) {
		return isExist(new ReadMarker(uuid, userId));
	}

	/** 
	* (non-Javadoc)
	* @see com.wellsoft.pt.common.marker.service.ReadMarkerService#isRead(java.lang.String, java.lang.String)
	*/
	@Override
	public ReadMarker get(String uuid, String userId) {
		return this.readMarkerDao.get(new ReadMarkerId(uuid, userId));
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.pt.common.marker.service.ReadMarkerService#getReadList(java.util.List,
	 *      java.lang.String)
	 */
	@Override
	public <ENTITY extends IdEntity> List<String> getReadList(List<String> uuids, String userId) {
		List<String> readList = new ArrayList<String>();
		for (String uuid : uuids) {
			if (isExist(uuid, userId)) {
				readList.add(uuid);
			}
		}
		return readList;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.pt.common.marker.service.ReadMarkerService#getReadList(java.util.Collection,
	 *      java.lang.String)
	 */
	@Override
	public <ENTITY extends IdEntity> Collection<ENTITY> getReadList(Collection<ENTITY> entities, String userId) {
		List<ENTITY> readList = new ArrayList<ENTITY>();
		for (ENTITY entity : entities) {
			if (isExist(entity.getUuid(), userId)) {
				readList.add(entity);
			}
		}
		return readList;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.pt.common.marker.service.ReadMarkerService#getUnReadList(java.util.List,
	 *      java.lang.String)
	 */
	@Override
	public <ENTITY extends IdEntity> List<String> getUnReadList(List<String> uuids, String userId) {
		List<String> unReadList = new ArrayList<String>();
		for (String uuid : uuids) {
			if (!isExist(uuid, userId)) {
				unReadList.add(uuid);
			}
		}
		return unReadList;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.pt.common.marker.service.ReadMarkerService#getUnReadList(java.util.Collection,
	 *      java.lang.String)
	 */
	@Override
	public <ENTITY extends IdEntity> Collection<ENTITY> getUnReadList(Collection<ENTITY> entities, String userId) {
		List<ENTITY> unReadList = new ArrayList<ENTITY>();
		for (ENTITY entity : entities) {
			if (!isExist(entity.getUuid(), userId)) {
				unReadList.add(entity);
			}
		}
		return unReadList;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.pt.common.marker.service.ReadMarkerService#markList(java.util.List,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public <ENTITY extends IdEntity> void markList(List<ENTITY> entities, String userId, String flagField) {
		for (ENTITY entity : entities) {
			BeanWrapper wrapper = new BeanWrapperImpl(entity);
			PropertyDescriptor propertyDescriptor = getPropertyDescriptor(entity.getClass(), flagField);
			if (propertyDescriptor != null) {
				Class<?> propertyType = propertyDescriptor.getPropertyType();
				if (isExist(entity.getUuid(), userId)) {
					if (propertyType.isAssignableFrom(Boolean.class)) {
						wrapper.setPropertyValue(flagField, true);
					} else if (propertyType.isAssignableFrom(String.class)) {
						wrapper.setPropertyValue(flagField, "true");
					} else if (propertyType.isAssignableFrom(Integer.class)) {
						wrapper.setPropertyValue(flagField, "1");
					}
				} else {
					if (propertyType.isAssignableFrom(Boolean.class)) {
						wrapper.setPropertyValue(flagField, false);
					} else if (propertyType.isAssignableFrom(String.class)) {
						wrapper.setPropertyValue(flagField, "false");
					} else if (propertyType.isAssignableFrom(Integer.class)) {
						wrapper.setPropertyValue(flagField, "0");
					}
				}
			}
		}
	}

	private PropertyDescriptor getPropertyDescriptor(Class<? extends IdEntity> entityClass, String flagField) {
		if (classPropertyDescriptorMap.containsKey(entityClass)) {
			Map<String, PropertyDescriptor> propertyDescriptorMap = classPropertyDescriptorMap.get(entityClass);
			return propertyDescriptorMap.get(flagField);
		} else {
			classPropertyDescriptorMap.put(entityClass, new HashMap<String, PropertyDescriptor>());
			BeanWrapper wrapper = new BeanWrapperImpl(entityClass);
			PropertyDescriptor[] propertyDescriptors = wrapper.getPropertyDescriptors();
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				if (propertyDescriptor.getName().equals(flagField)) {
					classPropertyDescriptorMap.get(entityClass).put(propertyDescriptor.getName(), propertyDescriptor);
					return propertyDescriptor;
				}
			}
		}
		return null;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.pt.common.marker.service.ReadMarkerService#markList(java.util.List,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void markList(List<QueryItem> items, String userId, String keyField, String flagField) {
		for (QueryItem queryItem : items) {
			if (queryItem.get(keyField) != null && StringUtils.isNotBlank(queryItem.get(keyField).toString())) {
				if (isExist(queryItem.get(keyField).toString(), userId)) {
					queryItem.put(flagField, true);
				} else {
					queryItem.put(flagField, false);
				}
			} else {
				queryItem.put(flagField, false);
			}
		}
	}

}
