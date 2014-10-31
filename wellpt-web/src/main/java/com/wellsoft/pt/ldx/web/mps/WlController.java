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
public class WlController extends BaseController {

	
	    private  String  drive ;
		private  String  url;
		private  String  DBUSER;
	    private  String  password;
	    private  String[][]results;
	    private int maxnum=1000000;
	    @Autowired
	    private  ExtendedPropertyPlaceholderConfigurer pro;
    
    
    
    @RequestMapping("/findwl")
	public String Findwl(@RequestParam(value = "gc") String gc,@RequestParam(value = "ys") String ys,@RequestParam(value = "wlcd") String wlcd,@RequestParam(value = "wlms") String wlms,@RequestParam(value = "time1") String time1,@RequestParam(value = "time2") String time2,
			Model model) throws ClassNotFoundException {
				
		
    	drive=pro.getProperty("multi.tenancy.lms.drive").toString();
        url=pro.getProperty("multi.tenancy.lms.dburl").toString();
        DBUSER=pro.getProperty("multi.tenancy.lms.dbuser").toString();
        password=pro.getProperty("multi.tenancy.lms.dbpassword").toString();
    	
    	
    	DateFormat dd = new SimpleDateFormat("yyyy-MM-dd"); 
    	DateFormat ds=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
      //(SELECT c.S_SCENE  FROM T_MES_SceneDftGdsCode a, T_DrvInfo b, T_Scene c where a.drvid = b.I_DID and a.gid = b.I_GID and b.I_SCENEID = c.N_SCENEID) 该字段用来获取场景
        
    	String sql="select hp.plant_code,hi.item_code, hi.descriptions,hi.primary_uom, h2.meaning,  h1.meaning,hi.key_component_flag, hic.iqc_flag,  hic.CODE_TYPE, hi.enable_flag from    hcm_plant hp,   hcm_item hi, hcm_item_control hic,(select hlt.lookup_type_id, hlt.lookup_type_code,hlt.lookup_code,hlt.meaning, hlt.start_date from hfwk_lookup_types_v hlt where hlt.lookup_type_id in (select hltb.lookup_type_id from hfwk_lookup_types_b hltb where hltb.lookup_type_code ='SUPPLY_TYPE')) h1,(select hlt.lookup_type_id,hlt.lookup_type_code, hlt.lookup_code, hlt.meaning, hlt.start_date from hfwk_lookup_types_v hlt where hlt.lookup_type_id in (select hltb.lookup_type_id from hfwk_lookup_types_b hltb  where hltb.lookup_type_code ='PLANNING_MAKE_BUY_CODE')) h2  where hi.plant_id = hic.plant_id(+) and hi.item_id = hic.item_id(+) and hi.wip_supply_type=h1.lookup_code and hi.planning_make_buy_code=h2.lookup_code and hp.plant_id=hi.plant_id";
	
    	
	
   if(time1.length()>0){
    		
    		sql+=" AND hcm_item.creation_date>=to_date('"+time1+"','yyyy-MM-dd')";
    		
    	}
   if(time2.length()>0){
    		
    		sql+=" AND hcm_item.creation_date <=to_date('"+time2+"','yyyy-MM-dd')";
    		
    	}
   
    	
    	
    	if(gc.length()>0){//工厂
    		
    		
    		sql+=getStrings(gc,"hi.plant_id");
    		
    	}

    	if(ys.length()>0){//工单
    		
    		sql+=getStrings(ys,"hi.enable_flag");	
    	}
    	if(wlcd.length()>0){//
    		
    		sql+=getStrings(wlcd,"hi.item_code");

    	}
    	if(wlms.length()>0){//
    		
    		sql+=getStrings(wlms,"hi.descriptions");
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
	        results=new String[maxnum][10];
	        result =stmt.executeQuery(sql);
	         while(result.next()){
	        	 //总共是10个字段查询 
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
		  tjshuju+="物料编码"+"@@";
		  tjshuju+="物料描述"+"@@";
		  tjshuju+="单位"+"@@";
		  tjshuju+="制造/采购"+"@@";
		  tjshuju+="WIP供应类型"+"@@";
		  tjshuju+="关键组件标识"+"@@";
		  tjshuju+="需要质检"+"@@";
		  tjshuju+="条码管理"+"@@";
		  tjshuju+="是否有效"+"@@";

 tjshuju+="##";
 
 
for(int k=0;k<i;k++){
	      
           for(int j=0;j<10;j++){
        	   
        	   //System.out.println(results[k][j]);
        	   
        	   tjshuju+=results[k][j]+"@@";
        	   
        	   
           }
           
           tjshuju+="##";
   }
 
 model.addAttribute("tjshuju",tjshuju);
         
         
         
         
         
         
         
         
 model.addAttribute("totalnum",i);//totalnum表示总条数   
 model.addAttribute("po",15);//po表示每页显示10条 
 model.addAttribute("page",1);//当前页
         
        
         model.addAttribute("result",results);
         return forward("/mps/wl");// 返回成功
         
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

	