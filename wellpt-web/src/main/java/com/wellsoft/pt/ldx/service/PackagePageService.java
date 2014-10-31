package com.wellsoft.pt.ldx.service;

import java.util.List;
import java.util.Map;

import com.wellsoft.pt.core.service.BaseService;
import com.wellsoft.pt.file.entity.FmFile;

@SuppressWarnings("rawtypes")
public interface PackagePageService extends BaseService {

	public Map generateFile(String customer , String material);
	
	public Map upgradeFile(String s);

	public List<FmFile> getFiles(String s);
	
	public Map searchFiles(String s);

}