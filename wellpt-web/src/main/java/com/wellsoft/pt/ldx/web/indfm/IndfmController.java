package com.wellsoft.pt.ldx.web.indfm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.org.entity.User;
import com.wellsoft.pt.org.service.DepartmentService;
import com.wellsoft.pt.org.service.UserService;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

@Controller
@RequestMapping("/indfm")
public class IndfmController extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private DepartmentService departmentService;

	@RequestMapping(value = "/kqxx")
	public String kqxx(HttpServletRequest request, HttpServletResponse response, Model model) {
		String lcpUserId = SpringSecurityUtils.getCurrentUserId();
		User user = userService.getById(lcpUserId);
		String employeeNumber = user.getEmployeeNumber();
		model.addAttribute("lcpUserId", lcpUserId);
		model.addAttribute("employeeNumber", employeeNumber);
		return forward("indfm/kqxx");
	}

	@RequestMapping(value = "/skxx")
	public String skxx(HttpServletRequest request, HttpServletResponse response, Model model) {
		String lcpUserId = SpringSecurityUtils.getCurrentUserId();
		User user = userService.getById(lcpUserId);
		String employeeNumber = user.getEmployeeNumber();
		model.addAttribute("lcpUserId", lcpUserId);
		model.addAttribute("employeeNumber", employeeNumber);
		return forward("indfm/skxx");
	}

	@RequestMapping(value = "/ygkqxx")
	public String ygkqhz(HttpServletRequest request, HttpServletResponse response, Model model) {
		String lcpUserId = SpringSecurityUtils.getCurrentUserId();
		User user = userService.getById(lcpUserId);
		String employeeNumber = user.getEmployeeNumber();
		String userName = user.getUserName();
		model.addAttribute("lcpUserId", lcpUserId);
		model.addAttribute("employeeNumber", employeeNumber);
		model.addAttribute("userName", userName);
		return forward("indfm/ygkqxx");
	}

	@RequestMapping(value = "/ygskxx")
	public String ygskxx(HttpServletRequest request, HttpServletResponse response, Model model) {
		String lcpUserId = SpringSecurityUtils.getCurrentUserId();
		User user = userService.getById(lcpUserId);
		String employeeNumber = user.getEmployeeNumber();
		String userName = user.getUserName();
		model.addAttribute("lcpUserId", lcpUserId);
		model.addAttribute("employeeNumber", employeeNumber);
		model.addAttribute("userName", userName);
		return forward("indfm/ygskxx");
	}

	@RequestMapping(value = "/ygpxxx")
	public String ygpxxx(HttpServletRequest request, HttpServletResponse response, Model model) {
		String lcpUserId = SpringSecurityUtils.getCurrentUserId();
		User user = userService.getById(lcpUserId);
		String employeeNumber = user.getEmployeeNumber();
		String userName = user.getUserName();
		model.addAttribute("lcpUserId", lcpUserId);
		model.addAttribute("employeeNumber", employeeNumber);
		model.addAttribute("userName", userName);
		return forward("indfm/ygpxxx");
	}
}
