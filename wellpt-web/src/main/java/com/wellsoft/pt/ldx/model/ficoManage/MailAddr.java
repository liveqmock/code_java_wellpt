package com.wellsoft.pt.ldx.model.ficoManage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.wellsoft.pt.core.entity.IdEntity;

/**
 * 
 * Description: 邮件发送配置表
 *  
 * @author HeShi
 * @date 2014-10-1
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-10-1.1	HeShi		2014-10-1		Create
 * </pre>
 *
 */
@Entity
@Table(name = "UF_MAIL_ADDR")
@DynamicUpdate
@DynamicInsert
public class MailAddr extends IdEntity{
	private static final long serialVersionUID = 1L;
	public MailAddr() {
	}
	/**
	 * 姓名
	 */
	@Column(name = "name")
	private String name;
	/**
	 * 邮件地址
	 */
	@Column(name = "addr")
	private String addr;
	/**
	 * 类型
	 */
	@Column(name = "type")
	private String type;
	/**
	 * 功能代码
	 */
	@Column(name = "funcode")
	private String funcode;
	/**
	 * 发送类型
	 */
	@Column(name = "sendtype")
	private String sendtype;
	/**
	 * 排序号
	 */
	@Column(name = "ordernum")
	private String ordernum;
	/**
	 * 工号
	 */
	@Column(name = "userid")
	private String userid;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFuncode() {
		return funcode;
	}
	public void setFuncode(String funcode) {
		this.funcode = funcode;
	}
	public String getSendtype() {
		return sendtype;
	}
	public void setSendtype(String sendtype) {
		this.sendtype = sendtype;
	}
	public String getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
}
