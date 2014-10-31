package com.wellsoft.pt.demo.web;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wellsoft.pt.basicdata.datasource.entity.DataSourceColumn;
import com.wellsoft.pt.basicdata.datasource.facade.DataSourceApiFacade;
import com.wellsoft.pt.basicdata.dyview.provider.ViewColumnType;
import com.wellsoft.pt.bpm.engine.entity.FlowInstance;
import com.wellsoft.pt.bpm.engine.entity.TaskInstance;
import com.wellsoft.pt.bpm.engine.service.FlowService;
import com.wellsoft.pt.bpm.engine.service.TaskService;
import com.wellsoft.pt.common.enums.JsonDataErrorCode;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.core.web.FaultMessage;
import com.wellsoft.pt.core.web.ResultMessage;
import com.wellsoft.pt.demo.service.DemoService;
import com.wellsoft.pt.demo.support.DemoConstants;
import com.wellsoft.pt.dyform.entity.DyFormDefinition;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.DictMap;
import com.wellsoft.pt.dyform.support.DyFormData;
import com.wellsoft.pt.dyform.support.SubformDefinition;
import com.wellsoft.pt.dytable.bean.FormAndDataBean;
import com.wellsoft.pt.dytable.facade.DytableApiFacade;
import com.wellsoft.pt.file.entity.FmFile;
import com.wellsoft.pt.file.facade.FileManagerApiFacade;
import com.wellsoft.pt.repository.entity.mongo.file.MongoFileEntity;
import com.wellsoft.pt.repository.service.MongoFileService;
import com.wellsoft.pt.repository.service.impl.MongoFileServiceImpl;
import com.wellsoft.pt.utils.encode.JsonBinder;
import com.wellsoft.pt.workflow.work.bean.WorkBean;
import com.wellsoft.pt.workflow.work.service.WorkService;

