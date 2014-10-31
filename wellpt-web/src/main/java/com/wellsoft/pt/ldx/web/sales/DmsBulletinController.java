package com.wellsoft.pt.ldx.web.sales;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.model.sales.Bulletin;
import com.wellsoft.pt.ldx.service.sales.DmsBulletinService;

@Controller
@Scope("prototype")
@RequestMapping("/sales")
public class DmsBulletinController extends BaseController {

	@Autowired
	private DmsBulletinService dmsBulletinService;

	@RequestMapping("/preUpdateBulletin")
	public String updateProduct(
			@RequestParam(value = "objectUuid") String objectUuid, Model model) {
		Bulletin object = new Bulletin();
		if (StringUtils.isNotEmpty(objectUuid))
			object = dmsBulletinService.getBulletin(objectUuid);
		model.addAttribute("entity", object);
		return forward("/ldx/sales/updateBulletin");
	}
}
