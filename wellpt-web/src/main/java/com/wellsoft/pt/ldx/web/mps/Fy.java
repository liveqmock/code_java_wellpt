package com.wellsoft.pt.ldx.web.mps;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
public class Fy extends BaseController {
	
	
	//分页技术
	private  String  drive ;
	private  String  url;
	private  String  DBUSER;
    private  String  password;
    private  String[][]results;
    private int maxnum=1000000;
    @Autowired
    private  ExtendedPropertyPlaceholderConfigurer pro;
	
	@RequestMapping("/up")
	public String up(@RequestParam(value = "shuju") String shuju,@RequestParam(value = "page") String page,@RequestParam(value = "op") String op,
			@RequestParam(value = "adr") String adr,	Model model) throws ClassNotFoundException, SQLException {
		//前一页
		
		//shuju 表示数据  page表示当前的页面  op表示每页显示条数  默认没页显示10条
		
		int i=0;

		  drive=pro.getProperty("multi.tenancy.lms.drive").toString();
          url=pro.getProperty("multi.tenancy.lms.dburl").toString();
          DBUSER=pro.getProperty("multi.tenancy.lms.dbuser").toString();
          password=pro.getProperty("multi.tenancy.lms.dbpassword").toString();
		
   //result数组用来保存所有的数据
		
	
	String ts1[]=shuju.split("##");
    	String ts2[]=ts1[1].split("@@");
    	String [][]result=new String[ts1.length-1][ts2.length];
    	for(int k=0;k<result.length;k++){
    		i++;
    		String tss2[]=ts1[k+1].split("@@");
    		for(int q=0;q<tss2.length;q++){
    			result[k][q]=tss2[q];
    			
    		}
    	}
	
 //绳命一个result数组 用来保存当前显示的数据
    	int pages=Integer.parseInt(page);
    	if(pages>1)
    		pages--;
    	else
    		pages=1;
    	
   //当前处于page页
    	 model.addAttribute("totalnum",ts1.length-1);//totalnum表示总条数   
         model.addAttribute("po",15);//po表示每页显示10条 
         model.addAttribute("page",pages);//当前页
    	 model.addAttribute("bj", "1");
    	
    	 model.addAttribute("result",result);
		 model.addAttribute("tjshuju",shuju);
		 
		 model.addAttribute("list",getlist());
     	 model.addAttribute("list2",getlist2());
		 
		 
		 return forward(adr);// 返回成功
	}
	
	@RequestMapping("/down")
	public String down(@RequestParam(value = "shuju") String shuju,@RequestParam(value = "page") String page,@RequestParam(value = "op") String op,
			@RequestParam(value = "adr") String adr,	Model model) throws ClassNotFoundException, SQLException {
		 //后一页
		
		int i=0;
		  drive=pro.getProperty("multi.tenancy.lms.drive").toString();
          url=pro.getProperty("multi.tenancy.lms.dburl").toString();
          DBUSER=pro.getProperty("multi.tenancy.lms.dbuser").toString();
          password=pro.getProperty("multi.tenancy.lms.dbpassword").toString();
		
		   //result数组用来保存所有的数据
				
			
			String ts1[]=shuju.split("##");
		    	String ts2[]=ts1[1].split("@@");
		    	String [][]result=new String[ts1.length-1][ts2.length];
		    	for(int k=0;k<result.length;k++){
		    		i++;
		    		String tss2[]=ts1[k+1].split("@@");
		    		for(int q=0;q<tss2.length;q++){
		    			result[k][q]=tss2[q];
		    			
		    		}
		    	}
			
		    	
		    	
		 //绳命一个result数组 用来保存当前显示的数据
		    	
		    	op=op.replace(" ", "");
		    	page=page.replace(" ","");
		    	
		    
		    	int tps=i>Integer.parseInt(op)?i/Integer.parseInt(op):1;
		    	if(tps!=1){
		    	int ts=tps%Integer.parseInt(op)>0?1:0;
		    	tps+=ts;//tps是总共多少页
		    	}
		    	int pages=Integer.parseInt(page);
		    	if(pages<tps)
		    		pages++;
		    	
		    	
		   //当前处于page页
		    	 model.addAttribute("totalnum",ts1.length-1);//totalnum表示总条数   
		         model.addAttribute("po",15);//po表示每页显示10条 
		         model.addAttribute("page",pages);//当前页
		    	 model.addAttribute("bj", "1");
		    	
		    	 model.addAttribute("result",result);
				 model.addAttribute("tjshuju",shuju);
				 model.addAttribute("list",getlist());
		     	 model.addAttribute("list2",getlist2());
				 return forward(adr);// 返回成功
	}
	@RequestMapping("/first")
	public String first(@RequestParam(value = "shuju") String shuju,@RequestParam(value = "page") String page,@RequestParam(value = "op") String op,
			@RequestParam(value = "adr") String adr,Model model) throws ClassNotFoundException, SQLException {
		 //首页
		
		int i=0;
		
		   //result数组用来保存所有的数据
				
		  drive=pro.getProperty("multi.tenancy.lms.drive").toString();
          url=pro.getProperty("multi.tenancy.lms.dburl").toString();
          DBUSER=pro.getProperty("multi.tenancy.lms.dbuser").toString();
          password=pro.getProperty("multi.tenancy.lms.dbpassword").toString();
          
			String ts1[]=shuju.split("##");
		    	String ts2[]=ts1[1].split("@@");
		    	String [][]result=new String[ts1.length-1][ts2.length];
		    	for(int k=0;k<result.length;k++){
		    		String tss2[]=ts1[k+1].split("@@");
		    		i++;
		    		for(int q=0;q<tss2.length;q++){
		    			result[k][q]=tss2[q];
		    			
		    		}
		    	}
			
		 //绳命一个result数组 用来保存当前显示的数据
		    	int pages=Integer.parseInt(page);
		    	pages=1; //设置pages=1
		    	
		   //当前处于page页
		    	 model.addAttribute("totalnum",ts1.length-1);//totalnum表示总条数   
		         model.addAttribute("po",15);//po表示每页显示10条 
		         model.addAttribute("page",pages);//当前页
		    	 model.addAttribute("bj", "1");
		    	
		    	 model.addAttribute("result",result);
				 model.addAttribute("tjshuju",shuju);
				 model.addAttribute("list",getlist());
		     	 model.addAttribute("list2",getlist2());
				 return forward(adr);// 返回成功
	}
	
