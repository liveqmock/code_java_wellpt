package com.wellsoft.pt.ldx.web.materialsManage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wellsoft.pt.core.support.ExtendedPropertyPlaceholderConfigurer;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.core.web.ResultMessage;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.DyFormData;
import com.wellsoft.pt.ldx.service.materialsManage.MaterialsManageService;
import com.wellsoft.pt.ldx.util.StringUtils;
import com.wellsoft.pt.utils.encode.JsonBinder;

@Controller
@Scope("prototype")
@RequestMapping("/materials")
public class MaterialsManageController extends BaseController {
	@Autowired
	private MaterialsManageService mmService;
	@Autowired
	DyFormApiFacade dyf;

	/**
	 * 表单弹出方法，uuid和form_uuid必须提供
	 * 
	 * @param sqdh
	 * @param uuid
	 * @param form_uuid
	 * @param map
	 * @return
	 */
	@RequestMapping("/toMaintain")
	public String getRequisitionNo(
			@RequestParam(value = "sqdh") String sqdh,
			@RequestParam(value = "hh") String hh,
			@RequestParam(value = "uuid", required = false) String uuid,
			@RequestParam(value = "form_uuid", required = false) String form_uuid,
			Model map) {
		map.addAttribute("dataUuid", uuid);
		map.addAttribute("formUuid",
				dyf.getFormUuidById("uf_clkfgl_clkfglmkbd"));
		map.addAttribute("No", sqdh + (StringUtils.isNotEmpty(hh) ? hh : ""));
		return "pt/materials/view_gyswh";
	}

	/**
	 * 策略备库补录
	 * 
	 * @param zdjhh
	 * @param uuid
	 * @param form_uuid
	 * @param map
	 * @return
	 */
	@RequestMapping("/toCLBKBL")
	public String toCLBKBL(
			@RequestParam(value = "zdjhh") String zdjhh,
			@RequestParam(value = "uuid", required = false) String uuid,
			@RequestParam(value = "form_uuid", required = false) String form_uuid,
			Model map) {
		map.addAttribute("dataUuid", uuid);
		map.addAttribute("formUuid", dyf.getFormUuidById("uf_scgl_clblgkbbl"));
		map.addAttribute("zdjhh", zdjhh);
		return "pt/materials/view_clbkbl";
	}

