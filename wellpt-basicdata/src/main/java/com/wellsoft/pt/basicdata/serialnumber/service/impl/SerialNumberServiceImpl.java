package com.wellsoft.pt.basicdata.serialnumber.service.impl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.basicdata.serialnumber.bean.SerialNumberBean;
import com.wellsoft.pt.basicdata.serialnumber.dao.SerialNumberDao;
import com.wellsoft.pt.basicdata.serialnumber.dao.SerialNumberMaintainDao;
import com.wellsoft.pt.basicdata.serialnumber.entity.SerialNumber;
import com.wellsoft.pt.basicdata.serialnumber.entity.SerialNumberMaintain;
import com.wellsoft.pt.basicdata.serialnumber.service.SerialNumberService;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryData;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo;
import com.wellsoft.pt.common.enums.IdPrefix;
import com.wellsoft.pt.common.enums.Separator;
import com.wellsoft.pt.common.utils.DateUtil;
import com.wellsoft.pt.common.utils.StringUtil;
import com.wellsoft.pt.core.dao.Page;
import com.wellsoft.pt.core.entity.IdEntity;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.core.template.TemplateEngine;
import com.wellsoft.pt.core.template.TemplateEngineFactory;
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
 * Description: 流水号定义服务层实现类
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
public class SerialNumberServiceImpl implements SerialNumberService {

	@Autowired
	private SerialNumberDao serialNumberDao;
	@Autowired
	private SerialNumberMaintainDao serialNumberMaintainDao;

	@Autowired
	private AclService aclService;

	@Autowired
	private OrgApiFacade orgApiFacade;

	@Autowired
	private DyFormApiFacade dyFormApiFacade;

	/**
	 * 
	 * 获得所有流水号定义
	 * 
	 * @return
	 */
	@Override
	public List<SerialNumber> findAll() {
		return serialNumberDao.getAll();
	}

	/**
	 * 根据名称来获取对应的可编辑流水号定义
	 */
	@Override
	public SerialNumber getByName(String name) {
		String initialValue = null;
		SerialNumber serial = serialNumberDao.findUniqueBy("name", name);
		String keyPart = serial.getKeyPart();
		String headPart = serial.getHeadPart();
		String lastPart = serial.getLastPart();
		String startDate = serial.getStartDate();

		//将关键字定义格式全部替换成${}的形式
		keyPart = replaceKeyWord(keyPart);
		headPart = replaceKeyWord(headPart);
		lastPart = replaceKeyWord(lastPart);

		try {
			Map<Object, Object> keyWordMap = getMapValue(startDate);

			TemplateEngine templateEngine = TemplateEngineFactory.getDefaultTemplateEngine();

			//替换模板关键字为Map集合里面的值
			keyPart = templateEngine.process(keyPart, keyWordMap);
			// System.out.println(keyPart);
			headPart = templateEngine.process(headPart, keyWordMap);
			// System.out.println(headPart);
			lastPart = templateEngine.process(lastPart, keyWordMap);
			serialNumberDao.getSession().clear();//清除缓存
			serial.setKeyPart(keyPart);
			serial.setHeadPart(headPart);
			serial.setLastPart(lastPart);
			// System.out.println(lastPart);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			// System.out.println("关键字定义出错，请重新定义！");
			return null;
		}
		return serial;
	}

	/**
	 * 
	 * 获得所有可编辑流水号
	 * 
	 * @param uuid
	 * @return
	 */
	@Override
	public List<SerialNumber> getByIsEditor(Boolean isEditor) {
		List<SerialNumber> serialList = null;
		if (isEditor) {
			serialList = serialNumberDao.findBy("isEditor", isEditor);//得到所有的可编辑流水号定义
		}
		return serialList;
	}

	/**
	 * 
	 * 根据指定ID获取可编辑流水号
	 * 
	 * @param uuid
	 * @return
	 */
	@Override
	public List<SerialNumber> getByDesignatedId(Boolean isEditor, String designatedId) {
		List<SerialNumber> serialList = null;
		List<SerialNumber> serialListById = new ArrayList<SerialNumber>();
		if (isEditor) {
			serialList = serialNumberDao.findBy("isEditor", isEditor);//得到所有的可编辑流水号定义
			if (serialList.size() > 0) {
				for (SerialNumber serialNumber : serialList) {
					if (designatedId.equals(serialNumber.getId())) {
						serialListById.add(serialNumber);
					}
				}
			}

		}
		return serialListById;
	}

	/**
	 * 
	 * 根据指定类型获取可编辑流水号
	 * 
	 * @param uuid
	 * @return
	 */
	@Override
	public List<SerialNumber> getByDesignatedType(Boolean isEditor, String designatedType) {
		List<SerialNumber> serialList = null;
		List<SerialNumber> serialListByType = new ArrayList<SerialNumber>();
		if (isEditor) {
			serialList = serialNumberDao.findBy("isEditor", isEditor);//得到所有的可编辑流水号定义
			if (serialList.size() > 0) {
				for (SerialNumber serialNumber : serialList) {
					if (designatedType.equals(serialNumber.getType())) {
						serialListByType.add(serialNumber);
					}
				}
			}
		}
		return serialListByType;
	}

