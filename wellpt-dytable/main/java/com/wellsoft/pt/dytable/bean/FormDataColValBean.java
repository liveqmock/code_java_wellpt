/*
 * @(#)2012-11-13 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.wellsoft.pt.dytable.support.FormUtils;
import com.wellsoft.pt.repository.entity.LogicFileInfo;

/**
 * Description: 字段值对象
 *  
 * @author jiangmb
 * @date 2012-11-13
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-11-13.1	jiangmb		2012-11-13		Create
 * </pre>
 *
 */
public class FormDataColValBean implements Serializable {
	//字段英文名
	private String colEnName;
	//字段类型
	private String dataType;
	//字段值
	private Object value;
	//数据类型
	private String inputMode;
	//显示名
	private String descName;

	/**
	 * @return the inputMode
	 */
	public String getInputMode() {
		return inputMode;
	}

	/**
	 * @param inputMode 要设置的inputMode
	 */
	public void setInputMode(String inputMode) {
		this.inputMode = inputMode;
	}

	/**
	 * @return the descName
	 */
	public String getDescName() {
		return descName;
	}

	/**
	 * @param descName 要设置的descName
	 */
	public void setDescName(String descName) {
		this.descName = descName;
	}

	public String getColEnName() {
		return colEnName;
	}

	public void setColEnName(String colEnName) {
		this.colEnName = colEnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getValue() {
		if (value == null) {
			return null;
		}
		if (value instanceof String) {
			return (String) value;
		} else if (value instanceof Integer) {
			return value + "";
		} else if (value instanceof Collection) {
			return JSONArray.fromObject(value).toString();
		} else {
			return JSONObject.fromObject(value).toString();
		}
	}

	/**
	 * 获取从前台统一上传控件上传过来的文件ID列表
	 * @return
	 */
	public List<String> getFileIds() {
		List<Object> files = this.getFiles();//获取从前台传送过来的文件列表信息 
		if (files == null) {
			files = new ArrayList<Object>();
		}

		List<String> fileIDs = new ArrayList<String>();
		for (Object obj : files) {
			if (obj instanceof LogicFileInfo) {
				LogicFileInfo file = (LogicFileInfo) obj;
				fileIDs.add(file.getFileID());
			} else {
				Map<String, String> fileInfo = (Map<String, String>) obj;
				fileIDs.add(fileInfo.get("fileID"));
			}

		}
		return fileIDs;
	}

	public List<Object> getFiles() {
		if (value == null) {
			return null;
		}
		if (value instanceof String) {
			return null;
		}

		return (List<Object>) value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * 判断是否是附件，是附件返回true, 否则返回false
	 * @return
	 */
	public boolean isAttach() {
		return FormUtils.isAttach(inputMode, this.colEnName);

	}
}
