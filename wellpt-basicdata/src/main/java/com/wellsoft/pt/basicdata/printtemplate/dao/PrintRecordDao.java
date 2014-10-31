package com.wellsoft.pt.basicdata.printtemplate.dao;

import org.springframework.stereotype.Repository;

import com.wellsoft.pt.basicdata.printtemplate.entity.PrintRecord;
import com.wellsoft.pt.core.dao.hibernate.HibernateDao;

/**
 * 
 * Description:  打印记录数据层访问类
 *  
 * @author zhouyq
 * @date 2013-4-3
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-4-3.1	zhouyq		2013-4-3		Create
 * </pre>
 *
 */
@Repository
public class PrintRecordDao extends HibernateDao<PrintRecord, String> {

}
