package com.wellsoft.pt.ldx.web.ssoWeb;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.service.ssoService.ISsoService;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

@Controller
@Scope("prototype")
@RequestMapping("/sso")
public class ssoController extends BaseController{
	@Autowired
	private ISsoService iSsoService;
	/**
	 * 登录查询
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/loginFind", method = RequestMethod.POST)
	public void loginBoReport(HttpServletRequest request, HttpServletResponse response){
		String lcpUserId = SpringSecurityUtils.getCurrentUserId();
		List list = iSsoService.get(lcpUserId);
		JSONArray json = new JSONArray().fromObject(list);
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改用户映射
	 * @param username
	 * @param password
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/updatePas", method = RequestMethod.POST)
	public void updatePassword(@RequestParam(value = "username")String username,@RequestParam(value = "oldpassword")String oldpassword,
			@RequestParam(value = "newpassword")String newpassword,HttpServletRequest request, HttpServletResponse response){
		int num = iSsoService.update(username, oldpassword,newpassword);
		try {
			response.getWriter().print(num>0?"成功":"失败");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 添加用户映射
	 * @param lcpuserid
	 * @param username
	 * @param password
	 */
	@RequestMapping(value = "/adduser", method = RequestMethod.POST)
	public void addUser(@RequestParam(value = "lcpuserid")String lcpuserid,@RequestParam(value = "username")String username
			,@RequestParam(value = "password")String password,HttpServletRequest request, HttpServletResponse response){
		int num = iSsoService.save(lcpuserid,username, password);
		try {
			response.getWriter().print(num>0?"成功":"失败");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
