package com.wellsoft.pt.ldx.web.ficoManage;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wellsoft.pt.basicdata.sap.config.SAPDbConfig;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.model.ficoManage.Zfmt0003;
import com.wellsoft.pt.ldx.service.ISapService;
import com.wellsoft.pt.ldx.util.DateUtils;
import com.wellsoft.pt.ldx.util.StringUtils;
import com.wellsoft.pt.org.entity.User;
import com.wellsoft.pt.security.core.userdetails.UserDetails;
import com.wellsoft.pt.security.facade.SecurityApiFacade;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

/**
 * 
 * Description: 财务每日到款资料
 *  
 * @author HeShi
 * @date 2014-9-16
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-9-16 	HeShi		2014-9-16		Create
 * </pre>
 *
 */
@Controller
@RequestMapping("/ficoManage/dailyReceive")
public class DailyReceiveController extends BaseController{
	@Resource(name="sapConnectConfig")
	private SAPDbConfig sapConfig;
	@Autowired
	private SecurityApiFacade securityApiFacade;
	@Autowired
	private ISapService sapService;
	
	private Zfmt0003 param = new Zfmt0003();//查询参数

	@RequestMapping("/report")
	public String initReport(Model model,HttpServletRequest request){
		return forward("/ldx/ficoManage/dailyReceiveReport");
	}
	
	private boolean checkIsFicoAdmin(){
		if(hasRole("ROLE_ADMIN")){
			return true;
		}
		return false;
	}
	
	private boolean hasRole(String roleName){
		String userId = ((UserDetails)SpringSecurityUtils.getAuthentication().getPrincipal()).getUserId();
		return securityApiFacade.hasRole(userId,roleName);
	}
	
