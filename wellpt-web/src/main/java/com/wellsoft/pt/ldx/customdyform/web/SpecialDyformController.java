package com.wellsoft.pt.ldx.customdyform.web;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bitronix.tm.utils.ExceptionUtils;

import com.wellsoft.pt.core.web.ResultMessage;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.DyFormData;
import com.wellsoft.pt.ldx.customdyform.service.SpecialDyformService;
import com.wellsoft.pt.utils.encode.JsonBinder;
import com.wellsoft.pt.utils.json.JsonUtils;
import com.wellsoft.pt.utils.web.FileDownloadUtils;
/**
 * Description: 日程控制类
 *  
 * @author wuzq
 * @date 2013-3-1
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-3-1	wuzq		2013-2-7		Create
 * </pre>
 *
 */
@Controller
@RequestMapping("/special/dyform")
public class SpecialDyformController {
	
	@Autowired
	DyFormApiFacade dyFormApiFacade;
	@Autowired
	SpecialDyformService specialDyformService;
	
	// 根据formUuid及dataUuid查询出一条表单数据
	@RequestMapping(value = "/formdata")
	@ResponseBody
	public String formdata(@RequestParam(value="formUuid",required=false)String formUuid,
			@RequestParam(value="dataUuid",required=false)String dataUuid,ModelMap modelMap){
		
		String jsonStr = JsonUtils.object2Json(dyFormApiFacade.getFormData(formUuid, dataUuid));
		return jsonStr;
	}
	
	// 查询表单数据
	@RequestMapping(value = "/query")
	@ResponseBody
	public String query(@RequestParam(value="formId",required=false)String formId,
			@RequestParam(value="columns",required=false)String columns,
			@RequestParam(value="condition",required=false)String condition,ModelMap modelMap){
		
		return JsonUtils.collection2Json(specialDyformService.formDataQuery(formId, columns, condition));
	}
	
	// 查询表单及从表数据
	@RequestMapping(value = "/queryall")
	@ResponseBody
	public String queryall(@RequestParam(value="formId",required=false)String formId,
			@RequestParam(value="columns",required=false)String columns,
			@RequestParam(value="condition",required=false)String condition,
			@RequestParam(value="childFromId",required=false)String childFromId,
			@RequestParam(value="childColumns",required=false)String childColumns,ModelMap modelMap){
		try {
			return JsonUtils.collection2Json(specialDyformService.formDataQueryAll(formId, columns, condition,childFromId,childColumns));
		} catch (Exception e) {
			e.printStackTrace();
			return "{failed}";
		}
	}
		
	@RequestMapping(value = "/sap")
	@ResponseBody
	public String sap(@RequestParam(value="dataSourceId",required=false)String dataSourceId,
			@RequestParam(value="sql",required=false)String sql,ModelMap modelMap){
		
		return JsonUtils.object2Json(specialDyformService.dataSourceQuery(dataSourceId,sql));
	}
	
	@RequestMapping(value = "/product_test")
	public String productTest(@RequestParam(value="formUuid",required=false)String formUuid,
			@RequestParam(value="dataUuid",required=false)String dataUuid,ModelMap modelMap){
		// 获得流程表单的数据
		//DyFormData dyformdata =dyFormApiFacade.getDyFormData(formUuid, dataUuid);
		// 获得主表表单数据
		//Map<String,Object> dataMap = dyformdata.getFormDataByFormUuidAndDataUuid(formUuid, dataUuid);
		specialDyformService.saveDataToForm(formUuid, dataUuid);
		modelMap.put("oldFormUuid", formUuid);
		modelMap.put("formUuid", dyFormApiFacade.getFormUuidById("uf_pbgl_cplxsyyrohscsysmgz"));
		modelMap.put("dataUuid", dataUuid);
		return "app/spcl/product_test";
	}
	
	@RequestMapping(value = "/tst_exception")
	public String tstException(@RequestParam(value="formId",required=false)String formId,
			@RequestParam(value="dataUuid",required=false)String dataUuid,ModelMap modelMap){
		modelMap.put("formUuid", dyFormApiFacade.getFormUuidById(formId));
		modelMap.put("dataUuid", dataUuid);
		return "app/spcl/tst_exception";
	}
	
	@RequestMapping(value = "/saveFormData",method = RequestMethod.POST)
	public ResponseEntity<ResultMessage> saveFormData(@RequestBody String paramString){
		DyFormData localDyFormData = (DyFormData)JsonBinder.buildNormalBinder().fromJson(paramString, DyFormData.class);
		ResultMessage localResultMessage = new ResultMessage();
	    localResultMessage.setSuccess(true);
	    localResultMessage.setData(dyFormApiFacade.saveFormData(localDyFormData));
	    return new ResponseEntity<ResultMessage>(localResultMessage, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/delFormData",method = RequestMethod.POST)
	@ResponseBody
	public String delFormData(String paramString){
		
		return "";
	}
	
	@RequestMapping(value = "/standbyExport",method = RequestMethod.GET)
	@ResponseBody
	public void standbyExport(@RequestParam(value="formUuid",required=false)String formUuid,
			@RequestParam(value="dataUuid",required=false)String dataUuid,HttpServletRequest request,
			HttpServletResponse response){
		File file = specialDyformService.standbyExport(formUuid,dataUuid);
		try {
			FileInputStream fileis = new FileInputStream(file);
			FileDownloadUtils.download(request, response, fileis, file.getName());
			fileis.close();
			if (file.exists()) {
				file.delete();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			FileDownloadUtils.download(request, response, new ByteArrayInputStream(ExceptionUtils.getStackTrace(e)
					.getBytes()), "error.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/standbyImport",method = RequestMethod.POST)
	@ResponseBody
	public String standbyImport(HttpServletRequest request,
			HttpServletResponse response){
		
		return "";
	}
	
	@RequestMapping(value = "/addAccount",method = RequestMethod.POST)
	@ResponseBody
	public String addAccount(@RequestParam(value="formUuid")String formUuid,
			@RequestParam(value="dataUuid")String dataUuid){
		specialDyformService.addAccount(formUuid, dataUuid);
		return "success";
	}
	
}
