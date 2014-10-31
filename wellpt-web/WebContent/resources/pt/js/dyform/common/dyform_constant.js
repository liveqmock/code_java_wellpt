/**
 * 该文件中的常量与后台的DyFormConfig要实时保持同步
 */

//操作结果
var dyResult = {
	success:'1',//成功
	error:'0'//失败
};
//字段类型
var dyFormInputType = {
		_text:'1',//文本输入框，允许输入字符串
		_date:'2',//日期选择，只允许日期控件丢或者手动输入合法的日期
		_double:'12',//双精度浮点数
		_int:'13',//文本框整数输入
		_long:'14',//长整型输入
		_float:'15',//浮点数输入
		_clob:'16'//大字段
};
//字段类型
var dyFormInputTypeObj = {
		_text:{"code":dyFormInputType._text, "name":"文本"},
		_date:{"code":dyFormInputType._date, "name":"日期"},
		_double:{"code":dyFormInputType._double, "name":"双精度浮点数"},
		_int:{"code":dyFormInputType._int, "name":"整型"},
		_long:{"code":dyFormInputType._long, "name":"长整型"},
		_float:{"code":dyFormInputType._float, "name":"浮点型"},
		_clob:{"code":dyFormInputType._clob, "name":"大字段"}
};
//字段值
var dyFormInputValue = {
		userImport:'1',//用户输入
		jsEquation:'2',//JS公式
		creatOperation:'3',//由后台创建时计算
		showOperation:'4',//由前台显示时计算
		twoDimensionCode:'5',//二维码
		shapeCod:'6',//条形码
		relationDoc:'7'//关联文档
};


var dyFormOrgSelectType={
	orgSelect: '8', //组织选择框
	orgSelectStaff: '9', //组织选择框（人员）
	orgSelectDepartment: '10', //组织选择框（部门）
	orgSelectStaDep: '11', //组织选择框（部门+人员）
	orgSelectAddress: '28',//组织选择框 (单位通讯录)
	orgSelectJob:'51',//组织选择框 (部门+职位)
	orgSelectPublicGroup:'52',//组织选择框 (群组)
	orgSelectMyDept:'53',//组织选择框(我的部门)
	orgSelectMyParentDept:'54',//组织选择框(上级部门)
	orgSelectMyUnit:'55'//组织选择框(部门+职位+人员)
};

//输入样式
var dyFormInputMode = {
		text:'1',//普通表单输入
		ckedit:'2',//富文本编辑
		//accessory:'3',//附件
		accessory1:'4',//图标显示
		//accessory2:'5',//图标显示（含正文）
		accessory3:'6',//列表显示（不含正文）
		accessoryImg:'33',//图片附件	
		serialNumber:'7',//可编辑流水号
		unEditSerialNumber:'29', //不可编辑流水号
		
		orgSelect:dyFormOrgSelectType.orgSelect,//组织选择控件  valuemap 
		orgSelectStaff: dyFormOrgSelectType.orgSelectStaff, //组织选择框（人员）
		orgSelectDepartment: dyFormOrgSelectType.orgSelectDepartment, //组织选择框（部门）
		orgSelectStaDep: dyFormOrgSelectType.orgSelectStaDep, //组织选择框（部门+人员）
		orgSelectAddress: dyFormOrgSelectType.orgSelectAddress,//组织选择框 (单位通讯录)
		
		
		timeEmploy:"12",//资源选择
		timeEmployForMeet:"13",//资源选择（会议）
		timeEmployForCar:"14",//资源选择（车辆）
		timeEmployForDriver:"15",//资源选择（司机）
		treeSelect:"16",//树形下拉框  valuemap
		radio:'17',//radio表单元素 valuemap 
		checkbox:'18',//checkbox表单元素 valuemap
		selectMutilFase:'19',//下拉单选框 valuemap
		textArea:'20',//文本域输入
		//fileUpload:'21',//附件上传
		textBody:'22',//正文 
		dialog:'26',//弹出框
		xml:'27',//XML
		
		date:'30',//日期
		number:'31',//数字控件
		viewdisplay:'32',//视图展示
		embedded:'40',//url嵌入页面
		job:'41'//职位控件
		
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
		_string:dyFormInputType._text,//字符串
		_date:dyFormInputType._date,//日期
		_int:dyFormInputType._int,//整数
		_long:dyFormInputType._long,//长整型
		_float:dyFormInputType._float,//浮点数 
		_clob:dyFormInputType._clob//大字段
};
//从表编辑类型
var dySubFormEdittype = {
	rowEdit:'1',//行内编辑
	newWin:'2'//弹出窗口编辑
};

