/*
 * @(#)2012-10-30 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dyform.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wellsoft.pt.core.dao.hibernate.HibernateDao;
import com.wellsoft.pt.dyform.entity.DyFormDefinition;
import com.wellsoft.pt.dyform.support.DyFormDefinitionUtils;
import com.wellsoft.pt.repository.dao.DbTableDao;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

/**
 * Description: 对应FormDefinition的Dao操作
 *  
 * @author jiangmb
 * @date 2012-12-21
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-12-21.1	jiangmb		2012-12-21		Create
 * </pre>
 *
 */
@Repository
public class DyFormDataDao extends HibernateDao {

	Logger logger = Logger.getLogger(DyFormDataDao.class);

	@Autowired
	DbTableDao dbTableDao;

	/**
	 * 根据指定的字段名及值查询指定的表单表
	 * 
	 * @param tblName
	 * @param fieldName
	 * @param fieldValue
	 * @param j 
	 * @param beginPosition 
	 * @return  
	 * @throws Exception 
	 */
	public List<Map<String, Object>> queryFormDataList(String tblName, String fieldName, String fieldValue,
			int beginPosition, int count) throws Exception {
		String sql = ("select * from (select * from " + tblName + " where " + fieldName + " = '" + fieldValue
				+ "') where ROWNUM < " + (beginPosition + count) + " and ROWNUM >= " + beginPosition + " order by " + fieldName);
		return dbTableDao.query(sql);
	}

	/**
	 * 根据指定的字段名及值查询指定的表单表除了uuid指定的记录以外的记录
	 * 
	 * @param tblName
	 * @param fieldName
	 * @param fieldValue
	 * @return  
	 * @throws Exception 
	 */
	public List<Map<String, Object>> queryFormDataList(String uuid, String tblName, String fieldName,
			String fieldValue, int beginPosition, int count) throws Exception {
		String sql = ("select * from (select * from " + tblName + " where uuid != '" + uuid + "' and " + fieldName
				+ " = '" + fieldValue + "') where ROWNUM < " + (beginPosition + count) + " and ROWNUM >= "
				+ beginPosition + " order by " + fieldName);
		return dbTableDao.query(sql);
	}

	/**
	 * 
	 * @param tblName 数据目的表
	 * @param formData 数据
	 * @param string 
	 * @throws JSONException 
	 * @throws ParseException 
	 */
	public void save(DyFormDefinition definition, Map<String/*字段名*/, Object/*字段值*/> formData) throws JSONException,
			ParseException {
		DyFormDefinitionUtils dUtils = new DyFormDefinitionUtils(definition);
		StringBuffer columns = new StringBuffer("");
		StringBuffer values = new StringBuffer("");
		//columns.append("creator,create_time,modifier,modify_time, parent_id");
		columns.append("creator,create_time,modifier,modify_time");
		values.append("?").append(",").append("?").append(",").append("?").append(",").append("?");
		List<String> fieldNames = new ArrayList<String>();
		for (String fieldName : formData.keySet()) {
			columns.append(",").append(fieldName);
			values.append(",?");
			fieldNames.add(fieldName);
		}

		String sql = "insert into " + definition.getName() + " (" + columns.toString() + ") values (" + values + ")";

		Session session = this.getSessionFactory().getCurrentSession();

		SQLQuery sqlquery = session.createSQLQuery(sql);

		sqlquery.setString(0, SpringSecurityUtils.getCurrentUserId());
		sqlquery.setTimestamp(1, Calendar.getInstance().getTime());
		sqlquery.setString(2, SpringSecurityUtils.getCurrentUserId());
		sqlquery.setTimestamp(3, Calendar.getInstance().getTime());
		//String parent_id = (String) formData.get("parent_id");
		//sqlquery.setString(4, parent_id);
		for (int i = 0; i < fieldNames.size(); i++) {
			String fieldName = fieldNames.get(i);
			Object valueObj = formData.get(fieldName);

			if (!dUtils.isFieldInDefinition(fieldName)) {//在定义中没有该字段,默认为字段串类型 
				sqlquery.setString(i + 4, (String) valueObj);
				continue;
			}
			if (dUtils.isInputModeEqDate(fieldName)) {
				Date date = (Date) (valueObj);
				sqlquery.setTimestamp(i + 4, date);
				continue;
			} else if (dUtils.isInputModeEqNumber(fieldName)) {
				if (valueObj == null || ((String) valueObj).trim().length() == 0) {
					sqlquery.setString(i + 4, null);
					continue;
				}

				if (dUtils.isDbDataTypeEqLong(fieldName)) {

					sqlquery.setLong(i + 4, Long.parseLong((String) (valueObj)));

				} else if (dUtils.isDbDataTypeEqInt(fieldName)) {

					sqlquery.setInteger(i + 4, Integer.parseInt((String) valueObj));

				} else if (dUtils.isDbDataTypeEqFloat(fieldName)) {

					sqlquery.setFloat(i + 4, Float.parseFloat((String) valueObj));

				}
				continue;
			}

			sqlquery.setString(i + 4, (String) valueObj);

		}

		sqlquery.executeUpdate();
	}

	public void update(DyFormDefinition formDefinition, Map<String, Object> formData) throws JSONException {
		DyFormDefinitionUtils dUtils = new DyFormDefinitionUtils(formDefinition);
		if (formData == null || formData.size() == 0) {
			return;
		}
		String uuid = (String) formData.get("uuid");
		StringBuffer sql = new StringBuffer();
		sql.append("update " + formDefinition.getName() + " set modifier = ? ,modify_time = ? ");
		List<String> fieldNames = new ArrayList<String>();
		Iterator<String> fieldNamesIt = formData.keySet().iterator();
		while (fieldNamesIt.hasNext()) {
			String fieldName = fieldNamesIt.next();
			if ("uuid".equals(fieldName)) {
				continue;
			}

			sql.append(",");
			sql.append(fieldName);
			sql.append("=");
			sql.append("?");
			fieldNames.add(fieldName);
		}

		sql.append(" where uuid = ?");

		Session session = this.getSessionFactory().getCurrentSession();

		SQLQuery sqlquery = session.createSQLQuery(sql.toString());
		sqlquery.setString(0, SpringSecurityUtils.getCurrentUserId());
		sqlquery.setTimestamp(1, Calendar.getInstance().getTime());
		for (int i = 0; i < fieldNames.size(); i++) {

			String fieldName = fieldNames.get(i);

			Object valueObj = formData.get(fieldName);

			if (!dUtils.isFieldInDefinition(fieldName)) {//在定义中没有该字段,默认为字段串类型 
				sqlquery.setString(i + 2, (String) valueObj);
				continue;
			}
			if (dUtils.isInputModeEqDate(fieldName)) {
				Date date = (Date) (valueObj);
				sqlquery.setTimestamp(i + 2, date);
				continue;
			} else if (dUtils.isInputModeEqNumber(fieldName)) {

				if (dUtils.isDbDataTypeEqLong(fieldName)) {

					sqlquery.setLong(i + 2, Long.parseLong((String) (valueObj)));

				} else if (dUtils.isDbDataTypeEqInt(fieldName)) {

					sqlquery.setInteger(i + 2, Integer.parseInt((String) valueObj));

				} else if (dUtils.isDbDataTypeEqFloat(fieldName)) {

					sqlquery.setFloat(i + 2, Float.parseFloat((String) valueObj));

				}
				continue;
			}

			sqlquery.setString(i + 2, (String) valueObj);

		}
		sqlquery.setString(2 + fieldNames.size(), uuid);
		System.out.println(sqlquery);
		sqlquery.executeUpdate();
	}
}
