package com.wellsoft.pt.dyform.support.enums;

public enum EnumInputMode {
	/*text:'1',//普通表单输入
	ckedit:'2',//富文本编辑
	accessory:'3',//附件
	accessory1:'4',//图标显示
	accessory2:'5',//图标显示（含正文）
	accessory3:'6',//列表显示（不含正文）
	serialNumber:'7',//可编辑流水号
	orChoose: '8', //组织选择框
	orChoose1: '9', //组织选择框（人员）
	orChoose2: '10', //组织选择框（部门）
	orChoose3: '11', //组织选择框（部门+人员）
	orChoose4: '28',//组织选择框 (单位通讯录)
	timeEmploy:"12",//资源选择
	timeEmployForMeet:"13",//资源选择（会议）
	timeEmployForCar:"14",//资源选择（车辆）
	timeEmployForDriver:"15",//资源选择（司机）
	treeSelect:"16",//树形下拉框
	radio:'17',//radio表单元素
	checkbox:'18',//checkbox表单元素
	selectMutilFase:'19',//下拉单选框
	textArea:'20',//文本域输入
	fileUpload:'21',//附件上传
	textBody:'22',//正文
	int:'23',//
	long:'24',//
	float:'25',//
	dialog:'26',//弹出框
	xml:'27',//XML
	unEditSerialNumber:'29', //不可编辑流水号
	date:'30',//日期
	number:'31'//数字控件
	*/

	DELETED("DELETED", "已删除"), DEFAULT("DEFAULT", "默认值");
	private String value = "";
	private String remark;

	private EnumInputMode(String value, String remark) {
		this.value = value;
		this.remark = remark;
	}

	public String getValue() {
		return value;
	}

	public String getRemark() {
		return remark;
	}

	public EnumInputMode value2EnumObj(String value) {
		EnumInputMode enumObj = null;
		for (EnumInputMode status : EnumInputMode.values()) {
			if (status.getValue().equals(value)) {
				enumObj = status;
			}
		}

		return enumObj;
	}
}
