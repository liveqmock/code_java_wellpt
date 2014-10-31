package com.wellsoft.pt.ldx.web.prodplan;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wellsoft.pt.basicdata.sap.config.SAPDbConfig;
import com.wellsoft.pt.core.support.ExtendedPropertyPlaceholderConfigurer;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.model.productionPlan.PlanShare;
import com.wellsoft.pt.ldx.model.productionPlan.ZPPT0068;
import com.wellsoft.pt.ldx.service.productionPlan.IProductionPlanService;
import com.wellsoft.pt.ldx.util.DateUtils;
import com.wellsoft.pt.ldx.util.StringUtils;
import com.wellsoft.pt.org.entity.User;
import com.wellsoft.pt.security.core.userdetails.UserDetails;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

/**
 * 
 * Description: 工单管理控制器
 *  
 * @author HeShi
 * @date 2014-7-29
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-9-4.1	HeShi		2014-9-4		Create
 * </pre>
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/productionPlan/workOrder")
public class WorkOrderController extends BaseController {
	@Resource(name="sapConnectConfig")
	private SAPDbConfig sapConfig;
	@Autowired
	private IProductionPlanService productionPlanService;
	@Autowired
	private ExtendedPropertyPlaceholderConfigurer propertyConfigurer;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/search")
	public String search(Model model,HttpServletRequest request) throws Exception {
		//当前查询条件:生管
		String sgNum = request.getParameter("sgNum");
		//当前查询条件:线号
		String zxh = request.getParameter("zxh");
		List<Object> lineList = new ArrayList<Object>();
		if(StringUtils.isBlank(sgNum)&&"0".equals(findUserType())){
			sgNum = getSgNum();
		}
		if(StringUtils.isNotBlank(sgNum)){
			lineList = findLineListBySgNum(sgNum);
		}
		List<Object> sgList = productionPlanService.findListBySql("select distinct zsg from zppt0035 order by zsg");
		model.addAttribute("sgList",sgList);
		model.addAttribute("lineList",lineList);
		model.addAttribute("sgNum",sgNum);
		model.addAttribute("zxh",zxh);
		String today = DateUtils.formatDate(new Date(),"yyyyMMdd");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("zxh",zxh);
		String sqlA = "select zrq from(select zrq, rownum from (select distinct zrq from zppt0034 where zrq >= '"
				+ today + "'"
				+ " and zxh='"+zxh+"'"
				+ " order by zrq) where rownum <= 3)";
		List<Object> tempList = productionPlanService.findListBySql(sqlA);
		List<String> days = new ArrayList<String>();
		for(Object temp:tempList){
			days.add(StringUtils.nullToString(temp));
		}
		if(days.isEmpty()){
			days.add(today);
		}
		map.put("days", days);
		List<PlanShare> allPlanShares; 
		if(StringUtils.isBlank(zxh)){
			allPlanShares = new ArrayList<PlanShare>();
		}else{
			allPlanShares = productionPlanService.getCurrentPlan(map);
		}
		
		List<ZPPT0068> woList =(List<ZPPT0068>) productionPlanService.findByHql("from ZPPT0068 where zxh='"+zxh+"' and zksdat>='"+today+"' order by zsxh");
		//当前执行计划
		List<ZPPT0068> planList = new ArrayList<ZPPT0068>();
		//变动计划
		List<ZPPT0068> changeList = new ArrayList<ZPPT0068>();
		for(ZPPT0068 wo:woList){
			//查询实际完工量
			wo.setSjwgsl(findSjwglByGdh(wo.getZgdh()));
			wo.setMatnr(StringUtils.removeLeftZero(wo.getMatnr()));
			wo.setZsxh(StringUtils.removeLeftZero(wo.getZsxh()));
			if("R".equals(wo.getZzt())){
				//新增
				wo.setZzt("新增");
				if(wo.getZzrjhl().compareTo(wo.getZjrjhl())==0){
					wo.setZzt("正常");
				}
				planList.add(wo);
			}else if("M".equals(wo.getZzt())){
				//变更
				wo.setZzt("变更(由"+StringUtils.removeStrAfterComma(wo.getZzrjhl())+"更改为"+StringUtils.removeStrAfterComma(wo.getZjrjhl())+")");
				planList.add(wo);
			}else if("X".equals(wo.getZzt())){
				//取消或者计划量发生变化
				wo.setZzt("取消");
				changeList.add(wo);
			}else if("C".equals(wo.getZzt())){
				wo.setZzt("完工");
				changeList.add(wo);
			}
		}
		model.addAttribute("days",getDays(days.toArray()));
		model.addAttribute("weeks",getWeeks(days.toArray()));
		model.addAttribute("allPlans",getPlanShares(allPlanShares));
		model.addAttribute("planList",planList);
		model.addAttribute("changeList",changeList);
		return forward("/ldx/prodplan/workOrderList");
	}

	private List<PlanShare> getPlanShares(List<PlanShare> allPlanShares) {
		List<PlanShare> plans = new ArrayList<PlanShare>();
		for(PlanShare plan:allPlanShares){
			plan.setOrderLineNo(StringUtils.removeLeftZero(plan.getOrderLineNo()));
			plan.setProductOrder(StringUtils.removeLeftZero(plan.getProductOrder()));
			plan.setOrderLineNo(StringUtils.removeLeftZero(plan.getOrderLineNo()));
			plan.setWlId(StringUtils.removeLeftZero(plan.getWlId()));
			plan.setSdExpDate(StringUtils.removeLeftZero(plan.getSdExpDate()));
			plans.add(plan);
		}
		return plans;
	}
	
	/**
	 * 
	 * 根据工单号查询实际完工量
	 * 
	 * @param gdh
	 * @return
	 */
	private String findSjwglByGdh(String gdh){
		String url = StringUtils.nullToString(propertyConfigurer.getProperty("multi.tenancy.sap.url"));
		String user = StringUtils.nullToString(propertyConfigurer.getProperty("multi.tenancy.sap.username"));
		String pwd = StringUtils.nullToString(propertyConfigurer.getProperty("multi.tenancy.sap.password"));
		String sql = "select nvl(sum(i_Trin),0) as count from t_mes_troutc where s_won ='"+gdh+"'";
		Connection conn = null;
		PreparedStatement psStatement = null;
		ResultSet rSet = null;
		String count="0";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			conn = DriverManager.getConnection(url, user, pwd);
			psStatement = conn.prepareStatement(sql);
			rSet = psStatement.executeQuery();
			while (rSet.next()) {
				count = StringUtils.nullToString(rSet.getObject("count"));//来访日期
				//System.out.println("工单号:"+gdh+",完成量:"+count);
				break;
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}finally{
			try {
				rSet.close();
				psStatement.close();
				conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return count;
	}
	
	
	/**
	 * 
	 * 保存备注信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("saveRemark")
	public void saveRemark(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
		String[] retObj = new String[2];// 返回信息
		//获取参数
		String value = request.getParameter("value");
		String zgdh = request.getParameter("zgdh");
		try {
			checkHasRole(zgdh);
			value = StringUtils.isBlank(value) ? " " : value.trim().replaceAll("'", "‘").replaceAll("<", "&lt;")
					.replaceAll(">", "&gt;");
			String sql = "update zppt0068 set remark='" + value + "' " + " where zgdh ='" + zgdh + "' and mandt="+sapConfig.getClient();
			productionPlanService.execSql(sql);
			retObj[0] = "success";
		} catch (Exception e) {
			retObj[0] = "fail";
			retObj[1] = e.getMessage();
		}
		JSONArray ja = JSONArray.fromObject(retObj);
		response.getOutputStream().write(ja.toString().getBytes("UTF-8"));
	}
	
	/**
	 * 
	 * 校验当前用户是否为当前工单的对应生管
	 * 
	 * @param zgdh
	 * @throws Exception
	 */
	private void checkHasRole(String zgdh) throws Exception{
		String userCode = getUserCode();
		String sql = "select 1 from zppt0068 a "
			+ "inner join zppt0031 b on a.aufnr=b.aufnr and a.mandt=b.mandt "
			+ "inner join zppt0035 c on b.zsg=c.zsg "
			+ "where a.zgdh='"+zgdh+"' "
			+ "and c.zoauser='"+userCode+"'";
		List<Object> list = productionPlanService.findListBySql(sql);
		if(list==null||list.isEmpty()){
			throw new Exception("您不是当前工单对应生管,没有维护权限!");
		}
	}

	private String[] getDays(Object[] array) {
		if(null==array||array.length==0)
			return new String[0];
		String[] days = new String[array.length];
		try {
			for(int i=0;i<array.length;i++){
				days[i] = DateUtils.formatDate(DateUtils.parseDate(StringUtils.nullToString(array[i]), "yyyyMMdd"),"MM月dd日");
			}
		} catch (ParseException e) {
			return new String[array.length];
		} 
		return days;
	}

	/**
	 * 
	 * 日期数组转转化为星期数组
	 * 
	 * @param array
	 * @return
	 */
	private String[] getWeeks(Object[] array) {
		if(null==array||array.length==0)
			return new String[0];
		String[] weeks = new String[array.length];
		try {
			for(int i=0;i<array.length;i++){
				Date date = DateUtils.parseDate(StringUtils.nullToString(array[i]),"yyyyMMdd");
				weeks[i] = DateUtils.getWeekOfDate(date);
			}
		} catch (ParseException e) {
			return new String[array.length];
		} 
		return weeks;
	}
	
	/**
	 * 
	 * 根据生管查询线别列表
	 * 
	 * @param sgNum
	 * @return
	 */
	public List<Object> findLineListBySgNum(String sgNum){
		String sql = "select distinct zxh from zppt0034 where zsg='"+sgNum+"' and zrq='20130101' order by zxh";
		return productionPlanService.findListBySql(sql);
	}
	
	/**
	 * 
	 * 获取生管编号
	 * 
	 * @return
	 */
	public String getSgNum() {
		String sql = "select max(zsg) from zppt0035 where zoauser = '" + getUserCode() + "'";
		return (String) productionPlanService.findListBySql(sql).get(0);
	}
	
	/**
	 * 判断用户类型
	 * 
	 * @return 0:生管,1:课长,2:组长
	 */
	public String findUserType() {
		String usrCode = getUserCode();
		if (StringUtils.isNotBlank(usrCode)) {
			//生管为0，课长为1，组长为2
			String sql = "select max(tp) from(select distinct '1' as tp from zppt0035 where zkz = '"
					+ usrCode
					+ "' "
					+ "union all select distinct '2' from zppt0035 where zoauser = '"
					+ usrCode
					+ "' and zsg = zzz "
					+ "union all select distinct '0' from zppt0035 where zoauser = '"
					+ usrCode
					+ "' and zsg <> zzz "
					+ " )";
			List<Object> objs = productionPlanService.findListBySql(sql);
			if (objs!=null&&objs.size()>0) {
				return StringUtils.nullToString(objs.get(0));
			}
		}
		return "";
	}
	
	/**
	 * 取得当前登录工号
	 * @return
	 */
	private String getUserCode(){
		String userId = ((UserDetails)SpringSecurityUtils.getAuthentication().getPrincipal()).getUserId();
		User user = productionPlanService.getCurrentUser(userId);
		return user.getEmployeeNumber();
//		UserDetails ud = SpringSecurityUtils.getCurrentUser();
//		return ud.getEmployeeNumber();
	}
	
	@RequestMapping("/add")
	public String add(Model model){
		model.addAttribute("method", "1");//新增
		return forward("/ldx/prodplan/workOrderConfigAdd");
	}
	
	@RequestMapping("/modify")
	public String modify(@RequestParam(value = "key") String key,Model model) throws Exception {
		model.addAttribute("method", "0");//修改
		String sql = "select key,zjhl,type from"
			+ " (select mandt,case when aufnr like 'SG_%' then substr(aufnr,4,4) else aufnr end as key,zjhl as zjhl,case when aufnr like 'SG_%' then '生管' else '生产订单' end as type from zppt0069)"
			+ " where key='"+key+"'";
		List<Object> list = productionPlanService.findListBySql(sql);
		if(null!=list && list.size()>0){
			Object[] objects =(Object[])list.get(0);
			model.addAttribute("key",key);
			model.addAttribute("zjhl",StringUtils.nullToString(objects[1]));
			model.addAttribute("type",StringUtils.nullToString(objects[2]));
		}
		return forward("/ldx/prodplan/workOrderConfigAdd");
	}
	
}
