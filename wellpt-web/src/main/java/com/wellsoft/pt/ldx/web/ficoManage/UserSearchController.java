package com.wellsoft.pt.ldx.web.ficoManage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.service.ISapService;
import com.wellsoft.pt.ldx.util.StringUtils;

/**
 * 
 * Description: 财务管理用户查找
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
public class UserSearchController extends BaseController{
	@Autowired
	private ISapService sapService;
	
	@RequestMapping("/userSearch")
	public String custSearch(Model model,HttpServletRequest request) throws Exception {
		String bukrs = request.getParameter("bukrs");
		String kunnr = request.getParameter("kunnr");
		String name = request.getParameter("name");
		String code = request.getParameter("code");
		model.addAttribute("bukrs", bukrs); 
		model.addAttribute("kunnr", kunnr); 
		model.addAttribute("name", name);
		model.addAttribute("code", code);
		model.addAttribute("list", searchUserList(bukrs,kunnr,name,code));
		return forward("/ldx/ficoManage/userSearch");
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("searchUserInfo")
	public void searchUserInfo(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		String bukrs = request.getParameter("bukrs");
		String kunnr = request.getParameter("kunnr");
		String name = request.getParameter("name");
		String code = request.getParameter("code");
		List list = searchUserList(bukrs,kunnr,name,code);
		JSONArray ja = JSONArray.fromObject(list);
		response.getOutputStream().write(ja.toString().getBytes("UTF-8"));
	}
	
	/**
	 * 
	 * 查询用户信息
	 * 
	 * @param bukrs
	 * @param kunnr
	 * @param name
	 * @param code
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private List searchUserList(String bukrs,String kunnr,String name,String code){
		String query = "select distinct userid,a.ename,b.usrid,type from ("
			+ " select zrsm as userid,'RSM' as type from zfmt0007 a where kunnr='"+kunnr+"' and bukrs='"+bukrs+"'"
			+ " union all "
			+ " select zom,'OM' from zfmt0007 a where kunnr='"+kunnr+"' and bukrs='"+bukrs+"'"
			+ " union all "
			+ " select zaa,'AA' from zfmt0007 a where kunnr='"+kunnr+"' and bukrs='"+bukrs+"'"
			+ " union all "
			+ " select zae,'AE' from zfmt0007 a where kunnr='"+kunnr+"' and bukrs='"+bukrs+"'"
			+ " union all "
			+ " select zrname,'AR' from zfmt0007 a where kunnr='"+kunnr+"' and bukrs='"+bukrs+"'"
			+ " )temp " 
			+ " left join pa0001 a on temp.userid=a.pernr"
			+ " left join pa0105 b on temp.userid=b.pernr and b.usrty = 'MAIL'"
			+ " where temp.userid!=' '";
		if(StringUtils.isNotBlank(name)||StringUtils.isNotBlank(code)){
			//通过用户名及工号查询
			query+="union all select distinct pernr,ename,pernr,'' from pa0001 where ename like '%"+StringUtils.nullToString(name)+"%' ";
			if(StringUtils.isNotBlank(code)){
				query +="and pernr = '"+StringUtils.nullToString(code)+"'";
			}
		}
		return sapService.findListBySql(query);
	}
	
}
