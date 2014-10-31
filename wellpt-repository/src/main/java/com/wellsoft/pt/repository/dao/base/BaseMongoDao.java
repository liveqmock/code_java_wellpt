package com.wellsoft.pt.repository.dao.base;

 

import java.io.FileNotFoundException;
import java.io.InputStream;
 
import java.util.List;
import java.util.Map;

 

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
 
import com.wellsoft.pt.repository.entity.mongo.file.MongoFileEntity;
import com.wellsoft.pt.repository.support.enums.EnumQueryPattern;
 

 
 
/**
 * Description: 文件操作接口
 *  
 * @author Administrator
 * @date 2014-3-12
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-3-12.1	hunt		2014-3-12		Create
 * </pre>
 *
 */
public interface BaseMongoDao {
	public final String dbTimePattern = "yyyyMMddmmss";
	
	 
	
	
	/**
	 * 将文件持久化
	 * 
	 * @param filePath,保存的路径 <br/> 保存到mongodb里面时，该字段将作为bucket <br/>如果该值为null则默认为fs
	 * @param fileName 文件名
	 * @param file 文件
	 * @return
	 */
	public MongoFileEntity saveFile(String filePath, String fileName, String contentType, InputStream inputStream,   String id);
	
	
	/**
	 * 通过id精确定位filePath文件路径下的文件记录 
	 * @param filePath 文件路径<br/>如果该值为null则默认为fs
	 * @param id 文件在保存时,会返回一个对象给用户,该对象中的id即为该参数.
	 * @return
	 * @throws FileNotFoundException 
	 */
	public MongoFileEntity findFileById(String filePath, String id)   ;
	
	
	 
	/**
	 * 通过file模糊搜索filePath文件路径下的文件记录
	 * @param filePath 文件路径 <br/>如果该值为null则默认为fs
	 * @param fileName 文件名
	 * @param pattern 文件名模糊匹配方式
	 * @return 
	 */
	public List<MongoFileEntity> findFileByName(String filePath,String fileName, EnumQueryPattern pattern);
	
	
	
	public List<GridFSDBFile> findProtoFiles(String filePath, String synbeforedays);
	 
	 
	/**
	 *根据fileName定位更新文件 
	 * @param filePath 文件路径 <br/>如果该值为null则默认为fs
	 * @param fileName  
	 * @param id  文件在保存时,会返回一个对象给用户,该对象中的id即为该参数.
	 * @return
	 */
	//mongodb drive 没有提供该接口
	//public int updateFileNameById(String filePath,String fileName, String id);
	
	
	/**
	 *根据id定位更新文件 
	 * @param filePath 文件路径 <br/>如果该值为null则默认为fs
	 * @param fileName 
	 * @param file
	 * @return
	 *//*
	public FileEntity updateFileById(String filePath,String id, File file);*/
	
	/**
	 * 根据id 精确删除文件记录
	 * 
	 * @param filePath 文件路径 <br/>如果该值为null则默认为fs
	 * @param id  文件在保存时,会返回一个对象给用户,该对象中的id即为该参数.
	 */
	public void deleteFileById(String filePath, String id);
	
	
	/**
	 * 根据fileName精确 删除文件记录
	 * 
	 * @param filePath 文件路径 <br/>如果该值为null则默认为fs
	 * @param fileName  
	 */
	public void deleteFileByFileName(String filePath, String fileName);
	
	
	
	
	
	public DBObject findDocumentById(String collectionName,String id);
	
	/**
	 * 创建文档,如果创建失败则返回null, id为null时表示id为驱动产生。
	 * @param collectionName
	 * @param params
	 * @param id
	 * @return
	 */
	public DBObject createDocument(String collectionName, Map<String, Object> params, String id);

	/**
	 * 创建文档,如果创建失败则返回null
	 * @param collectionName
	 * @param dbObject
	 * @return
	 */
	public DBObject createDocument(String collectionName, DBObject dbObject);


	public void deleteDocumentById(String collectionName, String id);



	public DBObject findOneDocument(String collectionName, DBObject query);
	
	
	public DBCursor findDocument(String collectionName, DBObject query);

	/**
	 * 根据id更新document
	 * @param collectionName
	 * @param params
	 * @param id
	 * @return
	 */
	public int updateDocument(String collectionName, Map<String, Object> params, String id) ;
	
	public int updateDocument(String collectionName,DBObject dbObject, String id) ;
	
	public int updateDocument(String collectionName, DBObject dbObject, DBObject query) ;


	public MongoFileEntity findOneFile( String filePath, DBObject query);
	
}
