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
 * Description: 财务管理其他应收款查询
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
public class OtherAcctSearchController extends BaseController{
	@Resource(name="sapConnectConfig")
	private SAPDbConfig sapConfig;
	@Autowired
	private ISapService sapService;
	
	private PagingInfo pagingInfo = new PagingInfo();
	{
		pagingInfo.setPageSize(20);
	}
	
	@RequestMapping("/otherAcct")
	public String otherAcct(Model model,HttpServletRequest request) throws Exception {
		String from = request.getParameter("from");
		String bukrs = request.getParameter("bukrs");
		String kunnr = request.getParameter("kunnr");
		String belnr = request.getParameter("belnr");
		String sgtxt = request.getParameter("sgtxt");
		model.addAttribute("from", from); 
		model.addAttribute("bukrs", bukrs);
		model.addAttribute("kunnr", kunnr);
		model.addAttribute("belnr", belnr);
		model.addAttribute("sgtxt", sgtxt);
		setPage(request);
		model.addAttribute("recList", findOtherAcctList(bukrs,kunnr,belnr,sgtxt,pagingInfo));
		model.addAttribute("preList", searchPreReceived(kunnr,bukrs));
		searchSum(model,bukrs,kunnr,belnr,sgtxt);
		model.addAttribute("pagingInfo", pagingInfo);
		return forward("/ldx/ficoManage/otherAcctSearch");
	}
	
	/**
	 * 
	 * 查询客户预收款信息
	 * 
	 * @param kunnr
	 * @param bukrs
	 * @return
	 */
	private List<Object> searchPreReceived(String kunnr, String bukrs) {
		StringBuffer sql = new StringBuffer("select a.bstkd,a.vbeln,a.zcamount,a.zwoamt,a.zhc,a.zpodat,a.zbamt,a.waers,a.kursf,a.ztnum,a.zanote,a.belnr,a.zuonr,a.zbelnr from zfmt0004 a,zfmt0003 b ");
		sql.append(" where a.mandt =").append(sapConfig.getClient())
			.append(" and a.zrbl = 'A'")
			.append(" and a.zbelnr = b.zbelnr")
			.append(" and a.zcirs='C'")
			.append(" and a.zcamount!=a.zwoamt")
			.append(" and b.kunnr = '").append(kunnr).append("'")
			.append(" and b.bukrs = '").append(bukrs).append("'");
		sql.append(" order by a.zbelnr,a.zposnr");
		return sapService.findListBySql(sql.toString());
	}

	/**
	 * 
	 * 查询其他应收款信息
	 * 
	 * @param bukrs
	 * @param kunnr
	 * @param belnr
	 * @param sgtxt
	 * @param pagingInfo2
	 * @return
	 */
	private List<Object> findOtherAcctList(String bukrs, String kunnr, String belnr, String sgtxt, PagingInfo pagingInfo2) {
		StringBuffer queryString = new StringBuffer("");
		queryString.append(" select belnr,sgtxt,waers,sum(case when bschl>'09' then -wrbtr else wrbtr end) as wrbtr,1 as type,kunnr from bsid d")
			.append(" where umskz='3'")
			.append(" and mandt=").append(sapConfig.getClient())
			.append(" and bukrs='").append(bukrs).append("'")
			.append(" and kunnr='").append(kunnr).append("'");
		if(StringUtils.isNotBlank(belnr)){
			queryString.append(" and belnr='").append(belnr.trim()).append("'");
		}
		if(StringUtils.isNotBlank(sgtxt)){
			queryString.append(" and sgtxt like '%").append(sgtxt.trim()).append("%'");
		}
		queryString.append(" group by belnr,sgtxt,waers,kunnr order by belnr,sgtxt,waers");
		return sapService.findPageListBySql(queryString.toString(),pagingInfo);
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
	 * 查询金额合计
	 * 
	 * @param model
	 * @param bukrs
	 * @param kunnr
	 * @param belnr
	 * @param sgtxt
	 */
	private void searchSum(Model model,String bukrs,String kunnr,String belnr,String sgtxt){
		//预收未清合计
		String yswqSum="";
		//未清金额合计
		String wqjeSum="";
		StringBuffer sql = new StringBuffer();
		//---未清金额合计
		sql.append("select 1 as type,waers,sum(wrbtr) as wqje from (")
			.append(" select waers,sum(case when bschl>'09' then -wrbtr else wrbtr end) as wrbtr,1 as type,kunnr from bsid d")
			.append(" where umskz='3'")
			.append(" and mandt=").append(sapConfig.getClient())
			.append(" and bukrs='").append(bukrs).append("'")
			.append(" and kunnr='").append(kunnr).append("'");
		if(StringUtils.isNotBlank(belnr)){
			sql.append(" and belnr='").append(belnr.trim()).append("'");
		}
		if(StringUtils.isNotBlank(sgtxt)){
			sql.append(" and sgtxt like '%").append(sgtxt.trim()).append("%'");
		}
		sql.append(" group by belnr,sgtxt,waers,kunnr)group by waers")
			.append(" union all");
		//---预收未清合计
		sql.append(" select 2 as type,a.waers,sum(a.zcamount-a.zwoamt) as yswq from zfmt0004 a,zfmt0003 b ")
			.append(" where a.mandt =").append(sapConfig.getClient())
			.append(" and a.zrbl = 'A'")
			.append(" and a.zcirs='C'")
			.append(" and a.zbelnr = b.zbelnr ")
			.append(" and b.bukrs = '").append(bukrs).append("'")
			.append(" and b.kunnr = '").append(kunnr).append("'");
		sql.append(" group by a.waers order by type,waers");
		List<Object> list = sapService.findListBySql(sql.toString());
		if(null!=list && list.size()>0){
			for(Object object:list){
				Object[] objects = (Object[])object;
				if("1".equals(objects[0].toString())){
					wqjeSum+=objects[1].toString()+":"+objects[2].toString()+" ;";
				}else if("2".equals(objects[0].toString())){
					yswqSum+=objects[1].toString()+":"+objects[2].toString()+" ;";
				}
			}
		}
		model.addAttribute("wqjeSum",wqjeSum);
		model.addAttribute("yswqSum",yswqSum);
	}
	
}
