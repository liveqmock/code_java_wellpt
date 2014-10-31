package com.wellsoft.pt.basicdata.serialnumber.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.basicdata.serialnumber.bean.SerialNumberMaintainBean;
import com.wellsoft.pt.basicdata.serialnumber.dao.SerialNumberDao;
import com.wellsoft.pt.basicdata.serialnumber.dao.SerialNumberMaintainDao;
import com.wellsoft.pt.basicdata.serialnumber.entity.SerialNumber;
import com.wellsoft.pt.basicdata.serialnumber.entity.SerialNumberMaintain;
import com.wellsoft.pt.basicdata.serialnumber.service.SerialNumberMaintainService;
import com.wellsoft.pt.basicdata.serialnumber.service.SerialNumberService;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryData;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo;
import com.wellsoft.pt.common.enums.IdPrefix;
import com.wellsoft.pt.common.enums.Separator;
import com.wellsoft.pt.core.dao.Page;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.org.entity.Department;
import com.wellsoft.pt.org.entity.User;
import com.wellsoft.pt.org.facade.OrgApiFacade;
import com.wellsoft.pt.security.acl.entity.AclSid;
import com.wellsoft.pt.security.acl.service.AclService;
import com.wellsoft.pt.security.core.userdetails.UserDetails;
import com.wellsoft.pt.utils.bean.BeanUtils;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

/**
 * 
 * Description: 流水号维护服务层实现类
 *  
 * @author zhouyq
 * @date 2013-3-6
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-3-6.1	zhouyq		2013-3-6		Create
 * </pre>
 *
 */
@Service
@Transactional
public class SerialNumberMaintainServiceImpl implements SerialNumberMaintainService {
	@Autowired
	private SerialNumberDao serialNumberDao;

	@Autowired
	private SerialNumberMaintainDao serialNumberMaintainDao;
	@Autowired
	private SerialNumberService serialNumberService;

	@Autowired
	private AclService aclService;

	@Autowired
	private OrgApiFacade orgApiFacade;
	@Autowired
	private DyFormApiFacade dyFormApiFacade;

	/**
	 * 根据指定id来获取流水号维护集合
	 */
	@Override
	public List<SerialNumberMaintain> getByDesignatedId(String designatedId, String isOverride) {
		Integer incremental = 0;
		List<SerialNumber> sList = serialNumberDao.findBy("id", designatedId);
		for (SerialNumber serialNumber : sList) {
			incremental = serialNumber.getIncremental();
		}
		List<SerialNumberMaintain> maintainList = serialNumberMaintainDao.findBy("id", designatedId);
		serialNumberMaintainDao.getSession().clear();//清除缓存
		List<SerialNumberMaintain> maintains = BeanUtils.convertCollection(maintainList, SerialNumberMaintain.class);
		for (SerialNumberMaintain s : maintains) {
			s.setPointer(String.valueOf(Integer.valueOf(s.getPointer()) + incremental));
			//			maintainList.add(serialNumberMaintain);
		}
		return maintains;
	}

	/**
	 * 根据名称来获取所有的流水号维护集合
	 */
	@Override
	public List<SerialNumberMaintain> getByName(String name) {
		Integer incremental = 0;
		String initialValue = "";
		Boolean isFillPosition = false;
		List<SerialNumber> sList = serialNumberDao.findBy("name", name);
		for (SerialNumber serialNumber : sList) {
			incremental = serialNumber.getIncremental();
			initialValue = serialNumber.getInitialValue();
			Boolean fillPosition = serialNumber.getIsFillPosition();
			if (fillPosition) {
				isFillPosition = true;
			}
		}

		List<SerialNumberMaintain> maintainList = serialNumberMaintainDao.findBy("name", name);
		serialNumberMaintainDao.getSession().clear();//清除缓存
		List<SerialNumberMaintain> maintains = BeanUtils.convertCollection(maintainList, SerialNumberMaintain.class);
		for (SerialNumberMaintain s : maintains) {
			String result = s.getPointer();//旧指针
			String newPointer = String.valueOf(Integer.valueOf(s.getPointer()) + incremental);//未补位新指针
			//补位处理
			if (isFillPosition) {
				int length = initialValue.length();
				System.out.println("长度为：" + length);
				// 0 代表前面补充0    
				// 4 代表长度为4    
				// d 代表参数为正数型    
				result = String.format("%0" + String.valueOf(length) + "d", Integer.valueOf(result));
				newPointer = String.format("%0" + String.valueOf(length) + "d", Integer.valueOf(newPointer));
			}
			s.setPointer(newPointer);
		}
		return maintains;
	}

	/**
	 * 通过uuid获取流水号维护
	 * 
	 * @param uuid
	 * @return
	 */
	@Override
	public SerialNumberMaintain get(String uuid) {
		return serialNumberMaintainDao.get(uuid);
	}

