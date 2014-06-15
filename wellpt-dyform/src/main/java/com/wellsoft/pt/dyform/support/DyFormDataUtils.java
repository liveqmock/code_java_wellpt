package com.wellsoft.pt.dyform.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.wellsoft.pt.dyform.support.DyFormConfig.EnumDySysVariable;
import com.wellsoft.pt.repository.entity.LogicFileInfo;
import com.wellsoft.pt.repository.entity.mongo.file.MongoFileEntity;
import com.wellsoft.pt.repository.service.MongoFileService;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

public class DyFormDataUtils {
	private final DyFormDefinitionUtils dUtils;
	private Map<String, Object> formDataColMap = null;
	private MongoFileService mongoFileService = null;

	public DyFormDataUtils(final DyFormDefinitionUtils dUtils, MongoFileService mongoFileService,
			Map<String, Object> formDataColMap) {
		this.dUtils = dUtils;
		this.formDataColMap = formDataColMap;
		this.mongoFileService = mongoFileService;
	}

	private void processFileField(String fieldName) {
		Object fileInfoObjs = formDataColMap.get(fieldName);
		if (fileInfoObjs instanceof List) {
			if (((List) (fileInfoObjs)).size() == 0) {
				formDataColMap.put(fieldName, null);
				return;
			}
		}
		if (fileInfoObjs == null || ((String) fileInfoObjs).trim().length() == 0) {
			return;
		} else {
			String dataUuid = (String) formDataColMap.get("uuid");
			List<String> fileIds = getFileIds(fileInfoObjs);
			fileOperate(dataUuid, fieldName, fileIds, true);
			formDataColMap.put(fieldName, null);
		}

	}

