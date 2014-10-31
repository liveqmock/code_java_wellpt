package com.wellsoft.pt.ldx.web.orderManage;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.wellsoft.pt.basicdata.sap.config.SAPDbConfig;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.service.OrderManageService;
import com.wellsoft.pt.ldx.util.StringUtils;

@Controller
@Scope("prototype")
@RequestMapping("/orderManage/orderLineItemDetail")
public class orderLineItemDetailController extends BaseController {
	@Autowired
	private OrderManageService orderManageService;
	@Resource(name="sapConnectConfig")
	private SAPDbConfig sapConfig;

	@RequestMapping("/init")
	public String init(Model model) throws Exception {

		return forward("/orderManage/orderLineItemDetail");
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	protected void doSearch(@RequestParam(value = "vbeln") String vbeln,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("select a.mandt,a.vbeln,a.posnr,a.matnr,a.maktx,a.kwmeng,a.vrkme,a.netpr,a.kdmat,a.werks,a.zpsdat3,b.zbpdate,b.gltrs,a.zyhrq,a.edatu,a.zhfkr,a.zdats,a.zdate,a.psmng,a.zyjrk,a.zyhjg,a.zyhbz,a.wadat_ist,a.lfimg,a.zddzt,a.zchsl,a.zchrq,a.zsfyq,a.zyqbm,a.zycdl,a.zyqbz,a.omchjh ");
		queryString.append(" from zsdt0049 a");
		queryString
				.append(" left join zsdt0011 b on a.vbeln=b.vbeln and a.posnr=b.posnr and a.mandt=b.mandt");
		queryString.append(" where a.mandt='" + 600 + "'");
		queryString.append(" and a.vbeln='"
				+ StringUtils.addLeftZero(vbeln.trim(), 10) + "'");// 查询条件

		String sql = "select count(1) from zsdt0049 where 1=1";
		if (StringUtils.isNotEmpty(vbeln))
			sql += " and vbeln='" + vbeln + "'";
		List totalCount = orderManageService.getListForSQL(sql);
		PagingInfo pagingInfo = new PagingInfo();
		pagingInfo.setTotalCount(Long.parseLong(totalCount.get(0).toString()));
		List list = orderManageService.getListByPage(queryString.toString(),
				pagingInfo);

		JSONArray json = new JSONArray().fromObject(list);
		response.getWriter().print(json.toString());

	}

	@RequestMapping("/update")
	public ModelAndView updateOrder(
			@RequestParam(value = "vbeln") String vbeln,
			@RequestParam(value = "posnr") String posnr) {
		ModelAndView orderContext = new ModelAndView();
		orderContext.setViewName("/orderManage/showAndUpdate");

		String sql = "select zhfkr,zyhrq,zyhjg,zyhbz,zchsl,zchrq,zyqbm,zycdl,zyqbz,omchjh from zsdt0049 where vbeln='"
				+ vbeln + "' and posnr='" + posnr + "'";
		List list = orderManageService.getListForSQL(sql);
		Object[] object = (Object[]) list.get(0);
		orderContext.addObject("vbeln", vbeln);
		orderContext.addObject("posnr", posnr);
		orderContext.addObject("zhfkr", object[0]);
		orderContext.addObject("zyhrq", object[1]);
		orderContext.addObject("zyhjg", object[2]);
		orderContext.addObject("zyhbz", object[3]);
		orderContext.addObject("zchsl", object[4]);
		orderContext.addObject("zchrq", object[5]);
		orderContext.addObject("zyqbm", object[6]);
		orderContext.addObject("zycdl", object[7]);
		orderContext.addObject("zyqbz", object[8]);
		orderContext.addObject("omchjh", object[9]);
		return orderContext;
	}

	/**
	 * 查询订单行项目明细抬头
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/show")
	public ModelAndView findTopMessage(
			@RequestParam(value = "vbeln") String vbeln) {
		// 查询sql
		Object[] object = null;
		ModelAndView orderContext = new ModelAndView();
		orderContext.setViewName("/orderManage/orderStatusCollect");
		if (StringUtils.isNotEmpty(vbeln)) {// 订单号不为空
			StringBuffer find = new StringBuffer();
			find.append("select distinct t1.mandt,t1.vbeln,t6.bezei,t3.lfgsk, t1.VKBUR,t2.bstkd,'' as fhdh,(select tvgrt.bezei from tvgrt  where tvgrt.vkgrp=t1.VKGRP and tvgrt.mandt = '"
					+ 500
					+ "') as AE,nvl((select kna1.sortl from kna1 where kna1.kunnr = t1.Kunnr and kna1.mandt = '"
					+ 500
					+ "' and rownum=1),' ') as sortlTemp,t1.waerk,(select tvv2t.bezei from tvv2t  where tvv2t.kvgr2=t1.KVGR2 and tvv2t.mandt='"
					+ 500 + "') as OM,t1.abrvw ");
			find.append(" from vbak t1 ");
			find.append(" left join vbkd t2 on  t1.vbeln = t2.vbeln  ");
			find.append(" left join vbuk t3 on t1.vbeln = t3.vbeln");
			find.append(" left join lips t4 on t1.vbeln = t4.vbelv");
			find.append(" left join TVAKT t6 on t1.mandt=t6.mandt and t1.auart = t6.auart and t6.spras='1'");
			find.append(" where t1.mandt='" + sapConfig.getClient() + "' ");
			find.append(" and t1.vbeln='"+vbeln+"'");// +StringUtils.addLeftZero(vbeln,10)+"'");订单号为查询条件
			List<Object[]> list = orderManageService.getListForSQL(find
					.toString());

			if (list != null && list.size() > 0) {
				object = list.get(0);
				orderContext.addObject("mandt", object[0]);
				orderContext.addObject("vbeln", object[1]);
				orderContext.addObject("bezei", object[2]);
				orderContext.addObject("lfgsk", object[3]);
				orderContext.addObject("VKBUR", object[4]);
				orderContext.addObject("bstkd", object[5]);
				orderContext.addObject("fhdh", object[6]);
				orderContext.addObject("AE", object[7]);
				orderContext.addObject("sortlTemp", object[8]);
				orderContext.addObject("waerk", object[9]);
				orderContext.addObject("OM", object[10]);
				orderContext.addObject("abrvw", object[11]);
			}

			String sql = "select distinct t5.vbeln from vbak t1"
					+ " inner join lips t4 on t1.vbeln = t4.vbelv"
					+ " inner join likp t5 on t4.vbeln = t5.vbeln"
					+ " where t5.LFART in ('ZALF', 'ZLR')"
					+ " and t1.vbeln = '"
					+ StringUtils.addLeftZero(vbeln.trim(), 10) + "'";
			List<Object> vbelnList = orderManageService.getListForSQL(sql);
			orderContext.addObject("vbelnList", vbelnList);
		}
		return orderContext;
	}

}
