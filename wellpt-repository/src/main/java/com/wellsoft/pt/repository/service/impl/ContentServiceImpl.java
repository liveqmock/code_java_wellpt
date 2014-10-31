package com.wellsoft.pt.repository.service.impl;

import java.io.*;
import java.util.*;

import javax.jcr.*;

import net.sf.json.*;
import net.sf.json.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.google.common.collect.*;
import com.wellsoft.pt.core.context.*;
import com.wellsoft.pt.core.entity.*;
import com.wellsoft.pt.repository.service.*;
import com.wellsoft.pt.repository.support.*;
import com.wellsoft.pt.utils.json.*;

/**
 * 
* @ClassName: DefaultContentManager
* @Description: 利用jcr建立存储word和索引的处理。
* 每个表建立一个父node，根据id（所有id均为uuid类型）建立对应实体的node，根据filed定义
* 中的是否索引，如果是索引的字段将根据单独存为一个属性，如果不是索引字段将全部放入一个
* content字段中，该表中上传的附件也将放入节点中
* @author lilin
 */
@Service
public class ContentServiceImpl implements ContentService {
	//	@Autowired
	//	private JCRRepositoryHelper repositroyManager;
	@Autowired
	private FieldService fieldManager;

	protected static JsonConfig config = new JsonConfig();
	static {
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		config.setExcludes(new String[] { "handler", "hibernateLazyInitializer" });
	}

