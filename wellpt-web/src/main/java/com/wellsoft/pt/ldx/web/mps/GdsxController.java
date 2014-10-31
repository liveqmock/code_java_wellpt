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
public class GdsxController extends BaseController {

	

	    private  String  drive ;
		private  String  url;
		private  String  DBUSER;
	    private  String  password;
	    private  String[][]results;
	    private int maxnum=1000000;
	    @Autowired
	    private  ExtendedPropertyPlaceholderConfigurer pro;
	    
    
    
    @RequestMapping("/findgdsx")
	public String findgdsx(@RequestParam(value = "gd") String gd,@RequestParam(value = "gc") String gc,@RequestParam(value = "cx") String cx,@RequestParam(value = "wlcd") String wlcd,@RequestParam(value = "wlms") String wlms,@RequestParam(value = "time1") String time1,@RequestParam(value = "time2") String time2,
			Model model) throws ClassNotFoundException {
				
		
    	drive=pro.getProperty("multi.tenancy.lms.drive").toString();
        url=pro.getProperty("multi.tenancy.lms.dburl").toString();
        DBUSER=pro.getProperty("multi.tenancy.lms.dbuser").toString();
        password=pro.getProperty("multi.tenancy.lms.dbpassword").toString();
    	
    	
    	DateFormat dd = new SimpleDateFormat("yyyy-MM-dd"); 
    	DateFormat ds=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
      //(SELECT c.S_SCENE  FROM T_MES_SceneDftGdsCode a, T_DrvInfo b, T_Scene c where a.drvid = b.I_DID and a.gid = b.I_GID and b.I_SCENEID = c.N_SCENEID) 该字段用来获取场景
        
    	
    	
    	String sql=
    			"select hcm_plant.plant_code,\n" +
    					"       hcm_production_line.prod_line_code,\n" + 
    					"       hcm_production_line.descriptions,\n" + 
    					"       hcm_workcell.workcell_code,\n" + 
    					"       hcm_workcell.descriptions,\n" + 
    					"       hme_work_order.work_order_num,\n" + 
    					"       hcm_item.item_code,\n" + 
    					"       hcm_item.descriptions,\n" + 
    					"       hme_work_order.wo_qty,\n" + 
    					"       hme_switch_wo.creation_date\n" + 
    					"      -- T_MES_TrOutC.i_Trin,\n" + 
    					"      -- T_MES_SubPro.Subamount,\n" + 
    					"      --s.s2,\n" + 
    					"      -- T_MES_SubPro.Subamount - s.s2\n" + 
    					"  from hme_work_order,\n" + 
    					"       hcm_item,\n" + 
    					"       hcm_production_line,\n" + 
    					"       hcm_plant,\n" + 
    					"       hme_switch_wo,\n" + 
    					"       hcm_workcell\n" + 
    					"       --T_MES_TrOutC,\n" + 
    					"       --T_MES_SubPro\n" + 
    					"      /* (select a.zgdh s1, b.GAMNG s2\n" + 
    					"          from zppt0068 a, zppt0030 b\n" + 
    					"         where a.aufnr = b.aufnr(+)) s*/\n" + 
    					" where hcm_item.item_id(+) = hme_work_order.item_id\n" + 
    					"   and hcm_item.plant_id(+) = hme_work_order.plant_id\n" + 
    					"   and hme_work_order.plant_id = hcm_plant.plant_id(+)\n" + 
    					"   and hcm_production_line.prod_line_id(+) = hme_work_order.prod_line_id\n" + 
    					"   and hme_work_order.plant_id = hcm_production_line.plant_id(+)\n" + 
    					"   and hme_switch_wo.work_order_id = hme_work_order.work_order_id\n" + 
    					"   and hme_switch_wo.wkc_id = hcm_workcell.workcell_id\n" + 
    					"  -- and T_MES_TROUTC.S_WON(+) = hme_work_order.work_order_num\n" + 
    					"  -- and T_MES_SubPro.Won(+) = hme_work_order.work_order_num\n" + 
    					"   --and s.s1(+) = hme_work_order.work_order_num\n";

    	
    	
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
    		
    		sql+=" AND hme_switch_wo.creation_date >=to_date('"+time1+"','yyyy-MM-dd')";
    		
    	}
    	if(time2.length()>0){
    		
    		sql+=" AND hme_switch_wo.creation_date <=to_date('"+time2+"','yyyy-MM-dd')";
    		
    	}
    	
    	if(gc.length()>0){//工厂
    		
    		sql+=getStrings(gc,"hcm_plant.plant_code");
    		
    	}
    	if(gd.length()>0){//工单
    		
    		sql+=getStrings(gd,"hme_work_order.work_order_num");
    		
    	}

    	if(cx.length()>0){//
    		
    		sql+=getStrings(cx,"hcm_production_line.PROD_LINE_CODE");
    		
    	}
    
    	if(wlcd.length()>0){//
    		
    		sql+=getStrings(wlcd,"hcm_item.item_code");
    		
    	}
    
    	if(wlms.length()>0){//
    		
    		sql+=getStrings(wlms,"hcm_item.descriptions");
    	
    	}
    	
		 Connection conn = null;//表示数据库连接
         Statement stmt= null;//表示数据库的更新
         ResultSet result = null;//查询数据库    
         ResultSet rs=null;
         
         int i=0;
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
		  tjshuju+="生产线"+"@@";
		  tjshuju+="生产线描述"+"@@";
		  tjshuju+="WKC"+"@@";
		  tjshuju+="WKC描述"+"@@";
		  tjshuju+="工单号"+"@@";
		  tjshuju+="物料编码"+"@@";
		  tjshuju+="物料描述"+"@@";
		  tjshuju+="计划数量"+"@@";
		  tjshuju+="上线时间"+"@@";
		/*  tjshuju+="投入量"+"@@";
		  tjshuju+="员工报工"+"@@";
		  tjshuju+="计划量"+"@@";
		  tjshuju+="差异数量"+"@@";*/

         tjshuju+="##";
  
  
for(int k=0;k<i;k++){
 	      
            for(int j=0;j<10;j++){
         	   
         	  
         	   tjshuju+=results[k][j]+"@@";
         	   
         	   
            }
            
            tjshuju+="##";
    }
  
  model.addAttribute("tjshuju",tjshuju);
       
         
         
         model.addAttribute("totalnum",i);//totalnum表示总条数   
         model.addAttribute("po",15);//po表示每页显示10条 
         model.addAttribute("page",1);//当前页
         model.addAttribute("result",results);
         return forward("/mps/gdsx");// 返回成功
         
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

	