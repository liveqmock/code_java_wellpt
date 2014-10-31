package com.wellsoft.pt.basicdata.systemtable.dao;

import org.springframework.stereotype.Repository;

import com.wellsoft.pt.basicdata.systemtable.entity.SystemTableAttribute;
import com.wellsoft.pt.core.dao.hibernate.HibernateDao;

/**
 * 
 * Description: 系统表结构数据层访问类
 *  
 * @author zhouyq
 * @date 2013-3-22
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-3-22.1	zhouyq		2013-3-22		Create
 * </pre>
 *
 */
@Repository
public class SystemTableAttributeDao extends HibernateDao<SystemTableAttribute, String> {
}
