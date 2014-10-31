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
import com.wellsoft.pt.ldx.service.ficoManage.IDefaultBaoeDataService;
import com.wellsoft.pt.ldx.util.StringUtils;

/**
 * 
 * Description: 财务管理未知客户摘要信息维护管理控制器
 *  
 * @author qiulb
 * @date 2014-9-18
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-9-18 	qiulb		2014-9-18		Create
 * </pre>
 *
 */
@Controller
@RequestMapping("/ficoDefault/Sgtxt")
public class EmptyKunnrSgtxtController extends BaseController{
	
	@Autowired
	private IDefaultBaoeDataService defaultBaoeService;
	
	@Resource(name="sapConnectConfig")
	private SAPDbConfig sapConfig;
	
	@RequestMapping("/add")
	public String add(Model model) throws Exception {
		model.addAttribute("method", "1"); //创建
		return forward("/ldx/ficoManage/emptyKunnrSgtxt");
	}
	
	@RequestMapping("/modify")
	public String modify(Model model,@RequestParam(value = "sgtxt") String sgtxt) throws Exception {
		model.addAttribute("method", "0"); //更改
		
		//执行sql
		String sql = "select zfmt0019.sgtxt,zfmt0019.kunnr,zfmt0019.zcrem,kna1.sortl from zfmt0019 join kna1 on zfmt0019.kunnr = kna1.kunnr and zfmt0019.mandt = kna1.mandt where zfmt0019.mandt = " + sapConfig.getClient() + " and zfmt0019.sgtxt = '" + sgtxt + "'";
		List<Object> lit = defaultBaoeService.findListBySql(sql);
		
		if (lit != null && lit.size() > 0){
			Object ob1 = lit.get(0);
			Object[] ob2 = (Object[])ob1;
			model.addAttribute("sgtxt", ob2[0]);
			model.addAttribute("kunnr", StringUtils.removeLeftZero(ob2[1].toString()));
			model.addAttribute("zcrem", ob2[2]);
			model.addAttribute("sortl", ob2[3]);
		}		
		
		return forward("/ldx/ficoManage/emptyKunnrSgtxt");
	}
	

}
