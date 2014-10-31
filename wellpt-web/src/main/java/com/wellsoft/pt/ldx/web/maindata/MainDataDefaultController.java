package com.wellsoft.pt.ldx.web.maindata;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.service.maindata.DefaultDataService;
import com.wellsoft.pt.ldx.util.DateUtils;

@Controller
@Scope("prototype")
@RequestMapping("/maindata")
public class MainDataDefaultController extends BaseController {
	@Autowired
	private DefaultDataService defaultDataService;

	@RequestMapping("/preUploadFile")
	public String preAdd(@RequestParam(value = "module") String module,
			Model model) {
		model.addAttribute("module", module);
		return forward("/maindata/uploadFile");
	}

	@RequestMapping(value = "/uploadWg", method = RequestMethod.POST)
	@ResponseBody
	public void uploadWgTemplate(
			@RequestParam(value = "uploadWgFile", required = false) MultipartFile file,
			@RequestParam(value = "module") String module,
			HttpServletResponse response) {
		response.setContentType(MediaType.TEXT_HTML_VALUE);
		try {
			InputStream is = file.getInputStream();
			Workbook hwk = null;
			if (file.getOriginalFilename().toUpperCase().endsWith("XLSX")) {
				hwk = new XSSFWorkbook(is);
			} else {
				hwk = new HSSFWorkbook(is);
			}
			Sheet sh = hwk.getSheetAt(0);
			Row firstRow = sh.getRow(0);
			if ("QM".equals(module)) {
				String[] headStr = new String[] { "物料组", "物料组名称", "工厂", "产品组",
						"视图扩充", "批次管理", "检验类型01", "检验类型03", "检验类型10", "检验类型89",
						"检验类型LDX01", "检验类型LDX02", "检验类型LDX03", "检验类型LDX04",
						"检验类型LDX05", "检验类型LDX06", "平均检验期" };
				for (int i = 0; i < headStr.length; i++) {
					Cell cell = firstRow.getCell(i);
					String head = getCellValue(cell, false, false);
					if (!headStr[i].equals(head)) {
						response.getWriter().write("上传文件格式错误!");
						response.getWriter().flush();
						response.getWriter().close();
						return;
					}
				}
				defaultDataService.importQm(sh);
			}
			if ("PLAN".equals(module)) {
				String[] headStr = new String[] { "物料组", "物料组名称", "工厂", "产品组",
						"价格维护", "计划价格1", "舍入值", "计划价格1日期", "价格单位" };
				for (int i = 0; i < headStr.length; i++) {
					Cell cell = firstRow.getCell(i);
					String head = getCellValue(cell, false, false);
					if (!headStr[i].equals(head)) {
						response.getWriter().write("上传文件格式错误!");
						response.getWriter().flush();
						response.getWriter().close();
						return;
					}
				}
				defaultDataService.importPlan(sh);
			}
			if ("PP".equals(module)) {
				String[] headStr = new String[] { "物料组", "物料组名称", "工厂", "产品组",
						"优先级", "视图扩充", "MRP类型", "MRP控制者", "批量大小", "MRP组",
						"采购类型", "特殊采购类", "反冲", "生产仓储地点", "外部采购仓储地点", "自制生产时间",
						"收货处理时间", "计划边际码", "安全库存", "策略组", "消耗模式", "向前消耗期间",
						"逆向消耗期间", "可用性检查", "部件废品%", "独立/集中", "生产计划参数文件",
						"生产管理员", "不足交货允差", "过度交货允差", "最大批量大小" };
				for (int i = 0; i < headStr.length; i++) {
					Cell cell = firstRow.getCell(i);
					String head = getCellValue(cell, false, false);
					if (!headStr[i].equals(head)) {
						response.getWriter().write("上传文件格式错误!");
						response.getWriter().flush();
						response.getWriter().close();
						return;
					}
				}
				defaultDataService.importPp(sh);
			}
			if ("FACTORY".equals(module)) {
				String[] headStr = new String[] { "物料组", "物料组名称", "产品组", "长描述",
						"备注", "工厂", "生产仓储地点", "外部采购仓储地点", "优先级" };
				for (int i = 0; i < headStr.length; i++) {
					Cell cell = firstRow.getCell(i);
					String head = getCellValue(cell, false, false);
					if (!headStr[i].equals(head)) {
						response.getWriter().write("上传文件格式错误!");
						response.getWriter().flush();
						response.getWriter().close();
						return;
					}
				}
				defaultDataService.importFactory(sh);
			}
			if ("FICO".equals(module)) {
				String[] headStr = new String[] { "物料组", "物料组名称", "工厂", "价格控制",
						"评估类", "价格确定", "无成本估算", "原始组", "科目设置组" };
				for (int i = 0; i < headStr.length; i++) {
					Cell cell = firstRow.getCell(i);
					String head = getCellValue(cell, false, false);
					if (!headStr[i].equals(head)) {
						response.getWriter().write("上传文件格式错误!");
						response.getWriter().flush();
						response.getWriter().close();
						return;
					}
				}
				defaultDataService.importFico(sh);
			}
			if ("SD".equals(module)) {
				String[] headStr = new String[] { "物料组", "物料组名称", "工厂", "短描述",
						"视图扩充", "销售组织", "分销渠道"};
				for (int i = 0; i < headStr.length; i++) {
					Cell cell = firstRow.getCell(i);
					String head = getCellValue(cell, false, false);
					if (!headStr[i].equals(head)) {
						response.getWriter().write("上传文件格式错误!");
						response.getWriter().flush();
						response.getWriter().close();
						return;
					}
				}
				defaultDataService.importSd(sh);
			}
			if ("MM".equals(module)) {
				String[] headStr = new String[] { "物料组", "物料组名称", "工厂", "视图扩充",
						"采购组", "货源清单", "配额管理", "采购订单文本", "计划交货时间"};
				for (int i = 0; i < headStr.length; i++) {
					Cell cell = firstRow.getCell(i);
					String head = getCellValue(cell, false, false);
					if (!headStr[i].equals(head)) {
						response.getWriter().write("上传文件格式错误!");
						response.getWriter().flush();
						response.getWriter().close();
						return;
					}
				}
				defaultDataService.importMm(sh);
			}
			if ("BAOGUAN".equals(module)) {
				String[] headStr = new String[] { "物料组", "物料组名称", "短描述", "视图扩充",
						"中文报关名", "英文报关名"};
				for (int i = 0; i < headStr.length; i++) {
					Cell cell = firstRow.getCell(i);
					String head = getCellValue(cell, false, false);
					if (!headStr[i].equals(head)) {
						response.getWriter().write("上传文件格式错误!");
						response.getWriter().flush();
						response.getWriter().close();
						return;
					}
				}
				defaultDataService.importBaoguan(sh);
			}
			response.getWriter().write("success");
			response.getWriter().flush();
			response.getWriter().close();
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getCellValue(Cell cell, boolean isUpperCase, boolean isdate) {
		String returnValue = "";

		if (null != cell) {

			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC: // 数字或日期
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
			case Cell.CELL_TYPE_STRING: // 字符串
				if (isUpperCase)
					returnValue = cell.getStringCellValue() == null ? "" : cell
							.getStringCellValue().trim().toUpperCase();
				else
					returnValue = cell.getStringCellValue() == null ? "" : cell
							.getStringCellValue().trim();
				break;
			case Cell.CELL_TYPE_BOOLEAN: // 布尔
				Boolean booleanValue = cell.getBooleanCellValue();
				returnValue = booleanValue.toString();
				break;
			case Cell.CELL_TYPE_BLANK: // 空值
				returnValue = "";
				break;
			case Cell.CELL_TYPE_FORMULA: // 公式
				returnValue = cell.getCellFormula();
				break;
			case Cell.CELL_TYPE_ERROR: // 故障
				returnValue = "";
				break;
			default:
				returnValue = "";
				break;
			}
		}
		return returnValue;
	}

}
