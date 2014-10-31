package com.wellsoft.pt.ldx.web.ficoManage;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wellsoft.pt.basicdata.sap.config.SAPDbConfig;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.model.ficoManage.Zfmt0017;
import com.wellsoft.pt.ldx.service.ficoManage.INonReceiveService;
import com.wellsoft.pt.ldx.util.DateUtils;
import com.wellsoft.pt.ldx.util.StringUtils;
import com.wellsoft.pt.security.core.userdetails.UserDetails;
import com.wellsoft.pt.security.facade.SecurityApiFacade;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

/**
 * 
 * Description: 未收汇手工资料
 *  
 * @author HeShi
 * @date 2014-8-25
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-8-26 	HeShi		2014-8-26		Create
 * </pre>
 *
 */
@Controller
@RequestMapping("/ficoManage/nonReceive")
public class NonReceiveController extends BaseController{
	@Resource(name="sapConnectConfig")
	private SAPDbConfig sapConfig;
	
	@Autowired
	private INonReceiveService nonReceiveService;
	
	//日利率
	private static Double INTEREST_CONS = 0.06D/360;
	
	//未收汇报表30天汇率
	private static Double INTEREST_ONE_MONTH = new BigDecimal(INTEREST_CONS).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
	
	//未收汇报表31天汇率
	private static Double INTEREST_DOUBLE_MONTH = new BigDecimal(INTEREST_CONS*2).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
	
	@RequestMapping("/add")
	public String add(Model model) throws Exception {
		model.addAttribute("model", "add"); //创建
		return forward("/ldx/ficoManage/nonReceiveAdd");
	}
	
	@RequestMapping("/modify")
	public String modify(Model model,@RequestParam(value = "vbeln") String vbeln) throws Exception {
		model.addAttribute("model", "modify"); //更改
		Zfmt0017 doc = nonReceiveService.findDocByVbeln(vbeln);
		model.addAttribute("doc",doc);
		return forward("/ldx/ficoManage/nonReceiveAdd");
	}
	
	@RequestMapping("/report")
	public String initReport(Model model,HttpServletRequest request){
		checkUserRole(request);
		return forward("/ldx/ficoManage/nonReceiveReport");
	}
	
	/**
	 * 进入未收汇报表时校验查看权限
	 */
	private void checkUserRole(HttpServletRequest request) {
		String ficoRole = StringUtils.nullToString(request.getSession().getAttribute("FicoRecRole"));
		if(StringUtils.isNotBlank(ficoRole)){
			return;
		}
		if(checkIsFicoAdmin(request)){
			return;
		}
		checkFicoRoleLevel(request);
	}
	
