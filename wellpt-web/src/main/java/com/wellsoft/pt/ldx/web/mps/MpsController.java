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
public class MpsController  extends BaseController {
	
	@Autowired
	private ZdService zdService;
	@Autowired
	private HisService hisService;
	String [][]result;
	String [][]result2;
	String []showd;
	String []sdate=new String[16];
	String id;
	@RequestMapping("/mps")
	public String mps(@RequestParam(value = "xs") String xs,@RequestParam(value = "sc") String sc,@RequestParam(value = "jh") String jh,@RequestParam(value = "sd") String sd,
			Model model) throws ServletException, IOException, ParseException{
		
		
		 String []sdate=new String[16];
		 DateFormat dd=new SimpleDateFormat("yyyy/mm/dd");
		
		if(xs.length()==0){
			
			
			xs="%";
			
		}
		if(jh.length()==0)
		{
			
			jh="%";
		}
		if(sc.length()==0){
			
			
			sc="%";
			
		}
		if(sd.length()==0){
			
			sd="%";
			
		}
		
	    List <Zdview> l2 = zdService.getZdviews(xs, jh, sc, sd);
	    result2=new String[l2.size()+2][31];

	int i=0;
	int j=0;
	int k=0;
	
	
if(l2.size()>0){
		setssdate();
	   for(j=0;j<2;j++)
		   for(i=0;i<31;i++)
		   result2[j][i]=" ";
	       result2[0][1]="海德信分厂整灯MPS  Sept (02版）";
	   
	   
	   
	  //在这里对数据进行屁配置
	    i=2;
	    for(Zdview ls:l2){
	    	
	    	System.out.println(i);
	    	result2[i][0]=ls.getShowdate();
	    	result2[i][1]=ls.getSailorder();
	    	result2[i][2]=ls.getLineitem();
	    	result2[i][3]=ls.getTlineitem();
	    	result2[i][4]=ls.getPorder();
	        result2[i][5]=ls.getBorder();
	        result2[i][6]=ls.getSutcode();
	        result2[i][7]=ls.getSapid();
	        result2[i][8]=ls.getLongdec();
	        result2[i][9]=dd.format(ls.getT828());
	        result2[i][10]=dd.format(ls.getComdate());
	        result2[i][11]=dd.format(ls.getOrderdate());
           if(ls.getOrdernum()<=0){
            	result2[i][12]=" ";
            }
            else
            result2[i][12]=Integer.toString(ls.getOrdernum());
          if(ls.getSapnum()<=0){
        	   
        	   result2[i][13]=" ";
           }
           else
            result2[i][13]=Integer.toString(ls.getSapnum());
            result2[i][14]=ls.getBzcheck();
           if(ls.getRenum()<=0){
            	result2[i][15]=" ";
            }
            else
            result2[i][15]=String.valueOf(ls.getRenum());
           result2[i][16]=ls.getZdtype();
           result2[i][17]=ls.getZddia();
           result2[i][18]=ls.getPackway();
           result2[i][19]=ls.getComp();
           result2[i][20]=ls.getZlqid();
           result2[i][21]=ls.getZlqidinf();
           result2[i][22]=ls.getDgid();
           result2[i][23]=ls.getDginf();
           result2[i][24]=ls.getUpoverflow();
           result2[i][25]=ls.getDownflow();
           result2[i][26]=ls.getStruinf();
           result2[i][27]=ls.getStrureinf();
           result2[i][28]=dd.format(ls.getIndate());
           result2[i][29]=ls.getRemark();
           if(ls.getTnum()<=0){
           	result2[i][30]=" ";
           }
           else
           result2[i][30]=Integer.toString(ls.getTnum());
	    i++;		
	    }
	    
	    
	    
	    
	    
	    String [][]result3=new String[result2.length][31];
	
	     
	
	     
	     
	     
	     
	     result3[1][0]="日期";
	     result3[1][1]="销售订单号";
		 result3[1][2]="对应销售订单行项目";
		 result3[1][3]="合并销售订单行项目";
		 result3[1][4]="计划订单号";
		 result3[1][5]="生产订单号";
		 result3[1][6]="客户代码";
		 result3[1][7]="SAP产品ID";
		 result3[1][8]="长描述";
		 result3[1][9]="8-28上线时间";
		 result3[1][10]="完工日期";
		 result3[1][11]="订单交期";
		 result3[1][12]="订单量";
		 result3[1][13]="SAP系统总装完成量";
		 result3[1][14]="包装入库";
		 result3[1][15]="剩余排产计划量";
		 result3[1][16]="整灯品种     规格";
		 result3[1][17]="灯管管径";
		 result3[1][18]="包装方式";
		 result3[1][19]="生产工厂";
		 result3[1][20]="镇流器(驱动）ID";
		 result3[1][21]="镇流器（驱动）信息";
		 result3[1][22]="灯管（光源）ID";
		 result3[1][23]="灯管（光源）信息";
		 result3[1][24]="上盖（结构件)";
		 result3[1][25]="下盖（泡中）";
		 result3[1][26]="结构件信息";
		 result3[1][27]="电光源包材回复信息";
		 result3[1][28]="验货";
		 result3[1][29]="备注";
		 result3[1][30]="数量";
		 
		 
		 
		 
		 
		 
	     for(i=2;i<result2.length;i++)
	    	 for(j=0;j<31;j++)
	    	 {
	    		 result3[i][j]=result2[i][j];
	    		 //System.out.println(result2[i][j]);
	    	 }
	     
	
	       
	     
	     
	     
	       
	  	model.addAttribute("value1",result3);
	  	 id="1";
	  	model.addAttribute("id",id);
	 	return forward("/mps/result2");// 返回成功
	}
	else{
		return forward("/mps/error");
		
	}
	}
	public void setssdate(){
		
		  Calendar a=Calendar.getInstance();
		  int year=a.get(Calendar.YEAR);
		  String f=Integer.toString(year);
	  	  sdate[0]=f+"/9/1-"+f+"/9/3";
	  	  sdate[1]=f+"/9/11-"+f+"/9/13";
	  	  sdate[2]=f+"/9/15-"+f+"/9/17";
	  	  sdate[3]=f+"/9/18-"+f+"/9/20";
	  	  sdate[4]=f+"/9/22-"+f+"/9/24";
	  	  sdate[5]=f+"/9/25-"+f+"/9/27";
	  	  sdate[6]=f+"/9/29-"+f+"/9/30";
	  	  sdate[7]=f+"/10/4-"+f+"/10/5";
	      sdate[8]=f+"/10/6-"+f+"/10/8";
	  	  sdate[9]=f+"/10/9-"+f+"/9/11";
	  	  sdate[10]=f+"/10/13-"+f+"/10/15";
	  	  sdate[11]=f+"/10/16-"+f+"/10/18";
	  	  sdate[12]=f+"/10/20-"+f+"/10/22";
	  	  sdate[13]=f+"/10/23-"+f+"/10/25";
	  	  sdate[14]=f+"/10/27-"+f+"/10/29";
	  	  sdate[15]=f+"/10/30-"+f+"/10/31";
	}
	
}