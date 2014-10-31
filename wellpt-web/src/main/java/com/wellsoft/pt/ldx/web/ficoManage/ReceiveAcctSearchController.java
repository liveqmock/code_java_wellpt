package com.wellsoft.pt.ldx.web.ficoManage;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wellsoft.pt.basicdata.sap.config.SAPDbConfig;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.service.ISapService;
import com.wellsoft.pt.ldx.util.StringUtils;

/**
 * 
 * Description: 财务管理应收款查询
 *  
 * @author HeShi
 * @date 2014-9-23
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-9-23 	HeShi		2014-9-23		Create
 * </pre>
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/ficoManage")
public class ReceiveAcctSearchController extends BaseController{
	@Resource(name="sapConnectConfig")
	private SAPDbConfig sapConfig;
	@Autowired
	private ISapService sapService;
	
	private PagingInfo pagingInfo = new PagingInfo();
	{
		pagingInfo.setPageSize(20);
	}
	
	@RequestMapping("/receiveAcct")
	public String receiveAcct(Model model,HttpServletRequest request) throws Exception {
		String from = request.getParameter("from");
		String bukrs = request.getParameter("bukrs");
		String kunnr = request.getParameter("kunnr");
		String xblnr = request.getParameter("xblnr");
		String xref1 = request.getParameter("xref1");
		String clear = request.getParameter("clear");
		model.addAttribute("from", from); 
		model.addAttribute("kunnr", kunnr);
		model.addAttribute("xblnr", xblnr);
		model.addAttribute("xref1", xref1);
		model.addAttribute("clear", clear);
		model.addAttribute("bukrs", bukrs);
		setPage(request);
		model.addAttribute("recList", findReceiveAcctList(bukrs,kunnr,xblnr,xref1,clear,pagingInfo));
		model.addAttribute("preList", searchPreReceived(kunnr,bukrs,xblnr,xref1));
		searchSum(model,bukrs,kunnr,xblnr,xref1);
		model.addAttribute("pagingInfo", pagingInfo);
		return forward("/ldx/ficoManage/receiveAcctSearch");
	}
	
	private void setPage(HttpServletRequest request){
		if(pagingInfo==null){
			pagingInfo = new PagingInfo();
		}
		if(pagingInfo.getCurrentPage()<1){
			pagingInfo.setCurrentPage(1);
		}
		String currentPageString = request.getParameter("currentPage");
		String pageSize = request.getParameter("pageSize");
		if(StringUtils.isNotBlank(currentPageString)){
			pagingInfo.setCurrentPage(Integer.valueOf(currentPageString));
		}
		if(StringUtils.isNotBlank(pageSize)){
			pagingInfo.setPageSize(Integer.valueOf(pageSize));
		}
	}
	
	/**
	 * 
	 * 查询应收款信息
	 * 
	 * @param bukrs
	 * @param kunnr
	 * @param xblnr
	 * @param xref1
	 * @param clear
	 * @return
	 */
	private List<Object> findReceiveAcctList(String bukrs,String kunnr,String xblnr,String xref1,String clear,PagingInfo pagingInfo){
		StringBuffer sql = new StringBuffer();
		sql.append(" with yswqtab as (")
			.append(" select a.bstkd,a.vbeln,a.waers,b.kunnr,sum(a.zcamount-a.zwoamt) as yswq from zfmt0004 a,zfmt0003 b  where zrbl = 'A' and a.zcirs='C' and a.zbelnr=b.zbelnr ")
			.append(" and b.bukrs='").append(bukrs).append("'")
			.append(" group by a.bstkd,a.vbeln,a.waers,b.kunnr)")
			.append(" , wqjetab as (")
			.append(" select zuonr,waers,type,bstnk_vf,vgbel,kunnr,sum(wrbtr) as wqje from (")
			.append(" select d.zuonr,case when bschl>'09' then -wrbtr else wrbtr end as wrbtr,waers,k.bstnk_vf,p.vgbel,0 as type,kunnr from bsid d")
			.append(" left join vbrk k on  k.vbeln = d.zuonr and k.mandt = d.mandt ")
			.append(" left join (select distinct vgbel,vbeln from vbrp) p on p.vbeln = d.zuonr ")
			.append(" where umskz=' '")
			.append(" and d.mandt =").append(sapConfig.getClient())
			.append(" and d.bukrs ='").append(bukrs).append("'")
			.append(" and kunnr='").append(kunnr).append("'");
		if(StringUtils.isNotBlank(xblnr)){
			sql.append(" and k.bstnk_vf like '%").append(xblnr.trim()).append("%'");
		}
		if(StringUtils.isNotBlank(xref1)){
			sql.append(" and p.vgbel='").append(StringUtils.addLeftZero(xref1.trim(),10)).append("'");
		}
		sql.append(" )group by zuonr,waers,type,bstnk_vf,vgbel,kunnr)");
		if(StringUtils.isNotBlank(clear)){
			//查询已清项
			sql.append(" , yqx as (")
				.append(" select zuonr,waers,type,bstnk_vf,vgbel,kunnr,sum(wrbtr) as wqje from (")
				.append(" select d.zuonr,case when bschl>'09' then -wrbtr else wrbtr end as wrbtr,waers,k.bstnk_vf,p.vgbel,1 as type,kunnr from bsad d")
				.append(" left join vbrk k on  k.vbeln = d.zuonr and k.mandt = d.mandt ")
				.append(" left join (select distinct vgbel,vbeln from vbrp) p on p.vbeln = d.zuonr ")
				.append(" where umskz=' '")
				.append(" and d.mandt =").append(sapConfig.getClient())
				.append(" and d.bukrs ='").append(bukrs).append("'")
				.append(" and kunnr='").append(kunnr).append("'");
			if(StringUtils.isNotBlank(xblnr)){
				sql.append(" and k.bstnk_vf like '%").append(xblnr.trim()).append("%'");
			}
			if(StringUtils.isNotBlank(xref1)){
				sql.append(" and p.vgbel='").append(StringUtils.addLeftZero(xref1.trim(),10)).append("'");
			}
			sql.append(" )group by zuonr,waers,type,bstnk_vf,vgbel,kunnr)");
		}
		StringBuffer queryString = new StringBuffer(sql.toString());
		queryString.append(" select ta.zuonr,ta.bstnk_vf,ta.vgbel,nvl(tb.yswq,0),0,nvl(ta.wqje,0),ta.type,ta.waers from wqjetab ta")
			.append(" left join yswqtab tb on ta.bstnk_vf = tb.bstkd and ta.vgbel = tb.vbeln and ta.waers = tb.waers and ta.kunnr = tb.kunnr")
			.append(" where ta.kunnr = '").append(kunnr).append("'");
		if(StringUtils.isNotBlank(xblnr)){
			queryString.append(" and ta.bstnk_vf like '%").append(xblnr.trim()).append("%'");
		}
		if(StringUtils.isNotBlank(xref1)){
			queryString.append(" and ta.vgbel='").append(StringUtils.addLeftZero(xref1.trim(),10)).append("'");
		}
		if(StringUtils.isNotBlank(clear)){
			//查询已清项
			queryString
				.append(" union all")
				.append(" select tc.zuonr,tc.bstnk_vf,tc.vgbel,nvl(tb.yswq,0),0,nvl(tc.wqje,0),tc.type,tc.waers from yqx tc")
				.append(" left join yswqtab tb on tc.bstnk_vf = tb.bstkd and tc.vgbel = tb.vbeln and tc.waers = tb.waers and tc.kunnr = tb.kunnr")
				.append(" where tc.kunnr = '").append(kunnr).append("'");
			if(StringUtils.isNotBlank(xblnr)){
				queryString.append(" and tc.bstnk_vf like '%").append(xblnr.trim()).append("%'");
			}
			if(StringUtils.isNotBlank(xref1)){
				queryString.append(" and tc.vgbel='").append(StringUtils.addLeftZero(xref1.trim(),10)).append("'");
			}
		}
		return sapService.findPageListBySql(queryString.toString(),pagingInfo);
	}
	
	/**
	 * 
	 * 查询预收款信息
	 * 
	 * @param kunnr
	 * @param xblnr
	 * @param xref1
	 * @return
	 */
	private List<Object> searchPreReceived(String kunnr,String bukrs,String xblnr,String xref1){
		StringBuffer sql = new StringBuffer("with temp as (")
		    .append(" select sum(zcamount) as amt,zbelnr,zposnr_s from zfmt0004 where zdrs='C' and zcirs='C' group by zbelnr,zposnr_s")
		    .append(" )")
		    .append(" select a.bstkd,a.vbeln,a.zcamount,nvl(c.amt,0),a.zhc,a.zpodat,a.zbamt,a.waers,a.kursf,a.ztnum,a.zanote,a.belnr,a.zuonr,a.zbelnr from zfmt0004 a")
		    .append(" inner join zfmt0003 b on  a.zbelnr = b.zbelnr")
		    .append(" left join temp c on a.zbelnr=c.zbelnr and a.zposnr = c.zposnr_s")
		    .append(" where a.mandt =").append(sapConfig.getClient())
		    .append(" and a.zrbl = 'A'")
		    .append(" and a.zdrs!='C'")
		    .append(" and a.zcamount!=nvl(c.amt,0)")
			.append(" and b.kunnr = '").append(kunnr).append("'")
			.append(" and b.bukrs='").append(bukrs).append("'");
		if(StringUtils.isNotBlank(xblnr)){
			sql.append(" and a.bstkd like '%").append(xblnr.trim()).append("%'");
		}
		if(StringUtils.isNotBlank(xref1)){
			sql.append(" and a.vbeln ='").append(StringUtils.addLeftZero(xref1.trim(),10)).append("'");
		}
		sql.append(" order by a.zbelnr,a.zposnr");
		return sapService.findListBySql(sql.toString());
	}
	
	/**
	 * 
	 * 查询金额合计
	 * 
	 * @param model
	 * @param bukrs
	 * @param kunnr
	 * @param xblnr
	 * @param xref1
	 */
	private void searchSum(Model model,String bukrs,String kunnr,String xblnr,String xref1){
		//预收未清合计
		String yswqSum="";
		//应收金额合计
		String ysjeSum="";
		//未清金额合计
		String wqjeSum="";
		StringBuffer sql = new StringBuffer();
		//---未清金额合计
		sql.append("select 1 as type,waers,sum(wrbtr) as wqje from (")
			.append(" select d.zuonr,case when bschl>'09' then -wrbtr else wrbtr end as wrbtr,waers,k.bstnk_vf,p.vgbel,0 as type,kunnr from bsid d")
			.append(" left join vbrk k on  k.vbeln = d.zuonr and k.mandt = d.mandt")
			.append(" left join (select distinct vgbel,vbeln from vbrp) p on p.vbeln = d.zuonr")
			.append(" where umskz=' '")
			.append(" and d.mandt=").append(sapConfig.getClient())
			.append(" and d.bukrs ='").append(bukrs).append("'")
			.append(" and d.kunnr='").append(kunnr).append("'");
		if(StringUtils.isNotBlank(xblnr)){
			sql.append(" and k.bstnk_vf like '%").append(xblnr.trim()).append("%'");
		}
		if(StringUtils.isNotBlank(xref1)){
			sql.append(" and p.vgbel='").append(StringUtils.addLeftZero(xref1.trim(),10)).append("'");
		}
		sql.append(" )group by waers")
			.append(" union all");
		//---预收未清合计
		sql.append(" select 2 as type,a.waers,sum(a.zcamount-a.zwoamt) as yswq from zfmt0004 a,zfmt0003 b ")
			.append(" where a.mandt =").append(sapConfig.getClient())
			.append(" and a.zrbl = 'A'")
			.append(" and a.zcirs='C'")
			.append(" and a.zbelnr = b.zbelnr ")
			.append(" and b.bukrs='").append(bukrs).append("'")
			.append(" and b.kunnr = '").append(kunnr).append("'");
		if(StringUtils.isNotBlank(xblnr)){
			sql.append(" and a.bstkd like '%").append(xblnr.trim()).append("%'");
		}
		if(StringUtils.isNotBlank(xref1)){
			sql.append(" and a.vbeln = '").append(StringUtils.addLeftZero(xref1.trim(),10)).append("'");
		}
		sql.append(" group by a.waers ")
			.append(" union all");
		//---应收金额合计
		sql.append(" select 3 as type,d.waers,sum(p.netwr) as ysje from vbrp p")
			.append(" left join (select distinct mandt,zuonr,kunnr,waers from bsid ")
			.append(" where bukrs ='").append(bukrs).append("'")
			.append(" )d on p.mandt = d.mandt and p.vbeln=d.zuonr")
			.append(" left join vbrk k on k.mandt = p.mandt and k.vbeln=d.zuonr")
			.append(" where p.mandt=").append(sapConfig.getClient())
			.append(" and d.kunnr = '").append(kunnr).append("'");
		if(StringUtils.isNotBlank(xblnr)){
			sql.append(" and k.bstnk_vf like '%").append(xblnr.trim()).append("%'");
		}
		if(StringUtils.isNotBlank(xref1)){
			sql.append(" and p.vgbel='").append(StringUtils.addLeftZero(xref1.trim(),10)).append("'");
		}
		sql.append(" group by d.waers order by type,waers");
		List<Object> list = sapService.findListBySql(sql.toString());
		if(null!=list && list.size()>0){
			for(Object object:list){
				Object[] objects = (Object[])object;
				if("1".equals(objects[0].toString())){
					wqjeSum+=objects[1].toString()+":"+objects[2].toString()+" ;";
				}else if("2".equals(objects[0].toString())){
					yswqSum+=objects[1].toString()+":"+objects[2].toString()+" ;";
				}else{
					ysjeSum+=objects[1].toString()+":"+objects[2].toString()+" ;";
				}
			}
		}
		model.addAttribute("wqjeSum",wqjeSum);
		model.addAttribute("yswqSum",yswqSum);
		model.addAttribute("ysjeSum",ysjeSum);
	}
	
}