	private boolean checkIsFicoAdmin(HttpServletRequest request){
		if(hasRole("管理员角色","管理员权限")||hasGranted("001")){
			request.getSession().setAttribute("FicoRecRole", "ADMIN");
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	private void checkFicoRoleLevel(HttpServletRequest request){
		String userid = getLoginName();
		if(StringUtils.isNotBlank(userid)){
			String sql = "select min(type) from ("
				+ " select distinct 3 as type from zfmt0007 where zaa ='"+userid+"'"
				+ " union all"
				+ " select distinct 2 as type from zfmt0007 where zae ='"+userid+"'"
				+ " union all"
				+ " select distinct 1 as type from zfmt0007 where zrsm ='"+userid+"'"
				+ " union all"
				+ " select distinct 0 as type from zfmt0007 where zrname ='"+userid+"'"
				+ " )";
			List list = nonReceiveService.findListBySql(sql);
			if(null!=list && list.size()>0){
				String levelMax = StringUtils.nullToString(list.get(0));
				request.getSession().setAttribute("FicoRecRole", levelMax);
			}else{
				request.getSession().setAttribute("FicoRecRole", "NONE");
			}
		}
	}
	
	/**
	 * 查询未收汇信息
	 */
	private List<Object> findNoneRecList(HttpServletRequest request){
		String zar = request.getParameter("zarName");
		String bukrs = request.getParameter("bukrs");
		String bgnum = request.getParameter("bgnum");
		String sortl = request.getParameter("sortl");
		String vbeln = request.getParameter("vbeln");
		String cyDateBegin = request.getParameter("cyDateBegin");
		String cyDateEnd = request.getParameter("cyDateEnd");
		String wq = request.getParameter("wq");
		String bf = request.getParameter("bf");
		String yq = request.getParameter("yq");
		String kp = request.getParameter("kp");
		String jz = request.getParameter("jz");
		StringBuffer cond = new StringBuffer();
		cond.append("with temp as(")
			.append(" select vbeln,listagg(budat,'|') within group(order by budat) as budat,listagg(zcamount,'|') within group(order by budat) as zcamount,sum(zcamount) as amt,sum(zsum) as zsum,sum(zhc) as zhc from (")
			.append(" select na.vbeln, nb.budat, sum(na.zcamount-na.zhc) as zcamount,sum(na.zcamount) as zsum,sum(na.zhc) as zhc from zfmt0004 na,zfmt0003 nb where na.mandt=nb.mandt and (na.zrbl='B' or (na.zrbl='A' and not exists(select 1 from zfmt0004 te where te.zbelnr=na.zbelnr and te.zposnr_s=na.zposnr))) and na.zdrs='C' and na.zcirs='C' and na.zbelnr=nb.zbelnr and na.mandt=").append(sapConfig.getClient())
			.append(" and na.vbeln!=' ' group by na.vbeln, nb.budat,nb.zbelnr")
			.append(" )group by vbeln")
			.append(" ),bsidtep as(")
			.append(" select p.vgbel as vbeln,nvl(sum(case when bschl >'09' then -wrbtr else wrbtr end),0) as unclear from bsid d")
			.append(" inner join (select distinct vgbel,vbeln from vbrp) p on p.vbeln = d.zuonr")
			.append(" where umskz=' '")
			.append(" and d.mandt =").append(sapConfig.getClient())
			.append(" group by p.vgbel")
			.append(" )")
			.append(" select")
			.append(" a.vbeln 外向交货单,a.bukrs 出口单位,b.zddate 申报日期,a.zodate 出运日期,b.zeamt 报关金额,")
			.append(" a.zdamt 发货金额,a.ziamt 开票金额,a.maktx 品名,b.lfimg 报关数量,a.menge 实际数量,")
			.append(" b.zclfimg 折算数量,a.werks 生产工厂,a.zinvoice 发票号,a.znation 出口国家,a.kunnr 客户ID,")
			.append(" a.sortl 客户简称,a.zrsm RSM,a.zae AE,a.zaa AA,a.zeway 收汇方式,")
			.append(" b.zcost 报关海运费,b.vbelv 报关单号,b.zmodel 柜型,a.zlbelnr 收款凭证号,a.zlwaers 币种,")
			.append(" a.zlterm 付款条件代码,a.zlvtext 付款条件,nvl(t.amt,0) 已收金额,nvl(case when a.ziamt=0 then a.zdamt else a.ziamt end,0)+nvl(b.zcamt,0)-nvl(t.amt,0)-nvl(t.zhc,0) 应收款余额,")
			.append(" t.budat 到款日期,t.zcamount 到款金额,nvl(t.zhc,0) 调整金额,b.zvtext 扣款类型,b.zcamt 扣款金额,")
			.append(" b.znote 备注,a.zdays 期限,a.zddate 到期日,a.zdmonth 到期月份,a.fkdat 起算日期,a.ukurs 汇率,")
			.append(" a.klimk 信用额,a.fcurr 信用币种,a.ztendency 趋势,nvl(v.unclear,0) 未清金额,")
			.append(" case when nvl(case when a.ziamt=0 then a.zdamt else a.ziamt end,0)+nvl(b.zcamt,0)-nvl(t.amt,0)-nvl(t.zhc,0)=nvl(v.unclear,0) then '' else '是' end 是否不符,")
			.append(" nvl(case when a.ziamt=0 then a.zdamt else a.ziamt end,0)+nvl(b.zcamt,0)-nvl(t.amt,0)-nvl(t.zhc,0)-nvl(v.unclear,0) 不符金额,")
			.append(" nvl(d.zday,0) 宽限期,zkdate 放宽到期日,a.ukurs 开票汇率,a.vkbur 销售部门 ,a.meins 开票日期")
			.append(" from zfmt0016 a")
			.append(" left join zfmt0017 b on a.madat=b.mandt and a.vbeln=b.vbeln")
			.append(" left join zfmt0007 d on a.kunnr=d.kunnr and a.bukrs=d.bukrs")
			.append(" left join temp t on a.vbeln=t.vbeln")
			.append(" left join bsidtep v on a.vbeln=v.vbeln");
		if(StringUtils.isNotBlank(zar)){
			cond.append(" left join zfmt0007 z on a.madat=z.mandt and a.kunnr=z.kunnr and a.bukrs=z.bukrs");
		}
		cond.append(" where a.madat=").append(sapConfig.getClient());
		//校验权限
		if(!initRoleLevelCondition(request)){
			cond.append(" and 1=2");
		}
		String zrsmName = request.getParameter("zrsmName");
		String zaeName = request.getParameter("zaeName");
		String zaaName = request.getParameter("zaaName");
		if(StringUtils.isNotBlank(bukrs)){
			//公司代码
			cond.append(" and a.bukrs='").append(bukrs.trim()).append("'");
		}
		if(StringUtils.isNotBlank(zrsmName)){
			//RSM
			cond.append(" and a.zrsm like '%").append(zrsmName.trim()).append("%'");
		}
		if(StringUtils.isNotBlank(zaeName)){
			//AE
			cond.append(" and a.zae like '%").append(zaeName.trim()).append("%'");
		}
		if(StringUtils.isNotBlank(zaaName)){
			//AA
			cond.append(" and a.zaa like '%").append(zaaName.trim()).append("%'");
		}
		if(StringUtils.isNotBlank(zar)){
			cond.append(" and z.zrname='").append(zar).append("'");
		}
		if(StringUtils.isNotBlank(bgnum)){
			//报关单号
			cond.append(" and b.vbelv = '").append(bgnum.trim()).append("'");
		}
		if(StringUtils.isNotBlank(sortl)){
			//客户简称
			cond.append(" and a.sortl like '%").append(sortl.trim()).append("%'");
		}
		if(StringUtils.isNotBlank(vbeln)){
			//外向交货单
			cond.append(" and a.vbeln = '").append(StringUtils.addLeftZero(vbeln.trim(),10)).append("'");
		}
		if(StringUtils.isNotBlank(cyDateBegin)){
			//出运开始日期
			cond.append(" and a.zodate >='").append(cyDateBegin.trim().replaceAll("-","")).append("'");
		}
		if(StringUtils.isNotBlank(cyDateEnd)){
			//出运结束日期
			cond.append(" and a.zodate <='").append(cyDateEnd.trim().replaceAll("-","")).append("'");
		}
		if(StringUtils.isNotBlank(wq)){
			//是否未清
			if("1".equals(wq)){
				cond.append(" and nvl(case when a.zdamt>a.ziamt then a.zdamt else a.ziamt end,0)-nvl(t.amt,0)-nvl(t.zhc,0)>0 ");
			}else{
				cond.append(" and nvl(case when a.zdamt>a.ziamt then a.zdamt else a.ziamt end,0)-nvl(t.amt,0)-nvl(t.zhc,0)<=0 ");
			}
		}
		if(StringUtils.isNotBlank(yq)){
			//是否逾期
			if("1".equals(yq)){
				cond.append(" and nvl(case when a.zdamt>a.ziamt then a.zdamt else a.ziamt end,0)-nvl(t.amt,0)-nvl(t.zhc,0)>0 and nvl(a.zddate,'00000000')<to_char(sysdate-nvl(d.zday,0),'yyyyMMdd')");
			}else{
				cond.append(" and (nvl(case when a.zdamt>a.ziamt then a.zdamt else a.ziamt end,0)-nvl(t.amt,0)-nvl(t.zhc,0)<=0 or nvl(a.zddate,'00000000')>=to_char(sysdate-nvl(d.zday,0),'yyyyMMdd'))");
			}
		}
		if(StringUtils.isNotBlank(bf)){
			//是否不符
			if("1".equals(bf)){
				cond.append(" and nvl(case when a.zdamt>a.ziamt then a.zdamt else a.ziamt end,0)+nvl(b.zcamt,0)!=nvl(t.amt,0)+nvl(t.zhc,0)+nvl(v.unclear,0)");
			}else{
				cond.append(" and nvl(case when a.zdamt>a.ziamt then a.zdamt else a.ziamt end,0)+nvl(b.zcamt,0)=nvl(t.amt,0)+nvl(t.zhc,0)+nvl(v.unclear,0)");
			}
		}
		if(StringUtils.isNotBlank(kp)){
			//是否已开票
			if("1".equals(kp)){
				cond.append(" and a.fkdat!='00000000' and a.ziamt>0");
			}
		}
		cond.append(" order by a.vbeln");
		
		String query = cond.toString();
		if(StringUtils.isBlank(jz)){
			//是否已记账
			query.replaceFirst("and na.zdrs='C' and na.zcirs='C'","");
		}
		
		List<Object> list = nonReceiveService.findListBySql(query);
		return list;
	}
	
	private boolean initRoleLevelCondition(HttpServletRequest request){
		String roleLevel = StringUtils.nullToString(request.getSession().getAttribute("FicoRecRole"));
		if(StringUtils.isBlank(roleLevel)||"NONE".equals(roleLevel)){
			return false;
		}
		if("ADMIN".equals(roleLevel) || "0".equals(roleLevel)){
			//管理员或应收人员
			return true;
		}
		String userName = ((UserDetails)SpringSecurityUtils.getAuthentication().getPrincipal()).getUserName();
		if("1".equals(roleLevel)){
			//RSM
			request.setAttribute("zrsmName",userName);
		}else if("2".equals(roleLevel)){
			//AE
			request.setAttribute("zaeName",userName);
		}else if("3".equals(roleLevel)){
			//AA
			request.setAttribute("zaaName",userName);
		}
		return true;
	}
	
	/**
	 * 下载未收汇报表
	 * @throws Exception
	 */
	@RequestMapping("/download")
	public void downLoadExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		OutputStream os = response.getOutputStream();
		String dateStr = DateUtils.formatDate();
		String fileName = "noneReceiveProceeds_"+dateStr+".xls";
		response.reset();
		response.setHeader("Content-disposition",
				"attachment; filename=" + fileName);
		response.setContentType("application/msexcel");
		InputStream in = this.getClass().getResourceAsStream("noneReceiveProceeds.xls");
		List<Object> result = findNoneRecList(request);
		int resize = result.size();
		HSSFWorkbook workbook = new HSSFWorkbook(in);
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFRow row = null;
		HSSFCell cell = null;
		// 创建样式数组
		HSSFDataFormat dateformat = workbook.createDataFormat();
		HSSFCellStyle stringStyle = getStyleForStringCell(workbook.createCellStyle());
		HSSFCellStyle dateStyle = getStyleForDateCell(workbook.createCellStyle(),dateformat);
		HSSFCellStyle currencyStyle = getStyleForNumCell(workbook.createCellStyle());
		Object[] objects;
		for(int i=0;i<resize;i++){
			objects = (Object[])result.get(i);
			int j = 0;
			row = sheet.createRow(1 + i);
			//1外向交货单
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[0]));
			//2.出口单位
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[1]));
			//3.申报日期
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(dateStyle);
			if(null!=getDateValue(objects[2]))
				cell.setCellValue(getDateValue(objects[2]));
			//4.出运日期
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(dateStyle);
			if(null!=getDateValue(objects[3]))
				cell.setCellValue(getDateValue(objects[3]));
			//4.0开票日期  add by HeShi 20140626
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(dateStyle);
			if(null!=getDateValue(objects[50]))
				cell.setCellValue(getDateValue(objects[50]));
			//4.1起算日期  add by HeShi 20140314
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(dateStyle);
			if(null!=getDateValue(objects[38]))
				cell.setCellValue(getDateValue(objects[38]));
			//5.报关金额
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(getCurrencyValue(objects[4]));
			//6.发货金额
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(getCurrencyValue(objects[5]));
			//7.开票金额
			Double invoiceAmt = getCurrencyValue(objects[6]);
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(invoiceAmt);
			//8.品名
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[7]));
			//9.报关数量
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(getCurrencyValue(objects[8]));
			//10实际数量
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(getCurrencyValue(objects[9]));
			//11折算数量
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(getCurrencyValue(objects[10]));
			//12生产工厂
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[11]));
			//13发票号（外合同）
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[12]));
			//14出口国家
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[13]));
			//15客户ID
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[14]));
			//16客户简称
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[15]));
			//16.1销售部门
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[49]));
			//17RSM
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[16]));
			//18AE
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[17]));
			//19AA
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[18]));
			//20收汇方式
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[19]));
			//21报关海运费
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(getCurrencyValue(objects[20]));
			//22报关单号
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[21]));
			//23柜型
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[22]));
			//24收款凭证号
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[23]));
			//25币种
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[24]));
			//26付款条件代码
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[25]));
			//27付款条件
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[26]));
			//28已收金额
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(getCurrencyValue(objects[27]));
			//29应收款余额
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			Double receiveBlance = getCurrencyValue(objects[28]).compareTo(0D)>0?getCurrencyValue(objects[28]):0D;
			cell.setCellValue(receiveBlance);
			//最后一次到款日期
			Date finalRecDate;
			//30第一次到款日期
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(dateStyle);
			Date dateFirst = getReceiveDateIndex(objects[29],0);
			finalRecDate = dateFirst;
			if(null!=dateFirst)
				cell.setCellValue(dateFirst);
			//31第一次到款金额
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			Double amtFirst = getReceiveAmtIndex(objects[30],0);
			if(amtFirst!=null)
				cell.setCellValue(amtFirst);
			//32第二次到款日期
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(dateStyle);
			Date dateSecond = getReceiveDateIndex(objects[29],1);
			if(null!=dateSecond){
				if(dateSecond.after(finalRecDate)){
					finalRecDate=dateSecond;
				}
				cell.setCellValue(dateSecond);
			}
			//33第二次到款金额
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			Double amtSecond = getReceiveAmtIndex(objects[30],1);
			if(amtSecond!=null)
				cell.setCellValue(amtSecond);
			//34第三次到款日期
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(dateStyle);
			Date dateThird = getReceiveDateIndex(objects[29],2);
			if(null!=dateThird){
				if(dateThird.after(finalRecDate)){
					finalRecDate=dateThird;
				}
				cell.setCellValue(dateThird);
			}
			//35第三次到款金额
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			Double amtThird = getReceiveAmtIndex(objects[30],2);
			if(amtThird!=null)
				cell.setCellValue(amtThird);
			//36第四次到款日期
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(dateStyle);
			Date dateFourth = getReceiveDateIndex(objects[29],3);
			if(null!=dateFourth){
				if(dateFourth.after(finalRecDate)){
					finalRecDate=dateFourth;
				}
				cell.setCellValue(dateFourth);
			}
			//37第四次到款金额
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			Double amtFourth = getReceiveAmtIndex(objects[30],3);
			if(amtFourth!=null)
				cell.setCellValue(amtFourth);
			//37-1第五次到款日期
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(dateStyle);
			Date dateFifth = getReceiveDateIndex(objects[29],4);
			if(null!=dateFifth){
				if(dateFifth.after(finalRecDate)){
					finalRecDate=dateFifth;
				}
				cell.setCellValue(dateFifth);
			}
			//37-2第五次到款金额
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			Double amtFifth = getReceiveAmtIndex(objects[30],4);
			if(amtFifth!=null)
				cell.setCellValue(amtFifth);
			//37-2第六次到款日期
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			StringBuffer datas = new StringBuffer();
			int indexDate = 5;
			Date temp = null;
			do {
				temp = getReceiveDateIndex(objects[29],indexDate);
				if(null!=temp){
					if(temp.after(finalRecDate)){
						finalRecDate=temp;
					}
					datas.append(DateUtils.formatDate(temp,"yyyy/MM/dd")).append(";");
				}
				indexDate++;
			} while (temp!=null);
			if(StringUtils.isNotBlank(datas.toString())){
				cell.setCellValue(datas.toString());
			}
