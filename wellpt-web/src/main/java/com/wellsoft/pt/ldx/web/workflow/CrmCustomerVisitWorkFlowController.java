package com.wellsoft.pt.ldx.web.workflow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wellsoft.pt.core.support.ExtendedPropertyPlaceholderConfigurer;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.ldx.util.StringUtils;
import com.wellsoft.pt.workflow.work.bean.WorkBean;
import com.wellsoft.pt.workflow.work.service.WorkService;

/**
 * 
 * Description: CRM客户来访记录创建流程
 *  
 * @author HeShi
 * @date 2014-9-24
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-9-24 	HeShi		2014-9-24		Create
 * </pre>
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/crm/customerVisitPlan")
public class CrmCustomerVisitWorkFlowController extends BaseController{
	@Autowired
	private DyFormApiFacade dyformApiFacade;
	@Autowired
	private WorkService workService;
	@Autowired
	private ExtendedPropertyPlaceholderConfigurer propertyConfigurer;
	
	private String url;
	private String user;
	private String pwd;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/create")
	public String createWorkFlow(Model model,@RequestParam(value = "planId") String planId) throws Exception {
		String workFlowId = "85b6dc52-d3b2-4859-89b8-7d903acbe36b";//流程id
		String visitPlanFormId = "02c65835-66f7-43e9-8229-1c9446495800";//客户来访表单ID
		String sheduleFormUuid = "42a25bb0-9ef4-4fb4-ad2b-7cc9d77d80bc";//行程安排表单UUID
		String custListFormUuid = "5d9bd99c-27d6-4c9c-9a49-7560330954a1";//来访人员表单UUID
		Map<String,Object> allMap = findCustomerVisitDataFromCrm(planId);
		List<Map<String,Object>> mainList = (List<Map<String,Object>>)allMap.get("mainList");
		List<Map<String,Object>> planList = (List<Map<String,Object>>)allMap.get("planList");
		List<Map<String,Object>> personList = (List<Map<String,Object>>)allMap.get("personList");
		Map<String,List<Map<String,Object>>> formMap = new HashMap<String,List<Map<String,Object>>>();
		formMap.put(visitPlanFormId,mainList);
		if(null!=planList&&!planList.isEmpty()){
			formMap.put(sheduleFormUuid,planList);
		}
		if(null!=personList&&!personList.isEmpty()){
			formMap.put(custListFormUuid,personList);
		}
		String planFormUuid = dyformApiFacade.saveFormData(visitPlanFormId,formMap,null,null);
		WorkBean localWorkBean = this.workService.newWork(workFlowId);
		if (StringUtils.isNotBlank(visitPlanFormId)) {
			localWorkBean.setFormUuid(visitPlanFormId);
			if (StringUtils.isNotBlank(planFormUuid)){
				localWorkBean.setDataUuid(planFormUuid);
			}
		}
		model.addAttribute(localWorkBean);
		return forward("/workflow/work/work_view");
	}
	
	/**
	 * 
	 * 根据客户ID查询客户来访计划相关数据
	 * select * from crm_customer_visit_plan where account ="客户ID";--客户来访计划表
	 * select * from crm_customer_visit_plan_detaileditinerary where plan_id ="主表ID";--客户来访计划详细行程表
	`* select * from crm_customer_visit_plan_personalinformation where plan_id ="主表ID";--客户来访计划来访人员信息表
	 * 
	 * 来访计划主数据
	 * 
	 * select a.visit_date,a.customer_code,b.rating,b.country,b.record_type,c.amt
		from crm_customer_visit_plan a 
		left join crm_accounts b on a.account=b.id
		left join (SELECT count(1) as amt,plan_id from crm_customer_visit_plan_personalinformation group by plan_id) c on a.id=c.plan_id
		where a.id='d834485e-00a50034-4bf9'
	 * 
	 * 行程安排
	 * select start_time,end_time,site,activity_content from crm_customer_visit_plan_detaileditinerary where plan_id='d834485e-00a50034-4bf9'
	 * 
	 * 来访人员信息
	 * select b.name,b.title,b.gender,b.buying_role,b.mailing_country,b.hobby,a.plan_id from 
		crm_customer_visit_plan_personalinformation a 
		inner join crm_contacts b on a.contact_id=b.id
		where a.plan_id='d834485e-00a50034-4bf9'
	 * @param custID
	 * @return
	 */
	private Map<String,Object> findCustomerVisitDataFromCrm(String planId){
		url = StringUtils.nullToString(propertyConfigurer.getProperty("multi.tenancy.crm.url"));
		user = StringUtils.nullToString(propertyConfigurer.getProperty("multi.tenancy.crm.username"));
		pwd = StringUtils.nullToString(propertyConfigurer.getProperty("multi.tenancy.crm.password"));
		if(StringUtils.isBlank(planId)){
			//计划ID为空则取一条测试数据
			planId = "d834485e-00a50034-4bf9";
		}
		Map<String,Object> allMap = new HashMap<String,Object>();
		//查询来访计划主表
		String mainSql = "select a.visit_date,a.customer_code,f.customer_potential_rank as rank,e.name as country,f.biz_type as type,c.amt"
			+ " from crm_customer_visit_plan a "
			+ " left join crm_accounts b on a.account=b.id"
			+ " left join (SELECT count(1) as amt,plan_id from crm_customer_visit_plan_personalinformation group by plan_id) c on a.id=c.plan_id"
			+ " left join crm_country e on b.country=e.id"
			+ " left join crm_accounts_property_expand_2 f on b.id=f.relation_id"
			+ " where a.id='"+planId+"'";
		//查询来访人员
		String persons = "select b.name,b.title,b.gender,b.buying_role,b.mailing_country,b.hobby,a.plan_id" 
			+ " from crm_customer_visit_plan_personalinformation a "
			+ " left join crm_contacts b on a.contact_id=b.id"
			+ " where a.plan_id='"+planId+"'";
		//查询行程安排
		String schedule = "select start_time,end_time,site,activity_content from crm_customer_visit_plan_detaileditinerary where plan_id='"+planId+"'";

		Connection conn = null;
		PreparedStatement psStatement = null;
		ResultSet rSet = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, user, pwd);
			psStatement = conn.prepareStatement(mainSql);
			rSet = psStatement.executeQuery();
			System.out.println("=====来访计划=====");
			List<Map<String,Object>> mainList = new ArrayList<Map<String,Object>>();
			while (rSet.next()) {
				String visit_date = StringUtils.nullToString(rSet.getObject("visit_date"));//来访日期
				String customer_code = StringUtils.nullToString(rSet.getObject("customer_code"));//客户代码
				String rank = StringUtils.nullToString(rSet.getObject("rank"));//客户级别
				String country = StringUtils.nullToString(rSet.getObject("country"));//客户国籍
				String amt = StringUtils.nullToString(rSet.getObject("amt"));//来访人数
				String type = StringUtils.nullToString(rSet.getObject("type"));//客户类型
				System.out.println("来访日期:"+visit_date+",客户代码:"+customer_code+",客户级别:"+rank+",客户国籍:"+country+",来访人数:"+amt+",客户类型:"+type);
				Map<String,Object> mainData = new HashMap<String,Object>();
				mainData.put("khlfrq",visit_date);
				mainData.put("qzkhdm",customer_code);
				mainData.put("khjb",rank);
				mainData.put("lfrs",amt);
				mainData.put("khlx",type);
				mainData.put("lfkhgj",country);
				mainList.add(mainData);
			}
			allMap.put("mainList",mainList);
			System.out.println("=====行程安排=====");
			psStatement = conn.prepareStatement(schedule);
			rSet = psStatement.executeQuery();
			List<Map<String,Object>> planList = new ArrayList<Map<String,Object>>();
			while (rSet.next()) {
				String start_time = StringUtils.nullToString(rSet.getObject("start_time"));//开始时间
				String end_time = StringUtils.nullToString(rSet.getObject("end_time"));//结束时间
				String site = StringUtils.nullToString(rSet.getObject("site"));//地点
				String activity_content = StringUtils.nullToString(rSet.getObject("activity_content"));//活动内容
				System.out.println("开始时间:"+start_time+",结束时间:"+end_time+",地点:"+site+",活动内容:"+activity_content);
				Map<String,Object> plan = new HashMap<String,Object>();
				plan.put("kssj",start_time);
				plan.put("jssj",end_time);
				plan.put("dd",site);
				plan.put("hdnr",activity_content);
				planList.add(plan);
			}
			allMap.put("planList",planList);
			System.out.println("=====来访人员=====");
			psStatement = conn.prepareStatement(persons);
			rSet = psStatement.executeQuery();
			List<Map<String,Object>> personList = new ArrayList<Map<String,Object>>();
			while (rSet.next()) {
				String name = StringUtils.nullToString(rSet.getObject("name"));//姓名
				String title = StringUtils.nullToString(rSet.getObject("title"));//头衔
				String gender = StringUtils.nullToString(rSet.getObject("gender"));//性别
				String buying_role = StringUtils.nullToString(rSet.getObject("buying_role"));//购买角色
				String mailing_country = StringUtils.nullToString(rSet.getObject("mailing_country"));//国籍
				String hobby = StringUtils.nullToString(rSet.getObject("hobby"));//兴趣
				System.out.println("姓名:"+name+",头衔:"+title+",性别:"+gender+",购买角色:"+buying_role+",国籍:"+mailing_country+",兴趣:"+hobby);
				Map<String,Object> person = new HashMap<String,Object>();
				person.put("lfryxm",name);
				person.put("lfrytx",title);
				person.put("lfryxb",gender);
				person.put("lfrygmjs",buying_role);
				person.put("lfrygj",mailing_country);
				person.put("lfryxqah",hobby);
				personList.add(person);
			}
			allMap.put("personList",personList);
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
		return allMap;
	}
}
