package com.wellsoft.pt.dyform.support;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

/**
 * 
 * 该类要实时与前台的dyform_constantw文件保存同步
 *  
 * @author Administrator
 * @date 2014-6-23
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-6-23.1	hunt		2014-6-23		Create
 * </pre>
 *
 */
public class DyFormConfig {
	public static final String CHARSET = "UTF-8";

	public static final String digestAlgorithm = "MD5";

	// 表单数据签名，JCR结点名后缀
	public static final String DYTABLE_SIGNATURE_NODE_NAME_SUFFIX = "_signature";

	public static final Integer DYTABLE_VERIFICATION_NOTNULL = 1;

	public static final Integer DYTABLE_VERIFICATION_URL = 2;

	public static final Integer DYTABLE_VERIFICATION_EMAIL = 3;

	public static final Integer DYTABLE_VERIFICATION_ISCARD = 4;

	public static final Integer DYTABLE_VERIFICATION_UNIQUE = 5;

	public static final String INPUTMODE_Text = "1";//普通表单输入
	public static final String INPUTMODE_CKEDIT = "2";//富文本编辑
	public static final String INPUTMODE_ACCESSORY = "3";//附件
	public static final String INPUTMODE_ACCESSORY1 = "4";//图标显示
	public static final String INPUTMODE_ACCESSORY2 = "5";//图标显示（含正文）
	public static final String INPUTMODE_ACCESSORY3 = "6";//列表显示（不含正文）
	public static final String INPUTMODE_ACCESSORYIMG = "33";//图片上传控件

	public static final String INPUTMODE_SerialNumber = "7";//可编辑流水号

	public static final String INPUTMODE_ORGSELECT = "8";//组织选择框
	public static final String INPUTMODE_ORGSELECTSTAFF = "9";//组织选择框（人员）
	public static final String INPUTMODE_ORGSELECTDEPARTMENT = "10";//组织选择框（部门）
	public static final String INPUTMODE_ORGSELECTSTADEP = "11";//组织选择框（部门+人员）
	public static final String INPUTMODE_ORGSELECTADDRESS = "28";//组织选择框 (单位通讯录)

	public static final String INPUTMODE_TIMEEMPLOY = "12";//资源选择
	public static final String INPUTMODE_TIMEEMPLOYFORMEET = "13";//资源选择（会议）
	public static final String INPUTMODE_TIMEEMPLOYFORCAR = "14";//资源选择（车辆）
	public static final String INPUTMODE_TIMEEMPLOYFORDRIVER = "15";//资源选择（司机）
	public static final String INPUTMODE_TREESELECT = "16";//树形下拉框
	public static final String INPUTMODE_RADIO = "17";//radio表单元素
	public static final String INPUTMODE_CHECKBOX = "18";//checkbox表单元素
	public static final String INPUTMODE_SELECTMUTILFASE = "19";//下拉单选框
	public static final String INPUTMODE_TEXTAREA = "20";//文本域输入
	public static final String INPUTMODE_FILEUPLOAD = "21";//附件上传
	public static final String INPUTMODE_TEXTBODY = "22";//正文
	public static final String INPUTMODE_INT = "23";
	public static final String INPUTMODE_LONG = "24";
	public static final String INPUTMODE_FLOAT = "25";
	public static final String INPUTMODE_DIALOG = "26";//弹出框
	public static final String INPUTMODE_XML = "27";//XML
	public static final String INPUTMODE_UNEDITSERIALUMBER = "29";//不可编辑流水号
	public static final String INPUTMODE_DATE = "30";//日期
	public static final String INPUTMODE_NUMBER = "31";//数字输入框
	public static final String INPUTMODE_EMBEDDED = "40";//url嵌入页面
	public static final String INPUTMODE_JOB = "41";//职位控件

