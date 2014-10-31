package com.wellsoft.pt.ldx.service.mps.impl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wellsoft.pt.core.service.impl.BaseServiceImpl;
import com.wellsoft.pt.ldx.dao.maindata.WlGcDao;
import com.wellsoft.pt.ldx.dao.mps.Dqbdao;
import com.wellsoft.pt.ldx.model.mainData.Mmview;
import com.wellsoft.pt.ldx.model.mainData.WlGc;
import com.wellsoft.pt.ldx.model.mps.Dqbview;
import com.wellsoft.pt.ldx.model.mps.Hisview;
import com.wellsoft.pt.ldx.service.impl.MpsImpl;
import com.wellsoft.pt.ldx.service.impl.SapServiceImpl;
import com.wellsoft.pt.ldx.service.mps.DqbService;
import com.wellsoft.pt.ldx.model.mps.Dqbview;

@Service
@Transactional
public class DqbServiceImpl extends MpsImpl implements DqbService {

	
	

	
	@Override
	public Dqbview getObject(String qid) {//寻找实体
	
		String str="select * from droid_question_bank where question_id="+qid;
		List<Object> objects = this.mpsDao.getSession().createSQLQuery(str).list();
		for(Object os:objects){
			  
			  
			for(int i=0;i<13;i++)
        		if(((Object[]) os)[i]==null){
        			 
        			((Object[]) os)[i]="";
        			
        		}
			
			
			Dqbview dqbs=new Dqbview();
			  dqbs.setQuestion_id(((Object[]) os)[0].toString());
	          dqbs.setLevel_1(((Object[]) os)[1].toString());
	          dqbs.setLevel_2(((Object[]) os)[2].toString());
	          dqbs.setLevel_3(((Object[]) os)[3].toString());
	          dqbs.setQuestion_text(((Object[]) os)[4].toString());
	          dqbs.setCreated_by(((Object[]) os)[5].toString());
	          dqbs.setCreation_date(((Object[]) os)[6].toString());
	          dqbs.setLast_updated_by(((Object[]) os)[7].toString());
	          dqbs.setLast_update_date(((Object[]) os)[8].toString());
	          dqbs.setLast_update_login(((Object[]) os)[9].toString());
	          dqbs.setQuestion_type(((Object[]) os)[10].toString());
	          dqbs.setOperation_seq(((Object[]) os)[11].toString());
	          dqbs.setIs_key(((Object[]) os)[12].toString()); 
			  
	          
	          // 这里的OPERATION 为" "
			 return dqbs; 
		  }
		return null;
	
	}
	@Override
	public List<Dqbview> getObjects(String sql) {
		List<Object> objects = this.mpsDao.getSession().createSQLQuery(sql).list();
		List<Dqbview> dqb = new ArrayList<Dqbview>();     
        for(Object os:objects){//数据待附值
        	
        	//字符串是否允许为空  level2 ,level3,last_update_login,question_type,operation_seq,is_key
        	//如果在数据库中这些项为空 那么把他赋值成" "
        	for(int i=0;i<13;i++)
        		if(((Object[]) os)[i]==null){
        			 
        			((Object[]) os)[i]="";
        			
        		}
        	Dqbview dqbs=new Dqbview();
            dqbs.setQuestion_id(((Object[]) os)[0].toString());
            dqbs.setLevel_1(((Object[]) os)[1].toString());
            dqbs.setLevel_2(((Object[]) os)[2].toString());
            dqbs.setLevel_3(((Object[]) os)[3].toString());
            dqbs.setQuestion_text(((Object[]) os)[4].toString());
            dqbs.setCreated_by(((Object[]) os)[5].toString());
            dqbs.setCreation_date(((Object[]) os)[6].toString());
            dqbs.setLast_updated_by(((Object[]) os)[7].toString());
            dqbs.setLast_update_date(((Object[]) os)[8].toString());
            dqbs.setLast_update_login(((Object[]) os)[9].toString());
            dqbs.setQuestion_type(((Object[]) os)[10].toString());
            dqbs.setOperation_seq(((Object[]) os)[11].toString());
            dqbs.setIs_key(((Object[]) os)[12].toString());
            dqb.add(dqbs);
        }
   return dqb;   
	}
	@Override
	public String addobjects(List<Dqbview>dqbview) {
		
		
		
		//添加数据  主键不存在重复的问题  再添加的时候必须判断是覆盖还是直接添加  
		for(Dqbview dqb:dqbview){
			
		if(getQuestion_id(dqb)!=null){
			
		//把之前的答案的数据删除
			String sql2="delete  droid_question_options where question_id="+getQuestion_id(dqb);
       //跟新新的题目
			String sql="update droid_question_bank set ";
			sql+="question_text='"+dqb.getQuestion_text()+"', ";
			sql+="operation_seq='"+dqb.getOperation_seq()+"', ";
			sql+="IS_KEY='"+dqb.getIs_key()+"', ";
			//5个WHO字段  
			sql+="created_by=-1,";
			sql+="creation_date=sysdate,";
			sql+="last_updated_by=-1,";
			sql+="last_update_date=sysdate,";
			sql+="last_update_login=-1 ";
			sql+=" where question_id="+getQuestion_id(dqb);
			this.mpsDao.getSession().createSQLQuery(sql).executeUpdate();// 
			this.mpsDao.getSession().createSQLQuery(sql2).executeUpdate();// 
			
		}
		else{
		String sql="insert into droid_question_bank values( droid_question_bank_s.nextval, ";
		sql+="'"+dqb.getLevel_1()+"',";
		sql+="'"+dqb.getLevel_2()+"',";
		sql+="'"+dqb.getLevel_3()+"',";
		sql+="'"+dqb.getQuestion_text()+" ',";
		sql+="-1," ;//create_by
	    sql+="sysdate,";
	    sql+="-1 ,";
	    sql+="sysdate,";
	    sql+="-1 ,";
	    sql+="'"+dqb.getQuestion_type()+"',";
	    sql+="'"+dqb.getOperation_seq()+"',";
	    sql+="'"+dqb.getIs_key()+"' ";
		sql+=" )";
		this.mpsDao.getSession().createSQLQuery(sql).executeUpdate();// 
		}
		}
		return null;
	}
	@Override
	public String addorupdateobject(String sql) {
		// TODO Auto-generated method stub 执行单行数据插入
	  
	    this.mpsDao.getSession().createSQLQuery(sql).executeUpdate();
	    return null;
	}
	@Override
	public String getQuestion_id(Dqbview e) {
	
		
	//	System.out.println(e.getLevel1().toString());
		
		
		String sql="select question_id from droid_question_bank where ";
	    sql+="level_1='"+e.getLevel_1()+"' and ";
	    sql+="level_2='"+e.getLevel_2()+"' and ";
	    sql+="level_3='"+e.getLevel_3()+"' and ";
	    sql+="QUESTION_TYPE='"+e.getQuestion_type()+"'";
	   
		List<Object> objects = this.mpsDao.getSession().createSQLQuery(sql).list();
		
		  for(Object os:objects){//数据待附值
			  
			  //确认只有这4个变量可以唯一确认一个数据  所以可以直接处理
		   return  os.toString();
			  
			  
			  
		  }
		
		
		return null;
	}
	

