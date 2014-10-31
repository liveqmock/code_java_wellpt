package com.wellsoft.pt.ldx.web.maindata;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wellsoft.pt.basicdata.sap.config.SAPDbConfig;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.model.mainData.WlGc;
import com.wellsoft.pt.ldx.service.maindata.MainDataWlGcService;

@Controller
@Scope("prototype")
@RequestMapping("/maindata")
public class MainDataWlGcController extends BaseController {
	@Autowired
	private MainDataWlGcService mainDataWlGcService;
	@Autowired
	private SAPDbConfig sapConnectConfig;

	@RequestMapping("/preAdd")
	public String preAdd(@RequestParam(value = "wlgcUuid") String wlgcUuid,
			Model model) {
		WlGc wlgc = new WlGc();
		List<Object> stores = new ArrayList<Object>();
		List<Object> factorys = new ArrayList<Object>();
		String[] o = { "", "" };
		stores.add(o);
		factorys.add(o);
		if (StringUtils.isNotEmpty(wlgcUuid)) {
			wlgc = mainDataWlGcService.getObject(wlgcUuid);
			stores = mainDataWlGcService.findStores(
					sapConnectConfig.getClient(), wlgc.getFactory());
		}
		factorys = mainDataWlGcService.findFactorys(sapConnectConfig
				.getClient());
		model.addAttribute("stores", stores);
		model.addAttribute("factorys", factorys);
		model.addAttribute("wlgc", wlgc);
		return forward("/maindata/addWlgc");
	}

}
