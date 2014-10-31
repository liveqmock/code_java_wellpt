package com.wellsoft.pt.ldx.web.mps;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
public class WnodeController extends BaseController {

	
	   private  String  drive ;
		private  String  url;
		private  String  DBUSER;
	    private  String  password;
	    private  String[][]results;
	    private int maxnum=1000000;
	    @Autowired
	    private  ExtendedPropertyPlaceholderConfigurer pro;
    
    @RequestMapping("/findwnode")
	public String FindStaff(@RequestParam(value = "time1") String time1,@RequestParam(value = "time2") String time2,@RequestParam(value = "gd") String gd,@RequestParam(value = "uid") String uid,@RequestParam(value = "wn") String wn,@RequestParam(value = "xt") String xt,@RequestParam(value = "wds") String wds,
			Model model) throws ClassNotFoundException {
			
    	
    	drive=pro.getProperty("multi.tenancy.lms.drive").toString();
        url=pro.getProperty("multi.tenancy.lms.dburl").toString();
        DBUSER=pro.getProperty("multi.tenancy.lms.dbuser").toString();
        password=pro.getProperty("multi.tenancy.lms.dbpassword").toString();
    	
    	
        DateFormat dd=new SimpleDateFormat("yyyy-MM-dd");
        DateFormat ds=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
      //(SELECT c.S_SCENE  FROM T_MES_SceneDftGdsCode a, T_DrvInfo b, T_Scene c where a.drvid = b.I_DID and a.gid = b.I_GID and b.I_SCENEID = c.N_SCENEID) 该字段用来获取场景
        
    	String sql="SELECT  h.D_GETTIME,h.S_WON,h.S_USERID,h.I_DFTID,t.S_DFTCODECONTENT,h.I_GID,h.I_DID,x.x3, tf.S_LINE,h.I_SHIFT FROM T_MES_SceneDftGdsCode h,T_DftGdsCode t,T_factoryStru tf,(SELECT distinct a.I_GID x1,a.I_DID x2,c.S_SCENE x3 FROM T_MES_SceneDftGdsCode a, T_DrvInfo b, T_Scene c where a.I_DID = b.I_DID(+) and a.I_GID = b.I_GID(+) and b.I_SCENEID = c.N_SCENEID(+))x WHERE t.I_DFTID(+)=h.I_DFTID and h.I_DID=x.x2(+) and h.I_GID=x.x1(+)  and  tf.I_GID(+)=h.i_gid and tf.I_DID(+)=h.i_did";
  
  /*
    	
if(time1.length()==0&&time2.length()==0){
    		
    		SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd");   
    	    time1=ss.format(new Date());   
    	    Calendar calendar = Calendar.getInstance();
    	    calendar.add(Calendar.DAY_OF_YEAR, 1);
    	    Date date = calendar.getTime();
    	    time2=ss.format(date);
    	    //System.out.println("第一天"+time1+"第二天 "+time2);
    	}
  */
    	
    	
    	
    	if(time1.length()>0){
    		
    		sql+=" AND h.D_GETTIME >= to_date('"+time1+"','yyyy-MM-dd')";
    	}
    	if(time2.length()>0){
    		
    		sql+=" AND h.D_GETTIME <= to_date('"+time2+"','yyyy-MM-dd')";
    		
    	}
    	if(gd.length()>0){
    		
    		sql+=getStrings(gd, "h.S_WON");
 
    	}
    	if(uid.length()>0){
    		
    		
    		sql+=getStrings(uid, "h.S_USERID");
    		
    	}
    	if(wn.length()>0){
      		 	 
      		sql+= getStrings(wn,"h.I_DFTID");
    		
    	}
    	
    	if(wds.length()>0){
    		
    		//错误代码描述
    		sql+=getStrings(wds,"t.S_DFTCODECONTENT");
    		
    	}
    	if(xt.length()>0){
    		
    		//线体
    		sql+=getStrings(xt,"tf.S_LINE");
    	}
    	
    	
    	
    	  int i=0;
		 Connection conn = null;//表示数据库连接
         Statement stmt= null;//表示数据库的更新
         ResultSet result = null;//查询数据库    
       
         try {
        	  Class.forName(drive);//使用class类来加载程序
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
         com.Filter(results, i,10);
         if(i>0){
         model.addAttribute("bj", "1");
         }
         else {
         model.addAttribute("bj", "0");
         }
         model.addAttribute("totalnum",i);
 
         String tjshuju="";  
		  tjshuju+="采集时间"+"@@";
		  tjshuju+="工单号"+"@@";
		  tjshuju+="用户ID"+"@@";
		  tjshuju+="不良代码ID"+"@@";
		  tjshuju+="不良代码描述"+"@@";
		  tjshuju+="机台GID"+"@@";
		  tjshuju+="机台DID"+"@@";
		  tjshuju+="场景"+"@@";
		  tjshuju+="线别"+"@@";
		  tjshuju+="班次"+"@@";
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
         return forward("/mps/wnode");// 返回成功
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

	