package com.wellsoft.pt.ldx.web.prodplan;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.service.productionPlan.IProductionStockService;

/**
 * 
 * Description: 生产计划平台可用库存管理控制器
 *  
 * @author hes
 * @date 2014-7-29
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-7-29.1	heshi		2014-7-29		Create
 * </pre>
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/productionPlan/stock")
public class ProductionStockController extends BaseController {

	@Autowired
	private IProductionStockService productionStockService;
	

	@RequestMapping("/add")
	public String add() throws Exception {
		return forward("/ldx/prodplan/stockAdd");
	}
	
	@RequestMapping("/modify")
	public String modify(@RequestParam(value = "dwerks") String dwerks, @RequestParam(value = "lgort") String lgort,Model model) throws Exception {
		List<String[]> list = productionStockService.findStockByInfo(dwerks,lgort);
		String lvorm ="";
		if(null!=list && list.size()>0){
			lvorm = list.get(0)[2];
		}
		model.addAttribute("dwerks",dwerks);
		model.addAttribute("lgort",lgort);
		model.addAttribute("lvorm",lvorm);
		return forward("/ldx/prodplan/stockModify");
	}
}
