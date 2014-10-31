package com.wellsoft.pt.ldx.web.prodplan;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.service.productionPlan.IProductionLineManagerService;

/**
 * 
 * Description: 生产计划平台用户资料管理
 *  
 * @author hes
 * @date 2014-8-1
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-8-1.1	heshi		2014-8-1		Create
 * </pre>
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/productionPlan/lineManage")
public class ProdLineManagerController extends BaseController {

	@Autowired
	private IProductionLineManagerService productionLineManagerService;

	@RequestMapping("/add")
	public String add() throws Exception {
		return forward("/ldx/prodplan/lineManagerAdd");
	}
	
	@RequestMapping("/modify")
	public String modify(@RequestParam(value = "zsg") String zsg, @RequestParam(value = "zoauser") String zoauser,Model model) throws Exception {
		List<String[]> list = productionLineManagerService.findLineManagerByInfo(zsg, zoauser);
		String zkz="",verid="",matkl="",atwrt="",dwerks="",zzz="";
		if(null!=list && list.size()>0){
			zkz = list.get(0)[2];
			verid = list.get(0)[3];
			matkl = list.get(0)[4];
			atwrt = list.get(0)[5];
			dwerks = list.get(0)[6];
			zzz = list.get(0)[7];
		}
		model.addAttribute("zsg",zsg);
		model.addAttribute("zoauser",zoauser);
		model.addAttribute("zkz",zkz);
		model.addAttribute("verid",verid);
		model.addAttribute("matkl",matkl);
		model.addAttribute("atwrt",atwrt);
		model.addAttribute("dwerks",dwerks);
		model.addAttribute("zzz",zzz);
		return forward("/ldx/prodplan/lineManagerModify");
	}
}
