package com.wellsoft.pt.ldx.web.orderManage;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wellsoft.pt.basicdata.sap.config.SAPDbConfig;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.service.OrderManageService;

@Controller
@Scope("prototype")
@RequestMapping("/orderManage/productionSearch")
public class ProductionSearchController extends BaseController{
	@Resource(name="sapConnectConfig")
	private SAPDbConfig sapConfig;
	@Autowired
	private OrderManageService orderManageService;
	
	@RequestMapping("/findStatus")
	public ModelAndView findStatus(){
		ModelAndView orderContext = new ModelAndView(); 
		orderContext.setViewName("/orderManage/productionStatus");
		StringBuffer sum = new StringBuffer();
		//查询状态汇总
		sum.append(" with cust as(")
			.append(" select distinct ta.vbeln,tb.sortl,tb.kunnr from vbak ta,kna1 tb where ta.mandt=").append(sapConfig.getClient())
			.append(" and ta.mandt=tb.mandt and ta.kunnr=tb.kunnr")
			.append(" )")
			.append(" select nvl(sum(case when substr(k.zstatus,0,1)='3' then 1 else 0 end),0),")
			.append(" nvl(sum(case when substr(k.zstatus,0,1)='4' then 1 else 0 end),0),")
			.append(" nvl(sum(case when substr(k.zstatus,0,1)='5' then 1 else 0 end),0),")
			.append(" nvl(sum(case when substr(k.zstatus,0,1)='6' then 1 else 0 end),0)")
			.append(" from vbap a")
			.append(" left join cust b on a.vbeln=b.vbeln ")
			.append(" left join zsdt0047 c on a.mandt=c.mandt and a.vbeln=c.vbeln and a.posnr=c.posnr")
			.append(" left join makt d on a.mandt=d.mandt and a.matnr=d.matnr")
			.append(" left join zsdt0033 e on a.mandt=e.mandt and a.vbeln=e.vbeln and a.posnr=e.posnr")
			.append(" inner join plaf h on a.mandt=h.mandt and a.vbeln=h.kdauf and a.posnr=h.kdpos")
			.append(" left join afpo i on a.mandt=i.mandt and a.vbeln=i.kdauf and a.posnr=i.kdpos")
			.append(" left join afko j on a.mandt=j.mandt and i.aufnr=j.aufnr")
			.append(" inner join zsdt0038 k on a.mandt=k.mandt and a.vbeln=k.vbeln and a.posnr=k.posnr and k.zitem='实际' and substr(k.zstatus,0,1) in ('3','4','5','6')")
			.append(" and a.mandt=").append(sapConfig.getClient());
		
		List list = orderManageService.getListForSQL(sum.toString());
		Object[] object = null;
		if(list!=null&&list.size()>0){
			object = (Object[]) list.get(0);
			orderContext.addObject("djh", object[0]);
			orderContext.addObject("yjh", object[1]);
			orderContext.addObject("zzhi", object[2]);
			orderContext.addObject("ywc", object[3]);
		}
		return orderContext;
	}
}
