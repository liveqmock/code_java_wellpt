package com.wellsoft.pt.demo.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.basicdata.facade.BasicDataApiFacade;
import com.wellsoft.pt.bpm.engine.access.IdentityResolverStrategy;
import com.wellsoft.pt.bpm.engine.entity.FlowInstance;
import com.wellsoft.pt.bpm.engine.entity.TaskActivity;
import com.wellsoft.pt.bpm.engine.entity.TaskInstance;
import com.wellsoft.pt.bpm.engine.entity.TaskOperation;
import com.wellsoft.pt.bpm.engine.entity.TaskSubFlow;
import com.wellsoft.pt.bpm.engine.exception.WorkFlowException;
import com.wellsoft.pt.bpm.engine.service.FlowService;
import com.wellsoft.pt.bpm.engine.service.TaskService;
import com.wellsoft.pt.bpm.engine.support.WorkFlowOperation;
import com.wellsoft.pt.common.enums.Separator;
import com.wellsoft.pt.core.entity.IdEntity;
import com.wellsoft.pt.core.service.impl.BaseServiceImpl;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.demo.service.DemoService;
import com.wellsoft.pt.demo.support.DemoConstants;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.DyFormData;
import com.wellsoft.pt.dyform.support.DyFormDataUtils;
import com.wellsoft.pt.dytable.bean.FormAndDataBean;
import com.wellsoft.pt.dytable.facade.DytableApiFacade;
import com.wellsoft.pt.org.facade.OrgApiFacade;
import com.wellsoft.pt.repository.entity.mongo.file.MongoFileEntity;
import com.wellsoft.pt.repository.service.MongoFileService;
import com.wellsoft.pt.security.facade.SecurityApiFacade;
import com.wellsoft.pt.utils.date.DateUtils;
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
@Service
@Transactional
public class DemoServiceImpl extends BaseServiceImpl implements DemoService {

	@Autowired
	private TaskService taskService;

	@Autowired
	private OrgApiFacade orgApiFacade;

	@Autowired
	private BasicDataApiFacade basicDataApiFacade;

	@Autowired
	private FlowService flowService;

	@Autowired
	private DytableApiFacade dytableApiFacade;

	@Autowired
	private MongoFileService mongoFileService;

	@Autowired
	DyFormApiFacade dyFormApiFacade;

	@Override
	public List<QueryItem> queryQueryItemData(String hql, Map<String, Object> values, PagingInfo pagingInfo) {
		return this.dao.query(hql, values, QueryItem.class, pagingInfo);
	}

	@Override
	public Long findCountByHql(String hql, Map<String, Object> values) {
		return this.dao.findUniqueBy(Long.class, hql, values);
	}

	@Override
	public List<Map<Object, Object>> getShareData(String parentFlowInstUuid) {
		List<Map<Object, Object>> dataList = new ArrayList<Map<Object, Object>>(0);
		// 作为父流程实例UUID获取数据
		TaskSubFlow example = new TaskSubFlow();
		example.setParentFlowInstUuid(parentFlowInstUuid);
		List<TaskSubFlow> list = this.dao.findByExample(example);
		if (!list.isEmpty()) {
			for (TaskSubFlow taskSubFlow : list) {
				String subFlowInstUuid = taskSubFlow.getFlowInstUuid();
				FlowInstance subFlowInstance = flowService.getFlowInstance(subFlowInstUuid);
				if (subFlowInstance != null) {
					List<TaskInstance> subTaskInstances = this.getTaskInstancesByFlowInstUuid(parentFlowInstUuid);
					//					for (TaskInstance subTaskInstance : subTaskInstances) {
					// 获取子流程共享数据
					Map<Object, Object> data = getSubFlowShareData(taskSubFlow, subFlowInstance,
							(subTaskInstances == null || subTaskInstances.size() == 0) ? null : subTaskInstances.get(0));
					dataList.add(data);
					//					}
				}
			}
		}

		if (list.isEmpty()) {
			// 作为子流程实例UUID获取数据
			TaskSubFlow subFlow = new TaskSubFlow();
			subFlow.setFlowInstUuid(parentFlowInstUuid);
			List<TaskSubFlow> subFlows = this.dao.findByExample(subFlow);
			if (!subFlows.isEmpty()) {
				example = new TaskSubFlow();
				example.setParentFlowInstUuid(subFlows.get(0).getParentFlowInstUuid());
				list = this.dao.findByExample(example);

				if (!list.isEmpty()) {
					for (TaskSubFlow taskSubFlow : list) {
						// 子流程相等，忽略掉
						if (parentFlowInstUuid.equals(taskSubFlow.getFlowInstUuid())) {
							continue;
						}
						// 子流程不共享，忽略掉
						if (!Boolean.TRUE.equals(taskSubFlow.getIsShare())) {
							continue;
						}
						String subFlowInstUuid = taskSubFlow.getFlowInstUuid();
						FlowInstance subFlowInstance = flowService.getFlowInstance(subFlowInstUuid);
						if (subFlowInstance != null) {
							List<TaskInstance> subTaskInstances = this
									.getTaskInstancesByFlowInstUuid(parentFlowInstUuid);
							//							for (TaskInstance subTaskInstance : subTaskInstances) {
							// 获取子流程共享数据
							Map<Object, Object> data = getSubFlowShareData(taskSubFlow, subFlowInstance,
									(subTaskInstances == null || subTaskInstances.size() == 0) ? null
											: subTaskInstances.get(0));
							dataList.add(data);
							//							}
						}
					}
				}
			}
		}
		return dataList;
	}

