//操作结果
var dyResult = {
	success:'1',//成功
	error:'0'//失败
};
//字段类型
var dyFormInputType = {
		text:'1',//文本输入框，允许输入字符串
		date:'2',//日期选择，只允许日期控件丢或者手动输入合法的日期
		int:'13',//文本框整数输入
		long:'14',//长整型输入
		float:'15',//浮点数输入
		clob:'16',//大字段
};
//字段值
var dyFormInputValue = {
		userImport:'1',//用户输入
		jsEquation:'2',//JS公式
		creatOperation:'3',//由后台创建时计算
		showOperation:'4',//由前台显示时计算
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
		orgSelect:"8",//组织选择控件 
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
		dialog:'26',//弹出框
		xml:'27',//XML
		unEditSerialNumber:'29', //不可编辑流水号
		date:'30',//日期
		number:'31',//数字控件
		viewdisplay:'32'//视图展示
};

var dyFormOrgSelectType={
	orgSelect: '8', //组织选择框
	orgSelectStaff: '9', //组织选择框（人员）
	orgSelectDepartment: '10', //组织选择框（部门）
	orgSelectStaDep: '11', //组织选择框（部门+人员）
	orgSelectAddress: '28',//组织选择框 (单位通讯录)
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
		string:dyFormInputType.text,//字符串
		date:dyFormInputType.date,//日期
		int:dyFormInputType.int,//整数
		long:dyFormInputType.long,//长整型
		float:dyFormInputType.float,//浮点数 
		clob:dyFormInputType.clob//大字段
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
		show: '1', //隐藏
		hide: '2' //不隐藏
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
};

//从表字段展示或隐藏
var dySubFormFieldEdit = {
		notEdit:"1",//不可编辑	
		edit:"2"//可编辑
};

//校验规则
var dyCheckRule = {
		//约束条件
		notNull:'1',//非空
		unique:'5',//唯一校验
			
		//文本样式，校验规则
		common:'10',//
		url:'11',
		email:'12',
		idCard:'13',
		tel :'14',//固定电话
		mobilePhone:'15'//手机
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

//编辑模式
var dyshowType={
		edit:'1',//可编辑
		showAsLabel:'2',//直接以文本的形式显示
		readonly:'3',//有输入框但只读
        disabled:'4',//有输入框但被disabled
        hide:'5'//隐藏整行
};


//表单数据状态
var dyStatusType = {
	NORMAL:"",//正常
	DELETED:""//已被删除
	
};

/**
 * 用于标识表单中各字段的类型
 */
var dyFieldSysType = {
		system:0,//系统字段
		custom:2,//用户自定义字段
		admin:1,//管理员定义字段
		parentForm:3//该字段用于保存对应的记录的主表的数据uuid
};

var dyDateFmt={
		yearMonthDate:'1',//当前日期(2000-01-01)
		yearMonthDateCn:'2',//当前日期(2000年1月1日)
	    yearCn:'3',//当前日期(2000年)
	    yearMonthCn:'4',//当前日期(2000年1月)
	    monthDateCn:'5',//当前日期(1月1日)
	    weekCn:'6',//当前日期(星期一)
	    year:'7',//当前年份(2000)
	    timeHour:'8',//当前时间(12)
	    timeMin:'9',//当前时间(12:00)
	    timeSec:'10',//当前时间(12:00:00)
		dateTimeHour:'11',//日期到时 当前日期时间(2000-01-01 12)
		dateTimeMin:'12',//日期到分 当前日期时间(2000-01-01 12:00)
		dateTimeSec:'13',//日期到秒 当前日期时间(2000-01-01 12:00:00)
	};

/**
 * 系统变量定义，用于text文本框的默认值
 */
var dySysVariable={
		currentYearMonthDate:"{CURRENTYEARMONTHDATE}",//当前日期(2000-01-01)
		currentYearMonthDateCn:"{CURRENTYEARMONTHDATECN}",//当前日期(2000年1月1日)
		currentYearCn:"{CURRENTYEARCN}",//当前日期(2000年)
		currentYearMonthCn:"{CURRENTYEARMONTHCN}",//当前日期(2000年1月)
		currentMonthDateCn:"{CURRENTMONTHDATECN}",//当前日期(1月1日)
		currentWeekCn:"{CURRENTWEEKCN}",//当前日期(星期一)
		currentYear:"{CURRENTYEAR}",//当前年份(2000)
		currentTimeMin:"{CURRENTTIMEMIN}",//当前时间(12:00)
		currentTimeSec:"{CURRENTTIMESEC}",//当前时间(12:00:00)
		currentDateTimeMin:"{CURRENTDATETIMEMIN}",//日期到分 当前日期时间(2000-01-01 12:00)
		currentDateTimeSec:"{CURRENTDATETIMESEC}",//日期到秒 当前日期时间(2000-01-01 12:00:00)
		
		currentUserId:"{CURRENTUSERID}",//{当前用户ID}
		currentUserName:"{CURRENTUSERNAME}",//{当前用户姓名}
		currentUserDepartmentPath:"{CURRENTUSERDEPARTMENTPATH}",//{当前用户部门(长名称)}
		currentUserDepartmentName:"{CURRENTUSERDEPARTMENTNAME}",//{当前用户部门(短名称)}
		
		currentCreatorId:"{CURRENTCREATORID}",//{创建人ID}
		currentCreatorName:"{CURRENTCREATORNAME}",//{创建人姓名}
		currentCreatorDepartmentPath:"{CURRENTCREATORDEPARTMENTPATH}",//{创建人部门(长名称)}
		currentCreatorDepartmentName:"{CURRENTCREATORDEPARTMENTNAME}"//{创建人部门(短名称)}
};		
		
		



