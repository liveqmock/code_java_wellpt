package com.wellsoft.pt.ldx.web.mps;

import java.awt.List;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.wellsoft.pt.core.support.ExtendedPropertyPlaceholderConfigurer;
import com.wellsoft.pt.core.web.BaseController;
@Controller
@Scope("prototype")
@RequestMapping("/mps")
public class EnterStaff extends BaseController {
	
	
	
	
	private  String  drive ;
	private  String  url;
	private  String  DBUSER;
    private  String  password;
    @Autowired
    private  ExtendedPropertyPlaceholderConfigurer pro;
    
	
	@RequestMapping("/estaff")
	public String estaff(Model model) {
		
		String bj="-1";
		model.addAttribute("bj",bj);
		return forward("/mps/staff");
	}
	
	@RequestMapping("/eerwh")
	public String eerwh(Model model) {
		
		String bj="-1";
		model.addAttribute("bj",bj);
		return forward("/mps/erwh");
	}
	@RequestMapping("/ewnode")
	public String ewnode(Model model) {
		
		String bj="-1";
		model.addAttribute("bj",bj);
		return forward("/mps/wnode");
	}
	
	@RequestMapping("/ecrtime")
	public String ecrtime(Model model){
		
		String bj="-1";
		model.addAttribute("bj",bj);
		return forward("/mps/crtime");
	}
	
	
	@RequestMapping("/eerwhtj")
	public String erwhtj(Model model){
		
		String bj="-1";
		model.addAttribute("bj",bj);
		return forward("/mps/erwhtj");
	}
	

@RequestMapping("/ezcwf")
	public String ezcwf(Model model){
		
		String bj="-1";
		model.addAttribute("bj",bj);
		return forward("/mps/zcwf");
	}



@RequestMapping("/ecxbg")
public String ecxbg(Model model){
	
	String bj="-1";
	model.addAttribute("bj",bj);
	return forward("/mps/cxbg");
}


@RequestMapping("/ecxsj")
public String ecxsj(Model model){
	
	String bj="-1";
	model.addAttribute("bj",bj);
	return forward("/mps/cxsj");
}

@RequestMapping("/egd")
public String egd(Model model){
	
	String bj="-1";
	model.addAttribute("bj",bj);
	return forward("/mps/gd");
}
@RequestMapping("/ewl")
public String ewl(Model model){
	
	String bj="-1";
	model.addAttribute("bj",bj);
	return forward("/mps/wl");
}
@RequestMapping("/egdsx")
public String egdsx(Model model){
	
	String bj="-1";
	model.addAttribute("bj",bj);
	return forward("/mps/gdsx");
}
@RequestMapping("/esjxj")
public String esjxj(Model model) throws ClassNotFoundException{
	
	
	
	//前台存在2个list 
	
	
	 drive=pro.getProperty("multi.tenancy.lms.drive").toString();
     url=pro.getProperty("multi.tenancy.lms.dburl").toString();
     DBUSER=pro.getProperty("multi.tenancy.lms.dbuser").toString();
     password=pro.getProperty("multi.tenancy.lms.dbpassword").toString();
 
 ArrayList list=new ArrayList();
 ArrayList list2=new ArrayList();
 String sql=" select  distinct v.meaning  from hfwk_lookup_types_v v where v.lookup_type_code = 'DROID_DOCUMENT_TYPE'";
 String sql2="select distinct v.meaning from hfwk_lookup_types_v v where v.lookup_type_code = 'DROID_DOCUMENT_STATUS'";
 Connection conn = null;//表示数据库连接
 Statement stmt= null;//表示数据库的更新
 ResultSet result = null;//查询数据库    
 ResultSet rs=null;
 Connection conn2 = null;//表示数据库连接
 Statement stmt2= null;//表示数据库的更新
 ResultSet result2 = null;//查询数据库    
 ResultSet rs2=null;
 
 
 
 
 Class.forName(drive);//使用class类来加载程序
 try {
	conn =DriverManager.getConnection(url,DBUSER,password);
	 stmt = conn.createStatement();
	 result =stmt.executeQuery(sql);
	 while(result.next()){
		 
		list.add(result.getString(1));
		 
	 }
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}finally{
	 
	 
	 try {//关闭之前的连接
		
		
        result.close();   
        stmt.close();
        conn.close();
       
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
	// TODO Auto-generated catch block
	e.printStackTrace();
}finally{
	 
	 
	 try {//关闭之前的连接
		
		
       result2.close();   
       stmt2.close();
       conn2.close();
      
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  
	 
}
 
	String bj="-1";
	model.addAttribute("bj",bj);
	model.addAttribute("list",list);
	model.addAttribute("list2",list2);
	return forward("/mps/sjxj");
}
}
