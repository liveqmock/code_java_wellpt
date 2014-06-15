/*
 * @(#)2014-6-6 V1.0
 * 
 * Copyright 2014 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * Description: 如何描述该类
 *  
 * @author wubin
 * @date 2014-6-6
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-6-6.1	wubin		2014-6-6		Create
 * </pre>
 *
 */
public class ExcelUtils {

	public static File generateExcelFile(List<String[]> dataList, String[] titleArray, String fileName) {

		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("Sheet1");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow(0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell = row.createCell(0);
		for (int i = 0; i < titleArray.length; i++) {
			cell.setCellValue(titleArray[i]);
			cell.setCellStyle(style);
			cell = row.createCell(i + 1);
		}

		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，

		for (int i = 0; i < dataList.size(); i++) {
			row = sheet.createRow(i + 1);
			String[] dataArray = dataList.get(i);
			//			Map<String, String> map = dataList.get(i);
			// 第四步，创建单元格，并设置值
			for (int j = 0; j < dataArray.length; j++) {
				row.createCell(j).setCellValue(dataArray[j]);
			}
			//			for (String key : map.keySet()) {
			//				row.createCell((short) i).setCellValue(map.get(key));
			//			}
		}
		// 第六步，将文件存到指定位置
		try {
			FileOutputStream fout = new FileOutputStream("E:/" + fileName + ".xls");
			wb.write(fout);
			fout.close();
			return new File("E:/" + fileName + ".xls");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