	/**
	 * 通过uuid获取流水号定义
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public SerialNumber get(String uuid) {
		return serialNumberDao.get(uuid);
	}

	/**
	 * 通过uuid获取流水号定义VO对象
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public SerialNumberBean getBeanByUuid(String uuid) {
		SerialNumber serialNumber = this.serialNumberDao.get(uuid);
		SerialNumberBean bean = new SerialNumberBean();
		BeanUtils.copyProperties(serialNumber, bean);

		//设置流水号定义使用人
		List<AclSid> aclSids = aclService.getSid(serialNumber);
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
	 * 保存流水号定义bean
	 * @see com.wellsoft.pt.message.service.SystemTableService#saveBean(com.wellsoft.pt.message.bean.PrintTemplateBean)
	 */
	@Override
	public void saveBean(SerialNumberBean bean) {
		SerialNumber serialNumber = new SerialNumber();
		// 保存新serialNumber 设置id值
		if (StringUtils.isBlank(bean.getUuid())) {
			bean.setUuid(null);
		} else {
			serialNumber = this.serialNumberDao.get(bean.getUuid());
		}
		BeanUtils.copyProperties(bean, serialNumber);

		// 增量大于0验证
		if (serialNumber.getIncremental() == null || serialNumber.getIncremental() <= 0) {
			throw new RuntimeException("流水号增量必须大于0");
		}

		//设置所有者
		if (StringUtils.isNotBlank(bean.getOwnerIds())) {
			String[] ownerIds = StringUtils.split(bean.getOwnerIds(), Separator.SEMICOLON.getValue());
			serialNumber.setOwners(Arrays.asList(ownerIds));
		}
		this.serialNumberDao.save(serialNumber);

		//ACL行数据权限保存
		this.saveAcl(serialNumber);
	}

	/** 
	 * 删除流水号定义
	 * @see com.wellsoft.pt.message.service.SystemTableService#remove(java.lang.String)
	 */
	@Override
	public void remove(String uuid) {
		this.serialNumberDao.delete(uuid);
	}

