package com.wellsoft.pt.ldx.model.mps;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.wellsoft.pt.core.entity.IdEntity;


public class Dqoview {


	private String question_id;
	private String option_id;
	private String option_text;
	private String  created_by;
	private String  creation_date;
	private String  last_update_by;
	private String  last_update_date;
	private String  last_update_login;
	
	public Dqoview(){
		
		
	}
	public void setQuestion_id(String question_id) {
		this.question_id = question_id;
	}
	public void setOption_id(String option_id) {
		this.option_id = option_id;
	}
	public void setOption_text(String option_text) {
		this.option_text = option_text;
	}
	public void setCreation_date(String creation_date) {
		this.creation_date = creation_date;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public void setLast_update_by(String last_update_by) {
		this.last_update_by = last_update_by;
	}
	public void setLast_update_date(String last_update_date) {
		this.last_update_date = last_update_date;
	}
	public void setLast_update_login(String last_update_login) {
		this.last_update_login = last_update_login;
	}
	
	
	
	
	
	public String getQuestion_id() {
		return question_id;
	}



	public String getOption_id() {
		return option_id;
	}

	
	
	public String getOption_text() {
		return option_text;
	}


	
	public String getCreated_by() {
		return created_by;
	}
	
	
	public String getCreation_date() {
		return creation_date;
	}
	
	
	public String getLast_update_by() {
		return last_update_by;
	}
	
	
	public String getLast_update_date() {
		return last_update_date;
	}
	
	
	public String getLast_update_login() {
		return last_update_login;
	}

	
}
