package com.wellsoft.pt.ldx.web.mps;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wellsoft.pt.core.support.ExtendedPropertyPlaceholderConfigurer;
import com.wellsoft.pt.core.web.BaseController;
@Controller
@Scope("prototype")
@RequestMapping("/mps")
public class ErwhController extends BaseController {

	
	 
	 
	private  String  drive ;
	private  String  url;
	private  String  DBUSER;
    private  String  password;
    private  String[][]results;
    private int maxnum=1000000;
    @Autowired
    private  ExtendedPropertyPlaceholderConfigurer pro;
   

  
    @RequestMapping("/finderwh")
	public String FindStaff(@RequestParam(value = "time1") String time1,@RequestParam(value = "time2") String time2,@RequestParam(value = "gd") String gd,@RequestParam(value = "uid") String uid,@RequestParam(value = "yc") String yc,
			Model model) throws ClassNotFoundException {
				
        DateFormat dd=new SimpleDateFormat("yyyy-MM-dd");
        DateFormat ds=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      //(SELECT c.S_SCENE  FROM T_MES_SceneDftGdsCode a, T_DrvInfo b, T_Scene c where a.drvid = b.I_DID and a.gid = b.I_GID and b.I_SCENEID = c.N_SCENEID) 该字段用来获取场景
      
         drive=pro.getProperty("multi.tenancy.lms.drive").toString();
         url=pro.getProperty("multi.tenancy.lms.dburl").toString();
         DBUSER=pro.getProperty("multi.tenancy.lms.dbuser").toString();
         password=pro.getProperty("multi.tenancy.lms.dbpassword").toString();
         
         System.out.println(drive);
         System.out.println(url);
         System.out.println(DBUSER);
         System.out.println(password);
         
       
        //pro.getProperty(uid)
        
    	String sql="SELECT  t.D_GETTIME,t.S_WON,t.S_USERID,t.I_CODENO,s.S_REASON,t.I_Ehours,t.I_GID,t.I_DID,x.x3,tf.S_LINE,t.I_SHIFT FROM T_ExRe s, T_factoryStru tf,T_MES_ExHours t, (SELECT distinct a.I_Gid x1,a.I_DID x2,c.S_SCENE x3 FROM T_MES_ExHours a, T_DrvInfo b, T_Scene c where a.I_DID = b.I_DID(+) and a.I_GID = b.I_GID(+) and b.I_SCENEID = c.N_SCENEID(+))x where x.x1(+)=t.I_GID and x.x2(+)=t.I_DID and t.I_codeno=s.I_EXNO(+) and t.I_GID=tf.I_GID(+)  and t.I_DID=tf.I_DID(+)";
    	
   
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
    		
    		sql+=" AND t.D_GETTIME >= to_date('"+time1+"','yyyy-MM-dd')";
    		
    	}
    	if(time2.length()>0){
    		
    		sql+=" AND t.D_GETTIME <= to_date('"+time2+"','yyyy-MM-dd')";
    		
    	}
    	if(gd.length()>0){
    		
    		//sql+=" AND WON ='"+gd+"'";
    		
	       String sq="";
    		
    		String[]strArray = gd.split(",");
    		 
    		sq=" AND ( t.S_WON like '"+strArray[0]+"'";
    		
    		 for(int i=1;i<strArray.length;i++){
    			 
    		 	 sq+=" OR t.S_WON like '"+strArray[i]+"'";
    			 
    		 }
    		 
    		sql+=sq+" )";
    		
    		
    	}
    	if(uid.length()>0){
    		
    		//sql+=" AND USERID ='"+uid+"'";
    		
	     String sq="";
    		
    		String[]strArray = uid.split(",");
    		 
    		sq=" AND ( t.S_USERID like '"+strArray[0]+"'";
    		
    		 for(int i=1;i<strArray.length;i++){
    			 
    		 	 sq+=" OR t.S_USERID like '"+strArray[i]+"'";
    			 
    		 }
    		sql+=sq+" )";
    		
    		
    		
    	}
    	if(yc.length()>0){
    		
    		//sql+=" AND CODENO='"+yc+"'";
    		
    		
            String sq="";
    		
    		String[]strArray = yc.split(",");
    		 
    		sq=" AND ( t.I_CODENO like '"+strArray[0]+"'";
    		
    		 for(int i=1;i<strArray.length;i++){
    			 
    		 	 sq+=" OR t.I_CODENO like '"+strArray[i]+"'";
    			 
    		 }
    		 
    		sql+=sq+" )";
    		
    		
    		
    	}
		 Connection conn = null;//表示数据库连接
         Statement stmt= null;//表示数据库的更新
         ResultSet result = null;//查询数据库    
       
         int i=0;
         try {
        	 Class.forName(drive);//使用class类来加载程序
			 conn =DriverManager.getConnection(url,DBUSER,password);
			 stmt = conn.createStatement();
			 results=new String[maxnum][11];
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
	        i++;
	         }
	         
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //连接数据库
         //Statement接口要通过connection接口来进行实例化操作
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
         com.Filter(results, i, 11);
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
		  tjshuju+="异常代码编码"+"@@";
		  tjshuju+="异常代码描述"+"@@";
		  tjshuju+="异常工时"+"@@";
		  tjshuju+="机台GID"+"@@";
		  tjshuju+="机台DID"+"@@";
		  tjshuju+="场景"+"@@";
		  tjshuju+="线别"+"@@";
		  tjshuju+="班次"+"@@";

  tjshuju+="##";
  
  
for(int k=0;k<i;k++){
 	      
            for(int j=0;j<11;j++){
         	   
         	  
         	   
         	   tjshuju+=results[k][j]+"@@";
         	   
         	   
            }
            
            tjshuju+="##";
    }
  
  model.addAttribute("tjshuju",tjshuju);

         
         
  model.addAttribute("totalnum",i);//totalnum表示总条数   
  model.addAttribute("po",15);//po表示每页显示10条 
  model.addAttribute("page",1);//当前页
         model.addAttribute("result",results);
         return forward("/mps/erwh");// 返回成功 
	}
}

	