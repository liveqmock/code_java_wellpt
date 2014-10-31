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

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;



@Controller
@Scope("prototype")
@RequestMapping("/mps")
public class StaffController extends BaseController {

	

	    private  String  drive ;
		private  String  url;
		private  String  DBUSER;
	    private  String  password;
	    private  String[][]results;
	    private int maxnum=1000000;
	    @Autowired
	    private  ExtendedPropertyPlaceholderConfigurer pro;
	
	
	
	
   
    
    @RequestMapping("/findstaff")
	public String FindStaff(@RequestParam(value = "time1") String time1,@RequestParam(value = "time2") String time2,@RequestParam(value = "gd") String gd,@RequestParam(value = "uid") String uid,@RequestParam(value = "gx") String gx,
			Model model) throws ClassNotFoundException, ParseException {
				
       
    	
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    	System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
    	
    	
    	drive=pro.getProperty("multi.tenancy.lms.drive").toString();
        url=pro.getProperty("multi.tenancy.lms.dburl").toString();
        DBUSER=pro.getProperty("multi.tenancy.lms.dbuser").toString();
        password=pro.getProperty("multi.tenancy.lms.dbpassword").toString();
     
    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	 SimpleDateFormat ds=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
      //(SELECT c.S_SCENE  FROM T_MES_SceneDftGdsCode a, T_DrvInfo b, T_Scene c where a.drvid = b.I_DID and a.gid = b.I_GID and b.I_SCENEID = c.N_SCENEID) 该字段用来获取场景
        
    	String sql=
    			"SELECT a.gettime,\n" +
    					"       a.won,\n" + 
    					"       a.USERID,\n" + 
    					"       a.SUBAMOUNT,\n" + 
    					"       a.Gid,\n" + 
    					"       a.drvid,\n" + 
    					"       c.S_SCENE,\n" + 
    					"       a.PRO_ID,\n" + 
    					"       a.shift--,\n" + 
    					"      -- d.i_Trin,\n" + 
    					"      -- s.s1\n" + 
    					"  FROM T_MES_SubPro a,\n" + 
    					"       T_DrvInfo b,\n" + 
    					"       T_Scene c--,\n" + 
    					"      -- T_MES_TrOutC d,\n" + 
    					"      /* (select a.GAMNG s1, b.zgdh s2\n" + 
    					"          from zppt0030 a, zppt0068 b\n" + 
    					"         where a.aufnr(+) = b.aufnr) s*/\n" + 
    					" where a.drvid = b.I_DID(+)\n" + 
    					"   and a.gid = b.I_GID(+)\n" + 
    					"   and b.I_SCENEID = c.N_SCENEID(+)\n" + 
    					"   --and a.won = d.s_won(+)\n" + 
    					"  -- and a.won = s.s2(+)\n";

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
    		
    		
    		sql+=" AND GETTIME >= to_date('"+time1+"','yyyy-MM-dd')";
    		
    	}
    	if(time2.length()>0){
    		
    		sql+=" AND GETTIME <= to_date('"+time2+"','yyyy-MM-dd')";
    		
    	}
    	if(gd.length()>0){//工单号
    		
    		
    		String sq="";
    		
    		String[]strArray = gd.split(",");
    		 
    		sq=" AND ( WON like '"+strArray[0]+"'";
    		
    		 for(int i=1;i<strArray.length;i++){
    			 
    		 	 sq+=" OR WON like '"+strArray[i]+"'";
    			 
    		 }
    		 
    		sql+=sq+" )";
    		
    	}
    	if(uid.length()>0){//用户ID
    		
    		//sql+=" AND USERID ='"+uid+"'";
    		
    		
	String sq="";
    		
    		String[]strArray = uid.split(",");
    		 
    		sq=" AND ( USERID like '"+strArray[0]+"'";
    		
    		 for(int i=1;i<strArray.length;i++){
    			 
    		 	 sq+=" OR USERID like '"+strArray[i]+"'";
    			 
    		 }
    		 
    		sql+=sq+" )";
    	
    	}
    	if(gx.length()>0){//
    		
    	//	sql+=" AND PRO_ID='"+gx+"'";
    		
    		
String sq="";
    		
    		String[]strArray = gx.split(",");
    		 
    		sq=" AND ( PRO_ID like '"+strArray[0]+"'";
    		
    		 for(int i=1;i<strArray.length;i++){
    			 
    		 	 sq+=" OR PRO_ID like '"+strArray[i]+"'";
    			 
    		 }
    		 
    		sql+=sq+" )";
    		
    		
    	}
		 Connection conn = null;//表示数据库连接
         Statement stmt= null;//表示数据库的更新
         ResultSet result = null;//查询数据库    
         ResultSet rs=null;
         int i=0;
         Class.forName(drive);//使用class类来加载程序
         try {
			conn =DriverManager.getConnection(url,DBUSER,password);
			   stmt = conn.createStatement();
		         results=new String[maxnum][9];
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
		        i++;
		         }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
         com.Filter(results, i, 9);
         if(i>0){
         model.addAttribute("bj", "1");
         }
         else {
         model.addAttribute("bj", "0");
         }
         
         model.addAttribute("totalnum",i);//totalnum表示总条数   
         model.addAttribute("po",15);//po表示每页显示10条 
         model.addAttribute("page",1);//当前页
         int tp=0;
       
         String tjshuju="";  
		  tjshuju+="采集时间"+"@@";
		  tjshuju+="工单号"+"@@";
		  tjshuju+="用户ID"+"@@";
		  tjshuju+="报工数量"+"@@";
		  tjshuju+="机台GID"+"@@";
		  tjshuju+="机台DID"+"@@";
		  tjshuju+="场景"+"@@";
		  tjshuju+="工序号"+"@@";
		  tjshuju+="班次"+"@@";
		 
		
	
 tjshuju+="##";
for(int k=0;k<i;k++){
	      
           for(int j=0;j<9;j++){
        	   
        	 //  System.out.println(results[k][j]);
        	   
        	   tjshuju+=results[k][j]+"@@";
        	   
        	   
           }
           
           tjshuju+="##";
   }
 
         model.addAttribute("tjshuju",tjshuju);
         model.addAttribute("result",results);
         System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
         return forward("/mps/staff");// 返回成功
         
	}
}

	