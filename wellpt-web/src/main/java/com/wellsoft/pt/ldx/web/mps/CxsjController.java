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
public class CxsjController extends BaseController {

	
	private  String  drive ;
	private  String  url;
	private  String  DBUSER;
    private  String  password;
    private  String[][]results;
    private int maxnum=1000000;
    @Autowired
    private  ExtendedPropertyPlaceholderConfigurer pro;
	
	
    @RequestMapping("/cxsj")
	public String FindStaff(@RequestParam(value = "yg") String yg,@RequestParam(value = "cj") String cj,@RequestParam(value = "gd") String gd,@RequestParam(value = "comp") String comp,@RequestParam(value = "kb") String kb,@RequestParam(value = "wid") String wid,
			@RequestParam(value = "time1") String time1,@RequestParam(value = "time2") String time2,	Model model) throws ClassNotFoundException{
		
    	
    	
    	drive=pro.getProperty("multi.tenancy.lms.drive").toString();
        url=pro.getProperty("multi.tenancy.lms.dburl").toString();
        DBUSER=pro.getProperty("multi.tenancy.lms.dbuser").toString();
        password=pro.getProperty("multi.tenancy.lms.dbpassword").toString();
    	
    	
        DateFormat dd=new SimpleDateFormat("yyyy-MM-dd");
        DateFormat ds=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      //(SELECT c.S_SCENE  FROM T_MES_SceneDftGdsCode a, T_DrvInfo b, T_Scene c where a.drvid = b.I_DID and a.gid = b.I_GID and b.I_SCENEID = c.N_SCENEID) 该字段用来获取场景
       
  String sql= "SELECT T_factoryStru.I_FACTORY,\n" +
				  "       T_MES_ProWorTim.I_Employeeno,\n" + 
				  "       T_factoryStru.S_DEPARTMENT,\n" + 
				  "       T_factoryStru.S_CLASS,\n" + 
				  "       T_factoryStru.S_LINE,\n" + 
				  "       T_MES_ProWorTim.S_Won,\n" + 
				  "       x.x3,\n" + 
				  "       T_MES_ProWorTim.I_Shift,\n" + 
				  "       T_MES_ProWorTim.d_Starttime,\n" + 
				  "       T_MES_ProWorTim.d_Endtime,\n" + 
				  "       hi.item_code,\n" + 
				  "       round(to_number(T_MES_ProWorTim.D_EndTime -\n" + 
				  "                       T_MES_ProWorTim.D_StartTime) * 24)\n" + 
				  "  FROM T_MES_ProWorTim,\n" + 
				  "       T_factoryStru,\n" + 
				  "       hme_work_order wo,\n" + 
				  "       hcm_item hi,\n" + 
				  "       (SELECT distinct a.I_Gid x1, a.I_DID x2, c.S_SCENE x3\n" + 
				  "          FROM T_MES_ProWorTim a, T_DrvInfo b, T_Scene c\n" + 
				  "         where a.I_DID = b.I_DID(+)\n" + 
				  "           and a.I_GID = b.I_GID(+)\n" + 
				  "           and b.I_SCENEID = c.N_SCENEID(+)) x\n" + 
				  " WHERE T_factoryStru.I_GID(+) = T_MES_ProWorTim.I_Gid\n" + 
				  "   and T_factoryStru.I_DID(+) = T_MES_ProWorTim.I_DID\n" + 
				  "   and T_MES_ProWorTim.I_DID = x.x2(+)\n" + 
				  "   and T_MES_ProWorTim.I_GID = x.x1(+)\n" + 
				  "   and wo.work_order_num = T_MES_ProWorTim.s_Won\n" + 
				  "   and wo.item_id = hi.item_id\n" + 
				  "   and wo.plant_id = hi.plant_id";

  
  
  
 	
	//根据采集时间
  
 if(time1.length()>0){
  		
  		sql+=" AND T_MES_ProWorTim.d_Gettime >=to_date('"+time1+"','yyyy-MM-dd')";
  		
  	}
  	if(time2.length()>0){
  		
  		sql+=" AND T_MES_ProWorTim.d_Gettime <=to_date('"+time2+"','yyyy-MM-dd')";
  		
  	}

  
  
    	if(yg.length()>0){
    		
    	
    		sql+=getStrings(yg,"T_MES_ProWorTim.I_Employeeno");
    		
    	}
    	if(gd.length()>0){
    		
    		
    		sql+=getStrings(gd,"T_MES_ProWorTim.S_Won");
    		
    	}
    	if(cj.length()>0){
    		
    		
    		sql+=getStrings(cj,"x.x3");
    		
    	}
    	if(comp.length()>0){
    		
    		
    		 sql+=getStrings(comp,"T_factoryStru.I_FACTORY");
    		
    	}
    	
    	if(kb.length()>0){
    		
    		 sql+=getStrings(kb,"T_factoryStru.S_CLASS");
    		
    	}
    	if(wid.length()>0){
    		
    		
    		sql+=getStrings(wid,"s.s2");
    	}
    	
    	
		 Connection conn = null;//表示数据库连接
         Statement stmt= null;//表示数据库的更新
         ResultSet result = null;//查询数据库    
         int i=0;
         
         try {
        	Class.forName(drive);//使用class类来加载程序
			conn =DriverManager.getConnection(url,DBUSER,password);
			  stmt = conn.createStatement();
		         results=new String[maxnum][12];
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
		        results[i][10]=result.getString(11);
		        results[i][11]=result.getString(12);
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
         com.Filter(results, i, 12);
         
         
         if(i>0){
         model.addAttribute("bj", "1");
         }
         else {
         model.addAttribute("bj", "0");
         }
         model.addAttribute("totalnum",i);
         
         
         
         String tjshuju="";  
		  tjshuju+="工厂"+"@@";
		  tjshuju+="员工号"+"@@";
		  tjshuju+="部门"+"@@";
		  tjshuju+="课别"+"@@";
		  tjshuju+="线别"+"@@";
		  tjshuju+="工单号"+"@@";
		  tjshuju+="场景"+"@@";
		  tjshuju+="班次"+"@@";
		  tjshuju+="开工时间"+"@@";
		  tjshuju+="完工时间"+"@@";
		  tjshuju+="物料ID"+"@@";
		  tjshuju+="实际工时"+"@@";
		

  tjshuju+="##";

for(int k=0;k<i;k++){
 	      
            for(int j=0;j<12;j++){
         	   
         	  
         	   
         	   tjshuju+=results[k][j]+"@@";
         	   
         	   
            }
            
            tjshuju+="##";
    }
  
  model.addAttribute("tjshuju",tjshuju);
         
         
         
         
         
         
         
          model.addAttribute("totalnum",i);//totalnum表示总条数   
          model.addAttribute("po",15);//po表示每页显示10条 
          model.addAttribute("page",1);//当前页
         model.addAttribute("result",results);
         return forward("/mps/cxsj");// 返回成功 
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

	