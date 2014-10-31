package com.wellsoft.pt.ldx.web.prodplan;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import com.wellsoft.pt.ldx.service.productionPlan.IProductionLineCapacityService;
import com.wellsoft.pt.ldx.util.DateUtils;

/**
 * 
 * Description: 生产计划平台标准产能管理
 *  
 * @author hes
 * @date 2014-8-4
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-8-4.1	heshi		2014-8-4		Create
 * </pre>
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/productionPlan/lineCapacity")
public class ProdLineCapacityController extends BaseController {

	@Autowired
	private IProductionLineCapacityService productionLineCapacityService;

	/**
	 * 
	 * 跳转至新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/add")
	public String add() throws Exception {
		return forward("/ldx/prodplan/lineCapacityAdd");
	}
	
	/**
	 * 
	 * 跳转至编辑页面
	 * 
	 * @param zxh 线号
	 * @param zrq 日期
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/modify")
	public String modify(@RequestParam(value = "zxh") String zxh, @RequestParam(value = "zrq") String zrq,Model model) throws Exception {
		List<String[]> list = productionLineCapacityService.findLineCapacityByInfo(zxh, zrq);
		String dwerks="",department="",clazz="",zsg="",ltxa1="",gamng1="",gamng2="",gamng3="",zxbbbxs="",zcxdm="";
		if(null!=list && list.size()>0){
			dwerks = list.get(0)[0];
			department = list.get(0)[1];
			clazz = list.get(0)[2];
			zsg = list.get(0)[4];
			ltxa1 = list.get(0)[6];
			gamng1 = list.get(0)[7];
			gamng2 = list.get(0)[8];
			gamng3 = list.get(0)[9];
			zxbbbxs = list.get(0)[10];
			zcxdm = list.get(0)[11];
		}
		model.addAttribute("zxh",zxh);
		model.addAttribute("zrq",zrq);
		model.addAttribute("dwerks",dwerks);
		model.addAttribute("department",department);
		model.addAttribute("clazz",clazz);
		model.addAttribute("zsg",zsg);
		model.addAttribute("ltxa1",ltxa1);
		model.addAttribute("dwerks",dwerks);
		model.addAttribute("gamng1",gamng1);
		model.addAttribute("gamng2",gamng2);
		model.addAttribute("gamng3",gamng3);
		model.addAttribute("zxbbbxs",zxbbbxs);
		model.addAttribute("zcxdm",zcxdm);
		return forward("/ldx/prodplan/lineCapacityModify");
	}
	
	/**
	 * 
	 * 跳转至复制页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/copy")
	public String copy() throws Exception {
		return forward("/ldx/prodplan/lineCapacityCopy");
	}
	
	/**
	 * 
	 * 跳转至复制页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/adjust")
	public String adjust() throws Exception {
		return forward("/ldx/prodplan/lineCapacityAdjust");
	}
	
	/**
	 * 
	 * 跳转至批量删除页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/batchDel")
	public String batchDel() throws Exception {
		return forward("/ldx/prodplan/lineCapacityBatchDel");
	}
	
	/**
	 * 上传模板下载
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/download")
	public void downLoadMpsTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		OutputStream os = response.getOutputStream();
		String fileName = "lineCapacityTemplate.xls";
		response.reset();
		response.setHeader("Content-disposition","attachment; filename=" + fileName);
		response.setContentType("application/msexcel");
		InputStream in = this.getClass().getResourceAsStream("lineCapacityTemplate.xls");
		HSSFWorkbook workbook = new HSSFWorkbook(in);
		workbook.write(os);
		response.flushBuffer();
		os.close();
	}
	
	/**
	 * 
	 * 上传完工模板
	 * 
	 * @param file
	 * @param model
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public void uploadWgTemplate(@RequestParam(value = "uploadFile", required = false) MultipartFile file, HttpServletResponse response){
		response.setContentType(MediaType.TEXT_HTML_VALUE);
		try {
			InputStream is = file.getInputStream();
			HSSFWorkbook hwk = new HSSFWorkbook(is);
			HSSFSheet sh = hwk.getSheetAt(0);
		    HSSFRow firstRow = sh.getRow(0);
		    String[] headStr = new String[]{ "工厂", "部门", "课别", "线号", "生管", "日期", "工序别", "标准产能A", "标准产能B", "标准产能C", "线别报表显示", "产线ID"};
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
			String[][] rowarr = new String[rows - 1][12];
			int rowno = 0;
			try{
				for (rowno = 0; rowno < rows - 1; rowno++) {
					HSSFRow row = sh.getRow(rowno + 1);
					int cols = row.getLastCellNum() - row.getFirstCellNum();
					for (int j = 0; j < cols; j++) {
						HSSFCell cell = row.getCell(j);
						String col = "";
						if(j==5){
							col = getCellValue(cell, true, true);
						}else{
							col = getCellValue(cell, true, false);
						}
						rowarr[rowno][j] = col;
					}
					Double.parseDouble(rowarr[rowno][7]);
					Double.parseDouble(rowarr[rowno][8]);
					Double.parseDouble(rowarr[rowno][9]);
				}
			}catch (Exception e){
				response.getWriter().write("上传失败!上传文件第"+(rowno+1)+"行数据格式错误!");
				response.getWriter().flush();
				response.getWriter().close();
				return;
			}
			
			//完工上传文件临时列表
			for (int k = 0; k < rowarr.length; k++) {
				String dwerks =rowarr[k][0].trim();
				String department =rowarr[k][1].trim();
				String clazz =rowarr[k][2].trim();
				String zxh =rowarr[k][3].trim();
				String zsg =rowarr[k][4].trim();
				String zrq =rowarr[k][5].trim();
				String ltxa1 =rowarr[k][6].trim();
				String gamng1 =rowarr[k][7].trim();
				String gamng2 =rowarr[k][8].trim();
				String gamng3 =rowarr[k][9].trim();
				String zxbbbxs =rowarr[k][10].trim();
				String zcxdm =rowarr[k][11].trim();
				productionLineCapacityService.saveLineCapacity(dwerks,department,clazz,zxh,zsg,zrq,ltxa1,gamng1,gamng2,gamng3,zxbbbxs,zcxdm);
			}
			response.getWriter().write("success");
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
}
