package com.wellsoft.pt.ldx.service.mps.impl;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.wellsoft.pt.ldx.model.mps.Hisview;
import com.wellsoft.pt.ldx.service.mps.HisService;



@Service
@Transactional
public class HisServiceImpl extends BaseServiceImpl implements HisService {

	@Override
	public List<QueryItem> queryQueryItemData(String hql,
			Map<String, Object> queryParams, PagingInfo pagingInfo) {
		return this.dao.query(hql, queryParams, QueryItem.class, pagingInfo);
		// TODO Auto-generated method stub
	
	}

	@Override
	public Map saveHisview(Hisview objectview) {//实现保存数据功能
		// TODO Auto-generated method stub
		HashMap hashmap = new HashMap();
		objectview.setCreator(SpringSecurityUtils.getCurrentUserId());
		objectview.setCreateTime(new Date());
		objectview.setModifier(SpringSecurityUtils.getCurrentUserId());
		objectview.setModifyTime(new Date());
		this.dao.save(objectview);
		return hashmap;
	}

	@Override
	public Map saveHisviews(List<Hisview> objectviewlist) {
	
		
		HashMap hashmap = new HashMap();
		
		
		for(Hisview objectview:objectviewlist){
			
			objectview.setCreator(SpringSecurityUtils.getCurrentUserId());
			objectview.setCreateTime(new Date());
			objectview.setModifier(SpringSecurityUtils.getCurrentUserId());
			objectview.setModifyTime(new Date());
			this.dao.save(objectview);
		}
		return hashmap;
	}
	
}
