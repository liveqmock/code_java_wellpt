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

//样式条件类型
var dyViewCondType = {
	equal:'1',
	notEqual:'2',
	less:'3',
	lessEqual:'4',
	greater:'5',
	greaterEqual:'6',
	isIn:'7',
	isNotIn:'8'
};

//查询字段的类型
var selectFieldTypeId = {
		text:"TEXT",
		date:"DATE",
		org:"ORG",
		select:"SELECT",
		radio:"RADIO",
		checkbox:"CHECKBOX",
		dialog:"DIAlOG",
}

//精确查询的条件
var exactValue = {
		equal:"1",
		include :"2",
		noEqual:"3",
		noInclude:"4"
}