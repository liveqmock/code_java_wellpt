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
import com.wellsoft.pt.ldx.model.mainData.Ficoview;
import com.wellsoft.pt.ldx.service.maindata.MainDataFicoService;
import com.wellsoft.pt.ldx.service.maindata.MainDataWlGcService;

@Controller
@Scope("prototype")
@RequestMapping("/maindata")
public class MainDataFicoController extends BaseController {
	@Autowired
	private MainDataWlGcService mainDataWlGcService;
	@Autowired
	private MainDataFicoService mainDataFicoService;
	@Autowired
	private SAPDbConfig sapConnectConfig;

	@RequestMapping("/preAddFico")
	public String preAdd(@RequestParam(value = "objectUuid") String objectUuid,
			Model model) {
		Ficoview objectView = new Ficoview();
		List<Object> factorys = new ArrayList<Object>();
		String[] o = { "", "" };
		factorys.add(o);
		if (StringUtils.isNotEmpty(objectUuid)) {
			objectView = mainDataFicoService.getObject(objectUuid);
			if ("0".equals(objectView.getStatus())) {
				mainDataFicoService.updateFromDefaultParam(objectView);
			}
		}
		factorys = mainDataWlGcService.findFactorys(sapConnectConfig
				.getClient());
		model.addAttribute("factorys", factorys);
		model.addAttribute("objectView", objectView);
		return forward("/maindata/addFico");
	}

}
