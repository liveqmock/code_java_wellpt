/*
 * @(#)2012-11-15 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.datadict.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.basicdata.datadict.bean.DataDictionaryAttributeBean;
import com.wellsoft.pt.basicdata.datadict.bean.DataDictionaryBean;
import com.wellsoft.pt.basicdata.datadict.dao.DataDictionaryAttributeDao;
import com.wellsoft.pt.basicdata.datadict.dao.DataDictionaryDao;
import com.wellsoft.pt.basicdata.datadict.entity.DataDictionary;
import com.wellsoft.pt.basicdata.datadict.entity.DataDictionaryAttribute;
import com.wellsoft.pt.basicdata.datadict.service.DataDictionaryService;
import com.wellsoft.pt.basicdata.dyview.support.TreeNodeForView;
import com.wellsoft.pt.common.component.tree.TreeNode;
import com.wellsoft.pt.common.enums.IdPrefix;
import com.wellsoft.pt.common.enums.Separator;
import com.wellsoft.pt.core.service.CommonValidateService;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.org.entity.Department;
import com.wellsoft.pt.org.entity.User;
import com.wellsoft.pt.org.facade.OrgApiFacade;
import com.wellsoft.pt.security.acl.entity.AclSid;
import com.wellsoft.pt.security.acl.service.AclService;
import com.wellsoft.pt.security.core.userdetails.UserDetails;
import com.wellsoft.pt.utils.bean.BeanUtils;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

/**
 * Description: 数据字典服务层实现类
 * 
 * @author zhulh
 * @date 2012-11-15
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-11-15.1	zhulh		2012-11-15		Create
 * </pre>
 * 
 */
@Service
@Transactional
public class DataDictionaryServiceImpl implements DataDictionaryService {

	@Autowired
	private AclService aclService;

	@Autowired
	private DataDictionaryDao dataDictionaryDao;

	@Autowired
	private DataDictionaryAttributeDao dataDictionaryAttributeDao;

	@Autowired
	private OrgApiFacade orgApiFacade;