	public static final String INPUTTYPE_TEXT = "1";//文本输入框，允许输入字符串
	public static final String INPUTTYPE_DATE = "2";//日期选择，只允许日期控件丢或者手动输入合法的日期
	public static final String INPUTTYPE_DATETIMEHOUR = "6";//日期到时
	public static final String INPUTTYPE_DATETIMEMIN = "7";//日期到分
	public static final String INPUTTYPE_DATETIMESEC = "8";//日期到秒
	public static final String INPUTTYPE_TIMEHOUR = "9";//时间到时
	public static final String INPUTTYPE_TIMEMIN = "10";//时间到分
	public static final String INPUTTYPE_TIMESEC = "11";//时间到秒
	public static final String INPUTTYPE_INT = "13";//文本框整数输入
	public static final String INPUTTYPE_LONG = "14";//长整型输入
	public static final String INPUTTYPE_FLOAT = "15";//浮点数输入
	public static final String INPUTTYPE_CLOB = "16";//大字段

	public static final String BODY_FILEID_PREFIX = "BODY_FILEID_";

	public static final String ORDER_SUBFORM_FIELDNAME_PREFIX = "_ORDER";

	public static final String assistedpofix4realValue = "_real";
	public static final String assistedpofix4DisplayValue = "_display";
	public static final String DYFORM_RELATIONTBL_POSTFIX = "_RL";
	public static final String VIEWNAME_OF_SUBFORM = "subformdataview";

	public static class InputModeUtils {
		public static final Boolean isInputModeEqAttach(String inputMode) {
			if (DyFormConfig.INPUTMODE_ACCESSORY1.equals(inputMode)
					|| DyFormConfig.INPUTMODE_ACCESSORY2.equals(inputMode)
					|| DyFormConfig.INPUTMODE_ACCESSORY3.equals(inputMode)
					|| DyFormConfig.INPUTMODE_ACCESSORYIMG.equals(inputMode)
					|| DyFormConfig.INPUTMODE_ACCESSORY.equals(inputMode)) {
				return true;
			} else {
				return false;
			}
		}

		public static final Boolean isInputModeEqDate(String inputMode) {
			if (DyFormConfig.INPUTMODE_DATE.equals(inputMode)) {
				return true;
			}
			return false;
		}

		public static boolean isInputModeEqNumber(String inputMode) {
			if (DyFormConfig.INPUTMODE_NUMBER.equals(inputMode)) {
				return true;
			}
			return false;
		}

		public static boolean isInputModeEqText(String inputMode) {
			if (DyFormConfig.INPUTMODE_Text.equals(inputMode)) {
				return true;
			}
			return false;
		}

	}

	/**
	 * Description =  字段值的产生方式 
	 *  
	 * @author Administrator
	 * @date 2014-6-8
	 * @version 1.0
	 * 
	 * <pre>
	 * 修改记录 = 
	 * 修改后版本	修改人		修改日期			修改内容
	 * 2014-6-8.1	hunt		2014-6-8		Create
	 * </pre>
	 *
	 */
	public interface ValueCreateMethod {

		String userImport = "1";//用户输入
		String jsEquation = "2";//JS公式
		String creatOperation = "3";//创建时计算
		String showOperation = "4";//显示时计算
		String twoDimensionCode = "5";//二维码
		String shapeCod = "6";//条形码
		String relationDoc = "7";//关联文档	
	}

	/**
	 * Description =  对应的数据库字段类型
	 *  
	 * @author hunt
	 * @date 2014-6-8
	 * @version 1.0
	 * 
	 * <pre>
	 * 修改记录 = 
	 * 修改后版本	修改人		修改日期			修改内容
	 * 2014-6-8.1	hunt		2014-6-8		Create
	 * </pre>
	 *
	 */
	public interface DbDataType {
		String _string = "1";//字符串
		String _date = "2";//日期
		String _int = "13";//整数
		String _long = "14";//长整型
		String _float = "15";//浮点数 
		String _clob = "16";//大字段
		String _double = "12";//双精度浮点数
	}

	public static class DbDataTypeUtils {

