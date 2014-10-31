package com.wellsoft.pt.ldx.web.ficoManage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wellsoft.pt.basicdata.sap.config.SAPDbConfig;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.service.ISapService;
import com.wellsoft.pt.ldx.util.StringUtils;

/**
 * 
 * Description: 财务管理客户查找
 *  
 * @author HeShi
 * @date 2014-8-25
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-8-25 	HeShi		2014-8-25		Create
 * </pre>
 *
 */
@Controller
@RequestMapping("/ficoManage")
public class CustSearchController extends BaseController{
	@Resource(name="sapConnectConfig")
	private SAPDbConfig sapConfig;
	@Autowired
	private ISapService sapService;
	
	@RequestMapping("/custSearch")
	public String custSearch(Model model,HttpServletRequest request) throws Exception {
		String bukrs = request.getParameter("bukrs");
		String kunnr = request.getParameter("kunnr");
		String sortl = request.getParameter("sortl");
		String recBukrs = request.getParameter("recBukrs");
		String modify = request.getParameter("modify");
		model.addAttribute("bukrs", bukrs); 
		model.addAttribute("kunnr", kunnr); 
		model.addAttribute("sortl", sortl);
		model.addAttribute("recBukrs", recBukrs);
		model.addAttribute("modify", modify);
		return forward("/ldx/ficoManage/custSearch");
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("searchCustInfo")
	public void searchCustInfo(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		String bukrs = request.getParameter("bukrs");
		String kunnr = request.getParameter("kunnr");
		String sortl = request.getParameter("sortl");
		String recBukrs = request.getParameter("recBukrs");
		StringBuffer queryString = new StringBuffer("");
		queryString.append(" with usr as (select distinct pernr,ename from pa0001 where mandt=").append(sapConfig.getClient()).append(") ")
			.append(" select a.bukrs,a.kunnr,a.sortl,b.ename as om,c.ename as ae,d.ename as aa,e.ename as rsm,f.ename as fico from zfmt0007 a")
			.append(" left join usr b on a.zom=b.pernr")
			.append(" left join usr c on a.zae=c.pernr")
			.append(" left join usr d on a.zaa=d.pernr")
			.append(" left join usr e on a.zrsm=e.pernr")
			.append(" left join usr f on a.zrname=f.pernr")
			.append(" where a.mandt=").append(sapConfig.getClient());
		if(StringUtils.isNotBlank(bukrs)){
			queryString.append(" and a.bukrs='").append(bukrs).append("'");
		}
		if(StringUtils.isNotBlank(kunnr)){
			queryString.append(" and a.kunnr like '%").append(kunnr.trim()).append("'");
		}
		if(StringUtils.isNotBlank(sortl)){
			queryString.append(" and a.sortl like '%").append(sortl.trim().toUpperCase()).append("%'");
		}
		if(StringUtils.isNotBlank(recBukrs)&&"9999".equals(recBukrs)){
			//绿能智造只能选择1000或7200登记
			queryString.append(" and a.bukrs in ('1000','7200','9999')");
		}
		if(StringUtils.isNotBlank(recBukrs)&&"7300".equals(recBukrs)){
			//绿能智造只能选择1000或7200登记
			queryString.append(" and a.bukrs in ('1000','7200','7300')");
		}
		List list = sapService.findListBySql(queryString.toString());
		JSONArray ja = JSONArray.fromObject(list);
		response.getOutputStream().write(ja.toString().getBytes("UTF-8"));
	}
	
}