	/**
	 * 通过uuid获取流水号维护VO对象
	 * 
	 * @param uuid
	 * @return
	 */
	@Override
	public SerialNumberMaintainBean getBeanByUuid(String uuid) {
		SerialNumberMaintain serialNumberMaintain = this.serialNumberMaintainDao.get(uuid);
		SerialNumberMaintainBean bean = new SerialNumberMaintainBean();
		BeanUtils.copyProperties(serialNumberMaintain, bean);

		//设置流水号维护使用人
		List<AclSid> aclSids = aclService.getSid(serialNumberMaintain);
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
			} else if (sid.startsWith(IdPrefix.DEPARTMENT.getValue())) {
				Department department = orgApiFacade.getDepartmentById(sid);
				ownerIds.append(department.getId());
				ownerNames.append(department.getName());
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

	/** 
	 * 保存流水号维护bean
	 * @see com.wellsoft.pt.message.service.SerialNumberMaintainService#saveBean(com.wellsoft.pt.message.bean.SerialNumberMaintainBean)
	 */
	@Override
	public void saveBean(SerialNumberMaintainBean bean) {
		SerialNumberMaintain serialNumberMaintain = new SerialNumberMaintain();
		// 保存新serialNumberMaintain 设置id值
		if (StringUtils.isBlank(bean.getUuid())) {
			bean.setUuid(null);
		} else {
			serialNumberMaintain = this.serialNumberMaintainDao.get(bean.getUuid());
		}
		BeanUtils.copyProperties(bean, serialNumberMaintain);

		//设置所有者
		if (StringUtils.isNotBlank(bean.getOwnerIds())) {
			String[] ownerIds = StringUtils.split(bean.getOwnerIds(), Separator.SEMICOLON.getValue());
			serialNumberMaintain.setOwners(Arrays.asList(ownerIds));
		}
		this.serialNumberMaintainDao.save(serialNumberMaintain);
		//ACL行数据权限保存
		this.saveAcl(serialNumberMaintain);
	}

	/** 
	 * 删除流水号维护
	 * @see com.wellsoft.pt.message.service.SerialNumberMaintainService#remove(java.lang.String)
	 */
	@Override
	public void remove(String uuid) {
		this.serialNumberMaintainDao.delete(uuid);
	}

	/**
	 * 
	 * 批量删除
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.serialnumber.service.SerialNumberMaintainService#deleteAllById(java.lang.String[])
	 */
	@Override
	public void deleteAllById(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			SerialNumberMaintain serialNumberMaintain = serialNumberMaintainDao.getById(ids[i]);
			serialNumberMaintainDao.delete(serialNumberMaintain);
		}

	}

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.message.service.SerialNumberMaintainService#query(com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo)
	 */
	@Override
	public JqGridQueryData query(JqGridQueryInfo queryInfo) {
		Page<SerialNumberMaintain> pageData = new Page<SerialNumberMaintain>();
		pageData.setPageNo(queryInfo.getPage());
		pageData.setPageSize(queryInfo.getRows());
		this.serialNumberMaintainDao.findPage(pageData);
		List<SerialNumberMaintain> serialNumberMaintains = pageData.getResult();
		List<SerialNumberMaintain> jqUsers = new ArrayList<SerialNumberMaintain>();
		for (SerialNumberMaintain serialNumberMaintain : serialNumberMaintains) {
			SerialNumberMaintain jqSerialNumberMaintain = new SerialNumberMaintain();
			BeanUtils.copyProperties(serialNumberMaintain, jqSerialNumberMaintain);
			jqUsers.add(jqSerialNumberMaintain);
		}
		JqGridQueryData queryData = new JqGridQueryData();
		queryData.setCurrentPage(queryInfo.getPage());
		queryData.setDataList(jqUsers);
		queryData.setRepeatitems(false);
		queryData.setTotalPages(pageData.getTotalPages());
		queryData.setTotalRows(pageData.getTotalCount());
		return queryData;
	}