	@Override
	public void addNode(String tableName, HashMap map) {
		//
		try {
			Node rootnode = JcrRepositoryHelper.getSystemSession().getRootNode();
			Node tablenode = null;
			try {
				tablenode = rootnode.getNode(tableName);
			} catch (javax.jcr.PathNotFoundException e) {
			}
			if (tablenode == null) {
				tablenode = rootnode.addNode(tableName);
			}
			// id值作为node path
			Node entitynode = tablenode.addNode(map.get("uuid").toString());
			//这里利用filedmanage的定义来处理jcr对应关系。
			//需要进行索引存储的字段，每个字段单独一个属性

			JSONObject jsonEntity = JSONObject.fromObject(map, config);
			entitynode.setProperty("content", jsonEntity.toString());

			//			//只要索引不需要存储的字段，作为jcr的多值字段存放
			//			List<FieldDefinition> unIndexfieldlist = fieldManager.getAllUnIndexField(tableName);
			//			StringBuilder content = new StringBuilder();
			//			List<String> contentlist = Lists.newArrayList();
			//			for (FieldDefinition field : unIndexfieldlist) {
			//				contentlist.add(map.get(field.getFieldName()).toString());
			//			}
			//			Object[] objcetarray = contentlist.toArray();
			//
			//			entitynode.setProperty("content", Arrays.asList(objcetarray).toArray(new String[objcetarray.length]));

			//文档字段
			//			String documentuuid = map.get("documentid").toString();
			//			if (documentuuid != null) {
			//				entitynode.setProperty("document", documentuuid);
			//			}
			JcrRepositoryHelper.getSystemSession().save();
		} catch (PathNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void addNode(IdEntity entity) {
		String tablename = CommonSqlManager.getTableName(entity.getClass());
		//
		try {
			Node rootnode = JcrRepositoryHelper.getSystemSession().getRootNode();
			Node tablenode = null;
			try {
				tablenode = rootnode.getNode(tablename);
			} catch (javax.jcr.PathNotFoundException e) {
			}
			if (tablenode == null) {
				tablenode = rootnode.addNode(tablename);
			}

			Node entitynode = tablenode.addNode(entity.getUuid());
			//这里利用filedmanage的定义来处理jcr对应关系。
			//需要进行索引存储的字段，每个字段单独一个属性
			JSONObject jsonEntity = JSONObject.fromObject(entity, config);
			entitynode.setProperty("content", jsonEntity.toString());

			//			//只要索引不需要存储的字段，作为jcr的多值字段存放
			//			List<FieldDefinition> unIndexfieldlist = fieldManager.getAllUnIndexField(tablename);
			//			StringBuilder content = new StringBuilder();
			//			List<String> contentlist = Lists.newArrayList();
			//			for (FieldDefinition field : unIndexfieldlist) {
			//				contentlist.add(BeanUtils.getProperty(entity, field.getFieldName()).toString());
			//				//添加一个分割符
			//				//				content.append("@@");
			//				//				entitynode.setProperty(field.getFieldName(), BeanUtils.getProperty(entity, field.getFieldName()));
			//			}
			//			if (contentlist.size() > 0) {
			//				Object[] objcetarray = contentlist.toArray();
			//
			//				entitynode.setProperty("content", Arrays.asList(objcetarray).toArray(new String[objcetarray.length]));
			//			}
			//文档字段
			//			String documentuuid = BeanUtils.getProperty(entity, "documentid");
			//			if (documentuuid != null) {
			//				entitynode.setProperty("document", documentuuid);
			//			}
			JcrRepositoryHelper.getSystemSession().save();
		} catch (PathNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteNode(IdEntity entity) {
		String tablename = CommonSqlManager.getTableName(entity.getClass());
		//
		try {
			Node rootnode;

			rootnode = JcrRepositoryHelper.getSystemSession().getRootNode();

			Node tablenode = null;
			try {
				tablenode = rootnode.getNode(tablename);
			} catch (javax.jcr.PathNotFoundException e) {
				return;
			}
			try {
				Node entitynode = tablenode.getNode(entity.getUuid());
				entitynode.remove();
				JcrRepositoryHelper.getSystemSession().save();
			} catch (javax.jcr.PathNotFoundException e) {
				return;
			}

		} catch (PathNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteNode(String tableName, HashMap map) {
		try {
			Node rootnode;

			rootnode = JcrRepositoryHelper.getSystemSession().getRootNode();

			Node tablenode = null;
			try {
				tablenode = rootnode.getNode(tableName);
			} catch (javax.jcr.PathNotFoundException e) {
				return;
			}
			try {
				Node entitynode = tablenode.getNode(map.get(IdEntity.UUID).toString());
				entitynode.remove();
				JcrRepositoryHelper.getSystemSession().save();
			} catch (javax.jcr.PathNotFoundException e) {
				return;
			}

		} catch (RepositoryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void updateNode(IdEntity entity) {
		String tablename = CommonSqlManager.getTableName(entity.getClass());
		//
		try {
			Node rootnode;

			rootnode = JcrRepositoryHelper.getSystemSession().getRootNode();

			Node tablenode = null;
			try {
				tablenode = rootnode.getNode(tablename);
			} catch (javax.jcr.PathNotFoundException e) {
				return;
			}
			try {
				Node entitynode = tablenode.getNode(entity.getUuid());
				JSONObject jsonEntity = JSONObject.fromObject(entity, config);
				entitynode.setProperty("content", jsonEntity.toString());
				JcrRepositoryHelper.getSystemSession().save();
			} catch (javax.jcr.PathNotFoundException e) {
				return;
			}

		} catch (RepositoryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void updateNode(String tableName, HashMap map) {
		try {
			Node rootnode;

			rootnode = JcrRepositoryHelper.getSystemSession().getRootNode();

			Node tablenode = null;
			try {
				tablenode = rootnode.getNode(tableName);
			} catch (javax.jcr.PathNotFoundException e) {
				return;
			}

			try {
				Node entitynode = tablenode.addNode(map.get("uuid").toString());
				//这里利用filedmanage的定义来处理jcr对应关系。
				//需要进行索引存储的字段，每个字段单独一个属性

				JSONObject jsonEntity = JSONObject.fromObject(map, config);
				entitynode.setProperty("content", jsonEntity.toString());
				JcrRepositoryHelper.getSystemSession().save();
			} catch (javax.jcr.PathNotFoundException e) {
				return;
			}

		} catch (RepositoryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * 
	* @Title: getAllFile
	* @Description: 获取该节点下所有附件
	* @param @param tableName
	* @param @param id
	* @param @return    设定文件
	* @return List<InputStream>    返回类型
	* @throws
	 */
	@Override
	public List<InputStream> getAllFile(String tableName, String id) {
		try {
			Node rootnode;

			rootnode = JcrRepositoryHelper.getSystemSession().getRootNode();

			Node tablenode = null;
			try {
				tablenode = rootnode.getNode(tableName);
			} catch (javax.jcr.PathNotFoundException e) {
				return null;
			}

			Node entitynode = tablenode.getNode(id);

			//获取文档字段
			//			String documentuuid = entitynode.getProperty("documentid").toString();
			//
			//			Node attatchNode = rootnode.getNode(documentuuid);
			//			Value attachFileValue = (Value) attatchNode.getProperty("").getValue();
			//			InputStream attachFileIS = attachFileValue.getStream();
			//			if (documentuuid != null) {
			//				entitynode.setProperty("document", documentuuid);
			//			}

		} catch (RepositoryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	* @Title: getNodeMap
	* @Description:获取节点，按照map值返回
	* @param @param tableName
	* @param @param id
	* @param @return    设定文件
	* @return Map    返回类型
	* @throws
	 */
	@Override
	public Map getNodeMap(String tableName, String uuid) {

		Map nodemap = Maps.newHashMap();
		try {
			Node rootnode = JcrRepositoryHelper.getSystemSession().getRootNode();
			Node node = rootnode.getNode(tableName + "/" + uuid);
			Property content = node.getProperty("content");
			nodemap = JsonUtils.toMap(content.getValue().getString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return nodemap;
	}

	public Object getNodeEntity(Class clazz, String uuid) {
		Object nodeentity = null;

		try {
			if (!clazz.isInstance(IdEntity.class)) {
				return null;
			}
			Node rootnode = JcrRepositoryHelper.getSystemSession().getRootNode();
			String tablename = CommonSqlManager.getTableName(clazz);
			Node node = rootnode.getNode(tablename + "/" + uuid);
			Property content = node.getProperty("content");
			nodeentity = JsonUtils.toBean(content.getValue().getString(), clazz);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return nodeentity;
	}

	/**
	 * 
	* @Title: getProperty
	* @Description:获取节点下某一属性的值
	* @param @param tableName
	* @param @param id
	* @param @param property
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	@Override
	public String getProperty(String tableName, String id, String property) {
		return null;
	}

	/**
	 * 
	* @Title: getProperties
	* @Description: 获取节点下某些属性的值
	* @param @param tableName
	* @param @param id
	* @param @param properties
	* @param @return    设定文件
	* @return List<String>    返回类型
	* @throws
	 */
	@Override
	public List<String> getProperties(String tableName, String id, String... properties) {
		return null;
	}
}
