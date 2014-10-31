package com.wellsoft.pt.ldx.web.sales;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wellsoft.pt.basicdata.sap.config.SAPDbConfig;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.model.sales.SellUser;
import com.wellsoft.pt.ldx.service.ISapService;
import com.wellsoft.pt.ldx.service.sales.DmsUserService;

@Controller
@Scope("prototype")
@RequestMapping("/sales")
public class DmsUserController extends BaseController {

	@Autowired
	private DmsUserService dmsUserService;
	@Autowired
	private ISapService sapService;

	@Resource(name="sapConnectConfig")
	private SAPDbConfig sapConfig;

	@RequestMapping("/preUpdateUser")
	public String updateProduct(
			@RequestParam(value = "objectUuid") String objectUuid,
			@RequestParam(value = "isEdit") Boolean isEdit, Model model) {
		SellUser object = new SellUser();
		if (StringUtils.isNotEmpty(objectUuid))
			object = dmsUserService.getUser(objectUuid);
		List<String[]> zterms = new ArrayList<String[]>();
		List<String[]> kdgrps = new ArrayList<String[]>();
		List<String[]> kondas = new ArrayList<String[]>();
		List<String[]> bzirks = new ArrayList<String[]>();
		List<Object> list = sapService
				.findListBySql("select zterm,text1 from T052U where spras='1' and zterm in ('CN01','CN02','CN03','CN04','CN05','CN06','CN07') and mandt='"
						+ sapConfig.getClient() + "'");
		createArray(list, zterms);
		list = sapService
				.findListBySql("select kdgrp,ktext from T151T where spras='1' and kdgrp in ('C1','C2') and mandt='"
						+ sapConfig.getClient() + "'");
		createArray(list, kdgrps);
		list = sapService
				.findListBySql("select konda,vtext from T188T where spras='1' and konda in ('Z1','Z2','Z3','Z4','Z5','Z6') and mandt='"
						+ sapConfig.getClient() + "'");
		createArray(list, kondas);
		list = sapService
				.findListBySql("select bzirk,bztxt from T171t where spras='1' and bzirk in ('C1','C2','C3','C5','C6','C7','C8','C9','E1','E2','E3','E5','E6') and mandt='"
						+ sapConfig.getClient() + "'");
		createArray(list, bzirks);
		model.addAttribute("zterms", zterms);
		model.addAttribute("kdgrps", kdgrps);
		model.addAttribute("kondas", kondas);
		model.addAttribute("bzirks", bzirks);
		model.addAttribute("entity", object);
		model.addAttribute("isEdit", isEdit);
		return forward("/ldx/sales/updateUser");
	}

	private void createArray(List<Object> list, List<String[]> arrayList) {
		for (Object object : list) {
			if (object instanceof Object[]) {
				String[] re = new String[2];
				for (int i = 0; i < ((Object[]) object).length; i++) {
					if (((Object[]) object)[i] != null) {
						re[i] = ((Object[]) object)[i].toString();
					} else {
						re[i] = "";
					}
				}
				arrayList.add(re);
			}
		}
	}
}
