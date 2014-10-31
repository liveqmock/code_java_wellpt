package com.wellsoft.pt.dytable.service;

import java.io.File;

import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.dytable.dao.FieldDefinitionDao;
import com.wellsoft.pt.dytable.dao.FormDefinitionDao;
import com.wellsoft.pt.dytable.entity.FormDefinition;

/**
 * Description: 处理根据配置文件添加 修改 删除动态表单定义的操作和处理service接口
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
public interface DynamicFormConfigService {
	/**
	 * 
	* @Title: createDynamicForm
	* @Description: 根据hbm文件创建自定义表单
	* 
	* @param @param hbmxmlfile    设定文件
	* @return void    返回类型
	* @throws
	 */

	public void createDynamicForm(File hbmxmlfile);

	/**
	 * 
	* @Title: updateDynamicForm
	* @Description: 更新数据库，包括更新sessionfactory中相应的映射
	* @param     设定文件
	* @return void    返回类型
	* @throws
	 */
	public void updateDynamicForm(File hbmxmlfile);

	/**
	 * 更新数据库表结构
	 * 
	 * @param hbmxmlfile
	 * @param entity
	 */
	public void updateDynamicForm(File hbmxmlfile, FormDefinition entity);

	/**
	 * 
	* @Title: deleteDynamicForm
	* @Description: 删除相应的配置，这里数据库中如果有数据要如何处理呢？
	* @param     设定文件
	* @return void    返回类型
	* @throws
	 */
	public void deleteDynamicForm(File hbmxmlfile);

	/**
	 * 
	* @Title: setFormDefinitionDao
	* @Description: 设置FormDefinitionDao
	* @param @param formDefinitionDao    设定文件
	* @return void    返回类型
	* @throws
	 */
	@Autowired
	public void setFormDefinitionDao(FormDefinitionDao formDefinitionDao);

	/**
	 * 
	* @Title: getFormDefinitionDao
	* @Description: 获取FormDefinitionDao
	* @param @return    设定文件
	* @return FormDefinitionDao    返回类型
	* @throws
	 */
	public FormDefinitionDao getFormDefinitionDao();

	/**
	 * 
	* @Title: setFiledDefinitionDao
	* @Description: 设置fielddefinitiondao
	* @param @param filedDefinitionDao    设定文件
	* @return void    返回类型
	* @throws
	 */
	@Autowired
	public void setFieldDefinitionDao(FieldDefinitionDao filedDefinitionDao);

	/**
	 * 
	* @Title: getFiledDefinitionDao
	* @Description: 获取fieldDefinitiondao
	* @param @return    设定文件
	* @return FieldDefinitionDao    返回类型
	* @throws
	 */
	public FieldDefinitionDao getFieldDefinitionDao();

	/**
	 * 如何描述该方法
	 * 
	 * @param cfg
	 */
	public void addNewConfig(Configuration cfg);
}
