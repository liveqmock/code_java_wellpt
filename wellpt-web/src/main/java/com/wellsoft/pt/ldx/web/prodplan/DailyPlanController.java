package com.wellsoft.pt.ldx.web.prodplan;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
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
import com.wellsoft.pt.ldx.model.productionPlan.PlanShare;
import com.wellsoft.pt.ldx.service.productionPlan.IProductionPlanService;
import com.wellsoft.pt.ldx.util.DateUtils;
import com.wellsoft.pt.ldx.util.StringUtils;

/**
 * 
 * Description: 日滚动计划
 *  
 * @author HeShi
 * @date 2014-9-15
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-9-15 	HeShi		2014-9-15		Create
 * </pre>
 *
 */
@Controller
@RequestMapping("/productionPlan/dailyPlanReport/")
public class DailyPlanController extends BaseController{
	@Resource(name="sapConnectConfig")
	private SAPDbConfig sapConfig;
	
	@Autowired
	private IProductionPlanService productionPlanService;
	
	@RequestMapping("/report")
	public String initReport(Model model,HttpServletRequest request){
		String sql = "select zversion from (select distinct substr(zversion,0,8) as zversion from zppt0044 order by zversion desc) where rownum <= 10";
		List<Object> list = productionPlanService.findListBySql(sql);
		String sgQuqry = "select distinct zsg from zppt0035 where mandt='"+sapConfig.getClient()+"' order by zsg";
		List<Object> selectSgList = productionPlanService.findListBySql(sgQuqry);
		model.addAttribute("versionList",list);
		model.addAttribute("selectSgList",selectSgList);
		return forward("/ldx/prodplan/dailyPlanReport");
	}
	
