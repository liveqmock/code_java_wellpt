package com.wellsoft.pt.ldx.web.maindata;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.service.BoUserService;
import com.wellsoft.pt.org.entity.User;

@Controller
@Scope("prototype")
@RequestMapping("/maindata")
public class BoUserController extends BaseController {
	@Autowired
	private BoUserService boUserService;

	@RequestMapping("/preAddBo")
	public String preAdd(@RequestParam(value = "objectUuid") String objectUuid, Model model) {
		List<Object> list = boUserService
				.findListBySql("select uuid,department_name,user_name,bouser,bopwd from org_user where uuid='"
						+ objectUuid + "'");
		Object object = list.get(0);
		User u = new User();
		if (object instanceof Object[]) {
			if (((Object[]) object)[0] != null) {
				u.setUuid(((Object[]) object)[0].toString());
			}
			if (((Object[]) object)[1] != null) {
				u.setDepartmentName(((Object[]) object)[1].toString());
			}
			if (((Object[]) object)[2] != null) {
				u.setUserName(((Object[]) object)[2].toString());
			}
			if (((Object[]) object)[3] != null) {
				u.setBoUser(((Object[]) object)[3].toString());
			}
			if (((Object[]) object)[4] != null) {
				u.setBoPwd(((Object[]) object)[4].toString());
			}
		}
		model.addAttribute("objectView", u);
		return forward("/maindata/addBo");
	}

}
