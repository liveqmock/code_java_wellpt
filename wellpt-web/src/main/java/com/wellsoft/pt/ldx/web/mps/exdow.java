package com.wellsoft.pt.ldx.web.mps;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * Servlet implementation class exdowm
 */
@WebServlet("/exdown")
public class exdow extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	WritableWorkbook book;
	
	
    public exdow() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
	//生成EXCEL表格	
		int x=0;
		int y=0;
	 	
    	String tjshuju=request.getParameter("tjshuju");
    	String pd=request.getParameter("sj");
    	
    	String ts1[]=tjshuju.split("##");
    	
    	System.out.println(ts1[1]);
    	
    	String ts2[]=ts1[1].split("@@");
    	
    	
    	
    	String [][]result=new String[ts1.length][ts2.length];
    	
    	
    	for(int k=0;k<result.length;k++){
    		
    		String tss2[]=ts1[k].split("@@");
    		
    		for(int q=0;q<tss2.length;q++){
    			
    			result[k][q]=tss2[q];
    			//System.out.println(result[k][q]);

    		}
    	}
    	
    	//
    	
    //
 
    // EXCEL表格导出	
    /*	 common com=new common();
         com.Filter(result, ts1.length, ts2.length);	
    */ 
    
    	 String filePath="em.xls";
    	 File file = new File(filePath); 
    	 Label tempLabel = null;
		book = Workbook.createWorkbook(new File(filePath));
		WritableSheet sheet = book.createSheet(pd, 0);//设置EXCEL表格名称
		sheet.setColumnView(1, 5);

		for(int i=0;i<result.length;i++)
			for(int j=0;j<result[i].length;j++){
				
				  WritableCellFormat tempCellFormat = null;
				  tempCellFormat = getBodyCellStyle();
				   tempLabel = new Label(j, i, result[i][j],
					       tempCellFormat);
				  try {
					sheet.addCell(tempLabel);
				} catch (RowsExceededException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (WriteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
			
		}
		
		
		book.write();
		   try {
			book.close();
		} catch (WriteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//下载EXCEL 表格 
		
		 try{  
	            response.setContentType("application/msexcel;");                
	            response.setHeader("Content-Disposition", new String(("attachment;filename="+"em.xls").getBytes("GB2312"), "UTF-8"));  
	              
	            File f = new File("em.xls");   //D://mb/mbs.xls
	            //File f = new File(targetFolder.getPath()+File.separator+fName);  
	            FileInputStream in = new FileInputStream(f);  
	            byte b[] = new byte[1024];  
	            int i = 0;  
	            ServletOutputStream out = response.getOutputStream();  
	            while((i=in.read(b))!=  -1){  
	                out.write(b, 0, i);  
	            }  
	            out.flush();  
	            out.close();  
	            in.close();  
	        }catch(Exception e){  
	            e.printStackTrace();  
	        }  

	}
	public WritableCellFormat getBodyCellStyle() {
		  WritableFont font = new WritableFont(WritableFont.createFont("宋体"), 10,
		    WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);
		  WritableCellFormat bodyFormat = new WritableCellFormat(font);
		  try {
		   // 设置单元格背景色：表体为白色
		   bodyFormat.setBackground(Colour.WHITE);
		   // 设置表头表格边框样式
		   // 整个表格线为细线、黑色
		   bodyFormat
		     .setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		  } catch (WriteException e) {
		   System.out.println("表体单元格样式设置失败！");
		  }
		  return bodyFormat;
		 }


}