		/**
		 * 字段的对应的数据库类型是不是长整型
		 * @param fieldName
		 * @return
		 */
		public static boolean isDbDataTypeEqLong(String dbDataType) {

			if (DyFormConfig.DbDataType._long.equals(dbDataType)) {
				return true;
			}

			return false;
		}

		/**
		 * 判断字段数据库类型是否为int类型
		 * @param fieldName
		 * @return
		 */
		public static boolean isDbDataTypeEqInt(String dbDataType) {

			if (DyFormConfig.DbDataType._int.equals(dbDataType)) {
				return true;
			}
			return false;
		}

		/**
		 * 判断字段数据库类型是否为float类型
		 * @param fieldName
		 * @return
		 */
		public static boolean isDbDataTypeEqFloat(String dbDataType) {

			if (DyFormConfig.DbDataType._float.equals(dbDataType)) {
				return true;
			}
			return false;
		}

		public static boolean isDbDataTypeEqDouble(String dbDataType) {
			if (DyFormConfig.DbDataType._double.equals(dbDataType)) {
				return true;
			}
			return false;
		}

		/**
		 * 判断字段数据库类型是否为date类型
		 * @param fieldName
		 * @return
		 */
		public static boolean isDbDataTypeEqDate(String dbDataType) {
			if (DyFormConfig.DbDataType._date.equals(dbDataType)) {
				return true;
			}
			return false;
		}

		/**
		 * 判断字段数据库类型是否为string类型
		 * @param fieldName
		 * @return
		 */
		public static boolean isDbDataTypeEqString(String dbDataType) {
			if (DyFormConfig.DbDataType._string.equals(dbDataType)) {
				return true;
			}
			return false;
		}

		public static boolean isDbDataTypeAsNumber(String dbDataType) {
			if (isDbDataTypeEqFloat(dbDataType) || isDbDataTypeEqInt(dbDataType) || isDbDataTypeEqLong(dbDataType)
					|| isDbDataTypeEqDouble(dbDataType)) {
				return true;
			} else {
				return false;
			}
		}

		public static boolean isDbDataTypeEqClob(String dbDataType) {
			if (DyFormConfig.DbDataType._clob.equals(dbDataType)) {
				return true;
			}
			return false;
		}

		public static boolean isDbDataTypeAsString(String dbDataType) {
			if (isDbDataTypeEqString(dbDataType) || isDbDataTypeEqClob(dbDataType)) {
				return true;
			}
			return false;
		}

		public static String getDataTypeNameByNum(String dataTypeNum) {
			if (DbDataType._int.equals(dataTypeNum)) {
				return "INTEGER";
			} else if (DbDataType._date.equals(dataTypeNum)) {
				return "DATE";
			} else if (DbDataType._clob.equals(dataTypeNum)) {
				return "CLOB";
			} else if (DbDataType._float.equals(dataTypeNum)) {
				return "FLOAT";
			} else if (DbDataType._double.equals(dataTypeNum)) {
				return "DOUBLE";
			} else if (DbDataType._long.equals(dataTypeNum)) {
				return "LONG";
			} else if (DbDataType._string.equals(dataTypeNum)) {
				return "STRING";
			} else {
				return "STRING";
			}
		}

	}

	/**
	 * 用于标识表单中各字段的类型
	 */
	public interface DyFieldSysType {
		int system = 0;//系统字段
		int custom = 2;//用户自定义字段
		int admin = 1;//管理员定义字段
		//int parentForm = 3;//该字段用于保存对应的记录的主表的数据uuid,及排序
		//int assist = 4;//辅助性字段
	}

