package com.wellsoft.pt.dytable.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.sql.Clob;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wellsoft.pt.common.enums.IdPrefix;
import com.wellsoft.pt.dytable.bean.ColInfoBean;
import com.wellsoft.pt.dytable.bean.FormAndDataBean;
import com.wellsoft.pt.dytable.bean.FormDataBean;
import com.wellsoft.pt.dytable.bean.FormDataColValBean;
import com.wellsoft.pt.dytable.bean.FormDataRecordBean;
import com.wellsoft.pt.dytable.bean.RootTableInfoBean;
import com.wellsoft.pt.dytable.entity.FieldDefinition;
import com.wellsoft.pt.dytable.entity.FormDefinition;
import com.wellsoft.pt.dytable.entity.FormRelation;
import com.wellsoft.pt.dytable.service.FormDataService;
import com.wellsoft.pt.dytable.service.FormDefinitionService;
import com.wellsoft.pt.dytable.support.DytableConfig;
import com.wellsoft.pt.dytable.support.FormDataResultTransformer;
import com.wellsoft.pt.dytable.utils.DynamicUtils;
import com.wellsoft.pt.org.entity.Department;
import com.wellsoft.pt.org.entity.User;
import com.wellsoft.pt.org.facade.OrgApiFacade;
import com.wellsoft.pt.repository.entity.FileUpload;
import com.wellsoft.pt.repository.entity.mongo.file.MongoFileEntity;
import com.wellsoft.pt.repository.service.MongoFileService;
import com.wellsoft.pt.unit.entity.CommonUnit;
import com.wellsoft.pt.unit.facade.UnitApiFacade;
import com.wellsoft.pt.utils.reflection.ConvertUtils;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

/**
 * Description: 表单数据操作DAO
 * 
 * @author jiangmb
 * @date 2012-12-21
 * @version 1.0
 * 
 *          <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-12-21.1	jiangmb		2012-12-21		Create
 * </pre>
 * 
 */
@Repository
public class FormDataDao {
	private Logger logger = LoggerFactory.getLogger(FormDataDao.class);

	@Autowired
	private FormDefinitionDao formDefinitionDao;

	@Autowired
	private OrgApiFacade orgApiFacade;

	//@Autowired
	//private FileService fileService;

	@Autowired
	private MongoFileService mongoFileService;

	@Autowired
	private FormDataService formDataService;

	@Autowired
	private FormDefinitionService formDefinitionService;

	@Autowired
	private UnitApiFacade unitApiFacade;

	/**
	 * 保存表单数据
	 * 
	 * @param formDatas
	 * @return
	 */
	public String saveFormData(List<FormDataBean> formDatas) {
		FormDataBean formDataBean = new FormDataBean();
		for (FormDataBean f : formDatas) {
			if ("1".equals(f.getTableType())) {
				formDataBean = f;
			}
		}

		String mainTableUUId = DynamicUtils.getRandomUUID();
		Session session = formDefinitionDao.getSession();
		try {
			boolean isAdd = true;
			if (StringUtils.isNotEmpty(formDataBean.getRecordList().get(0).getUuid())) {
				mainTableUUId = formDataBean.getRecordList().get(0).getUuid();
				isAdd = false;
			}
			if (!isAdd) {
				for (FormDataBean formData : formDatas) {
					updateSingleFormData(session, formData, mainTableUUId);
				}
			} else {
				for (FormDataBean formData : formDatas) {
					saveSingleFormData(session, formData, mainTableUUId);
				}
			}
		} catch (Exception e) {
			logger.error("插入单据报错：" + e.getMessage());
			throw new RuntimeException(e);
		}
		return mainTableUUId;
	}

	public String saveFormData(List<FormDataBean> formDatas, boolean isNew) {
		FormDataBean formDataBean = new FormDataBean();
		for (FormDataBean f : formDatas) {
			if ("1".equals(f.getTableType())) {
				formDataBean = f;
			}
		}
		String mainTableUUId = formDataBean.getRecordList().get(0).getUuid();
		Session session = formDefinitionDao.getSession();
		try {
			if (!isNew) {
				for (FormDataBean formData : formDatas) {
					updateSingleFormData(session, formData, mainTableUUId);
				}
			} else {
				for (FormDataBean formData : formDatas) {
					saveSingleFormData(session, formData, mainTableUUId);
				}
			}
		} catch (Exception e) {
			logger.error("插入单据报错：" + e.getMessage());
			throw new RuntimeException(e);
		}
		return mainTableUUId;
	}

	/**
	 * 保存表单数据, dataId由调用者分配
	 * 
	 * @param formDatas
	 * @return
	 */
	public void saveFormData(List<FormDataBean> formDatas, String dataUUID) {
		FormDataBean formDataBean = new FormDataBean();
		for (FormDataBean f : formDatas) {
			if ("1".equals(f.getTableType())) {
				formDataBean = f;
			}
		}

		Session session = formDefinitionDao.getSession();
		try {
			boolean isAdd = true;
			if (StringUtils.isNotEmpty(formDataBean.getRecordList().get(0).getUuid())) {
				isAdd = false;
			}
			if (!isAdd) {
				for (FormDataBean formData : formDatas) {
					updateSingleFormData(session, formData, formDataBean.getRecordList().get(0).getUuid());
				}
			} else {
				for (FormDataBean formData : formDatas) {
					saveSingleFormData(session, formData, dataUUID);
				}
			}
		} catch (Exception e) {
			logger.error("插入单据报错：" + e.getMessage());
			throw new RuntimeException(e);
		}

	}

