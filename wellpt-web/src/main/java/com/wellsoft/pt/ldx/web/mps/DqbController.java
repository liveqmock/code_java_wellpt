package com.wellsoft.pt.ldx.web.mps;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.fileupload.util.Streams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.model.mps.Dqbview;
import com.wellsoft.pt.ldx.model.mps.Dqoview;
import com.wellsoft.pt.ldx.model.mps.Hisview;
import com.wellsoft.pt.ldx.service.mps.DqbService;
import com.wellsoft.pt.ldx.service.mps.DqoService;
@Controller
@Scope("prototype")
@RequestMapping("/mps")
public class DqbController  extends BaseController{

	private String [][]result;
	@Autowired
	private DqbService dqbs;
	@Autowired
	private DqoService dqos;
	private String showerror;
	@RequestMapping("/finddqb")
	public String Dqbfindbyid(@RequestParam(value = "qid") String qid,Model model) {
	String sql="select * from  DROID_QUESTION_bank where  question_id=' "+qid+" '" ;
	List<Dqbview> dqbv = new ArrayList<Dqbview>();
	dqbv=dqbs.getObjects(sql);//显示数据
	model.addAttribute("dqbv",dqbv);//dqbv
	for(int i=0;i<dqbv.size();i++)
		    System.out.println(dqbv.get(i).getQuestion_id());
	return forward("/mps/question/te2");// 返回测试页面
	}
	@RequestMapping("/updqb")
	public String Updqb(@RequestParam(value = "attachment") MultipartFile attachment,Model model){
		//该函数实现EXCEL表格上传数据的效果
		   try {  
	            InputStream stream  = attachment.getInputStream();
	            Streams.copy(stream, new FileOutputStream("d:/mb/question.xls"), true);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } 
		   try {  
	            String fileName = "D://mb/question.xls"; // Excel文件所在路径  
	            File file = new File(fileName); // 创建文件对象  
	            Workbook wb = Workbook.getWorkbook(file); // 从文件流中获取Excel工作区对象（WorkBook）  
	            Sheet sheet = wb.getSheet(0); // 从工作区中取得页（Sheet）  
	           result=new String[sheet.getRows()][sheet.getColumns()];
			  // System.out.println(sheet.getColumns());//
	           for (int i = 4; i < sheet.getRows(); i++) { // 循环打印Excel表中的内容  
	                for (int j = 0; j < sheet.getColumns(); j++) {  
	                    Cell cell = sheet.getCell(j, i);  
	                    System.out.println(cell.getContents());  
	                     result[i][j]=cell.getContents();
	                    // System.out.println(result[i][j]);
	                }  
	                System.out.println();  
	            }  
	        } catch (BiffException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	//针对上面的内容 把数据填写到数据库 分别填写到2个库中   
		 //测试该功能是否可以使用  
		//   dqbs.addobject();
		   List<Dqbview> dqb=new ArrayList<Dqbview>();
		   List <Dqoview> dqo =new ArrayList<Dqoview>();
		   showerror="";
		   for (int i = 4; i <result.length; i++) {
			   Dqbview ob=new  Dqbview();
			 if(pd(result[i][0],result[i][1],result[i][2],result[i][4],i)==null){
			      result[i][4]=dqbs.gettype(result[i][4]);//meaning 转CODE
				   //根据前6个数据插入到数据库表中
				   ob.setLevel_1(result[i][0]);
				   ob.setLevel_2(result[i][1]);
				   ob.setLevel_3(result[i][2]);
				   ob.setQuestion_text(result[i][3]);
				   ob.setQuestion_type(result[i][4]);//题库类型插入需要等待修改
				   ob.setOperation_seq(result[i][5]);
				   ob.setIs_key(result[i][6]);
			       dqb.add(ob);
			 }
			 else{
				 showerror+=pd(result[i][0],result[i][1],result[i][2],result[i][4],i)+" <br /> ";
			 }
			   //根据之前插入的数据获取QUESTION_ID 
			   //根据之后的数据插入到OPTION表
		   }
	//判断如果数据存在错误就直接退出
		   System.out.println(showerror);
		   if(showerror.length()>0){
			  showerror="题库数据导入失败 数据存在错误 具体错误信息显示如下: <br />"+showerror; 
		   }
		   else{  
			System.out.println("yes");
		   showerror="题库数据导入成功 ";
		   dqbs.addobjects(dqb);//执行插入到数据库的操作
		   for(int i=4;i<result.length;i++){
			  // result[i][4]=dqbs.gettype(result[i][4]);//meaning 转CODE
			   Dqbview ob=new  Dqbview();
			   ob.setLevel_1(result[i][0]);
			   ob.setLevel_2(result[i][1]);
			   ob.setLevel_3(result[i][2]);
			   ob.setQuestion_text(result[i][3]);
			   ob.setQuestion_type(result[i][4]);//题库类型插入需要等待修改
			   ob.setOperation_seq(result[i][5]);
			   ob.setIs_key(result[i][6]);
			   String qid=dqbs.getQuestion_id(ob);
			   int j=7;
			   while(result[i][j]!=null&&result[i][j].length()>0){
			   Dqoview ov=new Dqoview();
			   ov.setQuestion_id(qid);
			   ov.setOption_text(result[i][j]);
			   dqo.add(ov);
			   j++;
			  }  
		   }
		   dqos.addobjects(dqo);
		   }
		   model.addAttribute("resut", showerror);
		   return  forward("/mps/question/result");//跳转到te2 页面
	}
	@RequestMapping("/enteradd")
	public String Enteradd(Model model){
	//  进入添加页面	
		
		 return  forward("/mps/question/adddqb");//跳转到te2 页面
	}
	@RequestMapping("/adddqb")
	public String Adddqb(@RequestParam(value = "level_1") String level_1,@RequestParam(value = "level_2") String level_2,@RequestParam(value = "level_3") String level_3,@RequestParam(value = "question_text") String question_text,@RequestParam(value = "question_type") String question_type,@RequestParam(value = "operation_seq") String operation_seq,@RequestParam(value = "is_key") String is_key,Model model) throws UnsupportedEncodingException{
	//  进入添加页面	
		
		//出现中文乱码问题  临时处理方法
		
		    System.out.println(question_text);
	     	System.out.println(question_type);
	     	System.out.println(operation_seq);
	     	System.out.println(is_key);
	     //	question_text=new String(question_text.getBytes("GBK"),"UTF-8");
	     	System.out.println(question_text);
		    question_type=dqbs.gettype(question_type);
		    String sql="insert into droid_question_bank values( droid_question_bank_s.nextval, ";
			sql+="'"+level_1+"',";
			sql+="'"+level_2+"',";
			sql+="'"+level_3+"',";
			sql+="'"+question_text+" ',";
			sql+="-1," ;//create_by
		    sql+="sysdate,";
		    sql+="-1 ,";
		    sql+="sysdate,";
		    sql+="-1 ,";
		    sql+="'"+question_type+"',";
		    sql+="'"+operation_seq+"',";
		    sql+="'"+is_key+"' ";
			sql+=" )";
		    dqbs.addorupdateobject(sql);
			model.addAttribute("resut", "题目添加成功");
		    return  forward("/mps/question/result");//跳转到te2 页面
	}
	
	@RequestMapping("/enterupdate")
	public String Enterupdate(@RequestParam(value = "qid") String qid,Model model){
	//  进入更新页面	必须将所有数据查询出来
		
		Dqbview dqb=new Dqbview();
		dqb=dqbs.getObject(qid);
	    model.addAttribute("question_id", dqb.getQuestion_id());
	    model.addAttribute("level_1",dqb.getLevel_1());
	    model.addAttribute("level_2",dqb.getLevel_2());
	    model.addAttribute("level_3",dqb.getLevel_3());
	    model.addAttribute("question_text",dqb.getQuestion_text());
	    model.addAttribute("question_type",dqbs.getmeaning(dqb.getQuestion_type()));
	    model.addAttribute("operation_seq",dqb.getOperation_seq());
	    model.addAttribute("is_key",dqb.getIs_key());
	   // System.out.println(dqb.getOperation_seq().length());
	   //在进入update 之前必须查询答案对应的数据并显示 
	  List<Dqoview>  dqov=  dqos.getObjects("select * from droid_question_options where question_id="+qid);
	  String  [][]chuangdi=new String[dqov.size()][8];

	  int i=0;
	  for(Dqoview dq:dqov){
		chuangdi[i][0]=dq.getQuestion_id();
		chuangdi[i][1]=dq.getOption_id();
		chuangdi[i][2]=dq.getOption_text();
		chuangdi[i][3]=dq.getCreated_by();
		chuangdi[i][4]=dq.getCreation_date();
		chuangdi[i][5]=dq.getLast_update_by();
		chuangdi[i][6]=dq.getLast_update_date();
		chuangdi[i][7]=dq.getLast_update_login();
		i++;
	  }
	    
	    model.addAttribute("option",chuangdi);
		return  forward("/mps/question/updatedqb");
		
	}
	@RequestMapping("/updatedqb")
	public String Updatedqb(@RequestParam(value = "question_id") String question_id,@RequestParam(value = "level_1") String level_1,@RequestParam(value = "level_2") String level_2,@RequestParam(value = "level_3") String level_3,@RequestParam(value = "question_text") String question_text,@RequestParam(value = "question_type") String question_type,@RequestParam(value = "operation_seq") String operation_seq,@RequestParam(value = "is_key") String is_key,@RequestParam(value = "bs") String bs,@RequestParam(value = "bs2") String bs2,Model model) throws UnsupportedEncodingException{
	//  进入信息修改页面 对信息进行修改

		/*
	    System.out.println(question_text);
     	System.out.println(question_type);
     	System.out.println(operation_seq);
     	System.out.println(is_key);
     	question_text=new String(question_text.getBytes("ISO8859-1"),"GBK");
     	question_type=new String(question_type.getBytes("ISO8859-1"),"GBK");
     	operation_seq=new String(operation_seq.getBytes("ISO8859-1"),"GBK");
     	is_key=new String(is_key.getBytes("ISO8859-1"),"GBK");
		*/
		question_type=dqbs.gettype(question_type);
		String 
		sql="update droid_question_bank set ";
		sql+="question_text='"+question_text+"', ";
		sql+="operation_seq='"+operation_seq+"', ";
		sql+="IS_KEY='"+is_key+"', ";
		sql+="created_by=-1,";
		sql+="creation_date=sysdate,";
		sql+="last_updated_by=-1,";
		sql+="last_update_date=sysdate,";
		sql+="last_update_login=-1, ";
		sql+="level_1='"+level_1+"', ";
		sql+="level_2='"+level_2+"', ";
		sql+="level_3='"+level_3+"', ";
		sql+="question_type='"+question_type+"' ";
		sql+="where question_id= "+question_id;
		System.out.println(sql);
		System.out.println(question_id);
		dqbs.addorupdateobject(sql);
		
		//更新对应数据
		
		  String[] bss=bs.split("@@");
		  String[] bss2=bs2.split("@@");
		
		 for(int i=0;i<bss.length;i++) {
			 
			String sql2="update droid_question_options set option_text='"+bss2[i];
			sql2+="'  where option_id="+bss[i];
			dqos.addorupdateobject(sql2);
		 }
	  //在更新dqb的同时必须更新对应的dqo	 考虑使用ajax
	   model.addAttribute("resut", "题目以及对应的答案更新成功");
		return  forward("/mps/question/result");//跳转到te2 页面
	}
	
	@RequestMapping("/deletedqb")
	public String deletedqb(@RequestParam(value = "qids") String qids,Model model){
	//  进入更新页面	必须将所有数据查询出来
		
	//qids作为前端的字符串处理  用@@隔离  
		System.out.println(qids);
	/*	//同时删除除了question_bank 和question_options 2个表对应的数据  
		String sql1="delete from  droid_question_bank    where ";
		String sql2="delete from  droid_question_options where ";
	    String[] strs=qids.split("@@");  
	    for(int i=0;i<strs.length;i++)   {
		if(i!=0){
			sql1+=" or ";
			sql2+=" or ";
		 }
		 sql1+="question_id="+strs[i]+"";
		 sql2+="question_id="+strs[i]+"";
	  }
      System.out.println(sql1);
      System.out.println(sql2);
      */
	  dqbs.deleteObjects(qids);
	  model.addAttribute("resut", "题目以及对应的答案删除成功");
      return  forward("/mps/question/result");//删除处理结果 成功或则失败
	}
	
	@RequestMapping("/enterfind")
	public String enterfind(Model model){
	return  forward("/mps/question/index");//进入查询首页
	}
  
 	@RequestMapping("/enteradddqo")
 	public String Enteradddqo(@RequestParam(value = "question_id") String question_id,Model model){
 	//  进入添加答案页面	
 		
 		 model.addAttribute("question_id",question_id);
 		 return  forward("/mps/question/adddqo");//跳转到te2 页面
 	}
	@RequestMapping("/adddqo")
 	public String Adddqo(@RequestParam(value = "question_id") String question_id,@RequestParam(value = "option_text") String option_text,Model model){
 	//  处理添加答案页面	
 		
	
		String sql="insert into droid_question_options values( ";
		sql+="'"+question_id+"',";
		sql+="droid_question_options_s.nextval,";
		sql+="'"+option_text+" ',";
		sql+="-1," ;//create_by
	    sql+="sysdate,";
	    sql+="-1 ,";
	    sql+="sysdate,";
	    sql+="-1 ";
		sql+=" )";
	    dqos.addorupdateobject(sql);
		model.addAttribute("resut", "答案添加成功");
 		return  forward("/mps/question/result");//跳转到te2 页面
 	} 
	   
  @RequestMapping("/deletedqo")
   public String Deletedqo(@RequestParam(value = "optionids") String optionids,Model model){
	 
	  dqos.deleteObjects(optionids);//optionids是一组以@@号拼接的字符串
	  model.addAttribute("resut", "答案删除成功");
      return  forward("/mps/question/result");//跳转到te2 页面
	}  
	
     
     
     
     
     
     private boolean Stringtodouble(String str){
         boolean ret = true;
         try{
             double d = Double.parseDouble(str);
             ret = true;
         }catch(Exception ex){
             ret = false;
         }
         return ret;
     }
     
     public  String pd(String level1,String level2,String level3,String q_type,int i){ //
  	   
  	   //对QUESTION 进行测试 查看输入数据是否存在BUG
      	 if(Stringtodouble(level1)&&Stringtodouble(level2)&&Stringtodouble(level3)&&(dqbs.gettype(q_type)!=null)){
      		return null; 
      	 }
      	 else{
      		 String yy="";
         if(!Stringtodouble(level1)){
      	   
      	   yy+="第"+i+"行题级一数据无效       ";
      	 
         }
       if(!Stringtodouble(level2)){
      	   
      	   yy+="第"+i+"行题级二数据无效      ";
      	  
         }	 
       if(!Stringtodouble(level3)){
    	   yy+="第"+i+"行题级三 数据无效    ";
       }	 	 
       if(dqbs.gettype(q_type)==null){
      	   yy+="第"+i+"题库类型不存在      ";
         }			 
      	return yy; 
      	 }
     }
       

}
