package com.wellsoft.pt.ldx.web.prodplan;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.wellsoft.pt.basicdata.sap.config.SAPDbConfig;
import com.wellsoft.pt.core.support.ExtendedPropertyPlaceholderConfigurer;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.model.productionPlan.PlanShare;
import com.wellsoft.pt.ldx.model.productionPlan.ZPPT0031;
import com.wellsoft.pt.ldx.service.productionPlan.IProductionPlanService;
import com.wellsoft.pt.ldx.util.DateUtils;
import com.wellsoft.pt.ldx.util.StringUtils;
import com.wellsoft.pt.org.entity.User;
import com.wellsoft.pt.security.core.userdetails.UserDetails;
import com.wellsoft.pt.security.facade.SecurityApiFacade;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

/**
 * 
 * Description: 部件生产计划平台控制器
 *  
 * @author heshi
 * @date 2014-8-5
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-8-23.1	heshi		2014-8-23		Create
 * </pre>
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/productionPlan/bomPlan/")
public class BomPlanController extends BaseController {

	@Autowired
	private IProductionPlanService productionPlanService;
	@Resource(name="sapConnectConfig")
	private SAPDbConfig sapConfig;
	@Autowired
	private SecurityApiFacade securityApiFacade;
	@Autowired
	private ExtendedPropertyPlaceholderConfigurer propertyConfigurer;
	
	private static final String USER_TYPE = "prodPlanUserType";
	private static final String USERTYPE_SG = "0";//用户登录类型--生管
	private static final String USERTYPE_KZ = "1";//用户登录类型--课长
	private static final String USERTYPE_ZZ = "2";//用户登录类型--组长
	private static final String USERTYPE_KZSG = "3";//用户登录类型--课长或组长以生管身份登录
	private static final String USERTYPE_ADMIN = "9";//管理员
	private static final String SG_NUM = "prodPlanSgNum";
	private static final String GXB_ZZ = "总装";
	private static final String GXB_BZ = "包装";
	/**
	 * 负荷查询开始时间
	 */
	private String startDay = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
	/**
	 * 负荷查询结束时间
	 */
	private String endDay = DateUtils.formatDate(
			DateUtils.addDate(new Date(), 2), "yyyy-MM-dd");

	/**
	 * 
	 * 登录类型选择
	 * 
	 * @param aufnr
	 * @return
	 */
	@RequestMapping("/route")
	public String route(Model model) {
		this.getRequest().getSession().removeAttribute(SG_NUM);
		this.getRequest().getSession().removeAttribute(USER_TYPE);
		String type = findUserType();
		if (USERTYPE_SG.equals(type)) {
			this.getRequest().getSession().setAttribute(USER_TYPE, type);
			this.getRequest().getSession().setAttribute(SG_NUM, getSgNum());
			return redirect("/productionPlan/bomPlan/search");
		} else if (USERTYPE_KZ.equals(type)||USERTYPE_ZZ.equals(type)) {
			model.addAttribute("sgList",getLoginSgList(type));
			model.addAttribute("typeList",initUserTypeList(type));
			model.addAttribute("planType","bomPlan");
			return forward("/ldx/prodplan/userTypeChoose");
		} else if (USERTYPE_ADMIN.equals(type)) {
			this.getRequest().getSession().setAttribute(USER_TYPE, type);
			return redirect("/productionPlan/bomPlan/search");
		} else {
			return forward("/ldx/error");
		}
	}
	
	/**
	 * 页面重定向,打开生产排程页面
	 * 用于课长登录时选择登录类型：1课长，0组长，3课长以生管身份登录
	 * @return
	 */
	@RequestMapping("/redirect")
	public String redirect(@RequestParam(value = "userType") String userType, @RequestParam(value = "productionManager") String productionManager,Model model) {
		if (StringUtils.isNotBlank(userType)) {
			this.getRequest().getSession().setAttribute(USER_TYPE, userType);
			if (USERTYPE_KZSG.equals(userType)
					&& StringUtils.isNotBlank(productionManager)) {// 课长以生管身份登录
				this.getRequest().getSession()
						.setAttribute(SG_NUM, productionManager);
			} else if (USERTYPE_SG.equals(userType)) {
				String sql = "select distinct zsg from zppt0035 where zoauser='"
						+ getUserCode() + "' and zsg not like 'A%'";
				Object o = productionPlanService.findListBySql(sql).get(0);
				this.getRequest().getSession()
						.setAttribute(SG_NUM, o.toString());
			}
		}
		return redirect("/productionPlan/bomPlan/search");
	}
	
	/**
	 * 
	 * 搜索页面相关记录
	 * 
	 * @param model
	 */
	@RequestMapping("/search")
	public String search(Model model) {
		String type = (String) this.getRequest().getSession().getAttribute(USER_TYPE);
		model.addAttribute("userType",type);
		PagingInfo pageInfo = new PagingInfo();
		pageInfo.setCurrentPage(1);
		pageInfo.setPageSize(10);
		getSgList(type,model);
		getXbList(type,model);
		String sgNum = "";
		if (USERTYPE_SG.equals(type) || USERTYPE_KZSG.equals(type)) {
			sgNum = (String) this.getRequest().getSession()
					.getAttribute(SG_NUM);
			model.addAttribute("sgNum",sgNum);
		}
		if(StringUtils.isBlank(type)&&StringUtils.isBlank(sgNum)){
			return redirect("/productionPlan/bomPlan/route");
		}
		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("startDay",startDay);
		model.addAttribute("endDay",endDay);
		return forward("/ldx/prodplan/bomPlan");
	}
	
	/**
	 * 
	 * 根据当前登录用户类型加载生管下拉列表
	 * 
	 * @param type
	 */
	private void getSgList(String type,Model model) {
		List<String> selectSgList = new ArrayList<String>();
		List<String> temp = new ArrayList<String>();
		selectSgList.clear();
		if (StringUtils.isNotBlank(getUserCode())) {
			String sql = "select distinct zsg from zppt0035 where 1=1";// zppt0035 
			// 暂不限制课长权限，默认查看所有生管
			if (USERTYPE_SG.equals(type)) {// 生管
				sql = sql + "and zoauser = '" + getUserCode() + "'";
			} else if (USERTYPE_KZ.equals(type)) {// 课长
				sql = sql + "and zkz = '" + getUserCode() + "'";
			} else if(USERTYPE_ZZ.equals(type)){//组长
				sql = sql + "and zzz in (select zsg from zppt0035 where zoauser ='"+getUserCode()+"')";
			} else if (USERTYPE_ADMIN.equals(type)) {// 管理员、经理
				// 查询全部
			} else if (USERTYPE_KZSG.equals(type)) {// 课长以生管身份登录
				String sgNum = (String) this.getRequest().getSession()
						.getAttribute(SG_NUM);
				sql = sql + "and zsg = '" + sgNum + "'";
			}
			sql += " and zsg not like 'A%' order by zsg";
			List<Object> list = productionPlanService.findListBySql(sql);
			if (null != list && list.size() > 0) {
				for (Object obj : list) {
					String zsg = StringUtils.nullToString(obj);
					selectSgList.add(zsg);
					temp.add(zsg);
				}
			}
			//查询组长列表
			if (USERTYPE_SG.equals(type) || USERTYPE_KZSG.equals(type)) {
				String sqlb = "select distinct(zzz) from zppt0035 ";
				String sgNum = "";
				sgNum = (String) this.getRequest().getSession()
						.getAttribute(SG_NUM);
				sqlb = sqlb + "where zsg = '"+sgNum+"' and zsg not like 'A%'";
				List<Object> listB = productionPlanService.findListBySql(sqlb);
				if (null != listB && listB.size() > 0) {
					for (Object obj : listB) {
						if(null!=obj && StringUtils.isNotBlank(obj.toString())){
							String zsg = StringUtils.nullToString(obj);
							if(!temp.contains(zsg)){
								temp.add(zsg);
								selectSgList.add(zsg);
							}
							//若当前登录用户为组长，则查询组长名下的生管列表
							if ((USERTYPE_SG.equals(type) || USERTYPE_KZSG.equals(type))
									&& StringUtils.isNotBlank(sgNum)
									&& zsg.equals(sgNum)) {
								String zzSql = "select distinct zsg from zppt0035 where zzz='"+zsg+"' and zsg not like 'A%' order by zsg";
								List<Object> listc = productionPlanService.findListBySql(zzSql);
								if (null != listc && listc.size() > 0) {
									for (Object objc : listc) {
										String zzz = StringUtils.nullToString(objc);
										if(!temp.contains(zzz)){
											temp.add(zzz);
											selectSgList.add(zzz);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		model.addAttribute("selectSgList",selectSgList);
		this.getRequest().getSession().setAttribute("sgLi", selectSgList);
	}
	
	/**
	 * 根据当前用户查找线别列表
	 */
	public void getXbList(String type,Model model) {
		List<String> xbLoadList = new ArrayList<String>();
		if (StringUtils.isNotBlank(getUserCode())) {
			String sql = "select distinct zxh from zppt0034 where zrq='20130101' ";
			if(USERTYPE_SG.equals(type)
					||USERTYPE_KZSG.equals(type)){
				String sgNum = (String) this.getRequest().getSession()
				.getAttribute(SG_NUM);
				sql = sql + "and zsg = '"+sgNum+"' ";
			}
			sql = sql + " order by zxh ";
			List<Object> list = productionPlanService.findListBySql(sql);
			if (null != list && list.size() > 0) {
				for (Object obj : list) {
					String zxh = StringUtils.nullToString(obj);
					xbLoadList.add(zxh);
				}
			}
		}
		model.addAttribute("xbLoadList",xbLoadList);
	}

	@SuppressWarnings("rawtypes")
	private List getLoginSgList(String type) {
		String usrCode = getUserCode();
		String sql = "";
		if(USERTYPE_KZ.equals(type)){
			sql = "select distinct zsg from zppt0035 where zkz = '"
			+ usrCode + "' and zsg not like 'A%' order by zsg";
		}
		else if(USERTYPE_ZZ.equals(type)){
			sql = "select distinct zsg from zppt0035 where zzz in (select zsg from zppt0035 where zoauser ='"
			+ usrCode + "' and zsg not like 'A%') order by zsg";
		}
		return productionPlanService.findListBySql(sql);
	}
	
	private List<String[]> initUserTypeList(String type) {
		List<String[]> li = new ArrayList<String[]>();
		if(USERTYPE_KZ.equals(type)){
			li.add(new String[]{"课长","1"});
		}else if(USERTYPE_ZZ.equals(type)){
			li.add(new String[]{"组长","0"});
		}
		li.add(new String[]{"生管","3"});
		return li;
	}
	
	/**
	 * 判断用户类型
	 * 
	 * @return 0:生管,1:课长,2:组长
	 */
	public String findUserType() {
		if (hasRole("ROLE_ADMIN")||hasRole("ROLE_SCPC_MANAGE")) {
			return USERTYPE_ADMIN;
		}
		String usrCode = getUserCode();
		if (StringUtils.isNotBlank(usrCode)) {
			//生管为0，课长为1，组长为2
			String sql = "select max(tp) from(select distinct '1' as tp from zppt0035 where zkz = '"
					+ usrCode
					+ "' and zsg not like 'A%'"
					+ "union all select distinct '2' from zppt0035 where zoauser = '"
					+ usrCode
					+ "' and zsg = zzz and zsg not like 'A%'"
					+ "union all select distinct '0' from zppt0035 where zoauser = '"
					+ usrCode
					+ "' and zsg <> zzz and zsg not like 'A%'"
					+ " )";
			List<Object> objs = productionPlanService.findListBySql(sql);
			if (objs!=null&&objs.size()>0) {
				return StringUtils.nullToString(objs.get(0));
			}
		}
		return "";
	}
	
	/**
	 * 
	 * 获取生管编号
	 * 
	 * @return
	 */
	public String getSgNum() {
		String sql = "select max(zsg) from zppt0035 where zoauser = '"
				+ getUserCode() + "' and zsg not like 'A%'";
		return (String) productionPlanService.findListBySql(sql).get(0);
	}
	
	/**
	 * 获取Request
	 * @return
	 */
	private HttpServletRequest getRequest(){
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	/**
	 * 点击完工
	 */
	@RequestMapping("finishPlan")
	public void finishPlan(HttpServletRequest request, HttpServletResponse response) {
		String aufnr = request.getParameter("aufnr");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotBlank(aufnr)) {
			// 未勾选记录点击完工
			ZPPT0031 planDetail = productionPlanService.get(aufnr.trim());
			// 先查找记录,防止用户更改任务单号后点击完工找不到记录
			if (null != planDetail) {
				if (("1".equals(planDetail.getZwgpd())||"2".equals(planDetail.getZwgpd()))
						&&!isIndeedFinished(aufnr.trim())) {// 已经完工，再次点击完工恢复为原来状态
					planDetail.setZwgpd("0");
				} else {
					planDetail.setZwgpd("1");// 完工判断
				}
				productionPlanService.updatePlanDetail(planDetail);
			}
		} else if (StringUtils.isNotBlank(ids)) {
			String[] idArray = ids.split(",");
			if (idArray.length > 0) {
				for (String id : idArray) {
					ZPPT0031 planDetail = productionPlanService.get(id);
					if (null != planDetail) {
						if (("1".equals(planDetail.getZwgpd())||"2".equals(planDetail.getZwgpd()))
								&&!isIndeedFinished(aufnr.trim())) {// 已经完工，且未完成量不为0，再次点击完工恢复为原来状态
							planDetail.setZwgpd("0");
						} else {
							planDetail.setZwgpd("1");// 完工判断
						}
						productionPlanService.updatePlanDetail(planDetail);
					}
				}
			}
		}
	}
	
	/**
	 * 判断订单是否已完工，若未完成量为0，则返回true
	 * @param aufnr
	 * @return
	 */
	public boolean isIndeedFinished(String aufnr) {
		String sql = "select 1 from zppt0030 where zwwcl=0 and aufnr='" + aufnr
				+ "'";
		List<Object> list = productionPlanService.findListBySql(sql);
		if (null != list && list.size() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 点击保存
	 */
	@RequestMapping("savePlanDetail")
	public void savePlanDetail(HttpServletRequest request, HttpServletResponse response) {
		String errorOrder = "";
		String aufnr = request.getParameter("aufnr");
		String zyxj = request.getParameter("zyxj");
		String zqt = request.getParameter("zqt");
		String zsg = request.getParameter("zsg");
		String ids = request.getParameter("ids");
		String zlevel = request.getParameter("zlevel");
		if (StringUtils.isNotBlank(aufnr)) {
			errorOrder = saveSingleDetail(aufnr, zyxj, zqt, zsg, zlevel, true);
		} else if (StringUtils.isNotBlank(ids)) {
			String[] idArray = ids.split(",");
			if (idArray.length > 0) {
				for (String id : idArray) {
					String err = saveSingleDetail(id, zyxj, zqt, zsg, zlevel, false);
					if (StringUtils.isNotBlank(err)) {
						errorOrder = errorOrder + err + ",";
					}
				}
			}
		}
		if (StringUtils.isNotBlank(errorOrder) && errorOrder.length() > 0) {
			errorOrder = errorOrder.substring(0, errorOrder.length() - 1);
		}
		try {
			response.getOutputStream().write(errorOrder.getBytes("UTF-8"));
		} catch (Exception e) {
		}
	}
	
	/**
	 * 保存单条动态表记录
	 * 
	 * @param aufnr
	 * @param zyxj
	 * @param zqt
	 * @param zsg
	 */
	@SuppressWarnings("rawtypes")
	public String saveSingleDetail(String aufnr, String zyxj, String zqt,
			String zsg, String zlevel, boolean isSingle) {
		String res = null;
		ZPPT0031 planDetailTemp = productionPlanService.get(aufnr.trim());
		if (planDetailTemp == null) {
			// 新增
//			ZPPT0031 planDetail = new ZPPT0031();
//			planDetail.setAufnr(aufnr.trim());// 生产任务单
//			planDetail.setMandt(getClient());// 客户端
//			planDetail.setZqt(StringUtils.isBlank(zqt) ? " " : zqt.trim());// 备注
//			planDetail.setZsg(zsg);// 生管
//			planDetail.setZxhscrq(" ");// 线号生产日期
//			planDetail.setZypcl("0");// 已排产量
//			planDetail.setZyxj(StringUtils.isBlank(zyxj) ? "0100"
//					: StringUtils.addLeftZero(zyxj, 4));
//			planDetail.setZwgpd("0");// 未完工
//			planDetail.setZzt("0");// 未安排
//			planDetail.setZqlzt("0");// 不缺料
//			planDetail.setZwlzk(" ");// 缺料状况
//			planDetail.setZlevel(StringUtils.isBlank(zlevel) ? "1" : zlevel.trim());//难易度
//			productionPlanService.addPlanDetail(planDetail);
		} else {
			ZPPT0031 planDetail = productionPlanService.get(aufnr.trim());
			if (StringUtils.isNotBlank(zyxj) || isSingle)
				planDetail.setZyxj(StringUtils.isBlank(zyxj) ? " "
						: StringUtils.addLeftZero(zyxj, 4));
			if (StringUtils.isNotBlank(zlevel) || isSingle)
				planDetail.setZlevel(StringUtils.isBlank(zlevel) ? "1" : zlevel.trim());//难易度
			if (StringUtils.isNotBlank(zqt) || isSingle)
				planDetail.setZqt(StringUtils.isBlank(zqt) ? " " : zqt);
			if (StringUtils.isNotBlank(zsg)) {
				if (!zsg.equals(planDetail.getZsg())) {
					String checksql = "select 1 from zppt0033 where aufnr='"+aufnr.trim()+"' and mandt="+getClient();
					List checkList = productionPlanService.findListBySql(checksql);
					if (null != checkList
							&& checkList.size() > 0) {
						res = aufnr;
					}
				}
				if (StringUtils.isBlank(res)) {
					planDetail.setZsg(zsg.trim());
				}
			}
			productionPlanService.updatePlanDetail(planDetail);
		}
		return res;
	}
	
	
	/**
	 * 
	 * 上传完工模板
	 * 
	 * @param file
	 * @param model
	 */
	@RequestMapping(value = "/uploadWg", method = RequestMethod.POST)
	@ResponseBody
	public void uploadWgTemplate(@RequestParam(value = "uploadWgFile", required = false) MultipartFile file, HttpServletResponse response){
		response.setContentType(MediaType.TEXT_HTML_VALUE);
		try {
			InputStream is = file.getInputStream();
			HSSFWorkbook hwk = new HSSFWorkbook(is);
			HSSFSheet sh = hwk.getSheetAt(0);
		    HSSFRow firstRow = sh.getRow(0);
		    String[] headStr = new String[]{ "本次线别", "本次日期", "本次报工", "源工单", "源线别", "源日期", "源数量"};
		    for (int i = 0; i < headStr.length; i++) {
				HSSFCell cell = firstRow.getCell(i);
				String head = getCellValue(cell, false, false);
				if (!headStr[i].equals(head)) {
					response.getWriter().write("上传文件格式错误!");
					response.getWriter().flush();
					response.getWriter().close();
					return;
				}
			}
		    int rows = sh.getLastRowNum() + 1 - sh.getFirstRowNum();
		    this.getRequest().getSession().removeAttribute("wgUpload");
			Object usertype = this.getRequest().getSession().getAttribute(USER_TYPE);
			if (null == usertype
					|| (!USERTYPE_SG.equals(usertype) && !USERTYPE_KZSG.equals(usertype))) {
				response.getWriter().write("上传失败!需要以生管身份登录才能上传完工数量!");
				response.getWriter().flush();
				response.getWriter().close();
				return;
			}
			String[][] rowarr = new String[rows - 1][7];
			int rowno = 0;
			try{
				for (rowno = 0; rowno < rows - 1; rowno++) {
					HSSFRow row = sh.getRow(rowno + 1);
					int cols = row.getLastCellNum() - row.getFirstCellNum();
					for (int j = 0; j < cols; j++) {
						HSSFCell cell = row.getCell(j);
						String col = "";
						if(j==1||j==5){
							col = getCellValue(cell, true, true);
						}else{
							col = getCellValue(cell, true, false);
						}
						rowarr[rowno][j] = col;
					}
					Double.parseDouble(rowarr[rowno][2]);
					Double.parseDouble(rowarr[rowno][6]);
				}
			}catch (Exception e){
				response.getWriter().write("上传失败!上传文件第"+(rowno+1)+"行数据格式错误!");
				response.getWriter().flush();
				response.getWriter().close();
				return;
			}
			
			//完工上传文件临时列表
			List<String[]> wgUpload = new ArrayList<String[]>();
			for (int k = 0; k < rowarr.length; k++) {
				String bcxb = rowarr[k][0].trim();// 本次线别
				String bcrq = rowarr[k][1].trim();// 本次日期
				String bcbg = rowarr[k][2].trim();// 本次报工
				String aufnr = StringUtils.addLeftZero(rowarr[k][3], 12);// 源工单
				String zxh = rowarr[k][4].trim();// 源线别
				String gstrp = rowarr[k][5].trim();// 源日期
				String amt = rowarr[k][6].trim();// 源数量
				String id = String.valueOf(k);
				if (StringUtils.isNotBlank(bcxb)
						&& StringUtils.isNotBlank(bcrq)
						&& StringUtils.isNotBlank(bcbg)
						&& StringUtils.isNotBlank(aufnr)
						&& StringUtils.isNotBlank(zxh)
						&& StringUtils.isNotBlank(gstrp)
						&& StringUtils.isNotBlank(amt)) {
					Double bgamt = Double.valueOf(bcbg);
					Double pcamt = Double.valueOf(amt);
					if(bgamt.compareTo(pcamt)>0){
						response.getWriter().write("上传失败!生产订单:"+aufnr+"报工数量高于源数量!");
						response.getWriter().flush();
						response.getWriter().close();
						return;
					}
					String sql1 = "select nvl(sum(gamng01+gamng02+gamng03),0) from zppt0033 where aufnr='"+aufnr+"' and zxh='"+zxh+"' and gstrp>='"+gstrp+"'";
					Object sum1 = productionPlanService.findListBySql(sql1).get(0);
					Double su = Double.parseDouble(sum1.toString());
					if(su.compareTo(pcamt)<0){
						response.getWriter().write("订单源数量大于源日期之后的总排产量,订单号:"+aufnr+" 日期:"+gstrp +" 线号:"+zxh+" 源数量:"+pcamt);
						response.getWriter().flush();
						response.getWriter().close();
						return;
					}else{
						wgUpload.add(new String[] { bcxb,bcrq,bcbg,aufnr, zxh, gstrp, amt, id });
					}
				}else{
					response.getWriter().write("上传失败!第"+k+1+"行数据内包含空值!");
					response.getWriter().flush();
					response.getWriter().close();
					return;
				}
			}
			this.getRequest().getSession().setAttribute("wgUpload", wgUpload);
			response.getWriter().write("success");
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 完工数量模板下载
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/downloadWg")
	public void downLoadWgTemplate(@RequestParam("scrq") String scrq, HttpServletRequest request, HttpServletResponse response) throws Exception {
		OutputStream os = response.getOutputStream();
		String fileName = "completeTemplate.xls";
		response.reset();
		response.setHeader("Content-disposition","attachment; filename=" + fileName);
		response.setContentType("application/msexcel");
		InputStream in = this.getClass().getResourceAsStream("completeTemplate.xls");
		HSSFWorkbook workbook = new HSSFWorkbook(in);
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFCellStyle stringStyle = workbook.createCellStyle();
		HSSFCellStyle dateStyle = workbook.createCellStyle();
		HSSFDataFormat dateformat = workbook.createDataFormat();
		HSSFFont font = workbook.createFont();
		HSSFRow row = null;
		HSSFCell cell = null;
		String sql = " select aufnr,zxh,gstrp,sum(ct) from (select aufnr,zxh,gstrp,gamng01+gamng02+gamng03 as ct from zppt0033 a where gstrp='"+scrq.replaceAll("-", "")+"'";
		Object usertype = this.getRequest().getSession().getAttribute(USER_TYPE);
		if (null != usertype
				&& (USERTYPE_SG.equals(usertype) || USERTYPE_KZSG.equals(usertype))) {
			String sgNum = (String) this.getRequest().getSession()
					.getAttribute(SG_NUM);
			sql = sql + " and exists(select 1 from zppt0034 b where b.zxh = a.zxh and b.zsg='"+sgNum+"')";
		}
		sql = sql + " and exists (select 1 from zppt0030 c where c.aufnr=a.aufnr)"
			+ " and (a.gamng01+a.gamng02+a.gamng03)>0 )";
		sql = sql + " group by aufnr,zxh,gstrp order by gstrp,zxh,aufnr";
		List temp = productionPlanService.findListBySql(sql);
		if(null!=temp && temp.size()>0){
			int index = 1;
			Object[] obj; 
			for(Object o:temp){
				obj = (Object[])o;
				row = sheet.createRow(index++);
				cell =row.createCell(0);//本次线别
				cell.setCellStyle(getStyleForNormalDataRow(
						stringStyle, font, true));
				cell.setCellValue(String.valueOf(obj[1]));
				cell =row.createCell(1);//本次日期
				cell.setCellStyle(getStyleForDateDataRow(dateStyle, font, dateformat, true));
				cell.setCellValue(DateUtils.parseDate(String.valueOf(obj[2]),"yyyyMMdd"));
				cell =row.createCell(2);//本次报工
				cell.setCellStyle(getStyleForNormalDataRow(
						stringStyle, font, true));
				cell.setCellValue(String.valueOf(obj[3]));
				cell =row.createCell(3);//源工单
				cell.setCellStyle(getStyleForNormalDataRow(
						stringStyle, font, true));
				cell.setCellValue(String.valueOf(obj[0]));
				cell =row.createCell(4);//源线别
				cell.setCellStyle(getStyleForNormalDataRow(
						stringStyle, font, true));
				cell.setCellValue(String.valueOf(obj[1]));
				cell =row.createCell(5);//源日期
				cell.setCellStyle(getStyleForDateDataRow(dateStyle, font, dateformat, true));
				cell.setCellValue(DateUtils.parseDate(String.valueOf(obj[2]),"yyyyMMdd"));
				cell =row.createCell(6);//源数量
				cell.setCellStyle(getStyleForNormalDataRow(
						stringStyle, font, true));
				cell.setCellValue(String.valueOf(obj[3]));
			}
		}else{
			row = sheet.createRow(1);
			for (int i = 0; i < 7; i++) {
				cell = row.createCell(i);
				cell.setCellValue("");
			}
		}
		workbook.write(os);
		response.flushBuffer();
		os.close();
	}
	
	/**
	 * MES完工数量模板下载
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/downloadMes")
	public void downLoadWgFromMes(@RequestParam("scrq") String scrq, HttpServletRequest request, HttpServletResponse response) throws Exception {
		OutputStream os = response.getOutputStream();
		String fileName = "completeTemplate.xls";
		response.reset();
		response.setHeader("Content-disposition","attachment; filename=" + fileName);
		response.setContentType("application/msexcel");
		InputStream in = this.getClass().getResourceAsStream("completeTemplate.xls");
		HSSFWorkbook workbook = new HSSFWorkbook(in);
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFCellStyle stringStyle = workbook.createCellStyle();
		HSSFCellStyle dateStyle = workbook.createCellStyle();
		HSSFDataFormat dateformat = workbook.createDataFormat();
		HSSFFont font = workbook.createFont();
		HSSFRow row = null;
		HSSFCell cell = null;
		StringBuffer sql = new StringBuffer("with base as (select a.aufnr,a.zxh,a.gstrp,b.zsg,sum(a.gamng01 + a.gamng02 + a.gamng03) as ct from zppt0033 a");
		sql.append(" inner join zppt0031 b on a.aufnr = b.aufnr where a.gstrp = '").append(scrq.replaceAll("-", "")).append("' ");
		Object usertype = this.getRequest().getSession().getAttribute(USER_TYPE);
		if (null != usertype
				&& (USERTYPE_SG.equals(usertype) || USERTYPE_KZSG.equals(usertype))) {
			String sgNum = (String) this.getRequest().getSession()
					.getAttribute(SG_NUM);
			sql.append(" and b.zsg = '"+sgNum+"'");
		}
		sql.append(" group by a.aufnr, a.zxh, a.gstrp, b.zsg),")
			.append(" history as (select b.aufnr, b.zxh, sum(b.zwgl) as wgcnt")
			.append(" from base a inner join zppt0033 b on a.aufnr = b.aufnr and a.zxh = b.zxh")
			.append(" where b.gstrp <= '").append(scrq.replaceAll("-", "")).append("'")
			.append(" group by b.aufnr, b.zxh),")
			.append(" mes as (select c.aufnr, c.zxh, sum(d.i_trin) as mescnt")
			.append(" from base a")
			.append(" inner join zppt0068 c on a.zxh = c.zxh and a.aufnr = c.aufnr")
			.append(" inner join t_mes_troutc d on c.zgdh = d.s_won")
			.append(" where d.d_gettime < to_date(to_char(to_date('").append(scrq.replaceAll("-", "")).append("','yyyyMMdd')+1,'yyyyMMdd')||'070000', 'yyyyMMddhh24miss')")
			.append(" group by c.aufnr, c.zxh)")
			.append(" select ta.aufnr,ta.zxh,ta.gstrp,ta.ct,case when tc.mescnt > tb.wgcnt then tc.mescnt - tb.wgcnt else 0 end as comp,tc.mescnt as total,tb.wgcnt as bghis")
			.append(" from base ta ")
			.append(" left join history tb on ta.aufnr = tb.aufnr and ta.zxh = tb.zxh")
			.append(" left join mes tc on ta.aufnr = tc.aufnr and ta.zxh = tc.zxh");
		if (null != usertype
				&& (USERTYPE_SG.equals(usertype) || USERTYPE_KZSG.equals(usertype))) {
			String sgNum = (String) this.getRequest().getSession()
					.getAttribute(SG_NUM);
			sql.append(" where ta.zsg='"+sgNum+"'");
		}
		sql.append(" order by tb.zxh,ta.aufnr");
		
		List<String[]> temp = findWgListByJdbc(sql.toString());
		if(null!=temp && temp.size()>0){
			int index = 1;
			for(String[] obj:temp){
				row = sheet.createRow(index++);
				cell =row.createCell(0);//本次线别
				cell.setCellStyle(getStyleForNormalDataRow(
						stringStyle, font, true));
				cell.setCellValue(String.valueOf(obj[1]));
				cell =row.createCell(1);//本次日期
				cell.setCellStyle(getStyleForDateDataRow(dateStyle, font, dateformat, true));
				cell.setCellValue(DateUtils.parseDate(String.valueOf(obj[2]),"yyyyMMdd"));
				cell =row.createCell(2);//本次报工
				cell.setCellStyle(getStyleForNormalDataRow(
						stringStyle, font, true));
				cell.setCellValue(String.valueOf(obj[4]));
				cell =row.createCell(3);//源工单
				cell.setCellStyle(getStyleForNormalDataRow(
						stringStyle, font, true));
				cell.setCellValue(String.valueOf(obj[0]));
				cell =row.createCell(4);//源线别
				cell.setCellStyle(getStyleForNormalDataRow(
						stringStyle, font, true));
				cell.setCellValue(String.valueOf(obj[1]));
				cell =row.createCell(5);//源日期
				cell.setCellStyle(getStyleForDateDataRow(dateStyle, font, dateformat, true));
				cell.setCellValue(DateUtils.parseDate(String.valueOf(obj[2]),"yyyyMMdd"));
				cell =row.createCell(6);//源数量
				cell.setCellStyle(getStyleForNormalDataRow(
						stringStyle, font, true));
				cell.setCellValue(String.valueOf(obj[3]));
			}
		}else{
			row = sheet.createRow(1);
			for (int i = 0; i < 7; i++) {
				cell = row.createCell(i);
				cell.setCellValue("");
			}
		}
		workbook.write(os);
		response.flushBuffer();
		os.close();
	}
	
	@SuppressWarnings("finally")
	public List<String[]> findWgListByJdbc(String sql){
		List<String[]> result = new ArrayList<String[]>();
		String url = StringUtils.nullToString(propertyConfigurer.getProperty("multi.tenancy.sap.url"));
		String user = StringUtils.nullToString(propertyConfigurer.getProperty("multi.tenancy.sap.username"));
		String pwd = StringUtils.nullToString(propertyConfigurer.getProperty("multi.tenancy.sap.password"));
		Connection conn = null;
		PreparedStatement psStatement = null;
		ResultSet rSet = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			conn = DriverManager.getConnection(url, user, pwd);
			psStatement = conn.prepareStatement(sql);
			rSet = psStatement.executeQuery();
			while (rSet.next()) {
				String[] res = new String[7];
				res[0] = StringUtils.nullToString(rSet.getObject("aufnr"));//生产订单号
				res[1] = StringUtils.nullToString(rSet.getObject("zxh"));//线号
				res[2] = StringUtils.nullToString(rSet.getObject("gstrp"));//生产日期
				res[3] = StringUtils.nullToString(rSet.getObject("ct"));//计划量
				res[4] = StringUtils.nullToString(rSet.getObject("comp"));//完工量
				res[5] = StringUtils.nullToString(rSet.getObject("total"));//MES全部报工量
				res[6] = StringUtils.nullToString(rSet.getObject("bghis"));//历史报工量
				result.add(res);
				 
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
			return result;
		}
	}
	
	/**
	 * MPS模板下载
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/downloadMps")
	public void downLoadMpsTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		OutputStream os = response.getOutputStream();
		String fileName = "mpsDateTemplate.xls";
		response.reset();
		response.setHeader("Content-disposition","attachment; filename=" + fileName);
		response.setContentType("application/msexcel");
		InputStream in = this.getClass().getResourceAsStream("mpsDateTemplate.xls");
		HSSFWorkbook workbook = new HSSFWorkbook(in);
		workbook.write(os);
		response.flushBuffer();
		os.close();
	}
	
	/**
	 * 
	 * 上传MPS模板
	 * 
	 * @param file
	 * @param model
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/uploadMps", method = RequestMethod.POST)
	@ResponseBody
	public void uploadMpsTemplate(@RequestParam(value = "uploadMpsFile", required = false) MultipartFile file, HttpServletResponse response){
		response.setContentType(MediaType.TEXT_HTML_VALUE);
		int linked = 0;
		int unLink = 0;
		int inserterror = 0;
		int updateerror = 0;
		try {
			InputStream is = file.getInputStream();
			HSSFWorkbook hwk = new HSSFWorkbook(is);
			HSSFSheet sh = hwk.getSheetAt(0);
		    HSSFRow firstRow = sh.getRow(0);
		    String[] headStr = new String[]{ "销售订单号", "销售订单行项", "MPS上线日期", "备注", "生管", "生产订单号", "难易度"};
		    for (int i = 0; i < headStr.length; i++) {
				HSSFCell cell = firstRow.getCell(i);
				String head = getCellValue(cell, false, false);
				if (!headStr[i].equals(head)) {
					response.getWriter().write("上传文件格式错误!");
					response.getWriter().flush();
					response.getWriter().close();
					return;
				}
			}
			int rows = sh.getLastRowNum() + 1 - sh.getFirstRowNum();
		    String[][] rowarr = new String[rows - 1][7];
			int rowno = 0;
			try{
				for (rowno = 0; rowno < rows - 1; rowno++) {
					HSSFRow row = sh.getRow(rowno + 1);
					int cols = row.getLastCellNum() - row.getFirstCellNum();
					for (int j = 0; j < cols; j++) {
						HSSFCell cell = row.getCell(j);
						String col = "";
						if(j==2){
							col = getCellValue(cell, true, true);
						}else{
							col = getCellValue(cell, true, false);
						}
						rowarr[rowno][j] = col;
					}
				}
			}catch (Exception e){
				response.getWriter().write("上传失败!上传文件第"+(rowno+1)+"行数据格式错误!");
				response.getWriter().flush();
				response.getWriter().close();
				return;
			}
			//校验MPS生管
			List<String> sgLi =(List<String>) this.getRequest().getSession().getAttribute("sgLi");
			for(int i=0;i<rows - 1;i++){
				String sgTemp = rowarr[i][4];
				//只能组内分配
				if(StringUtils.isNotBlank(sgTemp)
						&&!sgLi.contains(sgTemp)){
					response.getWriter().write("上传失败!上传文件第"+(i+1)+"行生管'"+sgTemp+"'不存在或超出分配范围!");
					response.getWriter().flush();
					response.getWriter().close();
					return;
				}
				//生管只能将订单分配给自己，组长只能将自己手中订单分配给管辖内的生管
				String aufnr = StringUtils.isBlank(rowarr[i][5])?"":StringUtils.addLeftZero(rowarr[i][5], 12);
				if(StringUtils.isNotBlank(aufnr)){
					String checkSql = "select '1',zsg from zppt0031 where aufnr ='"+aufnr+"' union select '2',zsg from zppt0035 where zoauser = '"+getUserCode()+"'";
					List li = productionPlanService.findListBySql(checkSql);
					String ddSg = "";//订单所属生管
					String loginSg = "";//登录用户生管
					if(li!=null&&li.size()>0){
						for(Object obj:li){
							Object[] o = (Object[]) obj;
							if("1".equals(o[0].toString())){
								ddSg = o[1].toString();
							}
							if("2".equals(o[0].toString())){
								loginSg = o[1].toString();
							}
						}
					}
					if(StringUtils.isNotBlank(ddSg)){
						String userType =(String) this.getRequest().getSession().getAttribute(USER_TYPE);
						if (USERTYPE_KZSG.equals(userType)&&StringUtils.isNotBlank(sgTemp)) {
							// 组长或课长以生管身份登录
							String sgNum = (String) this.getRequest().getSession()
								.getAttribute(SG_NUM);
							String zz = sgTemp;
							if("A008".equals(sgNum)){
								//A008可以取回A005手中任务
								zz = "A005";
							}
							if (!ddSg.equals(sgNum) 
									&& !ddSg.equals(sgTemp)
									&& !ddSg.equals(zz)) {
								response.getWriter().write("上传失败!上传文件第"+ (i + 1) + "行,订单'" + aufnr + "'已经分配给生管"+ddSg);
								response.getWriter().flush();
								response.getWriter().close();
								return;
							}
						}
						if (USERTYPE_SG.equals(userType)&&StringUtils.isNotBlank(sgTemp)){
							// 组长或生管登录,只能将自己名下订单分配给组内人员
							String sgNum = (String) this.getRequest().getSession()
								.getAttribute(SG_NUM);
							List zzlist = productionPlanService.findListBySql("select 1 from zppt0035 where zzz='"+sgNum+"'");
							if(null!=zzlist && zzlist.size()>0){
								//组长登录
								String zz = sgTemp;
								if("A008".equals(sgNum)){
									//A008可以取回A005手中任务
									zz = "A005";
								}
								if(!ddSg.equals(loginSg)&&!ddSg.equals(sgTemp) && !ddSg.equals(zz)){
									response.getWriter().write("上传失败!上传文件第"+(i+1)+"行,订单'"+aufnr+"'已经分配给生管"+ddSg);
									response.getWriter().flush();
									response.getWriter().close();
									return;
								}
							}else{
								//普通生管登录
								Object zz = productionPlanService.findListBySql("select distinct(zzz) from zppt0035 where zsg='"+sgNum+"'").get(0);
								if("A008".equals(sgNum)){
									//A008可以取回A005手中任务
									zz = "A005";
								}
								if(!ddSg.equals(sgTemp)&&!ddSg.equals(zz.toString())){
									response.getWriter().write("上传失败!上传文件第"+(i+1)+"行,订单'"+aufnr+"'已经分配给生管"+ddSg);
									response.getWriter().flush();
									response.getWriter().close();
									return;
								}
							}
						}
					}
				}
			}
			
			//上传文件为mps上线日期
			this.getRequest().getSession().removeAttribute("mpsUnLinked");
			String sgSql = "select distinct zsg from zppt0035";
			List<Object> sgs = productionPlanService.findListBySql(sgSql);
			List<String> sgMap = new ArrayList<String>();
			if(null!=sgs && sgs.size()>0){
				for(Object sg:sgs){
					if(null!=sg){
						sgMap.add(sg.toString());
					}
				}
			}
			List<String[]> mpsUnLinked = new ArrayList<String[]>();
			for (int k = 0; k < rowarr.length; k++) {
				String vbeln = StringUtils.addLeftZero(rowarr[k][0], 10);// 销售订单号
				String posnr = StringUtils.addLeftZero(rowarr[k][1], 6);// 销售订单行项
				String date = rowarr[k][2];// MPS上线日期
				String desc = rowarr[k][3];// 备注
				String zsg = rowarr[k][4];// 生管
				String aufnr = StringUtils.isBlank(rowarr[k][5])?"":StringUtils.addLeftZero(rowarr[k][5], 12);// 生产订单
				String zlevel = rowarr[k][6];// 难易度
				
				String type = productionPlanService.updateMpsDate(getClient(),
						vbeln, posnr, date, desc , zsg, aufnr);
				//上传后将备注栏更新到zppt0031表的其他字段
				if(StringUtils.isNotBlank(desc)&&desc.length()>40){
					desc = desc.substring(0,39);
				}
				StringBuffer updSql = new StringBuffer(
						"update zppt0031 set zqt = zqt");
				if (StringUtils.isNotBlank(desc)) {
					//追加备注
					updSql.append("||' '||'").append(desc).append("'");
				}
				updSql.append(" ,zsxrq='").append(date).append("' ");
				if (StringUtils.isNotBlank(zlevel)){
					updSql.append(",zlevel='").append(zlevel.trim()).append("' ");
				}
				if (StringUtils.isNotBlank(zsg) && sgMap.contains(zsg)) {
					updSql.append(",zsg='").append(zsg.trim()).append("' ");
				}
				String checkSql = "";
				
				if(StringUtils.isNotBlank(aufnr)){
					updSql.append(" where aufnr='").append(aufnr).append("' ");
					checkSql = "select 1 from zppt0030 where aufnr = '"+aufnr+"'";
				} else {
					updSql.append(
							"  where aufnr in (select aufnr from zppt0030 where kdauf = '")
							.append(vbeln).append("' and kdpos = '")
							.append(posnr).append("') ");
					checkSql = "select 1 from zppt0030 where kdauf = '"+vbeln+"' and kdpos = '"+posnr+"'";
				}
				productionPlanService.execSql(updSql.toString());
				
				List obj = productionPlanService.findListBySql(checkSql);
				if(obj!=null && obj.size()>0){
					linked++;
				}else{
					unLink++;
					mpsUnLinked.add(new String[]{StringUtils.removeLeftZero(vbeln),StringUtils.removeLeftZero(posnr),date,desc,zsg,StringUtils.removeLeftZero(aufnr),zlevel});
				}
				
				if ("inserterror".equals(type)) {
					inserterror++;
				} else if ("updateerror".equals(type)) {
					updateerror++;
				}
			}
			this.getRequest().getSession().setAttribute("mpsUnLinked", mpsUnLinked);
			String result = "";
			if (linked > 0) {
				result = result.concat(linked + "条记录关联订单成功;");
			}
			if (unLink > 0) {
				result = result.concat(unLink + "条记录关联失败;");
			}
			if (updateerror > 0||inserterror > 0) {
				result = result.concat( String.valueOf(updateerror+inserterror) + "条记录上传失败;");
			}
			response.getWriter().write("上传成功!" + result);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 根据上传的完工文件，生成实际产量与计划任务的对比情况，同时生成当天各线别的生产负荷列表
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/generatePlanCompare")
	public void generatePlanCompare(@RequestParam("type") String gsType,@RequestParam("zz") String zzDefalt,@RequestParam("bz") String bzDefalt, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		System.out.println("==begin method generatePlanCompare==");
		//完工上传文件临时列表
		List<String[]> wgUpload =(List<String[]>) this.getRequest().getSession().getAttribute("wgUpload");
		if(null==wgUpload || wgUpload.size()==0){
			return;
		}
		Map<String,Object> result = new HashMap<String,Object>();
		//查询上传文件对应的排产计划列表
		StringBuffer planQuery = new StringBuffer("select aufnr,zxh,gstrp,sum(gamng01+gamng02+gamng03) from zppt0033 group by aufnr,zxh,gstrp having 1=2 ");
		//查询上传的生产订单对应的标准工时
		StringBuffer gsQuery = new StringBuffer("select a.aufnr,a.vgw01,a.vgw02,b.zlevel from zppt0030 a,zppt0031 b where a.aufnr=b.aufnr and a.aufnr in (");
		//线别生产日期对应的标准产能
		String sgNum =(String) this.getRequest().getSession().getAttribute(SG_NUM);
		StringBuffer cnQuery = new StringBuffer(
				"select zxh,zrq,ltxa1,gamng1+gamng2+gamng3 from zppt0034 where zrq='")
				.append(wgUpload.get(0)[1]).append("' and zsg='").append(sgNum).append("' ")
				.append("order by zrq,zxh ");
		//生成标准产能查询语句时判断是否条件重复的临时变量
		List<String> temp = new ArrayList<String>();
		//线别及生产日期对应生产订单列表映射结果集
		Map<String,List<String[]>> xbrqMap = new HashMap<String,List<String[]>>();
		for (int k = 0; k < wgUpload.size(); k++) {
			String[] rowarr = wgUpload.get(k);
			String bgxb = rowarr[0];// 本次线别
			String bgrq = rowarr[1];// 本次日期
			String bgamt = rowarr[2];// 本次报工
			String aufnr = rowarr[3];// 源订单
			String zxh = rowarr[4];// 源线别
			String gstrp = rowarr[5];// 源日期
			//String amt = rowarr[6];// 源数量
			List<String[]> aufnrs; 
			if(null==xbrqMap.get(bgxb.concat(bgrq))){
				aufnrs = new ArrayList<String[]>();
			}else{
				aufnrs = xbrqMap.get(bgxb.concat(bgrq));
			}
			aufnrs.add(new String[]{aufnr,bgamt});
			xbrqMap.put(bgxb.concat(bgrq), aufnrs);
			
			planQuery.append("or(aufnr = '")
				.append(aufnr)
				.append("' and zxh='")
				.append(zxh)
				.append("' and gstrp='")
				.append(gstrp)
				.append("') ");
			gsQuery.append("'")
				.append(aufnr)
				.append("'");
			if(k < wgUpload.size()-1){
				gsQuery.append(",");
			}
			String xbrq = zxh.concat("_").concat(gstrp);
			if(!temp.contains(xbrq)){
				temp.add(xbrq);
			}
		}
		gsQuery.append(") ");
		//标准产能列表
		List cnList = productionPlanService.findListBySql(cnQuery.toString());
		
		//标准工时列表
		List gsList = productionPlanService.findListBySql(gsQuery.toString());
		//标准工时映射：生产订单号：总装工时，包装工时，难易度
		Map<String,String[]> gsConfig = new HashMap<String,String[]>();
		if(null!=gsList && gsList.size()>0){
			for(Object o:gsList){
				Object[] obj = (Object[]) o;
				if("1".equals(gsType)){
					//启用统一配置工时
					gsConfig.put(String.valueOf(obj[0]), new String[]{zzDefalt,bzDefalt,String.valueOf(obj[3])});
				}else{
					//使用订单工时
					gsConfig.put(String.valueOf(obj[0]), new String[]{String.valueOf(obj[1]),String.valueOf(obj[2]),String.valueOf(obj[3])});
				}
			}
		}
		
		//生成上传时间范围内产线负荷列表
		List<String[]> loadsList = new ArrayList<String[]>();
		if(null!=cnList && cnList.size()>0){
			for(Object o:cnList){
				Object[] obj = (Object[]) o;
				String xb = String.valueOf(obj[0]);//线别
				String rq = String.valueOf(obj[1]);//日期
				String gx = String.valueOf(obj[2]);//工序
				Double zcn = Double.parseDouble(String.valueOf(obj[3]));//总产能
				Double cn = new Double(0);
				Double amtAll = new Double(0);
				List<String[]> dd_cl = xbrqMap.get(xb.concat(rq));
				//循环当前线别包含的订单列表，计算产能合计
				if(null!=dd_cl && dd_cl.size()>0){
					for(String[] dc :dd_cl){
						String aufnr = dc[0];//订单号
						Double cl = Double.parseDouble(dc[1]);//报工量
						String[] gsConf = gsConfig.get(aufnr);
						if(null==gsConf){//对应动态表已经被删除
							gsConf=new String[]{zzDefalt,bzDefalt,"1"};
						}
						Double zzgs = Double.parseDouble(gsConf[0]);//总装工时
						Double bzgs = Double.parseDouble(gsConf[1]);//包装工时
						Double zlevel = Double.parseDouble(gsConf[2]);//难易度
						if(gx.indexOf(GXB_ZZ)>-1){
							cn = cn + zzgs*cl/zlevel;
						}
						if(gx.indexOf(GXB_BZ)>-1){
							cn = cn + bzgs*cl/zlevel;
						}
						amtAll = amtAll + cl; 
					}
				}
				
				Double scfhPersent = roundHalfUp(((cn / zcn) * 100),1); // 负荷=已排产能/总产能
				String fh = String.valueOf(scfhPersent).concat("%");// 保留小数点后一位,四舍五入
				String color = (scfhPersent.compareTo(new Double(110))>0||scfhPersent.compareTo(new Double(100))<0)?"red":"black";
				loadsList.add(new String[]{color,xb,rq,String.valueOf(amtAll),fh});
			}
		}
		result.put("loads", loadsList);
		JSONArray ja = JSONArray.fromObject(result);
		System.out.println("==exit method generatePlanCompare==");
		response.getOutputStream().write(ja.toString().getBytes("UTF-8"));
	}
	
	
	/**
	 * 确认更新上传的完工数量与计划量不匹配的排产记录
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("completeConfirm")
	public void completeConfirm(@RequestParam("type") String gsType,@RequestParam("zz") String zzDefalt,@RequestParam("bz") String bzDefalt, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
		List<String[]> temp = new ArrayList<String[]>();
		// 完工上传文件临时列表
		List<String[]> wgUpload = (List<String[]>) this.getRequest()
				.getSession().getAttribute("wgUpload");
		String errorString = "";
		Map<String,String> map = new HashMap<String,String>();
		for (String[] wg : wgUpload) {
			try{
				productionPlanService.updateCompletePlans(wg,gsType,zzDefalt,bzDefalt);
				productionPlanService.deleteZeroPlan(wg[3]);//删除零排产数据
				map.put(StringUtils.nullToString(wg[0])+"|"+StringUtils.nullToString(wg[3]),"1");
				map.put(StringUtils.nullToString(wg[4])+"|"+StringUtils.nullToString(wg[3]),"1");
				temp.add(wg);
			}catch(Exception e){
				e.printStackTrace();
				errorString+=","+wg[3];
			}
		}
		for(String key:map.keySet()){
			String aufnr = StringUtils.addLeftZero(key.split("\\|")[1],12);
			String zxh = key.split("\\|")[0];
			String sql = "select nvl(sum(zwgl),0) from zppt0033 "
				+ " where aufnr = '"+aufnr+"' and zxh='"+zxh+"' and mandt="+getClient();
			List<Object> planList = productionPlanService.findListBySql(sql);
			if(null!=planList&&planList.size()>0){
				Double doneAmt = Double.parseDouble(StringUtils.nullToString(planList.get(0)));
				productionPlanService.updateCompleteCountForWorkOrder(aufnr,zxh,doneAmt);
			}
		}
		this.getRequest().getSession().setAttribute("wgUploadSuccess",temp);
		this.getRequest().getSession().removeAttribute("wgUpload");
		String message = "更新成功"+ (StringUtils.isBlank(errorString)?"":errorString);
		response.getOutputStream().write(message.getBytes("UTF-8"));
	}
	
	/**
	 * 更新完工数量后导出更新成功数据报表
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/exportSuccessComplete")
	public void exportSuccessComplete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<String[]> temp = (List<String[]>) this.getRequest().getSession().getAttribute("wgUploadSuccess");
		// 上传成功后导出影响记录
		OutputStream os = response.getOutputStream();
		String fileName = "uploadSuccess_" + DateUtils.formatDate(new Date(), "yyyyMMdd_HHmmss") + ".xls";
		response.reset();
		response.setHeader("Content-disposition","attachment; filename=" + fileName);
		response.setContentType("application/msexcel");
		InputStream in = this.getClass().getResourceAsStream("completeTemplate.xls");
		HSSFWorkbook workbook = new HSSFWorkbook(in);
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFCellStyle stringStyle = workbook.createCellStyle();
		HSSFCellStyle dateStyle = workbook.createCellStyle();
		HSSFDataFormat dateformat = workbook.createDataFormat();
		HSSFFont font = workbook.createFont();
		HSSFRow row = null;
		HSSFCell cell = null;
		if (null != temp && temp.size() > 0) {
			int index = 1;
			for (String[] obj : temp) {
				row = sheet.createRow(index++);
				cell =row.createCell(0);//本次线别
				cell.setCellStyle(getStyleForNormalDataRow(
						stringStyle, font, true));
				cell.setCellValue(String.valueOf(obj[0]));
				cell =row.createCell(1);//本次日期
				cell.setCellStyle(getStyleForDateDataRow(dateStyle, font, dateformat, true));
				cell.setCellValue(DateUtils.parseDate(String.valueOf(obj[1]),"yyyyMMdd"));
				cell =row.createCell(2);//本次报工
				cell.setCellStyle(getStyleForNormalDataRow(
						stringStyle, font, true));
				cell.setCellValue(String.valueOf(obj[2]));
				cell =row.createCell(3);//源工单
				cell.setCellStyle(getStyleForNormalDataRow(
						stringStyle, font, true));
				cell.setCellValue(String.valueOf(obj[3]));
				cell =row.createCell(4);//源线别
				cell.setCellStyle(getStyleForNormalDataRow(
						stringStyle, font, true));
				cell.setCellValue(String.valueOf(obj[4]));
				cell =row.createCell(5);//源日期
				cell.setCellStyle(getStyleForDateDataRow(dateStyle, font, dateformat, true));
				cell.setCellValue(DateUtils.parseDate(String.valueOf(obj[5]),"yyyyMMdd"));
				cell =row.createCell(6);//源数量
				cell.setCellStyle(getStyleForNormalDataRow(
						stringStyle, font, true));
				cell.setCellValue(String.valueOf(obj[6]));
			}
		}else{
			row = sheet.createRow(1);
			for (int i = 0; i < 7; i++) {
				cell = row.createCell(i);
				cell.setCellValue("");
			}
		}
		//清除session信息
//		this.getRequest().getSession().removeAttribute("wgUploadSuccess");
//		this.getRequest().getSession().removeAttribute("wgUpload");
		workbook.write(os);
		response.flushBuffer();
		os.close();
	}
	
	/**
	 * 导出排产计划报表
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/exportExcelAll")
	public void exportExcelAll(@RequestParam("reportType") String reportType,HttpServletRequest request, HttpServletResponse response) throws Exception {
		int daycount = Integer.parseInt(reportType);
		String searchType1 = request.getParameter("searchType1");
		String searchValue1 = request.getParameter("searchValue1");
		String searchType2 = request.getParameter("searchType2");
		String searchValue2 = request.getParameter("searchValue2");
		String searchType3 = request.getParameter("searchType3");
		String searchValue3 = request.getParameter("searchValue3");
		String searchValue3to = request.getParameter("searchValue3to");
		String searchType4 = request.getParameter("searchType4");
		String searchValue4 = request.getParameter("searchValue4");
		// 查询数据
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(searchValue1)) {
			map.put(searchType1, searchValue1);
		}
		if (StringUtils.isNotBlank(searchValue2)) {
			map.put(searchType2, searchValue2);
		}
		if (StringUtils.isNotBlank(searchValue3)) {
			map.put(searchType3.concat("from"), searchValue3.replaceAll("-", ""));
		}
		if (StringUtils.isNotBlank(searchValue3to)) {
			map.put(searchType3.concat("to"), searchValue3to.replaceAll("-", ""));
		}
		if (StringUtils.isNotBlank(searchValue4)) {
			String temp;
			if("aufnr".equals(searchType4)){
				//生产订单号补足12位
				temp = StringUtils.addLeftZero(searchValue4,12);
			}else if("kdauf".equals(searchType4)){
				//销售订单号补足10位
				temp = StringUtils.addLeftZero(searchValue4,10);
			}else if("matnr".equals(searchType4)){
				//物料ID补足18位
				temp = StringUtils.addLeftZero(searchValue4,18);
			}else{
				temp=searchValue4;
			}
			map.put(searchType4, temp);
		}
		String type = (String) this.getRequest().getSession()
				.getAttribute(USER_TYPE);
		if (USERTYPE_SG.equals(type) || USERTYPE_KZSG.equals(type)) {
			String sgNum = (String) this.getRequest().getSession()
					.getAttribute(SG_NUM);
			map.put("zsg", sgNum);
		}
		// 开始日期
		String beginDay = DateUtils.formatDate(new Date(), "yyyyMMdd");
		if("scrq".equals(searchType3)
				&&StringUtils.isNotBlank(searchValue3)){
			beginDay = searchValue3.replaceAll("-", "");
		}
		// 查询接下来十天内的可排产日期
		String sqlA = "select zrq from(select zrq, rownum from (select distinct zrq from zppt0034 where zrq >= '"
				+ beginDay + "'";
		if (USERTYPE_SG.equals(type) || USERTYPE_KZSG.equals(type)) {
			String sgNum = (String) this.getRequest().getSession()
					.getAttribute(SG_NUM);
			sqlA+=" and zsg='"+sgNum+"' ";
		}
		sqlA += " order by zrq) where rownum <= 10)";
		List days = productionPlanService.findListBySql(sqlA);
		if (days != null && days.size() > daycount) {
			days = days.subList(0, daycount);
		}
		map.put("days", days);
		List<PlanShare> li = productionPlanService.getCurrentPlan(map);
		String startDay = "";
		String endDay = "";
		if (null != days && days.size() > 0) {
			startDay = DateUtils.formatDate(DateUtils.parseDate(
					String.valueOf(days.get(0)), "yyyyMMdd"), "MM月dd日");
			endDay = DateUtils.formatDate(DateUtils.parseDate(
					String.valueOf(days.get(daycount - 1)), "yyyyMMdd"),
					"MM月dd日");
		}
		OutputStream os = response.getOutputStream();
		String fileName = "ProductPlan_"
				+ DateUtils.formatDate(new Date(), "yyyyMMdd_HHmmss") + ".xls";
		response.reset();
		response.setHeader("Content-disposition",
				"attachment; filename=" + fileName);
		response.setContentType("application/msexcel");
		InputStream in = this.getClass().getResourceAsStream("planSharing"+String.valueOf(daycount)+"day.xls");
		HSSFWorkbook workbook = new HSSFWorkbook(in);
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFRow row = null;
		HSSFCell cell = null;
		// 设置第一行标题
		row = sheet.getRow(0);
		cell = row.getCell(0);
		cell.setCellValue("                                 整灯生产计划");
		// 设置第二行标题
		row = sheet.getRow(1);
		cell = row.getCell(0);
		cell.setCellValue("                                ".concat(startDay)
				.concat("-").concat(endDay));
		// 设置第三行排产日期
		row = sheet.getRow(2);
		for (int i = 0; i < daycount; i++) {
			cell = row.getCell(14 + i * 3);
			cell.setCellValue(DateUtils.parseDate(String.valueOf(days.get(i)),
					"yyyyMMdd"));
		}
		// 设置第四行排产日期-星期
		row = sheet.getRow(3);
		for (int i = 0; i < daycount; i++) {
			cell = row.getCell(14 + i * 3);
			cell.setCellValue(DateUtils.getWeekOfDate(DateUtils.parseDate(
					String.valueOf(days.get(i)), "yyyyMMdd")));
		}
		// 合计行
		int[] lineSum = new int[30];// 线别合计
		int[] allSum = new int[30];// 全部合计
		HSSFCellStyle styleArray[] = new HSSFCellStyle[4];
		HSSFFont font = workbook.createFont();
		HSSFDataFormat dateformat = workbook.createDataFormat();
		for (int s = 0; s < 4; s++) {
			styleArray[s] = workbook.createCellStyle();
		}
		int rowCount = 5;
		String lineNo = null;// 线号
		for (PlanShare ps : li) {
			boolean normal = "red".equals(ps.getColor()) ? false : true;
			// 切换行号，添加合计行
			if (null != lineNo && !lineNo.equals(ps.getLineNo())) {
				row = sheet.createRow(rowCount++);
				for (int i = 0; i < (17 + daycount * 3); i++) {
					cell = row.createCell(i);
					cell.setCellStyle(getStyleForSumDataRow(styleArray[1]));
				}
				for (int i = 0; i < daycount * 3; i++) {
					cell = row.getCell(14 + i);
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(Integer.valueOf(lineSum[i]));
				}
				lineSum = new int[30];
			}
			lineNo = ps.getLineNo();
			row = sheet.createRow(rowCount++);
			for (int i = 0; i < (17 + daycount * 3); i++) {
				cell = row.createCell(i);
				cell.setCellStyle(getStyleForNormalDataRow(styleArray[0], font,
						normal));
//				if(i<12||i>(13+daycount * 3)){
//				}else{
//					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3], font,
//							normal));
//				}
			}
			// 线号
			cell = row.getCell(0);
			cell.setCellValue(ps.getLineNo());
			// 客户
			cell = row.getCell(1);
			cell.setCellValue(ps.getCustNo());
			// 生产订单号
			cell = row.getCell(2);
			cell.setCellValue(StringUtils.removeLeftZero(ps.getProductOrder()));
			// 销售订单号
			cell = row.getCell(3);
			cell.setCellValue(StringUtils.removeLeftZero(ps.getSaleOrder()));
			// 行项目
			cell = row.getCell(4);
			cell.setCellValue(StringUtils.removeLeftZero(ps.getOrderLineNo()));
			// 物料id
			cell = row.getCell(5);
			cell.setCellValue(StringUtils.removeLeftZero(ps.getWlId()));
			// 物料描述
			cell = row.getCell(6);
			cell.setCellValue(ps.getWlDesc());
			// 订单量
			cell = row.getCell(7);
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellStyle(getStyleForNormalNumRow(styleArray[3], font,
					normal));
			cell.setCellValue(Integer.valueOf(ps.getOrderAmt()));
			// 交期
			cell = row.getCell(8);
			if (StringUtils.isNotBlank(ps.getSdExpDate())&&!"00000000".equals(ps.getSdExpDate())) {
				cell.setCellValue(DateUtils.parseDate(ps.getSdExpDate(),
						"yyyyMMdd"));
				cell.setCellStyle(getStyleForDateDataRow(styleArray[2], font,
						dateformat, normal));
			}
			// MPS上线日期
			cell = row.getCell(9);
			if (StringUtils.isNotBlank(ps.getMpsDate())
					&& !"00000000".equals(ps.getMpsDate())
					&& !"null".equals(ps.getMpsDate())) {
				cell.setCellValue(DateUtils.parseDate(ps.getMpsDate(),
				"yyyyMMdd"));
				cell.setCellStyle(getStyleForDateDataRow(styleArray[2], font,
						dateformat, normal));
			}
			// 总装工时
			cell = row.getCell(10);
			cell.setCellStyle(getStyleForNormalNumRow(styleArray[3], font,
					normal));
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(ps.getZzHours());
			// 包装工时
			cell = row.getCell(11);
			cell.setCellStyle(getStyleForNormalNumRow(styleArray[3], font,
					normal));
			cell.setCellValue(ps.getBzHours());
			// 计划量
			cell = row.getCell(12);
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellStyle(getStyleForNormalNumRow(styleArray[3], font,
					normal));
			cell.setCellValue(Integer.valueOf(ps.getPlanAmt()));
			// 已完成量
			cell = row.getCell(13);
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellStyle(getStyleForNormalNumRow(styleArray[3], font,
					normal));
			cell.setCellValue(Integer.valueOf(ps.getCompleteAmt()));
			// 状态
			cell = row.getCell(14 + daycount * 3);
			cell.setCellValue(ps.getStatus());
			// 物料状态
			cell = row.getCell(15 + daycount * 3);
			cell.setCellValue(ps.getWlStatus());
			// 备注-其他
			cell = row.getCell(16 + daycount * 3);
			cell.setCellValue(ps.getDesc());
			// 3天计划
			cell = row.getCell(14);
			if (!"0".equals(ps.getDay1A())) {
				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				cell.setCellValue(Integer.valueOf(ps.getDay1A()));
				cell.setCellStyle(getStyleForNormalNumRow(styleArray[3], font,
						normal));
				lineSum[0] += Integer.parseInt(ps.getDay1A());
				allSum[0] += Integer.parseInt(ps.getDay1A());
			}
			cell = row.getCell(15);
			if (!"0".equals(ps.getDay1B())) {
				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				cell.setCellValue(Integer.valueOf(ps.getDay1B()));
				cell.setCellStyle(getStyleForNormalNumRow(styleArray[3], font,
						normal));
				lineSum[1] += Integer.parseInt(ps.getDay1B());
				allSum[1] += Integer.parseInt(ps.getDay1B());
			}
			cell = row.getCell(16);
			if (!"0".equals(ps.getDay1C())) {
				cell.setCellStyle(getStyleForNormalNumRow(styleArray[3], font,
						normal));
				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				cell.setCellValue(Integer.valueOf(ps.getDay1C()));
				lineSum[2] += Integer.parseInt(ps.getDay1C());
				allSum[2] += Integer.parseInt(ps.getDay1C());
			}
			cell = row.getCell(17);
			if (!"0".equals(ps.getDay2A())) {
				cell.setCellStyle(getStyleForNormalNumRow(styleArray[3], font,
						normal));
				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				cell.setCellValue(Integer.valueOf(ps.getDay2A()));
				lineSum[3] += Integer.parseInt(ps.getDay2A());
				allSum[3] += Integer.parseInt(ps.getDay2A());
			}
			cell = row.getCell(18);
			if (!"0".equals(ps.getDay2B())) {
				cell.setCellStyle(getStyleForNormalNumRow(styleArray[3], font,
						normal));
				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				cell.setCellValue(Integer.valueOf(ps.getDay2B()));
				lineSum[4] += Integer.parseInt(ps.getDay2B());
				allSum[4] += Integer.parseInt(ps.getDay2B());
			}
			cell = row.getCell(19);
			if (!"0".equals(ps.getDay2C())) {
				cell.setCellStyle(getStyleForNormalNumRow(styleArray[3], font,
						normal));
				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				cell.setCellValue(Integer.valueOf(ps.getDay2C()));
				lineSum[5] += Integer.parseInt(ps.getDay2C());
				allSum[5] += Integer.parseInt(ps.getDay2C());
			}
			cell = row.getCell(20);
			if (!"0".equals(ps.getDay3A())) {
				cell.setCellStyle(getStyleForNormalNumRow(styleArray[3], font,
						normal));
				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				cell.setCellValue(Integer.valueOf(ps.getDay3A()));
				lineSum[6] += Integer.parseInt(ps.getDay3A());
				allSum[6] += Integer.parseInt(ps.getDay3A());
			}
			cell = row.getCell(21);
			if (!"0".equals(ps.getDay3B())) {
				cell.setCellStyle(getStyleForNormalNumRow(styleArray[3], font,
						normal));
				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				cell.setCellValue(Integer.valueOf(ps.getDay3B()));
				lineSum[7] += Integer.parseInt(ps.getDay3B());
				allSum[7] += Integer.parseInt(ps.getDay3B());
			}
			cell = row.getCell(22);
			if (!"0".equals(ps.getDay3C())) {
				cell.setCellStyle(getStyleForNormalNumRow(styleArray[3], font,
						normal));
				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				cell.setCellValue(Integer.valueOf(ps.getDay3C()));
				lineSum[8] += Integer.parseInt(ps.getDay3C());
				allSum[8] += Integer.parseInt(ps.getDay3C());
			}
			if (daycount >= 7) {
				cell = row.getCell(23);
				if (!"0".equals(ps.getDay4A())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(Integer.valueOf(ps.getDay4A()));
					lineSum[9] += Integer.parseInt(ps.getDay4A());
					allSum[9] += Integer.parseInt(ps.getDay4A());
				}
				cell = row.getCell(24);
				if (!"0".equals(ps.getDay4B())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(Integer.valueOf(ps.getDay4B()));
					lineSum[10] += Integer.parseInt(ps.getDay4B());
					allSum[10] += Integer.parseInt(ps.getDay4B());
				}
				cell = row.getCell(25);
				if (!"0".equals(ps.getDay4C())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(Integer.valueOf(ps.getDay4C()));
					lineSum[11] += Integer.parseInt(ps.getDay4C());
					allSum[11] += Integer.parseInt(ps.getDay4C());
				}
				cell = row.getCell(26);
				if (!"0".equals(ps.getDay5A())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(Integer.valueOf(ps.getDay5A()));
					lineSum[12] += Integer.parseInt(ps.getDay5A());
					allSum[12] += Integer.parseInt(ps.getDay5A());
				}
				cell = row.getCell(27);
				if (!"0".equals(ps.getDay5B())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(Integer.valueOf(ps.getDay5B()));
					lineSum[13] += Integer.parseInt(ps.getDay5B());
					allSum[13] += Integer.parseInt(ps.getDay5B());
				}
				cell = row.getCell(28);
				if (!"0".equals(ps.getDay5C())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(Integer.valueOf(ps.getDay5C()));
					lineSum[14] += Integer.parseInt(ps.getDay5C());
					allSum[14] += Integer.parseInt(ps.getDay5C());
				}
				cell = row.getCell(29);
				if (!"0".equals(ps.getDay6A())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(Integer.valueOf(ps.getDay6A()));
					lineSum[15] += Integer.parseInt(ps.getDay6A());
					allSum[15] += Integer.parseInt(ps.getDay6A());
				}
				cell = row.getCell(30);
				if (!"0".equals(ps.getDay6B())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(Integer.valueOf(ps.getDay6B()));
					lineSum[16] += Integer.parseInt(ps.getDay6B());
					allSum[16] += Integer.parseInt(ps.getDay6B());
				}
				cell = row.getCell(31);
				if (!"0".equals(ps.getDay6C())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(Integer.valueOf(ps.getDay6C()));
					lineSum[17] += Integer.parseInt(ps.getDay6C());
					allSum[17] += Integer.parseInt(ps.getDay6C());
				}
				cell = row.getCell(32);
				if (!"0".equals(ps.getDay7A())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(Integer.valueOf(ps.getDay7A()));
					lineSum[18] += Integer.parseInt(ps.getDay7A());
					allSum[18] += Integer.parseInt(ps.getDay7A());
				}
				cell = row.getCell(33);
				if (!"0".equals(ps.getDay7B())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(Integer.valueOf(ps.getDay7B()));
					lineSum[19] += Integer.parseInt(ps.getDay7B());
					allSum[19] += Integer.parseInt(ps.getDay7B());
				}
				cell = row.getCell(34);
				if (!"0".equals(ps.getDay7C())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(Integer.valueOf(ps.getDay7C()));
					lineSum[20] += Integer.parseInt(ps.getDay7C());
					allSum[20] += Integer.parseInt(ps.getDay7C());
				}
			}
			if (daycount >= 10) {
				cell = row.getCell(35);
				if (!"0".equals(ps.getDay8A())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(Integer.valueOf(ps.getDay8A()));
					lineSum[21] += Integer.parseInt(ps.getDay8A());
					allSum[21] += Integer.parseInt(ps.getDay8A());
				}
				cell = row.getCell(36);
				if (!"0".equals(ps.getDay8B())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(Integer.valueOf(ps.getDay8B()));
					lineSum[22] += Integer.parseInt(ps.getDay8B());
					allSum[22] += Integer.parseInt(ps.getDay8B());
				}
				cell = row.getCell(37);
				if (!"0".equals(ps.getDay8C())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(Integer.valueOf(ps.getDay8C()));
					lineSum[23] += Integer.parseInt(ps.getDay8C());
					allSum[23] += Integer.parseInt(ps.getDay8C());
				}
				cell = row.getCell(38);
				if (!"0".equals(ps.getDay9A())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(Integer.valueOf(ps.getDay9A()));
					lineSum[24] += Integer.parseInt(ps.getDay9A());
					allSum[24] += Integer.parseInt(ps.getDay9A());
				}
				cell = row.getCell(39);
				if (!"0".equals(ps.getDay9B())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(Integer.valueOf(ps.getDay9B()));
					lineSum[25] += Integer.parseInt(ps.getDay9B());
					allSum[25] += Integer.parseInt(ps.getDay9B());
				}
				cell = row.getCell(40);
				if (!"0".equals(ps.getDay9C())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(Integer.valueOf(ps.getDay9C()));
					lineSum[26] += Integer.parseInt(ps.getDay9C());
					allSum[26] += Integer.parseInt(ps.getDay9C());
				}
				cell = row.getCell(41);
				if (!"0".equals(ps.getDay0A())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(Integer.valueOf(ps.getDay0A()));
					lineSum[27] += Integer.parseInt(ps.getDay0A());
					allSum[27] += Integer.parseInt(ps.getDay0A());
				}
				cell = row.getCell(42);
				if (!"0".equals(ps.getDay0B())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(Integer.valueOf(ps.getDay0B()));
					lineSum[28] += Integer.parseInt(ps.getDay0B());
					allSum[28] += Integer.parseInt(ps.getDay0B());
				}
				cell = row.getCell(43);
				if (!"0".equals(ps.getDay0C())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(Integer.valueOf(ps.getDay0C()));
					lineSum[29] += Integer.parseInt(ps.getDay0C());
					allSum[29] += Integer.parseInt(ps.getDay0C());
				}
			}
		}
		// 最后一条线合计项
		row = sheet.createRow(rowCount++);
		for (int i = 0; i < (17 + daycount * 3); i++) {
			cell = row.createCell(i);
			cell.setCellStyle(getStyleForSumDataRow(styleArray[1]));
		}
		for (int i = 0; i < daycount * 3; i++) {
			cell = row.getCell(14 + i);
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(Integer.valueOf(lineSum[i]));
		}
		// 所有合计项
		row = sheet.createRow(rowCount++);
		for (int i = 0; i < (17 + daycount * 3); i++) {
			cell = row.createCell(i);
			cell.setCellStyle(getStyleForSumDataRow(styleArray[1]));
		}
		cell = row.getCell(0);
		cell.setCellValue("合计");
		for (int i = 0; i < daycount * 3; i++) {
			cell = row.getCell(14 + i);
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(Integer.valueOf(allSum[i]));
		}
		// 当日排产总量 add by heshi 20130924
		row = sheet.createRow(rowCount++);
		for (int i = 0; i < (17 + daycount * 3); i++) {
			cell = row.createCell(i);
			cell.setCellStyle(getStyleForSumDataRow(styleArray[1]));
		}
		cell = row.getCell(0);
		cell.setCellValue("当日排产总量");
		for (int i = 0; i < daycount; i++) {
			cell = row.getCell(14 + i*3);
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(Integer.valueOf(allSum[i*3]+allSum[i*3+1]+allSum[i*3+2]));
		}

		workbook.write(os);
		response.flushBuffer();
		os.close();
	}
	
	/**
	 * 取得当前登录工号
	 * @return
	 */
	private String getUserCode(){
		String userId = ((UserDetails)SpringSecurityUtils.getAuthentication().getPrincipal()).getUserId();
		User user = productionPlanService.getCurrentUser(userId);
		return user.getEmployeeNumber();
	}
	
	private boolean hasRole(String roleName){
		String userId = ((UserDetails)SpringSecurityUtils.getAuthentication().getPrincipal()).getUserId();
		return securityApiFacade.hasRole(userId,roleName);
	}
	
	/**
	 * 对double类型进行精度修改 四舍五入
	 * 
	 * @param value
	 * @param scale
	 * @return
	 */
	private static Double roundHalfUp(Double value, int scale) {
		BigDecimal b = new BigDecimal(value.toString());
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	
	public String getClient() {
		return sapConfig.getClient();
	}
	public String getCellValue(HSSFCell cell, boolean isUpperCase,
			boolean isdate) {
		String returnValue = "";

		if (null != cell) {

			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC: // 数字或日期
				if (isdate) {
					Date date = cell.getDateCellValue();
					returnValue = DateUtils.formatDate(date, "yyyyMMdd");
				} else {
					Double doubleValue = cell.getNumericCellValue();
					DecimalFormat df = new DecimalFormat("0");  
					String str = df.format(doubleValue);
					if (str.endsWith(".0")) {
						str = str.substring(0, str.length() - 2);
					}
					returnValue = str;
				}
				break;
			case HSSFCell.CELL_TYPE_STRING: // 字符串
				if (isUpperCase)
					returnValue = cell.getStringCellValue() == null ? "" : cell
							.getStringCellValue().trim().toUpperCase();
				else
					returnValue = cell.getStringCellValue() == null ? "" : cell
							.getStringCellValue().trim();
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN: // 布尔
				Boolean booleanValue = cell.getBooleanCellValue();
				returnValue = booleanValue.toString();
				break;
			case HSSFCell.CELL_TYPE_BLANK: // 空值
				returnValue = "";
				break;
			case HSSFCell.CELL_TYPE_FORMULA: // 公式
				returnValue = cell.getCellFormula();
				break;
			case HSSFCell.CELL_TYPE_ERROR: // 故障
				returnValue = "";
				break;
			default:
				returnValue = "";
				break;
			}
		}
		return returnValue;
	}
	
	/**
	 * 创建正常数据行样式
	 * 
	 * @param style
	 *            样式表
	 * @param font
	 *            字体
	 * @param font
	 *            字体
	 * @return
	 */
	public static HSSFCellStyle getStyleForNormalDataRow(HSSFCellStyle style,
			HSSFFont font, boolean normal) {
		// 设置底边框;
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为左侧对齐;
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		font.setColor(HSSFColor.BLACK.index);
		style.setFont(font);
		return style;
	}
	
	/**
	 * 创建日期数据行样式
	 * 
	 * @param style
	 *            样式表
	 * @param font
	 *            字体
	 * @param font
	 *            字体
	 * @return
	 */
	public static HSSFCellStyle getStyleForDateDataRow(HSSFCellStyle style,
			HSSFFont font, HSSFDataFormat format, boolean normal) {
		// 设置底边框;
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为左侧对齐;
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		font.setColor(HSSFColor.BLACK.index);
		style.setFont(font);
		style.setDataFormat(format.getFormat("m-d"));
		return style;
	}
	
	/**
	 * 创建正常数值行样式
	 * 
	 * @param style
	 *            样式表
	 * @param font
	 *            字体
	 * @param font
	 *            字体
	 * @return
	 */
	public static HSSFCellStyle getStyleForNormalNumRow(HSSFCellStyle style,
			HSSFFont font, boolean normal) {
		// 设置底边框;
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为右侧对齐;
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		font.setColor(HSSFColor.BLACK.index);
		// if(normal){
		// font.setColor(HSSFColor.BLACK.index);
		// }else{
		// font.setColor(HSSFColor.RED.index);
		// }
		style.setFont(font);
		return style;
	}
	
	/**
	 * 创建合计项数据行样式
	 * 
	 * @param style
	 *            样式表
	 * @param font
	 *            字体
	 * @return
	 */
	public static HSSFCellStyle getStyleForSumDataRow(HSSFCellStyle style) {
		// 设置底边框;
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为左侧对齐;
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 设置背景颜色
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.AQUA.index);
		return style;
	}
}