	/**
	 * 下载财务每日收款报表
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/download")
	public void downLoadExcel(HttpServletRequest request, HttpServletResponse response,Zfmt0003 param) throws Exception {
		OutputStream os = response.getOutputStream();
		String dateStr = DateUtils.formatDate();
		String fileName = "financeDailyCash_"+dateStr+".xls";
		response.reset();
		response.setHeader("Content-disposition",
				"attachment; filename=" + fileName);
		response.setContentType("application/msexcel");
		InputStream in = this.getClass().getResourceAsStream("default_financeDayCash.xls");
		List resultList = findDailyRecList(param);
		
		HSSFWorkbook workbook = new HSSFWorkbook(in);
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFRow row = null;
		HSSFCell cell = null;

		int columns = sheet.getRow(0).getPhysicalNumberOfCells();

		// 创建样式数组
		HSSFCellStyle styleArray[] = new HSSFCellStyle[columns];

		// 一次性创建所有列的样式放在数组里
		for (int s = 0; s < columns; s++) {
			// 得到数组实例
			styleArray[s] = workbook.createCellStyle();
		}

		HSSFCellStyle style = null;
		for (int i = 0; i < resultList.size(); i++) {
			int j = 0;
			int k = 0;
			Object[] model =(Object[]) resultList.get(i);
			row = sheet.createRow(1 + i);	
			//客户ID（明细）
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(StringUtils.nullToString(model[32]));				
			//客户简称（明细）
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(StringUtils.nullToString(model[33]));			
			//公司代码（明细）
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(StringUtils.nullToString(model[38]));	
			//记账汇率
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			if(null==model[20]||StringUtils.isBlank(model[20].toString())){
				cell.setCellValue("");
			}else{
				cell.setCellValue(Double.valueOf(StringUtils.nullToString(model[20])));
			}
			//收款币别
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(StringUtils.nullToString(model[7]));	
			//到款日期（收款日期）
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(StringUtils.nullToString(model[5]));
			//到帐金额
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);	
			cell.setCellValue(Double.valueOf(StringUtils.nullToString(model[6])));	
			//手续费
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			if(null==model[13]||StringUtils.isBlank(model[13].toString())){
				cell.setCellValue("");
			}else{
				cell.setCellValue(Double.valueOf(StringUtils.nullToString(model[13])));
			}
			//手续费比例(暂时为空串"",没有计算)
//			cell = row.createCell(j++);
//			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//			style = getStyle(styleArray[k++]);
//			style.setLocked(false);
//			cell.setCellStyle(style);
//			cell.setCellValue("");
			//本次付款金额（明细）（客户预付金额）
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			if(null==model[31]||StringUtils.isBlank(model[31].toString())){
				cell.setCellValue("");
			}else{
				cell.setCellValue(Double.valueOf(StringUtils.nullToString(model[31])));
			}
			//冲销金额（明细）（冲预收已出货的账款）
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			if(null==model[18]||StringUtils.isBlank(model[18].toString())){
				cell.setCellValue("");
			}else{
				cell.setCellValue(Double.valueOf(StringUtils.nullToString(model[18])));
			}
			//预收账款余额=本次付款金额（明细）-冲销金额（明细）
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			if(null==model[31]||StringUtils.isBlank(model[31].toString())||null==model[18]||StringUtils.isBlank(model[18].toString())){
				cell.setCellValue("");
			}else{
				cell.setCellValue(Double.valueOf(StringUtils.nullToString(model[31]))-Double.valueOf(StringUtils.nullToString(model[18])));
			}
			//add by HeShi 20140416 begin 添加已开票过账后的冲销金额及预付款余额
			//实际过账冲销金额
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			if(null==model[39]||StringUtils.isBlank(model[39].toString())){
				cell.setCellValue("");
			}else{
				cell.setCellValue(Double.valueOf(StringUtils.nullToString(model[39])));
			}
			//过账后预收账款余额
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			if(null==model[31]||StringUtils.isBlank(model[31].toString())||null==model[39]||StringUtils.isBlank(model[39].toString())){
				cell.setCellValue("");
			}else{
				cell.setCellValue(Double.valueOf(StringUtils.nullToString(model[31]))-Double.valueOf(StringUtils.nullToString(model[39])));
			}
			//add by HeShi 20140416 end
			//完整合同号
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(StringUtils.nullToString(model[15]));
			//凭证编号（流水号）
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(StringUtils.nullToString(model[1]));				
			//记账凭证号
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(StringUtils.nullToString(model[34])+","+StringUtils.nullToString(model[35])+","+StringUtils.nullToString(model[36]));
		    //冲销凭证
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(StringUtils.nullToString(model[37]));
			//申报单号
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(StringUtils.nullToString(model[8]));			
			//国家汇款人
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(StringUtils.nullToString(model[9]));			
			//业务对象（摘要）
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(StringUtils.nullToString(model[10]));	
			//预收状态
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			if(StringUtils.nullToString(model[24]).equals("P")){
				cell.setCellValue("P-未分解");
			}else if(StringUtils.nullToString(model[24]).equals("F")){
				cell.setCellValue("F-已分解");
			}else if(StringUtils.nullToString(model[24]).equals("C")){
				cell.setCellValue("C-已确认");
			}
			//流转状态
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			if(StringUtils.nullToString(model[25]).equals("P")){
				cell.setCellValue("P-维护");
			}else if(StringUtils.nullToString(model[25]).equals("F")){
				cell.setCellValue("F-确认");
			}else if(StringUtils.nullToString(model[25]).equals("C")){
				cell.setCellValue("C-生成凭证");
			}
			//是否登记
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			if(StringUtils.nullToString(model[11]).equals("0")){
				cell.setCellValue("否");
			}else if(StringUtils.nullToString(model[11]).equals("1")){
				cell.setCellValue("是");
			}						
			//是否入账
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			if(StringUtils.nullToString(model[12]).equals("0")){
				cell.setCellValue("否");
			}else if(StringUtils.nullToString(model[12]).equals("1")){
				cell.setCellValue("是");
			}
			//RSM
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(StringUtils.nullToString(model[26]));
			//OM
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(StringUtils.nullToString(model[27]));
			//AE
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(StringUtils.nullToString(model[28]));
			//AA
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(StringUtils.nullToString(model[29]));
			//应收人员
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(StringUtils.nullToString(model[30]));
			//合同金额
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			if(null==model[14]||StringUtils.isBlank(model[14].toString())){
				cell.setCellValue("");
			}else{
				cell.setCellValue(Double.valueOf(StringUtils.nullToString(model[14])));
			}		
			//预计出口日期
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(StringUtils.nullToString(model[16]));
			//外向交货单号
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(StringUtils.nullToString(model[17]));
			//业务类型
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			if(null==model[19]||StringUtils.isBlank(model[19].toString())){
				cell.setCellValue("");
			}else if("A".equals(model[19])){
				cell.setCellValue("预收");
			}else if("B".equals(model[19])){
				cell.setCellValue("应收");
			}else if("C".equals(model[19])){
				cell.setCellValue("其他应收款");
			}else if("D".equals(model[19])){
				cell.setCellValue("样品款");
			}			
			//样品款类型
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);          
			if(null==model[21]||StringUtils.isBlank(model[21].toString())){
				cell.setCellValue("");
			}else if("1".equals(model[21])){
				cell.setCellValue("报关");
			}else if("2".equals(model[21])){
				cell.setCellValue("不报关");
			}else if("3".equals(model[21])){
				cell.setCellValue("内销");
			}
			//特殊总账
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(StringUtils.nullToString(model[22]));			
			//天数:到款日期-预计出口日期
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			if(StringUtils.isNotBlank(StringUtils.nullToString(model[5]))&&!"00000000".equals(StringUtils.nullToString(model[5]))&&StringUtils.isNotBlank(StringUtils.nullToString(model[16]))&&!"00000000".equals(StringUtils.nullToString(model[16]))){
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		        Date fDate = format.parse(StringUtils.nullToString(model[16]),new ParsePosition(0));
		        Date oDate = format.parse(StringUtils.nullToString(model[5]),new ParsePosition(0));
				int day = daysOfTwo(fDate, oDate);
				cell.setCellValue(day);//double
			}else{
				cell.setCellValue("");
			}
			//客户编码（zfmt0003表）
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(StringUtils.nullToString(model[3]));
			//客户简称(zfmt0003表)
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(StringUtils.nullToString(model[4]));
			//公司代码(zfmt0003表)
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(StringUtils.nullToString(model[2]));				
			//数据来源
			cell = row.createCell(j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			if(StringUtils.nullToString(model[23]).equals("0")){
				cell.setCellValue("手动");
			}else if(StringUtils.nullToString(model[23]).equals("1")){
				cell.setCellValue("自动");
			}			
		}
		
		workbook.write(os);
		response.flushBuffer();
		os.close();
	}
	
	@SuppressWarnings("rawtypes")
	private List findDailyRecList(Zfmt0003 param2) {
		boolean isAdmin = checkIsFicoAdmin();
		String currentUserId = getUserCode();
		String client = sapConfig.getClient();
		//从客户对应表ZFMT0007查询该用户（应收人员）所对应的“公司代码+客户编码”
		String sqlBukrsAndKunnr = "select mandt,bukrs,kunnr from ZFMT0007 where mandt = '"+client+"'  and zrname='"+currentUserId+"'";
		List bukrsAndKunnrList = sapService.findListBySql(sqlBukrsAndKunnr);
		String bukrsAndKunnrTotal = "";
		for (Object object : bukrsAndKunnrList) {
			Object[] objects = (Object[]) object;
			bukrsAndKunnrTotal += "'"+objects[1].toString().trim()+objects[2].toString().trim()+"',";
		}
		String bukrsAndKunnrCondition = "";//构造公司代码和客户编码的结合体为查询条件，类似：(公司代码1客户编码1,公司代码2客户编码2...)
		if(isAdmin){//应收管理员-查看全部,AR模块业务管理员-查看全部权限
			bukrsAndKunnrCondition = "I am admin";
		}else if(StringUtils.isNotBlank(bukrsAndKunnrTotal)){
			bukrsAndKunnrCondition = "("+bukrsAndKunnrTotal.substring(0, bukrsAndKunnrTotal.length()-1)+")";
		}
		String sql = "select t3.mandt,t3.zbelnr,t3.bukrs,t3.kunnr,nvl((select kna1.sortl from kna1 where kna1.kunnr = t3.Kunnr and kna1.mandt = '"
				+ client
				+ "' and rownum=1),' ') as sortlTemp,t3.bldat,t3.zcamount,t3.waers,t3.zdoip,t3.zcrem,t3.sgtxt,t3.zcheck,t3.zpost,t4.zhc,t4.zbamt,t4.bstkd,t4.zpodat,t4.vbeln,t4.zwoamt,t4.zrbl,t4.kursf,t4.zsmc,t4.umskz,t3.zds,t4.zdrs,t4.zcirs,(select distinct pa.ename from pa0001 pa where pa.mandt = '"
				+ client
				+ "' and pa.pernr = t7.zrsm) as zrsmTemp,(select distinct pa.ename from pa0001 pa where pa.mandt = '"
				+ client
				+ "' and pa.pernr = t7.zom) as zomTemp,(select distinct pa.ename from pa0001 pa where pa.mandt = '"
				+ client
				+ "' and pa.pernr = t7.zae) as zaeTemp,(select distinct pa.ename from pa0001 pa where pa.mandt = '"
				+ client
				+ "' and pa.pernr = t7.zaa) as zaaTemp,(select distinct pa.ename from pa0001 pa where pa.mandt = '"
				+ client
				+ "' and pa.pernr = t7.zrname) as zrnameTemp,t4.zcamount as zcamount2,t4.kunnr as kunnr2 ,nvl((select kna1.sortl from kna1 where kna1.kunnr = t4.Kunnr and kna1.mandt = '"
				+ client
				+ "' and rownum=1),' ') as sortlTemp2,t4.belnr,t4.belnr2,t4.belnr3,t4.stblg,t4.bukrs as bukrs2," 
				+ " nvl(case when t4.zrbl='A' and t4.vbeln=' ' then (select sum(temp4.zcamount) from zfmt0004 temp4 where temp4.zbelnr=t4.zbelnr and temp4.zposnr_s=t4.zposnr and temp4.zdrs='C' and temp4.zcirs='C') else (case when t4.zdrs='C' and t4.zcirs='C' then t4.zcamount else 0 end) end,0) as cxamt"
				+ " from ZFMT0003 t3 left join ZFMT0004 t4 on t3.zbelnr = t4.zbelnr left join ZFMT0007 t7 on t3.bukrs||t3.kunnr = t7.bukrs||t7.kunnr where t3.mandt = '"
				+ client + "'";
		if(StringUtils.isNotBlank(bukrsAndKunnrCondition)){//权限控制的查询条件（公司代码和客户编码的结合体），有数据访问
			if(!"I am admin".equals(bukrsAndKunnrCondition)){//不是管理员，只能查看、维护自己对应的公司代码和客户编码，管理员可以查看全部信息，但不能维护
				sql += " and t3.bukrs||t3.kunnr in "+bukrsAndKunnrCondition+"";
			}
			if (StringUtils.isNotBlank(param.getZbelnr())) {//凭证编号
				sql += " and t3.zbelnr='"+param.getZbelnr().trim()+"'";
			}
			if (StringUtils.isNotBlank(param.getBukrs())) {//公司代码
				sql += " and t3.bukrs='"+param.getBukrs().trim()+"'";
			}
			if (StringUtils.isNotBlank(param.getKunnr())) {//客户编码
				sql += " and t3.kunnr='"+param.getKunnr().trim()+"'";
			}
			if (StringUtils.isNotBlank(param.getSortl())) {//客户简称
				sql += " and t3.sortl like '%"+param.getSortl().trim()+"%'";
			}			
			if (StringUtils.isNotBlank(param.getFromBldat())) {//到款日期
				sql += " and t3.bldat>='"+param.getFromBldat().trim().replaceAll("-", "")+"'";
			}
			if (StringUtils.isNotBlank(param.getToBldat())) {//到款日期
				sql += " and t3.bldat<='"+param.getToBldat().trim().replaceAll("-", "")+"'";
			}
			if (StringUtils.isNotBlank(param.getZdoip())) {//申报单号
				sql += " and t3.zdoip='"+param.getZdoip().trim()+"'";
			}
			if (StringUtils.isNotBlank(param.getZcrem())) {//国家汇款人
				sql += " and t3.zcrem='"+param.getZcrem().trim()+"'";
			}
			if (StringUtils.isNotBlank(param.getZcheck())) {//是否登记
				sql += " and t3.zcheck='"+param.getZcheck().trim()+"'";
			}
			if (StringUtils.isNotBlank(param.getZpost())) {//是否入账
				sql += " and t3.zpost='"+param.getZpost().trim()+"'";
			}
			if (StringUtils.isNotBlank(param.getZds())) {//数据来源
				sql += " and t3.zds='"+param.getZds().trim()+"'";
			}
			if (StringUtils.isNotBlank(param.getZae())) {//zae
				sql += " and t7.zae='"+param.getZae().trim()+"'";
			}
			if (StringUtils.isNotBlank(param.getZrname())) {//zrname
				sql += " and t7.zrname='"+param.getZrname().trim()+"'";
			}
			//add by HeShi 20140508 begin
			if (StringUtils.isNotBlank(param.getZaa())) {//zaa
				sql += " and t7.zaa='"+param.getZaa().trim()+"'";
			}
			if (StringUtils.isNotBlank(param.getZrsm())) {//zrsm
				sql += " and t7.zrsm='"+param.getZrsm().trim()+"'";
			}
			//add by HeShi 20140508 end
			if (StringUtils.isNotBlank(param.getZrbl())){//业务类型
				sql += " and t4.zrbl='"+param.getZrbl()+"'";
			}
			if (StringUtils.isNotBlank(param.getBstkd())){//完整合同号
				sql += " and t4.bstkd like '%"+param.getBstkd().trim()+"%'";
			}
			if (StringUtils.isNotBlank(param.getVbeln())){//外向交货单号
				sql += " and t4.vbeln='"+param.getVbeln().trim()+"'";
			}
			sql += " order by t3.zbelnr";//按凭证编号（流水号）升序排序，为分页服务
		}
		return sapService.findListBySql(sql);
	}

	/**
	 * 两个时间相距的天数
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public static int daysOfTwo(Date fDate, Date oDate) {
	       Calendar aCalendar = Calendar.getInstance();
	       aCalendar.setTime(fDate);
	       int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
	       aCalendar.setTime(oDate);
	       int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
	       return  day2-day1;
	 }
	
	public HSSFCellStyle getStyle(HSSFCellStyle style) {
		// 设置底边框;
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
		// 设置左边框;
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// 设置左边框颜色;
		style.setLeftBorderColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
		// 设置右边框;
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
		// 设置顶边框;
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 设置顶边框颜色;
		style.setTopBorderColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
		// 在样式用应用设置的字体;
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		return style;
	}
	
	/**
	 * 取得当前登录工号
	 * @return
	 */
	private String getUserCode(){
		String userId = ((UserDetails)SpringSecurityUtils.getAuthentication().getPrincipal()).getUserId();
		User user = sapService.getCurrentUser(userId);
		return user.getEmployeeNumber();
	}
	

	public Zfmt0003 getParam() {
		return param;
	}

	public void setParam(Zfmt0003 param) {
		this.param = param;
	}
}