	@Override
	public SerialNumberMaintain saveAcl(SerialNumberMaintain serialNumberMaintain) {
		List<AclSid> aclSids = aclService.getSid(serialNumberMaintain);
		List<String> existSids = new ArrayList<String>();
		for (AclSid aclSid : aclSids) {
			existSids.add(aclSid.getSid());
		}
		List<String> sids = getAclSid(serialNumberMaintain);
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
			aclService.removePermission(serialNumberMaintain, BasePermission.ADMINISTRATION, sid);
		}
		// 新增
		/*if (serialNumberMaintain.getParent() != null) {
			aclService.save(serialNumberMaintain, serialNumberMaintain.getParent(), sids.get(0), BasePermission.ADMINISTRATION);
		}*/
		for (String sid : sids) {
			aclService.addPermission(serialNumberMaintain, BasePermission.ADMINISTRATION, sid);
		}
		return aclService
				.get(SerialNumberMaintain.class, serialNumberMaintain.getUuid(), BasePermission.ADMINISTRATION);
	}

	/**
	 * 返回流水号维护使用者在ACL中的SID
	 * 
	 * @param serialNumberMaintain
	 * 
	 * @return
	 */
	private List<String> getAclSid(SerialNumberMaintain serialNumberMaintain) {
		if (serialNumberMaintain.getOwners().isEmpty()) {
			// "ROLE_SERIAL_NUMBER"
			serialNumberMaintain.getOwners().add(ACL_SID);
			return serialNumberMaintain.getOwners();
		}
		// 返回组织部门中选择的角色作为SID
		return serialNumberMaintain.getOwners();
	}

	/**
	 * 判断当前登录用户是否在指定的组织部门中
	 * 
	 * @param serialNumberMaintain
	 * @param sid
	 */
	private Boolean hasPermission(SerialNumberMaintain serialNumberMaintain) {
		Boolean hasPermission = false;
		// 获取该流水号维护的所有SID，判断是否有访问权限
		List<AclSid> aclSids = aclService.getSid(serialNumberMaintain);
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
	 * 
	 * 获得所有可编辑流水号
	 * 
	 * @param uuid
	 * @return
	 */
	@Override
	public List<SerialNumberMaintain> getByIsEditor(Boolean isEditor) {
		List<SerialNumberMaintain> serialList = null;
		if (isEditor) {
			serialList = serialNumberMaintainDao.findBy("isEditor", isEditor);
		}
		return serialList;

	}

	/**
	 * 
	 * 保存可编辑流水号的指针
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.serialnumber.service.SerialNumberMaintainService#savePointer(java.lang.String)
	 */
	@Override
	public void savePointer(String serial, String headPart, String serialVal, String isOverride, String newPointer) {
		SerialNumberMaintain s = serialNumberMaintainDao.findUniqueBy("headPart", headPart);
		if (s != null) {
			String id = s.getId();
			SerialNumber num = serialNumberDao.findUniqueBy("id", id);
			Boolean isFillPosition = num.getIsFillPosition();
			String initialValue = num.getInitialValue();
			Integer increment = num.getIncremental();

			String hPart = s.getHeadPart();
			String lPart = s.getLastPart();
			String result = serial.replaceAll(hPart, "").replaceAll(lPart, "");

			//补位处理
			if (isFillPosition) {
				int length = initialValue.length();
				System.out.println("长度为：" + length);
				// 0 代表前面补充0    
				// 4 代表长度为4    
				// d 代表参数为正数型    
				result = String.format("%0" + String.valueOf(length) + "d", Integer.valueOf(result));
				newPointer = String.format("%0" + String.valueOf(length) + "d", Integer.valueOf(newPointer));
				System.out.println("补位后：" + result);
			}
			//判断是否强制覆盖指针
			if ("1".equals(isOverride)) {
				if (Integer.valueOf(newPointer) > Integer.valueOf(newPointer) - increment) {
					s.setPointer(newPointer);
					System.out.println("替换之后：" + newPointer);
					serialNumberMaintainDao.save(s);
				} else {
					System.out.println("新指针不大于当前指针，不覆盖");
				}
			} else if ("2".equals(isOverride)) {
				s.setPointer(newPointer);
				System.out.println("替换之后：" + newPointer);
				serialNumberMaintainDao.save(s);
			}

		} else {
			SerialNumber num = serialNumberDao.findUniqueBy("name", serialVal);
			//			String nameId = num.getName();
			String numberId = num.getId();
			//进行解析，不存入数据库
			SerialNumber num1 = serialNumberService.getByName(serialVal);
			String headPart1 = num1.getHeadPart();
			String lastPart = num1.getLastPart();
			String keyPart = num1.getKeyPart();
			Boolean isFillPosition = num.getIsFillPosition();
			String initialValue = num.getInitialValue();
			//			System.out.println("初始值为：" + initialValue);
			SerialNumberMaintain newS = new SerialNumberMaintain();
			newS.setName(serialVal);
			newS.setId(numberId);
			newS.setKeyPart(keyPart);
			newS.setHeadPart(headPart1);
			newS.setLastPart(lastPart);
			String point = serial.replaceAll(headPart1, "").replaceAll(lastPart, "");
			//补位处理
			if (isFillPosition) {
				int length = initialValue.length();
				System.out.println("长度为：" + length);
				// 0 代表前面补充0    
				// 4 代表长度为4    
				// d 代表参数为正数型    
				point = String.format("%0" + String.valueOf(length) + "d", Integer.valueOf(point));
				System.out.println("补位后：" + point);
			}

			newS.setPointer(point);
			newS.setIsEditor(true);
			serialNumberMaintainDao.save(newS);

		}

	}

	/**
	 * 
	 * 检测当前流水号是否已被占用
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.serialnumber.service.SerialNumberMaintainService#checkIsOccupied()
	 */
	@Override
	public Boolean checkIsOccupied(String formUuid, String projection, String lastSerialNumber) {
		String[] projections = new String[10];
		projections[0] = projection;
		List<QueryItem> itemList = dyFormApiFacade.query(formUuid, projections, null, null, null, null, null, 0, 0);
		if (itemList.size() > 0) {
			for (QueryItem queryItem : itemList) {
				if (lastSerialNumber.equals(queryItem.get("textNumber"))) {
					System.out.println("该号已被占用！");
					return true;
				}
			}
		}
		return false;
	}
}
