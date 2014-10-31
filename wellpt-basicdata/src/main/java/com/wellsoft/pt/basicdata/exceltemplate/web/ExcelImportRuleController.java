package com.wellsoft.pt.basicdata.exceltemplate.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wellsoft.pt.basicdata.exceltemplate.entity.ExcelImportRule;
import com.wellsoft.pt.basicdata.exceltemplate.service.ExcelImportRuleService;
import com.wellsoft.pt.basicdata.facade.BasicDataApiFacade;
import com.wellsoft.pt.basicdata.systemtable.entity.SystemTable;
import com.wellsoft.pt.basicdata.systemtable.entity.SystemTableAttribute;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryData;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.dyform.entity.DyFormDefinition;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.FieldDefinition;
import com.wellsoft.pt.repository.entity.mongo.file.MongoFileEntity;
import com.wellsoft.pt.repository.service.MongoFileService;
import com.wellsoft.pt.utils.web.FileDownloadUtils;

/**
 * 
 * Description: Excel导入规则控制层
 *  
 * @author zhouyq
 * @date 2013-4-22
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-4-22.1	zhouyq		2013-4-22		Create
 * </pre>
 *
 */
@Controller
@RequestMapping("/basicdata/excelimportrule")
public class ExcelImportRuleController extends BaseController {

	@Autowired
	private ExcelImportRuleService excelImportRuleService;
	@Autowired
	private BasicDataApiFacade basicDataApiFacade;
	@Autowired
	private DyFormApiFacade dyFormApiFacade;

	@Autowired
	private MongoFileService mongoFileService;

	/**
	 * 跳转到Excel导入规则界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "")
	public String excelImportRule() {
		return forward("/basicdata/excelimportrule/excelimportrule");
	}

	/**
	 * 
	 * 显示Excel导入规则列表
	 * 
	 * @param queryInfo
	 * @return
	 */

	@RequestMapping(value = "/list")
	public @ResponseBody
	JqGridQueryData listAsJson(JqGridQueryInfo queryInfo) {
		JqGridQueryData queryData = excelImportRuleService.query(queryInfo);
		return queryData;
	}

	/**
	 * 
	 * 列对应列表
	 * 
	 * @param queryInfo
	 * @return
	 */
	@RequestMapping(value = "/excelList")
	public @ResponseBody
	JqGridQueryData excelListAsJson(JqGridQueryInfo queryInfo) {
		JqGridQueryData queryData = excelImportRuleService.queryExcelList(queryInfo);
		return queryData;
	}

