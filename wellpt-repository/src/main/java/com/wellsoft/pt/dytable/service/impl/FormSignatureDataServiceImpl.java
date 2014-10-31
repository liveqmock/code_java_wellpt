package com.wellsoft.pt.dytable.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.dytable.service.FormSignatureDataService;
import com.wellsoft.pt.repository.dao.DbTableDao;

@Service
@Transactional
public class FormSignatureDataServiceImpl implements FormSignatureDataService {

	@Autowired
	DbTableDao dbTableDao;

	@Override
	public List<Map<String, Object>> query(String sql) throws Exception {

		return dbTableDao.query(sql);
	}

}
