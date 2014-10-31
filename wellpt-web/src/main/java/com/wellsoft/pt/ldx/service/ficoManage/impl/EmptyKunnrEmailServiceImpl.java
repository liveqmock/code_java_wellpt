package com.wellsoft.pt.ldx.service.ficoManage.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.ldx.service.ficoManage.IEmptyKunnrEmailService;
import com.wellsoft.pt.ldx.service.impl.SapServiceImpl;
import com.wellsoft.pt.ldx.util.StringUtils;

/**
 * 
 * Description: 未知客户补充资料
 *  
 * @author HeShi
 * @date 2014-8-27
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-8-27 	HeShi		2014-8-27		Create
 * </pre>
 *
 */
@Service
@Transactional
public class EmptyKunnrEmailServiceImpl extends SapServiceImpl implements IEmptyKunnrEmailService{

	@Override
	public Map<?, ?> saveText(String zbelnr, String ztext) {
		Map<String,String> map = new HashMap<String,String>();
		String update = "update zfmt0003 set ztext='"+(StringUtils.isBlank(ztext)?" ":ztext)+"' "
			+ " where zbelnr='"+zbelnr+"' and mandt="+getClient();
		execSql(update);
		map.put("res","success");
		map.put("msg","保存成功!");			
		return map;
	}
}