	@SuppressWarnings({ "static-method", "unchecked" })
	private List<Object> getFiles(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof String) {
			return null;
		}
		return (List<Object>) value;
	}

	/**
	 * 获取从前台统一上传控件上传过来的文件ID列表
	 * @return
	 */
	private List<String> getFileIds(Object value) {
		List<Object> files = this.getFiles(value);//获取从前台传送过来的文件列表信息 
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

	private void fileOperate(String folderID, String fieldName, List<String> fileIDListFromPage, boolean isNew) {

		List<String> newFileIDList = new ArrayList<String>(); //该列表中的fileID都是要push到数据库里面的

		if (isNew) {//新增
			newFileIDList.addAll(fileIDListFromPage);
		} else {//更新
			List<MongoFileEntity> dbFiles = mongoFileService.getFilesFromFolder(folderID, fieldName);//取出存放在数据库里面的文件
			if (dbFiles == null) {
				newFileIDList.addAll(fileIDListFromPage);
			} else {
				Iterator<MongoFileEntity> it1 = dbFiles.iterator();
				while (it1.hasNext()) {
					MongoFileEntity dbFile = it1.next();
					String dbFileID = dbFile.getId();
					boolean isExist = false;
					Iterator<String> it2 = fileIDListFromPage.iterator();
					while (it2.hasNext()) {
						String fileId = it2.next();
						if (dbFileID.equals(fileId)) {//如果该文件在数据库中已存在,则不用进行再处理
							it2.remove();
							isExist = true;
							break;
						}
					}

					if (!isExist) {//如果从页面传进来的文件没有该文件，则表示该文件是要被删除的
						mongoFileService.popFileFromFolder(folderID, dbFileID);
					}
				}
				newFileIDList.addAll(fileIDListFromPage);
			}
		}

		//将要保存到数据库的文件push到数据 库中
		if (newFileIDList.size() > 0) {
			mongoFileService.pushFilesToFolder(folderID, newFileIDList, fieldName);
		}
	}

	private void parseDateField(String fieldName) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat();
		String datePattern = dUtils.getDateTimePatternByFieldName(fieldName);
		sdf.applyPattern(datePattern);
		String value = (String) formDataColMap.get(fieldName);
		if (value != null && value.trim().length() > 0) {
			formDataColMap.put(fieldName, sdf.parse(value));
		} else {
			formDataColMap.put(fieldName, null);
		}

	}

	private void parseNumberField(String fieldName) {
		String value = (String) formDataColMap.get(fieldName);
		if (value == null || value.trim().length() == 0) {//如果用户没填值，从字段定义中取默认值，若没默认值则使用0
			String defaultValue = (String) dUtils.getValueOfObject4FieldProperty(fieldName, "defaultValue");
			if (defaultValue == null || defaultValue.trim().length() == 0) {
				defaultValue = "0";
			}
			this.formDataColMap.put(fieldName, defaultValue);
		}
	}

	public void doProcessValueCreateBySystem(String fieldName) {
		if (this.dUtils.isInputModeEqDate(fieldName)) {
			formDataColMap.put(fieldName, new Date());
		} else if (this.dUtils.isInputModeEqText(fieldName)) {
			//String valueObj = (String) formDataColMap.get(fieldName);
			String value = this.replaceSystemVariable(fieldName);
			formDataColMap.put(fieldName, value);
		}
	}

	private String replaceSystemVariable(String fieldName) {
		String defaultValue = (String) dUtils.getValueOfObject4FieldProperty(fieldName, "defaultValue");
		Pattern p = Pattern.compile(".*(\\{[^\\}]+\\}).*");
		Matcher matcher = p.matcher(defaultValue);
		while (matcher.find()) {
			String datePattern = null;
			for (int i = 0; i < matcher.groupCount(); i++) {
				String pattern = matcher.group(i + 1);
				EnumDySysVariable systeVar = EnumDySysVariable.key2EnumObj(pattern);

				switch (systeVar) {
				case currentCreatorDepartmentName:
					defaultValue = defaultValue.replace(pattern, SpringSecurityUtils.getCurrentUserDepartmentName());
					break;
				case currentUserDepartmentPath:
					defaultValue = defaultValue.replace(pattern, SpringSecurityUtils.getCurrentUserDepartmentPath());
					break;
				case currentCreatorDepartmentPath:
					defaultValue = defaultValue.replace(pattern, SpringSecurityUtils.getCurrentUserDepartmentPath());
					break;
				case currentCreatorId:
					defaultValue = defaultValue.replace(pattern, SpringSecurityUtils.getCurrentUserId());
					break;
				case currentCreatorName:
					defaultValue = defaultValue.replace(pattern, SpringSecurityUtils.getCurrentUserName());
					break;
				case currentUserId:
					defaultValue = defaultValue.replace(pattern, SpringSecurityUtils.getCurrentUserId());
					break;
				case currentUserName:
					defaultValue = defaultValue.replace(pattern, SpringSecurityUtils.getCurrentUserName());
					break;
				case currentUserDepartmentName:
					datePattern = systeVar.getPattern();
					break;

				/////日期//////
				case currentDateTimeMin:

					datePattern = systeVar.getPattern();
					break;
				case currentDateTimeSec:
					datePattern = systeVar.getPattern();
					break;
				case currentMonthDateCn:
					datePattern = systeVar.getPattern();
					break;
				case currentTimeMin:
					datePattern = systeVar.getPattern();
					break;
				case currentTimeSec:
					datePattern = systeVar.getPattern();
					break;

				case currentWeekCn:
					datePattern = systeVar.getPattern();
					break;
				case currentYear:
					datePattern = systeVar.getPattern();
					break;
				case currentYearCn:
					datePattern = systeVar.getPattern();
					break;
				case currentYearMonthCn:
					datePattern = systeVar.getPattern();
					break;
				case currentYearMonthDate:
					datePattern = systeVar.getPattern();
					break;
				case currentYearMonthDateCn:
					datePattern = systeVar.getPattern();
					break;
				default:
					break;
				}
				if (datePattern != null) {
					SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
					defaultValue = defaultValue.replace(systeVar.getKey(), sdf.format(new Date()));
				}

			}
			matcher = p.matcher(defaultValue);
		}
		return defaultValue;

	}

	public void doProcessValueCreateByUser(String fieldName) throws ParseException {
		if (dUtils.isInputModeEqAttach(fieldName)) {//附件字段
			this.processFileField(fieldName);//处理文件字段 
		} else if (dUtils.isInputModeEqDate(fieldName)) {//日期字段
			this.parseDateField(fieldName);
		} else if (dUtils.isInputModeEqText(fieldName)) {//文本字段

		} else if (dUtils.isInputModeEqNumber(fieldName)) {
			this.parseNumberField(fieldName);
		}

	}

	/** 
	 * 数据验证
	 */
	public void validate(String fieldName) {

		if (dUtils.isInputModeEqNumber(fieldName)) {
			String value = (String) formDataColMap.get(fieldName);
			if (!StringUtils.isNumeric(value)) {
				throw new NumberFormatException(fieldName + "=" + value);
			}
		}

	}
}
