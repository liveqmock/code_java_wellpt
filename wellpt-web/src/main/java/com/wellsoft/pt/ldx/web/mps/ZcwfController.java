package com.wellsoft.pt.ldx.web.mps;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.wellsoft.pt.core.support.ExtendedPropertyPlaceholderConfigurer;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.integration.request.ReceiveRequest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
@Controller
@Scope("prototype")
@RequestMapping("/mps")
public class ZcwfController extends BaseController {

	

	
	    private  String  drive ;
		private  String  url;
		private  String  DBUSER;
	    private  String  password;
	    private  String[][]results;
	    private int maxnum=1000000;
	    @Autowired
	    private  ExtendedPropertyPlaceholderConfigurer pro;
	
	
    @RequestMapping("/findzcwf")
	public String FindStaff(@RequestParam(value = "gc") String gc,@RequestParam(value = "bm") String bm,@RequestParam(value = "kb") String kb,@RequestParam(value = "xb") String xb,@RequestParam(value = "gd") String gd,@RequestParam(value = "lb") String lb,@RequestParam(value = "time1") String time1,@RequestParam(value = "time2") String time2,
			Model model) throws ClassNotFoundException{
			
    	
    	drive=pro.getProperty("multi.tenancy.lms.drive").toString();
        url=pro.getProperty("multi.tenancy.lms.dburl").toString();
        DBUSER=pro.getProperty("multi.tenancy.lms.dbuser").toString();
        password=pro.getProperty("multi.tenancy.lms.dbpassword").toString();
    	
    	
        DateFormat dd=new SimpleDateFormat("yyyy-MM-dd");
        DateFormat ds=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      //(SELECT c.S_SCENE  FROM T_MES_SceneDftGdsCode a, T_DrvInfo b, T_Scene c where a.drvid = b.I_DID and a.gid = b.I_GID and b.I_SCENEID = c.N_SCENEID) 该字段用来获取场景
       
  String sql="select  T_factoryStru.I_FACTORY,T_factoryStru.S_DEPARTMENT,  T_factoryStru.S_CLASS, T_factoryStru.S_LINE,T_MES_SceneDftGdsCode.S_Won,x.x3,T_MES_SceneDftGdsCode.I_Shift,T_DftGdsCode.i_DftID,T_DftGdsCode.s_DftCodeContent,T_DftGdsCode.s_DfteType  from T_factoryStru,T_DftGdsCode,T_MES_SceneDftGdsCode,(SELECT  distinct a.I_Gid x1,a.I_DID x2,c.S_SCENE x3 FROM T_MES_SceneDftGdsCode a, T_DrvInfo b, T_Scene c where a.I_DID = b.I_DID(+) and a.I_GID = b.I_GID(+) and b.I_SCENEID = c.N_SCENEID(+))x where T_factoryStru.I_GID(+)=T_MES_SceneDftGdsCode.I_Gid  and  T_MES_SceneDftGdsCode.I_DID=T_factoryStru.I_DID(+) and T_DftGdsCode.I_DFTID(+)=T_MES_SceneDftGdsCode.I_Dftid and T_MES_SceneDftGdsCode.I_DID=x.x2(+) and T_MES_SceneDftGdsCode.I_Gid=x.x1(+)";
    	
    	
 	
	
 if(time1.length()>0){
  		
  		sql+=" AND T_MES_SceneDftGdsCode.d_Gettime >=to_date('"+time1+"','yyyy-MM-dd')";
  		
  	}
  if(time2.length()>0){
  		
  		sql+=" AND T_MES_SceneDftGdsCode.d_Gettime <=to_date('"+time2+"','yyyy-MM-dd')";
  		
  	}
 
  if(gc.length()>0){
    		
    		
    		
    		sql+=getStrings(gc,"T_factoryStru.I_FACTORY");
    		
    	}
    	if(bm.length()>0){
    		
    	
    		sql+=getStrings(bm,"T_factoryStru.S_DEPARTMENT");
    		
    	}
    	if(kb.length()>0){
    		
    		
    		sql+=getStrings(kb,"T_factoryStru.S_CLASS");
    		
    	}
    	if(xb.length()>0){
    			
    		sql+=getStrings(xb,"T_factoryStru.S_LINE");
    		
    	}
    	if(gd.length()>0){
    		
    		
    		sql+=getStrings(gd,"T_MES_SceneDftGdsCode.s_Won");
    		
    	}
    	if(lb.length()>0){
    		
    		sql+=getStrings(lb,"T_DftGdsCode.s_DfteType");
    	}
		 Connection conn = null;//表示数据库连接
         Statement stmt= null;//表示数据库的更新
         ResultSet result = null;//查询数据库    
         Class.forName(drive);//使用class类来加载程序
         int i=0;
         try {
			conn =DriverManager.getConnection(url,DBUSER,password);
			  stmt = conn.createStatement();
		       results=new String[maxnum][10];
		       result =stmt.executeQuery(sql);
		         while(result.next()){
		        results[i][0]=result.getString(1);
		        results[i][1]=result.getString(2);
		        results[i][2]=result.getString(3);
		        results[i][3]=result.getString(4);
		        results[i][4]=result.getString(5);
		        results[i][5]=result.getString(6);
		        results[i][6]=result.getString(7);
		        results[i][7]=result.getString(8);
		        results[i][8]=result.getString(9);
		        results[i][9]=result.getString(10);
		        i++;
		         }
		} catch (SQLException e) {
			
			e.printStackTrace();
		} 
  finally{
        	 try {//关闭之前的连接
	             result.close();   
	             stmt.close();
	             conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         }
     
       
         common com=new common();
         com.Filter(results, i, 10);
         if(i>0){
         model.addAttribute("bj", "1");
         }
         else {
         model.addAttribute("bj", "0");
         }
         model.addAttribute("totalnum",i);
         
         
         String tjshuju="";  
		  tjshuju+="工厂"+"@@";
		  tjshuju+="部门"+"@@";
		  tjshuju+="课别"+"@@";
		  tjshuju+="线别"+"@@";
		  tjshuju+="工单号"+"@@";
		  tjshuju+="场景"+"@@";
		  tjshuju+="班次"+"@@";
		  tjshuju+="不良代码"+"@@";
		  tjshuju+="不良代码描述"+"@@";
		  tjshuju+="缺陷类别"+"@@";
		  
tjshuju+="##";
for(int k=0;k<i;k++){
	      
          for(int j=0;j<10;j++){
       	   
       	  // System.out.println(results[k][j]);
       	   
       	   tjshuju+=results[k][j]+"@@";
       	   
       	   
          }
          
          tjshuju+="##";
  }

model.addAttribute("tjshuju",tjshuju);
         
         

            model.addAttribute("totalnum",i);//totalnum表示总条数   
         model.addAttribute("po",15);//po表示每页显示10条 
         model.addAttribute("page",1);//当前页
         model.addAttribute("result",results);
         return forward("/mps/zcwf");// 返回成功 
	}
    
    public String getStrings(String a,String b){
    	
    	
   	 String sq="";
  		
 		 String[]strArray = a.split(",");
 		 
 		sq=" AND ( "+b+" like '"+strArray[0]+"'";
 		
 		 for(int i=1;i<strArray.length;i++){
 			 
 		 	 sq+=" OR "+b+" like '"+strArray[i]+"'";
 			 
 		 }
 		 
 		return (sq+" )");
   }

}

	