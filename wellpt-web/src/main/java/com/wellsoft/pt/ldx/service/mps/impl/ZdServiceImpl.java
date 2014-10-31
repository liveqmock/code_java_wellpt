package com.wellsoft.pt.ldx.service.mps.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.ZoneView;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wellsoft.pt.basicdata.sap.service.SAPRfcService;
import com.wellsoft.pt.basicdata.sap.util.SAPRfcJson;
import com.wellsoft.pt.core.service.impl.BaseServiceImpl;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.file.dao.FmFileDao;
import com.wellsoft.pt.file.entity.FmFile;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;
import com.wellsoft.pt.ldx.dao.mps.Zdviewdao;
import com.wellsoft.pt.ldx.model.mainData.Baoguanview;
import com.wellsoft.pt.ldx.model.mainData.Ficoview;
import com.wellsoft.pt.ldx.model.mps.Zdview;
import com.wellsoft.pt.ldx.service.mps.ZdService;

@Service
@Transactional
public class ZdServiceImpl extends BaseServiceImpl implements ZdService {
	
	@Autowired
	private Zdviewdao zdViewDao;

	@Override
	public List<QueryItem> queryQueryItemData(String hql,
			Map<String, Object> queryParams, PagingInfo pagingInfo) {
		return this.dao.query(hql, queryParams, QueryItem.class, pagingInfo);
		// TODO Auto-generated method stub
	
	}
	@Override
	public Map saveZdview(Zdview objectview) {
		// TODO Auto-generated method stub
		
		HashMap hashmap = new HashMap();
		objectview.setCreator(SpringSecurityUtils.getCurrentUserId());
		objectview.setCreateTime(new Date());
		objectview.setModifier(SpringSecurityUtils.getCurrentUserId());
		objectview.setModifyTime(new Date());
		this.dao.save(objectview);
		return hashmap;//
	}
	@Override
	public void FindAndDelete() {
		List<Zdview> objects = zdViewDao.find("from Zdview where creator=?",SpringSecurityUtils.getCurrentUserId());
		for(Zdview d1:objects){
			
			zdViewDao.delete(d1);
		}
	}
	@Override
	public Map saveZdviews(List<Zdview> objectviewlist) {
		// TODO Auto-generated method stub
		HashMap hashmap = new HashMap();
		for(Zdview objectview:objectviewlist){
			
			objectview.setCreator(SpringSecurityUtils.getCurrentUserId());
			objectview.setCreateTime(new Date());
			objectview.setModifier(SpringSecurityUtils.getCurrentUserId());
			objectview.setModifyTime(new Date());
			this.dao.save(objectview);	
		}
		return hashmap;//
	}
	@Override
	public List<Zdview> getZdviews(String xs,String jh,String sc,String sd) {
		// TODO Auto-generated method stub
		
		List<Zdview> objects = zdViewDao.find("from Zdview where sailorder like ? and porder like ? and border like ? and sapid like ?",xs,jh,sc,sd);
		return objects;
		
	}
}
