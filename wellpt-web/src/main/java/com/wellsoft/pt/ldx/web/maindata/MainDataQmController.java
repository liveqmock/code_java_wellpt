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
import com.wellsoft.pt.ldx.model.mainData.Qmview;
import com.wellsoft.pt.ldx.service.maindata.MainDataQmService;
import com.wellsoft.pt.ldx.service.maindata.MainDataWlGcService;

@Controller
@Scope("prototype")
@RequestMapping("/maindata")
public class MainDataQmController extends BaseController {
	@Autowired
	private MainDataWlGcService mainDataWlGcService;
	@Autowired
	private MainDataQmService mainDataQmService;
	@Autowired
	private SAPDbConfig sapConnectConfig;

	@RequestMapping("/preAddQm")
	public String preAdd(@RequestParam(value = "objectUuid") String objectUuid,
			Model model) {
		Qmview objectView = new Qmview();
		List<Object> factorys = new ArrayList<Object>();
		String[] o = { "", "" };
		factorys.add(o);
		if (StringUtils.isNotEmpty(objectUuid)) {
			objectView = mainDataQmService.getObject(objectUuid);
			if ("0".equals(objectView.getStatus())) {
				mainDataQmService.updateFromDefaultParam(objectView);
			}
		}
		factorys = mainDataWlGcService.findFactorys(sapConnectConfig
				.getClient());
		model.addAttribute("factorys", factorys);
		model.addAttribute("objectView", objectView);
		return forward("/maindata/addQm");
	}

}
