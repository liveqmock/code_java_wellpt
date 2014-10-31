package com.wellsoft.pt.demo.service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.wellsoft.pt.bpm.engine.entity.TaskInstance;
import com.wellsoft.pt.core.service.BaseService;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.dyform.support.DyFormData;
import com.wellsoft.pt.workflow.work.bean.WorkBean;

/**
 * 
 * Description: 如何描述该类
 *  
 * @author wangbx
 * @date 2014-4-22
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-4-22.1	wangbx		2014-4-22		Create
 * </pre>
 *
 */
public interface DemoService extends BaseService {

	public List<QueryItem> queryQueryItemData(String string, Map<String, Object> queryParams, PagingInfo pagingInfo);

	public Long findCountByHql(String string, Map<String, Object> queryParams);

	public List<Map<Object, Object>> getShareData(String flowInstUuid);

	public List<TaskInstance> getTaskInstancesByFlowInstUuid(String parentFlowInstUuid);

	public InputStream print(WorkBean workBean);

	/**
	 * 
	 * 判断是否具有该按钮权限
	 * 
	 * @param code
	 * @return
	 */
	Boolean isGrant(String code);

	public void file2Folders() throws FileNotFoundException;

	/**
	 * 这是一个测试表单问题的demo,不可使用
	 * 
	 * @param dyFormData
	 * @return
	 */
	public String saveDyformdata(DyFormData dyFormData);

}
