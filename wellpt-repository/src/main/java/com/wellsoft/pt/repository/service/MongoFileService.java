package com.wellsoft.pt.repository.service;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import com.mongodb.gridfs.GridFSDBFile;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.repository.entity.Folder;
import com.wellsoft.pt.repository.entity.LogicFileInfo;
import com.wellsoft.pt.repository.entity.mongo.file.MongoFileEntity;

/**
 * 文件操作
 * @author Hunt
 *
 */
/**
 * @author Administrator
 *
 */
public interface MongoFileService {

	/**
	 * 保存文件 
	 * @param fileName 不得为空
	 
	 * @param inputStream 不得为空
	 * @return 返回null表示保存失败 
	 */

	MongoFileEntity saveFile(String fileName, InputStream inputStream);

	/**
	 * 保存文件，文件ID由用户指定 ,使用该接口效率极为低下,因为需要先判断文件是否存在，如果存在则覆盖，如果不存在则新建
	 * @param fileID
	 * @param fileName
	 * @param inputStream
	 * @return
	 */
	MongoFileEntity saveFile(String fileID, String fileName, InputStream inputStream);

	/**
	 * 保存文件及签名
	 * @param fileName
	 
	 * @param inputStream
	 * @param digest_value
	 * @param digest_algorithm
	 * @param signature_value
	 * @param certificate
	 * @return 返回null表示保存失败
	 */

	public MongoFileEntity saveFile(String fileName, InputStream inputStream, String digest_value,
			String digest_algorithm, String signature_value, String certificate);

	/**
	 * 保存文件及签名, 文件ID由用户指定 ,使用该接口效率极为低下,因为需要先判断文件是否存在，如果存在则覆盖，如果不存在则新建
	 * @param fileID
	 * @param fileName
	 * @param inputStream
	 * @param digest_value
	 * @param digest_algorithm
	 * @param signature_value
	 * @param certificate
	 * @return 返回null表示保存失败
	 */
	public MongoFileEntity saveFile(String fileID, String fileName, InputStream inputStream, String digest_value,
			String digest_algorithm, String signature_value, String certificate);

	/**
	 * 更新签名
	 * @param fileId   not null
	 * @param digest_value   not null
	 * @param digest_algorithm   not null
	 * @param signature_value   not null
	 * @param certificate   not null 
	 */
	public void updateSignature(String fileId, String digest_value, String digest_algorithm, String signature_value,
			String certificate);

	/**
	 * 将文件列表push到文件夹中
	 * @param folderID
	 * @param fileIDs
	 * @param purpose 字段名字
	 */
	void pushFilesToFolder(String folderID, List<String> fileIDs, String purpose);

	/**
	 *  将文件push到文件夹中
	 * @param folderID
	 * @param fileID
	 * @param purpose 字段名字
	 */
	void pushFileToFolder(String folderID, String fileID, String purpose);

	/**
	 * 获取文件列表
	 * @param nodeName
	 * @param purpose 字段名字, 该参数为null时表示获取所有的文件
	 * @return
	 */
	public List<MongoFileEntity> getFilesFromFolder(String folderID, String purpose);

	/**
	 * 获取不包含IO信息的文件列表
	 * @param folderID
	 * @param purpose
	 * @return
	 */
	public List<LogicFileInfo> getNonioFilesFromFolder(String folderID, String purpose);

	/**
	 * 获取单个文件
	 * @param fileID
	 * @return 
	 */
	public MongoFileEntity getFile(String fileID);

	/**
	 * 将文件夹中的所有文件清空
	 * @param nodeName
	 */
	public void popAllFilesFromFolder(String folderID);

	/**
	 * 清除文件夹中的某个文件
	 * @param folderID
	 * @param fileID
	 */
	public void popFileFromFolder(String folderID, String fileID);

	/**
	 * 判断文件是否在文件夹中
	 * @param folderID
	 * @param fileID
	 * @return  存在返回true, 不存在返回false
	 */
	public boolean isFileInFolder(String folderID, String fileID);

