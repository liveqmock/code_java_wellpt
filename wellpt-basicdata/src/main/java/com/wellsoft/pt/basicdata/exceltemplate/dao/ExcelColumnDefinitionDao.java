package com.wellsoft.pt.basicdata.exceltemplate.dao;

import org.springframework.stereotype.Repository;

import com.wellsoft.pt.basicdata.exceltemplate.entity.ExcelColumnDefinition;
import com.wellsoft.pt.core.dao.hibernate.HibernateDao;

/**
 * 
 * Description: Excel列对应数据层访问类
 *  
 * @author zhouyq
 * @date 2013-4-24
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-4-24.1	zhouyq		2013-4-24		Create
 * </pre>
 *
 */
@Repository
public class ExcelColumnDefinitionDao extends HibernateDao<ExcelColumnDefinition, String> {

}
