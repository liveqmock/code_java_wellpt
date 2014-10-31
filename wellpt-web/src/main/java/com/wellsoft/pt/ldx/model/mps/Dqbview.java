package com.wellsoft.pt.ldx.model.mps;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


@Entity
@Table(name = "droid_question_bank")
@DynamicUpdate
@DynamicInsert
public class Dqbview implements Serializable {

	

	
	@Id
	@Column(name="question_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String  question_id;
	
	
	@Column(name="level_1")
	private String  level_1;
	
	@Column(name="level_2")
	private String  level_2;
	
	
	@Column(name="level_3")
	private String  level_3;
	
	@Column(name="question_text")
	private String  question_text;
	
	
	@Column(name="created_by")
	private String  created_by;
	
	
	@Column(name="creation_date")
	private String  creation_date;
	
	@Column(name="last_updated_by")
	private String  last_updated_by;
	
	@Column(name="last_update_date")
	private String  last_update_date;
	
	@Column(name="last_update_login")
	private String  last_update_login;
	
	
	@Column(name="question_type")
	private String  question_type;
	
	@Column(name="operation_seq")
	private String  operation_seq;
	
	@Column(name="is_key")
	private String  is_key;
	
	public  Dqbview(){
	}
	
	

	public String getQuestion_id() {
		return question_id;
	}

	
	public String getLevel_1() {
		return level_1;
	}
	

	public String getLevel_2() {
		return level_2;
	}
	
	
	public String getLevel_3() {
		return level_3;
	}
	

	public String getQuestion_text() {
		return question_text;
	}
	

	public String getCreated_by() {
		return created_by;
	}
	

	public String getCreation_date() {
		return creation_date;
	}
	

	public String getLast_updated_by() {
		return last_updated_by;
	}
	

	public String getLast_update_date() {
		return last_update_date;
	}
	

	public String getLast_update_login() {
		return last_update_login;
	}
	

	public String getQuestion_type() {
		return question_type;
	}
	
	
	public String getOperation_seq() {
		return operation_seq;
	}
	

	public String getIs_key() {
		return is_key;
	}
	
	
	
	
	public void setQuestion_id(String question_id) {
		if(question_id!=null)
		this.question_id = question_id;
		else
		this.question_id="";
	}
	public void setLevel_1(String level_1) {
		if(level_1!=null)
		this.level_1 = level_1;
		else
		this.level_1="";
	}
public void setLevel_2(String level_2) {

		if(level_2!=null)
		this.level_2 = level_2;
		else
		this.level_2="";
	}
public void setLevel_3(String level_3) {
	if(level_3!=null)
	this.level_3 = level_3;
	else
	this.level_3="";

}
public void setQuestion_text(String question_text) {
	if(question_text !=null)
	this.question_text = question_text;
	else
    this.question_text ="";
}

public void setCreated_by(String created_by) {
	if(created_by!=null)
	this.created_by = created_by;
	else
	this.created_by="";
}
public void setCreation_date(String creation_date) {
	if(creation_date!=null)
	this.creation_date = creation_date;
	else
	this.creation_date ="";
}	
public void setLast_updated_by(String last_updated_by) {
	if(last_updated_by!=null)
	this.last_updated_by = last_updated_by;
	else
	this.last_updated_by ="";
}
public void setLast_update_date(String last_update_date) {
	if(last_update_date!=null)
	this.last_update_date = last_update_date;
	else
	this.last_update_date="";
}
public void setLast_update_login(String last_update_login) {
	if(last_update_login!=null)
	this.last_update_login = last_update_login;
	else
	this.last_update_login="";
}
public void setQuestion_type(String question_type) {
	if(question_type!=null)
	this.question_type = question_type;
	else
	this.question_type="";
}
public void setOperation_seq(String operation_seq) {
	if(operation_seq!=null)
	this.operation_seq = operation_seq;
	else
	this.operation_seq ="";
}
public void setIs_key(String is_key) {
	if(is_key!=null)
	this.is_key = is_key;
	else
	this.is_key="";
}
	
}
