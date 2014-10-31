package com.wellsoft.pt.ldx.web.ficoManage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wellsoft.pt.basicdata.sap.config.SAPDbConfig;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.model.ficoManage.Zfmt0003;
import com.wellsoft.pt.ldx.model.ficoManage.Zfmt0004;
import com.wellsoft.pt.ldx.service.ficoManage.IFicoManageService;
import com.wellsoft.pt.ldx.util.DateUtils;
import com.wellsoft.pt.ldx.util.StringUtils;
import com.wellsoft.pt.security.core.userdetails.UserDetails;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

/**
 * 
 * Description: 资金管理控制器
 *  
 * @author HeShi
 * @date 2014-9-17
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-9-17 	HeShi		2014-9-17		Create
 * </pre>
 *
 */
@Controller
@RequestMapping("/ficoManage")
public class FicoManageController extends BaseController{
	//操作类型,业务员提交到账信息
	private static final String TYPE_SUBMIT_YW="01YW业务提交到账信息";
	//操作类型,业务员修改到账信息
	private static final String TYPE_UPDATE_YW="02YW业务修改到账信息";
	//操作类型,业务员添加到账分解信息
	private static final String TYPE_ADDSUB_YW="03YW业务新增到账分解";
	//操作类型,业务员修改到账分解信息
	private static final String TYPE_UPDSUB_YW="04YW业务修改到账分解";
	//操作类型,业务员删除到账分解信息
	private static final String TYPE_DElSUB_YW="05YW业务删除到账分解";
	//操作类型,业务员预收分解
	private static final String TYPE_SEPSUB_YW="06YW业务员拆分预收款";
	//操作类型,财务拒绝
	private static final String TYPE_REJECT_YW="11CW财务退回到账信息";
	//操作类型,财务删除到账信息
	private static final String TYPE_DELETE_CW="12CW财务删除到账信息";
	//操作类型,财务修改到账信息
	private static final String TYPE_UPDATE_CW="13CW财务修改到账信息";
	//操作类型,财务生成凭证1
	private static final String TYPE_CERT01_CW="14CW财务生成会计凭证";
	//操作类型,财务生成预收冲销凭证
	private static final String TYPE_CERT02_CW="15CW财务生成预收凭证";
	//操作类型,财务回冲凭证
	private static final String TYPE_BACK01_CW="16CW财务回冲会计凭证";
	//操作类型,财务回冲预收凭证
	private static final String TYPE_BACK02_CW="17CW财务回冲预收凭证";
	//操作类型,财务分解退回
	private static final String TYPE_SEPBAC_YW="18CW财务退回分解信息";
	//操作类型,财务回冲退回出纳
	private static final String TYPE_BAKDEL_YW="19CW财务回冲退回出纳";
	@Resource(name="sapConnectConfig")
	private SAPDbConfig sapConfig;
	@Autowired
	private IFicoManageService ficoManageService;
	
	@RequestMapping("/ficoRoute")
	public String ficoRoute(@RequestParam(value = "flowNum") String flowNum, HttpServletRequest request, Model model){
		String from = request.getParameter("from");
		String user = request.getParameter("user");
		String mail = request.getParameter("mail");
		if (StringUtils.isNotBlank(user)) {
			request.getSession().setAttribute("ficoTempUser", user);
		}
		Zfmt0003 acct = ficoManageService.findAcctByFlowNum(flowNum);
		if (null == acct) {
			String error = "根据凭证编号‘" + flowNum + "’查询不到到账信息，或者无权限查看!";
			model.addAttribute("error",error);
			return forward("/ldx/error");
		}
		model.addAttribute("flowNum",flowNum);
		if ("yw".equals(from)) {
			//邮件入口,免登录,根据用户参数判断权限
			if (StringUtils.isNotBlank(mail) && "y".equals(mail)) {
				if (StringUtils.isBlank(user) || !user.equals(acct.getZsname())) {
					return redirect("/ficoManage/acctView");
				}
			} else {
				String userId = getUserCode();
				if (StringUtils.isBlank(userId) || !userId.equals(acct.getZsname())) {
					return redirect("/ficoManage/acctView");
				}
			}
			//业务入口
			if ("P".equals(acct.getZdrs()) && "P".equals(acct.getZcirs())) {
				//状态PP跳转至信息登记界面
				return redirect("/ficoManage/acctEdit");
			} else if ("P".equals(acct.getZdrs()) && "C".equals(acct.getZcirs())) {
				//状态PC跳转至预收分解界面
				return redirect("/ficoManage/acctSeprate");
			} else {
				//其他状态跳转至查看界面
				return redirect("/ficoManage/acctView");
			}
		}else if("cw".equals(from)){
			//财务入口
			if ("F".equals(acct.getZcirs())) {
				//流转状态F跳转至生成凭证1界面
				return redirect("/ficoManage/acctCert1");
			}else if(("F".equals(acct.getZdrs())||"P".equals(acct.getZdrs())) && "C".equals(acct.getZcirs())){
				//状态FC跳转至生成凭证2界面
				return redirect("/ficoManage/acctCert2");
			}else{
				//其他状态跳转至查看界面
				return redirect("/ficoManage/acctView");
			}
		}else{
			return redirect("/ficoManage/acctView");
		}

	}
	
	/**
	 * 
	 * 跳转至到款信息查看界面
	 * 
	 * @param flowNum
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/acctView")
	public String acctView(@RequestParam(value = "flowNum") String flowNum, Model model){
		model.addAttribute("entity",findEntity(flowNum));
		model.addAttribute("recBukrs",findDailyReceiveBukrs(flowNum));
		model.addAttribute("sepList",findSepratedAccts(flowNum));
		model.addAttribute("condition",findCondition(flowNum));
		return forward("/ldx/ficoManage/acctView");
	}
	

	/**
	 * 
	 * 跳转至到款信息登记界面
	 * 
	 * @param flowNum
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/acctEdit")
	public String acctEdit(@RequestParam(value = "flowNum") String flowNum, Model model,HttpServletRequest request){
		Zfmt0003 entity = findEntity(flowNum);
		model.addAttribute("entity",entity);
		model.addAttribute("recBukrs",findDailyReceiveBukrs(flowNum));
		model.addAttribute("sepList",findSepratedAccts(flowNum));
		model.addAttribute("condition",findCondition(flowNum));
		model.addAttribute("noteTip",findNoteTip(entity,request));
		model.addAttribute("amtSum",caculateAmtSum(flowNum));
		model.addAttribute("arInfo",findArOfAccount(flowNum));
		model.addAttribute("sumDatas",findCustSumDatas(entity.getKunnr(),entity.getBukrs()));
		return forward("/ldx/ficoManage/acctEdit");
	}
	
	
	/**
	 * 
	 * 跳转至生成会计凭证界面
	 * 
	 * @param flowNum
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/acctCert1")
	public String acctCert1(@RequestParam(value = "flowNum") String flowNum, Model model,HttpServletRequest request){
		Zfmt0003 entity = findEntity(flowNum);
		model.addAttribute("entity",entity);
		model.addAttribute("recBukrs",findDailyReceiveBukrs(flowNum));
		model.addAttribute("noteTip",findNoteTip(entity,request));
		model.addAttribute("sepList",findSepratedAccts(flowNum));
		return forward("/ldx/ficoManage/acctCert1");
	}
	
	/**
	 * 跳转至到预收分解生成凭证界面
	 * @return
	 */
	@RequestMapping("/acctCert2")
	public String acctCert2(@RequestParam(value = "flowNum") String flowNum, Model model,HttpServletRequest request){
		Zfmt0003 entity = findEntity(flowNum);
		model.addAttribute("entity",entity);
		model.addAttribute("recBukrs",findDailyReceiveBukrs(flowNum));
		model.addAttribute("noteTip",findNoteTip(entity,request));
		model.addAttribute("sepList",findReceivedForCert(flowNum));
		return forward("/ldx/ficoManage/acctCert2");
	}
	
