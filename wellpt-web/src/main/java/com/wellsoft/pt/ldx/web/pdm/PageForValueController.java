package com.wellsoft.pt.ldx.web.pdm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.wellsoft.pt.ldx.service.pdm.DefaultValueService;

@Controller
@Scope("prototype")
@RequestMapping("/pageForValues")
public class PageForValueController extends BaseController{
	@Autowired
	private DefaultValueService dvService;
	
	/**
	 * 可行性报告charter取值
	 * @param xmdh
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/queryCharterToAR", method = RequestMethod.POST)
	public void forCharterToAnalysisReport(@RequestParam(value = "xmdh") String xmdh
			,HttpServletRequest request, HttpServletResponse response){
		StringBuffer sql = new StringBuffer("select t.cpdl,t.cpxl,t.cpxh,t.xmms,t.mbbomcb,to_char(t.xmqdrq,'yyyy-mm-dd'),")
			.append("to_char(t.sytgrq,'yyyy-mm-dd'),to_char(t.epdxrq,'yyyy-mm-dd')")
			.append(",to_char(t.jzrq,'yyyy-mm-dd'),to_char(t.klcrq,'yyyy-mm-dd'),t.yxj");
		sql.append(",t.uuid from ")
		.append("(select * from uf_leedarson_djxmsqdfc where ")
		.append("xmdh='").append(xmdh).append("' and zt='11'")
		.append(" order by rq desc)")
		.append(" t where rownum=1");
	
		List list1 = dvService.queryBySql(sql.toString());
		Object[] object = (Object[]) list1.get(0);
		String cpdl = object[0].toString();
		String uuid = object[11].toString();
		
		//3个从表取值
		StringBuffer sql2 = new StringBuffer("select ");
		if(cpdl.indexOf("1")>=0){
			sql2.append("u.fbsid from uf_leedarson_djxmsqdfc t ")
			.append("left join uf_tsdsjghbdtbd_rl y on(t.uuid=y.mainform_data_uuid) left join uf_tsdsjghbdtbd u on(y.data_uuid=u.uuid)");
		}else if(cpdl.indexOf("3")>=0){
			sql2.append("u.fbsid from uf_leedarson_djxmsqdfc t ")
			.append("left join uf_mbdsjghbdtbd2_rl y on(t.uuid=y.mainform_data_uuid) left join uf_mbdsjghbdtbd2 u on(y.data_uuid=u.uuid)");
		}else if(cpdl.indexOf("2")>=0){
			sql2.append("u.fbsid from uf_leedarson_djxmsqdfc t ")
			.append("left join uf_xddsjghbdtbd2_rl y on(t.uuid=y.mainform_data_uuid) left join uf_xddsjghbdtbd2 u on(y.data_uuid=u.uuid)");
		}else{
			sql2.append(" * from uf_leedarson_djxmsqdfc t");
			xmdh = "";
		}
		sql2.append(" where ").append(" t.xmdh='"+xmdh+"' and t.uuid='"+uuid+"'");
		
		List list2 = dvService.queryBySql(sql2.toString());
		
		Map map = new HashMap();
		map.put("charter", list1);
		map.put("cpid", list2);
		
		JSONArray json = new JSONArray().fromObject(map);
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取EP默认值
	 * @param tableName
	 * @param whereSql
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getDefaultForEP", method = RequestMethod.POST)
	public void getDefaultForEP(@RequestParam(value = "tableName") String tableName,@RequestParam(value = "whereSql") String whereSql
			,HttpServletRequest request, HttpServletResponse response){
		List list = dvService.query(tableName, whereSql);
		JSONArray json = new JSONArray().fromObject(list);
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * 灯具各阶段设计评审表头取值
	 * @param xmdh
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getCharterToDJSJPS", method = RequestMethod.POST)
	public void getCharterToDJSJPS(@RequestParam(value = "xmdh") String xmdh
			,HttpServletRequest request, HttpServletResponse response){
		StringBuffer sql = new StringBuffer("select t.cpdl,t.cpxl,t.cpxh,t.xmms from ")
		.append(" (select * from uf_leedarson_djxmsqdfc where ")
		.append("xmdh='").append(xmdh).append("' and zt='11'")
		.append(" order by rq desc)")
		.append(" t where rownum=1");
		
		List list = dvService.queryBySql(sql.toString());
		JSONArray json = new JSONArray().fromObject(list);
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public String createUUid(){
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replaceAll("-", "");
		return uuid;
	}
}