/**
 * 
 * Description: 收发文控制层
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
@Controller
@RequestMapping("/demo")
public class DemoController extends BaseController {

	@Autowired
	private WorkService workService;

	@Autowired
	private FileManagerApiFacade fileManagerApiFacade;

	@Autowired
	private DytableApiFacade dytableApiFacade;

	@Autowired
	private TaskService taskService;

	@Autowired
	private FlowService flowService;

	@Autowired
	private MongoFileService mongoFileService;

	@Autowired
	private DemoService demoService;

	/**
	 * 
	 * 发起新流程
	 * 
	 * @param flowDefUuid
	 * @param model
	 * @return
	 */
	@RequestMapping("/flowDefUuid/new")
	public String newDemoByFlowDefUuid(@RequestParam(value = "flowDefUuid") String flowDefUuid,
			@RequestParam(value = "scriptUrl", required = false) String scriptUrl,
			@RequestParam(value = "rtscriptUrl", required = false) String rtscriptUrl, Model model) {
		WorkBean workBean = workService.newWork(flowDefUuid);
		if (StringUtils.isNotBlank(scriptUrl)) {
			workBean.addExtraParam(DemoConstants.CUSTOM_SCRIPT_URL, scriptUrl);
		}
		if (StringUtils.isNotBlank(rtscriptUrl)) {
			workBean.addExtraParam(DemoConstants.CUSTOM_RT_SCRIPT_URL, rtscriptUrl);
		}
		model.addAttribute(workBean);
		return forward("/workflow/work/work_view");
	}

	/**
	 * 
	 * 文件库保存后跳转新流程，并填充相关数据
	 * 
	 * @param flowDefUuid
	 * @param fileUuid
	 * @param model
	 * @return
	 */
	@RequestMapping("/flowDefUuid/newfill")
	public String newFillDemoByFlowDefUuid(@RequestParam(value = "flowDefUuid") String flowDefUuid,
			@RequestParam(value = "fileUuid") String fileUuid,
			@RequestParam(value = "scriptUrl", required = false) String scriptUrl,
			@RequestParam(value = "rtscriptUrl", required = false) String rtscriptUrl, Model model) {
		WorkBean workBean = workService.newWork(flowDefUuid);
		FmFile file = fileManagerApiFacade.getFmFileByUuid(fileUuid);

		if (StringUtils.isNotBlank(scriptUrl)) {
			workBean.addExtraParam(DemoConstants.CUSTOM_SCRIPT_URL, scriptUrl);
		}
		if (StringUtils.isNotBlank(rtscriptUrl)) {
			workBean.addExtraParam(DemoConstants.CUSTOM_RT_SCRIPT_URL, rtscriptUrl);
		}
		model.addAttribute(workBean);
		return forward("/workflow/work/work_view");
	}

	/**
	 * 流程提交后跳转新流程
	 * 
	 * @param flowDefUuid
	 * @param taskUuid
	 * @param scriptUrl
	 * @param model
	 * @return
	 */
	@RequestMapping("/taskUuid/newfill")
	public String newFillDemoByTaskUuid(@RequestParam(value = "flowDefUuid") String flowDefUuid,
			@RequestParam(value = "taskUuid") String taskUuid,
			@RequestParam(value = "scriptUrl", required = false) String scriptUrl,
			@RequestParam(value = "rtscriptUrl", required = false) String rtscriptUrl, Model model) {
		WorkBean workBean = workService.newWork(flowDefUuid);
		TaskInstance taskInstance = taskService.get(taskUuid);

		if (StringUtils.isNotBlank(scriptUrl)) {
			workBean.addExtraParam(DemoConstants.CUSTOM_SCRIPT_URL, scriptUrl);
		}
		if (StringUtils.isNotBlank(rtscriptUrl)) {
			workBean.addExtraParam(DemoConstants.CUSTOM_RT_SCRIPT_URL, rtscriptUrl);
		}

		model.addAttribute(workBean);
		return forward("/workflow/work/work_view");
	}

	//解析收文管理表单数据
	private Map<String, Object> getFieldValueByMappingName(String formUuid, String dataUuid, Map<String, Object> map) {
		FormAndDataBean fadb = dytableApiFacade.getFormData(formUuid, dataUuid);
		String gongwen_type = (String) fadb.getFieldValueByMappingName(DemoConstants.GONGWEN_TYPE);
		String file_laiyuan = (String) fadb.getFieldValueByMappingName(DemoConstants.FILE_LAIYUAN);
		String shouwen_biaoti = (String) fadb.getFieldValueByMappingName(DemoConstants.SHOUWEN_BIAOTI);
		String text_number = (String) fadb.getFieldValueByMappingName(DemoConstants.TEXT_NUMBER);
		String dispatch_unit = (String) fadb.getFieldValueByMappingName(DemoConstants.DISPATCH_UNIT);
		String emergency_degree = (String) fadb.getFieldValueByMappingName(DemoConstants.EMERGENCY_DEGREE);
		String shouwen_code = (String) fadb.getFieldValueByMappingName(DemoConstants.SHOUWEN_CODE);

		//fieldName 表单字段名fileupload
		List<MongoFileEntity> mongoFileEntities = mongoFileService.getFilesFromFolder(dataUuid,
				DemoConstants.FILEUPLOOD);

		String fjJson = "";
		for (MongoFileEntity mon : mongoFileEntities) {
			fjJson += "," + mon.getFileID() + ";" + mon.getFileName();
		}

		map.put(DemoConstants.GONGWEN_TYPE + "_1", gongwen_type);
		map.put(DemoConstants.FILE_LAIYUAN + "_1", file_laiyuan);
		map.put(DemoConstants.SHOUWEN_BIAOTI + "_1", shouwen_biaoti);
		map.put(DemoConstants.TEXT_NUMBER + "_1", text_number);
		map.put(DemoConstants.DISPATCH_UNIT + "_1", dispatch_unit);
		map.put(DemoConstants.EMERGENCY_DEGREE + "_1", emergency_degree);
		map.put(DemoConstants.SHOUWEN_CODE + "_1", shouwen_code);
		if (!fjJson.equals("")) {
			map.put(DemoConstants.FILEUPLOOD + "_1", fjJson.replaceFirst(",", ""));
		}
		return map;
	}

	//解析发文管理表单数据
	private Map<String, String> getSendTextFieldValueByMappingName(String formUuid, String dataUuid,
			Map<String, String> map) {
		FormAndDataBean fadb = dytableApiFacade.getFormData(formUuid, dataUuid);
		String text_title = (String) fadb.getFieldValueByMappingName(DemoConstants.TEXT_TITLE);
		String fawen_leixing = (String) fadb.getFieldValueByMappingName(DemoConstants.FAWEN_LEIXING);
		String fawen_wenhao = (String) fadb.getFieldValueByMappingName(DemoConstants.FAWEN_WENHAO);
		String fawen_danwei = (String) fadb.getFieldValueByMappingName(DemoConstants.FAWEN_DANWEI);
		String nigao_bumen = (String) fadb.getFieldValueByMappingName(DemoConstants.NIGAO_BUMEN);
		String nigao_man = (String) fadb.getFieldValueByMappingName(DemoConstants.NIGAO_MAN);
		String nigao_date = (String) fadb.getFieldValueByMappingName(DemoConstants.NIGAO_DATE);
		String qianfa_person = (String) fadb.getFieldValueByMappingName(DemoConstants.QIANFA_PERSON);
		String qianfa_date = (String) fadb.getFieldValueByMappingName(DemoConstants.QIANFA_DATE);

		//fieldName 表单字段名fileupload
		List<MongoFileEntity> mongoFileEntities = mongoFileService.getFilesFromFolder(dataUuid,
				DemoConstants.SEND_FILEUPLOOD);

		String fjJson = "";
		for (MongoFileEntity mon : mongoFileEntities) {
			fjJson += "," + mon.getFileID() + ";" + mon.getFileName();
		}

		map.put(DemoConstants.TEXT_TITLE + "_1", text_title);
		map.put(DemoConstants.FAWEN_LEIXING + "_1", fawen_leixing);
		map.put(DemoConstants.FAWEN_WENHAO + "_1", fawen_wenhao);
		map.put(DemoConstants.FAWEN_DANWEI + "_1", fawen_danwei);
		map.put(DemoConstants.NIGAO_BUMEN + "_1", nigao_bumen);
		map.put(DemoConstants.NIGAO_MAN + "_1", nigao_man);
		map.put(DemoConstants.NIGAO_DATE + "_1", nigao_date);
		map.put(DemoConstants.QIANFA_PERSON + "_1", qianfa_person);
		map.put(DemoConstants.QIANFA_DATE + "_1", qianfa_date);
		if (!fjJson.equals("")) {
			map.put(DemoConstants.SEND_FILEUPLOOD + "_1", fjJson.replaceFirst(",", ""));
		}
		return map;
	}

	/**
	 * 
	 * 收文流程保存跳转到新的文件库后，获取表单数据
	 * 
	 * @param flowInstUuid
	 * @return
	 */
	@RequestMapping(value = "/getFields/flowInst/{flowInstUuid}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getFieldsByFlowInstUuid(@PathVariable String flowInstUuid) {
		FlowInstance flowInstance = flowService.getFlowInstance(flowInstUuid);
		Map<String, Object> map = new HashMap<String, Object>();
		return getFieldValueByMappingName(flowInstance.getFormUuid(), flowInstance.getDataUuid(), map);
	}

	/**
	 * 
	 * 发文流程保存跳转到新的文件库后，获取表单数据
	 * 
	 * @param flowInstUuid
	 * @return
	 */
	@RequestMapping(value = "/getSendTextFields/flowInst/{flowInstUuid}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> getSendTextFieldsByFlowInstUuid(@PathVariable String flowInstUuid) {
		FlowInstance flowInstance = flowService.getFlowInstance(flowInstUuid);
		Map<String, String> map = new HashMap<String, String>();
		return getSendTextFieldValueByMappingName(flowInstance.getFormUuid(), flowInstance.getDataUuid(), map);
	}

	/**
	 * 
	 * 流程提交跳转到新的文件库后，获取表单数据
	 * 
	 * @param flowInstUuid
	 * @return
	 */
	@RequestMapping(value = "/getFields/formData/{formUuid}/{dataUuid}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getFieldsByFormData(@PathVariable String formUuid, @PathVariable String dataUuid) {
		Map<String, Object> map = new HashMap<String, Object>();
		return getFieldValueByMappingName(formUuid, dataUuid, map);
	}

	@RequestMapping("/wellfileupload")
	public String wellFileupload() {
		return forward("/demo/wellfileupload_demo");
	}

	@RequestMapping({ "/controldemo" })
	public String controldemo() {
		return forward("/demo/wcontrol_demo");
	}

	@RequestMapping({ "/controldemo1" })
	public String controldemo1() {
		return forward("/demo/wcontrol_demo1");
	}

	@Autowired
	DyFormApiFacade dyFormApiFacade;

	@RequestMapping({ "/dyformapi" })
	public String dyformApi() {
		String formUuid = "a204cdc1-e699-4d7f-b385-8aa7f7c34b94";

		//this.dyFormApiFacade.queryFormDataOfMainform(formUuid, Restrictions.like("form_uuid", "'%a%'"), null);

		return forward("/demo/wcontrol_demo");
	}

	@RequestMapping({ "/exportMongo" })
	public void exportMongo(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dataUuid = "45a0fb00c03bad6a4780efb744ed01d5";
		Date lastModifyTime = this.mongoFileService.getLastModifyTimeOfFolder(dataUuid);
		StringBuffer msg = new StringBuffer();
		msg.append("lastModifyTime:" + sdf.format(lastModifyTime));

		File file = this.mongoFileService.exportChangeInfo(dataUuid, sdf.parse("2014-6-15 00:00:00"));
		//FileInputStream fis = new FileInputStream(file);
		//File dest = new File("c:/" + dataUuid + "zip");
		response.getOutputStream().println(msg.toString());

	}

	@RequestMapping({ "/importMongo" })
	public void importMongo(HttpServletRequest request, HttpServletResponse response) throws Exception {

		File zipFile = new File(MongoFileServiceImpl.appDataDir + MongoFileServiceImpl.pathSeparator
				+ "c638d12767800001675318921faf1cef_1402761600000.zip");
		this.mongoFileService.importChangeInfo(zipFile);

		response.getOutputStream().println("import ok");
	}

	@RequestMapping("file2Folders")
	@ResponseBody
	public ResultMessage file2Folders() throws IOException {
		ResultMessage resultMessage = new ResultMessage();
		this.demoService.file2Folders();
		/*String fileId = this.mongoFileService.createFolderID();
		File file1 = new File("c:/3.txt");
		MongoFileEntity fileEntitye = this.mongoFileService.saveFile(fileId, "test.txt", new FileInputStream(file1));
		String foderId = this.mongoFileService.createFolderID();
		String foderId2 = this.mongoFileService.createFolderID();
		this.mongoFileService.pushFileToFolder(foderId, fileId, "test");
		this.mongoFileService.pushFileToFolder(foderId, fileId, "test");*/
		return resultMessage;
	}

	/**
	 * 模拟从表数据
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("createSubform")
	@ResponseBody
	public ResultMessage createSubform() throws IOException {
		ResultMessage resultMessage = new ResultMessage();
		Map<String, Object> datas = new HashMap<String, Object>();
		datas.put("uuid", "c643cc7feca00001967916c011901aac");
		this.dyFormApiFacade.copySubFormData("f608d8cb-5dc2-40c9-8fe2-e5de4d067959",
				"c643cc886af00001c2b02d4114221e65", "f608d8cb-5dc2-40c9-8fe2-e5de4d067959",
				"c643cc886af00001c2b02d4114221e65", "0644f91c-0dca-4c69-85bf-6cd0b3f60ef6", " uuid = :uuid ", datas);
		/*String fileId = this.mongoFileService.createFolderID();
		File file1 = new File("c:/3.txt");
		MongoFileEntity fileEntitye = this.mongoFileService.saveFile(fileId, "test.txt", new FileInputStream(file1));
		String foderId = this.mongoFileService.createFolderID();
		String foderId2 = this.mongoFileService.createFolderID();
		this.mongoFileService.pushFileToFolder(foderId, fileId, "test");
		this.mongoFileService.pushFileToFolder(foderId, fileId, "test");*/
		return resultMessage;
	}

	@RequestMapping(value = "/saveFormData", method = RequestMethod.POST)
	public ResponseEntity<ResultMessage> saveFormData(@RequestBody String jsonData) throws JSONException,
			JsonParseException, JsonMappingException, IOException, ParseException {
		DyFormData dyFormData = JsonBinder.buildNormalBinder().fromJson(jsonData, DyFormData.class);
		String result = this.demoService.saveDyformdata(dyFormData);

		//c64404018950000159fa124018d01668
		//dyFormData.setFieldValue("selectest", "DYBTN_WF_ADMIN");
		//dyFormData.setFieldValue("selectest", 1);

		return getSucessfulResultMsg(result);
	}

	@RequestMapping(value = "/autoComplete", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	String autoComplete(String searchText, String fields, String dataSourceId) throws JSONException,
			JsonParseException, JsonMappingException, IOException, ParseException {
		List<String> fieldList = JsonBinder.buildNormalBinder().fromJson(fields, List.class);
		Set<DataSourceColumn> set = new HashSet<DataSourceColumn>();
		Collection<DataSourceColumn> DataSourceColumns = new ArrayList<DataSourceColumn>();
		Map<String, Object> map = new HashMap<String, Object>();

		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" (1 = 2 ");
		String orderField = "";
		for (String fieldName : fieldList) {
			DataSourceColumn fieldC = new DataSourceColumn();
			fieldC.setFieldName(fieldName);
			fieldC.setColumnAliase(fieldName);
			fieldC.setColumnName(fieldName);
			//fieldC.setColumnDataType(ViewColumnType.STRING.name());
			DataSourceColumns.add(fieldC);
			whereSql.append(" or ").append(fieldName).append(" like '%'||:").append(fieldName).append("||'%'  ");
			map.put(fieldName, searchText);
			orderField = fieldName;
		}
		whereSql.append(")");
		//DataSourceDefinition d = null;
		set.addAll(DataSourceColumns);
		JSONArray array = new JSONArray();//test_jiekou
		PagingInfo pagingInfo = new PagingInfo();
		pagingInfo.setPageSize(500);
		List<QueryItem> items = dataSourceApiFacade.query(dataSourceId, whereSql.toString(), map, "  " + orderField
				+ " desc", pagingInfo);
		for (QueryItem item : items) {
			JSONObject json = new JSONObject();
			for (String fieldName : fieldList) {
				json.put(fieldName, item.get(fieldName));
			}
			array.put(json);
		}

		return array.toString();
	}

	@Autowired
	DataSourceApiFacade dataSourceApiFacade;

	/**
	 * 获取表单定义
	 * @param fieldDefinitionService 
	 * 
	 * @param tableId
	 * @return
	 */
	@RequestMapping(value = "/getFormDefinition", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	String findFormDefinition(@RequestParam("formUuid") String formUuid) {
		DyFormDefinition formDef = //this.dyFormDefinitionService.findDyFormDefinitionByFormUuid(formUuid);
		this.dyFormApiFacade.getFormDefinition(formUuid);
		return formDef.getDefinitionJson();
	}

	/**
	 * 获取指定表单的从表定义
	 * @param fieldDefinitionService 
	 * 
	 * @param tableId
	 * @return
	 */
	@RequestMapping(value = "/getSubformDefinition", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	String getSubformDefinition(@RequestParam("formUuid") String formUuid) {
		List<SubformDefinition> sds = dyFormApiFacade.getSubformDefinitions(formUuid);
		for (SubformDefinition sd : sds) {
			System.out.println(sd.getDisplayName());
		}
		return "";
	}

	/**
	 * 获取表单定义
	 * @param fieldDefinitionService 
	 * 
	 * @param tableId
	 * @return
	 */
	@RequestMapping(value = "/queryds", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	String queryDataSource() {
		Set<DataSourceColumn> set = new HashSet<DataSourceColumn>();
		Collection<DataSourceColumn> DataSourceColumns = new ArrayList<DataSourceColumn>();
		DataSourceColumn title = new DataSourceColumn();
		title.setFieldName("title");
		title.setColumnAliase("title");
		title.setColumnName("标题");
		title.setColumnDataType(ViewColumnType.STRING.name());
		DataSourceColumns.add(title);

		DataSourceColumn author = new DataSourceColumn();
		author.setFieldName("author");
		author.setColumnAliase("author");
		author.setColumnName("责任者");
		author.setColumnDataType(ViewColumnType.STRING.name());
		DataSourceColumns.add(author);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "te");
		set.addAll(DataSourceColumns);
		JSONArray array = new JSONArray();
		array.put(fileManageDataSourceProvider.query(set, " title like ''||:title||'%' ", map, " title desc",
				new PagingInfo()));
		return array.toString();
	}

	@Autowired
	com.wellsoft.pt.file.support.FileManageDataSourceProvider fileManageDataSourceProvider;

	@SuppressWarnings("static-method")
	protected ResponseEntity<ResultMessage> getSucessfulResultMsg(Object data) {
		ResultMessage resultMessage = new ResultMessage();
		resultMessage.setSuccess(true);
		resultMessage.setData(data);
		return new ResponseEntity<ResultMessage>(resultMessage, HttpStatus.OK);
	}

	@SuppressWarnings("static-method")
	protected ResponseEntity<ResultMessage> getFaultResultMsg(Object data, JsonDataErrorCode jsonDataErrorCode) {
		FaultMessage resultMessage = new FaultMessage();
		resultMessage.setSuccess(false);
		resultMessage.setData(data);
		resultMessage.setErrorCode(jsonDataErrorCode.toString());
		return new ResponseEntity<ResultMessage>(resultMessage, HttpStatus.EXPECTATION_FAILED);
	}

	public static void main(String[] args) {
		DictMap dy = new DictMap();
		DictMap dy2 = new DictMap();

		System.out.println(dy.hashCode());
		System.out.println(dy2.hashCode());

		DataSourceColumn c1 = new DataSourceColumn();
		DataSourceColumn c2 = new DataSourceColumn();

		System.out.println(c1.hashCode());
		System.out.println(c2.hashCode());
	}
}