//从表展示类型
var dySubFormShowType = {
		jqgridShow:'1',//jqgrid展示
		tableShow:'2'//普通列表展示
};

//从表是否隐藏操作按钮
var dySubFormHideButtons = {
		show: '1', //展示
		hide: '2' //隐藏
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
		notEdit:"0",//不可编辑	
		edit:"1"//可编辑
};

//在从表字段可编辑的前提一下,将在光标离开时，还一样显示为控件而非标签
var dySubFormFieldCtl = {
		label:"0",//不可编辑	
		control:"1"//可编辑
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
		mobilePhone:'15',//手机
		
		//数字控件校验
		num_int:'n13',//整数
		num_long:'n14',//长整数
		num_float:'n15',//浮点数
		num_double:'n12'//双精度浮点数
};

//表类型
var dyTableType = {
		mainTable:'1',//主表
		subtable:'2'//从表
};

//供选项来源
var dyDataSourceType = {
		dataConstant:'1',//常量
		dataDictionary:'2',//字典
		dataView:'3',//视图
		dataSource:'4'//数据源
};

//编辑模式
var dyshowType={
		edit:'1',//可编辑
		showAsLabel:'2',//直接以文本的形式显示
		readonly:'3',//有输入框但只读
        disabled:'4',//有输入框但被disabled
        hide:'5'//隐藏
};


//表单数据状态
var dyStatusType = {
	NORMAL:"",//正常
	DELETED:""//已被删除
	
};

/**
 * 是否启用签名
 */
var signature = {
	enable:"2",//启用
	disable:"1"//不启用
	
};

/**
 * 用于标识表单中各字段的类型
 */
var dyFieldSysType = {
		system:0,//系统字段
		custom:2,//用户自定义字段
		admin:1,//管理员定义字段
		//parentForm:3//该字段用于保存对应的记录的主表的数据uuid
		assist:4//辅助性字段
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
		dateTimeSec:'13'//日期到秒 当前日期时间(2000-01-01 12:00:00)
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
		currentUserMainJobName:"{CURRENTUSERMAINJOBNAME}",//{当前用户主职位}
		currentCreatorId:"{CURRENTCREATORID}",//{创建人ID}
		currentCreatorName:"{CURRENTCREATORNAME}",//{创建人姓名}
		currentCreatorDepartmentId:"{CURRENTCREATORDEPARTMENTID}",//{创建人部门ID}
		currentCreatorDepartmentPath:"{CURRENTCREATORDEPARTMENTPATH}",//{创建人部门(长名称)}
		currentCreatorDepartmentName:"{CURRENTCREATORDEPARTMENTNAME}",//{创建人部门(短名称)}
		currentCreatorMainJobName:"{CURRENTCREATORMAINJOBNAME}"//{创建人主职位}
};

/**
 * 关联控件的展示方式
 */
var relationShowType = {
		dialog:"1",//单击文本输入框弹出对话框，供选择的数据展示在对话框里面
		dropdiv:"2"//在文本框中输入关键字，将搜索到的供选择的数据展示在下拉的div中
};
		
		
//控件位置
var dyControlPos={
		mainForm:'0',//主表
		subForm:'1'//子表
};

var groupUsed = {
		uniqueGroup:"uniqueGroup"//拥有这个属性的元素间的值互斥，则值不得相等
};

/*时间资源控件的类型*/
var timeResouceType={
		MEET_RESOURCE:"MEET_RESOURCE",
		CAR_RESOURCE:"CAR_RESOURCE",
		DRIVER_RESOURCE:"DRIVER_RESOURCE"
};
 
/**
 * 数据关联方式
 * 数据关联控件有两种，一种是通过弹出框，一种是通过搜索框,通过这两种控件将关联数据加载进控件中
 */
var dyRelativeMethod = {
	DIALOG:"1",//弹出框
	SEARCH:"2"//搜索框
};

/**
*单选和多选
 */
var dySelectType = {
	single:"1",//单选
	multiple:"2"//多选
};

var layOutInput = {
		tab:"1",
		block:"2"
};