	@Autowired
	private CommonValidateService commonValidateService;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.pt.basicdata.datadic.service.DataDictionaryService#get(java.lang.String)
	 */
	@Override
	public DataDictionary get(String uid) {
		return dataDictionaryDao.get(uid);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.pt.basicdata.datadic.service.DataDictionaryService#save(com.wellsoft.pt.basicdata.datadic.entity.DataDictionary)
	 */
	@Override
	public DataDictionary saveAcl(DataDictionary dataDictionary) {
		List<AclSid> aclSids = aclService.getSid(dataDictionary);
		List<String> existSids = new ArrayList<String>();
		for (AclSid aclSid : aclSids) {
			existSids.add(aclSid.getSid());
		}
		List<String> sids = getAclSid(dataDictionary);
		//新的SID
		List<String> newSids = new ArrayList<String>();
		for (String newSid : sids) {
			if (!existSids.contains(newSid)) {
				newSids.add(newSid);
			}
		}
		//要删除的SID
		List<String> delSids = new ArrayList<String>();
		for (String newSid : existSids) {
			if (!sids.contains(newSid)) {
				delSids.add(newSid);
			}
		}

		//删除
		for (String sid : delSids) {
			aclService.removePermission(dataDictionary, BasePermission.ADMINISTRATION, sid);
		}
		// 新增
		if (dataDictionary.getParent() != null) {
			aclService.save(dataDictionary, dataDictionary.getParent(), sids.get(0), BasePermission.ADMINISTRATION);
		}
		for (String sid : sids) {
			if (!existSids.contains(sid)) {
				aclService.addPermission(dataDictionary, BasePermission.ADMINISTRATION, sid);
			}
		}
		return aclService.get(DataDictionary.class, dataDictionary.getUuid(), BasePermission.ADMINISTRATION);
	}

	/**
	 * 返回数据字典所有者在ACL中的SID
	 * 
	 * @param dataDictionary
	 * 
	 * @return
	 */
	private List<String> getAclSid(DataDictionary dataDictionary) {
		List<String> newOwners = new ArrayList<String>();
		if (dataDictionary.getOwners().isEmpty()) {
			// "ROLE_DATA_DIC"
			dataDictionary.getOwners().add(ACL_SID);
			return dataDictionary.getOwners();
		} else {
			List<String> owners = dataDictionary.getOwners();
			for (String owner : owners) {
				if (owner.startsWith(IdPrefix.DEPARTMENT.getValue())) {
					owner = "ROLE_" + owner;
				}
				newOwners.add(owner);
			}
		}
		// 返回组织部门中选择的角色作为SID
		return newOwners;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.pt.basicdata.datadic.service.DataDictionaryService#removeByPk(java.lang.String)
	 */
	@Override
	public void removeByPk(String uuid) {
		aclService.removeByPk(DataDictionary.class, uuid);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.pt.basicdata.datadic.service.DataDictionaryService#removeAllByPk(java.util.Collection)
	 */
	@Override
	public void removeAllByPk(Collection<String> uuids) {
		aclService.removeAllByPk(DataDictionary.class, uuids);
	}

	/**
	 * 判断当前登录用户是否在指定的组织部门中
	 * 
	 * @param dataDictionary
	 * @param sid
	 */
	private Boolean hasPermission(DataDictionary dataDictionary) {
		Boolean hasPermission = false;
		// 获取该字典的所有SID，判断是否有访问权限
		List<AclSid> aclSids = aclService.getSid(dataDictionary);
		// 如果SID为空，默认有权限，以免在导入基础数据角本后还要更新ACL数据
		if (aclSids.isEmpty()) {
			return true;
		}
		for (AclSid aclSid : aclSids) {
			String sid = aclSid.getSid();
			// 如果所有者是默认的则有权限
			if (sid.equals(ACL_SID)) {
				hasPermission = true;
				break;
			} else {// 由组织部门提供接口，判断当前登录用户是否在指定的SID(组织部门)中
				if (sid.startsWith(IdPrefix.USER.getValue())) {
					if (StringUtils.equals(((UserDetails) SpringSecurityUtils.getCurrentUser()).getUserId(), sid)) {
						hasPermission = true;
						break;
					}
				} else {
					hasPermission = false;
				}
			}
		}
		return hasPermission;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.pt.basicdata.datadict.service.DataDictionaryService#getAsTreeAsync(java.lang.String)
	 */
	@Override
	public List<TreeNode> getAsTreeAsync(String uuid) {
		List<DataDictionary> list = new ArrayList<DataDictionary>();
		TreeNode node = new TreeNode();
		// 查询所有根结点
		if (TreeNode.ROOT_ID.equals(uuid)) {
			list = this.dataDictionaryDao.getTopLevel();
		} else {
			// 查询指定结点的下一级子结点
			DataDictionary dataDictionary = this.dataDictionaryDao.get(uuid);
			if (dataDictionary != null) {
				list = Arrays.asList(dataDictionary.getChildren().toArray(new DataDictionary[0]));
			}
		}

		List<TreeNode> children = node.getChildren();
		for (DataDictionary dataDictionary : list) {
			TreeNode child = new TreeNode();
			child.setId(dataDictionary.getUuid());
			child.setName(dataDictionary.getName());
			child.setData(dataDictionary.getCode());
			child.setIsParent(dataDictionary.getChildren().size() > 0);
			child.setNocheck(dataDictionary.getChildren().size() > 0);
			children.add(child);
		}
		return children;
	}

	/** 
	 * 异步加载树形结点，维护时不需要考虑ACL权限(视图专用)
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.datadict.service.DataDictionaryService#getAsTreeAsyncForView(java.lang.String)
	 */
	@Override
	public List<TreeNode> getAsTreeAsyncForView(String uuid) {
		List<DataDictionary> list = new ArrayList<DataDictionary>();
		TreeNodeForView node = new TreeNodeForView();
		// 查询所有根结点
		if (TreeNode.ROOT_ID.equals(uuid)) {
			list = this.dataDictionaryDao.getTopLevel();
		} else {
			// 查询指定结点的下一级子结点
			DataDictionary dataDictionary = this.dataDictionaryDao.get(uuid);
			if (dataDictionary != null) {
				list = Arrays.asList(dataDictionary.getChildren().toArray(new DataDictionary[0]));
			}
		}
		List<TreeNode> children = node.getChildren();
		for (DataDictionary dataDictionary : list) {
			TreeNodeForView child = new TreeNodeForView();
			Set<DataDictionaryAttribute> attributes = dataDictionary.getAttributes();
			for (DataDictionaryAttribute a : attributes) {
				child.setAttribute(a.getValue());
			}
			child.setId(dataDictionary.getUuid());
			child.setName(dataDictionary.getName());
			child.setData(dataDictionary.getCode());
			child.setIsParent(dataDictionary.getChildren().size() > 0);
			child.setNocheck(dataDictionary.getChildren().size() > 0);
			children.add(child);
		}
		return children;
	}

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.datadict.service.DataDictionaryService#getFromTypeAsTreeAsync(java.lang.String, java.lang.String)
	 */
	@Override
	public List<TreeNode> getFromTypeAsTreeAsync(String uuid, String type) {
		String rootUuid = uuid;
		if (TreeNode.ROOT_ID.equals(uuid)) {
			DataDictionary dataDictionary = this.getByType(type);
			if (dataDictionary != null) {
				rootUuid = dataDictionary.getUuid();
			} else {
				TreeNode node = new TreeNode();
				return node.getChildren();
			}
		}
		return getAsTreeAsync(rootUuid);
	}

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.datadict.service.DataDictionaryService#getFromTypeAsTreeAsync(java.lang.String, java.lang.String)
	 */
	@Override
	public List<TreeNode> getViewTypeAsTreeAsync(String uuid, String type) {
		String rootUuid = uuid;
		if (TreeNode.ROOT_ID.equals(uuid)) {
			DataDictionary dataDictionary = this.getByType(type);
			if (dataDictionary != null) {
				rootUuid = dataDictionary.getUuid();
			} else {
				TreeNode node = new TreeNode();
				return node.getChildren();
			}
		}
		return getAsTreeAsyncForView(rootUuid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wellsoft.pt.basicdata.datadict.service.DataDictionaryService#getBean
	 * (java.lang.String)
	 */
	@Override
	public DataDictionaryBean getBean(String uuid) {
		DataDictionary dataDictionary = this.dataDictionaryDao.get(uuid);
		DataDictionaryBean bean = new DataDictionaryBean();
		BeanUtils.copyProperties(dataDictionary, bean);

		// 1、设置父节点
		DataDictionary parent = dataDictionary.getParent();
		if (parent != null) {
			bean.setParentName(parent.getName());
			bean.setParentUuid(parent.getUuid());
		}

		// 2、设置子结点
		Set<DataDictionary> children = dataDictionary.getChildren();
		Set<DataDictionary> beanChildren = BeanUtils.convertCollection(children, DataDictionary.class);
		bean.setChildren(beanChildren);

		// 3、设置字典属性
		Set<DataDictionaryAttribute> attributes = dataDictionary.getAttributes();
		Set<DataDictionaryAttribute> beanAttributes = BeanUtils.convertCollection(attributes,
				DataDictionaryAttribute.class);
		bean.setAttributes(beanAttributes);

		// 4、设置所有者
		List<AclSid> aclSids = aclService.getSid(dataDictionary);
		List<String> sids = new ArrayList<String>();
		for (AclSid sid : aclSids) {
			if (ACL_SID.equals(sid.getSid())) {
				continue;
			}
			sids.add(sid.getSid());
		}
		StringBuilder ownerIds = new StringBuilder();
		StringBuilder ownerNames = new StringBuilder();
		Iterator<String> it = sids.iterator();
		while (it.hasNext()) {
			String sid = it.next();
			if (sid.startsWith(IdPrefix.USER.getValue())) {
				User user = orgApiFacade.getUserById(sid);
				ownerIds.append(user.getId());
				ownerNames.append(user.getUserName());
			} else if (sid.startsWith(IdPrefix.ROLE.getValue())) {
				sid = sid.substring(IdPrefix.ROLE.getName().length() + 1);
				Department department = orgApiFacade.getDepartmentById(sid);
				if (department != null) {
					ownerIds.append(department.getId());
					ownerNames.append(department.getName());
				}
			}
			if (it.hasNext()) {
				ownerIds.append(Separator.SEMICOLON.getValue());
				ownerNames.append(Separator.SEMICOLON.getValue());
			}
		}
		bean.setOwnerIds(ownerIds.toString());
		bean.setOwnerNames(ownerNames.toString());

		return bean;
	}

	/*
	 * 保存数据字典
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wellsoft.pt.basicdata.datadict.service.DataDictionaryService#saveBean
	 * (com.wellsoft.pt.basicdata.datadict.bean.DataDictionaryBean)
	 */
	@Override
	public void saveBean(DataDictionaryBean bean) {
		DataDictionary dataDictionary = new DataDictionary();
		if (StringUtils.isNotBlank(bean.getUuid())) {
			dataDictionary = this.dataDictionaryDao.get(bean.getUuid());
			// 类型非空唯一性判断
			if (StringUtils.isNotBlank(bean.getType())) {
				if (!commonValidateService.checkUnique(bean.getUuid(), "dataDictionary", "type", bean.getType())) {
					throw new RuntimeException("已经存在类型为[" + bean.getType() + "]的字典!");
				}
			}
		} else {
			// 类型非空唯一性判断
			if (StringUtils.isNotBlank(bean.getType())) {
				if (commonValidateService.checkExists("dataDictionary", "type", bean.getType())) {
					throw new RuntimeException("已经存在类型为[" + bean.getType() + "]的字典!");
				}
			}
		}
		BeanUtils.copyProperties(bean, dataDictionary);

		// 1、设置父节点
		if (StringUtils.isNotBlank(bean.getParentUuid())) {
			dataDictionary.setParent(this.dataDictionaryDao.get(bean.getParentUuid()));
		}

		// 2、设置所有者
		if (StringUtils.isNotBlank(bean.getOwnerIds())) {
			String[] ownerIds = StringUtils.split(bean.getOwnerIds(), Separator.SEMICOLON.getValue());
			dataDictionary.setOwners(Arrays.asList(ownerIds));
		}

		this.dataDictionaryDao.save(dataDictionary);

		// 3、保存子结点
		for (DataDictionaryBean child : bean.getDeletedChildren()) {
			if (StringUtils.isNotBlank(child.getUuid())) {
				this.remove(child.getUuid());
			}
		}
		Set<DataDictionaryBean> children = bean.getChangedChildren();
		for (DataDictionaryBean child : children) {
			if (StringUtils.isNotBlank(child.getUuid())) {
				// 类型非空唯一性判断
				if (StringUtils.isNotBlank(child.getType())) {
					if (!commonValidateService.checkUnique(child.getUuid(), "dataDictionary", "type", child.getType())) {
						throw new RuntimeException("已经存在类型为[" + child.getType() + "]的字典!");
					}
				}
				DataDictionary datadict = this.dataDictionaryDao.get(child.getUuid());
				BeanUtils.copyProperties(child, datadict);
				this.dataDictionaryDao.save(datadict);
			} else {
				// 类型非空唯一性判断
				if (StringUtils.isNotBlank(child.getType())) {
					if (commonValidateService.checkExists("dataDictionary", "type", child.getType())) {
						throw new RuntimeException("已经存在类型为[" + child.getType() + "]的字典!");
					}
				}
				DataDictionary dataDictionaryModel = new DataDictionary();
				BeanUtils.copyProperties(child, dataDictionaryModel);
				dataDictionaryModel.setUuid(null);
				dataDictionaryModel.setParent(dataDictionary);
				this.dataDictionaryDao.save(dataDictionaryModel);
			}
		}

		// 4、保存字典属性
		for (DataDictionaryAttribute attribute : bean.getDeletedAttributes()) {
			if (StringUtils.isNotBlank(attribute.getUuid())) {
				dataDictionaryAttributeDao.delete(attribute.getUuid());
			}
		}
		Set<DataDictionaryAttributeBean> attributeBeans = bean.getChangedAttributes();
		for (DataDictionaryAttributeBean attribute : attributeBeans) {
			if (StringUtils.isNotBlank(attribute.getUuid())) {
				DataDictionaryAttribute attributeModel = this.dataDictionaryAttributeDao.get(attribute.getUuid());
				BeanUtils.copyProperties(attribute, attributeModel);
				this.dataDictionaryAttributeDao.save(attributeModel);
			} else {
				DataDictionaryAttribute attributeModel = new DataDictionaryAttribute();
				BeanUtils.copyProperties(attribute, attributeModel);
				attributeModel.setUuid(null);
				attributeModel.setDataDictionary(dataDictionary);
				this.dataDictionaryAttributeDao.save(attributeModel);
			}
		}

		// 5、ACL行数据权限保存
		this.saveAcl(dataDictionary);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wellsoft.pt.basicdata.datadict.service.DataDictionaryService#remove
	 * (java.lang.String)
	 */
	@Override
	public void remove(String uuid) {
		this.aclService.removeByPk(DataDictionary.class, uuid);
		this.dataDictionaryDao.delete(uuid);
	}

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.datadict.service.DataDictionaryService#getByType(java.lang.String)
	 */
	@Override
	public DataDictionary getByType(String type) {
		// 直接从数据库查，以获取所有者
		DataDictionary dataDictionary = dataDictionaryDao.getByType(type);
		if (dataDictionary == null) {
			return null;
		}

		// 如果没有权限访问返回null
		if (!hasPermission(dataDictionary)) {
			return null;
		}

		// 转换字典属性对象
		dataDictionary.setAttributes(BeanUtils.convertCollection(dataDictionary.getAttributes(),
				DataDictionaryAttribute.class));

		return dataDictionary;
	}

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.datadict.service.DataDictionaryService#getDataDictionariesByType(java.lang.String)
	 */
	@Override
	public List<DataDictionary> getDataDictionariesByType(String type) {
		List<DataDictionary> list = new ArrayList<DataDictionary>();

		DataDictionary dataDictionary = getByType(type);
		if (dataDictionary == null) {
			return list;
		}

		// 获取与判断子结点是否有访问权限
		Set<DataDictionary> children = dataDictionary.getChildren();
		for (DataDictionary child : children) {
			if (hasPermission(child)) {
				DataDictionary dictionary = new DataDictionary();
				BeanUtils.copyProperties(child, dictionary);
				// 转换字典属性对象
				dictionary.setAttributes(BeanUtils.convertCollection(child.getAttributes(),
						DataDictionaryAttribute.class));
				list.add(dictionary);
			}
		}
		return list;
	}

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.datadict.service.DataDictionaryService#getKeyValuePair(java.lang.String, java.lang.String)
	 */
	@Override
	public QueryItem getKeyValuePair(String sourceType, String targetCode) {
		QueryItem item = new QueryItem();
		item.put("label", sourceType);
		item.put("value", targetCode);
		if (StringUtils.isNotBlank(sourceType) && StringUtils.isNotBlank(targetCode)) {
			DataDictionary dataDictionary = this.getByType(sourceType);
			if (StringUtils.equals(dataDictionary.getCode(), targetCode)) {
				item.put("label", dataDictionary.getName());
				item.put("value", targetCode);
			} else {
				String[] codes = targetCode.split(Separator.SEMICOLON.getValue());
				item.clear();
				for (int index = 0; index < codes.length; index++) {
					traverseChildren(dataDictionary, codes[index], item, "");
				}
			}
		}
		return item;
	}

	/**
	 * @param parent
	 * @param code
	 * @param item
	 */
	private boolean traverseChildren(DataDictionary parent, String code, QueryItem item, String path) {
		Set<DataDictionary> children = parent.getChildren();
		for (DataDictionary child : children) {
			if (!StringUtils.equals(child.getCode(), code)) {
				if (traverseChildren(child, code, item, path + child.getName() + Separator.SLASH.getValue())) {
					return true;
				}
			} else {
				if (item.get("value") == null) {
					item.put("label", path + child.getName());
					item.put("value", code);
				} else {
					item.put("label", item.get("label") + Separator.SEMICOLON.getValue() + path + child.getName());
					item.put("value", item.get("value") + Separator.SEMICOLON.getValue() + code);
				}
				return true;
			}
		}
		return false;
	}

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.datadict.service.DataDictionaryService#getDataDictionaries(java.lang.String, java.lang.String)
	 */
	@Override
	public List<DataDictionary> getDataDictionaries(String type, String code) {
		List<DataDictionary> list = new ArrayList<DataDictionary>();
		List<DataDictionary> dataDictionaries = dataDictionaryDao.getDataDictionaries(type, code);
		for (DataDictionary dataDictionary : dataDictionaries) {
			if (hasPermission(dataDictionary)) {
				DataDictionary dictionary = new DataDictionary();
				BeanUtils.copyProperties(dataDictionary, dictionary);
				// 转换字典属性对象
				dictionary.setAttributes(BeanUtils.convertCollection(dataDictionary.getAttributes(),
						DataDictionaryAttribute.class));
				list.add(dictionary);
			}
		}
		return list;
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.datadict.service.DataDictionaryService#getDataDictionaryName(java.lang.String, java.lang.String)
	 */
	@Override
	public Map<String, Object> getDataDictionaryName(String type, String code) {
		Map<String, Object> map = new HashMap<String, Object>();
		DataDictionary dataDictionary = new DataDictionary();
		List<DataDictionary> dataDictionaries = dataDictionaryDao.getDataDictionaries(type, code);
		if (dataDictionaries.size() > 0) {
			dataDictionary = dataDictionaries.get(0);
			if (hasPermission(dataDictionary)) {
				DataDictionary dictionary = new DataDictionary();
				BeanUtils.copyProperties(dataDictionary, dictionary);
				// 转换字典属性对象
				dictionary.setAttributes(BeanUtils.convertCollection(dataDictionary.getAttributes(),
						DataDictionaryAttribute.class));
				map.put("label", dictionary.getName());
			}
		} else {
			map.put("label", code);
		}
		return map;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.pt.basicdata.datadict.service.DataDictionaryService#getAsTreeAsyncForControl(java.lang.String)
	 */
	@Override
	public List<TreeNode> getAsTreeAsyncForControl(String uuid) {
		List<DataDictionary> list = new ArrayList<DataDictionary>();
		TreeNode node = new TreeNode();
		// 查询所有根结点
		if (TreeNode.ROOT_ID.equals(uuid)) {
			list = this.dataDictionaryDao.getTopLevel();
		} else {
			// 查询指定结点的下一级子结点
			DataDictionary dataDictionary = this.dataDictionaryDao.get(uuid);
			if (dataDictionary != null) {
				list = Arrays.asList(dataDictionary.getChildren().toArray(new DataDictionary[0]));
			}
		}

		List<TreeNode> children = node.getChildren();
		for (DataDictionary dataDictionary : list) {
			TreeNode child = new TreeNode();
			child.setId(dataDictionary.getUuid());
			child.setName(dataDictionary.getName());
			child.setData(dataDictionary.getType());//此处返回的是type
			child.setIsParent(dataDictionary.getChildren().size() > 0);
			child.setNocheck(dataDictionary.getChildren().size() > 0);
			children.add(child);
		}
		return children;
	}

	/**
	 * 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.datadict.service.DataDictionaryService#getAsTreeAsyncForUuid(java.lang.String)
	 */
	@Override
	public List<TreeNode> getAsTreeAsyncForUuid(String uuid, String type) {
		List<DataDictionary> list = new ArrayList<DataDictionary>();
		DataDictionary parentdataDictionary = this.getByType(type);
		if (TreeNode.ROOT_ID.equals(uuid)) {
			list = Arrays.asList(parentdataDictionary.getChildren().toArray(new DataDictionary[0]));
		} else {
			// 查询指定结点的下一级子结点
			DataDictionary dataDictionary = this.dataDictionaryDao.get(uuid);
			if (dataDictionary != null) {
				list = Arrays.asList(dataDictionary.getChildren().toArray(new DataDictionary[0]));
			}
		}

		TreeNode node = new TreeNode();
		List<TreeNode> children = node.getChildren();
		for (DataDictionary dataDictionary : list) {
			TreeNode child = new TreeNode();
			child.setId(dataDictionary.getUuid());
			child.setName(dataDictionary.getName());
			child.setData(dataDictionary.getUuid());//此处返回的是uuid
			child.setIsParent(dataDictionary.getChildren().size() > 0);
			child.setNocheck(dataDictionary.getChildren().size() > 0);
			children.add(child);
		}
		return children;
	}
}
