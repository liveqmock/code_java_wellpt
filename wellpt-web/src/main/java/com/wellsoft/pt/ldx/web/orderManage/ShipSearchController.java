package com.wellsoft.pt.ldx.web.orderManage;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.wellsoft.pt.basicdata.sap.config.SAPDbConfig;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.service.OrderManageService;

@Controller
@Scope("prototype")
@RequestMapping("/orderManage/shipSearch")
public class ShipSearchController extends BaseController{
	@Autowired
	private OrderManageService orderManageService;
	@Resource(name="sapConnectConfig")
	private SAPDbConfig sapConfig;
	
	@RequestMapping("/findStatus")
	public ModelAndView findStatus(){
		ModelAndView orderContext = new ModelAndView(); 
		orderContext.setViewName("/orderManage/shipSearchStatus");
		
		//查询sql
		StringBuffer find = new StringBuffer();
		find.append("with zsdt16 as(")
			.append(" select distinct a.vbeln,a.vgbel,b.zgbh from lips a ")
			.append(" inner join zsdt0016 b on a.mandt = b.mandt and a.vbeln = b.vbeln and a.posnr = b.posnr")
			.append(" where a.mandt =").append(sapConfig.getClient())
			.append(" )")
			.append(" select ")
			.append(" nvl(sum(case when a.zchzt='done-及时' then 1 else 0 end),0),")
			.append(" nvl(sum(case when a.zchzt='done-delay' then 1 else 0 end),0),")
			.append(" nvl(sum(case when e.zyhjg='合格' then 1 else 0 end),0),")
			.append(" nvl(sum(case when e.zyhjg='不合格' then 1 else 0 end),0)")
			.append(" from zsdt0048 a")
			.append(" left join zsdt0050 e on a.vgbel = e.vbeln and a.mandt = e.mandt ")
			.append(" left join zsdt16 c on a.vgbel = c.vgbel and a.vbeln = c.vbeln")
			.append(" where a.mandt = ")
			.append(sapConfig.getClient());
		
		List list = orderManageService.getListForSQL(find.toString());
		Object[] object = null;
		if(list!=null&&list.size()>0){
			object = (Object[]) list.get(0);
			orderContext.addObject("jhyq", object[0]);
			orderContext.addObject("jhjs", object[1]);
			orderContext.addObject("yhhe", object[2]);
			orderContext.addObject("yhbhe", object[3]);
		}
		return orderContext;
	}
	@RequestMapping("/findOrderByVbeln")
	public ModelAndView findOrderByVbeln(@RequestParam(value="vbeln")String vbeln,@RequestParam(value="vgbel")String vgbel
			,@RequestParam(value="zgbh")String zzjbh){
		ModelAndView orderContext = new ModelAndView(); 
		orderContext.setViewName("/orderManage/shipEdit");
		String sql =  " select " 
			+ " a.mandt,a.vbeln,a.vgbel,a.lfimg,a.omchjh,a.zdata,a.zxyzcq,a.zdqdata,a.zdygk,a.zgx,a.zysfs,a.zzgrq,a.zchq,a.edatu,a.zchzt,a.zzrbm,a.zchyc,a.zycbz,a.zchsl" 
			+ " from zsdt0048 a "
			+ " where a.mandt ="+sapConfig.getClient()+" and a.vbeln ='"+vbeln.trim()+"' and a.vgbel ='"+vgbel.trim()+"'" ;
		List list = orderManageService.getListForSQL(sql);
		Object[] object = null;
		if(list!=null&&list.size()>0){
			object = (Object[]) list.get(0);
			orderContext.addObject("mandt", object[0]);
			orderContext.addObject("vbeln", object[1]);
			orderContext.addObject("vgbel", object[2]);
			orderContext.addObject("lfimg", object[4]);
			orderContext.addObject("omchjh", object[5]);
			orderContext.addObject("zdata", object[6]);
			orderContext.addObject("zxyzcq", object[7]);
			orderContext.addObject("zdqdata", object[8]);
			orderContext.addObject("zdygk", object[9]);
			orderContext.addObject("zgx", object[10]);
			orderContext.addObject("zysfs", object[11]);
			orderContext.addObject("zzgrq", object[12]);
			orderContext.addObject("zchq", object[13]);
			orderContext.addObject("edatu", object[14]);
			orderContext.addObject("zchzt", object[15]);
			orderContext.addObject("zchyc", object[16]);
			orderContext.addObject("zycbz", object[17]);
			orderContext.addObject("zchsl", object[18]);
			orderContext.addObject("zzjbh", zzjbh);
		}
		
		return orderContext;
	}
}
