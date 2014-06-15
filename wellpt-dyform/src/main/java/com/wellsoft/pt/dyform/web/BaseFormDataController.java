package com.wellsoft.pt.dyform.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wellsoft.pt.common.component.jqgrid.JqGridQueryController;
import com.wellsoft.pt.common.enums.JsonDataErrorCode;
import com.wellsoft.pt.core.web.FaultMessage;
import com.wellsoft.pt.core.web.ResultMessage;

public class BaseFormDataController extends JqGridQueryController {

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
