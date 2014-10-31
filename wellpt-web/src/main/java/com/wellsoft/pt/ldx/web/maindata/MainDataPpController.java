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
import com.wellsoft.pt.ldx.model.mainData.Ppview;
import com.wellsoft.pt.ldx.service.maindata.MainDataPpService;
import com.wellsoft.pt.ldx.service.maindata.MainDataWlGcService;

@Controller
@Scope("prototype")
@RequestMapping("/maindata")
public class MainDataPpController extends BaseController {
	@Autowired
	private MainDataWlGcService mainDataWlGcService;
	@Autowired
	private MainDataPpService mainDataPpService;
	@Autowired
	private SAPDbConfig sapConnectConfig;

	@RequestMapping("/preAddPp")
	public String preAdd(@RequestParam(value = "objectUuid") String objectUuid,
			Model model) {
		Ppview objectView = new Ppview();
		List<Object> stores = new ArrayList<Object>();
		List<Object> factorys = new ArrayList<Object>();
		String[] o = { "", "" };
		stores.add(o);
		factorys.add(o);
		if (StringUtils.isNotEmpty(objectUuid)) {
			objectView = mainDataPpService.getObject(objectUuid);
			stores = mainDataWlGcService.findStores(
					sapConnectConfig.getClient(), objectView.getFactory());
			if ("0".equals(objectView.getStatus())) {
				mainDataPpService.updateFromDefaultParam(objectView);
			}
		}
		factorys = mainDataWlGcService.findFactorys(sapConnectConfig
				.getClient());
		model.addAttribute("stores", stores);
		model.addAttribute("factorys", factorys);
		model.addAttribute("objectView", objectView);
		return forward("/maindata/addPp");
	}

}
