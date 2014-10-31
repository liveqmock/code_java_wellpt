package com.wellsoft.pt.repository.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wellsoft.pt.core.dao.hibernate.HibernateDao;
import com.wellsoft.pt.repository.dao.FileInFolderDao;
import com.wellsoft.pt.repository.entity.FileInFolder;

@Repository
public class FileInFolderDaoImpl extends HibernateDao<FileInFolder, String> implements FileInFolderDao {

	@Override
	public boolean isFileInFolder(String fileID, String folderID) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("folderUuid", folderID);
		values.put("fileUuid", fileID);

		List<FileInFolder> fileInFolders = this.find(
				"FROM FileInFolder O WHERE O.fileUuid = :fileUuid AND O.folder.uuid = :folderUuid ", values);

		return (fileInFolders != null && fileInFolders.size() > 0);
	}

	@Override
	public List<FileInFolder> getFilesFromFolder(String folderID, String purpose) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("folderUuid", folderID);
		if (purpose == null || purpose.trim().length() == 0) {
			return this.find("FROM FileInFolder O WHERE O.folder.uuid = :folderUuid ", values);
		} else {
			values.put("purpose", purpose);
			return this.find("FROM FileInFolder O WHERE O.purpose = :purpose AND O.folder.uuid = :folderUuid ", values);
		}
	}

	@Override
	public List<FileInFolder> getFoldersOfFile(String fileID) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("fileID", fileID);
		return this.find("FROM FileInFolder O WHERE O.fileUuid = :fileID ", values);
	}

}
