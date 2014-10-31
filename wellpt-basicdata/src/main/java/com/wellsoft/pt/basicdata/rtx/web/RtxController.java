package com.wellsoft.pt.basicdata.rtx.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wellsoft.pt.basicdata.rtx.entity.Rtx;
import com.wellsoft.pt.basicdata.rtx.service.RtxService;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.org.entity.User;
import com.wellsoft.pt.org.service.UserService;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

/**
 * 
 * Description: Rtx设置控制层
 *  
 * @author zhouyq
 * @date 2013-6-17
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-6-17.1	zhouyq		2013-6-17		Create
 * </pre>
 *
 */
@Controller
@RequestMapping("/basicdata/rtx")
public class RtxController extends BaseController {
	@Autowired
	private RtxService rtxService;
	@Autowired
	private UserService userService;

	/**
	 * 跳转到Rtx设置界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "")
	public String rtx() {
		return forward("/basicdata/rtx/rtx");
	}

	/**
	 * 单点登录
	 * 
	 * @return
	 */
	@RequestMapping(value = "singlesignon")
	public String singlesignon(Model model) {
		Rtx rtx = rtxService.getAll().get(0);
		String currentId = SpringSecurityUtils.getCurrentUserId();
		User user = userService.getById(currentId);
		//判断是否启用用户简称
		if (rtx.getIsEnableAbbreviation()) {
			String loginName = user.getLoginName();
			model.addAttribute("loginName", loginName);
		} else {
			String loginName = user.getUserName();
			model.addAttribute("loginName", loginName);
		}
		model.addAttribute("rtxClientDownloadAddress", rtx.getRtxClientDownloadAddress());
		model.addAttribute("rtxServerIp", rtx.getRtxServerIp());
		model.addAttribute("rtxServerPort", rtx.getRtxServerPort());
		System.out.println("跳转到实现单点登录界面");
		return forward("/basicdata/rtx/singlesignon");

	}

	/**
	 * 
	 * 判断是否启用rtx
	 * 
	 * @return
	 */
	@RequestMapping(value = "isRtxEnable")
	@ResponseBody
	public Boolean isRtxEnable() {
		Boolean result = rtxService.isRtxEnable();
		return result;
	}
}
