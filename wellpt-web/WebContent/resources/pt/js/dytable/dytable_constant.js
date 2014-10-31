//操作结果
var dyResult = {
	success:'1',//成功
	error:'0'//失败
};
//字段类型
var dyFormInputType = {
		text:'1',//文本输入框，允许输入字符串
		date:'2',//日期选择，只允许日期控件丢或者手动输入合法的日期
		dateTimeHour:'6',//日期到时
		dateTimeMin:'7',//日期到分
		dateTimeSec:'8',//日期到秒
		timeHour:'9',//时间到时
		timeMin:'10',//时间到分
		timeSec:'11',//时间到秒
		int:'13',//文本框整数输入
		long:'14',//长整型输入
		float:'15',//浮点数输入
		clob:'16',//大字段
};
//字段值
var dyFormInputValue = {
		userImport:'1',//用户输入
		jsEquation:'2',//JS公式
		creatOperation:'3',//创建时计算
		showOperation:'4',//显示时计算
		twoDimensionCode:'5',//二维码
		shapeCod:'6',//条形码
		relationDoc:'7',//关联文档
};
//输入样式
var dyFormInputMode = {
		text:'1',//普通表单输入
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
		unEditSerialNumber:'29' //不可编辑流水号
};
//元素展示
var elementShow = {
		show:'1', //展示
		hidden:'2' //隐藏
};
//字段展示
var dyFormDataShow = {
		directShow:'1', //编辑展示
		indirect:'2' //显示展示
};

//后台数据类型
var dyFormDataType = {
		string:'1',//字符串
		date:'2',//日期
		int:'3',//整数
		long:'4',//长整型
		float:'5',//浮点数
		dateTimeHour:'6',//日期到时
		dateTimeMin:'7',//日期到分
		dateTimeSec:'8',//日期到秒
		timeHour:'9',//时间到时
		timeMin:'10',//时间到分
		timeSec:'11'//时间到秒
};
//从表编辑类型
var dySubFormEdittype = {
	rowEdit:'1',//行内编辑
	newWin:'2'//弹出窗口编辑
};

//从表展示类型
var dySubFormShowType = {
		jqgridShow:'1',//jqgrid展示
		tableShow:'2',//普通列表展示
};

//从表是否隐藏操作按钮
var dySubFormHideButtons = {
		isHide: '1', //隐藏
		isNotHide: '2' //不隐藏
};

//从表是否隐藏操作按钮
var dySubFormTableOpen = {
		open: '1', //展开
		notOpen: '2' //折叠
};

//从表数据分组展示
var dySubFormGroupShow = {
		show: '1', //展开
		notShow: '2' //折叠
};

//从表字段展示或隐藏
var dySubFormFieldShow = {
		show:"1",//展示
		notShow:"2"//隐藏	
}

//从表字段展示或隐藏
var dySubFormFieldEdit = {
		notEdit:"1",//不可编辑	
		edit:"2"//可编辑
}

//校验规则
var dyCheckRule = {
		notNull:'1',//非空
		url:'2',//url校验
		email:'3',//email校验
		idCard:'4',//身份证校验
		unique:'5'//唯一校验
};

//表类型
var dyTableType = {
		mainTable:'1',//主表
		subtable:'2'//从表
};

//供选项来源
var dyDataSourceType = {
		dataConstant:'1',//常量
		dataDictionary:'2'//字典
};