	/**
	 * 客户验货标准解读跟踪清单信息补录
	 * 
	 * @param jdsx
	 * @param uuid
	 * @param form_uuid
	 * @param map
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/to_uf_pbgl_khyhbzjdgzqdbl")
	public String toCustomerMakeup(
			@RequestParam(value = "jdry", required = false) String jdry,
			@RequestParam(value = "xh", required = false) String xh,
			@RequestParam(value = "khz", required = false) String khz,
			@RequestParam(value = "ksrq", required = false) String ksrq,
			@RequestParam(value = "wcrq", required = false) String wcrq,
			@RequestParam(value = "uuid", required = false) String uuid,
			@RequestParam(value = "form_uuid", required = false) String form_uuid,
			Model map) throws ParseException {
		map.addAttribute("dataUuid", uuid);
		map.addAttribute("formUuid",
				dyf.getFormUuidById("uf_pbgl_khyhbzjdgzqdbl"));
		map.addAttribute("jdry", jdry);
		map.addAttribute("xh", xh);
		map.addAttribute("khz", khz);
		map.addAttribute("ksrq", ksrq);
		map.addAttribute("wcrq", wcrq);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		int result = 0;
		Date startDate = sdf.parse(ksrq), endDate = sdf
				.parse(wcrq);
		while (startDate.compareTo(endDate) <= 0) {
			if (startDate.getDay() != 6 && startDate.getDay() != 0)
				result++;
			startDate.setDate(startDate.getDate() + 1);
		}
		System.out.println("时间差2：" + result);
		String jdsx = result+"";
		map.addAttribute("jdsx", jdsx);
		return "pt/materials/view_customerMakeup";
	}
	/**
	 * 客户验货标准解读跟踪清单信息补录：计算时效
	 * @param qdrq
	 * @param fxrq
	 * @param request
	 * @param response
	 * @throws ParseException
	 */
	@RequestMapping("/setTime")
	public void setTime(@RequestParam(value = "qdrq", required = false) String qdrq,
			@RequestParam(value = "fxrq", required = false) String fxrq,HttpServletRequest request,
			HttpServletResponse response) throws ParseException{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int result = 0;
		Date startDate = sdf.parse(qdrq), endDate = sdf
				.parse(fxrq);
		while (startDate.compareTo(endDate) <= 0) {
			if (startDate.getDay() != 6 && startDate.getDay() != 0)
				result++;
			startDate.setDate(startDate.getDate() + 1);
		}
		JSONArray json = new JSONArray().fromObject(result);
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 添加表单from值
	 * 
	 * @param paramString
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping(value = "/addCLBL", method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public ResponseEntity<ResultMessage> addCLBL(@RequestBody String paramString)
			throws JSONException {
		JSONObject jsonObj = new JSONObject(paramString);
		DyFormData localDyFormData = (DyFormData) JsonBinder
				.buildNormalBinder().fromJson(paramString, DyFormData.class);
		return getSucessfulResultMsg(this.dyf.saveFormData(localDyFormData));
	}

	/**
	 * 返回值方法
	 * 
	 * @param paramObject
	 * @return
	 */
	protected ResponseEntity<ResultMessage> getSucessfulResultMsg(
			Object paramObject) {

		ResultMessage localResultMessage = new ResultMessage();
		localResultMessage.setSuccess(true);
		localResultMessage.setData(paramObject);
		return new ResponseEntity(localResultMessage, HttpStatus.OK);
	}

	/*
	 * @RequestMapping("/searchCollter") public void
	 * searchCollter(@RequestParam(value = "jcbh")String jcbh
	 * ,HttpServletRequest request, HttpServletResponse response){ List list =
	 * mmService.queryMaterialCollect("", "", jcbh); JSONArray json = new
	 * JSONArray().fromObject(list); try {
	 * response.getWriter().print(json.toString()); } catch (IOException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } }
	 * 
	 * @RequestMapping("/getSummaryDetails") public void
	 * getSummaryDetails(@RequestParam(value = "jcbh")String jcbh
	 * ,HttpServletRequest request, HttpServletResponse response){ List list1 =
	 * mmService.queryMaterialBySqdh(jcbh.split("_")[0]); List list2 =
	 * mmService.queryCollectByJcbh(jcbh); Map map = new HashMap();
	 * map.put("taitou", list1); map.put("mingxi", list2); JSONArray json = new
	 * JSONArray().fromObject(map); try {
	 * response.getWriter().print(json.toString()); } catch (IOException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } }
	 */
	@Autowired
	private ExtendedPropertyPlaceholderConfigurer propertyConfigurer;

	/**
	 * 获取CRM客户组
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/search/getCrmCustomerGroup", method = RequestMethod.GET)
	public void getCrmCustomerGroup(HttpServletRequest request,
			HttpServletResponse response) {
		String url = StringUtils.nullToString(propertyConfigurer
				.getProperty("multi.tenancy.crm.url"));
		String user = StringUtils.nullToString(propertyConfigurer
				.getProperty("multi.tenancy.crm.username"));
		String pwd = StringUtils.nullToString(propertyConfigurer
				.getProperty("multi.tenancy.crm.password"));

		String sql = "select distinct customer_code from crm_accounts where 1=1";

		Connection conn = null;
		PreparedStatement psStatement = null;
		ResultSet rSet = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, user, pwd);
			psStatement = conn.prepareStatement(sql);
			rSet = psStatement.executeQuery();

			List<Map<String, Object>> mainList = new ArrayList<Map<String, Object>>();
			while (rSet.next()) {
				String customer_code = StringUtils.nullToString(rSet
						.getObject("customer_code"));// 客户代码

				Map<String, Object> mainData = new HashMap<String, Object>();
				mainData.put("qzkhdm", customer_code);
				mainList.add(mainData);
			}
			JSONArray json = new JSONArray().fromObject(mainList);
			try {
				response.getWriter().print(json.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