	/**
	 * 跳转至回冲界面
	 * @return
	 */
	@RequestMapping("/acctBackFlush")
	public String acctBackFlush(@RequestParam(value = "flowNum") String flowNum, Model model,HttpServletRequest request){
		Zfmt0003 entity = findEntity(flowNum);
		model.addAttribute("entity",entity);
		model.addAttribute("recBukrs",findDailyReceiveBukrs(flowNum));
		model.addAttribute("sepList",findSepratedAccts(flowNum));
		return forward("/ldx/ficoManage/acctBackFlush");
	}
	
	
	/**
	 * 
	 * 跳转至到款信息登记界面
	 * 
	 * @param flowNum
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/acctSeprate")
	public String acctSeprate(@RequestParam(value = "flowNum") String flowNum, Model model,HttpServletRequest request){
		Zfmt0003 entity = findEntity(flowNum);
		model.addAttribute("entity",entity);
		model.addAttribute("recBukrs",findDailyReceiveBukrs(flowNum));
		model.addAttribute("sepList",findSepratedAccts(flowNum));
		model.addAttribute("condition",findCondition(flowNum));
		model.addAttribute("noteTip",findNoteTip(entity,request));
		model.addAttribute("amtSum",caculateAmtSum(flowNum));
		model.addAttribute("arInfo",findArOfAccount(flowNum));
		return forward("/ldx/ficoManage/acctSeprate");
	}
	
	/**
	 * 
	 * 查询当前客户未清金额信息
	 * 
	 * @param kunnr
	 * @param bukrs
	 * @return
	 */
	private Object findCustSumDatas(String kunnr, String bukrs) {
		String ysSum = "",wqSum = "",qtSum = "";
		if(StringUtils.isBlank(kunnr)||"...".equals(kunnr))
			return new String[]{ysSum,wqSum,qtSum};
		String sql = "select 1 as type,a.waers,sum(a.zcamount-a.zwoamt) as yswq from zfmt0004 a,zfmt0003 b where a.zbelnr = b.zbelnr and a.zrbl = 'A' and a.zcirs='C' and b.kunnr='"+kunnr+"' and b.bukrs='"+bukrs+"' group by a.waers "
			+ " union all"
			+ " select 2 as type,waers,sum(case when bschl>'09' then -wrbtr else wrbtr end) as wrbtr from bsid d where umskz=' ' and kunnr='"+kunnr+"' and d.bukrs='"+bukrs+"' and d.mandt='"+sapConfig.getClient()+"' group by waers "
			+ " union all"
			+ " select 3 as type,waers,sum(case when bschl>'09' then -wrbtr else wrbtr end) as wrbtr from bsid d where umskz='3' and kunnr='"+kunnr+"' and d.bukrs='"+bukrs+"' and d.mandt='"+sapConfig.getClient()+"' group by waers order by type, waers";
		
		List<Object> list = ficoManageService.findListBySql(sql);
		if(null!=list && list.size()>0){
			for(Object object:list){
				Object[] objects = (Object[]) object;
				if("1".equals(objects[0].toString())){
					ysSum+=objects[1].toString()+":"+objects[2].toString()+" ;";
				}else if("2".equals(objects[0].toString())){
					wqSum+=objects[1].toString()+":"+objects[2].toString()+" ;";
				}else if("3".equals(objects[0].toString())){
					qtSum+=objects[1].toString()+":"+objects[2].toString()+" ;";
				}
			}
		}
		return new String[]{ysSum,wqSum,qtSum};
	}

	/**
	 * 
	 * 查询当前应收人员信息
	 * 
	 * @param flowNum
	 * @return
	 */
	private String findArOfAccount(String flowNum) {
		String ar = "";
		String sql = "select c.ename,d.usrid from zfmt0003 a "
			+ " inner join zfmt0007 b on a.bukrs = b.bukrs and a.kunnr = b.kunnr"
			+ " left join pa0001 c on b.zrname = c.pernr and not exists (select 1 from pa0001 f where f.pernr = c.pernr and f.begda>c.begda)"
			+ " left join pa0105 d on b.zrname = d.pernr and usrty='CALL' and not exists (select 1 from pa0105 e where e.usrty = d.usrty and e.pernr = d.pernr and e.begda>d.begda)"
			+ " where a.zbelnr ='"+flowNum+"'";
		List<Object> list = ficoManageService.findListBySql(sql);
		if(list!=null && list.size()>0){
			Object[] objects = (Object[])list.get(0);
			String name = StringUtils.nullToString(objects[0]);
			ar = name;
			String tel = StringUtils.nullToString(objects[1]);
			if(StringUtils.isNotBlank(tel)){
				ar += (",电话:" + tel);
			}
		}
		return ar;
	}

	/**
	 * 
	 * 初始化提示信息
	 * 
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String findNoteTip(Zfmt0003 zfmt0003,HttpServletRequest request) {
		String noteTip = "";
		String currentNote = zfmt0003.getZtext();
		//session为空,提示当前备注
		if(null==request||null==request.getSession()){
			noteTip = currentNote.trim();
			return "";
		}else{
			Map<String,String> noteToken =(Map<String,String>) request.getSession().getAttribute("ficoNoteToken");
			if(null==noteToken||noteToken.isEmpty()){
				//session为空,或未提醒过
				noteToken = new HashMap<String,String>();
				noteToken.put(zfmt0003.getZbelnr(),currentNote);
				request.getSession().setAttribute("ficoNoteToken",noteToken);
				noteTip = currentNote.trim();
			}else{
				String temp = noteToken.get(zfmt0003.getZbelnr());
				if(StringUtils.isBlank(temp)){
					//未提醒过
					noteToken.put(zfmt0003.getZbelnr(),currentNote);
					request.getSession().setAttribute("ficoNoteToken",noteToken);
					noteTip = currentNote.trim();
				}
			}
		}
		return noteTip;
	}

	/**
	 * 
	 * 查询付款条件
	 * 
	 * @param flowNum
	 * @return
	 */
	private String findCondition(String flowNum) {
		String query = "select a.zvtext from  zfmt0008 a,zfmt0003 b,KNVV c "
			+ " where a.zterm = c.zterm"
			+ " and b.kunnr = c.kunnr"
			+ " and b.bukrs = c.VKORG"
			+ " and a.mandt = "+sapConfig.getClient()
			+ " and b.zbelnr = '"+flowNum+"'";
		List<Object> list = ficoManageService.findListBySql(query);
		if(null!=list && list.size()>0){
			return StringUtils.nullToString(list.get(0));
		}else{
			return "";
		}
	}

	/**
	 * 
	 * 查询到款分解列表
	 * 
	 * @param flowNum
	 * @return
	 */
	private List<Zfmt0004> findSepratedAccts(String flowNum) {
		return ficoManageService.findSepAcctsByFlowNum(flowNum);
	}
	
	private List<Zfmt0004> findReceivedForCert(String flowNum) {
		List<Zfmt0004> sepList = ficoManageService.findReceivedForCert(flowNum);
		for(Zfmt0004 sep:sepList){
			sep.setWqamt("0");
			if(StringUtils.isNotBlank(sep.getBstkd())
					&& StringUtils.isNotBlank(sep.getVbeln())){
				StringBuffer query = new StringBuffer("select bstnk_vf,vgbel,sum(wrbtr) as con,waers from(")
					.append(" select d.zuonr,k.bstnk_vf,p.vgbel,case when bschl>'09' then -wrbtr else wrbtr end as wrbtr,waers,case when umskz='3' then 1 else 0 end as type from bsid d ")
					.append(" left join vbrk k on k.vbeln = d.zuonr and k.mandt = d.mandt ")
					.append(" left join (select distinct vgbel,vbeln from vbrp where mandt=")
					.append(sapConfig.getClient())
					.append(" and vgbel='")
					.append(sep.getVbeln())
					.append("') p on p.vbeln = d.zuonr ")
					.append(" where umskz=' '")
					.append(" and p.vgbel='")
					.append(StringUtils.addLeftZero(sep.getVbeln(),10))
					.append("' ")
					.append(" and trim(k.bstnk_vf) = '")
					.append(sep.getBstkd())
					.append("' ")
					.append(" and k.mandt =")
					.append(sapConfig.getClient())
					.append(" and d.mandt =")
					.append(sapConfig.getClient())
					.append(" )group by bstnk_vf,vgbel,waers,type");
				List<Object> resList = ficoManageService.findListBySql(query.toString());
				if(null!=resList && resList.size()>0){
					Object[] obj = (Object[])resList.get(0);
					sep.setWqamt(StringUtils.nullToString(obj[2]));
				}
			}
		}
		return sepList;
	}

	/**
	 * 
	 * 根据流水号查询日记账公司代码
	 * 
	 * @param flowNum
	 * @return
	 */
	private String findDailyReceiveBukrs(String flowNum) {
		String sql = "select bukrs from zfmt0001 where zbelnr ='"+flowNum+"' union all select bukrs from zfmt0003 where zbelnr ='"+flowNum+"'";
		return StringUtils.nullToString(ficoManageService.findListBySql(sql).get(0));
	}