//			Date dateSixth = getReceiveDateIndex(objects[29],5);
//			if(null!=dateSixth){
//				if(dateSixth.after(finalRecDate)){
//					finalRecDate=dateSixth;
//				}
//				cell.setCellValue(dateSixth);
//			}
			//37-2第六次到款金额
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
//			Double amtSixth = getReceiveAmtIndex(objects[30],5);
//			if(amtSixth!=null && amtSixth.compareTo(0D)>0)
//				cell.setCellValue(amtSixth);
			StringBuffer amts = new StringBuffer();
			int indexAmt = 5;
			Double tempAmt = null;
			do {
				tempAmt = getReceiveAmtIndex(objects[30],indexAmt);
				if(null!=tempAmt){
					amts.append(tempAmt).append("; ");
				}
				indexAmt++;
			} while (tempAmt!=null);
			if(StringUtils.isNotBlank(amts.toString())){
				cell.setCellValue(amts.toString());
			}
			//38调整金额（手续费）
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(getCurrencyValue(objects[31]));
			//39扣款类型
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[32]));
			//40扣款金额
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(getCurrencyValue(objects[33]));
			//41备注
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[34]));
			//42期限
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(getCurrencyValue(objects[35]));
			//43到期日
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(dateStyle);
			Date endDate = getDateValue(objects[36]);
			if(null!=endDate)
				cell.setCellValue(endDate);
			//44到期月份
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(currencyStyle);
			String monthString=StringUtils.nullToString(objects[37]);
			if(StringUtils.isNotBlank(monthString)&&monthString.indexOf("0000")<0)
				cell.setCellValue(monthString);
			//44.1 放宽后到期日
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(dateStyle);
			Date fdDate = getDateValue(objects[47]);
			if(null!=fdDate)
				cell.setCellValue(fdDate);
			//宽限期
			int zday = Integer.valueOf(StringUtils.nullToString(objects[46]));
			if(zday>0&&null!=endDate){
				//宽限期大于0,则将到期日延后
				endDate = org.apache.commons.lang.time.DateUtils.addDays(endDate,zday);
			}
			//45逾期天数(应收余额>0?当前日期-到期日:NULL)
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			if(receiveBlance.compareTo(0D)>0){
				if(null!=endDate && DateUtils.beforeToday(endDate)){
					cell.setCellValue(DateUtils.dateDiff(DateUtils.now(),endDate));
				}
			}
			//  逾期条件
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			if(receiveBlance.compareTo(0D)>0){
				if(null!=endDate && DateUtils.beforeToday(endDate)){
					cell.setCellValue(getExpCondition(DateUtils.dateDiff(DateUtils.now(),endDate)));
				}
			}
			//46逾期金额((到期日<当前日期&&应收余额>0)?应收余额:0)
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			if(receiveBlance.compareTo(0D)>0){
				if(null!=endDate && DateUtils.beforeToday(endDate)){
					cell.setCellValue(receiveBlance);
				}
			}
			//单笔到期已收=∑(第N次到款日期<到期日?第N次到款金额:0)
			//Double singleRecAmtBeforeEnd = getSingleReceiveBeforeEndDate(endDate,objects[29],objects[30]);
			//逾期天数
			Integer expDays=0;
			if (receiveBlance.compareTo(0D) > 0) {
				// 应收余额>0:取今天-到期日
				if (null != fdDate && DateUtils.beforeToday(fdDate)) {
					expDays = DateUtils.dateDiff(fdDate, DateUtils.now());
				}
			} else {
				// 应收余额小于0且最后次到款日期>到期日:取最后一次到款日期-到期日
				if (null != fdDate && null != finalRecDate
						&& finalRecDate.after(fdDate)) {
					expDays = DateUtils.dateDiff(fdDate, finalRecDate);
				}
			}
			
			//单笔AR逾期金额
			Double singleExpAmts = getSingleReceiveAfterEndDate(fdDate,objects[29], objects[30])
					+ ((fdDate!=null && DateUtils.beforeToday(fdDate)) ? receiveBlance : 0D);
			//单笔AR欠款余额
			Double singleBalance = receiveBlance.compareTo(0D)>0?receiveBlance:0D;
			//51单笔逾期天数
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			int singleExpDays = 0;
			if(singleExpAmts.compareTo(0D)>0){
				if(singleBalance.compareTo(1D)>0 && DateUtils.beforeToday(fdDate)){
					//单笔AR欠款余额>1：今天-到期日
					singleExpDays = DateUtils.dateDiff(fdDate,DateUtils.now());
				}else{
					//最后一次到款日期-到期日
					if(null!=endDate && null!=finalRecDate && finalRecDate.after(fdDate)){
						singleExpDays = DateUtils.dateDiff(fdDate, finalRecDate);
					}
				}
			}else{
				singleExpDays = 0;
			}
			cell.setCellValue(singleExpDays);
			//47单笔AR欠款余额=7开票金额-48单笔到期已收
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(singleBalance);
			//48单笔到期已收--delete on 20140318