	/**
	 * 
	 * 解析excel测试 返回List<Map<String,Object>>
	 * 
	 * @param uploadFile
	 * @param id
	 * @throws IOException
	 */
	@RequestMapping(value = "/submitExcel3")
	public @ResponseBody
	JqGridQueryData uploadFile3(@RequestParam(value = "upload") MultipartFile uploadFile, @RequestParam("id") String id)
			throws IOException {
		if (uploadFile.isEmpty()) {
			System.out.println("上传文件为空！");
		} else {
			try {
				JqGridQueryData queryData = new JqGridQueryData();
				queryData.setRepeatitems(false);
				List<Map<String, Object>> list = excelImportRuleService.getListFromExcel(id,
						uploadFile.getInputStream());
				queryData.setDataList(list);
				return queryData;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 
	 * 获得所有的系统实体
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getSelectEntity")
	public @ResponseBody
	Map<String, List<Map<String, Object>>> getSelectEntity() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<SystemTable> systemTableList = basicDataApiFacade.getAllSystemTables();
		for (SystemTable s : systemTableList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", s.getUuid());
			map.put("name", s.getFullEntityName());
			map.put("cName", s.getChineseName());
			list.add(map);
		}
		Map<String, List<Map<String, Object>>> m = new HashMap<String, List<Map<String, Object>>>();
		m.put("data", list);
		return m;
	}

	/**
	 * 
	 * 获得实体的所有字段
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getSelectEntityColumn", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Map<String, List<Map<String, Object>>> getSelectEntityColumn(@RequestParam("uuid") String uuid) {
		if (uuid != null && !uuid.equals("")) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<SystemTableAttribute> systemTableAttributeList = basicDataApiFacade.getSystemTableColumns(uuid);
			for (SystemTableAttribute s : systemTableAttributeList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("columnName", s.getAttributeName());
				list.add(map);
			}
			Map<String, List<Map<String, Object>>> m = new HashMap<String, List<Map<String, Object>>>();
			m.put("data", list);
			return m;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * 获得所有的动态表单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getSelectForm")
	public @ResponseBody
	Map getSelectForm() {
		List list = new ArrayList();
		List<DyFormDefinition> formDefinitionList = dyFormApiFacade.getAllFormDefinitions(true);
		for (DyFormDefinition s : formDefinitionList) {
			Map map = new HashMap();
			map.put("id", s.getUuid());
			map.put("name", s.getUuid());
			map.put("cName", s.getDisplayName());
			list.add(map);
		}
		Map m = new HashMap();
		m.put("data", list);
		return m;
	}

	/**
	 * 
	 * 获得动态表单的所有字段
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getSelectFormColumn", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Map getSelectFormColumn(@RequestParam("uuid") String uuid) {
		if (uuid != null && !uuid.equals("")) {
			List list = new ArrayList();
			List<FieldDefinition> systemTableAttributeList = dyFormApiFacade.getFormDefinition(uuid)
					.getFieldDefintions();
			for (FieldDefinition f : systemTableAttributeList) {
				Map map = new HashMap();
				map.put("columnName", f.getName());
				list.add(map);
			}
			Map m = new HashMap();
			m.put("data", list);
			return m;
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/uploadExcel", method = RequestMethod.POST)
	@ResponseBody
	public void uploadExcel(@RequestParam(value = "upload") MultipartFile upload, HttpServletResponse response)
			throws IOException {
		String uuid = UUID.randomUUID().toString();
		//		上传处理
		mongoFileService.popAllFilesFromFolder(uuid);
		MongoFileEntity file = mongoFileService.saveFile(uuid, upload.getInputStream());
		mongoFileService.pushFileToFolder(uuid, file.getId(), "attach");

		response.setContentType(MediaType.TEXT_HTML_VALUE);
		response.getWriter().write(uuid);
		response.getWriter().flush();
		response.getWriter().close();
	}

	//读取处理
	@RequestMapping(value = "/downloadImage")
	@ResponseBody
	public void downloadImage(@RequestParam("uuid") String uuid, HttpServletRequest request,
			HttpServletResponse response) {
		ExcelImportRule excelImportRule = excelImportRuleService.getExcelImportRule(uuid);
		if (excelImportRule.getFileUuid() != null && !"".equals(excelImportRule.getFileUuid())) {
			List<MongoFileEntity> files = mongoFileService.getFilesFromFolder(excelImportRule.getFileUuid(), "attach");
			if (files == null || files.size() == 0) {

			}
			MongoFileEntity mongoFileEntity = files.get(0);
			InputStream inputStream = mongoFileEntity.getInputstream();
			String fileName = excelImportRule.getName();
			FileDownloadUtils.download(request, response, inputStream, fileName + ".xls");
		} else {
			ServletOutputStream os;
			try {
				os = response.getOutputStream();
				os.print("<script type='text/javascript'>alert('该规则未上传模版');</script>");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * 获得excel导入规则模版选项
	 * 
	 * @return
	 */
	@RequestMapping(value = "/excelTempOption")
	@ResponseBody
	public String getExcelTempleteOption() {
		List<ExcelImportRule> excelImportRules = excelImportRuleService.getExcelImportRule();
		StringBuffer sb = new StringBuffer();
		for (ExcelImportRule excelImportRule : excelImportRules) {
			sb.append("<option value='" + excelImportRule.getCode() + "'>");
			sb.append(excelImportRule.getName());
			sb.append("</option>");
		}
		return sb.toString();
	}
}