	@Override
	public String gettype(String s){//根据你输入的MEANING 获取CODE
		
		
		String sql="select DISTINCT v.lookup_code s1 from hfwk_lookup_types_v v where v.lookup_type_code = 'DROID_DOCUMENT_TYPE' and v.meaning=";
		sql+=" '"+s+"' ";
		List<Object> objects = this.mpsDao.getSession().createSQLQuery(sql).list();
		for(Object os:objects){//数据待附值
			  
			  //确认只有这4个变量可以唯一确认一个数据  所以可以直接处理
		   return  os.toString();
			  
			  
			  
		  }
		
		
		return null;
	}
	@Override
	public Map savedqb(Dqbview dqb) {
		// TODO Auto-generated method stub
		
	//单个题目数据的添加
		
		
		return null;
	}
	@Override
	public String getmeaning(String s) {
		
		
		String sql="select DISTINCT  v.meaning s1 from hfwk_lookup_types_v v where v.lookup_type_code = 'DROID_DOCUMENT_TYPE' and  v.lookup_code=";
		sql+=" '"+s+"' ";
		List<Object> objects = this.mpsDao.getSession().createSQLQuery(sql).list();
		for(Object os:objects){//数据待附值
			  
			  //确认只有这4个变量可以唯一确认一个数据  所以可以直接处理
		   return  os.toString();  
		  }
		
		return null;
	}
	@Override
	public Map deleteObjects(String qids) {
		// TODO Auto-generated method stub
		/*
		String sql3="delete  droid_question_options where question_id=2554";
		System.out.println(sql3);
		this.mpsDao.getSession().createQuery(sql3).executeUpdate();//必须先删除子数据  在删除主数据
		this.mpsDao.getSession().createQuery(sql1).executeUpdate();
		
	   */
		   String[] strs=qids.split("@@");
		   for(int i=0;i<strs.length;i++){
			   
			   String sql2="delete  droid_question_bank where question_id="+strs[i];
			   String sql3="delete droid_question_options where question_id="+strs[i];
			   this.mpsDao.getSession().createSQLQuery(sql2).executeUpdate();//必须先删除子数据  在删除主数据
			   this.mpsDao.getSession().createSQLQuery(sql3).executeUpdate();//必须先删除子数据  在删除主数据
		   }
	
		return null;
	}
	
	public Dqbview getDqbById(String id){
		
		Dqbview dqbs=new Dqbview();
		
		
		String sql="select * from droid_question_bank where question_id="+id;
		List<Object> objects = this.mpsDao.getSession().createSQLQuery(sql).list();
		  for(Object os:objects){//数据待附值
			  
			  
				for(int i=0;i<13;i++)
	        		if(((Object[]) os)[i]==null){
	        			 
	        			((Object[]) os)[i]="";
	        			
	        		}
				
			    dqbs.setQuestion_id(((Object[]) os)[0].toString());
	            dqbs.setLevel_1(((Object[]) os)[1].toString());
	            dqbs.setLevel_2(((Object[]) os)[2].toString());
	            dqbs.setLevel_3(((Object[]) os)[3].toString());
	            dqbs.setQuestion_text(((Object[]) os)[4].toString());
	            dqbs.setCreated_by(((Object[]) os)[5].toString());
	            dqbs.setCreation_date(((Object[]) os)[6].toString());
	            dqbs.setLast_updated_by(((Object[]) os)[7].toString());
	            dqbs.setLast_update_date(((Object[]) os)[8].toString());
	            dqbs.setLast_update_login(((Object[]) os)[9].toString());
	            dqbs.setQuestion_type(((Object[]) os)[10].toString());
	            dqbs.setOperation_seq(((Object[]) os)[11].toString());
	            dqbs.setIs_key(((Object[]) os)[12].toString());

			return dqbs;
		  }
		return null;
		
	}
	
}
