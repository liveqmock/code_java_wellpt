package com.wellsoft.pt.repository.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wellsoft.pt.core.entity.IdEntity;

/**
 * 
* @ClassName: ContentManager
* @Description: jcr的管理封装
* @author lilin
 */
public interface ContentService {

	public void addNode(String tableName, HashMap map);

	public void addNode(IdEntity entity);

	public void deleteNode(IdEntity entity);

	public void deleteNode(String tableName, HashMap map);

	public void updateNode(IdEntity entity);

	public void updateNode(String tableName, HashMap map);

	public List<InputStream> getAllFile(String tableName, String id);

	public Map getNodeMap(String tableName, String id);

	public String getProperty(String tableName, String id, String property);

	public List<String> getProperties(String tableName, String id, String[] properties);
}