	/**
	 * 
	 * 查找到账信息
	 * 
	 * @param flowNum
	 * @return
	 */
	public Zfmt0003 findEntity(String flowNum){
		Zfmt0003 entity = ficoManageService.findAcctByFlowNum(flowNum);
		if (StringUtils.isNotBlank(entity.getZsname())) {
			entity.setZsname(ficoManageService.findUserNameByCode(entity.getZsname()));
		}
		return entity;
	}
	
	/**
	 * 
	 * 业务保存到账信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("saveAcct")
	public void saveAcct(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
		String[] retObj = new String[2];// 返回信息
		//获取参数
		String zbelnr = request.getParameter("zbelnr");
		String value = request.getParameter("value");
		String type = request.getParameter("type");
		if (("budat".equals(type) || "bldat".equals(type)) && StringUtils.isNotBlank(value)) {
			value = value.replaceAll("-", "");
		}
		try {
			value = StringUtils.isBlank(value) ? " " : value.trim().replaceAll("'", "‘").replaceAll("<", "&lt;")
					.replaceAll(">", "&gt;");
			String sql = "update zfmt0003 set " + type + "='" + value + "' " + " where zbelnr ='" + zbelnr + "' and mandt="+sapConfig.getClient();
			System.out.println(sql);
			ficoManageService.execSql(sql);
			ficoManageService.saveLog(null,TYPE_UPDATE_YW, "sql:" + sql);
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
	 * 财务人员修改到账信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("saveAcctCw")
	public void saveAcctCw(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
		String[] retObj = new String[2];// 返回信息
		//获取参数
		String zbelnr = request.getParameter("zbelnr");
		String value = request.getParameter("value");
		String type = request.getParameter("type");
		if (("budat".equals(type) || "bldat".equals(type)) && StringUtils.isNotBlank(value)) {
			value = value.replaceAll("-", "");
		}
		try {
			value = StringUtils.isBlank(value) ? " " : value.trim().replaceAll("'", "‘").replaceAll("<", "&lt;")
					.replaceAll(">", "&gt;");
			String sql = "update zfmt0003 set " + type + "='" + value + "' " + " where zbelnr ='" + zbelnr + "' and mandt="+sapConfig.getClient();
			System.out.println(sql);
			ficoManageService.execSql(sql);
			ficoManageService.saveLog(null,TYPE_UPDATE_CW, "sql:" + sql);
			retObj[0] = "success";
		} catch (Exception e) {
			retObj[0] = "fail";
			retObj[1] = e.getMessage();
		}
		JSONArray ja = JSONArray.fromObject(retObj);
		response.getOutputStream().write(ja.toString().getBytes("UTF-8"));
	}
	
	/**
	 * 计算当前金额合计
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping("getSum")
	public void getSum(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
		String flowNum = request.getParameter("flowNum");
		String message = caculateAmtSum(flowNum);
		response.getOutputStream().write(message.getBytes("UTF-8"));
	}
	
	/**
	 * 提交时校验到账金额维护是否正确
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping("checkBalance")
	public void checkBalance(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		String flowNum = request.getParameter("flowNum");
		String[] retObj = new String[2];// 返回信息
		String userid = getUserCode();
		boolean needCheck = true;
		//判断当前用户是否为此客户的应收人员
		if(StringUtils.isNotBlank(userid)){
			String findAR = "select 1 from zfmt0003 ta,zfmt0007 tb"
				+ " where ta.mandt = tb.mandt "
				+ " and ta.bukrs = tb.bukrs "
				+ " and ta.kunnr = tb.kunnr "
				+ " and ta.zbelnr ='"+flowNum+"'"
				+ " and tb.zrname ='"+userid+"'"
				+ " and ta.mandt="+sapConfig.getClient();
			List<Object> temp = ficoManageService.findListBySql(findAR);
			if(null!=temp && temp.size()>0){
				needCheck = false;
			}
		}
		try{
			ficoManageService.checkAcctBalance(flowNum);
			retObj[0] = "success";
		}catch (Exception e) {
			retObj[0] = needCheck ? "fail":"confirm";
			retObj[1] = e.getMessage();
		}
		JSONArray ja = JSONArray.fromObject(retObj);
		response.getOutputStream().write(ja.toString().getBytes("UTF-8"));
	}
	
	/**
	 * 
	 * 查询当前到款登记金额总和
	 * 
	 * @param flowNum
	 * @return
	 */
	private String caculateAmtSum(String flowNum) {
		String message = "0.00";
		String sql = "select sum(to_number(to_char(nvl(nvl(zcamount,0)*nvl(kursf,1)/nvl(zpeinh,1)-zhc,0),'FM9999999999.99'))) from zfmt0004 where zbelnr='"+flowNum+"'";
		List<Object> list = ficoManageService.findListBySql(sql);
		if(null!=list && list.size()>0){
			message = StringUtils.nullToString(list.get(0));
		}
		return message;
	}
	
	/**
	 * 
	 * 获取当前登录用户工号
	 * 
	 * @return
	 */
	private String getUserCode() {
		UserDetails userDetail = SpringSecurityUtils.getCurrentUser();
		return userDetail.getEmployeeNumber(); 
	}
	
