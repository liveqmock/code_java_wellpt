package com.wellsoft.pt.ldx.web.ficoManage;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wellsoft.pt.basicdata.sap.config.SAPDbConfig;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.service.ficoManage.IDefaultPaymentDataService;

/**
 * 
 * Description: 财务管理付款条件维护管理控制器
 *  
 * @author qiulb
 * @date 2014-8-12
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-8-12 	qiulb		2014-8-12		Create
 * </pre>
 *
 */
@Controller
@RequestMapping("/ficoDefault/Payment")
public class DefaultPaymentDataController extends BaseController{
	
	@Autowired
	private IDefaultPaymentDataService defaultPaymentService;
	
	@Resource(name="sapConnectConfig")
	private SAPDbConfig sapConfig;
	
	@RequestMapping("/add")
	public String add(Model model) throws Exception {
		model.addAttribute("method", "1"); //创建
		return forward("/ldx/ficoManage/PaymentAdd");
	}
	
	@RequestMapping("/modify")
	public String modify(Model model,@RequestParam(value = "zterm") String zterm) throws Exception {
		model.addAttribute("method", "0"); //更改
		
		//执行sql
		String sql = "select zterm,bukrs,kunnr,zvtext,zdeadline,waers,zearea,zkdgrp from zfmt0008 where zfmt0008.mandt = " + sapConfig.getClient() + " and zfmt0008.zterm = '" + zterm + "'";
		List<Object> lit = defaultPaymentService.findListBySql(sql);
		
		if (lit != null && lit.size() > 0){
			Object ob1 = lit.get(0);
			Object[] ob2 = (Object[])ob1;
			model.addAttribute("zterm",  	ob2[0]);
			model.addAttribute("bukrs",  	ob2[1]);
			model.addAttribute("kunnr",  	ob2[2]);
			model.addAttribute("zvtext", 	ob2[3]);
			model.addAttribute("zdeadline", ob2[4]);
			model.addAttribute("waers",  	ob2[5]);
			model.addAttribute("zearea", 	ob2[6]);
			model.addAttribute("zkdgrp", 	ob2[7]);
		}		
		
		return forward("/ldx/ficoManage/PaymentAdd");
	}
	
}