	@Override
	public List<TaskInstance> getTaskInstancesByFlowInstUuid(String flowInstUuid) {
		String hql = "from TaskInstance t where t.flowInstance.uuid =:flowInstUuid and type=2 order by t.createTime desc";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("flowInstUuid", flowInstUuid);
		return this.dao.query(hql, values, TaskInstance.class);
	}

	private Map<Object, Object> getSubFlowShareData(TaskSubFlow taskSubFlow, FlowInstance subFlowInstance,
			TaskInstance subTaskInstance) {
		Map<Object, Object> data = new HashMap<Object, Object>();
		// 名称
		data.put("title", subFlowInstance.getTitle());
		String todoName = taskSubFlow.getTodoName();
		// 办理对象
		if (StringUtils.isBlank(todoName)) {
			data.put("todoUser", "");
		} else {
			data.put("todoUser", IdentityResolverStrategy.resolveAsNames(Arrays.asList(StringUtils.split(todoName,
					Separator.SEMICOLON.getValue()))));
		}
		// 办理意见
		data.put("opinion", getOpinions(subFlowInstance.getUuid()));

		// 办理状态
		data.put("currentTask", subTaskInstance == null && subFlowInstance.getEndTime() == null ? "草稿中"
				: (subTaskInstance == null ? "已结束" : subTaskInstance.getName()));
		// 当前在办
		//data.put("currentUser", getAssignee(subTaskInstance));

		return data;
	}

	private String getOpinions(String flowInstUuid) {
		List<TaskActivity> taskActivities = taskService.getTaskActivities(flowInstUuid);
		Map<String, List<TaskOperation>> operationMap = taskService.getOperationAsMap(flowInstUuid);
		StringBuilder sb = new StringBuilder();
		for (TaskActivity taskActivity : taskActivities) {
			// 流转信息
			String taskInstUuid = taskActivity.getTaskInstUuid();
			TaskInstance taskInstance = taskService.getTask(taskInstUuid);
			sb.append("<" + taskInstance.getName() + ">");
			sb.append(" ");
			sb.append(DateUtils.formatDateTime(taskActivity.getCreateTime()));
			sb.append(" ");
			sb.append(orgApiFacade.getUserById(taskActivity.getCreator()).getUserName());
			sb.append(Separator.LINE.getValue() + "<br>");

			// 操作信息
			List<TaskOperation> taskOperations = operationMap.get(taskInstUuid);
			if (taskOperations == null) {
				continue;
			}
			for (TaskOperation taskOperation : taskOperations) {
				sb.append(orgApiFacade.getUserById(taskOperation.getAssignee()).getUserName());
				sb.append(" ");
				sb.append(DateUtils.formatDateTime(taskOperation.getCreateTime()));
				sb.append(" ");
				sb.append(WorkFlowOperation.getName(taskOperation.getActionType()));
				if (StringUtils.isNotBlank(taskOperation.getOpinionText())) {
					sb.append(Separator.LINE.getValue() + "<br>");
					sb.append(" ");
					sb.append(taskOperation.getOpinionText());
				}
				sb.append(Separator.LINE.getValue() + "<br>");
			}
		}
		return sb.toString();
	}