	/**
	 * 提交当前到账信息修改
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("submitAcct")
	public void submitAcct(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		String flowNum = request.getParameter("flowNum");
		String[] retObj = new String[2];// 返回信息
		try{
			//ficoManageService.checkAcctBalance(flowNum);
			ficoManageService.submitAcctYw(flowNum);
			ficoManageService.saveLog(null,TYPE_SUBMIT_YW,"流水号:"+flowNum);
			retObj[0] = "success";
		}catch (Exception e) {
			retObj[0] = "fail";
			retObj[1] = e.getMessage();
		}
		JSONArray ja = JSONArray.fromObject(retObj);
		response.getOutputStream().write(ja.toString().getBytes("UTF-8"));
	}
	
	/**
	 * 根据合同号及外向交货单号异步查询应收款信息
	 * @return
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * 
	 * --根据合同号及外向交货单查询应收款
	 * with rec as(
	 * select zuonr,bstnk_vf,vgbel,0 as ys,sum(wrbtr) as con,waers,type from(
	 * select d.zuonr,k.bstnk_vf,p.vgbel,0,case when bschl>'09' then -wrbtr else wrbtr end as wrbtr,waers,case when umskz='3' then 1 else 0 end as type from bsid d 
	 * left join vbrk k on k.vbeln = d.zuonr 
	 * left join (select distinct vgbel,vbeln from vbrp where mandt=500 and vgbel='0080000030') p on p.vbeln = d.zuonr 
	 * where umskz!='A'
	 * and p.vgbel='0080000030'
	 * and k.bstnk_vf = '20120308-05'
	 * and k.mandt = 500
	 * and d.mandt = 500
	 * and d.kunnr = '0100260000'
	 * )
	 * group by zuonr,bstnk_vf,vgbel,0,waers,type
	 * )
	 * select rec.zuonr,rec.bstnk_vf,rec.vgbel,nvl(ae.zpamount-ae.zdwoamt,0) as wq,rec.ys,rec.con,rec.type,rec.waers from rec left join zfmt0006 ae on rec.bstnk_vf = ae.bstkd 
	 * 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("findReceivableAccts")
	public void findReceivableAccts(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException,
			IOException {
		// 合同号
		String xblnr = request.getParameter("xblnr");
		// 外向交货单号
		String xref1 = request.getParameter("xref1");
		// 客户编号
		String kunnr = request.getParameter("kunnr");
		// 公司代码
		String bukrs = request.getParameter("bukrs");
		// 备注
		String sgtxt = request.getParameter("sgtxt");
		if(StringUtils.isBlank(xblnr)&&StringUtils.isBlank(xref1)&&StringUtils.isBlank(sgtxt)){
			JSONArray ja = JSONArray.fromObject(new ArrayList());
			response.getOutputStream().write(ja.toString().getBytes("UTF-8"));
		}
		StringBuffer sqls = new StringBuffer();
		//备注为空,查询应收信息
		//字段顺序:发票号\合同\外向交货单\预收未清\应收金额\未清金额\类型\币种\备注
		if(StringUtils.isBlank(sgtxt)){
			sqls.append(" with yswqtab as (")
			.append(" select a.bstkd,a.vbeln,a.waers,b.kunnr,sum(a.zcamount-a.zwoamt) as yswq from zfmt0004 a,zfmt0003 b  where zrbl = 'A' and a.zbelnr=b.zbelnr group by a.bstkd,a.vbeln,a.waers,b.kunnr)")
			.append(" , wqjetab as (")
			.append(" select zuonr,waers,type,bstnk_vf,vgbel,kunnr,sum(wrbtr) as wqje from (")
			.append(" select d.zuonr,case when bschl>'09' then -wrbtr else wrbtr end as wrbtr,waers,k.bstnk_vf,p.vgbel,case when umskz='3' then 1 else 0 end as type,kunnr from bsid d")
			.append(" left join vbrk k on  k.vbeln = d.zuonr and k.mandt = d.mandt ")
			.append(" left join (select distinct vgbel,vbeln from vbrp) p on p.vbeln = d.zuonr ")
			.append(" where umskz=' '")
			.append(" and d.mandt =").append(sapConfig.getClient())
			.append(" and d.bukrs ='").append(bukrs).append("'")
			.append(" and kunnr='").append(kunnr).append("'");
			if(StringUtils.isNotBlank(xblnr)){
				sqls.append(" and k.bstnk_vf like '%").append(xblnr.trim()).append("%'");
			}
			if(StringUtils.isNotBlank(xref1)){
				sqls.append(" and p.vgbel='").append(StringUtils.addLeftZero(xref1.trim(),10)).append("'");
			}
			sqls.append(" )group by zuonr,waers,type,bstnk_vf,vgbel,kunnr)")
			.append(" select ta.zuonr,ta.bstnk_vf,ta.vgbel,nvl(tb.yswq,0),0,nvl(ta.wqje,0),ta.type,ta.waers,' ' from wqjetab ta")
			.append(" left join yswqtab tb on ta.bstnk_vf = tb.bstkd and ta.vgbel = tb.vbeln and ta.waers = tb.waers and ta.kunnr = tb.kunnr")
			.append(" where ta.kunnr = '").append(kunnr).append("'");
			if(StringUtils.isNotBlank(xblnr)){
				sqls.append(" and ta.bstnk_vf like '%").append(xblnr.trim()).append("%'");
			}
			if(StringUtils.isNotBlank(xref1)){
				sqls.append(" and ta.vgbel='").append(StringUtils.addLeftZero(xref1.trim(),10)).append("'");
			}
		}else{
			//备注为非空,查询其他应收款
			sqls.append(" select belnr,' ' as bstnk_vf,' ' as vgbel,0 as yswq,0 as tempamt,sum(case when bschl>'09' then -wrbtr else wrbtr end) as wrbtr,1 as type,waers,sgtxt from bsid d ")
				.append(" where umskz='3'")
				.append(" and mandt=").append(sapConfig.getClient())
				.append(" and d.bukrs ='").append(bukrs).append("'")
				.append(" and kunnr='").append(kunnr).append("'");
			if(StringUtils.isNotBlank(sgtxt)){
				sqls.append(" and sgtxt like '%").append(sgtxt.trim()).append("%'");
			}
			sqls.append(" group by belnr,sgtxt,waers order by belnr,sgtxt,waers");
		}
		List list = ficoManageService.findListBySql(sqls.toString());
		JSONArray ja = JSONArray.fromObject(list);
		response.getOutputStream().write(ja.toString().getBytes("UTF-8"));
	}
	
	/**
	 * 检查外向交货单是否已经清账
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("checkZounrClear")
	public void checkZounrClear(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		// 合同号
		String xblnr = request.getParameter("xblnr");
		// 外向交货单号
		String xref1 = request.getParameter("xref1");
		// 客户编号
		String kunnr = request.getParameter("kunnr");
		// 公司代码
		String bukrs = request.getParameter("bukrs");
		StringBuffer query = new StringBuffer("select 1 from bsad d");
		query.append(" inner join vbrk k on k.vbeln = d.zuonr and k.mandt = d.mandt")
			.append(" inner join (select distinct vgbel,vbeln from vbrp) p on p.vbeln = d.zuonr ")
			.append(" where umskz=' '")
			.append(" and k.bstnk_vf like '%").append(xblnr.trim()).append("%'")
			.append(" and d.bukrs ='").append(bukrs).append("'")
			.append(" and p.vgbel='").append(StringUtils.addLeftZero(xref1.trim(),10)).append("'");
		List list = ficoManageService.findListBySql(query.toString());
		String retString="";
		if(null!=list && list.size()>0){
			retString = "当前外向交货单已清账!";
		}else{
			StringBuffer queryKunnr = new StringBuffer("select d.kunnr,d.bukrs from bsid d");
			queryKunnr.append(" inner join (select distinct vgbel,vbeln from vbrp) p on p.vbeln = d.zuonr ")
				.append(" where umskz=' '")
				.append(" and p.vgbel='").append(StringUtils.addLeftZero(xref1.trim(),10)).append("'");
			List<Object> temp = ficoManageService.findListBySql(queryKunnr.toString());
			if(null!=temp && temp.size()>0){
				Object[] object = (Object[])temp.get(0);
				if(!kunnr.equals(StringUtils.nullToString(object[0]))){
					retString = "当前外向交货单对应客户编号为"+StringUtils.nullToString(object[0])+",与当前客户不符!";
				}else if(!bukrs.equals(StringUtils.nullToString(object[1]))){
					retString = "当前外向交货单对应公司代码为"+StringUtils.nullToString(object[1])+",与当前公司代码不符!";
				}else{
					retString = "当前外向交货单已开具发票,请确认合同号是否录入正确!";
				}
			}else{
				retString = "当前外向交货单未开具发票,请与财务联系!";
			}
		}
		response.getOutputStream().write(retString.getBytes("UTF-8"));
	}
	
	/**
	 * 删除预收分解信息
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping("deleteAcctSeprate")
	public void deleteAcctSeprate(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException,
			IOException {
		String message = "success";
		Zfmt0004 param = getParams(request);
		try {
			ficoManageService.checkStatusBeforEdit(param.getZbelnr(),param.getZposnr());
			ficoManageService.deleteZfmt0004(param);
			ficoManageService.saveLog(null,TYPE_DElSUB_YW,"zbelnr:"+param.getZbelnr()+",posnr:"+param.getZposnr());
		} catch (Exception e) {
			message = e.getMessage() + ",请联系AR:" + findArOfAccount(param.getZbelnr());
		}
		response.getOutputStream().write(message.getBytes("UTF-8"));
	}
	
	/**
	 * 保存或更新预收分解信息
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping("saveAcctSeprate")
	public void saveAcctSeprate(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException,
			IOException {
		String[] retObj = new String[2];// 返回信息
		//获取参数
		Zfmt0004 params = getParams(request);
		try {
			if (params.getZposnr().indexOf("tempAcct")>-1) {
				//新增
				params.setZdrs("P");//预收状态默认P-未分解
				params.setZcirs("P");//流转状态默认P-维护
				//预收，
				if("A".equals(params.getZrbl())){
					String today = DateUtils.formatDate(new Date(),"yyyyMMdd");
					if(StringUtils.isNotBlank(params.getVbeln())){
						throw new Exception("预收款不能录入外向交货单!");
					}
					//发票号保存为当前日期
					params.setZuonr(today);
					//特别总帐标识默认为A
					params.setUmskz("A");
					//冲销预收过账日期默认为今天
					params.setZwodat(today);
				}
				//其他应收款,特别总帐标识默认为3
				if("C".equals(params.getZrbl())){
					params.setUmskz("3");
				}
				//样品款-产品组默认为20-LED
				if("D".equals(params.getZrbl())){
					params.setZuonr(DateUtils.formatDate(new Date(),"yyyyMMdd"));
					if(StringUtils.isBlank(params.getSpart())){
						params.setSpart("20");
					}
				}
				if("B".equals(params.getZrbl())){
					//应收款校验保存金额不大于BSID余额
					checkReceiveBalance(params.getVbeln(),params.getBstkd(),params.getZcamount());
				}
				retObj[1] = ficoManageService.saveZfmt0004(params);
				ficoManageService.saveLog(null,TYPE_ADDSUB_YW,"zbelnr:"+params.getZbelnr()+",posnr:"+retObj[1]+",zrbl:"+params.getZrbl()+",zcamount:"+params.getZcamount()+",zhc:"+params.getZhc());
			} else {
				//预收，
				if("A".equals(params.getZrbl())){
					if(StringUtils.isNotBlank(params.getVbeln())){
						throw new Exception("预收款不能录入外向交货单!");
					}
				}
				//修改
				if("B".equals(params.getZrbl())){
					//应收款校验保存金额不大于BSID余额
					checkReceiveBalance(params.getVbeln(),params.getBstkd(),params.getZcamount());
				}
				ficoManageService.checkStatusBeforEdit(params.getZbelnr(),params.getZposnr());
				ficoManageService.updateZfmt0004(params);
				retObj[1] = params.getZposnr();
				ficoManageService.saveLog(null,TYPE_UPDSUB_YW,"zbelnr:"+params.getZbelnr()+",posnr:"+retObj[1]+",zrbl:"+params.getZrbl()+",zcamount:"+params.getZcamount()+",zhc:"+params.getZhc());
			}
			ficoManageService.updateParentAcct(params.getZbelnr(),retObj[1]);
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
	 * 校验应收款金额
	 * 
	 * @param vbeln
	 * @param bstkd
	 * @param amt
	 * @throws LdxPortalException
	 */
	private void checkReceiveBalance(String vbeln,String bstkd,String amt) throws Exception{
		String sql = "select nvl(sum(wrbtr),0) from ("
			+ " select case when bschl>'09' then -wrbtr else wrbtr end as wrbtr from bsid d"
			+ " inner join vbrk k on  k.vbeln = d.zuonr and k.mandt = d.mandt"
			+ " inner join (select distinct vgbel,vbeln from vbrp) p on p.vbeln = d.zuonr "
			+ " where umskz =' '"
			+ " and d.mandt =" + sapConfig.getClient()
			+ " and p.vgbel ='"+StringUtils.addLeftZero(vbeln.trim(),10)+"'";
		if(StringUtils.isNotBlank(bstkd))
			sql	+=" and trim(k.bstnk_vf) ='"+bstkd+"'";
			
		sql	+= ")";
		List<Object> list = ficoManageService.findListBySql(sql);
		if(list==null || list.size()==0){
			throw new Exception("当前外向交货单没有未清款项!");
		}else{
			Object object = list.get(0);
			Double temp = Double.parseDouble(StringUtils.nullToString(object));
			Double curr = Double.parseDouble(StringUtils.nullToString(amt));
			if(temp.compareTo(curr)<0){
				throw new Exception("收款金额不能大于当前外向交货单未清金额("+temp+")!");
			}
		}
	}
	
