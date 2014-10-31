package com.wellsoft.pt.ldx.service.sales.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.core.service.impl.BaseServiceImpl;
import com.wellsoft.pt.file.dao.FmFileDao;
import com.wellsoft.pt.file.entity.FmFile;
import com.wellsoft.pt.ldx.service.sales.OutBoundService;

@Service
@Transactional
public class OutBoundServiceImpl extends BaseServiceImpl implements
		OutBoundService {

	@Autowired
	private FmFileDao fmFileDao;

	@SuppressWarnings({ "deprecation" })
	@Override
	public List<FmFile> findFmFile(String wl) {
		List<FmFile> fmFiles = fmFileDao.find(
				"from FmFile where status=2 and fmFolder.uuid=? and title=?",
				"849b4c95-e571-45a4-8f12-a678b82ce091", wl);
		return fmFiles;
	}
}
