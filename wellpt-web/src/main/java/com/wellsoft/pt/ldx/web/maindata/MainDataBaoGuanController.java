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

import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.model.mainData.Baoguanview;
import com.wellsoft.pt.ldx.service.maindata.MainDataBaoGuanService;

@Controller
@Scope("prototype")
@RequestMapping("/maindata")
public class MainDataBaoGuanController extends BaseController {
	@Autowired
	private MainDataBaoGuanService mainDataBaoGuanService;

	@RequestMapping("/preAddBaoGuan")
	public String preAdd(@RequestParam(value = "objectUuid") String objectUuid,
			Model model) {
		Baoguanview objectView = new Baoguanview();
		List<Object> factorys = new ArrayList<Object>();
		String[] o = { "", "" };
		factorys.add(o);
		if (StringUtils.isNotEmpty(objectUuid)) {
			objectView = mainDataBaoGuanService.getObject(objectUuid);
			if ("0".equals(objectView.getStatus())) {
				mainDataBaoGuanService.updateFromDefaultParam(objectView);
			}
		}
		model.addAttribute("objectView", objectView);
		return forward("/maindata/addBaoGuan");
	}

}
