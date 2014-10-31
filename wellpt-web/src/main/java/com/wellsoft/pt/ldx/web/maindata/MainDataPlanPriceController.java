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
import com.wellsoft.pt.ldx.model.mainData.PlanView;
import com.wellsoft.pt.ldx.service.maindata.MainDataPlanPriceService;
import com.wellsoft.pt.ldx.service.maindata.MainDataWlGcService;

@Controller
@Scope("prototype")
@RequestMapping("/maindata")
public class MainDataPlanPriceController extends BaseController {
	@Autowired
	private MainDataWlGcService mainDataWlGcService;
	@Autowired
	private MainDataPlanPriceService mainDataPlanPriceService;
	@Autowired
	private SAPDbConfig sapConnectConfig;

	@RequestMapping("/preAddPlanPrice")
	public String preAdd(@RequestParam(value = "planUuid") String planUuid,
			Model model) {
		PlanView planView = new PlanView();
		List<Object> stores = new ArrayList<Object>();
		List<Object> factorys = new ArrayList<Object>();
		String[] o = { "", "" };
		stores.add(o);
		factorys.add(o);
		if (StringUtils.isNotEmpty(planUuid)) {
			planView = mainDataPlanPriceService.getObject(planUuid);
			stores = mainDataWlGcService.findStores(
					sapConnectConfig.getClient(), planView.getFactory());
			if ("0".equals(planView.getStatus())) {
				mainDataPlanPriceService.updateFromDefaultParam(planView);
			}
		}
		factorys = mainDataWlGcService.findFactorys(sapConnectConfig
				.getClient());
		model.addAttribute("stores", stores);
		model.addAttribute("factorys", factorys);
		model.addAttribute("planView", planView);
		return forward("/maindata/addPlanPrice");
	}

}
