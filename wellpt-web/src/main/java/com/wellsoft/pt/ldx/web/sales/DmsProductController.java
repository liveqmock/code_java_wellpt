package com.wellsoft.pt.ldx.web.sales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.model.sales.DmsProduct;
import com.wellsoft.pt.ldx.service.sales.DmsProductService;

@Controller
@Scope("prototype")
@RequestMapping("/sales")
public class DmsProductController extends BaseController {

	@Autowired
	private DmsProductService dmsProductService;

	@RequestMapping("/preUpdateProduct")
	public String updateProduct(@RequestParam(value = "matnr") String matnr,
			Model model) {
		DmsProduct product = dmsProductService.getProduct(matnr);
		product.setMatnr(product.getMatnr().substring(8));
		model.addAttribute("entity", product);
		return forward("/ldx/sales/updateProduct");
	}

}
