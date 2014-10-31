package com.wellsoft.pt.ldx.web.orderManage;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.wellsoft.pt.basicdata.sap.config.SAPDbConfig;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.service.OrderManageService;
import com.wellsoft.pt.ldx.util.StringUtils;

@Controller
@Scope("prototype")
@RequestMapping("/orderManage/omStatus")
public class orderStatusOmController extends BaseController{
	@Autowired
	private OrderManageService orderManageService;
	@Resource(name="sapConnectConfig")
	private SAPDbConfig sapConfig;
	@RequestMapping(value="/search")
	public ModelAndView search(@RequestParam(value="sortl")String sortl){

		ModelAndView orderContext = new ModelAndView(); 
		orderContext.setViewName("/orderManage/orderStatusOm");
		//查询
		List list = doSearch(sortl);
		orderContext.addObject("orderList",list);
		//查询汇总
		List list1 = doSearchSum(sortl);
		Object[] object = null;
		if(list1!=null&&list1.size()>0){
			object = (Object[]) list1.get(0);
			orderContext.addObject("dbz", object[0]);
			orderContext.addObject("dbzyj", object[1]);
			orderContext.addObject("dbzd", object[2]);
			orderContext.addObject("dch", object[3]);
			orderContext.addObject("dchyj", object[4]);
			orderContext.addObject("dchd", object[5]);
			orderContext.addObject("wsdch", object[6]);
			orderContext.addObject("ddc", object[7]);
			orderContext.addObject("ddcyj", object[8]);
			orderContext.addObject("ddcd", object[9]);
			orderContext.addObject("yhbhe", object[10]);
		}
		return orderContext;
	}
	
	@RequestMapping(value="/init")
	public ModelAndView init(){

		ModelAndView orderContext = new ModelAndView(); 
		orderContext.setViewName("/orderManage/orderStatusOm");
		List list = doSearch("");
		orderContext.addObject("orderList",list);
		List list1 = doSearchSum("");
		Object[] object = null;
		if(list1!=null&&list1.size()>0){
			object = (Object[]) list1.get(0);
			orderContext.addObject("dbz", object[0]);
			orderContext.addObject("dbzyj", object[1]);
			orderContext.addObject("dbzd", object[2]);
			orderContext.addObject("dch", object[3]);
			orderContext.addObject("dchyj", object[4]);
			orderContext.addObject("dchd", object[5]);
			orderContext.addObject("wsdch", object[6]);
			orderContext.addObject("ddc", object[7]);
			orderContext.addObject("ddcyj", object[8]);
			orderContext.addObject("ddcd", object[9]);
			orderContext.addObject("yhbhe", object[10]);
		}
		return orderContext;
	}
	
	
	public List doSearch(String sortl){
		ModelAndView orderContext = new ModelAndView(); 
		orderContext.setViewName("/orderManage/orderStatusOm");
		//查询sql
		StringBuffer queryString = new StringBuffer();
		queryString.append("select sortl,")
			.append(" sum(case when zddzt='待包装' then 1 else 0 end),")
			.append(" sum(case when zddzt='待包装-预警' then 1 else 0 end),")
			.append(" sum(case when zddzt='待包装-delay' then 1 else 0 end),")
			.append(" sum(case when zddzt='待出货' then 1 else 0 end),")
			.append(" sum(case when zddzt='待出货-预警' then 1 else 0 end),")
			.append(" sum(case when zddzt='待出货-delay' then 1 else 0 end),")
			.append(" sum(case when zddzt='尾数待出货' then 1 else 0 end),")
			.append(" sum(case when zddzt='待订舱' then 1 else 0 end),")
			.append(" sum(case when zddzt='待订舱-预警' then 1 else 0 end),")
			.append(" sum(case when zddzt='待订舱-delay' then 1 else 0 end),")
			.append(" sum(case when zddzt='已完全出货' then 1 else 0 end)")
			.append(" from zsdt0050 ")
			.append(" where mandt =").append(sapConfig.getClient())
			.append(" and sortl != ' '");
		if(StringUtils.isNotBlank(sortl)){
			queryString.append(" and sortl='").append(sortl.trim()).append("'");
		}
		queryString.append(" group by sortl order by sortl");
		
		return orderManageService.getListForSQL(queryString.toString());
	}
	
	public List doSearchSum(String sortl){

		//查询汇总行
		StringBuffer querySummary = new StringBuffer();
		querySummary.append("select ")
			.append(" sum(case when zddzt='待包装' then 1 else 0 end),")
			.append(" sum(case when zddzt='待包装-预警' then 1 else 0 end),")
			.append(" sum(case when zddzt='待包装-delay' then 1 else 0 end),")
			.append(" sum(case when zddzt='待出货' then 1 else 0 end),")
			.append(" sum(case when zddzt='待出货-预警' then 1 else 0 end),")
			.append(" sum(case when zddzt='待出货-delay' then 1 else 0 end),")
			.append(" sum(case when zddzt='尾数待出货' then 1 else 0 end),")
			.append(" sum(case when zddzt='待订舱' then 1 else 0 end),")
			.append(" sum(case when zddzt='待订舱-预警' then 1 else 0 end),")
			.append(" sum(case when zddzt='待订舱-delay' then 1 else 0 end),")
			.append(" sum(case when zddzt='已完全出货' then 1 else 0 end)")
			.append(" from zsdt0050 ")
			.append(" where mandt =").append(sapConfig.getClient());
		if(StringUtils.isNotEmpty(sortl)){
			querySummary.append(" and sortl='").append(sortl.trim()).append("'");
		}
		
		return orderManageService.getListForSQL(querySummary.toString());
	}
}
