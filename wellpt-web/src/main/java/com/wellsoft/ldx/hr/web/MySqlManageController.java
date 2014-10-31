package com.wellsoft.ldx.hr.web;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wellsoft.ldx.hr.bean.PersonUser;
import com.wellsoft.ldx.hr.utils.MySqlUtils;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.DyFormData;
import com.wellsoft.pt.ldx.util.StringUtils;
import com.wellsoft.pt.org.entity.Employee;
import com.wellsoft.pt.org.entity.User;
import com.wellsoft.pt.org.facade.OrgApiFacade;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

@Controller
@RequestMapping("/sample")
public class MySqlManageController extends BaseController{

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	@Autowired
	private OrgApiFacade orgApiFacade;
	
	@RequestMapping(value = "/issue",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONArray getCrmCustomerGroup(
		@RequestParam(value="crmidValue",required=false) String crmidValue) {
		
		PreparedStatement psStatement = null;
		ResultSet rSet = null;
		Connection conn = MySqlUtils.getConnection();
		
		JSONArray json = null;
		String sql = "SELECT accounts.customer_code," +
					         "property.potential_customer_code," +
					         "property.customer_potential_rank " +
					         "FROM crm_accounts as accounts " +
					         "LEFT JOIN crm_accounts_property_expand_2 as property " +
					         "ON accounts.id=property.relation_id " +
					         "WHERE accounts.account_number=" + crmidValue;
		
		try {
			psStatement = conn.prepareStatement(sql);
			//psStatement.setString(1, crmidValue);
			rSet = psStatement.executeQuery();
			Map<String, Object> map =new HashMap<String, Object>();
			while (rSet.next()) {
//				String customer_code = StringUtils.nullToString(rSet
//						.getObject("customer_code"));// 客户代码
//
//				Map<String, Object> mainData = new HashMap<String, Object>();
//				mainData.put("qzkhdm", customer_code);
//				mainList.add(mainData);
				System.out.println(rSet.getString(1));
				if(StringUtils.isNotBlank(rSet.getString(1))){
					map.put("customer_code", rSet.getString(1));
				}else{
					map.put("customer_code", "");
				}
				
				if(StringUtils.isNotBlank(rSet.getString(2))){
					map.put("potential_customer_code", rSet.getString(2));
				}else{
					map.put("potential_customer_code", "");
				}
				
				if(StringUtils.isNotBlank(rSet.getString(3))){
					map.put("customer_potential_rank", rSet.getString(3));
				}else{
					map.put("customer_potential_rank", "");
				}
				json = new JSONArray().fromObject(map);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	/**
	 * 新增账户回写
	 */
	@RequestMapping(value = "/account",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONArray newCount(
			@RequestParam(value="ygbh",required=false) String ygbh,
			@RequestParam(value="xm",required=false) String xm,
			@RequestParam(value="systype",required=false) String systype,
			@RequestParam(value="account",required=false) String account
			){
		String formUuid = dyFormApiFacade.getFormUuidById("uf_doc_account");
		DyFormData dyformData = dyFormApiFacade.createDyformData(formUuid);
		String userid = "";
		JSONArray json = null;
		
		//通过姓名获得用户id
		try {
			xm =  java.net.URLDecoder.decode(xm,"UTF-8"); 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<Employee> lists = orgApiFacade.getEmployeesByName(xm);
		List<PersonUser> newLists = new ArrayList<PersonUser>();
		PersonUser personUser = null;
		for(int i=0;i<lists.size();i++){
			personUser = new PersonUser();
			Employee employee = lists.get(i);
			personUser.setYggh(employee.getEmployeeNumber());
			personUser.setJob(employee.getMajorJobName());
			personUser.setDepartment(employee.getDepartmentName());
			personUser.setName(employee.getName());
			newLists.add(personUser);
		}
		
		if(org.apache.commons.lang.StringUtils.isNotBlank(systype)){
			systype = DyFormApiFacade.getDisplayValue(systype);
		}else{
			systype = "";
		}
		if(newLists != null ){
			if(newLists.size() == 1){
				userid = newLists.get(0).getYggh();
//				userid： LCP用户ID，如：U0010000001
//				code：工号
//				systype ：系统类型
//				account： 帐号
//				stats：状态
				dyformData.setFieldMapValue("userid", "");
				dyformData.setFieldMapValue("code", ygbh);
				dyformData.setFieldMapValue("systype", systype);
				dyformData.setFieldMapValue("account", xm);
				dyformData.setFieldMapValue("stats", "1");
				String rtnValue = dyFormApiFacade.saveFormData(dyformData);
				System.out.println(rtnValue);
				json = new JSONArray().fromObject(newLists);
				return json;
			}else if(newLists.size() > 1){
				json = new JSONArray().fromObject(newLists);
				return json; 
			}else{
				return json;
			}
			
		}
		return json;
	}
	
	/**
	 * 获取员工编号
	 */
	@RequestMapping(value = "/getNumber",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void newCount(
			@RequestParam(value="xm",required=false) String xm
			){
		
		//通过姓名获得员工编号
//TODO HWY
		System.out.println("xm");
		System.out.println("xm");
	}
}
