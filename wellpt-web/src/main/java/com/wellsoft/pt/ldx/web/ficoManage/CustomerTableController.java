package com.wellsoft.pt.ldx.web.ficoManage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.model.ficoManage.CustomerTable;
import com.wellsoft.pt.ldx.service.ficoManage.ICustomerTableService;

/**
 * 
 * Description: 财务管理客户对应表维护
 *  
 * @author HeShi
 * @date 2014-8-25
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-8-25 	HeShi		2014-8-25		Create
 * </pre>
 *
 */
@Controller
@RequestMapping("/ficoManage/customerTable")
public class CustomerTableController extends BaseController{
	
	@Autowired
	private ICustomerTableService customerTableService;
	
	@RequestMapping("/add")
	public String add(Model model) throws Exception {
		model.addAttribute("model", "add"); //创建
		return forward("/ldx/ficoManage/customerTableAdd");
	}
	
	@RequestMapping("/modify")
	public String modify(Model model,@RequestParam(value = "bukrs") String bukrs,@RequestParam(value = "kunnr") String kunnr) throws Exception {
		model.addAttribute("model", "modify"); //更改
		CustomerTable customer = customerTableService.findCustomerTable(bukrs,kunnr);
		model.addAttribute("customer",customer);
		return forward("/ldx/ficoManage/customerTableAdd");
	}
	
}
