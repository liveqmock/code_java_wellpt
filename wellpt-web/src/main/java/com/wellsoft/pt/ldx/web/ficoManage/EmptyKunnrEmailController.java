package com.wellsoft.pt.ldx.web.ficoManage;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wellsoft.pt.basicdata.sap.config.SAPDbConfig;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.service.ficoManage.IEmptyKunnrEmailService;
import com.wellsoft.pt.ldx.util.StringUtils;

/**
 * 
 * Description: 财务管理客户对应表维护
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
@RequestMapping("/ficoManage/emptyKunnrEmail")
public class EmptyKunnrEmailController extends BaseController{
	
	@Autowired
	private IEmptyKunnrEmailService emptyKunnrEmailService;
	@Resource(name="sapConnectConfig")
	private SAPDbConfig sapConfig;
	
	@RequestMapping("/modify")
	public String modify(Model model,@RequestParam(value = "zbelnr") String zbelnr) throws Exception {
		String sql = "select ztext from zfmt0003 where zbelnr='"+zbelnr+"' and mandt="+sapConfig.getClient();
		List<Object> list = emptyKunnrEmailService.findListBySql(sql);
		model.addAttribute("zbelnr",zbelnr);
		if(null!=list&&list.size()>0){
			model.addAttribute("ztext",StringUtils.nullToString(list.get(0)));
		}
		return forward("/ldx/ficoManage/emptyKunnrEmail");
	}
	
}
