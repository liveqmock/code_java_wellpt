package com.wellsoft.pt.dytable.support;

public class DytableConfig {
	public static final String CHARSET = "UTF-8";
	public static final String DYTABLE_JCR_MODLE_NAME = "DY_TABLE_FORM";

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
	 * 判断某输入类型是不是附件类型 
	 * @param inputType
	 * @param fieldName
	 * @return
	 */
	public static boolean isAttach(String inputType) {
		if (INPUTMODE_ACCESSORY1.equals(inputType) || INPUTMODE_ACCESSORY2.equals(inputType)
				|| INPUTMODE_ACCESSORY3.equals(inputType) || INPUTMODE_TEXTBODY.equals(inputType)) {
			return true;
		} else {
			return false;
		}

	}
}
