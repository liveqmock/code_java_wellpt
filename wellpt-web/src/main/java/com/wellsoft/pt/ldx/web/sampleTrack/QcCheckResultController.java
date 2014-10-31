package com.wellsoft.pt.ldx.web.sampleTrack;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.model.sampleTrack.SampleTrack;
import com.wellsoft.pt.ldx.service.sampleTrack.SampleTrackService;
import com.wellsoft.pt.ldx.util.StringUtils;

@Controller
@Scope("prototype")
@RequestMapping("/qcCheckResult")
public class QcCheckResultController extends BaseController{

	@Autowired
	private SampleTrackService sampleTrackService;
	
	@RequestMapping("/show")
	public ModelAndView showAndUpdate(@RequestParam(value = "lineItemId")String lineItemId){
		ModelAndView orderContext = new ModelAndView();
		orderContext.setViewName("/sampleTrack/qcCheckUpdate");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SampleTrack sampleTrack = new SampleTrack();
		try {
			sampleTrack = sampleTrackService.findEntityById(lineItemId,"品保检验结果");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		orderContext.addObject("lineItemId", lineItemId);
		orderContext.addObject("qcCheckDate", null!=sampleTrack.getQccheckdate()?formatter.format(sampleTrack.getQccheckdate()):"");
		orderContext.addObject("qcFinishDate", null!=sampleTrack.getQcfinishdate()?formatter.format(sampleTrack.getQcfinishdate()):"");
		orderContext.addObject("qcCheckResult", sampleTrack.getQccheckresult());
		orderContext.addObject("qcSecondResult", sampleTrack.getQcsecondresult());
		orderContext.addObject("qcCheckItem", sampleTrack.getQccheckitem());
		orderContext.addObject("qcCheckMemo", sampleTrack.getQccheckmemo());
		orderContext.addObject("qcExceptCause", sampleTrack.getQcexceptcause());
		return orderContext;
	}
	/**
	 * 样品费用结算导出
	 * @param groupCondition
	 * @param year
	 * @param sampleCharge1
	 * @param productMode1
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/sampleFeeSettle/export")
	public void exportSampleFeeSettle(@RequestParam(value = "groupCondition")String groupCondition,@RequestParam(value = "year")String year
			,@RequestParam(value = "sampleCharge1")String sampleCharge1,@RequestParam(value = "productMode1")String productMode1,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		OutputStream os = response.getOutputStream();
		response.reset();
		String fname = new SimpleDateFormat("yyyyMMdd_HHmmssSSS")
				.format(new Date()) + "_sampleFeeSettle.xls";
		response.setHeader("Content-disposition",
				"attachment; filename=" + fname);
		response.setContentType("application/msexcel");

		String path = request.getSession().getServletContext()
				.getRealPath("/");
		String pathname = "";
		if("部门名称".equals(groupCondition)){		
			 pathname = path+"\\pt\\sampleTrack\\default_sampleFeeSettle1.xls";
		}else if("客户代码".equals(groupCondition)){
			 pathname = path+"\\pt\\sampleTrack\\default_sampleFeeSettle2.xls";
		}	
		

		File inputFile = new File(pathname);
		
//		doSearch();
//		sampleTracks = page.getResult();
		List<Object[]> resultList = sampleTrackService.findSampleFeeSettleListByPage(groupCondition, year, sampleCharge1, productMode1);//样品费用结算列表

		
		FileInputStream in = new FileInputStream(inputFile);

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

		HSSFCellStyle style = null;
		for (int i = 0; i < resultList.size(); i++) {

			int j = 0;
			int k = 0;
			Object[] model = resultList.get(i);
			row = sheet.createRow(2 + i);		
			//部门名称or客户代码
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(String.valueOf(model[0]));				
			//1月样品成本
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model[1].toString()));				
			//1月PI金额
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model[2].toString()));						
			//2月样品成本
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model[3].toString()));				
			//2月PI金额
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model[4].toString()));				
			//3月样品成本
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model[5].toString()));				
			//3月PI金额
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model[6].toString()));			
			//4月样品成本
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model[7].toString()));			
			//4月PI金额
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model[8].toString()));			
			//5月样品成本
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model[9].toString()));			
			//5月PI金额
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model[10].toString()));			
			//6月样品成本
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model[11].toString()));				
			//6月PI金额
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model[12].toString()));			
			//7月样品成本
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model[13].toString()));			
			//7月PI金额
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model[14].toString()));			
			//8月样品成本
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model[15].toString()));			
			//8月PI金额
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model[16].toString()));			
			//9月样品成本
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model[17].toString()));			
			//9月PI金额
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model[18].toString()));				
			//10月样品成本
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model[19].toString()));			
			//10月PI金额
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model[20].toString()));			
			//11月样品成本
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model[21].toString()));				
			//11月PI金额
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model[22].toString()));			
			//12月样品成本
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model[23].toString()));				
			//12月PI金额
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model[24].toString()));
		}

		workbook.write(os);
		response.flushBuffer();
		os.close();
	
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
}
