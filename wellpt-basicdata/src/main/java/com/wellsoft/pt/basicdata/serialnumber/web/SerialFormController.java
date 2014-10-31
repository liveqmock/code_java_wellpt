package com.wellsoft.pt.basicdata.serialnumber.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wellsoft.pt.basicdata.serialnumber.service.SerialNumberMaintainService;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryData;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo;
import com.wellsoft.pt.core.web.BaseController;

/**
 * 
 * Description: 可编辑流水号控制层
 *  
 * @author zhouyq
 * @date 2013-3-13
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-3-13.1	zhouyq		2013-3-13		Create
 * </pre>
 *
 */
@Controller
@RequestMapping("/basicdata/serialform")
public class SerialFormController extends BaseController {
	@Autowired
	private SerialNumberMaintainService serialNumberMaintainService;

	/**
	 * 跳转到可编辑流水号界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "")
	public String serialNumberMaintain() {
		return forward("/basicdata/serialnumber/serialform");
	}

	/**
	 * 
	 * 显示可编辑流水号列表
	 * 
	 * @param queryInfo
	 * @return
	 */
	@RequestMapping(value = "/list")
	public @ResponseBody
	JqGridQueryData listAsJson(JqGridQueryInfo queryInfo) {
		JqGridQueryData queryData = serialNumberMaintainService.query(queryInfo);
		return queryData;
	}

}
