package com.wellsoft.pt.ldx.web.pdm;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wellsoft.pt.core.web.BaseController;

@Controller
@Scope("prototype")
@RequestMapping("/pdmForm")
public class pdmFormShowController extends BaseController{
	
	/**
	 * 描述：行点击跳转处理逻辑
	 * @param uuid
	 * @param form_uuid
	 * @return
	 */
	@RequestMapping(value="/rowClick")
	public String rowClickMethod(@RequestParam(value="uuid",required=false) String uuid,
			@RequestParam(value="form_uuid",required=false) String form_uuid,
    		Model map){
		
		map.addAttribute("dataUuid", uuid);//uuid:表单的uuid
		map.addAttribute("formUuid", form_uuid);
		map.addAttribute("rowclick", "true");
		map.addAttribute("flag", "rowclick");
		
		return "/pt/pdm/pdmFormShow";
	}

}
