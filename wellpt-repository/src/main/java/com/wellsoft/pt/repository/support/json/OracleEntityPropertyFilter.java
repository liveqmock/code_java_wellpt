package com.wellsoft.pt.repository.support.json;

import net.sf.json.util.PropertyFilter;

import com.wellsoft.pt.repository.entity.FileInFolder;
import com.wellsoft.pt.repository.entity.Folder;
import com.wellsoft.pt.repository.entity.FolderOperateLog;

public class OracleEntityPropertyFilter implements PropertyFilter {

	@Override
	public boolean apply(Object source, String propertyName, Object value) {
		if (source instanceof Folder) {
			if (propertyName.equals("files") || propertyName.equals("logs")) {
				return true;
			}
		}
		if (source instanceof FileInFolder) {
			if (propertyName.equals("folder")) {
				return true;
			}
		}
		if (source instanceof FolderOperateLog) {
			if (propertyName.equals("folder")) {
				return true;
			}
		}
		return false;
	}

}
