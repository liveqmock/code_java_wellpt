package com.wellsoft.pt.repository.dao;

import java.util.List;

import com.wellsoft.pt.repository.entity.FileInFolder;

public interface FileInFolderDao {

	boolean isFileInFolder(String fileID, String folderID);

	List<FileInFolder> getFilesFromFolder(String folderID, String purpose);

	/**
	 * 获取与该文件相关的记录
	 * @param fileID
	 * @return
	 */
	List<FileInFolder> getFoldersOfFile(String fileID);

}
