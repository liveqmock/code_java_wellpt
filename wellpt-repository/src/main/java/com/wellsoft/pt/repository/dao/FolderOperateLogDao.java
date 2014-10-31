package com.wellsoft.pt.repository.dao;

import java.util.Date;
import java.util.List;

import com.wellsoft.pt.repository.entity.FolderOperateLog;

public interface FolderOperateLogDao {
	List<FolderOperateLog> getLogsAfterTime(String folderID, Date date);

	public Date getLastModifyTimeOfFolder(String folderID);

	FolderOperateLog getLogByUuid(String uuid);
}
