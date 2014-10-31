package com.wellsoft.pt.ldx.web.mps;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.fr.general.DateUtils;
import com.fr.third.org.apache.poi.hssf.record.formula.functions.Date;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.model.mps.Hisview;
import com.wellsoft.pt.ldx.model.mps.Zdview;
import com.wellsoft.pt.ldx.service.mps.ZdService;
import com.wellsoft.pt.ldx.service.mps.HisService;
import java.io.FileInputStream;
import java.io.FileOutputStream;  
import java.io.InputStream;  
import java.io.PrintWriter;  
import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
@Controller
@Scope("prototype")
@RequestMapping("/mps")
public class UpExcelController  extends BaseController {
	
	@Autowired
	private ZdService zdService;
	@Autowired
	private HisService hisService;
	private String [][]result;
	private String [][]result2;
	private String []showd;
	private String []sdate=new String[16];
	String id;
	@RequestMapping("/upex")
	public String preAdd(@RequestParam(value = "attachment") MultipartFile attachment,
			Model model) throws ServletException, IOException, ParseException{
		
	  //1读取EXCEL
	     try {  
	    	    
	            InputStream stream  = attachment.getInputStream();
	            Streams.copy(stream, new FileOutputStream("d:/mb/mb.xls"), true);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } 
	     try {  
	            String fileName = "D://mb/mb.xls"; // Excel文件所在路径  
	            File file = new File(fileName); // 创建文件对象  
	            Workbook wb = Workbook.getWorkbook(file); // 从文件流中获取Excel工作区对象（WorkBook）  
	            Sheet sheet = wb.getSheet(0); // 从工作区中取得页（Sheet）  
	           result=new String[sheet.getRows()][sheet.getColumns()];
	           for (int i = 0; i < sheet.getRows(); i++) { // 循环打印Excel表中的内容  
	                for (int j = 0; j < sheet.getColumns(); j++) {  
	                    Cell cell = sheet.getCell(j, i);  
	                    System.out.println(cell.getContents());  
	                    result[i][j]=cell.getContents();
	                }  
	                System.out.println();  
	            }  
	        } catch (BiffException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	     setssdate();     
//将从EXCEL中获取的数据进行处理
	     
	     
	     
	     int i=0;
	     int j=0;
	     int k=0;
	     int q=0;
		 int x=0;
		 int y=0;
	     int sz=0;
	     for(j=29;j<=44;j++)
		     for(i=5;i<result.length;i++)//i=0 第一行标注为注释字段  第二行字段名称 第三行暂时不做处理 
		    	 
		     {
		    	    if(result[i][j].length()>0) {//如果该时间工作量不为空就进行赋值运算
		    		 
		    		    sz++;//先确认总的个数 
		    	 }
		     }
		    //初始化新的数组 4行是预留部分
	        sz+=4;
		    result2=new String[sz][42];
		    y=1;
				  for(j=0;j<29;j++)
				  {
						
					  result2[3][y]=result[3][j];
					  y++;	  
				  }
			  
		     result2[3][29]="备注";
		     result2[3][0]="日期";
		     result2[3][30]="数量";
		    k=4;//从第5行开始计算
		    for(j=29;j<=44;j++)
			     for(i=4;i<result.length;i++)//i=0 第一行标注为注释字段  第二行字段名称 第三行暂时不做处理  
			     {
			    	    if(result[i][j].length()>0) {//如果该时间工作量不为空就进行赋值运算
			    		 
			    	    	q=0;
			    		  result2[k][q]=sdate[j-29];//第一列显示日期result[1][j]
			    		    q++;
			    		  for(int h=0;h<29;h++){
			    				 result2[k][q]=result[i][h];
			    				 q++;  
			    			  }
			    		  result2[k][q]=result[i][j];
			    		  k++;
			    		  }
			    	 }
		    
		    
		    
		    
		    
		    
		    //确认数据是否有误  查询内容是类型和字段长度
		    //目前进行判断的有一些内容   2销售订单号 3 5 6   
		 
		    for(i=4;i<result2.length;i++){   	
		    if((!Pd(result2[i][1],8)) ||((!Pd(result2[i][2],2))&&(!Pd(result2[i][2],3)))||(!Pd(result2[i][4],7)&&result[i][4].length()>0)||(!Pd(result2[i][5],10)&&result2[i][5].length()>0)||(result2[i][30].length()>0&&!Pd2(result2[i][30]))  ){

		    	//检测出EXCEL数据存在问题 直接报错     
		    return forward("/mps/upmpserr");
		    }
		    }
		   //存储数据之前必须删除啊当前表中的字段
		    zdService.FindAndDelete();
		   //下面进行数据库存储 
		    //HISZHDNG 表
		    DateFormat dd=new SimpleDateFormat("yyyy/mm/dd");
		    java.util.Date dt=null; 
		    
		    List <Hisview> l1 =new ArrayList<Hisview>();
		    List <Zdview> l2 = new ArrayList<Zdview>();
		    
		  for(i=4;i<result2.length;i++)
		  {
		               
			  
			  //顺序发生改变  从EXCEL中读取的数据发生了变化  列也随着发生变化      
			  
			  
		                  Hisview  ob=new  Hisview();
		                  Zdview   ob2  =new Zdview();
		                  
		                  ob.setShowdate(result2[i][0]);
		                  ob.setSailorder(result2[i][1]);
		                  ob.setLineitem(result2[i][2]);
		                  ob.setTlineitem(result2[i][3]);
		                  ob.setPorder(result2[i][4]);
		                  ob.setBorder(result2[i][5]);
		                  ob.setSutcode(result2[i][6]);
		                  ob.setSapid(result2[i][7]);
		                  ob.setLongdec(result2[i][8]);
		                  ob.setT828(dd.parse(changedate(result2[i][9])));
		                  ob.setComdate(dd.parse(changedate(result2[i][10])));
		                  ob.setOrderdate(dd.parse(changedate(result2[i][11])));
		                  ob.setOrdernum(Integer.parseInt( changenum(result2[i][12])));
		                  ob.setSapnum(Integer.parseInt( changenum(result2[i][13])));
		                  ob.setBzcheck(result2[i][14]);//原包材校验-->包装入库量
		                  ob.setRenum(Integer.parseInt( changenum(result2[i][15])));
		                  ob.setZdtype(result2[i][16]);
		                  ob.setZddia(result2[i][17]);
		                  ob.setPackway(result2[i][18]);
		                  ob.setComp(result2[i][19]);
		                  ob.setZlqid(result2[i][20]);
		                  ob.setZlqidinf(result2[i][21]);
		                  ob.setDgid(result2[i][22]);
		                  ob.setDginf(result2[i][23]);
		                  ob.setUpoverflow(result2[i][24]);
		                  ob.setDownflow(result2[i][25]);
		                  ob.setStruinf(result2[i][26]);
		                  ob.setStrureinf(result2[i][27]);
		                  ob.setIndate(dd.parse(changedate(result2[i][28])));
		                  ob.setRemark(result2[i][29]);
		                  ob.setTnum(Integer.parseInt( changenum(result2[i][30]))); 
		                  
		                
		                  
		                  
		                  
		                  //
		                 
		                  ob2.setShowdate(result2[i][0]);
		                  ob2.setSailorder(result2[i][1]);
		                  ob2.setLineitem(result2[i][2]);
		                  ob2.setTlineitem(result2[i][3]);
		                  ob2.setPorder(result2[i][4]);
		                  ob2.setBorder(result2[i][5]);
		                  ob2.setSutcode(result2[i][6]);
		                  ob2.setSapid(result2[i][7]);
		                  ob2.setLongdec(result2[i][8]);
		                  ob2.setT828(dd.parse(changedate(result2[i][9])));
		                  ob2.setComdate(dd.parse(changedate(result2[i][10])));
		                  ob2.setOrderdate(dd.parse(changedate(result2[i][11])));
		                  ob2.setOrdernum(Integer.parseInt( changenum(result2[i][12])));
		                  ob2.setSapnum(Integer.parseInt( changenum(result2[i][13])));
		                  ob2.setBzcheck(result2[i][14]);//原包材校验-->包装入库量
		                  ob2.setRenum(Integer.parseInt( changenum(result2[i][15])));
		                  ob2.setZdtype(result2[i][16]);
		                  ob2.setZddia(result2[i][17]);
		                  ob2.setPackway(result2[i][18]);
		                  ob2.setComp(result2[i][19]);
		                  ob2.setZlqid(result2[i][20]);
		                  ob2.setZlqidinf(result2[i][21]);
		                  ob2.setDgid(result2[i][22]);
		                  ob2.setDginf(result2[i][23]);
		                  ob2.setUpoverflow(result2[i][24]);
		                  ob2.setDownflow(result2[i][25]);
		                  ob2.setStruinf(result2[i][26]);
		                  ob2.setStrureinf(result2[i][27]);
		                  ob2.setIndate(dd.parse(changedate(result2[i][28])));
		                  ob2.setRemark(result2[i][29]);
		                  ob2.setTnum(Integer.parseInt( changenum(result2[i][30])));   
		                  
		                  
		                  
		                  
		                  
		                  
		                  
		                  
		                  l1.add(ob);
		                  l2.add(ob2);        
	       }
		  //提高并发性
		    hisService.saveHisviews(l1);
            zdService.saveZdviews(l2);
		  
	

            
	//result2 是导入到数据库    设置result3作为显示在页面上的数据
		  
   String [][]result3=new String[result2.length][46];

  //实现数量分开和日期后调 2个功能   在RESULT2	中数量一列的下标为30
  
  
   
   
   for(i=3;i<4;i++)
	   for(j=0;j<46;j++)
		   if(j<30)
			   result3[i][j]=result2[i][j+1];
		   else
			   result3[i][j]=" ";
   
   result3[i][29]="日期";
   
   
   
   
 

   for(i=0;i<16;i++){//对一行的后面6个数值赋值
	   
	   result3[3][30+i]=sdate[i];//设置表头 
	   
   }
   
   
  
   
   
   
   
     for(i=4;i<result2.length;i++)
     {
    	 
    	 for(k=0;k<29;k++){
    		 
    		 result3[i][k]=result2[i][k+1];
    	 }
    	result3[i][29]=result2[i][0];//第28列是日期
    	result3[i][30]=result2[i][30];
    	
    	 
    	for(k=30;k<46;k++)
    		result3[i][k]=" ";//把数据设置成空 
    	  for(j=0;j<16;j++){
    		  if(result2[i][0].equals(sdate[j])){
    			
    			  result3[i][30]="";//把之前的日期位置设置为空
    			  result3[i][30+j]=result2[i][30];
    			  
    			  
    		  }
    	  } 
     }
     
     
     
     result3[3][28]="备注";
     result3[3][29]="日期";
	model.addAttribute("value1",result3);
	 id="1";
	model.addAttribute("id",id);
	return forward("/mps/result");// 返回成功
	
	}
	public String  changedate(String da){
		
		
		
		if(da.trim().length()>0){
		  Calendar a=Calendar.getInstance();
		  int year=a.get(Calendar.YEAR);
		  String f=Integer.toString(year);
		  System.out.println(f+"/"+da);
		  return (f+"/"+da);
		}
		else return "0000/00/00";
		
	}
	public String  changenum(String num){//数据转换
		
		if(num.trim().length()>0)
		return num.trim();
		 else
	    return "-1";
		
	}
	public void setssdate(){
		
		  Calendar a=Calendar.getInstance();
		  int year=a.get(Calendar.YEAR);
		  String f=Integer.toString(year);
		 for(int i=0;i<16;i++){
			 
			 sdate[i]=f+result[3][29+i];
		 }
	}
	
	public boolean Pd(String s,int l){
		
		
		if(s.length()!=l){
			
			return false;
		}
		else{
			
			for(int i=0;i<s.length();i++){
				
				if(s.charAt(i)<'0'||s.charAt(i)>'9')
					return false;
				
			}
		}
		
		return true;
	}
	
public  boolean Pd2(String s){
	
	int x=1;
	try{ 
	    int i=Integer.valueOf(s); 
	}catch(Exception e){ 
		x=0;
		return false;
	}
	
	if(x==1){
		
		return true;
	}else
	{
		return false;
	}
	
}
}