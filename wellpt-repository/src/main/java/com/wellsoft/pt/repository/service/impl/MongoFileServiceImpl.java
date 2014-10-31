package com.wellsoft.pt.repository.service.impl;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.tools.ant.filters.StringInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.mongodb.gridfs.GridFSDBFile;
import com.wellsoft.pt.core.exception.WellException;
import com.wellsoft.pt.core.resource.Config;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dytable.utils.DynamicUtils;
import com.wellsoft.pt.repository.dao.FileDao;
import com.wellsoft.pt.repository.dao.FileInFolderDao;
import com.wellsoft.pt.repository.dao.FolderDao;
import com.wellsoft.pt.repository.dao.FolderOperateLogDao;
import com.wellsoft.pt.repository.dao.impl.FileDaoImpl;
import com.wellsoft.pt.repository.entity.FileEntity;
import com.wellsoft.pt.repository.entity.FileInFolder;
import com.wellsoft.pt.repository.entity.Folder;
import com.wellsoft.pt.repository.entity.FolderOperateLog;
import com.wellsoft.pt.repository.entity.LogicFileInfo;
import com.wellsoft.pt.repository.entity.mongo.file.MongoFileEntity;
import com.wellsoft.pt.repository.service.MongoFileService;
import com.wellsoft.pt.repository.support.FileUploadHandler;
import com.wellsoft.pt.repository.support.UnZip;
import com.wellsoft.pt.repository.support.enums.EnumOperateType;
import com.wellsoft.pt.repository.support.enums.EnumReplicaType;
import com.wellsoft.pt.repository.support.json.OracleEntityPropertyFilter;
import com.wellsoft.pt.utils.file.ZipUtils;

@Service
@Transactional
public class MongoFileServiceImpl implements MongoFileService {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	FileDao fileDao;

	@Autowired
	FolderDao folderDao;

	@Autowired
	FileInFolderDao fileInFolderDao;

	@Autowired
	FolderOperateLogDao folderOperateLogDao;

	@Override
	public MongoFileEntity saveFile(String fileName, InputStream inputStream) {
		//String mimeType = new MimetypesFileTypeMap().getContentType(fileName);
		MongoFileEntity fileEntity = null;

		fileEntity = this.saveFile(DynamicUtils.getRandomUUID(), fileName, inputStream);

		return fileEntity;
	}

	public MongoFileEntity saveFile(String fileID, String fileName, InputStream inputStream) {
		String mimeType = getMimeType(fileName);
		MongoFileEntity fileEntity = null;
		fileEntity = this.fileDao.saveFile(fileID, fileName, mimeType, inputStream);

		return fileEntity;
	}

	@SuppressWarnings("static-method")
	private String getMimeType(String fileName) {
		return new MimetypesFileTypeMap().getContentType(fileName);

	}

	@Override
	public MongoFileEntity saveFile(String fileName, InputStream inputStream, String digest_value,
			String digest_algorithm, String signature_value, String certificate) {

		MongoFileEntity fileEntity = null;

		fileEntity = this.saveFile(DynamicUtils.getRandomUUID(), fileName, inputStream, digest_value, digest_algorithm,
				signature_value, certificate);

		return fileEntity;
	}

	public MongoFileEntity saveFile(String fileID, String fileName, InputStream inputStream, String digest_value,
			String digest_algorithm, String signature_value, String certificate) {
		String mimeType = getMimeType(fileName);
		MongoFileEntity fileEntity = null;

		fileEntity = this.fileDao.saveFile(fileID, fileName, mimeType, inputStream, digest_value, digest_algorithm,
				signature_value, certificate);

		return fileEntity;
	}

	@Override
	public void updateSignature(String fileId, String digest_value, String digest_algorithm, String signature_value,
			String certificate) {
		this.fileDao.updateSignature(fileId, digest_value, digest_algorithm, signature_value, certificate);

	}

