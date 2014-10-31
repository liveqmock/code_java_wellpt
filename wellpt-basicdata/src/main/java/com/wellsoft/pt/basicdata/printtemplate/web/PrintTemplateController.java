package com.wellsoft.pt.basicdata.printtemplate.web;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wellsoft.pt.basicdata.printtemplate.entity.PrintTemplate;
import com.wellsoft.pt.basicdata.printtemplate.service.PrintTemplateService;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryData;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.repository.entity.mongo.file.MongoFileEntity;
import com.wellsoft.pt.repository.service.MongoFileService;
import com.wellsoft.pt.utils.web.FileDownloadUtils;

/**
 * 
 * Description:  打印模板控制层
 *  
 * @author zhouyq
 * @date 2013-3-20
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-3-20.1	zhouyq		2013-3-20		Create
 * </pre>
 *
 */
@Controller
@RequestMapping("/basicdata/printtemplate")
public class PrintTemplateController extends BaseController {

	@Autowired
	private PrintTemplateService printTemplateService;

	@Autowired
	private MongoFileService mongoFileService;

	/**
	 * 
	 * 上传模板文件
	 * 
	 * @param uploadFile
	 * @param uuid
	 * @throws IOException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 */
	@RequestMapping(value = "/uploadFile")
	@ResponseBody
	public void uploadFile(@RequestParam(value = "uploadFile") MultipartFile uploadFile,
			@RequestParam("uuid") String uuid) throws IOException, IntrospectionException, IllegalAccessException,
			InvocationTargetException {
		System.out.println("uuid:" + uuid);
		PrintTemplate printTemplate = printTemplateService.getByUuid(uuid);
		if (uploadFile.isEmpty()) {
			System.out.println("上传文件为空！");
		} else {
			if (StringUtils.isNotBlank(uuid)) {
				mongoFileService.popAllFilesFromFolder(uuid);
				MongoFileEntity file = mongoFileService.saveFile(uuid, uploadFile.getInputStream());
				mongoFileService.pushFileToFolder(uuid, file.getId(), "attach");
				System.out.println("删除旧模板，开始上传新模板！");
				System.out.println("上传文件不为空！开始上传");
			}
			printTemplate.setFileUuid(uuid);
			printTemplateService.save(printTemplate);
			System.out.println("上传成功！");
		}
	}

	/**
	 * 
	 * 下载打印模板定义
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.printtemplate.service.PrintTemplateService#download(java.lang.String)
	 */
	@RequestMapping(value = "/download")
	public void download(@RequestParam("downloaduuid") String downloaduuid, HttpServletRequest request,
			HttpServletResponse response) {
		List<MongoFileEntity> files = mongoFileService.getFilesFromFolder(downloaduuid, "attach");
		if (files == null || files.size() == 0) {

		}
		MongoFileEntity mongoFileEntity = files.get(0);

		PrintTemplate printTemplate = printTemplateService.getByUuid(downloaduuid);

		InputStream inputStream = mongoFileEntity.getInputstream();

		if ("wordType".equals(printTemplate.getTemplateType())) {
			FileDownloadUtils.download(request, response, inputStream, downloaduuid + ".doc");
		} else if ("htmlType".equals(printTemplate.getTemplateType())) {
			FileDownloadUtils.download(request, response, inputStream, downloaduuid + ".html");
		}
	}

	/**
	 * 跳转到打印模板界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "")
	public String printTemplate() {
		return forward("/basicdata/printtemplate/printtemplate");
	}

	/**
	 * 
	 * 显示打印模板列表
	 * 
	 * @param queryInfo
	 * @return
	 */

	@RequestMapping(value = "/list")
	public @ResponseBody
	JqGridQueryData listAsJson(JqGridQueryInfo queryInfo) {
		JqGridQueryData queryData = printTemplateService.query(queryInfo);
		return queryData;
	}

}