	/**
	 * 
	 * 如何描述该方法
	 * 
	 * @param reportType
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/exportExcelAll")
	public void exportExcelAll(@RequestParam("reportType") String reportType,@RequestParam("version") String version,HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 查询数据
		String searchType1 = request.getParameter("searchType1");
		String searchValue1 = request.getParameter("searchValue1");
		String searchType2 = request.getParameter("searchType2");
		String searchValue2 = request.getParameter("searchValue2");
		String searchType3 = request.getParameter("searchType3");
		String searchValue3 = request.getParameter("searchValue3");
		String searchValue4 = request.getParameter("searchValue4");
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(searchValue1)) {
			map.put(searchType1, searchValue1);
		}
		if (StringUtils.isNotBlank(searchValue2)) {
			map.put(searchType2, searchValue2);
		}
		if (StringUtils.isNotBlank(searchValue3)) {
			map.put(searchType3, searchValue3.replaceAll("-", ""));
		}
		if (StringUtils.isNotBlank(searchValue4)) {
			map.put("aufnr", StringUtils.addLeftZero(searchValue4,12));
		}
		
		// 查询接下来十天内的可排产日期
		// 版本对应日期
		String beginDay = version.substring(0, 8);
		// 查询接下来十天内的可排产日期
		String sqlA = "select zrq from(select zrq, rownum from (select distinct zrq from zppt0034 where zrq >= '"
				+ beginDay + "' order by zrq) where rownum <= 10)";
		List days = productionPlanService.findListBySql(sqlA);
		int daycount = Integer.parseInt(reportType);
		if(days!=null && days.size()>daycount){
			days=days.subList(0, daycount);
		}
		String startDay = "";
		String endDay = "";
		if (null != days && days.size() > 0) {
			startDay = DateUtils.formatDate(DateUtils.parseDate(
					String.valueOf(days.get(0)), "yyyyMMdd"), "MM月dd日");
			endDay = DateUtils.formatDate(DateUtils.parseDate(
					String.valueOf(days.get(daycount - 1)), "yyyyMMdd"),
					"MM月dd日");
		}
		map.put("version", version);
		map.put("days", days);
		List<PlanShare> li = productionPlanService.findSharingPlanByPage(map);
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
		//设置第一行标题
		row = sheet.getRow(0);
		cell = row.getCell(0);
		cell.setCellValue("                                 整灯生产计划");
		//设置第二行标题
		row = sheet.getRow(1);
		cell = row.getCell(0);
		cell.setCellValue("                                ".concat(startDay).concat("-").concat(endDay));
		//设置第三行排产日期
		row = sheet.getRow(2);
		for(int i=0;i<daycount;i++){
			cell = row.getCell(14+i*3);
			cell.setCellValue(DateUtils.parseDate(String.valueOf(days.get(i)), "yyyyMMdd"));
		}
		//设置第四行排产日期-星期
		row = sheet.getRow(3);
		for(int i=0;i<daycount;i++){
			cell = row.getCell(14+i*3);
			cell.setCellValue(DateUtils.getWeekOfDate(DateUtils.parseDate(String.valueOf(days.get(i)), "yyyyMMdd")));
		}
		//合计行
		int[] lineSum = new int[30];//线别合计
		int[] allSum = new int[30];//全部合计
		HSSFCellStyle styleArray[] = new HSSFCellStyle[4];
		HSSFFont font = workbook.createFont();
		HSSFDataFormat dateformat= workbook.createDataFormat();
		for (int s = 0; s < 4; s++) {
			styleArray[s] = workbook.createCellStyle();
		}
		int rowCount=5;
		String lineNo = null;//线号
		for(PlanShare ps:li){
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
			}
			// 线号
			cell = row.getCell(0);
			cell.setCellValue(ps.getLineNo());
			// 客户
			cell = row.getCell(1);
			cell.setCellValue(ps.getCustNo());
			// 生产订单号
			cell = row.getCell(2);
			cell.setCellValue(ps.getProductOrder());
			// 销售订单号
			cell = row.getCell(3);
			cell.setCellValue(ps.getSaleOrder());
			// 行项目
			cell = row.getCell(4);
			cell.setCellValue(ps.getOrderLineNo());
			// 物料id
			cell = row.getCell(5);
			cell.setCellValue(ps.getWlId());
			// 物料描述
			cell = row.getCell(6);
			cell.setCellValue(ps.getWlDesc());
			// 订单量
			cell = row.getCell(7);
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			try {
				cell.setCellValue(Integer.valueOf(ps.getOrderAmt()));
			} catch (Exception e) {
				cell.setCellValue(0);
			}
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
			cell.setCellValue(ps.getZzHours());
			// 包装工时
			cell = row.getCell(11);
			cell.setCellStyle(getStyleForNormalNumRow(styleArray[3], font,
					normal));
			cell.setCellValue(ps.getBzHours());
			// 计划量
			cell = row.getCell(12);
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			try {
				cell.setCellValue(Integer.valueOf(ps.getPlanAmt()));
			} catch (Exception e) {
				cell.setCellValue(0);
			}
			// 已完成量
			cell = row.getCell(13);
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			try {
				cell.setCellValue(Integer.valueOf(ps.getCompleteAmt()));
			} catch (Exception e) {
				cell.setCellValue(0);
			}
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
				cell.setCellValue(Integer.valueOf(ps.getDay1A()));
				cell.setCellStyle(getStyleForNormalNumRow(styleArray[3], font,
						normal));
				lineSum[0] += Integer.parseInt(ps.getDay1A());
				allSum[0] += Integer.parseInt(ps.getDay1A());
			}
			cell = row.getCell(15);
			if (!"0".equals(ps.getDay1B())) {
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
				cell.setCellValue(Integer.valueOf(ps.getDay1C()));
				lineSum[2] += Integer.parseInt(ps.getDay1C());
				allSum[2] += Integer.parseInt(ps.getDay1C());
			}
			cell = row.getCell(17);
			if (!"0".equals(ps.getDay2A())) {
				cell.setCellStyle(getStyleForNormalNumRow(styleArray[3], font,
						normal));
				cell.setCellValue(Integer.valueOf(ps.getDay2A()));
				lineSum[3] += Integer.parseInt(ps.getDay2A());
				allSum[3] += Integer.parseInt(ps.getDay2A());
			}
			cell = row.getCell(18);
			if (!"0".equals(ps.getDay2B())) {
				cell.setCellStyle(getStyleForNormalNumRow(styleArray[3], font,
						normal));
				cell.setCellValue(Integer.valueOf(ps.getDay2B()));
				lineSum[4] += Integer.parseInt(ps.getDay2B());
				allSum[4] += Integer.parseInt(ps.getDay2B());
			}
			cell = row.getCell(19);
			if (!"0".equals(ps.getDay2C())) {
				cell.setCellStyle(getStyleForNormalNumRow(styleArray[3], font,
						normal));
				cell.setCellValue(Integer.valueOf(ps.getDay2C()));
				lineSum[5] += Integer.parseInt(ps.getDay2C());
				allSum[5] += Integer.parseInt(ps.getDay2C());
			}
			cell = row.getCell(20);
			if (!"0".equals(ps.getDay3A())) {
				cell.setCellStyle(getStyleForNormalNumRow(styleArray[3], font,
						normal));
				cell.setCellValue(Integer.valueOf(ps.getDay3A()));
				lineSum[6] += Integer.parseInt(ps.getDay3A());
				allSum[6] += Integer.parseInt(ps.getDay3A());
			}
			cell = row.getCell(21);
			if (!"0".equals(ps.getDay3B())) {
				cell.setCellStyle(getStyleForNormalNumRow(styleArray[3], font,
						normal));
				cell.setCellValue(Integer.valueOf(ps.getDay3B()));
				lineSum[7] += Integer.parseInt(ps.getDay3B());
				allSum[7] += Integer.parseInt(ps.getDay3B());
			}
			cell = row.getCell(22);
			if (!"0".equals(ps.getDay3C())) {
				cell.setCellStyle(getStyleForNormalNumRow(styleArray[3], font,
						normal));
				cell.setCellValue(Integer.valueOf(ps.getDay3C()));
				lineSum[8] += Integer.parseInt(ps.getDay3C());
				allSum[8] += Integer.parseInt(ps.getDay3C());
			}
			if (daycount >= 7) {
				cell = row.getCell(23);
				if (!"0".equals(ps.getDay4A())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellValue(Integer.valueOf(ps.getDay4A()));
					lineSum[9] += Integer.parseInt(ps.getDay4A());
					allSum[9] += Integer.parseInt(ps.getDay4A());
				}
				cell = row.getCell(24);
				if (!"0".equals(ps.getDay4B())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellValue(Integer.valueOf(ps.getDay4B()));
					lineSum[10] += Integer.parseInt(ps.getDay4B());
					allSum[10] += Integer.parseInt(ps.getDay4B());
				}
				cell = row.getCell(25);
				if (!"0".equals(ps.getDay4C())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellValue(Integer.valueOf(ps.getDay4C()));
					lineSum[11] += Integer.parseInt(ps.getDay4C());
					allSum[11] += Integer.parseInt(ps.getDay4C());
				}
				cell = row.getCell(26);
				if (!"0".equals(ps.getDay5A())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellValue(Integer.valueOf(ps.getDay5A()));
					lineSum[12] += Integer.parseInt(ps.getDay5A());
					allSum[12] += Integer.parseInt(ps.getDay5A());
				}
				cell = row.getCell(27);
				if (!"0".equals(ps.getDay5B())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellValue(Integer.valueOf(ps.getDay5B()));
					lineSum[13] += Integer.parseInt(ps.getDay5B());
					allSum[13] += Integer.parseInt(ps.getDay5B());
				}
				cell = row.getCell(28);
				if (!"0".equals(ps.getDay5C())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellValue(Integer.valueOf(ps.getDay5C()));
					lineSum[14] += Integer.parseInt(ps.getDay5C());
					allSum[14] += Integer.parseInt(ps.getDay5C());
				}
				cell = row.getCell(29);
				if (!"0".equals(ps.getDay6A())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellValue(Integer.valueOf(ps.getDay6A()));
					lineSum[15] += Integer.parseInt(ps.getDay6A());
					allSum[15] += Integer.parseInt(ps.getDay6A());
				}
				cell = row.getCell(30);
				if (!"0".equals(ps.getDay6B())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellValue(Integer.valueOf(ps.getDay6B()));
					lineSum[16] += Integer.parseInt(ps.getDay6B());
					allSum[16] += Integer.parseInt(ps.getDay6B());
				}
				cell = row.getCell(31);
				if (!"0".equals(ps.getDay6C())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellValue(Integer.valueOf(ps.getDay6C()));
					lineSum[17] += Integer.parseInt(ps.getDay6C());
					allSum[17] += Integer.parseInt(ps.getDay6C());
				}
				cell = row.getCell(32);
				if (!"0".equals(ps.getDay7A())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellValue(Integer.valueOf(ps.getDay7A()));
					lineSum[18] += Integer.parseInt(ps.getDay7A());
					allSum[18] += Integer.parseInt(ps.getDay7A());
				}
				cell = row.getCell(33);
				if (!"0".equals(ps.getDay7B())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellValue(Integer.valueOf(ps.getDay7B()));
					lineSum[19] += Integer.parseInt(ps.getDay7B());
					allSum[19] += Integer.parseInt(ps.getDay7B());
				}
				cell = row.getCell(34);
				if (!"0".equals(ps.getDay7C())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
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
					cell.setCellValue(Integer.valueOf(ps.getDay8A()));
					lineSum[21] += Integer.parseInt(ps.getDay8A());
					allSum[21] += Integer.parseInt(ps.getDay8A());
				}
				cell = row.getCell(36);
				if (!"0".equals(ps.getDay8B())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellValue(Integer.valueOf(ps.getDay8B()));
					lineSum[22] += Integer.parseInt(ps.getDay8B());
					allSum[22] += Integer.parseInt(ps.getDay8B());
				}
				cell = row.getCell(37);
				if (!"0".equals(ps.getDay8C())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellValue(Integer.valueOf(ps.getDay8C()));
					lineSum[23] += Integer.parseInt(ps.getDay8C());
					allSum[23] += Integer.parseInt(ps.getDay8C());
				}
				cell = row.getCell(38);
				if (!"0".equals(ps.getDay9A())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellValue(Integer.valueOf(ps.getDay9A()));
					lineSum[24] += Integer.parseInt(ps.getDay9A());
					allSum[24] += Integer.parseInt(ps.getDay9A());
				}
				cell = row.getCell(39);
				if (!"0".equals(ps.getDay9B())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellValue(Integer.valueOf(ps.getDay9B()));
					lineSum[25] += Integer.parseInt(ps.getDay9B());
					allSum[25] += Integer.parseInt(ps.getDay9B());
				}
				cell = row.getCell(40);
				if (!"0".equals(ps.getDay9C())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellValue(Integer.valueOf(ps.getDay9C()));
					lineSum[26] += Integer.parseInt(ps.getDay9C());
					allSum[26] += Integer.parseInt(ps.getDay9C());
				}
				cell = row.getCell(41);
				if (!"0".equals(ps.getDay0A())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellValue(Integer.valueOf(ps.getDay0A()));
					lineSum[27] += Integer.parseInt(ps.getDay0A());
					allSum[27] += Integer.parseInt(ps.getDay0A());
				}
				cell = row.getCell(42);
				if (!"0".equals(ps.getDay0B())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellValue(Integer.valueOf(ps.getDay0B()));
					lineSum[28] += Integer.parseInt(ps.getDay0B());
					allSum[28] += Integer.parseInt(ps.getDay0B());
				}
				cell = row.getCell(43);
				if (!"0".equals(ps.getDay0C())) {
					cell.setCellStyle(getStyleForNormalNumRow(styleArray[3],
							font, normal));
					cell.setCellValue(Integer.valueOf(ps.getDay0C()));
					lineSum[29] += Integer.parseInt(ps.getDay0C());
					allSum[29] += Integer.parseInt(ps.getDay0C());
				}
			}
		}
		//最后一条线合计项
		row = sheet.createRow(rowCount++);
		for(int i=0;i<(17+daycount*3);i++){
			cell = row.createCell(i);
			cell.setCellStyle(getStyleForSumDataRow(styleArray[1]));
		}
		for(int i=0;i<daycount*3;i++){
			cell = row.getCell(14+i);
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(Integer.valueOf(lineSum[i]));
		}
		//所有合计项
		row = sheet.createRow(rowCount++);
		for(int i=0;i<(17+daycount*3);i++){
			cell = row.createCell(i);
			cell.setCellStyle(getStyleForSumDataRow(styleArray[1]));
		}
		cell=row.getCell(0);
		cell.setCellValue("合计");
		for(int i=0;i<daycount*3;i++){
			cell = row.getCell(14+i);
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
	 * 创建合计项数据行样式
	 * @param style 样式表
	 * @param font 字体
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
	
	/**
	 * 创建正常数据行样式
	 * @param style 样式表
	 * @param font 字体
	 * @param font 字体
	 * @return
	 */
	public static HSSFCellStyle getStyleForNormalDataRow(HSSFCellStyle style,HSSFFont font,boolean normal) {
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
//		if(normal){
//			font.setColor(HSSFColor.BLACK.index);
//		}else{
//			font.setColor(HSSFColor.RED.index);
//		}
		style.setFont(font);
		return style;
	}
	
	/**
	 * 创建日期数据行样式
	 * @param style 样式表
	 * @param font 字体
	 * @param font 字体
	 * @return
	 */
	public static HSSFCellStyle getStyleForDateDataRow(HSSFCellStyle style,HSSFFont font,HSSFDataFormat format,boolean normal) {
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
//		if(normal){
//			font.setColor(HSSFColor.BLACK.index);
//		}else{
//			font.setColor(HSSFColor.RED.index);
//		}
		style.setFont(font);
		style.setDataFormat(format.getFormat("m-d"));
		return style;
	}
	
	/**
	 * 创建正常数值行样式
	 * @param style 样式表
	 * @param font 字体
	 * @param font 字体
	 * @return
	 */
	public static HSSFCellStyle getStyleForNormalNumRow(HSSFCellStyle style,HSSFFont font,boolean normal) {
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
		font.setColor(HSSFColor.BLACK.index);
//		if(normal){
//			font.setColor(HSSFColor.BLACK.index);
//		}else{
//			font.setColor(HSSFColor.RED.index);
//		}
		style.setFont(font);
		return style;
	}
	
}
