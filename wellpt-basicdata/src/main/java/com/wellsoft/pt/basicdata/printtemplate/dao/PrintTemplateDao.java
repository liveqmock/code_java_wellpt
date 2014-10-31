package com.wellsoft.pt.basicdata.printtemplate.dao;

import org.springframework.stereotype.Repository;

import com.wellsoft.pt.basicdata.printtemplate.entity.PrintTemplate;
import com.wellsoft.pt.core.dao.hibernate.HibernateDao;

/**
 * 
 * Description: 打印模板数据层访问类
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
@Repository
public class PrintTemplateDao extends HibernateDao<PrintTemplate, String> {

	public PrintTemplate getById(String id) {
		return findUniqueBy("id", id);
	}
}
