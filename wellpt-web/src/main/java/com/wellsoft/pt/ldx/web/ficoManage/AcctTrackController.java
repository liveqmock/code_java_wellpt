package com.wellsoft.pt.ldx.web.ficoManage;

import java.util.ArrayList;
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
import com.wellsoft.pt.ldx.model.ficoManage.Zfmt0003;
import com.wellsoft.pt.ldx.service.ISapService;
import com.wellsoft.pt.ldx.util.StringUtils;
import com.wellsoft.pt.security.core.userdetails.UserDetails;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

/**
 * 
 * Description: 到款资料跟踪列表
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
public class AcctTrackController extends BaseController{
	@Resource(name="sapConnectConfig")
	private SAPDbConfig sapConfig;
	@Autowired
	private ISapService sapService;
	
	private PagingInfo pagingInfo = new PagingInfo();
	{
		pagingInfo.setPageSize(10);
	}
	
	@RequestMapping("/acctTrack")
	public String acctTrack(Model model,HttpServletRequest request) throws Exception {
		String zsname = request.getParameter("zsname");
		String bukrs = request.getParameter("bukrs");
		String zbelnr = request.getParameter("zbelnr");
		String kunnr = request.getParameter("kunnr");
		String sortl = request.getParameter("sortl");
		String zremind = request.getParameter("zremind");
		String zdrs = request.getParameter("zdrs");
		String zcirs = request.getParameter("zcirs");
		String sepStatus = request.getParameter("sepStatus");
		model.addAttribute("zsname", zsname); 
		model.addAttribute("bukrs", bukrs);
		model.addAttribute("zbelnr", zbelnr);
		model.addAttribute("kunnr", kunnr);
		model.addAttribute("sortl", sortl);
		model.addAttribute("kunnr", kunnr);
		model.addAttribute("zremind", zremind);
		model.addAttribute("zdrs", zdrs);
		model.addAttribute("zcirs", zcirs);
		model.addAttribute("sepStatus", sepStatus);
		setPage(request);
		model.addAttribute("list", findAcctList(zsname,bukrs,zbelnr,kunnr,sortl,zremind,zdrs,zcirs,sepStatus,pagingInfo));
		model.addAttribute("pagingInfo", pagingInfo);
		return forward("/ldx/ficoManage/acctTrack");
	}
	
	private List<Zfmt0003> findAcctList(String zsname, String bukrs, String zbelnr,String kunnr,String sortl, String zremind, String zdrs,
			String zcirs, String sepStatus, PagingInfo pagingInfo2) {
		String userCode = getUserCode();
		String client = sapConfig.getClient();//客户端
		StringBuffer sql = new StringBuffer(
				"select a.bukrs,a.zbelnr,nvl(b.ename,' '),a.kunnr,a.sortl,a.waers,a.sgtxt,a.hkont,a.zcamount,a.zdrs,a.zcirs,nvl(c.usrid,' ') ")
				.append(" ,a.zremind,a.zetime,a.zdoip,a.zcheck,a.zds,a.bldat,a.budat,a.zcrem,a.zpost,a.zrdate,a.ztext,d.bstkd  ")
				.append(" from zfmt0003 a ")
				.append(" left join (select distinct pernr,ename from pa0001)b on a.zsname = b.pernr ")
				.append(" left join (select pernr,max(usrid) as usrid from pa0105 where usrty='MAIL' group by pernr) c on a.zsname = c.pernr ")
				.append(" left join (select zbelnr,max(bstkd) as bstkd from zfmt0004 where zrbl='A' and vbeln=' ' and zcamount<>zwoamt group by zbelnr) d on a.zbelnr = d.zbelnr ")
				.append(" where a.mandt = '").append(client).append("' ");
		if(StringUtils.isNotBlank(zsname)){//业务员
			sql.append(" and b.ename like '%").append(zsname).append("%' ");
		}
		if(StringUtils.isNotBlank(zbelnr)){//凭证编号
			sql.append(" and a.zbelnr like '%").append(zbelnr).append("%' ");
		}
		if(StringUtils.isNotBlank(bukrs)){//公司代码
			sql.append(" and a.bukrs like '%").append(bukrs).append("%' ");
		}
		if(StringUtils.isNotBlank(kunnr)){//客户编号
			sql.append(" and a.kunnr like '%").append(kunnr).append("%' ");
		}
		if(StringUtils.isNotBlank(sortl)){//客户简称
			sql.append(" and a.sortl like '%").append(sortl.toUpperCase()).append("%' ");
		}
		if(StringUtils.isNotBlank(zdrs)){//预收状态
			sql.append(" and a.zdrs = '").append(zdrs).append("' ");
		}
		if(StringUtils.isNotBlank(zcirs)){//流转状态
			sql.append(" and a.zcirs = '").append(zcirs).append("' ");
		}
		if(StringUtils.isNotBlank(zremind)){//提醒状态
			sql.append(" and a.zremind = '").append(zremind).append("' ");
		}
		if(StringUtils.isNotBlank(userCode)){//对应会计id
			sql.append(" and exists (select 1 from zfmt0007 b where b.bukrs = a.bukrs and b.kunnr = a.kunnr ")
				.append(" and b.zrname = '")
				.append(userCode)
				.append("') ");
		}
		if(StringUtils.isNotBlank(sepStatus)){//到账分解状态
			if("FC".equals(sepStatus)){
				sql.append(" and exists (select 1 from zfmt0004 d where d.zdrs='F' and d.zcirs='C' and d.zbelnr = a.zbelnr) ");
			}
		}
		sql.append(" order by a.bldat desc,a.zbelnr asc");
		List<Object> list = sapService.findPageListBySql(sql.toString(),pagingInfo);
		List<Zfmt0003> result = new ArrayList<Zfmt0003>();
		if(null!=list && list.size()>0){
			Zfmt0003 zfmt0003;
			for(Object obj:list){
				Object[] ob = (Object[])obj;
				zfmt0003 = new Zfmt0003();
				zfmt0003.setBukrs(ob[0].toString());//公司代码
				zfmt0003.setZbelnr(ob[1].toString());//凭证编号（流水号）
				zfmt0003.setZsname(ob[2].toString());//业务员
				zfmt0003.setKunnr(ob[3].toString());//客户编号
				zfmt0003.setSortl(ob[4].toString());//客户简称
				zfmt0003.setWaers(ob[5].toString());//收款币别
				zfmt0003.setSgtxt(ob[6].toString());//业务对象（摘要）
				zfmt0003.setHkont(ob[7].toString());//银行类科目
				zfmt0003.setZcamount(StringUtils.getCurrencyStr(ob[8].toString()));//收款金额
				zfmt0003.setZdrs(ob[9].toString());//预收状态
				zfmt0003.setZcirs(ob[10].toString());//流转状态
				zfmt0003.setMail(ob[11].toString());//Email
				zfmt0003.setZremind(StringUtils.nullToString(ob[12]));//提醒
				zfmt0003.setZetime(StringUtils.nullToString(ob[13]));//最后提醒时间
				zfmt0003.setZdoip(StringUtils.nullToString(ob[14]));
				zfmt0003.setZcheck(StringUtils.nullToString(ob[15]));
				zfmt0003.setZds(StringUtils.nullToString(ob[16]));
				zfmt0003.setBldat(StringUtils.nullToString(ob[17]));
				zfmt0003.setBudat(StringUtils.nullToString(ob[18]));
				zfmt0003.setZcrem(StringUtils.nullToString(ob[19]));
				zfmt0003.setZpost(StringUtils.nullToString(ob[20]));
				zfmt0003.setZrdate(StringUtils.nullToString(ob[21]));
				zfmt0003.setZtext(StringUtils.nullToString(ob[22]));
				zfmt0003.setBstkd(StringUtils.nullToString(ob[23]));
				result.add(zfmt0003);
			}
		}
		return result;
	}
	
	/**
	 * 
	 * 获取当前登录用户工号
	 * 
	 * @return
	 */
	private String getUserCode() {
		UserDetails userDetail = SpringSecurityUtils.getCurrentUser();
		return userDetail.getEmployeeNumber(); 
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
