package com.wellsoft.pt.basicdata.serialnumber.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.wellsoft.pt.basicdata.serialnumber.bean.SerialNumberBean;
import com.wellsoft.pt.basicdata.serialnumber.entity.SerialNumber;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryData;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo;
import com.wellsoft.pt.core.entity.IdEntity;

/**
* 
* Description: 流水号定义服务层接口
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

public interface SerialNumberService {
	public static final String ACL_SID = "ROLE_SERIAL_NUMBER";

	/**
	 * 
	 * 获取所有流水号id集合
	 * 
	 * @return
	 */
	public List<String> getSerialNumberIdList();

	/**
	 * 
	 * 获取所有流水号类型集合
	 * 
	 * @return
	 */
	public List<String> getSerialNumberTypeList();

	/**
	 * 
	 * 获得所有流水号定义
	 * 
	 * @return
	 */
	public List<SerialNumber> findAll();

	/**
	 * 根据名称来获取所有的流水号维护集合
	 */
	public SerialNumber getByName(String name);

	/**
	 * 
	 * 根据是否可编辑获得所有可编辑流水号
	 * 
	 * @param uuid
	 * @return
	 */
	public List<SerialNumber> getByIsEditor(Boolean isEditor);

	/**
	 * 
	 * 根据指定ID获取可编辑流水号
	 * 
	 * @param uuid
	 * @return
	 */
	public List<SerialNumber> getByDesignatedId(Boolean isEditor, String designatedId);

	/**
	 * 
	 * 根据指定类型获取可编辑流水号
	 * 
	 * @param uuid
	 * @return
	 */
	public List<SerialNumber> getByDesignatedType(Boolean isEditor, String designatedType);

	/**
	 * 使用acl接口存放流水号定义使用人
	 * 
	 * @param serialNumber
	 * @return
	 */
	SerialNumber saveAcl(SerialNumber serialNumber);

	/**
	 * 通过uuid获取流水号定义
	 * 
	 * @param id
	 * @return
	 */
	public SerialNumber get(String uuid);

	/**
	 * 通过uuid获取流水号定义VO对象
	 * 
	 * @param id
	 * @return
	 */
	public SerialNumberBean getBeanByUuid(String uuid);

	/**
	 * 保存流水号定义
	 * 
	 * @param uuid
	 * @return
	 */
	public void saveBean(SerialNumberBean bean);

	/**
	 * 通过UUID删除流水号定义
	 * 
	 * @param uuid
	 * @return
	 */
	public void remove(String uuid);

	/**
	 * 
	 * 批量删除
	 * 
	 * @param uuids
	 */
	public void deleteAllById(String[] ids);

	/**
	 * 流水号定义列表查询
	 * 
	 * @param queryInfo
	 * @return
	 */
	public JqGridQueryData query(JqGridQueryInfo queryInfo);

	/**
	 * 获取流水号接口,单个实体
	 */
	public abstract <ENTITY extends IdEntity> String getSerialNumber(String id, ENTITY entity, Boolean isOccupied,
			Map<String, Object> dytableMap, String fieldName) throws Exception;

	/**
	 * 获取流水号接口,实体集合
	 */
	public abstract <ENTITY extends IdEntity> String getSerialNumber(String id, Collection<ENTITY> entities,
			Boolean isOccupied, Map<String, Object> dytableMap, String fieldName) throws Exception;

	/**
	 * 
	 * 动态表单专用获取不可编辑流水号接口
	 * 
	 * @return
	 */
	public String getNotEditorSerialNumberForDytable(String serialNumberId, Boolean isOccupied, String formUuid,
			String field);

	/**
	 * 如何描述该方法
	 * 
	 * @param serialNumberId
	 * @return
	 */
	public SerialNumber getById(String serialNumberId);

}
