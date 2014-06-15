/*
 * @(#)2013-3-1 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.common.marker.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Description: 如何描述该类
 * 
 * @author zhulh
 * @date 2013-3-1
 * @version 1.0
 * 
 *          <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-3-1.1	zhulh		2013-3-1		Create
 * </pre>
 * 
 */
@Entity
@Table(name = "cd_read_marker")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ReadMarker {
	// 联合主键
	private ReadMarkerId id;

	private Date readTime;

	public ReadMarker() {
	}

	public ReadMarker(String entityUuid, String userId) {
		this.setId(new ReadMarkerId(entityUuid, userId));
		this.readTime = Calendar.getInstance().getTime();
	}

	@EmbeddedId
	public ReadMarkerId getId() {
		return id;
	}

	public void setId(ReadMarkerId id) {
		this.id = id;
	}

	/**
	 * @return the readTime
	 */
	public Date getReadTime() {
		return readTime;
	}

	/**
	 * @param readTime 要设置的readTime
	 */
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

}
