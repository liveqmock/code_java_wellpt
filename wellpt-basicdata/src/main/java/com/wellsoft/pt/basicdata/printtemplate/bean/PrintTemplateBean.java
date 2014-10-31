package com.wellsoft.pt.basicdata.printtemplate.bean;

import com.wellsoft.pt.basicdata.printtemplate.entity.PrintTemplate;

/**
 * 
 * Description: 打印模板VO类
 *  
 * @author zhouyq
 * @date 2013-3-21
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-3-21.1	zhouyq		2013-3-21		Create
 * </pre>
 *
 */
public class PrintTemplateBean extends PrintTemplate {
	private static final long serialVersionUID = 1L;

	/** 打印模板使用人，从组织机构中选择直接作为ACL中的SID */
	private String ownerNames;

	private String ownerIds;

	/**
	 * @return the ownerNames
	 */
	public String getOwnerNames() {
		return ownerNames;
	}

	/**
	 * @param ownerNames 要设置的ownerNames
	 */
	public void setOwnerNames(String ownerNames) {
		this.ownerNames = ownerNames;
	}

	/**
	 * @return the ownerIds
	 */
	public String getOwnerIds() {
		return ownerIds;
	}

	/**
	 * @param ownerIds 要设置的ownerIds
	 */
	public void setOwnerIds(String ownerIds) {
		this.ownerIds = ownerIds;
	}

}
