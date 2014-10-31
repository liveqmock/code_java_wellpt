package com.wellsoft.pt.ldx.web.mps;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class SjxjController extends BaseController {

	
	private  String  drive ;
	private  String  url;
	private  String  DBUSER;
    private  String  password;
    private  String[][]results;
    private int maxnum=1000000;
    @Autowired
    private  ExtendedPropertyPlaceholderConfigurer pro;
    
    @RequestMapping("/findsjxj")
	public String FindSjxj(@RequestParam(value = "jytype") String jytype,@RequestParam(value = "jyph") String jyph,@RequestParam(value = "ddh") String ddh,@RequestParam(value = "gd") String gd,@RequestParam(value = "kb") String kb,@RequestParam(value = "xb") String xb,@RequestParam(value = "bc") String bc,@RequestParam(value = "xs") String xs,@RequestParam(value = "guest") String guest,@RequestParam(value = "time1") String time1,@RequestParam(value = "time2") String time2,@RequestParam(value = "soh") String soh,
			Model model) throws ClassNotFoundException {
				
    	
    	
    	  drive=pro.getProperty("multi.tenancy.lms.drive").toString();
          url=pro.getProperty("multi.tenancy.lms.dburl").toString();
          DBUSER=pro.getProperty("multi.tenancy.lms.dbuser").toString();
          password=pro.getProperty("multi.tenancy.lms.dbpassword").toString();
          
  
    	
          DateFormat dd=new SimpleDateFormat("yyyy-MM-dd");
          DateFormat ds=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	String sql=
    			"   SELECT\n" +
    				
    					"       hp.plant_code,\n" + 
    					"       hp.descriptions, --工厂描述\\\\\\\\n\\\\\\\" +\\\\n\\\" +\\n\" +\n" + 
    					"       hplg.descriptions, --生产课别描述\\\\\\\\n\\\\\\\" +\\\\n\\\" +\\n\" +\n" + 
    					"       hpl.descriptions, --线别描述\\\\\\\\n\\\\\\\" +\\\\n\\\" +\\n\" +\n" + 
    					"       ddh.shift_code, --生产班次\\\\\\\\n\\\\\\\" +\\\\n\\\" +\\n\" +\n" + 
    					"       ddh.work_order_id, --工单ID\\\\\\\\n\\\\\\\" +\\\\n\\\" +\\n\" +\n" + 
    					"       ddh.document_number, --生产批号\\\\\\\\n\\\\\\\" +\\\\n\\\" +\\n\" +\n" + 
    					"       hi.item_code, --物料编码\\\\\\\\n\\\\\\\" +\\\\n\\\" +\\n\" +\n" + 
    					"       hi.descriptions, --物料段描述\\\\\\\\n\\\\\\\" +\\\\n\\\" +\\n\" +\n" + 
    					"       ldx.customer, --客户简称\\\\\\\\n\\\\\\\" +\\\\n\\\" +\\n\" +\n" + 
    					"       ldx.po_number, --销售订单\\\\\\\\n\\\\\\\" +\\\\n\\\" +\\n\" +\n" + 
    					"       ldx.so_number, --销售订单行项目\\\\\\\\n\\\\\\\" +\\\\n\\\" +\\n\" +\n" + 
    					"       ddh.created_by, --创建人\\\\\\\\n\\\\\\\" +\\\\n\\\" +\\n\" +\n" + 
    					"       ddh.creation_date,\n" + 
    					"       dqb.operation_seq, --工序号\\\\\\\\n\\\\\\\" +\\\\n\\\" +\\n\" +\n" + 
    					"       dqb.is_key, --是否关键工序\\\\\\\\n\\\\\\\" +\\\\n\\\" +\\n\" +\n" + 
    					"       dqb.question_text, --检查项目\\\\\\\\n\\\\\\\" +\\\\n\\\" +\\n\" +\n" + 
    					"       dqo.option_text, --检查结果\\\\\\\\n\\\\\\\" +\\\\n\\\" +\\n\" +\n" + 
    					"       ddl.result_code, --判定\\\\\\\\n\\\\\\\" +\\\\n\\\" +\\n\" +\n" + 
    					"       ddl.result_text --判定文本\\\\\\\\n\\\\\\\" +\\\\n\\\" +\\n\" +\n" + 
    					"  FROM droid_document_header     ddh,\n" + 
    					"       hme_work_order            hwo,\n" + 
    					"       ldx_make_order            ldx,\n" + 
    					"       ldx_mo_wo_ref             lmwr,\n" + 
    					"       hcm_production_line       hpl,\n" + 
    					"       hcm_production_line_group hplg,\n" + 
    					"       hcm_item                  hi,\n" + 
    					"       hcm_plant                 hp,\n" + 
    					"       droid_document_line       ddl,\n" + 
    					"       droid_question_bank       dqb,\n" + 
    					"       droid_question_options    dqo\n" + 
    					"WHERE ddh.work_order_id = hwo.work_order_num\n" + 
    					"   AND (nvl(ddh.prod_line_id, hwo.prod_line_id) = hpl.prod_line_id OR\n" + 
    					"       (ddh.prod_line_id IS NULL AND hwo.prod_line_id IS NULL))\n" + 
    					"   AND hpl.prod_line_group_id = hplg.prod_line_group_id\n" + 
    					"   AND hwo.item_id = hi.item_id\n" + 
    					"   AND hwo.plant_id = hi.plant_id\n" + 
    					"   AND hwo.work_order_id = lmwr.work_order_id\n" + 
    					"   AND ldx.make_order_id = lmwr.make_order_id\n" + 
    					"   AND hwo.plant_id = hp.plant_id\n" + 
    					"   AND ddh.document_header_id = ddl.document_header_id\n" + 
    					"   AND ddl.option_id = dqo.option_id(+)\n" + 
    					"   AND ddl.level_1 = dqb.level_1\n" + 
    					"   AND ddl.level_2 = dqb.level_2\n" + 
    					"   AND ddl.level_3 = dqb.level_3\n" + 
    					"   AND ddh.document_type = dqb.question_type";

    		

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
      		
      		sql+=" AND   ddh.creation_date >=to_date('"+time1+"','yyyy-MM-dd')";
      		
      	}
      	if(time2.length()>0){
      		
      		sql+=" AND  ddh.creation_date<=to_date('"+time2+"','yyyy-MM-dd')";
      		
      	}
    	
    if(jyph.length()>0){
    	
    	sql+=getStrings(jyph,"ddh.document_number");
    }
  if(jytype.length()>0){//检验类型  列表查询
	  
	  
	  System.out.println(jytype);
	  jytype=jytype.replace(" ", "");
	  sql+="  and  ddh.document_type  = (select DISTINCT v.lookup_code s1 from hfwk_lookup_types_v v where v.lookup_type_code = 'DROID_DOCUMENT_TYPE' and v.meaning= '"+jytype.trim()+"') " ;
	  
    }
  if(ddh.length()>0){//订单号
	  
	  //System.out.println(type);
   
	  sql+=getStrings(ddh,"ldx.make_order_num");
	  

  }
  if(gd.length()>0)
  {
	  
	  sql+=getStrings(gd,"ddh.work_order_id"); 
	   
  }
   
 if(kb.length()>0){
	 
	 sql+=getStrings(kb,"hplg.descriptions");
	 
	 
 }
 if(xb.length()>0){
	 
	 sql+=getStrings(xb,"hpl.descriptions");
	 
 }
 if(bc.length()>0){
	 
	 sql+=getStrings(bc,"ddh.shift_code");
	 
	 
 }
 if(guest.length()>0){
	 
	 sql+=getStrings(guest,"ldx.customer");
	 
 }  
 if(xs.length()>0){
	 
	 sql+=getStrings(xs,"ddh.shift_code");
	 
	 
 }
 if(soh.length()>0){
	 
	 sql+=getStrings(soh,"ldx.so_number");
	 
 }  
 
 sql+="  order by hp.plant_code,\n" + 
			"          hplg.descriptions,\n" + 
			"          hpl.descriptions,\n" + 
			"          ddh.work_order_id,\n" + 
			"          ddh.document_number,\n" + 
			"          ddh.creation_date,\n" + 
			"           ddl.document_line_id\n" + 
			"";
 
 System.out.println(sql);
    	
		 Connection conn = null;//表示数据库连接
         Statement stmt= null;//表示数据库的更新
         ResultSet result = null;//查询数据库    
         ResultSet rs=null;
         int i=0;
        
         try {
        	 Class.forName(drive);//使用class类来加载程序
			conn =DriverManager.getConnection(url,DBUSER,password);
			  stmt = conn.createStatement();
		         results=new String[maxnum][20];
		        
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
		        results[i][12]=result.getString(13);
		        results[i][13]=result.getString(14);
		        results[i][14]=result.getString(15);
		        results[i][15]=result.getString(16);
		        results[i][16]=result.getString(17);
		        results[i][17]=result.getString(18);
		        results[i][18]=result.getString(19);
		        results[i][19]=result.getString(20);
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
         com.Filter(results, i, 20);
         
         System.out.println(i);
    
         if(i>0){
         model.addAttribute("bj", "1");
         }
         else {
         model.addAttribute("bj", "0");
         }
         model.addAttribute("totalnum",i);
         
         
  
       
         
         String tjshuju="";  
		  tjshuju+="生产工厂"+"@@";
		  tjshuju+="工厂描述"+"@@";
		  tjshuju+="生产课别描述"+"@@";
		  tjshuju+="线别描述"+"@@";
		  tjshuju+="生产班次"+"@@";
		  tjshuju+="工单ID"+"@@";
		  tjshuju+="生产批号"+"@@";
		  tjshuju+="物料编码"+"@@";
		  tjshuju+="物料段描述"+"@@";
		  tjshuju+="客户简称"+"@@";
		  tjshuju+="销售订单"+"@@";
		  tjshuju+="销售订单行项目"+"@@";
		  tjshuju+="创建人"+"@@";
		  tjshuju+="创建时间"+"@@";
		  tjshuju+="工序号"+"@@";
		  tjshuju+="是否关键工序"+"@@";
		  tjshuju+="检查项目"+"@@";
		  tjshuju+="检查结果"+"@@";
		  tjshuju+="判定"+"@@";
		  tjshuju+="判定文本"+"@@";
		  
		  
		  

   tjshuju+="##";

 for(int k=0;k<i;k++){
  	      
             for(int j=0;j<20;j++){
          	   
          	  // System.out.println(results[k][j]);
          	   
          	   tjshuju+=results[k][j]+"@@";
          	   
          	   
             }
             
             tjshuju+="##";
     }
   
 
 System.out.println(tjshuju);
 
   model.addAttribute("tjshuju",tjshuju);
        
         model.addAttribute("result",results);
         
         
         
     // 下面是为了满足SELECT语句
         
         
         ArrayList list=new ArrayList();
         ArrayList list2=new ArrayList();
         String sql3=" select  distinct v.meaning  from hfwk_lookup_types_v v where v.lookup_type_code = 'DROID_DOCUMENT_TYPE'";
         String sql2="select distinct v.meaning from hfwk_lookup_types_v v where v.lookup_type_code = 'DROID_DOCUMENT_STATUS'";
         Connection conn3 = null;//表示数据库连接
         Statement stmt3= null;//表示数据库的更新
         ResultSet result3 = null;//查询数据库    
         ResultSet rs3=null;
         Connection conn2 = null;//表示数据库连接
         Statement stmt2= null;//表示数据库的更新
         ResultSet result2 = null;//查询数据库    
         ResultSet rs2=null;
         Class.forName(drive);//使用class类来加载程序
         try {
			conn3 =DriverManager.getConnection(url,DBUSER,password);
			   stmt3 = conn3.createStatement();
		         result3 =stmt3.executeQuery(sql3);
		         while(result3.next()){
		        	 
		        	list.add(result3.getString(1));
		        	 
		         }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //连接数据库
 finally{
        	 
        	 
        	 try {//关闭之前的连接
				
				
	             result3.close();   
	             stmt3.close();
	             conn3.close();
	            
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        	 
         }
         
     
         
         try {
			conn2=DriverManager.getConnection(url,DBUSER,password);
			  stmt2 = conn2.createStatement();
		         result2 =stmt2.executeQuery(sql2);
		         
		         while(result2.next()){
		        	 
		        	list2.add(result2.getString(1));
		        	 
		         }
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
  finally{
        	 
        	 
        	 try {//关闭之前的连接
				
				
	             result2.close();   
	             stmt2.close();
	             conn2.close();
	            
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        	 
         }
       
         
        
        
            model.addAttribute("totalnum",i);//totalnum表示总条数   
            model.addAttribute("po",15);//po表示每页显示10条 
            model.addAttribute("page",1);//当前页
        	model.addAttribute("list",list);
        	model.addAttribute("list2",list2);
   
            return forward("/mps/sjxj");// 返回成功
         
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

	