package com.wellsoft.pt.ldx.customdyform.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.wellsoft.pt.core.support.QueryItem;


/**
 * Description: 日程接口�?
 *  
 * @author wuzq
 * @date 2013-3-1
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版�?修改�?	修改日期			修改内容
 * 2013-3-1	wuzq		2013-2-7		Create
 * </pre>
 *
 */
public interface SpecialDyformService {
	
	public List<QueryItem> dataSourceQuery(String dataSourceId,String sql);

	public void saveDataToForm(String formUuid, String dataUuid);

	public List<QueryItem> formDataQuery(String formId, String columns, String condition);

	public List<QueryItem> formDataQueryAll(String formId, String columns, String condition,String childFormId,String childColumns) throws Exception;
	
	public File standbyExport(String paramString, String dataUuid);

	public List<Map<String, Object>> standbyImport(InputStream is, String sheetName) throws IOException;
	
	public void addAccount(String formUuid,String dataUuid);
}
