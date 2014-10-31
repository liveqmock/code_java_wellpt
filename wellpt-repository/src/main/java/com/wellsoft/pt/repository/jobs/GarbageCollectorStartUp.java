/*
 * @(#)2014-6-28 V1.0
 * 
 * Copyright 2014 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.repository.jobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;

import com.mongodb.gridfs.GridFSDBFile;
import com.wellsoft.pt.core.context.ApplicationContextHolder;
import com.wellsoft.pt.core.resource.Config;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.repository.entity.LogicFileInfo;
import com.wellsoft.pt.repository.service.MongoFileService;
import com.wellsoft.pt.task.job.Job;
import com.wellsoft.pt.task.job.JobData;

/**
 * Description: 垃圾文件回收任务
 *  
 * @author hunt
 * @date 2014-6-28
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-6-28.1	hunt		2014-6-28		Create
 * </pre>
 *
 */
public class GarbageCollectorStartUp extends Job {
	private static boolean locked;

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.task.job.Job#execute(org.quartz.JobExecutionContext, com.wellsoft.pt.task.job.JobData)
	 */
	@Override
	protected void execute(JobExecutionContext context, JobData jobData) {
		if (locked) {
			return;
		}
		try {
			locked = true;
			logger.info("mongo garbage collect startup");
			startup();
			logger.info("mongo garbage collect over");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			locked = false;
		}
	}

	final int maxResults = 100;
	int firstResult = 0;
	final MongoFileService mongoService = (MongoFileService) ApplicationContextHolder.getBean("mongoFileService");

	private void startup() {
		logger.info("------------>clean the file in repo_file but not in repo_file_in_folder  ");
		cleanGarbageKindOne();//clean the file in repo_file but not in repo_file_in_folder  
		logger.info("the file in repo_file but not in repo_file_in_folder has been cleaned");
		
		
		logger.info("------------>clean the file in mongo but not in repo_file");
		cleanGarbageKindTwo();//clean the file in mongo but not in repo_file
		logger.info("the file in mongo but not in repo_file has been cleaned");
		
	}

	private void cleanGarbageKindTwo() {
		String synbeforedays = Config.getValue("mongodb.server.gc.synbeforedays");
		 List<GridFSDBFile> fsdbFiles = this.mongoService.findProtoFiles(synbeforedays);
		 if(fsdbFiles == null || fsdbFiles.size() == 0){ 
			 return  ;
		 }
		 
		long uselesscount = 0l;
		long usefulcount = 0l;
		long uselessSize = 0l;
		long usefulSize = 0l;
		long totalcount = 0;
		List<String> physicalFileIds = new ArrayList<String>();
		Map<String, GridFSDBFile> physicalInfos = new HashMap<String, GridFSDBFile>();
		 for(GridFSDBFile fsdbFile:fsdbFiles){
			String physicalFileId = fsdbFile.getId().toString();
			physicalFileIds.add(physicalFileId);
			physicalInfos.put(physicalFileId, fsdbFile);
			totalcount ++;
			if(physicalFileIds.size() <= 60 && totalcount != fsdbFiles.size()){ 
				continue;
			}
			
			List<LogicFileInfo> logicFileInfos = this.mongoService.getFilesByPhysicalFileId(physicalFileIds);
			for(String physicalFileIdx:physicalFileIds){
				fsdbFile = physicalInfos.get(physicalFileIdx);
				boolean isUsed = isUsedOfPhysicalFile(physicalFileIdx, logicFileInfos);
				if(isUsed){
					usefulcount ++;
					usefulSize += fsdbFile.getLength();
					logger.info("used file:" + physicalFileIdx + "(" + fsdbFile.getFilename() + ") fulcount: "  + usefulcount + "space:" + usefulSize+ "  total:" + totalcount); 
				}else{
					this.mongoService.destroyProtoFile(fsdbFile.getId().toString()); 
					uselesscount ++;
					uselessSize += fsdbFile.getLength();
					logger.info("------->find a useless file:" + physicalFileIdx + "(" + fsdbFile.getFilename() + ") lesscount:" + uselesscount + " space:" + uselessSize + "  total:" + totalcount);
				}
			}
			
			
			
			physicalFileIds.clear();
			physicalInfos.clear();
		 }
		 logger.info("no useless file any more ^-^ it's cleaned count:" + uselesscount + "  size:" + uselessSize);
		
	}

	private void cleanGarbageKindOne() {
		List<QueryItem> fileItems = mongoService.getFilesByPage(firstResult, maxResults);
		if (fileItems == null || fileItems.size() == 0) {//如果已经全部扫描过一遍，则从头开始再扫描
			firstResult = 0;
			return;
		}

		doProcess(fileItems);
		
	}
	
	private boolean isUsedOfPhysicalFile(String physicalFileId, List<LogicFileInfo> logicFileInfos) {
		if(logicFileInfos == null || logicFileInfos.size() == 0){
			return false;
		}
		for(LogicFileInfo lfile: logicFileInfos){
			if(physicalFileId.equals(lfile.getPhysicalFileId())){
				return true;
			}
		}
		return false;
	 
		
	}

	private void doProcess(List<QueryItem> fileItems) {

		for (QueryItem item : fileItems) {
			firstResult++;
			String fileID = item.getString("uuid");

			if (mongoService.isFileInFolder(fileID)) {//文件如果在文件夹中则不处理

				continue;
			}
			logger.info("mongo garbage collect destroy file fileId: " + fileID);
			mongoService.destroyFile(fileID);

			//firstResult++;
		}
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
		}
		List<QueryItem> fileItemsNew = mongoService.getFilesByPage(firstResult, maxResults);
		if (fileItemsNew != null && fileItemsNew.size() > 0) {
			doProcess(fileItemsNew);
		}

	}
}
