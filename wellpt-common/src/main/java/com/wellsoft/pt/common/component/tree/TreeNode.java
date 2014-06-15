/*
 * @(#)2012-12-20 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.common.component.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 如何描述该类
 * 
 * @author zhulh
 * @date 2012-12-20
 * @version 1.0
 * 
 *          <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2012-12-20.1	zhulh		2012-12-20		Create
 * </pre>
 * 
 */
public class TreeNode {
	public static final String ROOT_ID = "-1";
	private String id;
	private String name;
	private String path;
	private String url;
	private boolean open;
	private boolean checked;
	private boolean nocheck;
	private boolean isParent;
	private String iconSkin;
	private List<TreeNode> children = new ArrayList<TreeNode>(0);

	private Object data;

	public TreeNode() {
	}

	public TreeNode(String id, String name, String url) {
		this.id = id;
		this.name = name;
		this.url = url;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            要设置的id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name 要设置的name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path 要设置的path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url 要设置的url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the open
	 */
	public boolean isOpen() {
		return open;
	}

	/**
	 * @param open
	 *            要设置的open
	 */
	public void setOpen(boolean open) {
		this.open = open;
	}

	/**
	 * @return the checked
	 */
	public boolean isChecked() {
		return checked;
	}

	/**
	 * @param checked
	 *            要设置的checked
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	/**
	 * @return the nocheck
	 */
	public boolean isNocheck() {
		return nocheck;
	}

	/**
	 * @param nocheck 要设置的nocheck
	 */
	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}

	/**
	 * @return the isParent
	 */
	public boolean getIsParent() {
		return isParent;
	}

	/**
	 * @param isParent 要设置的isParent
	 */
	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}

	/**
	 * @return the iconSkin
	 */
	public String getIconSkin() {
		return iconSkin;
	}

	/**
	 * @param iconSkin 要设置的iconSkin
	 */
	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}

	/**
	 * @return the children
	 */
	public List<TreeNode> getChildren() {
		return children;
	}

	/**
	 * @param children
	 *            要设置的children
	 */
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data 要设置的data
	 */
	public void setData(Object data) {
		this.data = data;
	}

}
