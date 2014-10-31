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

/**
 * Servlet implementation class download
 */
@WebServlet("/download")
public class download extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public download() {
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
		
		 try{  
	          
	            response.setContentType("application/msexcel;");                
	            response.setHeader("Content-Disposition", new String(("attachment;filename="+"mbs.xls").getBytes("GB2312"), "UTF-8"));  
	              
	            File f = new File("D://mb/mbs.xls");   //D://mb/mbs.xls
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
		// TODO Auto-generated method stub
	}

}
