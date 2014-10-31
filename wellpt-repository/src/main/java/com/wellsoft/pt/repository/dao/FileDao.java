package com.wellsoft.pt.repository.dao;

import java.beans.IntrospectionException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.mongodb.gridfs.GridFSDBFile;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.repository.entity.LogicFileInfo;
import com.wellsoft.pt.repository.entity.mongo.file.MongoFileEntity;
import com.wellsoft.pt.repository.support.enums.EnumReplicaType;

public interface FileDao {

	/**
	 * 保存文件
	 * @param fileName 不得为空
	 * @param contentType 不得为空
	 * @param inputStream 不得为空
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 */

	MongoFileEntity saveFile(String fileName, String contentType, InputStream inputStream);

	/**
	 * 保存文件 
	 * @param fileName 不得为空
	 * @param contentType 不得为空
	 * @param inputFile 不得为空
	 * @return
	 * @throws FileNotFoundException
	 */
	//FileEntity saveFile(String fileName,String contentType,File inputFile) throws FileNotFoundException;

	/**
	 * 保存文件及签名
	 * @param fileName  不得为空
	 * @param contentType  不得为空
	 * @param inputStream  不得为空
	 * @param digest_value   
	 * @param digest_algorithm   
	 * @param signature_value  
	 * @param certificate   
	 * @param puspose 文件用途
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */

	MongoFileEntity saveFile(String fileName, String contentType, InputStream inputStream, String digest_value,
			String digest_algorithm, String signature_value, String certificate);

	/**
	 * 保存文件及签名
	 * @param fileID 文件ID为用户指定  
	 * @param fileName  不得为空
	 * @param contentType  不得为空
	 * @param inputStream  不得为空
	 * @param digest_value
	 * @param digest_algorithm
	 * @param signature_value
	 * @param certificate
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 */
	public MongoFileEntity saveFile(String fileID, String fileName, String contentType, InputStream inputStream,
			String digest_value, String digest_algorithm, String signature_value, String certificate);

	/**
	 * 保存文件 
	 * @param fileName  不得为空
	 * @param contentType  不得为空
	 * @param inputFile  不得为空
	 * @param digest_value   
	 * @param digest_algorithm   
	 * @param signature_value  
	 * @param certificate 
	 * @param puspose 文件用途  
	 * @return
	 * @throws FileNotFoundException
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	/*
	FileEntity saveFile(String fileName,
		String contentType,
		File inputFile, 
		String digest_value, 
		String digest_algorithm, 
		String signature_value, 
		String certificate 
		) throws FileNotFoundException, IntrospectionException, IllegalAccessException, InvocationTargetException;
	*/

	/**
	 * 根据fileID获取文件
	 * @param fileID 文件Id, 不得为空
	 * @return
	 * @throws FileNotFoundException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 */
	MongoFileEntity getFile(String fileID) throws FileNotFoundException;

	/**
	 * 获取文件的逻辑信息
	 * @param fileID
	 * @return
	 * @throws FileNotFoundException
	 */
	public LogicFileInfo getLogicFileInfo(String fileID) throws FileNotFoundException;

	/**
	 * 从文件夹集中删除某个文件夹，即该文件不再被这个文件夹引用
	 * @param fileID
	 * @param folderID
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	@Deprecated
	void popLinkFolder(String fileID, String folderID) throws IntrospectionException, IllegalAccessException,
			InvocationTargetException, FileNotFoundException, IOException;

	/**
	 * 将文件夹添加天文件夹集中，即该文件被这个文件夹所引用
	 * @param fileID
	 * @param folderID
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 * @throws FileNotFoundException 
	 */
	void pushLinkFolder(String fileID, String folderID) throws IntrospectionException, IllegalAccessException,
			InvocationTargetException, FileNotFoundException;

	/**
	 * 更新签名
	 * @param fileId
	 * @param digest_value  not null
	 * @param digest_algorithm not null
	 * @param signature_value not null
	 * @param certificate not null
	 */
	public void updateSignature(String fileId, String digest_value, String digest_algorithm, String signature_value,
			String certificate);

	/**
	 * 根据文件fileID,复制并产生副本，将副本返回
	 * @param fileID
	 * @return 返回副本
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws FileNotFoundException 
	 */
	MongoFileEntity copyFile(String fileID) throws IntrospectionException, IllegalAccessException,
			InvocationTargetException, FileNotFoundException;

	/**
	 * 判断文件是否存在
	 * @param fileID
	 * @return
	 */
	boolean isExistFile(String fileID);

	/**
	 * 根据文件ID删除文件,只删除文件的逻辑信息,不删除文件的物理信息,物理信息由后台任务去删除,删除的信息保存在日志中,删除的信息可恢复
	 * @param fileID 文件 ID, 不得为空
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	void deleteFile(String fileID) throws IntrospectionException, IllegalAccessException, InvocationTargetException;

	/**
	 * 根据文件ID删除文件,只删除文件的逻辑信息,不删除文件的物理信息,物理信息由后台任务去删除,删除的信息不可恢复
	 * @param fileID 文件 ID, 不得为空
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	void destroyFile(String fileID);

	/**
	 * 从被删除文件表中还原文件
	 * @param fileID
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 * @throws FileNotFoundException 
	 */
	MongoFileEntity recoveryFile(String fileID) throws IntrospectionException, IllegalAccessException,
			InvocationTargetException, FileNotFoundException;

	/**
	 * 获取副本文件，该文件没有逻辑信息
	 * @param fileID
	 * @param type
	 * @return
	 * @throws FileNotFoundException 
	 */
	MongoFileEntity getReplicaFile(String hostedFileID, EnumReplicaType type) throws FileNotFoundException;

	/**
	 * 销毁
	 * @param fileID
	 * @param type
	 * @throws FileNotFoundException 
	 */
	void destroyReplicaFile(String hostedFileID, EnumReplicaType type) throws FileNotFoundException;

	/**
	 * push文件副本至主文件
	 * @param fileID
	 * @param swf
	 * @param swfInputstream
	 * @throws FileNotFoundException 
	 */
	void createReplicaFile(String hostedFileID, EnumReplicaType type, InputStream inputstream)
			throws FileNotFoundException;

	MongoFileEntity saveFile(String fileID, String fileName, String mimeType, InputStream inputStream);

	public MongoFileEntity savePhysicalFile(String tblName, String fileID, String fileName, String contentType,
			InputStream inputStream);

	void saveFile(LogicFileInfo logicFileInfo);

	List<QueryItem> getFilesByPage(int firstResult, int maxResults);
	
	List<GridFSDBFile> findProtoFiles(String synbeforedays);

	void destroyPhysicalFile( String physicalFileId);

	List<LogicFileInfo> getLogicFileInfoByPhysicalFileId(String physicalFileId);
	
	List<LogicFileInfo> getLogicFileInfoByPhysicalFileId(List<String> physicalFileIds);
	
	public MongoFileEntity findProtoFile(String physicalFileId) ;

}
