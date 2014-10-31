package com.wellsoft.pt.ldx.web.sales;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wellsoft.pt.basicdata.sap.config.SAPDbConfig;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.model.sales.Sellorder;
import com.wellsoft.pt.ldx.model.sales.Sellorderline;
import com.wellsoft.pt.ldx.service.ISapService;
import com.wellsoft.pt.ldx.service.sales.DmsOrderService;

@Controller
@Scope("prototype")
@RequestMapping("/sales")
public class DmsOrderController extends BaseController {

	@Autowired
	private DmsOrderService dmsorderService;

	@Autowired
	private ISapService sapService;

	@Resource(name="sapConnectConfig")
	private SAPDbConfig sapConfig;

	@RequestMapping("/preViewOrder")
	public String viewOrder(
			@RequestParam(value = "objectUuid") String objectUuid, Model model) {
		Sellorder object = dmsorderService.getSellOrder(objectUuid);
		Double d = 0.000;
		DecimalFormat fnum = new DecimalFormat("##0.00");
		for (Sellorderline line : object.getOrderlines()) {
			d = d + line.getSl() * line.getDj().doubleValue() / line.getJgdw();
		}
		object.setJg(fnum.format(d));
		List<String[]> zterms = new ArrayList<String[]>();
		List<Object> list = sapService
				.findListBySql("select zterm,text1 from T052U where spras='1' and zterm in ('CN01','CN02','CN03','CN04','CN05','CN06','CN07') and mandt='"
						+ sapConfig.getClient() + "'");
		createArray(list, zterms);
		model.addAttribute("entity", object);
		model.addAttribute("zterms", zterms);
		return forward("/ldx/sales/viewOrder");
	}

	@RequestMapping("/preEditOrder")
	public String editOrder(
			@RequestParam(value = "objectUuid") String objectUuid, Model model) {
		Sellorder object = dmsorderService.getSellOrder(objectUuid);
		Double d = 0.000;
		DecimalFormat fnum = new DecimalFormat("##0.00");
		for (Sellorderline line : object.getOrderlines()) {
			d = d + line.getSl() * line.getDj().doubleValue() / line.getJgdw();
		}
		object.setJg(fnum.format(d));
		List<String[]> zterms = new ArrayList<String[]>();
		List<Object> list = sapService
				.findListBySql("select zterm,text1 from T052U where spras='1' and zterm in ('CN01','CN02','CN03','CN04','CN05','CN06','CN07') and mandt='"
						+ sapConfig.getClient() + "'");
		createArray(list, zterms);
		model.addAttribute("entity", object);
		model.addAttribute("zterms", zterms);
		return forward("/ldx/sales/editOrder");
	}

	@RequestMapping("/preAuditOrder")
	public String auditOrder(
			@RequestParam(value = "objectUuid") String objectUuid, Model model) {
		Sellorder object = dmsorderService.getSellOrder(objectUuid);
		Double d = 0.000;
		DecimalFormat fnum = new DecimalFormat("##0.00");
		for (Sellorderline line : object.getOrderlines()) {
			d = d + line.getSl() * line.getDj().doubleValue() / line.getJgdw();
		}
		object.setJg(fnum.format(d));
		List<String[]> zterms = new ArrayList<String[]>();
		List<Object> list = sapService
				.findListBySql("select zterm,text1 from T052U where spras='1' and zterm in ('CN01','CN02','CN03','CN04','CN05','CN06','CN07') and mandt='"
						+ sapConfig.getClient() + "'");
		createArray(list, zterms);
		model.addAttribute("entity", object);
		model.addAttribute("zterms", zterms);
		return forward("/ldx/sales/auditOrder");
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
