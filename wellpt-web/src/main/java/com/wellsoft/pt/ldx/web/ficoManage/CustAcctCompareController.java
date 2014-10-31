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
 * Description: 财务管理客户未清查询
 *  
 * @author HeShi
 * @date 2014-9-24
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-9-24 	HeShi		2014-9-24		Create
 * </pre>
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/ficoManage")
public class CustAcctCompareController extends BaseController{
	@Resource(name="sapConnectConfig")
	private SAPDbConfig sapConfig;
	@Autowired
	private ISapService sapService;
	
	private PagingInfo pagingInfo = new PagingInfo();
	{
		pagingInfo.setPageSize(15);
	}
	
	@RequestMapping("/custAcctCompare")
	public String custAcctCompare(Model model,HttpServletRequest request) throws Exception {
		String bukrs = request.getParameter("bukrs");
		String kunnr = request.getParameter("kunnr");
		String sortl = request.getParameter("sortl");
		String tip = request.getParameter("tip");
		model.addAttribute("bukrs", bukrs);
		model.addAttribute("kunnr", kunnr);
		model.addAttribute("sortl", sortl);
		model.addAttribute("tip", tip);
		setPage(request);
		model.addAttribute("list", findCustAcctList(bukrs,kunnr,sortl,tip,pagingInfo));
		model.addAttribute("pagingInfo", pagingInfo);
		return forward("/ldx/ficoManage/custAcctCompare");
	}
	

	private List<Object> findCustAcctList(String bukrs, String kunnr, String sortl, String tip, PagingInfo pagingInfo2) {
		//查询sql
		StringBuffer sql = new StringBuffer();
		sql.append(" with acct as(")
			.append(" select bukrs,kunnr,waers,sum(pre) as pre,sum(rec) as rec,sum(oth) as oth from (")
			.append(" select bukrs,kunnr,waers,")
			.append(" case")
			.append(" when umskz = 'A' and bschl>'09' then  -wrbtr")
			.append(" when umskz = 'A' and bschl<'10' then wrbtr")
			.append(" else 0")
			.append(" end as pre,")
			.append(" case")
			.append(" when umskz = ' ' and bschl>'09' then  -wrbtr")
			.append(" when umskz = ' ' and bschl<'10' then wrbtr")
			.append(" else 0")
			.append(" end as rec,")
			.append(" case")
			.append(" when umskz = '3' and bschl>'09' then  -wrbtr")
			.append(" when umskz = '3' and bschl<'10' then wrbtr")
			.append(" else 0")
			.append(" end as oth")
			.append(" from bsid where umskz in ('A', ' ', '3') ")
			.append(" and mandt=").append(sapConfig.getClient());
		if(StringUtils.isNotBlank(bukrs)){
			sql.append(" and bukrs = '").append(bukrs.trim()).append("'");
		}
		if(StringUtils.isNotBlank(kunnr)){
			sql.append(" and kunnr = '").append(kunnr.trim()).append("'");
		}
		sql.append(") group by bukrs,kunnr,waers) ")
			.append(" ,usr as ")
			.append(" (select distinct pernr,ename from pa0001)")
			.append(" ,cust as ")
			.append(" (select distinct kunnr,sortl from kna1 where mandt=")
			.append(sapConfig.getClient());
		if(StringUtils.isNotBlank(kunnr)){
			sql.append(" and kunnr = '").append(kunnr.trim()).append("'");
		}
		sql.append("),prer as")
			.append(" (select a.bukrs, a.kunnr, a.waers, sum(a.zcamount -  case when a.vbeln=' ' then nvl(b.amt, 0) else a.zwoamt end) as yswq")
			.append(" from zfmt0004 a ")
			.append(" left join (select t.zbelnr,t.zposnr_s,sum(to_number(to_char(nvl(t.zcamount * nvl(t.kursf, 1) /nvl(decode(t.zpeinh,0,1,t.zpeinh), 1),0),'FM9999999999.99'))) as amt from zfmt0004 t")
			.append(" where t.zposnr_s != ' ' and t.zposnr is not null and t.zposnr_s != '00000' and t.zrbl != 'A' and t.zdrs = 'C' and t.zcirs = 'C' group by t.zbelnr, t.zposnr_s) b")
			.append(" on a.zbelnr = b.zbelnr and a.zposnr = b.zposnr_s where a.zrbl = 'A' and a.zcirs = 'C'");
		if(StringUtils.isNotBlank(bukrs)){
			sql.append(" and a.bukrs = '").append(bukrs.trim()).append("'");
		}
		if(StringUtils.isNotBlank(kunnr)){
			sql.append(" and a.kunnr = '").append(kunnr.trim()).append("'");
		}
		sql.append(" group by a.bukrs,a.kunnr,a.waers ) ");
		sql.append(" select acct.bukrs,acct.kunnr,cust.sortl,acct.waers,zsrm.ename as srm,zae.ename as ae,zaa.ename as aa,zom.ename as om,acct.pre,acct.rec,acct.oth,nvl(-prer.yswq,0) as yswq,case when acct.pre!=nvl(-prer.yswq,0) then '1' else ' ' end as tip from acct ")
			.append(" left join cust on acct.kunnr = cust.kunnr")
			.append(" left join zfmt0007 b on acct.bukrs = b.bukrs and acct.kunnr = b.kunnr")
			.append(" left join usr zsrm on b.zrsm = zsrm.pernr")
			.append(" left join usr zae on b.zae = zae.pernr")
			.append(" left join usr zaa on b.zaa = zaa.pernr")
			.append(" left join usr zom on b.zom = zom.pernr")
			.append(" left join prer on acct.bukrs = prer.bukrs and acct.kunnr = prer.kunnr and acct.waers = prer.waers")
			.append(" where 1=1 ");
		if(StringUtils.isNotBlank(bukrs)){
			sql.append(" and acct.bukrs ='").append(bukrs.trim()).append("'");
		}
		if(StringUtils.isNotBlank(kunnr)){
			sql.append(" and acct.kunnr ='").append(kunnr.trim()).append("'");
		}
		if(StringUtils.isNotBlank(sortl)){
			sql.append(" and cust.sortl like '%").append(sortl.trim()).append("%'");
		}
		if(StringUtils.isNotBlank(tip)){
			sql.append(" and acct.pre!=nvl(-prer.yswq,0)");
		}
		sql.append(" order by acct.bukrs,acct.kunnr,acct.waers");
		return sapService.findPageListBySql(sql.toString(),pagingInfo);
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
	
}
