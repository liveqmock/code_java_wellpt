package com.wellsoft.pt.ldx.service.ficoManage.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.ldx.model.ficoManage.Zfmt0017;
import com.wellsoft.pt.ldx.service.ficoManage.INonReceiveService;
import com.wellsoft.pt.ldx.service.impl.SapServiceImpl;
import com.wellsoft.pt.ldx.util.StringUtils;

/**
 * 
 * Description: 未收汇手工资料service
 *  
 * @author HeShi
 * @date 2014-8-25
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-8-25 	HeShi		2014-8-25		Create
 * </pre>
 *
 */
@Service
@Transactional
public class NonReceiveServiceImpl extends SapServiceImpl implements INonReceiveService{
	/**
	 *  删除客户记录
	 */
	@Override
	public void deleteDoc(String params){
		if(StringUtils.isBlank(params))      //判断获取到的参数值是否为空,为空，直接返回，不做后续处理
			return;
		String[] array = params.split(";");
		if(null==array||array.length==0)
			return;
		for (String map: array) {
			String delete = "delete from zfmt0017 where vbeln='" +StringUtils.nullToString(map) + "' and mandt=" +getClient() ;
			execSql(delete);
		}
	}

	@Override
	public Map<?, ?> saveDoc(Zfmt0017 doc) {
		Map<String,String> map = new HashMap<String,String>();
		if(StringUtils.isBlank(doc.getVbeln())){
			map.put("res","fail");
			map.put("msg","外向交货单不能为空值!");			
			return map;
		}
		if(StringUtils.isNotBlank(checkVbelnExist(doc.getVbeln()))){
			map.put("res","fail");
			map.put("msg","当前外向交货单已存在,请勿重复添加!");			
			return map;
		}
		doc.setMandt(getClient());
		doc.setVbeln(StringUtils.addLeftZero(doc.getVbeln(),10));
		doc.setVbelv(StringUtils.isBlank(doc.getVbelv())?" ":doc.getVbelv());
		doc.setLfimg(StringUtils.isBlank(doc.getLfimg())?"0":doc.getLfimg());
		doc.setZeamt(StringUtils.isBlank(doc.getZeamt())?"0":doc.getZeamt());
		doc.setZddate(StringUtils.isBlank(doc.getZddate())?"00000000":doc.getZddate());
		doc.setZclfimg(StringUtils.isBlank(doc.getZclfimg())?"0":doc.getZclfimg());
		doc.setZcost(StringUtils.isBlank(doc.getZcost())?"0":doc.getZcost());
		doc.setZmodel(StringUtils.isBlank(doc.getZmodel())?" ":doc.getZmodel());
		doc.setZterm(StringUtils.isBlank(doc.getZterm())?" ":doc.getZterm());
		doc.setZvtext(StringUtils.isBlank(doc.getZvtext())?" ":doc.getZvtext());
		doc.setZcamt(StringUtils.isBlank(doc.getZcamt())?"0":doc.getZcamt());
		doc.setZnote(StringUtils.isBlank(doc.getZnote())?" ":doc.getZnote());
		doc.setKunnr(StringUtils.isBlank(doc.getKunnr())?" ":doc.getKunnr());
		sapDao.save(doc);
		map.put("res","success");
		map.put("msg","保存成功!");			
		return map;
	}

	@Override
	public Map<?, ?> updateDoc(Zfmt0017 doc) {
		Map<String,String> map = new HashMap<String,String>();
		Zfmt0017 temp = findDocByVbeln(doc.getVbeln());
		temp.setVbeln(StringUtils.addLeftZero(doc.getVbeln(),10));
		temp.setVbelv(StringUtils.isBlank(doc.getVbelv())?" ":doc.getVbelv());
		temp.setLfimg(StringUtils.isBlank(doc.getLfimg())?"0":doc.getLfimg());
		temp.setZeamt(StringUtils.isBlank(doc.getZeamt())?"0":doc.getZeamt());
		temp.setZddate(StringUtils.isBlank(doc.getZddate())?"00000000":doc.getZddate());
		temp.setZclfimg(StringUtils.isBlank(doc.getZclfimg())?"0":doc.getZclfimg());
		temp.setZcost(StringUtils.isBlank(doc.getZcost())?"0":doc.getZcost());
		temp.setZmodel(StringUtils.isBlank(doc.getZmodel())?" ":doc.getZmodel());
		temp.setZterm(StringUtils.isBlank(doc.getZterm())?" ":doc.getZterm());
		temp.setZvtext(StringUtils.isBlank(doc.getZvtext())?" ":doc.getZvtext());
		temp.setZcamt(StringUtils.isBlank(doc.getZcamt())?"0":doc.getZcamt());
		temp.setZnote(StringUtils.isBlank(doc.getZnote())?" ":doc.getZnote());
		temp.setKunnr(StringUtils.isBlank(doc.getKunnr())?" ":doc.getKunnr());
		sapDao.save(temp);
		map.put("res","success");
		map.put("msg","保存成功!");			
		return map;
	}

	@Override
	public String checkVbelnExist(String vbeln) {
		String sql = "select 1 from zfmt0017 where mandt="+getClient()+" and vbeln='"+StringUtils.nullToString(vbeln)+"'";
		List<Object> list = findListBySql(sql);
		if(null!=list && list.size()>0){
			return "当前外向交货单已存在!";
		}
		return "";
	}

	@Override
	public Zfmt0017 findDocByVbeln(String vbeln) {
		return sapDao.findUniqueBy(Zfmt0017.class,"vbeln",vbeln);
	}

}
