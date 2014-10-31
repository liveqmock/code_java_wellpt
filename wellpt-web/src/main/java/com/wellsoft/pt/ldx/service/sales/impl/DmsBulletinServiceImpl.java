package com.wellsoft.pt.ldx.service.sales.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.ldx.model.sales.Bulletin;
import com.wellsoft.pt.ldx.service.impl.DmsServiceImpl;
import com.wellsoft.pt.ldx.service.sales.DmsBulletinService;

@Service
@Transactional
public class DmsBulletinServiceImpl extends DmsServiceImpl implements
		DmsBulletinService {

	@Override
	public Bulletin getBulletin(String objectUuid) {
		Bulletin object = this.dmsDao.get(Bulletin.class, objectUuid);
		return object;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map deleteObjects(String s) {
		HashMap hashmap = new HashMap();
		String as[] = s.split(";");
		for (int i = 0; i < as.length; i++) {
			Bulletin object = this.dmsDao.get(Bulletin.class, as[i]);
			this.dmsDao.delete(object);
		}
		hashmap.put("state", "1");
		return hashmap;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map saveBulletin(Bulletin object) {
		HashMap hashmap = new HashMap();
		if (StringUtils.isNotEmpty(object.getId())) {
			Bulletin bulletin = this.dmsDao.get(Bulletin.class, object.getId());
			bulletin.setName(object.getName());
			bulletin.setContent(object.getContent());
			this.dmsDao.save(bulletin);
		} else {
			object.setId(null);
			object.setCreatedate(new Date());
			this.dmsDao.save(object);
		}
		hashmap.put("state", "1");
		return hashmap;
	}
}