	/**
	 * 
	 * 批量删除
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.serialnumber.service.SerialNumberService#deleteAllById(java.lang.String[])
	 */
	@Override
	public void deleteAllById(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			SerialNumber serialNumber = serialNumberDao.getById(ids[i]);
			serialNumberDao.delete(serialNumber);
		}

	}

	/** 
	 * JQgrid流水号定义列表查询
	 * @see com.wellsoft.pt.message.service.SystemTableService#query(com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo)
	 */
	@Override
	public JqGridQueryData query(JqGridQueryInfo queryInfo) {
		Page<SerialNumber> pageData = new Page<SerialNumber>();
		pageData.setPageNo(queryInfo.getPage());
		pageData.setPageSize(queryInfo.getRows());
		this.serialNumberDao.findPage(pageData);
		List<SerialNumber> serialNumbers = pageData.getResult();
		List<SerialNumber> jqUsers = new ArrayList<SerialNumber>();
		for (SerialNumber serialNumber : serialNumbers) {
			SerialNumber jqSerialNumber = new SerialNumber();
			BeanUtils.copyProperties(serialNumber, jqSerialNumber);
			jqUsers.add(jqSerialNumber);
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
	public SerialNumber saveAcl(SerialNumber serialNumber) {
		List<AclSid> aclSids = aclService.getSid(serialNumber);
		List<String> existSids = new ArrayList<String>();
		for (AclSid aclSid : aclSids) {
			existSids.add(aclSid.getSid());
		}
		List<String> sids = getAclSid(serialNumber);
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
			aclService.removePermission(serialNumber, BasePermission.ADMINISTRATION, sid);
		}
		// 新增
		/*if (serialNumber.getParent() != null) {
			aclService.save(serialNumber, serialNumber.getParent(), sids.get(0), BasePermission.ADMINISTRATION);
		}*/
		for (String sid : sids) {
			aclService.addPermission(serialNumber, BasePermission.ADMINISTRATION, sid);
		}
		return aclService.get(SerialNumber.class, serialNumber.getUuid(), BasePermission.ADMINISTRATION);
	}

	/**
	 * 返回流水号定义使用者在ACL中的SID
	 * 
	 * @param serialNumber
	 * 
	 * @return
	 */
	private List<String> getAclSid(SerialNumber serialNumber) {
		if (serialNumber.getOwners().isEmpty()) {
			// "ROLE_SERIAL_NUMBER"
			serialNumber.getOwners().add(ACL_SID);
			return serialNumber.getOwners();
		}
		// 返回组织部门中选择的角色作为SID
		return serialNumber.getOwners();
	}

	/**
	 * 判断当前登录用户是否在指定的组织部门中
	 * 
	 * @param serialNumber
	 * @param sid
	 */
	private Boolean hasPermission(SerialNumber serialNumber) {
		Boolean hasPermission = false;
		// 获取该流水号的所有SID，判断是否有访问权限
		List<AclSid> aclSids = aclService.getSid(serialNumber);
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

	//以下部分为接口的解析
	/**
	 * 获取流水号接口,单个实体
	 */
	@Override
	public <ENTITY extends IdEntity> String getSerialNumber(String id, ENTITY entity, Boolean isOccupied,
			Map<String, Object> dytableMap, String fieldName) throws Exception {
		SerialNumber serialNumber = serialNumberDao.findUniqueBy("id", id);

		String type = serialNumber.getType();
		String name = serialNumber.getName();
		String numberId = serialNumber.getId();
		String keyPart = serialNumber.getKeyPart();
		String headPart = serialNumber.getHeadPart();
		String initialValue = serialNumber.getInitialValue();
		Boolean isFillPosition = serialNumber.getIsFillPosition();
		Integer incremental = serialNumber.getIncremental();
		String lastPart = serialNumber.getLastPart();
		Boolean isFillNumber = serialNumber.getIsFillNumber();
		String startDate = serialNumber.getStartDate();
		Boolean isEditor = serialNumber.getIsEditor();
		String remark = serialNumber.getRemark();

		//将关键字定义格式全部替换成${}的形式
		keyPart = replaceKeyWord(keyPart);
		headPart = replaceKeyWord(headPart);
		lastPart = replaceKeyWord(lastPart);

		Map<Object, Object> keyWordMap = getMapValue(startDate, entity, dytableMap);
		TemplateEngine templateEngine = TemplateEngineFactory.getDefaultTemplateEngine();
		try {
			//替换模板关键字为Map集合里面的值
			keyPart = templateEngine.process(keyPart, keyWordMap);
			// System.out.println(keyPart);
			headPart = templateEngine.process(headPart, keyWordMap);
			// System.out.println(headPart);
			lastPart = templateEngine.process(lastPart, keyWordMap);
			// System.out.println(lastPart);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("关键字定义出错，请重新定义！");
			return null;
		}

		// System.out.println("keyPart:" + keyPart);
		// System.out.println("headPart:" + headPart);
		// System.out.println("lastPart:" + lastPart);

		//检查流水号维护中是否已经存在该关键字的流水号
		//add by huanglinchuan 2014.10.17 begin
		//SerialNumberMaintain serialNumberMaintain = serialNumberMaintainDao.findUniqueBy("keyPart", keyPart);
		SerialNumberMaintain serialNumberMaintain = serialNumberMaintainDao.getByIdAndKeyPart(id, keyPart);
		//add by huanglinchuan 2014.10.17 end
		String serialNum = null;
		if (serialNumberMaintain != null) {
			String pointer = serialNumberMaintain.getPointer();//如果流水号维护中存在的话就获取当前的指针值
			//补位处理
			if (isFillPosition) {
				int length = initialValue.length();
				// System.out.println("长度为：" + length);
				// 0 代表前面补充0    
				// 4 代表长度为4    
				// d 代表参数为正数型    
				pointer = String.format("%0" + String.valueOf(length) + "d", Integer.valueOf(pointer));
				// System.out.println("补位后：" + pointer);
			}
			//			String maintainHeadPart = serialNumberMaintain.getHeadPart();//获取维护头部
			//			String maintainLastPart = serialNumberMaintain.getLastPart();//获取维护尾部
			//补号处理
			if (isFillNumber) {
				String classFullName = entity.getClass().getName();//获取类完全限定名
				String className = classFullName.substring(classFullName.lastIndexOf(".") + 1);//获取类名
				List<String> serialList = serialNumberDao.findByFieldName(fieldName, className);

				List<String> numList = new ArrayList<String>();//真正流水号列集合
				String tempHeadPart = headPart;
				for (String value : serialList) {
					//判断当前流水号是否为null,为null不用截取
					if (value != null && !("".equals(value))) {
						value = value.substring(tempHeadPart.length(), tempHeadPart.length() + pointer.length());
						numList.add(value);
						// System.out.println("真正流水号:" + value);
					}
				}
				Map<String, String> map = new HashMap<String, String>();//真正流水号列集合
				List<String> fillNumList = new ArrayList<String>();//补号的集合
				for (String value : numList) {
					map.put(value, value);
				}
				for (int i = Integer.valueOf(initialValue); i < Integer.valueOf(pointer); i = i + incremental) {
					//若有补位，则返回的补号也要有补位
					if (isFillPosition) {
						int length = initialValue.length();
						// System.out.println("长度为：" + length);
						// 0 代表前面补充0    
						// 4 代表长度为4    
						// d 代表参数为正数型    
						String temp = String.format("%0" + String.valueOf(length) + "d", i);
						if (!map.containsKey(temp)) {
							fillNumList.add(temp);
							// System.out.println("需要的补号：" + temp);
						}
					}
				}
				//如果补号集合里有补号
				if (fillNumList.size() > 0) {
					Collections.sort(fillNumList);//对补号集合进行排序
					String minFillNum = fillNumList.get(0);
					// System.out.println("最小的补号：" + minFillNum);
					/*if (isOccupied) {
						serialNumberMaintain.setPointer(minFillNum);
						serialNumberMaintainDao.save(serialNumberMaintain);
					}*/
					serialNum = headPart + minFillNum + lastPart;
					// System.out.println("更新维护指针:" + minFillNum);
					// System.out.println("更新生成的流水号：" + headPart + "【" + minFillNum + "】" + lastPart);
				} else {
					pointer = String.valueOf(Integer.valueOf(pointer) + incremental);//若没有补号则指针值加上增量
					//补位处理
					if (isFillPosition) {
						int length = initialValue.length();
						// System.out.println("长度为：" + length);
						// 0 代表前面补充0    
						// 4 代表长度为4    
						// d 代表参数为正数型    
						pointer = String.format("%0" + String.valueOf(length) + "d", Integer.valueOf(pointer));
						// System.out.println("补位后：" + pointer);
					}
					//若占用
					if (isOccupied) {
						serialNumberMaintain.setPointer(pointer);
						serialNumberMaintain.setHeadPart(headPart);
						serialNumberMaintain.setLastPart(lastPart);
						serialNumberMaintain.setIsEditor(isEditor);
						serialNumberMaintainDao.save(serialNumberMaintain);
					}
					serialNum = headPart + pointer + lastPart;

					// System.out.println("aaa更新维护指针:" + pointer);
					// System.out.println("aaa更新生成的流水号：" + headPart + "【" + pointer + "】" + lastPart);
				}
			} else {
				pointer = String.valueOf(Integer.valueOf(pointer) + incremental);//若没有补号则指针值加上增量
				//补位处理
				if (isFillPosition) {
					int length = initialValue.length();
					// System.out.println("长度为：" + length);
					// 0 代表前面补充0    
					// 4 代表长度为4    
					// d 代表参数为正数型    
					pointer = String.format("%0" + String.valueOf(length) + "d", Integer.valueOf(pointer));
					// System.out.println("补位后：" + pointer);
				}
				//若占用
				if (isOccupied) {
					serialNumberMaintain.setPointer(pointer);
					serialNumberMaintain.setHeadPart(headPart);
					serialNumberMaintain.setLastPart(lastPart);
					serialNumberMaintain.setIsEditor(isEditor);
					serialNumberMaintainDao.save(serialNumberMaintain);
				}
				serialNum = headPart + pointer + lastPart;

				// System.out.println("更新维护指针:" + pointer);
				// System.out.println("更新生成的流水号：" + headPart + "【" + pointer + "】" + lastPart);
			}

		} else {
			//若流水号维护中不存在则新建流水号
			SerialNumberMaintain maintain = new SerialNumberMaintain();
			maintain.setId(numberId);
			maintain.setName(name);
			maintain.setKeyPart(keyPart);
			maintain.setHeadPart(headPart);
			maintain.setLastPart(lastPart);
			maintain.setPointer(initialValue);
			maintain.setIsEditor(isEditor);
			//若被占用，存入数据库
			if (isOccupied) {
				serialNumberMaintainDao.save(maintain);
			}
			serialNum = headPart + initialValue + lastPart;

			// System.out.println("新建维护指针:" + initialValue);
			// System.out.println("首次生成的流水号：" + headPart + "【" + initialValue + "】" + lastPart);
		}
		// System.out.println("最终流水号：" + serialNum);
		return serialNum;
	}

	/**
		* 
		* 将关键字定义格式全部替换成${}的形式
		* 
		* @param keyWrod
		* @return
		*/

	private static String replaceKeyWord(String keyWrod) {
		if (!(StringUtils.isBlank(keyWrod))) {
			return keyWrod.replace("{{", "${").replace("}}", "}");
		}
		return "";
	}

	/**
		* 
		* 关键字定义（实体集合）
		* 
		* @return
		* @throws Exception 
		*/

	private static <ENTITY extends IdEntity> Map<Object, Object> getMapValue(String startDate,
			Collection<ENTITY> entities, Map<String, Object> dytableMap) throws Exception {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);//获取年份  
		int month = cal.get(Calendar.MONTH) + 1;//获取月份   
		int day = cal.get(Calendar.DATE);//获取日   
		int hour = cal.get(Calendar.HOUR_OF_DAY);//小时   
		int minute = cal.get(Calendar.MINUTE);//分              
		int second = cal.get(Calendar.SECOND);//秒   

		//判断新年度日期设置是否为null或空
		if (startDate != null && !("".equals(startDate))) {
			String a[] = startDate.split("-");
			String m = a[0];
			String d = a[1];
			if (month < Integer.valueOf(m)) {
				year = year - 1;
			} else if (month == Integer.valueOf(m)) {
				if (day < Integer.valueOf(d)) {
					year = year - 1;
				}
			}
			// System.out.println("新年度开始日期：" + startDate);
			// System.out.println("截取月份：" + m);
			// System.out.println("截取天：" + d);
		}

		// System.out.println("当前年：" + year);

		Map<Object, Object> keyWordMap = new HashMap<Object, Object>();
		keyWordMap.put("年", String.valueOf(year));
		keyWordMap.put("月", DateUtil.getFormatDate(month));
		keyWordMap.put("日", DateUtil.getFormatDate(day));
		keyWordMap.put("时", DateUtil.getFormatDate(hour));
		keyWordMap.put("分", DateUtil.getFormatDate(minute));
		keyWordMap.put("秒", DateUtil.getFormatDate(second));
		keyWordMap.put("简年", String.valueOf(year).substring(2));
		keyWordMap.put("大写年", toChinese(String.valueOf(year)));
		keyWordMap.put("大写月", toChinese(String.valueOf(month)));
		keyWordMap.put("大写日", toChinese(String.valueOf(day)));

		keyWordMap.put("YEAR", String.valueOf(year));
		keyWordMap.put("MONTH", String.valueOf(month));
		keyWordMap.put("DAY", String.valueOf(day));
		keyWordMap.put("HOUR", String.valueOf(hour));
		keyWordMap.put("MINUTE", String.valueOf(minute));
		keyWordMap.put("SECOND", String.valueOf(second));
		keyWordMap.put("SHORTYEAR", String.valueOf(year).substring(2));

		//动态表单解析
		keyWordMap.put("dytable", dytableMap);

		//只传入${属性}时解析
		for (Object obj : entities) {
			//反射取得实体类的属性及值存入keyWordMap集合中
			BeanWrapperImpl wrapper = new BeanWrapperImpl(obj);
			PropertyDescriptor[] descriptors = wrapper.getPropertyDescriptors();
			for (PropertyDescriptor descriptor : descriptors) {
				String propertyName = descriptor.getName();
				// System.out.println(propertyName);
				keyWordMap.put(propertyName, wrapper.getPropertyValue(propertyName));
			}
		}

		//传入${类.属性}时解析
		for (Object obj : entities) {
			String className = obj.getClass().getSimpleName();
			String lowerCaseName = className.substring(0, 1).toLowerCase() + className.substring(1);
			// System.out.println("首字母小写类名：" + lowerCaseName);
			keyWordMap.put(lowerCaseName, obj);
		}

		return keyWordMap;
	}

	/**
	* 
	* 关键字定义(单个实体)
	* 
	* @return
	* @throws Exception 
	*/

	private static <ENTITY extends IdEntity> Map<Object, Object> getMapValue(String startDate, ENTITY entity,
			Map<String, Object> dytableMap) throws Exception {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);//获取年份  
		int month = cal.get(Calendar.MONTH) + 1;//获取月份   
		int day = cal.get(Calendar.DATE);//获取日   
		int hour = cal.get(Calendar.HOUR_OF_DAY);//小时   
		int minute = cal.get(Calendar.MINUTE);//分              
		int second = cal.get(Calendar.SECOND);//秒   

		//判断新年度日期设置是否为null或空
		if (startDate != null && !("".equals(startDate))) {
			String a[] = startDate.split("-");
			String m = a[0];
			String d = a[1];
			if (month < Integer.valueOf(m)) {
				year = year - 1;
			} else if (month == Integer.valueOf(m)) {
				if (day < Integer.valueOf(d)) {
					year = year - 1;
				}
			}
			// System.out.println("新年度开始日期：" + startDate);
			// System.out.println("截取月份：" + m);
			// System.out.println("截取天：" + d);
		}

		// System.out.println("当前年：" + year);

		Map<Object, Object> keyWordMap = new HashMap<Object, Object>();
		keyWordMap.put("年", String.valueOf(year));
		keyWordMap.put("月", DateUtil.getFormatDate(month));
		keyWordMap.put("日", DateUtil.getFormatDate(day));
		keyWordMap.put("时", DateUtil.getFormatDate(hour));
		keyWordMap.put("分", DateUtil.getFormatDate(minute));
		keyWordMap.put("秒", DateUtil.getFormatDate(second));
		keyWordMap.put("简年", String.valueOf(year).substring(2));
		keyWordMap.put("大写年", toChinese(String.valueOf(year)));
		keyWordMap.put("大写月", toChinese(String.valueOf(month)));
		keyWordMap.put("大写日", toChinese(String.valueOf(day)));

		keyWordMap.put("YEAR", String.valueOf(year));
		keyWordMap.put("MONTH", String.valueOf(month));
		keyWordMap.put("DAY", String.valueOf(day));
		keyWordMap.put("HOUR", String.valueOf(hour));
		keyWordMap.put("MINUTE", String.valueOf(minute));
		keyWordMap.put("SECOND", String.valueOf(second));
		keyWordMap.put("SHORTYEAR", String.valueOf(year).substring(2));

		//反射取得实体类的属性及值存入keyWordMap集合中
		BeanWrapperImpl wrapper = new BeanWrapperImpl(entity);
		PropertyDescriptor[] descriptors = wrapper.getPropertyDescriptors();
		for (PropertyDescriptor descriptor : descriptors) {
			String propertyName = descriptor.getName();
			// System.out.println(propertyName);
			keyWordMap.put(propertyName, wrapper.getPropertyValue(propertyName));
		}
		//传入${类.属性}时解析
		String className = entity.getClass().getSimpleName();
		String lowerCaseName = className.substring(0, 1).toLowerCase() + className.substring(1);
		// System.out.println("首字母小写类名：" + lowerCaseName);
		keyWordMap.put(lowerCaseName, entity);

		//动态表单解析
		keyWordMap.put("dytable", dytableMap);
		return keyWordMap;
	}

	/**
	* 
	* 关键字定义（单个参数）
	* 
	* @return
	* @throws Exception 
	*/

	private static <ENTITY extends IdEntity> Map<Object, Object> getMapValue(String startDate) throws Exception {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);//获取年份  
		int month = cal.get(Calendar.MONTH) + 1;//获取月份   
		int day = cal.get(Calendar.DATE);//获取日   
		int hour = cal.get(Calendar.HOUR_OF_DAY);//小时   
		int minute = cal.get(Calendar.MINUTE);//分              
		int second = cal.get(Calendar.SECOND);//秒   

		//判断新年度日期设置是否为null或空
		if (startDate != null && !("".equals(startDate))) {
			String a[] = startDate.split("-");
			String m = a[0];
			String d = a[1];
			if (month < Integer.valueOf(m)) {
				year = year - 1;
			} else if (month == Integer.valueOf(m)) {
				if (day < Integer.valueOf(d)) {
					year = year - 1;
				}
			}
			// System.out.println("新年度开始日期：" + startDate);
			// System.out.println("截取月份：" + m);
			// System.out.println("截取天：" + d);
		}

		// System.out.println("当前年：" + year);

		Map<Object, Object> keyWordMap = new HashMap<Object, Object>();
		keyWordMap.put("年", String.valueOf(year));
		keyWordMap.put("月", DateUtil.getFormatDate(month));
		keyWordMap.put("日", DateUtil.getFormatDate(day));
		keyWordMap.put("时", DateUtil.getFormatDate(hour));
		keyWordMap.put("分", DateUtil.getFormatDate(minute));
		keyWordMap.put("秒", DateUtil.getFormatDate(second));
		keyWordMap.put("简年", String.valueOf(year).substring(2));
		keyWordMap.put("大写年", toChinese(String.valueOf(year)));
		keyWordMap.put("大写月", toChinese(String.valueOf(month)));
		keyWordMap.put("大写日", toChinese(String.valueOf(day)));

		keyWordMap.put("YEAR", String.valueOf(year));
		keyWordMap.put("MONTH", String.valueOf(month));
		keyWordMap.put("DAY", String.valueOf(day));
		keyWordMap.put("HOUR", String.valueOf(hour));
		keyWordMap.put("MINUTE", String.valueOf(minute));
		keyWordMap.put("SECOND", String.valueOf(second));
		keyWordMap.put("SHORTYEAR", String.valueOf(year).substring(2));

		return keyWordMap;
	}

	/**
		* 日期转为中文大写
		*/

	private static final String[] NUMBERS = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };

	public static synchronized String toChinese(String str) {
		StringBuffer sb = new StringBuffer();
		sb.append(getSplitDateStr(str, 0));
		return sb.toString();
	}

	public static String getSplitDateStr(String str, int unit) {
		// unit是单位 0=年 1=月 2日
		String[] DateStr = str.split("-");
		if (unit > DateStr.length)
			unit = 0;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < DateStr[unit].length(); i++) {

			if ((unit == 1 || unit == 2) && Integer.valueOf(DateStr[unit]) > 9) {
				sb.append(convertNum(DateStr[unit].substring(0, 1))).append("拾")
						.append(convertNum(DateStr[unit].substring(1, 2)));
				break;
			} else {
				sb.append(convertNum(DateStr[unit].substring(i, i + 1)));
			}
		}
		if (unit == 1 || unit == 2) {
			return sb.toString().replaceAll("^壹", "").replace("零", "");
		}
		return sb.toString();
	}

	private static String convertNum(String str) {
		return NUMBERS[Integer.valueOf(str)];
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/** 
	 * 动态表单专用获取不可编辑流水号接口
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.serialnumber.service.SerialNumberService#getSerialNumberForDytable
	 */
	@Override
	public String getNotEditorSerialNumberForDytable(String serialNumberId, Boolean isOccupied, String formUuid,
			String field) {
		String fieldForQueryItem = StringUtil.replaceUnderlineAndfirstToUpper(field);
		SerialNumber serialNumber = serialNumberDao.findUniqueBy("id", serialNumberId);

		String name = serialNumber.getName();
		String numberId = serialNumber.getId();
		String keyPart = serialNumber.getKeyPart();
		String headPart = serialNumber.getHeadPart();
		String initialValue = serialNumber.getInitialValue();
		Boolean isFillPosition = serialNumber.getIsFillPosition();
		Integer incremental = serialNumber.getIncremental();
		String lastPart = serialNumber.getLastPart();
		Boolean isFillNumber = serialNumber.getIsFillNumber();
		String startDate = serialNumber.getStartDate();
		Boolean isEditor = serialNumber.getIsEditor();

		//将关键字定义格式全部替换成${}的形式
		keyPart = replaceKeyWord(keyPart);
		headPart = replaceKeyWord(headPart);
		lastPart = replaceKeyWord(lastPart);

		Map<Object, Object> keyWordMap = new HashMap<Object, Object>();
		try {
			keyWordMap = getMapValue(startDate);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		TemplateEngine templateEngine = TemplateEngineFactory.getDefaultTemplateEngine();
		try {
			//替换模板关键字为Map集合里面的值
			keyPart = templateEngine.process(keyPart, keyWordMap);
			headPart = templateEngine.process(headPart, keyWordMap);
			lastPart = templateEngine.process(lastPart, keyWordMap);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		//检查流水号维护中是否已经存在该关键字的流水号
		//add by huanglinchuan 2014.10.17 begin
		//SerialNumberMaintain serialNumberMaintain = serialNumberMaintainDao.findUniqueBy("keyPart", keyPart);
		SerialNumberMaintain serialNumberMaintain = serialNumberMaintainDao.getByIdAndKeyPart(serialNumberId, keyPart);
		//add by huanglinchuan 2014.10.17 end
		String serialNum = null;
		if (serialNumberMaintain != null) {
			String pointer = serialNumberMaintain.getPointer();//如果流水号维护中存在的话就获取当前的指针值
			//补位处理
			if (isFillPosition) {
				int length = initialValue.length();
				// 0 代表前面补充0    
				// 4 代表长度为4    
				// d 代表参数为正数型    
				pointer = String.format("%0" + String.valueOf(length) + "d", Integer.valueOf(pointer));
			}
			//补号处理
			if (isFillNumber) {
				String[] fields = new String[10];
				fields[0] = field;
				List<QueryItem> itemList = dyFormApiFacade.query(formUuid, fields, null, null, null, null, null, 0, 0);
				List<String> serialNumberFromDytableList = new ArrayList<String>();//动态表单指定存放流水号字段的值的集合
				//判断表单查询出来的该字段中是否有值
				if (itemList.size() > 0) {
					for (QueryItem queryItem : itemList) {
						serialNumberFromDytableList.add((String) queryItem.get(fieldForQueryItem));
					}
				}

				Map<String, String> map = new HashMap<String, String>();//真正流水号Map集合
				List<String> fillNumList = new ArrayList<String>();//补号的集合
				if (serialNumberFromDytableList.size() > 0) {
					for (String value : serialNumberFromDytableList) {
						map.put(value, value);
					}
				}
				int i = Integer.valueOf(initialValue);
				while (i < Integer.valueOf(pointer)) {
					//若有补位，则返回的补号也要有补位
					if (isFillPosition) {
						int length = initialValue.length();
						// System.out.println("长度为：" + length);
						// 0 代表前面补充0    
						// 4 代表长度为4    
						// d 代表参数为正数型    
						String temp = String.format("%0" + String.valueOf(length) + "d", i);
						if (!map.containsKey(temp)) {
							fillNumList.add(temp);
						}
					}
					i = i + incremental;
				}
				//如果补号集合里有补号
				if (fillNumList.size() > 0) {
					Collections.sort(fillNumList);//对补号集合进行排序
					String minFillNum = fillNumList.get(0);
					serialNum = headPart + minFillNum + lastPart;
					return serialNum;
				} else {
					pointer = String.valueOf(Integer.valueOf(pointer) + incremental);//若没有补号则指针值加上增量
					//补位处理
					if (isFillPosition) {
						int length = initialValue.length();
						// 0 代表前面补充0    
						// 4 代表长度为4    
						// d 代表参数为正数型    
						pointer = String.format("%0" + String.valueOf(length) + "d", Integer.valueOf(pointer));
					}
				}
				//若占用
				if (isOccupied) {
					serialNumberMaintain.setPointer(pointer);
					serialNumberMaintain.setHeadPart(headPart);
					serialNumberMaintain.setLastPart(lastPart);
					serialNumberMaintain.setIsEditor(isEditor);
					serialNumberMaintainDao.save(serialNumberMaintain);
				}
				serialNum = headPart + pointer + lastPart;
			} else {
				pointer = String.valueOf(Integer.valueOf(pointer) + incremental);//若没有补号则指针值加上增量
				//补位处理
				if (isFillPosition) {
					int length = initialValue.length();
					// 0 代表前面补充0    
					// 4 代表长度为4    
					// d 代表参数为正数型    
					pointer = String.format("%0" + String.valueOf(length) + "d", Integer.valueOf(pointer));
				}
				//若占用
				if (isOccupied) {
					serialNumberMaintain.setPointer(pointer);
					serialNumberMaintain.setHeadPart(headPart);
					serialNumberMaintain.setLastPart(lastPart);
					serialNumberMaintain.setIsEditor(isEditor);
					serialNumberMaintainDao.save(serialNumberMaintain);
				}
				serialNum = headPart + pointer + lastPart;
			}
		} else {
			//若流水号维护中不存在则新建流水号
			SerialNumberMaintain maintain = new SerialNumberMaintain();
			maintain.setId(numberId);
			maintain.setName(name);
			maintain.setKeyPart(keyPart);
			maintain.setHeadPart(headPart);
			maintain.setLastPart(lastPart);
			maintain.setPointer(initialValue);
			maintain.setIsEditor(isEditor);
			//若被占用，存入数据库
			if (isOccupied) {
				serialNumberMaintainDao.save(maintain);
			}
			serialNum = headPart + initialValue + lastPart;
		}
		return serialNum;
	}

	/**
	 * 
	 * 接口解析，实体集合
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.serialnumber.service.SerialNumberService#getSerialNumber(java.lang.String, java.util.Collection, java.lang.Boolean)
	 */
	@Override
	public <ENTITY extends IdEntity> String getSerialNumber(String id, Collection<ENTITY> entities, Boolean isOccupied,
			Map<String, Object> dytableMap, String fieldName) throws Exception {
		SerialNumber serialNumber = serialNumberDao.findUniqueBy("id", id);

		String type = serialNumber.getType();
		String name = serialNumber.getName();
		String numberId = serialNumber.getId();
		String keyPart = serialNumber.getKeyPart();
		String headPart = serialNumber.getHeadPart();
		String initialValue = serialNumber.getInitialValue();
		Boolean isFillPosition = serialNumber.getIsFillPosition();
		Integer incremental = serialNumber.getIncremental();
		String lastPart = serialNumber.getLastPart();
		Boolean isFillNumber = serialNumber.getIsFillNumber();
		String startDate = serialNumber.getStartDate();
		Boolean isEditor = serialNumber.getIsEditor();
		String remark = serialNumber.getRemark();

		//将关键字定义格式全部替换成${}的形式
		keyPart = replaceKeyWord(keyPart);
		headPart = replaceKeyWord(headPart);
		lastPart = replaceKeyWord(lastPart);

		Map<Object, Object> keyWordMap = getMapValue(startDate, entities, dytableMap);
		TemplateEngine templateEngine = TemplateEngineFactory.getDefaultTemplateEngine();
		try {
			//替换模板关键字为Map集合里面的值
			keyPart = templateEngine.process(keyPart, keyWordMap);
			// System.out.println(keyPart);
			headPart = templateEngine.process(headPart, keyWordMap);
			// System.out.println(headPart);
			lastPart = templateEngine.process(lastPart, keyWordMap);
			// System.out.println(lastPart);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("关键字定义出错，请重新定义！");
			return null;
		}

		// System.out.println("keyPart:" + keyPart);
		// System.out.println("headPart:" + headPart);
		// System.out.println("lastPart:" + lastPart);

		//检查流水号维护中是否已经存在该关键字的流水号
		//add by huanglinchuan 2014.10.17 begin
		//SerialNumberMaintain serialNumberMaintain = serialNumberMaintainDao.findUniqueBy("keyPart", keyPart);
		SerialNumberMaintain serialNumberMaintain = serialNumberMaintainDao.getByIdAndKeyPart(id, keyPart);
		//add by huanglinchuan 2014.10.17 end
		String serialNum = null;
		if (serialNumberMaintain != null) {
			String pointer = serialNumberMaintain.getPointer();//如果流水号维护中存在的话就获取当前的指针值
			//补位处理
			if (isFillPosition) {
				int length = initialValue.length();
				// System.out.println("长度为：" + length);
				// 0 代表前面补充0    
				// 4 代表长度为4    
				// d 代表参数为正数型    
				pointer = String.format("%0" + String.valueOf(length) + "d", Integer.valueOf(pointer));
				// System.out.println("补位后：" + pointer);
			}
			//			String maintainHeadPart = serialNumberMaintain.getHeadPart();//获取维护头部
			//			String maintainLastPart = serialNumberMaintain.getLastPart();//获取维护尾部
			//补号处理
			if (isFillNumber) {
				for (ENTITY entity : entities) {
					try {
						BeanWrapper wrapper = new BeanWrapperImpl(entity);
						PropertyDescriptor descriptor = wrapper.getPropertyDescriptor(fieldName);
						if (descriptor == null) {
							continue;
						}
					} catch (Exception e) {
						continue;
					}
					String classFullName = entity.getClass().getName();//获取类完全限定名
					String className = classFullName.substring(classFullName.lastIndexOf(".") + 1);//获取类名
					List<String> serialList = serialNumberDao.findByFieldName(fieldName, className);

					List<String> numList = new ArrayList<String>();//真正流水号列集合
					String tempHeadPart = headPart;
					for (String value : serialList) {
						//判断当前流水号是否为null,为null不用截取
						if (value != null && !("".equals(value))) {
							value = value.substring(tempHeadPart.length(), tempHeadPart.length() + pointer.length());
							numList.add(value);
							// System.out.println("真正流水号:" + value);
						}
					}
					Map<String, String> map = new HashMap<String, String>();//真正流水号列集合
					List<String> fillNumList = new ArrayList<String>();//补号的集合
					for (String value : numList) {
						map.put(value, value);
					}
					for (int i = Integer.valueOf(initialValue); i < Integer.valueOf(pointer); i = i + incremental) {
						//若有补位，则返回的补号也要有补位
						if (isFillPosition) {
							int length = initialValue.length();
							// System.out.println("长度为：" + length);
							// 0 代表前面补充0    
							// 4 代表长度为4    
							// d 代表参数为正数型    
							String temp = String.format("%0" + String.valueOf(length) + "d", i);
							if (!map.containsKey(temp)) {
								fillNumList.add(temp);
								// System.out.println("需要的补号：" + temp);
							}
						}
					}
					//如果补号集合里有补号
					if (fillNumList.size() > 0) {
						Collections.sort(fillNumList);//对补号集合进行排序
						String minFillNum = fillNumList.get(0);
						// System.out.println("最小的补号：" + minFillNum);
						/*if (isOccupied) {
							serialNumberMaintain.setPointer(minFillNum);
							serialNumberMaintainDao.save(serialNumberMaintain);
						}*/
						serialNum = headPart + minFillNum + lastPart;
						// System.out.println("更新维护指针:" + minFillNum);
						// System.out.println("更新生成的流水号：" + headPart + "【" + minFillNum + "】" + lastPart);
					} else {
						pointer = String.valueOf(Integer.valueOf(pointer) + incremental);//若没有补号则指针值加上增量
						//补位处理
						if (isFillPosition) {
							int length = initialValue.length();
							// System.out.println("长度为：" + length);
							// 0 代表前面补充0    
							// 4 代表长度为4    
							// d 代表参数为正数型    
							pointer = String.format("%0" + String.valueOf(length) + "d", Integer.valueOf(pointer));
							// System.out.println("补位后：" + pointer);
						}
						//若占用
						if (isOccupied) {
							serialNumberMaintain.setPointer(pointer);
							serialNumberMaintain.setHeadPart(headPart);
							serialNumberMaintain.setLastPart(lastPart);
							serialNumberMaintain.setIsEditor(isEditor);
							serialNumberMaintainDao.save(serialNumberMaintain);
						}
						serialNum = headPart + pointer + lastPart;

						// System.out.println("aaa更新维护指针:" + pointer);
						// System.out.println("aaa更新生成的流水号：" + headPart + "【" + pointer + "】" + lastPart);
					}
				}
			} else {
				pointer = String.valueOf(Integer.valueOf(pointer) + incremental);//若没有补号则指针值加上增量
				//补位处理
				if (isFillPosition) {
					int length = initialValue.length();
					// System.out.println("长度为：" + length);
					// 0 代表前面补充0    
					// 4 代表长度为4    
					// d 代表参数为正数型    
					pointer = String.format("%0" + String.valueOf(length) + "d", Integer.valueOf(pointer));
					// System.out.println("补位后：" + pointer);
				}
				//若占用
				if (isOccupied) {
					serialNumberMaintain.setPointer(pointer);
					serialNumberMaintain.setHeadPart(headPart);
					serialNumberMaintain.setLastPart(lastPart);
					serialNumberMaintain.setIsEditor(isEditor);
					serialNumberMaintainDao.save(serialNumberMaintain);
				}
				serialNum = headPart + pointer + lastPart;

				// System.out.println("更新维护指针:" + pointer);
				// System.out.println("更新生成的流水号：" + headPart + "【" + pointer + "】" + lastPart);
			}

		} else {
			//若流水号维护中不存在则新建流水号
			SerialNumberMaintain maintain = new SerialNumberMaintain();
			maintain.setId(numberId);
			maintain.setName(name);
			maintain.setKeyPart(keyPart);
			maintain.setHeadPart(headPart);
			maintain.setLastPart(lastPart);
			maintain.setPointer(initialValue);
			maintain.setIsEditor(isEditor);
			//若被占用，存入数据库
			if (isOccupied) {
				serialNumberMaintainDao.save(maintain);
			}
			serialNum = headPart + initialValue + lastPart;

			// System.out.println("新建维护指针:" + initialValue);
			// System.out.println("首次生成的流水号：" + headPart + "【" + initialValue + "】" + lastPart);
		}
		// System.out.println("最终流水号：" + serialNum);
		return serialNum;

	}

	/**
	 * 
	 * 获取所有流水号id集合
	 * 
	 * @return
	 */
	@Override
	public List<String> getSerialNumberIdList() {
		List<SerialNumber> serialNumberList = serialNumberDao.getAll();
		List<String> serialNumberIdList = new ArrayList<String>();
		for (SerialNumber serialNumber : serialNumberList) {
			serialNumberIdList.add(serialNumber.getId());
		}
		return serialNumberIdList;
	}

	/**
	 * 
	 * 获取所有流水号类型集合
	 * 
	 * @return
	 */
	@Override
	public List<String> getSerialNumberTypeList() {
		List<SerialNumber> serialNumberList = serialNumberDao.getAll();
		List<String> serialNumberTypeList = new ArrayList<String>();
		for (SerialNumber serialNumber : serialNumberList) {
			//获取所有不重复的流水号类型
			if (Collections.frequency(serialNumberTypeList, serialNumber.getType()) < 1)
				serialNumberTypeList.add(serialNumber.getType());
		}
		return serialNumberTypeList;
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.serialnumber.service.SerialNumberService#getById(java.lang.String)
	 */
	@Override
	public SerialNumber getById(String serialNumberId) {
		return serialNumberDao.getById(serialNumberId);
	}

}