//			j++;
//			cell = row.createCell(j);
//			cell.setCellStyle(currencyStyle);
//			cell.setCellValue(singleRecAmtBeforeEnd);
			//49单笔AR逾期金额 =单笔逾期金额合计+应收余额
			
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			if(expDays>0){
				cell.setCellValue(singleExpAmts);
			}
			//50最后次到款日期
//			j++;
//			cell = row.createCell(j);
//			cell.setCellStyle(dateStyle);
//			if(finalRecDate!=null){
//				cell.setCellValue(finalRecDate);
//			}
			//51逾期天数(29应收款余额>0?(43到期日-今天):(50最后次到款日期-43到期日))
//			j++;
//			cell = row.createCell(j);
//			cell.setCellStyle(currencyStyle);
//			cell.setCellValue(expDays);
			//52逾期利息金额(49单笔AR逾期金额 * 51逾期天数 * 0.006 / 30)
//			Double expInterests = roundHalfUp((singleExpAmts*expDays*0.006D/30),2);
//			j++;
//			cell = row.createCell(j);
//			cell.setCellStyle(currencyStyle);
//			cell.setCellValue(expInterests);
			//53汇率
//			j++;
//			cell = row.createCell(j);
//			cell.setCellStyle(currencyStyle);
//			cell.setCellValue(getCurrencyValue(objects[39]));
			//54信用额
