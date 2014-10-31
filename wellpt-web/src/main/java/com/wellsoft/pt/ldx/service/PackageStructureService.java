package com.wellsoft.pt.ldx.service;

import java.util.Map;

import com.wellsoft.pt.core.service.BaseService;

@SuppressWarnings("rawtypes")
public interface PackageStructureService extends BaseService {

	public Map generateFile(String s);
	
	public Map upgradeFile(String s);

}
