package com.wellsoft.pt.dytable.service;

import java.util.List;

import com.wellsoft.pt.common.bean.LabelValueBean;
import com.wellsoft.pt.dytable.entity.FieldDefinition;

/**
 * Description: 动态表单字段定义service接口
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
public interface FieldDefinitionService {
	/**
	 * 通过id查找字段定义对象
	 * 
	 * @param id
	 * @return
	 */
	public FieldDefinition getFieldById(String id);

	/**
	 * 获取所有字段字义信息列表
	 * 
	 * @return
	 */
	public List<FieldDefinition> getAllField();

	/**
	 * 保存单个实体对象
	 * 
	 * @param entity
	 */
	public void saveField(FieldDefinition entity);

	/**
	 * 根据提供的id删除字段定义信息
	 * 
	 * @param id
	 */
	public void deleteField(String id);

	/**
	 * 根据表名获取表对应的全部字段定义信息列表
	 * 
	 * @param entityName
	 * @return
	 */
	public List<FieldDefinition> getAllFieldByTable(String entityName);

	/**
	 * 根据表单UUID查找对应的字段字义信息列表
	 * 
	 * @param uid
	 * @return
	 */
	List<FieldDefinition> getFieldByForm(String uid);

	/**
	 * 根据指定的实体名和版本号查找对应的字段定义信息列表
	 * 
	 * @param entityName
	 * @param version
	 * @return
	 */
	List<FieldDefinition> getField(String entityName, String version);

	/**
	 * 如何描述该方法
	 * 
	 * @param tableId
	 * @return
	 */
	public List<LabelValueBean> getFieldLavelValueBean(String tableId);

	/**
	 * 如何描述该方法
	 * 
	 * @param tableId
	 * @return
	 */
	public List<LabelValueBean> getFieldNameAndValueLavelValueBean(String tableId);
}
