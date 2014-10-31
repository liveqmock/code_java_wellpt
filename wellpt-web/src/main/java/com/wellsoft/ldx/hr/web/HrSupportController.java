package com.wellsoft.ldx.hr.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rsa.cryptoj.f.iF;
import com.rsa.cryptoj.f.nu;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.org.entity.User;
import com.wellsoft.pt.org.service.UserService;

@Controller
@Scope("prototype")
@RequestMapping("/hrsupport")
public class HrSupportController  extends BaseController{
	@Autowired
	private UserService userService;
	/**
	 * 灯具技术规格书默认值获取
	 * @param tableName
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getUserDetail")
	public void getUserDetail(@RequestParam(value = "id") String userId,
			HttpServletRequest request, HttpServletResponse response){
		User user = userService.getById(userId);
		Map<String,String> map=new HashMap<String,String>();
		if(null!=user){
			map.put("job",user.getMajorJobName());
			map.put("dep",user.getDepartmentName());
		}
		JSONArray json = new JSONArray().fromObject(map);
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
