/*
 * @(#)2012-10-30 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dyform.dao;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.Type;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wellsoft.pt.core.dao.hibernate.HibernateDao;
import com.wellsoft.pt.dyform.entity.DyFormDefinition;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.DyFormConfig.EnumFieldPropertyName;
import com.wellsoft.pt.dyform.support.DyFormDataUtils;
import com.wellsoft.pt.dyform.support.DyFormDefinitionJSON;
import com.wellsoft.pt.dyform.support.enums.EnumRelationTblSystemField;
import com.wellsoft.pt.dyform.support.enums.EnumSystemField;
import com.wellsoft.pt.dytable.exception.SaveDataException;
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
		formData = setKeyAsLowerCase(formData);//将值map中的key设置为小写
		DyFormDefinitionJSON dUtils = definition.getJsonHandler();
		StringBuffer columns = new StringBuffer("");
		StringBuffer values = new StringBuffer("");
		//columns.append("creator,create_time,modifier,modify_time");
		formData.put("creator", SpringSecurityUtils.getCurrentUserId());
		formData.put("create_time", Calendar.getInstance().getTime());
		formData.put("modifier", SpringSecurityUtils.getCurrentUserId());
		formData.put("modify_time", Calendar.getInstance().getTime());
		formData.put("rec_ver", "0");

		//columns.append("creator,create_time,modifier,modify_time");
		//values.append("?").append(",").append("?").append(",").append("?").append(",").append("?");
		//values.append("?").append(",").append("?").append(",").append("?").append(",").append("?");
		List<String> fieldNames = new ArrayList<String>();
		/*fieldNames.add("creator");
		fieldNames.add("create_time");
		fieldNames.add("modifier");
		fieldNames.add("modify_time");
		fieldNames.add("rec_ver");*/
		Iterator<String> it = formData.keySet().iterator();
		Map<String, Object> fileNameMap = new HashMap<String, Object>();
		while (it.hasNext()) {
			String fieldName = it.next();
			Object obj = fileNameMap.get(fieldName.toLowerCase());
			if (obj != null) {
				continue;
			} else {
				fileNameMap.put(fieldName.toLowerCase(), new Object());
			}
			if (!(dUtils.isFieldInDefinition(fieldName) || DyFormDefinitionJSON.isSysTypeAsSystem(fieldName))) {//非表单字段 
				continue;
			} else if (dUtils.isInputModeEqAttach(fieldName)) {
				//附件字段
				continue;
			}
			if (columns.length() == 0) {
				columns.append(fieldName);
				values.append("?");
			} else {
				columns.append(",").append(fieldName);
				values.append(",?");
			}
			//columns.append(",").append(fieldName);

			fieldNames.add(fieldName);
		}

		String sql = "insert into " + definition.getName() + " (" + columns.toString() + ") values (" + values + ")";

		Session session = this.getSessionFactory().getCurrentSession();

		SQLQuery sqlquery = session.createSQLQuery(sql);

		/*sqlquery.setString(0, SpringSecurityUtils.getCurrentUserId());
		sqlquery.setTimestamp(1, Calendar.getInstance().getTime());
		sqlquery.setString(2, SpringSecurityUtils.getCurrentUserId());
		sqlquery.setTimestamp(3, Calendar.getInstance().getTime());*/
		//String parent_id = (String) formData.get("parent_id");
		//sqlquery.setString(4, parent_id);
		for (int i = 0; i < fieldNames.size(); i++) {
			String fieldName = fieldNames.get(i);
			Object valueObj = DyFormDataUtils.getValueFromMap(fieldName, formData);

			if (!dUtils.isFieldInDefinition(fieldName)) {//在定义中没有该字段,默认为字段串类型 

				setDateValue4SqlQuery(sqlquery, i, valueObj);

				continue;
			}
			String dbDataType = dUtils.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.dbDataType);
			if (dUtils.isDbDataTypeEqDate(fieldName)) {
				valueObj = DyFormDataUtils.convertData2DbType(dbDataType, valueObj, null,
						dUtils.getDateTimePatternByFieldName(fieldName));
			} else {
				valueObj = DyFormDataUtils.convertData2DbType(dbDataType, valueObj);
			}

			if (dUtils.isDbDataTypeEqDate(fieldName)) {
				setDateValue4SqlQuery(sqlquery, i, valueObj);
				continue;
			} else if (dUtils.isInputModeEqNumber(fieldName)) {
				Type type = org.hibernate.type.IntegerType.INSTANCE;
				if (dUtils.isDbDataTypeEqLong(fieldName)) {
					type = org.hibernate.type.LongType.INSTANCE;
				} else if (dUtils.isDbDataTypeEqInt(fieldName)) {
					type = org.hibernate.type.IntegerType.INSTANCE;
				} else if (dUtils.isDbDataTypeEqFloat(fieldName)) {
					type = org.hibernate.type.FloatType.INSTANCE;
				} else if (dUtils.isDbDataTypeEqDouble(fieldName)) {
					type = org.hibernate.type.DoubleType.INSTANCE;
				}
				sqlquery.setParameter(i, valueObj, type);
				continue;
			} else if (dUtils.isInputModeEqText(fieldName)) {//文本字段 
				sqlquery.setString(i, (String) valueObj);
				continue;
			}
			try {
				sqlquery.setString(i, (String) valueObj);
			} catch (Exception e) {
				logger.error(fieldName + ":" + valueObj);
				throw new SaveDataException();
			}

		}

		sqlquery.executeUpdate();

		//保存数据关系
		if (DyFormDataUtils.getValueFromMap(EnumRelationTblSystemField.mainform_data_uuid.name(), formData) == null//不是从表关系
				&& DyFormDataUtils.getValueFromMap(EnumRelationTblSystemField.parent_uuid.name(), formData) == null //不是父子节点关系
		) {
			return;
		}
		StringBuffer relatationColumns = new StringBuffer("");
		StringBuffer relatationValues = new StringBuffer("");
		relatationColumns.append(EnumRelationTblSystemField.create_time.name()).append(",")
				.append(EnumRelationTblSystemField.creator.name()).append(",")
				.append(EnumRelationTblSystemField.data_uuid.name()).append(",")
				.append(EnumRelationTblSystemField.mainform_data_uuid.name()).append(",")
				.append(EnumRelationTblSystemField.mainform_form_uuid.name()).append(",")
				.append(EnumRelationTblSystemField.modifier.name()).append(",")
				.append(EnumRelationTblSystemField.modify_time.name()).append(",")
				.append(EnumRelationTblSystemField.parent_uuid.name()).append(",")
				.append(EnumRelationTblSystemField.rec_ver.name()).append(",")
				.append(EnumRelationTblSystemField.sort_order.name()).append(",")
				.append(EnumRelationTblSystemField.uuid.name());

		relatationValues.append(":" + EnumRelationTblSystemField.create_time.name()).append(",")
				.append(":" + EnumRelationTblSystemField.creator.name()).append(",")
				.append(":" + EnumRelationTblSystemField.data_uuid.name()).append(",")
				.append(":" + EnumRelationTblSystemField.mainform_data_uuid.name()).append(",")
				.append(":" + EnumRelationTblSystemField.mainform_form_uuid.name()).append(",")
				.append(":" + EnumRelationTblSystemField.modifier.name()).append(",")
				.append(":" + EnumRelationTblSystemField.modify_time.name()).append(",")
				.append(":" + EnumRelationTblSystemField.parent_uuid.name()).append(",")
				.append(":" + EnumRelationTblSystemField.rec_ver.name()).append(",")
				.append(":" + EnumRelationTblSystemField.sort_order.name()).append(",")
				.append(":" + EnumRelationTblSystemField.uuid.name());

		String relationSql = "insert into " + definition.getRelationTbl() + " (" + relatationColumns.toString()
				+ ") values (" + relatationValues.toString() + ")";

		SQLQuery relationSqlquery = session.createSQLQuery(relationSql);

		relationSqlquery.setTimestamp(EnumRelationTblSystemField.create_time.name(), new Date());
		relationSqlquery.setString(EnumRelationTblSystemField.creator.name(), SpringSecurityUtils.getCurrentUserId());
		relationSqlquery.setString(EnumRelationTblSystemField.data_uuid.name(),
				(String) DyFormDataUtils.getValueFromMap(EnumSystemField.uuid.name(), formData));
		relationSqlquery.setString(EnumRelationTblSystemField.mainform_data_uuid.name(), (String) DyFormDataUtils
				.getValueFromMap(EnumRelationTblSystemField.mainform_data_uuid.name(), formData));
		relationSqlquery.setString(EnumRelationTblSystemField.mainform_form_uuid.name(), (String) DyFormDataUtils
				.getValueFromMap(EnumRelationTblSystemField.mainform_form_uuid.name(), formData));
		relationSqlquery.setString(EnumRelationTblSystemField.modifier.name(), SpringSecurityUtils.getCurrentUserId());
		relationSqlquery.setTimestamp(EnumRelationTblSystemField.modify_time.name(), new Date());
		relationSqlquery.setString(EnumRelationTblSystemField.parent_uuid.name(),
				(String) DyFormDataUtils.getValueFromMap(EnumRelationTblSystemField.parent_uuid.name(), formData));
		relationSqlquery.setInteger(EnumRelationTblSystemField.rec_ver.name(), 0);
		relationSqlquery.setString(EnumRelationTblSystemField.sort_order.name(),
				(String) DyFormDataUtils.getValueFromMap(EnumRelationTblSystemField.sort_order.name(), formData));
		relationSqlquery.setString(EnumRelationTblSystemField.uuid.name(), DyFormApiFacade.createUuid());
		relationSqlquery.executeUpdate();
	}

	private Map<String/*字段名*/, Object/*字段值*/> setKeyAsLowerCase(Map<String, Object> formData) {
		if (formData == null) {
			return null;
		}
		Map<String/*字段名*/, Object/*字段值*/> map = new HashMap<String, Object>();
		Iterator<String> it = formData.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			map.put(key.toLowerCase(), formData.get(key));
		}
		return map;
	}

	private static void setDateValue4SqlQuery(SQLQuery sqlquery, int i, Object valueObj) {
		if (valueObj instanceof Timestamp) {
			sqlquery.setTimestamp(i, (Timestamp) valueObj);
		} else if (valueObj instanceof Date) {
			sqlquery.setTimestamp(i, (Date) valueObj);
		} else if (valueObj instanceof java.sql.Date) {
			sqlquery.setTimestamp(i, (java.sql.Date) valueObj);
		} else {
			sqlquery.setString(i, (String) valueObj);
		}
	}

	public void update(DyFormDefinition formDefinition, Map<String, Object> formData) throws JSONException {

		if (formData == null || formData.size() == 0) {
			return;
		}
		formData = setKeyAsLowerCase(formData);//将值map中的key设置为小写
		String uuid = (String) formData.get(EnumSystemField.uuid.name());
		StringBuffer sql = new StringBuffer();
		sql.append("update " + formDefinition.getName() + " set modifier = ? ,modify_time = ? ");
		List<String> fieldNames = new ArrayList<String>();
		Iterator<String> fieldNamesIt = formData.keySet().iterator();
		while (fieldNamesIt.hasNext()) {
			String fieldName = fieldNamesIt.next();
			if (!formDefinition.getJsonHandler().isFieldInDefinition(fieldName)) {
				continue;
			} else if (formDefinition.getJsonHandler().isInputModeEqAttach(fieldName)) {
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

			Object valueObj = DyFormDataUtils.getValueFromMap(fieldName, formData);

			if (!formDefinition.getJsonHandler().isFieldInDefinition(fieldName)) {//只对用户定义的字段进行更新
				//sqlquery.setString(i + 2, (String) valueObj);
				continue;
			}
			if (formDefinition.getJsonHandler().isInputModeEqDate(fieldName)) {
				setDateValue4SqlQuery(sqlquery, i + 2, valueObj);
				continue;
			} else if (formDefinition.getJsonHandler().isInputModeEqNumber(fieldName)) {
				Type type = org.hibernate.type.IntegerType.INSTANCE;
				if (formDefinition.getJsonHandler().isDbDataTypeEqLong(fieldName)) {
					type = org.hibernate.type.LongType.INSTANCE;
				} else if (formDefinition.getJsonHandler().isDbDataTypeEqInt(fieldName)) {
					type = org.hibernate.type.IntegerType.INSTANCE;
				} else if (formDefinition.getJsonHandler().isDbDataTypeEqFloat(fieldName)) {
					type = org.hibernate.type.FloatType.INSTANCE;

				} else if (formDefinition.getJsonHandler().isDbDataTypeEqDouble(fieldName)) {
					type = org.hibernate.type.DoubleType.INSTANCE;

				}
				sqlquery.setParameter(i + 2, valueObj, type);
				continue;
			}

			sqlquery.setString(i + 2, valueObj == null ? null : valueObj.toString());

		}
		sqlquery.setString(2 + fieldNames.size(), uuid);

		sqlquery.executeUpdate();

		//保存数据关系
		if (formData.get(EnumRelationTblSystemField.mainform_data_uuid.name()) == null//不是从表关系
				&& formData.get(EnumRelationTblSystemField.parent_uuid.name()) == null //不是父子节点关系
		) {
			return;
		}
		StringBuffer relatationColumns = new StringBuffer("");

		//判读关联关系表中是否有数据，如果没有的话，插入数据，如果有的话，更新数据
		String mainform_data_uuid = (String) (DyFormDataUtils.getValueFromMap(EnumRelationTblSystemField.mainform_data_uuid.name(), formData));
		String uluuid = (String) (DyFormDataUtils.getValueFromMap(EnumRelationTblSystemField.uuid.name(), formData));
		//获得关联关系表
		String relationTable = formDefinition.getRelationTbl();
		String relSql = "select * from " + formDefinition.getRelationTbl()
				+ " where mainform_data_uuid ='" + mainform_data_uuid  + "' and " 
				+ "data_uuid = '" + uluuid + "'";
		
		SQLQuery relQuery = session.createSQLQuery(relSql);
		List relList = relQuery.list();
		if(relList.size()==0){
			StringBuffer insrelatationColumns = new StringBuffer("");
			StringBuffer insrelatationValues = new StringBuffer("");
			insrelatationColumns.append(EnumRelationTblSystemField.create_time.name()).append(",")
					.append(EnumRelationTblSystemField.creator.name()).append(",")
					.append(EnumRelationTblSystemField.data_uuid.name()).append(",")
					.append(EnumRelationTblSystemField.mainform_data_uuid.name()).append(",")
					.append(EnumRelationTblSystemField.mainform_form_uuid.name()).append(",")
					.append(EnumRelationTblSystemField.modifier.name()).append(",")
					.append(EnumRelationTblSystemField.modify_time.name()).append(",")
					.append(EnumRelationTblSystemField.parent_uuid.name()).append(",")
					.append(EnumRelationTblSystemField.rec_ver.name()).append(",")
					.append(EnumRelationTblSystemField.sort_order.name()).append(",")
					.append(EnumRelationTblSystemField.uuid.name());

			insrelatationValues.append(":" + EnumRelationTblSystemField.create_time.name()).append(",")
					.append(":" + EnumRelationTblSystemField.creator.name()).append(",")
					.append(":" + EnumRelationTblSystemField.data_uuid.name()).append(",")
					.append(":" + EnumRelationTblSystemField.mainform_data_uuid.name()).append(",")
					.append(":" + EnumRelationTblSystemField.mainform_form_uuid.name()).append(",")
					.append(":" + EnumRelationTblSystemField.modifier.name()).append(",")
					.append(":" + EnumRelationTblSystemField.modify_time.name()).append(",")
					.append(":" + EnumRelationTblSystemField.parent_uuid.name()).append(",")
					.append(":" + EnumRelationTblSystemField.rec_ver.name()).append(",")
					.append(":" + EnumRelationTblSystemField.sort_order.name()).append(",")
					.append(":" + EnumRelationTblSystemField.uuid.name());

			String relationSql = "insert into " + formDefinition.getRelationTbl() + " (" + insrelatationColumns.toString()
					+ ") values (" + insrelatationValues.toString() + ")";

			SQLQuery relationSqlquery = session.createSQLQuery(relationSql);

			relationSqlquery.setTimestamp(EnumRelationTblSystemField.create_time.name(), new Date());
			relationSqlquery.setString(EnumRelationTblSystemField.creator.name(), SpringSecurityUtils.getCurrentUserId());
			relationSqlquery.setString(EnumRelationTblSystemField.data_uuid.name(),
					(String) DyFormDataUtils.getValueFromMap(EnumSystemField.uuid.name(), formData));
			relationSqlquery.setString(EnumRelationTblSystemField.mainform_data_uuid.name(), (String) DyFormDataUtils
					.getValueFromMap(EnumRelationTblSystemField.mainform_data_uuid.name(), formData));
			relationSqlquery.setString(EnumRelationTblSystemField.mainform_form_uuid.name(), (String) DyFormDataUtils
					.getValueFromMap(EnumRelationTblSystemField.mainform_form_uuid.name(), formData));
			relationSqlquery.setString(EnumRelationTblSystemField.modifier.name(), SpringSecurityUtils.getCurrentUserId());
			relationSqlquery.setTimestamp(EnumRelationTblSystemField.modify_time.name(), new Date());
			relationSqlquery.setString(EnumRelationTblSystemField.parent_uuid.name(),
					(String) DyFormDataUtils.getValueFromMap(EnumRelationTblSystemField.parent_uuid.name(), formData));
			relationSqlquery.setInteger(EnumRelationTblSystemField.rec_ver.name(), 0);
			relationSqlquery.setString(EnumRelationTblSystemField.sort_order.name(),
					(String) DyFormDataUtils.getValueFromMap(EnumRelationTblSystemField.sort_order.name(), formData));
			relationSqlquery.setString(EnumRelationTblSystemField.uuid.name(), DyFormApiFacade.createUuid());
			relationSqlquery.executeUpdate();
							
		}else{
			relatationColumns
			//.append(EnumRelationTblSystemField.create_time.name()).append(" = :").append(EnumRelationTblSystemField.create_time.name()).append(",")
			//.append(EnumRelationTblSystemField.creator.name()).append(" = :").append(EnumRelationTblSystemField.creator.name()).append(",")
			//.append(EnumRelationTblSystemField.data_uuid.name()).append(" = :").append(EnumRelationTblSystemField.data_uuid.name()).append(",")
			//.append(EnumRelationTblSystemField.mainform_data_uuid.name()).append(" = :")
			//.append(EnumRelationTblSystemField.mainform_data_uuid.name()).append(",")
			//.append(EnumRelationTblSystemField.mainform_form_uuid.name()).append(" = :")
			//.append(EnumRelationTblSystemField.mainform_form_uuid.name()).append(",")
			.append(EnumRelationTblSystemField.modifier.name()).append(" = :")
			.append(EnumRelationTblSystemField.modifier.name())
			.append(",")
			.append(EnumRelationTblSystemField.modify_time.name())
			.append(" = :")
			.append(EnumRelationTblSystemField.modify_time.name());
			
			//.append(EnumRelationTblSystemField.parent_uuid.name()).append(" = :")
			//.append(EnumRelationTblSystemField.parent_uuid.name()).append(",")
			//.append(EnumRelationTblSystemField.rec_ver.name()).append(" = :")
			//.append(EnumRelationTblSystemField.rec_ver.name()).append(",")
			
			String seqNO = (String) DyFormDataUtils.getValueFromMap(EnumRelationTblSystemField.sort_order.name(), formData);
			if(seqNO != null && seqNO.trim().length() > 0){
				relatationColumns.append(",")
				.append(EnumRelationTblSystemField.sort_order.name()).append(" = :")
				 .append(EnumRelationTblSystemField.sort_order.name()).append("");
			}
			
			//.append(EnumRelationTblSystemField.uuid.name()).append(" = :")
			//.append(EnumRelationTblSystemField.uuid.name());
		
			String relationSql = "update " + formDefinition.getRelationTbl() + "  set " + relatationColumns.toString()
					+ " where " + EnumRelationTblSystemField.data_uuid.name() + " = :"
					+ EnumRelationTblSystemField.data_uuid.name();
		
			SQLQuery relationSqlquery = session.createSQLQuery(relationSql);
		
			//relationSqlquery.setTimestamp(EnumRelationTblSystemField.create_time.name(), new Date());
			//relationSqlquery.setString(EnumRelationTblSystemField.creator.name(), SpringSecurityUtils.getCurrentUserId());
			relationSqlquery.setString(EnumRelationTblSystemField.data_uuid.name(),
					(String) DyFormDataUtils.getValueFromMap(EnumSystemField.uuid.name(), formData));
			//relationSqlquery.setString(EnumRelationTblSystemField.mainform_data_uuid.name(),
			//		(String) formData.get(EnumRelationTblSystemField.mainform_data_uuid.name()));
			//relationSqlquery.setString(EnumRelationTblSystemField.mainform_form_uuid.name(),
			//		(String) formData.get(EnumRelationTblSystemField.mainform_form_uuid.name()));
			relationSqlquery.setString(EnumRelationTblSystemField.modifier.name(), SpringSecurityUtils.getCurrentUserId());
			relationSqlquery.setTimestamp(EnumRelationTblSystemField.modify_time.name(), new Date());
			//relationSqlquery.setString(EnumRelationTblSystemField.parent_uuid.name(),
			//		(String) formData.get(EnumRelationTblSystemField.parent_uuid.name()));
			//relationSqlquery.setInteger(EnumRelationTblSystemField.rec_ver.name(), 0);
			if(seqNO != null && seqNO.trim().length() > 0){
				relationSqlquery.setString(EnumRelationTblSystemField.sort_order.name(), 	seqNO   );
			}
			
			//relationSqlquery.setString(EnumRelationTblSystemField.uuid.name(), DyFormApiFacade.createUuid());
			relationSqlquery.executeUpdate();
		}
		
		
	}

	/**
	 *删除主表与子表关系<br/>
	 *删除父子关系 
	 * @param tblNameOfMainform
	 * @param tblNameOfSubform
	 * @param dataUuids
	 * @param dataUuidOfMainform 
	 */
	public void deleteSubformFormMainform(String relationTblNameOfSubform, List<String> dataUuids,
			String dataUuidOfMainform) {
		if (dataUuids == null || dataUuids.size() == 0
				|| org.apache.commons.lang.StringUtils.isBlank(dataUuidOfMainform)) {
			return;
		}

		Session session = this.getSessionFactory().getCurrentSession();
		for (String dataUuid : dataUuids) {
			StringBuffer sql = new StringBuffer();
			sql.append("delete from  " + relationTblNameOfSubform + "  ");
			sql.append(" where  ");
			sql.append(EnumRelationTblSystemField.data_uuid.name());
			sql.append("= '");
			sql.append(dataUuid);
			sql.append("' and ");
			sql.append(EnumRelationTblSystemField.mainform_data_uuid.name());
			sql.append("= '");
			sql.append(dataUuidOfMainform);
			sql.append("'");
			SQLQuery sqlquery = session.createSQLQuery(sql.toString());
			sqlquery.executeUpdate();
		}

	}

	public static void main(String[] args) {

		Integer i = (Integer) null;
		System.out.println(i);
	}
}