	@Override
	public void pushFilesToFolder(String folderID, List<String> fileIDs, String purpose) {
		purpose = (purpose == null || purpose.trim().length() == 0) ? "attach" : purpose;
		try {
			this.folderDao.pushFilesToFolder(folderID, fileIDs, purpose.toLowerCase());
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IntrospectionException e) {
			logger.error("exception was invoked when convert bean to Map:" + e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error("exception was invoked when convert bean to Map:" + e.getMessage(), e);
		} catch (InvocationTargetException e) {
			logger.error("exception was invoked when convert bean to Map:" + e.getMessage(), e);
		} catch (FileExistsException e) {
			logger.error(e.getMessage(), e);
		}

	}

	@Override
	public void pushFileToFolder(String folderID, String fileID, String purpose) {

		List<String> fileIDs = new ArrayList<String>();
		fileIDs.add(fileID);
		this.pushFilesToFolder(folderID, fileIDs, purpose);
	}

	@Override
	public List<MongoFileEntity> getFilesFromFolder(String folderID, String purpose) {
		Assert.notNull(folderID, "parameter[folderID]  is null");
		purpose = (purpose == null || purpose.trim().length() == 0 || purpose.equals("null")) ? null : purpose
				.toLowerCase();
		List<MongoFileEntity> fileEntities = null;
		try {
			fileEntities = this.folderDao.getFilesFromFolder(folderID, purpose);
		} catch (FileNotFoundException e) {
			logger.info(e.getMessage(), e);
		}

		return fileEntities;
	}

	@Override
	public List<LogicFileInfo> getNonioFilesFromFolder(String folderID, String purpose) {
		Assert.notNull(folderID, "parameter[folderID]  is null");
		List<LogicFileInfo> fileEntities = null;
		try {
			fileEntities = this.folderDao.getLogicFilesFromFolder(folderID,
					purpose == null ? null : purpose.toLowerCase());
		} catch (FileNotFoundException e) {
			logger.info(e.getMessage(), e);
		}

		return fileEntities;
	}

	@Override
	public MongoFileEntity getFile(String fileID) {
		try {
			return this.fileDao.getFile(fileID);
		} catch (FileNotFoundException e) {
			logger.info(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public void popAllFilesFromFolder(String folderID) {
		try {
			this.folderDao.deleteFolder(folderID);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (IntrospectionException e) {
			logger.error("exception was invoked when convert bean to Map:" + e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error("exception was invoked when convert bean to Map:" + e.getMessage(), e);
		} catch (InvocationTargetException e) {
			logger.error("exception was invoked when convert bean to Map:" + e.getMessage(), e);
		}

	}

	@Override
	public void popFileFromFolder(String folderID, String fileID) {
		try {
			this.folderDao.popFileFromFolder(folderID, fileID);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (IntrospectionException e) {
			logger.error("exception was invoked when convert bean to Map:" + e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error("exception was invoked when convert bean to Map:" + e.getMessage(), e);
		} catch (InvocationTargetException e) {
			logger.error("exception was invoked when convert bean to Map:" + e.getMessage(), e);
		}
	}

	@Override
	public void deleteFile(String fileID) throws IntrospectionException, IllegalAccessException,
			InvocationTargetException {
		//先判断文件有没有被文件夹引用 

		if (isFileInFolder(fileID)) {
			throw new WellException("can not delete this file[" + fileID
					+ "] , file is linked by folders, please pop first");
		}
		this.fileDao.deleteFile(fileID);

	}

	@Override
	public boolean isFileInFolder(String fileID) {
		List<FileInFolder> ships = this.fileInFolderDao.getFoldersOfFile(fileID);
		if (ships != null && ships.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public void destroyFile(String fileID) {
		//先判断文件有没有被文件夹引用  
		if (isFileInFolder(fileID)) {
			throw new WellException("can not destroy this file[" + fileID
					+ "] , file is linked by folders, please pop first");
		}
		this.fileDao.destroyFile(fileID);
	}

	@Override
	public void createReplicaOfSWF(String fileID) {
		MongoFileEntity dbFile = this.getFile(fileID);
		String fileName = dbFile.getFileName();
		if (fileName.toLowerCase().indexOf(".doc") > -1 || fileName.toLowerCase().indexOf(".docx") > -1
				|| fileName.toLowerCase().indexOf(".ppt") > -1 || fileName.toLowerCase().indexOf(".pptx") > -1
				|| fileName.toLowerCase().indexOf(".xls") > -1 || fileName.toLowerCase().indexOf(".xlsx") > -1
				|| fileName.toLowerCase().indexOf(".txt") > -1) {

			FileUploadHandler handler = new FileUploadHandler();

			FileEntity file = handler.test(dbFile.getInputstream(), fileName);

			if (file == null) {//该副本已存在
				return;
			}

			//产生副本
			InputStream swfInputstream = file.getFile(); //将副本保存到数据库

			//将副本附加到目标文件中
			try {
				//this.fileDao.appendReplicaToFile(EnumReplicaType.SWF/*副本类型*/, swfFile.getId(),fileID);

				this.fileDao.createReplicaFile(fileID, EnumReplicaType.SWF/*副本类型*/, swfInputstream);

			} catch (FileNotFoundException e) {
				logger.error(e.getMessage(), e);
			} finally {
				try {
					swfInputstream.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}

			//产生swf文件副本

		}

	}

	@Override
	public MongoFileEntity getReplicaOfSWF(String fileID) throws FileNotFoundException {
		return this.fileDao.getReplicaFile(fileID, EnumReplicaType.SWF);
	}

	@Override
	public void destroyReplicaOfSWF(String fileID) {
		try {
			this.fileDao.destroyReplicaFile(fileID, EnumReplicaType.SWF);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public boolean isFileInFolder(String folderID, String fileID) {
		if (folderID == null || folderID.trim().length() == 0) {
			return false;
		}

		if (fileID == null || fileID.trim().length() == 0) {
			return false;
		}

		return this.folderDao.isFileInFolder(folderID, fileID);
	}

	public FileDao getFileDao() {
		return fileDao;
	}

	public void setFileDao(FileDao fileDao) {
		this.fileDao = fileDao;
	}

	public FolderDao getFolderDao() {
		return folderDao;
	}

	public void setFolderDao(FolderDao folderDao) {
		this.folderDao = folderDao;
	}

	@Override
	public void setCurrentTenantId(String tenantId) {
		((FileDaoImpl) this.fileDao).setCurrentTenantId(tenantId);
		//((FolderDaoImpl)this.folderDao).setCurrentTenantId(tenantId); 
	}

	@Override
	public void destroyFolder(String folderID) {
		this.folderDao.destroyFolder(folderID);
	}

	@Override
	public String createFolderID() {
		return DynamicUtils.getRandomUUID();
	}

	@Override
	public List<Folder> getFoldersOfFile(String fileID) {
		List<FileInFolder> list = this.fileInFolderDao.getFoldersOfFile(fileID);
		List<Folder> folders = new ArrayList<Folder>();
		if (list != null) {
			for (FileInFolder file : list) {
				folders.add(file.getFolder());
			}
		}
		return folders;
	}

	@Override
	public MongoFileEntity copyFile(String fileID) throws FileNotFoundException, IntrospectionException,
			IllegalAccessException, InvocationTargetException {
		return fileDao.copyFile(fileID);
	}

	@Override
	public Folder getFolderByFolderID(String folderID) {
		try {
			return this.getFolderDao().getFolder(folderID);
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	@Override
	public Date getLastModifyTimeOfFolder(String folderID) {
		return this.folderOperateLogDao.getLastModifyTimeOfFolder(folderID);
	}

	@Override
	public File exportChangeInfo(String folderID, Date time) {
		if (!this.isFolderExist(folderID)) {
			logger.info("folderID[" + folderID + "] is not exist");
			return null;
		}
		Date lastModifyTime = this.getLastModifyTimeOfFolder(folderID);
		if (lastModifyTime == null || time.after(lastModifyTime)) {
			logger.info("no any change after [" + time + "] in folder[" + folderID + "]");
			return null;
		}
		String folderName = folderID + "_" + time.getTime();
		String exportDir = appDataDir + folderName;

		File dir = new File(exportDir);
		dir.mkdirs();
		JSONObject exportData = new JSONObject();

		exportData.put("foderID", folderID);
		List<FolderOperateLog> operateLogs = folderOperateLogDao.getLogsAfterTime(folderID, time);
		JSONArray logJSONArray = new JSONArray();
		JSONArray fileJSONArray = new JSONArray();
		for (FolderOperateLog log : operateLogs) {
			JSONObject logJson = JSONObject.fromObject(log, jsonConfig);
			System.out.println(logJson.toString());
			logJSONArray.add(logJson);
			System.out.println(logJSONArray.toString());
			EnumOperateType type = EnumOperateType.type2EnumObj(log.getOperateType());
			if (type == EnumOperateType.PUSH) {
				String fileJson = log.getFileJson().trim();
				JSONArray files = JSONArray.fromObject(fileJson);
				for (int j = 0; j < files.size(); j++) {
					String fileUuid = files.getJSONObject(j).getString("fileUuid");
					MongoFileEntity fileEntity = null;
					MongoFileEntity swffileEntity = null;
					try {
						LogicFileInfo lFile = this.fileDao.getLogicFileInfo(fileUuid);
						fileJSONArray.add(JSONObject.fromObject(lFile));
						fileEntity = this.fileDao.getFile(fileUuid);
						swffileEntity = this.getReplicaOfSWF(fileUuid);
					} catch (FileNotFoundException e1) {
						continue;
					}
					File destFolder = new File(dir.getAbsolutePath() + pathSeparator);
					destFolder.mkdir();
					writeToDir(destFolder, fileEntity, swffileEntity);

				}
			}
		}
		exportData.put("logs", logJSONArray);
		exportData.put("files", fileJSONArray);
		File destfile = new File(dir, folderJSONFileName);
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			is = new StringInputStream(exportData.toString(), "utf-8");
			destfile.createNewFile();
			fos = new FileOutputStream(destfile);
			IOUtils.copyLarge(is, fos);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (fos != null) {
				IOUtils.closeQuietly(fos);
			}
			if (is != null) {
				IOUtils.closeQuietly(is);
			}
		}
		String zipFileName = appDataDir + folderName + ".zip";
		try {
			ZipUtils.zipFolder(exportDir, zipFileName);
			FileUtils.deleteDirectory(dir);

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new File(zipFileName);

	}

	private void writeToDir(File destFolder, MongoFileEntity fileEntity, MongoFileEntity swffileEntity) {
		File file = new File(destFolder, fileEntity.getPhysicalID());
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			is = fileEntity.getInputstream();
			file.createNewFile();
			fos = new FileOutputStream(file);
			IOUtils.copyLarge(is, fos);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (fos != null) {
				IOUtils.closeQuietly(fos);
			}
			if (is != null) {
				IOUtils.closeQuietly(is);
			}

		}
		if (swffileEntity == null) {
			return;
		}
		File swffile = new File(destFolder, swffileEntity.getPhysicalID());
		try {
			is = swffileEntity.getInputstream();
			swffile.createNewFile();
			fos = new FileOutputStream(swffile);
			IOUtils.copyLarge(is, fos);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (fos != null) {
				IOUtils.closeQuietly(fos);
			}
			if (is != null) {
				IOUtils.closeQuietly(is);
			}

		}

	}

	private final String folderJSONFileName = "folderJSON";
	public final static String pathSeparator = File.separator;
	public final static String appDataDir = Config.APP_DATA_DIR + pathSeparator + "mongofilesyn" + pathSeparator
			+ "export" + pathSeparator;
	JsonConfig jsonConfig = new JsonConfig();
	{
		jsonConfig.setJsonPropertyFilter(new OracleEntityPropertyFilter());
	}

	@Override
	public boolean importChangeInfo(File zipFile) throws IOException {
		String folderName = DynamicUtils.getRandomUUID();
		String exportDir = appDataDir + folderName;
		File dir = new File(exportDir);
		dir.mkdirs();
		UnZip.depress(zipFile.getAbsolutePath(), exportDir);
		File folderJSONFile = getFolderJSONFile(dir);
		if (folderJSONFile == null) {
			logger.warn("cann't find any valid file");
			return false;
		}

		FileReader reader = new FileReader(folderJSONFile);
		int fileLen = (int) folderJSONFile.length();
		char[] chars = new char[fileLen];
		reader.read(chars);
		reader.close();

		try {
			String json = String.valueOf(chars);
			ImExJson imexJson = new ImExJson(json);
			Folder folder = this.getFolderByFolderID(imexJson.folderID);
			if (folder == null) {
				folder = new Folder();
				folder.setUuid(imexJson.folderID);
				folder.doBindCreateTimeAsNow();
				folder.doBindCreatorAsCurrentUser();
				folder.doBindModifierAsCurrentUser();
				folder.doBindModifyTimeAsNow();
			}
			List<FolderOperateLog> logs = imexJson.logs;
			for (FolderOperateLog log : logs) {
				if (EnumOperateType.POP == EnumOperateType.type2EnumObj(log.getOperateType())) {
					//pop文件
					this.popFileFromFolder(folder, log);
				} else {
					//push文件
					this.pushFileToFolder(folder, dir, log, imexJson);
				}
			}
			this.folderDao.save(folder);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}

		return true;
	}

	private void pushFileToFolder(Folder folder, File dir, FolderOperateLog log, ImExJson imexJson) {
		System.out.println(log.getFileJson());
		String fileJson = log.getFileJson().trim();
		JSONArray files = JSONArray.fromObject(fileJson);
		for (int j = 0; j < files.size(); j++) {
			FileInFolder fileFolder = (FileInFolder) JSONObject.toBean(files.getJSONObject(j), FileInFolder.class);

			fileFolder.setUuid(null);
			folder.getFiles().add(fileFolder);
			String fileUuid = fileFolder.getFileUuid();
			//			if (this.fileInFolderDao.isFileInFolder(fileUuid, folder.getUuid())) {
			//				continue;
			//			}
			if (this.isLogicFileInfoExist(fileUuid)) {
				continue;
			}
			LogicFileInfo logicFileInfo = imexJson.getLogicFileInfo(fileUuid);
			File file = getFileFormZip(logicFileInfo.getPhysicalFileId(), dir);
			try {
				if (file == null)
					continue;
				System.out.println(fileUuid);
				this.fileDao.saveFile(logicFileInfo);
				this.fileDao.savePhysicalFile(null, logicFileInfo.getPhysicalFileId(), logicFileInfo.getFileName(),
						logicFileInfo.getContentType(), new FileInputStream(file));

			} catch (FileNotFoundException e) {
				logger.error(e.getMessage(), e);
			}

			File swffile = getFileFormZip(logicFileInfo.getSwfUuid(), dir);
			if (swffile == null) {
				continue;
			}
			try {
				this.fileDao.savePhysicalFile(null, logicFileInfo.getSwfUuid(), logicFileInfo.getFileName(),
						logicFileInfo.getContentType(), new FileInputStream(swffile));
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage(), e);
			}
		}

		this.addLogs4Folder(folder, log);

	}

	private void addLogs4Folder(Folder folder, FolderOperateLog log) {
		if (this.isLogExist(log)) {
			return;
		}
		folder.getLogs().add(log);

	}

	private boolean isLogExist(FolderOperateLog log) {

		return this.folderOperateLogDao.getLogByUuid(log.getUuid()) == null ? false : true;
	}

	private boolean isLogicFileInfoExist(String fileUuid) {

		return this.getFile(fileUuid) == null ? false : true;
	}

	private File getFileFormZip(String fileUuid, File dir) {
		if (dir.isDirectory()) {
			for (File file : dir.listFiles()) {
				File destFile = getFileFormZip(fileUuid, file);
				if (destFile == null) {
					continue;
				} else {
					return destFile;
				}
			}
		} else {
			if (dir.getName().equals(fileUuid)) {
				return dir;
			}
		}
		return null;
	}

	@SuppressWarnings("static-method")
	private void popFileFromFolder(Folder folder, FolderOperateLog log) {
		JSONArray files = JSONArray.fromObject(log.getFileJson());
		for (int j = 0; j < files.size(); j++) {
			String fileUuid = files.getJSONObject(j).getString("fileUuid");
			Iterator<FileInFolder> it = folder.getFiles().iterator();
			while (it.hasNext()) {
				FileInFolder file = it.next();
				if (file.getFileUuid().equals(fileUuid)) {
					it.remove();
				}
			}
		}
		this.addLogs4Folder(folder, log);
	}

	@Override
	public boolean isFolderExist(String folderID) {
		return this.getFolderByFolderID(folderID) == null ? false : true;
	}

	private File getFolderJSONFile(File dir) {
		if (dir.isDirectory()) {
			for (File file : dir.listFiles()) {
				File destFile = getFolderJSONFile(file);
				if (destFile == null) {
					continue;
				} else {
					return destFile;
				}
			}
		} else {
			if (dir.getName().equals(folderJSONFileName)) {
				return dir;
			}
		}
		return null;
	}

	@Override
	public boolean isFolerChanged(String folderID, Date time) {
		List<FolderOperateLog> fileInfos = this.folderOperateLogDao.getLogsAfterTime(folderID, time);
		if (fileInfos == null || fileInfos.size() == 0) {
			return false;
		} else {
			return true;
		}

	}

	public static void main(String[] args) throws Exception {
		//ZipUtils.zipFolder("E:\\temp", "F:\\temp.zip");
		//ZipUtils.decompress("F:\\temp.zip");
		/*File filedir = new File("F:\\flash");
		for (File file : filedir.listFiles()) {
			System.out.println(file.getAbsolutePath());
		}
		*/

		JSONObject o = JSONObject.fromObject("{\"id\":\"system.out.println\"}");
		System.out.println(o.getString("id"));
	}

	class ImExJson {
		private JSONObject json = null;
		public String folderID = null;
		public List<FolderOperateLog> logs = new ArrayList<FolderOperateLog>();
		public List<LogicFileInfo> files = new ArrayList<LogicFileInfo>();

		public ImExJson(String jsonData) {
			System.out.println(jsonData);
			json = JSONObject.fromObject(jsonData);
			try {
				folderID = json.getString("foderID");
			} catch (Exception e) {
				e.printStackTrace();
			}

			JSONArray logJSONArray = json.getJSONArray("logs");
			//Map<String, Class> classMap = new HashMap<String, Class>();
			//classMap.put("fileJson", String.class);
			for (int i = 0; i < logJSONArray.size(); i++) {
				JSONObject logJSON = logJSONArray.getJSONObject(i);
				JSONArray fileJson = logJSON.getJSONArray("fileJson");
				//logJSON.put("fileJson", "\"" + fileJson.toString() + "\"");
				logJSON.put("fileJson", fileJson.toString() + "  ");
				System.out.println(logJSON.toString());
				FolderOperateLog log = (FolderOperateLog) JSONObject.toBean(logJSON, FolderOperateLog.class);
				logs.add(log);
			}

			JSONArray fileJSONArray = json.getJSONArray("files");
			for (int i = 0; i < fileJSONArray.size(); i++) {
				JSONObject fileJSON = fileJSONArray.getJSONObject(i);

				//classMap.put("logs", FolderOperateLog.class);
				LogicFileInfo fileInfo = (LogicFileInfo) JSONObject.toBean(fileJSON, LogicFileInfo.class);
				files.add(fileInfo);
			}

		}

		public LogicFileInfo getLogicFileInfo(String fileUuid) {
			for (LogicFileInfo fileInfo : files) {
				if (fileInfo.getUuid().equals(fileUuid)) {
					return fileInfo;
				}
			}
			return null;
		}
	}

	@Override
	public void testInOneTra() {
		try {
			File file = new File("c:/3.txt");

			FileInputStream is = new FileInputStream(file);
			MongoFileEntity mongofile = this.saveFile(file.getName(), is);
			List<String> fileids = new ArrayList<String>();
			fileids.add(mongofile.getFileID());
			this.pushFilesToFolder(DyFormApiFacade.createUuid(), fileids, "test");
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public List<QueryItem> getFilesByPage(int firstResult, int maxResults) {
		return this.fileDao.getFilesByPage(firstResult, maxResults);

	}

	@Override
	public List<GridFSDBFile> findProtoFiles(String synbeforedays) {
		
		return  this.fileDao.findProtoFiles(synbeforedays);
	}
	

	@Override
	public   MongoFileEntity findProtoFile(String physicalFileId) {
		
		return  this.fileDao.findProtoFile(physicalFileId);
	}


	@Override
	public void destroyProtoFile(String physicalFileId) {
		 
		  this.fileDao.destroyPhysicalFile( physicalFileId);
	}

	@Override
	public List<LogicFileInfo> getFilesByPhysicalFileId(String physicalFileId) {
		 List<LogicFileInfo> files = this.fileDao.getLogicFileInfoByPhysicalFileId(physicalFileId);
		return files;
	}

	@Override
	public List<LogicFileInfo> getFilesByPhysicalFileId(
			List<String> physicalFileIds) {
		return this.fileDao.getLogicFileInfoByPhysicalFileId(physicalFileIds);
	}
}