	@Override
	public InputStream print(WorkBean workBean) {
		String printTemplateId = workBean.getPrintTemplateId();

		if (DemoConstants.PRINT_TEMPLATE_SWDCFKD.equals(printTemplateId)) {
			// 督查反馈单
			return printDCFKDReception(workBean);
		} else {
			throw new WorkFlowException("未知的打印模板[" + printTemplateId + "]");
		}

	}

	private InputStream printDCFKDReception(WorkBean workBean) {
		String templateId = workBean.getPrintTemplateId();
		String flowInstUuid = workBean.getFlowInstUuid();

		// 督查反馈单
		String formUuid = workBean.getFormUuid();
		FlowInstance flowInstance = flowService.getFlowInstance(flowInstUuid);
		String dataUuid = flowInstance.getDataUuid();
		FormAndDataBean dcfkdFormData = dytableApiFacade.getFormShowData(formUuid, dataUuid);

		Map<String, Object> dataMap = dcfkdFormData.getData();
		Iterator it = dataMap.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			Map<String, Object> dataMap1 = (Map<String, Object>) dataMap.get(key);
			Iterator it1 = dataMap1.keySet().iterator();
			while (it1.hasNext()) {
				String key1 = (String) it1.next();
				String value = dataMap1.get(key1).toString();
				if (key1.equals(DemoConstants.FORM_CHENGYUE)) {
					String[] vas = value.split(",");
					dataMap1.put(key1, vas[0]);
					break;
				}
			}
		}

		List<IdEntity> allEntities = new ArrayList<IdEntity>();
		Map<String, Object> dytableMap = new HashMap<String, Object>();

		List<Map<Object, Object>> dataList = this.getShareData(flowInstUuid);
		dytableMap.putAll(dcfkdFormData.getData());
		dytableMap.put(DemoConstants.SUB_DCFK, dataList);
		return basicDataApiFacade.getPrintResultAsInputStream(templateId, allEntities, dytableMap, null);
	}

	//	//获取子流程当前在办
	//	private String getAssignee(TaskInstance taskInstance) {
	//		StringBuilder sb = new StringBuilder();
	//		List<AclSid> aclSids = aclServiceWrapper.getSid(TaskInstance.class, taskInstance.getUuid(), AclPermission.TODO);
	//		Iterator<AclSid> it = aclSids.iterator();
	//		while (it.hasNext()) {
	//			String name = IdentityResolverStrategy.resolveAsName(it.next().getSid());
	//			if (name != null) {
	//				sb.append(name);
	//				if (it.hasNext()) {
	//					sb.append(Separator.SEMICOLON.getValue());
	//				}
	//			}
	//		}
	//		return sb.toString();
	//	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.app.xzsp.service.XZSPService#isGrant(java.lang.String)
	 */
	@Override
	public Boolean isGrant(String code) {
		return SecurityApiFacade.isGranted(code);
	}

	public void file2Folders() throws FileNotFoundException {
		String fileId = this.mongoFileService.createFolderID();
		File file1 = new File("c:/3.txt");
		MongoFileEntity fileEntitye = this.mongoFileService.saveFile(fileId, "test.txt", new FileInputStream(file1));
		String foderId = this.mongoFileService.createFolderID();
		String foderId2 = this.mongoFileService.createFolderID();
		this.mongoFileService.pushFileToFolder(foderId, fileId, "test");
		this.mongoFileService.pushFileToFolder(foderId2, fileId, "test");
	}

	/** 
	 * 这是一个测试表单问题的demo,不可使用
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.demo.service.DemoService#saveDyformdata(com.wellsoft.pt.dyform.support.DyFormData)
	 */
	@Override
	public String saveDyformdata(DyFormData dyFormData) {
		String folderId = "c64404018950000159fa124018d01668";
		DyFormData subDyFormdata = dyFormData.getDyFormData("7fa6bc6d-4fd7-4f38-9c4d-b18dff77bfd0", folderId);
		String fieldName = "DJFJ";
		Object attachObjs = subDyFormdata.getFieldValue(fieldName);
		subDyFormdata.setFieldValue(fieldName, new ArrayList());//将附件字段清空

		List<String> fieldIds = DyFormDataUtils.getFileIds(attachObjs);
		this.mongoFileService.pushFilesToFolder(folderId, fieldIds, fieldName);
		String result = this.dyFormApiFacade.saveFormData(dyFormData);
		return result;
	}

}
