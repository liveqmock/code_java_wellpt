package com.wellsoft.pt.dyform.service;

import java.util.List;
import java.util.Map;

import com.wellsoft.pt.basicdata.datasource.entity.DataSourceDefinition;
import com.wellsoft.pt.dytable.bean.TreeNodeBean;

/**
 * Description: 处理动态表单与数据源的交互
 *  
 * @author hunt
 * @date 2014-9-15
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-9-15.1	hunt		2014-9-15		Create
 * </pre>
 *
 */
public interface DyFormDataSourceService {
	/**
	 * 获取所有的数据源
	 * @return
	 */
	List<DataSourceDefinition> getAllDataSource();

	/**
	 * 获取所有的数据源,只返回name和id 
	 * @return 返回map ,map的key为数据源id, value为数据源名称
	 */
	List<Map<String, String>> getAllDataSourceNameAndId();

	public List<TreeNodeBean> getAllDataSourceId();
}
