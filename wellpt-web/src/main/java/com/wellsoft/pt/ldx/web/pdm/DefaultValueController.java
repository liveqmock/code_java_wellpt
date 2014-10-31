package com.wellsoft.pt.ldx.web.pdm;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@RequestMapping("/defaultValue")
public class DefaultValueController  extends BaseController{
	@Autowired
	private DefaultValueService dvService;
	/**
	 * 灯具技术规格书默认值获取
	 * @param tableName
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getDefaultData")
	public void getDJGGS(@RequestParam(value = "tableName") String tableName,
			HttpServletRequest request, HttpServletResponse response){

		List list1 = dvService.query(tableName, " formTable='uf_djjsggsdtbg1'");
		List list2 = dvService.query(tableName, " formTable='uf_djjsggsdtbg2'");
		List list3 = dvService.query(tableName, " formTable='uf_djjsggsdtbg3'");
		List list4 = dvService.query("ldx_default_djjsggs2", " formTable='uf_djjsggsdtbg4'");
		Map map = new HashMap();
		
		map.put("dtbg1", list1);
		map.put("dtbg2", list2);
		map.put("dtbg3", list3);
		map.put("dtbg4", list4);
		
		JSONArray json = new JSONArray().fromObject(map);
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	/**
	 * 灯具PDCP审核表默认值获取
	 * @param tableName
	 * @param whereSql
	 * @param xmdh
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getDefaultPDCP")
	public void getPDCP(@RequestParam(value = "tableName") String tableName,@RequestParam(value = "whereSql") String whereSql
			,HttpServletRequest request, HttpServletResponse response){
		List list1 = dvService.query(tableName, whereSql);
		
		JSONArray json = new JSONArray().fromObject(list1);
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	/**
	 * 获取PDCP相关表单值
	 * @param xmdh
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/queryDataToPDCP", method = RequestMethod.POST)
	public void queryDataToPDCP(@RequestParam(value = "xmdh") String xmdh
			,HttpServletRequest request, HttpServletResponse response){
		StringBuffer sql1 = new StringBuffer("select t.cpxl,t.cpxh,t.cpdl,to_char(t.sytgrq,'yyyy-mm-dd hh:mi:ss'),to_char(t.klcrq" +
				",'yyyy-mm-dd hh:mi:ss'),t.mbsj,t.yjmll,t.mbbomcb,t.uuid from ")
				.append("(select * from uf_leedarson_djxmsqdfc where ")
				.append("xmdh='").append(xmdh).append("' and zt='11'")
				.append(" order by rq desc)")
				.append(" t where rownum=1");
		StringBuffer sql2 = new StringBuffer("select to_char(a.sjpg22,'yyyy-mm-dd hh:mi:ss'),to_char(a.sjpg62,'yyyy-mm-dd hh:mi:ss')," +
				"a.mbbomcb from ")
		.append("(select * from uf_djcpkxxfxbg where ")
		.append("xmdh='").append(xmdh).append("' and zt='11'")
		.append(" order by rq desc)")
		.append(" a where rownum=1");
		List list1 = dvService.queryBySql(sql1.toString());
		List list2 = dvService.queryBySql(sql2.toString());
		
		Object[] object = (Object[]) list1.get(0);
		String cpdl = object[2].toString();
		String uuid = object[8].toString();
		
		StringBuffer sql3 = new StringBuffer("select ");
		if(cpdl.indexOf("1")>=0){
			sql3.append("u.fbsid from uf_leedarson_djxmsqdfc t ")
			.append("left join uf_tsdsjghbdtbd_rl y on(t.uuid=y.mainform_data_uuid) left join uf_tsdsjghbdtbd u on(y.data_uuid=u.uuid)");
		}else if(cpdl.indexOf("3")>=0){
			sql3.append("u.fbsid from uf_leedarson_djxmsqdfc t ")
			.append("left join uf_mbdsjghbdtbd2_rl y on(t.uuid=y.mainform_data_uuid) left join uf_mbdsjghbdtbd2 u on(y.data_uuid=u.uuid)");
		}else if(cpdl.indexOf("2")>=0){
			sql3.append("u.fbsid from uf_leedarson_djxmsqdfc t ")
			.append("left join uf_xddsjghbdtbd2_rl y on(t.uuid=y.mainform_data_uuid) left join uf_xddsjghbdtbd2 u on(y.data_uuid=u.uuid)");
		}else{
			sql3.append(" t.cpdl from uf_leedarson_djxmsqdfc t");
			xmdh = "";
			uuid="";
		}
		sql3.append(" where ").append(" t.xmdh='"+xmdh+"' and t.uuid='"+uuid+"'");
		
		List list3 = dvService.queryBySql(sql3.toString());
		
		Map map = new HashMap();
		map.put("charter", list1);
		map.put("kxxbg", list2);
		map.put("cpid", list3);
		JSONArray json = new JSONArray().fromObject(map);
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	/**
	 * 灯具ADCP审核表默认值获取
	 * @param tableName
	 * @param whereSql
	 * @param xmdh
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getDefaultADCP")
	public void getADCP(@RequestParam(value = "tableName") String tableName,@RequestParam(value = "whereSql") String whereSql
			,HttpServletRequest request, HttpServletResponse response){
		List list1 = dvService.query(tableName, whereSql);
		
		JSONArray json = new JSONArray().fromObject(list1);
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取ADCP相关表单值
	 * @param xmdh
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/queryDataToADCP", method = RequestMethod.POST)
	public void queryDataToADCP(@RequestParam(value="xmdh")String xmdh, HttpServletRequest request, HttpServletResponse response){
		//到灯具项目申请单中获取相应的字段
		StringBuffer sql1 = new StringBuffer("select t.cpxl,t.cpxh,t.cpdl,to_char(t.rzwcrq,'yyyy-mm-dd hh:mi:ss'),t.mbsj,t.yjmll," +
				"t.mbbomcb,t.uuid from ")
			.append("(select * from uf_leedarson_djxmsqdfc where ")
			.append("xmdh='").append(xmdh).append("' and zt='11'")
			.append(" order by rq desc)")
		    .append(" t where rownum=1");
		List list1 = dvService.queryBySql(sql1.toString());
		
		Object[] object = (Object[]) list1.get(0);    //获取第一条记录
		String cpdl = object[2].toString();           //获取产品大类
		String uuid = object[7].toString();
		
		//判断产品大类，根据不同的产品大类到不同的表中获取产品ID
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
			sql2.append(" t.cpdl from uf_leedarson_djxmsqdfc t");
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
	 * 根据产品ID获取相关值 --EP申请单
	 * @param cpid
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/queryDataToEP", method = RequestMethod.POST)
	public void queryDataToEP(@RequestParam(value="cpid") String cpid,HttpServletRequest request, HttpServletResponse response){
		//到LED替换灯项目任务书中获取关联字段
		StringBuffer sql1 = new StringBuffer("select i.cpfbsid,h.mbqy,h.mbkh from uf_ledthdxmrwsdtbd i left join " +
				"uf_ledthdxmrwsdtbd_rl r on (i.uuid = r.data_uuid) left join uf_leedarson_ledthdxmrws h on (r.mainform_data_uuid = h.uuid)")
		        .append(" where i.cpfbsid = '")
		        .append(cpid).append("'");
		List list1 = dvService.queryBySql(sql1.toString());
		
		Map map = new HashMap();
		map.put("charter",list1);
		JSONArray json = new JSONArray().fromObject(map);
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
