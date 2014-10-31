package com.wellsoft.pt.ldx.servlet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.EAN13TextPainter;
import org.jbarcode.paint.WidthCodedPainter;

import com.wellsoft.pt.ldx.util.StringUtils;

/**
 * 
 * Description: 条形码生成servlet
 *  
 * @author HeShi
 * @date 2014-8-29
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-8-29.1	HeShi		2014-8-29		Create
 * </pre>
 *
 */
public class BarcodeGenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取编码
		String code = request.getParameter("code");
		if (StringUtils.isBlank(code)) {
			code = "0123456789";
		}
		String height = request.getParameter("height");
		String width = request.getParameter("width");
		String xd = request.getParameter("xd");
		Double hi = 15D;
		Double wi = 8D;
		Double xw = 0.5D;
		if(StringUtils.isNotBlank(height)){
			hi = Double.valueOf(height);
		}
		if(StringUtils.isNotBlank(width)){
			wi = Double.valueOf(width);
		}
		if(StringUtils.isNotBlank(xd)){
			xw = Double.valueOf(xd);
		}
		ByteArrayOutputStream bout = new ByteArrayOutputStream(4096);
		try {
			//编码器
			JBarcode localJBarcode = new JBarcode(Code128Encoder.getInstance(), WidthCodedPainter.getInstance(),
					EAN13TextPainter.getInstance());
			localJBarcode.setEncoder(Code128Encoder.getInstance());
			localJBarcode.setPainter(WidthCodedPainter.getInstance());
			localJBarcode.setTextPainter(BaseLineTextPainter.getInstance());
			localJBarcode.setShowCheckDigit(false);
			localJBarcode.setBarHeight(hi);//条码高度
			localJBarcode.setWideRatio(wi);//宽度
			localJBarcode.setXDimension(xw);//条码间距
			BufferedImage localBufferedImage = localJBarcode.createBarcode(code);
			ImageIO.write(localBufferedImage,"jpeg",bout);
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			response.setContentType("image/jpeg");
			response.setContentLength(bout.size());
			response.getOutputStream().write(bout.toByteArray());
			response.getOutputStream().flush();
		}
	}
}
