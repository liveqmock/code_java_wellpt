package com.wellsoft.pt.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

public class ExcelUtilFactory {
	private static ExcelUtilFactory instance = null;

	private static HttpServletRequest excelRequest = null;

	private static HttpServletResponse excelResponse = null;

	public static ExcelUtilFactory getInstance(HttpServletRequest request, HttpServletResponse response) {
		if (instance == null) {
			instance = new ExcelUtilFactory();
		}
		excelRequest = request;
		excelResponse = response;
		return instance;
	}

	public static void outputExcel(String excelName, List list, String[] firstRowValue) {
		ExcelWorkBook work = new ExcelWorkBook();
		work.setWorkbookName(excelName);
		ExcelSheetRow sheetRow = new ExcelSheetRow();
		ExcelSheetCell sheetCell = new ExcelSheetCell();
		ExcelCellStyleUtils util = new ExcelCellStyleUtils(work);
		sheetCell.createCurrRowTitle(sheetRow, work, firstRowValue);
		sheetCell.createCurrRowRecord(sheetRow, work, list);
		String realPath = getExcelRealPath(excelName);
		// String realPath = "e:/temp/testRealPath_2.xls";
		work.writerFileStream(realPath);
		downloadFile(realPath);
	}

	public static void outputToRealPath(String sheetName, List list, String[] firstRowValue, String realPath) {
		ExcelWorkBook work = new ExcelWorkBook();
		work.setWorkbookName(sheetName);
		ExcelSheetRow sheetRow = new ExcelSheetRow();
		ExcelSheetCell sheetCell = new ExcelSheetCell();

		sheetCell.createCurrRowTitle(sheetRow, work, firstRowValue);
		sheetCell.createCurrRowRecord(sheetRow, work, list);

		work.writerFileStream(realPath);
	}

	public static void outputToDirectory(String sheetName, List list, String[] titleValue, String destDir,
			String excelName) {
		ExcelWorkBook work = new ExcelWorkBook();
		work.setWorkbookName(sheetName);
		ExcelSheetRow sheetRow = new ExcelSheetRow();
		ExcelSheetCell sheetCell = new ExcelSheetCell();

		sheetCell.createCurrRowTitle(sheetRow, work, titleValue);
		sheetCell.createCurrRowRecord(sheetRow, work, list);

		work.write(destDir, excelName);
	}

	private static String getExcelRealPath(String excelName) {
		String realPath = excelRequest.getRealPath("/UploadFile");
		File excelFile = new File(realPath);
		if (!excelFile.exists()) {
			excelFile.mkdirs();
		}
		excelName = realPath + "\\" + excelName + ".xls";
		return excelName;
	}

	private static void downloadFile(String strfileName) {
		try {
			// 获得ServletContext对象
			if (excelFileNotFund(strfileName)) {
				throw new IllegalArgumentException("File=[" + strfileName + "] not fund file path");
			}
			// 取得文件的绝对路径
			File excelFile = getExcelDownloadPath(strfileName);
			putResponseStream(strfileName, excelFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static File getExcelDownloadPath(String excelName) {
		// String realPath = excelRequest.getRealPath("/UploadFile");
		// excelName = realPath+ "\\" + excelName;
		// excelName = replaceRNAll(excelName);
		File excelFile = new File(excelName);
		return excelFile;
	}

	// 用传入参数的判断
	private static boolean excelFileNotFund(String strfileName) {
		return strfileName == null || strfileName.equals("");
	}

	/**
	 * 
	 * @param strfileName :
	 *            文件名称
	 * @param excelName :
	 *            文件的相对路径或绝对路径
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void putResponseStream(String strfileName, File excelName) throws UnsupportedEncodingException,
			FileNotFoundException, IOException {
		strfileName = URLEncoder.encode(strfileName, "UTF-8");
		excelResponse.setHeader("Content-disposition", "attachment; filename=" + strfileName);
		excelResponse.setContentLength((int) excelName.length());
		excelResponse.setContentType("application/x-download");
		byte[] buffer = new byte[1024];
		int i = 0;
		FileInputStream fis = new FileInputStream(excelName);
		while ((i = fis.read(buffer)) > 0) {
			JspWriter out = null;
			excelResponse.getOutputStream().write(buffer, 0, i);
		}
		fis.close();
	}

	public static void main(String[] args) {
		long beginTime = System.currentTimeMillis();
		System.out.println("开始时间:" + beginTime / 1000);
		List beanList = new ArrayList();
		String[] excelTitle = new String[10];
		excelTitle[0] = "编号";
		excelTitle[1] = "基金名称";
		excelTitle[2] = "单位净值";
		excelTitle[3] = "日增长率";
		excelTitle[4] = "累积净值";
		excelTitle[5] = "编号";
		excelTitle[6] = "基金名称";
		excelTitle[7] = "单位净值";
		excelTitle[8] = "日增长率";
		excelTitle[9] = "累积净值";
		String[] beanArr = new String[10];
		for (int i = 0; i < 100; i++) {
			beanArr[0] = String.valueOf(i + 1);
			beanArr[1] = "基金A" + i;
			beanArr[2] = "1.0427";
			beanArr[3] = "-2.7514%";
			beanArr[4] = "1.1558";
			beanArr[5] = String.valueOf(i + 1);
			beanArr[6] = "基金A" + i;
			beanArr[7] = "1.0427";
			beanArr[8] = "-2.7514%";
			beanArr[9] = "1.1558";
			beanList.add(beanArr);
		}
		outputToDirectory("今天测试_factory", beanList, excelTitle, "c:/", "test.xls");
		long endTime = System.currentTimeMillis();
		System.out.println("测试55000,总计" + (endTime - beginTime) / 1000 + "秒,用时");
	}
}
