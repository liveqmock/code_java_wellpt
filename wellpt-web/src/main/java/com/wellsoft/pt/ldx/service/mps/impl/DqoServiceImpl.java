package com.wellsoft.pt.ldx.service.mps.impl;
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
import com.wellsoft.pt.ldx.service.mps.DqoService;
import com.wellsoft.pt.ldx.model.mps.Dqoview;

@Service
@Transactional
public class DqoServiceImpl extends MpsImpl implements DqoService {

	@Override
	public Map deleteObjects(String s) {
	
		 String[] strs=s.split("@@");
		   for(int i=0;i<strs.length;i++){
			   
			   String sql2="delete  droid_question_options where option_id="+strs[i];
			   String sql3="delete  droid_question_options where option_id="+strs[i];
			   this.mpsDao.getSession().createSQLQuery(sql2).executeUpdate();//必须先删除子数据  在删除主数据
			   this.mpsDao.getSession().createSQLQuery(sql3).executeUpdate();//必须先删除子数据  在删除主数据
		   }
		return null;
	}
	@Override
	public Dqoview getObject(String qid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Dqoview> getObjects(String sql) {
		// TODO Auto-generated method stub
		
		//获取object 
		
		
		List<Object> objects = this.mpsDao.getSession().createSQLQuery(sql).list();
		List<Dqoview> dqo = new ArrayList<Dqoview>();     
        for(Object os:objects){//数据待附值
        	
        	//字符串是否允许为空  level2 ,level3,last_update_login,question_type,operation_seq,is_key
        	//如果在数据库中这些项为空 那么把他赋值成" "
        	for(int i=0;i<8;i++)
        		if(((Object[]) os)[i]==null){
        			 
        			((Object[]) os)[i]="";
        			
        		}
        	Dqoview dqos=new Dqoview();
        	dqos.setQuestion_id(((Object[]) os)[0].toString());
            dqos.setOption_id(((Object[]) os)[1].toString());
            dqos.setOption_text(((Object[]) os)[2].toString());
            dqos.setCreated_by(((Object[]) os)[3].toString());
            dqos.setCreation_date(((Object[]) os)[4].toString());
            dqos.setLast_update_by(((Object[]) os)[5].toString());
            dqos.setLast_update_date(((Object[]) os)[6].toString());
            dqos.setLast_update_login(((Object[]) os)[7].toString());
            dqo.add(dqos);
        }
   return dqo;   
		
	
	}

	@Override
	public String addobjects(List<Dqoview> dqoview) {
		
		for(Dqoview dqo:dqoview){
			
			String sql="insert into droid_question_options values( ";
			sql+="' "+dqo.getQuestion_id()+" ',";
			sql+="droid_question_options_s.nextval,";
			sql+="' "+dqo.getOption_text()+" ',";
			sql+="-1," ;//create_by
		    sql+="sysdate,";
		    sql+="-1 ,";
		    sql+="sysdate,";
		    sql+="-1";
			sql+=" )";
			this.mpsDao.getSession().createSQLQuery(sql).executeUpdate();// 
		}
		return null;
	}
	@Override
	public String addobject() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public String addorupdateobject(String sql) {
		// TODO Auto-generated method stub 执行单行数据插入
	  
	    this.mpsDao.getSession().createSQLQuery(sql).executeUpdate();
	    return null;
	}
}
