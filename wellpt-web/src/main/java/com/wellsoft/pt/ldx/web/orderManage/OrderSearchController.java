package com.wellsoft.pt.ldx.web.orderManage;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.wellsoft.pt.basicdata.sap.config.SAPDbConfig;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.model.orderManage.Zsdt0050;
import com.wellsoft.pt.ldx.service.OrderManageService;
import com.wellsoft.pt.ldx.service.impl.SapServiceImpl;
@Controller
@Scope("prototype")
@RequestMapping("/orderManage/orderSearch")
public class OrderSearchController extends BaseController{

	@Autowired
	private OrderManageService orderManageService;
	@Resource(name="sapConnectConfig")
	private SAPDbConfig sapConfig;

	private Zsdt0050 param = new Zsdt0050();
	
	@RequestMapping("/init")
	public String init(@RequestParam(value="vbeln")String vbeln
			,HttpServletRequest request,HttpServletResponse response) throws Exception{
	    	return forward("/orderManage/orderSearch");
	}
	@RequestMapping("/orderEdit")
	public ModelAndView orderEdit(@RequestParam(value="vbeln")String vbeln){
		ModelAndView orderContext = new ModelAndView(); 
		orderContext.setViewName("/orderManage/orderEdit");
		String sql = "select " 
			+ " mandt,vbeln,abrvw,sortl,zprodtype,zpono,bstkd,kwmeng,zpsdat3,edatu,zhfkr,krpch,xmdyb,zsjtg,zbzzd,zyjrk,zyhrq," 
			+ "zyhjg,zyhbz,zhychh,zchws,chwsbz,zchsl,zchrq,zzbgd,zbz,omchjh,zshtsl,zschws,zsfyq,zyqbm,zycdl,zyqbz,zddzt,werks,zddps "
			+ " from zsdt0050 "
			+ " where mandt ="+sapConfig.getClient()+" and vbeln ='"+vbeln.trim()+"'";
		List list = orderManageService.getListForSQL(sql);
		Object[] object = null;
		if(list!=null&&list.size()>0){
			object = (Object[]) list.get(0);
			orderContext.addObject("vbeln", object[1]);
			orderContext.addObject("zpono", object[5]);
			orderContext.addObject("xmdyb", object[12]);
			orderContext.addObject("zhfkr", object[10]);
			orderContext.addObject("krpch", object[11]);
			orderContext.addObject("zsjtg", object[13]);
			orderContext.addObject("zbzzd", object[14]);
			orderContext.addObject("zyhrq", object[16]);
			orderContext.addObject("zyhjg", object[17]);
			orderContext.addObject("zyhbz", object[18]);
			orderContext.addObject("zbz", object[25]);
			orderContext.addObject("zzbgd", object[24]);
			orderContext.addObject("zchsl", object[22]);
			orderContext.addObject("zchrq", object[23]);
			orderContext.addObject("chwsbz", object[21]);
			orderContext.addObject("omchjh", object[26]);
			orderContext.addObject("zyqbm", object[30]);
			orderContext.addObject("zycdl", object[31]);
			orderContext.addObject("zyqbz", object[32]);
			
		}
		return orderContext;
	}
	
	@RequestMapping("/findStatus")
	public ModelAndView findStatus(){
		ModelAndView orderContext = new ModelAndView(); 
		orderContext.setViewName("/orderManage/orderSearch");
		//查询sql
		StringBuffer find = new StringBuffer();
		find.append("select ")
			.append(" sum(case when zddps='0待评审-预警' then 1 else 0 end),")
			.append(" sum(case when zddps='1待评审-delay' then 1 else 0 end),")
			.append(" sum(case when zddps='2待AE确认转正-预警' then 1 else 0 end),")
			.append(" sum(case when zddps='3待AE确认转正-delay' then 1 else 0 end),")
			.append(" sum(case when zddps='4不及时签发' then 1 else 0 end),")
			.append(" sum(case when zddps='5及时签发' then 1 else 0 end),")
			.append(" sum(case when zddps='6不需评审' then 1 else 0 end),")
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
			.append(" sum(case when zddzt='已完全出货' then 1 else 0 end),")
			.append(" sum(case when zddzt='退货处理' then 1 else 0 end),")
			.append(" sum(case when zddzt='退货完成' then 1 else 0 end)")
			.append(" from zsdt0050")
			.append(" where mandt = ")
			.append(sapConfig.getClient());
		List list = orderManageService.getListForSQL(find.toString());
		Object[] object = null;
		if(list!=null&&list.size()>0){
			object = (Object[]) list.get(0);
			orderContext.addObject("dspyj", object[0]);
			orderContext.addObject("dspd", object[1]);
			orderContext.addObject("dzzyj", object[2]);
			orderContext.addObject("dzzd", object[3]);
			orderContext.addObject("bjsqf", object[4]);
			orderContext.addObject("jsqf", object[5]);
			orderContext.addObject("bxps", object[6]);
			orderContext.addObject("dbz", object[7]);
			orderContext.addObject("dbzyj", object[8]);
			orderContext.addObject("dbzd", object[9]);
			orderContext.addObject("dch", object[10]);
			orderContext.addObject("dchyj", object[11]);
			orderContext.addObject("dchd", object[12]);
			orderContext.addObject("wsdch", object[13]);
			orderContext.addObject("ddc", object[14]);
			orderContext.addObject("ddcyj", object[15]);
			orderContext.addObject("ddcd", object[16]);
			orderContext.addObject("ywqch", object[17]);
			orderContext.addObject("thcl", object[18]);
			orderContext.addObject("thwc", object[19]);
		}
		//查找生产进度
		StringBuffer pro = new StringBuffer();
		pro.append("with cust as(")
			.append(" select distinct ta.vbeln,tb.sortl from vbak ta,kna1 tb where ta.mandt=").append(sapConfig.getClient())
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
		List proList = orderManageService.getListForSQL(pro.toString());
		if(proList!=null&&proList.size()>0){
			object = (Object[]) proList.get(0);
			orderContext.addObject("djh", object[0]);
			orderContext.addObject("yjh", object[1]);
			orderContext.addObject("zz", object[2]);
			orderContext.addObject("ywc", object[3]);
		}
		//查询出货状态
		StringBuffer sum = new StringBuffer();
		sum.append(" select ")
			.append(" nvl(sum(case when a.zchzt='done-及时' then 1 else 0 end),0),")
			.append(" nvl(sum(case when a.zchzt='done-delay' then 1 else 0 end),0),")
			.append(" nvl(sum(case when e.zyhjg='合格' then 1 else 0 end),0),")
			.append(" nvl(sum(case when e.zyhjg='不合格' then 1 else 0 end),0)")
			.append(" from zsdt0048 a")
			.append(" left join zsdt0050 e on a.vgbel = e.vbeln and a.mandt = e.mandt ")
			.append(" where a.mandt = ")
			.append(sapConfig.getClient());
		
		List sumList = orderManageService.getListForSQL(sum.toString());
		if(sumList!=null&&sumList.size()>0){
			object = (Object[]) sumList.get(0);
			orderContext.addObject("jhyq", object[0]);
			orderContext.addObject("jhjs", object[1]);
			orderContext.addObject("yhhg", object[2]);
			orderContext.addObject("yhbhg", object[3]);
		}
		return orderContext;
	}
}
