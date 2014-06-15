package com.wellsoft.pt.dyform.support;

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
	public static final String INPUTMODE_SerialNumber = "7";//可编辑流水号
	public static final String INPUTMODE_ORCHOOSE = "8";//组织选择框
	public static final String INPUTMODE_ORCHOOSE1 = "9";//组织选择框（人员）
	public static final String INPUTMODE_ORCHOOSE2 = "10";//组织选择框（部门）
	public static final String INPUTMODE_ORCHOOSE3 = "11";//组织选择框（部门+人员）
	public static final String INPUTMODE_ORCHOOSE4 = "28";//组织选择框 (单位通讯录)
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
	}

	/**
	 * 用于标识表单中各字段的类型
	 */
	public interface DyFieldSysType {
		int system = 0;//系统字段
		int custom = 2;//用户自定义字段
		int admin = 1;//管理员定义字段
		int parentForm = 3;//该字段用于保存对应的记录的主表的数据uuid
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

		currentUserId("{CURRENTUSERID}", ""), //{当前用户ID}
		currentUserName("{CURRENTUSERNAME}", ""), //{当前用户姓名}
		currentUserDepartmentPath("{CURRENTUSERDEPARTMENTPATH}", ""), //{当前用户部门(长名称)}
		currentUserDepartmentName("{CURRENTUSERDEPARTMENTNAME}", ""), //{当前用户部门(短名称)}

		currentCreatorId("{CURRENTCREATORID}", ""), //{创建人ID}
		currentCreatorName("{CURRENTCREATORNAME}", ""), //{创建人姓名}
		currentCreatorDepartmentPath("{CURRENTCREATORDEPARTMENTPATH}", ""), //{创建人部门(长名称)}
		currentCreatorDepartmentName("{CURRENTCREATORDEPARTMENTNAME}", "");//{创建人部门(短名称)}

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
	}

}