	public interface DyDateFomat {
		String yearMonthDate = "1";//当前日期(2000-01-01)
		String yearMonthDateCn = "2";//当前日期(2000年1月1日)
		String yearCn = "3";//当前日期(2000年)
		String yearMonthCn = "4";//当前日期(2000年1月)
		String monthDateCn = "5";//当前日期(1月1日)
		//String weekCn = "6";//当前日期(星期一)
		String year = "7";//当前年份(2000)
		String timeHour = "8";//当前时间(12)
		String timeMin = "9";//当前时间(12 = 00)
		String timeSec = "10";//当前时间(12 = 00 = 00)
		String dateTimeHour = "11";//日期到时 当前日期时间(2000-01-01 12)
		String dateTimeMin = "12";//日期到分 当前日期时间(2000-01-01 12 = 00)
		String dateTimeSec = "13";//日期到秒 当前日期时间(2000-01-01 12 = 00 = 00)
	}

	public enum EnumDySysVariable {
		currentYearMonthDate("{CURRENTYEARMONTHDATE}", "yyyy-MM-dd"), //当前日期(2000-01-01) 
		currentYearMonthDateCn("{CURRENTYEARMONTHDATECN}", "yyyy年MM月dd日"), //当前日期(2000年1月1日)
		currentYearCn("{CURRENTYEARCN}", "yyyy年"), //当前日期(2000年)
		currentYearMonthCn("{CURRENTYEARMONTHCN}", "yyyy年MM月"), //当前日期(2000年1月)
		currentMonthDateCn("{CURRENTMONTHDATECN}", "MM月dd日"), //当前日期(1月1日)
		currentWeekCn("{CURRENTWEEKCN}", "EEE"), //当前日期(星期一)
		currentYear("{CURRENTYEAR}", "yyyy"), //当前年份(2000)
		currentTimeMin("{CURRENTTIMEMIN}", "HH:mm"), //当前时间(12 : 00)
		currentTimeSec("{CURRENTTIMESEC}", "HH:mm:ss"), //当前时间(12 : 00 : 00)
		currentDateTimeMin("{CURRENTDATETIMEMIN}", "yyyy-MM-dd HH:mm"), //日期到分 当前日期时间(2000-01-01 12 : 00)
		currentDateTimeSec("{CURRENTDATETIMESEC}", "yyyy-MM-dd HH:mm:ss"), //日期到秒 当前日期时间(2000-01-01 12 : 00 : 00)

		currentUserId("{CURRENTUSERID}", "") {
			@Override
			public String getValue() {

				return SpringSecurityUtils.getCurrentUserId();
			}
		}, //{当前用户ID}
		currentUserName("{CURRENTUSERNAME}", "") {
			@Override
			public String getValue() {

				return SpringSecurityUtils.getCurrentUserName();
			}
		}, //{当前用户姓名}
		currentUserDepartmentPath("{CURRENTUSERDEPARTMENTPATH}", "") {
			@Override
			public String getValue() {

				return SpringSecurityUtils.getCurrentUserDepartmentPath();
			}
		}, //{当前用户部门(长名称)}
		currentUserDepartmentName("{CURRENTUSERDEPARTMENTNAME}", "") {
			@Override
			public String getValue() {
				return SpringSecurityUtils.getCurrentUserDepartmentName();
			}
		}, //{当前用户部门(短名称)}
		currentUserMainJobName("{CURRENTUSERMAINJOBNAME}", "") {
			@Override
			public String getValue() {
				return DyFormApiFacade.getCurrentUserMainJobName();
			}
		}, //{当前用户主职位}

		currentCreatorId("{CURRENTCREATORID}", "") {
			@Override
			public String getValue() {

				return SpringSecurityUtils.getCurrentUserId();
			}
		}, //{创建人ID}
		currentCreatorName("{CURRENTCREATORNAME}", "") {
			@Override
			public String getValue() {

				return SpringSecurityUtils.getCurrentUserName();
			}
		}, //{创建人姓名}
		currentCreatorDepartmentPath("{CURRENTCREATORDEPARTMENTPATH}", "") {
			@Override
			public String getValue() {
				return SpringSecurityUtils.getCurrentUserDepartmentPath();
			}
		}, //{创建人部门(长名称)}
		currentCreatorDepartmentId("{CURRENTCREATORDEPARTMENTID}", "") {
			@Override
			public String getValue() {
				return SpringSecurityUtils.getCurrentUserDepartmentId();
			}
		}, //{创建人部门ID}
		currentCreatorDepartmentName("{CURRENTCREATORDEPARTMENTNAME}", "") {
			@Override
			public String getValue() {
				return SpringSecurityUtils.getCurrentUserDepartmentName();
			}
		}, //{创建人部门(短名称)}
		currentCreatorMainJobName("{CURRENTCREATORMAINJOBNAME}", "") {
			@Override
			public String getValue() {
				return DyFormApiFacade.getCurrentUserMainJobName();
			}
		};//{创建人主职位}

