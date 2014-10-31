package com.wellsoft.pt.repository.support;

import com.wellsoft.pt.core.resource.Config;

public class JcrConstants implements org.apache.jackrabbit.JcrConstants {
	public static final String FILE = "well:document";
	public static final String CONTENT = "well:content";
	public static final String CONTENT_TYPE = "well:resource";
	public static final String SIZE = "well:size";
	public static final String LANGUAGE = "well:language";
	public static final String AUTHOR = "well:author";
	public static final String VERSION_COMMENT = "well:versionComment";
	public static final String NAME = "well:name";
	public static final String NODE_TYPE = "well:note";
	public static final String NODE_LIST = "well:notes";
	public static final String NODE_LIST_TYPE = "well:notes";
	public static final String NODE_MIX_TYPE = "well:notes";
	public static final String NODE_DATE = "well:date";
	public static final String NODE_USER = "well:user";
	public static final String NODE_TEXT = "well:text";
	public static final String FOLDER_NODE_NAME = "well:foldernode";
	public static final String FOLDER_TYPE = "well:folder";
	public static final String FOLDER_AUTHOR = "well:author";
	public static final String FOLDER_NAME = "well:name";
	public static final String TEMP_NODE_NAME = "well:tempnode";
	// 判断是否提交文档标志
	public static final String FOLDER_SUBMIT = "well:submit";

	public static final String MULTI_FILE_SOURCE = "multi_file_source";

	public static final String FTL_TEMPLATE_PATH = Config.CLASS_DIR + "/" + "jcr";

	public static final String FTL_REPO_TEMPLATE_PATH = "repotemplate";
	public static final String FTL_FILE_REPO_TEMPLATE_PATH = "repofiletemplate";

}