	/**
	 * 
	 * 保存或者更新表单数据
	 * 
	 * @param formUuid
	 * @param data
	 * @return
	 */
	public String saveFormData(String formUuid, Map<String, Object> data) {
		Session session = formDefinitionDao.getSession();
		String dataUuid = null;
		StringBuffer columns = new StringBuffer("");
		StringBuffer values = new StringBuffer("");

		DateFormat formatDate1 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat formatDate2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		DateFormat formatDate3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat formatDate4 = new SimpleDateFormat("HH:mm");
		DateFormat formatDate5 = new SimpleDateFormat("HH:mm:ss");
		dataUuid = (String) (data.get("uuid") == null ? "" : data.get("uuid"));

		FormAndDataBean form = null;
		if (dataUuid != null && dataUuid.trim().length() > 0) {
			form = formDataService.getFormData(formUuid, dataUuid, null);
		}

		if (form != null) {//在数据库中找不到表信息，所以说明是新建的

			// 读取主表信息
			FormDefinition formDefinition = formDefinitionDao.get(formUuid);
			Set<FieldDefinition> fieldList = formDefinition.getFieldDefinitions();
			Map<String, FieldDefinition> fieldDefinitions = ConvertUtils.convertElementToMap(fieldList, "fieldName");
			// 原表的字段信息
			List<ColInfoBean> colInfoBeans = form.getForm().getTableInfo().getFields();
			String tableName = form.getForm().getTableInfo().getTableName();
			StringBuffer a = new StringBuffer("");
			a.append("modifier = :modifier,");
			a.append("modify_time = :modify_time");

			Map<String, Object> paramValues = new HashMap<String, Object>();
			Map<String, Date> dateValues = new HashMap<String, Date>();
			for (int i = 0; i < colInfoBeans.size(); i++) {
				String paramName = colInfoBeans.get(i).getColEnName();
				if (data.containsKey(paramName)) {
					FieldDefinition fieldDefinition = fieldDefinitions.get(paramName);
					if (fieldDefinition.getType().equals(DytableConfig.INPUTTYPE_DATE)) {
						if (data.get(paramName) != null) {
							try {
								Date date = null;
								if (data.get(paramName) instanceof Date) {
									date = (Date) data.get(paramName);
								} else {
									formatDate1.parse(data.get(paramName).toString());
								}
								dateValues.put(paramName, date);
								a.append("," + paramName + " = :" + paramName);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							a.append("," + paramName + " = null");
						}

					} else if (fieldDefinition.getType().equals(DytableConfig.INPUTTYPE_DATETIMEMIN)) {
						if (data.get(paramName) != null) {
							try {
								Date date = formatDate2.parse(data.get(paramName).toString());
								dateValues.put(paramName, date);
								a.append("," + paramName + " = :" + paramName);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							a.append("," + paramName + " = null");
						}
					} else if (fieldDefinition.getType().equals(DytableConfig.INPUTTYPE_DATETIMESEC)) {
						if (data.get(paramName) != null) {
							try {
								Date date = formatDate3.parse(data.get(paramName).toString());
								dateValues.put(paramName, date);
								a.append("," + paramName + " = :" + paramName);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							a.append("," + paramName + " = null");
						}
					} else if (fieldDefinition.getType().equals(DytableConfig.INPUTTYPE_TIMEMIN)) {
						if (data.get(paramName) != null) {
							try {
								Date date = formatDate4.parse(data.get(paramName).toString());
								dateValues.put(paramName, date);
								a.append("," + paramName + " = :" + paramName);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							a.append("," + paramName + " = null");
						}
					} else if (fieldDefinition.getType().equals(DytableConfig.INPUTTYPE_TIMESEC)) {
						if (data.get(paramName) != null) {
							try {
								Date date = formatDate5.parse(data.get(paramName).toString());
								dateValues.put(paramName, date);
								a.append("," + paramName + " = :" + paramName);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							a.append("," + paramName + " = null");
						}
					} else {
						a.append("," + paramName + " = :" + paramName);
						paramValues.put(paramName, data.get(paramName));
					}
				}
			}
			String sql = "update " + tableName + " set " + a.toString() + " where uuid=" + "'" + data.get("uuid") + "'";
			if (logger.isDebugEnabled()) {
				logger.debug("插入表单数据SQL语句：" + sql);
			}
			SQLQuery sqlquery = session.createSQLQuery(sql);
			//修改信息字段
			sqlquery.setString("modifier", SpringSecurityUtils.getCurrentUserId());
			sqlquery.setTimestamp("modify_time", Calendar.getInstance().getTime());
			//字符串字段
			for (String key : paramValues.keySet()) {
				sqlquery.setString(key, paramValues.get(key) == null ? "" : paramValues.get(key).toString());//(key, getSerializableValue(paramValues.get(key)));
			}
			//时间字段
			for (String key : dateValues.keySet()) {
				sqlquery.setTimestamp(key, dateValues.get(key));
			}
			sqlquery.executeUpdate();
		} else {
			RootTableInfoBean rootTableInfo = formDefinitionService.getRootTableInfoByUuid(formUuid, true, true);
			String tableName = rootTableInfo.getTableInfo().getTableName();
			// 读取主表信息
			FormDefinition formDefinition = formDefinitionDao.get(formUuid);
			Set<FieldDefinition> fieldList = formDefinition.getFieldDefinitions();
			columns.append("uuid,creator,create_time,modifier,modify_time,form_id,parent_id");
			if (dataUuid == null || dataUuid.trim().length() == 0)
				dataUuid = DynamicUtils.getRandomUUID();

			//			values.append("'" + dataUuid + "'");
			Object parentId = data.get("parent_id");
			values.append("'").append(dataUuid).append("',").append("?").append(",").append("?").append(",")
					.append("?").append(",").append("?").append(",'").append(formUuid).append("'").append(",'")
					.append(parentId).append("'");
			Map<Integer, String> sqlMap = new HashMap<Integer, String>();
			int i = 4;
			for (FieldDefinition fieldDefinition : fieldList) {
				if (data.containsKey(fieldDefinition.getFieldName())) {
					columns.append(",").append(fieldDefinition.getFieldName());
					if (data.get(fieldDefinition.getFieldName()) != null) {
						String type = fieldDefinition.getType();
						if (type != null
								&& (type.equals(DytableConfig.INPUTTYPE_DATE)
										|| type.equals(DytableConfig.INPUTTYPE_DATETIMEMIN)
										|| type.equals(DytableConfig.INPUTTYPE_DATETIMESEC)
										|| type.equals(DytableConfig.INPUTTYPE_TIMEMIN) || type
											.equals(DytableConfig.INPUTTYPE_TIMESEC))) {
							values.append(",").append("?");
							sqlMap.put(i, fieldDefinition.getType() + "," + data.get(fieldDefinition.getFieldName()));
							i++;
						} else {
							values.append(",").append("'" + data.get(fieldDefinition.getFieldName()) + "'");
						}

					} else {
						values.append(",").append("null");
					}
				}
			}
			String sql = "insert into " + tableName + " (" + columns.toString() + ") values (" + values + ")";
			if (logger.isDebugEnabled()) {
				logger.debug("插入表单数据SQL语句：" + sql);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("插入表单数据SQL语句：" + sql);
			}

			SQLQuery sqlquery = session.createSQLQuery(sql);
			sqlquery.setString(0, SpringSecurityUtils.getCurrentUserId());
			sqlquery.setTimestamp(1, Calendar.getInstance().getTime());
			sqlquery.setString(2, SpringSecurityUtils.getCurrentUserId());
			sqlquery.setTimestamp(3, Calendar.getInstance().getTime());

			for (Integer key_ : sqlMap.keySet()) {
				String[] val = sqlMap.get(key_).split(",");
				if (DytableConfig.INPUTTYPE_DATE.equals(val[0])) {
					try {
						Date date = formatDate1.parse(val[1]);
						sqlquery.setTimestamp(key_, date);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}//yyyy-MM-dd

				} else if (DytableConfig.INPUTTYPE_DATETIMEMIN.equals(val[0])) {
					try {
						Date date = formatDate2.parse(val[1]);
						sqlquery.setTimestamp(key_, date);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}//yyyy-MM-dd//yyyy-MM-dd HH:mm

				} else if (DytableConfig.INPUTTYPE_DATETIMESEC.equals(val[0])) {
					try {
						Date date = formatDate3.parse(val[1]);
						sqlquery.setTimestamp(key_, date);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}//yyyy-MM-dd HH:mm:ss

				} else if (DytableConfig.INPUTTYPE_TIMEMIN.equals(val[0])) {
					try {
						Date date = formatDate4.parse(val[1]);
						sqlquery.setTimestamp(key_, date);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}//HH:mm

				} else if (DytableConfig.INPUTTYPE_TIMESEC.equals(val[0])) {
					try {
						Date date = formatDate5.parse(val[1]);
						sqlquery.setTimestamp(key_, date);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}//HH:mm:ss
				}
			}
			sqlquery.executeUpdate();
		}
		return dataUuid;
	}

	/**
	 * 如何描述该方法
	 * 
	 * @param object
	 * @return
	 */
	private Serializable getSerializableValue(Object rawValue) {
		if (rawValue == null) {
			return "";
		}
		Class<?> cls = rawValue.getClass();
		if (cls.isAssignableFrom(Boolean.class)) {
			return (Boolean) rawValue;
		} else if (cls.isAssignableFrom(Character.class)) {
			return (Character) rawValue;
		} else if (cls.isAssignableFrom(Byte.class)) {
			return (Byte) rawValue;
		} else if (cls.isAssignableFrom(Short.class)) {
			return (Short) rawValue;
		} else if (cls.isAssignableFrom(Integer.class)) {
			return (Integer) rawValue;
		} else if (cls.isAssignableFrom(Long.class)) {
			return (Long) rawValue;
		} else if (cls.isAssignableFrom(Float.class)) {
			return (Float) rawValue;
		} else if (cls.isAssignableFrom(Double.class)) {
			return (Double) rawValue;
		} else if (cls.isAssignableFrom(Date.class)) {
			return (Date) rawValue;
		}
		return rawValue.toString();
	}

	/**
	 * 删除表单数据
	 * 
	 * @param mainForm
	 * @param subList
	 * @param dataUids
	 */
	public void delete(FormDefinition mainForm, List<FormRelation> subList, String[] dataUids) {
		Session session = formDefinitionDao.getSession();
		try {
			for (String dataUid : dataUids) {
				for (FormRelation relation : subList) {// 删除从表数据
					FormDefinition formDefinition = formDefinitionDao.get(relation.getSubFormUuid());
					deleteSingleFormData(session, formDefinition.getName(), dataUid, "2");
				}
				deleteSingleFormData(session, mainForm.getName(), dataUid, "1");
			}
		} catch (Exception e) {
			logger.error("删除单据报错：" + e.getMessage());
		}
	}

	/**
	 * 更新单张表数据
	 * 
	 * @param session
	 * @param tableName
	 * @param uid
	 * @param tableType
	 */
	private void updateSingleFormData(Session session, FormDataBean formData, String mainFormDataUUID) {
		String tableId = formData.getFormDefinitionUuid();
		FormDefinition tableInfo = (FormDefinition) session.get(FormDefinition.class, tableId);// formDefinitionDao.get(tableId);
		String tableName = formData.getTableName();
		List<FormDataRecordBean> recordDataList = formData.getRecordList();
		if (mainFormDataUUID != "" && mainFormDataUUID != null) {
			String dataSqldelete = "delete from " + tableName + " where parent_id = '" + mainFormDataUUID + "'";
			session.createSQLQuery(dataSqldelete).executeUpdate();
		}
		for (FormDataRecordBean record : recordDataList) {
			StringBuffer columns = new StringBuffer("");
			StringBuffer values = new StringBuffer("");
			String sql = "";
			DateFormat formatDate1 = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat formatDate2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			DateFormat formatDate3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DateFormat formatDate4 = new SimpleDateFormat("HH:mm");
			DateFormat formatDate5 = new SimpleDateFormat("HH:mm:ss");
			if ("1".equals(formData.getTableType())) {// 为主表
				values.append("modifier = ?" + ",modify_time = ?" + ", form_id = ?");
				List<FormDataColValBean> colInfoList = record.getColValList();
				for (FormDataColValBean colVal : colInfoList) {
					values.append(",").append(colVal.getColEnName()).append("=").append("?");
				}
				sql = "update " + tableName + " set " + values.toString() + " where uuid = '" + mainFormDataUUID + "'";
				SQLQuery sqlquery = session.createSQLQuery(sql);
				sqlquery.setString(0, SpringSecurityUtils.getCurrentUserId());
				sqlquery.setTimestamp(1, Calendar.getInstance().getTime());
				sqlquery.setString(2, tableInfo.getUuid());
				for (int i = 0; i < colInfoList.size(); i++) {
					FormDataColValBean colVal = colInfoList.get(i);
					String valueCol = colVal.getValue() == null ? null : colVal.getValue();
					if (colVal.getDataType() != null && DytableConfig.INPUTTYPE_DATE.equals(colVal.getDataType())) {
						if (StringUtils.isBlank(valueCol)) {
							sqlquery.setTimestamp(i + 3, null);
						} else {
							try {
								Date date = formatDate1.parse(valueCol);
								sqlquery.setTimestamp(i + 3, date);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}//yyyy-MM-dd

						}
					} else if (colVal.getDataType() != null
							&& DytableConfig.INPUTTYPE_DATETIMEMIN.equals(colVal.getDataType())) {
						if (StringUtils.isBlank(valueCol)) {
							sqlquery.setTimestamp(i + 3, null);
						} else {
							try {
								Date date = formatDate2.parse(valueCol);
								sqlquery.setTimestamp(i + 3, date);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}//yyyy-MM-dd HH:mm

						}
					} else if (colVal.getDataType() != null
							&& DytableConfig.INPUTTYPE_DATETIMESEC.equals(colVal.getDataType())) {
						if (StringUtils.isBlank(valueCol)) {
							sqlquery.setTimestamp(i + 3, null);
						} else {
							try {
								Date date = formatDate3.parse(valueCol);
								sqlquery.setTimestamp(i + 3, date);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}//yyyy-MM-dd HH:mm:ss
						}
					} else if (colVal.getDataType() != null
							&& DytableConfig.INPUTTYPE_TIMEMIN.equals(colVal.getDataType())) {
						if (StringUtils.isBlank(valueCol)) {
							sqlquery.setTimestamp(i + 3, null);
						} else {
							try {
								Date date = formatDate4.parse(valueCol);
								sqlquery.setTimestamp(i + 3, date);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}//HH:mm
						}
					} else if (colVal.getDataType() != null
							&& DytableConfig.INPUTTYPE_TIMESEC.equals(colVal.getDataType())) {
						if (StringUtils.isBlank(valueCol)) {
							sqlquery.setTimestamp(i + 3, null);
						} else {
							try {
								Date date = formatDate5.parse(valueCol);
								sqlquery.setTimestamp(i + 3, date);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}//HH:mm:ss

						}
					} else {
						sqlquery.setString(i + 3, valueCol);
					}
				}
				sqlquery.executeUpdate();
			} else {// 从表
				int flag = 0;//flag = 0 时：新增，flag = 1 时：更新
				String uuid = StringUtils.isEmpty(record.getUuid()) ? DynamicUtils.getRandomUUID() : record.getUuid();
				if (flag == 0) {
					columns.append("uuid,creator,create_time,modifier,modify_time,sort_order,form_id,parent_id");
					values.append("'")
							.append(StringUtils.isEmpty(record.getUuid()) ? DynamicUtils.getRandomUUID() : record
									.getUuid()).append("',").append("?").append(",").append("?").append(",")
							.append("?").append(",").append("?").append(",'").append(record.getSortorder())
							.append("','").append(tableInfo.getUuid()).append("','").append(mainFormDataUUID)
							.append("'");
					List<FormDataColValBean> colInfoList = record.getColValList();
					for (FormDataColValBean colVal : colInfoList) {
						columns.append(",").append(colVal.getColEnName());
						values.append(",?");
					}
					sql = "insert into " + tableName + " (" + columns.toString() + ") values (" + values + ")";
					SQLQuery sqlquery = session.createSQLQuery(sql);
					sqlquery.setString(0, SpringSecurityUtils.getCurrentUserId());
					sqlquery.setTimestamp(1, Calendar.getInstance().getTime());
					sqlquery.setString(2, SpringSecurityUtils.getCurrentUserId());
					sqlquery.setTimestamp(3, Calendar.getInstance().getTime());
					for (int i = 0; i < colInfoList.size(); i++) {
						FormDataColValBean colVal = colInfoList.get(i);

						String value = colVal.getValue() == null ? null : colVal.getValue();
						if (colVal.getDataType() != null && "2".equals(colVal.getDataType())) {// 日期(暂时存储为字符类型)
							sqlquery.setString(i + 4, value);
						} else {
							sqlquery.setString(i + 4, value);
						}
					}
					sqlquery.executeUpdate();
				} else {
					values.append("modifier = ?" + ",modify_time = ?" + ", form_id = ?" + ",parent_id = '"
							+ mainFormDataUUID + "'  ");
					List<FormDataColValBean> colInfoList = record.getColValList();
					for (FormDataColValBean colVal : colInfoList) {
						values.append(",").append(colVal.getColEnName()).append("=").append("?");
					}
					sql = "update " + tableName + " set " + values.toString() + " where uuid='" + uuid + "'";
					SQLQuery sqlquery = session.createSQLQuery(sql);
					sqlquery.setString(0, SpringSecurityUtils.getCurrentUserId());
					sqlquery.setTimestamp(1, Calendar.getInstance().getTime());
					sqlquery.setString(2, tableInfo.getUuid());
					for (int i = 0; i < colInfoList.size(); i++) {
						FormDataColValBean colVal = colInfoList.get(i);
						String valueCol = colVal.getValue() == null ? null : colVal.getValue();
						if (colVal.getDataType() != null && DytableConfig.INPUTTYPE_DATE.equals(colVal.getDataType())) {
							if (StringUtils.isBlank(valueCol)) {
								sqlquery.setTimestamp(i + 3, null);
							} else {
								try {
									Date date = formatDate1.parse(valueCol);
									sqlquery.setTimestamp(i + 3, date);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}//yyyy-MM-dd

							}
						} else if (colVal.getDataType() != null
								&& DytableConfig.INPUTTYPE_DATETIMEMIN.equals(colVal.getDataType())) {
							if (StringUtils.isBlank(valueCol)) {
								sqlquery.setTimestamp(i + 3, null);
							} else {
								try {
									Date date = formatDate2.parse(valueCol);
									sqlquery.setTimestamp(i + 3, date);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}//yyyy-MM-dd HH:mm

							}
						} else if (colVal.getDataType() != null
								&& DytableConfig.INPUTTYPE_DATETIMESEC.equals(colVal.getDataType())) {
							if (StringUtils.isBlank(valueCol)) {
								sqlquery.setTimestamp(i + 3, null);
							} else {
								try {
									Date date = formatDate3.parse(valueCol);
									sqlquery.setTimestamp(i + 3, date);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}//yyyy-MM-dd HH:mm:ss

							}
						} else if (colVal.getDataType() != null
								&& DytableConfig.INPUTTYPE_TIMEMIN.equals(colVal.getDataType())) {
							if (StringUtils.isBlank(valueCol)) {
								sqlquery.setTimestamp(i + 3, null);
							} else {
								try {
									Date date = formatDate4.parse(valueCol);
									sqlquery.setTimestamp(i + 3, date);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}//HH:mm

							}
						} else if (colVal.getDataType() != null
								&& DytableConfig.INPUTTYPE_TIMESEC.equals(colVal.getDataType())) {
							if (StringUtils.isBlank(valueCol)) {
								sqlquery.setTimestamp(i + 3, null);
							} else {
								try {
									Date date = formatDate5.parse(valueCol);
									sqlquery.setTimestamp(i + 3, date);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}//HH:mm:ss

							}
						} else {
							sqlquery.setString(i + 3, valueCol);
						}
					}
					sqlquery.executeUpdate();
				}
			}
		}
	}

	/**
	 * 删除单张表数据
	 * 
	 * @param session
	 * @param tableName
	 * @param uid
	 * @param tableType
	 */
	private void deleteSingleFormData(Session session, String tableName, String uid, String tableType) {
		StringBuffer sb = new StringBuffer("delete from ");
		sb.append(tableName).append(" where ");
		if ("1".equals(tableType)) {// 主表
			sb.append("uuid = '").append(uid).append("'");
		} else {// 从表
			sb.append("parent_id = '").append(uid).append("'");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("删除表单数据:" + sb.toString());
		}
		session.createSQLQuery(sb.toString()).executeUpdate();
	}

	/**
	 * 保存单张表数据
	 * 
	 * @param session
	 * @param formData
	 * @param mainFormDataUUID
	 */
	private void saveSingleFormData(Session session, FormDataBean formData, String mainFormDataUUID) {
		String tableId = formData.getFormDefinitionUuid();
		FormDefinition tableInfo = (FormDefinition) session.get(FormDefinition.class, tableId);// formDefinitionDao.get(tableId);
		String tableName = formData.getTableName();
		List<FormDataRecordBean> recordDataList = formData.getRecordList();

		for (FormDataRecordBean record : recordDataList) {
			StringBuffer columns = new StringBuffer("");
			StringBuffer values = new StringBuffer("");

			if ("1".equals(formData.getTableType())) {// 为主表
				columns.append("uuid,creator,create_time,modifier,modify_time,form_id");
				values.append("'").append(mainFormDataUUID).append("',").append("?").append(",").append("?")
						.append(",").append("?").append(",").append("?").append(",'").append(tableInfo.getUuid())
						.append("'");
			} else {// 从表
				columns.append("uuid,creator,create_time,modifier,modify_time,sort_order,form_id,parent_id");
				values.append("'")
						.append(StringUtils.isEmpty(record.getUuid()) ? DynamicUtils.getRandomUUID() : record.getUuid())
						.append("',").append("?").append(",").append("?").append(",").append("?").append(",")
						.append("?").append(",'").append(record.getSortorder()).append("','")
						.append(tableInfo.getUuid()).append("','").append(mainFormDataUUID).append("'");
			}

			List<FormDataColValBean> colInfoList = record.getColValList();
			for (FormDataColValBean colVal : colInfoList) {
				columns.append(",").append(colVal.getColEnName());
				values.append(",?");
			}

			String sql = "insert into " + tableName + " (" + columns.toString() + ") values (" + values + ")";
			if (logger.isDebugEnabled()) {
				logger.debug("插入表单数据SQL语句：" + sql);
			}

			SQLQuery sqlquery = session.createSQLQuery(sql);
			sqlquery.setString(0, SpringSecurityUtils.getCurrentUserId());
			sqlquery.setTimestamp(1, Calendar.getInstance().getTime());
			sqlquery.setString(2, SpringSecurityUtils.getCurrentUserId());
			sqlquery.setTimestamp(3, Calendar.getInstance().getTime());

			// 暂时数据类型为：varchar,date,int,float,后面数据字典模块加上后需要用数据字典替换
			if (logger.isDebugEnabled()) {
				logger.debug("表:" + tableName + ",字段数：" + colInfoList.size());
			}
			DateFormat formatDate1 = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat formatDate2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			DateFormat formatDate3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DateFormat formatDate4 = new SimpleDateFormat("HH:mm");
			DateFormat formatDate5 = new SimpleDateFormat("HH:mm:ss");
			for (int i = 0; i < colInfoList.size(); i++) {
				FormDataColValBean colVal = colInfoList.get(i);
				String value = colVal.getValue() == null ? "" : colVal.getValue();
				if (colVal.getDataType() != null && DytableConfig.INPUTTYPE_DATE.equals(colVal.getDataType())) {
					if (StringUtils.isBlank(value)) {
						sqlquery.setTimestamp(i + 4, null);
					} else {
						try {
							Date date = formatDate1.parse(value);
							sqlquery.setTimestamp(i + 4, date);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}//yyyy-MM-dd

					}
				} else if (colVal.getDataType() != null
						&& DytableConfig.INPUTTYPE_DATETIMEMIN.equals(colVal.getDataType())) {
					if (StringUtils.isBlank(value)) {
						sqlquery.setTimestamp(i + 4, null);
					} else {
						try {
							Date date = formatDate2.parse(value);
							sqlquery.setTimestamp(i + 4, date);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}//yyyy-MM-dd HH:mm

					}
				} else if (colVal.getDataType() != null
						&& DytableConfig.INPUTTYPE_DATETIMESEC.equals(colVal.getDataType())) {
					if (StringUtils.isBlank(value)) {
						sqlquery.setTimestamp(i + 4, null);
					} else {
						try {
							Date date = formatDate3.parse(value);
							sqlquery.setTimestamp(i + 4, date);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}//yyyy-MM-dd HH:mm:ss

					}
				} else if (colVal.getDataType() != null && DytableConfig.INPUTTYPE_TIMEMIN.equals(colVal.getDataType())) {
					if (StringUtils.isBlank(value)) {
						sqlquery.setTimestamp(i + 4, null);
					} else {
						try {
							Date date = formatDate4.parse(value);
							sqlquery.setTimestamp(i + 4, date);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}//HH:mm

					}
				} else if (colVal.getDataType() != null && DytableConfig.INPUTTYPE_TIMESEC.equals(colVal.getDataType())) {
					if (StringUtils.isBlank(value)) {
						sqlquery.setTimestamp(i + 4, null);
					} else {
						try {
							Date date = formatDate5.parse(value);
							sqlquery.setTimestamp(i + 4, date);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}//HH:mm:ss

					}
				} else {
					sqlquery.setString(i + 4, value);
				}
			}
			sqlquery.executeUpdate();
		}
	}

	/**
	 * 获取表单数据
	 * 
	 * @param tableType
	 * @param tableName
	 * @param dataUid
	 * @return
	 */
	public List<Map> getFormDataList(String tableType, String tableName, String dataUid, String temp) {
		List<Map> list = new ArrayList();

		Session session = formDefinitionDao.getSession();

		String sql;
		if ("1".equals(tableType)) {// 主表
			sql = "SELECT *\n" + "  FROM DYTABLE_FIELD_DEFINITION\n" + " WHERE FORM_UUID IN\n"
					+ "       (SELECT FORM_ID\n" + "          FROM " + tableName + "\n" + "         WHERE UUID = '"
					+ dataUid + "')";
		} else {// 从表
			sql = "SELECT *\n" + "  FROM DYTABLE_FIELD_DEFINITION\n" + " WHERE FORM_UUID IN\n"
					+ "       (SELECT FORM_ID\n" + "          FROM " + tableName + "\n"
					+ "         WHERE PARENT_ID = '" + dataUid + "')";
		}
		if (logger.isDebugEnabled()) {
			logger.debug("查询表单字段信息列表SQL:" + sql);
		}
		List<Map> fieldInfoList = session.createSQLQuery(sql).setResultTransformer(FormDataResultTransformer.INSTANCE)
				.list();

		StringBuffer dataSql = new StringBuffer("select uuid,creator,create_time,modify_time,sort_order");
		Set<String> choorseNames = new HashSet<String>();

		String fileName3 = "";
		String fileName_date = "";
		String fileName_dateTimeMin = "";
		String fileName_dateTimeSec = "";
		String fileName_timeMin = "";
		String fileName_timeSec = "";
		List<String> fileFieldNames = new ArrayList<String>();
		for (int i = 0; i < fieldInfoList.size(); i++) {
			Map map = fieldInfoList.get(i);
			dataSql.append(",").append(null == map.get("fieldname") ? map.get("FIELDNAME") : map.get("fieldname"));
			String type = (String) map.get("input_data_type");
			if (DytableConfig.INPUTMODE_ORCHOOSE.equals(type) || DytableConfig.INPUTMODE_ORCHOOSE1.equals(type)
					|| DytableConfig.INPUTMODE_ORCHOOSE2.equals(type) || DytableConfig.INPUTMODE_ORCHOOSE3.equals(type)
					|| DytableConfig.INPUTMODE_ORCHOOSE4.equals(type)) {
				choorseNames.add((String) map.get("fieldname"));
			}

			//解析出附件字段
			if (DytableConfig.INPUTMODE_ACCESSORY1.equals(type) || DytableConfig.INPUTMODE_ACCESSORY2.equals(type)
					|| DytableConfig.INPUTMODE_ACCESSORY3.equals(type) || DytableConfig.INPUTMODE_TEXTBODY.equals(type)) {
				String fileName = (String) map.get("fieldname");
				if (fileName != null && fileName.trim().length() > 0)
					fileFieldNames.add(fileName.toLowerCase());
			}

			if (DytableConfig.INPUTMODE_CKEDIT.equals(type)
					|| DytableConfig.INPUTMODE_TREESELECT.equals((String) map.get("type"))) {//富文本字段
				fileName3 += ";" + (String) map.get("fieldname");
			}
			if (DytableConfig.INPUTTYPE_DATE.equals((String) map.get("type"))) {
				fileName_date += ";" + (String) map.get("fieldname");
			}
			if (DytableConfig.INPUTTYPE_DATETIMEMIN.equals((String) map.get("type"))) {
				fileName_dateTimeMin += ";" + (String) map.get("fieldname");
			}
			if (DytableConfig.INPUTTYPE_DATETIMESEC.equals((String) map.get("type"))) {
				fileName_dateTimeSec += ";" + (String) map.get("fieldname");
			}
			if (DytableConfig.INPUTTYPE_TIMEMIN.equals((String) map.get("type"))) {
				fileName_timeMin += ";" + (String) map.get("fieldname");
			}
			if (DytableConfig.INPUTTYPE_TIMESEC.equals((String) map.get("type"))) {
				fileName_timeSec += ";" + (String) map.get("fieldname");
			}
		}

		fileName3 = fileName3.replaceFirst(";", "");
		fileName_date = fileName_date.replaceFirst(";", "");
		fileName_dateTimeMin = fileName_dateTimeMin.replaceFirst(";", "");
		fileName_dateTimeSec = fileName_dateTimeSec.replaceFirst(";", "");
		fileName_timeMin = fileName_timeMin.replaceFirst(";", "");
		fileName_timeSec = fileName_timeSec.replaceFirst(";", "");
		dataSql.append(" from ").append(tableName);
		if ("1".equals(tableType)) {// 主表
			dataSql.append(" where uuid = '").append(dataUid).append("'");
		} else {// 从表
			dataSql.append(" where parent_id = '").append(dataUid).append("'");
		}
		dataSql.append(" order by create_time,sort_order asc");
		if (logger.isDebugEnabled()) {
			logger.debug("查询表单数据SQL：" + dataSql.toString());
		}

		list = session.createSQLQuery(dataSql.toString()).setResultTransformer(FormDataResultTransformer.INSTANCE)
				.list();
		/**
		 * 此处把uuid转化成成id是因为jqgrid要包含有id这个列，不然setRowData无效，
		 * 暂时还没找到是否是jqGrid问题还是有参数可配置
		 ****************/
		List reList = new ArrayList();
		for (Map map : list) {
			Iterator ite = map.keySet().iterator();
			Map obj = new HashMap();
			Map<String, User> userMap = new HashMap<String, User>();
			Set<String> users = new HashSet<String>();
			Set<String> depts = new HashSet<String>();
			Set<String> units = new HashSet<String>();
			while (ite.hasNext()) {
				String key = ite.next().toString();
				obj.put(StringUtils.equals("uuid", key.toLowerCase()) ? "id" : key.toLowerCase(), map.get(key));
			}

			/*for(String fileFieldName: fileFieldNames){
				fileFieldName = fileFieldName.toLowerCase();
				String dataUUID = (String)obj.get("id");
				List<MongoFileEntity> fileEntities = mongoFileService.getFilesFromFolder(dataUUID, fileFieldName);
				List<FileUpload> files = new ArrayList<FileUpload>();
				if(fileEntities != null){
					for(MongoFileEntity fileEntity: fileEntities){
						FileUpload upload = new FileUpload();
						upload.setFileID(fileEntity.getId());
						upload.setFilename(fileEntity.getFileName());
						files.add(upload);
					}
				}
				
				obj.put(fileFieldName, files);
			}*/

			//}

			if (fileName3 != null) {
				String[] fileName3s = fileName3.split(";");
				for (int i = 0; i < fileName3s.length; i++) {
					if (map.get(fileName3s[i].toLowerCase()) != null
							&& StringUtils.isNotBlank(map.get(fileName3s[i].toLowerCase()).toString())) {
						Clob value = (Clob) map.get(fileName3s[i].toLowerCase());
						String reString = "";
						Reader is;
						StringBuffer sb = new StringBuffer();
						BufferedReader br = null;
						try {
							is = value.getCharacterStream();
							// 得到流
							br = new BufferedReader(is);
							String s = br.readLine();

							while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
								sb.append(s);
								s = br.readLine();
							}
						} catch (Exception e) {
							e.printStackTrace();
							return null;
						} finally {
							try {
								if (br != null) {
									br.close();
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						reString = sb.toString();
						map.put(fileName3s[i], reString);
						obj.put(fileName3s[i], reString);
					}
				}
			}
			if (!StringUtils.isBlank(fileName_date)) {
				String[] fileName_date_ = fileName_date.split(";");
				for (int i = 0; i < fileName_date_.length; i++) {
					if (map.get(fileName_date_[i].toLowerCase()) != null
							&& StringUtils.isNotBlank(map.get(fileName_date_[i].toLowerCase()).toString())) {
						Date value = (Date) map.get(fileName_date_[i].toLowerCase());
						DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
						String dateStr = format.format(value);
						obj.put(fileName_date_[i].toLowerCase(), dateStr);
					}
				}
			}
			if (!StringUtils.isBlank(fileName_dateTimeMin)) {
				String[] fileName_dateTimeMin_ = fileName_dateTimeMin.split(";");
				for (int i = 0; i < fileName_dateTimeMin_.length; i++) {
					if (map.get(fileName_dateTimeMin_[i].toLowerCase()) != null
							&& StringUtils.isNotBlank(map.get(fileName_dateTimeMin_[i].toLowerCase()).toString())) {
						Date value = (Date) map.get(fileName_dateTimeMin_[i].toLowerCase());
						DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
						String dateStr = format.format(value);
						obj.put(fileName_dateTimeMin_[i].toLowerCase(), dateStr);
					}
				}
			}
			if (!StringUtils.isBlank(fileName_dateTimeSec)) {
				String[] fileName_dateTimeSec_ = fileName_dateTimeSec.split(";");
				for (int i = 0; i < fileName_dateTimeSec_.length; i++) {
					if (map.get(fileName_dateTimeSec_[i].toLowerCase()) != null
							&& StringUtils.isNotBlank(map.get(fileName_dateTimeSec_[i].toLowerCase()).toString())) {
						Date value = (Date) map.get(fileName_dateTimeSec_[i].toLowerCase());
						DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String dateStr = format.format(value);
						obj.put(fileName_dateTimeSec_[i].toLowerCase(), dateStr);
					}
				}
			}
			if (!StringUtils.isBlank(fileName_timeMin)) {
				String[] fileName_timeMin_ = fileName_timeMin.split(";");
				for (int i = 0; i < fileName_timeMin_.length; i++) {
					if (map.get(fileName_timeMin_[i].toLowerCase()) != null
							&& StringUtils.isNotBlank(map.get(fileName_timeMin_[i].toLowerCase()).toString())) {
						Date value = (Date) map.get(fileName_timeMin_[i].toLowerCase());
						DateFormat format = new java.text.SimpleDateFormat("HH:mm");
						String dateStr = format.format(value);
						obj.put(fileName_timeMin_[i].toLowerCase(), dateStr);
					}
				}
			}
			if (!StringUtils.isBlank(fileName_timeSec)) {
				String[] fileName_timeSec_ = fileName_timeSec.split(";");
				for (int i = 0; i < fileName_timeSec_.length; i++) {
					if (map.get(fileName_timeSec_[i].toLowerCase()) != null
							&& StringUtils.isNotBlank(map.get(fileName_timeSec_[i].toLowerCase()).toString())) {
						Date value = (Date) map.get(fileName_timeSec_[i].toLowerCase());
						DateFormat format = new java.text.SimpleDateFormat("HH:mm:ss");
						String dateStr = format.format(value);
						obj.put(fileName_timeSec_[i].toLowerCase(), dateStr);
					}
				}
			}
			/*if (map.get("body_col") != null && StringUtils.isNotBlank(map.get("body_col").toString())) {
				String value = (String) map.get("body_col");
				String[] values = value.split(",");
				String moduleName = "DY_TABLE_FORM";
				List<FileEntity> files = fileService.getFiles(moduleName, values[0]);
				obj.put("bodyFile", files);
			}*/
			for (String chooseName : choorseNames) {
				users.clear();
				depts.clear();
				units.clear();
				String chooseValue = (String) map.get(chooseName.toLowerCase());
				if (chooseValue != null) {
					String[] tmp = chooseValue.split(",");
					if (tmp.length == 2) {
						chooseValue = tmp[1];
					}
					map.put(chooseName.toLowerCase(), chooseValue);

					String[] chooseValueSplit = chooseValue.split(";");
					for (int i = 0; i < chooseValueSplit.length; i++) {
						if (chooseValueSplit[i].startsWith(IdPrefix.USER.getValue())) {
							users.add(chooseValueSplit[i]);
						} else if (chooseValueSplit[i].startsWith(IdPrefix.DEPARTMENT.getValue())) {
							depts.add(chooseValueSplit[i]);
						} else {
							units.add(chooseValueSplit[i]);
						}
					}
					StringBuilder descNames = new StringBuilder();

					StringBuilder descNamesUser = new StringBuilder();
					if (users.size() > 0) {
						List<User> user = orgApiFacade.getUsersByIds(users);
						userMap = ConvertUtils.convertElementToMap(user, "id");

						Iterator<User> it = user.iterator();
						while (it.hasNext()) {
							String descName = it.next().getUserName();
							descNamesUser.append(descName);
							if (it.hasNext()) {
								descNamesUser.append(";");
							}
						}
					}
					String descNamesUsers = descNamesUser.toString();

					StringBuilder descNameDept = new StringBuilder();
					if (depts.size() > 0) {
						List<Department> dept = orgApiFacade.getDepartmentByIds(depts);
						Iterator<Department> it2 = dept.iterator();
						while (it2.hasNext()) {
							String descName = it2.next().getName();
							descNameDept.append(descName);
							if (it2.hasNext()) {
								descNameDept.append(";");
							}
						}
					}
					String descNameDepts = descNameDept.toString();

					String unitNames = "";
					if (units.size() > 0) {
						for (String unitId : units) {
							CommonUnit commonUnit = unitApiFacade.getCommonUnitById(unitId);
							if (commonUnit != null) {
								unitNames += ";" + commonUnit.getName();
							} else {
								unitNames += ";" + unitId;
							}
						}
						unitNames = unitNames.replaceFirst(";", "");
					}

					if (!StringUtils.isBlank(unitNames)) {
						descNames.append(unitNames);
					} else {
						if (descNamesUser.toString().equals("") && !descNameDept.toString().equals("")) {
							descNames.append(descNameDept.toString());
						} else if (descNameDept.toString().equals("") && !descNamesUser.toString().equals("")) {
							descNames.append(descNamesUser.toString());
						} else if (!descNamesUser.toString().equals("") && !descNameDept.toString().equals("")) {
							descNames.append(descNamesUser.toString() + ";" + descNameDept.toString());
						}
					}
					obj.put(chooseName, descNames.toString() + "," + chooseValue);
				}
			}
			reList.add(obj);
		}

		return reList;
	}

	public List<Map<String, Object>> getSubFormDataList(String subTableName, String sourceFormUuid,
			String sourceDataUuid, String whereSql, Map<String, Object> values) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Session session = formDefinitionDao.getSession();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select *\n from ").append(subTableName).append(" where parent_id = '").append(sourceDataUuid)
				.append("'").append(" and ").append(whereSql);
		SQLQuery sqlquery = session.createSQLQuery(sqlBuffer.toString());
		sqlquery.setProperties(values);
		list = sqlquery.setResultTransformer(FormDataResultTransformer.INSTANCE).list();
		return list;
	}

	/**
	 * 获取表单数据,带条件查询
	 * 
	 * @param tableType
	 * @param tableName
	 * @param dataUid
	 * @param whereSql
	 * @return
	 */
	public List<Map> getFormDataList2(String tableType, String tableName, String dataUid, String temp, String whereSql) {
		List<Map> list = new ArrayList();

		Session session = formDefinitionDao.getSession();

		String sql;
		if ("1".equals(tableType)) {// 主表
			sql = "SELECT *\n" + "  FROM DYTABLE_FIELD_DEFINITION\n" + " WHERE FORM_UUID IN\n"
					+ "       (SELECT FORM_ID\n" + "          FROM " + tableName + "\n" + "         WHERE UUID = '"
					+ dataUid + "')";
		} else {// 从表
			sql = "SELECT *\n" + "  FROM DYTABLE_FIELD_DEFINITION\n" + " WHERE FORM_UUID IN\n"
					+ "       (SELECT FORM_ID\n" + "          FROM " + tableName + "\n"
					+ "         WHERE PARENT_ID = '" + dataUid + "')";
		}
		if (logger.isDebugEnabled()) {
			logger.debug("查询表单字段信息列表SQL:" + sql);
		}
		List<Map> fieldInfoList = session.createSQLQuery(sql).setResultTransformer(FormDataResultTransformer.INSTANCE)
				.list();

		StringBuffer dataSql = new StringBuffer("select uuid,creator,create_time,modify_time");
		Set<String> choorseNames = new HashSet<String>();

		List<String> fileFieldNames = new ArrayList<String>();
		for (int i = 0; i < fieldInfoList.size(); i++) {
			Map map = fieldInfoList.get(i);
			dataSql.append(",").append(null == map.get("fieldname") ? map.get("FIELDNAME") : map.get("fieldname"));
			String type = (String) map.get("input_data_type");
			if ("8".equals(type) || "9".equals(type) || "10".equals(type) || "11".equals(type)) {
				choorseNames.add((String) map.get("fieldname"));
			}

			//解析出附件字段
			if (DytableConfig.INPUTMODE_ACCESSORY1.equals(type) || DytableConfig.INPUTMODE_ACCESSORY2.equals(type)
					|| DytableConfig.INPUTMODE_ACCESSORY3.equals(type) || DytableConfig.INPUTMODE_TEXTBODY.equals(type)) {
				String fileName = (String) map.get("fieldname");
				if (fileName != null && fileName.trim().length() > 0)
					fileFieldNames.add(fileName.toLowerCase());
			}

		}
		dataSql.append(" from ").append(tableName);
		if ("1".equals(tableType)) {// 主表
			dataSql.append(" where uuid = '").append(dataUid).append("'");
			if (whereSql != null && !whereSql.equals("")) {
				dataSql.append(" and " + whereSql);
			}
		} else {// 从表
			dataSql.append(" where parent_id = '").append(dataUid).append("'");
		}
		dataSql.append(" order by create_time,sort_order asc");
		if (logger.isDebugEnabled()) {
			logger.debug("查询表单数据SQL：" + dataSql.toString());
		}

		list = session.createSQLQuery(dataSql.toString()).setResultTransformer(FormDataResultTransformer.INSTANCE)
				.list();
		/**
		 * 此处把uuid转化成成id是因为jqgrid要包含有id这个列，不然setRowData无效，
		 * 暂时还没找到是否是jqGrid问题还是有参数可配置
		 ****************/
		List reList = new ArrayList();
		for (Map map : list) {
			Iterator ite = map.keySet().iterator();
			Map obj = new HashMap();
			Map<String, User> userMap = new HashMap<String, User>();
			Set<String> users = new HashSet<String>();
			Set<String> depts = new HashSet<String>();
			while (ite.hasNext()) {
				String key = ite.next().toString();
				obj.put(StringUtils.equals("uuid", key.toLowerCase()) ? "id" : key.toLowerCase(), map.get(key));
			}
			/*if (map.get(fileName) != null && StringUtils.isNotBlank(map.get(fileName).toString())) {
				String value = (String) map.get(fileName);
				String moduleName = "DY_TABLE_FORM";
				List<FileEntity> files = fileService.downFiles(moduleName, value);
				obj.put("fileList", files);
			}
			if (fileName2 != null) {
				String[] fileName2s = fileName2.split(";");
				for (int i = 0; i < fileName2s.length; i++) {
					if (map.get(fileName2s[i].toLowerCase()) != null
							&& StringUtils.isNotBlank(map.get(fileName2s[i].toLowerCase()).toString())) {
						String value = (String) map.get(fileName2s[i].toLowerCase());
						String moduleName = "DY_TABLE_FORM";
						List<FileEntity> files = fileService.getFiles(moduleName, value);
						obj.put("fileList" + fileName2s[i].toLowerCase(), files);
					}
				}
			}

			if (map.get("body_col") != null && StringUtils.isNotBlank(map.get("body_col").toString())) {
				String value = (String) map.get("body_col");
				String[] values = value.split(",");
				String moduleName = "DY_TABLE_FORM";
				List<FileEntity> files = fileService.downFiles(moduleName, value);
				obj.put("bodyFile", files);
			}*/

			for (String fileFieldName : fileFieldNames) {
				fileFieldName = fileFieldName.toLowerCase();
				String dataUUID = (String) obj.get("id");
				List<MongoFileEntity> fileEntities = mongoFileService.getFilesFromFolder(dataUUID, fileFieldName);
				List<FileUpload> files = new ArrayList<FileUpload>();
				if (fileEntities != null) {
					for (MongoFileEntity fileEntity : fileEntities) {
						FileUpload upload = new FileUpload();
						upload.setFileID(fileEntity.getId());
						upload.setFilename(fileEntity.getFileName());
						files.add(upload);
					}
				}
				obj.put(fileFieldName, files);
			}

			for (String chooseName : choorseNames) {
				users.clear();
				depts.clear();
				String chooseValue = (String) map.get(chooseName);
				if (chooseValue != null) {
					String[] chooseValueSplit = chooseValue.split(";");
					for (int i = 0; i < chooseValueSplit.length; i++) {
						if (chooseValueSplit[i].startsWith(IdPrefix.USER.getValue())) {
							users.add(chooseValueSplit[i]);
						} else if (chooseValueSplit[i].startsWith(IdPrefix.DEPARTMENT.getValue())) {
							depts.add(chooseValueSplit[i]);
						}
					}
					StringBuilder descNames = new StringBuilder();
					StringBuilder descNamesUser = new StringBuilder();
					if (users.size() > 0) {
						List<User> user = orgApiFacade.getUsersByIds(users);
						userMap = ConvertUtils.convertElementToMap(user, "id");

						Iterator<User> it = user.iterator();
						while (it.hasNext()) {
							String descName = it.next().getUserName();
							descNamesUser.append(descName);
							if (it.hasNext()) {
								descNamesUser.append(";");
							}
						}
					}
					StringBuilder descNameDept = new StringBuilder();
					if (depts.size() > 0) {
						List<Department> dept = orgApiFacade.getDepartmentByIds(depts);
						Iterator<Department> it2 = dept.iterator();
						while (it2.hasNext()) {
							String descName = it2.next().getName();
							descNameDept.append(descName);
							if (it2.hasNext()) {
								descNameDept.append(";");
							}
						}
					}
					if (descNamesUser.toString().equals("")) {
						descNames.append(descNameDept.toString());
					} else if (descNameDept.toString().equals("")) {
						descNames.append(descNamesUser.toString());
					} else {
						descNames.append(descNamesUser.toString() + ";" + descNameDept.toString());
					}
					obj.put(chooseName, descNames.toString() + "," + chooseValue);
				}
			}
			reList.add(obj);
		}

		return reList;
	}

	/**
	 * 
	 * 开放的查找表单数据的方法
	 * 
	 * @param tableName
	 * @param whereSql
	 * @return
	 */
	public List<Map> getFormDataBySql(String tableName, String whereSql) {
		Session session = formDefinitionDao.getSession();
		String sql = "";
		if (!StringUtils.isBlank(whereSql)) {
			sql = "select * from " + tableName + " where " + whereSql + " order by sort_order";
		} else {
			sql = "select * from " + tableName + " order by sort_order";
		}
		List<Map> list = session.createSQLQuery(sql).setResultTransformer(FormDataResultTransformer.INSTANCE).list();
		return list;
	}

	/**
	 * 
	 * 开放的查找表单数据的方法
	 * 
	 * @param tableName
	 * @param whereSql
	 * @return
	 */
	public List<Map> getFormDataBySql2(String tableName, String whereSql, String maxSql) {
		Session session = formDefinitionDao.getSession();
		String sql = "";
		if (!StringUtils.isBlank(whereSql) && !StringUtils.isBlank(maxSql)) {
			sql = "select " + maxSql + " from " + tableName + " where " + whereSql;
		} else if (!StringUtils.isBlank(whereSql)) {
			sql = "select * from " + tableName + " where " + whereSql;
		} else {
			sql = "select * from " + tableName;
		}
		List<Map> list = session.createSQLQuery(sql).setResultTransformer(FormDataResultTransformer.INSTANCE).list();
		return list;
	}

}