		private String key;
		private String pattern;

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getPattern() {
			return pattern;
		}

		public void setPattern(String pattern) {
			this.pattern = pattern;
		}

		private EnumDySysVariable(String key, String pattern) {
			this.key = key;
			this.pattern = pattern;
		}

		public static EnumDySysVariable key2EnumObj(String keyValue) {
			EnumDySysVariable enumObj = null;
			for (EnumDySysVariable status : EnumDySysVariable.values()) {
				if (status.key.equals(keyValue)) {
					enumObj = status;
				}
			}
			return enumObj;
		}

		public String getValue() {
			sdf.applyPattern(this.getPattern());
			return sdf.format(new Date());
		}

		SimpleDateFormat sdf = new SimpleDateFormat();
	}

	public interface DyShowType {
		String edit = "1";//可编辑
		String showAsLabel = "2";//直接以文本的形式显示
		String readonly = "3";//有输入框但只读
		String disabled = "4";//有输入框但被disabled
		String hide = "5";//隐藏整行
	}

	public interface DyDataSourceType {
		String dataConstant = "1";
		String dataDictionary = "2";
	}

	/**
	 * Description: 表单定义中,各属性名称
	 *  
	 * @author hunt
	 * @date 2014-7-3
	 * @version 1.0
	 * 
	 * <pre>
	 * 修改记录:
	 * 修改后版本	修改人		修改日期			修改内容
	 * 2014-7-3.1	hunt		2014-7-3		Create
	 * </pre>
	 *
	 */
	public enum EnumFormPropertyName {
		name, //表名
		uuid, //定义uuid
		displayName, //表单显示名称
		applyTo, //应用于
		fields, //
		subforms, //从表的配置
		relationTbl, //与数据表对应的数据关系表
		outerId, remark, //描述
		formDisplay, //	表单显示形式 ： 两种 一种是可编辑展示、 一种是直接展示文本,这个字段已没用
		moduleId, //模块ID
		moduleName, //模块名
		printTemplateId, //	打印模板的ID
		printTemplateName, //打印模板的名称
		displayFormModelName, //	显示单据的名称,这个字段没有用
		displayFormModelId, //显示单据对应的表单uuid
		code, //表单编号
		version, //	版本 ,形式：1.0
		enableSignature, //	是否启用表单签名
		html, //模板
		blocks, //区域 
	}

	/**
	 * 字段定义中各属性名 
	 * @author Administrator
	 * @date 2014-6-26
	 * @version 1.0
	 * 
	 * <pre>
	 * 修改记录:
	 * 修改后版本	修改人		修改日期			修改内容
	 * 2014-6-26.1	hunt		2014-6-26		Create
	 * </pre>
	 *
	 */
	public enum EnumFieldPropertyName {
		name(), //字段名
		oldName, //旧的字段名
		realDisplay, //真实值与显示值的字段名，一个JSON对象,real为真实值的字段名，display为显示值的字段名
		showType(), //编辑模式
		applyTo(), //
		displayName(), //
		dbDataType(), //
		indexed(), //
		showed(), //
		sorted(), //
		sysType(), //
		length(), //
		defaultValue(), //
		valueCreateMethod(), //
		onlyreadUrl(), //
		inputMode(), //

		textAlign(), //
		ctlWidth(), //
		ctlHight(), //
		fontSize(), //
		fontColor(), // 
		fieldCheckRules(), //
		contentFormat(), optionDataSource(), dictCode();
	}

