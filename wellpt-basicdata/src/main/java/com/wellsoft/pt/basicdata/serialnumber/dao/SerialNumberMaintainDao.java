package com.wellsoft.pt.basicdata.serialnumber.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wellsoft.pt.basicdata.serialnumber.entity.SerialNumberMaintain;
import com.wellsoft.pt.core.dao.hibernate.HibernateDao;

/**
 * 
 * Description: 流水号定义数据层访问类
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
@Repository
public class SerialNumberMaintainDao extends HibernateDao<SerialNumberMaintain, String> {

	public SerialNumberMaintain getById(String id) {
		return findUniqueBy("id", id);
	}

	//add by huanglinchuan 2014.10.17 begin
	public SerialNumberMaintain getByIdAndKeyPart(String id,String keyPart){
		Map<String,String> map=new HashMap<String,String>();
		map.put("id", id);
		map.put("keyPart", keyPart);
		return findUnique("from SerialNumberMaintain a where a.id=:id and a.keyPart=:keyPart",map);
	}
	//add by huanglinchuan 2014.10.17 end
}
