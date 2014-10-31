package com.wellsoft.pt.ldx.web.maindata;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wellsoft.pt.basicdata.sap.config.SAPDbConfig;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.service.maindata.MainDataPpService;
import com.wellsoft.pt.ldx.service.maindata.MainDataWlGcService;

@Controller
@Scope("prototype")
@RequestMapping("/maindata")
public class MainDataWlgcStatusController extends BaseController {
	@Autowired
	private MainDataWlGcService mainDataWlGcService;
	@Autowired
	private MainDataPpService mainDataPpService;
	@Autowired
	private SAPDbConfig sapConnectConfig;

	@RequestMapping("/preUpdateWlgcStatus")
	public String preUpdate(Model model) {
		List<String> fs = mainDataPpService.findWlgcOwnFactory();
		List<Object> factorys = new ArrayList<Object>();
		if (fs.contains("*")) {
			String[] o = { " ", " " };
			factorys.add(o);
		}
		List<Object> results = mainDataWlGcService
				.findFactorys(sapConnectConfig.getClient());
		for (Object object : results) {
			if (object instanceof Object[]) {
				String werks = ((Object[]) object)[0].toString();
				if (fs.contains(werks)) {
					String[] arr = new String[2];
					arr[0] = ((Object[]) object)[0].toString();
					arr[1] = ((Object[]) object)[1].toString();
					factorys.add(arr);
				}
			}
		}
		model.addAttribute("factorys", factorys);
		return forward("/maindata/updateWlgcStatus");
	}

}