	@RequestMapping("/last")
	public String last(@RequestParam(value = "shuju") String shuju,@RequestParam(value = "page") String page,@RequestParam(value = "op") String op,
			@RequestParam(value = "adr") String adr,Model model) throws ClassNotFoundException, SQLException {
		
		int i=0;
		
		   //result数组用来保存所有的数据
		  drive=pro.getProperty("multi.tenancy.lms.drive").toString();
          url=pro.getProperty("multi.tenancy.lms.dburl").toString();
          DBUSER=pro.getProperty("multi.tenancy.lms.dbuser").toString();
          password=pro.getProperty("multi.tenancy.lms.dbpassword").toString();
			
			String ts1[]=shuju.split("##");
		    	String ts2[]=ts1[1].split("@@");
		    	String [][]result=new String[ts1.length-1][ts2.length];
		    	for(int k=0;k<result.length;k++){
		    		i++;
		    		String tss2[]=ts1[k+1].split("@@");
		    		for(int q=0;q<tss2.length;q++){
		    			result[k][q]=tss2[q];
		    			
		    		}
		    	}
		    	
		    	
		    	op=op.replace(" ", "");
		    	page=page.replace(" ","");
		    	int tps=0;
		    	if(i>Integer.parseInt(op))
		    	tps=i/Integer.parseInt(op);
		    	else 
		    	tps=1;
		    	if(i%Integer.parseInt(op)!=0&&tps!=1){

		    		tps+=1;
		    	}
		    	
		    	
		    	
		    	
		    	int pages=Integer.parseInt(page);
		    	pages=tps;//跳转到最后一个页面
		    	
		    	
		    	
		    	
		   //当前处于page页
		    	 model.addAttribute("totalnum",ts1.length-1);//totalnum表示总条数   
		         model.addAttribute("po",15);//po表示每页显示10条 
		         model.addAttribute("page",pages);//当前页
		    	 model.addAttribute("bj", "1");
		    	 model.addAttribute("result",result);
				 model.addAttribute("tjshuju",shuju);
				 model.addAttribute("list",getlist());
		     	 model.addAttribute("list2",getlist2());
				 return forward(adr);// 返回成功
	}
	
	
	
	
	public ArrayList  getlist() throws SQLException, ClassNotFoundException{
		
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
        conn3 =DriverManager.getConnection(url,DBUSER,password); //连接数据库
        stmt3 = conn3.createStatement();
        result3 =stmt3.executeQuery(sql3);
        while(result3.next()){
       	 
       	list.add(result3.getString(1));
       	 
        }
        conn3.close();
        stmt3.close();
        result3.close();
        
        conn2=DriverManager.getConnection(url,DBUSER,password);
        stmt2 = conn2.createStatement();
        result2 =stmt2.executeQuery(sql2);
        
        while(result2.next()){
       	 
       	list2.add(result2.getString(1));
       	 
        }
        conn2.close();
        stmt2.close();
        result2.close();
       
       	return list;
	}
	
	
public ArrayList  getlist2() throws SQLException, ClassNotFoundException{
		
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
        conn3 =DriverManager.getConnection(url,DBUSER,password); //连接数据库
        stmt3 = conn3.createStatement();
        result3 =stmt3.executeQuery(sql3);
        while(result3.next()){
       	 
       	list.add(result3.getString(1));
       	 
        }
        conn3.close();
        stmt3.close();
        result3.close();
        
        conn2=DriverManager.getConnection(url,DBUSER,password);
        stmt2 = conn2.createStatement();
        result2 =stmt2.executeQuery(sql2);
        
        while(result2.next()){
       	 
       	list2.add(result2.getString(1));
       	 
        }
        conn2.close();
        stmt2.close();
        result2.close();
       
       	return list2;
	}
	
	
	
	
	
	
}