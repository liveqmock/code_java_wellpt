package com.wellsoft.pt.dyform.web;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wellsoft.pt.common.component.jqgrid.JqGridQueryController;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryData;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo;
import com.wellsoft.pt.common.enums.JsonDataErrorCode;
import com.wellsoft.pt.core.support.QueryData;
import com.wellsoft.pt.core.support.QueryInfo;
import com.wellsoft.pt.core.web.FaultMessage;
import com.wellsoft.pt.core.web.ResultMessage;
import com.wellsoft.pt.dyform.entity.DyFormDisplayModel;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.service.DyFormDisplayModelService;
import com.wellsoft.pt.dyform.support.exception.hibernate.HibernateDdlException;
import com.wellsoft.pt.utils.encode.JsonBinder;

@Controller
@RequestMapping("/dyformmodel")
public class DyFormDisplayModelController extends JqGridQueryController {

	@Autowired
	DyFormDisplayModelService dyFormDisplayModelService;

	@Autowired
	DyFormApiFacade dyFormApiFacade;

	@RequestMapping("/openDisplayModel")
	public String addFormDefinition(@RequestParam(value = "uuid", required = false) String uuid, Model model) {
		model.addAttribute("uuid", uuid);
		return forward("/dyform/dyform_model");
	}

	/**
	 * 保存显示单据信息
	 * 
	 * @param rootTableInfo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveDisplayModel", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResultMessage> saveDisplayModel(@RequestParam(value = "model") String modelJson)
			throws Exception {

		DyFormDisplayModel model = JsonBinder.buildNormalBinder().fromJson(modelJson, DyFormDisplayModel.class);
		try {
			dyFormDisplayModelService.save(model);
		} catch (HibernateDdlException e) {
			logger.error(e.getMessage(), e);
			return getFaultResultMsg(e.getMessage(), JsonDataErrorCode.FormDefinitionUpdateException);
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			return getFaultResultMsg(e.getMessage(), JsonDataErrorCode.FormDefinitionUpdateException);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return getFaultResultMsg(e.getMessage(), JsonDataErrorCode.FormDefinitionUpdateException);
		}
		return getSucessfulResultMsg(model.getUuid());
	}

	@RequestMapping("/getDisplayModel")
	public ResponseEntity<ResultMessage> getDisplayModel(@RequestParam(value = "uuid", required = true) String uuid,
			Model model) {
		DyFormDisplayModel dModel = this.dyFormDisplayModelService.get(uuid);
		return getSucessfulResultMsg(dModel);
	}

	@RequestMapping("/getDisplayModelByModelId")
	public ResponseEntity<ResultMessage> getDisplayModelByModelId(
			@RequestParam(value = "modelId", required = true) String modelId, Model model) {
		DyFormDisplayModel dModel = this.dyFormDisplayModelService.getByModelId(modelId);
		return getSucessfulResultMsg(dModel);
	}

	@RequestMapping("/delete")
	public ResponseEntity<ResultMessage> deleteDisplayModel(@RequestParam(value = "uuid", required = true) String uuid,
			Model model) {
		DyFormDisplayModel dModel = this.dyFormDisplayModelService.get(uuid);
		if (dModel == null) {//显示单据不存在
			return getSucessfulResultMsg("");
		} else {
			boolean isUsed = this.dyFormApiFacade.isUsedOfDisplayModel(dModel.getOuterId());
			if (isUsed) {
				return getFaultResultMsg("this display model is used by other forms",
						JsonDataErrorCode.FormModelBeUsedException);
			} else {
				this.dyFormDisplayModelService.deleteDisplayModel(uuid);
				return getSucessfulResultMsg("");
			}
		}

	}

	@RequestMapping(value = "/list/tree")
	public @ResponseBody
	JqGridQueryData listAsTreeJson(HttpServletRequest request, JqGridQueryInfo jqGridQueryInfo) {
		QueryInfo queryInfo = buildQueryInfo(jqGridQueryInfo, request);
		QueryData queryData = dyFormDisplayModelService.getForPageAsTree(jqGridQueryInfo, queryInfo);
		JqGridQueryData jqGridQueryData = convertToJqGridQueryData(queryData);

		return jqGridQueryData;
	}

	/**
	 * 显示单据列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list/list")
	public String ediableFormDefinitionList(Model model) {
		model.addAttribute("flag", 1);
		return forward("/dyform/dyform_model_list");
	}

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
}