	/**
	 * 该方法仅用于测试 
	 * @param tenantId
	 */
	public void setCurrentTenantId(String tenantId);

	/**
	 * 产生swf文件副本 
	 * @param fileID
	 */
	void createReplicaOfSWF(String fileID);

	/**
	 * 产生swf文件副本
	 * @param inputstream
	 * @param fileID
	 */
	/*void createReplicaOfSWF(InputStream inputstream, String fileID);*/

	/**
	 * 获取文件的swf副本文件,该副本文件没有逻辑信息
	 * @param fileID
	 * @throws FileNotFoundException 
	 */
	MongoFileEntity getReplicaOfSWF(String fileID) throws FileNotFoundException;

	/**
	 * 销毁swf副本文件
	 * @param fileID
	 */
	void destroyReplicaOfSWF(String fileID);

	/** 
	 * 销毁文件夹, 不可还原
	 * @param folderID
	 * @throws Exception 
	 */
	void destroyFolder(String folderID);

	/**
	 * 删除文件,转移至日志中,可恢复
	 * @param fileID
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 * @throws Exception 
	 */
	void deleteFile(String fileID) throws IntrospectionException, IllegalAccessException, InvocationTargetException;

	/**
	 * 销毁文件,如果文件被文件夹引用了，这时会抛出异常, 不可恢复
	 * @param fileID 
	 */
	void destroyFile(String fileID);

	/**
	 * 
	 * 创建唯一的folderID
	 * @return
	 */
	String createFolderID();

	/**
	 * 获取引用该文件的文件夹
	 * @param fileID
	 */
	List<Folder> getFoldersOfFile(String fileID);

	MongoFileEntity copyFile(String fileID) throws FileNotFoundException, IntrospectionException,
			IllegalAccessException, InvocationTargetException;

	/**
	 * 获取文件夹信息
	 * 
	 * @param folderID
	 * @return
	 */
	Folder getFolderByFolderID(String folderID);

	/**
	 * 获取文件夹最后的更新时间
	 * @param folderID
	 * @return
	 */
	Date getLastModifyTimeOfFolder(String folderID);

	/**
	 * 文件夹在指定的时间之后是否有更新
	 * @param folderID
	 * @param modifyTime
	 * @return
	 */
	boolean isFolerChanged(String folderID, Date modifyTime);

	/**
	 * 导出文件夹被更新过的信息,没有被数据被更新时，则返回null
	 * @param folderID
	 * @param modifyTime
	 * @return
	 * @throws FileNotFoundException 
	 */
	File exportChangeInfo(String folderID, Date modifyTime) throws FileNotFoundException;

	/**
	 * 导入文件夹被更新过的信息
	 * @param changeInfo
	 * @return
	 * @throws Exception 
	 */
	boolean importChangeInfo(File changeInfo) throws Exception;

	/**
	 * 文件夹是否存在 
	 * 
	 * @param folderID
	 * @return
	 */
	boolean isFolderExist(String folderID);

	/**
	 *判断文件是否存在于夹中 
	 * @param fileId
	 * @return
	 */
	boolean isFileInFolder(String fileId);

	
	
	
	/**
	 * 该方法请
	 */
	void testInOneTra();
	
	

	/**
	 * 分页查询文件
	 * 
	 * @return
	 */
	public List<QueryItem> getFilesByPage(int firstResult, int maxResults);
	
	/**
	 * 获取所有在mongo中的原文件
	 * @param synbeforedays 
	 * @return
	 */
	public List<GridFSDBFile> findProtoFiles(String synbeforedays);
	
	/**
	 * 从mongo中文件原文件
	 * @param physicalFileId
	 * @return
	 */
	public void destroyProtoFile(String physicalFileId);

	List<LogicFileInfo> getFilesByPhysicalFileId(String physicalFileId);
	
	List<LogicFileInfo> getFilesByPhysicalFileId(List<String> physicalFileId);
	
	public   MongoFileEntity findProtoFile(String physicalFileId) ;

}