	public enum EnumSubformPropertyName {
		formUuid, outerId, name, displayName,

		isGroupShowTitle, //是否要分组展示
		groupShowTitle, //分组展示标题
		isGroupColumnShow, //分组字段是否展示 20140701 add  
		subformApplyTableId, subrRelationDataDefiantion, tableOpen, //从表是展示还是折叠（收缩） 

		editMode, // 编辑模式1.行内编辑 2.弹出窗口编辑
		hideButtons, //1：不隐藏;2:隐藏			
		fields; //参照表单从表字段定义 
	}

	public enum EnumFieldPropertyName_fieldCheckRules {
		value(), label();
	}

	public enum EnumDyCheckRule {
		//约束条件
		notNull("1", "非空"), //非空
		unique("5", "唯一"), //唯一校验

		//文本样式，校验规则
		common("10", ""), //
		url("11", "url"), //
		email("12", "email"), //
		idCard("13", "身份证"), //身份证
		tel("14", "固定电话"), //固定电话
		mobilePhone("15", "手机");//手机
		private String ruleKey = "";
		private String ruleLabel = "";

		private EnumDyCheckRule(String ruleKey, String ruleLabel) {
			this.ruleKey = ruleKey;
			this.ruleLabel = ruleLabel;
		}

		public String getRuleKey() {
			return ruleKey;
		}

		public String getRuleLabel() {
			return ruleLabel;
		}

	}

	public enum EnumSignature {

		enable("1", "不启用"), disable("2", "启用");

		private String value = "";
		private String remark = "";

		private EnumSignature(String value, String remark) {
			this.value = value;
			this.remark = remark;
		}

		public static EnumSignature value2EnumObj(String keyValue) {
			EnumSignature enumObj = null;
			for (EnumSignature status : EnumSignature.values()) {
				if (status.value.equalsIgnoreCase(keyValue)) {
					enumObj = status;
				}
			}
			return enumObj;
		}

		public String getValue() {
			return value;
		}

		public String getRemark() {
			return remark;
		}

	}

	public enum EnumSubformFieldPropertyName {
		name, displayName, order, //在展示从表时各字段的排列顺序
		sortable, //在展示从表时是否允许列排序
		srcFieldName, //来源字段
		hidden, //从表字段是否展示
		width, //宽度
		editable, //可否编辑
		controlable, //直接显示为控件
		formula;//运算公式
	}

	public enum EnumHideSubFormOperateBtn {
		show("1", "不隐藏"), hide("2", "隐藏");

		private String value = "";
		private String remark = "";

		private EnumHideSubFormOperateBtn(String value, String remark) {
			this.value = value;
			this.remark = remark;
		}

		public static EnumHideSubFormOperateBtn value2EnumObj(String keyValue) {
			EnumHideSubFormOperateBtn enumObj = null;
			for (EnumHideSubFormOperateBtn status : EnumHideSubFormOperateBtn.values()) {
				if (status.value.equalsIgnoreCase(keyValue)) {
					enumObj = status;
				}
			}
			return enumObj;
		}

		public String getValue() {
			return value;
		}

		public String getRemark() {
			return remark;
		}

	}

	public enum EnumSubFormFieldShow {
		show("1", "不隐藏"), hide("2", "隐藏");

		private String value = "";
		private String remark = "";

		private EnumSubFormFieldShow(String value, String remark) {
			this.value = value;
			this.remark = remark;
		}

		public static EnumSubFormFieldShow value2EnumObj(String keyValue) {
			EnumSubFormFieldShow enumObj = null;
			for (EnumSubFormFieldShow status : EnumSubFormFieldShow.values()) {
				if (status.value.equalsIgnoreCase(keyValue)) {
					enumObj = status;
				}
			}
			return enumObj;
		}

		public String getValue() {
			return value;
		}

		public String getRemark() {
			return remark;
		}

	}

}