//			j++;
//			cell = row.createCell(j);
//			cell.setCellStyle(currencyStyle);
//			cell.setCellValue(getCurrencyValue(objects[40]));
			//54信用币种
//			j++;
//			cell = row.createCell(j);
//			cell.setCellStyle(stringStyle);
//			cell.setCellValue(StringUtils.nullToString(objects[41]));
			//55趋势
//			j++;
//			cell = row.createCell(j);
//			cell.setCellStyle(stringStyle);
//			cell.setCellValue(StringUtils.nullToString(objects[42]));
			//52 逾期利息金额
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(calculateExpInterest(fdDate,objects[29], objects[30], receiveBlance));
			//53 30天逾期日利率
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(new BigDecimal(INTEREST_CONS).setScale(6, BigDecimal.ROUND_HALF_UP).toString());
			//54 31天逾期日利率
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(new BigDecimal(INTEREST_CONS*2).setScale(6, BigDecimal.ROUND_HALF_UP).toString());
			//55开票汇率
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(getCurrencyValue(objects[48]));
			//56未清金额
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(getCurrencyValue(objects[43]));
			//57是否未清
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[44]));
			//58不符金额
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(getCurrencyValue(objects[45]));
		}
		
		workbook.write(os);
		response.flushBuffer();
		os.close();
	}
	
	/**
	 * 设置普通文本格样式
	 * @param style
	 * @return
	 */
	public static HSSFCellStyle getStyleForStringCell(HSSFCellStyle style) {
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
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
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
	public static HSSFCellStyle getStyleForDateCell(HSSFCellStyle style,
			HSSFDataFormat format) {
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
		style.setDataFormat(format.getFormat("yyyy-m-d"));
		return style;
	}
	
	/**
	 * 设置数值格样式
	 * @param style
	 * @return
	 */
	public static HSSFCellStyle getStyleForNumCell(HSSFCellStyle style) {
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
		return style;
	}
	
	/**
	 * 获取金额
	 * @param from
	 * @return
	 */
	private Date getDateValue(Object from){
		String value = StringUtils.nullToString(from);
		if(StringUtils.isBlank(value)
				||"00000000".equals(value)){
			return null;
		}
		try{
			Date current = DateUtils.parseDate(value,"yyyyMMdd");
			return current;
		}catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * 获取金额
	 * @param from
	 * @return
	 */
	private Double getCurrencyValue(Object from){
		Double result = 0D;
		try{
			result = Double.valueOf(StringUtils.nullToString(from));
		}catch (Exception e) {
			result = 0D;
		}
		return result;
	}
	
	/**
	 * 获取第N次到款日期
	 * @param from
	 * @param index
	 * @return
	 */
	private Date getReceiveDateIndex(Object from,int index){
		String value = StringUtils.nullToString(from);
		if(StringUtils.isBlank(value)||index<0)
			return null;
		String[] values = value.split("\\|");
		if(index>values.length-1)
			return null;
		return getDateValue(values[index]);
	}
	
	/**
	 * 获取第N次到款金额
	 * @param from
	 * @param index
	 * @return
	 */
	private Double getReceiveAmtIndex(Object from,int index){
		String value = StringUtils.nullToString(from);
		if(StringUtils.isBlank(value)||index<0)
			return null;
		String[] values = value.split("\\|");
		if(index>values.length-1)
			return null;
		return getCurrencyValue(values[index]);
	}
	
	/**
	 * 获取逾期条件
	 * @param expDays
	 * @return
	 */
	private String getExpCondition(int expDays){
		if(expDays<=30){
			return "0-30天";
		}else if(expDays<=60){
			return "30-60天";
		}else if(expDays<=90){
			return "60-90天";
		}else if(expDays<=180){
			return "90-180天";
		}else{
			return "180天以上";
		}
	}
	
	/**
	 * 单笔逾期金额合计
	 */
	private Double getSingleReceiveAfterEndDate(Date endDate,Object dates,Object amts){
		Double result = 0D;
		if(endDate==null)
			return 0D;
		Date recDate;
		int i=0;
		do{
			recDate = getReceiveDateIndex(dates,i);
			Double recAmt= getReceiveAmtIndex(amts,i);
			if(null!=recDate && null!=recAmt && recDate.after(endDate)){
				result+=recAmt;
			}
			i++;
		}while(recDate!=null);
		return result;
	}
	
	/**
	 * 计算逾期利息金额
	 * @param endDate
	 * @param dates
	 * @param amts
	 * @param balance
	 * @return
	 */
	public Double calculateExpInterest(Date endDate,Object dates,Object amts,Double balance){ 
		Double result = 0D;
		if(endDate==null)
			return 0D;
		Date recDate;
		int i=0;
		do{
			recDate = getReceiveDateIndex(dates,i);
			Double recAmt= getReceiveAmtIndex(amts,i);
			if(null!=recDate && null!=recAmt && recDate.after(endDate)){
				int dateDiff = DateUtils.dateDiff(endDate, recDate);
				int in30Day = dateDiff>30?30:dateDiff;
				int out30Day = dateDiff>30?(dateDiff-30):0;
				result+=recAmt*(in30Day*INTEREST_ONE_MONTH+out30Day*INTEREST_DOUBLE_MONTH);
			}
			i++;
		}while(recDate!=null);
		
		Date today = new Date();
		if(today.after(endDate)&&balance.compareTo(0D)>0){
			int dateDiff = DateUtils.dateDiff(endDate, today);
			int in30Day = dateDiff>30?30:dateDiff;
			int out30Day = dateDiff>30?(dateDiff-30):0;
			result+=balance*(in30Day*INTEREST_ONE_MONTH+out30Day*INTEREST_DOUBLE_MONTH);
		}
		return new BigDecimal(result).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 取得当前登录用户名
	 * @return
	 */
	private String getLoginName(){
		return ((UserDetails)SpringSecurityUtils.getAuthentication().getPrincipal()).getCode();
	}
	
	private boolean hasRole(String... roleName){
		return SpringSecurityUtils.hasAnyRole(roleName);
	}
	
	private boolean hasGranted(String code){
		return SecurityApiFacade.isGranted(code);
	}
	
}