	/**
	 * 登记未开票信息
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping("saveZounrCheckInfo")
	public void saveZounrCheckInfo(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException,
			IOException {
		//流水号参数
		String zbelnr = request.getParameter("zbelnr");
		//外向交货单,服务层进行左补零操作
		String vbeln = request.getParameter("vbeln");
		//删除通知时间标识
		String del = request.getParameter("del");
		boolean flag = StringUtils.isNotBlank(del)?true:false;
		String result = "操作成功!";
		try {
			ficoManageService.saveZounrNotExistInfo(zbelnr,vbeln,flag);
		} catch (Exception e) {
			result = "操作失败!"+e.getMessage();
		}
		response.getOutputStream().write(result.getBytes("UTF-8"));
	}
	
	/**
	 * 获取AE预计收款金额
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping("findAcctDefalt")
	public void findAcctDefalt(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
		String[] ret = new String[]{"0","00000000"};// 返回信息
		// 合同号
		String xblnr = request.getParameter("xblnr");
		if (StringUtils.isNotBlank(xblnr)) {
			String queruy = "select zpamount-zdwoamt from zfmt0006 where mandt=" + sapConfig.getClient()
			+ " and zover='否' and bstkd='"
			+ (StringUtils.isBlank(xblnr) ? " " : xblnr.trim())
			+ "'";
			List<Object> list = ficoManageService.findListBySql(queruy);
			if (list != null && list.size() > 0) {
				ret[0] = StringUtils.nullToString(list.get(0));
			}
		}
		ret[1]=DateUtils.formatDate(new Date(),"yyyyMMdd");
		JSONArray ja = JSONArray.fromObject(ret);
		response.getOutputStream().write(ja.toString().getBytes("UTF-8"));
	}
	
	/**
	 * 下载上传模板
	 * @throws Exception
	 */
	@RequestMapping("downLoadTemplate")
	public void downLoadTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		OutputStream os = response.getOutputStream();
		String fileName = "acctUploadTemplate.xls";
		response.reset();
		response.setHeader("Content-disposition","attachment; filename=" + fileName);
		response.setContentType("application/msexcel");
		InputStream in = this.getClass().getResourceAsStream("receiveAcct.xls");
		HSSFWorkbook workbook = new HSSFWorkbook(in);
		workbook.write(os);
		response.flushBuffer();
		os.close();
	}
	
	/**
	 * 下载上传错误提示
	 * @throws Exception
	 */
	@RequestMapping("downLoadErrorTip")
	public void downLoadErrorTip(HttpServletRequest request, HttpServletResponse response) throws Exception {
		OutputStream os = response.getOutputStream();
		response.reset();
		String fname = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_uploadAcctError.xls";
		response.setHeader("Content-disposition","attachment; filename=" + fname);
		response.setContentType("application/msexcel");
		InputStream in = this.getClass().getResourceAsStream("receiveAcct.xls");
		HSSFWorkbook workbook = new HSSFWorkbook(in);
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFRow row = null;
		HSSFCell cell = null;
		int columns = sheet.getRow((short) 0).getPhysicalNumberOfCells();
		// 创建样式数组
		HSSFCellStyle styleArray[] = new HSSFCellStyle[columns];
		// 一次性创建所有列的样式放在数组里
		for (int s = 0; s < columns; s++) {
			// 得到数组实例
			styleArray[s] = workbook.createCellStyle();
		}
		String[][] rowarr = (String[][]) request.getSession().getAttribute("uploadAcctError");
		for (int i = 0; i < rowarr.length; i++) {
			int j = 0;
			row = sheet.createRow(1 + i);
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(rowarr[i][j]);
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(rowarr[i][j]);
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(rowarr[i][j]);
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(rowarr[i][j]);
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(rowarr[i][j]);
		}
		workbook.write(os);
		response.flushBuffer();
		os.close();
	}
	
	/**
	 * 上传EXCEL文件
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public void upload(@RequestParam(value = "uploadFile", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uploadError = "";
		//流水号
		String zbelnr = request.getParameter("flowNum");
		//客户简称
		String sortl = request.getParameter("sortl");
		if (file == null) {
			response.getWriter().write("请选择EXCEL文件!");
			response.getWriter().flush();
			response.getWriter().close();
			return;
		}
		InputStream is = file.getInputStream();
		HSSFWorkbook hwk = new HSSFWorkbook(is);
		HSSFSheet sh = hwk.getSheetAt(0);
	    HSSFRow firstRow = sh.getRow(0);
	    //1校验标题行
	    String[] headStr = new String[]{ "ECIS号", "外向交货单", "手续费", "应收金额"};
	    for (int i = 0; i < headStr.length; i++) {
			HSSFCell cell = firstRow.getCell(i);
			String head = getCellValue(cell);
			if (!headStr[i].equals(head)) {
				response.getWriter().write("上传文件格式错误!");
				response.getWriter().flush();
				response.getWriter().close();
				return;
			}
		}
	    //2校验上传数据格式
	    int rows = sh.getLastRowNum() + 1 - sh.getFirstRowNum();
	    String[][] rowarr = new String[rows - 1][5];
		int rowno = 0;
		try{
			for (rowno = 0; rowno < rows - 1; rowno++) {
				HSSFRow row = sh.getRow(rowno + 1);
				for (int j = 0; j < 4; j++) {
					HSSFCell cell = row.getCell(j);
					String col = getCellValue(cell);
					rowarr[rowno][j] = col;
				}
				if(StringUtils.isBlank(rowarr[rowno][0])
						&&StringUtils.isBlank(rowarr[rowno][1])){
					//throw new Exception("ECIS及外向交货单同时为空");
					break;//遇到空行时跳出循环
				}
				Double.parseDouble(rowarr[rowno][2]);//手续费
				Double.parseDouble(rowarr[rowno][3]);//应收金额
			}
		}catch (NumberFormatException ne){
			response.getWriter().write("上传失败!上传文件第"+(rowno+1)+"行金额格式错误!");
			response.getWriter().flush();
			response.getWriter().close();
			return;
		}catch (Exception e){
			response.getWriter().write("上传失败!上传文件第"+(rowno+1)+"行ECIS及外向交货单不能同时为空!");
			response.getWriter().flush();
			response.getWriter().close();
			return;
		}
		//3逐行生成应收款数据
		String kunnr = "";
		String bukrs = "";
		if(StringUtils.isBlank(kunnr)||StringUtils.isBlank(bukrs)){
			String sql =  "select bukrs,kunnr from zfmt0003 where zbelnr='"+zbelnr+"' and mandt="+sapConfig.getClient();
			Object[] object = (Object[]) ficoManageService.findListBySql(sql).get(0);
			bukrs = (String) object[0];
			kunnr = (String) object[1];
		}
		for(int i=0;i<rows - 1;i++){
			if(StringUtils.isBlank(rowarr[i][0])
					&&StringUtils.isBlank(rowarr[i][1])){
				break;//遇到空行时跳出循环
			}
			rowarr[i][4] = ficoManageService.uploadSingleReceive(sortl,zbelnr,rowarr[i][0],rowarr[i][1],Double.valueOf(rowarr[i][2]),Double.valueOf(rowarr[i][3]),kunnr,bukrs);
			if(!"success".equals(rowarr[i][4])){
				uploadError = "error";
			}
			//System.out.println("upload"+StringUtils.addLeftZero(i+"",2)+":"+rowarr[i][0]+","+rowarr[i][1]+","+rowarr[i][2]+","+rowarr[i][3]+"========"+rowarr[i][4]);
		}
		request.getSession().setAttribute("uploadAcctError",rowarr);
		if(StringUtils.isNotBlank(uploadError)){
			request.getSession().setAttribute("uploadError"+zbelnr,"文件中部分记录上传失败,请下载错误提示!");
			response.getWriter().write("文件中部分记录上传失败,请下载错误提示!");
			response.getWriter().flush();
			response.getWriter().close();
		}else{
			request.getSession().removeAttribute("uploadError"+zbelnr);
			response.getWriter().write("success");
			response.getWriter().flush();
			response.getWriter().close();
		}
	}
	
	
	/**
	 * 批量删除PP状态分解信息
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping("deleteAllPPStatus")
	public void deleteAllPPStatus(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		//流水号参数
		String zbelnr = request.getParameter("zbelnr");
		String result = "操作成功!";
		try {
			if(StringUtils.isNotBlank(zbelnr)){
				String sql = "delete from zfmt0004 where zbelnr='"+zbelnr.trim()+"' and zdrs='P' and zcirs='P'";
				ficoManageService.execSql(sql);
			}
		} catch (Exception e) {
			result = "操作失败!"+e.getMessage();
		}
		response.getOutputStream().write(result.getBytes("UTF-8"));
	}
	
	
	private String getCellValue(HSSFCell cell) {
		String value = "";
		if (null != cell) {
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_STRING:
				value = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					if (date != null) {
						value = new SimpleDateFormat("yyyy-MM-dd").format(date);
					} else {
						value = "";
					}
				} else {
					value = new DecimalFormat("0.00").format(cell.getNumericCellValue());
				}
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				break;
			case HSSFCell.CELL_TYPE_ERROR:
				value = "";
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				value = (cell.getBooleanCellValue() == true ? "Y" : "N");
				break;
			default:
				value = "";
			}
		}
		return value;
	}

	/**
	 * 
	 * 获取请求参数
	 * 
	 * @param request
	 * @return
	 */
	private Zfmt0004 getParams(HttpServletRequest request) {
		Zfmt0004 param = new Zfmt0004();
		param.setMandt(sapConfig.getClient());//客户端
		param.setBelnr(request.getParameter("belnr"));//凭证号
		param.setBstkd(request.getParameter("bstkd"));//合同号
		param.setKursf(request.getParameter("kursf"));//记账汇率
		param.setRstgr(request.getParameter("rstgr"));//预收冲销凭证号
		param.setUmskz(request.getParameter("umskz"));//特殊总帐
		param.setVbeln(request.getParameter("vbeln"));//外向交货单号
		param.setWaers(request.getParameter("waers"));//合同币种
		param.setZanote(request.getParameter("zanote"));//备注
		param.setZbamt(request.getParameter("zbamt"));//合同金额
		param.setZbelnr(request.getParameter("zbelnr"));//凭证编号（流水号）
		param.setZcamount(request.getParameter("zcamount"));//收款金额
		param.setZcirs(request.getParameter("zcirs"));//流转状态
		param.setZdrs(request.getParameter("zdrs"));//预收状态
		param.setZhc(request.getParameter("zhc"));//手续费
		param.setZpodat(request.getParameter("zpodat"));//预计出口日期
		param.setZposnr(request.getParameter("zposnr"));//行项目
		param.setZrbl(request.getParameter("zrbl"));//业务类型
		param.setZsmc(request.getParameter("zsmc"));//样品款类型
		param.setZtnum(request.getParameter("ztnum"));//快递单号
		param.setZwoamt(request.getParameter("zwoamt"));//冲销金额
		param.setZuonr(request.getParameter("zuonr"));//发票号
		param.setSpart(request.getParameter("spart"));//产品组
		param.setZstblg(request.getParameter("zstblg"));//红冲凭证
		param.setZpStblg(request.getParameter("zpStblg"));//红冲预收凭证
		param.setZwodat(request.getParameter("zwodat"));//冲销预收过账日期
		param.setZposnrSup(request.getParameter("zposnrsup"));//父信息行项
		param.setZpeinh(request.getParameter("zpeinh"));//单位换算系数
		param.setBukrs(request.getParameter("bukrs"));//公司代码
		return param;
	}
	
	
	/**
	 * 根据流水号及行项，克隆一条数据
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("cloneAcctSerprate")
	public void cloneAcctSerprate(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
		String[] retObj = new String[2];// 返回信息
		String zbelnr = request.getParameter("zbelnr");
		String zposnr = request.getParameter("zposnr");
		Zfmt0004 object = ficoManageService.findSepAcctByZbelnrAndZposnr(zbelnr,zposnr);
		object.setZposnrSup(object.getZposnr());
		object.setZposnr(null);
		object.setBstkd("");
		object.setVbeln("");
		object.setZdrs("P");
		object.setZcirs("C");
		object.setZcamount("0");
		object.setZwoamt("0");
		object.setZhc("0");
		try {
			retObj[1] = ficoManageService.saveZfmt0004(object);
			ficoManageService.saveLog(null,TYPE_SEPSUB_YW,"zbelnr:"+zbelnr+",parent_posnr:"+zposnr);
			retObj[0] = "success";
		} catch (Exception e) {
			retObj[0] = "fail";
			retObj[1] = e.getMessage();
		}
		JSONArray ja = JSONArray.fromObject(retObj);
		response.getOutputStream().write(ja.toString().getBytes("UTF-8"));
	}
	
	/**
	 * 保存或更新预收分解信息
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping("saveAcctSepSubmit")
	public void saveAcctSepSubmit(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException,
			IOException {
		String[] retObj = new String[2];// 返回信息
		//获取参数
		Zfmt0004 params = getParams(request);
		try {
			try{
				Double.parseDouble(params.getZcamount());
			}catch (Exception e) {
				throw new Exception("金额格式错误");
			}
			if (params.getZposnr().indexOf("tempAcct")>-1) {
				//新增
				params.setZdrs("P");//预收状态默认P-未分解
				params.setZcirs("P");//流转状态默认P-维护
				//预收，
				if("A".equals(params.getZrbl())){
					String today = DateUtils.formatDate(new Date(),"yyyyMMdd");
					//发票号保存为当前日期
					params.setZuonr(today);
					//特别总帐标识默认为A
					params.setUmskz("A");
					//冲销预收过账日期默认为今天
					params.setZwodat(today);
				}
				//其他应收款,特别总帐标识默认为3
				if("C".equals(params.getZrbl())){
					params.setUmskz("3");
				}
				//样品款-产品组默认为20-LED
				if("D".equals(params.getZrbl())){
					params.setZuonr(DateUtils.formatDate(new Date(),"yyyyMMdd"));
					if(StringUtils.isBlank(params.getSpart())){
						params.setSpart("20");
					}
				}
				if("B".equals(params.getZrbl())){
					//应收款校验保存金额不大于BSID余额
					checkReceiveBalance(params.getVbeln(),params.getBstkd(),params.getZcamount());
				}
				retObj[1] = ficoManageService.saveZfmt0004(params);
				ficoManageService.saveLog(null,TYPE_ADDSUB_YW,"zbelnr:"+params.getZbelnr()+",posnr:"+retObj[1]+",zrbl:"+params.getZrbl()+",zcamount:"+params.getZcamount()+",zhc:"+params.getZhc());
			} else {
				//修改
				ficoManageService.checkStatusBeforEdit(params.getZbelnr(),params.getZposnr());
				if("B".equals(params.getZrbl())){
					//应收款校验保存金额不大于BSID余额
					checkReceiveBalance(params.getVbeln(),params.getBstkd(),params.getZcamount());
				}
				ficoManageService.updateZfmt0004(params);
				retObj[1] = params.getZposnr();
				ficoManageService.saveLog(null,TYPE_UPDSUB_YW,"zbelnr:"+params.getZbelnr()+",posnr:"+retObj[1]+",zrbl:"+params.getZrbl()+",zcamount:"+params.getZcamount()+",zhc:"+params.getZhc());
			}
			ficoManageService.updateParentAcct(params.getZbelnr(),retObj[1]);
			ficoManageService.checkParentAcctBalance(params.getZbelnr(),retObj[1]);
			ficoManageService.submitAcctSep(params.getZbelnr());
			ficoManageService.saveLog(null,TYPE_SUBMIT_YW,"保存预收分解后自动提交,zbelnr:"+params.getZbelnr());
			retObj[0] = "success";
		} catch (Exception e) {
			retObj[0] = "fail";
			retObj[1] = e.getMessage() + ",请联系AR:" + findArOfAccount(params.getZbelnr());
		}
		JSONArray ja = JSONArray.fromObject(retObj);
		response.getOutputStream().write(ja.toString().getBytes("UTF-8"));
	}
	
	@RequestMapping("/belnrSearch")
	public String belnrSearch(HttpServletRequest request, Model model){
		String belnr = request.getParameter("belnr");
		String bukrs = request.getParameter("bukrs");
		String gjahr = request.getParameter("gjahr");
		Object[] res = ficoManageService.getBelnrDetail(belnr,bukrs,gjahr);
		model.addAttribute("belnr",belnr);
		model.addAttribute("bukrs",bukrs);
		model.addAttribute("gjahr",gjahr);
		model.addAttribute("head",res[1]);
		model.addAttribute("item",res[2]);
		return forward("/ldx/ficoManage/belnrSearch");
	}
	
	
	/**
	 * 修改客户编号
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping("/updateKunnr")
	public void updateKunnr(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
		String[] retObj = new String[3];// 返回信息
		String kunnr = request.getParameter("kunnr");
		String bukrs = request.getParameter("bukrs");
		String flowNum = request.getParameter("flowNum");
		String from = request.getParameter("from");
		if(StringUtils.isBlank(kunnr)){
			retObj[0] = "fail";
			retObj[1] = "客户编号不能为空值!";
			JSONArray ja = JSONArray.fromObject(retObj);
			response.getOutputStream().write(ja.toString().getBytes("UTF-8"));
			return;
		}
		String query = "select nvl(a.zom,' '),nvl(a.sortl,' '),nvl(b.ename,' ') from zfmt0007 a left join pa0001 b on b.pernr = a.zom where a.kunnr='"+kunnr.trim()+"' and a.bukrs='"+bukrs+"'";
		List<Object> list = ficoManageService.findListBySql(query);
		StringBuffer sb = new StringBuffer();
		sb.append("update zfmt0003 set kunnr='").append(kunnr.trim()).append("' ");
		if(null!=list && list.size()>0){
			Object[] objects =(Object[]) list.get(0);
			retObj[1] = StringUtils.nullToString(objects[1]);
			retObj[2] = StringUtils.nullToString(objects[2]);
			sb.append(",zsname='").append(StringUtils.isBlank(StringUtils.nullToString(objects[0]))?" ":StringUtils.nullToString(objects[0])).append("'");
			sb.append(",sortl='").append((StringUtils.isBlank(StringUtils.nullToString(objects[1]))||"...".equals(kunnr))?" ":StringUtils.nullToString(objects[1])).append("'");
			if(StringUtils.isNotBlank(bukrs)){
				sb.append(",bukrs='").append(bukrs).append("'");
			}
		}else{
			sb.append(",zsname=' ',sortl=' '");
			retObj[1] = "";
			retObj[2] = "";
		}
		sb.append(" where zbelnr='").append(flowNum.trim()).append("'");
		try {
			ficoManageService.execSql(sb.toString());
			String updateType = TYPE_UPDATE_YW;
			if(StringUtils.isNotBlank(from)&&"cw".equals(from)){
				updateType = TYPE_UPDATE_CW;
			}
			ficoManageService.saveLog(null,updateType,"sql:"+sb.toString());
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
	 * 保存到账信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("reject")
	public void reject(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
		String retObj = "";// 返回信息
		//获取参数
		String flowNum = request.getParameter("flowNum");
		try {
			ficoManageService.checkManualAcct(flowNum);
			ficoManageService.rejectAcct(flowNum);
			ficoManageService.saveLog(null,TYPE_REJECT_YW,"财务退回,zbelnr:"+flowNum);
			retObj = "success";
		} catch (Exception e) {
			retObj = "退回失败:"+e.getMessage();
		}
		response.getOutputStream().write(retObj.getBytes("UTF-8"));
	}
	
	/**
	 * 生成凭证1:针对整单生成会计凭证
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("generateCertAll")
	public void generateCertAll(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
		String retObj = "";// 返回信息
		String flowNum = request.getParameter("flowNum");
		try {
			ficoManageService.checkAcctBalance(flowNum);
			ficoManageService.updateZPnameByZbelnr(flowNum);
			ficoManageService.certOne(flowNum);
			ficoManageService.saveLog(null,TYPE_CERT01_CW,"zbelnr:"+flowNum);
			retObj = "success";
		} catch (Exception e) {
			retObj = "生成凭证失败："+e.getMessage();
		}
		response.getOutputStream().write(retObj.getBytes("UTF-8"));
	}
	
	/**
	 * 手工记账
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping("manualCert")
	public void manualCert(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException,
			IOException {
		String[] retObj = new String[2];// 返回信息
		//获取参数
		String manualNum = request.getParameter("manualNum");
		String manualNum2 = request.getParameter("manualNum2");
		String manualNum3 = request.getParameter("manualNum3");
		if(StringUtils.isBlank(manualNum)){
			retObj[0] = "fail";
			retObj[1] = "凭证编号不能为空值!";
			JSONArray ja = JSONArray.fromObject(retObj);
			response.getOutputStream().write(ja.toString().getBytes("UTF-8"));
			return;
		}
		//校验不能重复生成凭证
		String flowNum = request.getParameter("flowNum");
	    List<Object> list = ficoManageService.findListBySql("select distinct belnr from zfmt0004 where zbelnr='"+flowNum+"'");
	    if(list!=null && list.size()>0){
	    	for(Object object:list){
	    		if(null!=object && StringUtils.isNotBlank(object.toString())){
	    			retObj[0] = "fail";
	    			retObj[1] = "当前到账信息已经生成凭证!";
	    			JSONArray ja = JSONArray.fromObject(retObj);
	    			response.getOutputStream().write(ja.toString().getBytes("UTF-8"));
	    			return;
	    		}
	    	}
	    }
		try {
			ficoManageService.updateZPnameByZbelnr(flowNum);
			List<String> belnrs = new ArrayList<String>();
			belnrs.add(manualNum);
			if(StringUtils.isNotBlank(manualNum2)){
				belnrs.add(manualNum2);
			}
			if(StringUtils.isNotBlank(manualNum3)){
				belnrs.add(manualNum3);
			}
			ficoManageService.certManual(flowNum,belnrs);
			ficoManageService.saveLog(null,TYPE_CERT01_CW,"手工生成会计凭证zbelnr:"+flowNum+",manualNum:"+manualNum);
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
	 * 对单笔预收分解信息进行生成凭证操作
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("generateCertSingle")
	public void generateCertSingle(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
		String[] retObj = new String[3];// 返回信息
		String flowNum = request.getParameter("flowNum");
		String zposnr = request.getParameter("zposnr");
		try {
			ficoManageService.updateZPnameByZbelnr(flowNum);
			String stblg = ficoManageService.certTwo(flowNum,zposnr);
			retObj[0] = "success";
			retObj[1] = stblg;
			retObj[2] = queryWodate(flowNum,zposnr);
			ficoManageService.saveLog(null,TYPE_CERT02_CW,"zbelnr:"+flowNum+",posnr:"+zposnr+",stblg="+stblg);
		} catch (Exception e) {
			retObj[0] = "fail";
			retObj[1] = e.getMessage();
		}
		JSONArray ja = JSONArray.fromObject(retObj);
		response.getOutputStream().write(ja.toString().getBytes("UTF-8"));
	}
	
	public String queryWodate(String flowNum,String zposnr){
		String query = "select zwodat from ZFMT0004 where zbelnr='"+flowNum.trim()+"' and zposnr='"+StringUtils.addLeftZero(zposnr,5)+"'";
		List<Object> list = ficoManageService.findListBySql(query);
		if(null!=list && list.size()>0){
			String wodate = StringUtils.nullToString(list.get(0));
			if(StringUtils.isNotBlank(wodate)&&wodate.length()>3){
				return wodate.substring(0,4);
			}
		}
		return "";
	}
	
	/**
	 * 
	 * 手工冲销
	 * 
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping("manualFlush")
	public void manualFlush(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
		String[] retObj = new String[2];// 返回信息
		//获取参数
		String manualNum = request.getParameter("manualNum");
		if(StringUtils.isBlank(manualNum)){
			retObj[0] = "fail";
			retObj[1] = "凭证编号不能为空值!";
			JSONArray ja = JSONArray.fromObject(retObj);
			response.getOutputStream()
					.write(ja.toString().getBytes("UTF-8"));
			return;
		}
		//校验不能重复生成凭证
		String flowNum = request.getParameter("flowNum");
		String zposnr = request.getParameter("zposnr");
		List<Object> list = ficoManageService.findListBySql("select distinct stblg from zfmt0004 where zbelnr='"+flowNum+"' and zposnr='"+StringUtils.addLeftZero(zposnr,5)+"'");
	    if(list!=null && list.size()>0){
	    	for(Object object:list){
	    		if(null!=object && StringUtils.isNotBlank(object.toString())){
	    			retObj[0] = "fail";
	    			retObj[1] = "当前预收款已经冲销,不能重复操作!";
	    			JSONArray ja = JSONArray.fromObject(retObj);
	    			response.getOutputStream().write(ja.toString().getBytes("UTF-8"));
	    			return;
	    		}
	    	}
	    }
		try {
			ficoManageService.updateZPnameByZbelnr(flowNum);
			ficoManageService.saveReceiveFlushManual(flowNum,manualNum,zposnr);
			ficoManageService.saveLog(null,TYPE_CERT02_CW,"手工生成预收冲销凭证zbelnr:"+flowNum+",zposnr:"+zposnr+",manualNum:"+manualNum);
			retObj[0] = "success";
		} catch (Exception e) {
			retObj[0] = "fail";
			retObj[1] = e.getMessage();
		}
		JSONArray ja = JSONArray.fromObject(retObj);
		response.getOutputStream().write(ja.toString().getBytes("UTF-8"));
	}
	
	/**
	 * 财务分解退回操作
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sepratePushBack")
	public void sepratePushBack(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
		String[] retObj = new String[2];// 返回信息
		String flowNum = request.getParameter("flowNum");
		try {
			ficoManageService.sepratePushBack(flowNum);
			ficoManageService.saveLog(null,TYPE_SEPBAC_YW,"财务分解退回,zbelnr:"+flowNum);
			retObj[0] = "success";
		} catch (Exception e) {
			retObj[0] = "fail";
			retObj[1] = e.getMessage();
		}
		JSONArray ja = JSONArray.fromObject(retObj);
		response.getOutputStream().write(ja.toString().getBytes("UTF-8"));
	}
	
	/**
	 * 回冲预收
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping("backFlushReceive")
	public void backFlushReceive(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		String message = "success";
		//流水号
		String zbelnr = request.getParameter("zbelnr");
		//行号
		String zposnr = request.getParameter("zposnr");
		//是否校验标识
		String check = request.getParameter("check");
		try {
			//校验当前是否为手工初始数据
			//(预收回冲暂不校验) ficoManageService.checkManualAcct(zbelnr);
			if(StringUtils.isNotBlank(check)&&"N".equals(check)){
				//校验标识为N时跳过校验
			}else{
				//校验凭证是否已经冲销
				ficoManageService.checkReceiveBelnrFlush(zbelnr,zposnr);
			}
			//回冲预收凭证
			ficoManageService.updateBackFlushReceive(zbelnr,zposnr);
			ficoManageService.saveLog(null,TYPE_BACK02_CW,"回冲预收,zbelnr:"+zbelnr+",zposnr:"+zposnr);
		} catch (Exception e) {
			message = e.getMessage();
		}
		response.getOutputStream().write(message.getBytes("UTF-8"));
	}
	
	/**
	 * 回冲凭证
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping("backFlushBelnr")
	public void backFlushBelnr(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		String message = "success";
		//流水号
		String zbelnr = request.getParameter("zbelnr");
		//是否校验标识
		String check = request.getParameter("check");
		try {
			//校验是否为手工初始信息
			ficoManageService.checkManualAcct(zbelnr);
			if(StringUtils.isNotBlank(check)&&"N".equals(check)){
				//校验标识为N时跳过校验
			}else{
				//校验凭证是否已经冲销
				ficoManageService.checkFicoBelnrFlush(zbelnr);
			}
			//回冲凭证
			ficoManageService.updateBackFlushBelnr(zbelnr);
			ficoManageService.saveLog(null,TYPE_BACK01_CW,"回冲凭证,zbelnr:"+zbelnr);
		} catch (Exception e) {
			message = e.getMessage();
		}
		response.getOutputStream().write(message.getBytes("UTF-8"));
	}
	
	
	/**
	 * 回冲并退回给出纳
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping("backFlushAndDelete")
	public void backFlushAndDelete(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		String message = "success";
		//流水号
		String zbelnr = request.getParameter("zbelnr");
		//是否校验标识
		String check = request.getParameter("check");
		try {
			//校验是否为手工初始信息
			ficoManageService.checkManualAcct(zbelnr);
			if(StringUtils.isNotBlank(check)&&"N".equals(check)){
				//校验标识为N时跳过校验
			}else{
				//校验凭证是否已经冲销
				ficoManageService.checkFicoBelnrFlush(zbelnr);
			}
			//回冲凭证并退回出纳
			ficoManageService.updateBackFlushAndDoDelete(zbelnr);
			ficoManageService.saveLog(null,TYPE_BAKDEL_YW,"回冲凭证并且退回财务,zbelnr:"+zbelnr);
		} catch (Exception e) {
			message = e.getMessage();
		}
		response.getOutputStream().write(message.getBytes("UTF-8"));
	}
	
	/**
	 * 根据流水号删除到账信息
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping("deleteReceive")
	public void deleteReceive(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		String zbelnr = request.getParameter("zbelnr");
		String message = "success";
		String deleteSub = "delete from zfmt0004 where zbelnr='"+zbelnr+"'";
		String deleteMain = "delete from zfmt0003 where zbelnr='"+zbelnr+"'";
		String updateDaily = "update zfmt0001 set zpc=' ' where zbelnr='"+zbelnr+"'";
		try {
			ficoManageService.checkManualAcct(zbelnr);
			ficoManageService.execSql(deleteSub);
			ficoManageService.execSql(deleteMain);
			ficoManageService.execSql(updateDaily);
			ficoManageService.saveLog(null,TYPE_DELETE_CW,"删除到账信息,zbelnr:"+zbelnr);
		} catch (Exception e) {
			message = e.getMessage();
		}
		response.getOutputStream().write(message.getBytes("UTF-8"));
	}
	
	/**
	 * 到款资料跟踪提醒功能
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping("remind")
	public void remind(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		String[] retObj = new String[2];// 返回信息
		try {
			ficoManageService.saveRemindInfo();
			retObj[0] = "success";
		} catch (Exception e) {
			retObj[0] = "fail";
			retObj[1] = e.getMessage();
		}
		JSONArray ja = JSONArray.fromObject(retObj);
		response.getOutputStream().write(ja.toString().getBytes("UTF-8"));
	}
	
	/**
	 * 发送邮件提醒
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping("sendEmail")
	public void sendEmail(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException,
			IOException {
		String[] retObj = new String[2];// 返回信息
		//获取参数
		String zbelnrs = request.getParameter("zbelnrs");
		String emailcc = request.getParameter("emailcc");
		try {
			if(StringUtils.isNotBlank(emailcc)){
				emailcc = emailcc.replaceAll(" ","").replaceAll(";",",");
			}
			String[] zbelnrArray = zbelnrs.split(",");
			ficoManageService.sendNoticeEmailToAe(zbelnrArray,emailcc);
			retObj[0] = "success";
		} catch (Exception e) {
			retObj[0] = "fail";
			retObj[1] = e.getMessage();
		}
		JSONArray ja = JSONArray.fromObject(retObj);
		response.getOutputStream().write(ja.toString().getBytes("UTF-8"));
	}
	
	@RequestMapping("/dataSynchronize")
	public String dataSynchronize(){
		return forward("/ldx/ficoManage/dataSynchronizeTest");
	}
	
}
