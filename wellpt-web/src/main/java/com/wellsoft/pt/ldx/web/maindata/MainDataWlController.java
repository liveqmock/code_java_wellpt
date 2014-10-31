package com.wellsoft.pt.ldx.web.maindata;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.model.mainData.GcMessage;
import com.wellsoft.pt.ldx.service.maindata.MainDataWlService;

@Controller
@Scope("prototype")
@RequestMapping("/maindata")
public class MainDataWlController extends BaseController {
	@Autowired
	private MainDataWlService mainDataWlService;

	@RequestMapping("/preDistributeGc")
	public String preAdd(@RequestParam(value = "wlids") String wlids,
			Model model) {
		try {
			// wlids = new String(wlids.getBytes( "iso-8859-1" ), "UTF-8" );
			wlids = java.net.URLDecoder.decode(wlids, "UTF-8");
		} catch (Exception e) {
		}
		List<GcMessage> factorys = new ArrayList<GcMessage>();
		JSONArray jsonArray = JSONArray.fromObject(wlids);
		factorys = mainDataWlService.findFactorys(((JSONObject) jsonArray
				.get(0)).get("wl").toString());
		model.addAttribute("factorys", factorys);
		model.addAttribute("wlids", wlids.replaceAll("\"", "'"));
		return forward("/maindata/distributeGc");
	}

}
