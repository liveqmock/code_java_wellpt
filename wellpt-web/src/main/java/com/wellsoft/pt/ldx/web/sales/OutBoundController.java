package com.wellsoft.pt.ldx.web.sales;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wellsoft.pt.basicdata.sap.service.SAPRfcService;
import com.wellsoft.pt.basicdata.sap.util.SAPRfcJson;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.file.entity.FmFile;
import com.wellsoft.pt.ldx.model.sales.OutBoundManage;
import com.wellsoft.pt.ldx.service.sales.OutBoundService;

@Controller
@Scope("prototype")
@RequestMapping("/sales")
public class OutBoundController extends BaseController {

	@Autowired
	private SAPRfcService saprfcservice;

	@Autowired
	private OutBoundService outBoundService;

	private String totalMoney = "0.00", totalTax = "0.00", total = "0.00";
	private List<OutBoundManage> outBounds = new ArrayList<OutBoundManage>();
	private OutBoundManage outBoundObject = new OutBoundManage();

	@RequestMapping("/preSearchOutBound")
	public String preAdd(Model model) {
		return forward("/ldx/sales/preSearchOutBound");
	}

	@RequestMapping("/searchOutBound")
	public String search(@RequestParam(value = "wxfhdh") String wxfhdh,
			Model model) {
		setData(wxfhdh);
		model.addAttribute("object", outBoundObject);
		model.addAttribute("totalMoney", totalMoney);
		model.addAttribute("totalTax", totalTax);
		model.addAttribute("total", total);
		model.addAttribute("wxfhdh", wxfhdh);
		model.addAttribute("objects", outBounds);
		return forward("/ldx/sales/searchOutBound");
	}

	@RequestMapping("/export")
	public String export(@RequestParam(value = "wxfhdh") String wxfhdh,
			HttpServletResponse response, Model model) throws Exception {
		setData(wxfhdh);
		InputStream is = this.getClass().getResourceAsStream("宜家内销开票导出模板.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(is);
		XSSFSheet sheet1 = wb.getSheetAt(0);
		sheet1.getRow(0).getCell(1).setCellValue(wxfhdh);
		sheet1.getRow(2).getCell(2).setCellValue(outBoundObject.getName1());
		sheet1.getRow(3).getCell(2).setCellValue(outBoundObject.getRemark());
		sheet1.getRow(4).getCell(2).setCellValue(outBoundObject.getZadrc());
		sheet1.getRow(5).getCell(2).setCellValue(outBoundObject.getZbank());
		sheet1.getRow(7).getCell(6).setCellValue(totalMoney);
		sheet1.getRow(7).getCell(8).setCellValue(totalTax);
		sheet1.getRow(8).getCell(1).setCellValue(total);
		sheet1.getRow(10).getCell(7).setCellValue(outBoundObject.getXabln());
		sheet1.getRow(11).getCell(7).setCellValue(outBoundObject.getZrnum());
		int i = 7;
		for (OutBoundManage ob : outBounds) {
			sheet1.shiftRows(i, sheet1.getLastRowNum(), 1, true, false);
			XSSFRow sourceRow = null;
			XSSFRow targetRow = null;
			XSSFCell sourceCell = null;
			XSSFCell targetCell = null;
			sourceRow = sheet1.getRow(i - 1);
			targetRow = sheet1.createRow(i);
			targetRow.setHeight(sourceRow.getHeight());
			for (short m = sourceRow.getFirstCellNum(); m < sourceRow
					.getLastCellNum(); m++) {
				sourceCell = sourceRow.getCell(m);
				targetCell = targetRow.createCell(m);
				targetCell.setCellStyle(sourceCell.getCellStyle());
				targetCell.setCellType(sourceCell.getCellType());
			}
			sheet1.getRow(i).getCell(0).setCellValue(ob.getZptxt());
			sheet1.getRow(i).getCell(1).setCellValue(ob.getKdmat());
			sheet1.getRow(i).getCell(2).setCellValue("套");
			sheet1.getRow(i).getCell(3).setCellValue(ob.getRfmng());
			sheet1.getRow(i).getCell(4).setCellValue(ob.getNetpr());
			sheet1.getRow(i).getCell(6).setCellValue(ob.getMoney());
			sheet1.getRow(i).getCell(7).setCellValue("17%");
			sheet1.getRow(i).getCell(8).setCellValue(ob.getTax());
			i++;
		}
		response.setContentType("application/x-msdownload;");
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String("开票数据.xlsx".getBytes("utf-8"), "ISO8859-1"));
		BufferedOutputStream outputStream = new BufferedOutputStream(
				response.getOutputStream());
		wb.write(outputStream);
		outputStream.close();
		model.addAttribute("wxfhdh", wxfhdh);
		return forward("/ldx/sales/searchOutBound");
	}

	private void setData(String wxfhdh) {
		DecimalFormat fnum = new DecimalFormat("##0.00");
		SAPRfcJson util = new SAPRfcJson("");
		util.setField("PI_VBELN", wxfhdh);
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "ZSDI0015",
				util.getRfcJson(), 1, -1, null);
		if ("E".equals(rfcjson.getStructure("PO_RETURN").get("TYPE"))) {
			throw new RuntimeException(rfcjson.getStructure("PO_RETURN")
					.get("MESSAGE").toString());
		}
		JSONObject o = rfcjson.getRecord("PT_OUT");
		JSONArray jObs = o.getJSONArray("row");
		for (int i = 0; i < jObs.size(); i++) {
			JSONObject object = (JSONObject) jObs.get(i);
			OutBoundManage outBound = new OutBoundManage();
			outBound.setName1(object.get("NAME1").toString());
			outBound.setRemark(object.get("REMARK").toString());
			outBound.setZadrc(object.get("ZADRC").toString());
			outBound.setZbank(object.get("ZBANK").toString());
			outBound.setZptxt(object.get("ZPTXT").toString());
			outBound.setKdmat(object.get("KDMAT").toString());
			outBound.setRfmng(object.get("RFMNG").toString()
					.replace(".000", ""));
			outBound.setNetpr(object.get("NETPR").toString());
			outBound.setXabln(object.get("XABLN").toString());
			outBound.setZrnum(object.get("ZRNUM").toString());
			if (StringUtils.isNotEmpty(outBound.getKdmat())) {
				List<FmFile> files = outBoundService.findFmFile(outBound
						.getKdmat().trim());
				if (null != files && files.size() > 0) {
					FmFile file = files.get(0);
					outBound.setRfmng(String.valueOf(Integer.valueOf(outBound
							.getRfmng())
							/ Integer.valueOf(file.getReservedText2())));
					outBound.setNetpr(file.getReservedText3());
				} else {
					outBound.setRfmng("0");
					outBound.setNetpr("0.00");
				}
			}
			outBound.setMoney(String.valueOf(fnum.format(Double
					.valueOf(outBound.getRfmng())
					* Double.valueOf(outBound.getNetpr()))));
			outBound.setTax(String.valueOf(fnum.format(Double.valueOf(outBound
					.getMoney()) * 0.17)));
			totalMoney = fnum.format(Double.valueOf(totalMoney)
					+ Double.valueOf(outBound.getRfmng())
					* Double.valueOf(outBound.getNetpr()));
			totalTax = fnum.format(Double.valueOf(totalTax)
					+ Double.valueOf(outBound.getTax()));
			outBounds.add(outBound);
		}
		Double f = Double.valueOf(totalMoney) + Double.valueOf(totalTax);
		total = fnum.format(f);
		if (outBounds.size() > 0) {
			outBoundObject = outBounds.get(0);
		} else {
			throw new RuntimeException("没有数据");
		}
	}

}
